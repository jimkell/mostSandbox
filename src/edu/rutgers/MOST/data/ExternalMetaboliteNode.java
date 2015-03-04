package edu.rutgers.MOST.data;

public class ExternalMetaboliteNode extends PathwayMetaboliteNode {
	
	private String position;
	private double offset;
	private int direction;
	private String reversible;
	private double fluxValue;
	private String reactionDisplayName;
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public double getOffset() {
		return offset;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
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
	public double getFluxValue() {
		return fluxValue;
	}
	public void setFluxValue(double fluxValue) {
		this.fluxValue = fluxValue;
	}
	public String getReactionDisplayName() {
		return reactionDisplayName;
	}
	public void setReactionDisplayName(String reactionDisplayName) {
		this.reactionDisplayName = reactionDisplayName;
	}
	
}
