package org.uet.dse.rtlplus.parser.ast;

public class AstObject {
	
	private String name;
	private String className;
	
	public AstObject(Object object, Object object2) {
		name = (String) object;
		className = (String) object;
		System.out.println(String.format("Object: %s: %s", name, className));

	}

}
