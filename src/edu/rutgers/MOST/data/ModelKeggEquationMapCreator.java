package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.rutgers.MOST.config.LocalConfig;

public class ModelKeggEquationMapCreator {
	
	public void createKeggEquationMap() {
		Map<String, PathwayReactionData> modelKeggEquationMap = new HashMap<String, PathwayReactionData>();
		ArrayList<Integer> reactionsMissingKeggId = new ArrayList<Integer>();
		for (int j = 0; j < LocalConfig.getInstance().getReactionEquationMap().size(); j++) {
			boolean found = true;
			ArrayList<String> keggReactantIds = new ArrayList<String>();
			ArrayList<String> keggProductIds = new ArrayList<String>();
			PathwayReactionData prd = new PathwayReactionData();
			if (LocalConfig.getInstance().getReactionEquationMap().containsKey(j)) {
				int reactionId = ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getReactants().get(0).getReactionId();
				//System.out.println(reactionId);
				prd.setReactionId(Integer.toString(reactionId));
				for (int r = 0; r < ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getReactants().size(); r++) {
					//reactionId = ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getReactants().get(r).getReactionId();
					int metabId = ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getReactants().get(r).getMetaboliteId();
					if (LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)) != null) {
						//System.out.println("k r " + LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)));
						keggReactantIds.add(LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)));
					} else {
						found = false;
						keggReactantIds.clear();
						break;
					}
				}
				for (int p = 0; p < ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getProducts().size(); p++) {
					int metabId = ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getProducts().get(p).getMetaboliteId();
					if (LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)) != null) {
						//System.out.println("k p " + LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)));
						keggProductIds.add(LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)));
					}  else {
						found = false;
						keggProductIds.clear();
					}
				}
				if (found) {
					prd.setKeggReactantIds(keggReactantIds);
					prd.setKeggProductIds(keggProductIds);
					modelKeggEquationMap.put(Integer.toString(reactionId), prd);
				} else {
					reactionsMissingKeggId.add(Integer.valueOf(prd.getReactionId()));
				}
			} 
//			for (int p = 0; p < ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getProducts().size(); p++) {
//				int metabId = ((SBMLReactionEquation) LocalConfig.getInstance().getReactionEquationMap().get(j)).getProducts().get(p).getMetaboliteId();
//				if (LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)) != null) {
//					//System.out.println("k p " + LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)));
//					keggProductIds.add(LocalConfig.getInstance().getMetaboliteIdKeggIdMap().get(Integer.toString(metabId)));
//				}  else {
//					found = false;
//					keggProductIds.clear();
//				}
//			}
			//System.out.println(reactionId + " found=" + found);
//			if (found) {
//				prd.setKeggReactantIds(keggReactantIds);
//				prd.setKeggProductIds(keggProductIds);
//				modelKeggEquationMap.put(Integer.toString(reactionId), prd);
//			}
		}
		LocalConfig.getInstance().setModelKeggEquationMap(modelKeggEquationMap);
		//System.out.println(LocalConfig.getInstance().getModelKeggEquationMap());
		LocalConfig.getInstance().setReactionsMissingKeggId(reactionsMissingKeggId);
	}

}
