package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MetabolicPathway {
	private String id;
	private String name;
	private Map<String, PathwayMetabolite> metabolites = new HashMap<String, PathwayMetabolite>();
	private Map<String, PathwayReaction> reactions = new HashMap<String, PathwayReaction>();
	private ArrayList<PathwayEdge> edges;
	private ArrayList<ArrayList<String>> ecNumbers = new ArrayList<ArrayList<String>>();
	// horizontal or vertical
	private String direction;
	
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
	public Map<String, PathwayMetabolite> getMetabolites() {
		return metabolites;
	}
	public void setMetabolites(Map<String, PathwayMetabolite> metabolites) {
		this.metabolites = metabolites;
	}
	public Map<String, PathwayReaction> getReactions() {
		return reactions;
	}
	public void setReactions(Map<String, PathwayReaction> reactions) {
		this.reactions = reactions;
	}
	public ArrayList<ArrayList<String>> getEcNumbers() {
		return ecNumbers;
	}
	public void setEcNumbers(ArrayList<ArrayList<String>> ecNumbers) {
		this.ecNumbers = ecNumbers;
	}
	public ArrayList<PathwayEdge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<PathwayEdge> edges) {
		this.edges = edges;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Metabolic Pathway [id=" + id
		+ ", name=" + name		
		+ ", metabolites=" + metabolites
		+ ", reactions=" + reactions
		+ ", edges=" + edges
		+ ", ecNumbers=" + ecNumbers
		+ ", direction=" + direction + "]\n";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

