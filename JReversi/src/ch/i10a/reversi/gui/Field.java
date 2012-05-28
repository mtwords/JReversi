package ch.i10a.reversi.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import ch.i10a.reversi.gameplay.MoveList.Move;

public class Field extends JPanel implements ActionListener {

	protected int value;
	protected int rowNum;
	protected int colNum;
	protected ArrayList<Field> possibleHits = new ArrayList<Field>();

	public Field(int value, int rowNum, int colNum) {
		setValue(value);
		setRowNum(rowNum);
		setColNum(colNum);
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public ArrayList<Field> getPossibleHits() {
		return possibleHits;
	}
	public void setPossibleHits(ArrayList<Field> possibleHits) {
		this.possibleHits = possibleHits;
	}

	public boolean isAnimating() {
		return false;
	}

	@Override
	public Field clone() {
		Field clone = new Field(value, rowNum, colNum);
//		if (!possibleHits.isEmpty()) {
//			ArrayList<Field> possibleHitsClone = new ArrayList<Field>();
//			System.out.println(possibleHits.size());
//			for (Field field : possibleHits) {
//				possibleHitsClone.add(field.clone());
//			}
//			clone.setPossibleHits(possibleHitsClone);
//		}
		return clone;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Field:\n");
		sb.append("value: " + value);
		sb.append("\n");
		sb.append("col: " + colNum);
		sb.append("\n");
		sb.append("row: " + rowNum);
		sb.append("\n");
		sb.append("coord: " + Move.getMove(rowNum, colNum));
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// do nothing
	}

}
