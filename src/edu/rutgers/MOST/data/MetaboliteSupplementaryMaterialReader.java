package edu.rutgers.MOST.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;
import edu.rutgers.MOST.config.LocalConfig;

public class MetaboliteSupplementaryMaterialReader {

	public void readFile(File file, String abbreviationColumnName, String keggIDColumnName) {
		CSVReader reader;

		int count = 0;
		int abbreviationColumnIndex = 0;
		int keggIDColumnIndex = 0;

		try {
			reader = new CSVReader(new FileReader(file), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count == 0) {
						for (int s = 0; s < dataArray.length; s++) {
							if (dataArray[s].trim().equals(abbreviationColumnName)) {
								System.out.println(dataArray[s]);
								abbreviationColumnIndex = s;
							}
							if (dataArray[s].trim().equals(keggIDColumnName)) {
								System.out.println(dataArray[s]);
								keggIDColumnIndex = s;
							}
						}
					} else if (count > 0) {
						for (int s = 0; s < dataArray.length; s++) {	
							if (s == abbreviationColumnIndex) {
								System.out.println(dataArray[s]);
							}
							if (s == keggIDColumnIndex) {
								System.out.println(dataArray[s]);
							}
						}
					}
					count += 1;
				}
				reader.close();
				//System.out.println("external " + externalMetabolitesList);
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
//		reader.readFile(iaf1260, "abbreviation", "KeggID");
		File ijo1366 = new File("etc/sbml/E. Coli/iJO1366/inline-supplementary-material-2.csv");
		reader.readFile(ijo1366, "Metabolite Abbreviation", "KEGG ID");
	}
	
}
