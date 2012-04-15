package ch.i10a.reversi.gameplay;

import java.awt.Color;

import ch.i10a.reversi.settings.ReversiProperties;
import ch.i10a.reversi.settings.SettingsConst;

/**
 * This class manages the players on the board.
 * It knows the active player and handles the changement of it. 
 *
 */
public class PlayerManager {

	private static PlayerAdapter[] players;
	private static PlayerAdapter activePlayer;
	private static int passValue;

	static {
		activePlayer = new HumanPlayer();
		players = new PlayerAdapter[] {activePlayer, getPlayerBySettings()};
		passValue = 0;
	}

	static PlayerAdapter getPlayerBySettings() {
		int whiteProp = ReversiProperties.inst().getIntProperty(SettingsConst.PROP_KEY_WHITE);
		if (whiteProp == SettingsConst.PROP_VALUE_HUMAN) {
			return new HumanPlayer(Color.BLACK, 1);
		} else {
			return new ComputerPlayer(Color.BLACK, 1);
		}
	}

	public static PlayerAdapter getActivePlayer() {
		return activePlayer;
	}

	public static void nextPlayer() {
		activePlayer = getNextPlayer();
	}
	private static PlayerAdapter getNextPlayer() {
		if (activePlayer.getColor() == Color.WHITE) {
			return players[1];
		} else {
			return players[0];
		}
	}

	public static PlayerAdapter getWhitePlayer() {
		if (players[0].getColor() == Color.WHITE) {
			return players[0];
		} else {
			return players[1];
		}
	}
	public static PlayerAdapter getBlackPlayer() {
		if (players[0].getColor() == Color.BLACK) {
			return players[0];
		} else {
			return players[1];
		}
	}

	public static void setPass(){
		passValue = activePlayer.getValue();
	}
	
	public static void setUnPass(){
		passValue = 0;
	}
	
	public static int getPass(){
		return passValue;
	}

	public static void updateStones(int count) {
		activePlayer.updateStonesCount(count);
		// "- 1" because one stones was setted by the player
		getNextPlayer().updateStonesCount(-(count - 1));
	}
}
