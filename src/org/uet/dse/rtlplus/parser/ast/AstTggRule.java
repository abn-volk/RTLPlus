package org.uet.dse.rtlplus.parser.ast;

import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.uet.dse.rtlplus.mm.MCorrRule;
import org.uet.dse.rtlplus.mm.MRule;
import org.uet.dse.rtlplus.mm.MTggRule;
import org.uet.dse.rtlplus.parser.Context;
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


	public MTggRule gen(Context ctx) throws MInvalidModelException, MSystemException {
		ctx.setLhsState(new MSystem(ctx.model()).state());
		ctx.setRhsState(new MSystem(ctx.model()).state());
		MRule srcRule = src.gen(ctx);
		MRule trgRule = trg.gen(ctx);
		MCorrRule corrRule = corr.gen(ctx);
		return new MTggRule(name, srcRule, trgRule, corrRule, toString());
	}

}
