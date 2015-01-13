package edu.rutgers.MOST.data;

import java.util.ArrayList;

import edu.rutgers.MOST.config.LocalConfig;

public class PathwayReactionNodeFactory {

	/**
	 * Creates PathwayReactionNode for a given compartment from list of EC Numbers.
	 * If no compartment specified, node created for entire list of EC Numbers.
	 * SBMLReactions and parameters from enzyme.dat attributes set. 
	 * @param ec
	 * @param compartment
	 * @return
	 */
	public PathwayReactionNode createPathwayReactionNode(ArrayList<String> ec, String compartment) {
		PathwayReactionNode pn = new PathwayReactionNode();
		ArrayList<String> sideReactants = new ArrayList<String>();
		ArrayList<String> sideProducts = new ArrayList<String>();
		ArrayList<String> enzymeDataEquations = new ArrayList<String>();
		ArrayList<SBMLReaction> reactions = new ArrayList<SBMLReaction>();
		
		for (int m = 0; m < ec.size(); m++) {
			if (LocalConfig.getInstance().getEcNumberReactionMap().containsKey(ec.get(m))) {
				// attributes from SBML Reaction
				ArrayList<SBMLReaction> reac = LocalConfig.getInstance().getEcNumberReactionMap().get(ec.get(m));
				for (int r = 0; r < reac.size(); r++) {
					// if compartment not defined, just draw everything for now
					if (compartment != null && compartment.length() > 0) {
						SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(reac.get(r).getId());
						if (equn.getCompartmentList().size() == 1 && equn.getCompartmentList().contains(compartment)) {
							reactions.add(reac.get(r));
						} else {
							// uncomment to show that reactions are eliminated if not correct compartment
//							System.out.println("n c " + equn.getCompartmentList());
//							System.out.println(ec);
						}
					} else {
						reactions.add(reac.get(r));
					}
				}
				// attributes from Enzyme.dat
				if (LocalConfig.getInstance().getEnzymeDataMap().containsKey(ec.get(m))) {
					if (LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity() == null) {
						// description can have alternate numbers. need to get these
						//System.out.println(keys.get(j) + " " + LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getDescription());
					} else {
						enzymeDataEquations.add(LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity());
						String[] halfReactions = LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity().split(" = ");
						for (int n = 0; n < LocalConfig.getInstance().getSideSpeciesList().size(); n++) {
							if (halfReactions[0].contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
								if (!sideReactants.contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
									sideReactants.add(LocalConfig.getInstance().getSideSpeciesList().get(n));
								}
							}
							if (halfReactions[1].contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
								if (!sideProducts.contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
									sideProducts.add(LocalConfig.getInstance().getSideSpeciesList().get(n));
								}
							}
						}
					}
				}
						
			}
		}
		//pn.setPathwayId(pathway.getId());
		pn.setSideReactants(sideReactants);
		pn.setSideProducts(sideProducts);
		pn.setEnzymeDataEquations(enzymeDataEquations);
		pn.setReactions(reactions);
		
		return pn;
	}
	
	/**
	 * PathwayConnectionNode created from PathwayReactionNode
	 * @param pn
	 * @return
	 */
	public PathwayConnectionNode createPathwayConnectionNode(PathwayReactionNode pn) {
		PathwayConnectionNode pcn = new PathwayConnectionNode();
		pcn.setSideReactants(pn.getSideReactants());
		pcn.setSideProducts(pn.getSideProducts());
		pcn.setEnzymeDataEquations(pn.getEnzymeDataEquations());
		pcn.setReactions(pn.getReactions());
		
		return pcn;
	}
	
	/** 
	 * Display name for tooltip created from input names if length of list of SBMLReactions = 0.
	 * Else display name created from SBMLReaction attributes.  
	 * @param displayName
	 * @param name
	 * @param reactions
	 * @return
	 */
	public String createDisplayName(String displayName, String name, ArrayList<SBMLReaction> reactions) {
		ArrayList<String> reactionNames = new ArrayList<String>();
		ArrayList<String> ecNumbers = new ArrayList<String>();
		ArrayList<String> equations = new ArrayList<String>();
		ArrayList<String> subsystems = new ArrayList<String>();
		if (reactions.size() > 0) {
			for (int i = 0; i < reactions.size(); i++) {
				if (!reactionNames.contains(reactions.get(i).getReactionName())) {
					reactionNames.add(reactions.get(i).getReactionName());
				}
				if (!ecNumbers.contains(reactions.get(i).getEcNumber())) {
					ecNumbers.add(reactions.get(i).getEcNumber());
				}
				if (!equations.contains(reactions.get(i).getReactionEqunAbbr())) {
					equations.add(reactions.get(i).getReactionEqunAbbr());
				}
				if (!subsystems.contains(reactions.get(i).getSubsystem())) {
					subsystems.add(reactions.get(i).getSubsystem());
				}
			}
			displayName = "<html>" + displayReactionName(reactionNames)
					+ displayECNumber(ecNumbers)
					+ "<p> Equation: " + name
					+ displaySubsystem(subsystems)
					+ displayModelEquation(equations);
		}
		return displayName;
	}
	
	public String displayReactionName(ArrayList<String> reactionNames) {
		String rn = "";
		if (reactionNames.size() > 0) {
			rn = reactionNames.get(0);
		}
		if (reactionNames.size() > 1) {
			rn = reactionNames.toString();
		}
		return rn;
	}
	
	public String displayECNumber(ArrayList<String> ecnumbers) {
		return maybeMakeList(ecnumbers, "EC Number");
	}
	
	public String displaySubsystem(ArrayList<String> subsystems) {
		return maybeMakeList(subsystems, "Subsystem");
	}
	
	/**
	 * Returns plural heading plus list to String if length of input list > 1. 
	 * Else returns singular heading plus input String.
	 * @param items
	 * @param heading
	 * @return
	 */
	public String maybeMakeList(ArrayList<String> items, String heading) {
		String item = "";
		if (items.size() > 0) {
			item = "<p>" + heading + ": " + items.get(0);
		}
		if (items.size() > 1) {
			item = "<p>" + heading + "(s): " + items.toString();
		}
		return item;
	}
	
	public String displayModelEquation(ArrayList<String> equations) {
		// since equations can be quite long and a list of reactions may not fit on screen,
		// each reaction is put on a separate line
		String modelEquationString = "";
		if (equations.size() > 0) {
			modelEquationString = "<p>Equation from Model: " + equations.get(0);
		}
		if (equations.size() > 1) {
			modelEquationString = "<p>Equation(s) from Model: " + equations.get(0);
			for (int m = 1; m < equations.size(); m++) {
				modelEquationString += ", <p>" + equations.get(m);
			}
		}
		return modelEquationString;
	}
	
	public String reversibleString(String reversibleValue) {
		String reversible = "";
		if (reversibleValue.equals("0")) {
			reversible = "false";
		} else if (reversibleValue.equals("1")) {
			reversible = "true";
		}
		return reversible;
	}

}
