package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class AstInvariantTgg {
	
	private String name;
	private List<String> condList = new ArrayList<>(1);

	public String getName() {
		return name;
	}
	
	public List<String> getConditions() {
		return condList;
	}

	public AstInvariantTgg(Object object) {
		name = (String) object;
	}

	public void addCondition(String cond) {
		condList.add(cond);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append(':');
		for (String cond : condList) {
			sb.append('[').append(cond).append(']');
		}
		return sb.toString();
	}
}
