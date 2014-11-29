package edu.rutgers.MOST.data;

public class GraphPositionConstants {

	// will need to have a map of positions to EC Number lists
	public static final String[] GLYCOLYSIS_POSITIONS = 
		{ 
		"0 0", "1 0", "2 0", "3 0", "4 0", "4 1", "5 0", "6 0", "7 0", "8 0", "9 0", "10 0"
		};

	public static final String GLYCOLYSIS_POSITIONS_STRING = "0 0,1 0,2 0,3 0,4 0,4 1,5 0,6 0,7 0,8 0,9 0,10 0";
	public static final String GLYCOLYSIS_EDGES_STRING = "0 1,1 2,2 3,3 4,3 5,5 4,4 6,6 7,7 8,8 9,9 10";
	// 0 = false, 1 = true
	public static final String GLYCOLYSIS_REVERSIBLE_STRING = "0,1,0,1,1,1,1,1,1,1,0";

	public static final String[] PENTOSE_PHOSPHATE_POSITIONS = 
		{ 
		"0 0", "1 0", "2 0", "3 0", "4 -1", "4 1", "5 0", "6 0"
		};

	public static final String PENTOSE_PHOSPHATE_POSITIONS_STRING = "0 0,1 0,2 0,3 0,4 -1,4 1,5 0,6 0";
	public static final String PENTOSE_PHOSPHATE_EDGES_STRING = "0 1,1 2,2 3,3 4,3 5,4 6,5 6,6 7";
	public static final String PENTOSE_PHOSPHATE_REVERSIBLE_STRING = "0,0,0,0,0,0,0,0";

	public static final String[] CITRIC_ACID_CYCLE_POSITIONS = 
		{ 
		"0 0", "1 0", "2 0", "3 0", "4 0", "5 0", "6 0", "7 0"
		};
	
	public static final String CITRIC_ACID_CYCLE_POSITIONS_STRING = "0 0,0.8 1.5,1.6 2,2.4 2,3.2 1.5,4 0,3 -1.5,2 -2,1 -1.5";
	public static final String CITRIC_ACID_CYCLE_EDGES_STRING = "0 1,1 2,2 3,3 4,4 5,5 6,6 7,7 8,8 0";
	public static final String CITRIC_ACID_CYCLE_REVERSIBLE_STRING = "0,0,0,0,0,0,0,0,0";

	// For experimentation only. When this gets bigger, will store in a
	// csv and store in memory as name value pairs
//	public static final String[] PATHWAY_LIST = 
//		{
//		"Glycolysis/Gluconeogenesis",
//		"Pentose Phosphate",
//		"Citric Acid Cycle"
//		};
	
	public static final String[] PATHWAY_LIST = 
		{
		"Glyc",
		"Pent",
		"TCA"
		};

	public static final String[] PATHWAY_POSITIONS_LIST = 
		{
		GLYCOLYSIS_POSITIONS_STRING,
		PENTOSE_PHOSPHATE_POSITIONS_STRING,
		CITRIC_ACID_CYCLE_POSITIONS_STRING
		};

	public static final String[] PATHWAY_EDGES_LIST = 
		{
		GLYCOLYSIS_EDGES_STRING,
		PENTOSE_PHOSPHATE_EDGES_STRING,
		CITRIC_ACID_CYCLE_EDGES_STRING
		};

	public static final String[] PATHWAY_REVERSIBLE_LIST = 
		{
		GLYCOLYSIS_REVERSIBLE_STRING,
		PENTOSE_PHOSPHATE_REVERSIBLE_STRING,
		CITRIC_ACID_CYCLE_REVERSIBLE_STRING
		};

	public static final String[][] GLYCOLYSIS_EC_NUMBERS = 
		{ 
		{"2.7.1.1", "2.7.1.2", "2.7.1.63", "2.7.1.147"}, // hexokinase, glucokinase, polyphosphate glucokinase
		{"5.3.1.9", "5.3.1.15"}, // glucose-6-phosphate isomerase, glucose-6-phosphate 1-epimerase
		{"2.7.1.11", "2.7.1.146", "2.7.1.147"}, // 6-phosphofructokinase, ADP-dependent phosphofructokinase/glucokinase
		{"4.1.2.13"}, // fructose-bisphosphate aldolase
		{"1.2.1.12", "1.2.1.59"}, // glyceraldehyde 3-phosphate dehydrogenase
		// there are 2 aldolase reactions here in Wikipedia need to look this up
		{"5.3.1.1"}, // triosephosphate isomerase - side
		{"2.7.2.3"}, // phosphoglycerate kinase
		{"5.4.2.1", "5.4.2.11", "5.4.2.12", "2.7.5.3"}, // phosphoglycerate mutase, 2.7.5.3 and 5.4.2.1 are transferred entries in enzyme.dat
		{"4.2.1.11"}, // enolase
		{"2.7.1.40"} // pyruvate kinase
		};

	public static final String[][] TCA_CYCLE_EC_NUMBERS = 
		{ 
		{"2.3.3.1", "2.3.3.3", "2.3.3.8", "4.1.3.7", "4.1.3.8"}, // citrate synthase, citrate lyase
		//{"2.3.3.1", "2.3.3.3", "2.3.3.8", "4.1.3.7", "4.1.3.6", "2.8.3.10"}, // citrate synthase, citrate lyase
		{"4.2.1.3"}, // aconitate hydratase
		{"1.1.1.42", "1.1.1.41", "1.1.1.286"}, // isocitrate dehydrogenase, isocitrate--homoisocitrate dehydrogenase	
		//{"1.2.4.2", "2.3.1.61"}, // 2-oxoglutarate dehydrogenase
		{"1.2.4.2"}, // 2-oxoglutarate dehydrogenase
		{"6.2.1.4", "6.2.1.5", "2.8.3.18"}, // succinyl-CoA synthetase, succinyl-CoA:acetate CoA-transferase
		//{"1.3.99.1", "1.3.5.1"}, // succinate dehydrogenase
		{"1.3.5.1", "1.3.5.4"}, // succinate dehydrogenase
		{"4.2.1.2"}, // fumarate hydratase
		{"1.1.1.37", "1.1.1.54"} // malate dehydrogenase
		};

	public static final String HORIZONTAL_DIRECTION = "horizontal";
	public static final String VERTICAL_DIRECTION = "vertical";
	
	public static final String PATHWAY_XML_FILE_NAME = "pathways.xml";

}
