package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class PathwayConnectionNode {
	private String compartment;
	// data from pathway_connections.csv
	// for each ArrayList, first entry will be pathway id, second metabolite id
	private ArrayList<ArrayList<String>> reactantPathwaysIds;
	private ArrayList<ArrayList<String>> productPathwaysIds;

	// key, used for tooltip in graph
	private String reactionName;
	// used for node text
	private String modelReactionName;
	private ArrayList<String> modelReactionNames;
	private String dataId;
	private String equation;
	private String modelEquation;
	private ArrayList<String> modelEquations;
	private ArrayList<Double> fluxes;

	// data from enzyme.dat
	private ArrayList<String> enzymeDataEquations;
	private ArrayList<String> mainReactants;
	private ArrayList<String> mainProducts;
	private ArrayList<String> sideReactants;
	private ArrayList<String> sideProducts;

	private ArrayList<PathwayMetaboliteNode> mainPathwayReactants;
	private ArrayList<PathwayMetaboliteNode> mainPathwayProducts;
	private String reversible;
	private ArrayList<String> ecNumbers;
	private double xPosition;
	private double yPosition;

	private ArrayList<String> compartmentList;
	private ArrayList<String> compartmentReactantsList;
	private ArrayList<String> compartmentProductsList;
	private String subsystem;
	public String getCompartment() {
		return compartment;
	}
	public void setCompartment(String compartment) {
		this.compartment = compartment;
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
	public void setProductPathwaysIds(
			ArrayList<ArrayList<String>> productPathwaysIds) {
		this.productPathwaysIds = productPathwaysIds;
	}
	public String getReactionName() {
		return reactionName;
	}
	public void setReactionName(String reactionName) {
		this.reactionName = reactionName;
	}
	public String getModelReactionName() {
		return modelReactionName;
	}
	public void setModelReactionName(String modelReactionName) {
		this.modelReactionName = modelReactionName;
	}
	public ArrayList<String> getModelReactionNames() {
		return modelReactionNames;
	}
	public void setModelReactionNames(ArrayList<String> modelReactionNames) {
		this.modelReactionNames = modelReactionNames;
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
	public ArrayList<String> getModelEquations() {
		return modelEquations;
	}
	public void setModelEquations(ArrayList<String> modelEquations) {
		this.modelEquations = modelEquations;
	}
	public ArrayList<Double> getFluxes() {
		return fluxes;
	}
	public void setFluxes(ArrayList<Double> fluxes) {
		this.fluxes = fluxes;
	}
	public ArrayList<String> getEnzymeDataEquations() {
		return enzymeDataEquations;
	}
	public void setEnzymeDataEquations(ArrayList<String> enzymeDataEquations) {
		this.enzymeDataEquations = enzymeDataEquations;
	}
	public ArrayList<String> getMainReactants() {
		return mainReactants;
	}
	public void setMainReactants(ArrayList<String> mainReactants) {
		this.mainReactants = mainReactants;
	}
	public ArrayList<String> getMainProducts() {
		return mainProducts;
	}
	public void setMainProducts(ArrayList<String> mainProducts) {
		this.mainProducts = mainProducts;
	}
	public ArrayList<String> getSideReactants() {
		return sideReactants;
	}
	public void setSideReactants(ArrayList<String> sideReactants) {
		this.sideReactants = sideReactants;
	}
	public ArrayList<String> getSideProducts() {
		return sideProducts;
	}
	public void setSideProducts(ArrayList<String> sideProducts) {
		this.sideProducts = sideProducts;
	}
	public ArrayList<PathwayMetaboliteNode> getMainPathwayReactants() {
		return mainPathwayReactants;
	}
	public void setMainPathwayReactants(
			ArrayList<PathwayMetaboliteNode> mainPathwayReactants) {
		this.mainPathwayReactants = mainPathwayReactants;
	}
	public ArrayList<PathwayMetaboliteNode> getMainPathwayProducts() {
		return mainPathwayProducts;
	}
	public void setMainPathwayProducts(
			ArrayList<PathwayMetaboliteNode> mainPathwayProducts) {
		this.mainPathwayProducts = mainPathwayProducts;
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
	public ArrayList<String> getCompartmentList() {
		return compartmentList;
	}
	public void setCompartmentList(ArrayList<String> compartmentList) {
		this.compartmentList = compartmentList;
	}
	public ArrayList<String> getCompartmentReactantsList() {
		return compartmentReactantsList;
	}
	public void setCompartmentReactantsList(
			ArrayList<String> compartmentReactantsList) {
		this.compartmentReactantsList = compartmentReactantsList;
	}
	public ArrayList<String> getCompartmentProductsList() {
		return compartmentProductsList;
	}
	public void setCompartmentProductsList(ArrayList<String> compartmentProductsList) {
		this.compartmentProductsList = compartmentProductsList;
	}
	public String getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	
	@Override
	public String toString() {
		return "Pathway Connection Node [compartment=" + compartment
				+ ", reactantPathwaysIds=" + reactantPathwaysIds
				+ ", productPathwaysIds=" + productPathwaysIds
				+ ", reactionName=" + reactionName
				+ ", modelReactionName=" + modelReactionName
				+ ", dataId=" + dataId
				+ ", equation=" + equation
				+ ", modelEquation=" + modelEquation
				+ ", enzymeDataEquations=" + enzymeDataEquations
				+ ", mainReactants=" + mainReactants		
				+ ", mainProducts=" + mainProducts
				+ ", sideReactants=" + sideReactants		
				+ ", sideProducts=" + sideProducts
				+ ", mainPathwayReactants=" + mainPathwayReactants		
				+ ", mainPathwayProducts=" + mainPathwayProducts
				+ ", reversible=" + reversible
				+ ", ecNumbers=" + ecNumbers
				+ ", xPosition=" + xPosition
				+ ", yPosition=" + yPosition + "]";
	}
}
