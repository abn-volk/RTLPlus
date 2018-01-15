package org.uet.dse.rtlplus.sync;

import java.util.List;

import org.tzi.use.uml.mm.MAssociation;

public class CachedLink {
	private String association;
	private String[] linkedObjects;
	public String getAssociation() {
		return association;
	}
	public String[] getLinkedObjects() {
		return linkedObjects;
	}
	public CachedLink(String association, String[] linkedObjects) {
		super();
		this.association = association;
		this.linkedObjects = linkedObjects;
	}
	
	
}
