package edu.rutgers.MOST.presentation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;                                                                                        
import java.awt.Color;                                                                                               
import java.awt.Container;                                                                                           
import java.awt.Dimension;                                                                                           
import java.awt.Font;
import java.awt.Graphics;                                                                                            
import java.awt.Graphics2D;                                                                                          
import java.awt.Stroke;
import java.awt.event.ActionEvent;                                                                                   
import java.awt.event.ActionListener;                                                                                
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
import javax.swing.JFrame;                                                                                           
import javax.swing.JPanel;                                                                                           
                                                                                                                     








import org.apache.commons.collections15.Transformer;                                                                 
import org.apache.commons.collections15.functors.ChainedTransformer;                                                 
                                                                                                                     
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.data.ECNumberMapCreator;
import edu.rutgers.MOST.data.MetabolicPathway;
import edu.rutgers.MOST.data.PathwayFilesReader;
import edu.rutgers.MOST.data.PathwayMetaboliteNode;
import edu.rutgers.MOST.data.PathwayReactionNode;
import edu.rutgers.MOST.data.ReactionFactory;
import edu.rutgers.MOST.data.SBMLReaction;
import edu.uci.ics.jung.algorithms.layout.Layout;                                                                    
import edu.uci.ics.jung.algorithms.layout.StaticLayout;                                                              
import edu.uci.ics.jung.graph.Graph;                                                                                 
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;                                                                         
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;                                                           
import edu.uci.ics.jung.visualization.Layer;                                                                         
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;                                                           
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;                                               
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;                                               
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;                                                
import edu.uci.ics.jung.visualization.control.ScalingControl;                                                        
import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;                                                   
import edu.uci.ics.jung.visualization.decorators.VertexIconShapeTransformer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;                           
                                                                                                                     
                                                                                                                     
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
                                                                                                                     
	Map<String, String[]> metabPosMap = new HashMap<String, String[]>();                                                       
   	List<String> metaboliteList; 
   	
   	// key = name of rxn, value = reactant, product, reversible
   	Map<String, String[]> reactionMap = new HashMap<String, String[]>(); 
   	List<String> reactionList;
   	
   	// lists used to distinguish node types
   	ArrayList<String> borderList = new ArrayList<String>();
   	ArrayList<String> pathwayNames = new ArrayList<String>();
   	ArrayList<String> metabolites = new ArrayList<String>();
   	ArrayList<String> reactions = new ArrayList<String>();
   	Map<String, Double> fluxMap = new HashMap<String, Double>(); 
   	
   	private float scalingFactor = (float) 1.2;
   	private int graphWidth = 10000;
   	private int graphHeight = 8000;
   	private int borderWidth = 100;
   	private int borderHeight = 100;
   	String borderLeftX = Integer.toString(borderWidth);
   	String borderRightX = Integer.toString(graphWidth - borderWidth);
   	String borderTopY = Integer.toString(borderHeight);
   	String borderBottomY = Integer.toString(graphHeight - borderHeight);
   	
   	private static double borderThickness = 4;
   	
   	private int horizontalIncrement = 250;
   	private int verticalIncrement = 125;
   	private int pathwayNameNodeWidth = 120;
   	private int pathwayNameNodeHeight = 40;
   	private int metaboliteNodeWidth = 75;
   	private int metaboliteNodeHeight = 25;
   	private int reactionNodeWidth = 100;
   	private int reactionNodeHeight = 25;
   	private int pathwayNameNodeFontSize = 16;
   	private int metaboliteNodeFontSize = 16;
   	private int reactionNodeFontSize = 16;
   	// positions to start text in node
   	private int pathwayNameNodeXPos = 0;
   	private int pathwayNameNodeYPos = 18;
   	private int metaboliteNodeXPos = 0;
   	private int metaboliteNodeYPos = 18;
   	private int reactionNodeXPos = 0;
   	private int reactionNodeYPos = 18;
   	
   	protected EdgeWeightStrokeFunction<Number> ewcs;
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
    public PathwaysFrame() {                                                                                     
        setLayout(new BorderLayout());  
		
		// draw cell border
		metabPosMap.put("1", new String[] {borderLeftX, borderTopY});                                                        
		metabPosMap.put("2", new String[] {borderRightX, borderTopY}); 
		metabPosMap.put("3", new String[] {borderRightX, borderBottomY});
		metabPosMap.put("4", new String[] {borderLeftX, borderBottomY});                                                         
		
		reactionMap.put("1", new String[] {"1", "2", "false"});
		reactionMap.put("2", new String[] {"2", "3", "false"});
		reactionMap.put("3", new String[] {"3", "4", "false"});
		reactionMap.put("4", new String[] {"4", "1", "false"});
		
		fluxMap.put("1", borderThickness);
		fluxMap.put("2", borderThickness);
		fluxMap.put("3", borderThickness);
		fluxMap.put("4", borderThickness);
		
		for (int b = 1; b < 5; b++) {
			borderList.add(Integer.toString(b));
		}
		
		ECNumberMapCreator ecMapCreator = new ECNumberMapCreator();
		ecMapCreator.createEcNumberReactionMap();
		
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readFiles();
		
		double startX = borderWidth + horizontalIncrement;
		double startY = graphHeight/2;
		for (int i = 0; i < LocalConfig.getInstance().getDrawOrder().size(); i++) {
			MetabolicPathway pathway = LocalConfig.getInstance().getMetabolicPathways().get(LocalConfig.getInstance().getDrawOrder().get(i));
			if (startPosMap.containsKey(pathway.getId())) {
				startX = startPosMap.get(pathway.getId()).get(0);
				startY = startPosMap.get(pathway.getId()).get(1);
			}
			if (LocalConfig.getInstance().getConnectionPositionMap().containsKey(pathway.getId())) {
//				System.out.println("map " + LocalConfig.getInstance().getConnectionPositionMap());
//				System.out.println("map " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()));
//				for (int p = 0; p < LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).size(); p++) {
//					System.out.println("reac " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0));
//					System.out.println("prod " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().get(0));
//				}
			}
			for (int j = 0; j < pathway.getMetabolitesData().size(); j++) {
				metabolites.add(pathway.getMetabolitesData().get(Integer.toString(j)).getNames().get(0));
				PathwayMetaboliteNode pn = new PathwayMetaboliteNode();
				pn.setDataId(pathway.getMetabolitesData().get(Integer.toString(j)).getId());
				double x = startX + horizontalIncrement*pathway.getMetabolitesData().get(Integer.toString(j)).getLevel();
				double y = startY + verticalIncrement*pathway.getMetabolitesData().get(Integer.toString(j)).getLevelPosition();
				pn.setxPosition(x);
				pn.setyPosition(y);
				pathway.getMetabolitesNodes().put(pn.getDataId(), pn);
				metabPosMap.put(pathway.getMetabolitesData().get(Integer.toString(j)).getNames().get(0), new String[] {Double.toString(x), Double.toString(y)});  
			}
			for (int k = 0; k < pathway.getReactionsData().size(); k++) {
				//System.out.println(pathway.getReactionsData().get(Integer.toString(k)).getEcNumbers());
//				for (int e = 0; e < pathway.getReactionsData().get(Integer.toString(k)).getEcNumbers().size(); e++) {
//					//System.out.println("get e " + pathway.getReactionsData().get(Integer.toString(k)).getEcNumbers().get(e));
//					if (LocalConfig.getInstance().getEcNumberReactionMap().containsKey(pathway.getReactionsData().get(Integer.toString(k)).getEcNumbers().get(e))) {
//						ArrayList<SBMLReaction> rxnsList = LocalConfig.getInstance().getEcNumberReactionMap().get(pathway.getReactionsData().get(Integer.toString(k)).getEcNumbers().get(e));
//						for (int f = 0; f < rxnsList.size(); f++) {
//							PathwayReactionNode pn = new PathwayReactionNode();
//							pn.setReactionName(rxnsList.get(f).getReactionName());
//							pn.setDataId(pathway.getReactionsData().get(Integer.toString(k)).getReactionId());
//							System.out.println(pn);
//							pathway.getReactionsNodes().put(pn.getDataId(), pn);
//						}
//					} else {
//						PathwayReactionNode pn = new PathwayReactionNode();
//						//System.out.println(pathway.getReactionsData().get(Integer.toString(k)).getName());
//						pn.setReactionName(pathway.getReactionsData().get(Integer.toString(k)).getName());
//						pn.setDataId(pathway.getReactionsData().get(Integer.toString(k)).getReactionId());
//						System.out.println(pn);
//						pathway.getReactionsNodes().put(pn.getDataId(), pn);
//					}
//				}
//				System.out.println(pathway);
				
				PathwayReactionNode pn = new PathwayReactionNode();
				//System.out.println(pathway.getReactionsData().get(Integer.toString(k)));
				reactions.add(pathway.getReactionsData().get(Integer.toString(k)).getName());
				double x = startX + horizontalIncrement*pathway.getReactionsData().get(Integer.toString(k)).getLevel();
				double y = startY + verticalIncrement*pathway.getReactionsData().get(Integer.toString(k)).getLevelPosition(); 
				metabPosMap.put(pathway.getReactionsData().get(Integer.toString(k)).getName(), new String[] {Double.toString(x), Double.toString(y)});  
				pn.setDataId(pathway.getReactionsData().get(Integer.toString(k)).getReactionId());
				pn.setxPosition(x);
				pn.setyPosition(y);
				pathway.getReactionsNodes().put(pn.getDataId(), pn);
//				System.out.println(pathway.getReactionsNodes());
//				System.out.println("x " + x);
//				System.out.println("y " + y);
				String reversible = "";
				if (pathway.getReactionsData().get(Integer.toString(k)).getReversible().equals("0")) {
					reversible = "false";
				} else if (pathway.getReactionsData().get(Integer.toString(k)).getReversible().equals("1")) {
					reversible = "true";
				}
				//System.out.println(pathway.getReactionsData().get(Integer.toString(k)).getReactantIds());
				for (int r = 0; r < pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().size(); r++) {
					//System.out.println(pathway.getMetabolites().get((pathway.getReactionsData().get(Integer.toString(k)).getMainReactants().get(r))).getNames().get(0));
					String reac = pathway.getMetabolitesData().get((pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().get(r))).getNames().get(0);
					//if (!reactionMap.containsKey(pathway.getReactionsData().get(Integer.toString(k)).getName())) {
						reactionMap.put(pathway.getReactionsData().get(Integer.toString(k)).getName() + "reactant " + Integer.toString(r), new String[] {pathway.getReactionsData().get(Integer.toString(k)).getName(), reac, reversible});
						fluxMap.put(pathway.getReactionsData().get(Integer.toString(k)).getName() + "reactant " + Integer.toString(r), 1.0);
					//} 
				}
				for (int p = 0; p < pathway.getReactionsData().get(Integer.toString(k)).getProductIds().size(); p++) {
					//System.out.println(pathway.getMetabolites().get((pathway.getReactionsData().get(Integer.toString(k)).getMainProducts().get(p))).getNames().get(0));
					String prod = pathway.getMetabolitesData().get((pathway.getReactionsData().get(Integer.toString(k)).getProductIds().get(p))).getNames().get(0);
					reactionMap.put(pathway.getReactionsData().get(Integer.toString(k)).getName() + "product " + Integer.toString(p), new String[] {pathway.getReactionsData().get(Integer.toString(k)).getName(), prod, "true"});
					fluxMap.put(pathway.getReactionsData().get(Integer.toString(k)).getName() + "product " + Integer.toString(p), 1.0);
				}
			}
			//System.out.println("map " + LocalConfig.getInstance().getConnectionPositionMap());
			//System.out.println("map " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()));
			if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()) != null) {
				System.out.println("pathway id " + pathway.getId());
//				System.out.println("reactant " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(0).getReactantPathwaysIds().get(0));
				if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(0).getReactantPathwaysIds().get(0).get(0).equals(pathway.getId())) {
					for (int p = 0; p < LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).size(); p++) {
						System.out.println("reac pathway id " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(0));
						System.out.println("reactant " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(1));
						System.out.println("x " + pathway.getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(1)).getxPosition());
						System.out.println("y " + pathway.getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(1)).getyPosition());
						System.out.println("d " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getDirection());
						System.out.println("l " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getLength());
						ArrayList<Double> xyList = new ArrayList<Double>();
						double newStartX = pathway.getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(1)).getxPosition();;
						double newStartY = pathway.getMetabolitesNodes().get(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(0).get(1)).getyPosition();
						if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getDirection().equals("vertical")) {
							newStartY += LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getLength() * verticalIncrement;	
						} else if (LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getDirection().equals("horizontal")) {
							newStartX += LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getLength() * horizontalIncrement;
						}
						xyList.add(newStartX);
						xyList.add(newStartY);
						startPosMap.put(LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().get(0).get(0), xyList);
						// purpose of these loops is to conpute average x and average y of reactants
						// and products to place reaction node
						for (int q = 0; q < LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().size(); q++) {
							System.out.println("reac pathway id " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(q).get(0));
							System.out.println("reactant " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getReactantPathwaysIds().get(q).get(1));
						}
						for (int r = 0; r < LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().size(); r++) {
							System.out.println("prod pathway id " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().get(r).get(0));
							System.out.println("product " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().get(r).get(1));
						}
						System.out.println("prod pathway id " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().get(0).get(0));
						System.out.println("product " + LocalConfig.getInstance().getConnectionPositionMap().get(pathway.getId()).get(p).getProductPathwaysIds().get(0).get(1));
//						System.out.println(startX);
//						System.out.println(startY);
					}
				}
			}
		}
		                                                                                                 		
//   		metabPosMap.put("M01", new String[] {"1000", "100"});                                                         
//   		
//   		// labels
//   		metabPosMap.put("R01", new String[] {"1000", "150"});
//   		
//   		metabPosMap.put("Pathway 1", new String[] {"850", "150"});  		

//   		fluxMap.put("R01", 0.0);
                                                                                                                     
   		metaboliteList = new ArrayList<String>(metabPosMap.keySet()); 
   		Collections.sort(metaboliteList);
   		//System.out.println("m " + metaboliteList);
   		
   		reactionList = new ArrayList<String>(reactionMap.keySet()); 
   		Collections.sort(reactionList);
   		//System.out.println(reactionList);
                                                                                                                     
        // create a simple graph for the demo                                                                        
        graph = new SparseMultigraph<String, Number>();  
        createVertices();                                                                                            
        createEdges();                                                                                               
                                                                                                                                                                                                                                                                                                                      
        Dimension layoutSize = new Dimension(graphWidth, graphHeight);                                                             
                                                                                                                     
        Layout<String,Number> layout = new StaticLayout<String,Number>(graph,                                        
        		new ChainedTransformer(new Transformer[]{                                                            
        				new MetabTransformer(metabPosMap),                                                                    
        				new PixelTransformer(new Dimension(graphWidth, graphHeight))                                         
        		}));                                                                                                 
        	                                                                                                         
        layout.setSize(layoutSize);                                                                                  
        vv =  new VisualizationViewer<String,Number>(layout,                                                         
        		new Dimension(1000,600));   
        
        vv.setBackground(Color.white);
        
        Map<String, Icon> iconMap = new HashMap<String, Icon>();                                                                                        
        for(int i = 0; i < metaboliteList.size(); i++) {                                                                                                        
        	String name = metaboliteList.get(i);
        	String abbr = LocalConfig.getInstance().getMetaboliteNameAbbrMap().get(name);
        	int width = (int) borderThickness;
    		int height = (int) borderThickness;
        	if (borderList.contains(name)) {
        		width = (int) borderThickness;
        		height = (int) borderThickness;
        	} else if (metabolites.contains(name)) {
        		width = metaboliteNodeWidth;
        		height = metaboliteNodeHeight;
        	} else if (reactions.contains(name)) {
        		width = reactionNodeWidth;
        		height = reactionNodeHeight;
        	}
        	// based on http://stackoverflow.com/questions/2736320/write-text-onto-image-in-java
        	BufferedImage bufferedImage = new BufferedImage(width, height,
        			BufferedImage.TYPE_INT_RGB);
        	Graphics graphics = bufferedImage.getGraphics();
        	if (borderList.contains(name)) {
        		graphics.setColor(Color.black);
        	} else if (metabolites.contains(name)) {
        		graphics.setColor(Color.lightGray);
        	} else if (reactions.contains(name)) {
        		graphics.setColor(Color.white);
        	}
        	graphics.fillRect(0, 0, width, height);
        	graphics.setColor(Color.BLACK);
        	if (pathwayNames.contains(name)) {
        		graphics.setFont(new Font("Arial", Font.BOLD, 20));
            	graphics.drawString(name, 5, 15);
        	} else {
        		if (metabolites.contains(name)) {
        			alignCenterString(graphics, abbr, width, metaboliteNodeXPos, metaboliteNodeYPos, metaboliteNodeFontSize);
        		} else if (reactions.contains(name)) {
        			alignCenterString(graphics, name, width, reactionNodeXPos, reactionNodeYPos, reactionNodeFontSize);
        		}
        	}
        	if (metabolites.contains(name) || reactions.contains(name)) {
        		drawBorder(graphics, width, height, 4);
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
        
        vv.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.gray));
        vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.black));
        //vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer(new BasicStroke(2.5f)));
        vv.getRenderContext().setEdgeStrokeTransformer(ewcs);
        
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
        vv.setGraphMouse(graphMouse);                                                                                
                                                                                                                 
        final ScalingControl scaler = new CrossoverScalingControl();                                                 
           
        // not sure what this does
        //vv.scaleToLayout(scaler);                                                                                                                                                                                              
                                                                                                                     
        JButton plus = new JButton("+");                                                                             
        plus.addActionListener(new ActionListener() {                                                                
            public void actionPerformed(ActionEvent e) {                                                             
                scaler.scale(vv, scalingFactor, vv.getCenter()); 
            }                                                                                                        
        });                                                                                                          
        JButton minus = new JButton("-");                                                                            
        minus.addActionListener(new ActionListener() {                                                               
            public void actionPerformed(ActionEvent e) {                                                             
                scaler.scale(vv, 1/scalingFactor, vv.getCenter());
            }                                                                                                        
        });                                                                                                          
                                                                                                                     
        JButton reset = new JButton("Reset");                                                                        
        reset.addActionListener(new ActionListener() {                                                               
                                                                                                                     
			public void actionPerformed(ActionEvent e) {                                                             
				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();       
				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setToIdentity();         
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
    		//System.out.println(info[0] + " " + info[2] + " " + info[1]);
    		String rev = info[2];
    		if (rev.equals("true")) {
    			graph.addEdge(new Double(i), info[0], info[1], EdgeType.DIRECTED); 
    		} else if (rev.equals("false")) {
    			graph.addEdge(new Double(i), info[0], info[1], EdgeType.UNDIRECTED); 
    		}
    		String rxnName = "";
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
			                                                                                    
			String x = coord[0];                                                                     
			String y = coord[1];  
			
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
    		if (s.length() > 11) {
        		s = s.substring(0, 8) + "...";
        	}
    	} else {
    		if (s.length() > 7) {
        		s = s.substring(0, 5) + "...";
        	}
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
                if (value > 0.1) {
                	if (value == borderThickness) {
                		strokeWidth = (int) (borderThickness);
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
                                                                                                                     

