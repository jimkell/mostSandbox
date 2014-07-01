package edu.rutgers.MOST.graphics;

import java.awt.*;
import java.awt.event.*;

//from http://www.daniweb.com/software-development/java/threads/417811/how-to-clear-a-canvas
public class DrawCanvas extends Canvas implements MouseListener,
MouseMotionListener {
	// Constants for shapes
	public static final int CIRCLE = 1;
	public static final int ROUNDED_RECTANGLE = 2;
	public static final int RECTANGLE_3D = 3;
	public static final int LINE = 4;
	public static final int OVAL = 5;
	public static final int SQUARE = 6;
	public static final int CLEAR = 7;
	// Coordinates of points to draw
	private int x1, y1, x2, y2;
	// shape to draw
	private int shape = CIRCLE;
	/**
	 * Method to set the shape
	 */
	public void setShape(int shape) {
		this.shape = shape;
	}
	// filled color
	private Color filledColor = null;
	/**
	 * Method to set filled color
	 */
	public void setFilledColor(Color color) {
		filledColor = color;
	}
	/**
	 * Constructor
	 */
	public DrawCanvas() {
		addMouseListener(this);
		addMouseMotionListener(this);
	} // end of constructor
	/**
	 * painting the component
	 */
	public void paint(Graphics g) {
		// the drawing area
		int x, y, width, height;
		// determine the upper-left corner of bounding rectangle
		x = Math.min(x1, x2);
		y = Math.min(y1, y2);
		// determine the width and height of bounding rectangle
		width = Math.abs(x1 - x2);
		height = Math.abs(y1 - y2);
		if(filledColor != null)
			g.setColor(filledColor);
		switch (shape) {
		case ROUNDED_RECTANGLE :
			if(filledColor == null)
				g.drawRoundRect(x, y, width, height, width/4, height/4);
			else
				g.fillRoundRect(x, y, width, height, width/4, height/4);
			break;
		case CIRCLE :
			int diameter = Math.max(width, height);
			if(filledColor == null)
				g.drawOval(x, y, diameter, diameter);
			else
				g.fillOval(x, y, diameter, diameter);
			break;
		case RECTANGLE_3D :
			if(filledColor == null)
				g.draw3DRect(x, y, width, height, true);
			else
				g.fill3DRect(x, y, width, height, true);
			break;
		case LINE :
			if(filledColor == null)
				g.drawLine(x1, y1, x2, y2);
			else
				g.drawLine(x1, y1, x2, y2);
			break;
		case OVAL :
			diameter = Math.max(width, height);
			if(filledColor == null)
				g.drawOval(x, y, width, height);
			else
				g.fillOval(x, y, width, height);
			break;
		case SQUARE :
			if(filledColor == null)
				g.drawRect(x, y, width, width);
			else
				g.fillRect(x, y, width, width);
			break;
		case CLEAR :
			//repaint();
			//g.clearRect(x, y, width, height);
			g.setColor(getBackground());
			g.clearRect(0, 0, width, height);
			break;
		}
	}
	/**
	 * Implementing MouseListener
	 */
	public void mousePressed(MouseEvent event) {
		x1 = event.getX();
		y1 = event.getY();
	}
	public void mouseReleased(MouseEvent event) {
		x2 = event.getX();
		y2 = event.getY();
		repaint();
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	/**
	 * Implementing MouseMotionListener
	 */
	public void mouseDragged(MouseEvent event) {
		x2 = event.getX();
		y2 = event.getY();
		repaint();
	}
	public void mouseMoved(MouseEvent e) {}
}
