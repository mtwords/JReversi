package ch.i10a.reversi.gameplay;

import java.awt.Color;

import ch.i10a.reversi.gameplay.MoveList.Move;
import ch.i10a.reversi.gui.Field;
import ch.i10a.reversi.settings.ReversiProperties;
import ch.i10a.reversi.settings.SettingsConst;


public class ComputerPlayer extends PlayerAdapter {

	private Color color = null;
	private int value = 1;

	/**
	 * Default color of a computer player is black
	 */
	public ComputerPlayer() {
		this(Color.BLACK, 1);
	}
	
	/**
	 * If a computer player has to be set up differently, use this constructor
	 * 
	 * @param Color color: the color to give the computer player, value: the field representation value for the computer player
	 */
	public ComputerPlayer(Color color, int value) {
		this.color = color;
		this.value = value;
	}

	/**
	 * here, the computer player makes his move. The move method to call from MoveHandler
	 * depends on the difficulty setting (easy, medium, hard).
	 * 
	 */
	@Override
	public void move() {
		Field bestMovableField = null;
		
		//if the difficulty is set to "easy", the getRandomMove method is chosen
		if (ReversiProperties.inst().getIntProperty("difficulty") == SettingsConst.PROP_VALUE_EASY){
			bestMovableField = MoveHandler.getRandomMove();
		}
		//if the difficulty is set to "medium", the getBestMove method is chosen with value false (medium calculation switch)
		if (ReversiProperties.inst().getIntProperty("difficulty") == SettingsConst.PROP_VALUE_MEDIUM){
			bestMovableField = MoveHandler.getBestMove(false);
		}
		//if the difficulty is set to "hard", the getBestMove method is chosen with value true (hard calculation switch)
		if (ReversiProperties.inst().getIntProperty("difficulty") == SettingsConst.PROP_VALUE_HARD){
			bestMovableField = MoveHandler.getBestMove(true);
		}

		//only if the methods return a field (e.g. not null), a move has to be done. if it returns null, the computer
		//player passes (passing realized elsewhere)
		if (bestMovableField != null) {
			getBoard().doMove(bestMovableField);
			getBoard().getMoves().add(Move.getMove(bestMovableField.getRowNum(), bestMovableField.getColNum()));
		}
	}

	/**
	 * Returns the color of the computer player (normally black)  
	 * @return Color of the computer player
	 */
	@Override
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns the field value representation of the computer player 
	 * @return integer representation of computer player
	 */
	public int getValue() {
		return value;
	}
}
