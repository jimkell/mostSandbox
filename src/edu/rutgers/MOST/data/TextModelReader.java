package edu.rutgers.MOST.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TextModelReader {
	
	/**
	 * 
	 * @param file
	 * @param row
	 * @return list of columns from csv file
	 */
	public ArrayList<String> columnNamesFromFile(File file, int row) {
		ArrayList<String> columnNamesFromFile = new ArrayList<String>();
		
		String[] dataArray = null;

		//use fileReader to read first line to get headers
		BufferedReader CSVFile;
		try {
			CSVFile = new BufferedReader(new FileReader(file));
			String dataRow = CSVFile.readLine();
			dataArray = dataRow.split(",");				

			//add all column names to list			
			for (int h = 0; h < dataArray.length; h++) { 
				addColumnName(dataArray[h], columnNamesFromFile);		
			}

			if (row > 0) {
				for (int i = 0; i < row; i++) {
					dataRow = CSVFile.readLine();
					dataArray = dataRow.split(",");								
					columnNamesFromFile.clear();
					//add all column names to list			
					for (int h = 0; h < dataArray.length; h++) { 
						addColumnName(dataArray[h], columnNamesFromFile);	
					} 
				}				
			}

			CSVFile.close();

		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e1.printStackTrace();							
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e1.printStackTrace();
		} 

		return columnNamesFromFile;
	}	
	
	/**
	 * Removes quotes from column names and trims whitespace
	 * @param data
	 * @param columnNamesFromFile
	 */
	public void addColumnName(String data, ArrayList<String> columnNamesFromFile) {
		//remove quotes if exist
		if (data.startsWith("\"")) {
			//removes " " and whitespace
			if (data.compareTo("\" \"") != 0 && data.trim().length() > 0) {
				columnNamesFromFile.add(data.substring(1, data.length() - 1));
			}					
		} else {
			if (data.trim().length() > 0) {
				columnNamesFromFile.add(data);
			}					
		}	
	}

}
