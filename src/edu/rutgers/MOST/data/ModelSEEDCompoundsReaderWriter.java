package edu.rutgers.MOST.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class ModelSEEDCompoundsReaderWriter {
	
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
							ArrayList<String> keggIdList = new ArrayList<String>();
							//System.out.println(aliases);
							String[] aliasStr = aliases.split(";");
							for (int i = 0; i < aliasStr.length; i++) {
								if (aliasStr[i].contains(keggLabel)) {
									//System.out.println(aliasStr[i]);
									String keggId = aliasStr[i].substring(keggLabel.length());
									if (keggId.startsWith(":")) {
										keggId = keggId.substring(1);
									}
									if (keggId.endsWith("\"")) {
										//System.out.println(keggId);
										keggId = keggId.substring(0, keggId.length() - 1);
										//System.out.println(keggId);
									}
									//System.out.println(keggId);
									keggIdList.add(keggId);
								}
							}
							//String modelSeedId = dataArray[0];
							//System.out.println(modelSeedId);
							String modelSeedAbbr = dataArray[1];
							//System.out.println(modelSeedAbbr);
							map.put(modelSeedAbbr, keggIdList);
						}
						
						
					}
					count += 1;
				}
				reader.close();
				System.out.println(map);
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
	
	public void write(String file, String keyColumnName, ArrayList<String> valueColumnNames) {
		//String extension = ".csv";
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(file), ',');
			String columnNames = "";
			for (int h = 0; h < valueColumnNames.size(); h++) {
				columnNames += valueColumnNames.get(h) + "\t";
			}
			String [] columns = (keyColumnName + "\t" + columnNames.substring(0, columnNames.length() - 1)).split("\t");
			writer.writeNext(columns);
			ArrayList<String> keys = new ArrayList<String>(map.keySet());
			Collections.sort(keys);
			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				String values = "";
				for (int j = 0; j < map.get(key).size(); j++) {
					//values += map.get(key).get(j) + "\t";
					values += map.get(key).get(j) + "|";
				}
				String [] entries = (key + "\t" + values.substring(0, values.length() - 1)).split("\t");
				writer.writeNext(entries);
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
		ModelSEEDCompoundsReaderWriter m = new ModelSEEDCompoundsReaderWriter();
		File file = new File("etc/visualization/Compounds.csv");
		m.readFile(file);
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("KEGG ID");
		m.write("etc/ModelSEEDAbbrKEGGIDs.csv", "Abbreviation", columns);
	}

}

