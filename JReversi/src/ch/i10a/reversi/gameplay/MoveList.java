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
		return equal;
	}

	// --------------- inner classes ------------------
	public static class Move {

		String move;

		public static Move getMove(int row, int col) {
			return new Move(row, col);
		}

		private Move(int row, int col) {
			move = resolveMove(row, col);
		}

		private String resolveMove(int row, int col) {
			return new String(letters[col] + numbers[row]);
		}

		@Override
		public String toString() {
			return move;
		}

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
