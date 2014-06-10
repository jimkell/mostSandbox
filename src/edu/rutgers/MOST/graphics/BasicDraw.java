package edu.rutgers.MOST.graphics;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class BasicDraw {

	// based on http://stackoverflow.com/questions/21989082/drawing-dashed-line-in-java
    public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2, int width){

    	//creates a copy of the Graphics instance
    	Graphics2D g2d = (Graphics2D) g.create();

    	Stroke dashed = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    	g2d.setStroke(dashed);
    	g2d.drawLine(x1, y1, x2, y2);

    	//gets rid of the copy
    	g2d.dispose();
    }
    
    public void drawStraightHorizontalPathway(Graphics g, int startX, int startY, int width, String[] metabolites) {
    	
    	//creates a copy of the Graphics instance
    	Graphics2D g2d = (Graphics2D) g.create();
    	
    	g2d.setStroke(new BasicStroke(width));
    	
    	for (int i = 0; i < metabolites.length; i++) {
    		FontMetrics fm = g2d.getFontMetrics();
            int stringWidth = fm.stringWidth(metabolites[i]);
            int stringHeight = fm.getHeight();
    		g2d.drawString(metabolites[i], startX, startY + stringHeight/4);
    		startX += stringWidth + GraphicsConstants.DEFAULT_SPACE;
    		// places line 10 px after end of string
    		g2d.drawLine(startX, startY, startX + GraphicsConstants.DEFAULT_HORIZONTAL_PATH_LENGTH, startY);
    		drawHorizontalArrow(g2d, startX + GraphicsConstants.DEFAULT_HORIZONTAL_PATH_LENGTH, startY, 2);
    		// places line 10 px after end of string
    		startX += stringWidth;
    	}
    	
    	g2d.dispose();
    }
    
    // startX and startY are start of line, endX and endY are end of line
    // avoids use of trig functions
    public void drawHorizontalArrow(Graphics g, int endX, int endY, int width) {
    	Graphics2D g2d = (Graphics2D) g.create();
    	
    	g2d.setStroke(new BasicStroke(width));
    	
    	g2d.drawLine(endX - GraphicsConstants.ARROW_LENGTH, endY - GraphicsConstants.ARROW_HEIGHT, endX, endY);
    	g2d.drawLine(endX - GraphicsConstants.ARROW_LENGTH, endY + GraphicsConstants.ARROW_HEIGHT, endX, endY);
    	
    	g2d.dispose();
    }
    
    
	
}
