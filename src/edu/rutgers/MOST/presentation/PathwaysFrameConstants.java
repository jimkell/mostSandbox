package edu.rutgers.MOST.presentation;

public class PathwaysFrameConstants {

	public static final float SCALING_FACTOR = (float) 1.2;
	public static final int GRAPH_WIDTH = 10000;
	public static final int GRAPH_HEIGHT = 8000;
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
	public static final int BOTTOM_SPACE = 300;
	public static final int START_Y = 3000;
   	
//	public static final int HORIZONTAL_INCREMENT = 350;
//	public static final int VERTICAL_INCREMENT = 175;
	public static final int HORIZONTAL_INCREMENT = 400;
	public static final int VERTICAL_INCREMENT = 160;
	public static final int PATHWAY_NAME_NODE_WIDTH = 120;
	public static final int PATHWAY_NAME_NODE_HEIGHT = 50;
	public static final int SIDE_NODE_WIDTH = 90;
	public static final int SIDE_NODE_HEIGHT = 28;
	//public static final int METABOLITE_NODE_WIDTH = 75;
	public static final int METABOLITE_NODE_WIDTH = 110;
//	public static final int METABOLITE_NODE_HEIGHT = 25;
	public static final int METABOLITE_NODE_HEIGHT = 36;
	//public static final int REACTION_NODE_WIDTH = 110;
	public static final int REACTION_NODE_WIDTH = 130;
//	public static final int REACTION_NODE_HEIGHT = 25;
	public static final int REACTION_NODE_HEIGHT = 36;
	public static final int PATHWAY_NAME_NODE_FONT_SIZE = 40;
//	public static final int METABOLITE_NODE_FONT_SIZE = 16;
//	public static final int REACTION_NODE_FONT_SIZE = 16;
	public static final int METABOLITE_NODE_FONT_SIZE = 32;
	public static final int REACTION_NODE_FONT_SIZE = 32;
	public static final int SIDE_NODE_FONT_SIZE = 28;
	public static final int METABOLITE_NODE_MAX_CHARS = 5;
	public static final int REACTION_NODE_MAX_CHARS = 7;
	public static final int METABOLITE_NODE_ELLIPSIS_CORRECTION = 2;
	public static final int REACTION_NODE_ELLIPSIS_CORRECTION = 2;
	// positions to start text in node
	public static final int PATHWAY_NAME_NODE_XPOS = 0;
	public static final int PATHWAY_NAME_NODE_YPOS = 18;
	public static final int METABOLITE_NODE_XPOS = 0;
//	public static final int METABOLITE_NODE_YPOS = 18;
	public static final int METABOLITE_NODE_YPOS = 28;
	public static final int REACTION_NODE_XPOS = 0;
//	public static final int REACTION_NODE_YPOS = 18;
	public static final int REACTION_NODE_YPOS = 28;
	public static final int SIDE_NODE_XPOS = 0;
	public static final int SIDE_NODE_YPOS = 23;
	
	public static final double DEFAULT_EDGE_WIDTH = 0.0; 
	public static final int ARROW_LENGTH = 30;
	public static final int ARROW_WIDTH = 25;
	public static final int ARROW_NOTCH = 12;
//	public static final int ARROW_LENGTH = 40;
//	public static final int ARROW_WIDTH = 40;
//	public static final int ARROW_NOTCH = 15;
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
	
	public static final int MAIN_COMPONENT = 0;
	public static final int PHOSPHORYLATION_COMPONENT = 1;
	
	//public static final int PHOSPHORYLATION_X_OFFSET = 3*HORIZONTAL_INCREMENT;
	public static final int PHOSPHORYLATION_X_OFFSET = 1400;
	public static final int PHOSPHORYLATION_Y_OFFSET = VERTICAL_INCREMENT;
	public static final int RIGHT_BORDER_INCREMENT = 0;
	
	// there is an area between f6p and pep in glycolysis where there are no transport reactions.
	// this is a good place to put transport reactions of side species
	public static final String SIDE_SPECIES_EXCHANGE_START_POSITION_REACTION = "C00085";
	public static final String SIDE_SPECIES_EXCHANGE_END_POSITION_REACTION = "C00022";
	
}
