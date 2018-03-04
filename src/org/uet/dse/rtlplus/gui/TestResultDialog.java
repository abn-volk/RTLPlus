package org.uet.dse.rtlplus.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.Session;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.uet.dse.rtlplus.testing.Result;
import org.uet.dse.rtlplus.testing.TestResult;

@SuppressWarnings("serial")
public class TestResultDialog extends JPanel {

	private MainWindow parent;

	public TestResultDialog(MainWindow parent, Session session, TestResult result) {
		super();
		this.parent = parent;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JTable table = new JTable();
		table.setModel(result);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		Container srcLabelBox = Box.createHorizontalBox();
		JLabel srcLabel = new JLabel("Source classifying terms");
		srcLabelBox.add(Box.createHorizontalStrut(10));
		srcLabelBox.add(srcLabel);
		srcLabelBox.add(Box.createHorizontalGlue());
		CtValueTable srcTable = new CtValueTable(result.getSrcTermList());

		Container trgLabelBox = Box.createHorizontalBox();
		JLabel trgLabel = new JLabel("Target classifying terms");
		trgLabelBox.add(Box.createHorizontalStrut(10));
		trgLabelBox.add(trgLabel);
		trgLabelBox.add(Box.createHorizontalGlue());
		CtValueTable trgTable = new CtValueTable(result.getTrgTermList());

		Container thisLabelBox = result.isForward() ? srcLabelBox : trgLabelBox;
		Container otherLabelBox = result.isForward() ? trgLabelBox : srcLabelBox;
		CtValueTable thisTable = result.isForward() ? srcTable : trgTable;
		CtValueTable otherTable = result.isForward() ? trgTable : srcTable;

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Result res = result.getResults().get(arg0.getFirstIndex());
				session.system().reset();
				copySystemState(res.getState(), session);
				if (result.isForward()) {
					srcTable.setResultList(res.getTermSolution());
					trgTable.setResultList(res.getOtherTermSolution());
				} else {
					trgTable.setResultList(res.getTermSolution());
					srcTable.setResultList(res.getOtherTermSolution());
				}

			}
		});
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(Box.createVerticalStrut(10));
		pane.add(table.getTableHeader());
		pane.add(table);
		pane.add(Box.createVerticalStrut(10));
		pane.add(thisLabelBox);
		pane.add(Box.createVerticalStrut(10));
		pane.add(thisTable.getTableHeader());
		pane.add(thisTable);
		pane.add(Box.createVerticalStrut(10));
		pane.add(otherLabelBox);
		pane.add(Box.createVerticalStrut(10));
		pane.add(otherTable.getTableHeader());
		pane.add(otherTable);
		pane.add(Box.createVerticalStrut(10));
		JScrollPane scrollPane = new JScrollPane(pane, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}

	private boolean copySystemState(MSystemState state, Session toSes) {
		UseSystemApi api = UseSystemApi.create(toSes);
		try {
			for (MObject obj : state.allObjects()) {
				api.createObject(obj.cls().name(), obj.name());
			}
			for (MLink lnk : state.allLinks()) {
				api.createLink(lnk.association().name(), lnk.linkedObjects().stream().map(it -> it.name())
						.collect(Collectors.toList()).toArray(new String[lnk.linkedObjectsAsArray().length]));
			}
			for (MObject obj : state.allObjects()) {
				for (Entry<MAttribute, Value> entry : obj.state(state).attributeValueMap().entrySet()) {
					api.setAttributeValue(obj.name(), entry.getKey().name(), entry.getValue().toString());
				}
			}
			return true;
		} catch (UseApiException e) {
			parent.logWriter().println("Error when copying system state: " + e.getMessage());
			return false;
		}
	}

}
