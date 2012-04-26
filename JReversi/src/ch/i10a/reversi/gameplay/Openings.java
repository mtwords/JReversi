package ch.i10a.reversi.gameplay;

import ch.i10a.reversi.gameplay.MoveList.Move;

public class Openings {

	public static MoveList<Move> tigerOpening = initTigerOpening();
	public static MoveList<Move> roseOpening = initRoseOpening();
	public static MoveList<Move> buffaloOpening = initBuffaloOpening();
	public static MoveList<Move> heathOpening = initHeathOpening();
	public static MoveList<Move> inoueOpening = initInoueOpening();
	public static MoveList<Move> shamanOpening = initShamanOpening();

	public static String checkOpening(MoveList<Move> moves) {
		if (tigerOpening.equals(moves)) {
			return "Tiger";
		} else if (roseOpening.equals(moves)) {
			return "Rose";
		} else if (buffaloOpening.equals(moves)) {
			return "Buffalo";
		} else if (heathOpening.equals(moves)) {
			return "Heath";
		} else if (inoueOpening.equals(moves)) {
			return "Inoue";
		} else if (shamanOpening.equals(moves)) {
			return "Shaman";
		}
		return "";
	}

	private static MoveList<Move> initTigerOpening() {
		MoveList<Move> tiger = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f5
		tiger.add(Move.getMove(5, 4));
		// d6
		tiger.add(Move.getMove(3, 5));
		// c3
		tiger.add(Move.getMove(2, 2));
		// d3
		tiger.add(Move.getMove(3, 2));
		// c4
		tiger.add(Move.getMove(2, 3));
		return tiger;
	}

	private static MoveList<Move> initRoseOpening() {
		MoveList<Move> rose = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f5
		rose.add(Move.getMove(5, 4));
		// d6
		rose.add(Move.getMove(3, 5));
		// c5
		rose.add(Move.getMove(2, 4));
		// f4
		rose.add(Move.getMove(5, 3));
		// e3
		rose.add(Move.getMove(4, 2));
		// c6
		rose.add(Move.getMove(2, 5));
		// d3
		rose.add(Move.getMove(3, 2));
		// f6
		rose.add(Move.getMove(5, 5));
		// e6
		rose.add(Move.getMove(4, 5));
		// d7
		rose.add(Move.getMove(3, 6));
		return rose;
	}

	private static MoveList<Move> initBuffaloOpening() {
		MoveList<Move> buffalo = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f5
		buffalo.add(Move.getMove(5, 4));
		// f6
		buffalo.add(Move.getMove(5, 5));
		// e6
		buffalo.add(Move.getMove(4, 5));
		// f4
		buffalo.add(Move.getMove(5, 3));
		// c3
		buffalo.add(Move.getMove(2, 2));
		return buffalo;
	}

	private static MoveList<Move> initHeathOpening() {
		MoveList<Move> heath = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f5
		heath.add(Move.getMove(5, 4));
		// f6
		heath.add(Move.getMove(5, 5));
		// e6
		heath.add(Move.getMove(4, 5));
		// f4
		heath.add(Move.getMove(5, 3));
		// g5
		heath.add(Move.getMove(6, 4));
		return heath;
	}

	private static MoveList<Move> initInoueOpening() {
		MoveList<Move> inoue = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f5
		inoue.add(Move.getMove(5, 4));
		// d6
		inoue.add(Move.getMove(5, 4));
		// c5
		inoue.add(Move.getMove(5, 4));
		// f4
		inoue.add(Move.getMove(5, 4));
		// e3
		inoue.add(Move.getMove(5, 4));
		// c6
		inoue.add(Move.getMove(5, 4));
		// e6
		inoue.add(Move.getMove(5, 4));
		return inoue;
	}

	private static MoveList<Move> initShamanOpening() {
		MoveList<Move> shaman = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f5
		shaman.add(Move.getMove(5, 4));
		// d6
		shaman.add(Move.getMove(3, 5));
		// c5
		shaman.add(Move.getMove(2, 4));
		// f4
		shaman.add(Move.getMove(5, 3));
		// e3
		shaman.add(Move.getMove(4, 2));
		// c6
		shaman.add(Move.getMove(2, 5));
		// f3
		shaman.add(Move.getMove(5, 2));
		return shaman;
	}

}
