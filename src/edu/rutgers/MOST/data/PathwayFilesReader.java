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
							if (s == PathwaysCSVFileConstants.PATHWAYS_ID_COLUMN) {
								pathwaysList.add(dataArray[s]);
								pathway.setId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAYS_NAME_COLUMN) {
								pathway.setName(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAYS_DIRECTION_COLUMN) {
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
						String id = dataArray[PathwaysCSVFileConstants.PATHWAY_METABOLITE_POSITIONS_ID_COLUMN];
						//System.out.println("id " + id);
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.PATHWAY_METABOLITE_POSITIONS_METABOLITE_ID_COLUMN) {
								pm.setId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_METABOLITE_POSITIONS_LEVEL_COLUMN) {
								pm.setLevel(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_METABOLITE_POSITIONS_POSITION_COLUMN) {
								pm.setLevelPosition(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_METABOLITE_POSITIONS_NAME_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] names = dataArray[s].split("\\|");
								ArrayList<String> namesList = new ArrayList<String>();
								for (int i = 0; i < names.length; i++) {
									namesList.add(names[i]);
								}
								pm.setNames(namesList);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_METABOLITE_POSITIONS_ABBR_COLUMN) {
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
				//System.out.println(metaboliteNameAbbrMap);
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
						String id = dataArray[PathwaysCSVFileConstants.PATHWAY_REACTIONS_PATHWAY_ID_COLUMN];
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_REACTION_ID_COLUMN) {
								pr.setId(dataArray[s]);
							}
							//System.out.println("map " + metabolicPathways.get(id));		
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_REACTANTS_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] reac = dataArray[s].split(",");
								ArrayList<String> mainReactants = new ArrayList<String>();
								for (int i = 0; i < reac.length; i++) {
									mainReactants.add(reac[i]);
								}
								pr.setMainReactants(mainReactants);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_PRODUCTS_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] prod = dataArray[s].split(",");
								ArrayList<String> mainProducts = new ArrayList<String>();
								for (int i = 0; i < prod.length; i++) {
									mainProducts.add(prod[i]);
								}
								pr.setMainProducts(mainProducts);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_REVERSIBLE_COLUMN) {
								//System.out.println(dataArray[s]);
								pr.setReversible(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_EC_NUM_LIST_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] ecNumbers = dataArray[s].split(",");
								ArrayList<String> ec = new ArrayList<String>();
								for (int i = 0; i < ecNumbers.length; i++) {
									ec.add(ecNumbers[i]);
									if (LocalConfig.getInstance().getEcNumMap().containsKey(ecNumbers[i])) {
										System.out.println(ecNumbers[i] + " " + LocalConfig.getInstance().getEcNumMap().get(ecNumbers[i]));
										// probably should put reaction here or something to make unique
										pr.setName(LocalConfig.getInstance().getEcNumMap().get(ecNumbers[i]) + " " + Integer.toString(count));
										String equn = LocalConfig.getInstance().getEnzymeDataMap().get(ecNumbers[i]).getCatalyticActivity();
										System.out.println(equn);
										pr.setEquation(equn);
									} 
									ecMap.put(ecNumbers[i], LocalConfig.getInstance().getEnzymeDataMap().get(ecNumbers[i]));
								}
								if (pr.getName() == null) {
									pr.setName(metabolicPathways.get(id).getName() + " " + pr.getId());
									
								}
								System.out.println(pr.getName());
								pr.setEcNumbers(ec);
								metabolicPathways.get(id).getEcNumbers().add(ec);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_LEVEL_COLUMN) {
								//System.out.println(dataArray[s]);
								pr.setLevel(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_POSITION_COLUMN) {
								//System.out.println(dataArray[s]);
								pr.setLevelPosition(Double.parseDouble(dataArray[s]));
							}
						}
						//System.out.println(pr);
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
							if (s == PathwaysCSVFileConstants.PATHWAY_DRAW_ORDER_PATHWAY_ID_COLUMN) {
								drawOrderList.add(dataArray[s]);
							}
						}
					}
					count += 1;
				}
				reader.close();
				//System.out.println(drawOrderList);
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
	
	public void readSideSpeciesFile(File sideSpecies) {
		CSVReader reader;

		int count = 0;
		ArrayList<String> sideSpeciesList = new ArrayList<String>();

		try {
			reader = new CSVReader(new FileReader(sideSpecies), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						for (int s = 0; s < dataArray.length; s++) {	
							if (s == PathwaysCSVFileConstants.PATHWAY_SIDE_SPECIES_NAME_COLUMN) {
								sideSpeciesList.add(dataArray[s]);
							}
						}
					}
					count += 1;
				}
				reader.close();
				//System.out.println(sideSpeciesList);
				LocalConfig.getInstance().setSideSpeciesList(sideSpeciesList);
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
		File pathways = new File(PathwaysCSVFileConstants.PATHWAYS_FILE_NAME);
		File pathwayMetabolites = new File(PathwaysCSVFileConstants.PATHWAY_METABOLITE_POSITIONS_FILE_NAME);
		File pathwayReactions = new File(PathwaysCSVFileConstants.PATHWAY_REACTIONS_FILE_NAME);
		File drawOrder = new File(PathwaysCSVFileConstants.PATHWAY_DRAW_ORDER_FILE_NAME);
		File sideSpecies = new File(PathwaysCSVFileConstants.PATHWAY_SIDE_SPECIES_FILE_NAME);
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readPathwaysFile(pathways);
		reader.readPathwayMetabolitesFile(pathwayMetabolites);
		reader.readPathwayReactionsFile(pathwayReactions);
		reader.readDrawOrderFile(drawOrder);
		reader.readSideSpeciesFile(sideSpecies);
	}
	
	public static void main( String args[] )
	{
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readFiles();
	}

}
