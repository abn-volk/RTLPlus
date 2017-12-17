package org.uet.dse.rtlplus.parser.ast;

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
		System.out.println(String.format("Rule: %s", name));

	}


}
