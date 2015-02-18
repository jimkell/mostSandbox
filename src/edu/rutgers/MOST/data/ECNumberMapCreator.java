package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.presentation.PathwaysFrameConstants;

public class ECNumberMapCreator {

	// need fluxes to get max and secondary max if there are infinite fluxes
	// usually in column as something like 999999.0
	private ArrayList<Double> fluxes = new ArrayList<Double>();
	
	private double maxUpperBound;
	private double secondaryMaxFlux;

	/**
	 * EC Number map created to be used for getting information from loaded
	 * model by EC Number
	 */
	public void createEcNumberReactionMap() {
		Map<String, ArrayList<SBMLReaction>> ecNumberReactionMap = new HashMap<String, ArrayList<SBMLReaction>>();
		ReactionFactory rf = new ReactionFactory("SBML");
		Vector<SBMLReaction> rxns = rf.getAllReactions();
		maxUpperBound = 0;
		for (int r = 0; r < rxns.size(); r++) {
			SBMLReaction reaction = (SBMLReaction) rxns.get(r);
			String ecString = reaction.getEcNumber();
			int id = reaction.getId();
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
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(id)) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(id));
				}
			}
			fluxes.add(reaction.getFluxValue());
			if (reaction.getUpperBound() > maxUpperBound) {
				maxUpperBound = reaction.getUpperBound();
			}
		}
		for (int j = 0; j< fluxes.size(); j++) {
			if (Math.abs(fluxes.get(j)) <= PathwaysFrameConstants.INFINITE_FLUX_RATIO*maxUpperBound) {
				if (Math.abs(fluxes.get(j)) > secondaryMaxFlux) {
					secondaryMaxFlux = Math.abs(fluxes.get(j));
				}
			}
		}
		Collections.sort(LocalConfig.getInstance().getUnplottedReactionIds());
		System.out.println("not plotted " + LocalConfig.getInstance().getUnplottedReactionIds());
		LocalConfig.getInstance().setEcNumberReactionMap(ecNumberReactionMap);
		LocalConfig.getInstance().setFluxes(fluxes);
		LocalConfig.getInstance().setMaxUpperBound(maxUpperBound);
		LocalConfig.getInstance().setSecondaryMaxFlux(secondaryMaxFlux);
		//System.out.println("ec " + ecNumberReactionMap);
		ArrayList<String> keys = new ArrayList<String>(ecNumberReactionMap.keySet());
		Collections.sort(keys);
		//System.out.println("keys " + keys);
		for (int j = 0; j < keys.size(); j++) {
			ArrayList<String> sideReactants = new ArrayList<String>();
			ArrayList<String> sideProducts = new ArrayList<String>();
			if (LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)) != null) {
				if (LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getCatalyticActivity() == null) {
					//System.out.println(keys.get(j) + " " + LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getDescription());
				} else {
					//System.out.println(keys.get(j) + " " + LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getCatalyticActivity());
					String[] halfReactions = LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getCatalyticActivity().split(" = ");
					for (int k = 0; k < LocalConfig.getInstance().getSideSpeciesList().size(); k++) {
						if (halfReactions.length > 0 && halfReactions[0].contains(LocalConfig.getInstance().getSideSpeciesList().get(k))) {
							sideReactants.add(LocalConfig.getInstance().getSideSpeciesList().get(k));
						}
						if (halfReactions.length > 1 && halfReactions[1].contains(LocalConfig.getInstance().getSideSpeciesList().get(k))) {
							sideProducts.add(LocalConfig.getInstance().getSideSpeciesList().get(k));
						}
					}
				}
			}
//			System.out.println("sr " + sideReactants);
//			System.out.println("sp " + sideProducts);
		}
	}
	
}
