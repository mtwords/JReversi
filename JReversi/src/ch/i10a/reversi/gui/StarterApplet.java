package ch.i10a.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import ch.i10a.reversi.gameplay.PlayerAdapter;
import ch.i10a.reversi.gameplay.PlayerManager;
import ch.i10a.reversi.settings.SettingsPanel;

/**
 * Starter class of the game. It is implemented as a JApplet,
 * therefore it can be used in a browser.
 */
public class StarterApplet extends JApplet {

	GeneralInfoPane infoPane;
	ReversiBoard board;
	PlayerOneInfoPane playerOne;
	PlayerTwoInfoPane playerTwo;
	GameInfoPane gameInfoPane;
	JPanel buttonPanel;

	JButton offerDrawButton;
	JButton newGameButton;
	JButton surrenderButton;

	@Override
	public void init() {
		super.init();

		// Data initialisations
		PlayerManager.init();

		// GUI initialisations
		infoPane = new GeneralInfoPane();
		board = new ReversiBoard(infoPane);
	}

	public void start() {
		super.start();

		getRootPane().setJMenuBar(createMenuBar());
		add(new LetterPane(), BorderLayout.NORTH);
		add(new CipherPane(), BorderLayout.WEST);
		add(board, BorderLayout.CENTER);
		add(infoPane, BorderLayout.EAST);
		add(buttonPanel = createButtonPanel(), BorderLayout.SOUTH);
	}
	@Override
	public void stop() {
		super.stop();

		board.setVisible(false);
		infoPane.setVisible(false);
		buttonPanel.setVisible(false);
		board = null;
		infoPane = null;
		buttonPanel = null;
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

	/**
	 * Creation of the menu bar for the settings
	 */
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
	 * Creation of the container that holds the gameplay buttons
	 */
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		offerDrawButton = new JButton("Offer Draw");
		offerDrawButton.addActionListener(new ActDraw());
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new StartNewGame());
		surrenderButton = new JButton("Surrender");
		surrenderButton.addActionListener(new SurrenderGame());

		buttonPanel.add(newGameButton);
		buttonPanel.add(offerDrawButton);
		buttonPanel.add(surrenderButton);
		return buttonPanel;
	}

	/**
	 * Overridden to set a fix size. Could be removed if dynamic size
	 * is supported.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setSize(new Dimension(330 + (8 * ReversiField.WIDTH), 25 + 25 + 35 + (8 * ReversiField.WIDTH)));
	}
		

	// --------------- inner classes ------------------
	
	/**
	 * this class just starts a new game via the ActionListener Interface
	 */
	private class StartNewGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			restart();
		}
	}

	/**
	 * this class plays draw via the ActionListener Interface.
	 * It is offered to the opponent, and if accepted, a fancy animation
	 * is going on.
	 */
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

				//if the game is over, those buttons may not be pressed anymore.
				offerDrawButton.setEnabled(false);
				surrenderButton.setEnabled(false);
			}
		}
	}
	/**
	 * this class starts the surrender of the active player via the ActionListener Interface.
	 * .
	 */
	private class SurrenderGame implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//if the game is over, those buttons may not be pressed anymore.
			offerDrawButton.setEnabled(false);
			surrenderButton.setEnabled(false);
			//the surrender function kicks in
			gameInfoPane.surrender();
		}
	}

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
	protected class GeneralInfoPane extends JPanel implements ActionListener {

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
			
			add(playerOne); //Info Pane Player One
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(playerTwo); //Info Pane Player Two
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(gameInfoPane); //Info Pane Player Two
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
		}

		/**
		 * after every move, the player stats have to be updated. this happens here
		 */
		public void updateInfo() {
			PlayerAdapter player = PlayerManager.getActivePlayer();
			PlayerAdapter opposite = PlayerManager.getOtherPlayer(player);

			if (player == PlayerManager.getWhitePlayer()) {
				playerTwo.setPlayerLabelText("> ");
				playerOne.setPlayerLabelText("");
				playerOne.setStonesLabelText(player.getStonesCount());
				playerTwo.setStonesLabelText(opposite.getStonesCount());
				if (PlayerManager.getPass() == player.getValue()) {
					playerOne.setPassLabelText("pass");
				} else {
					playerOne.setPassLabelText("");
				}
				playerOne.setLastMoveLabelText();
			} else {
				playerTwo.setPlayerLabelText("");
				playerOne.setPlayerLabelText("> ");
				playerTwo.setStonesLabelText(player.getStonesCount());
				playerOne.setStonesLabelText(opposite.getStonesCount());
				if (PlayerManager.getPass() == player.getValue()) {
					playerTwo.setPassLabelText("pass");
				} else {
					playerTwo.setPassLabelText("");
				}
				playerTwo.setLastMoveLabelText();
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
//			updateImage(PlayerManager.getActivePlayer());
		}
//		public void updateImage(PlayerAdapter player) {
//			Graphics g = null;
//			playerOne.getGraphics().setColor(playerOne.getBackground());
//			playerOne.getGraphics().fillRect(20, 20, 20, 20);
//			playerTwo.getGraphics().setColor(playerTwo.getBackground());
//			playerTwo.getGraphics().fillRect(20, 20, 40, 40);
//			if (player instanceof HumanPlayer) {
//				image = ReversiField.whiteStone;
//				g = playerOne.getGraphics();
//			} else {
//				image = ReversiField.blackStone;
//				g = playerTwo.getGraphics();
//			}
//			g.drawImage(image, 20, 20, null);
//		}
	}
	
	
	/**
	 * A JPanel, which contains Informations for 
	 * Player One (Black Player)
	 */
	private class PlayerOneInfoPane extends JPanel {
		JLabel playerLabel = GuiUtil.getLabel("White Player",300, 1 * 25);
		JLabel passLabel = GuiUtil.getLabel("", 300, 1*25);
		JLabel stonesLabel = GuiUtil.getLabel("Stones: " + PlayerManager.getWhitePlayer().getStonesCount(), 300, 1*25);
		JLabel moveLabel = GuiUtil.getLabel("Last Move: ", 300, 1*25);
		
		public PlayerOneInfoPane() {
			initComponents();
			setStonesLabelText(PlayerManager.getWhitePlayer().getStonesCount());
			setPlayerLabelText("> ");
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 2 * 50));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			add(playerLabel);
			add(stonesLabel);
			add(moveLabel);
			add(passLabel);
		}

		/**
		 * the player Label has to be updated sometimes. Done with this
		 * 
		 * @param prefix for the player 1
		 */
		public void setPlayerLabelText(String prefix) {
			playerLabel.setText(prefix + "White Player");
		}

		/**
		 * the stone count has to be updated sometimes. Done with this
		 * 
		 * @param integer of stones
		 */
		public void setStonesLabelText(int count){
			stonesLabel.setText("Stones: " + count);
		}

		/**
		 * if a player passes, this has to be displayed
		 * 
		 * @param String with the pass text in it
		 */
		public void setPassLabelText(String pass) {
			passLabel.setText(pass);
		}

		/**
		 * the last move for this player has to be displayed
		 * 
		 */
		public void setLastMoveLabelText() {
			moveLabel.setText("Last Move: " + board.getLastMove());
		}
	}
	
	
	/**
	 * A JPanel, which contains Informations for 
	 * Player Two (White Player)
	 */
	private class PlayerTwoInfoPane extends JPanel {
		
		JLabel playerLabel = GuiUtil.getLabel("Black Player",300, 1 * 25);
		JLabel passLabel = GuiUtil.getLabel("", 300, 1*25);
		JLabel stonesLabel = GuiUtil.getLabel("Stones: " + PlayerManager.getBlackPlayer().getStonesCount(), 300, 1*25);
		JLabel moveLabel = GuiUtil.getLabel("Last Move: ", 300, 1*25);

		public PlayerTwoInfoPane() {
			initComponents();
			setStonesLabelText(PlayerManager.getBlackPlayer().getStonesCount());
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 2 * 50));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			add(playerLabel);
			add(stonesLabel);
			add(moveLabel);
			add(passLabel);
		}

		/**
		 * the player Label has to be updated sometimes. Done with this
		 * 
		 * @param prefix for the player 2
		 */
		public void setPlayerLabelText(String prefix) {
			playerLabel.setText(prefix + "Black Player");
		}

		/**
		 * the stone count has to be updated sometimes. Done with this
		 * 
		 * @param integer of stones
		 */
		public void setStonesLabelText(int count){
			stonesLabel.setText("Stones: " + count);
		}

		/**
		 * if a player passes, this has to be displayed
		 * 
		 * @param String with the pass text in it
		 */
		public void setPassLabelText(String pass) {
			passLabel.setText("");
		}

		/**
		 * the last move for this player has to be displayed
		 * 
		 */
		public void setLastMoveLabelText() {
			moveLabel.setText("Last Move: " + board.getLastMove());
		}
	}
	
	/**
	 * A JPanel, which contains Informations about 
	 * The game (Stats, Draws, etc.)
	 */
	private class GameInfoPane extends JPanel {
		JLabel opening = GuiUtil.getLabel("",300, 1 * 25);
		JLabel winnerInfo = GuiUtil.getLabel("",300, 1 * 25);
		JLabel gameInfo = GuiUtil.getLabel("", 300, 1 * 25);

		public GameInfoPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
			setSize(new Dimension(300, 3 * 50));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			add(GuiUtil.getLabel("Game Informations",300, 1 * 25));
			add(opening);
			add(gameInfo);
			add(winnerInfo);
		}
		
		/**
		 * here, the redisplay of the gameInformation takes place
		 * 
		 */
		public void paint(Graphics g) {
			super.paint(g);	
			if(!Openings.checkOpening(board.getMoves()).isEmpty()){
				opening.setText("Opening: " + Openings.checkOpening(board.getMoves()));
			}

			//when a double pass happens or if there are no free fields, the game is over
			if(!MoveHandler.checkForFreeFields() || PlayerManager.checkDoublePass()){
				//then, those buttons may not be used no more
				offerDrawButton.setEnabled(false);
				surrenderButton.setEnabled(false);

				gameInfo.setText("The game is over");

				//who was the winner. It has to be displayed
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
		
		/**
		 * the surrender method. If called and afterwards a repaint is pushed, a fancy animation kicks in.
		 * 
		 */
		public void surrender(){
			ReversiField [][] fields = new ReversiField[8][8];
			for (int i = 0; i < fields.length; i++) {
				for (int j = 0; j < fields[i].length; j++) {
					int fieldValue = PlayerManager.getActivePlayer().getValue() * -1;
					fields[j][i] = new ReversiField(fieldValue, i, j);	
				}
				
			}
			//if player2 surrenders, the field is filling with white stones
			if(PlayerManager.getActivePlayer().getValue() == 1){
				playerOne.setStonesLabelText(0);
				playerTwo.setStonesLabelText(64);
				PlayerManager.getBlackPlayer().setStonesCount(0);
				PlayerManager.getWhitePlayer().setStonesCount(64);
			}
			//if player1 surrenders, the field is filling with black stones
			else{
				playerOne.setStonesLabelText(64);
				playerTwo.setStonesLabelText(0);
				PlayerManager.getBlackPlayer().setStonesCount(64);
				PlayerManager.getWhitePlayer().setStonesCount(0);
			}
			
			board.setBoard(fields);
			board.repaint();
			repaint();
		}
		
		/**
		 * If a draw is accepted, update every field with a fancy animation and show the message about the draw.
		 * the field representations are 32 black and 32 white
		 * 
		 */
		public void setDraw(){
			for (int i = 0; i < board.fields.length; i++) {
				for (int j = 0; j < board.fields[i].length/2; j++) {
					board.fields[j][i].setValue(1);
				}
				for (int j = board.fields[i].length/2; j < board.fields[i].length; j++) {
					board.fields[j][i].setValue(-1);
				}
			}
			playerOne.setStonesLabelText(32);
			playerTwo.setStonesLabelText(32);
			PlayerManager.getBlackPlayer().setStonesCount(32);
			PlayerManager.getWhitePlayer().setStonesCount(32);
			winnerInfo.setText("This game was a draw!");
			board.repaint();
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
