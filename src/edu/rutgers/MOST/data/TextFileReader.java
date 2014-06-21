package edu.rutgers.MOST.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TextFileReader {
	
	ArrayList<String> subsystems = new ArrayList<String>();
	
	public void readFile(File file, Character splitChar) {
		
		FileReader reader;
		try {
			reader = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(reader);
			String temp = br.readLine();
			while (temp != null) {
				temp = br.readLine();
				//System.out.println(temp);
				try {
					String[] s = temp.split("\t");
					for (int i = 0; i < s.length; i++) {
						//System.out.println(s[i]);
						if (i == 0 && !subsystems.contains(s[i])) {
							subsystems.add(s[i]);
							//System.out.println(s[i]);
						} 
						if (i == 1) {
							//System.out.println(s[i]);
							if (s[i].contains("(EC ")) {
								
								String ec = s[i].substring(s[i].indexOf("(EC ") + 4, s[i].indexOf(")"));
								System.out.println(ec);
							}
						}
						//System.out.println(s[i]);
					}
				} catch (Exception e) {
					
				}
			}
//			Collections.sort(subsystems);
//			for (int i = 0; i < subsystems.size(); i++) {
//				System.out.println(subsystems.get(i));
//			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		TextFileReader r = new TextFileReader();
		File f = new File("C:/subsystems2peg1.txt");
		r.readFile(f, '\t');
		//for (int i = 1; i <= 5; i++ ) {
//			File f = new File("C:/subsystems2peg" + i + ".txt");
//			r.readFile(f, '\t');
		//}
		//System.out.println();
	}
}
