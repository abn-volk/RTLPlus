package org.uet.dse.rtlplus.testing;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemState;

public class Result {

	private MSystemState state;
	private LinkedHashMap<ClassifyingTerm, Value> termSolution;
	private List<Value> otherTermSolution;
	
	public Result(MSystemState mSystemState, LinkedHashMap<ClassifyingTerm, Value> map, List<Value> list) {
		state = mSystemState;
		termSolution = map;
		otherTermSolution = list;
	}

	public MSystemState getState() {
		return state;
	}

	public LinkedHashMap<ClassifyingTerm, Value> getTermSolution() {
		return termSolution;
	}

	public List<Value> getOtherTermSolution() {
		return otherTermSolution;
	}
	
	public String getTermSolutionString() {
		return termSolution.values().stream().map(it -> it.toString()).collect(Collectors.joining(", ", "[", "]"));
	}
	
	public String getOtherTermSolutionString() {
		return otherTermSolution.stream().map(it -> it.toString()).collect(Collectors.joining(", ", "[", "]"));
	}
	
}