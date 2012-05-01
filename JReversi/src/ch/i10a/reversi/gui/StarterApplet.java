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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.i10a.reversi.gameplay.MoveHandler;
import ch.i10a.reversi.gameplay.MoveList;
import ch.i10a.reversi.gameplay.Openings;
import ch.i10a.reversi.gameplay.PlayerManager;
import ch.i10a.reversi.settings.SettingsPanel;

/**
 * Starter class of the game. It is implemented as a JApplet,
 * therefore it can be used in a browser.
 */
public class StarterApplet extends JApplet {

	GeneralInfoPane infoPane;
	Board board;
	PlayerOneInfoPane playerOne;
	PlayerTwoInfoPane playerTwo;
	
	@Override
	public void init() {
		super.init();

		// Data initialisations
		PlayerManager.init();

		// GUI initialisations
		infoPane = new GeneralInfoPane();
		board = new Board(infoPane);
	}

	public void start() {
		super.start();

		getRootPane().setJMenuBar(createMenuBar());
		add(new LetterPane(), BorderLayout.NORTH);
		add(new CipherPane(), BorderLayout.WEST);
		add(board, BorderLayout.CENTER);
		add(infoPane, BorderLayout.EAST);

	}
	@Override
	public void stop() {
		super.stop();

		board.setVisible(false);
		infoPane.setVisible(false);
		board = null;
		infoPane = null;
	}

	/**
	 * Used to manually restart the applet. In this case
	 * to start a new game from scratch.
	 */
	private void restart() {
		stop();
		destroy();
		init();
		start();
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
		JButton newGameButton;
		JButton surrenderButton;

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
			newGameButton = new JButton("New Game");
			newGameButton.addActionListener(new StartNewGame());
			surrenderButton = new JButton("Surrender");
			surrenderButton.addActionListener(new SurrenderGame());

			add(playerOne); //Info Pane Player One
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(playerTwo); //Info Pane Player Two
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(gameInfoPane); //Info Pane Player Two
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			
			add(offerDrawButton);
			add(newGameButton);
			add (surrenderButton);
			

		}

		private class ActDraw implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String drawLabel = null;
				if(PlayerManager.getActivePlayer().getColor() == Color.WHITE){
					drawLabel = "The WHITE Player offers you a draw. Accept?";
				}
				else{
					drawLabel = "The BLACK Player offers you a draw. Accept?";
				}

				int choice = JOptionPane.showConfirmDialog(board, drawLabel, "Offered draw", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					gameInfoPane.setDraw();
				}
			}

		}
		private class StartNewGame implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				restart();
			}

		}
		private class SurrenderGame implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameInfoPane.surrender();
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
		JLabel opening = GuiUtil.getLabel("Opening: ",300, 1 * 25);
		JLabel winnerInfo = GuiUtil.getLabel("",300, 1 * 25);
		JLabel gameInfo = GuiUtil.getLabel("", 300, 1 * 25);

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
			add(opening);
			add(gameInfo);
			add(winnerInfo);
		}
		
		public void paint(Graphics g) {
			super.paint(g);	
			opening.setText("Opening: " + Openings.checkOpening(board.getMoves()));

			if(!MoveHandler.checkForFreeFields() || PlayerManager.checkDoublePass()){
				
				gameInfo.setText("The game is over");

				if(PlayerManager.getBlackPlayer().getStonesCount() > PlayerManager.getWhitePlayer().getStonesCount()){
					winnerInfo.setText("BLACK Won the Game!");
				}
				else if(PlayerManager.getBlackPlayer().getStonesCount() < PlayerManager.getWhitePlayer().getStonesCount()){
					winnerInfo.setText("WHITE Won the Game!");
				}
				else{
					winnerInfo.setText("This game was a draw!");
				}
			}
			
		}
		
		public void surrender(){
			Field [][] fields = new Field[8][8];
			for (int i = 0; i < fields.length; i++) {
				for (int j = 0; j < fields[i].length; j++) {
					int fieldValue = PlayerManager.getActivePlayer().getValue() * -1;
					fields[j][i] = new Field(fieldValue, i, j);	
				}
				
			}
			if(PlayerManager.getActivePlayer().getValue() == 1){
				playerOne.setStonesLabelText(0);
				playerTwo.setStonesLabelText(64);
				PlayerManager.getBlackPlayer().setStonesCount(0);
				PlayerManager.getWhitePlayer().setStonesCount(64);
			}
			else{
				playerOne.setStonesLabelText(64);
				playerTwo.setStonesLabelText(0);
				PlayerManager.getBlackPlayer().setStonesCount(64);
				PlayerManager.getWhitePlayer().setStonesCount(0);
			}
			
			board.setBoard(fields);
			repaint();
		}
		
		/*If a draw is accepted, update every field and show the message about the draw */
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
			winnerInfo.setText("This game was a draw!");
			
			repaint();
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
			settingsDialog.setLocationRelativeTo(board);
			settingsDialog.setVisible(true);
		}
		
	}
}
