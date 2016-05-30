package edu.rutgers.MOST.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;
import edu.rutgers.MOST.config.LocalConfig;

public class GenericCSVFileReader {
	
	private EnzymeData enzymeData;
	
	public EnzymeData getEnzymeData() {
		return enzymeData;
	}

	public void setEnzymeData(EnzymeData enzymeData) {
		this.enzymeData = enzymeData;
	}

	public GenericCSVFileReader() {
		
	}
	
	public void readFile(File file) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(file), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						if (dataArray[0] != null && dataArray[0].length() > 0 && 
								dataArray[3] != null && dataArray[3].length() > 0 &&
								!LocalConfig.getInstance().getModelSEEDKeggIdMap().containsKey(dataArray[0])) {
							System.out.println(dataArray[0]);
							System.out.println(dataArray[3]);
						}
//						for (int s = 0; s < dataArray.length; s++) {
//							System.out.println(dataArray[s]);
//						}
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
	
	public static void main(String[] args) {
		KEGGIdModelSEEDReader seedReader = new KEGGIdModelSEEDReader();
		seedReader.readFile();
		System.out.println(LocalConfig.getInstance().getModelSEEDKeggIdMap());
		GenericCSVFileReader r = new GenericCSVFileReader();
		File f = new File("etc/sbml/Bacillus subtilis/13059_2009_2219_MOESM1_ESM.csv");
		r.readFile(f);
	}
}

