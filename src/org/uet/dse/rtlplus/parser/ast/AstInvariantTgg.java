package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class AstInvariantTgg {
	
	private String name;
	private List<AstAttrCorr> corrList = new ArrayList<>(1);
	private List<AstCondition> condList = new ArrayList<>(1);

	public String getName() {
		return name;
	}

	public List<AstAttrCorr> getCorrList() {
		return corrList;
	}

	public AstInvariantTgg(Object object) {
		name = (String) object;
	}

	public void addAttrCorr(AstAttrCorr ac) {
		corrList.add(ac);
		
	}

	public void addCondition(AstCondition cond) {
		condList.add(cond);
	}

}
