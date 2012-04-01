package ch.i10a.reversi.gameplay;

import java.awt.Color;

/**
 * This class manages the players on the board.
 * It knows the active player and handles the changement of it. 
 *
 */
public class PlayerManager {

	private static PlayerI[] players;
	private static PlayerI activePlayer;

	static {
		activePlayer = new HumanPlayer();
		players = new PlayerI[] {activePlayer, new HumanPlayer(Color.BLACK, 1)};
	}

	public static PlayerI getActivePlayer() {
		return activePlayer;
	}

	public static void nextPlayer() {
		if (activePlayer.getColor() == Color.WHITE) {
			activePlayer = players[1];
		} else {
			activePlayer = players[0];
		}
	}

}
