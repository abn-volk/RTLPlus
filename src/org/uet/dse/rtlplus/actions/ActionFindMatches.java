package org.uet.dse.rtlplus.actions;

import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.runtime.gui.IPluginAction;
import org.tzi.use.runtime.gui.IPluginActionDelegate;
import org.tzi.use.uml.sys.MSystemState;
import org.uet.dse.rtlplus.Main;
import org.uet.dse.rtlplus.gui.MatchListDialog;
import org.uet.dse.rtlplus.matching.BackwardMatchManager;
import org.uet.dse.rtlplus.matching.ForwardMatchManager;
import org.uet.dse.rtlplus.matching.IntegrationMatchManager;
import org.uet.dse.rtlplus.matching.Match;
import org.uet.dse.rtlplus.matching.MatchManager;
import org.uet.dse.rtlplus.mm.MRuleCollection.TransformationType;

import com.google.common.eventbus.EventBus;

public class ActionFindMatches implements IPluginActionDelegate {

	@Override
	public void performAction(IPluginAction pluginAction) {
		// TODO Show a list of matches and ask the user to run one
		// TODO Detect transformation type
		MainWindow mainWindow = pluginAction.getParent();
		PrintWriter logWriter = mainWindow.logWriter();
		MSystemState state = pluginAction.getSession().system().state();
		MatchManager manager = null;
		switch (Main.getTggRuleCollection().getType()) {
		case FORWARD:
		case SYNCHRONIZATION:
			manager = new ForwardMatchManager(state, (Main.getTggRuleCollection().getType() == TransformationType.SYNCHRONIZATION));
			break;
		case BACKWARD:
			manager = new BackwardMatchManager(state, false);
			break;
		case INTEGRATION:
			manager = new IntegrationMatchManager(state, false);
			break;
		}
		if (manager != null) {
			List<Match> matches = manager.findMatchesForRules(Main.getTggRuleCollection().getRuleList());
			if (matches.isEmpty())
				JOptionPane.showMessageDialog(mainWindow, "No matches were found.");
			else {
				EventBus eventBus = pluginAction.getSession().system().getEventBus();
				URL url = Main.class.getResource("/resources/list.png");
				ViewFrame vf = new ViewFrame("Match list", null, "");
				vf.setFrameIcon(new ImageIcon(url));
				MatchListDialog dialog = new MatchListDialog(vf, matches, eventBus, state, logWriter);
				vf.setContentPane(dialog);
				vf.pack();
				mainWindow.addNewViewFrame(vf);
			}
		}
		else JOptionPane.showMessageDialog(mainWindow, "This transformation type is unsupported.");
	}

}
