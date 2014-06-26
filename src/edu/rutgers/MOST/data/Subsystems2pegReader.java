package edu.rutgers.MOST.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import edu.rutgers.MOST.config.LocalConfig;

public class Subsystems2pegReader {

	public void readFile() {

		FileReader reader;
		try {
			reader = new FileReader(new File("etc/subsystem/subsystems2peg.txt"));
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(reader);
			String temp = br.readLine();
			Map<String, ArrayList<SubsystemReaction>> subsystemDataMap = new HashMap<String, ArrayList<SubsystemReaction>>();
			LocalConfig.getInstance().setSubsystemDataMap(subsystemDataMap);
			ArrayList<SubsystemReaction> reaction = new ArrayList<SubsystemReaction>();
			while (temp != null) {
				temp = br.readLine();
				try {
					String[] s = temp.split("\t");
					for (int i = 0; i < s.length; i++) {
						if (i == 1) {
							SubsystemReaction reac = new SubsystemReaction();
							String ecNumber = "";
							String description = s[i];
							String additionalInfo = "";
							if (s[i].contains("(EC ")) {
								ecNumber = s[i].substring(s[i].indexOf("(EC ") + 4, s[i].indexOf(")"));
								reac.setEcNumber(ecNumber);
								description = s[i].substring(0, s[i].indexOf("(EC "));
								additionalInfo = s[i].substring(s[i].indexOf(")") + 1, s[i].length());
								reac.setAdditionalInformation(additionalInfo);
							}
							reac.setReactionDescription(description);
							if (LocalConfig.getInstance().getSubsystemDataMap().containsKey(s[0])) {
								ArrayList<SubsystemReaction> reacList = LocalConfig.getInstance().getSubsystemDataMap().get(s[0]);
								reacList.add(reac);
								LocalConfig.getInstance().getSubsystemDataMap().put(s[0], reacList);
							} else {
								reaction = new ArrayList<SubsystemReaction>();
								reaction.add(reac);
								LocalConfig.getInstance().getSubsystemDataMap().put(s[0], reaction);
							}
						}
					}
				} catch (Exception e) {

				}
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
		Subsystems2pegReader r = new Subsystems2pegReader();
	    r.readFile();
	    System.out.println(LocalConfig.getInstance().getSubsystemDataMap());
	    System.out.println(LocalConfig.getInstance().getSubsystemDataMap().get("TCA_Cycle"));
	    System.out.println(LocalConfig.getInstance().getSubsystemDataMap().get("Glycolysis_and_Gluconeogenesis"));
	}
}
