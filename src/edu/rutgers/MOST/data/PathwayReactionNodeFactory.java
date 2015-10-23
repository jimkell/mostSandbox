package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.presentation.GraphicalInterface;
import edu.rutgers.MOST.presentation.PathwaysFrameConstants;

public class PathwayReactionNodeFactory {

	/**
	 * Creates PathwayReactionNode for a given compartment from list of EC Numbers.
	 * If no compartment specified, node created for entire list of EC Numbers.
	 * SBMLReactions and parameters from enzyme.dat attributes set. 
	 * @param ec
	 * @param compartment
	 * @return
	 */
	public PathwayReactionNode createPathwayReactionNode(ArrayList<String> ec, ArrayList<String> keggReactionIds, 
			ArrayList<String> keggReactantIds, ArrayList<String> keggProductIds, String compartment, int component, 
			Vector<SBMLReaction> allReactions, Map<Integer, SBMLReaction> idReactionMap) {
		PathwayReactionNode pn = new PathwayReactionNode();
		//ArrayList<String> sideReactants = new ArrayList<String>();
		//ArrayList<String> sideProducts = new ArrayList<String>();
		//ArrayList<String> enzymeDataEquations = new ArrayList<String>();
		ArrayList<SBMLReaction> reactions = new ArrayList<SBMLReaction>();
		
		for (int m = 0; m < ec.size(); m++) {
			if (LocalConfig.getInstance().getEcNumberReactionMap().containsKey(ec.get(m))) {
				// attributes from SBML Reaction
				ArrayList<SBMLReaction> reac = LocalConfig.getInstance().getEcNumberReactionMap().get(ec.get(m));
				if (component == PathwaysFrameConstants.PROCESSES_COMPONENT) {
					//reactions.add(reac.get(0));
				} else {
					addReactions(reactions, reac, compartment, keggReactantIds, keggProductIds, false);
				}
				// attributes from Enzyme.dat
//				if (LocalConfig.getInstance().getEnzymeDataMap().containsKey(ec.get(m))) {
//					if (LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity() == null) {
//						// description can have alternate numbers. need to get these
//						//System.out.println(keys.get(j) + " " + LocalConfig.getInstance().getEnzymeDataMap().get(keys.get(j)).getDescription());
//					} else {
//						enzymeDataEquations.add(LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity());
//						String[] halfReactions = LocalConfig.getInstance().getEnzymeDataMap().get(ec.get(m)).getCatalyticActivity().split(" = ");
//						for (int n = 0; n < LocalConfig.getInstance().getSideSpeciesList().size(); n++) {
//							if (halfReactions[0].contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
//								if (!sideReactants.contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
//									sideReactants.add(LocalConfig.getInstance().getSideSpeciesList().get(n));
//								}
//							}
//							if (halfReactions.length > 1 && halfReactions[1].contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
//								if (!sideProducts.contains(LocalConfig.getInstance().getSideSpeciesList().get(n))) {
//									sideProducts.add(LocalConfig.getInstance().getSideSpeciesList().get(n));
//								}
//							}
//						}
//					}
//				}		
			}
		}
		for (int n = 0; n < keggReactionIds.size(); n++) {
			if (LocalConfig.getInstance().getKeggIdReactionMap() != null && 
					LocalConfig.getInstance().getKeggIdReactionMap().containsKey(keggReactionIds.get(n))) {
				ArrayList<SBMLReaction> reac = LocalConfig.getInstance().getKeggIdReactionMap().get(keggReactionIds.get(n));
				if (keggReactionIds.size() == 1 && component == PathwaysFrameConstants.PROCESSES_COMPONENT) {
					//reactions.add(reac.get(0));
				} else {
					addReactions(reactions, reac, compartment, keggReactantIds, keggProductIds, false);
				}
			}
		}
		//System.out.println("ni " + LocalConfig.getInstance().getNoIdentifierIds());
		for (int i = 0; i < LocalConfig.getInstance().getNoIdentifierIds().size(); i++) {
			SBMLReaction r = idReactionMap.get(LocalConfig.getInstance().getNoIdentifierIds().get(i));
			ArrayList<SBMLReaction> reac = new ArrayList<SBMLReaction>();
			reac.add(r);
			//System.out.println(reac);
			if (component == PathwaysFrameConstants.PROCESSES_COMPONENT) {
				//reactions.add(reac.get(0));
			} else {
				addReactions(reactions, reac, compartment, keggReactantIds, keggProductIds, true);
			}
		}
//		System.out.println("ids " + LocalConfig.getInstance().getIdentifierIds());
//		System.out.println("no ids " + LocalConfig.getInstance().getNoIdentifierIds());
		//System.out.println("un " + LocalConfig.getInstance().getUnplottedReactionIds());
		// if reaction does not have an ec number or kegg reaction id, have to check all unplotted
		// reactions for a match
//		if (reactions.size() == 0) {
//			ReactionFactory f = new ReactionFactory("SBML");
//			Vector<SBMLReaction> allReactions = f.getAllReactions();
//			Map<String, SBMLReaction> reactionMap = new HashMap<String, SBMLReaction>();
//			for (int j = 0; j < allReactions.size(); j++) {
//				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(allReactions.get(j).getId())) {
//					reactionMap.put(Integer.toString(allReactions.get(j).getId()), allReactions.get(j));
//				}
//			}
//			for (int i = 0; i < LocalConfig.getInstance().getUnplottedReactionIds().size(); i++) {
//				if (LocalConfig.getInstance().getModelKeggEquationMap().containsKey(Integer.toString(LocalConfig.getInstance().getUnplottedReactionIds().get(i)))) {
//					PathwayReactionData modelData = LocalConfig.getInstance().getModelKeggEquationMap().get(Integer.toString(LocalConfig.getInstance().getUnplottedReactionIds().get(i)));
//					//System.out.println("data " + modelData);
//					boolean matchFound = false;
//					if (speciesMatch(keggReactantIds, modelData.getKeggReactantIds()) && 
//							speciesMatch(keggProductIds, modelData.getKeggProductIds())) {
//						matchFound = true;
//					} else if (speciesMatch(keggProductIds, modelData.getKeggReactantIds()) && 
//							speciesMatch(keggReactantIds, modelData.getKeggProductIds())) {
//						matchFound = true;
//					}
//					if (matchFound) {
//						int id = Integer.parseInt(modelData.getReactionId());
//						//int id = reactionMap.get(Integer.toString(LocalConfig.getInstance().getUnplottedReactionIds().get(i))).getId();
//						ArrayList<Integer> idList = new ArrayList<Integer>();
//						for (int r = 0; r < reactions.size(); r++) {
//							if (reactions.get(r) != null) {
//								idList.add(reactions.get(r).getId());
//							}
//						}
//						if (!idList.contains(id)) {
//							reactions.add(reactionMap.get(Integer.toString(LocalConfig.getInstance().getUnplottedReactionIds().get(i))));
//						}
//					}
//					
//					if (correctMainSpecies(reactionMap.get(modelData.getReactionId()), 
//							modelData.getKeggReactantIds(), modelData.getKeggProductIds())) {
//						// since ec map and kegg reaction id map have values that are lists,
//						// must add SBML Reaction to list to use addReactions method
//						ArrayList<SBMLReaction> list = new ArrayList<SBMLReaction>();
//						list.add(reactionMap.get(Integer.toString(LocalConfig.getInstance().getUnplottedReactionIds().get(i))));
////						addReactions(reactions, list, compartment, 
////								modelData.getKeggReactantIds(), modelData.getKeggProductIds());
//					} 
//				}
//			}
			//System.out.println("found rxn " + reactions);
//		}
		//System.out.println(LocalConfig.getInstance().getUnplottedReactionIds());
		//pn.setPathwayId(pathway.getId());
//		pn.setSideReactants(sideReactants);
//		pn.setSideProducts(sideProducts);
//		pn.setEnzymeDataEquations(enzymeDataEquations);
		pn.setReactions(reactions);
//		System.out.println(reactions);
		
		return pn;
	}
	
	public void addReactions(ArrayList<SBMLReaction> reactions, ArrayList<SBMLReaction> reac, String compartment, 
			ArrayList<String> keggReactantIds, ArrayList<String> keggProductIds, boolean exactMatch) {
		for (int r = 0; r < reac.size(); r++) {
			// if compartment not defined, just draw everything for now
			if (compartment != null && compartment.length() > 0) {
				if (reac.get(r) != null) {
					SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(reac.get(r).getId());
					if (equn.getCompartmentList().size() == 1 && equn.getCompartmentList().contains(compartment)) {
						addReactionIfNotPresent(reactions, reac.get(r), keggReactantIds, keggProductIds, exactMatch);
						if (LocalConfig.getInstance().getUnplottedReactionIds().contains(reac.get(r).getId())) {
							//System.out.println(reac.get(r).getId());
							LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(reac.get(r).getId()));
						}
					} else {
		
					}
				}
			} else {
				//addReactionIfNotPresent(reactions, reac.get(r), keggReactantIds, keggProductIds);
			}
		}
	}
	
	/**
	 * Contains not working for adding SBMLReactions to list
	 * @param reac
	 * @param r
	 */
	public void addReactionIfNotPresent(ArrayList<SBMLReaction> reactions, SBMLReaction r,
			ArrayList<String> keggReactantIds, ArrayList<String> keggProductIds, boolean exactMatch) {
		int id = r.getId();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for (int i = 0; i < reactions.size(); i++) {
			idList.add(reactions.get(i).getId());
		}
		if (!idList.contains(id)) {
			if (correctMainSpecies(r, keggReactantIds, keggProductIds, exactMatch)) {
				reactions.add(r);
				//System.out.println(reactions);
			} 
		}
	}
	
	/**
	 * Return true if all KEGG Ids of metabolite nodes in a reaction are present, else false
	 * @param r
	 * @param keggReactantIds
	 * @param keggProductIds
	 * @return
	 */
	public boolean correctMainSpecies(SBMLReaction r, ArrayList<String> keggReactantIds, ArrayList<String> keggProductIds, 
			boolean exactMatch) {
		boolean match = false;
		if (r != null && LocalConfig.getInstance().getModelKeggEquationMap().containsKey(Integer.toString(r.getId()))) {
			PathwayReactionData modelData = LocalConfig.getInstance().getModelKeggEquationMap().get(Integer.toString(r.getId()));
//			System.out.println(modelData);
//			System.out.println("m " + modelData.getKeggReactantIds());
//			System.out.println("m " + modelData.getKeggProductIds());
//			System.out.println("d " + keggReactantIds);
//			System.out.println("d " + keggProductIds);
			if (exactMatch) {
				if (speciesExactMatch(keggReactantIds, modelData.getKeggReactantIds()) && 
						speciesExactMatch(keggProductIds, modelData.getKeggProductIds())) {
					match = true;
				} else if (speciesExactMatch(keggReactantIds, modelData.getKeggProductIds()) && 
						speciesExactMatch(keggProductIds, modelData.getKeggReactantIds())) {
					match = true;
				}
			} else {
				if (speciesMatch(keggReactantIds, modelData.getKeggReactantIds()) && 
						speciesMatch(keggProductIds, modelData.getKeggProductIds())) {
					match = true;
				} else if (speciesMatch(keggReactantIds, modelData.getKeggProductIds()) && 
						speciesMatch(keggProductIds, modelData.getKeggReactantIds())) {
					match = true;
				}
			}
		}
		
		return match;
		
	}
	
	/**
	 * Checks if all KEGG Ids from data are present in KEGG Id list from model.
	 * @param dataIds
	 * @param modelIds
	 * @return
	 */
	public boolean speciesMatch(ArrayList<String> dataIds, ArrayList<String> modelIds) {
		boolean speciesMatch = true;
		ArrayList<String> data = removedSpeciesBeforeComparison(VisualizationConstants.IGNORE_FOR_IDENTIFIED_REACTION_LIST, dataIds);
		for (int i = 0; i < data.size(); i++) {
			if (!modelIds.contains(data.get(i))) {
				speciesMatch = false;
				break;
			}
		}
		return speciesMatch;
	}
	
	/**
	 * Checks for exact match from data are present in KEGG Id list from model.
	 * Some species removed before check.
	 * @param dataIds
	 * @param modelIds
	 * @return
	 */
	public boolean speciesExactMatch(ArrayList<String> dataIds, ArrayList<String> modelIds) {
		boolean speciesMatch = false;
		//System.out.println(modelIds);
		ArrayList<String> data = removedSpeciesBeforeComparison(VisualizationConstants.REMOVE_BEFORE_REACTION_COMPARISON, dataIds);
		ArrayList<String> model = removedSpeciesBeforeComparison(VisualizationConstants.REMOVE_BEFORE_REACTION_COMPARISON, modelIds);
		//System.out.println(model);
		Collections.sort(data);
		Collections.sort(model);
		if (data.equals(model)) {
			speciesMatch = true;
		} 
		if (speciesMatch) {
//			System.out.println(speciesMatch);
//			System.out.println(data);
//			System.out.println(model);
		}
		return speciesMatch;
	}
	
	public ArrayList<String> removedSpeciesBeforeComparison(String[] removeList, ArrayList<String> list) {
		for (int i = 0; i < removeList.length; i++) {
			if (list.contains(removeList[i])) {
				list.remove(list.indexOf(removeList[i]));
			}
		}
		return list;
	}
	
	/** 
	 * Display name for tooltip created from input names if length of list of SBMLReactions = 0.
	 * Else display name created from SBMLReaction attributes.  
	 * @param displayName
	 * @param name
	 * @param reactions
	 * @return
	 */
	public String createDisplayName(String displayName, String name, ArrayList<SBMLReaction> reactions, Map<Integer, SBMLReaction> idReactionMap) {
		ArrayList<String> reactionAbbrevations = new ArrayList<String>();
		ArrayList<String> reactionNames = new ArrayList<String>();
		ArrayList<String> ecNumbers = new ArrayList<String>();
		ArrayList<String> keggReactionIds = new ArrayList<String>();
		ArrayList<String> equations = new ArrayList<String>();
		ArrayList<String> subsystems = new ArrayList<String>();
		ArrayList<Double> fluxes = new ArrayList<Double>();
		if (reactions.size() > 0) {
			for (int i = 0; i < reactions.size(); i++) {
				if (reactions.get(i) != null) {
					if (!reactionAbbrevations.contains(reactions.get(i).getReactionAbbreviation())) {
						reactionAbbrevations.add(reactions.get(i).getReactionAbbreviation());
					}
					if (!reactionNames.contains(reactions.get(i).getReactionName())) {
						reactionNames.add(reactions.get(i).getReactionName());
					}
					if (!ecNumbers.contains(reactions.get(i).getEcNumber())) {
						ecNumbers.add(reactions.get(i).getEcNumber());
					}
					// since ec number reaction map made before kegg reaction ids assigned, these are
					// null in ec list and need to be obtained from map made more recently
					if (reactions.get(i) != null && idReactionMap.containsKey(reactions.get(i).getId())) {
						String keggReactionId = idReactionMap.get(reactions.get(i).getId()).getKeggReactionId();
						if (keggReactionId != null && keggReactionId.length() > 0) {
							if (!keggReactionIds.contains(keggReactionId)) {
								keggReactionIds.add(keggReactionId);
							}
						}
					}
					if (!equations.contains(reactions.get(i).getReactionEqunAbbr())) {
						String htmlEquation = reactions.get(i).getReactionEqunAbbr().replace("<", "&lt;");
						equations.add(htmlEquation);
					}
					if (!subsystems.contains(reactions.get(i).getSubsystem())) {
						subsystems.add(reactions.get(i).getSubsystem());
					}
//					if (!fluxes.contains(reactions.get(i).getFluxValue())) {
						fluxes.add(reactions.get(i).getFluxValue());
//					}
					//System.out.println("flux " + reactions.get(i).getFluxValue() + " log " + Math.log10(Math.abs(reactions.get(i).getFluxValue())));
				}
			}
			displayName = "<html>" + displayName(reactionAbbrevations)
					+ displayReactionName(reactionNames)
					+ displayReactionAbbreviation(reactionAbbrevations)
					+ displayECNumber(ecNumbers)
					+ displayKeggReactionId(keggReactionIds)
					+ "<p> Equation: " + name
					+ displaySubsystem(subsystems)
					+ displayModelEquation(equations)
					+ "<p> Fluxes: " + fluxes.toString();
		}
		return displayName;
	}
	
	public String displayName(ArrayList<String> reactionNames) {
		String rn = "";
		if (reactionNames.size() > 0) {
			rn = reactionNames.get(0);
		}
		if (reactionNames.size() > 1) {
			rn = reactionNames.toString();
		}
		return rn;
	}
	
	public String displayReactionAbbreviation(ArrayList<String> reactionAbbrevations) {
		return maybeMakeList(reactionAbbrevations, "Reaction Abbreviation");
	}
	
//	public String displayReactionName(ArrayList<String> reactionNames) {
//		return maybeMakeList(reactionNames, "Reaction Name");
//	}
	
	public String displayECNumber(ArrayList<String> ecnumbers) {
		return maybeMakeList(ecnumbers, "EC Number");
	}
	
	public String displayKeggReactionId(ArrayList<String> keggReactionIds) {
		return maybeMakeList(keggReactionIds, "KEGG Reaction Id");
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
		String item = "<p>" + heading + ": ";
		if (items.size() > 0) {
			item = "<p>" + heading + ": " + items.get(0);
		}
		if (items.size() > 1) {
			item = "<p>" + heading + "(s): " + items.toString();
		}
		return item;
	}
	
	public String displayReactionName(ArrayList<String> names) {
		// since equations can be quite long and a list of reactions may not fit on screen,
		// each reaction is put on a separate line
		String reactionNameString = "";
		if (names.size() > 0) {
			reactionNameString = "<p>Reaction Name: " + names.get(0);
		}
		if (names.size() > 1) {
			reactionNameString = "<p>Reaction Names: " + names.get(0);
			for (int m = 1; m < names.size(); m++) {
				reactionNameString += ", <p>" + names.get(m);
			}
		}
		return reactionNameString;
	}
	
	public String displayModelEquation(ArrayList<String> equations) {
		// since equations can be quite long and a list of reactions may not fit on screen,
		// each reaction is put on a separate line
		String modelEquationString = "";
		if (equations.size() > 0) {
			modelEquationString = "<p>Equation from Model: " + equations.get(0);
		}
		if (equations.size() > 1) {
			modelEquationString = "<p>Equations from Model: " + equations.get(0);
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
