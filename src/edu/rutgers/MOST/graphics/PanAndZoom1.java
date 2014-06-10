package edu.rutgers.MOST.graphics;

import java.awt.*;  
import java.awt.geom.*;  
import java.awt.image.BufferedImage;  
import java.util.ArrayList;

import javax.swing.*;  
import javax.swing.event.*;  

// based on code from http://www.coderanch.com/t/346846/GUI/java/Wanted-Java-zooming-panning-scroll 
public class PanAndZoom1 implements ChangeListener {  
    BufferedImage image;  
    JLabel label;  
   
    public void stateChanged(ChangeEvent e) {  
        int value = ((JSlider)e.getSource()).getValue();  
        double scale = value/100.0;  
        BufferedImage scaled = getScaledImage(scale);  
        label.setIcon(new ImageIcon(scaled));  
        label.revalidate();  // signal scrollpane  
    }  
   
    private BufferedImage getScaledImage(double scale) {  
        int w = (int)(scale*image.getWidth());  
        int h = (int)(scale*image.getHeight());  
        BufferedImage bi = new BufferedImage(w, h, image.getType());  
        Graphics2D g2 = bi.createGraphics();  
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);  
        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);  
        g2.drawRenderedImage(image, at);  
        g2.dispose();  
        return bi;  
    }  
   
    private JLabel getContent() {  
        createAnImage();  
        label = new JLabel(new ImageIcon(image));  
        label.setHorizontalAlignment(JLabel.CENTER);  
        return label;  
    }  
   
    private void createAnImage() { 
        int w = 1000;  
        int h = 1000;  
        int type = BufferedImage.TYPE_INT_RGB; // many options  
        image = new BufferedImage(w, h, type);  
        Graphics2D g2 = image.createGraphics();  
        BasicDraw basicDraw = new BasicDraw();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                            RenderingHints.VALUE_ANTIALIAS_ON);  
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,  
                            RenderingHints.VALUE_STROKE_PURE);
        
        g2.setStroke(new BasicStroke(2));
        
        g2.setPaint(Color.WHITE);  
        g2.fillRect(0,0,w,h);  
        g2.setColor(Color.BLACK);
        g2.drawLine(400, 30, 400, 105);
        FontMetrics fm = g2.getFontMetrics();
        int stringWidth = fm.stringWidth("Test");
        int stringHeight = fm.getHeight();
        // centers string relative to line
        g2.drawString("Test", 400 - stringWidth/2, 105 + stringHeight);
        g2.drawArc(400, 102, 50, 50, 180, 75);
        g2.drawArc(500, 225, 50, 50, 90, 180);
        // draw arrow at bottom of arc
        g2.drawLine(515, 265, 525, 275);
        g2.drawLine(512, 278, 526, 275);
        // places string 10 px after top end of arc
        g2.drawString("Test", 535, 225 + stringHeight/4);
        g2.drawOval(300, 150, 200, 200); 
        
        basicDraw.drawStraightHorizontalPathway(g2, 100, 500, 2, test);
        basicDraw.drawStraightVerticalPathway(g2, 750, 20, 2, test);
        g2.setPaint(Color.RED); 
        basicDraw.drawDashedLine(g2, 800, 20, 800, 300, 2);
        g2.dispose();  
    }  
   
    private JSlider getControl() {  
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 50, 200, 100);  
        slider.setSize(100, 20); 
        slider.setMajorTickSpacing(50);  
        slider.setMinorTickSpacing(10);  
        slider.setPaintTicks(true);  
        slider.setPaintLabels(true);  
        slider.addChangeListener(this);  
        return slider;          
    } 
    
    public String[] test = 
		{ 
			"metab1",
			"metab2",
			"metab3",		
			"metab4",
			"metab5",
			"long Metab\nName"
		};

    public static void main(String[] args) { 
    	final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("etc/most16.jpg").getImage()); 
		icons.add(new ImageIcon("etc/most32.jpg").getImage());
    	
        PanAndZoom1 app = new PanAndZoom1();  
        JFrame f = new JFrame(); 
        f.setTitle("MOST");
        f.setIconImages(icons);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new JScrollPane(app.getContent()));  
        f.getContentPane().add(app.getControl(), "Last");  
        f.setSize(1000, 600);  
        f.setLocationRelativeTo(null);  
        f.setVisible(true);  
    }  
}  
