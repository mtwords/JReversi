package ch.i10a.reversi.gameplay;

import java.awt.Color;

public class HumanPlayer extends PlayerAdapter {

	private Color color = null;
	private int value = -1;

	/**
	 * Default color of a human player is white 
	 */
	public HumanPlayer() {
		this(Color.WHITE, -1);
	}
	
	/**
	 * If a human player has to be set up differently, use this constructor
	 * 
	 * @param Color color: the color to give the human player, value: the field representation value for the human player
	 */
	public HumanPlayer(Color color, int value) {
		this.color = color;
		this.value = value;
	}

	
	/**
	 * The human move method doesn't need to do anything, the human player moves himself
	 * mainly here for inheritance reasons
	 * 
	 */
	@Override
	public void move() {
		// waiting for human to move
	}

	/**
	 * Returns the color of the human player (normally white) 
	 * @return Color of the human player
	 */
	@Override
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns the field value representation of the human player 
	 * @return integer representation of human player
	 */
	public int getValue() {
		return value;
	}

}
