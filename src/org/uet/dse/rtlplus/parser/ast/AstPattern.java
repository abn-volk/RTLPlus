package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class AstPattern {
	private List<AstObject> objList = new ArrayList<>();
	private List<AstLink> lnkList = new ArrayList<>();
	private List<String> condList = new ArrayList<>();

	public void addObject(AstObject obj) {
		objList.add(obj);
	}

	public void addLink(AstLink lnk) {
		lnkList.add(lnk);
	}

	public void addCondition(String cond) {
		condList.add(cond);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (AstObject obj : objList) {
			sb.append(obj.toString()).append('\n');
		}
		for (AstLink lnk : lnkList) {
			sb.append(lnk.toString()).append('\n');
		}
		for (String cond : condList) {
			sb.append('[').append(cond).append(']').append('\n');
		}
		return sb.toString();
	}

}
