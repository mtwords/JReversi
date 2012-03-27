package ch.i10a.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JPanel;

/**
 * Starter class of the game. It is implemented as a JApplet,
 * therefore it can be used in a browser.
 */
public class StarterApplet extends JApplet {

	Board board = new Board();

	@Override
	public void init() {
		super.init();
		add(new LetterPane(), BorderLayout.NORTH);
		add(new CipherPane(), BorderLayout.WEST);
		add(board, BorderLayout.CENTER);
	}

	/**
	 * Overridden to set a fix size. Could be removed if dynamic size
	 * is supported.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setSize(new Dimension(25 + (8 * Field.WIDTH), 25 + (8 * Field.WIDTH)));
	}

	// --------------- inner classes ------------------
	/**
	 * A JPanel, which can be used to name the a - h Fields.
	 * By now it is set fixed as horizontal.
	 */
	private class LetterPane extends JPanel {

		public LetterPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setSize(new Dimension(8 * 50, 25));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			add(GuiUtil.getLabel("", 25, 25));
			add(GuiUtil.getXOrientedFieldLabel("a"));
			add(GuiUtil.getXOrientedFieldLabel("b"));
			add(GuiUtil.getXOrientedFieldLabel("c"));
			add(GuiUtil.getXOrientedFieldLabel("d"));
			add(GuiUtil.getXOrientedFieldLabel("e"));
			add(GuiUtil.getXOrientedFieldLabel("f"));
			add(GuiUtil.getXOrientedFieldLabel("g"));
			add(GuiUtil.getXOrientedFieldLabel("h"));
		}

	}

	/**
	 * A JPanel, which can be used to name the 1 - 8 Fields.
	 * By now it is set fixed as vertical.
	 */
	private class CipherPane extends JPanel {

		public CipherPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(25, 8 * 50));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			add(GuiUtil.getYOrientedFieldLabel("1"));
			add(GuiUtil.getYOrientedFieldLabel("2"));
			add(GuiUtil.getYOrientedFieldLabel("3"));
			add(GuiUtil.getYOrientedFieldLabel("4"));
			add(GuiUtil.getYOrientedFieldLabel("5"));
			add(GuiUtil.getYOrientedFieldLabel("6"));
			add(GuiUtil.getYOrientedFieldLabel("7"));
			add(GuiUtil.getYOrientedFieldLabel("8"));
		}

	}
}
