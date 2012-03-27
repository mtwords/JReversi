package ch.i10a.reversi.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * A JPanel which represents the Reversi board.
 * It is initialized with 8 x 8 Fields.
 */
public class Board extends JPanel {

	private Field[][] fields;

	public Board() {
		initFields();
		initComponents();
	}

	private void initFields() {
		fields = new Field[8][8];
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
					fields[j][i] = new Field(-1);
				} else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
					fields[j][i] = new Field(1);
				} else {
					fields[j][i] = new Field(0);
				}
			}
		}
	}

	private void initComponents() {
		setLayout(new GridLayout(8, 8));
		setSize(new Dimension(8 * Field.WIDTH, 8 * Field.WIDTH));
		setPreferredSize(getSize());
		setMaximumSize(getSize());
		setMinimumSize(getSize());

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				add(fields[j][i]);
			}
		}
	}
}
