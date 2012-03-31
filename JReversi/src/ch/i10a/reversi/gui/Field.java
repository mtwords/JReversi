package ch.i10a.reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * JPanel that represents a Field of the Reversi board.
 */
public class Field extends JPanel {

	public static final int WIDTH = 50;

	private int value;

	public Field(int value) {
		this.value = value;
		addMouseListener(new MouseListener());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Color.GREEN);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setSize(new Dimension(WIDTH, WIDTH));

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

	// ----------------- inner classes --------------------
	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			// TODO value = -1 / 1;
			System.out.println(value);
			
		}
	}
}
