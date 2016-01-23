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
	
	//ArrayList<String> substitutionKeys = new ArrayList<String>(LocalConfig.getInstance().getMetaboliteSubstitutionsMap().keySet());
	private Map<String, ArrayList<String>> renameMetabolitesMap = new HashMap<String, ArrayList<String>>();

	public Map<String, ArrayList<String>> getRenameMetabolitesMap() {
		return renameMetabolitesMap;
	}

	public void setRenameMetabolitesMap(
			Map<String, ArrayList<String>> renameMetabolitesMap) {
		this.renameMetabolitesMap = renameMetabolitesMap;
	}

	public PathwayReactionNode createPathwayReactionNode(PathwayReactionData prd, 
			String compartment, int component, 
			Vector<SBMLReaction> allReactions, Map<Integer, SBMLReaction> idReactionMap) {
		PathwayReactionNode pn = new PathwayReactionNode();
		ArrayList<SBMLReaction> reactions = new ArrayList<SBMLReaction>();
		
		for (int m = 0; m < prd.getEcNumbers().size(); m++) {
			if (LocalConfig.getInstance().getEcNumberReactionMap().containsKey(prd.getEcNumbers().get(m))) {
				// attributes from SBML Reaction
				ArrayList<SBMLReaction> reac = LocalConfig.getInstance().getEcNumberReactionMap().get(prd.getEcNumbers().get(m));
//				if (component == PathwaysFrameConstants.PROCESSES_COMPONENT) {
//					//reactions.add(reac.get(0));
//				} else {
					addReactions(reactions, reac, compartment, prd, false);
//				}
			} 
		}
		for (int n = 0; n < prd.getKeggReactionIds().size(); n++) {
			if (LocalConfig.getInstance().getKeggIdReactionMap() != null && 
					LocalConfig.getInstance().getKeggIdReactionMap().containsKey(prd.getKeggReactionIds().get(n))) {
				ArrayList<SBMLReaction> reac = LocalConfig.getInstance().getKeggIdReactionMap().get(prd.getKeggReactionIds().get(n));
//				if (keggReactionIds.size() == 1 && component == PathwaysFrameConstants.PROCESSES_COMPONENT) {
//					//reactions.add(reac.get(0));
//				} else {
					addReactions(reactions, reac, compartment, prd, false);
//				}
			}
		}
//		System.out.println("ni " + LocalConfig.getInstance().getNoIdentifierIds());
		for (int i = 0; i < LocalConfig.getInstance().getNoIdentifierIds().size(); i++) {
			SBMLReaction r = idReactionMap.get(LocalConfig.getInstance().getNoIdentifierIds().get(i));
			ArrayList<SBMLReaction> reac = new ArrayList<SBMLReaction>();
			reac.add(r);
//			if (component == PathwaysFrameConstants.PROCESSES_COMPONENT) {
//				//reactions.add(reac.get(0));
//			} else {
				addReactions(reactions, reac, compartment, prd, true);
//			}
		}
		if (reactions.size() == 0) {
			for (int u = 0; u < LocalConfig.getInstance().getUnplottedReactionIds().size(); u++) {
				SBMLReaction r = idReactionMap.get(LocalConfig.getInstance().getUnplottedReactionIds().get(u));
				ArrayList<SBMLReaction> reac = new ArrayList<SBMLReaction>();
				reac.add(r);
//				if (component == PathwaysFrameConstants.PROCESSES_COMPONENT) {
//					//reactions.add(reac.get(0));
//				} else {
					addReactions(reactions, reac, compartment, prd, true);
//				}
			}
		}
		pn.setReactions(reactions);
		
		return pn;
	}
	
	// TODO: check if compartment is redundant since reactions from reaction factory are already
	// given by compartment
	public void addReactions(ArrayList<SBMLReaction> reactions, ArrayList<SBMLReaction> reac, String compartment, 
			PathwayReactionData prd, boolean exactMatch) {
		for (int r = 0; r < reac.size(); r++) {
			// if compartment not defined, just draw everything for now
			if (compartment != null && compartment.length() > 0) {
				if (reac.get(r) != null) {
					SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(reac.get(r).getId());
					if (equn.getCompartmentList().size() == 1 && equn.getCompartmentList().contains(compartment)) {
						if (addReactionIfNotPresent(reactions, reac.get(r), prd, exactMatch)) {
							if (LocalConfig.getInstance().getUnplottedReactionIds().contains(reac.get(r).getId())) {
								//System.out.println(reac.get(r).getId());
								LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(reac.get(r).getId()));
							}
						}
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
	public boolean addReactionIfNotPresent(ArrayList<SBMLReaction> reactions, SBMLReaction r,
			PathwayReactionData prd, boolean exactMatch) {
		boolean match = false;
		int id = r.getId();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for (int i = 0; i < reactions.size(); i++) {
			idList.add(reactions.get(i).getId());
		}
		if (!idList.contains(id)) {
			if (correctMainSpecies(r, prd, exactMatch)) {
				reactions.add(r);
				//System.out.println(reactions);
				match = true;
			} else {
				if (!LocalConfig.getInstance().getUnplottedReactionIds().contains(id)) {
					LocalConfig.getInstance().getUnplottedReactionIds().add(id);
				}
			}
		}
		return match;
	}
	
	/**
	 * Return true if all KEGG Ids of metabolite nodes in a reaction are present, else false
	 * @param r
	 * @param keggReactantIds
	 * @param keggProductIds
	 * @return
	 */
	public boolean correctMainSpecies(SBMLReaction r, PathwayReactionData prd, 
			boolean exactMatch) {
		boolean match = false;
		if (r != null && LocalConfig.getInstance().getModelKeggEquationMap().containsKey(Integer.toString(r.getId()))) {
			PathwayReactionData modelData = LocalConfig.getInstance().getModelKeggEquationMap().get(Integer.toString(r.getId()));
//			System.out.println("mr " + modelData.getKeggReactantIds());
//			System.out.println("mp " + modelData.getKeggProductIds());
//			System.out.println("d " + keggReactantIds);
//			System.out.println("d " + keggProductIds);
			if (exactMatch) {
				if (speciesExactMatch(prd.getKeggReactantIds(), prd.getKeggReactantIdsDataMap(), modelData.getKeggReactantIds()) && 
						speciesExactMatch(prd.getKeggProductIds(), prd.getKeggProductIdsDataMap(), modelData.getKeggProductIds())) {
					match = true;
				} else if (speciesExactMatch(prd.getKeggReactantIds(), prd.getKeggReactantIdsDataMap(), modelData.getKeggProductIds()) && 
						speciesExactMatch(prd.getKeggProductIds(), prd.getKeggProductIdsDataMap(), modelData.getKeggReactantIds())) {
					match = true;
				}
			} else {
				if (speciesMatch(prd.getKeggReactantIds(), prd.getKeggReactantIdsDataMap(), modelData.getKeggReactantIds()) && 
						speciesMatch(prd.getKeggProductIds(), prd.getKeggProductIdsDataMap(), modelData.getKeggProductIds())) {
					match = true;
				} else if (speciesMatch(prd.getKeggReactantIds(), prd.getKeggReactantIdsDataMap(), modelData.getKeggProductIds()) && 
						speciesMatch(prd.getKeggProductIds(), prd.getKeggProductIdsDataMap(), modelData.getKeggReactantIds())) {
					match = true;
				}
			}
		}
		
		return match;
		
	}
	
	/**
	 * Checks if all KEGG Ids from data are present in KEGG Id list from model. 
	 * If any species are in alternatives or substitutions, speciesExactMatch
	 * is used.
	 * @param dataIds
	 * @param keggIdsDataMap
	 * @param modelIds
	 * @return
	 */
	public boolean speciesMatch(ArrayList<String> dataIds, Map<String, PathwayMetaboliteData> keggIdsDataMap, ArrayList<String> modelIds) {
//		if (dataIds.contains("C00091") && dataIds.contains("C00011")) {
//			System.out.println("d " + dataIds);
//			System.out.println("m " + modelIds);
//		}
//		System.out.println("d " + dataIds);
//		System.out.println("m " + modelIds);
		boolean speciesMatch = true;
		boolean containsProton = false;
		if (modelIds.contains("C00080")) {
			containsProton = true;
		}
		ArrayList<String> data = dataIds;
		for (int i = 0; i < data.size(); i++) {
			if (!modelIds.contains(data.get(i))) {
				speciesMatch = false;
				if (!speciesMatch) {
					break;
				} 
			}
		}
		//System.out.println(speciesMatch);
		if (speciesMatch) {
			for (int j = 0; j < modelIds.size(); j++) {
				if (LocalConfig.getInstance().getAlternateMetabolitesMap().containsKey(modelIds.get(j)) ||
						LocalConfig.getInstance().getMetaboliteSubstitutionsMap().containsKey(modelIds.get(j))) {
					if (keggIdsDataMap.containsKey(modelIds.get(j))) {
						String name = keggIdsDataMap.get(modelIds.get(j)).getName();
						if (renameMetabolitesMap.containsKey(name)) {
							ArrayList<String> keggIds = renameMetabolitesMap.get(name);
							if (!keggIds.contains(modelIds.get(j))) {
								keggIds.add(modelIds.get(j));
								if (containsProton && maybeAddProton(modelIds.get(j)) && !keggIds.contains("C00080")) {
									keggIds.add("C00080");
								}
								renameMetabolitesMap.put(name, keggIds);
							}
						} else {
							ArrayList<String> keggIds = new ArrayList<String>();
							if (!keggIds.contains(modelIds.get(j))) {
								keggIds.add(modelIds.get(j));
								if (containsProton && maybeAddProton(modelIds.get(j)) && !keggIds.contains("C00080")) {
									keggIds.add("C00080");
								}
								renameMetabolitesMap.put(name, keggIds);
							}
						}
					} 
				}
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
	public boolean speciesExactMatch(ArrayList<String> dataIds, Map<String, PathwayMetaboliteData> keggIdsDataMap, ArrayList<String> modelIds) {
		boolean speciesMatch = false;
		boolean containsProton = false;
//		System.out.println("data " + dataIds);
//		System.out.println("model " + modelIds);
		if (modelIds.contains("C00080")) {
			containsProton = true;
		}
		ArrayList<String> data = removedSpeciesBeforeComparison(VisualizationConstants.REMOVE_BEFORE_REACTION_COMPARISON, dataIds);
		ArrayList<String> model = removedSpeciesBeforeComparison(VisualizationConstants.REMOVE_BEFORE_REACTION_COMPARISON, modelIds);
		//System.out.println(model);
		if (data.size() != model.size()) {
			//System.out.println("f");
			return false;
		} 
		Collections.sort(data);
		Collections.sort(model);
		if (data.equals(model)) {
			for (int i = 0; i < model.size(); i++) {
				if (LocalConfig.getInstance().getAlternateMetabolitesMap().containsKey(model.get(i)) ||
						LocalConfig.getInstance().getMetaboliteSubstitutionsMap().containsKey(model.get(i))) {
					String name = keggIdsDataMap.get(model.get(i)).getName();
					if (renameMetabolitesMap.containsKey(name)) {
						ArrayList<String> keggIds = renameMetabolitesMap.get(name);
						if (!keggIds.contains(model.get(i))) {
							keggIds.add(model.get(i));
							if (containsProton && maybeAddProton(model.get(i)) && !keggIds.contains("C00080")) {
								keggIds.add("C00080");
							}
							renameMetabolitesMap.put(name, keggIds);
						}
					} else {
						ArrayList<String> keggIds = new ArrayList<String>();
						if (!keggIds.contains(model.get(i))) {
							keggIds.add(model.get(i));
							if (containsProton && maybeAddProton(model.get(i)) && !keggIds.contains("C00080")) {
								keggIds.add("C00080");
							}
							renameMetabolitesMap.put(name, keggIds);
						}
					}
				}
			}
			speciesMatch = true;
		} else {
			ArrayList<String> data1 = new ArrayList<String>();
			ArrayList<String> model1 = new ArrayList<String>();
			// copy lists to avoid altering original lists for future comparison
			for (int i = 0; i < data.size(); i++) {
				data1.add(data.get(i));
			}
			for (int i = 0; i < model.size(); i++) {
				model1.add(model.get(i));
			}
//			System.out.println("d1 " + data1);
//			System.out.println("m1 " + model1);
			// remove all common entries
			for (int i = 0; i < data1.size(); i++) {
				if (model1.contains(data1.get(i))) {
					String ki = data1.get(i);
					model1.remove(model1.indexOf(data1.get(i)));
					data1.remove(data1.indexOf(data1.get(i)));
					// add any removed keys in alternate or substitution to map
					if (LocalConfig.getInstance().getAlternateMetabolitesMap().containsKey(ki) ||
							LocalConfig.getInstance().getMetaboliteSubstitutionsMap().containsKey(ki)) {
						if (keggIdsDataMap.containsKey(ki)) {
							String name = keggIdsDataMap.get(ki).getName();
							if (renameMetabolitesMap.containsKey(name)) {
								ArrayList<String> keggIds = renameMetabolitesMap.get(name);
								if (!keggIds.contains(ki)) {
									keggIds.add(ki);
									if (containsProton && maybeAddProton(ki) && !keggIds.contains("C00080")) {
										keggIds.add("C00080");
									}
									renameMetabolitesMap.put(name, keggIds);
								}
							} else {
								ArrayList<String> keggIds = new ArrayList<String>();
								if (!keggIds.contains(ki)) {
									keggIds.add(ki);
									if (containsProton && maybeAddProton(ki) && !keggIds.contains("C00080")) {
										keggIds.add("C00080");
									}
									renameMetabolitesMap.put(name, keggIds);
								}
							}
						} 
					}
				}
			}
			// may not be necessary, should already be sorted
			Collections.sort(data1);
			Collections.sort(model1);
//			System.out.println("d2 " + data1);
//			System.out.println("m2 " + model1);
			ArrayList<String> model2 = new ArrayList<String>();
			// data1 and model1 should be same size. replace substitutions and alternates (if side
			// species) with keys to enforce A -> B model where if B is present it is considered
			// to be a replacement for A. Substitutions can be main nodes or side nodes, alternates 
			// can only be side nodes. This prevents NAD, ATP main nodes from having multiple 
			// entries, but fatty acids can have multiple entries.
			Map<String, String> nameReplacedId = new HashMap<String, String>();
			for (int i = 0; i < data1.size(); i++) {
				String entry = model1.get(i);
				if (LocalConfig.getInstance().getMetaboliteSubstitutionsMap().containsKey(data1.get(i))) {
//					System.out.println("s " + LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get(data1.get(i)));
					for (int j = 0; j < model1.size(); j++) {
						if (LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get(data1.get(i)).contains(model1.get(j))) {
//							System.out.println("ks " + data1.get(i));
//							System.out.println("ms " + model1.get(j));
							if (keggIdsDataMap.containsKey(data1.get(i))) {
//								System.out.println("ds " + keggIdsDataMap.get(data1.get(i)).getName());
								// replace substitution with key
								entry = data1.get(i);
								nameReplacedId.put(keggIdsDataMap.get(data1.get(i)).getName(), model1.get(j));
							}
						} 
					}
				} else if (keggIdsDataMap.get(data1.get(i)).getType().equals(PathwaysCSVFileConstants.SIDE_METABOLITE_TYPE) &&
						LocalConfig.getInstance().getAlternateMetabolitesMap().containsKey(data1.get(i))) {
					for (int j = 0; j < model1.size(); j++) {
						if (LocalConfig.getInstance().getAlternateMetabolitesMap().get(data1.get(i)).contains(model1.get(j))) {
//							System.out.println("ka " + data1.get(i));
//							System.out.println("ma " + model1.get(j));
							if (keggIdsDataMap.containsKey(data1.get(i))) {
//								System.out.println("da " + keggIdsDataMap.get(data1.get(i)).getName());
								// replace alternate with key
								entry = data1.get(i);
								nameReplacedId.put(keggIdsDataMap.get(data1.get(i)).getName(), model1.get(j));
							}
						} 
					}
				}
				model2.add(entry);
			}
			Collections.sort(model2);
			if (data1.equals(model2)) {
//				System.out.println("d " + data1);
//				System.out.println("1 " + model1);
//				System.out.println("2 " + model2);
				speciesMatch = true;
//				System.out.println(nameReplacedId);
				ArrayList<String> names = new ArrayList<String>(nameReplacedId.keySet());
				for (int i = 0; i < names.size(); i++) {
					if (renameMetabolitesMap.containsKey(names.get(i))) {
						ArrayList<String> keggIds = renameMetabolitesMap.get(names.get(i));
						if (!keggIds.contains(nameReplacedId.get(names.get(i)))) {
							keggIds.add(nameReplacedId.get(names.get(i)));
							if (containsProton && maybeAddProton(nameReplacedId.get(names.get(i))) && !keggIds.contains("C00080")) {
								keggIds.add("C00080");
							}
							renameMetabolitesMap.put(names.get(i), keggIds);
						}
					} else {
						ArrayList<String> keggIds = new ArrayList<String>();
						if (!keggIds.contains(nameReplacedId.get(names.get(i)))) {
							keggIds.add(nameReplacedId.get(names.get(i)));
							if (containsProton && maybeAddProton(nameReplacedId.get(names.get(i))) && !keggIds.contains("C00080")) {
								keggIds.add("C00080");
							}
							renameMetabolitesMap.put(names.get(i), keggIds);
						}
					}
				}
			}
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
	
	public boolean maybeAddProton(String keggId) {
		//System.out.println(keggId);
		for (int i = 0; i < VisualizationConstants.REDUCED_SPECIES_WITH_PROTON.length; i++) {
			if (VisualizationConstants.REDUCED_SPECIES_WITH_PROTON[i].equals(keggId)) {
				return true;
			} else if (LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get("C00030").contains(keggId) ||
					LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get("C00028").contains(keggId)) {
				return true;
			}
		}
		return false;
		
	}
	
	/** 
	 * Display name for tooltip created from input names if length of list of SBMLReactions = 0.
	 * Else display name created from SBMLReaction attributes.  
	 * @param displayName
	 * @param name
	 * @param reactions
	 * @return
	 */
	public String createDisplayName(String displayName, String name, String id, ArrayList<SBMLReaction> reactions, Map<Integer, SBMLReaction> idReactionMap) {
		ArrayList<String> reactionAbbrevations = new ArrayList<String>();
		ArrayList<String> reactionNames = new ArrayList<String>();
		ArrayList<String> ecNumbers = new ArrayList<String>();
		ArrayList<String> keggReactionIds = new ArrayList<String>();
		ArrayList<String> equations = new ArrayList<String>();
		ArrayList<String> equationNames = new ArrayList<String>();
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
					if (reactions.get(i).getEcNumber() != null && reactions.get(i).getEcNumber().length() > 0 && !ecNumbers.contains(reactions.get(i).getEcNumber())) {
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
					if (!equationNames.contains(reactions.get(i).getReactionEqunNames())) {
						String htmlEquation = reactions.get(i).getReactionEqunNames().replace("<", "&lt;");
						equationNames.add(htmlEquation);
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
					+ displayModelEquation(equationNames)
					+ "<p> Fluxes: " + fluxes.toString()
					+ "<p> Reaction ID: " + id;
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
