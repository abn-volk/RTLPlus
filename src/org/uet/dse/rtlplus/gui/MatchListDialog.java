package org.uet.dse.rtlplus.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;
import org.uet.dse.rtlplus.matching.Match;
import org.uet.dse.rtlplus.objectdiagram.MatchSelectedEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@SuppressWarnings("serial")
public class MatchListDialog extends JPanel {
	private JList<Match> list;
	private JButton btn;
	private List<Match> matches;
	private EventBus eventBus;
	private Match selectedMatch;
	private ViewFrame viewFrame;

	public MatchListDialog(ViewFrame vf, List<Match> matches, EventBus eventBus, MSystemState state,
			PrintWriter logWriter) {
		super();
		DefaultListModel<Match> model = new DefaultListModel<>();
		this.matches = matches;
		this.eventBus = eventBus;
		viewFrame = vf;
		eventBus.register(this);
		for (Match match : matches) {
			model.addElement(match);
		}
		setLayout(new BorderLayout());
		list = new JList<>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(list, BorderLayout.CENTER);
		btn = new JButton("Run");
		btn.setMnemonic('R');
		btn.setEnabled(false);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectedMatch.run(state, logWriter);
				eventBus.post(new MatchRunEvent());
				eventBus.post(new MatchSelectedEvent(new ArrayList<MObject>(0)));
			}
		});
		add(btn, BorderLayout.PAGE_END);
		list.addListSelectionListener(new SelectionHandler());
	}

	class SelectionHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (!arg0.getValueIsAdjusting()) {
				selectedMatch = matches.get(arg0.getLastIndex());
				eventBus.post(new MatchSelectedEvent(selectedMatch.getObjectList().values()));
				btn.setEnabled(true);
			}
		}
	}

	@Subscribe
	public void onMatchRun(MatchRunEvent e) {
		viewFrame.dispose();
	}
	
	@Override
	protected void finalize() throws Throwable {
		eventBus.unregister(this);
		super.finalize();
	}

	private static class MatchRunEvent {
	}

}
