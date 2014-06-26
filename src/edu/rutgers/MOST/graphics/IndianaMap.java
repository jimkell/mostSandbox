package edu.rutgers.MOST.graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.event.MouseInputListener;
 
public class IndianaMap extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
DisplayCanvas canvas;
 
 
 
  public IndianaMap() {
	 super();
 
    Container container = getContentPane();
 
    canvas = new DisplayCanvas();
    container.add(canvas);
 
    JPanel panel = new JPanel();
    getContentPane().add(panel, BorderLayout.WEST);
 
    JButton ZoominButton = new JButton("+");
    ZoominButton.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		//canvas.increment();
 
    	}
    });
    ZoominButton.setHorizontalAlignment(SwingConstants.LEADING);
    ZoominButton.setVerticalAlignment(SwingConstants.TOP);
    panel.add(ZoominButton);
 
    JButton ZoomoutButton = new JButton("--");
    ZoomoutButton.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		//canvas.decrement();
 
    	}
    });
    panel.add(ZoomoutButton);
 
 
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    setSize(1440, 900);
 
 
    setVisible(true);
  }
 
  public static void main(String arg[]) {
    new IndianaMap();
 
  }
}
 
class DisplayCanvas extends JPanel {
  int x, y, x1, y1;
  int sumdifferencex ;
  int ctr = 0 ;
  int dx, dy;
  int sumdifferencey;
  int height, width;
  int scale = 1;
  int xfinal, yfinal;
  IndianaMap im;
  BufferedImage bi;
  int lastx = 0;
  int lasty = 0;
 
 
  DisplayCanvas() {
 
    setBackground(Color.white);
    setSize(1200, 800);
    addMouseMotionListener(new MouseInputListener()
 
    {
 
            @Override
            public void mouseClicked(MouseEvent mea) {
 
            }
 
            @Override
            public void mousePressed(MouseEvent mea) {
 
             x = mea.getX();
             y = mea.getY();
             System.out.println("got here1");
            }
 
            @Override
            public void mouseReleased(MouseEvent me) {
            dx = x + lastx;
            dy = y + lasty;
 
            }
 
            @Override
            public void mouseEntered(MouseEvent me) {
 
            }
 
            @Override
            public void mouseExited(MouseEvent me) {
                dx = me.getX();
                dy = me.getY();
            }
 
 
            public void mouseDragged(MouseEvent me) {
                System.out.println("got here2");
                 dx = me.getX() - x;
                 dy = me.getY() - y;
                 repaint();
            }
 
            @Override
            public void mouseMoved(MouseEvent me) {
            }
        });
 
    Image image = getToolkit().getImage("etc/1.bmp");
 
    MediaTracker mt = new MediaTracker(this);
    mt.addImage(image, 1);
    try {
      mt.waitForAll();
    } catch (Exception e) {
      System.out.println("Exception while loading image.");
    }
 
//    if (image.getWidth(this) == -1) {
//      System.out.println("no gif file");
//      System.exit(0);
//    }
 
//    bi = new BufferedImage(image.getWidth(this), image.getHeight(this),
//        BufferedImage.TYPE_INT_ARGB);
//    Graphics2D big = bi.createGraphics();
 
  }
 
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;
    g2D.drawImage(bi, dx + lastx, dy + lasty, this);
  }
}
