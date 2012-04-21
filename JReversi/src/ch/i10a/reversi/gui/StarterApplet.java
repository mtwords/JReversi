package ch.i10a.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ch.i10a.reversi.gameplay.MoveList;
import ch.i10a.reversi.gameplay.PlayerManager;
import ch.i10a.reversi.settings.SettingsPanel;

/**
 * Starter class of the game. It is implemented as a JApplet,
 * therefore it can be used in a browser.
 */
public class StarterApplet extends JApplet {

	GeneralInfoPane infoPane = new GeneralInfoPane();
	Board board = new Board(infoPane);
	

	@Override
	public void init() {
		super.init();
		getRootPane().setJMenuBar(createMenuBar());
		add(new LetterPane(), BorderLayout.NORTH);
		add(new CipherPane(), BorderLayout.WEST);
		add(board, BorderLayout.CENTER);
		add(infoPane, BorderLayout.EAST);

	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Settings");
		JMenuItem settingsItem = new JMenuItem("Settings...");
		settingsItem.addActionListener(new SettingsAction());
		menu.add(settingsItem);
		menuBar.add(menu);
		return menuBar;
	}

	/**
	 * Overridden to set a fix size. Could be removed if dynamic size
	 * is supported.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setSize(new Dimension(325 + (8 * Field.WIDTH), 25 + 25 + (8 * Field.WIDTH)));
	}
	

	// --------------- inner classes ------------------
	/**
	 * A JPanel, which can be used to name the a - h Fields.
	 * By now it is set fixed as horizontal.
	 */
	private class LetterPane extends JPanel {

		public LetterPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setSize(new Dimension(8 * 50, 25));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			add(GuiUtil.getLabel("", 25, 25));
			for (int i = 0; i < MoveList.letters.length; i++) {
				add(GuiUtil.getXOrientedFieldLabel(MoveList.letters[i]));
			}
		}

	}

	/**
	 * A JPanel, which can be used to name the 1 - 8 Fields.
	 * By now it is set fixed as vertical.
	 */
	private class CipherPane extends JPanel {

		public CipherPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(25, 8 * 50));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			for (int i = 0; i < MoveList.numbers.length; i++) {
				add(GuiUtil.getYOrientedFieldLabel(MoveList.numbers[i]));
				
			}
		}

	}
	
	/**
	 * A JPanel, which contains the two Informations for 
	 * the Players. The Player that's on has a Frame
	 * Around his Info Pane
	 */
	protected class GeneralInfoPane extends JPanel {

		JButton pass;
		PlayerOneInfoPane playerOne;
		PlayerTwoInfoPane playerTwo;

		public GeneralInfoPane() {
			initComponents();
		}

		private void initComponents() {
			playerOne = new PlayerOneInfoPane();
			playerTwo = new PlayerTwoInfoPane();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 4 * 50));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			pass = new JButton("Pass");
			pass.addActionListener(new ActPass());

			add(playerOne, BorderLayout.WEST); //Info Pane Player One
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(playerTwo, BorderLayout.WEST); //Info Pane Player Two
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(pass);
			
		}

		class ActPass implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				PlayerManager.setPass();
				
				PlayerManager.nextPlayer();
				repaint();
			}

		}
	}
	
	
	/**
	 * A JPanel, which contains Informations for 
	 * Player One (Black Player)
	 */
	private class PlayerOneInfoPane extends JPanel {
		
		JLabel passLabel = GuiUtil.getLabel("", 300, 1*25);
		JLabel stonesLabel = GuiUtil.getLabel("Stones: " + PlayerManager.getWhitePlayer().getStonesCount(), 300, 1*25);
		JLabel moveLabel = GuiUtil.getLabel("Last Move: ", 300, 1*25);
		
		public PlayerOneInfoPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 2 * 50));
			setBackground(Color.lightGray);
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			add(GuiUtil.getLabel("Player 1",300, 1 * 25));
			add(stonesLabel);
			add(moveLabel);
			add(passLabel);
		}

		public void paint(Graphics g) {
			super.paint(g);
			
			if (PlayerManager.getActivePlayer().getColor() == Color.WHITE) {
				g.setColor(Color.RED);
				g.fillRect(0, 0, 50, 50);
			}
			
			g.setColor(Color.BLACK);
			g.drawOval(4, 4, 40, 40);
			g.setColor(Color.WHITE);
			g.fillOval(4, 4, 40, 40);
			
			if(PlayerManager.getPass() == -1){
				passLabel.setText("pass");
			}
			else{
				passLabel.setText("");
			}

			stonesLabel.setText("Stones: " + PlayerManager.getWhitePlayer().getStonesCount());
			
			if(board.getMoves().size() > 0){
				if(PlayerManager.getActivePlayer().getColor() == Color.BLACK){
					moveLabel.setText("Last Move: " + board.getLastMove());
				}
				else{
					moveLabel.setText("Last Move: ");
				}
			}
			
		}
		
		
	}
	
	
	/**
	 * A JPanel, which contains Informations for 
	 * Player Two (White Player)
	 */
	private class PlayerTwoInfoPane extends JPanel {
		
		JLabel passLabel = GuiUtil.getLabel("", 300, 1*25);
		JLabel stonesLabel = GuiUtil.getLabel("Stones: " + PlayerManager.getBlackPlayer().getStonesCount(), 300, 1*25);
		JLabel moveLabel = GuiUtil.getLabel("Last Move: ", 300, 1*25);

		public PlayerTwoInfoPane() {
			initComponents();
		}

		private void initComponents() {
			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 2 * 50));
			setBackground(Color.lightGray);
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			add(GuiUtil.getLabel("Player 2",300, 1 * 25));
			add(stonesLabel);
			add(moveLabel);
			add(passLabel);
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			
			if (PlayerManager.getActivePlayer().getColor() == Color.BLACK) {
				g.setColor(Color.RED);
				g.fillRect(0, 0, 50, 50);
			}
			
			g.setColor(Color.BLACK);
			g.fillOval(4, 4, 40, 40);
			
			if(PlayerManager.getPass() == 1){
				passLabel.setText("pass");
			}
			else{
				passLabel.setText("");
			}

			stonesLabel.setText("Stones: " + PlayerManager.getBlackPlayer().getStonesCount());
			if(board.getMoves().size() > 0){
				if(PlayerManager.getActivePlayer().getColor() == Color.WHITE){
					moveLabel.setText("Last Move: " + board.getLastMove());
				}
				else{
					moveLabel.setText("Last Move: ");
				}
			}
			
		}
		
	}

	// --------------- menu classes ------------------
	private class SettingsAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SettingsPanel settingsPanel = new SettingsPanel();
			JDialog settingsDialog = new JDialog((Frame) null, "Settings");
			settingsDialog.add(settingsPanel);
			settingsDialog.pack();
			settingsDialog.setVisible(true);
		}
		
	}
}
