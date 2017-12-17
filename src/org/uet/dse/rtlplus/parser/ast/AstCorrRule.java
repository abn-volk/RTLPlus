package org.uet.dse.rtlplus.parser.ast;

public class AstCorrRule {
	
	private AstCorr lhs;
	private AstCorr rhs;

	public AstCorrRule(AstCorr _lhs, AstCorr _rhs) {
		lhs = _lhs;
		rhs = _rhs;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(\n").append(lhs.toString())
			.append("){\n").append(rhs.toString())
			.append("\n}");
		return sb.toString();
	}
}
