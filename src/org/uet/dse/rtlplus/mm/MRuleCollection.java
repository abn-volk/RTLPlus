package org.uet.dse.rtlplus.mm;

import java.util.ArrayList;
import java.util.List;

public class MRuleCollection {
	private String name;
	private List<MTggRule> ruleList = new ArrayList<>();

	public List<MTggRule> getRuleList() {
		return ruleList;
	}

	public String getName() {
		return name;
	}
	
	public void addRule(MTggRule rule) {
		ruleList.add(rule);
	}
}
