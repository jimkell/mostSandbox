package edu.rutgers.MOST.data;

public class PathwayMetaboliteNodeFactory {

	public PathwayMetaboliteNode createPathwayMetaboliteNode(String dataId, double x, double y,  
			String type, String abbreviation, String name) {
		PathwayMetaboliteNode pn = new PathwayMetaboliteNode();
		pn.setxPosition(x);
		pn.setyPosition(y);
		pn.setType(type);
		pn.setAbbreviation(abbreviation);
		pn.setName(name);
		return pn;
	}
	
}
