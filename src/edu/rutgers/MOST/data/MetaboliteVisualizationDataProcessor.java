package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class MetaboliteVisualizationDataProcessor {

	public void processMetabolitesData() {
		MetaboliteFactory f = new MetaboliteFactory("SBML");
		ArrayList<String> additionalMetaboliteKeys = new ArrayList<String>(LocalConfig.getInstance().getAdditionalMetabolitesMap().keySet());
		ArrayList<String> metaboliteSubstitutionKeys = new ArrayList<String>(LocalConfig.getInstance().getMetaboliteSubstitutionsMap().keySet());
		ArrayList<String> metaboliteAlternativeKeys = new ArrayList<String>(LocalConfig.getInstance().getMetaboliteAlternativesMap().keySet());
		//Map<String, ArrayList<String>> metaboliteSubstitutionsFoundMap = new HashMap<String, ArrayList<String>>();
		if (f.getKeggIdColumnIndex() > -1) {
			Vector<SBMLMetabolite> metabolites = f.getAllMetabolites();
			for (int i = 0; i < metabolites.size(); i++) {
				// if metabolite abbreviation is not empty and KEGG id is not empty
				// and not a boundary metabolite, process metabolite
				if (metaboliteAbbreviationValid(metabolites.get(i)) &&
						!isBoundaryMetabolite(metabolites.get(i))) {
					
					String metabId = Integer.toString(metabolites.get(i).getId());
					String keggId = metabolites.get(i).getKeggId();
					if (keggId == null || keggId.length() == 0) {
						if (f.getChebiIdColumnIndex() > -1) {
							String chebiId = metabolites.get(i).getChebiId();
							if (chebiId != null && chebiId.length() > 0) {
								if (LocalConfig.getInstance().getChebiIdKeggIdMap().containsKey(chebiId)) {
//									System.out.println(chebiId);
//									System.out.println(LocalConfig.getInstance().getChebiIdKeggIdMap().get(chebiId));
									keggId = LocalConfig.getInstance().getChebiIdKeggIdMap().get(chebiId);
								}
							}
						}
					}
					if (keggId != null && keggId.length() > 0) {
						// replace key from model with key from KEGG database in case where KEGG id from
						// model is not in KEGG database (depreciated?)
						for (int j = 0; j < additionalMetaboliteKeys.size(); j++) {
							if (LocalConfig.getInstance().getAdditionalMetabolitesMap().get(additionalMetaboliteKeys.get(j)).contains(keggId)) {
								keggId = additionalMetaboliteKeys.get(j);
							}
						}
						String originalKeggId = keggId;
						for (int k = 0; k < metaboliteSubstitutionKeys.size(); k++) {
							if (LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get(metaboliteSubstitutionKeys.get(k)).contains(keggId)) {
//								System.out.println("ki " + keggId);
//								System.out.println(LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get(metaboliteSubstitutionKeys.get(k)));
								keggId = metaboliteSubstitutionKeys.get(k);
//								if (metaboliteSubstitutionsFoundMap.containsKey(keggId)) {
//									ArrayList<String> m = metaboliteSubstitutionsFoundMap.get(keggId);
//									if (!m.contains(originalKeggId)) {
//										m.add(originalKeggId);
//										metaboliteSubstitutionsFoundMap.put(keggId, m);
//									}
//								} else {
//									ArrayList<String> m = new ArrayList<String>();
//									if (!m.contains(originalKeggId)) {
//										m.add(originalKeggId);
//										metaboliteSubstitutionsFoundMap.put(keggId, m);
//									}
//									metaboliteSubstitutionsFoundMap.put(keggId, m);
//								}
							}
						}
//						for (int m = 0; m < metaboliteAlternativeKeys.size(); m++) {
//							if (LocalConfig.getInstance().getMetaboliteAlternativesMap().get(metaboliteAlternativeKeys.get(m)).contains(keggId)) {
//								System.out.println(keggId);
//								System.out.println(LocalConfig.getInstance().getMetaboliteAlternativesMap().get(metaboliteAlternativeKeys.get(m)));
//							}
//						}
						// map used to match metabolite ids from model with KEGG ids
						LocalConfig.getInstance().getMetaboliteIdKeggIdMap().put(metabId, keggId);
						if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(keggId)) {
							ArrayList<SBMLMetabolite> metabolitesList = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId);
							metabolitesList.add(metabolites.get(i));
							// key - kegg id value SBMLMetabolite list, used to get data from model when
							// constructing nodes
							LocalConfig.getInstance().getKeggIdMetaboliteMap().put(keggId, metabolitesList);
						} else {
							ArrayList<SBMLMetabolite> metabolitesList = new ArrayList<SBMLMetabolite>();
							metabolitesList.add(metabolites.get(i));
							LocalConfig.getInstance().getKeggIdMetaboliteMap().put(keggId, metabolitesList);
						}
						if (!originalKeggId.equals(keggId)) {
							LocalConfig.getInstance().getSubstitutedMetabolitesMap().put(originalKeggId, keggId);
							if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(originalKeggId)) {
								ArrayList<SBMLMetabolite> metabolitesList = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(originalKeggId);
								metabolitesList.add(metabolites.get(i));
								// key - kegg id value SBMLMetabolite list, used to get data from model when
								// constructing nodes
								LocalConfig.getInstance().getKeggIdMetaboliteMap().put(originalKeggId, metabolitesList);
							} else {
								ArrayList<SBMLMetabolite> metabolitesList = new ArrayList<SBMLMetabolite>();
								metabolitesList.add(metabolites.get(i));
								LocalConfig.getInstance().getKeggIdMetaboliteMap().put(originalKeggId, metabolitesList);
							}
						}
					}
					if (!LocalConfig.getInstance().getKeggIdCompartmentMap().containsKey(keggId)) {
						ArrayList<String> compList = new ArrayList<String>();
						compList.add(metabolites.get(i).getCompartment());
						// used to determine which compartments a KEGG id occurs in
						LocalConfig.getInstance().getKeggIdCompartmentMap().put(keggId, compList);
					} else {
						ArrayList<String> compList = LocalConfig.getInstance().getKeggIdCompartmentMap().get(keggId);
						compList.add(metabolites.get(i).getCompartment());
						LocalConfig.getInstance().getKeggIdCompartmentMap().put(keggId, compList);
					}
				}
			}
//			System.out.println(LocalConfig.getInstance().getKeggIdCompartmentMap());
//			System.out.println(LocalConfig.getInstance().getMetaboliteIdKeggIdMap());
//			System.out.println(LocalConfig.getInstance().getKeggIdMetaboliteMap());
		}
	}

	private boolean metaboliteAbbreviationValid(SBMLMetabolite metabolite) {
		if (metabolite.getMetaboliteAbbreviation() != null &&
						metabolite.getMetaboliteAbbreviation().length() > 0) {
			return true;
		}
		return false;
		
	}
	
	private boolean isBoundaryMetabolite(SBMLMetabolite metabolite) {
		if (metabolite.getMetaboliteAbbreviation().endsWith("_b")) {
			return true;
		}
		return false;
		
	}
	
}
