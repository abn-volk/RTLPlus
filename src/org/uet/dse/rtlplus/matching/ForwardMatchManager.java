package org.uet.dse.rtlplus.matching;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.uet.dse.rtlplus.mm.MTggRule;
import org.uet.dse.rtlplus.parser.RTLKeyword;

public class ForwardMatchManager extends MatchManager {
	
	private List<? extends Map<String, MObject>> matched = new ArrayList<LinkedHashMap<String, MObject>>();

	public ForwardMatchManager(MSystemState systemState, PrintWriter logWriter, boolean sync) {
		super(systemState, logWriter, sync);
	}
	
	public ForwardMatchManager(MSystemState systemState, PrintWriter logWriter, boolean sync,
			Map<String, ? extends HashMap<String, MObject>> matchedObjects) {
		super(systemState, logWriter, sync, matchedObjects);
	}

	@Override
	public List<Match> findMatches() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Match> findMatchForObjects(List<MObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Match> findMatchForRule(MTggRule rule) {
		List<Match> matches = new ArrayList<>();
		MOperation op = findOperation(rule.getName(), RTLKeyword.forwardTransform);
		if (op == null)
			return matches;
		// System.out.println("Finding matches for " + rule.getName());
		// TODO: How to interpret empty result?
		// NULL => No objects, not valid for forward transformation
		// Empty => No matches
		List<? extends Map<String, MObject>> srcMatches = findSourceMatch(rule, op);
		if (srcMatches == null || srcMatches.isEmpty())
			return matches;
		// Normal
		for (Map<String, MObject> srcMatch : srcMatches) {
			Map<String, MObject> objs = new LinkedHashMap<>(srcMatch);
			List<? extends Map<String, MObject>> trgMatches = findTargetMatch(rule, op);
			if (trgMatches == null) {
				if (validatePreconditions(op, objs))
					matches.add(new ForwardMatch(rule, op, objs));
			}
			else if (trgMatches.isEmpty())
				return matches;
			else {
				for (Map<String, MObject> trgMatch : trgMatches) {
					objs.putAll(trgMatch);
					List<? extends Map<String, MObject>> corrMatches = findCorrelationMatch(rule, op);
					if (corrMatches == null) {
						if (validatePreconditions(op, objs))
							matches.add(new ForwardMatch(rule, op, objs));
					}
					else if (corrMatches.isEmpty())
						return matches;
					else {
						for (Map<String, MObject> corrMatch : corrMatches) {
							objs.putAll(corrMatch);
							matches.add(new ForwardMatch(rule, op, objs));
						}
					}
				}
			}
		}
		return matches;
	}

	@Override
	public List<Match> findMatchForRules(List<MTggRule> ruleList) {
		List<Match> matches = new ArrayList<>();
		for (MTggRule rule : ruleList) {
			matches.addAll(findMatchForRule(rule));
		}
		return matches;
	}
	
	private List<? extends Map<String, MObject>> findSourceMatch(MTggRule rule, MOperation operation) {
		List<MObject> ruleObjects = rule.getSrcRule().getAllObjects();
		MatchFinder finder = new MatchFinder(systemState, operation, ruleObjects);
		return finder.run();
	}
	
	private List<? extends Map<String, MObject>> findTargetMatch(MTggRule rule, MOperation operation) {
		List<MObject> ruleObjects = rule.getTrgRule().getNonDeletingObjects();
		MatchFinder finder = new MatchFinder(systemState, operation, ruleObjects);
		return finder.run();
	}
	
	private List<? extends Map<String, MObject>> findCorrelationMatch(MTggRule rule, MOperation operation) {
		List<MObject> ruleObjects = rule.getCorrRule().getNonDeletingObjects();
		MatchFinder finder = new MatchFinder(systemState, operation, ruleObjects);
		return finder.run();
	}
	

}
