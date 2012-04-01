package ch.i10a.reversi.gameplay;

import java.util.ArrayList;

public class MoveList<Move> extends ArrayList<Move> {

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
			return new String(letters[col] + numbers[row+1]);
		}

		@Override
		public String toString() {
			return move;
		}
	}

}
