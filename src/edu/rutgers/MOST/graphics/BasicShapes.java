package edu.rutgers.MOST.graphics;

import javax.swing.JFrame;

public class BasicShapes extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicShapes() {

        initUI();
    }
    
    private void initUI() {
        
        setTitle("Basic Shapes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(new Surface());
        
        setSize(350, 250);
        setLocationRelativeTo(null);        
    }
}