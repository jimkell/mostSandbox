package edu.rutgers.MOST.data;

import java.util.Map;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;

public class ExternalMetaboliteNodeFactory {

	public ExternalMetaboliteNode createExternalMetaboliteNode(double x, double y, String keggId,
			String abbr, String name, String position, double offset, int direction) {
		ExternalMetaboliteNode emn = new ExternalMetaboliteNode();
		emn.setxPosition(x);
		emn.setyPosition(y);
		emn.setKeggId(keggId);
		if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(keggId)) {
			String metabAbbr = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId).get(0).getMetaboliteAbbreviation();
			// check if metabolite ends with "_x"
			String ch = metabAbbr.substring(metabAbbr.length() - 2, metabAbbr.length() - 1);
			if (ch.equals("_")) {
				abbr = metabAbbr.substring(2, metabAbbr.length() - 2);
			} else {
				abbr = metabAbbr.substring(2);
			}
			name += " " + abbr;
		}
		if (LocalConfig.getInstance().getMetaboliteNameAbbrMap().containsKey(name)) {
			name = name + duplicateSuffix(name, LocalConfig.getInstance().getMetaboliteNameAbbrMap());
		}
		LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(name, abbr);
		emn.setAbbreviation(abbr);
		emn.setName(name);
		emn.setPosition(position);
		emn.setOffset(offset);
		emn.setDirection(direction);
		
		return emn;
	}
	
	/**
	 * Adds suffix to duplicate names
	 * @param value
	 * @param metaboliteNameAbbrMap
	 * @return
	 */
	public String duplicateSuffix(String value, Map<String, String> metaboliteNameAbbrMap) {
		String duplicateSuffix = GraphicalInterfaceConstants.DUPLICATE_SUFFIX;
		if (metaboliteNameAbbrMap.containsKey(value + duplicateSuffix)) {
			int duplicateCount = Integer.valueOf(duplicateSuffix.substring(1, duplicateSuffix.length() - 1));
			while (metaboliteNameAbbrMap.containsKey(value + duplicateSuffix.replace("1", Integer.toString(duplicateCount + 1)))) {
				duplicateCount += 1;
			}
			duplicateSuffix = duplicateSuffix.replace("1", Integer.toString(duplicateCount + 1));
		}
		return duplicateSuffix;
	}
}
