package edu.rutgers.MOST.data;

import java.awt.Color;
import java.util.ArrayList;

public class SVGEdge {

	ArrayList<String[]> endpoints;
	private Color stroke;
	private String strokeWidth;
	
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
	
}
