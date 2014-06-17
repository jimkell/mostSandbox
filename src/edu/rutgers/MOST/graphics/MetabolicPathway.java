package edu.rutgers.MOST.graphics;

import java.awt.*;  
import java.awt.geom.*;  
import java.awt.image.BufferedImage;  
import java.util.ArrayList;

import javax.swing.*;  
import javax.swing.event.*;  

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.data.EnzymeConstants;
import edu.rutgers.MOST.data.EnzymeDataReader;

// based on code from http://www.coderanch.com/t/346846/GUI/java/Wanted-Java-zooming-panning-scroll 
public class MetabolicPathway implements ChangeListener {  
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
     
        drawGlycolysisPathway(g2, 600, 30);
        
        g2.dispose();  
    }  
    
    public void drawGlycolysisPathway(Graphics g, int x, int y) {
    	ArrayList<String> glycolysisReactionNames = new ArrayList<String>();
    	for (int i = 0; i < EnzymeConstants.GLYCOLYSIS_EC_NUMBERS.length; i++) {
    		System.out.println(LocalConfig.getInstance().getEnzymeDataMap().get(EnzymeConstants.GLYCOLYSIS_EC_NUMBERS[i]));
    		String reaction = LocalConfig.getInstance().getEnzymeDataMap().get(EnzymeConstants.GLYCOLYSIS_EC_NUMBERS[i]).getDescription().get(0);
    		glycolysisReactionNames.add(newLineString(reaction));
    	}
    	System.out.println(glycolysisReactionNames);
    	BasicDraw basicDraw = new BasicDraw();
    	basicDraw.drawStraightVerticalPathway(g, x, y, 2, glycolysisReactionNames);
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

    public static void main(String[] args) { 
    	final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("etc/most16.jpg").getImage()); 
		icons.add(new ImageIcon("etc/most32.jpg").getImage());
    	
		EnzymeDataReader r = new EnzymeDataReader();
		r.readFile();
		//System.out.println(LocalConfig.getInstance().getEnzymeDataMap().get("1.1.1.1"));
		
		MetabolicPathway mp = new MetabolicPathway();  
        JFrame f = new JFrame(); 
        f.setTitle("MOST");
        f.setIconImages(icons);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new JScrollPane(mp.getContent()));  
        f.getContentPane().add(mp.getControl(), "Last");  
        f.setSize(1000, 600);  
        f.setLocationRelativeTo(null);  
        f.setVisible(true);  
    }  
}  

