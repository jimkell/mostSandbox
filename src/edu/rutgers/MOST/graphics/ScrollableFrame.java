package edu.rutgers.MOST.graphics;

import java.awt.*;  
import java.awt.event.*;  
  
// based on http://www.coderanch.com/t/342639/GUI/java/Graphics-Object-Scroll-Bar
public class ScrollableFrame extends Frame {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {  
        new ScrollableFrame();  
    }  
   
    public ScrollableFrame() {  
        super("MOST");  
        DrawingPanel drawingPanel = new DrawingPanel();  
        ScrollPane scrollPane = new ScrollPane();  
        scrollPane.add(drawingPanel);  
        add(scrollPane);  
        setSize(1000,600); 
        setLocationRelativeTo(null);
        setVisible(true);  
        addWindowListener(new WindowAdapter()  
        {public void windowClosing(WindowEvent e)  
            {dispose(); System.exit(0);}});  
    }  
}  
   
class DrawingPanel extends Panel {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int maxWidth;  
    int maxHeight;  
    final int PAD = 25;  
   
    public DrawingPanel() {  
        maxWidth = 2000;  
        maxHeight = 1000;  
    }  
   
    public void paint(Graphics g) { 
    	Graphics2D g2d = (Graphics2D) g;
        super.paint(g); 
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);
        
        g.drawLine(400, 30, 400, 120);
        g.drawArc(500, 225, 50, 50, 90, 180);
        g.drawOval(300, 150, 200, 200); 
//        g.setColor(Color.red);  
//        g.drawRect(50,50,200,200);  
   
//        g2d.setColor(Color.blue);  
//        g2d.drawRect(75,75,300,200);  
//        g.drawLine(700, 30, 700, 900);
    }  
   
    public Dimension getPreferredSize() {  
        return new Dimension(maxWidth, maxHeight);  
    }  
}  
