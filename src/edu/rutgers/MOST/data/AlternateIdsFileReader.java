package edu.rutgers.MOST.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Thie purpose of this reader is to read output.csv from KEGGCrwaler
 * and create a file of KEGG ids and CHEBI ids for KEGG ids present in file
 *
 */
public class AlternateIdsFileReader {
	
	Map<String, ArrayList<String>> keggIdChebiIdMap = new HashMap<String, ArrayList<String>>();

	public void readFile(File file) {
		CSVReader reader;
		
		int count = 0;
		int keggIdColumn = 0;
		int chebiIdColumn = 3;
		
		try {
			reader = new CSVReader(new FileReader(file), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
//						System.out.println(dataArray[keggIdColumn]);
//						System.out.println(dataArray[chebiIdColumn]);
						String keggIdString = dataArray[keggIdColumn].trim();
						String chebiIdString = dataArray[chebiIdColumn].trim();
						if (keggIdString != null && chebiIdString != null && 
								keggIdString.length() > 0 && chebiIdString.length() > 0) {
							String[] chebi = chebiIdString.split("\\|");
							ArrayList<String> chebiIdsList = new ArrayList<String>();
							for (int i = 0; i < chebi.length; i++) {
								chebiIdsList.add(chebi[i]);
							}
							for (int j = 0; j < chebiIdsList.size(); j++) {
								if (!keggIdChebiIdMap.containsKey(keggIdString)) {	
									ArrayList<String> chebiIds = new ArrayList<String>();
									chebiIds.add(chebiIdsList.get(j));
									keggIdChebiIdMap.put(keggIdString, chebiIds);
								} else {
									ArrayList<String> chebiIds = keggIdChebiIdMap.get(keggIdString);
									if (!chebiIds.contains(chebiIdsList.get(j))) {
										chebiIds.add(chebiIdsList.get(j));
										keggIdChebiIdMap.put(keggIdString, chebiIds);
									}
								}
							}
						}
					}
					count += 1;
				}
				reader.close();
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
	
	public void writeFile(String file) {
		ArrayList<String> keggIds = new ArrayList<String>(keggIdChebiIdMap.keySet());
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(file), ',');

			String headerNames = "";
			//start with 1 to avoid reading database id
			for (int i = 0; i < PathwaysCSVFileConstants.CHEBL_ID_KEGG_ID_COLUMN_NAMES.length; i++) {
				headerNames += PathwaysCSVFileConstants.CHEBL_ID_KEGG_ID_COLUMN_NAMES[i] + "\t";
			}

			String [] header = (headerNames.substring(0, headerNames.length() - 1)).split("\t");

			writer.writeNext(header);				

			for (int i = 0; i < keggIds.size(); i++) {
				ArrayList<String> chebiIds = keggIdChebiIdMap.get(keggIds.get(i));					
				for (int j = 0; j < chebiIds.size(); j++) {
					String [] entries = (chebiIds.get(j) + "\t" + keggIds.get(i)).split("\t");
					writer.writeNext(entries);
				}						
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
	
	public static void main( String args[] )
	{
		AlternateIdsFileReader reader = new AlternateIdsFileReader();
		File f = new File("etc/visualization/output.csv");
		reader.readFile(f);
		// Errors were found in metabolites.csv and have been manually fixed
		//reader.writeFile("etc/visualization/chebiIds_keggIds1.csv");
		reader.writeFile("etc/visualization/chebiIds_keggIds.csv");
	}
	
}

