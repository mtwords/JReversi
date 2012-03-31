package ch.i10a.reversi.gui;

import java.awt.Dimension;

import javax.swing.JLabel;

/**
 * A helper class with static methods. Could easily be 
 * used to generate periodic components such as labels...
 */
public class GuiUtil {

	/**
	 * Constructs a Field label with the given x alignment text and
	 * dynamic size dependent on the {@link Field.WITH}
	 * @param label: the text to be displayed
	 * @return a JLabel with the given Text with Field-dependent size
	 */
	public static JLabel getXOrientedFieldLabel(String label) {
		return getLabel(label, Field.WIDTH, (int) Math.round(Field.WIDTH * 0.5));
	}
	/**
	 * Constructs a Field label with the given y alignment text and
	 * dynamic size dependent on the {@link Field.WITH}
	 * @param label: the text to be displayed
	 * @return a JLabel with the given Text with Field-dependent size
	 */
	public static JLabel getYOrientedFieldLabel(String label) {
		return getLabel(label, (int) Math.round(Field.WIDTH * 0.5), Field.WIDTH);
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
	
	
}
