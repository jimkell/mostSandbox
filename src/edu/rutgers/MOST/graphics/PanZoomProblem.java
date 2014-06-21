package edu.rutgers.MOST.graphics;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class PanZoomProblem extends JPanel {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String imagePath =
            "http://nationalmap.gov/ustopo/UST_slideshow/columbia_bottom/"+
            "images/MO_Columbia_Bottom_20120126_TM_ImageOff_thumb.jpg";
             
    private static Image image;
     
    private double currentX;
    private double currentY;
    private double previousX;
    private double previousY;
    private double zoom = 1;
     
    public static void main(String[] args) throws Exception {
         
        image = ImageIO.read(new URL(imagePath));
         
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new PanZoomProblem());
                frame.setSize(640, 480);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
         
    }
     
    public PanZoomProblem() {
         
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                previousX = e.getX();
                previousY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                 
                // Determine the old and new mouse coordinates based on the translated coordinate space.
                Point2D adjPreviousPoint = getTranslatedPoint(previousX, previousY);
                Point2D adjNewPoint = getTranslatedPoint(e.getX(), e.getY());
                 
                double newX = adjNewPoint.getX() - adjPreviousPoint.getX();
                double newY = adjNewPoint.getY() - adjPreviousPoint.getY();
 
                previousX = e.getX();
                previousY = e.getY();
                 
                currentX += newX;
                currentY += newY;
                 
                repaint();
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                    incrementZoom(.1 * -(double)e.getWheelRotation());
                }
            }
        });
         
        setOpaque(false);
         
    }
     
    private void incrementZoom(double amount) {
         
        zoom += amount;
        zoom = Math.max(0.00001, zoom);
        repaint();
         
    }
     
    public void paintComponent(Graphics g) {
         
        Graphics2D g2d = (Graphics2D)g.create();
        AffineTransform tx = getCurrentTransform();
         
        g2d.drawImage(image, tx, this);
        g2d.dispose();
         
    }
     
    private AffineTransform getCurrentTransform() {
         
        AffineTransform tx = new AffineTransform();
         
        double centerX = (double)getWidth() / 2;
        double centerY = (double)getHeight() / 2;
         
        tx.translate(centerX, centerY);
        tx.scale(zoom, zoom);
        tx.translate(currentX, currentY);
         
        return tx;
         
    }
     
    // Convert the panel coordinates into the cooresponding coordinates on the translated image.
    private Point2D getTranslatedPoint(double panelX, double panelY) {
         
        AffineTransform tx = getCurrentTransform();
        Point2D point2d = new Point2D.Double(panelX, panelY);
        try {
            return tx.inverseTransform(point2d, null);
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
            return null;
        }
         
    }
     
}
