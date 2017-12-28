package org.uet.dse.rtlplus.matching;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Searches the system for objects and links that match the specification. Is
 * quite slow :(
 */

public class MatchFinder {

	private MSystemState systemState;
	private MOperation operation;
	private List<MObject> ruleObjects;
	private Map<String, MObject> objs;
	private List<Map<String, MObject>> matches;

	public MatchFinder(MSystemState systemState, MOperation operation, List<MObject> ruleObjects) {
		super();
		this.systemState = systemState;
		this.operation = operation;
		this.ruleObjects = ruleObjects;
		System.out.println("Rule objects: " + ruleObjects.toString());
		this.objs = new LinkedHashMap<>();
		this.matches = new ArrayList<Map<String, MObject>>();
	}

	/* Start finding a match */
	public List<? extends Map<String, MObject>> run() {
		if (ruleObjects.isEmpty())
			return null;
		findMatchAtPosition(objs, 0);
		// TODO: this is empty when a match fails because findMatchAtPosition is not
		// called recursively anymore, but not empty when there is actually 0 objects to
		// match because if (...) returns true. Hm.
		return matches;
	}

	private void findMatchAtPosition(Map<String, MObject> objs, int position) {
		// TODO: What to do when there are no objects of a class X?
		// TODO: Improve this by checking for links/conditions
		// TODO: CorrRule?
		// TODO: Filter by object?
		if (position >= ruleObjects.size()) {
			Map<String, MObject> objMap = new LinkedHashMap<String, MObject>(objs);
			matches.add(objMap);
			//System.out.println(matches);
		} else {
			MClass cls = ruleObjects.get(position).cls();
			for (MObject obj : systemState.objectsOfClassAndSubClasses(cls)) {
				String varName = ruleObjects.get(position).name();
				objs.put(varName, obj);
				findMatchAtPosition(objs, position + 1);
				objs.remove(varName);
			}
		}
	}

}
