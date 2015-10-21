package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Map;

import edu.rutgers.MOST.config.LocalConfig;

public class TransportReactionNodeCreator {

//	public TransportReactionNode createTransportReactionNode(ArrayList<String> keggReactantIds, String metabAbbr, 
//			int id, Map<Integer, SBMLReaction> idReactionMap, String sideSpecies, String transportType) {
//		VisualizationKeggReactionProcessor processor = new VisualizationKeggReactionProcessor();
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
//				direction = processor.reactionDirection(reactantComp, productComp);
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
