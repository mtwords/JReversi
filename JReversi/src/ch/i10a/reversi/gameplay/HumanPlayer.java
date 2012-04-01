package ch.i10a.reversi.gameplay;

import java.awt.Color;

public class HumanPlayer implements PlayerI {

	private Color color = null;

	/**
	 * Default color of a human player is white 
	 */
	public HumanPlayer() {
		this(Color.WHITE);
	}
	public HumanPlayer(Color color) {
		this.color = color;
	}

	@Override
	public void move() {
		// waiting for human to move
	}

	@Override
	public Color getColor() {
		return color;
	}
}
