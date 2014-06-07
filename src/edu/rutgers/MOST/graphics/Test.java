package edu.rutgers.MOST.graphics;

import java.awt.Dimension;

import javax.swing.JFrame;

//based on http://stackoverflow.com/questions/10148421/jpanel-setbackgroundcolor-black-does-nothing
public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setPreferredSize(new Dimension(1000, 600));
		frame.add(new Board());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
