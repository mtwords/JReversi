package ch.i10a.reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import ch.i10a.reversi.gameplay.MoveHandler;
import ch.i10a.reversi.gameplay.MoveList.Move;
import ch.i10a.reversi.gameplay.PlayerI;
import ch.i10a.reversi.gameplay.PlayerManager;
import ch.i10a.reversi.gui.StarterApplet.GeneralInfoPane;

/**
 * A JPanel which represents the Reversi board.
 * It is initialized with 8 x 8 Fields.
 */
public class ReversiBoard extends Board implements ActionListener {

	private MouseListener mouseListener = new MouseListener();
	private GeneralInfoPane infoPane;

	private ReversiField activeField;
	private Timer fieldTimer;
	private Timer animationCheckTimer;

	public ReversiBoard(GeneralInfoPane infoPane) {
		this.infoPane = infoPane;
		fieldTimer = new Timer(3, null);
		fieldTimer.setRepeats(true);

		initFields();
		MoveHandler.registerFields(fields);
		PlayerManager.registerBoard(this);
		initComponents();
		MoveHandler.collectingPossibleFieldHits();

		fieldTimer.start();
		animationCheckTimer = new Timer(5, this);//0 is a possible value
		animationCheckTimer.setRepeats(true);
	}
	
	/**
	 * Initializes the Fields with the beginning table setup
	 */
	private void initFields() {
		fields = new ReversiField[8][8];
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				int fieldValue = 0;
				if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
					fieldValue = -1;
				} else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
					fieldValue = 1;
				}
				fields[j][i] = new ReversiField(fieldValue, i, j);
				fields[j][i].addMouseListener(mouseListener);
				// add the field to the timer for animation purposes
				fieldTimer.addActionListener(fields[j][i]);
			}
		}
	}

	/**
	 * Initializes the components of the board
	 */
	private void initComponents() {
		setLayout(new GridLayout(8, 8));
		setSize(new Dimension(8 * ReversiField.WIDTH, 8 * ReversiField.WIDTH));
		setPreferredSize(getSize());
		setMaximumSize(getSize());
		setMinimumSize(getSize());

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				add(fields[j][i]);
			}
		}
	}
	
	// ----------------- helper methods --------------------
	/**
	 * sets the game fields to the values given in the parameter 
	 *  
	 */
	public void setBoard(ReversiField fields[][]){
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				this.fields[i][j].setValue(fields[i][j].getValue());
//				this.fields[i][j].update(this.fields[i][j].getValue());
			}
		}
	}

	/**
	 * Performs the move the player has choosen. This move is under
	 * concurrency control.
	 * @param field
	 */
	@Override
	public void doMove(Field field) {
		ArrayList<Field> hitFields = MoveHandler.doMove(field);
		if (!hitFields.isEmpty()) {
			animationCheckTimer.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (MoveHandler.isAnimating()) {
			return;
		}
		animationCheckTimer.stop();

		// add this move to list
		moves.add(Move.getMove(activeField.getRowNum(), activeField.getColNum()));
		// updates
		infoPane.repaint();

		activeField.repaint();
		PlayerManager.setUnPass();
		PlayerManager.nextPlayer();

		MoveHandler.collectingPossibleFieldHits();
		repaint();


		MoveHandler.checkIfNextPlayerIsPossibleToMove();
		PlayerManager.getActivePlayer().move();
	}
	
	// ----------------- inner classes --------------------
	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			activeField = (ReversiField) e.getComponent();
			doMove(activeField);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			ReversiField activeField = (ReversiField) e.getComponent();
			PlayerI activePlayer = PlayerManager.getActivePlayer();

			if (activeField.getValue() != 0) {
				return;
			}
			if (MoveHandler.checkNeighbourEnemies(activeField, activePlayer) 
					&& MoveHandler.checkHit(activeField, activePlayer)) {
				activeField.setBackground(Color.YELLOW);
				activeField.repaint();
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			ReversiField activeField = (ReversiField) e.getComponent();
			activeField.setBackground(Color.GREEN);
			activeField.repaint();
		}
	}
}