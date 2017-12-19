package org.uet.dse.rtlplus.mm;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.uet.dse.rtlplus.parser.RTLKeyword;

public class MTggRule {

	private String name;
	private MRule srcRule;
	private MRule trgRule;
	private MCorrRule corrRule;
	private String html;

	public MTggRule(String _name, MRule _srcRule, MRule _trgRule, MCorrRule _corrRule, String _str) {
		name = _name;
		srcRule = _srcRule;
		trgRule = _trgRule;
		corrRule = _corrRule;
		html = _str;
	}

	public String getName() {
		return name;
	}

	public MRule getSrcRule() {
		return srcRule;
	}

	public MRule getTrgRule() {
		return trgRule;
	}

	public MCorrRule getCorrRule() {
		return corrRule;
	}

	public String getHtml() {
		return html;
	}

	public void genForwardTransformation(StringBuilder ops, StringBuilder cons) {
		System.out.println("Generating forward transformation for " + name);
		// Operations
		StringBuilder sb = new StringBuilder();
		sb.append(name + RTLKeyword.forwardTransform + " (");
		// matchSR: Tuple (obj1: cls1, obj2: cls2)
		String sr = srcRule.genMatchBoth();
		if (!sr.isEmpty())
			sb.append("\n    matchS: Tuple(").append(sr).append(")");
		// matchCL
		String cl = corrRule.genMatchLeft();
		if (!cl.isEmpty()) {
			if (!sr.isEmpty())
				sb.append(',');
			sb.append("\n    matchC: Tuple(").append(cl).append(")");
		}
		// matchTL
		String tl = trgRule.genMatchLeft();
		if (!tl.isEmpty()) {
			if (!sr.isEmpty() || !cl.isEmpty())
				sb.append(',');
			sb.append("\n    matchT: Tuple(").append(tl).append(")");
		}
		sb.append(")\n");
		ops.append(sb);
		// Constraints
		if (!sr.isEmpty() || !cl.isEmpty() || !!tl.isEmpty()) {
			cons.append("\ncontext RuleCollection::").append(sb).append("pre: ").append(srcRule.genLetBoth("matchS"))
					.append(corrRule.genLetLeft("matchC")).append(trgRule.genLetLeft("matchT"));
			String srcCond = srcRule.genPreCondBoth();
			String trgCond = trgRule.genPreCondLeft();
			String corrCond = corrRule.genPreCondLeft();
			String result = Arrays.asList(srcCond, trgCond, corrCond).stream().filter(it -> !it.isEmpty())
					.collect(Collectors.joining(" and\n"));
			if (result.isEmpty())
				cons.append("true\n");
			else
				cons.append(result);
			// System.out.println("Source cond: " + srcCond);
			// System.out.println("Target cond: " + trgCond);
			// System.out.println("Correlation cond: " + corrCond);
		}
	}

	public void genBackwardTransformation(StringBuilder ops, StringBuilder cons) {
		System.out.println("Generating backward transformation for " + name);
		StringBuilder sb = new StringBuilder();
		sb.append(name + RTLKeyword.backwardTransform + " (");
		// matchSL: Tuple (obj1: cls1, obj2: cls2)
		String sl = srcRule.genMatchLeft();
		if (!sl.isEmpty())
			sb.append("\n    matchS: Tuple(").append(sl).append(")");
		// matchCL
		String cl = corrRule.genMatchLeft();
		if (!cl.isEmpty()) {
			if (!sl.isEmpty())
				sb.append(',');
			sb.append("\n    matchC: Tuple(").append(cl).append(")");
		}
		// matchTR
		String tr = trgRule.genMatchBoth();
		if (!tr.isEmpty()) {
			if (!sl.isEmpty() || !cl.isEmpty())
				sb.append(',');
			sb.append("\n    matchT: Tuple(").append(tr).append(")");
		}
		sb.append(")\n");
		ops.append(sb);
		// TODO:
		if (!sl.isEmpty() || !cl.isEmpty() || !!tr.isEmpty()) {
			cons.append("\ncontext RuleCollection::").append(sb).append("pre: ").append(srcRule.genLetLeft("matchS"))
					.append(corrRule.genLetLeft("matchC")).append(trgRule.genLetBoth("matchT"));
			String srcCond = srcRule.genPreCondLeft();
			String trgCond = trgRule.genPreCondBoth();
			String corrCond = corrRule.genPreCondLeft();
			String result = Arrays.asList(srcCond, trgCond, corrCond).stream().filter(it -> !it.isEmpty())
					.collect(Collectors.joining(" and\n"));
			if (result.isEmpty())
				cons.append("true\n");
			else
				cons.append(result);
			// System.out.println("Source cond: " + srcCond);
			// System.out.println("Target cond: " + trgCond);
			// System.out.println("Correlation cond: " + corrCond);
		}
	}

	public void genIntegration(StringBuilder ops, StringBuilder cons) {
		System.out.println("Generating integration transformation for " + name);
		StringBuilder sb = new StringBuilder();
		sb.append(name + RTLKeyword.integration + " (");
		// matchSL: Tuple (obj1: cls1, obj2: cls2)
		String sr = srcRule.genMatchBoth();
		if (!sr.isEmpty())
			sb.append("\n    matchS: Tuple(").append(sr).append(")");
		// matchCL
		String cl = corrRule.genMatchLeft();
		if (!cl.isEmpty()) {
			if (!sr.isEmpty())
				sb.append(',');
			sb.append("\n    matchC: Tuple(").append(cl).append(")");
		}
		// matchTR
		String tr = trgRule.genMatchBoth();
		if (!tr.isEmpty()) {
			if (!sr.isEmpty() || !tr.isEmpty())
				sb.append(',');
			sb.append("\n    matchT: Tuple(").append(tr).append(")");
		}
		sb.append(")\n");
		ops.append(sb);
		// TODO:
		if (!sr.isEmpty() || !cl.isEmpty() || !!tr.isEmpty()) {
			cons.append("\ncontext RuleCollection::").append(sb).append("pre: ").append(srcRule.genLetBoth("matchS"))
					.append(corrRule.genLetLeft("matchC")).append(trgRule.genLetBoth("matchT"));
			String srcCond = srcRule.genPreCondBoth();
			String trgCond = trgRule.genPreCondBoth();
			String corrCond = corrRule.genPreCondLeft();
			String result = Arrays.asList(srcCond, trgCond, corrCond).stream().filter(it -> !it.isEmpty())
					.collect(Collectors.joining(" and\n"));
			if (result.isEmpty())
				cons.append("true\n");
			else
				cons.append(result);
			// System.out.println("Source cond: " + srcCond);
			// System.out.println("Target cond: " + trgCond);
			// System.out.println("Correlation cond: " + corrCond);
		}
	}
}
