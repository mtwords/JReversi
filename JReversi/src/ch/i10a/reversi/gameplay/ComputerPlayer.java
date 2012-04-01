package ch.i10a.reversi.gameplay;

import java.awt.Color;


public class ComputerPlayer implements PlayerI {

	private Color color = null;
	private int value = 1;

	/**
	 * Default color of a computer player is black
	 */
	public ComputerPlayer() {
		this(Color.BLACK);
	}
	public ComputerPlayer(Color color) {
		this.color = color;
	}

	@Override
	public void move() {
		// TODO implement!
		// ...
		PlayerManager.nextPlayer();
	}

	@Override
	public Color getColor() {
		return color;
	}
	public int getValue() {
		return value;
	}
}
