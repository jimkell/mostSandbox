package edu.rutgers.MOST.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EnzymeDataReader {
	public EnzymeDataReader() {
		
	}
	
	public void readFile() {
		// based on http://stackoverflow.com/questions/3373155/how-to-open-a-dat-file-in-java-program
		FileReader file;
		try {
			file = new FileReader(new File("etc/Enzyme/enzyme.dat"));
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(file);
			String temp = br.readLine();
			int count = 0;
			while (temp != null) {
			   temp = br.readLine();
			   System.out.println(temp);
			   if (temp != null && temp.length() > 2) {
				   System.out.println(temp.substring(0, 2));
				   if (temp.substring(0, 2).equals("ID")) {
					   count += 1;
					   System.out.println(count);
				   }
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
		EnzymeDataReader r = new EnzymeDataReader();
		r.readFile();
	}
}
