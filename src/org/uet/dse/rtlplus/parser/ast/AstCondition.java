package org.uet.dse.rtlplus.parser.ast;

public class AstCondition {

	private String condition;
	
	public String getCondition() {
		return condition;
	}
	
	public AstCondition(Object object) {
		condition = (String) object;
		System.out.println(String.format("Condition: %s", condition));

	}

}
