package org.uet.dse.rtlplus.parser.ast;

public class AstObject {
	
	private String name;
	private String className;
	
	public AstObject(Object object, Object object2) {
		name = (String) object;
		className = (String) object2;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(':').append(className);
		return sb.toString();
	}

}
