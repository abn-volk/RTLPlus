package org.uet.dse.rtlplus.mm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.uet.dse.rtlplus.sync.CachedLink;

public class MRule {

	protected MPattern lhs;
	protected MPattern rhs;
	protected Map<String, String> objectsToCreate;

	public MRule(MPattern left, MPattern right) {
		lhs = left;
		rhs = right;
	}

	public MPattern getLhs() {
		return lhs;
	}

	public MPattern getRhs() {
		return rhs;
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

	public String genPreCondLeft(boolean hasLineBreaks) {
		return lhs.genPreCondition(hasLineBreaks);
	}

	public String genPreCondBoth(boolean hasLineBreaks) {
		String l = lhs.genPreCondition(hasLineBreaks);
		String r = rhs.genPreCondition(hasLineBreaks);
		if (!l.isEmpty() && !r.isEmpty())
			return l + (hasLineBreaks? " and\n" : " and ") + r;
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

	public List<MLink> getAllLinks() {
		List<MLink> linkList = new ArrayList<>(lhs.getLinkList());
		linkList.addAll(rhs.getLinkList());
		return linkList;
	}

	public List<MLink> getNonDeletingLinks() {
		return lhs.getLinkList();
	}

	public List<? extends MLink> getNewLinks() {
		return rhs.getLinkList();
	}

	public List<String> genLetCommandsLeft(String prefix) {
		List<String> commands = new ArrayList<String>();
		for (MObject obj : lhs.getObjectList()) {
			commands.add("let " + obj.name() + ":" + obj.cls().name() + " = " + prefix + "." + obj.name());
		}
		return commands;
	}

	public List<String> genLetCommandsRight(String prefix) {
		List<String> commands = new ArrayList<String>();
		for (MObject obj : rhs.getObjectList()) {
			commands.add("let " + obj.name() + ":" + obj.cls().name() + " = " + prefix + "." + obj.name());
		}
		return commands;
	}

	public List<String> genCreationCommands(String prefix, MSystemState systemState) {
		List<String> commands = genLetCommandsLeft(prefix);
		objectsToCreate = new LinkedHashMap<>();
		// Create objects
		for (MObject obj : rhs.getObjectList()) {
			String newObjName = systemState.uniqueObjectNameForClass(obj.cls());
			objectsToCreate.put(obj.name(), newObjName);
			commands.add("create " + newObjName + " : " + obj.cls().name());
			commands.add("let " + obj.name() + " = " + newObjName);
		}
		// Create links
		for (MLink lnk : rhs.getLinkList()) {
			String insertStr = "insert ("
					+ lnk.linkedObjects().stream().map(it -> it.name()).collect(Collectors.joining(", ")) + ") into "
					+ lnk.association().name();
			commands.add(insertStr);
		}
		return commands;
	}

	public Map<String, String> getObjectsToCreate() {
		return objectsToCreate;
	}

	public List<CachedLink> getLinksToCreate() {
		List<CachedLink> result = new ArrayList<>();
		for (MLink lnk : rhs.getLinkList()) {
			MObject[] objArray = lnk.linkedObjectsAsArray();
			int len = objArray.length;
			String[] objs = new String[len];
			for (int i=0; i<len; i++) {
				objs[i] = objArray[i].name();
			}
			result.add(new CachedLink(lnk.association().name(), objs));
		}
		return result;
	}

}
