package edu.rutgers.MOST.data;

import java.util.Arrays;

public class PathwaysCSVFileConstants {

	public static final String PATHWAYS_FILE_NAME = "etc/visualization/pathways.csv";
	public static final String PATHWAY_GRAPH_FILE_NAME = "etc/visualization/pathway_graphing_data.csv";
	public static final String METABOLITES_FILE_NAME = "etc/visualization/metabolites.csv";
	public static final String METABOLITE_POSITIONS_FILE_NAME = "etc/visualization/pathway_metabolite_positions.csv";
	public static final String REACTIONS_FILE_NAME = "etc/visualization/reactions.csv";
	public static final String REACTION_POSITIONS_FILE_NAME = "etc/visualization/pathways_reaction_positions.csv";
	public static final String PATHWAY_DRAW_ORDER_FILE_NAME = "pathway_draw_order.csv";
	public static final String PATHWAY_CONNECTIONS_FILE_NAME = "pathway_connections.csv";
	public static final String PATHWAY_SIDE_SPECIES_FILE_NAME = "pathway_side_species.csv";
	public static final String EXTERNAL_METABOLITES_FILE_NAME = "external_metabolites.csv";
	
	public static final String[] PATHWAYS_COLUMN_NAMES = 
		{
		"Pathway ID", "Pathway Name", "KEGG ID"
		};
	
	public static final String[] PATHWAY_GRAPH_COLUMN_NAMES = 
		{
		"Pathway ID", "Pathway Name", "Direction", "Component"
		};
	
	public static final String[] PATHWAY_METABOLITES_COLUMN_NAMES = 
		{
		"KEGG ID", "Names", "Occurence"
		};
	
	public static final String[] PATHWAY_METABOLITE_POSITIONS_COLUMN_NAMES = 
		{
		"Pathway ID", "Metabolite ID", "Metabolite Level", "Metabolite Level Position", "Metabolite Names", "Metabolite Abbreviation"
		};
	
	public static final String[] PATHWAY_REACTIONS_COLUMN_NAMES = 
		{
		"Pathway ID", "Reaction ID", "Reactants", "Products", "Reversible", "EC # List", "Level", "Level Position"
		};
	
	public static final String[] PATHWAY_DRAW_ORDER_COLUMN_NAMES = 
		{
		"Pathway ID"
		};
	
	public static final String[] PATHWAY_CONNECTIONS_COLUMN_NAMES = 
		{
		"Reactants", "Products", "Reversible", "EC # List", "Length", "Positioning", "Direction"
		};
	
	public static final String[] PATHWAY_SIDE_SPECIES_COLUMN_NAMES = 
		{
		"Species Name"
		};
	
	public static final String[] EXTERNAL_METABOLITES_COLUMN_NAMES = 
		{
		"Metabolite ID", "Metabolite Names", "Metabolite Abbreviation", "Position", "Offset"
		};
	
	private static java.util.List<String> pathwayColumnsList = Arrays.asList(PATHWAYS_COLUMN_NAMES);
	private static java.util.List<String> pathwayGraphColumnsList = Arrays.asList(PATHWAY_GRAPH_COLUMN_NAMES);
	private static java.util.List<String> pathwayMetabolitesList = Arrays.asList(PATHWAY_METABOLITES_COLUMN_NAMES);
	private static java.util.List<String> pathwayMetabolitePositionsList = Arrays.asList(PATHWAY_METABOLITE_POSITIONS_COLUMN_NAMES);
	private static java.util.List<String> pathwayReactionsColumnsList = Arrays.asList(PATHWAY_REACTIONS_COLUMN_NAMES);
	private static java.util.List<String> pathwayDrawOrderColumnsList = Arrays.asList(PATHWAY_DRAW_ORDER_COLUMN_NAMES);
	private static java.util.List<String> pathwayConnectionsColumnsList = Arrays.asList(PATHWAY_CONNECTIONS_COLUMN_NAMES);
	private static java.util.List<String> pathwaySideSpeciesColumnsList = Arrays.asList(PATHWAY_SIDE_SPECIES_COLUMN_NAMES);
	private static java.util.List<String> externalMetabolitesColumnsList = Arrays.asList(EXTERNAL_METABOLITES_COLUMN_NAMES);

	public static final int PATHWAYS_ID_COLUMN = pathwayColumnsList.indexOf("Pathway ID");
	public static final int PATHWAYS_NAME_COLUMN = pathwayColumnsList.indexOf("Pathway Name");
	public static final int PATHWAYS_KEGG_ID_COLUMN = pathwayColumnsList.indexOf("KEGG ID");
	
	public static final int PATHWAY_GRAPH_ID_COLUMN = pathwayGraphColumnsList.indexOf("Pathway ID");
	// pathway name column is redundant and not used except for making it
	// easier to see which pathway is which. name data comes from pathways.csv file
	public static final int PATHWAY_GRAPH_NAME_COLUMN = pathwayGraphColumnsList.indexOf("Pathway Name");
	public static final int PATHWAY_GRAPH_DIRECTION_COLUMN = pathwayGraphColumnsList.indexOf("Direction");
	public static final int PATHWAY_GRAPH_COMPONENT_COLUMN = pathwayGraphColumnsList.indexOf("Component");

	public static final int PATHWAY_METABOLITES_KEGG_ID_COLUMN = pathwayMetabolitesList.indexOf("KEGG ID");
	public static final int PATHWAY_METABOLITES_NAMES_COLUMN = pathwayMetabolitesList.indexOf("Names");
	public static final int PATHWAY_METABOLITES_OCCURENCE_COLUMN = pathwayMetabolitesList.indexOf("Occurence");
	
	public static final int PATHWAY_METABOLITE_POSITIONS_ID_COLUMN = pathwayMetabolitePositionsList.indexOf("Pathway ID");
	public static final int PATHWAY_METABOLITE_POSITIONS_METABOLITE_ID_COLUMN = pathwayMetabolitePositionsList.indexOf("Metabolite ID");
	public static final int PATHWAY_METABOLITE_POSITIONS_LEVEL_COLUMN = pathwayMetabolitePositionsList.indexOf("Metabolite Level");
	public static final int PATHWAY_METABOLITE_POSITIONS_POSITION_COLUMN = pathwayMetabolitePositionsList.indexOf("Metabolite Level Position");
	// name column is redundant and not used except for making it
	// easier to see which pathway is which. name data comes from metabolites.csv file
	public static final int PATHWAY_METABOLITE_POSITIONS_NAME_COLUMN = pathwayMetabolitePositionsList.indexOf("Metabolite Names");
	// abbreviation still used if metabolite not found in model
	public static final int PATHWAY_METABOLITE_POSITIONS_ABBR_COLUMN = pathwayMetabolitePositionsList.indexOf("Metabolite Abbreviation");
	
	public static final int PATHWAY_REACTIONS_PATHWAY_ID_COLUMN = pathwayReactionsColumnsList.indexOf("Pathway ID");
	public static final int PATHWAY_REACTIONS_REACTION_ID_COLUMN = pathwayReactionsColumnsList.indexOf("Reaction ID");
	public static final int PATHWAY_REACTIONS_REACTANTS_COLUMN = pathwayReactionsColumnsList.indexOf("Reactants");
	public static final int PATHWAY_REACTIONS_PRODUCTS_COLUMN = pathwayReactionsColumnsList.indexOf("Products");
	public static final int PATHWAY_REACTIONS_REVERSIBLE_COLUMN = pathwayReactionsColumnsList.indexOf("Reversible");
	public static final int PATHWAY_REACTIONS_EC_NUM_LIST_COLUMN = pathwayReactionsColumnsList.indexOf("EC # List");
	public static final int PATHWAY_REACTIONS_LEVEL_COLUMN = pathwayReactionsColumnsList.indexOf("Level");
	public static final int PATHWAY_REACTIONS_POSITION_COLUMN = pathwayReactionsColumnsList.indexOf("Level Position");
	
	public static final int PATHWAY_DRAW_ORDER_PATHWAY_ID_COLUMN = pathwayDrawOrderColumnsList.indexOf("Pathway ID");
	
	public static final int PATHWAY_CONNECTIONS_REACTANTS_COLUMN = pathwayConnectionsColumnsList.indexOf("Reactants");
	public static final int PATHWAY_CONNECTIONS_PRODUCTS_COLUMN = pathwayConnectionsColumnsList.indexOf("Products");
	public static final int PATHWAY_CONNECTIONS_REVERSIBLE_COLUMN = pathwayConnectionsColumnsList.indexOf("Reversible");
	public static final int PATHWAY_CONNECTIONS_EC_NUM_LIST_COLUMN = pathwayConnectionsColumnsList.indexOf("EC # List");
	public static final int PATHWAY_CONNECTIONS_LENGTH_COLUMN = pathwayConnectionsColumnsList.indexOf("Length");
	public static final int PATHWAY_CONNECTIONS_POSITIONING_COLUMN = pathwayConnectionsColumnsList.indexOf("Positioning");
	public static final int PATHWAY_CONNECTIONS_DIRECTION_COLUMN = pathwayConnectionsColumnsList.indexOf("Direction");
	
	public static final int PATHWAY_SIDE_SPECIES_NAME_COLUMN = pathwaySideSpeciesColumnsList.indexOf("Species Name");
	
	public static final int EXTERNAL_METABOLITE_ID_COLUMN = externalMetabolitesColumnsList.indexOf("Metabolite ID");
	public static final int EXTERNAL_METABOLITE_NAME_COLUMN = externalMetabolitesColumnsList.indexOf("Metabolite Names");
	public static final int EXTERNAL_METABOLITE_ABBR_COLUMN = externalMetabolitesColumnsList.indexOf("Metabolite Abbreviation");
	public static final int EXTERNAL_METABOLITE_POSITION_COLUMN = externalMetabolitesColumnsList.indexOf("Position");
	public static final int EXTERNAL_METABOLITE_OFFSET_COLUMN = externalMetabolitesColumnsList.indexOf("Offset");
	
}
