package org.uet.dse.rtlplus.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.tzi.kodkod.KodkodModelValidatorConfiguration;
import org.tzi.kodkod.KodkodSolver;
import org.tzi.kodkod.helper.LogMessages;
import org.tzi.kodkod.model.config.impl.ModelConfigurator;
import org.tzi.kodkod.model.config.impl.PropertyConfigurationVisitor;
import org.tzi.kodkod.model.iface.IClass;
import org.tzi.kodkod.model.iface.IModel;
import org.tzi.kodkod.model.type.TypeAtoms;
import org.tzi.kodkod.model.type.TypeConstants;
import org.tzi.use.kodkod.UseCTScrollingKodkodModelValidator;
import org.tzi.use.kodkod.plugin.CTInputReader;
import org.tzi.use.kodkod.plugin.PluginModelFactory;
import org.tzi.use.kodkod.plugin.USECommentFilterReader;
import org.tzi.use.kodkod.transform.TransformationException;
import org.tzi.use.kodkod.transform.enrich.ModelEnricher;
import org.tzi.use.kodkod.transform.ocl.DefaultExpressionVisitor;
import org.tzi.use.main.Session;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.Log;
import org.tzi.use.util.Pair;
import org.uet.dse.rtlplus.Main;
import org.uet.dse.rtlplus.RTLLoader;
import org.uet.dse.rtlplus.mm.MRuleCollection.TransformationType;

import kodkod.ast.Formula;
import kodkod.ast.IntConstant;
import kodkod.ast.IntExpression;
import kodkod.ast.Node;
import kodkod.ast.Relation;
import kodkod.ast.Variable;
import kodkod.engine.Solution;
import kodkod.instance.TupleSet;

public class CtTester extends SwingWorker<TestResult, Void> {

	private Session session;
	private File srcModel;
	private File trgModel;
	private String tggName;
	private File propFile;
	private String srcTerms;
	private String trgTerms;
	private ProgressMonitor monitor;
	private int bitwidth;
	private String error;

	public CtTester(Session session, File srcModel, File trgModel, String tggName, File propFile, String srcTerms,
			String trgTerms, ProgressMonitor monitor, int bitwidth) {
		super();
		this.session = session;
		this.srcModel = srcModel;
		this.trgModel = trgModel;
		this.tggName = tggName;
		this.propFile = propFile;
		this.srcTerms = srcTerms;
		this.trgTerms = trgTerms;
		this.monitor = monitor;
		this.bitwidth = bitwidth;
	}

	@Override
	protected TestResult doInBackground() throws Exception {
		try {
		// TODO Auto-generated method stub
		// TODO Use RTL loader to load the models & tgg rules in a NEW session
		// TODO Use a new session for Kodkod (pay attention to src/trg model for fwd/bwd
		// trans)
		// TODO Copy code from KodkodCTScrolling... to generate models
		// TODO Read CTs and add them to somewhere
		monitor.setNote("Loading metamodels and rules");
		System.out.println("Loading metamodels and rules");
//		monitor.setProgress(20);
		RTLLoader loader = new RTLLoader(new Session(), srcModel, trgModel, tggName, new PrintWriter(System.out));
		boolean res = loader.run();
		if (!res) {
			error = "Error when loading metamodels or rules. Please verify your syntax and try again.";
			return null;
		}
		TransformationType type = Main.getTggRuleCollection().getType();
		if (type != TransformationType.FORWARD && type != TransformationType.BACKWARD) {
			error = "The transformation type should be forward or backward, but is actually " + type.name();
			return null;
		}
		
		Session newSession = new Session();
		UseCTScrollingKodkodModelValidator validator = null;
		boolean readResult = true;
		IModel iModel = null;
		MSystem newSystem = null;
		List<ClassifyingTerm> terms = new ArrayList<>();
		
		monitor.setNote("Reading source classifying terms");
		System.out.println("Reading source classifying terms");
//		monitor.setProgress(30);
		BufferedReader reader = new BufferedReader(new USECommentFilterReader(new FileReader(srcTerms)));
		System.out.println("Hello");
		List<Pair<String>> srcTermList = new ArrayList<>();
		if (type == TransformationType.FORWARD) {
			newSystem = new MSystem(Main.getTggRuleCollection().getSourceModel());
			newSession.setSystem(newSystem);
			validator = new UseCTScrollingKodkodModelValidator(newSession);
			PluginModelFactory.INSTANCE.registerForSession(newSession);
			iModel = PluginModelFactory.INSTANCE.getModel(Main.getTggRuleCollection().getSourceModel());
			readResult = readTerms(reader, srcTermList, terms, Main.getTggRuleCollection().getSourceModel(), iModel, validator);
		} else
			readResult = readTerms(reader, srcTermList, Main.getTggRuleCollection().getSourceModel());
		if (srcTermList.isEmpty()) {
			error = "There are no source classifying terms.";
			return null;
		}
		if (!readResult)
			return null;

		monitor.setNote("Reading target classifying terms");
		System.out.println("Reading target classifying terms");
//		monitor.setProgress(40);
		BufferedReader reader2 = new BufferedReader(new USECommentFilterReader(new FileReader(trgTerms)));
		List<Pair<String>> trgTermList = new ArrayList<>();
		if (type == TransformationType.BACKWARD) {
			newSystem = new MSystem(Main.getTggRuleCollection().getTargetModel());
			newSession.setSystem(newSystem);
			validator = new UseCTScrollingKodkodModelValidator(newSession);
			PluginModelFactory.INSTANCE.registerForSession(newSession);
			iModel = PluginModelFactory.INSTANCE.getModel(Main.getTggRuleCollection().getTargetModel());
			readResult = readTerms(reader2, trgTermList, terms, Main.getTggRuleCollection().getTargetModel(), iModel, validator);
		} else
			readResult = readTerms(reader2, trgTermList, Main.getTggRuleCollection().getTargetModel());
		if (trgTermList.isEmpty()) {
			error = "There are no target classifying terms.";
			return null;
		}
		if (!readResult)
			return null;

		monitor.setNote("Reading properties file");
		System.out.println("Reading properties file");
//		monitor.setProgress(50);
		HierarchicalINIConfiguration hierarchicalINIConfiguration = new HierarchicalINIConfiguration();
		try {
			hierarchicalINIConfiguration.load(new USECommentFilterReader(new FileReader(propFile)));
		}
		catch (IOException ex) {
			error = ex.getMessage();
			return null;
		}
		Configuration config = getConfig(hierarchicalINIConfiguration);
		iModel.reset();
		PropertyConfigurationVisitor newConfigurationVisitor = new PropertyConfigurationVisitor(config, new PrintWriter(System.out));
		iModel.accept(newConfigurationVisitor);
		if (newConfigurationVisitor.containErrors()) {
			error = LogMessages.configurationError;
			return null;
		}
		ModelEnricher enricher = KodkodModelValidatorConfiguration.INSTANCE.getModelEnricher();
		enricher.enrichModel(newSystem, iModel);
		
		monitor.setNote("Finding models");
		System.out.println("Finding models");
//		monitor.setProgress(60);
		boolean running = true;
		KodkodModelValidatorConfiguration configuration = KodkodModelValidatorConfiguration.INSTANCE;
		configuration.setBitwidth(bitwidth);
		List<Map<Relation, TupleSet>> solutions = new ArrayList<Map<Relation, TupleSet>>();
		List<Map<ClassifyingTerm, Value>> termSolutions = new ArrayList<>();
		do {
			Formula f = genClassifyingTermFormula(termSolutions, terms, iModel);
			((ModelConfigurator) iModel.getConfigurator()).setSolutionFormula(f);
			KodkodSolver solver = new KodkodSolver();
			try {
				Solution solution = solver.solve(iModel);
				System.out.println(solution.outcome().name());
			} catch (Exception e) {
				e.printStackTrace();
				error = "Error while solving model: " + e.getMessage();
				return null;
			}
			
		} while (running);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private Formula genClassifyingTermFormula(List<Map<ClassifyingTerm, Value>> termSolutions, List<org.uet.dse.rtlplus.testing.ClassifyingTerm> classifyingTerms, IModel iModel) {
		Formula f = null;
		
		for (Map<ClassifyingTerm, Value> solutions : termSolutions) {
			Formula currSolution = null;
			
			for (ClassifyingTerm ct : classifyingTerms) {
				Value value = solutions.get(ct);
				Formula solFormula = encodeSolutionValue(ct.getKodkodExpr(), value, iModel);
				currSolution = (currSolution == null) ? solFormula : currSolution.and( solFormula ) ;
			}
			
			f = (f == null) ? currSolution.not() : f.and(currSolution.not()) ;
		}
		return f;
	}
	
	private Formula encodeSolutionValue(kodkod.ast.Node exp, Value value, IModel iModel) {
		if(exp instanceof kodkod.ast.Expression){
			if(value instanceof IntegerValue){
				return ((kodkod.ast.Expression) exp).eq(IntConstant.constant(((IntegerValue) value).value()).toExpression());
			}
			else if(value instanceof BooleanValue) {
				Map<String, kodkod.ast.Expression> typeLiterals = iModel.typeFactory().booleanType().typeLiterals();
				if(((BooleanValue) value).value()){
					return ((kodkod.ast.Expression) exp).eq(typeLiterals.get(TypeConstants.BOOLEAN_TRUE));
				} else {
					return ((kodkod.ast.Expression) exp).eq(typeLiterals.get(TypeConstants.BOOLEAN_FALSE));
				}
			}
			else if(value instanceof UndefinedValue){
				TypeAtoms typeLiteral;
				if(value.type().isKindOfCollection(VoidHandling.INCLUDE_VOID)){
					typeLiteral = iModel.typeFactory().undefinedSetType();
				} else {
					typeLiteral = iModel.typeFactory().undefinedType();
				}
				return ((kodkod.ast.Expression) exp).eq(typeLiteral.expression());
			}
			else {
				throw new TransformationException("Unsupported expression type found. (" + exp.getClass().toString() + " --- " + value.getClass().toString() + ")");
			}
		}
		else if(exp instanceof Formula){
			return ((BooleanValue) value).value() ? (Formula) exp : ((Formula) exp).not() ;
		}
		else if(exp instanceof IntExpression){
			return ((IntExpression) exp).eq(IntConstant.constant(((IntegerValue) value).value()));
		}
		throw new TransformationException("Unsupported expression type found. (" + exp.getClass().toString() + " --- " + value.getClass().toString() + ")");
	}

	private boolean readTerms(BufferedReader reader, List<Pair<String>> terms, MModel model) {
		System.out.println("Reading terms without validator");
		do {
			try {
				String name = reader.readLine();
				if (name == null)
					break;
				name = name.trim();
				if (name.isEmpty())
					name = "Term " + Integer.toString(terms.size() + 1);
				System.out.println("Term name: " + name);
				String line = reader.readLine();
				if (line == null)
					break;
				line = line.trim();
				if (line.isEmpty())
					break;
				System.out.println("Term: " + line);
				// Error checking
				StringWriter err = new StringWriter();
				Expression result = OCLCompiler.compileExpression(model, line, "<classifying term>",
						new PrintWriter(err), new VarBindings());
				if (result == null) {
					error = err.toString();
					return false;
				}
				if (!result.type().isTypeOfBoolean()) {
					error = "The expression:\n" + line + "\n must result in type 'Boolean'";
					return false;
				}
				Pair<String> term = new Pair<>();
				term.first = name;
				term.second = line;
				terms.add(term);
			} catch (IOException ignored) {
				error = ignored.getMessage();
				return false;
			}
		} while (true);
		return true;
	}

	private boolean readTerms(BufferedReader reader, List<Pair<String>> terms, List<ClassifyingTerm> cts, MModel model,
			IModel iModel, UseCTScrollingKodkodModelValidator validator) {
		System.out.println("Reading terms with validator");
		do {
			try {
				String name = reader.readLine();
				if (name == null)
					break;
				name = name.trim();
				if (name.isEmpty())
					name = "Term " + Integer.toString(terms.size() + 1);
				System.out.println("Term name: " + name);
				String line = reader.readLine();
				if (line == null)
					break;
				line = line.trim();
				if (line.isEmpty())
					break;
				System.out.println("Term: " + line);
				// Error checking
				StringWriter err = new StringWriter();
				Expression result = OCLCompiler.compileExpression(model, line, "<classifying term>",
						new PrintWriter(err), new VarBindings());
				if (result == null) {
					error = err.toString();
					return false;
				}
				if (!result.type().isTypeOfBoolean()) {
					error = "The expression:\n" + line + "\n must result in type 'Boolean'";
					return false;
				}
				Pair<String> term = new Pair<>();
				term.first = name;
				term.second = line;
				terms.add(term);
				Node obsTermKodkod;
				try {
					DefaultExpressionVisitor ev = new DefaultExpressionVisitor(
							iModel, new HashMap<String, Node>(),
							new HashMap<String, IClass>(), new HashMap<String, Variable>(), new ArrayList<String>());
					result.processWithVisitor(ev);
					obsTermKodkod = (Node) ev.getObject();
					ClassifyingTerm ct = new ClassifyingTerm(name, result, obsTermKodkod);
					cts.add(ct);
				} catch (TransformationException ex) {
					error = "The expression cannot be transformed by the model validator. Reason: " + ex.getMessage();
					return false;
				}
				

			} catch (IOException ignored) {
				error = ignored.getMessage();
				return false;
			}
		} while (true);
		return true;
	}
	
	private Configuration getConfig(HierarchicalINIConfiguration hierarchicalINIConfiguration) {
		if(hierarchicalINIConfiguration.getSections().isEmpty()){
			return hierarchicalINIConfiguration.getSection(null);
		} else {
			String section = hierarchicalINIConfiguration.getSections().iterator().next();
			return hierarchicalINIConfiguration.getSection(section);
		}
	}

	@Override
	protected void done() {
		monitor.setProgress(100);
		if (error != null) {
			System.out.println(error);
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
