package ch.i10a.reversi.gameplay;

import java.awt.Color;

import ch.i10a.reversi.gui.ReversiBoard;
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
	private static int doublePass;

	static {
		init();
	}

	public static void init() {
		activePlayer = new HumanPlayer();
		players = new PlayerAdapter[] {activePlayer, getPlayerBySettings()};
		passValue = 0;
		doublePass = 0;
	}

	/**
	 * gets the Player by reading out the SettingsConst File
	 * 
	 * 
	 * @return the player read out in the file
	 */
	static PlayerAdapter getPlayerBySettings() {
		int whiteProp = ReversiProperties.inst().getIntProperty(SettingsConst.PROP_KEY_WHITE);
		if (whiteProp == SettingsConst.PROP_VALUE_HUMAN) {
			return new HumanPlayer(Color.BLACK, 1);
		} else {
			return new ComputerPlayer(Color.BLACK, 1);
		}
	}

	/**
	 * sets the board for the players (the board has to be known by the players)
	 * 
	 * @param board to register
	 */
	public static void registerBoard(ReversiBoard board) {
		for (int i = 0; i < players.length; i++) {
			players[i].setBoard(board);
		}
	}

	/**
	 * getter for the active PlayerAdapter
	 * 
	 * @return activePlayer PlayerAdapter
	 */
	public static PlayerAdapter getActivePlayer() {
		return activePlayer;
	}

	/**
	 * method to switch the player
	 * 
	 */
	public static void nextPlayer() {
		activePlayer = getNextPlayer();
	}
	
	/**
	 * getter for the player who is on next
	 * 
	 * @return the next player (PlayerAdapter)
	 */
	public static PlayerAdapter getNextPlayer() {
		if (activePlayer.getColor() == Color.WHITE) {
			return players[1];
		} else {
			return players[0];
		}
	}

	/**
	 * getter for the player who is on next
	 * 
	 * @return the next player (PlayerAdapter)
	 */
	public static PlayerAdapter getOtherPlayer(PlayerAdapter player) {
		return (player == players[0]) ? players[1] : players[0];
	}
	
	/**
	 * getter for the white player
	 * 
	 * @return the white player (PlayerAdapter)
	 */
	public static PlayerAdapter getWhitePlayer() {
		if (players[0].getColor() == Color.WHITE) {
			return players[0];
		} else {
			return players[1];
		}
	}
	
	/**
	 * getter for the black player
	 * 
	 * @return the black player (PlayerAdapter)
	 */
	public static PlayerAdapter getBlackPlayer() {
		if (players[0].getColor() == Color.BLACK) {
			return players[0];
		} else {
			return players[1];
		}
	}

	/**
	 * sets the passValue to the active player (determining who has passed)
	 */
	public static void setPass(){
		passValue = activePlayer.getValue();
	}
	
	/**
	 * resets the passValue to 0 (equals: no one has passed)
	 * 
	 * @return the white player (PlayerAdapter)
	 */
	public static void setUnPass(){
		passValue = 0;
	}
	
	/**
	 * getter for who has passed
	 * 
	 * @return int value representation of the passing player
	 */
	public static int getPass(){
		return passValue;
	}

	/**
	 * sets the stones value for both players, one with adding the stones, the other by subtracting the stones
	 * 
	 * @param integer of how many stones where beaten
	 */
	public static void updateStones(int count) {
		activePlayer.updateStonesCount(count);
		// "- 1" because one stones was setted by the player
		getNextPlayer().updateStonesCount(-(count - 1));
	}
	
	/**
	 * setter for the double Pass value (if the double pass happens, the game is over
	 * 
	 */
	public static void setDoublePass(){
		doublePass = 1;
	}
	
	/**
	 * checks if the doublePass value is set, if yes, it returns true
	 * 
	 * @return true, if a double pass happened
	 */
	public static boolean checkDoublePass(){
		if(doublePass > 0){
			return true;
		}
		else{
			return false;
		}
		
	}
}
