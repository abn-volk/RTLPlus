package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemException;
import org.uet.dse.rtlplus.mm.MCorrRule;
import org.uet.dse.rtlplus.mm.MPattern;
import org.uet.dse.rtlplus.mm.MRule;
import org.uet.dse.rtlplus.parser.Context;

public class AstCorrRule {
	
	private AstCorr lhs;
	private AstCorr rhs;

	public AstCorrRule(AstCorr _lhs, AstCorr _rhs) {
		lhs = _lhs;
		rhs = _rhs;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(\n").append(lhs.toString())
			.append("){\n").append(rhs.toString())
			.append("\n}");
		return sb.toString();
	}

	public MCorrRule gen(Context ctx) throws MInvalidModelException, MSystemException {
		ctx.setSystemState(ctx.getLhsState());
		MPattern left = lhs.gen(ctx);
		ctx.setSystemState(ctx.getRhsState());
		try {
			for (MObject obj : ctx.getLhsState().allObjects()) {
				MObjectState objState = new MObjectState(obj);
				ctx.getRhsState().restoreObject(objState);
			}
			for (MLink lnk : ctx.getLhsState().allLinks()) {
				MAssociation assoc = lnk.association();
				List<MObject> objects = lnk.linkedObjects();
				ctx.getRhsState().createLink(assoc, new ArrayList<MObject>(objects), null);
			}
		} catch (MSystemException e) {
			e.printStackTrace();
		}
		MPattern right = rhs.gen(ctx);
		return new MCorrRule(left, right);
	}
}
