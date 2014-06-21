package edu.rutgers.MOST.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// class was only used to read subsystems2peg subfiles, can be used to read
// other data files in future
public class TextFileReader {
	
	ArrayList<String> entries = new ArrayList<String>();
	
	public void readFile(File file, Character splitChar) {
		
		FileReader reader;
		try {
			reader = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(reader);
			String temp = br.readLine();
			while (temp != null) {
				temp = br.readLine();
				try {
					String item = temp.substring(0, temp.indexOf("fig"));
					//System.out.println(item);
					if (!entries.contains(item)) {
						entries.add(item);
						System.out.println(item);
					}
				} catch (Exception e) {
					
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
	}
	
	public void writeFile(String file) {
		try {
			FileWriter writer = new FileWriter(file); 
			for(int i = 0; i < entries.size(); i++) {
				writer.write(entries.get(i));
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TextFileReader r = new TextFileReader();
		for (int i = 1; i <= 5; i++ ) {
			File f = new File("C:/subsystems2peg" + i + ".txt");
			r.readFile(f, '\t');
		}
		r.writeFile("subsystems2peg.txt");
	}
}
