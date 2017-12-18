package org.uet.dse.rtlplus.mm;

import java.util.List;
import java.util.Map;

import org.tzi.use.uml.sys.MLink;
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

	public MPattern(MSystemState systemState, List<MObject> objectList, List<MLink> linkList, List<String> conditionList) {
		super();
		this.systemState = systemState;
		this.objectList = objectList;
		this.linkList = linkList;
		this.conditionList = conditionList;
	}
	
	public MPattern(MSystemState systemState, List<MObject> objectList, List<MLink> linkList, Map<String, List<String>> invariantList) {
		this.systemState = systemState;
		this.objectList = objectList;
		this.linkList = linkList;
		this.invariantList = invariantList;
	}
	
	

}
