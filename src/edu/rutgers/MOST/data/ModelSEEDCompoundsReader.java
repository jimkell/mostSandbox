package edu.rutgers.MOST.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;

public class ModelSEEDCompoundsReader {
	
	static Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	
	public void readFile(File file) {
		CSVReader reader;

		int count = 0;

		try {
			reader = new CSVReader(new FileReader(file), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
//						for (int s = 0; s < dataArray.length; s++) {	
//							
//						}
						String keggLabel = "KEGG:";
						String aliases = dataArray[18];
						if (aliases.contains(keggLabel)) {
							//System.out.println(aliases);
							String[] aliasStr = aliases.split(";");
							for (int i = 0; i < aliasStr.length; i++) {
								if (aliasStr[i].contains(keggLabel)) {
									System.out.println(aliasStr[i]);
									String keggId = aliasStr[i].substring(keggLabel.length());
									System.out.println(keggId);
								}
							}
							String modelSeedId = dataArray[0];
							System.out.println(modelSeedId);
							String modelSeedAbbr = dataArray[1];
							System.out.println(modelSeedAbbr);
						}
						
						
					}
					count += 1;
				}
				reader.close();
				//System.out.println();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,                
						"File Not Found Error.",                
						"Error",                                
						JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}	
	}
	
	public static void main( String args[] )
	{
		ModelSEEDCompoundsReader reader = new ModelSEEDCompoundsReader();
		File file = new File("etc/visualization/Compounds.csv");
		reader.readFile(file);
	}

}

