package org.uet.dse.rtlplus.sync;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.util.soil.VariableEnvironment;
import org.uet.dse.rtlplus.Main;
import org.uet.dse.rtlplus.gui.MatchListDialog;
import org.uet.dse.rtlplus.gui.MatchListDialogResult;
import org.uet.dse.rtlplus.matching.BackwardMatchManager;
import org.uet.dse.rtlplus.matching.ForwardMatchManager;
import org.uet.dse.rtlplus.matching.Match;
import org.uet.dse.rtlplus.matching.MatchManager;
import org.uet.dse.rtlplus.mm.MRuleCollection.Side;
import org.uet.dse.rtlplus.mm.MRuleCollection.TransformationType;
import org.uet.dse.rtlplus.mm.MTggRule;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@SuppressWarnings("serial")
public class SyncWorker extends JPanel {

	// Search for matches when an object is created
	private Map<String, Set<MTggRule>> rulesForSrcClass;
	private Map<String, Set<MTggRule>> rulesForTrgClass;
	private Map<String, Side> classMap;
	// Collect info when transformations are run
	private boolean running = false;
	// Needed to find matches
	private MainWindow mainWindow;
	private MSystemState state;
	private PrintWriter logWriter;
	private EventBus eventBus;
	private UseSystemApi api;
	private boolean syncForward = true;
	// Needed for attribute assignment
	private Map<String, Set<String>> corrObjsForSrc;
	private Map<String, Set<String>> corrObjsForTrg;
	private Map<String, Set<String>> forwardCmdsForCorr;
	private Map<String, Set<String>> backwardCmdsForCorr;
	// Correlation object dependencies
	private Map<String, Set<String>> corrObjDependencies;
	private Map<String, OperationEnterEvent> pastTransformations;
	private Map<String, String> transformationForCorrObj;
	// Cache for current transformation
	private OperationEnterEvent event;

	public SyncWorker(MainWindow parent, Session session) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		JLabel label1 = new JLabel(
				"<html>Keep this window open to track incremental changes and update the model accordingly.</html>");
		add(label1);
		mainWindow = parent;
		api = UseSystemApi.create(session);
		classMap = Main.getTggRuleCollection().getClassMap();
		state = session.system().state();
		logWriter = parent.logWriter();
		eventBus = session.system().getEventBus();
		syncForward = Main.getTggRuleCollection().getType() == TransformationType.FORWARD;
		rulesForSrcClass = Main.getSyncData().getRulesForSrcClass();
		rulesForTrgClass = Main.getSyncData().getRulesForTrgClass();
		corrObjsForSrc = Main.getSyncData().getCorrObjsForSrc();
		corrObjsForTrg = Main.getSyncData().getCorrObjsForTrg();
		forwardCmdsForCorr = Main.getSyncData().getForwardCmdsForCorr();
		backwardCmdsForCorr = Main.getSyncData().getBackwardCmdsForCorr();
		corrObjDependencies = Main.getSyncData().getCorrObjDependencies();
		pastTransformations = Main.getSyncData().getPastTransformations();
		transformationForCorrObj = Main.getSyncData().getTransformationForCorrObj();
		// Listen to changes
		eventBus.register(this);
		System.out.println("Subscribed to EventBus");
	}
	
	@Subscribe
	public void onOperationEntered(OperationEnterEvent e) {
		running = true;
		// Initialise cache
		event = e;
	}
	
	@Subscribe
	public void onOperationExited(OperationExitEvent e) {
		if (e.isSuccess()) {
			String transName = Main.getUniqueNameGenerator().generate(e.getOpName());
			Main.getSyncData().addTransformation(transName, event);
		}
		running = false;
	}

	@Subscribe
	public void onObjectCreated(ObjectCreatedEvent e) {
		MObject obj = e.getCreatedObject();
		//System.out.println("Object created: " + obj.name());
		Side side = classMap.get(obj.cls().name());
		if (!running) {
			switch (side) {
			case SOURCE:
				// Find forward matches
				findForwardMatches(rulesForSrcClass.get(obj.cls().name()), Arrays.asList(obj), syncForward);
				break;
			case TARGET:
				// Find backward matches
				findBackwardMatches(rulesForSrcClass.get(obj.cls().name()), Arrays.asList(obj), !syncForward);
				break;
			default:
				break;
			}
		}
	}

	@Subscribe
	public void onObjectDestroyed(ObjectDestroyedEvent e) {
		String objName = e.getDestroyedObject().name();
		if (!running) {
			eventBus.unregister(this);
			Set<String> corrs = corrObjsForSrc.getOrDefault(objName, new HashSet<>());
			Set<String> allCorrs = findAllDependencies(corrs, new HashSet<>(corrs));
			Set<String> transToUndo = new HashSet<>();
			for (String corr : allCorrs) {
				String tran = transformationForCorrObj.get(corr);
				if (tran != null)
					transToUndo.add(tran);
			}
			// Undo transformations
			for (String tran : transToUndo) {
				OperationEnterEvent event = pastTransformations.get(tran);
				if (event != null) {
					undoTransformation(event);
					pastTransformations.remove(tran);
				}
			}
			eventBus.register(this);
		}
	}
	
	private void undoTransformation(OperationEnterEvent event) {
		// Delete links
		for (CachedLink lnk : event.getLinksToCreate()) {
			try {
				api.deleteLink(lnk.getAssociation(), lnk.getLinkedObjects());
			}
			catch (UseApiException ignored) {}
		}
		// Delete objects
		for (String objName : event.getObjectsToCreate().values()) {
			try {
				api.deleteObject(objName);
			} catch (UseApiException ignored) {}
		}
		// Delete correlation objects
		for (String objName : event.getCorrObjsToCreate().values()) {
			try {
				api.deleteObject(objName);
			} catch (UseApiException ignored) {}
		}
		logWriter.println("Transformation undone: " + event.getOpName());
	}
	
	private Set<String> findAllDependencies(Set<String> corrObjs, Set<String> allDeps) {
		Set<String> nextDeps = new HashSet<>();
		for (String corrObj : corrObjs) {
			Set<String> deps = corrObjDependencies.get(corrObj);
			if (deps != null) {
				for (String dep : deps) {
					if (allDeps.add(dep))
						nextDeps.add(dep);
				}
			}
		}
		if (!nextDeps.isEmpty()) {
			findAllDependencies(nextDeps, allDeps);
		}
		return allDeps;
	}

	@Subscribe
	public void onLinkInserted(LinkInsertedEvent e) {
		if (running) {
			// Link between src/trg and correlation
			List<MObject> objs = e.getParticipants();
			Side s1 = classMap.get(objs.get(0).cls().name());
			if (objs.size() == 2) {
				Side s2 = classMap.get(objs.get(1).cls().name());
				if (s1 == Side.SOURCE && s2 == Side.CORRELATION) {
					Main.getSyncData().addSrcCorrLink(objs.get(0).name(), objs.get(1).name());
				}
				if (s1 == Side.TARGET && s2 == Side.CORRELATION) {
					Main.getSyncData().addTrgCorrLink(objs.get(0).name(), objs.get(1).name());
				}
			}
		} else {
			List<MObject> objects = e.getLink().linkedObjects();
			Side side = classMap.get(objects.get(0).cls().name());
			Set<MTggRule> ruleList = new HashSet<>();
			boolean sameSide = true;
			for (MObject obj : objects) {
				if (classMap.get(obj.cls().name()) != side) {
					sameSide = false;
					break;
				}
				if (side == Side.SOURCE)
					ruleList.addAll(rulesForSrcClass.get(obj.cls().name()));
				else if (side == Side.TARGET)
					ruleList.addAll(rulesForTrgClass.get(obj.cls().name()));
			}
			if (sameSide) {
				switch (side) {
				case SOURCE:
					findForwardMatches(ruleList, objects, syncForward);
					break;
				case TARGET:
					findBackwardMatches(ruleList, objects, !syncForward);
					break;
				default:
					break;
				}
			}
		}
	}

	@Subscribe
	public void onLinkDeleted(LinkDeletedEvent e) {
		if (!running) {
			List<MObject> objects = e.getLink().linkedObjects();
			eventBus.unregister(this);
			checkConsistencyAndUndo(objects.stream().map(it -> it.name()).collect(Collectors.toList()));	
			eventBus.register(this);
		}
	}

	@Subscribe
	public void onAttributeAssigned(AttributeAssignedEvent e) {
		if (!running) {
			eventBus.unregister(this);
			//System.out.println("Attribute assignment");
			switch (classMap.get(e.getObject().cls().name())) {
			case SOURCE:
				Set<String> corrObjs = corrObjsForSrc.get(e.getObject().name());
				if (corrObjs != null) {
					//System.out.println(corrObjs);
					for (String corr : corrObjs) {
						MObject corrObj = state.objectByName(corr);
						if (corrObj != null) {
							Set<String> strs = forwardCmdsForCorr.get(corrObj.cls().name());
							if (strs != null) {
								//System.out.println("Strs = " + strs.toString());
								for (String str : strs) {
									String line = str.replace("self", corr);
									Shell.getInstance().processLineSafely("! set " + line);
								}
							}
						}
					}
				}
				checkConsistencyAndUndo(Arrays.asList(e.getObject().name()));
				findForwardMatches(rulesForSrcClass.get(e.getObject().cls().name()), Arrays.asList(e.getObject()), syncForward);
				break;
			case TARGET:
				Set<String> cObjs = corrObjsForTrg.get(e.getObject().name());
				if (cObjs != null) {
					for (String corr : cObjs) {
						MObject corrObj = state.objectByName(corr);
						if (corrObj != null) {
							Set<String> strs = backwardCmdsForCorr.get(corrObj.cls().name());
							if (strs != null) {
								for (String str : strs) {
									String line = str.replace("self", corr);
									Shell.getInstance().processLineSafely("! set " + line);
								}
							}
						}
					}
				}
				checkConsistencyAndUndo(Arrays.asList(e.getObject().name()));
				findBackwardMatches(rulesForSrcClass.get(e.getObject().cls().name()), Arrays.asList(e.getObject()), !syncForward);
				break;
			default:
				break;
			}
			eventBus.register(this);
		}
	}
	
	private void checkConsistencyAndUndo(List<String> objects) {
		Set<OperationEnterEvent> transToUndo = new HashSet<>();
		for (OperationEnterEvent e : pastTransformations.values()) {
			if (e.getMatchedObjects().values().containsAll(objects)) {
				MTggRule tggRule = Main.getTggRuleCollection().getRuleByName(e.getRuleName());
				String preCond = syncForward? tggRule.getSrcRule().genPreCondBoth(false)
						: tggRule.getTrgRule().genPreCondBoth(false);
				preCond = preCond.trim();
				logWriter.println("Preconditions: " + preCond);
				if (!preCond.isEmpty()) {
					// Assign variables
					VariableEnvironment varEnv = state.system().getVariableEnvironment();
					boolean mustUndo = false;
					for(Map.Entry<String, String> assignment : e.getMatchedObjects().entrySet()) {
						MObject obj = api.getObject(assignment.getValue());
						// Object does not exist, inconsistency found
						if (obj == null) {
							mustUndo = true;
							break;
						}
						varEnv.assign(assignment.getKey(), new ObjectValue(obj.cls(), obj));
					}
					if (mustUndo)
						transToUndo.add(e);
					else {
						// Evaluate preconditions
						try {
							Value val = api.evaluate(preCond);
							if (val.isBoolean() && ((BooleanValue) val).isFalse())
								mustUndo = true;
						} catch (UseApiException ex) {
							ex.printStackTrace();
							mustUndo = true;
						}
						if (mustUndo)
							transToUndo.add(e);
					}
				}				
			}
		}
		for (OperationEnterEvent tran : transToUndo) {
			undoTransformation(tran);
		}
	}

	private void findForwardMatches(Collection<MTggRule> ruleList, List<MObject> objects, boolean sync) {
		MatchListDialogResult res = new MatchListDialogResult();
		while (res.isResult()) {
			MatchManager fManager = new ForwardMatchManager(state, sync);
			List<Match> fMatches = fManager.findMatchesForRulesAndObjects(ruleList, objects);
			if (fMatches.isEmpty()) {
				logWriter.println("No forward matches available.");
			} else {
				URL url = Main.class.getResource("/resources/list.png");
				ViewFrame vf = new ViewFrame("New matches found!", null, "");
				vf.setFrameIcon(new ImageIcon(url));
				MatchListDialog dialog = new MatchListDialog(vf, fMatches, eventBus, state, logWriter, res);
				vf.setContentPane(dialog);
				vf.pack();
				mainWindow.addNewViewFrame(vf);
			}
		}
	}

	private void findBackwardMatches(Collection<MTggRule> ruleList, List<MObject> objects, boolean sync) {
		MatchListDialogResult res = new MatchListDialogResult();
		while (res.isResult()) {
			MatchManager bManager = new BackwardMatchManager(state, sync);
			List<Match> bMatches = bManager.findMatchesForRulesAndObjects(ruleList, objects);
			if (bMatches.isEmpty()) {
				logWriter.println("No backward matches available.");
			} else {
				URL url = Main.class.getResource("/resources/list.png");
				ViewFrame vf = new ViewFrame("New matches found!", null, "");
				vf.setFrameIcon(new ImageIcon(url));
				MatchListDialog dialog = new MatchListDialog(vf, bMatches, eventBus, state, logWriter, res);
				vf.setContentPane(dialog);
				vf.pack();
				mainWindow.addNewViewFrame(vf);
			}
		}
	}

	public void unsubscribe() {
		eventBus.unregister(this);
	}

}
