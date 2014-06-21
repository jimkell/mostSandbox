package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class SubsystemData {
	private String subsystem;
	private ArrayList<SubsystemReaction> reaction;
	
	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public ArrayList<SubsystemReaction> getReaction() {
		return reaction;
	}

	public void setReaction(ArrayList<SubsystemReaction> reaction) {
		this.reaction = reaction;
	}

	@Override
	public String toString() {
		return "Subsystem Data [subsystem=" + subsystem
		+ ", reaction=" + reaction + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
