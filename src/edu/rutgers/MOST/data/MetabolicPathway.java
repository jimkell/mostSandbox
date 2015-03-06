package edu.rutgers.MOST.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MetabolicPathway {
	private String id;
	private String name;
	private String keggId;
	private Map<String, PathwayMetaboliteData> metabolitesData = new HashMap<String, PathwayMetaboliteData>();
	private Map<String, PathwayMetaboliteNode> metabolitesNodes = new HashMap<String, PathwayMetaboliteNode>();
	private Map<String, PathwayReactionData> reactionsData = new HashMap<String, PathwayReactionData>();
	private Map<String, PathwayReactionNode> reactionsNodes = new HashMap<String, PathwayReactionNode>();
	private Map<String, ExternalMetaboliteData> externalMetabolitesData = new HashMap<String, ExternalMetaboliteData>();
	private Map<String, ExternalMetaboliteNode> externalMetabolitesNodes = new HashMap<String, ExternalMetaboliteNode>();
	private Map<String, ExternalMetaboliteData> transportMetabolitesData = new HashMap<String, ExternalMetaboliteData>();
	private Map<String, ExternalMetaboliteNode> transportMetabolitesNodes = new HashMap<String, ExternalMetaboliteNode>();
	private ArrayList<PathwayEdge> edges;
	private ArrayList<ArrayList<String>> ecNumbers = new ArrayList<ArrayList<String>>();
	// horizontal or vertical
	private String direction;
	private int component;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeggId() {
		return keggId;
	}
	public void setKeggId(String keggId) {
		this.keggId = keggId;
	}
	public Map<String, PathwayMetaboliteData> getMetabolitesData() {
		return metabolitesData;
	}
	public void setMetabolitesData(
			Map<String, PathwayMetaboliteData> metabolitesData) {
		this.metabolitesData = metabolitesData;
	}
	public Map<String, PathwayMetaboliteNode> getMetabolitesNodes() {
		return metabolitesNodes;
	}
	public void setMetabolitesNodes(
			Map<String, PathwayMetaboliteNode> metabolitesNodes) {
		this.metabolitesNodes = metabolitesNodes;
	}
	public Map<String, PathwayReactionData> getReactionsData() {
		return reactionsData;
	}
	public void setReactionsData(Map<String, PathwayReactionData> reactionsData) {
		this.reactionsData = reactionsData;
	}
	public Map<String, PathwayReactionNode> getReactionsNodes() {
		return reactionsNodes;
	}
	public void setReactionsNodes(Map<String, PathwayReactionNode> reactionsNodes) {
		this.reactionsNodes = reactionsNodes;
	}
	public Map<String, ExternalMetaboliteData> getExternalMetabolitesData() {
		return externalMetabolitesData;
	}
	public void setExternalMetabolitesData(
			Map<String, ExternalMetaboliteData> externalMetabolitesData) {
		this.externalMetabolitesData = externalMetabolitesData;
	}
	public Map<String, ExternalMetaboliteNode> getExternalMetabolitesNodes() {
		return externalMetabolitesNodes;
	}
	public void setExternalMetabolitesNodes(
			Map<String, ExternalMetaboliteNode> externalMetabolitesNodes) {
		this.externalMetabolitesNodes = externalMetabolitesNodes;
	}
	public Map<String, ExternalMetaboliteData> getTransportMetabolitesData() {
		return transportMetabolitesData;
	}
	public void setTransportMetabolitesData(
			Map<String, ExternalMetaboliteData> transportMetabolitesData) {
		this.transportMetabolitesData = transportMetabolitesData;
	}
	public Map<String, ExternalMetaboliteNode> getTransportMetabolitesNodes() {
		return transportMetabolitesNodes;
	}
	public void setTransportMetabolitesNodes(
			Map<String, ExternalMetaboliteNode> transportMetabolitesNodes) {
		this.transportMetabolitesNodes = transportMetabolitesNodes;
	}
	public ArrayList<ArrayList<String>> getEcNumbers() {
		return ecNumbers;
	}
	public void setEcNumbers(ArrayList<ArrayList<String>> ecNumbers) {
		this.ecNumbers = ecNumbers;
	}
	public ArrayList<PathwayEdge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<PathwayEdge> edges) {
		this.edges = edges;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public int getComponent() {
		return component;
	}
	public void setComponent(int component) {
		this.component = component;
	}
	@Override
	public String toString() {
		return "Metabolic Pathway [id=" + id
		+ ", name=" + name	
		+ ", keggId=" + keggId
		+ ", metabolitesData=" + metabolitesData
		+ ", metabolitesNodes=" + metabolitesNodes
		+ ", reactionsData=" + reactionsData
		+ ", reactionsNodes=" + reactionsNodes
		+ ", externalMetabolitesData=" + externalMetabolitesData
		+ ", externalMetabolitesNodes=" + externalMetabolitesNodes
		+ ", transportMetabolitesData=" + transportMetabolitesData
		+ ", transportMetabolitesNodes=" + transportMetabolitesNodes
		+ ", edges=" + edges
		+ ", ecNumbers=" + ecNumbers
		+ ", direction=" + direction
		+ ", component=" + component + "]\n";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

