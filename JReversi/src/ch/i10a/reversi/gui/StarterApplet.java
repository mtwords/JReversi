package ch.i10a.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

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
		add(new GeneralInfoPane(), BorderLayout.EAST);
	}

	/**
	 * Overridden to set a fix size. Could be removed if dynamic size
	 * is supported.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setSize(new Dimension(325 + (8 * Field.WIDTH), 25 + (8 * Field.WIDTH)));
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
	
	/**
	 * A JPanel, which contains the two Informations for 
	 * the Players. The Player that's on has a Frame
	 * Around his Info Pane
	 */
	private class GeneralInfoPane extends JPanel {

		public GeneralInfoPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 4 * 50));
			setMaximumSize(getSize());
			setMinimumSize(getSize());

			add(new PlayerOneInfoPane(), BorderLayout.WEST); //Info Pane Player One
			add(GuiUtil.getLabel(" ", 300, 5)); //Empty Label Delimiter
			add(new PlayerTwoInfoPane(), BorderLayout.WEST); //Info Pane Player Two
			
		}

	}
	
	
	/**
	 * A JPanel, which contains Informations for 
	 * Player One (Black Player)
	 */
	private class PlayerOneInfoPane extends JPanel {

		public PlayerOneInfoPane() {
			initComponents();
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 2 * 50));
			setBackground(Color.lightGray);
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			add(GuiUtil.getLabel("Player 1",300, 1 * 25));
			
			
		}
		public void paint(Graphics g) {
			super.paint(g);
			
			//Put in a switch. If Player 2 is on, draw the Rectangle
			g.setColor(Color.RED);
			g.fillRect(0, 0, 50, 50);
			//end switch
			
			g.setColor(Color.BLACK);
			g.fillOval(4, 4, 40, 40);
			
		}
		

	}
	
	
	/**
	 * A JPanel, which contains Informations for 
	 * Player Two (White Player)
	 */
	private class PlayerTwoInfoPane extends JPanel {
		
		public PlayerTwoInfoPane() {
			initComponents();
			
		}

		private void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setSize(new Dimension(300, 2 * 50));
			setBackground(Color.lightGray);
			setMaximumSize(getSize());
			setMinimumSize(getSize());
			add(GuiUtil.getLabel("Player 2",300, 1 * 25));
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			
			//Put in a switch. If Player 2 is on, draw the Rectangle
			g.setColor(Color.RED);
			g.fillRect(0, 0, 50, 50);
			//end switch
			
			g.setColor(Color.BLACK);
			g.drawOval(4, 4, 40, 40);
			g.setColor(Color.WHITE);
			g.fillOval(4, 4, 40, 40);
			
			

			
			
		}

	}
	
	
	
}
