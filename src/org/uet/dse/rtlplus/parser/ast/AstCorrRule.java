package org.uet.dse.rtlplus.parser.ast;

public class AstCorrRule {
	
	private AstCorr lhs;
	private AstCorr rhs;

	public AstCorrRule(AstCorr _lhs, AstCorr _rhs) {
		lhs = _lhs;
		rhs = _rhs;
	}

}
