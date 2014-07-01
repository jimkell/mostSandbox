package edu.rutgers.MOST.graphics;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

//from http://www.daniweb.com/software-development/java/threads/417811/how-to-clear-a-canvas
public class DrawCanvas extends Canvas implements MouseListener,
MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage image;  
	private int zoomExponent = 5;
	
	public int getZoomExponent() {
		return zoomExponent;
	}
	public void setZoomExponent(int zoomExponent) {
		this.zoomExponent = zoomExponent;
	}
	public DrawCanvas() {
		addMouseListener(this);
		addMouseMotionListener(this);
	} 
	
	public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(400, 30, 400, 105);
	}
	/**
	 * Implementing MouseListener
	 */
	public void mousePressed(MouseEvent event) {
		
	}
	public void mouseReleased(MouseEvent event) {
		
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	/**
	 * Implementing MouseMotionListener
	 */
	public void mouseDragged(MouseEvent event) {
		
	}
	public void mouseMoved(MouseEvent e) {}
}
