package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class AstCorr {
	
	private List<AstCorrLink> linkList = new ArrayList<>(1);
	private List<AstInvariantTgg> invList = new ArrayList<>(1);

	public void addCorrLink(AstCorrLink _corrLnk) {
		linkList.add(_corrLnk);
	}

	public void addInv(AstInvariantTgg _inv) {
		invList.add(_inv);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (AstCorrLink lnk : linkList) {
			sb.append(lnk.toString()).append('\n');
		}
		for (AstInvariantTgg inv : invList) {
			sb.append(inv.toString()).append('\n');
		}
		return sb.toString();
	}
}
