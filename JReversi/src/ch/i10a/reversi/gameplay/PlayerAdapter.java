package ch.i10a.reversi.gameplay;


public abstract class PlayerAdapter implements PlayerI {

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

}
