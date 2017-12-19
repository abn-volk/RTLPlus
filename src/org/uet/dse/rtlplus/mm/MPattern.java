package org.uet.dse.rtlplus.mm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;

public class MPattern {

	private MSystemState systemState;
	private List<MObject> objectList;
	private List<MLink> linkList;
	private List<String> conditionList;
	private Map<String, List<String>> invariantList;

	public MSystemState getSystemState() {
		return systemState;
	}

	public List<MObject> getObjectList() {
		return objectList;
	}

	public List<MLink> getLinkList() {
		return linkList;
	}

	public List<String> getConditionList() {
		return conditionList;
	}

	public Map<String, List<String>> getInvariantList() {
		return invariantList;
	}

	public MPattern(MSystemState systemState, List<MObject> objectList, List<MLink> linkList,
			List<String> conditionList) {
		super();
		this.systemState = systemState;
		this.objectList = objectList;
		this.linkList = linkList;
		this.conditionList = conditionList;
	}

	public MPattern(MSystemState systemState, List<MObject> objectList, List<MLink> linkList,
			Map<String, List<String>> invariantList) {
		this.systemState = systemState;
		this.objectList = objectList;
		this.linkList = linkList;
		this.invariantList = invariantList;
	}

	public String genPreCondition() {
		List<String> conditions = new ArrayList<>();
		// Links exist
		for (MLink lnk : linkList) {
			for (MLinkEnd end : lnk.linkEnds()) {
				for (MLinkEnd otherEnd : lnk.linkEnds()) {
					if (!end.equals(otherEnd)) {
						// theObjA.roleB->includes(theObjB)
						String con = end.object().name() + "." + otherEnd.associationEnd().nameAsRolename()
								+ "->includes(" + otherEnd.object().name() + ")";
						conditions.add(con);
					}
				}
			}
		}
		// Conditions satisfied
		if (conditionList != null)
			conditions.addAll(conditionList);
		return conditions.stream().collect(Collectors.joining(" and \n"));
	}
	
}
