package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.Map;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;

public class PathwayConnectionData {
	// data from pathway_connections.csv
	private ArrayList<String> keggReactionIds;
	// for each ArrayList, first entry will be pathway id, second metabolite id
	private ArrayList<ArrayList<String>> reactantPathwaysIds;
	private ArrayList<ArrayList<String>> productPathwaysIds;
	private ArrayList<String> keggReactantIds;
	private ArrayList<String> keggProductIds;
	private String reversible;
	private ArrayList<String> ecNumbers;
	private double length;
	private String positioning;
	//private double positioningIndex;
	private String direction;
	// equation made up of metabolite node names
	private String equation;

	private String name;
	// generic name used if reaction not found in model = name + ec numbers
	private String displayName;

	public ArrayList<String> getKeggReactionIds() {
		return keggReactionIds;
	}

	public void setKeggReactionIds(ArrayList<String> keggReactionIds) {
		this.keggReactionIds = keggReactionIds;
	}

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

	public ArrayList<String> getKeggReactantIds() {
		return keggReactantIds;
	}

	public void setKeggReactantIds(ArrayList<String> keggReactantIds) {
		this.keggReactantIds = keggReactantIds;
	}

	public ArrayList<String> getKeggProductIds() {
		return keggProductIds;
	}

	public void setKeggProductIds(ArrayList<String> keggProductIds) {
		this.keggProductIds = keggProductIds;
	}

	public void setProductPathwaysIds(
			ArrayList<ArrayList<String>> productPathwaysIds) {
		this.productPathwaysIds = productPathwaysIds;
	}

	public String getReversible() {
		return reversible;
	}

	public void setReversible(String reversible) {
		this.reversible = reversible;
	}

	public ArrayList<String> getEcNumbers() {
		return ecNumbers;
	}

	public void setEcNumbers(ArrayList<String> ecNumbers) {
		this.ecNumbers = ecNumbers;
	}
	
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getPositioning() {
		return positioning;
	}

	public void setPositioning(String positioning) {
		this.positioning = positioning;
	}

//	public double getPositioningIndex() {
//		return positioningIndex;
//	}
//
//	public void setPositioningIndex(double positioningIndex) {
//		this.positioningIndex = positioningIndex;
//	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEquation() {
		return equation;
	}

	public void setEquation(String equation) {
		this.equation = equation;
	}

	public void writeReactionEquation() {
		StringBuffer reacBfr = new StringBuffer();
		StringBuffer prodBfr = new StringBuffer();
		StringBuffer rxnBfr = new StringBuffer();

		for (int r = 0; r < reactantPathwaysIds.size(); r++) {
			String pathway = reactantPathwaysIds.get(r).get(0);
			String metaboliteId = reactantPathwaysIds.get(r).get(1);
			String name = LocalConfig.getInstance().getMetabolicPathways().get(pathway).getMetabolitesData().get(metaboliteId).getNames().get(0);
			if (r == 0) {
				reacBfr.append(name);							
			} else {
				reacBfr.append(" + " + name);			
			}			
		}

		for (int r = 0; r < productPathwaysIds.size(); r++) {
			String pathway = productPathwaysIds.get(r).get(0);
			String metaboliteId = productPathwaysIds.get(r).get(1);
			String name = LocalConfig.getInstance().getMetabolicPathways().get(pathway).getMetabolitesData().get(metaboliteId).getNames().get(0);
			if (r == 0) {
				prodBfr.append(name);							
			} else {
				prodBfr.append(" + " + name);			
			}			
		}

		if (reversible.equals("0")) {
			rxnBfr.append(reacBfr).append(" " + GraphicalInterfaceConstants.NOT_REVERSIBLE_ARROWS[2]).append(prodBfr);
		} else if (reversible.equals("1")) {
			rxnBfr.append(reacBfr).append(" " + GraphicalInterfaceConstants.REVERSIBLE_ARROWS[1]).append(prodBfr);
		}

		this.equation = rxnBfr.toString();
		//System.out.println(this.equation);
	}
	
	@Override
	public String toString() {
		return "Pathway Connection Data [keggReactionIds=" + keggReactionIds
				+ ", reactantPathwaysIds=" + reactantPathwaysIds
				+ ", productPathwaysIds=" + productPathwaysIds
				+ ", keggReactantIds=" + keggReactantIds
				+ ", keggProductIds=" + keggProductIds
				+ ", reversible=" + reversible
				+ ", ecNumbers=" + ecNumbers
				+ ", length=" + length
				+ ", positioning=" + positioning
				//+ ", positioningIndex=" + positioningIndex
				+ ", direction=" + direction
				+ ", name=" + name
				+ ", equation=" + equation + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}


