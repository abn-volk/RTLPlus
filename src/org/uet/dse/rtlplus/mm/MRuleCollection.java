package org.uet.dse.rtlplus.mm;

import java.util.List;

public class MRuleCollection {
	private String name;
	private List<MRule> ruleList;

	public String getName() {
		return name;
	}
	
	public void addRule(MRule rule) {
		ruleList.add(rule);
	}
}
