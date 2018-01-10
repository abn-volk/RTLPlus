package org.uet.dse.rtlplus.sync;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.sys.MObject;
import org.uet.dse.rtlplus.mm.MRuleCollection;
import org.uet.dse.rtlplus.mm.MTggRule;

public class SyncData {
	private Map<String, Set<MTggRule>> rulesForSrcClass;
	private Map<String, Set<MTggRule>> rulesForTrgClass;
	
	public SyncData(MRuleCollection rules) {
		rulesForSrcClass = new HashMap<>();
		rulesForTrgClass = new HashMap<>();
		for (MTggRule tggRule : rules.getRuleList()) {
			for (MObject obj : tggRule.getSrcRule().getNewObjects()) {
				Set<MTggRule> tggRules = rulesForSrcClass.get(obj.cls().name());
				if (tggRules == null) {
					tggRules = new HashSet<>();
					rulesForSrcClass.put(obj.cls().name(), tggRules);
				}
				tggRules.add(tggRule);

			}
			for (MObject obj : tggRule.getTrgRule().getNewObjects()) {
				Set<MTggRule> tggRules = rulesForSrcClass.get(obj.cls().name());
				if (tggRules == null) {
					tggRules = new HashSet<>();
					rulesForTrgClass.put(obj.cls().name(), tggRules);
				}
				tggRules.add(tggRule);

			}
		}
	}

	public Map<String, Set<MTggRule>> getRulesForSrcClass() {
		return rulesForSrcClass;
	}

	public Map<String, Set<MTggRule>> getRulesForTrgClass() {
		return rulesForTrgClass;
	}
	
	
}
