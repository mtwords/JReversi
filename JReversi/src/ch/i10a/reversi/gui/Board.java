package ch.i10a.reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import ch.i10a.reversi.gameplay.MoveList;
import ch.i10a.reversi.gameplay.MoveList.Move;
import ch.i10a.reversi.gameplay.PlayerI;
import ch.i10a.reversi.gameplay.PlayerManager;
import ch.i10a.reversi.gui.StarterApplet.GeneralInfoPane;

/**
 * A JPanel which represents the Reversi board.
 * It is initialized with 8 x 8 Fields.
 */
public class Board extends JPanel {

	private MouseListener mouseListener = new MouseListener();
	private GeneralInfoPane infoPane;

	private Field[][] fields;
	private Field activeField = null;
	private PlayerI activePlayer = null;
	private MoveList<Move> moves = new MoveList<Move>();

	public Board(GeneralInfoPane infoPane) {
		this.infoPane = infoPane;
		initFields();
		initComponents();
	}

	private void initFields() {
		fields = new Field[8][8];
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				int fieldValue = 0;
				if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
					fieldValue = -1;
				} else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
					fieldValue = 1;
				}
				fields[j][i] = new Field(fieldValue, i, j);
				fields[j][i].addMouseListener(mouseListener);
			}
		}
	}

	private void initComponents() {
		setLayout(new GridLayout(8, 8));
		setSize(new Dimension(8 * Field.WIDTH, 8 * Field.WIDTH));
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
	 * Returns the neighbour top of the active field. If 
	 * the field is at the top border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourTop(Field field) {
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldRowNum == 0) {
			return null;
		}
		return fields[field.getColNum()][activeFieldRowNum - 1];
	}
	/**
	 * Returns the neighbour bottom to the active field. If 
	 * the field is at the bottom border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |  r  |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourBottom(Field field) {
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldRowNum == 7) {
			return null;
		}
		return fields[field.getColNum()][activeFieldRowNum + 1];
	}
	/**
	 * Returns the neighbour left to the active field. If 
	 * the field is at the left border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |  r  |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourLeft(Field field) {
		int activeFieldColNum = field.getColNum();
		if (activeFieldColNum == 0) {
			return null;
		}
		return fields[activeFieldColNum - 1][field.getRowNum()];
	}
	/**
	 * Returns the neighbour right to the active field. If 
	 * the field is at the right border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |  r  |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourRight(Field field) {
		int activeFieldColNum = field.getColNum();
		if (activeFieldColNum == 7) {
			return null;
		}
		return fields[activeFieldColNum + 1][field.getRowNum()];
	}
	/**
	 * Returns the neighbour top left to the active field. If 
	 * the field is at the top or left border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |  r  |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourTopLeft(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 0 || activeFieldRowNum == 0) {
			return null;
		}
		return fields[activeFieldColNum - 1][activeFieldRowNum - 1];
	}
	/**
	 * Returns the neighbour top right to the active field. If 
	 * the field is at the right or top border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |  r  |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourTopRight(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 7 || activeFieldRowNum == 0) {
			return null;
		}
		return fields[activeFieldColNum + 1][activeFieldRowNum - 1];
	}
	/**
	 * Returns the neighbour at bottom right to the active field. If 
	 * the field is at the right or bottom border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |     |     |  r  |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourBottomRight(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 7 || activeFieldRowNum == 7) {
			return null;
		}
		return fields[activeFieldColNum + 1][activeFieldRowNum + 1];
	}
	/**
	 * Returns the neighbour at bottom left to the active field. If 
	 * the field is at the left or bottom border of the board, this method will
	 * return null.
	 * Example (a is the active field, r is the one to be returned):
	 * |-----|-----|-----|
	 * |     |     |     |
	 * |-----|-----|-----|
	 * |     |  a  |     |
	 * |-----|-----|-----|
	 * |  r  |     |     |
	 * |-----|-----|-----|
	 * @return null if field falls out of board, the corresponding field otherwise
	 */
	public Field getNeighbourBottomLeft(Field field) {
		int activeFieldColNum = field.getColNum();
		int activeFieldRowNum = field.getRowNum();
		if (activeFieldColNum == 0 || activeFieldRowNum == 7) {
			return null;
		}
		return fields[activeFieldColNum - 1][activeFieldRowNum + 1];
	}
	
	/**
	 * Returns true if a neighbour is an enemy.
	 * Example (a is the active field, r is any place an enemy could be on):
	 * |-----|-----|-----|
	 * |  r  |  r  |  r  |
	 * |-----|-----|-----|
	 * |  r  |  a  |  r  |
	 * |-----|-----|-----|
	 * |  r  |  r  |  r  |
	 * |-----|-----|-----|
	 * @return false no neighbour is an enemy
	 */
	public boolean checkNeighbourEnemiesBottom(Field field, PlayerI player){
		boolean enemyDetected = false;
		
		//Check in on every neighbour, if it is an enemy. If its an enemy, detection is positive.
		//Sysouts commented out. To Debug, uncomment
		if(getNeighbourBottom(field) != null){
			if(getNeighbourBottom(field).getValue() != player.getValue() && getNeighbourBottom(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("1");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemiesBottomLeft(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourBottomLeft(field) != null){
			if(getNeighbourBottomLeft(field).getValue() != player.getValue() && getNeighbourBottomLeft(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("2");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemiesLeft(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourLeft(field) != null){
			if(getNeighbourLeft(field).getValue() != player.getValue() && getNeighbourLeft(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("3");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemiesTopLeft(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourTopLeft(field) != null){
			if(getNeighbourTopLeft(field).getValue() != player.getValue() && getNeighbourTopLeft(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("4");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemiesTop(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourTop(field) != null){
			if(getNeighbourTop(field).getValue() != player.getValue() && getNeighbourTop(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("5");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemiesTopRight(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourTopRight(field) != null){
			if(getNeighbourTopRight(field).getValue() != player.getValue() && getNeighbourTopRight(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("6");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemiesRight(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourRight(field) != null){
			if(getNeighbourRight(field).getValue() != player.getValue() && getNeighbourRight(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("7");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemiesBottomRight(Field field, PlayerI player){
		boolean enemyDetected = false;
		if(getNeighbourBottomRight(field) != null){
			if(getNeighbourBottomRight(field).getValue() != player.getValue() && getNeighbourBottomRight(field).getValue() != 0){
				enemyDetected = true;
				//System.out.println("8");
			}
		}
		return enemyDetected;
	}
	public boolean checkNeighbourEnemies(Field field, PlayerI player){
		boolean enemiesDetected = false;
		if(checkNeighbourEnemiesBottom(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesBottomLeft(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesLeft(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesTopLeft(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesTop(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesTopRight(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesRight(field, player)){
			return true;
		}
		if(checkNeighbourEnemiesBottomRight(field, player)){
			return true;
		}
		
		return enemiesDetected;
	}



	// ----------------- inner classes --------------------
	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			activeField = (Field) e.getComponent();
			activePlayer = PlayerManager.getActivePlayer();
			
			//check on empty Field
			if(activeField.getValue() == 0){
				//check on enemies in the surrounding fields, no enemy, no further check
				if(checkNeighbourEnemies(activeField, activePlayer)){
					// updates the value depending on the player
					activeField.setValue(activePlayer.getColor() == Color.WHITE ? -1 : 1);
					// add this move to list
					moves.add(Move.getMove(activeField.getRowNum(), activeField.getColNum()));
					// updates
					activeField.repaint();
					infoPane.repaint();
					PlayerManager.nextPlayer();
				}
			}
			

			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			activeField = (Field) e.getComponent();
			activeField.setBackground(Color.YELLOW);
			activeField.repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			activeField = (Field) e.getComponent();
			activeField.setBackground(Color.GREEN);
			activeField.repaint();
		}
	}
}
