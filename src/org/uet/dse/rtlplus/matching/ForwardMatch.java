package org.uet.dse.rtlplus.matching;

import java.util.List;
import java.util.Map;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.uet.dse.rtlplus.mm.MTggRule;

public class ForwardMatch extends Match {

	public ForwardMatch(MTggRule rule, MOperation operation, Map<String, MObject> objectList) {
		super(rule, operation, objectList);
	}

	@Override
	public void run(MSystemState systemState) {
		List<String> commands = rule.getSrcRule().genLetCommandsRight("matchSR");
		// Create new target objects
		commands.addAll(rule.getTrgRule().genCreationCommands("matchTL", systemState));
		// Create new correlation objects
		commands.addAll(rule.getCorrRule().genCreationCommands("matchCL", systemState));
		System.out.println(commands);
	}

}
