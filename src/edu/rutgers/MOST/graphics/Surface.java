package edu.rutgers.MOST.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


class Surface extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(150, 150, 150));

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        g2d.drawLine(400, 30, 400, 120);
//        g2d.fillRect(30, 20, 50, 50);
//        g2d.fillRect(120, 20, 90, 60);
//        g2d.fillRoundRect(250, 20, 70, 60, 25, 25);
//
//        g2d.fill(new Ellipse2D.Double(10, 100, 80, 100));
        //g2d.fillArc(120, 130, 110, 100, 5, 150);
        g2d.drawArc(500, 225, 50, 50, 90, 180);
        //g2d.fillOval(270, 130, 50, 50);  
        g2d.drawOval(300, 150, 200, 200);  
   } 

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }    

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                BasicShapes bs = new BasicShapes();
                bs.setVisible(true);
            }
        });
    }
}