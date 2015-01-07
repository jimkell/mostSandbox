package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class PathwayConnectionNode extends PathwayReactionNode {
	// for each ArrayList, first entry will be pathway id, second metabolite id
	private ArrayList<ArrayList<String>> reactantPathwaysIds;
	private ArrayList<ArrayList<String>> productPathwaysIds;

	public ArrayList<ArrayList<String>> getReactantPathwaysIds() {
		return reactantPathwaysIds;
	}
	public void setReactantPathwaysIds(
			ArrayList<ArrayList<String>> reactantPathwaysIds) {
		this.reactantPathwaysIds = reactantPathwaysIds;
	}
	public ArrayList<ArrayList<String>> getProductPathwaysIds() {
		return productPathwaysIds;
	}
	public void setProductPathwaysIds(
			ArrayList<ArrayList<String>> productPathwaysIds) {
		this.productPathwaysIds = productPathwaysIds;
	}
	
}
