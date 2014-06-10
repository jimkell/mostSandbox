package edu.rutgers.MOST.graphics;

import java.awt.BasicStroke;
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
	
}
