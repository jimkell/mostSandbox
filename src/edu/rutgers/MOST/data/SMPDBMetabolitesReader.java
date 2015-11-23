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
 * Thie purpose of this reader is to read metabolites file from http://www.smpdb.ca/downloads
 * and create a file of KEGG ids and CHEBI ids for KEGG ids present in file
 *
 */
public class SMPDBMetabolitesReader {
	
	Map<String, ArrayList<String>> keggIdChebiIdMap = new HashMap<String, ArrayList<String>>();

	public void readFile(File file) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(file), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
//						System.out.println(dataArray[6]);
//						System.out.println(dataArray[7]);
						if (dataArray[6] != null && dataArray[7] != null && 
								dataArray[6].length() > 0 && dataArray[7].length() > 0) {
							if (!keggIdChebiIdMap.containsKey(dataArray[6])) {	
								ArrayList<String> chebiIds = new ArrayList<String>();
								chebiIds.add(dataArray[7]);
								keggIdChebiIdMap.put(dataArray[6], chebiIds);
							} else {
								ArrayList<String> chebiIds = keggIdChebiIdMap.get(dataArray[6]);
								if (!chebiIds.contains(dataArray[7])) {
									chebiIds.add(dataArray[7]);
									keggIdChebiIdMap.put(dataArray[6], chebiIds);
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
		SMPDBMetabolitesReader reader = new SMPDBMetabolitesReader();
		File f = new File("etc/smpdb_metabolites/metabolites.csv");
		reader.readFile(f);
		// Errors were found in metabolites.csv and have been manually fixed
		reader.writeFile("etc/visualization/chebiIds_keggIds1.csv");
		//reader.writeFile("etc/visualization/chebiIds_keggIds.csv");
	}
	
}
