package org.uet.dse.rtlplus.parser.ast;

public class AstAttrCorr {
	
	private String lhs;
	private String rhs;
	
	public String getLhs() {
		return lhs;
	}
	
	public String getRhs() {
		return rhs;
	}

	public AstAttrCorr(Object left, Object right) {
		lhs = (String) left;
		rhs = (String) right;
		System.out.println(String.format("Attribute correlation: %s = %s", lhs, rhs));
	}

}
