package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class PathwayReaction {
	private String name;
	private ArrayList<String> mainReactants;
	private ArrayList<String> mainProducts;
	private ArrayList<String> sideReactants;
	private ArrayList<String> sideProducts;
	private String direction; // forward or reverse, only necessary if not reversible
	private String reversible;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getReversible() {
		return reversible;
	}
	public void setReversible(String reversible) {
		this.reversible = reversible;
	}
	
	@Override
	public String toString() {
		return "Pathway Reaction [name=" + name
		+ ", mainReactants=" + mainReactants		
		+ ", mainProducts=" + mainProducts
		+ ", sideReactants=" + sideReactants
		+ ", sideProducts=" + sideProducts
		+ ", direction=" + direction
		+ ", sideProducts=" + sideProducts + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
