package edu.rutgers.MOST.graphics;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import edu.rutgers.MOST.graphics.PanAndZoom.PanAndZoomCanvas;
import edu.rutgers.MOST.graphics.PanAndZoom.PanningHandler;

//from http://www.daniweb.com/software-development/java/threads/417811/how-to-clear-a-canvas
public class DrawCanvas extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	PanAndZoomCanvas canvas;
	AffineTransform at;   // the current pan and zoom transform
	Point2D XFormedPoint; // storage for a transformed mouse point
	
	private int zoomExponent = 5;
	
	public int getZoomExponent() {
		return zoomExponent;
	}
	public void setZoomExponent(int zoomExponent) {
		this.zoomExponent = zoomExponent;
	}
	
	public int translateX = 0;
	public int translateY = 0;
	
	public DrawCanvas() {
		
	} 
	
	public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(400, 30, 400, 105);
	}
	class PanAndZoomCanvas extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		double translateX;
		double translateY;
		double scale;

		PanAndZoomCanvas() {
			translateX = 0;
			translateY = 0;
			scale = 1;
		}

		public void paintComponent(Graphics g) {
			Graphics2D ourGraphics = (Graphics2D) g;
			// save the original transform so that we can restore
			// it later
			AffineTransform saveTransform = ourGraphics.getTransform();

			// blank the screen. If we do not call super.paintComponent, then
			// we need to blank it ourselves
			ourGraphics.setColor(Color.WHITE);
			ourGraphics.fillRect(0, 0, getWidth(), getHeight());

			// We need to add new transforms to the existing
			// transform, rather than creating a new transform from scratch.
			// If we create a transform from scratch, we will
			// will start from the upper left of a JFrame, 
			// rather than from the upper left of our component
			at = new AffineTransform(saveTransform);

			// The panning transformation
			at.translate(translateX, translateY);

			ourGraphics.setTransform(at);

			// draw the objects
			ourGraphics.setColor(Color.BLACK);
			ourGraphics.drawRect(50, 50, 50, 50);
			ourGraphics.fillOval(100, 100, 100, 100);
			ourGraphics.drawString("Test Affine Transform", 50, 30);

			// make sure you restore the original transform or else the drawing
			// of borders and other components might be messed up
			ourGraphics.setTransform(saveTransform);
		}
		public Dimension getPreferredSize() {
			return new Dimension(500, 500);
		}
	}
}
