package edu.rutgers.MOST.graphics;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class BasicDraw {
	
	private int maxStringWidth;

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
    		if (metabolites[i].contains("\n")) {
    			drawMultilineString(g2d, metabolites[i], startX, startY - stringHeight - stringHeight/4, true);
    			stringWidth = maxStringWidth;
    		} else {
    			g2d.drawString(metabolites[i], startX, startY + stringHeight/4);
    		}
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
    
public void drawStraightVerticalPathway(Graphics g, int startX, int startY, int width, String[] metabolites) {
    	
    	//creates a copy of the Graphics instance
    	Graphics2D g2d = (Graphics2D) g.create();
    	
    	g2d.setStroke(new BasicStroke(width));
    	
    	for (int i = 0; i < metabolites.length; i++) {
    		FontMetrics fm = g2d.getFontMetrics();
            int stringWidth = fm.stringWidth(metabolites[i]);
            int stringHeight = fm.getHeight();
    		g2d.drawString(metabolites[i], startX - stringWidth/2, startY);
    		startY += GraphicsConstants.DEFAULT_SPACE;
    		// places line 10 px after end of string
    		g2d.drawLine(startX, startY, startX, startY + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH);
    		drawVerticalArrow(g2d, startX, startY + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH, 2);
    		// places line 10 px after end of string
    		startY += stringHeight + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH;
    	}
    	
    	g2d.dispose();
    }
    
    // startX and startY are start of line, endX and endY are end of line
    // avoids use of trig functions
    public void drawVerticalArrow(Graphics g, int endX, int endY, int width) {
    	Graphics2D g2d = (Graphics2D) g.create();
    	
    	g2d.setStroke(new BasicStroke(width));
    	
    	g2d.drawLine(endX - GraphicsConstants.ARROW_HEIGHT, endY - GraphicsConstants.ARROW_LENGTH, endX, endY);
    	g2d.drawLine(endX + GraphicsConstants.ARROW_HEIGHT, endY - GraphicsConstants.ARROW_LENGTH, endX, endY);
    	
    	g2d.dispose();
    }
    
    // based on http://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
    public void drawMultilineString(Graphics g, String text, int x, int y, boolean center) {
    	FontMetrics fm = g.getFontMetrics();
    	for (String line : text.split("\n")) {
    		int stringWidth = fm.stringWidth(line);
        	if (stringWidth > maxStringWidth) {
        		maxStringWidth = stringWidth;
        	}
    	}
        for (String line : text.split("\n")) {
        	if (center) {
        		g.drawString(line, x + maxStringWidth/2 - fm.stringWidth(line)/2, y += g.getFontMetrics().getHeight());
        	} else {
        		g.drawString(line, x, y += g.getFontMetrics().getHeight());
        	}
        }  
    }
	
}
