package org.uet.dse.rtlplus;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginRuntime;
import org.uet.dse.rtlplus.mm.MRuleCollection;

public class Main implements IPlugin {
	@Override
	public String getName() {
		return "RTL plugin";
	}

	@Override
	public void run(IPluginRuntime pluginRuntime) throws Exception {
	}
	
	private static MRuleCollection fTggRules = new MRuleCollection();
    private static MainWindow fMainWindow = null;
    
    public static MRuleCollection getTggRuleCollection() { return fTggRules; }
    
    public static void setRTLRule (MRuleCollection rules) {
        fTggRules = rules;
    }

	public static void setMainWindow(MainWindow fParent) {
		fMainWindow = fParent;
	}
	
}
