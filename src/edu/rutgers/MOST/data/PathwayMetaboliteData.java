package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class PathwayMetaboliteData {

	private String id;
	private String keggId;
	private int occurence;
	private ArrayList<String> names = new ArrayList<String>();
	private String name;
	private String abbreviation;
	private double level;
	private double levelPosition;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeggId() {
		return keggId;
	}

	public void setKeggId(String keggId) {
		this.keggId = keggId;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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

	@Override
	public String toString() {
		return "PathwayMetabolite Data [id=" + id
		+ ", keggId=" + keggId
		+ ", occurence=" + occurence
		+ ", names=" + names
		+ ", abbreviation=" + abbreviation
		+ ", level=" + level
		+ ", levelPosition=" + levelPosition + "]";
	}
	
}
