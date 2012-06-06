package ch.i10a.reversi.gameplay;

import java.util.ArrayList;

import ch.i10a.reversi.gameplay.MoveList.Move;

public class MoveList<T> extends ArrayList<Move> {

	public static String[] letters = new String[] {
		"a",
		"b",
		"c",
		"d",
		"e",
		"f",
		"g",
		"h",
	};
	public static String[] numbers = new String[] {
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
	};

	/**
	 * equals method for this class. is true when the moves saved in are equal
	 * 
	 * @param Object o to equal with
	 * 
	 * @return true if object o is equal with this object
	 */
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof MoveList)) {
			return false;
		}

		MoveList<Move> other = (MoveList<Move>) o;

		if (isEmpty() || other.isEmpty()) {
			return false;
		}

		boolean equal = false;
		if(this.size() == other.size()){
			for (int i = 0; i < size(); i++) {
				if (get(i) == null || other.get(i) == null) {
					return false;
				}
				Move m = (Move) get(i);
				if (m.equals(other.get(i))) {
					equal = true;
				} else {
					equal = false;
				}
			}
		}
		
		return equal;
	}

	// --------------- inner classes ------------------
	public static class Move {

		String move;

		/**
		 * gives back a move by taking the overgiven row and col.
		 * 
		 * @param int row is a numeric representation of the row in the board
		 * @param int col is a numeric representation of the column in the board
		 * 
		 * @return a Move created from row and col
		 */
		public static Move getMove(int row, int col) {
			return new Move(row, col);
		}

		/**
		 * creates a move by taking the overgiven row and col.
		 * 
		 * @param int row is a numeric representation of the row in the board
		 * @param int col is a numeric representation of the column in the board
		 * 
		 * @return an ArrayList of Fields that are hit by the move done in the field variable
		 */
		private Move(int row, int col) {
			move = resolveMove(row, col);
		}

		/**
		 * resolves the numeric representation in a String representation of the place of the field
		 * 
		 * @param int row is a numeric representation of the row in the board
		 * @param int col is a numeric representation of the column in the board
		 * 
		 * @return a String containing the letters and numbers of the concerned field
		 */
		private String resolveMove(int row, int col) {
			return new String(letters[col] + numbers[row]);
		}

		/**
		 * gives back the string representation of the move
		 * 
		 * @return a String representation of move
		 */
		@Override
		public String toString() {
			return move;
		}

		/**
		 * equals method for this class. is true when the move saved in are equal
		 * 
		 * @param Object obj to equal with
		 * 
		 * @return true if object obj is equal with this object
		 */
		@Override
		public boolean equals(Object obj) {
			if (! (obj instanceof Move)) {
				return false;
			}

			if (move == null || obj == null) {
				return false;
			}

			Move other = (Move) obj;
			return move.equals(other.toString());
		}
	}

}
