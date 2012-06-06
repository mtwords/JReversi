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

	/**
	 * getter for the value of this field
	 * 
	 * @return int value representation of this field
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * setter for the fields in this field
	 * 
	 * @param int value representation of this field
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * getter for the row number of this field
	 * 
	 * @return int value representation of the row number
	 */
	public int getRowNum() {
		return rowNum;
	}
	
	/**
	 * setter for the row number in this field
	 * 
	 * @param int value representation of this row number
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	/**
	 * getter for the column number of this field
	 * 
	 * @return int value representation of the column number
	 */
	public int getColNum() {
		return colNum;
	}
	
	/**
	 * setter for the column number in this field
	 * 
	 * @param int value representation of this column number
	 */
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	/**
	 * getter for the possible Hits with this field
	 * 
	 * @return ArrayList of fields stored
	 */
	public ArrayList<Field> getPossibleHits() {
		return possibleHits;
	}
	
	/**
	 * setter for the possible Hits with this field
	 * 
	 * @param ArrayList of fields to store
	 */
	public void setPossibleHits(ArrayList<Field> possibleHits) {
		this.possibleHits = possibleHits;
	}

	/**
	 * checks if the field is animating (false, as this is a field for computing)
	 * 
	 * @return boolean false
	 */
	public boolean isAnimating() {
		return false;
	}

	/**
	 * clones this field to have a non cohesive object
	 * 
	 * @param clone is a field containing the same values as the original field
	 */
	@Override
	public Field clone() {
		Field clone = new Field(value, rowNum, colNum);
		clone.removeAll();
		clone.setLayout(null);

		return clone;
	}

	/**
	 * returns a string representation of this field
	 * 
	 * @return String stuffed with the values of this field
	 */
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

	/**
	 * No action is to perform as this is a field for computing
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// do nothing
	}

}
