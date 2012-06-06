package ch.i10a.reversi.gui;

import javax.swing.JPanel;

import ch.i10a.reversi.gameplay.MoveList;
import ch.i10a.reversi.gameplay.MoveList.Move;

public class Board extends JPanel implements Cloneable {

	protected Field[][] fields;
	protected MoveList<Move> moves = new MoveList<Move>();

	/**
	 * getter for the fields in this board
	 * 
	 * @return an array of Field
	 */
	public Field[][] getFields() {
		return fields;
	}
	
	/**
	 * setter for the fields in this board
	 * 
	 * @param an array of Field
	 */
	public void setFields(Field[][] fields) {
		this.fields = fields;
	}

	/**
	 * mainly here for inheritance reasons
	 */
	public void doMove(Field field) {
		// do nothing
	}

	/**
	 * Returns a List of Moves that were made throughout the game 
	 * 
	 * @return MoveList<Move> of Moves made throughout the game
	 */
	public MoveList<Move> getMoves(){
		return moves;
	}
	
	/**
	 * Getter: Returns the last Move made in the game 
	 * 
	 * @return the last made Move 
	 */
	public Move getLastMove(){
		return moves.get(moves.size() - 1);
	}

	/**
	 * nulls out all the fields on this board (saving memory)
	 * 
	 * @param an array of Field
	 */
	public void nullFields() {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				fields[i][j] = null;
			}
		}
		fields = null;
	}

	/**
	 * clones this board to have a non cohesive object
	 * 
	 * @param clone is a board containing the same values as the original board
	 */
	@Override
	public Board clone() {
		Board clone = new Board();

		Field[][] cloneFields = new Field[8][8];
		for (int i = 0; i < cloneFields.length; i++) {
			for (int j = 0; j < cloneFields[i].length; j++) {
				cloneFields[i][j] = fields[i][j].clone();
			}
		}
		clone.setFields(cloneFields);

		return clone;
	}
}
