package edu.rutgers.MOST.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import edu.rutgers.MOST.config.LocalConfig;
import au.com.bytecode.opencsv.CSVReader;

public class PathwayFilesReader {
	
	Map<String, MetabolicPathway> metabolicPathways = new HashMap<String, MetabolicPathway>();
	Map<String, EnzymeData> ecMap = new HashMap<String, EnzymeData>();
	Map<String, String> metaboliteNameAbbrMap = new HashMap<String, String>();
	
	public PathwayFilesReader() {
		
	}
	
	public void readPathwaysFile(File pathways) {
		CSVReader reader;
		
		int count = 0;
		ArrayList<String> pathwaysList = new ArrayList<String>();
		
		try {
			reader = new CSVReader(new FileReader(pathways), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						MetabolicPathway pathway = new MetabolicPathway();
						for (int s = 0; s < dataArray.length; s++) {
							if (s == CSVFileConstants.PATHWAYS_ID_COLUMN) {
								pathwaysList.add(dataArray[s]);
								pathway.setId(dataArray[s]);
							}
							if (s == CSVFileConstants.PATHWAYS_NAME_COLUMN) {
								pathway.setName(dataArray[s]);
							}
							if (s == CSVFileConstants.PATHWAYS_DIRECTION_COLUMN) {
								pathway.setDirection(dataArray[s]);
							}
						}
						metabolicPathways.put(pathway.getId(), pathway);
					}
					count += 1;
				}
				reader.close();
//				System.out.println(pathwaysList);
//				System.out.println(metabolicPathways);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,                
						"File Not Found Error.",                
						"Error",                                
						JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}	
	}
	
	public void readPathwayMetabolitesFile(File pathwayMetabolites) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(pathwayMetabolites), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayMetabolite pm = new PathwayMetabolite();
						String id = dataArray[CSVFileConstants.PATHWAY_METABOLITE_POSITIONS_ID_COLUMN];
						//System.out.println("id " + id);
						for (int s = 0; s < dataArray.length; s++) {
							if (s == CSVFileConstants.PATHWAY_METABOLITE_POSITIONS_METABOLITE_ID_COLUMN) {
								pm.setId(dataArray[s]);
							}
							if (s == CSVFileConstants.PATHWAY_METABOLITE_POSITIONS_LEVEL_COLUMN) {
								pm.setLevel(Double.parseDouble(dataArray[s]));
							}
							if (s == CSVFileConstants.PATHWAY_METABOLITE_POSITIONS_POSITION_COLUMN) {
								pm.setLevelPosition(Double.parseDouble(dataArray[s]));
							}
							if (s == CSVFileConstants.PATHWAY_METABOLITE_POSITIONS_NAME_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] names = dataArray[s].split("\\|");
								ArrayList<String> namesList = new ArrayList<String>();
								for (int i = 0; i < names.length; i++) {
									namesList.add(names[i]);
								}
								pm.setNames(namesList);
							}
							if (s == CSVFileConstants.PATHWAY_METABOLITE_POSITIONS_ABBR_COLUMN) {
								pm.setAbbreviation(dataArray[s]);
							}
						}
						//System.out.println(pm);
						metabolicPathways.get(id).getMetabolites().put(pm.getId(), pm);
						metaboliteNameAbbrMap.put(pm.getNames().get(0), pm.getAbbreviation());
					}
					count += 1;
				}
				reader.close();
				//System.out.println(metabolicPathways);
				LocalConfig.getInstance().setMetabolicPathways(metabolicPathways);
				LocalConfig.getInstance().setMetaboliteNameAbbrMap(metaboliteNameAbbrMap);
				System.out.println(metaboliteNameAbbrMap);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,                
						"File Not Found Error.",                
						"Error",                                
						JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}	
	}
	
	public void readPathwayReactionsFile(File pathwayReactions) {
		CSVReader reader;
		
		int count = 0;
		//ArrayList<String> pathwaysList = new ArrayList<String>();
		
		try {
			reader = new CSVReader(new FileReader(pathwayReactions), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayReaction pr = new PathwayReaction();
						String id = dataArray[CSVFileConstants.PATHWAY_REACTIONS_PATHWAY_ID_COLUMN];
						for (int s = 0; s < dataArray.length; s++) {
							if (s == CSVFileConstants.PATHWAY_REACTIONS_REACTION_ID_COLUMN) {
								pr.setId(dataArray[s]);
							}
							//System.out.println("map " + metabolicPathways.get(id));		
							if (s == CSVFileConstants.PATHWAY_REACTIONS_REACTANTS_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] reac = dataArray[s].split(",");
								ArrayList<String> mainReactants = new ArrayList<String>();
								for (int i = 0; i < reac.length; i++) {
									mainReactants.add(reac[i]);
								}
								pr.setMainReactants(mainReactants);
							}
							if (s == CSVFileConstants.PATHWAY_REACTIONS_PRODUCTS_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] prod = dataArray[s].split(",");
								ArrayList<String> mainProducts = new ArrayList<String>();
								for (int i = 0; i < prod.length; i++) {
									mainProducts.add(prod[i]);
								}
								pr.setMainProducts(mainProducts);
							}
							if (s == CSVFileConstants.PATHWAY_REACTIONS_REVERSIBLE_COLUMN) {
								//System.out.println(dataArray[s]);
								pr.setReversible(dataArray[s]);
							}
							if (s == CSVFileConstants.PATHWAY_REACTIONS_EC_NUM_LIST_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] ecNumbers = dataArray[s].split(",");
								ArrayList<String> ec = new ArrayList<String>();
								for (int i = 0; i < ecNumbers.length; i++) {
									ec.add(ecNumbers[i]);
									ecMap.put(ecNumbers[i], LocalConfig.getInstance().getEnzymeDataMap().get(ecNumbers[i]));
								}
								pr.setEcNumbers(ec);
								metabolicPathways.get(id).getEcNumbers().add(ec);
							}
							if (s == CSVFileConstants.PATHWAY_REACTIONS_LEVEL_COLUMN) {
								//System.out.println(dataArray[s]);
								pr.setLevel(Double.parseDouble(dataArray[s]));
							}
							if (s == CSVFileConstants.PATHWAY_REACTIONS_POSITION_COLUMN) {
								//System.out.println(dataArray[s]);
								pr.setLevelPosition(Double.parseDouble(dataArray[s]));
							}
							pr.setName("test" + pr.getId());
						}
						System.out.println(pr);
						metabolicPathways.get(id).getReactions().put(pr.getId(), pr);
					}
					count += 1;
				}
				reader.close();
				System.out.println(metabolicPathways);
				//System.out.println(ecMap);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,                
						"File Not Found Error.",                
						"Error",                                
						JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}	
	}
	
	public void readDrawOrderFile(File drawOrder) {
CSVReader reader;
		
		int count = 0;
		ArrayList<String> drawOrderList = new ArrayList<String>();
		
		try {
			reader = new CSVReader(new FileReader(drawOrder), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						for (int s = 0; s < dataArray.length; s++) {	
							if (s == CSVFileConstants.PATHWAY_DRAW_ORDER_PATHWAY_ID_COLUMN) {
								drawOrderList.add(dataArray[s]);
							}
						}
					}
					count += 1;
				}
				reader.close();
				System.out.println(drawOrderList);
				LocalConfig.getInstance().setDrawOrder(drawOrderList);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,                
						"File Not Found Error.",                
						"Error",                                
						JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,                
					"File Not Found Error.",                
					"Error",                                
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}	
	}
	
	public void readFiles() {
		EnzymeDataReader r = new EnzymeDataReader();
		r.readFile();
		//System.out.println(LocalConfig.getInstance().getEnzymeDataMap().get("1.1.1.1"));
		File pathways = new File(CSVFileConstants.PATHWAYS_FILE_NAME);
		File pathwayMetabolites = new File(CSVFileConstants.PATHWAY_METABOLITE_POSITIONS_FILE_NAME);
		File pathwayReactions = new File(CSVFileConstants.PATHWAY_REACTIONS_FILE_NAME);
		File drawOrder = new File(CSVFileConstants.PATHWAY_DRAW_ORDER_FILE_NAME);
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readPathwaysFile(pathways);
		reader.readPathwayMetabolitesFile(pathwayMetabolites);
		reader.readPathwayReactionsFile(pathwayReactions);
		reader.readDrawOrderFile(drawOrder);
	}
	
	public static void main( String args[] )
	{
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readFiles();
	}

}
