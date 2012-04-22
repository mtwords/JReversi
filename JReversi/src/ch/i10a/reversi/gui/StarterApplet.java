package ch.i10a.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import ch.i10a.reversi.settings.ReversiProperties;
import ch.i10a.reversi.settings.SettingsConst;
import ch.i10a.reversi.settings.SettingsPanel;

/**
 * Starter class of the game. It is implemented as a JApplet,
 * therefore it can be used in a browser.
 */
public class StarterApplet extends JApplet {

	GeneralInfoPane infoPane = new GeneralInfoPane();
	Board board = new Board(infoPane);
	PlayerOneInfoPane playerOne;
	PlayerTwoInfoPane playerTwo;
	

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

		JButton offerDrawButton;

		GameInfoPane gameInfoPane;

		public GeneralInfoPane() {
			initComponents();
		}

		private void initComponents() {
			playerOne = new PlayerOneInfoPane();
			playerTwo = new PlayerTwoInfoPane();
			gameInfoPane = new GameInfoPane();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 4 * 50));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			offerDrawButton = new JButton("Offer Draw");
			offerDrawButton.addActionListener(new ActDraw());

			add(playerOne, BorderLayout.WEST); //Info Pane Player One
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(playerTwo, BorderLayout.WEST); //Info Pane Player Two
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(gameInfoPane, BorderLayout.WEST); //Info Pane Player Two
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(offerDrawButton);
			
		}

		private class ActDraw implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameInfoPane.offerDraw();

			
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

			
			setStonesLabelText(PlayerManager.getWhitePlayer().getStonesCount());
			if(board.getMoves().size() > 0){
				if(PlayerManager.getActivePlayer().getColor() == Color.BLACK){
					moveLabel.setText("Last Move: " + board.getLastMove());
				}
				else{
					moveLabel.setText("Last Move: ");
				}
			}
			
		}
		public void setStonesLabelText(int count){
			stonesLabel.setText("Stones: " + count);
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

			
			setStonesLabelText(PlayerManager.getBlackPlayer().getStonesCount());
			if(board.getMoves().size() > 0){
				if(PlayerManager.getActivePlayer().getColor() == Color.WHITE){
					moveLabel.setText("Last Move: " + board.getLastMove());
				}
				else{
					moveLabel.setText("Last Move: ");
				}
			}
			
		}
		public void setStonesLabelText(int count){
			stonesLabel.setText("Stones: " + count);
		}
		
	}
	
	/**
	 * A JPanel, which contains Informations about 
	 * The game (Stats, Draws, etc.)
	 */
	private class GameInfoPane extends JPanel {
		JLabel drawInfo = GuiUtil.getLabel("",300, 1 * 25);
		JButton sayYesToDraw = new JButton("Yes");
		JButton sayNoToDraw = new JButton("No");

		public GameInfoPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 3 * 50));
			setBackground(Color.lightGray);
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			add(GuiUtil.getLabel("Game Informations",300, 1 * 25));
			add(drawInfo);
			JPanel drawChoose = new JPanel();
			add(drawChoose);
			drawChoose.setLayout(new FlowLayout());
			drawChoose.setBackground(Color.lightGray);
			drawChoose.setSize(new Dimension(300, 1 * 50));
			
			/*Add ActionListener to Yes-Button */
			sayYesToDraw.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
	
					setDraw();
				}
			});
			/*Add ActionListener to No-Button */
			sayNoToDraw.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
	
					unsetDrawOffer();
				}
			});
			
			drawChoose.add(sayYesToDraw);
			drawChoose.add(sayNoToDraw);
			sayYesToDraw.setVisible(false);
			sayNoToDraw.setVisible(false);


		
		}
		
		public void paint(Graphics g) {
			super.paint(g);	
		}
		
		/*If a draw is offered, a dialog is shown in the GameInfoPane */
		public void offerDraw(){
			String setText = "";
			if(PlayerManager.getActivePlayer().getColor() == Color.WHITE){
				setText = "The WHITE Player offers you a draw. Accept?";
			}
			else{
				setText = "The BLACK Player offers you a draw. Accept?";
			}
			drawInfo.setText(setText);
			sayYesToDraw.setVisible(true);
			sayNoToDraw.setVisible(true);
			super.repaint();
		}
		
		/*If a draw is accepted, update everyf field and show the message about the draw */
		public void setDraw(){
			Field [][]fields = new Field[8][8];
			for (int i = 0; i < fields.length; i++) {
				for (int j = 0; j < fields[i].length/2; j++) {
					int fieldValue = 1;
					fields[j][i] = new Field(fieldValue, i, j);	
				}
				for (int j = fields[i].length/2; j < fields[i].length; j++) {
					int fieldValue = -1;
					fields[j][i] = new Field(fieldValue, i, j);	
				}
			}
			playerOne.setStonesLabelText(32);
			playerTwo.setStonesLabelText(32);
			PlayerManager.getBlackPlayer().setStonesCount(32);
			PlayerManager.getWhitePlayer().setStonesCount(32);
			board.setBoard(fields);
			sayYesToDraw.setVisible(false);
			sayNoToDraw.setVisible(false);
			drawInfo.setText("This game was a draw!");
			
			repaint();
		}
		
		/* if a draw is neglected, set the buttons and text invisible */
		public void unsetDrawOffer(){
			sayYesToDraw.setVisible(false);
			sayNoToDraw.setVisible(false);
			drawInfo.setText("");
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
