package edu.rutgers.MOST.data;

import java.util.ArrayList;

import edu.rutgers.MOST.config.LocalConfig;

public class PathwayMetaboliteNodeFactory {

	public PathwayMetaboliteNode createPathwayMetaboliteNode(String dataId, double x, double y,  
			String type, String abbreviation, String name, String keggId) {
		PathwayMetaboliteNode pn = new PathwayMetaboliteNode();
		pn.setxPosition(x);
		pn.setyPosition(y);
		pn.setType(type);
		pn.setAbbreviation(abbreviation);
		pn.setName(name);
		//createDisplayName(abbreviation, name, keggId);
		return pn;
	}
	
	/** 
	 * Display name for tooltip created from input names if length of list of SBMLReactions = 0.
	 * Else display name created from SBMLReaction attributes.  
	 * @param displayName
	 * @param name
	 * @param reactions
	 * @return
	 */
	public String createDisplayName(String metabName, String abbreviation, String name, String keggId) {
		String displayName = "";
		ArrayList<String> metaboliteAbbrevations = new ArrayList<String>();
		ArrayList<String> metaboliteNames = new ArrayList<String>();
		ArrayList<String> keggMetaboliteIds = new ArrayList<String>();
		if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(keggId)) {
			//System.out.println(LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId));
			ArrayList<SBMLMetabolite> m = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId);
			for (int k = 0; k < m.size(); k++) {
				if (m.get(k).getCompartment().equals(LocalConfig.getInstance().getSelectedCompartmentName())) {
					//System.out.println(m.get(k).getMetaboliteAbbreviation());
					metaboliteAbbrevations.add(m.get(k).getMetaboliteAbbreviation());
					metaboliteNames.add(m.get(k).getMetaboliteName());
					keggMetaboliteIds.add(keggId);
				}
			}
		}
		if (LocalConfig.getInstance().getAdditionalMetabolitesMap().containsKey(keggId) &&
				LocalConfig.getInstance().getAdditionalMetabolitesMap().get(keggId) != null) {
			try {
				for (int i = 0; i < LocalConfig.getInstance().getAdditionalMetabolitesMap().get(keggId).size(); i++) {
					keggId = LocalConfig.getInstance().getAdditionalMetabolitesMap().get(keggId).get(i);
					//System.out.println(LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId));
					ArrayList<SBMLMetabolite> m = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId);
					for (int k = 0; k < m.size(); k++) {
						if (m.get(k).getCompartment().equals(LocalConfig.getInstance().getSelectedCompartmentName())) {
							//System.out.println(m.get(k).getMetaboliteAbbreviation());
							metaboliteAbbrevations.add(m.get(k).getMetaboliteAbbreviation());
							metaboliteNames.add(m.get(k).getMetaboliteName());
							keggMetaboliteIds.add(keggId);
						}
					}
				}
			} catch (Throwable t) {
				
			}
		}
		if (LocalConfig.getInstance().getMetaboliteSubstitutionsMap().containsKey(keggId) && 
				LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get(keggId) != null) {
			try {
				for (int j = 0; j < LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get(keggId).size(); j++) {
					keggId = LocalConfig.getInstance().getMetaboliteSubstitutionsMap().get(keggId).get(j);
					//System.out.println(LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId));
					ArrayList<SBMLMetabolite> m = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId);
					for (int k = 0; k < m.size(); k++) {
						if (m.get(k).getCompartment().equals(LocalConfig.getInstance().getSelectedCompartmentName())) {
							//System.out.println(m.get(k).getMetaboliteAbbreviation());
							metaboliteAbbrevations.add(m.get(k).getMetaboliteAbbreviation());
							metaboliteNames.add(m.get(k).getMetaboliteName());
							keggMetaboliteIds.add(keggId);
						}
					}
				}
			} catch (Throwable t) {
				
			}
		}
//		if (metabolites.size() > 0) {
//			for (int i = 0; i < metabolites.size(); i++) {
//				if (!metaboliteAbbrevations.contains(abbreviation)) {
//					metaboliteAbbrevations.add(abbreviation);
//				}
//				if (!metaboliteNames.contains(name)) {
//					metaboliteNames.add(name);
//				}
//				if (!keggMetaboliteIds.contains(keggId)) {
//					keggMetaboliteIds.add(keggId);
//				}
//			}
			displayName = "<html>" + metabName + "<p>"
					+ displayName(metaboliteAbbrevations)
					+ displayMetaboliteName(metaboliteNames)
					+ displayMetaboliteAbbreviation(metaboliteAbbrevations)
					+ displayKeggId(keggMetaboliteIds);
			//System.out.println(displayName);
//		}
		return displayName;
	}
	
	public String displayName(ArrayList<String> metaboliteNames) {
		String mn = "";
		if (metaboliteNames.size() > 0) {
			mn = metaboliteNames.get(0);
		}
		if (metaboliteNames.size() > 1) {
			mn = metaboliteNames.toString();
		}
		return mn;
	}
	
	public String displayMetaboliteAbbreviation(ArrayList<String> metaboliteAbbrevations) {
		return maybeMakeList(metaboliteAbbrevations, "Metabolite Abbreviation");
	}
	
	public String displayMetaboliteName(ArrayList<String> metaboliteNames) {
		return maybeMakeList(metaboliteNames, "Metabolite Name");
	}
	
	public String displayKeggId(ArrayList<String> keggMetaboliteIds) {
		return maybeMakeList(keggMetaboliteIds, "KEGG ID");
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
	
}
