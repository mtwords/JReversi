package ch.i10a.reversi.gameplay;

import java.awt.Color;

public class HumanPlayer implements PlayerI {

	private Color color = null;
	private int value = -1;

	/**
	 * Default color of a human player is white 
	 */
	public HumanPlayer() {
		color = Color.WHITE;
	}
	public HumanPlayer(Color color, int value) {
		this.color = color;
		this.value = value;
	}

	@Override
	public void move() {
		// waiting for human to move
	}

	@Override
	public Color getColor() {
		return color;
	}
	public int getValue() {
		return value;
	}

}
