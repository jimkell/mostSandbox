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

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.presentation.GraphicalInterface;
import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVCombiner {
	
	static Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	
	public void readFile(File file, int keyColumnIndex, int valueColumnIndex, 
			int filetrimStartIndex, int filetrimEndIndex) {
		CSVReader reader;

		int count = 0;
		String key = "";
		String value = "";

		try {
			reader = new CSVReader(new FileReader(file), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						for (int s = 0; s < dataArray.length; s++) {	
							if (s == keyColumnIndex) {
								int totalTrimLength = filetrimStartIndex + filetrimEndIndex;
								if (dataArray[s] != null && dataArray[s].length() > totalTrimLength) {
									key = dataArray[s].substring(filetrimStartIndex, dataArray[s].length() - filetrimEndIndex);
								}
							}
							if (s == valueColumnIndex) {
								value = dataArray[s];
								if (value.startsWith("EC-")) {
									value = value.substring(3);
								}
							}
						}
						if (key != null && key.length() > 0 && value != null && value.length() > 0) {
							if (map.containsKey(key)) {
								ArrayList<String> values = map.get(key);
								if (!values.contains(value)) {
									values.add(value);
								}
							} else {
								ArrayList<String> values = new ArrayList<String>();
								if (!values.contains(value)) {
									values.add(value);
								}
								map.put(key, values);
							}
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
					values += map.get(key).get(j) + "\t";
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
		CSVCombiner c = new CSVCombiner();
		// write kegg id combined file
//		File iaf1260 = new File("etc/sbml/E. Coli/iAF1260/inline-supplementary-material-3.csv");
//		c.readFile(iaf1260, 0, 7, 0, 0);
//		File ijo1366 = new File("etc/sbml/E. Coli/iJO1366/inline-supplementary-material-2.csv");
//		c.readFile(ijo1366, 0, 6, 0, 3);
//		System.out.println(map);
//		ArrayList<String> columns = new ArrayList<String>();
//		columns.add("KEGG ID");
//		c.write("test.csv", "Metabolite Abbreviation", columns);
		// write ec number combined file
//		File iaf1260 = new File("etc/sbml/E. Coli/iAF1260/iAF1260_ec_num.csv");
//		c.readFile(iaf1260, 0, 1, 0, 0);
//		File ijo1366 = new File("etc/sbml/E. Coli/iJO1366/iJO1366_ec_num.csv");
//		c.readFile(ijo1366, 0, 1, 0, 0);
//		System.out.println(map);
//		ArrayList<String> columns = new ArrayList<String>();
//		columns.add("EC Number");
//		c.write("iAF1260_iJO1366_Combined_EC_Numbers.csv", "Reaction Abbreviation", columns);
		File iaf1260_iJO1366 = new File("iAF1260_iJO1366_Combined_EC_Numbers.csv");
		c.readFile(iaf1260_iJO1366, 0, 1, 2, 0);
		File iJR904 = new File("etc/sbml/E. Coli/iJR904/gb-2003-4-9-r54-s1.csv");
		c.readFile(iJR904, 0, 4, 0, 0);
		System.out.println(map);
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("EC Number");
		c.write("iAF1260_iJO1366_iJR904_Combined_EC_Numbers.csv", "Reaction Abbreviation", columns);
	}

}
