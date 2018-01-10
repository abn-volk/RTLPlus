package org.uet.dse.rtlplus.sync;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.main.Session;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.uml.sys.events.OperationExitedEvent;
import org.uet.dse.rtlplus.Main;
import org.uet.dse.rtlplus.gui.MatchListDialog;
import org.uet.dse.rtlplus.matching.BackwardMatchManager;
import org.uet.dse.rtlplus.matching.ForwardMatchManager;
import org.uet.dse.rtlplus.matching.Match;
import org.uet.dse.rtlplus.matching.MatchManager;
import org.uet.dse.rtlplus.mm.MRuleCollection.Side;
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
	// Needed for deletion

	public SyncWorker(MainWindow parent, Session session) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		JLabel label1 = new JLabel("<html>Keep this window open to track incremental changes and update the model accordingly.</html>");
		add(label1);
		mainWindow = parent;
		classMap = Main.getTggRuleCollection().getClassMap();
		state = session.system().state();
		logWriter = parent.logWriter();
		eventBus = session.system().getEventBus();
		rulesForSrcClass = Main.getSyncData().getRulesForSrcClass();
		rulesForTrgClass = Main.getSyncData().getRulesForTrgClass();
		
		// Listen to changes
		eventBus.register(this);
		System.out.println("Subscribed to EventBus");
	}

	@Subscribe
	public void onOperationEntered(OperationEnteredEvent e) {
		System.out.println("Entered operation: " + e.getOperationCall().toString());
		if (e.getOperationCall().getOperation().cls().name().equals("RuleCollection")) {
			running = true;
		}
	}

	@Subscribe
	public void onOperationExited(OperationExitedEvent e) {
		System.out.println("Exited operation: " + e.getOperationCall().toString());
		if (e.getOperationCall().getOperation().cls().name().equals("RuleCollection")) {
			running = false;
		}
	}

	@Subscribe
	public void onObjectCreated(ObjectCreatedEvent e) {
		MObject obj = e.getCreatedObject();
		System.out.println("Object created: " + obj.name()); 
		if (running) {
		}
		else {
			switch (classMap.get(obj.cls().name())) {
			case SOURCE:
				// Find forward matches
				MatchManager fManager = new ForwardMatchManager(state, true);
				List<Match> fMatches = fManager.findMatchesForRulesAndObjects(rulesForSrcClass.get(obj.cls().name()),
						Arrays.asList(obj));
				if (!fMatches.isEmpty()) {
					URL url = Main.class.getResource("/resources/list.png");
					ViewFrame vf = new ViewFrame("Match list", null, "");
					vf.setFrameIcon(new ImageIcon(url));
					MatchListDialog dialog = new MatchListDialog(vf, fMatches, eventBus, state, logWriter);
					vf.setContentPane(dialog);
					vf.pack();
					mainWindow.addNewViewFrame(vf);
				}
				break;
			case TARGET:
				// Find backward matches
				MatchManager bManager = new BackwardMatchManager(state, true);
				List<Match> bMatches = bManager.findMatchesForRulesAndObjects(rulesForTrgClass.get(obj.cls().name()),
						Arrays.asList(obj));
				if (!bMatches.isEmpty()) {
					URL url = Main.class.getResource("/resources/list.png");
					ViewFrame vf = new ViewFrame("Match list", null, "");
					vf.setFrameIcon(new ImageIcon(url));
					MatchListDialog dialog = new MatchListDialog(vf, bMatches, eventBus, state, logWriter);
					vf.setContentPane(dialog);
					vf.pack();
					mainWindow.addNewViewFrame(vf);
				}
				break;
			default:
				break;
			}
		}
	}

	@Subscribe
	public void onObjectDestroyed(ObjectDestroyedEvent e) {
		MObject obj = e.getDestroyedObject();
		System.out.println("Object destroyed: " + obj.name()); 
		if (running) {

		} else {
			
		}
	}

	@Subscribe
	public void onLinkInserted(LinkInsertedEvent e) {
		if (running) {
		}
		else {
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
				switch(side) {
				case SOURCE:
					MatchManager fManager = new ForwardMatchManager(state, true);
					List<Match> fMatches = fManager.findMatchesForRulesAndObjects(ruleList, objects);
					if (fMatches.isEmpty())
						JOptionPane.showMessageDialog(mainWindow, "No matches were found.");
					else {
						URL url = Main.class.getResource("/resources/list.png");
						ViewFrame vf = new ViewFrame("Match list", null, "");
						vf.setFrameIcon(new ImageIcon(url));
						MatchListDialog dialog = new MatchListDialog(vf, fMatches, eventBus, state, logWriter);
						vf.setContentPane(dialog);
						vf.pack();
						mainWindow.addNewViewFrame(vf);
					}
					break;
				case TARGET:
					MatchManager bManager = new BackwardMatchManager(state, true);
					List<Match> bMatches = bManager.findMatchesForRulesAndObjects(ruleList, objects);
					if (bMatches.isEmpty())
						JOptionPane.showMessageDialog(mainWindow, "No matches were found.");
					else {
						URL url = Main.class.getResource("/resources/list.png");
						ViewFrame vf = new ViewFrame("Match list", null, "");
						vf.setFrameIcon(new ImageIcon(url));
						MatchListDialog dialog = new MatchListDialog(vf, bMatches, eventBus, state, logWriter);
						vf.setContentPane(dialog);
						vf.pack();
						mainWindow.addNewViewFrame(vf);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	@Subscribe
	public void onLinkDeleted(LinkDeletedEvent e) {
		if (running) {

		} else {

		}
	}

	@Subscribe
	public void onAttributeAssigned(AttributeAssignedEvent e) {
		if (running) {

		} else {

		}
	}
	
	public void unsubscribe() {
		eventBus.unregister(this);
	}

}
