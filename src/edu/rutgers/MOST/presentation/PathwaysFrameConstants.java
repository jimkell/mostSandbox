package edu.rutgers.MOST.presentation;

import java.awt.Color;

public class PathwaysFrameConstants {

	public static final String NODE_INFORMATION_TITLE = "Node Information";
	
	public static final float SCALING_FACTOR = (float) 1.2;
	public static final float START_SCALING_FACTOR = (float) 0.1;
	public static final int GRAPH_WIDTH = 24000;
	public static final int GRAPH_HEIGHT = 18000;
	public static final int BORDER_WIDTH = 100;
	public static final int BORDER_HEIGHT = 100;
	public static final int PERIPLASM_WIDTH = 400;
	public static final int PERIPLASM_HEIGHT = 200;
	public static final int TRANSPORT_WIDTH_INCREMENT = 200;
	public static final int TRANSPORT_HEIGHT_INCREMENT = 100;
	
	public static final int OFFSET_WIDTH = 140;
	public static final int OFFSET_HEIGHT = 70;
	
	public static final double BORDER_THICKNESS = 4;
	
	public static final int TOP_SPACE = 300;
	public static final int BOTTOM_SPACE = 400;
	public static final int START_Y = 5600;
   	
//	public static final int HORIZONTAL_INCREMENT = 350;
//	public static final int VERTICAL_INCREMENT = 175;
	public static final int HORIZONTAL_INCREMENT = 400; 
//	public static final int VERTICAL_INCREMENT = 160;
	public static final int VERTICAL_INCREMENT = 380; // was 360, change made TCA cycle more circular
	public static final int PATHWAY_NAME_NODE_WIDTH = 200;
	public static final int PATHWAY_NAME_NODE_HEIGHT = 48;
	public static final int SIDE_NODE_WIDTH = 90;
	public static final int SIDE_NODE_HEIGHT = 28;
	public static final int METABOLITE_NODE_WIDTH = 100;
	public static final int METABOLITE_NODE_HEIGHT = 36;
	public static final int SMALL_MAIN_METABOLITE_NODE_WIDTH = 70;
	public static final int SMALL_MAIN_METABOLITE_NODE_HEIGHT = 36;
	public static final int SIDE_METABOLITE_NODE_WIDTH = 60;
	public static final int SIDE_METABOLITE_NODE_HEIGHT = 20;
	public static final int REACTION_NODE_WIDTH = 120;
	public static final int REACTION_NODE_HEIGHT = 36;
	public static final int PATHWAY_NAME_NODE_FONT_SIZE = 36;
	public static final int METABOLITE_NODE_FONT_SIZE = 28;
	public static final int SMALL_MAIN_METABOLITE_NODE_FONT_SIZE = 28;
	public static final int SIDE_METABOLITE_NODE_FONT_SIZE = 14;
	public static final int REACTION_NODE_FONT_SIZE = 28;
	public static final int SIDE_NODE_FONT_SIZE = 17;
	public static final int METABOLITE_NODE_MAX_CHARS = 5;
	public static final int SMALL_MAIN_METABOLITE_NODE_MAX_CHARS = 3;
	public static final int SIDE_METABOLITE_NODE_MAX_CHARS = 5;
	public static final int REACTION_NODE_MAX_CHARS = 7;
	public static final int PATHWAY_NAME_NODE_MAX_CHARS = 10;
	
	public static final int METABOLITE_NODE_ELLIPSIS_CORRECTION = 2;
	public static final int SMALL_MAIN_METABOLITE_NODE_ELLIPSIS_CORRECTION = 2;
	public static final int SIDE_METABOLITE_NODE_ELLIPSIS_CORRECTION = 2;
	public static final int REACTION_NODE_ELLIPSIS_CORRECTION = 2;
	public static final int PATHWAY_NAME_NODE_ELLIPSIS_CORRECTION = 2;
	// positions to start text in node
	public static final int PATHWAY_NAME_NODE_XPOS = 0;
	public static final int PATHWAY_NAME_NODE_YPOS = 34;
	public static final int METABOLITE_NODE_XPOS = 0;
	public static final int METABOLITE_NODE_YPOS = 23;
	public static final int SMALL_MAIN_METABOLITE_NODE_XPOS = 0;
	public static final int SMALL_MAIN_METABOLITE_NODE_YPOS = 23;
	public static final int SIDE_METABOLITE_NODE_XPOS = 0;
	public static final int SIDE_METABOLITE_NODE_YPOS = 13;
	public static final int REACTION_NODE_XPOS = 0;
	public static final int REACTION_NODE_YPOS = 23;
	public static final int SIDE_NODE_XPOS = 0;
	public static final int SIDE_NODE_YPOS = 23;
	
	public static final int PATHWAY_NAME_BORDER_WIDTH = 4;
	public static final int METABOLITE_BORDER_WIDTH = 4;
	public static final int SIDE_METABOLITE_BORDER_WIDTH = 2;
	
	public static final double DEFAULT_EDGE_WIDTH = 0.0; 
//	public static final int ARROW_LENGTH = 25; 
//	public static final int ARROW_WIDTH = 30;
//	public static final int ARROW_NOTCH = 12;
	public static final int ARROW_LENGTH = 20; 
	public static final int ARROW_WIDTH = 24;
	public static final int ARROW_NOTCH = 10;
	public static final double INFINITE_FLUX_RATIO = 0.95;
	public static final double INFINITE_FLUX_WIDTH = 16.0;
	public static final double SECONDARY_MAX_FLUX_WIDTH = 8.0;
	public static final double TOP_FLUX_RATIO = 0.5;
	public static final double TOP_FLUX_WIDTH = 6.0;
	public static final double MID_FLUX_RATIO = 0.25;
	public static final double MID_FLUX_WIDTH = 4.0;
	public static final double LOW_MID_FLUX_RATIO = 0.125;
	public static final double LOW_MID_FLUX_WIDTH = 3.0;
	public static final double LOWER_MID_FLUX_RATIO = 0.05;
	public static final double LOWER_MID_FLUX_WIDTH = 2.0;
	public static final double MINIMUM_FLUX_RATIO = 0.02;
	public static final double MINIMUM_FLUX_WIDTH = 1.0;
//	public static final double TOP_FLUX_RATIO = 0.5;
//	public static final double TOP_FLUX_WIDTH = 8.0;
//	public static final double MID_FLUX_RATIO = 0.25;
//	public static final double MID_FLUX_WIDTH = 6.0;
//	public static final double LOW_MID_FLUX_RATIO = 0.125;
//	public static final double LOW_MID_FLUX_WIDTH = 4.0;
//	public static final double LOWER_MID_FLUX_RATIO = 0.05;
//	public static final double LOWER_MID_FLUX_WIDTH = 3.0;
//	public static final double MINIMUM_FLUX_RATIO = 0.025;
//	public static final double MINIMUM_FLUX_WIDTH = 2.0;
	
	public static final int PATHWAYS_COMPONENT = 0;
	public static final int PROCESSES_COMPONENT = 1;
	
	//public static final int PHOSPHORYLATION_X_OFFSET = 3*HORIZONTAL_INCREMENT;
	public static final int PHOSPHORYLATION_X_OFFSET = 1400;
	public static final int PHOSPHORYLATION_Y_OFFSET = VERTICAL_INCREMENT;
	public static final int RIGHT_BORDER_INCREMENT = 0;
	
	// there is an area between f6p and pep in glycolysis where there are no transport reactions.
	// this is a good place to put transport reactions of side species, cis-acon starts second area
	public static final String SIDE_SPECIES_EXCHANGE_START_POSITION_METABOLITE = "C00085";
	public static final String SIDE_SPECIES_EXCHANGE_END_POSITION_METABOLITE = "C00074";
	public static final String SIDE_SPECIES_EXCHANGE_SECOND_START_POSITION_METABOLITE = "C00417";
	
	public static final String PERIPLASM_SUFFIX = "_p";
	public static final String EXTRAORGANISM_SUFFIX = "_e";
	
	// prevents nodes with calculated positions from touching
	public static final double NODE_SPACING_CORRECTION = 1.1;
	
	public static final Color PATHWAY_NAME_COLOR = Color.orange;
	
	public static final Color NODE_BACKGROUND_DETAULT_COLOR = Color.white;
//	public static final Color METABOLITE_NODE_DETAULT_COLOR = Color.white;
//	public static final Color REACTION_NODE_DETAULT_COLOR = Color.white;
	public static final Color REACTION_NODE_DETAULT_FONT_COLOR = Color.blue;
	
	public static final Color REACTION_KO_FONT_COLOR = Color.red;
	
	// not found colors from http://www.rapidtables.com/web/color/RGB_Color.htm
	// light blue
	public static final Color REACTION_NOT_FOUND_FONT_COLOR = new Color(204,229,255);
	// one step darker 
	//public static final Color REACTION_NOT_FOUND_FONT_COLOR = new Color(153,204,255);
	// light gray
	public static final Color METABOLITE_NOT_FOUND_COLOR = new Color(224,224,224);
	// one step darker 
	//public static final Color METABOLITE_NOT_FOUND_COLOR = new Color(192,192,192);

	// formerly used yellow and orange, but this is confusing since some metabolites
	// have no borders using the Roche map convention
	//public static final Color METABOLITE_NOT_FOUND_COLOR = Color.yellow;
	//public static final Color REACTION_NOT_FOUND_FONT_COLOR = Color.orange;
	
	public static final double BLACK_COLOR_VALUE = 0.0;
	public static final double GRAY_COLOR_VALUE = 1.0;
	public static final double RED_COLOR_VALUE = 2.0;
	public static final double GREEN_COLOR_VALUE = 3.0;
	public static final double BLUE_COLOR_VALUE = 4.0;
	
	public static final double DEFAULT_COLOR_VALUE = BLACK_COLOR_VALUE;
	
}
