package ch.i10a.reversi.gui;

import javax.swing.JPanel;

import ch.i10a.reversi.gameplay.MoveList;
import ch.i10a.reversi.gameplay.MoveList.Move;

public class Board extends JPanel implements Cloneable {

	protected Field[][] fields;
	protected MoveList<Move> moves = new MoveList<Move>();

	public Field[][] getFields() {
		return fields;
	}
	public void setFields(Field[][] fields) {
		this.fields = fields;
	}

	public void doMove(Field field) {
		// do nothing
	}

	/**
	 * Returns a List of Moves that were made throughout the game 
	 * @return MoveList<Move> of Moves made throughout the game
	 */
	public MoveList<Move> getMoves(){
		return moves;
	}
	
	/**
	 * Returns the last Move made in the game 
	 * @return the last made Move 
	 */
	public Move getLastMove(){
		return moves.get(moves.size() - 1);
	}

	public void nullFields() {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				fields[i][j] = null;
			}
		}
		fields = null;
	}

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
