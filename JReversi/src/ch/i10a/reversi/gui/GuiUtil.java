package ch.i10a.reversi.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JLabel;

/**
 * A helper class with static methods. Could easily be 
 * used to generate periodic components such as labels...
 */
public class GuiUtil {

	/**
	 * Constructs a Field label with the given x alignment text and
	 * dynamic size dependent on the {@link ReversiField.WITH}
	 * @param label: the text to be displayed
	 * @return a JLabel with the given Text with Field-dependent size
	 */
	public static JLabel getXOrientedFieldLabel(String label) {
		return getLabel(label, ReversiField.WIDTH, (int) Math.round(ReversiField.WIDTH * 0.5));
	}
	/**
	 * Constructs a Field label with the given y alignment text and
	 * dynamic size dependent on the {@link ReversiField.WITH}
	 * @param label: the text to be displayed
	 * @return a JLabel with the given Text with Field-dependent size
	 */
	public static JLabel getYOrientedFieldLabel(String label) {
		return getLabel(label, (int) Math.round(ReversiField.WIDTH * 0.5), ReversiField.WIDTH);
	}
	/**
	 * Constructs a Field label with the given text and sizes
	 * @param label: the text to be displayed
	 * @return a JLabel with the given Text sizes
	 */
	public static JLabel getLabel(String label, int sizeX, int sizeY) {
		JLabel l = new JLabel(label);
		l.setSize(new Dimension(sizeX, sizeY));
		l.setPreferredSize(l.getSize());
		l.setMaximumSize(l.getSize());
		l.setMinimumSize(l.getSize());
		l.setHorizontalTextPosition(JLabel.CENTER);
		l.setHorizontalAlignment(JLabel.CENTER);
		return l;
	}

	public static void closeAncestorWindow(Component cmp) {
		Container parent = cmp.getParent();
		while (parent != null) {
			parent = parent.getParent();

			if (parent instanceof Window) {
				((Window) parent).setVisible(false);
				((Window) parent).dispose();
				return;
			}
		}

	}
	
}
