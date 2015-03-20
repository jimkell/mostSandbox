package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class KEGGIdReactionMapCreator {
	
	/**
	 * EC Number map created to be used for getting information from loaded
	 * model by EC Number
	 */
	public void createKEGGIdReactionMap() {
		Map<String, ArrayList<SBMLReaction>> keggIdReactionMap = new HashMap<String, ArrayList<SBMLReaction>>();
		ReactionFactory rf = new ReactionFactory("SBML");
		Vector<SBMLReaction> rxns = rf.getAllReactions();
		for (int r = 0; r < rxns.size(); r++) {
			SBMLReaction reaction = (SBMLReaction) rxns.get(r);
			String keggReactionId = reaction.getKeggReactionId();
			int id = reaction.getId();
			if (keggReactionId != null && keggReactionId.length() > 0) {
				if (keggIdReactionMap.containsKey(keggReactionId)) {
					ArrayList<SBMLReaction> rxnsList = keggIdReactionMap.get(keggReactionId);
					rxnsList.add(reaction);
					keggIdReactionMap.put(keggReactionId, rxnsList);
				} else {
					ArrayList<SBMLReaction> rxnsList = new ArrayList<SBMLReaction>();
					rxnsList.add(reaction);
					keggIdReactionMap.put(keggReactionId, rxnsList);
				}
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(id)) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(id));
				}
			}
		}
		Collections.sort(LocalConfig.getInstance().getUnplottedReactionIds());
		//System.out.println("not plotted " + LocalConfig.getInstance().getUnplottedReactionIds());
		//System.out.println("kegg rxn id " + keggIdReactionMap);
		LocalConfig.getInstance().setKeggIdReactionMap(keggIdReactionMap);
//		ArrayList<String> keggList = new ArrayList<String>(keggIdReactionMap.keySet());
//		for (int i = 0; i < keggList.size(); i++) {
//			if (keggIdReactionMap.get(keggList.get(i)).size() == 1) {
//				System.out.println("ident " + keggIdReactionMap.get(keggList.get(i)));
//			}
//		}
	}

}
