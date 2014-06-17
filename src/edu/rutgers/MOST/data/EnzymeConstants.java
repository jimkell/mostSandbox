package edu.rutgers.MOST.data;

public class EnzymeConstants {
	public static final String[] TCA_CYCLE_EC_NUMBERS = 
		{ 
			"2.3.3.3",
			"4.2.1.3",
			"1.1.1.42",		
			"1.2.4.2",
			"6.2.1.5",
			"1.3.99.1",
			"4.2.1.2",
			"1.1.1.37",
			// alternate reactions 
			"4.1.3.6",
			"1.1.99.16",
			"2.3.1.61"
		};
	
	public static final String[] GLYCOLYSIS_EC_NUMBERS = 
		{ 
			"2.7.1.1",
			"5.3.1.9",
			"2.7.1.11",
			"4.1.2.13",
			"1.2.1.12",
			"2.7.2.3",
			"5.4.2.12", // was 5.4.2.1 but Enzyme database replaces with 5.4.2.11 or 5.4.2.12
			"4.2.1.11",
			"2.7.1.40"
		};
	
	public static final String[] GLYCOLYSIS_EC_NUMBERS_SIDE_BRANCH = 
		{ 
			"5.3.1.1"
		};
	
	public static final String[] GLYCOLYSIS_REACTANTS = 
		{ 
			"Glucose",
			"Glucose_6_phosphate",
			"Fructose_6_phosphate",
			"Fructose_1_6_bisphosphate",
			"Glyceraldehyde_3_phosphate",
			"3_Phospho_D_glyceroyl_phosphate",
			"3_Phospho_D_glyceroyl_phosphate",
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
			"3_Phospho_D_glyceroyl_phosphate",
			"D_Glycerate_2_phosphate",
			"M_Phosphoenolpyruvate",
			"Pyruvate"
		};

}
 

 






