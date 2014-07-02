package edu.rutgers.MOST.data;

import java.util.Arrays;

public class EnzymeConstants {
	public static final String[][] TCA_CYCLE_EC_NUMBERS = 
		{ 
			{"2.3.3.1", "2.3.3.3", "2.3.3.8", "4.1.3.7", "4.1.3.6", "2.8.3.10"}, // citrate synthase, citrate lyase
			{"4.2.1.3"}, // aconitate hydratase
			{"1.1.1.42", "1.1.1.41", "1.1.1.286"}, // isocitrate dehydrogenase, isocitrate--homoisocitrate dehydrogenase	
			{"1.2.4.2", "2.3.1.61"}, // 2-oxoglutarate dehydrogenase
			{"6.2.1.4", "6.2.1.5", "2.8.3.18"}, // succinyl-CoA synthetase, succinyl-CoA:acetate CoA-transferase
			{"1.3.99.1", "1.3.5.1"}, // succinate dehydrogenase
			{"4.2.1.2"}, // fumarate hydratase
			{"1.1.1.37", "1.1.1.54"} // malate dehydrogenase
		};
	
	public static final String[][] TCA_CYCLE_EC_NUMBERS_SIDE_REACTIONS = 
		{ 
			{"1.2.7.3"}, // 2-oxoglutarate ferredoxin oxidoreductase
		};
	
	public static final String[][] GLYCOLYSIS_EC_NUMBERS = 
		{ 
			{"2.7.1.1", "2.7.1.2", "2.7.1.63", "2.7.1.147"}, // hexokinase, glucokinase, polyphosphate glucokinase
			{"5.3.1.9", "5.3.1.15"}, // glucose-6-phosphate isomerase, glucose-6-phosphate 1-epimerase
			{"2.7.1.11", "2.7.1.146", "2.7.1.147"}, // 6-phosphofructokinase, ADP-dependent phosphofructokinase/glucokinase
			{"4.1.2.13"}, // fructose-bisphosphate aldolase
			{"1.2.1.12", "1.2.1.59"}, // glyceraldehyde 3-phosphate dehydrogenase
			{"2.7.2.3"}, // phosphoglycerate kinase
			{"5.4.2.1", "5.4.2.11", "5.4.2.12", "2.7.5.3"}, // phosphoglycerate mutase, 2.7.5.3 and 5.4.2.1 are transferred entries in enzyme.dat
			{"4.2.1.11"}, // enolase
			{"2.7.1.40"} // pyruvate kinase
		};
	
	// these reactions go in reverse direction in enzyme.dat
	public static final Integer[] GLYCOLYSIS_REVERSED_REACTIONS = 
		{
			5, 6, 8
		};
	
	public static final String[][] GLYCOLYSIS_EC_NUMBERS_SIDE_BRANCH = 
		{ 
			{"5.3.1.1"} // triosephosphate isomerase
		};
	
	public static final String[][] GLYCONEOGENESIS_EC_NUMBERS = 
		{ 
			{"3.1.3.11"}, // fructose-1,6-bisphosphatase I
			{"1.2.1.9", "1.2.7.6"}, // glyceraldehyde-3-phosphate dehydrogenase
			{"5.4.2.4", "5.4.2.11", "3.1.3.13"}, // bisphosphoglycerate mutase
			{"1.1.1.27"} // L-lactate dehydrogenase
		};
	
	public static final String[] GLYCOLYSIS_REACTANTS = 
		{ 
			"Glucose",
			"Glucose_6_phosphate",
			"Fructose_6_phosphate",
			"Fructose_1_6_bisphosphate",
			"Glyceraldehyde_3_phosphate",
			"3_Phospho_D_glyceroyl_phosphate",
			"3_Phospho_D_glycerate",
			"D_Glycerate_2_phosphate",
			"M_Phosphoenolpyruvate"
		};
	
	public static final String[] GLYCOLYSIS_PRODUCTS = 
		{ 
			"Glucose_6_phosphate",
			"Fructose_6_phosphate",
			"Fructose_1_6_bisphosphate",
			"Glyceraldehyde_3_phosphate",
			"3_Phospho_D_glyceroyl_phosphate",
			"3_Phospho_D_glycerate",
			"D_Glycerate_2_phosphate",
			"M_Phosphoenolpyruvate",
			"Pyruvate"
		};
	
	public static final String[][] PENTOSE_PHOSPHATE_OXIDATIVE_EC_NUMBERS = 
		{ 
			{"1.1.1.49"}, // glucose-6-phosphate 1-dehydrogenase
			{"3.1.1.31"}, // 6-phosphogluconolactonase
			{"1.1.1.44"} // 6-phosphogluconate dehydrogenase
		};
	
	// names of species used in enzyme.dat, used to determine which species in a reaction
	// will be placed on side arrows in pathways
	public static final String[] SIDE_SPECIES =
		{
			"ADP", "ATP", "NAD(+)", "NADH", "NADP(+)", "NADPH", "NAD(P)(+)", "NAD(P)H",
			"H(2)O", "H(+)", "OH(-)", "CO(2)"
		};

}
 

 






