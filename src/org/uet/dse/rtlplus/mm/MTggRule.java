package org.uet.dse.rtlplus.mm;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MMPrintVisitor;

public class MTggRule {
	
	private String name;
	private MRule srcRule;
	private MRule trgRule;
	private MCorrRule corrRule;
	private String html;
	

	public MTggRule(String _name, MRule _srcRule, MRule _trgRule, MCorrRule _corrRule, String _str) {
		name = _name;
		srcRule = _srcRule;
		trgRule = _trgRule;
		corrRule = _corrRule;
		html = _str;
	}
	
	public String getName() {
		return name;
	}

	public MRule getSrcRule() {
		return srcRule;
	}

	public MRule getTrgRule() {
		return trgRule;
	}

	public MCorrRule getCorrRule() {
		return corrRule;
	}
	
	public String getHtml() {
		return html;
	}

}
