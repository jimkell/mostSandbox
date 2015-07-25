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
import edu.rutgers.MOST.presentation.GraphicalInterfaceConstants;
import au.com.bytecode.opencsv.CSVReader;

public class PathwayFilesReader {
	
	Map<String, MetabolicPathway> metabolicPathways = new HashMap<String, MetabolicPathway>();
	Map<String, PathwayNameData> pathwayNameMap = new HashMap<String, PathwayNameData>();
	Map<String, PathwayMetaboliteData> metaboliteDataKeggIdMap = new HashMap<String, PathwayMetaboliteData>();
	Map<String, String> metaboliteNameAbbrMap = new HashMap<String, String>();
	Map<String, ArrayList<String>> additionalMetabolitesMap = new HashMap<String, ArrayList<String>>();
	Map<String, PathwayReactionData> reactionDataKeggIdMap = new HashMap<String, PathwayReactionData>();
	Map<String, ArrayList<String>> ecNumberKeggReactionIdMap = new HashMap<String, ArrayList<String>>();
	
	public PathwayFilesReader() {
		
	}
	
	public void readPathwaysFile(File pathways) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(pathways), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						MetabolicPathway pathway = new MetabolicPathway();
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.PATHWAYS_ID_COLUMN) {
								pathway.setId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAYS_NAME_COLUMN) {
								pathway.setName(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAYS_KEGG_ID_COLUMN) {
								pathway.setKeggId(dataArray[s]);
							}
						}
						metabolicPathways.put(pathway.getId(), pathway);
					}
					count += 1;
				}
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
	
	public void readPathwayGraphFile(File pathwayGraph) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(pathwayGraph), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						if (metabolicPathways.containsKey(dataArray[PathwaysCSVFileConstants.PATHWAY_GRAPH_ID_COLUMN])) {
							MetabolicPathway pathway = metabolicPathways.get(dataArray[PathwaysCSVFileConstants.PATHWAY_GRAPH_ID_COLUMN]);
							for (int s = 0; s < dataArray.length; s++) {
								if (s == PathwaysCSVFileConstants.PATHWAY_GRAPH_COMPONENT_COLUMN) {
									pathway.setComponent(Integer.parseInt(dataArray[s]));
								}
							}
							metabolicPathways.put(pathway.getId(), pathway);
						} else {
							System.out.println("pathway " + dataArray[PathwaysCSVFileConstants.PATHWAY_GRAPH_ID_COLUMN]  + " not found.");
						}
					}
					count += 1;
				}
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
	
	public void readPathwayNamesFile(File pathwayNames) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(pathwayNames), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayNameData pn = new PathwayNameData();
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.PATHWAY_NAMES_ID_COLUMN) {
								pn.setId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_NAMES_KEGG_IDS_COLUMN) {
								
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_NAMES_LEVEL_COLUMN) {
								pn.setLevel(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_NAMES_LEVEL_POSITION_COLUMN) {
								pn.setLevelPosition(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.PATHWAY_NAMES_NAME_COLUMN) {
								pn.setName(dataArray[s]);
							}
						}
						pathwayNameMap.put(pn.getId(), pn);
					}
					count += 1;
				}
				reader.close();
				LocalConfig.getInstance().setPathwayNameMap(pathwayNameMap);
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
	
	public void readMetabolitesFile(File metabolites) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(metabolites), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayMetaboliteData pm = new PathwayMetaboliteData();
						for (int s = 0; s < dataArray.length; s++) {
							//System.out.println(dataArray[s]);
							if (s == PathwaysCSVFileConstants.METABOLITES_KEGG_ID_COLUMN) {
								pm.setKeggId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.METABOLITES_NAMES_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] names = dataArray[s].split("\\|");
								ArrayList<String> namesList = new ArrayList<String>();
								for (int i = 0; i < names.length; i++) {
									namesList.add(names[i]);
								}
								pm.setNames(namesList);
							}
							if (s == PathwaysCSVFileConstants.METABOLITES_OCCURENCE_COLUMN) {
								pm.setOccurence(Integer.valueOf(dataArray[s]));
							}
						}
						metaboliteDataKeggIdMap.put(pm.getKeggId(), pm);
					}
					count += 1;
				}
				reader.close();
				LocalConfig.getInstance().setMetaboliteDataKeggIdMap(metaboliteDataKeggIdMap);
//				System.out.println(metaboliteDataKeggIdMap);
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
	
	public void readAdditionalMetabolitesFile(File additionalMetabolites) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(additionalMetabolites), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						for (int s = 0; s < dataArray.length; s++) {
							String keggId = dataArray[PathwaysCSVFileConstants.ADDITIONAL_METABOLITES_KEGG_ID_COLUMN];
							if (s == PathwaysCSVFileConstants.ADDITIONAL_METABOLITES_ALTERNATE_KEGG_IDS_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] ids = dataArray[s].split("\\|");
								ArrayList<String> idsList = new ArrayList<String>();
								for (int i = 0; i < ids.length; i++) {
									idsList.add(ids[i]);
									PathwayMetaboliteData pm = new PathwayMetaboliteData();
									pm.setKeggId(idsList.get(i));
									pm.setNames(LocalConfig.getInstance().getMetaboliteDataKeggIdMap().get(keggId).getNames());
									pm.setOccurence(0);
									//System.out.println("pm " + pm);
									LocalConfig.getInstance().getMetaboliteDataKeggIdMap().put(idsList.get(i), pm);
								}
								additionalMetabolitesMap.put(keggId, idsList);
								//System.out.println("k " + keggId);
								//System.out.println("list " + idsList);
							}
						}
					}
					count += 1;
				}
				reader.close();
				System.out.println("a " + additionalMetabolitesMap);
				LocalConfig.getInstance().setAdditionalMetabolitesMap(additionalMetabolitesMap);
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
	
	public void readMetabolitePositionsFile(File metabolitePositions) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(metabolitePositions), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayMetaboliteData pm = new PathwayMetaboliteData();
						String id = dataArray[PathwaysCSVFileConstants.METABOLITE_POSITIONS_ID_COLUMN];
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_METABOLITE_ID_COLUMN) {
								pm.setId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_LEVEL_COLUMN) {
								pm.setLevel(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_POSITION_COLUMN) {
								pm.setLevelPosition(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_NAME_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] names = dataArray[s].split("\\|");
								ArrayList<String> namesList = new ArrayList<String>();
								for (int i = 0; i < names.length; i++) {
									namesList.add(names[i]);
								}
								pm.setNames(namesList);
							}
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_ABBR_COLUMN) {
								pm.setAbbreviation(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_KEGG_ID_COLUMN) {
								pm.setKeggId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_BORDER_COLUMN) {
								pm.setBorder(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.METABOLITE_POSITIONS_TYPE_COLUMN) {
								pm.setType(dataArray[s]);
							}
						}
						metabolicPathways.get(id).getMetabolitesData().put(pm.getId(), pm);
						String name = pm.getNames().get(0);
						String abbr = pm.getAbbreviation();
						if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(pm.getKeggId())) {
							String metabAbbr = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(pm.getKeggId()).get(0).getMetaboliteAbbreviation();
							// check if metabolite ends with "_x"
							String ch = metabAbbr.substring(metabAbbr.length() - 2, metabAbbr.length() - 1);
							//System.out.println("ch " + ch);
							if (ch.equals("_")) {
								abbr = metabAbbr.substring(2, metabAbbr.length() - 2);
							} else {
								abbr = metabAbbr.substring(2);
							}
							name += " " + metabAbbr;
						}
						if (metaboliteNameAbbrMap.containsKey(name)) {
							name = name + duplicateSuffix(name, metaboliteNameAbbrMap);
						}
						pm.setName(name);
						//metaboliteNameAbbrMap.put(name, pm.getAbbreviation());
						metaboliteNameAbbrMap.put(name, abbr);
					}
					count += 1;
				}
				reader.close();
				LocalConfig.getInstance().setMetabolicPathways(metabolicPathways);
				LocalConfig.getInstance().setMetaboliteNameAbbrMap(metaboliteNameAbbrMap);
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
	
	public void readReactionsFile(File reactions) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(reactions), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayReactionData pr = new PathwayReactionData();
						ArrayList<String> keggIdsList = new ArrayList<String>();
						String keggReactionId = dataArray[PathwaysCSVFileConstants.REACTIONS_KEGG_ID_COLUMN];
						pr.setKeggReactionId(keggReactionId);
						for (int s = 0; s < dataArray.length; s++) {
							//System.out.println(dataArray[s]);
							if (s == PathwaysCSVFileConstants.REACTIONS_KEGG_REACTANTS_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] reactants = dataArray[s].split("\\|");
								ArrayList<String> reactantsList = new ArrayList<String>();
								for (int i = 0; i < reactants.length; i++) {
									reactantsList.add(reactants[i]);
									keggIdsList.add(reactants[i]);
								}
								pr.setKeggReactantIds(reactantsList);
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_KEGG_PRODUCTS_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] products = dataArray[s].split("\\|");
								ArrayList<String> productsList = new ArrayList<String>();
								for (int i = 0; i < products.length; i++) {
									productsList.add(products[i]);
									if (!keggIdsList.contains(products[i])) {
										keggIdsList.add(products[i]);
									}
								}
								pr.setKeggProductIds(productsList);
							}
							pr.setKeggIds(keggIdsList);
							// reversibility data currently not correct in this file
//							if (s == PathwaysCSVFileConstants.REACTIONS_REVERSABILITY_COLUMN) {
//								pr.setReversible(dataArray[s]);
//							}
							if (s == PathwaysCSVFileConstants.REACTIONS_EC_LIST_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] ecNumbers = dataArray[s].split("\\|");
								ArrayList<String> ecNumbersList = new ArrayList<String>();
								for (int i = 0; i < ecNumbers.length; i++) {
									ecNumbersList.add(ecNumbers[i]);
									if (ecNumbers[i] != null && ecNumbers[i].length() > 0) {
										if (ecNumberKeggReactionIdMap.containsKey(ecNumbers[i])) {
											ArrayList<String> r = ecNumberKeggReactionIdMap.get(ecNumbers[i]);
											r.add(keggReactionId);
											ecNumberKeggReactionIdMap.put(ecNumbers[i], r);
										} else {
											ArrayList<String> r = new ArrayList<String>();
											r.add(keggReactionId);
											ecNumberKeggReactionIdMap.put(ecNumbers[i], r);
										}
									}
								}
								pr.setEcNumbers(ecNumbersList);
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_OCCURENCES_COLUMN) {
								pr.setOccurences(Integer.valueOf(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_NAMES_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
								String[] names = dataArray[s].split("\\|");
								ArrayList<String> namesList = new ArrayList<String>();
								for (int i = 0; i < names.length; i++) {
									namesList.add(names[i]);
								}
								pr.setNames(namesList);
							}
						}
						reactionDataKeggIdMap.put(pr.getKeggReactionId(), pr);
					}
					count += 1;
				}
				reader.close();
				LocalConfig.getInstance().setReactionDataKeggIdMap(reactionDataKeggIdMap);
				//System.out.println(reactionDataKeggIdMap);
				LocalConfig.getInstance().setEcNumberKeggReactionIdMap(ecNumberKeggReactionIdMap);
				System.out.println("ec num kegg rxn " + ecNumberKeggReactionIdMap);
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
	
	public void readReactionPositionsFile(File reactionPositions) {
		CSVReader reader;
		
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(reactionPositions), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						PathwayReactionData pr = new PathwayReactionData();
						String id = dataArray[PathwaysCSVFileConstants.REACTIONS_PATHWAY_ID_COLUMN];
						Map<String, PathwayMetaboliteData> metabolitesData = LocalConfig.getInstance().getMetabolicPathways().get(id).getMetabolitesData();
						ArrayList<String> keggReactantIds = new ArrayList<String>();
						ArrayList<String> keggProductIds = new ArrayList<String>();
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.REACTIONS_PATHWAY_ID_COLUMN) {
								pr.setPathwayId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_REACTION_ID_COLUMN) {
								pr.setReactionId(dataArray[s]);
							}	
							if (s == PathwaysCSVFileConstants.REACTIONS_REACTANTS_COLUMN) {
								String[] reac = dataArray[s].split("\\|");
								ArrayList<String> reactantIds = new ArrayList<String>();
								for (int i = 0; i < reac.length; i++) {
									reactantIds.add(reac[i]);
									if (metabolitesData.containsKey(reac[i])) {
										keggReactantIds.add(metabolitesData.get(reac[i]).getKeggId());
									}
								}
								pr.setReactantIds(reactantIds);
								//System.out.println(keggReactantIds);
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_PRODUCTS_COLUMN) {
								String[] prod = dataArray[s].split("\\|");
								ArrayList<String> productIds = new ArrayList<String>();
								for (int i = 0; i < prod.length; i++) {
									productIds.add(prod[i]);
									if (metabolitesData.containsKey(prod[i])) {
										keggProductIds.add(metabolitesData.get(prod[i]).getKeggId());
									}
								}
								pr.setProductIds(productIds);
								//System.out.println(keggProductIds);
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_REVERSIBLE_COLUMN) {
								pr.setReversible(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_EC_NUM_LIST_COLUMN) {
								String[] ecNumbers = dataArray[s].split("\\|");
								ArrayList<String> ec = new ArrayList<String>();
								for (int i = 0; i < ecNumbers.length; i++) {
									ec.add(ecNumbers[i]);
								}
								pr.setEcNumbers(ec);
								metabolicPathways.get(id).getEcNumbers().add(ec);
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_LEVEL_COLUMN) {
								pr.setLevel(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_POSITION_COLUMN) {
								pr.setLevelPosition(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.REACTIONS_POSITION_KEGG_IDS_COLUMN) {
								String[] keggIds = dataArray[s].split("\\|");
								ArrayList<String> kegg = new ArrayList<String>();
								for (int i = 0; i < keggIds.length; i++) {
									kegg.add(keggIds[i]);
								}
								pr.setKeggReactionIds(kegg);
							}
						}
						pr.writeReactionEquation();
						pr.setName(pr.getEquation());
						pr.setDisplayName("<html>" + pr.getEquation() +"<p> EC Number(s): " + pr.getEcNumbers());
						pr.setKeggReactantIds(keggReactantIds);
						pr.setKeggProductIds(keggProductIds);
						metabolicPathways.get(id).getReactionsData().put(pr.getReactionId(), pr);
						//System.out.println(pr.getKeggReactionIds());
					}
					count += 1;
				}
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
	
	/**
	 * processes data for metabolites in periplasm or
	 * extraorganism compartments that participate in reactions
	 * @param externalMetabolites
	 */
	public void readExternalMetabolitesFile(File externalMetabolites) {
		CSVReader reader;

		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(externalMetabolites), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						ExternalMetaboliteData em = new ExternalMetaboliteData();
						String id = dataArray[PathwaysCSVFileConstants.EXTERNAL_METABOLITE_PATHWAY_ID_COLUMN];
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.EXTERNAL_METABOLITE_PATHWAY_ID_COLUMN) {
								em.setPathwayId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_METABOLITE_REACTION_ID_COLUMN) {
								em.setReactionId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_METABOLITE_ABBR_COLUMN) {
								em.setAbbreviation(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_METABOLITE_NAME_COLUMN) {
								// need to escape pipe: http://stackoverflow.com/questions/21524642/splitting-string-with-pipe-character
//								String[] names = dataArray[s].split("\\|");
//								ArrayList<String> namesList = new ArrayList<String>();
//								for (int i = 0; i < names.length; i++) {
//									namesList.add(names[i]);
//								}
//								em.setNames(namesList);
								em.setName(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_METABOLITE_POSITION_COLUMN) {
								em.setPosition(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_METABOLITE_OFFSET_COLUMN) {
								em.setOffset(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_METABOLITE_DIRECTION_COLUMN) {
								em.setDirection(Integer.valueOf(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_KEGG_METABOLITE_ID_COLUMN) {
								em.setKeggMetaboliteId(dataArray[s]);
								Map<String, PathwayReactionData> reactionsData = LocalConfig.getInstance().getMetabolicPathways().get(em.getPathwayId()).getReactionsData();
								if (em.getDirection() == -1) {
									ArrayList<String> keggReactantIds = reactionsData.get(em.getReactionId()).getKeggReactantIds();
									keggReactantIds.add(dataArray[s]);
									reactionsData.get(em.getReactionId()).setKeggReactantIds(keggReactantIds);
									//System.out.println(reactionsData.get(em.getReactionId()).getKeggReactantIds());
								} else if (em.getDirection() == 1) {
									ArrayList<String> keggProductIds = reactionsData.get(em.getReactionId()).getKeggProductIds();
									keggProductIds.add(dataArray[s]);
									reactionsData.get(em.getReactionId()).setKeggProductIds(keggProductIds);
									//System.out.println(reactionsData.get(em.getReactionId()).getKeggProductIds());
								}
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_EC_NUMBER_COLUMN) {
								em.setEcNumber(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.EXTERNAL_KEGG_REACTION_ID_COLUMN) {
								em.setKeggReactionId(dataArray[s]);
							}
						}
						metabolicPathways.get(id).getExternalMetabolitesData().put(em.getReactionId(), em);
						//System.out.println(em);
					}
					count += 1;
				}
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
	
	public void readTransportMetabolitesFile(File transportMetabolites) {
		CSVReader reader;

		ArrayList<String> transportMetaboliteIds = new ArrayList<String>();
		int count = 0;
		
		try {
			reader = new CSVReader(new FileReader(transportMetabolites), ',');
			String [] dataArray;
			try {
				while ((dataArray = reader.readNext()) != null) {
					if (count > 0) {
						ExternalMetaboliteData em = new ExternalMetaboliteData();
						String id = dataArray[PathwaysCSVFileConstants.TRANSPORT_METABOLITE_PATHWAY_ID_COLUMN];
						for (int s = 0; s < dataArray.length; s++) {
							if (s == PathwaysCSVFileConstants.TRANSPORT_METABOLITE_PATHWAY_ID_COLUMN) {
								em.setPathwayId(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.TRANSPORT_METABOLITE_ABBR_COLUMN) {
								em.setAbbreviation(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.TRANSPORT_METABOLITE_NAME_COLUMN) {
								em.setName(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.TRANSPORT_METABOLITE_POSITION_COLUMN) {
								em.setPosition(dataArray[s]);
							}
							if (s == PathwaysCSVFileConstants.TRANSPORT_METABOLITE_OFFSET_COLUMN) {
								em.setOffset(Double.parseDouble(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.TRANSPORT_METABOLITE_DIRECTION_COLUMN) {
								em.setDirection(Integer.valueOf(dataArray[s]));
							}
							if (s == PathwaysCSVFileConstants.TRANSPORT_KEGG_METABOLITE_ID_COLUMN) {
								em.setKeggMetaboliteId(dataArray[s]);
								if (!transportMetaboliteIds.contains(dataArray[s])) {
									transportMetaboliteIds.add(dataArray[s]);
								}
							}
						}
						metabolicPathways.get(id).getTransportMetabolitesData().put(em.getKeggMetaboliteId(), em);
						//System.out.println(em);
					}
					count += 1;
				}
				reader.close();
				LocalConfig.getInstance().setTransportMetaboliteIds(transportMetaboliteIds);
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
	
	/**
	 * Files where data is read once and the data structure is not modified in any way.
	 * Most of these are large files and it is beneficial to read only once.
	 */
	public void readOnceFiles() {
		EnzymeDataReader r = new EnzymeDataReader();
		r.readFile();
		File metabolites = new File(PathwaysCSVFileConstants.METABOLITES_FILE_NAME);
		File additionalMetabolites = new File(PathwaysCSVFileConstants.ADDITIONAL_METABOLITES_FILE_NAME);
		File reactions = new File(PathwaysCSVFileConstants.REACTIONS_FILE_NAME);
		File drawOrder = new File(PathwaysCSVFileConstants.PATHWAY_DRAW_ORDER_FILE_NAME);
		File sideSpecies = new File(PathwaysCSVFileConstants.PATHWAY_SIDE_SPECIES_FILE_NAME);
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readMetabolitesFile(metabolites);
		reader.readAdditionalMetabolitesFile(additionalMetabolites);
		reader.readReactionsFile(reactions);
		reader.readDrawOrderFile(drawOrder);
		reader.readSideSpeciesFile(sideSpecies);
	}
	
	/**
	 * Files where data is read but resultant data structure may be modified
	 */
	public void readFiles() {
//		EnzymeDataReader r = new EnzymeDataReader();
//		r.readFile();
		File pathways = new File(PathwaysCSVFileConstants.PATHWAYS_FILE_NAME);
		File pathwayNames = new File(PathwaysCSVFileConstants.PATHWAY_NAMES_FILE_NAME);
		File pathwayGraph = new File(PathwaysCSVFileConstants.PATHWAY_GRAPH_FILE_NAME);
		File metabolitePositions = new File(PathwaysCSVFileConstants.METABOLITE_POSITIONS_FILE_NAME);
		File reactionPositions = new File(PathwaysCSVFileConstants.REACTION_POSITIONS_FILE_NAME);
		File externalMetabolites = new File(PathwaysCSVFileConstants.EXTERNAL_METABOLITES_FILE_NAME);
		File transportMetabolites = new File(PathwaysCSVFileConstants.TRANSPORT_METABOLITES_FILE_NAME);
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readPathwaysFile(pathways);
		reader.readPathwayNamesFile(pathwayNames);
		reader.readPathwayGraphFile(pathwayGraph);
		reader.readMetabolitePositionsFile(metabolitePositions);
		reader.readReactionPositionsFile(reactionPositions);
		reader.readExternalMetabolitesFile(externalMetabolites);
		reader.readTransportMetabolitesFile(transportMetabolites);
	}
	
	/**
	 * Adds suffix to duplicate names
	 * @param value
	 * @param metaboliteNameAbbrMap
	 * @return
	 */
	public String duplicateSuffix(String value, Map<String, String> metaboliteNameAbbrMap) {
		String duplicateSuffix = GraphicalInterfaceConstants.DUPLICATE_SUFFIX;
		if (metaboliteNameAbbrMap.containsKey(value + duplicateSuffix)) {
			int duplicateCount = Integer.valueOf(duplicateSuffix.substring(1, duplicateSuffix.length() - 1));
			while (metaboliteNameAbbrMap.containsKey(value + duplicateSuffix.replace("1", Integer.toString(duplicateCount + 1)))) {
				duplicateCount += 1;
			}
			duplicateSuffix = duplicateSuffix.replace("1", Integer.toString(duplicateCount + 1));
		}
		return duplicateSuffix;
	}
	
	public static void main( String args[] )
	{
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readOnceFiles();
		reader.readFiles();
	}

}
