package org.uet.dse.rtlplus;

import java.io.PrintWriter;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.tzi.use.parser.ParseErrorHandler;
import org.uet.dse.rtlplus.parser.RTLLexer;
import org.uet.dse.rtlplus.parser.RTLParser;
import org.uet.dse.rtlplus.parser.ast.AstRuleCollection;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello world");
		String fileName = "/home/pnh/NCKH/use-4.2.0-492/Make2Ant/Make2Ant.tgg";
		ParseErrorHandler handler = new ParseErrorHandler(fileName, new PrintWriter(System.err));
		ANTLRFileStream stream = new ANTLRFileStream(fileName);
		RTLLexer lexer = new RTLLexer(stream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		RTLParser parser = new RTLParser(tokenStream);
		lexer.init(handler);
		parser.init(handler);
		AstRuleCollection rc = parser.tggRuleCollection();
	}
}
