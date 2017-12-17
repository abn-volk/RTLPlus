package org.uet.dse.rtlplus.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MSystemException;
import org.uet.dse.rtlplus.mm.MRuleCollection;
import org.uet.dse.rtlplus.parser.ast.AstRuleCollection;

public class RTLCompiler {
	private RTLCompiler() {
	};

	public static MRuleCollection compileSpecification(String in, String inName, PrintWriter err, MModel model)
			throws MSystemException {
		InputStream inStream = new ByteArrayInputStream(in.getBytes());
		ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
		ANTLRInputStream input;
		try {
			input = new ANTLRInputStream(inStream);
			input.name = inName;
		}
		catch (IOException exception) {
			err.println(exception.getMessage());
			return null;
		}
		
		RTLLexer lexer = new RTLLexer(input);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		RTLParser parser = new RTLParser(tokenStream);
		lexer.init(errHandler);
		parser.init(errHandler);
		try {
			AstRuleCollection ruleCollection = parser.tggRuleCollection();
			if (errHandler.errorCount() == 0) {
				Context ctx = new Context(inName, err, null, null);
				ctx.setModel(model);
				//TODO: invoke .gen here
			}
		}
		catch (RecognitionException e) {
			err.println(String.format("%s:%d:%d:%s", parser.getSourceName(), 
					e.line, e.charPositionInLine, e.getMessage()));
		}
		err.flush();
		// TODO: Return rule collection here
		return null;
	}
}
