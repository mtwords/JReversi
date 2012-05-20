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
	JRadioButton easyDifficulty;
	JRadioButton mediumDifficulty;
	JRadioButton hardDifficulty;

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
			setLayout(new GridLayout(0, 2));

			JLabel oppositeLabel = new JLabel("Opposite");
			JLabel difficultyLabel = new JLabel("Difficulty");
			JLabel blankLabel = new JLabel("");
			add(oppositeLabel);
			add(difficultyLabel);

			humanOpposite = new JRadioButton("Human");
			humanOpposite.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					easyDifficulty.setVisible(false);
					mediumDifficulty.setVisible(false);
					hardDifficulty.setVisible(false);
					
				}
				
			});
			computerOpposite = new JRadioButton("Computer");
			computerOpposite.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					easyDifficulty.setVisible(true);
					mediumDifficulty.setVisible(true);
					hardDifficulty.setVisible(true);
					
				}
				
			});
			easyDifficulty = new JRadioButton("Easy");
			mediumDifficulty = new JRadioButton("Medium");
			hardDifficulty = new JRadioButton("Hard");
			add(humanOpposite);
			add(easyDifficulty);
			add(computerOpposite);
			add(mediumDifficulty);
			add(blankLabel);
			add(hardDifficulty);

			ButtonGroup group = new ButtonGroup();
			group.add(humanOpposite);
			group.add(computerOpposite);
			
			ButtonGroup difficultyGroup = new ButtonGroup();
			difficultyGroup.add(easyDifficulty);
			difficultyGroup.add(mediumDifficulty);
			difficultyGroup.add(hardDifficulty);

			// make selection
			int white = ReversiProperties.inst().getIntProperty(SettingsConst.PROP_KEY_WHITE);
			if (white == SettingsConst.PROP_VALUE_HUMAN) {
				humanOpposite.setSelected(true);
				easyDifficulty.setVisible(false);
				mediumDifficulty.setVisible(false);
				hardDifficulty.setVisible(false);
			} else {
				computerOpposite.setSelected(true);
				easyDifficulty.setVisible(true);
				mediumDifficulty.setVisible(true);
				hardDifficulty.setVisible(true);
			}
			//Choose difficulty Level
			int difficulty = ReversiProperties.inst().getIntProperty("difficulty");
			if (difficulty == SettingsConst.PROP_VALUE_EASY){
				easyDifficulty.setSelected(true);
			}
			else if(difficulty == SettingsConst.PROP_VALUE_MEDIUM){
				mediumDifficulty.setSelected(true);
			}
			else if(difficulty == SettingsConst.PROP_VALUE_HARD){
				hardDifficulty.setSelected(true);
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
					//Save Player Information
					int white = SettingsConst.PROP_VALUE_HUMAN;
					if (computerOpposite.isSelected()) {
						white = SettingsConst.PROP_VALUE_COMPUTER;
					}
					ReversiProperties.inst().setProperty(SettingsConst.PROP_KEY_WHITE, white);
					ReversiProperties.inst().save();
					
					//Save difficulty Information
					if(easyDifficulty.isSelected()){
						ReversiProperties.inst().setProperty(SettingsConst.PROP_KEY_DIFFICULTY, SettingsConst.PROP_VALUE_EASY);
						ReversiProperties.inst().save();
					}
					if(mediumDifficulty.isSelected()){
						ReversiProperties.inst().setProperty(SettingsConst.PROP_KEY_DIFFICULTY, SettingsConst.PROP_VALUE_MEDIUM);
						ReversiProperties.inst().save();
					}
					if(hardDifficulty.isSelected()){
						ReversiProperties.inst().setProperty(SettingsConst.PROP_KEY_DIFFICULTY, SettingsConst.PROP_VALUE_HARD);
						ReversiProperties.inst().save();
					}
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
