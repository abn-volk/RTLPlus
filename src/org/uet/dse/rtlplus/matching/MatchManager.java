package org.uet.dse.rtlplus.matching;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.TupleValue;
import org.tzi.use.uml.ocl.value.TupleValue.Part;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.soil.VariableEnvironment;
import org.uet.dse.rtlplus.mm.MTggRule;

public abstract class MatchManager {
	protected MSystemState systemState;
	protected PrintWriter logWriter;
	protected boolean sync;
	protected Map<String, ? extends HashMap<String, MObject>> matchedObjects;
	
	
	public MatchManager(MSystemState systemState, PrintWriter logWriter, boolean sync) {
		super();
		this.systemState = systemState;
		this.logWriter = logWriter;
		this.sync = sync;
		this.matchedObjects = new HashMap<String, LinkedHashMap<String, MObject>>();
	}
	
	public MatchManager(MSystemState systemState, PrintWriter logWriter, boolean sync, Map<String, ? extends HashMap<String, MObject>> matchedObjects) {
		super();
		this.systemState = systemState;
		this.logWriter = logWriter;
		this.sync = sync;
		this.matchedObjects = matchedObjects;
	}
	
	
	public MOperation findOperation(String ruleName, String suffix) {
		MClass rc = systemState.system().model().getClass("RuleCollection");
		MOperation op = rc.operation(ruleName + suffix, false);
		if (op == null) {
			logWriter.println("Operation not found: " + ruleName+suffix);
			return null;
		}
		return op;
	}
	

	protected boolean validatePreconditions(MOperation op, Map<String, MObject> objs) {
//		System.out.println("========== VALIDATING PRECONDITIONS ============");
//		System.out.println("Objects: " + objs.toString());
		VariableEnvironment varEnv = systemState.system().getVariableEnvironment();
		for (VarDecl varDecl : op.paramList()) {
			TupleType type = (TupleType) varDecl.type();
			List<Part> parts = new ArrayList<>();
			for (String key : type.getParts().keySet()) {
				parts.add(new TupleValue.Part(0, key, new ObjectValue(objs.get(key).cls(), objs.get(key))));
			}
			TupleValue tuple = new TupleValue(type, parts);
			varEnv.assign(varDecl.name(), tuple);
		}
		//System.out.println(varEnv);
		for (MPrePostCondition pre : op.preConditions()) {
			Expression ex = pre.expression();
			Evaluator eval = new Evaluator();
			try {
				Value valid = eval.eval(ex, systemState, systemState.system().varBindings());
				if (valid.isBoolean() && ((BooleanValue) valid).isFalse()) {
					varEnv.clear();
//					System.out.println("=========== FALSE ==================");
					return false;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				varEnv.clear();
				return false;
			}
		}
		varEnv.clear();
		
//		System.out.println("=========== TRUE: " + objs.toString());
		return true;
	}

	public abstract List<Match> findMatches();
	
	public abstract List<Match> findMatchForObjects(List<MObject> objects);
	
	public abstract List<Match> findMatchForRule(MTggRule rule);
	
	public abstract List<Match> findMatchForRules(List<MTggRule> ruleList);
}
