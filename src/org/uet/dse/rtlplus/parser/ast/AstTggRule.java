package org.uet.dse.rtlplus.parser.ast;

import org.uet.dse.rtlplus.parser.RTLKeyword;

public class AstTggRule {
	
	private String name;
	private AstRule src;
	private AstRule trg;
	private AstCorrRule corr;

	public AstTggRule(Object object, AstRule _src, AstRule _trg, AstCorrRule _corr) {
		name = (String) object;
		src = _src;
		trg = _trg;
		corr = _corr;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(RTLKeyword.startTGGRule).append(' ').append(name).append('\n')
			.append(RTLKeyword.checkSource).append(src.toString())
			.append(RTLKeyword.checkTarget).append(trg.toString())
			.append(RTLKeyword.checkCorr).append(corr.toString())
			.append(RTLKeyword.endTGGRule).append('\n');
		return sb.toString();
	}

}
