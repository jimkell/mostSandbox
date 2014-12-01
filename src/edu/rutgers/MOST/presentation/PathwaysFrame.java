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
import edu.rutgers.MOST.data.MetabolicPathway;
import edu.rutgers.MOST.data.PathwayFilesReader;
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
   	private int reactionNodeWidth = 90;
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
		
		Map<String, String> ecNumMap = new HashMap<String, String>();
		ReactionFactory rf = new ReactionFactory("SBML");
		Vector<SBMLReaction> rxns = rf.getAllReactions();
		for (int r = 0; r < rxns.size(); r++) {
			SBMLReaction reaction = (SBMLReaction) rxns.get(r);
			String ecString = reaction.getEcNumber();
			int id = reaction.getId();
			String name = reaction.getReactionName();
			String subsystem = reaction.getSubsystem();
			if (ecString != null && ecString.length() > 0) {
				// model may contain more than one EC number, separated by white space
				// AraGEM model has this condition
				java.util.List<String> ecNumbers = Arrays.asList(ecString.split("\\s"));
				for (int i = 0; i < ecNumbers.size(); i++) {
					//System.out.println(ecNumbers.get(i) + " " + name);
					ecNumMap.put(ecNumbers.get(i), name);
				}
			}
		}
		System.out.println(ecNumMap);
		LocalConfig.getInstance().setEcNumMap(ecNumMap);
		
		PathwayFilesReader reader = new PathwayFilesReader();
		reader.readFiles();
		
		for (int i = 0; i < LocalConfig.getInstance().getDrawOrder().size(); i++) {
			MetabolicPathway pathway = LocalConfig.getInstance().getMetabolicPathways().get("1");
			for (int j = 0; j < pathway.getMetabolites().size(); j++) {
				metabolites.add(pathway.getMetabolites().get(Integer.toString(j)).getNames().get(0));
				int startX = borderWidth + horizontalIncrement;
				double x = startX + horizontalIncrement*pathway.getMetabolites().get(Integer.toString(j)).getLevel();
				int startY = graphHeight/2;
				double y = startY + verticalIncrement*pathway.getMetabolites().get(Integer.toString(j)).getLevelPosition(); 
				metabPosMap.put(pathway.getMetabolites().get(Integer.toString(j)).getNames().get(0), new String[] {Double.toString(x), Double.toString(y)});  
			}
			for (int k = 0; k < pathway.getReactions().size(); k++) {
				reactions.add(pathway.getReactions().get(Integer.toString(k)).getName());
				int startX = borderWidth + horizontalIncrement;
				double x = startX + horizontalIncrement*pathway.getReactions().get(Integer.toString(k)).getLevel();
				int startY = graphHeight/2;
				double y = startY + verticalIncrement*pathway.getReactions().get(Integer.toString(k)).getLevelPosition(); 
				metabPosMap.put(pathway.getReactions().get(Integer.toString(k)).getName(), new String[] {Double.toString(x), Double.toString(y)});  
				String reversible = "";
				if (pathway.getReactions().get(Integer.toString(k)).getReversible().equals("0")) {
					reversible = "false";
				} else if (pathway.getReactions().get(Integer.toString(k)).getReversible().equals("1")) {
					reversible = "true";
				}
				for (int r = 0; r < pathway.getReactions().get(Integer.toString(k)).getMainReactants().size(); r++) {
					//System.out.println(pathway.getMetabolites().get((pathway.getReactions().get(Integer.toString(k)).getMainReactants().get(r))).getNames().get(0));
					String reac = pathway.getMetabolites().get((pathway.getReactions().get(Integer.toString(k)).getMainReactants().get(r))).getNames().get(0);
					reactionMap.put(pathway.getReactions().get(Integer.toString(k)).getName() + "R " + Integer.toString(r), new String[] {pathway.getReactions().get(Integer.toString(k)).getName(), reac, reversible});
					fluxMap.put(pathway.getReactions().get(Integer.toString(k)).getName() + "R " + Integer.toString(r), 1.0);
				}
				for (int p = 0; p < pathway.getReactions().get(Integer.toString(k)).getMainProducts().size(); p++) {
					//System.out.println(pathway.getMetabolites().get((pathway.getReactions().get(Integer.toString(k)).getMainProducts().get(p))).getNames().get(0));
					String prod = pathway.getMetabolites().get((pathway.getReactions().get(Integer.toString(k)).getMainProducts().get(p))).getNames().get(0);
					reactionMap.put(pathway.getReactions().get(Integer.toString(k)).getName() + "P " + Integer.toString(p), new String[] {pathway.getReactions().get(Integer.toString(k)).getName(), prod, "true"});
					fluxMap.put(pathway.getReactions().get(Integer.toString(k)).getName() + "P " + Integer.toString(p), 1.0);
				}
			}
		}
		                                                                                                 		
//   		metabPosMap.put("M01", new String[] {"1000", "100"});                                                        
//   		metabPosMap.put("M02", new String[] {"1000", "200"});                                                        
//   		metabPosMap.put("M03", new String[] {"900", "300"});                                                         
//   		metabPosMap.put("M04", new String[] {"1100", "300"});                                                         
//   		metabPosMap.put("M05", new String[] {"1000", "400"});                                                         
//   		metabPosMap.put("M06", new String[] {"1000", "500"});                                                         
//   		metabPosMap.put("M07", new String[] {"1000", "600"});                                                          
//   		metabPosMap.put("M08", new String[] {"1000", "700"});                                                          
//   		metabPosMap.put("M09", new String[] {"1000", "800"});  
//   		metabPosMap.put("M10", new String[] {"1000", "900"});                                                         
//   		metabPosMap.put("M11", new String[] {"1000", "1000"});                                                         
//   		metabPosMap.put("M12", new String[] {"1000", "1100"});                                                          
//   		metabPosMap.put("M13", new String[] {"1000", "1200"});                                                          
//   		metabPosMap.put("M14", new String[] {"1000", "1300"}); 
//   		
//   		// labels
//   		metabPosMap.put("R01", new String[] {"1000", "150"});
//   		metabPosMap.put("R02", new String[] {"950", "250"});
//   		metabPosMap.put("R03", new String[] {"1050", "250"});
//   		metabPosMap.put("R04", new String[] {"950", "350"});
//   		metabPosMap.put("R05", new String[] {"1050", "350"});
//   		metabPosMap.put("R06", new String[] {"1000", "450"});
//   		metabPosMap.put("R07", new String[] {"1000", "550"});
//   		metabPosMap.put("R08", new String[] {"1000", "650"});
//   		metabPosMap.put("R09", new String[] {"1000", "750"});
//   		metabPosMap.put("R10", new String[] {"1000", "850"});
//   		metabPosMap.put("R11", new String[] {"1000", "950"});
//   		metabPosMap.put("R12", new String[] {"1000", "1050"});
//   		metabPosMap.put("R13", new String[] {"1000", "1150"});
//   		metabPosMap.put("R14", new String[] {"1000", "1250"});
//   		
//   		metabPosMap.put("Pathway 1", new String[] {"850", "150"});
//   		
//   		metabPosMap.put("M15", new String[] {"320", "500"});                                                         
//   		metabPosMap.put("M16", new String[] {"500", "500"});                                                          
//   		metabPosMap.put("M17", new String[] {"570", "600"});                                                          
//   		metabPosMap.put("M18", new String[] {"570", "700"});  
//   		metabPosMap.put("M19", new String[] {"500", "800"});                                                         
//   		metabPosMap.put("M20", new String[] {"320", "800"});
//   		metabPosMap.put("M21", new String[] {"250", "700"});                                                         
//   		metabPosMap.put("M22", new String[] {"250", "600"});
//   		
//   		// labels
//   		metabPosMap.put("R15", new String[] {"535", "550"});
//   		metabPosMap.put("R16", new String[] {"570", "650"});
//   		metabPosMap.put("R17", new String[] {"535", "750"});
//   		metabPosMap.put("R18", new String[] {"410", "800"});
//   		metabPosMap.put("R19", new String[] {"285", "750"});
//   		metabPosMap.put("R20", new String[] {"250", "650"});
//   		metabPosMap.put("R21", new String[] {"285", "550"});
//   		metabPosMap.put("R22", new String[] {"410", "500"});
//   		
//   		metabPosMap.put("Pathway 2", new String[] {"220", "450"});
//   		
//   		reactionMap.put("R01", new String[] {"M01", "M02", "false"});
//   		reactionMap.put("R02", new String[] {"M02", "M03", "false"});
//   		reactionMap.put("R03", new String[] {"M02", "M04", "true"});
//   		reactionMap.put("R04", new String[] {"M03", "M05", "false"});
//   		reactionMap.put("R05", new String[] {"M04", "M05", "false"});
//   		reactionMap.put("R06", new String[] {"M05", "M06", "false"});
//   		reactionMap.put("R07", new String[] {"M06", "M07", "false"});
//   		reactionMap.put("R08", new String[] {"M07", "M08", "true"});
//   		reactionMap.put("R09", new String[] {"M08", "M09", "false"});
//   		reactionMap.put("R10", new String[] {"M09", "M10", "true"});
//   		reactionMap.put("R11", new String[] {"M10", "M11", "false"});
//   		reactionMap.put("R12", new String[] {"M11", "M12", "false"});
//   		reactionMap.put("R13", new String[] {"M12", "M13", "true"});
//   		reactionMap.put("R14", new String[] {"M13", "M14", "false"});
//   		
//   		reactionMap.put("R15", new String[] {"M15", "M16", "false"});
//   		reactionMap.put("R16", new String[] {"M16", "M17", "false"});
//   		reactionMap.put("R17", new String[] {"M17", "M18", "true"});
//   		reactionMap.put("R18", new String[] {"M18", "M19", "false"});
//   		reactionMap.put("R19", new String[] {"M19", "M20", "false"});
//   		reactionMap.put("R20", new String[] {"M20", "M21", "false"});
//   		reactionMap.put("R21", new String[] {"M21", "M22", "false"});
//   		reactionMap.put("R22", new String[] {"M22", "M15", "true"});
//   		
//   		fluxMap.put("R01", 0.0);
//   		fluxMap.put("R02", 999999.0);
//   		fluxMap.put("R03", 999999.0);
//   		fluxMap.put("R04", 18.0);
//   		fluxMap.put("R05", 0.4);
//   		fluxMap.put("R06", 1.0);
//   		fluxMap.put("R07", 500.0);
//   		fluxMap.put("R08", 20.0);
//   		fluxMap.put("R09", 150.0);
//   		fluxMap.put("R10", 0.01);
//   		fluxMap.put("R11", 1000.0);
//   		fluxMap.put("R12", 999999.0);
//   		fluxMap.put("R13", 50.0);
//   		fluxMap.put("R14", 0.2);
//   		
//   		fluxMap.put("R15", 999999.0);
//   		fluxMap.put("R16", 0.2);
//   		fluxMap.put("R17", 0.3);
//   		fluxMap.put("R18", 0.0);
//   		fluxMap.put("R19", 0.5);
//   		fluxMap.put("R20", 60.0);
//   		fluxMap.put("R21", 7000.0);
//   		fluxMap.put("R22", 999999.0);
                                                                                                                     
   		metaboliteList = new ArrayList<String>(metabPosMap.keySet()); 
   		Collections.sort(metaboliteList);
   		System.out.println("m " + metaboliteList);
   		
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
        		if (abbr != null) {
        			alignCenterString(graphics, abbr, width, metaboliteNodeXPos, metaboliteNodeYPos, metaboliteNodeFontSize);
        		} else {
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
    	if (metabolites.contains(s)) {
    		if (s.length() > 7) {
        		s = s.substring(0, 5) + "...";
        	}
    	} else if (reactions.contains(s)) {
    		if (s.length() > 12) {
        		s = s.substring(0, 9) + "...";
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
                                                                                                                     

