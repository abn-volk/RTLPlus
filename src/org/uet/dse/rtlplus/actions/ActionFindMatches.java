package org.uet.dse.rtlplus.actions;

import java.util.List;

import org.tzi.use.runtime.gui.IPluginAction;
import org.tzi.use.runtime.gui.IPluginActionDelegate;
import org.uet.dse.rtlplus.Main;
import org.uet.dse.rtlplus.matching.ForwardMatchManager;
import org.uet.dse.rtlplus.matching.Match;

public class ActionFindMatches implements IPluginActionDelegate {

	@Override
	public void performAction(IPluginAction pluginAction) {
		// TODO Auto-generated method stub
		ForwardMatchManager manager = new ForwardMatchManager(pluginAction.getSession().system().state(),
				pluginAction.getParent().logWriter(), false);
		List<Match> matches = manager.findMatchForRules(Main.getTggRuleCollection().getRuleList());
		for (Match match : matches) {
			// System.out.println(match.toString());
			boolean success = match.run(pluginAction.getSession().system().state(), pluginAction.getParent().logWriter());
			if (!success) {
				pluginAction.getParent().logWriter().println("Failed to run match");
			}
			
		}
	}

}
