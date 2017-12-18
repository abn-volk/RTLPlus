package org.uet.dse.rtlplus.parser.ast;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemException;
import org.uet.dse.rtlplus.mm.MPattern;
import org.uet.dse.rtlplus.mm.MRule;
import org.uet.dse.rtlplus.parser.Context;

public class AstRule {

	private AstPattern lhs;
	private AstPattern rhs;
	
	public AstRule(AstPattern _lhs, AstPattern _rhs) {
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

	public MRule gen(Context ctx) {
		ctx.setSystemState(ctx.getLhsState());
		try {
			MPattern left = lhs.gen(ctx);
			// Because the rules are non-deleting, the RHS system state is the same as the LHS system state
			// Clone the LHS system state before adding new objects and links
			ctx.setSystemState(ctx.getRhsState());
			for (MObject obj : ctx.getLhsState().allObjects()) {
				MObjectState objState = new MObjectState(obj);
				ctx.getRhsState().restoreObject(objState);
			}
			for (MLink lnk : ctx.getLhsState().allLinks()) {
				MAssociation assoc = lnk.association();
				List<MObject> objects = lnk.linkedObjects();
				ctx.getRhsState().createLink(assoc, new ArrayList<MObject>(objects), null);
			}
			MPattern right = rhs.gen(ctx);
			return new MRule(left, right);
		} catch (MSystemException e) {
			e.printStackTrace();
		}
		return null;
	}

}
