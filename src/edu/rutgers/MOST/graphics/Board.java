package edu.rutgers.MOST.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Board extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public Board(){
        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g){ 
    	Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        g2d.drawLine(400, 30, 400, 120);
        g2d.drawArc(500, 225, 50, 50, 90, 180);
        g2d.drawOval(300, 150, 200, 200); 
    } 
}
