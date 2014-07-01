package edu.rutgers.MOST.graphics;

/* Adapted from code posted by R.J. Lorimer in an articleentitled "Java2D: Have Fun With Affine
Transform". The original post and code can be found 
at http://www.javalobby.org/java/forums/t19387.html.
*/

// actually from web.eecs.utk.edu/~bvz/gui/notes/transforms/PanAndZoom.java

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;

public class PanZoom {

	PanAndZoomCanvas canvas;
	AffineTransform at;   // the current pan and zoom transform
	Point2D XFormedPoint; // storage for a transformed mouse point
	
	Button plus;
	Button minus;
	int zoomExponent = 5;

	public PanZoom() {
		JFrame frame = new JFrame();
		canvas = new PanAndZoomCanvas();
		PanningHandler panner = new PanningHandler();
		canvas.addMouseListener(panner);
		canvas.addMouseMotionListener(panner);
		canvas.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel buttonPanel = new JPanel();
		plus = new Button("+");
		minus = new Button("-");
		plus.addActionListener(buttonActionListener);
		minus.addActionListener(buttonActionListener);
		buttonPanel.add(plus);
		buttonPanel.add(minus);

		frame.setTitle(GraphicalInterfaceConstants.TITLE);
		// Add the components to the canvas
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		// add window closing listener
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}

	class PanAndZoomCanvas extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		double translateX;
		double translateY;

		PanAndZoomCanvas() {
			translateX = 0;
			translateY = 0;
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
			int zoom = (int) Math.pow(2.0, zoomExponent);
			ourGraphics.drawRect(50, 50, 5*zoom, 5*zoom);

			// make sure you restore the original transform or else the drawing
			// of borders and other components might be messed up
			ourGraphics.setTransform(saveTransform);
		}
		public Dimension getPreferredSize() {
			return new Dimension(500, 500);
		}
	}

	class PanningHandler implements MouseListener,
	MouseMotionListener {
		double referenceX;
		double referenceY;
		// saves the initial transform at the beginning of the pan interaction
		AffineTransform initialTransform;

		// capture the starting point 
		public void mousePressed(MouseEvent e) {

			// first transform the mouse point to the pan and zoom
			// coordinates
			try {
				XFormedPoint = at.inverseTransform(e.getPoint(), null);
			}
			catch (NoninvertibleTransformException te) {
				System.out.println(te);
			}

			// save the transformed starting point and the initial
			// transform
			referenceX = XFormedPoint.getX();
			referenceY = XFormedPoint.getY();
			initialTransform = at;
		}

		public void mouseDragged(MouseEvent e) {

			// first transform the mouse point to the pan and zoom
			// coordinates. We must take care to transform by the
			// initial tranform, not the updated transform, so that
			// both the initial reference point and all subsequent
			// reference points are measured against the same origin.
			try {
				XFormedPoint = initialTransform.inverseTransform(e.getPoint(), null);
			}
			catch (NoninvertibleTransformException te) {
				System.out.println(te);
			}

			// the size of the pan translations 
			// are defined by the current mouse location subtracted
			// from the reference location
			double deltaX = XFormedPoint.getX() - referenceX;
			double deltaY = XFormedPoint.getY() - referenceY;

			// make the reference point be the new mouse point. 
			referenceX = XFormedPoint.getX();
			referenceY = XFormedPoint.getY();

			canvas.translateX += deltaX;
			canvas.translateY += deltaY;

			// schedule a repaint.
			canvas.repaint();
		}

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	ActionListener buttonActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent event) {	
			if(event.getSource() == plus) { 
				if (zoomExponent == GraphicsConstants.MIN_ZOOM_EXPONENT) {
					minus.setEnabled(true);
				}
				if (zoomExponent < GraphicsConstants.MAX_ZOOM_EXPONENT) {
					zoomExponent += 1;
				} else {
					plus.setEnabled(false);
				}
				System.out.println(zoomExponent);
				canvas.repaint();
			}
			else if(event.getSource() == minus) {
				if (zoomExponent == GraphicsConstants.MAX_ZOOM_EXPONENT) {
					plus.setEnabled(true);
				}
				if (zoomExponent > GraphicsConstants.MIN_ZOOM_EXPONENT) {
					zoomExponent -= 1;
				} else {
					minus.setEnabled(false);
				}
				System.out.println(zoomExponent);
				canvas.repaint();
			}
		}
	};
	
	public static void main(String[] args) {
		// Create a frame
		PanZoom frame = new PanZoom();
	}
}

