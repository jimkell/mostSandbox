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
import edu.rutgers.MOST.config.LocalConfig;

public class MetaboliteSupplementaryMaterialReader {
	
	public ArrayList<String> columnNamesFromFile(File file, int row) {
		TextModelReader textReader = new TextModelReader();
		ArrayList<String> columnNamesFromFile = textReader.columnNamesFromFile(file, row);	
		return columnNamesFromFile;
	}

	/**
	 * 
	 * @param file
	 * @param abbreviationColumnName
	 * @param keggIDColumnName
	 * @param trimStartIndex
	 * @param trimEndIndex
	 * Trim start index and end index used to match format of metabolite abbreviations in model
	 * with abbreviations in supplementary material
	 */
	//public void readFile(File file, String abbreviationColumnName, String keggIDColumnName,
	public void readFile(File file, int abbreviationColumnIndex, int keggIDColumnIndex,
			int filetrimStartIndex, int fileTrimEndIndex) {
		CSVReader reader;

		int count = 0;
//		int abbreviationColumnIndex = 0;
//		int keggIDColumnIndex = 0;
		String metabAbbr = "";
		String keggId = "";
		
		Map<String, String> metaboliteAbbrKeggIdMap = new HashMap<String, String>();

		try {
			reader = new CSVReader(new FileReader(file), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
//					if (count == 0) {
//						for (int s = 0; s < dataArray.length; s++) {
//							if (dataArray[s].trim().equals(abbreviationColumnName)) {
//								System.out.println(dataArray[s]);
//								abbreviationColumnIndex = s;
//							}
//							if (dataArray[s].trim().equals(keggIDColumnName)) {
//								System.out.println(dataArray[s]);
//								keggIDColumnIndex = s;
//							}
//						}
//					} else if (count > 0) {
					if (count > 0) {
						for (int s = 0; s < dataArray.length; s++) {	
							if (s == abbreviationColumnIndex) {
								//System.out.println(dataArray[s]);
								if (dataArray[s] != null && dataArray[s].length() > filetrimStartIndex && dataArray[s].length() > fileTrimEndIndex) {
									metabAbbr = dataArray[s].substring(filetrimStartIndex, dataArray[s].length() - fileTrimEndIndex);
									if (metabAbbr.contains("-")) {
										metabAbbr = metabAbbr.replace("-", "_");
									}
								}
							}
							if (s == keggIDColumnIndex) {
								//System.out.println(dataArray[s]);
								keggId = dataArray[s];
							}
						}
						if (metabAbbr !=  null && metabAbbr.length() > 0 && keggId != null && keggId.length() > 0) {
							metaboliteAbbrKeggIdMap.put(metabAbbr, keggId);
						}
					}
					count += 1;
				}
				reader.close();
				LocalConfig.getInstance().setMetaboliteAbbrKeggIdMap(metaboliteAbbrKeggIdMap);
				System.out.println(metaboliteAbbrKeggIdMap);
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
		MetaboliteSupplementaryMaterialReader reader = new MetaboliteSupplementaryMaterialReader();
//		File iaf1260 = new File("etc/sbml/E. Coli/iAF1260/inline-supplementary-material-3.csv");
//		reader.readFile(iaf1260, "abbreviation", "KeggID", 0, 0);
//		File ijo1366 = new File("etc/sbml/E. Coli/iJO1366/inline-supplementary-material-2.csv");
//		reader.readFile(ijo1366, "Metabolite Abbreviation", "KEGG ID", 0, 3);
	}
	
}
