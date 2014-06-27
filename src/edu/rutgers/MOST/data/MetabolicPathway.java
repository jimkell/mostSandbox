package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class MetabolicPathway {
	private String name;
	private ArrayList<ArrayList<PathwayReaction>> reactions;
	private boolean cycle;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ArrayList<PathwayReaction>> getReactions() {
		return reactions;
	}
	public void setReactions(ArrayList<ArrayList<PathwayReaction>> reactions) {
		this.reactions = reactions;
	}
	public boolean isCycle() {
		return cycle;
	}
	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}
	
	@Override
	public String toString() {
		return "Metabolic Pathway [name=" + name
		+ ", reactions=" + reactions	
		+ ", cycle=" + cycle + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
