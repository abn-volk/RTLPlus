package org.uet.dse.rtlplus.parser.ast;

public class AstRule {

	private AstPattern lhs;
	private AstPattern rhs;
	
	public AstRule(AstPattern _lhs, AstPattern _rhs) {
		lhs = _lhs;
		rhs = _rhs;
	}

}
