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
			manager = new ForwardMatchManager(pluginAction.getSession().system().state(), true);
			break;
		case BACKWARD:
			manager = new BackwardMatchManager(pluginAction.getSession().system().state(), true);
			break;
		default:
			manager = new IntegrationMatchManager(pluginAction.getSession().system().state(), false);
			break;
		}
		int i = 0;
		while (true) {
			List<Match> matches = manager.findMatches();
			if (matches.isEmpty())
				break;
			boolean success = false;
			for (Match match : matches) {
				// System.out.println(match.toString());
				success = match.run(pluginAction.getSession().system().state(),
						pluginAction.getParent().logWriter());
				if (!success) {
					pluginAction.getParent().logWriter().println("Failed to run match");
				} else {
					i++;
					break;
				}
			}
			if (!success)
				break;
		}
		JOptionPane.showMessageDialog(pluginAction.getParent(), String.format("Completed %d match(es).", i));
	}

}
