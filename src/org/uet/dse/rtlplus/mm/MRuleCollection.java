package org.uet.dse.rtlplus.mm;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.uet.dse.rtlplus.parser.Context;
import org.uet.dse.rtlplus.parser.RTLKeyword;

public class MRuleCollection {
	public static enum TransformationType {
		FORWARD, BACKWARD, INTEGRATION, COEVOLUTION, SYNCHRONIZATION
	};

	public static enum Side {
		SOURCE, TARGET, CORRELATION, OTHER
	};

	private String name;
	private TransformationType type;
	private Map<String, MTggRule> rules = new LinkedHashMap<>();
	private Context context;
	private Map<String, Side> classMap = new HashMap<>();
	public Map<String, Side> getClassMap() {
		return classMap;
	}

	public MRuleCollection(TransformationType _type) {
		type = _type;
	}

	public Collection<MTggRule> getRuleList() {
		return rules.values();
	}

	public String getName() {
		return name;
	}

	public TransformationType getType() {
		return type;
	}

	public void addRule(MTggRule rule) {
		rules.put(rule.getName(), rule);
	}
	
	public MTggRule getRuleByName(String name) {
		return rules.get(name);
	}

	public void setContext(Context ctx) {
		context = ctx;
		for (MClass cls : ctx.getCorrClasses()) {
			classMap.put(cls.name(), Side.CORRELATION);
		}
		classMap.put("RuleCollection", Side.CORRELATION);
	}

	public Context getContext() {
		return context;
	}

	public void setSourceModel(MModel model) {
		for (MClass cls : model.classes()) {
			classMap.put(cls.name(), Side.SOURCE);
		}
	}

	public void setTargetModel(MModel model) {
		for (MClass cls : model.classes()) {
			classMap.put(cls.name(), Side.TARGET);
		}
	}

	public String genCorrInvs() {
		boolean hasConstraints = false;
		StringBuilder sb = new StringBuilder("---------- Correlation invariants ----------\nconstraints\n");
		for (MTggRule rule : rules.values()) {
			Map<String, List<String>> lhsInvs = rule.getCorrRule().getLhs().getInvariantList();
			if (lhsInvs != null) {
				for (String cls : lhsInvs.keySet()) {
					if (lhsInvs.get(cls).size() > 0) {
						hasConstraints = true;
						sb.append(RTLKeyword.context + " " + cls + " inv:\n    ");
						sb.append(lhsInvs.get(cls).stream().collect(Collectors.joining(" and ")));
						sb.append('\n');
					}
				}
			}
			Map<String, List<String>> rhsInvs = rule.getCorrRule().getRhs().getInvariantList();
			if (rhsInvs != null) {
				for (String cls : rhsInvs.keySet()) {
					if (rhsInvs.get(cls).size() > 0) {
						hasConstraints = true;
						sb.append(RTLKeyword.context + " " + cls + " inv:\n    ");
						sb.append(rhsInvs.get(cls).stream().collect(Collectors.joining(" and ")));
						sb.append('\n');
					}
				}
			}
		}
		sb.append('\n');
		if (hasConstraints)
			return sb.toString();
		else
			return "";
	}

	public void genForwardTransformation(StringBuilder ops, StringBuilder cons) {
		for (MTggRule rule : rules.values()) {
			rule.genForwardTransformation(ops, cons);
		}
	}

	public void genBackwardTransformation(StringBuilder ops, StringBuilder cons) {
		for (MTggRule rule : rules.values()) {
			rule.genBackwardTransformation(ops, cons);
		}
	}

	public void genIntegration(StringBuilder ops, StringBuilder cons) {
		for (MTggRule rule : rules.values()) {
			rule.genIntegration(ops, cons);
		}
	}
	
	public void genCoevolution(StringBuilder ops, StringBuilder cons) {
		for (MTggRule rule : rules.values()) {
			rule.genCoevolution(ops, cons);
		}
	}
}
