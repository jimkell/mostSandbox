package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class TransportReactionNode {
	
	// key, used for tooltip in graph
	private String reactionName;
	// used for node text
	private String reactionAbbr;
	private String dataId;
	private String equation;
	private String modelEquation;
	private double fluxValue;
	private String metaboliteName;
	private String cytosolName;
	private String periplasmName;
	private String extraOrganismName;
	private ArrayList<PathwayMetaboliteNode> pathwayReactants;
	private ArrayList<PathwayMetaboliteNode> pathwayProducts;
	private String reversible;
	private double xPosition;
	private double yPosition;
	private String transportType;
	// direction = 1 if same direction as TransportReactionConstants, -1 if opposite
	private String direction;
	private String sideSpecies;
	// Transport reactions can have species switch sides such as a_c + b_p <=> a_p + b_c
	private String sideDirection;
	
	public String getReactionName() {
		return reactionName;
	}
	public void setReactionName(String reactionName) {
		this.reactionName = reactionName;
	}
	public String getReactionAbbr() {
		return reactionAbbr;
	}
	public void setReactionAbbr(String reactionAbbr) {
		this.reactionAbbr = reactionAbbr;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getEquation() {
		return equation;
	}
	public void setEquation(String equation) {
		this.equation = equation;
	}
	public String getModelEquation() {
		return modelEquation;
	}
	public void setModelEquation(String modelEquation) {
		this.modelEquation = modelEquation;
	}
	public double getFluxValue() {
		return fluxValue;
	}
	public void setFluxValue(double fluxValue) {
		this.fluxValue = fluxValue;
	}
	public String getMetaboliteName() {
		return metaboliteName;
	}
	public void setMetaboliteName(String metaboliteName) {
		this.metaboliteName = metaboliteName;
	}
	public String getCytosolName() {
		return cytosolName;
	}
	public void setCytosolName(String cytosolName) {
		this.cytosolName = cytosolName;
	}
	public String getPeriplasmName() {
		return periplasmName;
	}
	public void setPeriplasmName(String periplasmName) {
		this.periplasmName = periplasmName;
	}
	public String getExtraOrganismName() {
		return extraOrganismName;
	}
	public void setExtraOrganismName(String extraOrganismName) {
		this.extraOrganismName = extraOrganismName;
	}
	public ArrayList<PathwayMetaboliteNode> getPathwayReactants() {
		return pathwayReactants;
	}
	public void setPathwayReactants(
			ArrayList<PathwayMetaboliteNode> pathwayReactants) {
		this.pathwayReactants = pathwayReactants;
	}
	public ArrayList<PathwayMetaboliteNode> getPathwayProducts() {
		return pathwayProducts;
	}
	public void setPathwayProducts(ArrayList<PathwayMetaboliteNode> pathwayProducts) {
		this.pathwayProducts = pathwayProducts;
	}
	public String getReversible() {
		return reversible;
	}
	public void setReversible(String reversible) {
		this.reversible = reversible;
	}
	public double getxPosition() {
		return xPosition;
	}
	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}
	public double getyPosition() {
		return yPosition;
	}
	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getSideSpecies() {
		return sideSpecies;
	}
	public void setSideSpecies(String sideSpecies) {
		this.sideSpecies = sideSpecies;
	}
	public String getSideDirection() {
		return sideDirection;
	}
	public void setSideDirection(String sideDirection) {
		this.sideDirection = sideDirection;
	}
	@Override
	public String toString() {
		return "Transport Reaction Node [reactionName=" + reactionName
		+ ", reactionAbbr=" + reactionAbbr		
		+ ", dataId=" + dataId
		+ ", equation=" + equation
		+ ", modelEquation=" + modelEquation
		+ ", fluxValue=" + fluxValue
		+ ", metaboliteName=" + metaboliteName
		+ ", cytosolName=" + cytosolName
		+ ", periplasmName=" + periplasmName
		+ ", extraOrganismName=" + extraOrganismName
		+ ", pathwayReactants=" + pathwayReactants
		+ ", pathwayProducts=" + pathwayProducts
		+ ", reversible=" + reversible
		+ ", xPosition=" + xPosition
		+ ", yPosition=" + yPosition
		+ ", transportType=" + transportType
		+ ", direction=" + direction
		+ ", sideSpecies=" + sideSpecies
		+ ", sideDirection=" + sideDirection + "]";
	}

}
