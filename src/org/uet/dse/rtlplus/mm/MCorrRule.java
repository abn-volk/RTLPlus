package org.uet.dse.rtlplus.mm;

public class MCorrRule extends MRule {
	public MCorrRule(MPattern left, MPattern right) {
		super(left, right);
	}
	
	public MPattern getLhs() {
		return lhs;
	}

	public MPattern getRhs() {
		return rhs;
	}
}
