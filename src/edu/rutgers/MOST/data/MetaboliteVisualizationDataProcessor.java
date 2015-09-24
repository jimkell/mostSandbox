package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Vector;

import edu.rutgers.MOST.config.LocalConfig;

public class MetaboliteVisualizationDataProcessor {
	
	public void processMetabolitesData() {
		//reader.readFiles();
				MetaboliteFactory f = new MetaboliteFactory("SBML");
				ArrayList<String> additionalMetaboliteKeys = new ArrayList<String>(LocalConfig.getInstance().getAdditionalMetabolitesMap().keySet());
				ArrayList<String> metaboliteSubstitutionKeys = new ArrayList<String>(LocalConfig.getInstance().getMetaboliteSubstitutionsMap().keySet());
				if (f.getKeggIdColumnIndex() > -1) {
					Vector<SBMLMetabolite> metabolites = f.getAllMetabolites();
					for (int i = 0; i < metabolites.size(); i++) {
						if (metabolites.get(i).getMetaboliteAbbreviation() != null &&
								metabolites.get(i).getMetaboliteAbbreviation().length() > 0 &&
								!metabolites.get(i).getMetaboliteAbbreviation().endsWith("_b") &&
								metabolites.get(i).getKeggId() != null &&
								metabolites.get(i).getKeggId().length() > 0) {
							String metabId = Integer.toString(metabolites.get(i).getId());
							String keggId = metabolites.get(i).getKeggId();
							if (keggId != null && keggId.length() > 0) {
								// replace key from model with key from KEGG database
								for (int j = 0; j < additionalMetaboliteKeys.size(); j++) {
									if (LocalConfig.getInstance().getAdditionalMetabolitesMap().get(additionalMetaboliteKeys.get(j)).contains(keggId)) {
										keggId = additionalMetaboliteKeys.get(j);
									}
								}
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
					//System.out.println(LocalConfig.getInstance().getKeggIdCompartmentMap());
					//System.out.println(LocalConfig.getInstance().getMetaboliteIdKeggIdMap());
					//System.out.println(LocalConfig.getInstance().getKeggIdMetaboliteMap());
				}
	}

}
