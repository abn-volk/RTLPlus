package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class AstPattern {
	private List<AstObject> objList = new ArrayList<>();
	private List<AstLink> lnkList = new ArrayList<>();
	private List<AstCondition> condList = new ArrayList<>();

	public void addObject(AstObject obj) {
		objList.add(obj);
	}

	public void addLink(AstLink lnk) {
		lnkList.add(lnk);
	}

	public void addCondition(AstCondition cond) {
		condList.add(cond);
	}

}
