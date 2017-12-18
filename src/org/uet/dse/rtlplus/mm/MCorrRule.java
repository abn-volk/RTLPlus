package org.uet.dse.rtlplus.mm;

public class MCorrRule {
	
	private MPattern lhs;
	private MPattern rhs;

	public MCorrRule(MPattern left, MPattern right) {
		lhs = left;
		rhs = right;
	}
	
	public MPattern getLhs() {
		return lhs;
	}

	public MPattern getRhs() {
		return rhs;
	}
}
