package edu.rutgers.MOST.presentation;

import java.util.Arrays;

public class CompartmentsTableConstants {
	
	public static final String TITLE = GraphicalInterfaceConstants.TITLE + " - " + "Compartments";

	public static final String[] VISIBLE_COLUMN_NAMES = 
		{
			"Abbreviation", 	
			"Name",	
			"Outside",	
		};
	
	public static java.util.List<String> visibleColumnsList = Arrays.asList(VISIBLE_COLUMN_NAMES);
	
	public static final int ABBREVIATION_COLUMN = visibleColumnsList.indexOf("Abbreviation");
	public static final int ABBREVIATION_WIDTH = 150; 
	public static final int NAME_COLUMN = visibleColumnsList.indexOf("Name");
	public static final int NAME_WIDTH = 150; 
	public static final int OUTSIDE_COLUMN = visibleColumnsList.indexOf("Outside");
	public static final int OUTSIDE_WIDTH = 150;
	
	public static final int WIDTH = 450;
	public static final int HEIGHT = 300;
	
	// Compartments Name Dialog
	public static final String[] CYTOSOL_FILTER =
		{"cyt"
		}; 

	public static final String[] PERIPLASM_FILTER =
		{"peri"
		};

	public static final String[] EXTRA_ORGANISM_FILTER =
		{"extra"
		};
	
}
