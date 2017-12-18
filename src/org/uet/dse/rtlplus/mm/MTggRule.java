package org.uet.dse.rtlplus.mm;

public class MTggRule {
	
	private String name;
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
	
	public String getHtml() {
		return html;
	}

}
