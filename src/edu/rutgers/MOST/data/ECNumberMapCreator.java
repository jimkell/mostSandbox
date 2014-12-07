package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class ECNumberMapCreator {

	/**
	 * EC Number map created to be used for getting information from loaded
	 * model by EC Number
	 */
	public void createEcNumberReactionMap() {
		Map<String, ArrayList<SBMLReaction>> ecNumberReactionMap = new HashMap<String, ArrayList<SBMLReaction>>();
		ReactionFactory rf = new ReactionFactory("SBML");
		Vector<SBMLReaction> rxns = rf.getAllReactions();
		for (int r = 0; r < rxns.size(); r++) {
			SBMLReaction reaction = (SBMLReaction) rxns.get(r);
			String ecString = reaction.getEcNumber();
			if (ecString != null && ecString.length() > 0) {
				// model may contain more than one EC number, separated by white space
				// AraGEM model has this condition
				java.util.List<String> ecNumbers = Arrays.asList(ecString.split("\\s"));
				for (int i = 0; i < ecNumbers.size(); i++) {
					if (ecNumberReactionMap.containsKey(ecNumbers.get(i))) {
						ArrayList<SBMLReaction> rxnsList = ecNumberReactionMap.get(ecNumbers.get(i));
						rxnsList.add(reaction);
						ecNumberReactionMap.put(ecNumbers.get(i), rxnsList);
					} else {
						ArrayList<SBMLReaction> rxnsList = new ArrayList<SBMLReaction>();
						rxnsList.add(reaction);
						ecNumberReactionMap.put(ecNumbers.get(i), rxnsList);
					}
				}
			}
		}
		LocalConfig.getInstance().setEcNumberReactionMap(ecNumberReactionMap);
		//System.out.println("ec " + ecNumberReactionMap);
	}
	
}
