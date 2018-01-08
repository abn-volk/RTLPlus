package org.uet.dse.rtlplus.matching;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.tzi.use.parser.shell.ShellCommandCompiler;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.TupleValue;
import org.tzi.use.uml.ocl.value.TupleValue.Part;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.VariableEnvironment;
import org.uet.dse.rtlplus.mm.MTggRule;

public class IntegrationMatch extends Match {

	public IntegrationMatch(MTggRule rule, MOperation operation, Map<String, MObject> objectList) {
		super(rule, operation, objectList);
	}

	@Override
	public boolean run(MSystemState systemState, PrintWriter logWriter) {
		List<String> commands = rule.getSrcRule().genLetCommandsRight("matchSR");
		// Create new target objects
		commands.addAll(rule.getTrgRule().genLetCommandsRight("matchTR"));
		// Create new correlation objects
		commands.addAll(rule.getCorrRule().genCreationCommands("matchCL", systemState));
		// Update attributes
		// Variable assignments
		VariableEnvironment varEnv = systemState.system().getVariableEnvironment();
		for (VarDecl varDecl : operation.paramList()) {
			TupleType type = (TupleType) varDecl.type();
			List<Part> parts = new ArrayList<>();
			for (String key : type.getParts().keySet()) {
				parts.add(new TupleValue.Part(0, key, new ObjectValue(objectList.get(key).cls(), objectList.get(key))));
			}
			TupleValue tuple = new TupleValue(type, parts);
			varEnv.assign(varDecl.name(), tuple);
		}
		// Openter and opexit
		String openter = "openter rc " + operation.name() + "("
				+ operation.paramNames().stream().collect(Collectors.joining(", ")) + ")";
		commands.add(0, openter);
		// Execute commands
		for (String cmd : commands) {
			// System.out.println(cmd);
			MStatement statement = ShellCommandCompiler.compileShellCommand(systemState.system().model(), systemState,
					systemState.system().getVariableEnvironment(), cmd, "<input>", logWriter, false);
			try {
				systemState.system().execute(statement);
			} catch (MSystemException e) {
				logWriter.println(e.getMessage());
				// System.out.println(varEnv);
				varEnv.clear();
				return false;
			}
		}
		// Opexit
		try {
			systemState.system().execute(ShellCommandCompiler.compileShellCommand(systemState.system().model(),
					systemState, systemState.system().getVariableEnvironment(), "opexit", "<input>", logWriter, false));
		} catch (Exception ignored) {
		}
		varEnv.clear();
		return true;
	}

}