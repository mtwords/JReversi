package ch.i10a.reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ch.i10a.reversi.gameplay.PlayerManager;

/**
 * JPanel that represents a Field of the Reversi board.
 */
public class Field extends JPanel {

	public static final int WIDTH = 50;

	private int value;
	private int rowNum;
	private int colNum;

	// Graphic things
	static BufferedImage whiteStone;
	static BufferedImage blackStone;
	int x = 4;
	int y = 4;
	int width = blackStone.getWidth();
	int height = blackStone.getHeight();
	BufferedImage imageToPaint = whiteStone;

	static {
		try {
			blackStone = ImageIO.read(new File("images/black.gif"));
			whiteStone = ImageIO.read(new File("images/white.gif"));
		} catch (IOException e) {
			throw new RuntimeException("couldn't load image-files!", e);
		}
	}

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

		if (value == -1) {
			g.drawImage(whiteStone, x, y, width, height, null);
		} else if (value == 1) {
			g.drawImage(blackStone, x, y, width, height, null);
		} else if (Math.abs(value) == 2) {
			g.drawImage(imageToPaint, x, y, width, height, null);
		}

	}

	public void update() {
		Thread t = new Thread(new AnimationRunner(PlayerManager.getActivePlayer().getValue()));
		t.start();
	}

	private void chooseImageToPaint(int factor) {
		int activePlayerValue = PlayerManager.getActivePlayer().getValue();
		if (activePlayerValue * factor < 0) {
			imageToPaint = whiteStone;
		} else {
			imageToPaint = blackStone;
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

	// ----------------- inner classes --------------------
	private class AnimationRunner implements Runnable {

		int activePlayerValue;

		public AnimationRunner(int activePlayerValue) {
			this.activePlayerValue = activePlayerValue;
		}

		@Override
		public void run() {
			chooseImageToPaint(1);
			while (width != 0) {
				if (width % 2 == 0) {
					x += 1;
					y += 1;
				}
				width -= 1;
				height -= 1;
				repaint();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			chooseImageToPaint(-1);
			while (width != 40) {
				if (width % 2 == 0) {
					x -= 1;
					y -= 1;
				}
				width += 1;
				height += 1;
				repaint();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			setValue(activePlayerValue);
		}

	}
}
