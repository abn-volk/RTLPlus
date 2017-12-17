package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class AstLink {
	
	public List<String> objectList = new ArrayList<>(2);
	public String association;

	public void setAssociation(Object object) {
		association = (String) object;
	}

	public void addObject(Object object) {
		objectList.add((String) object);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		sb.append(String.join(",", objectList))
			.append("):").append(association);
		return sb.toString();
	}

}
