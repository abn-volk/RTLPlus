package org.uet.dse.rtlplus.matching;

import java.io.PrintWriter;
import java.util.Map;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.uet.dse.rtlplus.mm.MTggRule;

public abstract class Match {

	protected MOperation operation;
	protected MTggRule rule;
	protected Map<String, MObject> objectList;
	protected boolean sync;

	public Match(MTggRule rule, MOperation operation, Map<String, MObject> objectList, boolean sync) {
		super();
		this.operation = operation;
		this.rule = rule;
		this.objectList = objectList;
		this.sync = sync;
	}
	
	public abstract boolean run(MSystemState systemState, PrintWriter logWriter);
	
	public String toString() {
		return operation.name() + ": " + objectList.toString();
	}
	
	public Map<String, MObject> getObjectList() {
		return objectList;
	}
	
}
