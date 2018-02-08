package org.uet.dse.rtlplus.mm;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.sys.MObject;
import org.uet.dse.rtlplus.mm.MRuleCollection.TransformationType;

public class MCorrRule extends MRule {
	public MCorrRule(MPattern left, MPattern right) {
		super(left, right);
	}
	
	public MPattern getLhs() {
		return lhs;
	}

	public MPattern getRhs() {
		return rhs;
	}
	
	public List<String> genAttributeCommands(TransformationType type){
		List<String> commands = new ArrayList<>();
		for (MObject obj : rhs.getObjectList()) {
			List<String> invs = rhs.getInvariantList().get(obj.cls().name());
			if (invs != null) {
				for (String inv : invs) {
					switch (type) {
					case FORWARD:
					case COEVOLUTION:
						commands.add("set " + inv.replace("self.", obj.name() + ".").replace("=", ":="));
						break;
					case BACKWARD:
						String[] parts = inv.replace("self.", obj.name() + ".").split("=");
						if (parts.length == 2) {
							String newCommand = "set " + parts[1] + ":=" + parts[0];
							commands.add(newCommand);
						}
						break;
					default:
						break;
					}
				}
			}
		}
		return commands;
	}
}
