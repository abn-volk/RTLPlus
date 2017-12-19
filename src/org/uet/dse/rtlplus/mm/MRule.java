package org.uet.dse.rtlplus.mm;

import java.util.stream.Collectors;

import org.tzi.use.uml.sys.MObject;

public class MRule {

	protected MPattern lhs;
	protected MPattern rhs;

	public MRule(MPattern left, MPattern right) {
		lhs = left;
		rhs = right;
	}

	public String genMatchLeft() {
		return lhs.getObjectList().stream().map(it -> it.name() + ": " + it.cls().name())
				.collect(Collectors.joining(", "));
	}

	public String genMatchRight() {
		return rhs.getObjectList().stream().map(it -> it.name() + ": " + it.cls().name())
				.collect(Collectors.joining(", "));
	}

	public String genMatchBoth() {
		String l = genMatchLeft();
		String r = genMatchRight();
		if (!l.isEmpty() && !r.isEmpty())
			return l + ", " + r;
		else
			return l + r;
	}

	public String genLetLeft(String prefix) {
		StringBuilder sb = new StringBuilder();
		for (MObject obj : lhs.getObjectList()) {
			sb.append("let " + obj.name() + ":" + obj.cls().name() + " = " + prefix + "." + obj.name() + " in\n");
		}
		return sb.toString();
	}

	public String genLetRight(String prefix) {
		StringBuilder sb = new StringBuilder();
		for (MObject obj : rhs.getObjectList()) {
			sb.append("let " + obj.name() + ":" + obj.cls().name() + " = " + prefix + "." + obj.name() + " in\n");
		}
		return sb.toString();
	}

	public String genLetBoth(String prefix) {
		return genLetLeft(prefix) + genLetRight(prefix);
	}

	public String genPreCondLeft() {
		return lhs.genPreCondition();
	}

	public String genPreCondBoth() {
		String l = lhs.genPreCondition();
		String r = rhs.genPreCondition();
		if (!l.isEmpty() && !r.isEmpty())
			return l + " and\n" + r;
		else
			return l + r;
	}

	public String genPostCondition() {
		return "";
	}

}
