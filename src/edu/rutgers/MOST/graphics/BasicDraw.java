package edu.rutgers.MOST.graphics;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

// The purpose of this class is to provide methods for common tasks. The draw vertical and
// horizontal methods avoid using rotations and trig functions. Since most items will
// be either vertical or horizontal, this should make the graphics faster.
public class BasicDraw {

	//private int maxStringWidth;

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

	public void drawStraightHorizontalPathway(Graphics g, int startX, int startY, int width, ArrayList<String> reactions) {

		//creates a copy of the Graphics instance
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(width));

		for (int i = 0; i < reactions.size(); i++) {
			FontMetrics fm = g2d.getFontMetrics();
			int stringWidth = fm.stringWidth(reactions.get(i));
			int stringHeight = fm.getHeight();
			if (reactions.get(i).contains("\n")) {
				stringWidth = multilineStringWidth(g2d, reactions.get(i));
				// after first string, places string 10 px after arrow
				if (i > 0) {
					startX += GraphicsConstants.DEFAULT_HORIZONTAL_PATH_LENGTH + GraphicsConstants.DEFAULT_SPACE;
				}
				drawMultilineString(g2d, reactions.get(i), startX, startY - stringHeight - stringHeight/4, true);
			} else {
				// after first string, places string 10 px after arrow
				if (i > 0) {
					startX += GraphicsConstants.DEFAULT_HORIZONTAL_PATH_LENGTH + GraphicsConstants.DEFAULT_SPACE;
				}
				g2d.drawString(reactions.get(i), startX, startY + stringHeight/4);
			}
			startX += stringWidth + GraphicsConstants.DEFAULT_SPACE;
			// places line 10 px after end of string
			g2d.drawLine(startX, startY, startX + GraphicsConstants.DEFAULT_HORIZONTAL_PATH_LENGTH, startY);
			drawHorizontalArrow(g2d, startX + GraphicsConstants.DEFAULT_HORIZONTAL_PATH_LENGTH, startY, 2, GraphicsConstants.ARROW_WIDTH, GraphicsConstants.ARROW_LENGTH, 1);
		}

		g2d.dispose();
	}

	// startX and startY are start of line, endX and endY are end of line
	// avoids use of trig functions
	// direction 1 if forward (right), -1 if backwards (left)
	public void drawHorizontalArrow(Graphics g, int endX, int endY, int strokeWidth, int height, int width, int direction) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(strokeWidth));

		g2d.drawLine(endX - width*direction, endY - height, endX, endY);
		g2d.drawLine(endX - width*direction, endY + height, endX, endY);

		g2d.dispose();
	}

	public void drawStraightVerticalPathway(Graphics g, int startX, int startY, int width, ArrayList<String> reactions, 
			ArrayList<String> reactants, ArrayList<String> products, ArrayList<String> sideReactants, ArrayList<String> sideProducts) {

		//creates a copy of the Graphics instance
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(width));

		FontMetrics fm = g2d.getFontMetrics();
		int stringHeight = fm.getHeight();
		
		for (int i = 0; i < reactions.size(); i++) {
			drawAnyString(g2d, reactants.get(i), stringHeight, startX, startY);
			if (reactants.get(i).contains("\n")) {
				startY += GraphicsConstants.DEFAULT_SPACE + stringHeight;
			} else {
				startY += GraphicsConstants.DEFAULT_SPACE;
			}
			drawVerticalPathLabel(g, reactions.get(i), stringHeight, startX, startY);
			// places line 10 px after end of string
			g2d.drawLine(startX, startY, startX, startY + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH);
			drawVerticalArrow(g2d, startX, startY + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH, 2, GraphicsConstants.ARROW_WIDTH, GraphicsConstants.ARROW_LENGTH, 1);
			// places line 10 px after end of string
			startY += stringHeight + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH;
			System.out.println("sr " + sideReactants.get(i));
			System.out.println("sp " + sideProducts.get(i));
		}
		
		drawAnyString(g2d, products.get(products.size() - 1), stringHeight, startX, startY);
		if (products.get(products.size() - 1).contains("\n")) {
			startY += GraphicsConstants.DEFAULT_SPACE + stringHeight;
		} else {
			startY += GraphicsConstants.DEFAULT_SPACE;
		}

		g2d.dispose();
	}

	// startX and startY are start of line, endX and endY are end of line
	// avoids use of trig functions
	// direction 1 if down, -1 if up
	public void drawVerticalArrow(Graphics g, int endX, int endY, int strokeWidth, int height, int width, int direction) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(strokeWidth));

		g2d.drawLine(endX - height, endY - width*direction, endX, endY);
		g2d.drawLine(endX + height, endY - width*direction, endX, endY);

		g2d.dispose();
	}
	
	public void drawSideArc(Graphics g, int x, int y, int strokeWidth, int arcWidth, int arcHeight, int startAngle, int endAngle) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(strokeWidth));

		g2d.drawArc(x, y, arcWidth, arcHeight, startAngle, endAngle);
		
		g2d.dispose();
	}

	// side arc actually vertical but is for a horizontal pathway. in arc would have
	// a rotated arrow, so in can't use same method as out
	public void drawHorizontalSideInArc(Graphics g, int x, int y, int strokeWidth, int arcWidth, int arcHeight, int startAngle, int endAngle, int direction, boolean arrow) {
		drawSideArc(g, x, y, strokeWidth, arcWidth, arcHeight, startAngle, endAngle);
		if (arrow) {
			Graphics2D g2d = (Graphics2D) g.create();

			g2d.setStroke(new BasicStroke(strokeWidth));

			if (direction == -1) {
				y = y + GraphicsConstants.SIDE_ARC_MAJOR_AXIS;
			}
			g2d.drawLine(x - 2, y + 3*direction, x - 1 + arcWidth/2, y);
			g2d.drawLine(x - 1 + arcWidth/2, y + direction + direction*GraphicsConstants.SIDE_ARC_ARROW_LENGTH, x - 1 + arcWidth/2, y);
			
			g2d.dispose();
		}
	}

	// side arc actually vertical but is for a horizontal pathway
	public void drawHorizontalSideOutArc(Graphics g, int x, int y, int strokeWidth, int arcWidth, int arcHeight, int startAngle, int endAngle, int direction, boolean arrow) {
		drawSideArc(g, x, y, strokeWidth, arcWidth, arcHeight, startAngle, endAngle);
		if (arrow) {
			drawVerticalArrow(g, x + arcHeight/2, y + 1*direction + arcWidth,  1, GraphicsConstants.SIDE_ARC_ARROW_WIDTH, GraphicsConstants.SIDE_ARC_ARROW_LENGTH, direction);
		}
	}

	// side arc actually horizontal but is for a vertical pathway. in arc would have
	// a rotated arrow, so in can't use same method as out
	public void drawVerticalSideInArc(Graphics g, int x, int y, int strokeWidth, int arcWidth, int arcHeight, int startAngle, int endAngle, int direction, boolean arrow) {
		drawSideArc(g, x, y, strokeWidth, arcWidth, arcHeight, startAngle, endAngle);
		if (arrow) {
			Graphics2D g2d = (Graphics2D) g.create();

			g2d.setStroke(new BasicStroke(strokeWidth));

			if (direction == -1) {
				x = x + GraphicsConstants.SIDE_ARC_MAJOR_AXIS;
			}
			g2d.drawLine(x + 3*direction, y - 2, x, y - 1 + arcHeight/2);
			g2d.drawLine(x + direction + direction*GraphicsConstants.SIDE_ARC_ARROW_LENGTH, y - 1 + arcHeight/2, x, y - 1 + arcHeight/2);
			
			g2d.dispose();
		}
	}

	// side arc actually horizontal but is for a vertical pathway
	public void drawVerticalSideOutArc(Graphics g, int x, int y, int strokeWidth, int arcWidth, int arcHeight, int startAngle, int endAngle, int direction, boolean arrow) {
		drawSideArc(g, x, y, strokeWidth, arcWidth, arcHeight, startAngle, endAngle);
		if (arrow) {
			drawHorizontalArrow(g, x + 1*direction + arcHeight, y + arcWidth/2, 1, GraphicsConstants.SIDE_ARC_ARROW_WIDTH, GraphicsConstants.SIDE_ARC_ARROW_LENGTH, direction);
		}
	}

	public int multilineStringWidth(Graphics g, String text) {
		int maxWidth = 0;
		FontMetrics fm = g.getFontMetrics();
		for (String line : text.split("\n")) {
			int stringWidth = fm.stringWidth(line);
			if (stringWidth > maxWidth) {
				maxWidth = stringWidth;
			}
		}
		return maxWidth;
	}
	
	public void drawAnyString(Graphics g, String text, int stringHeight, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();
		FontMetrics fm = g2d.getFontMetrics();
		
		int productsStringWidth = fm.stringWidth(text);
		if (text.contains("\n")) {
			int maxWidth = multilineStringWidth(g2d, text);
			drawMultilineString(g2d, text, x - maxWidth/2, y - stringHeight, true);
		} else {
			g2d.drawString(text, x - productsStringWidth/2, y);
		}

		g2d.dispose();
	}

	// based on http://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
	public void drawMultilineString(Graphics g, String text, int x, int y, boolean center) {
		FontMetrics fm = g.getFontMetrics();
//		for (String line : text.split("\n")) {
//			int stringWidth = fm.stringWidth(line);
//			if (stringWidth > maxStringWidth) {
//				maxStringWidth = stringWidth;
//			}
//		}
		int maxStringWidth = multilineStringWidth(g, text);
		for (String line : text.split("\n")) {
			if (center) {
				g.drawString(line, x + maxStringWidth/2 - fm.stringWidth(line)/2, y += g.getFontMetrics().getHeight());
			} else {
				g.drawString(line, x, y += g.getFontMetrics().getHeight());
			}
		}  
	}
	
	public String newLineString(String reactionName) {
    	String newLineString = reactionName;
    	if (newLineString.length() > 20) {
    		String s[] = newLineString.split(" ");
    		for (int i = 0; i < s.length; i++) {
    			if (s.length > 1 && i == 1) {
    				newLineString = s[0] + "\n" + s[1];
    				if (s.length > 2) {
    					for (int j = 2; j < s.length; j++) {
    						newLineString += " " + s[j];
    					}
    				}
    			}
    		}
    	}
    	
		return newLineString;
    }
	
	public void drawVerticalPathLabel(Graphics g, String text, int stringHeight, int x, int y) {
		if (text.contains("\n")) {
			drawMultilineString(g, text, x + GraphicsConstants.DEFAULT_SPACE, (y - 3*stringHeight/2) + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH/2, false);
		} else {
			g.drawString(text, x + GraphicsConstants.DEFAULT_SPACE, y + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH/2);
		}
	}
	
	/******************************************************************************************/
	// rotated components
	/******************************************************************************************/
	
	public void drawRotatedArrowedLine(Graphics g, int startX, int startY, int angle, int width) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(width));
		
		// rotates around starting point
		g2d.rotate(Math.toRadians(angle), startX, startY);
        g2d.drawLine(startX, startY, startX, startY + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH);
        drawVerticalArrow(g2d, startX, startY + GraphicsConstants.DEFAULT_VERTICAL_PATH_LENGTH, width, GraphicsConstants.ARROW_WIDTH, GraphicsConstants.ARROW_LENGTH, 1);
		
		g2d.dispose();
	}
	
	public void drawRotatedSideInArc(Graphics g, int x, int y, int strokeWidth, int arcWidth, int arcHeight, int startAngle, int endAngle, int rotation, int direction, boolean arrow) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		// rotates around starting point
	    g2d.rotate(Math.toRadians(rotation), x, y);
		drawVerticalSideInArc(g2d, x, y, strokeWidth, arcWidth, arcHeight, startAngle, endAngle, direction, arrow);
		
		g2d.dispose();
	}
	
	public void drawRotatedSideOutArc(Graphics g, int x, int y, int strokeWidth, int arcWidth, int arcHeight, int startAngle, int endAngle, int rotation, int direction, boolean arrow) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		// rotates around starting point
	    g2d.rotate(Math.toRadians(rotation), x, y);
		drawVerticalSideOutArc(g2d, x, y, strokeWidth, arcWidth, arcHeight, startAngle, endAngle, direction, arrow);
		
		g2d.dispose();
	}

}
