package edu.rutgers.MOST.presentation;

import java.util.Arrays;

public class VisualizationsFindConstants {
	
	public static final String[] FIND_BY_COLUMN_LIST = 
		{ 
			GraphicalInterfaceConstants.METABOLITE_ABBREVIATION_COLUMN_NAME, 
			"KEGG Metabolite Id", "EC Number", "KEGG Reaction Id", 
			GraphicalInterfaceConstants.REACTION_ABBREVIATION_COLUMN_NAME
		};
		
		public static java.util.List<String> findByColumnList = Arrays.asList(FIND_BY_COLUMN_LIST);

}
