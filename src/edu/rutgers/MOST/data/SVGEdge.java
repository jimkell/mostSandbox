package edu.rutgers.MOST.data;

import java.awt.Color;
import java.util.ArrayList;

public class SVGEdge {

	ArrayList<String[]> endpoints;
	private Color stroke;
	private String strokeWidth;
	private String markerId;
	// changing refX moves arrow along edge
	private String refX;
	
	public ArrayList<String[]> getEndpoints() {
		return endpoints;
	}
	public void setEndpoints(ArrayList<String[]> endpoints) {
		this.endpoints = endpoints;
	}
	public Color getStroke() {
		return stroke;
	}
	public void setStroke(Color stroke) {
		this.stroke = stroke;
	}
	public String getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(String strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String getMarkerId() {
		return markerId;
	}
	public void setMarkerId(String markerId) {
		this.markerId = markerId;
	}
	public String getRefX() {
		return refX;
	}
	public void setRefX(String refX) {
		this.refX = refX;
	}
	
}
