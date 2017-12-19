package org.uet.dse.rtlplus.gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.CloseOnEscapeKeyListener;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.util.GridBagHelper;
import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.uet.dse.rtlplus.mm.MRuleCollection;
import org.uet.dse.rtlplus.parser.RTLCompiler;
import org.uet.dse.rtlplus.parser.RTLKeyword;

/**
 * RTL1.1
 * 
 * @author Khoa-Hai Nguyen
 * 
 */
@SuppressWarnings("serial")
public class RTLParserParameter extends JDialog {
	private Session fSession;
	private MainWindow fParent;
	private JTextField fTextModel2;
	private JTextField fTextTgg;
	private PrintWriter fLogWriter;
	private MModel fModel1;
	private MModel fModel2;
	private MModel fModel;
	private MRuleCollection fTggRules;
	private String modelName;
	private StringBuilder useContent  = new StringBuilder();

	public RTLParserParameter(Session session, MainWindow parent) {
		super(parent, "RTL Parser Parameter");
		fSession = session;
		fSession.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				closeDialog();
			}
		});

		fParent = parent;
		fLogWriter = parent.logWriter();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Label for source metamodel, which is already loaded
		JLabel labelModel1 = new JLabel("Source metamodel: " + Options.getRecentFile("use").getFileName());

		// Label and text field for target metamodel
		fTextModel2 = new JTextField(35);
		JLabel labelModel2 = new JLabel("Target metamodel:");
		labelModel2.setLabelFor(fTextModel2);

		// Label and text field for TGG rules
		fTextTgg = new JTextField(35);
		JLabel labelTgg = new JLabel("TGG rules:");
		labelTgg.setLabelFor(fTextTgg);

		JButton btnPath2 = new JButton("Browse...");
		btnPath2.addActionListener(new ActionListener() {
			private JFileChooser fChooser;

			public void actionPerformed(ActionEvent e) {
				String path;
				if (fChooser == null) {
					path = Options.getLastDirectory().toString();
					fChooser = new JFileChooser(path);
					ExtFileFilter filter = new ExtFileFilter("use", "USE specifications");
					fChooser.setFileFilter(filter);
					fChooser.setDialogTitle("Open specification");
				}
				int returnVal = fChooser.showOpenDialog(RTLParserParameter.this);
				if (returnVal != JFileChooser.APPROVE_OPTION)
					return;

				path = fChooser.getCurrentDirectory().toString();
				Options.setLastDirectory(new File(path).toPath());

				fTextModel2.setText(Paths.get(path, fChooser.getSelectedFile().getName()).toString());

			}
		});
		JButton btnPath3 = new JButton("Browse...");
		// btnPath3.setMnemonic('P');
		btnPath3.addActionListener(new ActionListener() {
			private JFileChooser fChooser;

			public void actionPerformed(ActionEvent e) {
				String path;
				if (fChooser == null) {
					path = Options.getLastDirectory().toString();
					fChooser = new JFileChooser(path);
					ExtFileFilter filter = new ExtFileFilter("tgg", "TGG rules");
					fChooser.setFileFilter(filter);
					fChooser.setDialogTitle("Open TGG file");
				}
				int returnVal = fChooser.showOpenDialog(RTLParserParameter.this);
				if (returnVal != JFileChooser.APPROVE_OPTION)
					return;

				path = fChooser.getCurrentDirectory().toString();
				Options.setLastDirectory(new File(path).toPath());

				fTextTgg.setText(Paths.get(path, fChooser.getSelectedFile().getName()).toString());

			}
		});

		JButton btnParse = new JButton("Parse");
		btnParse.setMnemonic('P');
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parseRTL();
			}
		});
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});

		// layout content pane
		JComponent contentPane = (JComponent) getContentPane();
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		GridBagHelper gh = new GridBagHelper(contentPane);
		gh.add(labelModel1, 0, 0, 6, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(labelModel2, 0, 1, 6, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(fTextModel2, 0, 2, 6, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(btnPath2, 7, 2, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(labelTgg, 0, 3, 6, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(fTextTgg, 0, 4, 6, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(btnPath3, 7, 4, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(btnParse, 0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
		gh.add(btnClose, 1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

		getRootPane().setDefaultButton(btnParse);
		pack();
		setLocationRelativeTo(parent);
		// fListClasses.requestFocus();

		// Close dialog on escape key
		CloseOnEscapeKeyListener ekl = new CloseOnEscapeKeyListener(this);
		addKeyListener(ekl);
	}

	private void closeDialog() {
		setVisible(false);
		dispose();
	}

	private void parseRTL() {
		if (checkPath()) {
			// Parse metamodel
			parseModels();
			// Parse TGG rule
			parseTGGRule();
			// gen USE file
			genUSEContent();
			writeUSEFile();

			/* Load model and RTL rules */
			if (fModel != null) {
				/* Load USE model */
				MModel newModel = null;
				MSystem system = null;
				try {
					newModel = USECompiler.compileSpecification(useContent.toString(), modelName, fLogWriter, new ModelFactory());
					fLogWriter.println("Load model " + modelName + " ...");
					if (newModel != null) {
						fLogWriter.println(newModel.getStats());
						// create system
						system = new MSystem(newModel);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				// set new system (may be null if compilation failed)
				final MSystem system2 = system; // need final variable for Runnable
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						fSession.setSystem(system2);
					}
				});
				/*
				 * Load TGG rules Rules.setMainWindow(fParent); Rules.setRTLRule(fTggRules);
				 * Rules.setRTLRuleFileName(fTextTgg.getText());
				 */
				fLogWriter.println("Compile successfully");
			} else {
				fLogWriter.println("Can not compile source files");
			}

		} else {
			fLogWriter.println("Error in path file");
		}
	}

	private void genUSEContent() {
		fLogWriter.println("Compile USE model specification...");
		useContent.append(fTggRules.getContext().generateCorrelations());
		useContent.append(fTggRules.genCorrInvs());
		useContent.append(genRuleCollection());
		useContent.append("\n---------- Transformation constraints ----------\n");
		// Reload models to get RuleCollection's operations
		// fModel = USECompiler.compileSpecification(useContent.toString(), modelName, fLogWriter, new ModelFactory());
		//List<MOperation> ops = fModel.getClass("RuleCollection").allOperations();
//		MOperation operation = null;
	}
	
	private String genRuleCollection() {
		StringBuilder ops = new StringBuilder("---------- RuleCollection ----------\nclass RuleCollection\n");
		StringBuilder cons = new StringBuilder("---------- Transformation constraints ----------\nconstraints");
		ops.append(RTLKeyword.startOperation);
		ops.append("\n---------- Forward transformations ----------\n");
		fTggRules.genForwardTransformation(ops, cons);
		ops.append("\n---------- Backward transformations ----------\n");
		fTggRules.genBackwardTransformation(ops, cons);
		ops.append("\n---------- Integration transformations ----------\n");
		fTggRules.genIntegration(ops, cons);
		ops.append(RTLKeyword.endClass).append('\n');
		ops.append(cons);
		return ops.toString();
	}

	/**
	 * Generate class RuleCollection
	 * 
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	private String genRuleCollection() {
//		String use = "";
//		use += "class RuleCollection\n";
//		// operations
//		use += "------------------------------------------------------------Operations\n";
//		use += RTLKeyword.startOperation + "\n";
//		use += "------------------------------Co-Evolution operations\n";
//		// Co-Evolution transformation
//		for (Iterator iter = fTggRules.getTggRules().iterator(); iter.hasNext();) {
//			MTggRule tgg = (MTggRule) iter.next();
//			use += tgg.buildParamsForCoEvol(2) + "\n";
//		}
//		// Forward transformation
//		use += "------------------------------Forward transformation operation\n";
//		for (Iterator iter = fTggRules.getTggRules().iterator(); iter.hasNext();) {
//			MTggRule tgg = (MTggRule) iter.next();
//			use += tgg.buildParamsForFwdTrafo(2) + "\n";
//		}
//		// Integration
//		use += "------------------------------Integration operation\n";
//		for (Iterator iter = fTggRules.getTggRules().iterator(); iter.hasNext();) {
//			MTggRule tgg = (MTggRule) iter.next();
//			use += tgg.buildParamsForIntegration(2) + "\n";
//		}
//		// Integration
//		use += RTLKeyword.endClass + "\n";
//		// before generate pre, postcondition, reload model to have class
//		// RuleCollection and operations
//		fModel = USECompiler.compileSpecification(useContent + use, modelName, fLogWriter, new ModelFactory());
//		List<MOperation> ops = fModel.getClass("RuleCollection").allOperations();
//		MOperation operation = null;
//		// Pre, Post-condition
//		use += "------------------------------------------------------------Pre, post-conditions\n";
//		use += "\nconstraints";
//		// co-evolution
//		use += "\n------------------------------Co-Evolution\n";
//		for (Iterator iter = fTggRules.getTggRules().iterator(); iter.hasNext();) {
//			MTggRule tgg = (MTggRule) iter.next();
//			for (Iterator iter1 = ops.iterator(); iter1.hasNext();) {
//				operation = (MOperation) iter1.next();
//				if (operation.name().equals(tgg.name() + RTLKeyword.coEvolution))
//					break;
//			}
//
//			use += "\ncontext RuleCollection::" + tgg.buildParamsForCoEvol(0);
//			use += tgg.buildPreConditionForCoEvol(operation, 2) + "\n";
//			use += tgg.buildPostConditionForCoEvol(operation, 2) + "\n";
//		}
//		// forward transformation
//		use += "\n" + RTLKeyword.comment(50, '-') + "Forward transformation\n";
//		for (Iterator iter = fTggRules.getTggRules().iterator(); iter.hasNext();) {
//			MTggRule tgg = (MTggRule) iter.next();
//			for (Iterator iter1 = ops.iterator(); iter1.hasNext();) {
//				operation = (MOperation) iter1.next();
//				if (operation.name().equals(tgg.name() + RTLKeyword.forwardTransform))
//					break;
//			}
//			use += "\ncontext RuleCollection::" + tgg.buildParamsForFwdTrafo(0);
//			use += tgg.buildPreConditionForFwdTrafo(operation, 2) + "\n";
//			use += tgg.buildPostConditionForFwdTrafo(operation, 2) + "\n";
//		}
//		// Integration
//		use += "\n" + RTLKeyword.comment(50, '-') + "Integration\n";
//		for (Iterator iter = fTggRules.getTggRules().iterator(); iter.hasNext();) {
//			MTggRule tgg = (MTggRule) iter.next();
//			for (Iterator iter1 = ops.iterator(); iter1.hasNext();) {
//				operation = (MOperation) iter1.next();
//				if (operation.name().equals(tgg.name() + RTLKeyword.integration))
//					break;
//			}
//			use += "\ncontext RuleCollection::" + tgg.buildParamsForIntegration(0);
//			use += tgg.buildPreConditionForIntegration(operation, 2) + "\n";
//			use += tgg.buildPostConditionForIntegration(operation, 2) + "\n";
//		}
//		return use;
//	}

	/**
	 * Join source and target model
	 * 
	 * @return A string contains two model
	 */
	private String unionModel() {
		String result = "";
		FileInputStream stream;
		try {
			String mm1 = "", mm2 = "";
			File f1 = Options.getRecentFile("use").toFile();
			stream = new FileInputStream(f1);
			fModel1 = USECompiler.compileSpecification(stream, f1.getName(), fLogWriter, new ModelFactory());
			byte[] bytes = Files.readAllBytes(f1.toPath());
			mm1 = new String(bytes, "UTF-8");
			mm1 = mm1.substring(mm1.indexOf(RTLKeyword.startClass));

			File f2 = new File(fTextModel2.getText());
			stream = new FileInputStream(f2);
			fModel2 = USECompiler.compileSpecification(stream, f2.getName(), fLogWriter, new ModelFactory());
			bytes = Files.readAllBytes(f2.toPath());
			mm2 = new String(bytes, "UTF-8");
			mm2 = mm2.substring(mm2.indexOf(RTLKeyword.startClass));
			modelName = fModel1.name() + "2" + fModel2.name();
			// All model
			result = RTLKeyword.model + " " + modelName + "\n" + mm1 + "\n" + mm2;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Parse source and target model
	 */
	private void parseModels() {
		fLogWriter.println("Loading source and target metamodel...");
		String model = unionModel();
		useContent.append(model);
		fModel = USECompiler.compileSpecification(model, modelName, fLogWriter, new ModelFactory());
	}

	/**
	 * Parse TGG rule from .tgg file
	 */
	private void parseTGGRule() {
		fLogWriter.println("Compile TGG rules...");
		try {
			fTggRules = RTLCompiler.compileSpecification(fTextTgg.getText(), fLogWriter, fModel);
		} catch (MSystemException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write USE specification for models and transformations
	 */
	private void writeUSEFile() {
		File f = new File(fTextTgg.getText());
		String fTGGsRule = modelName + ".use";
		String path = f.getPath();
		String fullPath = path.substring(0, path.length() - f.getName().length());
		fLogWriter.println("Writing USE model specification to file: " + Paths.get(fullPath, fTGGsRule).toString());
		try {
			PrintWriter out = new PrintWriter(new FileWriter(Paths.get(fullPath, fTGGsRule).toString()));
			out.print(useContent);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if paths are valid
	 */
	private boolean checkPath() {
		File f = new File(fTextModel2.getText());
		if (f.exists()) {
			f = new File(fTextTgg.getText());
			if (f.exists())
				return true;
		}
		return false;
	}
}
