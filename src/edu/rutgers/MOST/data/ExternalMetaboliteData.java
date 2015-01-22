package edu.rutgers.MOST.data;

public class ExternalMetaboliteData extends PathwayMetaboliteData {
	// top, bottom, left, right - t,b,l,r
	private String position;
	// x or y position set by x or y position of node in cytosol
	// offset can be used to move node from this position
	private double offset;

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
	
	@Override
	public String toString() {
		return "PathwayMetabolite Data [id=" + this.getId()
		+ ", names=" + this.getNames()
		+ ", abbreviation=" + this.getAbbreviation()
		+ ", position=" + position
		+ ", offset=" + offset + "]";
	}
}
