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
	public ComputerPlayer(Color color, int value) {
		this.color = color;
		this.value = value;
	}

	@Override
	public void move() {
		Field bestMovableField = null;
		if (ReversiProperties.inst().getIntProperty("difficulty") == SettingsConst.PROP_VALUE_EASY){
			bestMovableField = MoveHandler.getRandomMove();
		}
		if (ReversiProperties.inst().getIntProperty("difficulty") == SettingsConst.PROP_VALUE_MEDIUM){
			bestMovableField = MoveHandler.getBestMove();
		}

		if (bestMovableField != null) {
			getBoard().doMove(bestMovableField);
			getBoard().getMoves().add(Move.getMove(bestMovableField.getRowNum(), bestMovableField.getColNum()));
		}
	}

	@Override
	public Color getColor() {
		return color;
	}
	public int getValue() {
		return value;
	}
}
