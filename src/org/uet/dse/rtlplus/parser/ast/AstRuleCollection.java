package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

import org.uet.dse.rtlplus.parser.Context;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.sys.MSystemException;
import org.uet.dse.rtlplus.mm.MRuleCollection;
import org.uet.dse.rtlplus.parser.RTLKeyword;

public class AstRuleCollection {
	
	private String name;
	private String direction;
	private List<AstTggRule> ruleList = new ArrayList<>(1);
	
	public String getName() {
		return name;
	}

	public AstRuleCollection(Object _name) {
		name = (String) _name;
	}

	public void addRuleDefinition(AstTggRule rule) {
		ruleList.add(rule);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(RTLKeyword.transformation).append(' ').append(name).append(' ')
			.append(RTLKeyword.direction).append(' ').append(direction)
			.append('\n');
		for (AstTggRule rule : ruleList) {
			sb.append(rule.toString()).append('\n');
		}
		return sb.toString();
	}

	public void setDirection(Object object) {
		direction = (String) object;
	}
	
	public MRuleCollection gen(Context ctx) throws MInvalidModelException, MSystemException {
		MRuleCollection collection = new MRuleCollection();
		for (AstTggRule rule : ruleList) {
			collection.addRule(rule.gen(ctx));
		}
		return collection;
	}
}
