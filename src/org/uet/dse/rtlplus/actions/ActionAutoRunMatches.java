package org.uet.dse.rtlplus.actions;

import java.util.List;

import javax.swing.JOptionPane;

import org.tzi.use.runtime.gui.IPluginAction;
import org.tzi.use.runtime.gui.IPluginActionDelegate;
import org.uet.dse.rtlplus.Main;
import org.uet.dse.rtlplus.matching.BackwardMatchManager;
import org.uet.dse.rtlplus.matching.ForwardMatchManager;
import org.uet.dse.rtlplus.matching.IntegrationMatchManager;
import org.uet.dse.rtlplus.matching.Match;
import org.uet.dse.rtlplus.matching.MatchManager;

public class ActionAutoRunMatches implements IPluginActionDelegate {

	@Override
	public void performAction(IPluginAction pluginAction) {
		MatchManager manager = null;
		switch (Main.getTggRuleCollection().getType()) {
		case FORWARD:
			manager = new ForwardMatchManager(pluginAction.getSession().system().state(), false);
			break;
		case BACKWARD:
			manager = new BackwardMatchManager(pluginAction.getSession().system().state(), false);
			break;
		case INTEGRATION:
			manager = new IntegrationMatchManager(pluginAction.getSession().system().state(), false);
			break;
		case SYNCHRONIZATION_FORWARD:
			manager = new ForwardMatchManager(pluginAction.getSession().system().state(), true);
			break;
		case SYNCHRONIZATION_BACKWARD:
			manager = new BackwardMatchManager(pluginAction.getSession().system().state(), true);
			break;
		}
		int i = 0;
		while (true) {
			List<Match> matches = manager.findMatchesForRules(Main.getTggRuleCollection().getRuleList());
			if (matches.isEmpty())
				break;
			for (Match match : matches) {
				// System.out.println(match.toString());
				boolean success = match.run(pluginAction.getSession().system().state(),
						pluginAction.getParent().logWriter());
				if (!success) {
					pluginAction.getParent().logWriter().println("Failed to run match");
				} else {
					i++;
				}

			}
		}
		JOptionPane.showMessageDialog(pluginAction.getParent(), String.format("Completed %d match(es).", i));
	}

}
