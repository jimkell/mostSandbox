package edu.rutgers.MOST.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import edu.rutgers.MOST.config.LocalConfig;
import au.com.bytecode.opencsv.CSVReader;

public class PathwayFilesReader {
	
	Map<String, MetabolicPathway> metabolicPathways = new HashMap<String, MetabolicPathway>();
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
						PathwayMetaboliteData pm = new PathwayMetaboliteData();
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
						metabolicPathways.get(id).getMetabolitesData().put(pm.getId(), pm);
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
		String eq = "Equation: ";
		//ArrayList<String> namesList = new ArrayList<String>();
		//System.out.println("mp " + metabolicPathways);
		
		try {
			reader = new CSVReader(new FileReader(pathwayReactions), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayReactionData pr = new PathwayReactionData();
						String id = dataArray[PathwaysCSVFileConstants.PATHWAY_REACTIONS_PATHWAY_ID_COLUMN];
						//System.out.println("id " + id);
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_PATHWAY_ID_COLUMN) {
								pr.setPathwayId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_REACTION_ID_COLUMN) {
								pr.setReactionId(dataArray[s]);
							}
							//System.out.println("map " + metabolicPathways.get(id));		
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_REACTANTS_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] reac = dataArray[s].split("\\|");
								ArrayList<String> reactantIds = new ArrayList<String>();
								for (int i = 0; i < reac.length; i++) {
									reactantIds.add(reac[i]);
								}
								pr.setReactantIds(reactantIds);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_PRODUCTS_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] prod = dataArray[s].split("\\|");
								ArrayList<String> productIds = new ArrayList<String>();
								for (int i = 0; i < prod.length; i++) {
									productIds.add(prod[i]);
								}
								pr.setProductIds(productIds);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_REVERSIBLE_COLUMN) {
								pr.setReversible(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_REACTIONS_EC_NUM_LIST_COLUMN) {
								//System.out.println(dataArray[s]);
								String[] ecNumbers = dataArray[s].split("\\|");
								ArrayList<String> ec = new ArrayList<String>();
								for (int i = 0; i < ecNumbers.length; i++) {
									ec.add(ecNumbers[i]);
								}
								//System.out.println(pr.getName());
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
						pr.writeReactionEquation();
						pr.setName(pr.getEquation());
						//System.out.println(pr.getName());
						//System.out.println(pr);
						metabolicPathways.get(id).getReactionsData().put(pr.getReactionId(), pr);
					}
					count += 1;
				}
				reader.close();
				//System.out.println(metabolicPathways);
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
	
	public void readPathwayConnectionsFile(File pathwayConnections) {
		CSVReader reader;

		int count = 0;
		// used for connections that determine position of second pathway
		Map<String, ArrayList<PathwayConnectionData>> connectionPositionMap = new HashMap<String, ArrayList<PathwayConnectionData>>();
		// used for all other connections
		ArrayList<PathwayConnectionData> connectionslist = new ArrayList<PathwayConnectionData>();
		
		try {
			reader = new CSVReader(new FileReader(pathwayConnections), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayConnectionData pc = new PathwayConnectionData();
						ArrayList<ArrayList<String>> reactantPathwaysIds = new ArrayList<ArrayList<String>>();
						ArrayList<ArrayList<String>> productPathwaysIds = new ArrayList<ArrayList<String>>();
						for (int s = 0; s < dataArray.length; s++) {	
							if (s == PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_REACTANTS_COLUMN) {
								String[] reactants = dataArray[s].split("\\|");
								for (int t = 0; t < reactants.length; t++) {
									ArrayList<String> item = new ArrayList<String>();
									String[] pathwayId = reactants[t].split(" ");
									item.add(pathwayId[0]);
									item.add(pathwayId[1]);
									reactantPathwaysIds.add(item);
								}
								pc.setReactantPathwaysIds(reactantPathwaysIds);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_PRODUCTS_COLUMN) {
								String[] products = dataArray[s].split("\\|");
								for (int t = 0; t < products.length; t++) {
									ArrayList<String> item = new ArrayList<String>();
									String[] pathwayId = products[t].split(" ");
									item.add(pathwayId[0]);
									item.add(pathwayId[1]);
									productPathwaysIds.add(item);
								}
								pc.setProductPathwaysIds(productPathwaysIds);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_REVERSIBLE_COLUMN) {
								pc.setReversible(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_EC_NUM_LIST_COLUMN) {
								String[] ecNumbers = dataArray[s].split("\\|");
								ArrayList<String> ec = new ArrayList<String>();
								for (int i = 0; i < ecNumbers.length; i++) {
									ec.add(ecNumbers[i]);
								}
								pc.setEcNumbers(ec);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_LENGTH_COLUMN) {
								pc.setLength(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_DIRECTION_COLUMN) {
								pc.setDirection(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_POSITIONING_COLUMN) {
								pc.setPositioning(dataArray[s]);
								
							}
						}
						pc.writeReactionEquation();
						pc.setName(pc.getEquation());
						//System.out.println(pc);
						if (pc.getPositioning().equals("1")) {
							pc.setDirection(pc.getDirection());
							if (connectionPositionMap.containsKey(pc.getReactantPathwaysIds().get(0).get(0))) {
								ArrayList<PathwayConnectionData> cd = connectionPositionMap.get(pc.getReactantPathwaysIds().get(0).get(0));
								cd.add(pc);
							} else {
								ArrayList<PathwayConnectionData> cd = new ArrayList<PathwayConnectionData>();
								cd.add(pc);
								connectionPositionMap.put(pc.getReactantPathwaysIds().get(0).get(0), cd);
							}
						} else {
							connectionslist.add(pc);
						}
					}
					count += 1;
				}
				LocalConfig.getInstance().setConnectionPositionMap(connectionPositionMap);
				System.out.println(connectionPositionMap);
				LocalConfig.getInstance().setConnectionslist(connectionslist);
				System.out.println(connectionslist);
				reader.close();
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
		File pathwayConnections = new File(PathwaysCSVFileConstants.PATHWAY_CONNECTIONS_FILE_NAME);
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readPathwaysFile(pathways);
		reader.readPathwayMetabolitesFile(pathwayMetabolites);
		reader.readPathwayReactionsFile(pathwayReactions);
		reader.readDrawOrderFile(drawOrder);
		reader.readSideSpeciesFile(sideSpecies);
		reader.readPathwayConnectionsFile(pathwayConnections);
	}
	
	public static void main( String args[] )
	{
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readFiles();
	}

}
