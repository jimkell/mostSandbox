package edu.rutgers.MOST.data;

public class SubsystemReaction {
	private String reactionDescription;
	private String ecNumber;
	private String additionalInformation;
	private String additionalECNumber;
	
	public String getReactionDescription() {
		return reactionDescription;
	}
	public void setReactionDescription(String reactionDescription) {
		this.reactionDescription = reactionDescription;
	}
	public String getEcNumber() {
		return ecNumber;
	}
	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public String getAdditionalECNumber() {
		return additionalECNumber;
	}
	public void setAdditionalECNumber(String additionalECNumber) {
		this.additionalECNumber = additionalECNumber;
	}
	
	@Override
	public String toString() {
		return "Subsystem Reaction [reactionDescription=" + reactionDescription
		+ ", ecNumber=" + ecNumber
		+ ", additionalInformation=" + additionalInformation
		+ ", additionalECNumber=" + additionalECNumber + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
