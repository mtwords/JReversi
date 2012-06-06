package ch.i10a.reversi.gameplay;

import ch.i10a.reversi.gameplay.MoveList.Move;

public class Openings {

	public static MoveList<Move> tigerOpeningSouth = initTigerOpeningSouth();
	public static MoveList<Move> tigerOpeningEast = initTigerOpeningEast();
	public static MoveList<Move> tigerOpeningNorth = initTigerOpeningNorth();
	public static MoveList<Move> tigerOpeningWest = initTigerOpeningWest();
	public static MoveList<Move> roseOpeningSouth = initRoseOpeningSouth();
	public static MoveList<Move> roseOpeningEast = initRoseOpeningEast();
	public static MoveList<Move> roseOpeningNorth = initRoseOpeningNorth();
	public static MoveList<Move> roseOpeningWest = initRoseOpeningWest();
	public static MoveList<Move> buffaloOpeningNorth = initBuffaloOpeningNorth();
	public static MoveList<Move> buffaloOpeningEast = initBuffaloOpeningEast();
	public static MoveList<Move> buffaloOpeningSouth = initBuffaloOpeningSouth();
	public static MoveList<Move> buffaloOpeningWest = initBuffaloOpeningWest();
	public static MoveList<Move> heathOpeningSouth = initHeathOpeningSouth();
	public static MoveList<Move> heathOpeningEast = initHeathOpeningEast();
	public static MoveList<Move> heathOpeningNorth = initHeathOpeningNorth();
	public static MoveList<Move> heathOpeningWest = initHeathOpeningWest();
	public static MoveList<Move> inoueOpeningSouth = initInoueOpeningSouth();
	public static MoveList<Move> inoueOpeningEast = initInoueOpeningEast();
	public static MoveList<Move> inoueOpeningNorth = initInoueOpeningNorth();
	public static MoveList<Move> inoueOpeningWest = initInoueOpeningWest();
	public static MoveList<Move> shamanOpeningSouth = initShamanOpeningSouth();
	public static MoveList<Move> shamanOpeningEast = initShamanOpeningEast();
	public static MoveList<Move> shamanOpeningNorth = initShamanOpeningNorth();
	public static MoveList<Move> shamanOpeningWest = initShamanOpeningWest();

	
	/**
	 * Returns the opening played 
	 * 
	 * @return String of the played opening 
	 */
	public static String checkOpening(MoveList<Move> moves) {
		if (tigerOpeningSouth.equals(moves)) {
			return "Tiger";
		} else if (tigerOpeningEast.equals(moves)) {
			return "Tiger";
		} else if (tigerOpeningNorth.equals(moves)) {
			return "Tiger";
		} else if (tigerOpeningWest.equals(moves)) {
			return "Tiger";
		} else if (roseOpeningSouth.equals(moves)) {
			return "Rose";
		} else if (roseOpeningEast.equals(moves)) {
			return "Rose";
		} else if (roseOpeningNorth.equals(moves)) {
			return "Rose";
		} else if (roseOpeningWest.equals(moves)) {
			return "Rose";
		} else if (buffaloOpeningSouth.equals(moves)) {
			return "Buffalo";
		} else if (buffaloOpeningEast.equals(moves)) {
			return "Buffalo";
		} else if (buffaloOpeningNorth.equals(moves)) {
			return "Buffalo";
		} else if (heathOpeningSouth.equals(moves)) {
			return "Heath";
		} else if (heathOpeningEast.equals(moves)) {
			return "Heath";
		} else if (heathOpeningNorth.equals(moves)) {
			return "Heath";
		} else if (heathOpeningWest.equals(moves)) {
			return "Heath";
		} else if (inoueOpeningSouth.equals(moves)) {
			return "Inoue";
		} else if (inoueOpeningEast.equals(moves)) {
			return "Inoue";
		} else if (inoueOpeningNorth.equals(moves)) {
			return "Inoue";
		} else if (inoueOpeningWest.equals(moves)) {
			return "Inoue";
		} else if (shamanOpeningSouth.equals(moves)) {
			return "Shaman";
		}else if (shamanOpeningEast.equals(moves)) {
			return "Shaman";
		} else if (shamanOpeningNorth.equals(moves)) {
			return "Shaman";
		}else if (shamanOpeningWest.equals(moves)) {
			return "Shaman";
		}
		return "";
	}

	/**
	 * Returns the moves of the TigerOpening in South direction 
	 * 
	 * @return MoveList of Moves of the TigerOpening 
	 */
	private static MoveList<Move> initTigerOpeningSouth() {
		MoveList<Move> tiger = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f4
		tiger.add(Move.getMove(3, 5));
		// d3
		tiger.add(Move.getMove(2, 3));
		// c6
		tiger.add(Move.getMove(5, 2));
		// d6
		tiger.add(Move.getMove(5, 3));
		// c5
		tiger.add(Move.getMove(4, 2));
		return tiger;
	}
	
	/**
	 * Returns the moves of the TigerOpening in East direction 
	 * 
	 * @return MoveList of Moves of the TigerOpening 
	 */
	private static MoveList<Move> initTigerOpeningEast() {
		MoveList<Move> tiger = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// e3
		tiger.add(Move.getMove(2, 4));
		// f5
		tiger.add(Move.getMove(4, 5));
		// c6
		tiger.add(Move.getMove(5, 2));
		// c5
		tiger.add(Move.getMove(4, 2));
		// d6
		tiger.add(Move.getMove(5, 3));
		return tiger;
	}
	
	/**
	 * Returns the moves of the TigerOpening in North direction 
	 * 
	 * @return MoveList of Moves of the TigerOpening 
	 */
	private static MoveList<Move> initTigerOpeningNorth() {
		MoveList<Move> tiger = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// c5
		tiger.add(Move.getMove(4, 2));
		// e6
		tiger.add(Move.getMove(5, 4));
		// f3
		tiger.add(Move.getMove(2, 5));
		// e3
		tiger.add(Move.getMove(2, 4));
		// f4
		tiger.add(Move.getMove(3, 5));
		return tiger;
	}
	
	/**
	 * Returns the moves of the TigerOpening in West direction 
	 * 
	 * @return MoveList of Moves of the TigerOpening 
	 */
	private static MoveList<Move> initTigerOpeningWest() {
		MoveList<Move> tiger = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// d6
		tiger.add(Move.getMove(5, 3));
		// c4
		tiger.add(Move.getMove(3, 2));
		// f3
		tiger.add(Move.getMove(2, 5));
		// f4
		tiger.add(Move.getMove(3, 5));
		// e3
		tiger.add(Move.getMove(2, 4));
		return tiger;
	}

	/**
	 * Returns the moves of the RoseOpening in South direction 
	 * 
	 * @return MoveList of Moves of the RoseOpening 
	 */
	private static MoveList<Move> initRoseOpeningSouth() {
		MoveList<Move> rose = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f4
		rose.add(Move.getMove(3, 5));
		// d3
		rose.add(Move.getMove(2, 3));
		// c4
		rose.add(Move.getMove(3, 2));
		// f5
		rose.add(Move.getMove(4, 5));
		// e6
		rose.add(Move.getMove(5, 4));
		// c3
		rose.add(Move.getMove(2, 2));
		// d6
		rose.add(Move.getMove(5, 3));
		// f3
		rose.add(Move.getMove(2, 5));
		// e3
		rose.add(Move.getMove(2, 4));
		// d2
		rose.add(Move.getMove(1, 3));
		return rose;
	}
	
	/**
	 * Returns the moves of the RoseOpening in East direction 
	 * 
	 * @return MoveList of Moves of the RoseOpening 
	 */
	private static MoveList<Move> initRoseOpeningEast() {
		MoveList<Move> rose = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// e3
		rose.add(Move.getMove(2, 4));
		// f5
		rose.add(Move.getMove(3, 5));
		// e6
		rose.add(Move.getMove(5, 4));
		// d3
		rose.add(Move.getMove(2, 3));
		// c4
		rose.add(Move.getMove(3, 2));
		// f6
		rose.add(Move.getMove(5, 5));
		// c5
		rose.add(Move.getMove(4, 2));
		// f3
		rose.add(Move.getMove(2, 5));
		// f4
		rose.add(Move.getMove(3, 5));
		// g5
		rose.add(Move.getMove(4, 6));
		return rose;
	}
	
	/**
	 * Returns the moves of the RoseOpening in North direction 
	 * 
	 * @return MoveList of Moves of the RoseOpening 
	 */
	private static MoveList<Move> initRoseOpeningNorth() {
		MoveList<Move> rose = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// c5
		rose.add(Move.getMove(4, 2));
		// e6
		rose.add(Move.getMove(5, 4));
		// f5
		rose.add(Move.getMove(4, 5));
		// c4
		rose.add(Move.getMove(3, 2));
		// d3
		rose.add(Move.getMove(2, 3));
		// f6
		rose.add(Move.getMove(5, 5));
		// e3
		rose.add(Move.getMove(2, 4));
		// c6
		rose.add(Move.getMove(5, 2));
		// d6
		rose.add(Move.getMove(5, 3));
		// e7
		rose.add(Move.getMove(6, 4));
		return rose;
	}
	
	/**
	 * Returns the moves of the RoseOpening in West direction 
	 * 
	 * @return MoveList of Moves of the RoseOpening 
	 */
	private static MoveList<Move> initRoseOpeningWest() {
		MoveList<Move> rose = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// d6
		rose.add(Move.getMove(5, 3));
		// c4
		rose.add(Move.getMove(3, 2));
		// d3
		rose.add(Move.getMove(2, 3));
		// e6
		rose.add(Move.getMove(5, 4));
		// f5
		rose.add(Move.getMove(4, 5));
		// c3
		rose.add(Move.getMove(2, 2));
		// f4
		rose.add(Move.getMove(3, 5));
		// c6
		rose.add(Move.getMove(5, 2));
		// c5
		rose.add(Move.getMove(4, 2));
		// b4
		rose.add(Move.getMove(3, 1));
		return rose;
	}
	
	/**
	 * Returns the moves of the BuffaloOpening in South direction 
	 * 
	 * @return MoveList of Moves of the BuffaloOpening 
	 */
	private static MoveList<Move> initBuffaloOpeningSouth() {
		MoveList<Move> buffalo = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f4
		buffalo.add(Move.getMove(3, 5));
		// f3
		buffalo.add(Move.getMove(2, 5));
		// e3
		buffalo.add(Move.getMove(2, 4));
		// f5
		buffalo.add(Move.getMove(4, 5));
		// c6
		buffalo.add(Move.getMove(5, 2));
		return buffalo;
	}
	
	/**
	 * Returns the moves of the BuffaloOpening in East direction 
	 * 
	 * @return MoveList of Moves of the BuffaloOpening 
	 */
	private static MoveList<Move> initBuffaloOpeningEast() {
		MoveList<Move> buffalo = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// e3
		buffalo.add(Move.getMove(2, 4));
		// f3
		buffalo.add(Move.getMove(2, 5));
		// f4
		buffalo.add(Move.getMove(3, 5));
		// d3
		buffalo.add(Move.getMove(2, 3));
		// c6
		buffalo.add(Move.getMove(5, 2));
		return buffalo;
	}
	
	/**
	 * Returns the moves of the BuffaloOpening in North direction 
	 * 
	 * @return MoveList of Moves of the BuffaloOpening 
	 */
	private static MoveList<Move> initBuffaloOpeningNorth() {
		MoveList<Move> buffalo = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// c5
		buffalo.add(Move.getMove(4, 2));
		// c6
		buffalo.add(Move.getMove(5, 2));
		// d6
		buffalo.add(Move.getMove(5, 3));
		// c4
		buffalo.add(Move.getMove(3, 2));
		// f3
		buffalo.add(Move.getMove(2, 5));
		return buffalo;
	}
	
	/**
	 * Returns the moves of the BuffaloOpening in West direction 
	 * 
	 * @return MoveList of Moves of the BuffaloOpening 
	 */
	private static MoveList<Move> initBuffaloOpeningWest() {
		MoveList<Move> buffalo = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// d6
		buffalo.add(Move.getMove(5, 3));
		// c6
		buffalo.add(Move.getMove(5, 2));
		// c5
		buffalo.add(Move.getMove(4, 2));
		// e6
		buffalo.add(Move.getMove(5, 4));
		// f3
		buffalo.add(Move.getMove(2, 5));
		return buffalo;
	}

	/**
	 * Returns the moves of the HeathOpening in South direction 
	 * 
	 * @return MoveList of Moves of the HeathOpening 
	 */
	private static MoveList<Move> initHeathOpeningSouth() {
		MoveList<Move> heath = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f4
		heath.add(Move.getMove(3, 5));
		// f3
		heath.add(Move.getMove(2, 5));
		// e3
		heath.add(Move.getMove(2, 4));
		// f5
		heath.add(Move.getMove(4, 5));
		// g4
		heath.add(Move.getMove(3, 6));
		return heath;
	}
	
	/**
	 * Returns the moves of the HeathOpening in East direction 
	 * 
	 * @return MoveList of Moves of the HeathOpening 
	 */
	private static MoveList<Move> initHeathOpeningEast() {
		MoveList<Move> heath = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// e3
		heath.add(Move.getMove(2, 4));
		// f3
		heath.add(Move.getMove(2, 5));
		// f4
		heath.add(Move.getMove(3, 5));
		// d3
		heath.add(Move.getMove(2, 4));
		// e2
		heath.add(Move.getMove(1, 4));
		return heath;
	}
	
	/**
	 * Returns the moves of the HeathOpening in North direction 
	 * 
	 * @return MoveList of Moves of the HeathOpening 
	 */
	private static MoveList<Move> initHeathOpeningNorth() {
		MoveList<Move> heath = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// c5
		heath.add(Move.getMove(4, 2));
		// c6
		heath.add(Move.getMove(5, 2));
		// d6
		heath.add(Move.getMove(5, 3));
		// c4
		heath.add(Move.getMove(3, 2));
		// b5
		heath.add(Move.getMove(4, 1));
		return heath;
	}
	
	/**
	 * Returns the moves of the HeathOpening in West direction 
	 * 
	 * @return MoveList of Moves of the HeathOpening 
	 */
	private static MoveList<Move> initHeathOpeningWest() {
		MoveList<Move> heath = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// d6
		heath.add(Move.getMove(5, 3));
		// c4
		heath.add(Move.getMove(3, 2));
		// c5
		heath.add(Move.getMove(4, 2));
		// e6
		heath.add(Move.getMove(5, 4));
		// d7
		heath.add(Move.getMove(6, 3));
		return heath;
	}

	/**
	 * Returns the moves of the InoueOpening in South direction 
	 * 
	 * @return MoveList of Moves of the InoueOpening 
	 */
	private static MoveList<Move> initInoueOpeningSouth() {
		MoveList<Move> inoue = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f4
		inoue.add(Move.getMove(3, 5));
		// d3
		inoue.add(Move.getMove(2, 3));
		// c4
		inoue.add(Move.getMove(3, 2));
		// f5
		inoue.add(Move.getMove(4, 5));
		// e6
		inoue.add(Move.getMove(5, 4));
		// c3
		inoue.add(Move.getMove(2, 2));
		// e3
		inoue.add(Move.getMove(2, 4));
		return inoue;
	}

	/**
	 * Returns the moves of the InoueOpening in East direction 
	 * 
	 * @return MoveList of Moves of the InoueOpening 
	 */
	private static MoveList<Move> initInoueOpeningEast() {
		MoveList<Move> inoue = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// e3
		inoue.add(Move.getMove(2, 4));
		// f5
		inoue.add(Move.getMove(4, 5));
		// e6
		inoue.add(Move.getMove(5, 4));
		// d3
		inoue.add(Move.getMove(2, 3));
		// c4
		inoue.add(Move.getMove(3, 2));
		// f6
		inoue.add(Move.getMove(5, 5));
		// f4
		inoue.add(Move.getMove(3, 5));
		return inoue;
	}
	
	/**
	 * Returns the moves of the InoueOpening in North direction 
	 * 
	 * @return MoveList of Moves of the InoueOpening 
	 */
	private static MoveList<Move> initInoueOpeningNorth() {
		MoveList<Move> inoue = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// c5
		inoue.add(Move.getMove(4, 2));
		// e6
		inoue.add(Move.getMove(5, 4));
		// f5
		inoue.add(Move.getMove(4, 5));
		// c4
		inoue.add(Move.getMove(3, 2));
		// d3
		inoue.add(Move.getMove(2, 3));
		// f6
		inoue.add(Move.getMove(5, 5));
		// d6
		inoue.add(Move.getMove(5, 3));
		return inoue;
	}
	
	/**
	 * Returns the moves of the InoueOpening in West direction 
	 * 
	 * @return MoveList of Moves of the InoueOpening 
	 */
	private static MoveList<Move> initInoueOpeningWest() {
		MoveList<Move> inoue = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// d6
		inoue.add(Move.getMove(5, 3));
		// c6
		inoue.add(Move.getMove(5, 2));
		// d3
		inoue.add(Move.getMove(2, 3));
		// e6
		inoue.add(Move.getMove(5, 4));
		// f5
		inoue.add(Move.getMove(4, 5));
		// c3
		inoue.add(Move.getMove(2, 2));
		// c5
		inoue.add(Move.getMove(4, 2));
		return inoue;
	}
	
	/**
	 * Returns the moves of the ShamanOpening in South direction 
	 * 
	 * @return MoveList of Moves of the ShamanOpening 
	 */
	private static MoveList<Move> initShamanOpeningSouth() {
		MoveList<Move> shaman = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// f4
		shaman.add(Move.getMove(3, 5));
		// d3
		shaman.add(Move.getMove(2, 3));
		// c4
		shaman.add(Move.getMove(3, 2));
		// f5
		shaman.add(Move.getMove(4, 5));
		// e6
		shaman.add(Move.getMove(5, 4));
		// c3
		shaman.add(Move.getMove(2, 2));
		// f6
		shaman.add(Move.getMove(5, 5));
		return shaman;
	}
	
	/**
	 * Returns the moves of the ShamanOpening in East direction 
	 * 
	 * @return MoveList of Moves of the ShamanOpening 
	 */
	private static MoveList<Move> initShamanOpeningEast() {
		MoveList<Move> shaman = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// e3
		shaman.add(Move.getMove(2, 4));
		// f5
		shaman.add(Move.getMove(4, 5));
		// e6
		shaman.add(Move.getMove(5, 4));
		// d3
		shaman.add(Move.getMove(2, 3));
		// c4
		shaman.add(Move.getMove(3, 2));
		// f6
		shaman.add(Move.getMove(5, 5));
		// c3
		shaman.add(Move.getMove(2, 2));
		return shaman;
	}
	
	/**
	 * Returns the moves of the ShamanOpening in North direction 
	 * 
	 * @return MoveList of Moves of the ShamanOpening 
	 */
	private static MoveList<Move> initShamanOpeningNorth() {
		MoveList<Move> shaman = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// c5
		shaman.add(Move.getMove(4, 2));
		// e6
		shaman.add(Move.getMove(5, 4));
		// f5
		shaman.add(Move.getMove(4, 5));
		// c4
		shaman.add(Move.getMove(3, 2));
		// d3
		shaman.add(Move.getMove(2, 3));
		// f6
		shaman.add(Move.getMove(5, 5));
		// c3
		shaman.add(Move.getMove(2, 2));
		return shaman;
	}
	
	/**
	 * Returns the moves of the ShamanOpening in West direction 
	 * 
	 * @return MoveList of Moves of the ShamanOpening 
	 */
	private static MoveList<Move> initShamanOpeningWest() {
		MoveList<Move> shaman = new MoveList<MoveList.Move>();
		// a:0 b:1 c:2 d:3 e:4 f:5 g:6 h:7
		// d6
		shaman.add(Move.getMove(5, 3));
		// c4
		shaman.add(Move.getMove(3, 2));
		// d3
		shaman.add(Move.getMove(2, 3));
		// e6
		shaman.add(Move.getMove(5, 4));
		// f5
		shaman.add(Move.getMove(4, 5));
		// c3
		shaman.add(Move.getMove(2, 2));
		// f6
		shaman.add(Move.getMove(5, 5));
		return shaman;
	}

}
