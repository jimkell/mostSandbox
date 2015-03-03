package edu.rutgers.MOST.data;

public class ExternalMetaboliteData extends PathwayMetaboliteData {
	// top, bottom, left, right - t,b,l,r
	private String position;
	// x or y position set by x or y position of node in cytosol
	// offset can be used to move node from this position
	private double offset;
	private String pathwayId;
	private String reactionId;
	private int direction;
	private String keggMetaboliteId;
	private String ecNumber;
	private String keggReactionId;

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
	
	public String getPathwayId() {
		return pathwayId;
	}
	public void setPathwayId(String pathwayId) {
		this.pathwayId = pathwayId;
	}
	public String getReactionId() {
		return reactionId;
	}
	public void setReactionId(String reactionId) {
		this.reactionId = reactionId;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String getKeggMetaboliteId() {
		return keggMetaboliteId;
	}
	public void setKeggMetaboliteId(String keggMetaboliteId) {
		this.keggMetaboliteId = keggMetaboliteId;
	}
	public String getEcNumber() {
		return ecNumber;
	}
	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}
	public String getKeggReactionId() {
		return keggReactionId;
	}
	public void setKeggReactionId(String keggReactionId) {
		this.keggReactionId = keggReactionId;
	}
	@Override
	public String toString() {
		return "ExternalMetabolite Data [id=" + this.getId()
		+ ", names=" + this.getNames()
		+ ", abbreviation=" + this.getAbbreviation()
		+ ", position=" + position
		+ ", offset=" + offset
		+ ", pathwayId=" + pathwayId
		+ ", keggMetaboliteId=" + keggMetaboliteId
		+ ", ecNumber=" + ecNumber
		+ ", keggReactionId=" + keggReactionId + "]";
	}
}
