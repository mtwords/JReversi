package ch.i10a.reversi.gameplay;

import java.awt.Color;

/**
 * Interface representing a player class.
 * 
 * @see HumanPlayer
 * @see ComputerPlayer
 */
public interface PlayerI {

	public void move();

	public Color getColor();

}
