package edu.rutgers.MOST.graphics;

import java.awt.*;
import java.awt.event.*;

import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;

// from http://www.daniweb.com/software-development/java/threads/417811/how-to-clear-a-canvas
public class PathwayFrame extends Frame implements ActionListener {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Initial Frame size
	static final int WIDTH = 1000; // frame width
	static final int HEIGHT = 600; // frame height
	Button plus;
	Button minus;
	
	DrawCanvas canvas;
	
	public PathwayFrame() {
		super(GraphicalInterfaceConstants.TITLE);
		//super(GraphicalInterfaceConstants.TITLE + " - " + LocalConfig.getInstance().getModelName());
		setLayout(new BorderLayout());
		
		Panel bottomPanel = new Panel(new GridLayout(1, 1));
		add(bottomPanel, BorderLayout.SOUTH);
		Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(buttonPanel);

		Panel zoomPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(zoomPanel);
		plus = new Button("+");
		zoomPanel.add(plus);
		minus = new Button("-");
		zoomPanel.add(minus);
		plus.addActionListener(this);
		minus.addActionListener(this);
		
		canvas = new DrawCanvas();
		add(canvas, BorderLayout.CENTER);
	} 
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == plus) { 
			System.out.println("+");
			canvas.setShape(DrawCanvas.CLEAR);
			canvas.repaint();
		}
		else if(event.getSource() == minus) {
			System.out.println("-");
			canvas.setShape(DrawCanvas.CLEAR);
			canvas.repaint();
		}
	}
	
	public static void main(String[] argv) {
		// Create a frame
		PathwayFrame frame = new PathwayFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		//frame.setLocation(150, 100);
		// add window closing listener
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		
		frame.setVisible(true);
	}
}

