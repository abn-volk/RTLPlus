package org.uet.dse.rtlplus.actions;

import java.util.List;

import javax.swing.JOptionPane;

import org.tzi.use.runtime.gui.IPluginAction;
import org.tzi.use.runtime.gui.IPluginActionDelegate;
import org.uet.dse.rtlplus.Main;
import org.uet.dse.rtlplus.matching.ForwardMatchManager;
import org.uet.dse.rtlplus.matching.Match;

public class ActionAutoRunMatches implements IPluginActionDelegate {

	@Override
	public void performAction(IPluginAction pluginAction) {
		ForwardMatchManager manager = new ForwardMatchManager(pluginAction.getSession().system().state(), false);
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
