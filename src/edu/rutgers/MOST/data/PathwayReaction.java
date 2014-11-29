package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class PathwayReaction {
	private String id;
	private String name; 
	private double level;
	private double levelPosition;
	private String ecNumber;
	private ArrayList<String> ecNumbers;
	private String equation;
	private ArrayList<String> mainReactants;
	private ArrayList<String> mainProducts;
	private ArrayList<String> sideReactants;
	private ArrayList<String> sideProducts;
	private String reversible;
	private String subsystem;
	private ArrayList<String> compartmentList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public void setLevelPosition(double levelPosition) {
		this.levelPosition = levelPosition;
	}
	public String getEcNumber() {
		return ecNumber;
	}
	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}
	public ArrayList<String> getEcNumbers() {
		return ecNumbers;
	}
	public void setEcNumbers(ArrayList<String> ecNumbers) {
		this.ecNumbers = ecNumbers;
	}
	public String getEquation() {
		return equation;
	}
	public void setEquation(String equation) {
		this.equation = equation;
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
	public String getReversible() {
		return reversible;
	}
	public void setReversible(String reversible) {
		this.reversible = reversible;
	}
	public String getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	public ArrayList<String> getCompartmentList() {
		return compartmentList;
	}
	public void setCompartmentList(ArrayList<String> compartmentList) {
		this.compartmentList = compartmentList;
	}
	
	@Override
	public String toString() {
		return "Pathway Reaction [id=" + id
		+ ", name=" + name
		+ ", level=" + level
		+ ", levelPosition=" + levelPosition
		+ ", ecNumber=" + ecNumber	
		+ ", ecNumbers=" + ecNumbers
		+ ", equation=" + equation
		+ ", mainReactants=" + mainReactants		
		+ ", mainProducts=" + mainProducts
		+ ", sideReactants=" + sideReactants
		+ ", sideProducts=" + sideProducts
		+ ", reversible=" + reversible
		+ ", subsystem=" + subsystem
		+ ", compartmentList=" + compartmentList + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

