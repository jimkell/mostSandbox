package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class TransportReactionCategorizer {

	private Vector<SBMLReaction> compartmentOutsideRxns;
	private Vector<SBMLReaction> compartmentMembraneRxns;
	private Vector<SBMLReaction> membraneOutsideRxns;
	private ArrayList<Integer> transportIds;
	
	TransportCollectionsCreator transportCollectionsCreator = new TransportCollectionsCreator();
	public void removeExternalReactions() {
		ReactionFactory rf = new ReactionFactory("SBML");
		Vector<SBMLReaction> reactions = rf.getAllReactions();
		ArrayList<Integer> externalReactionIds = new ArrayList<Integer>();
		MetaboliteFactory f = new MetaboliteFactory("SBML");
		ArrayList<Integer> metaboliteExternalIdList = f.metaboliteExternalIdList();
		//System.out.println("unpl " + LocalConfig.getInstance().getUnplottedReactionIds());
		for (int i = 0; i < reactions.size(); i++) {
//		for (int j = 0; j < LocalConfig.getInstance().getUnplottedReactionIds().size(); j++) {
			int id = reactions.get(i).getId();
//			int id = LocalConfig.getInstance().getUnplottedReactionIds().get(j);
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
		LocalConfig.getInstance().setExternalReactionIds(externalReactionIds);
		// faster to make new list than remove from old list
		ArrayList<Integer> unplottedIds = new ArrayList<Integer>();
		for (int n = 0; n < LocalConfig.getInstance().getUnplottedReactionIds().size(); n++) {
			if (!externalReactionIds.contains(LocalConfig.getInstance().getUnplottedReactionIds().get(n))) {
				unplottedIds.add(LocalConfig.getInstance().getUnplottedReactionIds().get(n));
			}
		}
		LocalConfig.getInstance().setUnplottedReactionIds(unplottedIds);
//		for (int n = 0; n < externalReactionIds.size(); n++) {
//			if (LocalConfig.getInstance().getUnplottedReactionIds().contains(externalReactionIds.get(n))) {
//				LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(externalReactionIds.get(n)));
//			}
//		}
//		Collections.sort(LocalConfig.getInstance().getUnplottedReactionIds());
//		System.out.println("not plotted no ext " + LocalConfig.getInstance().getUnplottedReactionIds());
	}
	
	public void removeBiomassReactions() {
		ReactionFactory rf = new ReactionFactory("SBML");
		Vector<SBMLReaction> reactions = rf.getAllReactions();
		for (int i = 0; i < reactions.size(); i++) {
			//			for (int j = 0; j < LocalConfig.getInstance().getUnplottedReactionIds().size(); j++) {
			int id = reactions.get(i).getId();
			//				int id = LocalConfig.getInstance().getUnplottedReactionIds().get(j);
			SBMLReactionEquation equn = (SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(id);
			//System.out.println("eq " + equn);
			// get external reactions, not plotted
			ArrayList<SBMLReactant> reactants = equn.getReactants();
			ArrayList<SBMLProduct> products = equn.getProducts();
//			System.out.println(reactants.size());
//			System.out.println(products.size());
			if (reactants.size() > 15 || products.size() > 15) {
				if (LocalConfig.getInstance().getUnplottedReactionIds().contains(id)) {
					LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(id));
				}
			}
		}
	}
	
	public void categorizeTransportReactions() {
		ReactionFactory rf = new ReactionFactory("SBML");
		removeExternalReactions();
		removeBiomassReactions();
		if (LocalConfig.getInstance().getSelectedCompartmentName() != null &&
				LocalConfig.getInstance().getSelectedCompartmentName().length() > 0 &&
				LocalConfig.getInstance().getOutsideName() != null &&
				LocalConfig.getInstance().getOutsideName().length() > 0) {
			compartmentOutsideRxns = rf.getTransportReactionsByCompartments(LocalConfig.getInstance().getSelectedCompartmentName(), 
					LocalConfig.getInstance().getOutsideName());
			removeTransportReactionsFromUnplottedList(compartmentOutsideRxns);
			if (LocalConfig.getInstance().getMembraneName() != null &&
					LocalConfig.getInstance().getMembraneName().length() > 0) {
				compartmentMembraneRxns = rf.getTransportReactionsByCompartments(LocalConfig.getInstance().getSelectedCompartmentName(), 
						LocalConfig.getInstance().getMembraneName());
				removeTransportReactionsFromUnplottedList(compartmentMembraneRxns);
				membraneOutsideRxns = rf.getTransportReactionsByCompartments(LocalConfig.getInstance().getMembraneName(), 
						LocalConfig.getInstance().getOutsideName());
				removeTransportReactionsFromUnplottedList(membraneOutsideRxns);
			}
			LocalConfig.getInstance().setNoTransportReactionIds(LocalConfig.getInstance().getUnplottedReactionIds());
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
	
	public void removeTransportReactionsFromUnplottedList(Vector<SBMLReaction> rxns) {
//		System.out.println(LocalConfig.getInstance().getUnplottedReactionIds());
//		System.out.println(rxns);
		ArrayList<Integer> rxnIds = new ArrayList<Integer>();
		for (int i = 0; i < rxns.size(); i++) {
			rxnIds.add(rxns.get(i).getId());
		}
		// faster to make new list than remove from old list
		ArrayList<Integer> unplottedIds = new ArrayList<Integer>();
		for (int n = 0; n < LocalConfig.getInstance().getUnplottedReactionIds().size(); n++) {
			if (!rxnIds.contains(LocalConfig.getInstance().getUnplottedReactionIds().get(n))) {
				unplottedIds.add(LocalConfig.getInstance().getUnplottedReactionIds().get(n));
			}
		}
		LocalConfig.getInstance().setUnplottedReactionIds(unplottedIds);
		//System.out.println("no trans " + LocalConfig.getInstance().getUnplottedReactionIds());
	}
	
	public void createTransportCollectionFromList(ArrayList<Integer> idsList, Map<Integer, SBMLReaction> idReactionMap) {
//		for (int n = 0; n < idsList.size(); n++) {
//			transportCollectionsCreator.createTransportCollections(idsList.get(n), 
//					idReactionMap, TransportReactionConstants.PERIPLASM_EXTRAORGANISM_TRANSPORT);
//			if (LocalConfig.getInstance().getUnplottedReactionIds().contains(idsList.get(n))) {
//				LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(idsList.get(n)));
//			}
//		}
	}
}
