package org.uet.dse.rtlplus.mm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.tzi.use.uml.sys.MLink;
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

	/**
	 * Generate post-conditions when the right hand side is created during the
	 * transformation
	 * 
	 * @return The number of nested expressions (due to exists(theObjA | ...) )
	 */
	public int genPostCond(StringBuilder builder, int depth) {
		StringBuilder sb = new StringBuilder();
		lhs.genPostCondExisting(sb);
		StringBuilder sb2 = new StringBuilder();
		depth = rhs.genPostCondNew(sb2, depth);
		if (sb.length() > 0 && sb2.length() > 0)
			builder.append(sb).append(" and\n").append(sb2);
		else
			builder.append(sb).append(sb2);
		return depth;
	}

	public List<MObject> getAllObjects() {
		List<MObject> objList = new ArrayList<>(lhs.getObjectList());
		objList.addAll(rhs.getObjectList());
		return objList;
	}
	
	public List<MObject> getNewObjects() {
		List<MObject> objList = new ArrayList<>(rhs.getObjectList());
		objList.removeAll(lhs.getObjectList());
		return objList;
	}

	public List<String> getPreconditions() {
		if (lhs.getConditionList() != null)
			return lhs.getConditionList();
		return new ArrayList<>(0);
	}
	
	public List<String> getPostconditions() {
		if (rhs.getConditionList() != null)
			return rhs.getConditionList();
		return new ArrayList<>(0);
	}

	public List<MObject> getNonDeletingObjects() {
		List<MObject> objList = new ArrayList<>(lhs.getObjectList());
		return objList;
	}

	public Collection<MLink> getNonDeletingLinks() {
		return lhs.getLinkList();
	}

	public Collection<? extends MLink> getNewLinks() {
		return rhs.getLinkList();
	}

}
