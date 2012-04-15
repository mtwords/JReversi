package ch.i10a.reversi.settings;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ch.i10a.reversi.gui.GuiUtil;

public class SettingsPanel extends JPanel {

	// Components
	JRadioButton humanOpposite;
	JRadioButton computerOpposite;

	public SettingsPanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		add(new OppositePanel(), BorderLayout.NORTH);
		add(new ButtonPanel(), BorderLayout.SOUTH);
	}

	// --------------- inner classes ----------------
	private class OppositePanel extends JPanel {

		public OppositePanel() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new GridLayout(3, 1));

			JLabel oppositeLabel = new JLabel("Opposite");
			add(oppositeLabel);

			humanOpposite = new JRadioButton("Human");
			computerOpposite = new JRadioButton("Computer");
			add(humanOpposite);
			add(computerOpposite);

			ButtonGroup group = new ButtonGroup();
			group.add(humanOpposite);
			group.add(computerOpposite);

			// make selection
			int white = ReversiProperties.inst().getIntProperty(SettingsConst.PROP_KEY_WHITE);
			if (white == SettingsConst.PROP_VALUE_HUMAN) {
				humanOpposite.setSelected(true);
			} else {
				computerOpposite.setSelected(true);
			}
		}
	}

	private class ButtonPanel extends JPanel {

		// Buttons
		JButton save;
		JButton close;

		public ButtonPanel() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new FlowLayout());

			save = new JButton("Save");
			save.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int white = SettingsConst.PROP_VALUE_HUMAN;
					if (computerOpposite.isSelected()) {
						white = SettingsConst.PROP_VALUE_COMPUTER;
					}
					ReversiProperties.inst().setProperty(SettingsConst.PROP_KEY_WHITE, white);
					ReversiProperties.inst().save();
				}
			});
			add(save);

			close = new JButton("Close");
			close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GuiUtil.closeAncestorWindow(SettingsPanel.this);
				}
			});
			add(close);
		}
	}
}
