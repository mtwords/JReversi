package ch.i10a.reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import ch.i10a.reversi.gameplay.MoveList.Move;
import ch.i10a.reversi.gameplay.PlayerManager;

/**
 * JPanel that represents a Field of the Reversi board.
 */
public class ReversiField extends Field implements ActionListener {

	public static final int WIDTH = 50;

	// Graphic things
	static BufferedImage whiteStone;
	static BufferedImage blackStone;
	int x = 4;
	int y = 4;
	int width = blackStone.getWidth();
	int height = blackStone.getHeight();
	BufferedImage imageToPaint = whiteStone;
	boolean shrinkAnimation = true;
	boolean animating = false;

	static {
		try {
			blackStone = ImageIO.read(new File("images/black.gif"));
			whiteStone = ImageIO.read(new File("images/white.gif"));
		} catch (IOException e) {
			throw new RuntimeException("couldn't load image-files!", e);
		}
	}

	public ReversiField(int value, int rowNum, int colNum) {
		super(value, rowNum, colNum);
		setBackground(Color.GREEN);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setSize(new Dimension(WIDTH, WIDTH));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int hitCount = possibleHits.size();
		if (hitCount > 0) {
			g.drawString("" + hitCount, 20, 30);
		}

		if (value == -1) {
			g.drawImage(whiteStone, x, y, width, height, null);
		} else if (value == 1) {
			g.drawImage(blackStone, x, y, width, height, null);
		} else if (Math.abs(value) == 2) {
			g.drawImage(imageToPaint, x, y, width, height, null);
		}

	}
	
	/**
	 * Updates the field with the value specified
	 */
	public void update(final int value) {
		Thread t = new Thread(new AnimationRunner(value));
		t.start();
	}
	
	/**
	 * here, the image to paint is chosen (update painting)
	 */
	private void chooseImageToPaint(int factor) {
		int activePlayerValue = PlayerManager.getActivePlayer().getValue();
		if (activePlayerValue * factor > 0) {
			imageToPaint = whiteStone;
		} else {
			imageToPaint = blackStone;
		}
	}
	
	/**
	 * Returns the value of this field
	 * 
	 * @return int value of the field 
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets given value on this field
	 * 
	 * @param int value representation of this field
	 */
	public void setValue(int value) {
		if (value < -1 || value > 1) {
			animating = true;
		}
		this.value = value;
	}
	
	/**
	 * Returns the row number of this field
	 * 
	 * @return int row number of the field 
	 */
	public int getRowNum() {
		return rowNum;
	}
	
	/**
	 * Returns the col number of this field
	 * 
	 * @return int col number of the field 
	 */
	public int getColNum() {
		return colNum;
	}
	
	/**
	 * Sets the possible hits that can be achieved by playing
	 * this field
	 * 
	 * @param an ArrayList of all possible hits
	 */
	public void setPossibleHits(ArrayList<Field> possibleHitsCount) {
		this.possibleHits = possibleHitsCount;
	}
	
	/**
	 * gets the possible hits that can be achieved by playing
	 * this field
	 * 
	 * @return an ArrayList of all possible hits
	 */
	public ArrayList<Field> getPossibleHits() {
		return possibleHits;
	}

	/**
	 * checks if this particular field is animating
	 * 
	 * @return true if it is animating, false if not
	 */
	public boolean isAnimating() {
		return animating;
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
	 * important actionPerformed method
	 * all the update animation takes place here
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (value == 0 || !animating) {
			return;
		}

		// use actual image
		chooseImageToPaint(1);
		// shrink
		if (width != 0 && shrinkAnimation) {
			shrinkStone();
			return;
		}
		shrinkAnimation = false;
		
		// use other image
		chooseImageToPaint(-1);
		if (width != 40) {
			growStone();
			return;
		}

		// reset
		shrinkAnimation = true;
		value = PlayerManager.getActivePlayer().getValue();
		animating = false;
	}

	/**
	 * method for animation that lets the stones shrink in a smooth manner
	 * 
	 */
	private void shrinkStone() {
		if (width % 2 == 0) {
			x += 1;
			y += 1;
		}
		width -= 1;
		height -= 1;
		repaint();
	}
	
	/**
	 * method for animation that lets the stones grow in a smooth manner
	 * 
	 */
	private void growStone() {
		if (width % 2 == 0) {
			x -= 1;
			y -= 1;
		}
		width += 1;
		height += 1;
		repaint();
	}

	// ----------------- inner classes --------------------
	private class AnimationRunner implements Runnable {

		int activePlayerValue;

		public AnimationRunner(int activePlayerValue) {
			this.activePlayerValue = activePlayerValue;
		}

		/**
		 * the run method that kicks out the animation for both black and white occupied fields
		 * 
		 */
		@Override
		public void run() {
			//black painting
			chooseImageToPaint(1);
			while (width != 0) {
				if (width % 2 == 0) {
					x += 1;
					y += 1;
				}
				width -= 1;
				height -= 1;
				repaint();

			}
			//white painting
			chooseImageToPaint(-1);
			while (width != 40) {
				if (width % 2 == 0) {
					x -= 1;
					y -= 1;
				}
				width += 1;
				height += 1;
				repaint();

			}

			setValue(activePlayerValue);

		}

	}
}
