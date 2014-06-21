package edu.rutgers.MOST.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Subsystems2pegReader {

	public void readFile() {

		FileReader reader;
		try {
			reader = new FileReader(new File("etc/subsystem/subsystems2peg.txt"));
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(reader);
			String temp = br.readLine();
			while (temp != null) {
				temp = br.readLine();
				//System.out.println(temp);
				try {
					String[] s = temp.split("\t");
					for (int i = 0; i < s.length; i++) {
						if (i == 0) {
							System.out.println(s[i]);
						} 
						if (i == 1) {
							String ec = "";
							String description = s[i];
							if (s[i].contains("(EC ")) {
								ec = s[i].substring(s[i].indexOf("(EC ") + 4, s[i].indexOf(")"));
								System.out.println(ec);
								description = s[i].substring(0, s[i].indexOf("(EC "));
								
							}
							System.out.println(description);
						}
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

	public static void main(String[] args) {
		Subsystems2pegReader r = new Subsystems2pegReader();
	    r.readFile();
	}
}
