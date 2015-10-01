package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class VisualizationReactionCategorizer {

	TransportCollectionsCreator transportCollectionsCreator = new TransportCollectionsCreator();
	public void removeExternalReactions() {
		ArrayList<Integer> externalReactionIds = new ArrayList<Integer>();
		MetaboliteFactory f = new MetaboliteFactory("SBML");
		ArrayList<Integer> metaboliteExternalIdList = f.metaboliteExternalIdList();
		//System.out.println("unpl " + LocalConfig.getInstance().getUnplottedReactionIds());
		for (int j = 0; j < LocalConfig.getInstance().getUnplottedReactionIds().size(); j++) {
			int id = LocalConfig.getInstance().getUnplottedReactionIds().get(j);
			SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(id);
			//System.out.println("eq " + equn);
			// get external reactions, not plotted
			ArrayList<SBMLReactant> reactants = equn.getReactants();
			for (int r = 0; r < reactants.size(); r++) {
				int metabId = reactants.get(r).getMetaboliteId();
				if (metaboliteExternalIdList.contains(metabId)) {
					if (!externalReactionIds.contains(id)) {
						externalReactionIds.add(id);
					}
				}
			}
			ArrayList<SBMLProduct> products = equn.getProducts();
			for (int p = 0; p < products.size(); p++) {
				int metabId = products.get(p).getMetaboliteId();
				if (metaboliteExternalIdList.contains(metabId)) {
					if (!externalReactionIds.contains(id)) {
						externalReactionIds.add(id);
					}
				}
			}
		}
		System.out.println("ext rxns " + externalReactionIds);
		LocalConfig.getInstance().setExternalReactionIds(externalReactionIds);
		for (int n = 0; n < externalReactionIds.size(); n++) {
			if (LocalConfig.getInstance().getUnplottedReactionIds().contains(externalReactionIds.get(n))) {
				LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(externalReactionIds.get(n)));
			}
		}
		Collections.sort(LocalConfig.getInstance().getUnplottedReactionIds());
		//System.out.println("not plotted no ext " + LocalConfig.getInstance().getUnplottedReactionIds());
	}
	
	public void categorizeTransportReactions() {
		ReactionFactory rf = new ReactionFactory("SBML");
		Vector<SBMLReaction> reactions = rf.getAllReactions();
		Map<Integer, SBMLReaction> idReactionMap = new HashMap<Integer, SBMLReaction>();
		for (int i = 0; i < reactions.size(); i++) {
			idReactionMap.put(reactions.get(i).getId(), reactions.get(i));
		}
		removeExternalReactions();
		// unnecessary to categorize reactions if compartment names not defined
		if (LocalConfig.getInstance().getCytosolName() != null &&
				LocalConfig.getInstance().getCytosolName().length() > 0 &&
				LocalConfig.getInstance().getExtraOrganismName() != null &&
				LocalConfig.getInstance().getExtraOrganismName().length() > 0) {
			ArrayList<Integer> cytosolExtraOrganismIds = new ArrayList<Integer>();
			ArrayList<Integer> cytosolPeriplasmIds = new ArrayList<Integer>();
			ArrayList<Integer> periplasmExtraOrganismIds = new ArrayList<Integer>();
			// reactants or products in two compartments
			ArrayList<Integer> cytosolExtraOrganismIds2 = new ArrayList<Integer>();
			ArrayList<Integer> cytosolPeriplasmIds2 = new ArrayList<Integer>();
			ArrayList<Integer> periplasmExtraOrganismIds2 = new ArrayList<Integer>();
			for (int j = 0; j < LocalConfig.getInstance().getUnplottedReactionIds().size(); j++) {
				int id = LocalConfig.getInstance().getUnplottedReactionIds().get(j);
				SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(id);
				//System.out.println("eq " + equn);
				PathwayReactionData prd = LocalConfig.getInstance().getModelKeggEquationMap().get(Integer.toString(id));
				//System.out.println(prd);
				if (equn.getCompartmentList().size() == 2) { 
					// get periplasm to cytosol and periplasm to extra organism exchange and transport reactions
					if (LocalConfig.getInstance().getPeriplasmName() != null &&
							LocalConfig.getInstance().getPeriplasmName().length() > 0) {
						if (equn.getCompartmentList().contains(LocalConfig.getInstance().getCytosolName())
								&& equn.getCompartmentList().contains(LocalConfig.getInstance().getPeriplasmName())) {
							//System.out.println("ex " + equn.getCompartmentList() + "id " + id);
							if (LocalConfig.getInstance().getUnplottedReactionIds().contains(id)) {
//								LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(id));
								if ((equn.getCompartmentReactantsList().size() == 2 || equn.getCompartmentProductsList().size() == 2)) {
								    // remove reactions of type X_c + H_p = X_p + H_c
									if ((equn.getCompartmentReactantsList().size() == 2 && prd != null && prd.getKeggReactantIds().size() == 2 && prd.getKeggReactantIds().contains("C00080")) 
											&& (equn.getCompartmentProductsList().size() == 2 && prd != null && prd.getKeggProductIds().size() ==2 && prd.getKeggProductIds().contains("C00080"))) {
										cytosolPeriplasmIds2.add(id);
										//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Cytosol Periplasm2");
									}
									if ((equn.getCompartmentReactantsList().size() == 2 && prd != null && prd.getKeggReactantIds().size() == 2 && prd.getKeggReactantIds().contains("C00009")) 
											&& (equn.getCompartmentProductsList().size() == 2 && prd != null && prd.getKeggProductIds().size() ==2 && prd.getKeggProductIds().contains("C00009"))) {
										cytosolPeriplasmIds2.add(id);
										//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Cytosol Periplasm2");
									}
								} else {
									cytosolPeriplasmIds.add(id);
									//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Cytosol Periplasm");
								}
							}
						}
						if (equn.getCompartmentList().contains(LocalConfig.getInstance().getExtraOrganismName())
								&& equn.getCompartmentList().contains(LocalConfig.getInstance().getPeriplasmName())) {
							//System.out.println("ex " + equn.getCompartmentList() + "id " + id);
							if (LocalConfig.getInstance().getUnplottedReactionIds().contains(id)) {
								//LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(id));
								// remove reactions of type X_p + H_e = X_e + H_p
								if ((equn.getCompartmentReactantsList().size() == 2 || equn.getCompartmentProductsList().size() == 2)) {
									// remove reactions of type X_c + H_p = X_p + H_c
									if ((equn.getCompartmentReactantsList().size() == 2 && prd != null && prd.getKeggReactantIds().size() == 2 && prd.getKeggReactantIds().contains("C00080")) 
											&& (equn.getCompartmentProductsList().size() == 2 && prd != null && prd.getKeggProductIds().size() ==2 && prd.getKeggProductIds().contains("C00080"))) {
										periplasmExtraOrganismIds2.add(id);
										//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Extraorganism Periplasm2");
									}
									if ((equn.getCompartmentReactantsList().size() == 2 && prd != null && prd.getKeggReactantIds().size() == 2 && prd.getKeggReactantIds().contains("C00009")) 
											&& (equn.getCompartmentProductsList().size() == 2 && prd != null && prd.getKeggProductIds().size() ==2 && prd.getKeggProductIds().contains("C00009"))) {
										periplasmExtraOrganismIds2.add(id);
										//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Extraorganism Periplasm2");
									}
								} else {
									periplasmExtraOrganismIds.add(id);
									//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Extraorganism Periplasm");
								}
							}
						}
					}
					// get cytosol to extraorganism exchange and transport reactions
					if (equn.getCompartmentList().contains(LocalConfig.getInstance().getCytosolName())
							&& equn.getCompartmentList().contains(LocalConfig.getInstance().getExtraOrganismName())) {
						//System.out.println("ex " + equn.getCompartmentList() + "id " + id);
						if (LocalConfig.getInstance().getUnplottedReactionIds().contains(id)) {
//							LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(id));
							// remove reactions of type X_c + H_e = X_e + H_c	
							if ((equn.getCompartmentReactantsList().size() == 2 || equn.getCompartmentProductsList().size() == 2)) {
								// remove reactions of type X_c + H_p = X_p + H_c
								if ((equn.getCompartmentReactantsList().size() == 2 && prd != null && prd.getKeggReactantIds().size() == 2 && prd.getKeggReactantIds().contains("C00080")) 
										&& (equn.getCompartmentProductsList().size() == 2 && prd != null && prd.getKeggProductIds().size() ==2 && prd.getKeggProductIds().contains("C00080"))) {
									cytosolExtraOrganismIds2.add(id);
									//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Cytosol Extraorganism2");
								}
								if ((equn.getCompartmentReactantsList().size() == 2 && prd != null && prd.getKeggReactantIds().size() == 2 && prd.getKeggReactantIds().contains("C00009")) 
										&& (equn.getCompartmentProductsList().size() == 2 && prd != null && prd.getKeggProductIds().size() ==2 && prd.getKeggProductIds().contains("C00009"))) {
									cytosolExtraOrganismIds2.add(id);
									//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Cytosol Extraorganism2");
								}
							} else {
								cytosolExtraOrganismIds.add(id);
								//System.out.println("id " + id + "\t" + equn.equationAbbreviations + "\t" + "Cytosol Extraorganism");
							}
						}
					} 
				} else {
//					if (equn.getCompartmentList().size() == 1) {
//						if (equn.getCompartmentList().contains(LocalConfig.getInstance().getCytosolName())) {
//							LocalConfig.getInstance().getCytosolIds().add(id);
//						}
//					}
					//System.out.println(LocalConfig.getInstance().getCytosolIds());
					//System.out.println("comp list != 2 " + equn.getCompartmentList() + "id " + id);
				}
			}
			LocalConfig.getInstance().setCytosolExtraOrganismIds(cytosolExtraOrganismIds);
			LocalConfig.getInstance().setCytosolPeriplasmIds(cytosolPeriplasmIds);
			LocalConfig.getInstance().setPeriplasmExtraOrganismIds(periplasmExtraOrganismIds);
			// for some unknown reason, if it is attempted to remove these ids in if statements
			// where added to this list (above), it skips some ids. commented out
			//System.out.println("ce " + cytosolExtraOrganismIds);
			createTransportCollectionFromList(cytosolExtraOrganismIds, idReactionMap);
			createTransportCollectionFromList(cytosolExtraOrganismIds2, idReactionMap);
			createTransportCollectionFromList(cytosolPeriplasmIds, idReactionMap);
			createTransportCollectionFromList(cytosolPeriplasmIds2, idReactionMap);
			createTransportCollectionFromList(periplasmExtraOrganismIds, idReactionMap);
			createTransportCollectionFromList(periplasmExtraOrganismIds2, idReactionMap);
//			System.out.println("ce " + cytosolExtraOrganismIds);			
//			System.out.println("ce2 " + cytosolExtraOrganismIds2);
//			System.out.println("cp " + cytosolPeriplasmIds);
//			System.out.println("cp2 " + cytosolPeriplasmIds2);
//			System.out.println("pe " + periplasmExtraOrganismIds);
//			System.out.println("pe2 " + periplasmExtraOrganismIds2);
			Collections.sort(LocalConfig.getInstance().getUnplottedReactionIds());
			//System.out.println("not plotted " + LocalConfig.getInstance().getUnplottedReactionIds());
			try {
				//reactionsTable.getModel().getValueAt(0, rf.getKeggIdColumnIndex());
			} catch (Exception e) {
				//assignKeggReactionIds();
				//System.out.println("not plotted " + LocalConfig.getInstance().getUnplottedReactionIds());
				//assignKeggReactionIdsFromECNumbers();
			}
//			if (rf.getKeggIdColumnIndex() == -1) {
//				
//			//if (LocalConfig.getInstance().getKeggReactionIdColumnName().equals("")) {
//				assignKeggReactionIds();
//				System.out.println("not plotted " + LocalConfig.getInstance().getUnplottedReactionIds());
//				assignKeggReactionIdsFromECNumbers();
//			}
			KEGGIdReactionMapCreator keggIdReactionMapCreator = new KEGGIdReactionMapCreator();
			keggIdReactionMapCreator.createKEGGIdReactionMap();
		}
	}
	
	public void createUnplottedReactionsList() {
		ArrayList<Object> unplottedReactions = new ArrayList<Object>(LocalConfig.getInstance().getReactionEquationMap().keySet());
		ArrayList<Integer> unplottedReactionIds = new ArrayList<Integer>();
		for (int i = 0; i < unplottedReactions.size(); i++) {
			unplottedReactionIds.add((int) unplottedReactions.get(i));
		}
		LocalConfig.getInstance().setUnplottedReactionIds(unplottedReactionIds);
	}
	
	public void createTransportCollectionFromList(ArrayList<Integer> idsList, Map<Integer, SBMLReaction> idReactionMap) {
		for (int n = 0; n < idsList.size(); n++) {
			transportCollectionsCreator.createTransportCollections(idsList.get(n), 
					idReactionMap, TransportReactionConstants.PERIPLASM_EXTRAORGANISM_TRANSPORT);
			if (LocalConfig.getInstance().getUnplottedReactionIds().contains(idsList.get(n))) {
				LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(idsList.get(n)));
			}
		}
	}
}
