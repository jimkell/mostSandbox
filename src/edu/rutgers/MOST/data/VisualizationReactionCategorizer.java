package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class VisualizationReactionCategorizer {

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
		//System.out.println("ext rxns " + externalReactionIds);
		for (int n = 0; n < externalReactionIds.size(); n++) {
			if (LocalConfig.getInstance().getUnplottedReactionIds().contains(externalReactionIds.get(n))) {
				LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(externalReactionIds.get(n)));
			}
		}
		Collections.sort(LocalConfig.getInstance().getUnplottedReactionIds());
		//System.out.println("not plotted no ext " + LocalConfig.getInstance().getUnplottedReactionIds());
	}
	
	public void categorizeReactions() {
		TransportCollectionsCreator transportCollectionsCreator = new TransportCollectionsCreator();
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
					//System.out.println("comp list != 2 " + equn.getCompartmentList() + "id " + id);
				}
			}
			LocalConfig.getInstance().setCytosolExtraOrganismIds(cytosolExtraOrganismIds);
			// for some unknown reason, if it is attempted to remove these ids in if statements
			// where added to this list (above), it skips some ids. commented out
			//System.out.println("ce " + cytosolExtraOrganismIds);
			for (int k = 0; k < cytosolExtraOrganismIds.size(); k++) {
				transportCollectionsCreator.createTransportCollections(cytosolExtraOrganismIds.get(k), 
						idReactionMap, TransportReactionConstants.CYTOSOL_EXTRAORGANISM_TRANSPORT);
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(cytosolExtraOrganismIds.get(k))) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(cytosolExtraOrganismIds.get(k)));
				}
			}
			//System.out.println("ce2 " + cytosolExtraOrganismIds2);
			for (int k = 0; k < cytosolExtraOrganismIds2.size(); k++) {
				transportCollectionsCreator.createTransportCollections(cytosolExtraOrganismIds2.get(k), 
						idReactionMap, TransportReactionConstants.CYTOSOL_EXTRAORGANISM_TRANSPORT);
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(cytosolExtraOrganismIds2.get(k))) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(cytosolExtraOrganismIds2.get(k)));
				}
			}
			//System.out.println("cp " + cytosolPeriplasmIds);
			for (int m = 0; m < cytosolPeriplasmIds.size(); m++) {
				transportCollectionsCreator.createTransportCollections(cytosolPeriplasmIds.get(m), 
						idReactionMap, TransportReactionConstants.CYTOSOL_PERIPLASM_TRANSPORT);
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(cytosolPeriplasmIds.get(m))) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(cytosolPeriplasmIds.get(m)));
				}
			}
			//System.out.println("cp2 " + cytosolPeriplasmIds2);
			for (int m = 0; m < cytosolPeriplasmIds2.size(); m++) {
				transportCollectionsCreator.createTransportCollections(cytosolPeriplasmIds2.get(m), 
						idReactionMap, TransportReactionConstants.CYTOSOL_PERIPLASM_TRANSPORT);
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(cytosolPeriplasmIds2.get(m))) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(cytosolPeriplasmIds2.get(m)));
				}
			}
			//System.out.println("pe " + periplasmExtraOrganismIds);
			for (int n = 0; n < periplasmExtraOrganismIds.size(); n++) {
				transportCollectionsCreator.createTransportCollections(periplasmExtraOrganismIds.get(n), 
						idReactionMap, TransportReactionConstants.PERIPLASM_EXTRAORGANISM_TRANSPORT);
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(periplasmExtraOrganismIds.get(n))) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(periplasmExtraOrganismIds.get(n)));
				}
			}
			//System.out.println("pe2 " + periplasmExtraOrganismIds2);
			for (int n = 0; n < periplasmExtraOrganismIds2.size(); n++) {
				transportCollectionsCreator.createTransportCollections(periplasmExtraOrganismIds2.get(n), 
						idReactionMap, TransportReactionConstants.PERIPLASM_EXTRAORGANISM_TRANSPORT);
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(periplasmExtraOrganismIds2.get(n))) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(periplasmExtraOrganismIds2.get(n)));
				}
			}
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
	
//	public void createTransportCollections(int id, Map<Integer, SBMLReaction> idReactionMap, String transportType) {
//		//System.out.println(idReactionMap.get(id).getReactionAbbreviation());
//		if (LocalConfig.getInstance().getModelKeggEquationMap().containsKey(Integer.toString(id))) {
//			ArrayList<String> keggReactantIds = LocalConfig.getInstance().getModelKeggEquationMap().get(Integer.toString(id)).getKeggReactantIds();
//			ArrayList<String> keggProductIds = LocalConfig.getInstance().getModelKeggEquationMap().get(Integer.toString(id)).getKeggProductIds();
//			String sideSpecies = "";
//			if (keggReactantIds.contains("C00080") && keggProductIds.contains("C00080")) {
//				if (keggReactantIds.size() > 1 && keggProductIds.size() > 1) {
//					keggReactantIds.remove(keggReactantIds.indexOf("C00080"));
//					keggProductIds.remove(keggProductIds.indexOf("C00080"));
//					sideSpecies = "C00080";
//				} 
//			}
//			if (keggReactantIds.contains("C00009") && keggProductIds.contains("C00009")) {
//				if (keggReactantIds.size() > 1 && keggProductIds.size() > 1) {
//					keggReactantIds.remove(keggReactantIds.indexOf("C00009"));
//					keggProductIds.remove(keggProductIds.indexOf("C00009"));
//					sideSpecies = "C00009";
//				} 
//			}
//			// note: there are a few transport reactions with Na (C01330) where Na and
//			// a second species are transported from c to p
//			if (keggReactantIds.equals(keggProductIds)) {
//				String metabAbbr = createMetaboliteAbbreviation(LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggReactantIds.get(0)).get(0).getMetaboliteAbbreviation());
//				//System.out.println(metabAbbr);
//				//System.out.println(keggReactantIds);
//				if (LocalConfig.getInstance().getSideSpeciesList().contains(keggReactantIds.get(0))) {
//					if (!LocalConfig.getInstance().getTransportMetaboliteIds().contains(keggReactantIds.get(0))) {
//						LocalConfig.getInstance().getSideSpeciesTransportMetaboliteKeggIdMap().put(metabAbbr, LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggReactantIds.get(0)).get(0).getKeggId());
//						if (keggReactantIds.size() == 1) {
//							TransportReactionNode trn = createTransportReactionNode(keggReactantIds, metabAbbr, 
//									id, idReactionMap, sideSpecies, transportType);
//							if (LocalConfig.getInstance().getSideSpeciesTransportReactionNodeMap().containsKey(keggReactantIds.get(0))) {
//								ArrayList<TransportReactionNode> trnList = LocalConfig.getInstance().getSideSpeciesTransportReactionNodeMap().get(keggReactantIds.get(0));
//								trnList.add(trn);
//								LocalConfig.getInstance().getSideSpeciesTransportReactionNodeMap().put(keggReactantIds.get(0), trnList);
//							} else {
//								ArrayList<TransportReactionNode> trnList = new ArrayList<TransportReactionNode>();
//								trnList.add(trn);
//								LocalConfig.getInstance().getSideSpeciesTransportReactionNodeMap().put(keggReactantIds.get(0), trnList);
//							}
//							//System.out.println(idReactionMap.get(id).getReactionAbbreviation());
//						}
//					}
//				} else if (LocalConfig.getInstance().getTransportMetaboliteIds().contains(keggReactantIds.get(0))) {
//					if (keggReactantIds.size() == 1) {
//						TransportReactionNode trn = createTransportReactionNode(keggReactantIds, metabAbbr, 
//								id, idReactionMap, sideSpecies, transportType);
////						System.out.println("abbr " + idReactionMap.get(id).getReactionAbbreviation());
////						System.out.println(trn);
//						if (LocalConfig.getInstance().getKeggIdTransportReactionsMap().containsKey(keggReactantIds.get(0))) {
//							ArrayList<TransportReactionNode> trnList = LocalConfig.getInstance().getKeggIdTransportReactionsMap().get(keggReactantIds.get(0));
//							trnList.add(trn);
//							LocalConfig.getInstance().getKeggIdTransportReactionsMap().put(keggReactantIds.get(0), trnList);
//						} else {
//							ArrayList<TransportReactionNode> trnList = new ArrayList<TransportReactionNode>();
//							trnList.add(trn);
//							LocalConfig.getInstance().getKeggIdTransportReactionsMap().put(keggReactantIds.get(0), trnList);
//						}
//					}
//				}
//			}
//		}
//	}
	
//	public TransportReactionNode createTransportReactionNode(ArrayList<String> keggReactantIds, String metabAbbr, 
//			int id, Map<Integer, SBMLReaction> idReactionMap, String sideSpecies, String transportType) {
//		String cytosolName = "";
//		String periplasmName = "";
//		String extraOrganismName = "";
//		String direction = "1";
//		if (keggReactantIds.size() == 1) {
//			SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(id);
//			String reactantComp = "";
//			String productComp = "";
//			for (int r = 0; r < equn.getReactants().size(); r++) {
//				if (LocalConfig.getInstance().getMetaboliteIdKeggIdMap().containsKey(Integer.toString(equn.getReactants().get(r).getMetaboliteId()))) {
//					String keggId = LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(equn.getReactants().get(r).getMetaboliteId()));
//					if (keggId.equals("C00080")) {
//						
//					} else if (keggId.equals("C00009")) {
//						
//					} else {
//						reactantComp = equn.getReactants().get(r).getCompartment();
//						if (reactantComp != null && reactantComp.length() > 0) {
//							if (reactantComp.equals(LocalConfig.getInstance().getCytosolName())) {
//								cytosolName = equn.getReactants().get(r).getMetaboliteAbbreviation();
//							} else if (reactantComp.equals(LocalConfig.getInstance().getPeriplasmName())) {
//								periplasmName = equn.getReactants().get(r).getMetaboliteAbbreviation();
//							} else if (reactantComp.equals(LocalConfig.getInstance().getExtraOrganismName())) {
//								extraOrganismName = equn.getReactants().get(r).getMetaboliteAbbreviation();
//							}
//						}
//					}
//				}
//			}
//			for (int p = 0; p < equn.getProducts().size(); p++) {
//				if (LocalConfig.getInstance().getMetaboliteIdKeggIdMap().containsKey(Integer.toString(equn.getProducts().get(p).getMetaboliteId()))) {
//					String keggId = LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(equn.getProducts().get(p).getMetaboliteId()));
//					if (keggId.equals("C00080")) {
//						
//					} else if (keggId.equals("C00009")) {
//						
//					} else {
//						productComp = equn.getProducts().get(p).getCompartment();
//						if (productComp != null && productComp.length() > 0) {
//							if (productComp.equals(LocalConfig.getInstance().getCytosolName())) {
//								cytosolName = equn.getProducts().get(p).getMetaboliteAbbreviation();
//							} else if (productComp.equals(LocalConfig.getInstance().getPeriplasmName())) {
//								periplasmName = equn.getProducts().get(p).getMetaboliteAbbreviation();
//							} else if (productComp.equals(LocalConfig.getInstance().getExtraOrganismName())) {
//								extraOrganismName = equn.getProducts().get(p).getMetaboliteAbbreviation();
//							}
//						}
//					}
//				}
//			}
//			if (reactantComp != null && reactantComp.length() > 0 &&
//					productComp != null && productComp.length() > 0) {
//				direction = reactionDirection(reactantComp, productComp);
//			}
//		}
//		
//		TransportReactionNode trn = new TransportReactionNode();
//		trn.setReactionAbbr(idReactionMap.get(id).getReactionAbbreviation());
//		trn.setReactionName(idReactionMap.get(id).getReactionName());
//		trn.setFluxValue(idReactionMap.get(id).getFluxValue());
//		trn.setKnockout(idReactionMap.get(id).getKnockout());
//		trn.setSyntheticObjective(idReactionMap.get(id).getSyntheticObjective());
//		trn.setModelEquation(idReactionMap.get(id).getReactionEqunAbbr());
//		trn.setCytosolName(cytosolName);
//		trn.setPeriplasmName(periplasmName);
//		trn.setExtraOrganismName(extraOrganismName);
//		trn.setReversible(idReactionMap.get(id).getReversible());
//		trn.setTransportType(transportType);
//		trn.setDirection(direction);
//		trn.setSideSpecies(sideSpecies);
//		return trn;
//	}
	
//	public String reactionDirection(String reactantComp, String productComp) {
//		String direction = "1";
//		if (reactantComp.equals(LocalConfig.getInstance().getExtraOrganismName()) &&
//				(productComp.equals(LocalConfig.getInstance().getPeriplasmName()) ||
//						productComp.equals(LocalConfig.getInstance().getCytosolName()))) {
//			direction = "-1";
//		} else if (reactantComp.equals(LocalConfig.getInstance().getPeriplasmName()) && 
//				productComp.equals(LocalConfig.getInstance().getCytosolName())) {
//			direction = "-1";
//		}
//		return direction;
//	}
}
