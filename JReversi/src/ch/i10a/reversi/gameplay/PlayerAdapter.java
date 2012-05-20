package ch.i10a.reversi.gameplay;

import ch.i10a.reversi.gui.Board;

/**
 * Abstract Player class.
 * 
 * Useful for general Player data and functions, which doesn't
 * depend on the player itself.
 */
public abstract class PlayerAdapter implements PlayerI {

	private Board board;
	/** the game starts with 2 stones for each player **/
	private int stonesCount = 2;

	public int getStonesCount() {
		return stonesCount;
	}
	/**
	 * Updates the stones count by adding <code>count</code> to the actual
	 * number of stones owned by this player.
	 * @param count
	 */
	public void updateStonesCount(int count) {
		stonesCount += count;
	}
	/**
	 * Updates the stones count by setting the
	 * number of stones owned by this player.
	 * @param count
	 */
	public void setStonesCount(int count){
		stonesCount = count;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	public Board getBoard() {
		return board;
	}
}
