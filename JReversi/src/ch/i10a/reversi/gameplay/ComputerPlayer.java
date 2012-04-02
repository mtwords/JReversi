package ch.i10a.reversi.gameplay;

import java.awt.Color;


public class ComputerPlayer implements PlayerI {

	private Color color = null;
	private int value = 1;

	/**
	 * Default color of a computer player is black
	 */
	public ComputerPlayer() {
		this(Color.BLACK, 1);
	}
	public ComputerPlayer(Color color, int value) {
		this.color = color;
		this.value = value;
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
