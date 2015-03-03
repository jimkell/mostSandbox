package edu.rutgers.MOST.data;

public class ExternalMetaboliteNode extends PathwayMetaboliteNode {
	
	private String position;
	private double offset;
	private String reversible;
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public double getOffset() {
		return offset;
	}
	public void setOffset(double offset) {
		this.offset = offset;
	}
	public String getReversible() {
		return reversible;
	}
	public void setReversible(String reversible) {
		this.reversible = reversible;
	}
	
}
