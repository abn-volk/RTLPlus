package org.uet.dse.rtlplus.parser.ast;

public class AstCorrLink {
	
	private String sourceClass;
	private String targetClass;
	private String sourceObject;
	private String targetObject;
	private String name;
	private String role1;
	private String role2;
	private String className;
	
	

	public String getSourceClass() {
		return sourceClass;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public String getSourceObject() {
		return sourceObject;
	}

	public String getTargetObject() {
		return targetObject;
	}

	public String getName() {
		return name;
	}

	public String getRole1() {
		return role1;
	}

	public String getRole2() {
		return role2;
	}

	public String getClassName() {
		return className;
	}

	public void setSourceClass(Object object) {
		sourceClass = (String) object;
	}

	public void setTargetClass(Object object) {
		targetClass = (String) object;
	}

	public void setSourceObject(Object object) {
		sourceObject = (String) object;
	}

	public void setTargetObject(Object object) {
		targetObject = (String) object;
	}

	public void setName(Object object) {
		name = (String) object;
	}

	public void setRoleNames(Object object, Object object2) {
		role1 = (String) object;
		role2 = (String) object2;
	}

	public void setClass(Object object) {
		className = (String) object;
	}

}
