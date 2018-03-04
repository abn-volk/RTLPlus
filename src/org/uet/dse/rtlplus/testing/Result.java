package org.uet.dse.rtlplus.testing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemState;

public class Result {

	private MSystemState state;
	private List<Value> termSolution;
	private List<Value> otherTermSolution;

	public Result(MSystemState mSystemState, LinkedHashMap<ClassifyingTerm, Value> map, List<Value> list) {
		state = mSystemState;
		termSolution = new ArrayList<>(map.values());
		otherTermSolution = list;
	}

	public MSystemState getState() {
		return state;
	}

	public List<Value> getTermSolution() {
		return termSolution;
	}

	public List<Value> getOtherTermSolution() {
		return otherTermSolution;
	}

	public String getTermSolutionString() {
		return termSolution.stream().map(it -> it.toString()).collect(Collectors.joining(", ", "[", "]"));
	}

	public String getOtherTermSolutionString() {
		return otherTermSolution.stream().map(it -> it.toString()).collect(Collectors.joining(", ", "[", "]"));
	}

}