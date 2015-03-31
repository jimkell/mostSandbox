package edu.rutgers.MOST.presentation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;                                                                                        
import java.awt.Color;                                                                                               
import java.awt.Dimension;                                                                                           
import java.awt.Font;
import java.awt.Graphics;                                                                                            
import java.awt.Graphics2D;                                                                                          
import java.awt.Image;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;                                                                                   
import java.awt.event.ActionListener;                                                                                
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;                                                                                
import java.awt.geom.Point2D;                                                                                        
import java.awt.image.BufferedImage;
import java.util.ArrayList;                                                                                          
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;                                                                                            
import java.util.List;                                                                                               
import java.util.Map;                                                                                                
                                                                                                                     
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;                                                                                        
import javax.swing.JApplet;                                                                                          
import javax.swing.JButton;                                                                                          
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;                                                                                           
                                                                                                                     
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;

import org.apache.commons.collections15.Transformer;                                                                 
import org.apache.commons.collections15.functors.ChainedTransformer;                                                 
                                                                                                                     
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.data.ExternalMetaboliteData;
import edu.rutgers.MOST.data.ExternalMetaboliteNode;
import edu.rutgers.MOST.data.MetabolicPathway;
import edu.rutgers.MOST.data.PathwayConnectionNode;
import edu.rutgers.MOST.data.PathwayMetaboliteData;
import edu.rutgers.MOST.data.PathwayMetaboliteNode;
import edu.rutgers.MOST.data.PathwayReactionNode;
import edu.rutgers.MOST.data.PathwayReactionNodeFactory;
import edu.rutgers.MOST.data.ReactionFactory;
import edu.rutgers.MOST.data.SBMLReaction;
import edu.rutgers.MOST.data.TransportReactionConstants;
import edu.rutgers.MOST.data.TransportReactionNode;
import edu.uci.ics.jung.algorithms.layout.Layout;                                                                    
import edu.uci.ics.jung.algorithms.layout.StaticLayout;                                                              
import edu.uci.ics.jung.graph.Graph;                                                                                 
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeType;                                                                         
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;                                                           
import edu.uci.ics.jung.visualization.Layer;                                                                         
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;                                                           
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;                                               
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;                                               
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;                                                
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;                                                        
import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;                                                   
import edu.uci.ics.jung.visualization.decorators.VertexIconShapeTransformer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;  
import edu.uci.ics.jung.visualization.util.ArrowFactory;
                                                                                                                     
                                                                                                                     
/**                                                                                                                  
 * Graph created by plotting vertices.                                                                      
 * Scaling of the graph also scales the image background.                                                            
 * based on code by Tom Nelson                                                                                                
 *                                                                                                                   
 */                                                                                                                  
@SuppressWarnings("serial")                                                                                          
public class PathwaysFrame extends JApplet {                                                                     
	
    /**                                                                                                              
     * the graph                                                                                                     
     */                                                                                                              
    Graph<String, Number> graph;                                                                                     
                                                                                                                     
    /**                                                                                                              
     * the visual component and renderer for the graph                                                               
     */                                                                                                              
    VisualizationViewer<String, Number> vv;  
    
    public final JMenuItem visualizationOptionsItem = new JMenuItem(VisualizationOptionsConstants.VISUALIZATION_OPTIONS_MENU_ITEM_NAME);
                                                                                                                     
	Map<String, String[]> metabPosMap = new HashMap<String, String[]>();                                                       
   	List<String> metaboliteList; 
   	
   	// key = name of rxn, value = reactant, product, reversible
   	Map<String, String[]> reactionMap = new HashMap<String, String[]>(); 
   	List<String> reactionList;
   	
   	// lists used to distinguish node types
   	ArrayList<String> borderList = new ArrayList<String>();
   	ArrayList<String> noBorderList = new ArrayList<String>();
   	ArrayList<String> pathwayNames = new ArrayList<String>();
   	ArrayList<String> metabolites = new ArrayList<String>();
   	ArrayList<String> reactions = new ArrayList<String>();
   	Map<String, Double> fluxMap = new HashMap<String, Double>(); 
   	
   	private double layoutScale;
   	private double viewScale;
  
   	//String borderLeftX = Integer.toString(PathwaysFrameConstants.BORDER_WIDTH);
   	//String borderRightX = Integer.toString(PathwaysFrameConstants.GRAPH_WIDTH - PathwaysFrameConstants.BORDER_WIDTH);
   	//String borderTopY = Integer.toString(PathwaysFrameConstants.BORDER_HEIGHT);
   	//String borderBottomY = Integer.toString(PathwaysFrameConstants.GRAPH_HEIGHT - PathwaysFrameConstants.BORDER_HEIGHT);
   
   	private final JCheckBoxMenuItem transformItem = new JCheckBoxMenuItem("Transform");
   	
   	private NodeEditorDialog nodeEditor;
   	
   	public NodeEditorDialog getNodeEditor() {
		return nodeEditor;
	}

	public void setNodeEditor(NodeEditorDialog nodeEditor) {
		this.nodeEditor = nodeEditor;
	}

	protected EdgeWeightStrokeFunction<Number> ewcs;
   	@SuppressWarnings("rawtypes")
	protected DirectionalEdgeArrowTransformer arrowTransformer;
   	protected Map<Number, Number> edge_weight = new HashMap<Number, Number>();
   	
   	private ArrayList<MetabolicPathway> pathwaysList;
                                                                                                                     
    public ArrayList<MetabolicPathway> getPathwaysList() {
		return pathwaysList;
	}

	public void setPathwaysList(ArrayList<MetabolicPathway> pathwaysList) {
		this.pathwaysList = pathwaysList;
	}
	
	private Map<String, ArrayList<Double>> startPosMap = new HashMap<String, ArrayList<Double>>();

	/**                                                                                                              
     * create an instance of a simple graph with controls to                                                         
     * demo the zoom features.                                                                                       
     *                                                                                                               
     */                                                                                                              
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public PathwaysFrame() { 
        setLayout(new BorderLayout()); 
        
        transformItem.setState(false);
        
        /**************************************************************************/
    	// create menu bar
    	/**************************************************************************/

    	JMenuBar menuBar = new JMenuBar();

    	setJMenuBar(menuBar);

    	JMenu fileMenu = new JMenu("File");
    	fileMenu.setMnemonic(KeyEvent.VK_F);

    	menuBar.add(fileMenu);
    	
    	JMenu editMenu = new JMenu("Edit");
    	fileMenu.setMnemonic(KeyEvent.VK_E);
    	
    	editMenu.add(transformItem);
    	transformItem.setMnemonic(KeyEvent.VK_T);

    	transformItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean state = transformItem.getState();
				if (state == true) {
					final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse(); 
					graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
			        vv.setGraphMouse(graphMouse); 
				} else {
					final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse(); 
					graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
			        vv.setGraphMouse(graphMouse); 
				}
			}
		});

    	menuBar.add(editMenu);
    	
    	/**************************************************************************/
    	// end create menu bar
    	/**************************************************************************/
    	
    	ReactionFactory f = new ReactionFactory("SBML");
    	Vector<SBMLReaction> allReactions = f.getAllReactions();
    	Map<Integer, SBMLReaction> idReactionMap = new HashMap<Integer, SBMLReaction>();
		for (int i = 0; i < allReactions.size(); i++) {
			idReactionMap.put(allReactions.get(i).getId(), allReactions.get(i));
		}
    	
		// temporary lists to keep track of what ec numbers have been found
		ArrayList<String> foundEcNumbers = new ArrayList<String>();
	    ArrayList<String> notFoundEcNumbers = new ArrayList<String>(LocalConfig.getInstance().getEcNumberReactionMap().keySet());
		 
	    double startX = PathwaysFrameConstants.BORDER_WIDTH + PathwaysFrameConstants.HORIZONTAL_INCREMENT;
		double startY = PathwaysFrameConstants.START_Y;
		double maxX = 0;
		double maxY = 0;
	    if (LocalConfig.getInstance().getPeriplasmName() != null && LocalConfig.getInstance().getPeriplasmName().length() > 0) {
	    	startX += PathwaysFrameConstants.PERIPLASM_WIDTH;
	    	startY += PathwaysFrameConstants.PERIPLASM_HEIGHT;
	    }
	    
	    double sideSpeciesExchangeStartX = 0;
	    double sideSpeciesExchangeEndX = 0;
	    double sideSpeciesSecondExchangeStartX = 0;
		
	    ArrayList<String> foundMetabolitesList = new ArrayList<String>();
	    ArrayList<String> foundReactionsList = new ArrayList<String>();
		ArrayList<PathwayMetaboliteNode> externalMetaboliteNodeList = new ArrayList<PathwayMetaboliteNode>();
		ArrayList<PathwayMetaboliteNode> transportMetaboliteNodeList = new ArrayList<PathwayMetaboliteNode>();
	
		PathwayReactionNodeFactory prnf = new PathwayReactionNodeFactory();
		for (int i = 0; i < LocalConfig.getInstance().getDrawOrder().size(); i++) {
			MetabolicPathway pathway = LocalConfig.getInstance().getMetabolicPathways().get(LocalConfig.getInstance().getDrawOrder().get(i));
			if (pathway.getComponent() == PathwaysFrameConstants.MAIN_COMPONENT) {
				if (startPosMap.containsKey(pathway.getId())) {
					startX = startPosMap.get(pathway.getId()).get(0);
					startY = startPosMap.get(pathway.getId()).get(1);
				}
			} else if (pathway.getComponent() == PathwaysFrameConstants.PHOSPHORYLATION_COMPONENT) {
				// max will already be set at this time
				startX = maxX - PathwaysFrameConstants.PHOSPHORYLATION_X_OFFSET;
				startY = PathwaysFrameConstants.PHOSPHORYLATION_Y_OFFSET;
				if (LocalConfig.getInstance().getPeriplasmName() != null && LocalConfig.getInstance().getPeriplasmName().length() > 0) {
					startX += PathwaysFrameConstants.PERIPLASM_WIDTH;
					startY += PathwaysFrameConstants.PERIPLASM_HEIGHT;
				}	
				//System.out.println("start " + startX + " " + startY);
			}

			for (int j = 0; j < pathway.getMetabolitesData().size(); j++) {
				String metabName = pathway.getMetabolitesData().get(Integer.toString(j)).getName();
				//if (pathway.getComponent() == PathwaysFrameConstants.PHOSPHORYLATION_COMPONENT ||
				String keggId = pathway.getMetabolitesData().get(Integer.toString(j)).getKeggId();
				boolean drawMetabolite = true;
				if (!LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(keggId)) {
					if (!LocalConfig.getInstance().isGraphMissingMetabolitesSelected()) {
						drawMetabolite = false;
					}
					//System.out.println(keggId + " not found");
				} else {
					foundMetabolitesList.add(metabName);
				}
				if (drawMetabolite) {
					if (LocalConfig.getInstance().getSideSpeciesList().contains(pathway.getMetabolitesData().get(Integer.toString(j)).getKeggId())) {
						//if (LocalConfig.getInstance().getSideSpeciesList().contains(pathway.getMetabolitesData().get(Integer.toString(j)).getAbbreviation())) {
						noBorderList.add(pathway.getMetabolitesData().get(Integer.toString(j)).getName());
					}
					metabolites.add(metabName);
					PathwayMetaboliteNode pn = new PathwayMetaboliteNode();
					pn.setDataId(pathway.getMetabolitesData().get(Integer.toString(j)).getId());
					double x = 0;
					double y = 0;
					if (pathway.getDirection().equals("horizontal")) {
						x = startX + PathwaysFrameConstants.HORIZONTAL_INCREMENT*pathway.getMetabolitesData().get(Integer.toString(j)).getLevel();
						y = startY + PathwaysFrameConstants.VERTICAL_INCREMENT*pathway.getMetabolitesData().get(Integer.toString(j)).getLevelPosition();
					} else if (pathway.getDirection().equals("vertical")) {
						x = startX + PathwaysFrameConstants.HORIZONTAL_INCREMENT*pathway.getMetabolitesData().get(Integer.toString(j)).getLevelPosition();
						y = startY + PathwaysFrameConstants.VERTICAL_INCREMENT*pathway.getMetabolitesData().get(Integer.toString(j)).getLevel();
					}
					pn.setxPosition(x);
					pn.setyPosition(y);
					if (x > maxX) {
						maxX = x;
					}
					if (y > maxY) {
						maxY = y;
					}
					// set start and end positions for side species transport reactions
					if (keggId.equals(PathwaysFrameConstants.SIDE_SPECIES_EXCHANGE_START_POSITION_METABOLITE)) {
						sideSpeciesExchangeStartX = x;
					}
					if (keggId.equals(PathwaysFrameConstants.SIDE_SPECIES_EXCHANGE_END_POSITION_METABOLITE)) {
						sideSpeciesExchangeEndX = x;
					}
					if (keggId.equals(PathwaysFrameConstants.SIDE_SPECIES_EXCHANGE_SECOND_START_POSITION_METABOLITE)) {
						sideSpeciesSecondExchangeStartX = x;
					}
					pn.setAbbreviation(pathway.getMetabolitesData().get(Integer.toString(j)).getAbbreviation());
					pn.setName(pathway.getMetabolitesData().get(Integer.toString(j)).getName());
					pathway.getMetabolitesNodes().put(pn.getDataId(), pn);
					metabPosMap.put(metabName, new String[] {Double.toString(x), Double.toString(y)});
					// create transport metabolite if kegg id in transport metabolite file
					//String keggId = pathway.getMetabolitesData().get(Integer.toString(j)).getKeggId();
					if (pathway.getTransportMetabolitesData().containsKey(keggId)) {
						ExternalMetaboliteData emd = pathway.getTransportMetabolitesData().get(keggId);
						ExternalMetaboliteNode emn = new ExternalMetaboliteNode();
						emn.setxPosition(pn.getxPosition());
						emn.setyPosition(pn.getyPosition());
						//emn.setFluxValue(pn.getFluxValue());
						String abbr = emd.getAbbreviation();
						String name = emd.getName();
						if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(keggId)) {
							String metabAbbr = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId).get(0).getMetaboliteAbbreviation();
							// check if metabolite ends with "_x"
							String ch = metabAbbr.substring(metabAbbr.length() - 2, metabAbbr.length() - 1);
							if (ch.equals("_")) {
								abbr = metabAbbr.substring(2, metabAbbr.length() - 2);
							} else {
								abbr = metabAbbr.substring(2);
							}
							name += " " + abbr;
						}
						if (LocalConfig.getInstance().getMetaboliteNameAbbrMap().containsKey(name)) {
							name = name + duplicateSuffix(name, LocalConfig.getInstance().getMetaboliteNameAbbrMap());
						}
						LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(name, abbr);
						emn.setAbbreviation(abbr);
						emn.setName(name);
						emn.setKeggId(emd.getKeggMetaboliteId());
						emn.setPosition(emd.getPosition());
						emn.setOffset(emd.getOffset());
						emn.setDirection(emd.getDirection());
						//emn.setReversible(reversible);
						//emn.setReactionDisplayName(displayName);
						//System.out.println(emn);
						transportMetaboliteNodeList.add(emn);
						TransportReactionNode trn = new TransportReactionNode();
						trn.setCytosolName(metabName);
						if (LocalConfig.getInstance().getKeggIdTransportReactionsMap().containsKey(keggId)) {
							ArrayList<TransportReactionNode> trnList = LocalConfig.getInstance().getKeggIdTransportReactionsMap().get(keggId);
							for (int t = 0; t < trnList.size(); t++) {
								if (trnList.get(t).getTransportType().equals(TransportReactionConstants.CYTOSOL_EXTRAORGANISM_TRANSPORT) ||
										trnList.get(t).getTransportType().equals(TransportReactionConstants.CYTOSOL_PERIPLASM_TRANSPORT)) {
									trnList.get(t).setCytosolName(metabName);
								}
								System.out.println("key " + trnList.get(t));	
							}
						} else {
							System.out.println(keggId + " not present");
						}
					}
				}
			}
			
			for (int k = 0; k < pathway.getReactionsData().size(); k++) {
				// only draw cytosol for now
				PathwayReactionNode pn = prnf.createPathwayReactionNode(pathway.getReactionsData().get(Integer.toString(k)).getEcNumbers(),
						pathway.getReactionsData().get(Integer.toString(k)).getKeggReactionIds(), pathway.getReactionsData().get(Integer.toString(k)).getKeggReactantIds(),
						pathway.getReactionsData().get(Integer.toString(k)).getKeggProductIds(), LocalConfig.getInstance().getCytosolName(), pathway.getComponent());
				String displayName = prnf.createDisplayName(pathway.getReactionsData().get(Integer.toString(k)).getDisplayName(),
						pathway.getReactionsData().get(Integer.toString(k)).getName(),
						pn.getReactions(), idReactionMap);
				// update temporary lists to keep track of what ec numbers have been found
				for (int z = 0; z < pn.getReactions().size(); z++) {
					java.util.List<String> ecNumbers = Arrays.asList(pn.getReactions().get(z).getEcNumber().split("\\s"));
					for (int y = 0; y < ecNumbers.size(); y++) {
						//System.out.println(ecNumbers.get(y));
						if (!foundEcNumbers.contains(ecNumbers.get(y))) {
							foundEcNumbers.add(ecNumbers.get(y));
						}
						if (notFoundEcNumbers.contains(ecNumbers.get(y))) {
							notFoundEcNumbers.remove(ecNumbers.get(y));
						}
					}
					// set last one for now
					pn.setFluxValue(pn.getReactions().get(z).getFluxValue());
				}
				boolean drawReaction = true;
				if (pn.getReactions().size() > 0) {
					foundReactionsList.add(displayName);
				} else {
					if (!LocalConfig.getInstance().isGraphMissingReactionsSelected()) {
						drawReaction = false;
					}
				}
				if (drawReaction) {
					reactions.add(displayName);
					double x = 0;
					double y = 0;
					if (pathway.getDirection().equals("horizontal")) {
						x = startX + PathwaysFrameConstants.HORIZONTAL_INCREMENT*pathway.getReactionsData().get(Integer.toString(k)).getLevel();
						y = startY + PathwaysFrameConstants.VERTICAL_INCREMENT*pathway.getReactionsData().get(Integer.toString(k)).getLevelPosition();
					} else if (pathway.getDirection().equals("vertical")) {
						x = startX + PathwaysFrameConstants.HORIZONTAL_INCREMENT*pathway.getReactionsData().get(Integer.toString(k)).getLevelPosition();
						y = startY + PathwaysFrameConstants.VERTICAL_INCREMENT*pathway.getReactionsData().get(Integer.toString(k)).getLevel();
					}
					metabPosMap.put(displayName, new String[] {Double.toString(x), Double.toString(y)});  
					pn.setDataId(pathway.getReactionsData().get(Integer.toString(k)).getReactionId());
					pn.setxPosition(x);
					pn.setyPosition(y);
					String reversible = prnf.reversibleString(pathway.getReactionsData().get(Integer.toString(k)).getReversible());
					pn.setReversible(reversible);
					pathway.getReactionsNodes().put(pn.getDataId(), pn);
//					System.out.println(pn.getSideReactants());
//					System.out.println(pn.getSideProducts());
					//System.out.println("max ub " + LocalConfig.getInstance().getMaxUpperBound());
					//System.out.println(pathway.getReactionsData().get(Integer.toString(k)).getReactionId());
					// create external metabolite nodes
					if (pathway.getExternalMetabolitesData().containsKey(pathway.getReactionsData().get(Integer.toString(k)).getReactionId())) {
						//System.out.println("e " + pathway.getExternalMetabolitesData().get(pathway.getReactionsData().get(Integer.toString(k)).getReactionId()));
						ExternalMetaboliteNode emn = new ExternalMetaboliteNode();
						ExternalMetaboliteData emd = pathway.getExternalMetabolitesData().get(pathway.getReactionsData().get(Integer.toString(k)).getReactionId());
						emn.setxPosition(pn.getxPosition());
						emn.setyPosition(pn.getyPosition());
						emn.setFluxValue(pn.getFluxValue());
						String abbr = emd.getAbbreviation();
						String name = emd.getName();
						if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(emd.getKeggMetaboliteId())) {
							String metabAbbr = LocalConfig.getInstance().getKeggIdMetaboliteMap().get(emd.getKeggMetaboliteId()).get(0).getMetaboliteAbbreviation();
							// check if metabolite ends with "_x"
							String ch = metabAbbr.substring(metabAbbr.length() - 2, metabAbbr.length() - 1);
							if (ch.equals("_")) {
								abbr = metabAbbr.substring(2, metabAbbr.length() - 2);
							} else {
								abbr = metabAbbr.substring(2);
							}
							name += " " + abbr;
						}
//						if (LocalConfig.getInstance().getMetaboliteNameAbbrMap().containsKey(name)) {
//							name = name + duplicateSuffix(name, LocalConfig.getInstance().getMetaboliteNameAbbrMap());
//						}
						LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(name, abbr);
						emn.setAbbreviation(abbr);
						emn.setName(name);
						emn.setKeggId(emd.getKeggMetaboliteId());
						emn.setPosition(emd.getPosition());
						emn.setOffset(emd.getOffset());
						emn.setDirection(emd.getDirection());
						emn.setReversible(reversible);
						emn.setReactionDisplayName(displayName);
						externalMetaboliteNodeList.add(emn);
					}
					for (int r = 0; r < pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().size(); r++) {
						if (pathway.getMetabolitesData().containsKey((pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().get(r)))) {
							String reac = pathway.getMetabolitesData().get((pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().get(r))).getName();
							reactionMap.put(displayName + "reactant " + Integer.toString(r), new String[] {displayName, reac, reversible});
							fluxMap.put(displayName + "reactant " + Integer.toString(r), edgeThickness(pn.getFluxValue()));
						}
					}
					for (int p = 0; p < pathway.getReactionsData().get(Integer.toString(k)).getProductIds().size(); p++) {
						if (pathway.getMetabolitesData().containsKey((pathway.getReactionsData().get(Integer.toString(k)).getProductIds().get(p)))) {
							String prod = pathway.getMetabolitesData().get((pathway.getReactionsData().get(Integer.toString(k)).getProductIds().get(p))).getName();
							reactionMap.put(displayName + "product " + Integer.toString(p), new String[] {displayName, prod, "true"});
							fluxMap.put(displayName + "product " + Integer.toString(p), edgeThickness(pn.getFluxValue()));
						}
					}
				}
			}
			
			// update start position map if necessary
			if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()) != null) {
				if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(0).getReactantPathwaysIds().get(0).get(0).equals(pathway.getId())) {
					for (int p = 0; p < LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).size(); p++) {
						ArrayList<Double> xyList = new ArrayList<Double>();
						double newStartX = pathway.getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(1)).getxPosition();;
						double newStartY = pathway.getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(1)).getyPosition();
						if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getDirection().equals("vertical")) {
							newStartY += LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getLength() * PathwaysFrameConstants.VERTICAL_INCREMENT;	
						} else if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getDirection().equals("horizontal")) {
							newStartX += LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getLength() * PathwaysFrameConstants.HORIZONTAL_INCREMENT;
						}
						xyList.add(newStartX);
						xyList.add(newStartY);
						startPosMap.put(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().get(0).get(0), xyList);
					}
				}
			}
		}
		
		ArrayList<PathwayConnectionNode> connectionsNodelist = new ArrayList<PathwayConnectionNode>();
		for (int i = 0; i < LocalConfig.getInstance().getConnectionslist().size(); i++) {
			// only draw cytosol for now
			PathwayReactionNode pn = prnf.createPathwayReactionNode(LocalConfig.getInstance().getConnectionslist().get(i).getEcNumbers(),
					LocalConfig.getInstance().getConnectionslist().get(i).getKeggReactionIds(), 
					LocalConfig.getInstance().getConnectionslist().get(i).getKeggReactantIds(),
					LocalConfig.getInstance().getConnectionslist().get(i).getKeggProductIds(),
					LocalConfig.getInstance().getCytosolName(), 0);
			PathwayConnectionNode pcn = prnf.createPathwayConnectionNode(pn);
			pcn.setEquation(LocalConfig.getInstance().getConnectionslist().get(i).getEquation());
			pcn.setName(LocalConfig.getInstance().getConnectionslist().get(i).getEquation());
			String displayName = prnf.createDisplayName(LocalConfig.getInstance().getConnectionslist().get(i).getDisplayName(),
					LocalConfig.getInstance().getConnectionslist().get(i).getName(),
					pcn.getReactions(), idReactionMap);
			pcn.setDisplayName(displayName);
			ArrayList<PathwayMetaboliteNode> mainPathwayReactants = new ArrayList<PathwayMetaboliteNode>();
			ArrayList<PathwayMetaboliteNode> mainPathwayProducts = new ArrayList<PathwayMetaboliteNode>();
			pcn.setMainPathwayReactants(mainPathwayReactants);
			pcn.setMainPathwayProducts(mainPathwayProducts);
			pcn.setReactionName(LocalConfig.getInstance().getConnectionslist().get(i).getName());
			double avgReacX = 0;
			double avgReacY = 0;
			double avgProdX = 0;
			double avgProdY = 0;
			for (int j = 0; j < LocalConfig.getInstance().getConnectionslist().get(i).getReactantPathwaysIds().size(); j++) {
				PathwayMetaboliteNode reacNode = LocalConfig.getInstance().getMetabolicPathways().get(LocalConfig.getInstance().getConnectionslist().get(i).getReactantPathwaysIds().get(j).get(0)).getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionslist().get(i).getReactantPathwaysIds().get(j).get(1));				
				pcn.getMainPathwayReactants().add(reacNode);
				if (reacNode != null) {
					avgReacX += reacNode.getxPosition();
					avgReacY += reacNode.getyPosition();
				}
			}
			avgReacX = avgReacX/LocalConfig.getInstance().getConnectionslist().get(i).getReactantPathwaysIds().size();
			avgReacY = avgReacY/LocalConfig.getInstance().getConnectionslist().get(i).getReactantPathwaysIds().size();
			for (int k = 0; k < LocalConfig.getInstance().getConnectionslist().get(i).getProductPathwaysIds().size(); k++) {
				PathwayMetaboliteNode prodNode = LocalConfig.getInstance().getMetabolicPathways().get(LocalConfig.getInstance().getConnectionslist().get(i).getProductPathwaysIds().get(k).get(0)).getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionslist().get(i).getProductPathwaysIds().get(k).get(1));
				pcn.getMainPathwayProducts().add(prodNode);
				if (prodNode != null) {
					avgProdX += prodNode.getxPosition();
					avgProdY += prodNode.getyPosition();
				}
			}
			avgProdX = avgProdX/LocalConfig.getInstance().getConnectionslist().get(i).getProductPathwaysIds().size();
			avgProdY = avgProdY/LocalConfig.getInstance().getConnectionslist().get(i).getProductPathwaysIds().size();
			
			reactions.add(pcn.getReactionName());
			double avgX = (avgReacX + avgProdX)/2;
			double avgY = (avgReacY + avgProdY)/2;
			// length field set to > 1 or < -1 for second reaction if two reactions connect 
			// connection nodes, negative and positive values allow control of relative 
			// position to first connecting reaction
			if (Math.abs(LocalConfig.getInstance().getConnectionslist().get(i).getLength()) > 1 &&
					LocalConfig.getInstance().getConnectionslist().get(i).getPositioning().equals("0")) {
				if (LocalConfig.getInstance().getConnectionslist().get(i).getLength() > 0) {
					avgX += PathwaysFrameConstants.REACTION_NODE_WIDTH/2;
				} else {
					avgX -= PathwaysFrameConstants.REACTION_NODE_WIDTH/2;
				}
				avgY += LocalConfig.getInstance().getConnectionslist().get(i).getLength()*PathwaysFrameConstants.REACTION_NODE_HEIGHT/2;
			}
			pcn.setxPosition(avgX);
			pcn.setyPosition(avgY);
			pcn.setReversible(LocalConfig.getInstance().getConnectionslist().get(i).getReversible());
			connectionsNodelist.add(pcn);
		}
		
		LocalConfig.getInstance().setConnectionsNodelist(connectionsNodelist);
		
		for (int c = 0; c < connectionsNodelist.size(); c++) {
			String reversible = prnf.reversibleString(connectionsNodelist.get(c).getReversible());
			String displayName = prnf.createDisplayName(connectionsNodelist.get(c).getDisplayName(),
					connectionsNodelist.get(c).getName(),
					connectionsNodelist.get(c).getReactions(), idReactionMap);
			// update temporary lists to keep track of what ec numbers have been found
			for (int z = 0; z < connectionsNodelist.get(c).getReactions().size(); z++) {
				java.util.List<String> ecNumbers = Arrays.asList(connectionsNodelist.get(c).getReactions().get(z).getEcNumber().split("\\s"));
				for (int y = 0; y < ecNumbers.size(); y++) {
					if (!foundEcNumbers.contains(ecNumbers.get(y))) {
						foundEcNumbers.add(ecNumbers.get(y));
					}
					if (notFoundEcNumbers.contains(ecNumbers.get(y))) {
						notFoundEcNumbers.remove(ecNumbers.get(y));
					}
				}
				// set last one for now
				connectionsNodelist.get(c).setFluxValue(connectionsNodelist.get(c).getReactions().get(z).getFluxValue());
			}
			boolean drawReaction = true;
			if (connectionsNodelist.get(c).getReactions().size() > 0) {
				foundReactionsList.add(displayName);
			} else {
				if (!LocalConfig.getInstance().isGraphMissingReactionsSelected()) {
					drawReaction = false;
				}
			}
			if (drawReaction) {
				reactions.add(displayName);
				metabPosMap.put(displayName, new String[] {Double.toString(connectionsNodelist.get(c).getxPosition()), Double.toString(connectionsNodelist.get(c).getyPosition())});  
				for (int d = 0; d < connectionsNodelist.get(c).getMainPathwayReactants().size(); d++) {
					String reac = connectionsNodelist.get(c).getMainPathwayReactants().get(d).getName();
					reactionMap.put(displayName + "reactant " + Integer.toString(d), new String[] {displayName, reac, reversible});
					fluxMap.put(displayName + "reactant " + Integer.toString(d), edgeThickness(connectionsNodelist.get(c).getFluxValue()));
				}
				for (int e = 0; e < connectionsNodelist.get(c).getMainPathwayProducts().size(); e++) {
					String prod = connectionsNodelist.get(c).getMainPathwayProducts().get(e).getName();
					reactionMap.put(displayName + "product " + Integer.toString(e), new String[] {displayName, prod, "true"});
					fluxMap.put(displayName + "product " + Integer.toString(e), edgeThickness(connectionsNodelist.get(c).getFluxValue()));
				}
			}
		}
		
		if (LocalConfig.getInstance().getPeriplasmName() != null && LocalConfig.getInstance().getPeriplasmName().length() > 0) {
	    	maxX += PathwaysFrameConstants.PERIPLASM_WIDTH;
	    	maxY += PathwaysFrameConstants.PERIPLASM_HEIGHT;
	    }
		
		String borderLeftX = Integer.toString(PathwaysFrameConstants.BORDER_WIDTH);
		String borderRightX = Double.toString(maxX + PathwaysFrameConstants.RIGHT_BORDER_INCREMENT + PathwaysFrameConstants.METABOLITE_NODE_WIDTH);
		String borderTopY = Integer.toString(PathwaysFrameConstants.BORDER_HEIGHT);
		String borderBottomY = Double.toString(maxY + PathwaysFrameConstants.BOTTOM_SPACE + PathwaysFrameConstants.METABOLITE_NODE_HEIGHT);
		
		// draw cell border
		metabPosMap.put("1", new String[] {borderLeftX, borderTopY}); 
		metabPosMap.put("2", new String[] {borderRightX, borderTopY}); 
		metabPosMap.put("3", new String[] {borderRightX, borderBottomY});
		metabPosMap.put("4", new String[] {borderLeftX, borderBottomY});                                                         

		reactionMap.put("1", new String[] {"1", "2", "false"});
		reactionMap.put("2", new String[] {"2", "3", "false"});
		reactionMap.put("3", new String[] {"3", "4", "false"});
		reactionMap.put("4", new String[] {"4", "1", "false"});

		fluxMap.put("1", PathwaysFrameConstants.BORDER_THICKNESS);
		fluxMap.put("2", PathwaysFrameConstants.BORDER_THICKNESS);
		fluxMap.put("3", PathwaysFrameConstants.BORDER_THICKNESS);
		fluxMap.put("4", PathwaysFrameConstants.BORDER_THICKNESS);

		for (int b = 1; b < 5; b++) {
			borderList.add(Integer.toString(b));
		}
		
		if (LocalConfig.getInstance().getPeriplasmName() != null && LocalConfig.getInstance().getPeriplasmName().length() > 0) {
			borderLeftX = Integer.toString(PathwaysFrameConstants.BORDER_WIDTH + PathwaysFrameConstants.PERIPLASM_WIDTH);
			borderRightX = Double.toString(maxX + PathwaysFrameConstants.RIGHT_BORDER_INCREMENT + PathwaysFrameConstants.METABOLITE_NODE_WIDTH - PathwaysFrameConstants.PERIPLASM_WIDTH);
			borderTopY = Integer.toString(PathwaysFrameConstants.BORDER_HEIGHT + PathwaysFrameConstants.PERIPLASM_HEIGHT);
			borderBottomY = Double.toString(maxY + PathwaysFrameConstants.BOTTOM_SPACE + PathwaysFrameConstants.METABOLITE_NODE_HEIGHT - PathwaysFrameConstants.PERIPLASM_HEIGHT);
			//String borderLeftX = Integer.toString(PathwaysFrameConstants.BORDER_WIDTH + PathwaysFrameConstants.PERIPLASM_WIDTH);
		   	//String borderRightX = Integer.toString(PathwaysFrameConstants.GRAPH_WIDTH - (PathwaysFrameConstants.BORDER_WIDTH + PathwaysFrameConstants.PERIPLASM_WIDTH));
		   	//String borderTopY = Integer.toString(PathwaysFrameConstants.BORDER_HEIGHT + PathwaysFrameConstants.PERIPLASM_HEIGHT);
		   	//String borderBottomY = Integer.toString(PathwaysFrameConstants.GRAPH_HEIGHT - (PathwaysFrameConstants.BORDER_HEIGHT + PathwaysFrameConstants.PERIPLASM_HEIGHT));
		   	metabPosMap.put("5", new String[] {borderLeftX, borderTopY});                                                        
			metabPosMap.put("6", new String[] {borderRightX, borderTopY}); 
			metabPosMap.put("7", new String[] {borderRightX, borderBottomY});
			metabPosMap.put("8", new String[] {borderLeftX, borderBottomY});                                                         
			
			reactionMap.put("5", new String[] {"5", "6", "false"});
			reactionMap.put("6", new String[] {"6", "7", "false"});
			reactionMap.put("7", new String[] {"7", "8", "false"});
			reactionMap.put("8", new String[] {"8", "5", "false"});
			
			fluxMap.put("5", PathwaysFrameConstants.BORDER_THICKNESS);
			fluxMap.put("6", PathwaysFrameConstants.BORDER_THICKNESS);
			fluxMap.put("7", PathwaysFrameConstants.BORDER_THICKNESS);
			fluxMap.put("8", PathwaysFrameConstants.BORDER_THICKNESS);
			
			for (int b = 5; b < 9; b++) {
				borderList.add(Integer.toString(b));
			}
		}
		
		System.out.println(LocalConfig.getInstance().getSideSpeciesTransportReactionNodeMap());
		ArrayList<String> sideSpeciesTransportMetabs = new ArrayList<String>(LocalConfig.getInstance().getSideSpeciesTransportMetaboliteKeggIdMap().keySet());
		Collections.sort(sideSpeciesTransportMetabs);
		
		boolean startSecondInterval = false;
		for (int s = 0; s < sideSpeciesTransportMetabs.size(); s++) {
			String keggId = LocalConfig.getInstance().getSideSpeciesTransportMetaboliteKeggIdMap().get(sideSpeciesTransportMetabs.get(s));
			String keggName = "";
			if (LocalConfig.getInstance().getMetaboliteDataKeggIdMap().containsKey(keggId)) {
				keggName = LocalConfig.getInstance().getMetaboliteDataKeggIdMap().get(keggId).getNames().get(0);
			}
			ArrayList<TransportReactionNode> trnList = LocalConfig.getInstance().getSideSpeciesTransportReactionNodeMap().get(keggId);
			// eventually fad, gdp and gtp will be in pathways, remove for now
			// also remove atp. even though kegg id in transport_metabolites.csv, it still
			// gets plotted in recon model
			String parentNode = "";
			if (keggId.equals("C00035") || keggId.equals("C00044") || keggId.equals("C00016") || keggId.equals("C00002")) {

			} else {
				double nodeY = Double.parseDouble(borderBottomY) - PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT;
				ArrayList<String> compartmentList = LocalConfig.getInstance().getKeggIdCompartmentMap().get(keggId);
				if (LocalConfig.getInstance().getCytosolName() != null && LocalConfig.getInstance().getCytosolName().length() > 0 &&
						compartmentList.contains(LocalConfig.getInstance().getCytosolName())) {
					String metabAbbr = sideSpeciesTransportMetabs.get(s) + "_c";
					for (int t = 0; t < trnList.size(); t++) {
						if (trnList.get(t).getCytosolName() != null && trnList.get(t).getCytosolName().length() > 0) {
							metabAbbr = trnList.get(t).getCytosolName().substring(2);
						}
					}
					String metabName = metabAbbr;
					if (keggName.length() > 0) {
						metabName = keggName + " " + metabAbbr;
					}
					metabolites.add(metabName);
					PathwayMetaboliteNode pn = new PathwayMetaboliteNode();
					pn.setxPosition(sideSpeciesExchangeStartX);
					pn.setyPosition(nodeY);
					pn.setAbbreviation(metabAbbr);
					pn.setName(metabName);
					LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(metabName, metabAbbr);
					metabPosMap.put(metabName, new String[] {Double.toString(pn.getxPosition()), Double.toString(pn.getyPosition())});
					noBorderList.add(metabName);
					foundMetabolitesList.add(metabName);
					parentNode = metabName;
					nodeY += 2*PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT;
				}
				if (LocalConfig.getInstance().getPeriplasmName() != null && LocalConfig.getInstance().getPeriplasmName().length() > 0 &&
						compartmentList.contains(LocalConfig.getInstance().getPeriplasmName())) {
					String metabAbbr = sideSpeciesTransportMetabs.get(s) + "_p";
					for (int t = 0; t < trnList.size(); t++) {
						if (trnList.get(t).getPeriplasmName() != null && trnList.get(t).getPeriplasmName().length() > 0) {
							metabAbbr = trnList.get(t).getPeriplasmName().substring(2);
						}
					}
					String metabName = metabAbbr;
					if (keggName.length() > 0) {
						metabName = keggName + " " + metabAbbr;
					}
					metabolites.add(metabName);
					PathwayMetaboliteNode pn = new PathwayMetaboliteNode();
					pn.setxPosition(sideSpeciesExchangeStartX);
					pn.setyPosition(nodeY);
					pn.setAbbreviation(metabAbbr);
					pn.setName(metabName);
					LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(metabName, metabAbbr);
					metabPosMap.put(metabName, new String[] {Double.toString(pn.getxPosition()), Double.toString(pn.getyPosition())});
					noBorderList.add(metabName);
					foundMetabolitesList.add(metabName);
					String reactantName = parentNode;
					String productName = metabName;
					for (int t = 0; t < trnList.size(); t++) {
						if (trnList.get(t).getTransportType().equals(TransportReactionConstants.CYTOSOL_PERIPLASM_TRANSPORT)) {
							trnList.get(t).setxPosition(sideSpeciesExchangeStartX);
							trnList.get(t).setyPosition(nodeY - PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT);
							if (trnList.get(t).getDirection().equals("-1")) {
								reactantName = metabName;
								productName = parentNode;
							} 
							//System.out.println("pn " + parentNode);
							reactions.add(trnList.get(t).getReactionAbbr());
							metabPosMap.put(trnList.get(t).getReactionAbbr(), new String[] {Double.toString(trnList.get(t).getxPosition()), Double.toString(trnList.get(t).getyPosition())});  
							foundReactionsList.add(trnList.get(t).getReactionAbbr());
							reactionMap.put(trnList.get(t).getReactionAbbr() + " reactant " + 1, 
		   							new String[] {trnList.get(t).getReactionAbbr(), reactantName, 
		   							trnList.get(t).getReversible()});
		   					fluxMap.put(trnList.get(t).getReactionAbbr() + " reactant " + 1, 
		   							edgeThickness(trnList.get(t).getFluxValue()));
							reactionMap.put(trnList.get(t).getReactionAbbr() + " product " + 1, 
		   							new String[] {trnList.get(t).getReactionAbbr(), productName, 
		   							"true"});
		   					fluxMap.put(trnList.get(t).getReactionAbbr() + " product " + 1, 
		   							edgeThickness(trnList.get(t).getFluxValue()));
						}
					}
					parentNode = metabName;
					nodeY += PathwaysFrameConstants.PERIPLASM_HEIGHT;
					if (LocalConfig.getInstance().getExtraOrganismName() != null && LocalConfig.getInstance().getExtraOrganismName().length() > 0 &&
							compartmentList.contains(LocalConfig.getInstance().getExtraOrganismName())) {
						metabAbbr = sideSpeciesTransportMetabs.get(s) + "_e";
						for (int t = 0; t < trnList.size(); t++) {
							if (trnList.get(t).getExtraOrganismName() != null && trnList.get(t).getExtraOrganismName().length() > 0) {
								metabAbbr = trnList.get(t).getExtraOrganismName().substring(2);
							}
						}
						metabName = metabAbbr;
						if (keggName.length() > 0) {
							metabName = keggName + " " + metabAbbr;
						}
						metabolites.add(metabName);
						PathwayMetaboliteNode pn1 = new PathwayMetaboliteNode();
						pn1.setxPosition(sideSpeciesExchangeStartX);
						pn1.setyPosition(nodeY);
						pn1.setAbbreviation(metabAbbr);
						pn1.setName(metabName);
						LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(metabName, metabAbbr);
						metabPosMap.put(metabName, new String[] {Double.toString(pn1.getxPosition()), Double.toString(pn1.getyPosition())});
						noBorderList.add(metabName);
						foundMetabolitesList.add(metabName);
						reactantName = parentNode;
						productName = metabName;
						for (int t = 0; t < trnList.size(); t++) {
							if (trnList.get(t).getTransportType().equals(TransportReactionConstants.PERIPLASM_EXTRAORGANISM_TRANSPORT)) {
								trnList.get(t).setxPosition(sideSpeciesExchangeStartX);
								trnList.get(t).setyPosition(nodeY - PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT);
								if (trnList.get(t).getDirection().equals("-1")) {
									reactantName = metabName;
									productName = parentNode;
								} 
								//System.out.println("pn " + parentNode);
								reactions.add(trnList.get(t).getReactionAbbr());
								metabPosMap.put(trnList.get(t).getReactionAbbr(), new String[] {Double.toString(trnList.get(t).getxPosition()), Double.toString(trnList.get(t).getyPosition())});  
								foundReactionsList.add(trnList.get(t).getReactionAbbr());
								reactionMap.put(trnList.get(t).getReactionAbbr() + " reactant " + 2, 
			   							new String[] {trnList.get(t).getReactionAbbr(), reactantName, 
			   							trnList.get(t).getReversible()});
			   					fluxMap.put(trnList.get(t).getReactionAbbr() + " reactant " + 2, 
			   							edgeThickness(trnList.get(t).getFluxValue()));
								reactionMap.put(trnList.get(t).getReactionAbbr() + " product " + 2, 
			   							new String[] {trnList.get(t).getReactionAbbr(), productName, 
			   							"true"});
			   					fluxMap.put(trnList.get(t).getReactionAbbr() + " product " + 2, 
			   							edgeThickness(trnList.get(t).getFluxValue()));
							}
						}
					}
				} else if (LocalConfig.getInstance().getExtraOrganismName() != null && LocalConfig.getInstance().getExtraOrganismName().length() > 0 &&
						compartmentList.contains(LocalConfig.getInstance().getExtraOrganismName())) {
					String metabAbbr = sideSpeciesTransportMetabs.get(s) + "_e";
					for (int t = 0; t < trnList.size(); t++) {
						if (trnList.get(t).getExtraOrganismName() != null && trnList.get(t).getExtraOrganismName().length() > 0) {
							metabAbbr = trnList.get(t).getExtraOrganismName().substring(2);
						}
					}
					String metabName = metabAbbr;
					if (keggName.length() > 0) {
						metabName = keggName + " " + metabAbbr;
					}
					metabolites.add(metabName);
					PathwayMetaboliteNode pn1 = new PathwayMetaboliteNode();
					pn1.setxPosition(sideSpeciesExchangeStartX);
					pn1.setyPosition(nodeY);
					pn1.setAbbreviation(metabAbbr);
					pn1.setName(metabName);
					LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(metabName, metabAbbr);
					metabPosMap.put(metabName, new String[] {Double.toString(pn1.getxPosition()), Double.toString(pn1.getyPosition())});
					noBorderList.add(metabName);
					foundMetabolitesList.add(metabName);
					String reactantName = parentNode;
					String productName = metabName;
					for (int t = 0; t < trnList.size(); t++) {
						if (trnList.get(t).getTransportType().equals(TransportReactionConstants.CYTOSOL_EXTRAORGANISM_TRANSPORT)) {
							trnList.get(t).setxPosition(sideSpeciesExchangeStartX);
							trnList.get(t).setyPosition(nodeY - PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT);
							if (trnList.get(t).getDirection().equals("-1")) {
								reactantName = metabName;
								productName = parentNode;
							} 
							//System.out.println("pn " + parentNode);
							reactions.add(trnList.get(t).getReactionAbbr());
							metabPosMap.put(trnList.get(t).getReactionAbbr(), new String[] {Double.toString(trnList.get(t).getxPosition()), Double.toString(trnList.get(t).getyPosition())});  
							foundReactionsList.add(trnList.get(t).getReactionAbbr());
							reactionMap.put(trnList.get(t).getReactionAbbr() + " reactant " + -1, 
		   							new String[] {trnList.get(t).getReactionAbbr(), reactantName, 
		   							trnList.get(t).getReversible()});
		   					fluxMap.put(trnList.get(t).getReactionAbbr() + " reactant " + -1, 
		   							edgeThickness(trnList.get(t).getFluxValue()));
							reactionMap.put(trnList.get(t).getReactionAbbr() + " product " + -1, 
		   							new String[] {trnList.get(t).getReactionAbbr(), productName, 
		   							"true"});
		   					fluxMap.put(trnList.get(t).getReactionAbbr() + " product " + -1, 
		   							edgeThickness(trnList.get(t).getFluxValue()));
						}
					}
				}

				sideSpeciesExchangeStartX += PathwaysFrameConstants.REACTION_NODE_WIDTH + 10;
				if (sideSpeciesExchangeStartX > sideSpeciesExchangeEndX && !startSecondInterval) {
					startSecondInterval = true;
					sideSpeciesExchangeStartX = sideSpeciesSecondExchangeStartX;
				}
			}
		}
		
		//System.out.println(externalMetaboliteNodeList);
   		for (int e = 0; e < externalMetaboliteNodeList.size(); e++) {
   			updateExternalMetabolitePosition(externalMetaboliteNodeList.get(e), 
   		    		borderTopY, borderBottomY, borderLeftX, borderRightX, 
   		    		PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT, PathwaysFrameConstants.TRANSPORT_WIDTH_INCREMENT);
   			if (((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getOffset() != 0) {
   				updateExternalMetaboliteOffset(externalMetaboliteNodeList.get(e));
   			}
   			if (LocalConfig.getInstance().getPeriplasmName() != null && LocalConfig.getInstance().getPeriplasmName().length() > 0) {
   				LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX, externalMetaboliteNodeList.get(e).getAbbreviation() + "_p");
   				if (LocalConfig.getInstance().getSideSpeciesList().contains(externalMetaboliteNodeList.get(e).getKeggId())) {
   					noBorderList.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX);
   				}
   				metabolites.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX);
   				metabPosMap.put(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX, new String[] {Double.toString(externalMetaboliteNodeList.get(e).getxPosition()), 
   					Double.toString(externalMetaboliteNodeList.get(e).getyPosition())});
   				if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(externalMetaboliteNodeList.get(e).getKeggId())) {
   					foundMetabolitesList.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX);
   				}
   				if (((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getDirection() == -1) {
   					reactionMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "reactant " + -1, 
   							new String[] {((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName(), 
   							externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX, 
   							((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReversible()});
   					fluxMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "reactant " + -1, 
   							edgeThickness(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getFluxValue()));
   				} else if (((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getDirection() == 1) {
   					reactionMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "product " + -1, 
   							new String[] {((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName(), 
   							externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX, 
   					"true"});
   					fluxMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "product " + -1, 
   							edgeThickness(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getFluxValue()));
   				}
   	   			// add second node for extra organism
   				updateExternalMetabolitePosition(externalMetaboliteNodeList.get(e), 
   	   		    		borderTopY, borderBottomY, borderLeftX, borderRightX, 
   	   		    		PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT + PathwaysFrameConstants.PERIPLASM_HEIGHT, 
   	   		    		PathwaysFrameConstants.TRANSPORT_WIDTH_INCREMENT + PathwaysFrameConstants.PERIPLASM_WIDTH);
   	   			LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, externalMetaboliteNodeList.get(e).getAbbreviation() + "_e");
   	   			if (LocalConfig.getInstance().getSideSpeciesList().contains(externalMetaboliteNodeList.get(e).getKeggId())) {
   	   				noBorderList.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   	   			}
	   			metabolites.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
	   			metabPosMap.put(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, new String[] {Double.toString(externalMetaboliteNodeList.get(e).getxPosition()), 
	   				Double.toString(externalMetaboliteNodeList.get(e).getyPosition())});
	   			if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(externalMetaboliteNodeList.get(e).getKeggId())) {
   					foundMetabolitesList.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   				}
   			} else {
   				LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, externalMetaboliteNodeList.get(e).getAbbreviation() + "_e");
   				if (LocalConfig.getInstance().getSideSpeciesList().contains(externalMetaboliteNodeList.get(e).getKeggId())) {
   	   				noBorderList.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   	   			}
   	   			metabolites.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   	   			metabPosMap.put(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, new String[] {Double.toString(externalMetaboliteNodeList.get(e).getxPosition()), 
   	   				Double.toString(externalMetaboliteNodeList.get(e).getyPosition())});
   	   			if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(externalMetaboliteNodeList.get(e).getKeggId())) {
					foundMetabolitesList.add(externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
				}
   	   			if (((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getDirection() == -1) {
   	   				reactionMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "reactant " + -1, 
   	   					new String[] {((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName(), 
   	   					externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, 
   	   					((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReversible()});
   	   				fluxMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "reactant " + -1, 
   	   					edgeThickness(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getFluxValue()));
   	   			} else if (((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getDirection() == 1) {
   	   				reactionMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "product " + -1, 
   	   					new String[] {((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName(), 
   	   					externalMetaboliteNodeList.get(e).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, 
   	   					"true"});
   	   				fluxMap.put(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getReactionDisplayName() + "product " + -1, 
   	   					edgeThickness(((ExternalMetaboliteNode) externalMetaboliteNodeList.get(e)).getFluxValue()));
   	   			}
   			}
   		}
   		
   		for (int t = 0; t < transportMetaboliteNodeList.size(); t++) {
   			//System.out.println("transport " + transportMetaboliteNodeList.get(t).getKeggId());
   			if (LocalConfig.getInstance().getKeggIdCompartmentMap().containsKey(transportMetaboliteNodeList.get(t).getKeggId())) {
   				//System.out.println("transport " + LocalConfig.getInstance().getKeggIdCompartmentMap().get(transportMetaboliteNodeList.get(t).getKeggId()));
   			}
   			updateExternalMetabolitePosition(transportMetaboliteNodeList.get(t), 
   		    		borderTopY, borderBottomY, borderLeftX, borderRightX, 
   		    		PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT, PathwaysFrameConstants.TRANSPORT_WIDTH_INCREMENT);
   			if (((ExternalMetaboliteNode) transportMetaboliteNodeList.get(t)).getOffset() != 0) {
   				updateExternalMetaboliteOffset(transportMetaboliteNodeList.get(t));
   			}
   			if (LocalConfig.getInstance().getPeriplasmName() != null && LocalConfig.getInstance().getPeriplasmName().length() > 0) {
   				LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX, transportMetaboliteNodeList.get(t).getAbbreviation() + "_p");
   				if (LocalConfig.getInstance().getSideSpeciesList().contains(transportMetaboliteNodeList.get(t).getKeggId())) {
   					noBorderList.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX);
   				}
   				metabolites.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX);
   				metabPosMap.put(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX, new String[] {Double.toString(transportMetaboliteNodeList.get(t).getxPosition()), 
   					Double.toString(transportMetaboliteNodeList.get(t).getyPosition())});
   				if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(transportMetaboliteNodeList.get(t).getKeggId())) {
   					foundMetabolitesList.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.PERIPLASM_SUFFIX);
   				}
   	   			// add second node for extra organism
   				updateExternalMetabolitePosition(transportMetaboliteNodeList.get(t), 
   	   		    		borderTopY, borderBottomY, borderLeftX, borderRightX, 
   	   		    		PathwaysFrameConstants.TRANSPORT_HEIGHT_INCREMENT + PathwaysFrameConstants.PERIPLASM_HEIGHT, 
   	   		    		PathwaysFrameConstants.TRANSPORT_WIDTH_INCREMENT + PathwaysFrameConstants.PERIPLASM_WIDTH);
   	   			LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, transportMetaboliteNodeList.get(t).getAbbreviation() + "_e");
   	   			if (LocalConfig.getInstance().getSideSpeciesList().contains(transportMetaboliteNodeList.get(t).getKeggId())) {
   	   				noBorderList.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   	   			}
	   			metabolites.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
	   			metabPosMap.put(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, new String[] {Double.toString(transportMetaboliteNodeList.get(t).getxPosition()), 
	   				Double.toString(transportMetaboliteNodeList.get(t).getyPosition())});
	   			if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(transportMetaboliteNodeList.get(t).getKeggId())) {
	   				foundMetabolitesList.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
	   			}
   			} else {
   				LocalConfig.getInstance().getMetaboliteNameAbbrMap().put(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, transportMetaboliteNodeList.get(t).getAbbreviation() + "_e");
   				if (LocalConfig.getInstance().getSideSpeciesList().contains(transportMetaboliteNodeList.get(t).getKeggId())) {
   					noBorderList.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   				}
   				metabolites.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   				metabPosMap.put(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX, new String[] {Double.toString(transportMetaboliteNodeList.get(t).getxPosition()), 
   					Double.toString(transportMetaboliteNodeList.get(t).getyPosition())});
   				if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(transportMetaboliteNodeList.get(t).getKeggId())) {
   					foundMetabolitesList.add(transportMetaboliteNodeList.get(t).getName() + PathwaysFrameConstants.EXTRAORGANISM_SUFFIX);
   				}
   			}
   		}
		
		Collections.sort(foundEcNumbers);
		System.out.println("found " + foundEcNumbers);
		Collections.sort(notFoundEcNumbers);
		System.out.println("not found " + notFoundEcNumbers);
                                                                                                                     
   		metaboliteList = new ArrayList<String>(metabPosMap.keySet()); 
   		Collections.sort(metaboliteList);
   		//System.out.println("m " + metaboliteList);
   		
   		reactionList = new ArrayList<String>(reactionMap.keySet()); 
   		Collections.sort(reactionList);
                                                                                                                     
        // create graph     
        graph = new SparseMultigraph<String, Number>();  
        createVertices();                                                                                            
        createEdges();                                                                                               
                                                                                                                                                                                                                                                                                                                      
        Dimension layoutSize = new Dimension(PathwaysFrameConstants.GRAPH_WIDTH, PathwaysFrameConstants.GRAPH_HEIGHT);                                                             
                                                                                                                     
        Layout<String,Number> layout = new StaticLayout<String,Number>(graph,                                        
        		new ChainedTransformer(new Transformer[]{                                                            
        				new MetabTransformer(metabPosMap),                                                                    
        				new PixelTransformer(new Dimension(PathwaysFrameConstants.GRAPH_WIDTH, PathwaysFrameConstants.GRAPH_HEIGHT))                                         
        		}));                                                                                                 
        	                                                                                                         
        layout.setSize(layoutSize);                                                                                  
        vv =  new VisualizationViewer<String,Number>(layout,                                                         
        		new Dimension(1000,600));   
        
        vv.setBackground(Color.white);
        
        // based on code from http://stackoverflow.com/questions/21657249/mouse-events-on-vertex-of-jung-graph
        vv.addGraphMouseListener(new GraphMouseListener() {

			@Override
			public void graphClicked(final Object arg0, MouseEvent me) {
				// TODO Auto-generated method stub
				if (me.getButton() == MouseEvent.BUTTON3) {
					final VisualizationViewer<String,String> vv =(VisualizationViewer<String,String>)me.getSource();
			        //final Point2D p = me.getPoint();
			        JPopupMenu popup = new JPopupMenu();
			        JMenuItem editNodeMenu = new JMenuItem("Edit");
			        editNodeMenu.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							createNodeEditorDialog(arg0);
						}
					});
			        popup.add(editNodeMenu);
			        popup.show(vv, me.getX(), me.getY());
                }
				if (me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2) {
					createNodeEditorDialog(arg0);
                }
                me.consume();
			}

			@Override
			public void graphPressed(Object arg0, MouseEvent me) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void graphReleased(Object arg0, MouseEvent me) {
				// TODO Auto-generated method stub
				
			}
            
        });
           
        //System.out.println("fl " + foundReactionsList);
        Map<String, Icon> iconMap = new HashMap<String, Icon>();                                                                                        
        for(int i = 0; i < metaboliteList.size(); i++) {                                                                                                        
        	String name = metaboliteList.get(i);
        	String abbr = LocalConfig.getInstance().getMetaboliteNameAbbrMap().get(name);
        	int width = (int) PathwaysFrameConstants.BORDER_THICKNESS;
    		int height = (int) PathwaysFrameConstants.BORDER_THICKNESS;
        	if (borderList.contains(name)) {
        		width = (int) PathwaysFrameConstants.BORDER_THICKNESS;
        		height = (int) PathwaysFrameConstants.BORDER_THICKNESS;
        	} else if (metabolites.contains(name)) {
        		if (!noBorderList.contains(name)) {
        			width = PathwaysFrameConstants.METABOLITE_NODE_WIDTH;
                	height = PathwaysFrameConstants.METABOLITE_NODE_HEIGHT;
        		} else {
        			width = PathwaysFrameConstants.SIDE_NODE_WIDTH;
                	height = PathwaysFrameConstants.SIDE_NODE_HEIGHT;
        		}
        	} else if (reactions.contains(name)) {
        		width = PathwaysFrameConstants.REACTION_NODE_WIDTH;
        		height = PathwaysFrameConstants.REACTION_NODE_HEIGHT;
        	}
        	// based on http://stackoverflow.com/questions/2736320/write-text-onto-image-in-java
        	BufferedImage bufferedImage = new BufferedImage(width, height,
        			BufferedImage.TYPE_INT_RGB);
        	Graphics graphics = bufferedImage.getGraphics();
        	if (borderList.contains(name)) {
        		graphics.setColor(Color.black);
        	} else if (metabolites.contains(name)) {
        		if (!noBorderList.contains(name)) {
        			graphics.setColor(Color.lightGray);
        			if (!foundMetabolitesList.contains(name) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
            			graphics.setColor(Color.orange);
            		}
        		} else {
        			graphics.setColor(Color.white);
        		}
        	} else if (reactions.contains(name)) {
        		graphics.setColor(Color.white);
        		if (!foundReactionsList.contains(name) && LocalConfig.getInstance().isHighlightMissingReactionsSelected()) {
        			graphics.setColor(Color.yellow);
        		}
        	}
        	graphics.fillRect(0, 0, width, height);
        	graphics.setColor(Color.BLACK);
        	if (pathwayNames.contains(name)) {
        		graphics.setFont(new Font("Arial", Font.BOLD, PathwaysFrameConstants.PATHWAY_NAME_NODE_FONT_SIZE));
            	graphics.drawString(name, 5, 15);
        	} else {
        		if (metabolites.contains(name)) {
        			if (!noBorderList.contains(name)) {
        				alignCenterString(graphics, abbr, width, PathwaysFrameConstants.METABOLITE_NODE_XPOS, PathwaysFrameConstants.METABOLITE_NODE_YPOS, PathwaysFrameConstants.METABOLITE_NODE_FONT_SIZE);
        			} else {
        				if (!foundMetabolitesList.contains(name) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
                			graphics.setColor(Color.orange);
                		}
        				alignCenterString(graphics, abbr, width, PathwaysFrameConstants.SIDE_NODE_XPOS, PathwaysFrameConstants.SIDE_NODE_YPOS, PathwaysFrameConstants.SIDE_NODE_FONT_SIZE);
        			}
        		} else if (reactions.contains(name)) {
        			alignCenterString(graphics, name, width, PathwaysFrameConstants.REACTION_NODE_XPOS, PathwaysFrameConstants.REACTION_NODE_YPOS, PathwaysFrameConstants.REACTION_NODE_FONT_SIZE);
        		}
        	}
        	if (metabolites.contains(name) || reactions.contains(name)) {
        		if (!noBorderList.contains(name)) {
        			drawBorder(graphics, width, height, 4);
        		}
        	}
        	Icon icon = new ImageIcon(bufferedImage);
        	iconMap.put(name, icon);                                                                                                                                         
        }             
        
        final VertexIconShapeTransformer<String> vertexImageShapeFunction =                                                                           
                new VertexIconShapeTransformer<String>(new EllipseVertexShapeTransformer<String>());                                                      
                                                                                                                                                          
        final DefaultVertexIconTransformer<String> vertexIconFunction =                                                                               
            	new DefaultVertexIconTransformer<String>(); 
        
        vertexImageShapeFunction.setIconMap(iconMap);                                                                                                 
        vertexIconFunction.setIconMap(iconMap);                                                                                                       
                                                                                                                                                      
        vv.getRenderContext().setVertexShapeTransformer(vertexImageShapeFunction);                                                                    
        vv.getRenderContext().setVertexIconTransformer(vertexIconFunction); 
        
        // this class will provide both label drawing and vertex shapes
        //VertexLabelAsShapeRenderer<String,Number> vlasr = new VertexLabelAsShapeRenderer<String,Number>(vv.getRenderContext());

        vv.addPreRenderPaintable(new VisualizationViewer.Paintable(){                                            
        	public void paint(Graphics g) {                                                                      
        		Graphics2D g2d = (Graphics2D)g;                                                                  
        		AffineTransform oldXform = g2d.getTransform();                                                   
        		AffineTransform lat =                                                                            
        				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getTransform();
        		AffineTransform vat =                                                                            
        				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getTransform();  
        		AffineTransform at = new AffineTransform();                                                      
        		at.concatenate(g2d.getTransform());                                                              
        		at.concatenate(vat);                                                                             
        		at.concatenate(lat);                                                                             
        		g2d.setTransform(at);                                                                            
        		//                    g.drawImage(icon.getImage(), 0, 0,                                                               
        		//                    		icon.getIconWidth(),icon.getIconHeight(),vv);                                            
        		g2d.setTransform(oldXform);                                                                      
        	}                                                                                                    
        	public boolean useTransform() { return false; }                                                      
        });  
        
        ewcs = new EdgeWeightStrokeFunction<Number>(edge_weight);
        arrowTransformer = new DirectionalEdgeArrowTransformer(PathwaysFrameConstants.ARROW_LENGTH, PathwaysFrameConstants.ARROW_WIDTH, PathwaysFrameConstants.ARROW_NOTCH); 
        
        vv.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.gray));
        vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.black));
        //vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer(new BasicStroke(2.5f)));
        vv.getRenderContext().setEdgeStrokeTransformer(ewcs);
        vv.getRenderContext().setEdgeArrowTransformer(arrowTransformer);
        
        // add listeners for ToolTips  
        // may need to use abbreviations for labels
        // then tooltip could be full name and meta info if available
        vv.setVertexToolTipTransformer(new ToStringLabeller());   
        // labels should be either full names or abbreviations
        // tooltips - full names, EC#s, maybe even equation if 
        // zoom is sufficient?
        vv.setEdgeToolTipTransformer(new Transformer<Number,String>() {                                              
        	public String transform(Number edge) {                                                                   
        		return "E"+graph.getEndpoints(edge).toString();                                                      
        	}});                                                                                                     
                   
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<String,Number>());
        
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);                                               
        add(panel);                                                                                                  
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse(); 
		graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(graphMouse); 
                                                                                                                
        final ScalingControl scaler = new CrossoverScalingControl();                                                 
           
        // not sure what this does
        //vv.scaleToLayout(scaler);                                                                                                                                                                                              
                                                                                                                     
        JButton plus = new JButton("+");                                                                             
        plus.addActionListener(new ActionListener() {                                                                
            public void actionPerformed(ActionEvent e) {                                                             
                scaler.scale(vv, PathwaysFrameConstants.SCALING_FACTOR, vv.getCenter()); 
//                System.out.println("layout scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale());
//				System.out.println("view scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale());
                layoutScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale();
				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
            }                                                                                                        
        });                                                                                                          
        JButton minus = new JButton("-");                                                                            
        minus.addActionListener(new ActionListener() {                                                               
            public void actionPerformed(ActionEvent e) {                                                             
                scaler.scale(vv, 1/PathwaysFrameConstants.SCALING_FACTOR, vv.getCenter());
//                System.out.println("layout scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale());
//				System.out.println("view scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale());
                layoutScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale();
				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
            }                                                                                                        
        });                                                                                                          
                                                                                                                     
        JButton reset = new JButton("Reset");                                                                        
        reset.addActionListener(new ActionListener() {                                                               
                                                                                                                     
			public void actionPerformed(ActionEvent e) {                                                             
				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();       
				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setToIdentity();  
//				System.out.println("layout scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale());
//				System.out.println("view scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale());
				layoutScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale();
				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
			}});                                                                                                     
                                                                                                                     
        JPanel controls = new JPanel();                                                                              
        controls.add(plus);                                                                                          
        controls.add(minus);                                                                                         
        controls.add(reset);                                                                                         
        add(controls, BorderLayout.SOUTH);                                                                           
    }                                                                                                                
                                                                                                                     
    /**                                                                                                              
     * create some vertices                                                                                          
     * @param count how many to create                                                                               
     * @return the Vertices in an array                                                                              
     */                                                                                                              
    private void createVertices() {                                                                                  
        for (String met : metabPosMap.keySet()) {
            graph.addVertex(met); 
        } 
    }                                                                                                                
                                                                                                                     
    /**                                                                                                              
     * create edges for this demo graph                                                                              
     * @param v an array of Vertices to connect                                                                      
     */                                                                                                              
    void createEdges() { 
    	for(int i=0; i<reactionList.size(); i++) {
    		String[] info = reactionMap.get(reactionList.get(i));
    		String rev = info[2];
    		if (rev.equals("true")) {
    			graph.addEdge(new Double(i), info[0], info[1], EdgeType.DIRECTED); 
    		} else if (rev.equals("false")) {
    			graph.addEdge(new Double(i), info[0], info[1], EdgeType.UNDIRECTED); 
    		}
    		//String rxnName = "";
    		double fluxValue = fluxMap.get(reactionList.get(i));
    		edge_weight.put(new Double(i), fluxValue);
    	} 
    }                                                                                                                                                                                                                                                                                                                                             
                                                                                                                     
    static class MetabTransformer implements Transformer<String,String[]> {                                           
                                                                                                                     
    	Map<String,String[]> map;                                                                                    
    	public MetabTransformer(Map<String,String[]> map) {                                                           
    		this.map = map;                                                                                          
    	}                                                                                                            
                                                                                                                     
    	/**                                                                                                          
    	 * transform airport code to latlon string                                                                   
    	 */                                                                                                          
		public String[] transform(String m) { 
			return map.get(m);                                                                                    
		}                                                                                                            
    }                                                                                                                
                                                                                                                     
    static class PixelTransformer implements Transformer<String[],Point2D> {                                   
    	Dimension d;                                                                                                 
    	int startOffset;                                                                                             
    	                                                                                                             
    	public PixelTransformer(Dimension d) {                                                                 
    		this.d = d;                                                                                              
    	}                                                                                                            
    	/**                                                                                                          
    	 * transform a lat                                                                                           
    	 */                                                                                                          
    	public Point2D transform(String[] coord) { 
    		double xPos = 0;
			double yPos = 0;                                                                                     
			   
			String x = "0.0";
			String y = "0.0"; 
			if (coord != null) {
				x = coord[0]; 
				y = coord[1];  
			}
			//String x = coord[0];                                                                     
			//String y = coord[1];  
			
			xPos = Double.parseDouble(x);
			yPos = Double.parseDouble(y);
			                                                                                                  
			return new Point2D.Double(xPos,yPos);                                                           
		}                                                                                                                
    	                                                                                                             
    }                                                                                                                
       
    // based on http://www.coderanch.com/t/336616/GUI/java/Center-Align-text-drawString
    private void alignCenterString(Graphics g2d, String s, int width, int XPos, int YPos, int fontSize){  
    	g2d.setFont(new Font("Arial", Font.TYPE1_FONT, fontSize));
    	if (reactions.contains(s)) {
    		if (s.startsWith("<html>")) {
        		s = s.substring(6, s.indexOf("<p>"));
        	}
    		if (s.length() > PathwaysFrameConstants.REACTION_NODE_MAX_CHARS) {
        		s = s.substring(0, PathwaysFrameConstants.REACTION_NODE_MAX_CHARS - PathwaysFrameConstants.REACTION_NODE_ELLIPSIS_CORRECTION) + "...";
        	}
    	} else {
    		if (s.length() > PathwaysFrameConstants.METABOLITE_NODE_MAX_CHARS) {
        		s = s.substring(0, PathwaysFrameConstants.METABOLITE_NODE_MAX_CHARS - PathwaysFrameConstants.METABOLITE_NODE_ELLIPSIS_CORRECTION) + "...";
        	}
    	}
    	if (s.startsWith("R_") || s.startsWith("r_")) {
			s = s.substring(2);
		}
        int stringLen = (int)  
            g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
        int start = width/2 - stringLen/2;
        g2d.drawString(s, start + XPos, YPos);   
    }  
    
    private void drawBorder(Graphics g2d, int width, int height, int strokeWidth){
    	((Graphics2D) g2d).setStroke(new BasicStroke(strokeWidth));
    	g2d.drawLine(1, 1, width, 1);
    	g2d.drawLine(1, height - 1, width, height - 1);
    	g2d.drawLine(1, 1, 1, height);
    	g2d.drawLine(width - 1, 1, width - 1, height);
    }
    
    private final static class EdgeWeightStrokeFunction<E>
    implements Transformer<E,Stroke>
    {
    	//protected static final Stroke basic = new BasicStroke(0);
        //protected static final Stroke heavy = new BasicStroke(0);
        //protected static final Stroke dotted = RenderContext.DOTTED;
       
        //protected boolean weighted = false;
        protected Map<E,Number> edge_weight;
       
        public EdgeWeightStrokeFunction(Map<E,Number> edge_weight)
        {
            this.edge_weight = edge_weight;
        }
       
//        public void setWeighted(boolean weighted)
//        {
//            this.weighted = weighted;
//        }
       
        public Stroke transform(E e)
        {
        	int strokeWidth = 1;
            if (edge_weight.containsKey(e)) {
            	double value = edge_weight.get(e).doubleValue();
            	//System.out.println(value);
                if (value > 0.1) {
                	if (value == PathwaysFrameConstants.BORDER_THICKNESS) {
                		strokeWidth = (int) PathwaysFrameConstants.BORDER_THICKNESS;
                	} else {
                		strokeWidth = (int) value;
                	}
//                	if (value < 1.0) {
//                		strokeWidth = 1;
//                	} else if (value < 10) {
//                		strokeWidth = 2;
//                	} else if (value < 100) {
//                		strokeWidth = 3;
//                	} else if (value < 1000) {
//                		strokeWidth = 4;
//                	} else if (value < 10000) {
//                		strokeWidth = 5;
//                	}
                	return new BasicStroke(strokeWidth);
                } else {
                	return RenderContext.DOTTED;
                }  
            } else {
            	return RenderContext.DOTTED;
            }
        }
    }
    
    /**
     * Returns wedge arrows for undirected edges and notched arrows
     * for directed edges, of the specified dimensions.
     *
     * based on code from https://code.google.com/p/geoviz/source/browse/trunk/network/src/main/java/edu/uci/ics/jung/visualization/decorators/DirectionalEdgeArrowTransformer.java?r=774
     * by Joshua O'Madadhain
     */
    public class DirectionalEdgeArrowTransformer<V,E> implements Transformer<Context<Graph<V,E>,E>,Shape> {
        //protected Shape undirected_arrow;
        protected Shape directed_arrow;
       
        public DirectionalEdgeArrowTransformer(int length, int width, int notch_depth)
        {
        	directed_arrow = ArrowFactory.getNotchedArrow(width, length, notch_depth);
            //undirected_arrow = ArrowFactory.getWedgeArrow(width, length);
            // no arrow for undirected edge
            //undirected_arrow = ArrowFactory.getWedgeArrow(0, 0);
        }
       
        /**
         *
         */
        public Shape transform(Context<Graph<V,E>,E> context)
        {
        	return directed_arrow;
//        	System.out.println("c " + context.graph.getEdgeType(context.element));
//        	if (context.graph.getEdgeType(context.element) == EdgeType.DIRECTED) {
//                return directed_arrow;
//        	} else {
//        		System.out.println("c " + context.element.toString());
//        		return undirected_arrow;
//        	} 
        }

    }


    
    public void createNodeEditorDialog(Object arg0) {
    	final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("images/most16.jpg").getImage()); 
		icons.add(new ImageIcon("images/most32.jpg").getImage());

		if (getNodeEditor() != null) {
			getNodeEditor().dispose();
		}
		NodeEditorDialog frame = new NodeEditorDialog();
		setNodeEditor(frame);

		frame.setIconImages(icons);
		frame.setSize(350, 170);
		//frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.textField.setText(arg0.toString());
    }
    
    public double edgeThickness(double fluxValue) {
    	double thickness = PathwaysFrameConstants.DEFAULT_EDGE_WIDTH;
    	if (Math.abs(fluxValue) > PathwaysFrameConstants.INFINITE_FLUX_RATIO*LocalConfig.getInstance().getMaxUpperBound()) {
			//System.out.println("flux " + pn.getFluxValue());
			thickness = PathwaysFrameConstants.INFINITE_FLUX_WIDTH;
    	} else if (Math.abs(fluxValue) > 0) {
    		if (Math.abs(fluxValue) < PathwaysFrameConstants.MINIMUM_FLUX_RATIO*LocalConfig.getInstance().getSecondaryMaxFlux()) {
        		thickness = PathwaysFrameConstants.MINIMUM_FLUX_WIDTH;
    		} else if (Math.abs(fluxValue) < PathwaysFrameConstants.LOWER_MID_FLUX_RATIO*LocalConfig.getInstance().getSecondaryMaxFlux()) {
    			thickness = PathwaysFrameConstants.LOW_MID_FLUX_WIDTH;
    		} else if (Math.abs(fluxValue) < PathwaysFrameConstants.LOWER_MID_FLUX_RATIO*LocalConfig.getInstance().getSecondaryMaxFlux()) {
    			thickness = PathwaysFrameConstants.MID_FLUX_WIDTH;
    		} else if (Math.abs(fluxValue) < PathwaysFrameConstants.LOWER_MID_FLUX_RATIO*LocalConfig.getInstance().getSecondaryMaxFlux()) {
    			thickness = PathwaysFrameConstants.MID_FLUX_WIDTH;
    		} else if (Math.abs(fluxValue) < PathwaysFrameConstants.TOP_FLUX_RATIO*LocalConfig.getInstance().getSecondaryMaxFlux()) {
    			thickness = PathwaysFrameConstants.TOP_FLUX_WIDTH;
    		} else if (Math.abs(fluxValue) <= LocalConfig.getInstance().getSecondaryMaxFlux()) {
    			thickness = PathwaysFrameConstants.SECONDARY_MAX_FLUX_WIDTH;
    		}
    	}
    		
		return thickness;
    }
    
    /**
     * Updates x and/or y positions of external metabolite positions based on
     * top, bottom, left, or right location
     * @param externalMetaboliteNode
     * @param borderTopY
     * @param borderBottomY
     * @param borderLeftX
     * @param borderRightX
     * @param h
     * @param w
     */
    public void updateExternalMetabolitePosition(PathwayMetaboliteNode externalMetaboliteNode, 
    		String borderTopY, String borderBottomY, String borderLeftX, String borderRightX, int h, int w) {
    	if (((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("t")) {
    		externalMetaboliteNode.setyPosition(Double.parseDouble(borderTopY) - h);
    	} else if (((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("b")) {
    		externalMetaboliteNode.setyPosition(Double.parseDouble(borderBottomY) + h);
    	} else if (((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("l")) {
    		externalMetaboliteNode.setxPosition(Double.parseDouble(borderLeftX) - w);
    	} else if (((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("r")) {
    		externalMetaboliteNode.setxPosition(Double.parseDouble(borderRightX) + w);
    	}
    }
    
    public void updateExternalMetaboliteOffset(PathwayMetaboliteNode externalMetaboliteNode) {
    	if (((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("t") ||
    			((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("b")) {
    		externalMetaboliteNode.setxPosition(externalMetaboliteNode.getxPosition() + 
        			((ExternalMetaboliteNode) externalMetaboliteNode).getOffset()*PathwaysFrameConstants.OFFSET_WIDTH);
    	} else if (((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("l") ||
    			((ExternalMetaboliteNode) externalMetaboliteNode).getPosition().equals("r")) {
    		externalMetaboliteNode.setyPosition(externalMetaboliteNode.getyPosition() + 
                	((ExternalMetaboliteNode) externalMetaboliteNode).getOffset()*PathwaysFrameConstants.OFFSET_HEIGHT);
    	}
    		
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
    
    public static void main(String[] args) {                                                                         
        // create a frome to hold the graph                                                                          
//        final JFrame frame = new JFrame();                                                                           
//        Container content = frame.getContentPane();                                                                  
//        content.add(new PathwaysFrame());                                                                        
//        frame.pack();                                                                                                
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);                                                                                      
    }                                                                                                                
}  