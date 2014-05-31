package edu.rutgers.MOST.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.rutgers.MOST.config.LocalConfig;

public class EnzymeDataReader {
	
	private EnzymeData enzymeData;
	
	public EnzymeData getEnzymeData() {
		return enzymeData;
	}

	public void setEnzymeData(EnzymeData enzymeData) {
		this.enzymeData = enzymeData;
	}

	public EnzymeDataReader() {
		
	}
	
	public void readFile() {
		EnzymeData enzymeData = new EnzymeData();
		setEnzymeData(enzymeData);
		// based on http://stackoverflow.com/questions/3373155/how-to-open-a-dat-file-in-java-program
		FileReader file;
		try {
			file = new FileReader(new File("etc/Enzyme/enzyme.dat"));
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(file);
			String temp = br.readLine();
			ArrayList<EnzymeData> enzymeDataList = new ArrayList<EnzymeData>();
			LocalConfig.getInstance().setEnzymeDataList(enzymeDataList);
			ArrayList<String> description = new ArrayList<String>();
			ArrayList<String> alternateNames = new ArrayList<String>();
			ArrayList<String> catalyticActivity = new ArrayList<String>();
			ArrayList<String> cofactors = new ArrayList<String>();
			ArrayList<String> comments = new ArrayList<String>();
			while (temp != null) {
				temp = br.readLine();
				//System.out.println(temp);
				if (temp != null && temp.length() > 5) {	
					//System.out.println(temp.substring(0, 2));
					if (temp.substring(0, 2).equals("ID")) {
						if (getEnzymeData().getId() != null) {
							getEnzymeData().setCatalyticActivity(catalyticActivity);
							LocalConfig.getInstance().getEnzymeDataList().add(getEnzymeData());
						}
						// create new lists for each new id
						description = new ArrayList<String>();
						alternateNames = new ArrayList<String>();
						catalyticActivity = new ArrayList<String>();
						cofactors = new ArrayList<String>();
						comments = new ArrayList<String>();
						// create new enzyme data object
						EnzymeData enzyme = new EnzymeData();
						setEnzymeData(enzyme);
						enzyme.setId(temp.substring(5, temp.length()));
						//LocalConfig.getInstance().getEnzymeDataList().add(enzyme);
					}
					if (temp.substring(0, 2).equals("DE")) {
						description.add(temp.substring(5, temp.length()));
						getEnzymeData().setDescription(description);
					}
					if (temp.substring(0, 2).equals("AN")) {
						alternateNames.add(temp.substring(5, temp.length()));
						getEnzymeData().setAlternateNames(alternateNames);
					}
					if (temp.substring(0, 2).equals("CA")) {
						catalyticActivity.add(temp.substring(5, temp.length()));
						getEnzymeData().setCatalyticActivity(catalyticActivity);
					}
					if (temp.substring(0, 2).equals("CF")) {
						cofactors.add(temp.substring(5, temp.length()));
						getEnzymeData().setCofactors(cofactors);
					}
					if (temp.substring(0, 2).equals("CC")) {
						comments.add(temp.substring(5, temp.length()));
						getEnzymeData().setComments(comments);
					}
				}
			}
			for (int i = 0; i < LocalConfig.getInstance().getEnzymeDataList().size(); i++) {
				System.out.println(LocalConfig.getInstance().getEnzymeDataList().get(i).toString());
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		EnzymeDataReader r = new EnzymeDataReader();
		r.readFile();
	}
}
