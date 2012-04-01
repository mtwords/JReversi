package ch.i10a.reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * JPanel that represents a Field of the Reversi board.
 */
public class Field extends JPanel {

	public static final int WIDTH = 50;

	private int value;
	private int rowNum;
	private int colNum;

	public Field(int value, int rowNum, int colNum) {
		this.value = value;
		this.rowNum = rowNum;
		this.colNum = colNum;
		setBackground(Color.GREEN);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setSize(new Dimension(WIDTH, WIDTH));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (value < 0) {
			g.setColor(Color.BLACK);
			g.drawOval(4, 4, 40, 40);
			g.setColor(Color.WHITE);
			g.fillOval(4, 4, 40, 40);
		} else if (value > 0) {
			g.setColor(Color.BLACK);
			g.fillOval(4, 4, 40, 40);
		}
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
	public int getColNum() {
		return colNum;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Field:\n");
		sb.append("value: " + value);
		sb.append("\n");
		sb.append("col: " + colNum);
		sb.append("\n");
		sb.append("row: " + rowNum);
		return sb.toString();
	}
}
