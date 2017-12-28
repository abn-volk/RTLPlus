package org.uet.dse.rtlplus;

import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.util.UniqueNameGenerator;
import org.uet.dse.rtlplus.mm.MRuleCollection;
import org.uet.dse.rtlplus.mm.MRuleCollection.TransformationType;

public class Main implements IPlugin {
	@Override
	public String getName() {
		return "RTL plugin";
	}

	@Override
	public void run(IPluginRuntime pluginRuntime) throws Exception {
	}
	
	private static MRuleCollection fTggRules = new MRuleCollection(TransformationType.FORWARD);
    private static UniqueNameGenerator fUniqueNameGenerator;
    
    public static MRuleCollection getTggRuleCollection() { return fTggRules; }
    
    public static void setRTLRule (MRuleCollection rules) {
        fTggRules = rules;
        fUniqueNameGenerator = new UniqueNameGenerator();
    }
    
	public static UniqueNameGenerator getUniqueNameGenerator() {
		return fUniqueNameGenerator;
	}
	
}
