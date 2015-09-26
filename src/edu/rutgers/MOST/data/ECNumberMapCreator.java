package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class ECNumberMapCreator {

	// need fluxes to get max and secondary max if there are infinite fluxes
	// usually in column as something like 999999.0
	private ArrayList<Double> fluxes = new ArrayList<Double>();
	
	private double maxUpperBound;

	/**
	 * EC Number map created to be used for getting information from loaded
	 * model by EC Number. To avoid going through reactions twice, max upper bound
	 * found here, flux data processed further by VisualizationFluxesProcessor.
	 * If reaction is identified by EC Number, the only reason it should be rejected is
	 * if main species in reaction are different from main species in visualization diagram.
	 * This often occurs with sugars for example.
	 */
	public void createEcNumberReactionMap(Vector<SBMLReaction> rxns) {
		Map<String, ArrayList<SBMLReaction>> ecNumberReactionMap = new HashMap<String, ArrayList<SBMLReaction>>();
		//ReactionFactory rf = new ReactionFactory("SBML");
		//Vector<SBMLReaction> rxns = rf.getReactionsByCompartment(LocalConfig.getInstance().getCytosolName());
		//Vector<SBMLReaction> rxns = rf.getAllReactions();
		VisualizationFluxesProcessor processor = new VisualizationFluxesProcessor();
		maxUpperBound = 0;
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
			fluxes.add(reaction.getFluxValue());
			if (reaction.getUpperBound() > maxUpperBound) {
				maxUpperBound = reaction.getUpperBound();
			}
		}
		processor.setMaxUpperBound(maxUpperBound);
		processor.setFluxes(fluxes);
		processor.processFluxes();

		LocalConfig.getInstance().setEcNumberReactionMap(ecNumberReactionMap);
		//System.out.println("ec " + ecNumberReactionMap);
	}
	
}
