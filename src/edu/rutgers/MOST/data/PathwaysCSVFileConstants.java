package edu.rutgers.MOST.data;

import java.util.Arrays;

public class PathwaysCSVFileConstants {

	public static final String PATHWAYS_FILE_NAME = "etc/visualization/pathways.csv";
	public static final String PATHWAY_NAMES_FILE_NAME = "etc/visualization/pathway_names.csv";
	//public static final String PATHWAY_GRAPH_FILE_NAME = "etc/visualization/pathway_graphing_data.csv";
	public static final String PATHWAY_GRAPH_FILE_NAME = "etc/visualization/pathway_graphing_data1.csv";
	public static final String METABOLITES_FILE_NAME = "etc/visualization/metabolites.csv";
	// since there are only a few metabolites that have KEGG ids that are not in the metabolites.csv file generated
	// from the KEGG Crawler, just add them manually and append to map
	public static final String ADDITIONAL_METABOLITES_FILE_NAME = "etc/visualization/additional_metabolites.csv";
	//public static final String METABOLITE_POSITIONS_FILE_NAME = "etc/visualization/pathway_metabolite_positions.csv";
	public static final String METABOLITE_POSITIONS_FILE_NAME = "etc/visualization/pathway_metabolite_positions1.csv";
	public static final String REACTIONS_FILE_NAME = "etc/visualization/reactions.csv";
	//public static final String REACTION_POSITIONS_FILE_NAME = "etc/visualization/pathways_reaction_positions.csv";
	public static final String REACTION_POSITIONS_FILE_NAME = "etc/visualization/pathways_reaction_positions1.csv";
	//public static final String PATHWAY_DRAW_ORDER_FILE_NAME = "etc/visualization/pathway_draw_order.csv";
	public static final String PATHWAY_DRAW_ORDER_FILE_NAME = "etc/visualization/pathway_draw_order1.csv";
	public static final String PATHWAY_SIDE_SPECIES_FILE_NAME = "etc/visualization/pathway_side_species.csv";
	//public static final String EXTERNAL_METABOLITES_FILE_NAME = "etc/visualization/external_metabolites.csv";
	public static final String EXTERNAL_METABOLITES_FILE_NAME = "etc/visualization/external_metabolites1.csv";
	//public static final String TRANSPORT_METABOLITES_FILE_NAME = "etc/visualization/transport_metabolites.csv";
	public static final String TRANSPORT_METABOLITES_FILE_NAME = "etc/visualization/transport_metabolites1.csv";
	
	public static final String[] PATHWAYS_COLUMN_NAMES = 
		{
		"Pathway ID", "Pathway Name", "KEGG ID"
		};
	
	public static final String[] PATHWAY_NAMES_COLUMN_NAMES = 
		{
		"Pathway ID", "Pathway KEGG Ids", "Pathway Level", "Pathway Level Position", "Pathway Name"
		};
	
	public static final String[] PATHWAY_GRAPH_COLUMN_NAMES = 
		{
		"Pathway ID", "Pathway Name", "Component"
		};
	
	public static final String[] METABOLITES_COLUMN_NAMES = 
		{
		"KEGG ID", "Names", "Occurence"
		};
	
	public static final String[] ADDITIONAL_METABOLITES_COLUMN_NAMES = 
		{
		"KEGG ID", "Additional KEGG IDs", "Name"
		};
	
	public static final String[] METABOLITE_POSITIONS_COLUMN_NAMES = 
		{
		"Pathway ID", "Metabolite ID", "Metabolite Level", "Metabolite Level Position", 
		"Metabolite Names", "Metabolite Abbreviation", "KEGG ID", "Border", "Type"
		};
	
	public static final String[] REACTIONS_COLUMN_NAMES = 
		{
		"Reaction KEGG ID",	"Reactants", "Products", "Reversability", "EC list", "Occurences", "Names"
		};
	
	public static final String[] REACTION_POSITIONS_COLUMN_NAMES = 
		{
		"Pathway ID", "Reaction ID", "Reactants", "Products", "Reversible", "EC # List", 
		"Level", "Level Position", "KEGG IDs"
		};
	
	public static final String[] PATHWAY_DRAW_ORDER_COLUMN_NAMES = 
		{
		"Pathway ID"
		};
	
	public static final String[] PATHWAY_SIDE_SPECIES_COLUMN_NAMES = 
		{
		"Species Name"
		};

	public static final String[] EXTERNAL_METABOLITES_COLUMN_NAMES = 
		{
		"Pathway ID", "Metabolite ID", "Reaction ID", "Metabolite Name", "Metabolite Abbreviation", "Position", "Offset",
		"Direction", "KEGG Metabolite ID", "EC Number", "KEGG Reaction ID"
		};
	
	public static final String[] TRANSPORT_METABOLITES_COLUMN_NAMES = 
		{
		"Pathway ID", "Metabolite ID", "Metabolite Name", "Metabolite Abbreviation", "Position", "Offset",
		"Direction", "KEGG Metabolite ID"
		};
	
	private static java.util.List<String> pathwayColumnsList = Arrays.asList(PATHWAYS_COLUMN_NAMES);
	private static java.util.List<String> pathwayNamesColumnsList = Arrays.asList(PATHWAY_NAMES_COLUMN_NAMES);
	private static java.util.List<String> pathwayGraphColumnsList = Arrays.asList(PATHWAY_GRAPH_COLUMN_NAMES);
	private static java.util.List<String> metabolitesList = Arrays.asList(METABOLITES_COLUMN_NAMES);
	private static java.util.List<String> additionalMetabolitesList = Arrays.asList(ADDITIONAL_METABOLITES_COLUMN_NAMES);
	private static java.util.List<String> metabolitePositionsList = Arrays.asList(METABOLITE_POSITIONS_COLUMN_NAMES);
	private static java.util.List<String> reactionsColumnsList = Arrays.asList(REACTIONS_COLUMN_NAMES);
	private static java.util.List<String> reactionPositionsColumnsList = Arrays.asList(REACTION_POSITIONS_COLUMN_NAMES);
	private static java.util.List<String> pathwayDrawOrderColumnsList = Arrays.asList(PATHWAY_DRAW_ORDER_COLUMN_NAMES);
	private static java.util.List<String> pathwaySideSpeciesColumnsList = Arrays.asList(PATHWAY_SIDE_SPECIES_COLUMN_NAMES);
	private static java.util.List<String> externalMetabolitesColumnsList = Arrays.asList(EXTERNAL_METABOLITES_COLUMN_NAMES);
	private static java.util.List<String> transportMetabolitesColumnsList = Arrays.asList(TRANSPORT_METABOLITES_COLUMN_NAMES);

	public static final int PATHWAYS_ID_COLUMN = pathwayColumnsList.indexOf("Pathway ID");
	public static final int PATHWAYS_NAME_COLUMN = pathwayColumnsList.indexOf("Pathway Name");
	public static final int PATHWAYS_KEGG_ID_COLUMN = pathwayColumnsList.indexOf("KEGG ID");
	
	public static final int PATHWAY_NAMES_ID_COLUMN = pathwayNamesColumnsList.indexOf("Pathway ID");
	public static final int PATHWAY_NAMES_KEGG_IDS_COLUMN = pathwayNamesColumnsList.indexOf("Pathway KEGG Ids");
	public static final int PATHWAY_NAMES_LEVEL_COLUMN = pathwayNamesColumnsList.indexOf("Pathway Level");
	public static final int PATHWAY_NAMES_LEVEL_POSITION_COLUMN = pathwayNamesColumnsList.indexOf("Pathway Level Position");
	public static final int PATHWAY_NAMES_NAME_COLUMN = pathwayNamesColumnsList.indexOf("Pathway Name");
	
	public static final int PATHWAY_GRAPH_ID_COLUMN = pathwayGraphColumnsList.indexOf("Pathway ID");
	// pathway name column is redundant and not used except for making it
	// easier to see which pathway is which. name data comes from pathways.csv file
	public static final int PATHWAY_GRAPH_NAME_COLUMN = pathwayGraphColumnsList.indexOf("Pathway Name");
	public static final int PATHWAY_GRAPH_COMPONENT_COLUMN = pathwayGraphColumnsList.indexOf("Component");

	public static final int METABOLITES_KEGG_ID_COLUMN = metabolitesList.indexOf("KEGG ID");
	public static final int METABOLITES_NAMES_COLUMN = metabolitesList.indexOf("Names");
	public static final int METABOLITES_OCCURENCE_COLUMN = metabolitesList.indexOf("Occurence");
	
	public static final int ADDITIONAL_METABOLITES_KEGG_ID_COLUMN = additionalMetabolitesList.indexOf("KEGG ID");
	public static final int ADDITIONAL_METABOLITES_ALTERNATE_KEGG_IDS_COLUMN = additionalMetabolitesList.indexOf("Additional KEGG IDs");
	public static final int ADDITIONAL_METABOLITES_NAME_COLUMN = additionalMetabolitesList.indexOf("Name");
	
	public static final int METABOLITE_POSITIONS_ID_COLUMN = metabolitePositionsList.indexOf("Pathway ID");
	public static final int METABOLITE_POSITIONS_METABOLITE_ID_COLUMN = metabolitePositionsList.indexOf("Metabolite ID");
	public static final int METABOLITE_POSITIONS_LEVEL_COLUMN = metabolitePositionsList.indexOf("Metabolite Level");
	public static final int METABOLITE_POSITIONS_POSITION_COLUMN = metabolitePositionsList.indexOf("Metabolite Level Position");
	// name column is redundant and not used except for making it
	// easier to see which pathway is which. name data comes from metabolites.csv file
	public static final int METABOLITE_POSITIONS_NAME_COLUMN = metabolitePositionsList.indexOf("Metabolite Names");
	// abbreviation still used if metabolite not found in model
	public static final int METABOLITE_POSITIONS_ABBR_COLUMN = metabolitePositionsList.indexOf("Metabolite Abbreviation");
	public static final int METABOLITE_POSITIONS_KEGG_ID_COLUMN = metabolitePositionsList.indexOf("KEGG ID");
	public static final int METABOLITE_POSITIONS_BORDER_COLUMN = metabolitePositionsList.indexOf("Border");
	public static final int METABOLITE_POSITIONS_TYPE_COLUMN = metabolitePositionsList.indexOf("Type");
	
	public static final int REACTIONS_KEGG_ID_COLUMN = reactionsColumnsList.indexOf("Reaction KEGG ID");
	public static final int REACTIONS_KEGG_REACTANTS_COLUMN = reactionsColumnsList.indexOf("Reactants");
	public static final int REACTIONS_KEGG_PRODUCTS_COLUMN = reactionsColumnsList.indexOf("Products");
	public static final int REACTIONS_REVERSABILITY_COLUMN = reactionsColumnsList.indexOf("Reversability");
	public static final int REACTIONS_EC_LIST_COLUMN = reactionsColumnsList.indexOf("EC list");
	public static final int REACTIONS_OCCURENCES_COLUMN = reactionsColumnsList.indexOf("Occurences");
	public static final int REACTIONS_NAMES_COLUMN = reactionsColumnsList.indexOf("Names");
	
	public static final int REACTIONS_PATHWAY_ID_COLUMN = reactionPositionsColumnsList.indexOf("Pathway ID");
	public static final int REACTIONS_REACTION_ID_COLUMN = reactionPositionsColumnsList.indexOf("Reaction ID");
	public static final int REACTIONS_REACTANTS_COLUMN = reactionPositionsColumnsList.indexOf("Reactants");
	public static final int REACTIONS_PRODUCTS_COLUMN = reactionPositionsColumnsList.indexOf("Products");
	public static final int REACTIONS_REVERSIBLE_COLUMN = reactionPositionsColumnsList.indexOf("Reversible");
	public static final int REACTIONS_EC_NUM_LIST_COLUMN = reactionPositionsColumnsList.indexOf("EC # List");
	public static final int REACTIONS_LEVEL_COLUMN = reactionPositionsColumnsList.indexOf("Level");
	public static final int REACTIONS_POSITION_COLUMN = reactionPositionsColumnsList.indexOf("Level Position");
	public static final int REACTIONS_POSITION_KEGG_IDS_COLUMN = reactionPositionsColumnsList.indexOf("KEGG IDs");
	
	public static final int PATHWAY_DRAW_ORDER_PATHWAY_ID_COLUMN = pathwayDrawOrderColumnsList.indexOf("Pathway ID");

	public static final int PATHWAY_SIDE_SPECIES_NAME_COLUMN = pathwaySideSpeciesColumnsList.indexOf("Species Name");
	
	public static final int EXTERNAL_METABOLITE_PATHWAY_ID_COLUMN = externalMetabolitesColumnsList.indexOf("Pathway ID");
	public static final int EXTERNAL_METABOLITE_ID_COLUMN = externalMetabolitesColumnsList.indexOf("Metabolite ID");
	public static final int EXTERNAL_METABOLITE_REACTION_ID_COLUMN = externalMetabolitesColumnsList.indexOf("Reaction ID");
	public static final int EXTERNAL_METABOLITE_NAME_COLUMN = externalMetabolitesColumnsList.indexOf("Metabolite Name");
	public static final int EXTERNAL_METABOLITE_ABBR_COLUMN = externalMetabolitesColumnsList.indexOf("Metabolite Abbreviation");
	public static final int EXTERNAL_METABOLITE_POSITION_COLUMN = externalMetabolitesColumnsList.indexOf("Position");
	public static final int EXTERNAL_METABOLITE_OFFSET_COLUMN = externalMetabolitesColumnsList.indexOf("Offset");
	// -1 if reactant, 1 if product
	public static final int EXTERNAL_METABOLITE_DIRECTION_COLUMN = externalMetabolitesColumnsList.indexOf("Direction");
	// these fields may be redundant
	public static final int EXTERNAL_KEGG_METABOLITE_ID_COLUMN = externalMetabolitesColumnsList.indexOf("KEGG Metabolite ID");
	public static final int EXTERNAL_EC_NUMBER_COLUMN = externalMetabolitesColumnsList.indexOf("EC Number");
	public static final int EXTERNAL_KEGG_REACTION_ID_COLUMN = externalMetabolitesColumnsList.indexOf("KEGG Reaction ID");
	
	public static final int TRANSPORT_METABOLITE_PATHWAY_ID_COLUMN = transportMetabolitesColumnsList.indexOf("Pathway ID");
	public static final int TRANSPORT_METABOLITE_ID_COLUMN = transportMetabolitesColumnsList.indexOf("Metabolite ID");
	public static final int TRANSPORT_METABOLITE_NAME_COLUMN = transportMetabolitesColumnsList.indexOf("Metabolite Name");
	public static final int TRANSPORT_METABOLITE_ABBR_COLUMN = transportMetabolitesColumnsList.indexOf("Metabolite Abbreviation");
	public static final int TRANSPORT_METABOLITE_POSITION_COLUMN = transportMetabolitesColumnsList.indexOf("Position");
	public static final int TRANSPORT_METABOLITE_OFFSET_COLUMN = transportMetabolitesColumnsList.indexOf("Offset");
	// -1 if reactant, 1 if product
	public static final int TRANSPORT_METABOLITE_DIRECTION_COLUMN = transportMetabolitesColumnsList.indexOf("Direction");
	public static final int TRANSPORT_KEGG_METABOLITE_ID_COLUMN = transportMetabolitesColumnsList.indexOf("KEGG Metabolite ID");
	
	public static final String MAIN_METABOLITE_TYPE = "m";
	public static final String SMALL_MAIN_METABOLITE_TYPE = "sm";
	public static final String SIDE_METABOLITE_TYPE = "s";
	// Designates a main metabolite that occurs in more than one location, where if reaction
	// in which metabolite participates is not present, metabolite will not be drawn. Prevents
	// "orphan" metabolites. Example: pyruvate occurs in many reactions that are not found
	// in iAF1260 model.
	public static final String OPTIONAL_METABOLITE_TYPE = "o";

}
