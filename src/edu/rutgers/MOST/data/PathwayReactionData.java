package edu.rutgers.MOST.data;

import java.util.ArrayList;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;

public class PathwayReactionData {
	// data from pathway_reactions.csv
	private String pathwayId;
	private String reactionId;
	private ArrayList<String> reactantIds;
	private ArrayList<String> productIds;
	private String reversible;
	private ArrayList<String> ecNumbers;
	private double level;
	private double levelPosition;
	// equation made up of metabolite node names
	private String equation;
	
	// generic name used if reaction not found in model
	private String name;
	
	public String getPathwayId() {
		return pathwayId;
	}

	public void setPathwayId(String pathwayId) {
		this.pathwayId = pathwayId;
	}

	public String getReactionId() {
		return reactionId;
	}

	public void setReactionId(String reactionId) {
		this.reactionId = reactionId;
	}

	public ArrayList<String> getReactantIds() {
		return reactantIds;
	}

	public void setReactantIds(ArrayList<String> reactantIds) {
		this.reactantIds = reactantIds;
	}

	public ArrayList<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(ArrayList<String> productIds) {
		this.productIds = productIds;
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

	public double getLevel() {
		return level;
	}

	public void setLevel(double level) {
		this.level = level;
	}

	public double getLevelPosition() {
		return levelPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevelPosition(double levelPosition) {
		this.levelPosition = levelPosition;
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
		
		for (int r = 0; r < reactantIds.size(); r++) {
			String name = LocalConfig.getInstance().getMetabolicPathways().get(this.pathwayId).getMetabolitesData().get(reactantIds.get(r)).getNames().get(0);
			if (r == 0) {
				reacBfr.append(name);							
			} else {
				reacBfr.append(" + " + name);			
			}			
		}
		
		for (int r = 0; r < productIds.size(); r++) {
			String name = LocalConfig.getInstance().getMetabolicPathways().get(this.pathwayId).getMetabolitesData().get(productIds.get(r)).getNames().get(0);
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
		System.out.println(this.equation);
	}
	
	@Override
	public String toString() {
		return "Pathway Reaction Data [pathwayId=" + pathwayId
		+ ", reactionId=" + reactionId
		+ ", reactantIds=" + reactantIds
		+ ", productIds=" + productIds
		+ ", reversible=" + reversible
		+ ", ecNumbers=" + ecNumbers
		+ ", level=" + level
		+ ", levelPosition=" + levelPosition
		+ ", name=" + name
		+ ", equation=" + equation + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

