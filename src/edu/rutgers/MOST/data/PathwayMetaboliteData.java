package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class PathwayMetaboliteData {

	private String id;
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
		+ ", names=" + names
		+ ", abbreviation=" + abbreviation
		+ ", level=" + level
		+ ", levelPosition=" + levelPosition + "]";
	}
	
}
