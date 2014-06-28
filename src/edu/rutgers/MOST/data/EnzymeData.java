package edu.rutgers.MOST.data;

import java.util.ArrayList;

public class EnzymeData {
	private String id;
	private ArrayList<String> description;
	private ArrayList<String> alternateNames;
	private String catalyticActivity;
	private ArrayList<String> cofactors;
	private ArrayList<String> comments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<String> getDescription() {
		return description;
	}

	public void setDescription(ArrayList<String> description) {
		this.description = description;
	}

	public ArrayList<String> getAlternateNames() {
		return alternateNames;
	}

	public void setAlternateNames(ArrayList<String> alternateNames) {
		this.alternateNames = alternateNames;
	}

	public ArrayList<String> getCofactors() {
		return cofactors;
	}

	public void setCofactors(ArrayList<String> cofactors) {
		this.cofactors = cofactors;
	}
	
	public String getCatalyticActivity() {
		return catalyticActivity;
	}

	public void setCatalyticActivity(String catalyticActivity) {
		this.catalyticActivity = catalyticActivity;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Enzyme Data [id=" + id
		+ ", description=" + description
		+ ", alternateNames=" + alternateNames
		+ ", catalyticActivity=" + catalyticActivity
		+ ", cofactors=" + cofactors
		+ ", comments=" + comments + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
