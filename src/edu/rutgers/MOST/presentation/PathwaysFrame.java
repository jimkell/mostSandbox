package edu.rutgers.MOST.presentation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;                                                                                        
import java.awt.Color;                                                                                               
import java.awt.Dimension;                                                                                           
import java.awt.Font;
import java.awt.Graphics;                                                                                            
import java.awt.Graphics2D;                                                                                          
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;                                                                                   
import java.awt.event.ActionListener;                                                                                
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;                                                                                
import java.awt.geom.Point2D;                                                                                        
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;                                                                                          
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;                                                                                            
import java.util.Map;                                                                                                
                                                                                                                     


import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;                                                                                        
import javax.swing.JApplet;                                                                                          
import javax.swing.JButton;                                                                                          
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;                                                                                           
                                                                                                                     
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.apache.commons.collections15.Transformer;                                                                 
import org.apache.commons.collections15.functors.ChainedTransformer;                                                 
                                                                                                                     






import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.data.BorderRectangle;
import edu.rutgers.MOST.data.MetabolicPathway;
import edu.rutgers.MOST.data.PathwayMetaboliteNodeFactory;
import edu.rutgers.MOST.data.PathwayReactionNodeFactory;
import edu.rutgers.MOST.data.PathwaysCSVFileConstants;
import edu.rutgers.MOST.data.SVGBuilder;
import edu.rutgers.MOST.data.SVGEdge;
import edu.rutgers.MOST.data.SVGText;
import edu.rutgers.MOST.data.SVGWriter;
import edu.rutgers.MOST.data.VisualizationData;
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
import edu.uci.ics.jung.visualization.decorators.VertexIconShapeTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.util.ArrowFactory;
                                                                                                                     
                                                                                                                     
/**                                                                                                                  
 * Graph created by plotting vertices.                                                                      
 * Scaling of the graph also scales the image background.                                                            
 * based on code by Tom Nelson                                                                                                
 *                                                                                                                   
 */                                                                                                                  
@SuppressWarnings("serial")                                                                                          
public class PathwaysFrame extends JApplet {                                                                                                                                                                                 
    Graph<String, Number> graph; 
    public JPanel controls = new JPanel();
    
    private static VisualizationsFindDialog visualizationsFindDialog;                                                                                                              
	
	public static VisualizationsFindDialog getVisualizationsFindDialog() {
		return visualizationsFindDialog;
	}

	public static void setVisualizationsFindDialog(
			VisualizationsFindDialog visualizationsFindDialog) {
		PathwaysFrame.visualizationsFindDialog = visualizationsFindDialog;
	}

	private static HashMap<String, ArrayList<Double>> findLocationsMap;

	public static HashMap<String, ArrayList<Double>> getFindLocationsMap() {
		return findLocationsMap;
	}

	public static void setFindLocationsMap(
			HashMap<String, ArrayList<Double>> findLocationsMap) {
		PathwaysFrame.findLocationsMap = findLocationsMap;
	}

	/**                                                                                                              
     * the visual component and renderer for the graph                                                               
     */                                                                                                              
	VisualizationViewer<String, Number> vv;  

	//public final JMenuItem visualizationOptionsItem = new JMenuItem(VisualizationOptionsConstants.VISUALIZATION_OPTIONS_MENU_ITEM_NAME);

	// map with node names and positions
	Map<String, String[]> nodeNamePositionMap = new HashMap<String, String[]>();
	// keyset of node names
	ArrayList<String> nodeNameList = new ArrayList<String>(); 

	// key = name of rxn, value = reactant, product, reversible
	Map<String, String[]> reactionMap = new HashMap<String, String[]>(); 
	// keyset of reactions
	ArrayList<String> reactionList = new ArrayList<String>();

	// lists used to distinguish node types
	ArrayList<String> borderList = new ArrayList<String>();   // compartment border
	ArrayList<String> noBorderList = new ArrayList<String>();   // metabolite node border
	ArrayList<String> pathwayNames = new ArrayList<String>();
	ArrayList<String> mainMetabolites = new ArrayList<String>();
	ArrayList<String> smallMainMetabolites = new ArrayList<String>();
	ArrayList<String> sideMetabolites = new ArrayList<String>();
	ArrayList<String> cofactors = new ArrayList<String>();
	ArrayList<String> reactions = new ArrayList<String>();
	Map<String, Double> fluxMap = new HashMap<String, Double>(); 
	Map<String, Double> colorMap = new HashMap<String, Double>();
	ArrayList<String> koReactions = new ArrayList<String>();
	ArrayList<String> foundMetabolitesList = new ArrayList<String>();
	ArrayList<String> foundReactionsList = new ArrayList<String>();
	ArrayList<String> foundPathwayNamesList = new ArrayList<String>();
	Map<String, Icon> iconMap = new HashMap<String, Icon>(); 
	ArrayList<Integer> plottedIds = new ArrayList<Integer>();
	Map<String, String> oldNameNewNameMap = new HashMap<String, String>(); 

	// maps for find - exact match
	HashMap<String, ArrayList<String[]>> metaboliteAbbrPositionsMap = new HashMap<String, ArrayList<String[]>>();
	HashMap<String, ArrayList<String[]>> keggMetaboliteIdPositionsMap = new HashMap<String, ArrayList<String[]>>();
	HashMap<String, ArrayList<String[]>> ecNumberPositionsMap = new HashMap<String, ArrayList<String[]>>();
	HashMap<String, ArrayList<String[]>> keggReactionIdPositionsMap = new HashMap<String, ArrayList<String[]>>();
	HashMap<String, ArrayList<String[]>> reactionAbbrPositionsMap = new HashMap<String, ArrayList<String[]>>();

	PathwayReactionNodeFactory prnf = new PathwayReactionNodeFactory();
	PathwayMetaboliteNodeFactory pmnf = new PathwayMetaboliteNodeFactory();
	Utilities util = new Utilities();

	String compartmentLabel = "Model Name: " + LocalConfig.getInstance().getModelName();

	//   	private double layoutScale;
	private double viewScale = PathwaysFrameConstants.START_SCALING_FACTOR;

	private final JMenuItem saveGraphSVGItem = new JMenuItem("Save Graph As SVG");
	private final JMenuItem saveWindowPNGItem = new JMenuItem("Save Window As PNG");
	private final JCheckBoxMenuItem transformItem = new JCheckBoxMenuItem("Transform");

	private final JMenuItem findItem = new JMenuItem("Find");

	private NodeInformationDialog nodeInformationDialog;

	public NodeInformationDialog getNodeInformationDialog() {
		return nodeInformationDialog;
	}

	public void setNodeInformationDialog(NodeInformationDialog nodeInformationDialog) {
		this.nodeInformationDialog = nodeInformationDialog;
	}

	protected EdgeWeightStrokeFunction<Number> ewcs;
	@SuppressWarnings("rawtypes")
	protected DirectionalEdgeArrowTransformer arrowTransformer;
	protected Map<Number, Number> edge_weight = new HashMap<Number, Number>();
	protected Map<Number, Number> edge_color = new HashMap<Number, Number>();

	private ArrayList<MetabolicPathway> pathwaysList;

	public ArrayList<MetabolicPathway> getPathwaysList() {
		return pathwaysList;
	}

	public void setPathwaysList(ArrayList<MetabolicPathway> pathwaysList) {
		this.pathwaysList = pathwaysList;
	}

//	private Map<String, ArrayList<Double>> startPosMap = new HashMap<String, ArrayList<Double>>();

	// find-replace values 
	private boolean findMode;
	//	private boolean findButtonClicked;
	private boolean matchCase;
	private boolean wrapAround;
	private boolean searchBackwards;
	private boolean exactMatch;
	//	private boolean findFieldChanged;
	private boolean notFoundShown = false;

	private String oldFindValue = "";
	private int findStartIndex = 0;

	final ScalingControl scaler = new CrossoverScalingControl();
	
	public String report;

	/**                                                                                                              
	 * create an instance of a simple graph with controls to                                                         
	 * demo the zoom features.                                                                                       
	 *                                                                                                               
	 */                                                                                                              
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PathwaysFrame(int component) { 
		setLayout(new BorderLayout());
		//final ScalingControl scaler = new CrossoverScalingControl();

		// get visualization data from processor
		VisualizationData visualizationData = LocalConfig.getInstance().getVisualizationData();
		nodeNamePositionMap = visualizationData.getNodeNamePositionMap();
		nodeNameList = visualizationData.getNodeNameList();
		reactionMap = visualizationData.getReactionMap();
		reactionList = visualizationData.getReactionList();
		borderList = visualizationData.getBorderList();
		noBorderList = visualizationData.getNoBorderList();
		pathwayNames = visualizationData.getPathwayNames();
		mainMetabolites = visualizationData.getMainMetabolites();
		smallMainMetabolites = visualizationData.getSmallMainMetabolites();
		sideMetabolites = visualizationData.getSideMetabolites();
		cofactors = visualizationData.getCofactors();
		reactions = visualizationData.getReactions();
		fluxMap = visualizationData.getFluxMap();
		colorMap = visualizationData.getColorMap();
		koReactions = visualizationData.getKoReactions();
		foundMetabolitesList = visualizationData.getFoundMetabolitesList();
		foundReactionsList = visualizationData.getFoundReactionsList();
		foundPathwayNamesList = visualizationData.getFoundPathwayNamesList();
		iconMap = visualizationData.getIconMap();
		plottedIds = visualizationData.getPlottedIds();
		oldNameNewNameMap = visualizationData.getOldNameNewNameMap();
		metaboliteAbbrPositionsMap = visualizationData.getMetaboliteAbbrPositionsMap();
		keggMetaboliteIdPositionsMap = visualizationData.getKeggMetaboliteIdPositionsMap();
		ecNumberPositionsMap = visualizationData.getEcNumberPositionsMap();
		keggReactionIdPositionsMap = visualizationData.getKeggReactionIdPositionsMap();
		reactionAbbrPositionsMap = visualizationData.getReactionAbbrPositionsMap();
		report = visualizationData.getReport();
		
		transformItem.setState(false);
		
		//report = "";

		// register actions
		ActionListener findActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				showFindDialog();							
			}
		};

		VisualizationsFindDialog.findButton.addActionListener(findNextButtonActionListener);
		VisualizationsFindDialog.doneButton.addActionListener(findDoneButtonActionListener);
		VisualizationsFindDialog.caseCheckBox.addActionListener(matchCaseActionListener);
		VisualizationsFindDialog.wrapCheckBox.addActionListener(wrapAroundActionListener);
		VisualizationsFindDialog.backwardsCheckBox.addActionListener(searchBackwardsActionListener);
		VisualizationsFindDialog.exactMatchCheckBox.addActionListener(exactMatchActionListener);

		KeyStroke find = KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK,false);
		getRootPane().registerKeyboardAction(findActionListener,find,JComponent.WHEN_IN_FOCUSED_WINDOW); 

		/**************************************************************************/
		// create menu bar
		/**************************************************************************/

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		fileMenu.add(saveGraphSVGItem);
		saveGraphSVGItem.setMnemonic(KeyEvent.VK_S);

		saveGraphSVGItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				saveGraphAsSVG();
			}
		});

		fileMenu.add(saveWindowPNGItem);
		saveWindowPNGItem.setMnemonic(KeyEvent.VK_W);

		saveWindowPNGItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				saveAsPNG();
			}
		});

		fileMenu.add(saveWindowPNGItem);

		menuBar.add(fileMenu);

		JMenu editMenu = new JMenu("Edit");
		fileMenu.setMnemonic(KeyEvent.VK_E);

		editMenu.add(findItem);
		findItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!findMode) {
					showFindDialog();
				}	
			}
		});

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

		//processData(component);

		// create graph
		graph = new SparseMultigraph<String, Number>();
		createVertices();
		createEdges(); 
		LocalConfig.getInstance().setVisualizationsProgress(100);

		Dimension layoutSize = new Dimension(PathwaysFrameConstants.GRAPH_WIDTH, PathwaysFrameConstants.GRAPH_HEIGHT);                                                             

		Layout<String,Number> layout = new StaticLayout<String,Number>(graph,                                        
				new ChainedTransformer(new Transformer[]{                                                            
						new MetabTransformer(nodeNamePositionMap),                                                                    
						new PixelTransformer(new Dimension(PathwaysFrameConstants.GRAPH_WIDTH, PathwaysFrameConstants.GRAPH_HEIGHT))                                         
				}));                                                                                                 

		layout.setSize(layoutSize);   
		vv =  new VisualizationViewer<String,Number>(layout,                                                         
				new Dimension(PathwaysFrameConstants.GRAPH_WINDOW_WIDTH, 
						PathwaysFrameConstants.GRAPH_WINDOW_HEIGHT));   

		//final ScalingControl scaler = new CrossoverScalingControl();

		Point2D.Float p = new Point2D.Float(0.f, 0.f);
		scaler.scale(vv, PathwaysFrameConstants.START_SCALING_FACTOR, p);
		//scaler.scale(vv, PathwaysFrameConstants.START_SCALING_FACTOR, vv.getCenter());

		vv.setBackground(Color.white);

		// based on code from http://stackoverflow.com/questions/21657249/mouse-events-on-vertex-of-jung-graph
		vv.addGraphMouseListener(new GraphMouseListener() {

			@Override
			public void graphClicked(final Object arg0, MouseEvent me) {
				// TODO Auto-generated method stub
				if (me.getButton() == MouseEvent.BUTTON3) {
					final VisualizationViewer<String,String> vv =(VisualizationViewer<String,String>)me.getSource();
					//			        final Point2D p = me.getPoint();
					JPopupMenu popup = new JPopupMenu();
					JMenuItem nodeInformationMenu = new JMenuItem("View Node Information");
					nodeInformationMenu.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							createNodeInformationDialog(arg0);
						}
					});
					popup.add(nodeInformationMenu);
					popup.show(vv, me.getX(), me.getY());
				}
				if (me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2) {
					createNodeInformationDialog(arg0);
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

		final VertexIconShapeTransformer<String> vertexImageShapeFunction =                                                                           
				new VertexIconShapeTransformer<String>(new EllipseVertexShapeTransformer<String>());                                                      

		final DefaultVertexIconTransformer<String> vertexIconFunction =                                                                               
				new DefaultVertexIconTransformer<String>(); 

		createIconMap();
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
				g2d.setTransform(oldXform);                                                                      
			}                                                                                                    
			public boolean useTransform() { return false; }                                                      
		});  

		ewcs = new EdgeWeightStrokeFunction<Number>(edge_weight);
		arrowTransformer = new DirectionalEdgeArrowTransformer();

		// vary colors
		vv.getRenderContext().setEdgeDrawPaintTransformer(colorTransformer);

		// vary edge thicknesses
		vv.getRenderContext().setEdgeStrokeTransformer(ewcs);

		// all arrows same
		vv.getRenderContext().setEdgeArrowTransformer(arrowTransformer);
		vv.getRenderContext().setArrowFillPaintTransformer(colorTransformer);
		vv.getRenderContext().setArrowDrawPaintTransformer(colorTransformer);

		// Tooltips can be set programmatically
		// based on http://stackoverflow.com/questions/31940238/settooltip-in-jung-for-several-vertices
		vv.setVertexToolTipTransformer(new Transformer<String,String>() {                                              
			public String transform(String v) {
				return nodeName(v);                                                    
			}});                                
		// no tooltips on edges
		vv.setEdgeToolTipTransformer(new Transformer<Number,String>() {                                              
			public String transform(Number edge) {
				return "";
				//return "E"+graph.getEndpoints(edge).toString();                                                      
			}});                                                                                                     

		vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<String,Number>());

		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		add(panel);
		final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse(); 
		graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(graphMouse); 

		//final ScalingControl scaler = new CrossoverScalingControl();                                                 

		// not sure what this does
		//vv.scaleToLayout(scaler);                                                                                                                                                                                              

		JButton plus = new JButton("+");                                                                             
		plus.addActionListener(new ActionListener() {                                                                
			public void actionPerformed(ActionEvent e) { 
				scaler.scale(vv, PathwaysFrameConstants.SCALING_FACTOR, vv.getCenter());
				//                System.out.println("layout scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale());
				//				System.out.println("view scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale());
				//                layoutScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale();
				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
			}                                                                                                        
		});                                                                                                          
		JButton minus = new JButton("-");                                                                            
		minus.addActionListener(new ActionListener() {                                                               
			public void actionPerformed(ActionEvent e) {                                                             
				scaler.scale(vv, 1/PathwaysFrameConstants.SCALING_FACTOR, vv.getCenter());
				//                System.out.println("layout scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale());
				//				System.out.println("view scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale());
				//                layoutScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale();
				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
			}                                                                                                        
		});                                                                                                          

		controls.add(plus);                                                                                          
		controls.add(minus);                                                                                       
		add(controls, BorderLayout.SOUTH);  
		
	}  

	

	public void createGraph() {

	}

	/**                                                                                                              
	 * create some vertices                                                                                          
	 * @param count how many to create                                                                               
	 * @return the Vertices in an array                                                                              
	 */                                                                                                              
	public void createVertices() {                                                                                  
		for (String met : nodeNamePositionMap.keySet()) {
			graph.addVertex(met); 
		} 
	} 

	public void removeVertices() {                                                                                  
		for (String met : nodeNamePositionMap.keySet()) {
			graph.removeVertex(met); 
		} 
	} 

	/**                                                                                                              
	 * create edges for this demo graph                                                                              
	 * @param v an array of Vertices to connect                                                                      
	 */                                                                                                              
	public void createEdges() { 
		for(int i=0; i<reactionList.size(); i++) {
			String[] info = reactionMap.get(reactionList.get(i));
			String rev = info[2];
			if (rev.equals("true")) {
				graph.addEdge(new Double(i), info[0], info[1], EdgeType.DIRECTED); 
			} else if (rev.equals("false")) {
				graph.addEdge(new Double(i), info[0], info[1], EdgeType.UNDIRECTED); 
			}
			//String rxnName = "";
			if (fluxMap.containsKey(reactionList.get(i))) {
				double fluxValue = fluxMap.get(reactionList.get(i));
				edge_weight.put(new Double(i), fluxValue);
			} else {
				edge_weight.put(new Double(i), PathwaysFrameConstants.DEFAULT_EDGE_WIDTH);
			}
			if (colorMap.containsKey(reactionList.get(i))) {
				double color = colorMap.get(reactionList.get(i));
				edge_color.put(new Double(i), color);
			} else {
				edge_color.put(new Double(i), PathwaysFrameConstants.DEFAULT_COLOR_VALUE);
			}
		} 
	} 

	public void removeEdges() {
		for(int i=0; i<reactionList.size(); i++) {
			graph.removeEdge(new Double(i)); 
			fluxMap.clear();
			edge_weight.clear();
			edge_color.clear();
		}
	}

	/**
	 * Creates image icons to be used as nodes
	 * @return
	 */
	public void createIconMap() {
		//Map<String, Icon> iconMap = new HashMap<String, Icon>();   
		iconMap.clear();
		nodeNameList = new ArrayList<String>(nodeNamePositionMap.keySet()); 
		//   		Collections.sort(nodeNameList);
		for (int i = 0; i < nodeNameList.size(); i++) {                                                                                                        
			String name = nodeNameList.get(i);
			String abbr = LocalConfig.getInstance().getMetaboliteNameAbbrMap().get(name);
			int width = (int) PathwaysFrameConstants.BORDER_THICKNESS;
			int height = (int) PathwaysFrameConstants.BORDER_THICKNESS;
			if (borderList.contains(name)) {
				width = (int) PathwaysFrameConstants.BORDER_THICKNESS;
				height = (int) PathwaysFrameConstants.BORDER_THICKNESS;
			} else if (name.equals(compartmentLabel)) {
				width = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_WIDTH;
				height = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_HEIGHT;
			} else if (mainMetabolites.contains(name)) {
				if (!noBorderList.contains(name)) {
					width = PathwaysFrameConstants.METABOLITE_BORDER_NODE_WIDTH;
					height = PathwaysFrameConstants.METABOLITE_BORDER_NODE_HEIGHT;
				} else {
					width = PathwaysFrameConstants.METABOLITE_NO_BORDER_NODE_WIDTH;
					height = PathwaysFrameConstants.METABOLITE_NO_BORDER_NODE_HEIGHT;
				}
			} else if (smallMainMetabolites.contains(name)) {	
				width = PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_WIDTH;
				height = PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_HEIGHT;
			} else if (sideMetabolites.contains(name)) {	
				width = PathwaysFrameConstants.SIDE_METABOLITE_NODE_WIDTH;
				height = PathwaysFrameConstants.SIDE_METABOLITE_NODE_HEIGHT;	
			} else if (reactions.contains(name)) {
				width = PathwaysFrameConstants.REACTION_NODE_WIDTH;
				height = PathwaysFrameConstants.REACTION_NODE_HEIGHT;
			} else if (pathwayNames.contains(name)) {
				width = PathwaysFrameConstants.PATHWAY_NAME_NODE_WIDTH;
				height = PathwaysFrameConstants.PATHWAY_NAME_NODE_HEIGHT; 
			}
			// based on http://stackoverflow.com/questions/2736320/write-text-onto-image-in-java
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.setColor(PathwaysFrameConstants.NODE_BACKGROUND_DETAULT_COLOR);
			if (borderList.contains(name)) {
				graphics.setColor(Color.black);
			}
			graphics.fillRect(0, 0, width, height);
			graphics.setColor(Color.BLACK);
			if (pathwayNames.contains(name)) {
				if (foundPathwayNamesList.contains(name)) {
					graphics.setColor(PathwaysFrameConstants.PATHWAY_NAME_COLOR);
				} else {
					graphics.setColor(PathwaysFrameConstants.PATHWAY_NAME_NOT_FOUND_COLOR);
				}
				alignCenterString(graphics, name, width, PathwaysFrameConstants.PATHWAY_NAME_NODE_XPOS, PathwaysFrameConstants.PATHWAY_NAME_NODE_YPOS, PathwaysFrameConstants.PATHWAY_NAME_NODE_FONT_SIZE);
				drawBorder(graphics, width, height, PathwaysFrameConstants.PATHWAY_NAME_BORDER_WIDTH);
			} else if (name.equals(compartmentLabel)) {
				graphics.setFont(new Font(PathwaysFrameConstants.FONT_NAME, PathwaysFrameConstants.FONT_STYLE, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_FONT_SIZE));
				graphics.drawString(compartmentLabel, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS);
				String comp = maybeAddCompartmentNameSuffix(LocalConfig.getInstance().getSelectedCompartmentName());
				graphics.drawString("Compartment: " + comp, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS, 
						PathwaysFrameConstants.COMPARTMENT_LABEL_LINE_OFFSET + PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS);
			} else {
				if (mainMetabolites.contains(name)) {
					if (!foundMetabolitesList.contains(name) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
						graphics.setColor(PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR);
					}
					alignCenterString(graphics, abbr, width, PathwaysFrameConstants.METABOLITE_NODE_XPOS, PathwaysFrameConstants.METABOLITE_NODE_YPOS, PathwaysFrameConstants.METABOLITE_NODE_FONT_SIZE);
				} else if (smallMainMetabolites.contains(name)) {
					if (!foundMetabolitesList.contains(name) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
						graphics.setColor(PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR);
					}
					alignCenterString(graphics, abbr, width, PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_XPOS, PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_YPOS, PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_FONT_SIZE);
				} else if (sideMetabolites.contains(name)) {
					if (!foundMetabolitesList.contains(name) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
						graphics.setColor(PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR);
					}
					if (cofactors.contains(name)) {
						graphics.setColor(PathwaysFrameConstants.COFACTOR_COLOR);
						if (!foundMetabolitesList.contains(name) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
							graphics.setColor(PathwaysFrameConstants.COFACTOR_NOT_FOUND_COLOR);
						}
					}
					alignCenterString(graphics, abbr, width, PathwaysFrameConstants.SIDE_METABOLITE_NODE_XPOS, PathwaysFrameConstants.SIDE_METABOLITE_NODE_YPOS, PathwaysFrameConstants.SIDE_METABOLITE_NODE_FONT_SIZE);
				} else if (reactions.contains(name)) {
					graphics.setColor(PathwaysFrameConstants.REACTION_NODE_DETAULT_FONT_COLOR);
					if (!foundReactionsList.contains(name) && LocalConfig.getInstance().isHighlightMissingReactionsSelected()) {
						graphics.setColor(PathwaysFrameConstants.REACTION_NOT_FOUND_FONT_COLOR);
					} else if (koReactions.contains(name)) {
						graphics.setColor(PathwaysFrameConstants.REACTION_KO_FONT_COLOR);
					}
					alignCenterString(graphics, name, width, PathwaysFrameConstants.REACTION_NODE_XPOS, PathwaysFrameConstants.REACTION_NODE_YPOS, PathwaysFrameConstants.REACTION_NODE_FONT_SIZE);
				}
			}
			if (mainMetabolites.contains(name) || smallMainMetabolites.contains(name)) {
				if (!noBorderList.contains(name)) {
					drawBorder(graphics, width, height, PathwaysFrameConstants.METABOLITE_BORDER_WIDTH);
				}
			} else if (sideMetabolites.contains(name)) {
				if (!noBorderList.contains(name)) {
					drawBorder(graphics, width, height, PathwaysFrameConstants.SIDE_METABOLITE_BORDER_WIDTH);
				}
			}
			Icon icon = new ImageIcon(bufferedImage);
			iconMap.put(name, icon);                                                                                                                                        
		} 
	}

	static class MetabTransformer implements Transformer<String,String[]> {                                           

		Map<String,String[]> map;                                                                                    
		public MetabTransformer(Map<String,String[]> map) {                                                           
			this.map = map;                                                                                          
		}                                                                                                            

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

	private String displayString(String s) {
		if (reactions.contains(s)) {
			if (s.startsWith("<html>")) {
				s = s.substring(6, s.indexOf("<p>"));
			}
			if (s.length() > PathwaysFrameConstants.REACTION_NODE_MAX_CHARS) {
				s = s.substring(0, PathwaysFrameConstants.REACTION_NODE_MAX_CHARS - PathwaysFrameConstants.REACTION_NODE_ELLIPSIS_CORRECTION) + "...";
			}
		} else if (s.equals(compartmentLabel)) {
			if (s.length() > PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_MAX_CHARS) {
				s = s.substring(0, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_MAX_CHARS - PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_ELLIPSIS_CORRECTION) + "...";
			}	
		} else if (pathwayNames.contains(s)) {
			if (s.length() > PathwaysFrameConstants.PATHWAY_NAME_NODE_MAX_CHARS) {
				s = s.substring(0, PathwaysFrameConstants.PATHWAY_NAME_NODE_MAX_CHARS - PathwaysFrameConstants.PATHWAY_NAME_NODE_ELLIPSIS_CORRECTION) + "...";
			}	
		} else if (smallMainMetabolites.contains(s)) {
			if (s.length() > PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_MAX_CHARS) {
				s = s.substring(0, PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_MAX_CHARS - PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_ELLIPSIS_CORRECTION) + "...";
			}
		} else if (sideMetabolites.contains(s)) {
			if (s.length() > PathwaysFrameConstants.SIDE_METABOLITE_NODE_MAX_CHARS) {
				s = s.substring(0, PathwaysFrameConstants.SIDE_METABOLITE_NODE_MAX_CHARS - PathwaysFrameConstants.SIDE_METABOLITE_NODE_ELLIPSIS_CORRECTION) + "...";
			}
		} else {
			if (s.length() > PathwaysFrameConstants.METABOLITE_NODE_MAX_CHARS) {
				s = s.substring(0, PathwaysFrameConstants.METABOLITE_NODE_MAX_CHARS - PathwaysFrameConstants.METABOLITE_NODE_ELLIPSIS_CORRECTION) + "...";
			}
		}
		if (s.startsWith("R_") || s.startsWith("r_")) {
			s = s.substring(2);
		}

		return s;

	}
	
	private int startX(Graphics g2d, String s, int width) {
		int start = 0;
		int stringLen = (int)  
				g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();  
		start = width/2 - stringLen/2;
		
		return start;
		
	}

	// based on http://www.coderanch.com/t/336616/GUI/java/Center-Align-text-drawString
	private void alignCenterString(Graphics g2d, String s, int width, int XPos, int YPos, int fontSize){  
		g2d.setFont(new Font(PathwaysFrameConstants.FONT_NAME, PathwaysFrameConstants.FONT_STYLE, fontSize));
		s = displayString(s);
		int start = startX(g2d, s, width);
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
		protected Map<E,Number> edge_weight;

		public EdgeWeightStrokeFunction(Map<E,Number> edge_weight)
		{
			this.edge_weight = edge_weight;
		}

		public Stroke transform(E e)
		{
			int strokeWidth = 1;
			if (LocalConfig.getInstance().isScaleEdgeThicknessSelected()) {
				if (edge_weight.containsKey(e)) {
					double value = edge_weight.get(e).doubleValue();
					//System.out.println(value);
					if (value > 0.1) {
						if (value == PathwaysFrameConstants.BORDER_THICKNESS) {
							strokeWidth = (int) PathwaysFrameConstants.BORDER_THICKNESS;
						} else {
							strokeWidth = (int) value;
						}
						return new BasicStroke(strokeWidth);
					} else {
						return RenderContext.DOTTED;
					}  
				} else {
					return RenderContext.DOTTED;
				}
			} else {
				return new BasicStroke(strokeWidth);
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
		//        protected Shape directed_arrow;	
		float length = PathwaysFrameConstants.ARROW_LENGTH;
		float width = PathwaysFrameConstants.ARROW_WIDTH;
		float notch_depth = PathwaysFrameConstants.ARROW_NOTCH;
		protected Shape directed_arrow = ArrowFactory.getNotchedArrow(width, length, notch_depth);

		//        public DirectionalEdgeArrowTransformer(int length, int width, int notch_depth)
		//        {
		//        	directed_arrow = ArrowFactory.getNotchedArrow(width, length, notch_depth);
		//undirected_arrow = ArrowFactory.getWedgeArrow(width, length);
		// no arrow for undirected edge
		//undirected_arrow = ArrowFactory.getWedgeArrow(0, 0);
		//        }

		/**
		 *
		 */
		public Shape transform(Context<Graph<V,E>,E> context)
		{
			double value = 1.0;
			if (LocalConfig.getInstance().isScaleEdgeThicknessSelected()) {
				if (edge_weight.containsKey(context.element)) {
					value = edge_weight.get(context.element).doubleValue();
				}
			}
			double arrowSize = 0.25;
			if (LocalConfig.getInstance().isScaleEdgeThicknessSelected()) {
				if (value >= 1) {
					arrowSize = Math.sqrt(value)/2;
				}
			}
			directed_arrow = ArrowFactory.getNotchedArrow((float) (arrowSize*width), 
					(float) (arrowSize*length), (float) (arrowSize*notch_depth));
			return directed_arrow;
		}

	}

	Transformer<Number, Paint> colorTransformer = new Transformer<Number, Paint>() {

		public Paint transform(Number i) {
			if (edge_color.containsKey(i)) {
				double color = edge_color.get(i).doubleValue();
				return colorFromColorValue(color);
			}
			return Color.BLACK;
		}
	};

	public Color colorFromColorValue(double color) {
		Color defaultColor = Color.BLACK;
		if (color == PathwaysFrameConstants.BLACK_COLOR_VALUE) {
			return Color.BLACK;
		} else if (color == PathwaysFrameConstants.GRAY_COLOR_VALUE) {
			return Color.GRAY;
		} else if (color == PathwaysFrameConstants.RED_COLOR_VALUE) {
			return Color.RED;
		} else if (color == PathwaysFrameConstants.GREEN_COLOR_VALUE) {
			return Color.GREEN;
		} else if (color == PathwaysFrameConstants.BLUE_COLOR_VALUE) {
			return Color.BLUE;
		} else if (color == PathwaysFrameConstants.BLUE_NOT_FOUND_COLOR_VALUE) {
			return PathwaysFrameConstants.REACTION_EDGE_NOT_FOUND_COLOR;
		}

		return defaultColor;
	}
	
	public String arrowFromColorValue(double color) {
		String defaultArrow = PathwaysFrameConstants.BLACK_ARROW_NAME;
		if (color == PathwaysFrameConstants.BLACK_COLOR_VALUE) {
			return PathwaysFrameConstants.BLACK_ARROW_NAME;
		} else if (color == PathwaysFrameConstants.GRAY_COLOR_VALUE) {
			return PathwaysFrameConstants.GRAY_ARROW_NAME;
		} else if (color == PathwaysFrameConstants.RED_COLOR_VALUE) {
			return PathwaysFrameConstants.RED_ARROW_NAME;
		} else if (color == PathwaysFrameConstants.BLUE_NOT_FOUND_COLOR_VALUE) {
			return PathwaysFrameConstants.NOT_FOUND_ARROW_NAME;
		}
		
		return defaultArrow;
	}

	public void createNodeInformationDialog(Object arg0) {
		final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("images/most16.jpg").getImage()); 
		icons.add(new ImageIcon("images/most32.jpg").getImage());

		if (getNodeInformationDialog() != null) {
			getNodeInformationDialog().dispose();
		}
		NodeInformationDialog frame = new NodeInformationDialog(nodeName(arg0.toString()));
		setNodeInformationDialog(frame);

		frame.setIconImages(icons);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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
	 * Returns name of renamed node if oldNameNewNameMap contains name, else returns name.
	 * @param name
	 * @return
	 */
	public String nodeName(String name) {
		if (oldNameNewNameMap.containsKey(name)) {
			return oldNameNewNameMap.get(name);
		}
		return name;

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
	
	
	public String maybeAddCompartmentNameSuffix(String compAbbr) {
		for (int c = 0; c < LocalConfig.getInstance().getListOfCompartments().size(); c++) { 
			String item = LocalConfig.getInstance().getListOfCompartments().get(c).getId();
			if (item.equals(LocalConfig.getInstance().getSelectedCompartmentName())) {
				if (LocalConfig.getInstance().getListOfCompartments().get(c).getName() != null &&
    					LocalConfig.getInstance().getListOfCompartments().get(c).getName().length() > 0) {
					compAbbr += " (" + LocalConfig.getInstance().getListOfCompartments().get(c).getName() + ")";
    			}
			}
		}
		
		return compAbbr;
		
	}

	class PNGFileFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
		}

		public String getDescription() {
			return ".png files";
		}
	}

	/*******************************************************************************************/
	// Find
	/*******************************************************************************************/

	ActionListener doneButtonActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			findDialogCloseAction();
		}
	};

	public void showFindDialog() {
		//		LocalConfig.getInstance().setReactionsLocationsListCount(0);
		//		LocalConfig.getInstance().setMetabolitesLocationsListCount(0);
		//		findFieldChanged = false;
		VisualizationsFindDialog findDialog = new VisualizationsFindDialog();
		setVisualizationsFindDialog(findDialog);
		//getVisualizationsFindDialog().findButton.addActionListener(findNextButtonActionListener);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int y = (screenSize.height - findDialog.getSize().height)/2;

		final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("etc/most16.jpg").getImage()); 
		icons.add(new ImageIcon("etc/most32.jpg").getImage());
		findDialog.setIconImages(icons);
		findDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		findDialog.setAlwaysOnTop(true);        
		// Find/Replace positioned at far right on screen so it does not obscure scroll bar
		findDialog.setLocation((screenSize.width - findDialog.getSize().width) - 10, y);
		findDialog.setVisible(true);
		findDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				findDialogCloseAction();
			}
		});
		VisualizationsFindDialog.findBox.setEnabled(true);

		VisualizationsFindDialog.doneButton.addActionListener(doneButtonActionListener);

		//		findButtonClicked = false;
		// ensure states of boolean values match states of findReplace frame
		searchBackwards = VisualizationsFindConstants.SEARCH_BACKWARDS_DEFAULT;
		matchCase = VisualizationsFindConstants.MATCH_CASE_DEFAULT;
		wrapAround = VisualizationsFindConstants.WRAP_AROUND_DEFAULT;
		exactMatch = VisualizationsFindConstants.EXACT_MATCH_DEFAULT;
		findMode = true;
		findItem.setEnabled(false);
	}	

	public void findDialogCloseAction() {
		findMode = false;
		findItem.setEnabled(true);

		// ensure states of boolean values match states of findReplace frame
		searchBackwards = VisualizationsFindConstants.SEARCH_BACKWARDS_DEFAULT;
		matchCase = VisualizationsFindConstants.MATCH_CASE_DEFAULT;
		wrapAround = VisualizationsFindConstants.WRAP_AROUND_DEFAULT;
		exactMatch = VisualizationsFindConstants.EXACT_MATCH_DEFAULT;
		getVisualizationsFindDialog().setVisible(false);
		getVisualizationsFindDialog().dispose();
	}

	ActionListener findNextButtonActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			findAction();
			//			findButtonClicked = true;
		}
	};

	public void findAction() {
		findNext();
	}

	public void notFoundAction() {
		notFoundShown = true;
		getVisualizationsFindDialog().setAlwaysOnTop(false);
		Object[] options = {"OK"};
		int choice = JOptionPane.showOptionDialog(null, 
				"MOST has not found the item you are searching for.", 
				"Item Not Found", 
				JOptionPane.YES_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		if (choice == JOptionPane.YES_OPTION || choice == JOptionPane.CLOSED_OPTION) {
			notFoundShown = false;
		}
		getVisualizationsFindDialog().setAlwaysOnTop(true);
	}

	public void endFindAction() {
		getVisualizationsFindDialog().setAlwaysOnTop(false);
		Object[] options = {"    Yes    ", "    No    ",};
		int choice = JOptionPane.showOptionDialog(null, 
				"MOST has not found the item you are searching for.\nDo you want to start over from the beginning?", 
				"Item Not Found", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		if (choice == JOptionPane.YES_OPTION) {
			wrapAround = true; 

			VisualizationsFindDialog.wrapCheckBox.setSelected(true);
			if (searchBackwards) {
				if (searchBackwards && getFindLocationsMap() != null && getFindLocationsMap().size() > 0) {
					findStartIndex = getFindLocationsMap().size() - 1;
				}
			} else {
				findStartIndex = 0;
			}
		}
		if (choice == JOptionPane.NO_OPTION) {

		}
		getVisualizationsFindDialog().setAlwaysOnTop(true);
	}

	private boolean findValueChanged(String findValue) {
		boolean changed = false;
		if (findValue.equals(oldFindValue)) {

		} else {
			changed = true;
		}
		//		System.out.println("changed " + changed);
		return changed;
	}

	public void findNext() {
		String findValue = "";
		if (matchCase) {
			findValue = VisualizationsFindDialog.findBox.getSelectedItem().toString();
		} else {
			findValue = VisualizationsFindDialog.findBox.getSelectedItem().toString().toLowerCase();
		}
		HashMap<String, ArrayList<Double>> findLocationsMap = findLocationsMap();
		if (findValueChanged(findValue)) {
			if (searchBackwards) {
				findStartIndex = findLocationsMap.size() - 1;
			} else {
				findStartIndex = 0;
			}
		} 
		oldFindValue = findValue;
		//HashMap<String, ArrayList<Double>> findLocationsMap = findLocationsMap();
		if (findLocationsMap.size() == 0) {
			if (!notFoundShown) {
				notFoundAction();
			}
		} else {
			getVisualizationsFindDialog().requestFocus();
			ArrayList<String> findXCoordinates = new ArrayList<String>(findLocationsMap.keySet());
			//System.out.println(findXCoordinates);
			Collections.sort(findXCoordinates, new NumComparator());
			//			System.out.println(findXCoordinates);
			ArrayList<Double> findPositions = findLocationsMap.get(findXCoordinates.get(findStartIndex));
			findNodeByLocation(findPositions.get(0), findPositions.get(1));
			for (int i = 0; i < findXCoordinates.size(); i++) {
				//System.out.println(findLocationsMap.get(findXCoordinates.get(i)));
			}
			if (searchBackwards) {
				if (findStartIndex > 0) {
					findStartIndex -= 1;
				} else {
					if (wrapAround) {
						findStartIndex = findLocationsMap.size() - 1;
					} else {
						endFindAction();
					}
				}
			} else {
				if (findStartIndex < (findLocationsMap.size() - 1)) {
					findStartIndex += 1;
				} else {
					if (wrapAround) {
						findStartIndex = 0;
					} else {
						endFindAction();
					}
				}
			}													
		}
		getVisualizationsFindDialog().requestFocus();
	}

	public HashMap<String, ArrayList<Double>> findLocationsMap() {		
		HashMap<String, ArrayList<Double>> findLocationsMap = new HashMap<String, ArrayList<Double>>();

		String findValue = "";
		// save original entry for exact match
		String originalFindValue = "";
		if (matchCase) {
			findValue = VisualizationsFindDialog.findBox.getSelectedItem().toString();
			originalFindValue = findValue;
		} else {
			originalFindValue = VisualizationsFindDialog.findBox.getSelectedItem().toString();
			findValue = VisualizationsFindDialog.findBox.getSelectedItem().toString().toLowerCase();
		}
		//		System.out.println("fv " + findValue);
		if (exactMatch) {
			if (VisualizationsFindDialog.matchByBox.getSelectedItem().equals(
					GraphicalInterfaceConstants.METABOLITE_ABBREVIATION_COLUMN_NAME)) {
				if (metaboliteAbbrPositionsMap.containsKey(originalFindValue)) {
					for (int r = 0; r < metaboliteAbbrPositionsMap.get(originalFindValue).size(); r++) {
						updateFindLocationsMap(findLocationsMap, metaboliteAbbrPositionsMap.get(originalFindValue).get(r)[0], 
								metaboliteAbbrPositionsMap.get(originalFindValue).get(r)[1]);
					}
				}
			} else if (VisualizationsFindDialog.matchByBox.getSelectedItem().equals(
					VisualizationsFindConstants.KEGG_METABOLITE_ID_ITEM)) {
				String keggMetaboliteId = findValue.toUpperCase();
				if (keggMetaboliteIdPositionsMap.containsKey(keggMetaboliteId)) {
					for (int r = 0; r < keggMetaboliteIdPositionsMap.get(keggMetaboliteId).size(); r++) {
						updateFindLocationsMap(findLocationsMap, keggMetaboliteIdPositionsMap.get(keggMetaboliteId).get(r)[0], 
								keggMetaboliteIdPositionsMap.get(keggMetaboliteId).get(r)[1]);
					}
				}
			} else if (VisualizationsFindDialog.matchByBox.getSelectedItem().equals(
					VisualizationsFindConstants.EC_NUMBER_ITEM)) {
				if (ecNumberPositionsMap.containsKey(findValue)) {
					for (int e = 0; e < ecNumberPositionsMap.get(findValue).size(); e++) {
						updateFindLocationsMap(findLocationsMap, ecNumberPositionsMap.get(findValue).get(e)[0], 
								ecNumberPositionsMap.get(findValue).get(e)[1]);
					}
				}
			} else if (VisualizationsFindDialog.matchByBox.getSelectedItem().equals(
					VisualizationsFindConstants.KEGG_REACTION_ID_ITEM)) {
				// if match case is not selected, find values are set to lower case. while this works with generic find
				// this does not work with KEGG ids that are upper case in the reaction positions file
				String keggReactionId = findValue.toUpperCase();
				if (keggReactionIdPositionsMap.containsKey(keggReactionId)) {
					for (int r = 0; r < keggReactionIdPositionsMap.get(keggReactionId).size(); r++) {
						updateFindLocationsMap(findLocationsMap, keggReactionIdPositionsMap.get(keggReactionId).get(r)[0], 
								keggReactionIdPositionsMap.get(keggReactionId).get(r)[1]);
					}
				}
			} else if (VisualizationsFindDialog.matchByBox.getSelectedItem().equals(
					GraphicalInterfaceConstants.REACTION_ABBREVIATION_COLUMN_NAME)) {
				if (reactionAbbrPositionsMap.containsKey(originalFindValue)) {
					for (int r = 0; r < reactionAbbrPositionsMap.get(originalFindValue).size(); r++) {
						updateFindLocationsMap(findLocationsMap, reactionAbbrPositionsMap.get(originalFindValue).get(r)[0], 
								reactionAbbrPositionsMap.get(originalFindValue).get(r)[1]);
					}
				}
			}
		} else {
			for (int i = 0; i < nodeNameList.size(); i++) {
				String s = "";
				// if node was substituted (acceptor, etc.) and renamed get
				// display name from map
				if (oldNameNewNameMap.containsKey(nodeNameList.get(i))) {
					s = oldNameNewNameMap.get(nodeNameList.get(i));
				} else {
					s = nodeNameList.get(i);
				}
				if (matchCase) {

				} else {
					s = s.toLowerCase();
				}
				if (s.contains(findValue)) {
					updateFindLocationsMap(findLocationsMap, nodeNamePositionMap.get(nodeNameList.get(i))[0], 
							nodeNamePositionMap.get(nodeNameList.get(i))[1]);
				}
			}
		}

		return findLocationsMap;

	}

	private void updateFindLocationsMap(HashMap<String, ArrayList<Double>> findLocationsMap, String xString, String yString) {
		ArrayList<Double> coordinates = new ArrayList<Double>();
		double x = Double.parseDouble(xString);
		double y = Double.parseDouble(yString);

		coordinates.add(x);
		coordinates.add(y);
		findLocationsMap.put(xString, coordinates);
	}

	/**
	 * Zoom to full scale and move node to center
	 * @param x
	 * @param y
	 */
	private void findNodeByLocation(double x, double y) {
		// zoom in to full scale
		double zoom = 1/viewScale;
		viewScale = 1;
		Point2D.Float p = new Point2D.Float((float) vv.getCenter().getX(), (float) vv.getCenter().getY());
		scaler.scale(vv, (float) zoom, p);

		// based on code from http://stackoverflow.com/questions/5745183/how-to-programatically-pan-a-visualizationviewer-with-jung-the-java-library
		// scroll to location
		//    	MutableTransformer view = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW);
		MutableTransformer layout = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT);
		Point2D ctr = vv.getCenter();

		Point2D pnt = layout.inverseTransform(ctr);
		double deltaX = (pnt.getX()/viewScale);
		double deltaY = (pnt.getY()/viewScale);

		deltaX += 5680;   // start
		deltaY += 2700;  // start
		//        deltaY += 2570;  // start (before removing tabbed pane)
		// does not move exactly to center when in full screen, but values
		// above seem to work well enough
		//        deltaX += 6050;  // full
		//        deltaY += 2960;   // full
		//        deltaY += 2830;   // full (before removing tabbed pane)       

		layout.translate(deltaX - x, deltaY - y);
	}

	ActionListener matchCaseActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
			matchCase = abstractButton.getModel().isSelected();
		}
	};

	ActionListener wrapAroundActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
			wrapAround = abstractButton.getModel().isSelected();
		}
	};

	ActionListener searchBackwardsActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
			searchBackwards = abstractButton.getModel().isSelected();
			if (searchBackwards && getFindLocationsMap() != null && getFindLocationsMap().size() > 0) {
				findStartIndex = getFindLocationsMap().size() - 1;
			} else {
				findStartIndex = 0;
			}
		}
	};

	ActionListener exactMatchActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
			exactMatch = abstractButton.getModel().isSelected();
		}
	};

	ActionListener findDoneButtonActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {	
			findDialogCloseAction();
		}
	};	

	public void updateFindPositionsMap(HashMap<String, ArrayList<String[]>> positionsMap, String key, String[] pos) {
		if (key != null && key.length() > 0) {
			if (positionsMap.containsKey(key)) {
				ArrayList<String[]> positions = positionsMap.get(key);
				positions.add(pos);
				positionsMap.put(key, positions);
			} else {
				ArrayList<String[]> positions = new ArrayList<String[]>();
				positions.add(pos);
				positionsMap.put(key, positions);
			}
		}
	}

	/*******************************************************************************************/
	// End Find
	/*******************************************************************************************/
	
	public void saveGraphAsSVG() {
		SVGBuilder builder = new SVGBuilder();
		ArrayList<SVGEdge> edges = new ArrayList<SVGEdge>();
		for(int i=0; i<reactionList.size(); i++) {
			String[] info = reactionMap.get(reactionList.get(i));
			if (nodeNamePositionMap.containsKey(info[0]) && nodeNamePositionMap.containsKey(info[1])) {
				int width = 0;
				int height = 0;
				if (LocalConfig.getInstance().getMetaboliteNameDataMap().get(info[1]) != null) {
					width = PathwaysFrameConstants.METABOLITE_NO_BORDER_NODE_WIDTH;
					height = PathwaysFrameConstants.METABOLITE_NO_BORDER_NODE_HEIGHT;
					if (LocalConfig.getInstance().getMetaboliteNameDataMap().get(info[1]).getType().equals(PathwaysCSVFileConstants.SMALL_MAIN_METABOLITE_TYPE)) {
						width = PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_WIDTH;
						height = PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_HEIGHT;
					} else if (LocalConfig.getInstance().getMetaboliteNameDataMap().get(info[1]).getType().equals(PathwaysCSVFileConstants.SIDE_METABOLITE_TYPE)) {
						width = PathwaysFrameConstants.SIDE_METABOLITE_NODE_WIDTH;
						height = PathwaysFrameConstants.SIDE_METABOLITE_NODE_HEIGHT;
					} else if (LocalConfig.getInstance().getMetaboliteNameDataMap().get(info[1]).getBorder().equals("1")) {
						width = PathwaysFrameConstants.METABOLITE_BORDER_NODE_WIDTH;
						height = PathwaysFrameConstants.METABOLITE_BORDER_NODE_HEIGHT;
					}
				} 
				String[] reacEndpointCorrection = endpointCorrection(nodeNamePositionMap.get(info[0]), nodeNamePositionMap.get(info[1]), 
						PathwaysFrameConstants.REACTION_NODE_WIDTH, PathwaysFrameConstants.REACTION_NODE_HEIGHT);
//				System.out.println("r0 " + reacEndpointCorrection[0]);
//				System.out.println("r1 " + reacEndpointCorrection[0]);
				String[] metabEndpointCorrection = endpointCorrection(nodeNamePositionMap.get(info[0]), nodeNamePositionMap.get(info[1]), width, height);
//				System.out.println("m0 " + metabEndpointCorrection[0]);
//				System.out.println("m1 " + metabEndpointCorrection[0]);
				SVGEdge edge = new SVGEdge();
				ArrayList<String[]> endpoints = new ArrayList<String[]>();
//				endpoints.add(nodeNamePositionMap.get(info[0]));
//				endpoints.add(nodeNamePositionMap.get(info[1]));
				if (borderList.contains(info[0])) {
					endpoints.add(nodeNamePositionMap.get(info[0]));
				} else {
					endpoints.add(correctedEndpoint(nodeNamePositionMap.get(info[0]), reacEndpointCorrection, 
							PathwaysFrameConstants.REACTION_CORRECTION_TYPE));
				}
				endpoints.add(correctedEndpoint(nodeNamePositionMap.get(info[1]), metabEndpointCorrection, 
						PathwaysFrameConstants.METABOLITE_CORRECTION_TYPE));
				edge.setEndpoints(endpoints);
				edge.setStroke(colorFromColorValue(PathwaysFrameConstants.DEFAULT_COLOR_VALUE));
				if (colorMap.containsKey(reactionList.get(i))) {
					double color = colorMap.get(reactionList.get(i));
					edge.setStroke(colorFromColorValue(color));
					if (!borderList.contains(info[0])) {
						edge.setMarkerId(arrowFromColorValue(color));
//						edge.setRefX("-5");
					}
				}
				if (!borderList.contains(info[0])) {
//					edge.setMarkerId(arrowFromColorValue(color));
					edge.setRefX("0");
				}
				edge.setStrokeWidth("1");
				if (fluxMap.containsKey(reactionList.get(i))) {
					double fluxValue = fluxMap.get(reactionList.get(i));
					if (fluxValue > 1) {
						edge.setStrokeWidth(Double.toString(fluxValue));
					}
				}
				edges.add(edge);
			} else {
				//System.out.println("edge not found");
			}
		}
		builder.setEdges(edges);
		ArrayList<BorderRectangle> rects = new ArrayList<BorderRectangle>();
		ArrayList<SVGText> textList = new ArrayList<SVGText>();
		for (int j = 0; j < nodeNameList.size(); j++) {
			if (nodeNamePositionMap.containsKey(nodeNameList.get(j))) {
				double width = PathwaysFrameConstants.METABOLITE_BORDER_NODE_WIDTH;
				double height = PathwaysFrameConstants.METABOLITE_BORDER_NODE_HEIGHT;

				// set border color, borderless is node background color
				Color stroke = Color.BLACK;
				if (noBorderList.contains(nodeNameList.get(j)) || reactions.contains(nodeNameList.get(j))) {
					stroke = PathwaysFrameConstants.NODE_BACKGROUND_DETAULT_COLOR;
				} else if (pathwayNames.contains(nodeNameList.get(j))) {
					stroke = PathwaysFrameConstants.PATHWAY_NAME_COLOR;
				} else {
					if (mainMetabolites.contains(nodeNameList.get(j))) {
						if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
							stroke = PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR;
						}
					} else if (smallMainMetabolites.contains(nodeNameList.get(j))) {
						if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
							stroke = PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR;
						}
					} else if (sideMetabolites.contains(nodeNameList.get(j))) {
						if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
							stroke = PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR;
						}
						if (cofactors.contains(nodeNameList.get(j))) {
							stroke = PathwaysFrameConstants.COFACTOR_COLOR;
							if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
								stroke = PathwaysFrameConstants.COFACTOR_NOT_FOUND_COLOR;
							}
						}
					} else if (reactions.contains(nodeNameList.get(j))) {
						stroke = PathwaysFrameConstants.REACTION_NODE_DETAULT_FONT_COLOR;
						if (!foundReactionsList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingReactionsSelected()) {
							stroke = PathwaysFrameConstants.REACTION_NOT_FOUND_FONT_COLOR;
						} else if (koReactions.contains(nodeNameList.get(j))) {
							stroke = PathwaysFrameConstants.REACTION_KO_FONT_COLOR;
						}
					}
				}
				double strokeWidth = PathwaysFrameConstants.BORDER_THICKNESS;
				Color fillColor = PathwaysFrameConstants.NODE_BACKGROUND_DETAULT_COLOR;
				if (borderList.contains(nodeNameList.get(j))) {
					width = (int) PathwaysFrameConstants.BORDER_THICKNESS;
					height = (int) PathwaysFrameConstants.BORDER_THICKNESS;
				} else if (nodeNameList.get(j).equals(compartmentLabel)) {
					width = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_WIDTH;
					height = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_HEIGHT;
					strokeWidth = 0;
				} else if (mainMetabolites.contains(nodeNameList.get(j))) {
					if (!noBorderList.contains(nodeNameList.get(j))) {
						width = PathwaysFrameConstants.METABOLITE_BORDER_NODE_WIDTH;
						height = PathwaysFrameConstants.METABOLITE_BORDER_NODE_HEIGHT;
					} else {
						width = PathwaysFrameConstants.METABOLITE_NO_BORDER_NODE_WIDTH;
						height = PathwaysFrameConstants.METABOLITE_NO_BORDER_NODE_HEIGHT;
					}
				} else if (smallMainMetabolites.contains(nodeNameList.get(j))) {	
					width = PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_WIDTH;
					height = PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_HEIGHT;
				} else if (sideMetabolites.contains(nodeNameList.get(j))) {	
					width = PathwaysFrameConstants.SIDE_METABOLITE_NODE_WIDTH;
					height = PathwaysFrameConstants.SIDE_METABOLITE_NODE_HEIGHT;	
				} else if (reactions.contains(nodeNameList.get(j))) {
					width = PathwaysFrameConstants.REACTION_NODE_WIDTH;
					height = PathwaysFrameConstants.REACTION_NODE_HEIGHT;
				} else if (pathwayNames.contains(nodeNameList.get(j))) {
					width = PathwaysFrameConstants.PATHWAY_NAME_NODE_WIDTH;
					height = PathwaysFrameConstants.PATHWAY_NAME_NODE_HEIGHT; 
				}
				BorderRectangle r = new BorderRectangle();
				r.setX(Double.parseDouble(nodeNamePositionMap.get(nodeNameList.get(j))[0]) - width/2);
				r.setY(Double.parseDouble(nodeNamePositionMap.get(nodeNameList.get(j))[1]) - height/2);
				r.setWidth(width);
				r.setHeight(height);
				r.setStroke(stroke);
				r.setStrokeWidth(Double.toString(strokeWidth));
				r.setFill(fillColor);
				if (!borderList.contains(nodeNameList.get(j))) {
					rects.add(r);
				}
				SVGText svgText = new SVGText();
				String displayName = nodeNameList.get(j);
				if (LocalConfig.getInstance().getMetaboliteNameAbbrMap().containsKey(nodeNameList.get(j))) {
					displayName = LocalConfig.getInstance().getMetaboliteNameAbbrMap().get(nodeNameList.get(j));
				} 
				Color color = Color.black;
				String fontSize = Integer.toString(PathwaysFrameConstants.PATHWAY_NAME_NODE_FONT_SIZE);
				int xOffset = 0;
				int yOffset = 0;
				if (pathwayNames.contains(nodeNameList.get(j))) {
					if (foundPathwayNamesList.contains(nodeNameList.get(j))) {
						color = PathwaysFrameConstants.PATHWAY_NAME_COLOR;
					} else {
						color = PathwaysFrameConstants.PATHWAY_NAME_NOT_FOUND_COLOR;
					}
					xOffset = startX(getGraphics(), displayString(displayName), (int) (width*0.45));
					yOffset = PathwaysFrameConstants.PATHWAY_NAME_NODE_YPOS;
				} else if (nodeNameList.get(j).equals(compartmentLabel)) {
					fontSize = Integer.toString(PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_FONT_SIZE);
					//            		graphics.setFont(new Font("Arial", Font.TYPE1_FONT, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_FONT_SIZE));
					//            		graphics.drawString(compartmentLabel, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS);
					//            		graphics.drawString("Compartment Name: " + LocalConfig.getInstance().getSelectedCompartmentName(), PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS, 
							//            				PathwaysFrameConstants.COMPARTMENT_LABEL_LINE_OFFSET + PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS);
					xOffset = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS;
					yOffset = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS;
				} else {
					if (mainMetabolites.contains(nodeNameList.get(j))) {
						fontSize = Integer.toString(PathwaysFrameConstants.METABOLITE_NODE_FONT_SIZE);
						if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
							color = PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR;
						}
						xOffset = startX(getGraphics(), displayString(displayName), (int) (width*0.5));
						yOffset = PathwaysFrameConstants.METABOLITE_NODE_YPOS;
					} else if (smallMainMetabolites.contains(nodeNameList.get(j))) {
						fontSize = Integer.toString(PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_FONT_SIZE);
						if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
							color = PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR;
						}
						xOffset = startX(getGraphics(), displayString(displayName), (int) (width*0.4));
						yOffset = PathwaysFrameConstants.SMALL_MAIN_METABOLITE_NODE_YPOS;
					} else if (sideMetabolites.contains(nodeNameList.get(j))) {
						fontSize = Integer.toString(PathwaysFrameConstants.SIDE_METABOLITE_NODE_FONT_SIZE);
						if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
							color = PathwaysFrameConstants.METABOLITE_NOT_FOUND_COLOR;
						}
						if (cofactors.contains(nodeNameList.get(j))) {
							color = PathwaysFrameConstants.COFACTOR_COLOR;
							if (!foundMetabolitesList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingMetabolitesSelected()) {
								color = PathwaysFrameConstants.COFACTOR_NOT_FOUND_COLOR;
							}
						}
						xOffset = startX(getGraphics(), displayString(displayName), (int) (width*1));
						yOffset = PathwaysFrameConstants.SIDE_METABOLITE_NODE_YPOS;
					} else if (reactions.contains(nodeNameList.get(j))) {
						fontSize = Integer.toString(PathwaysFrameConstants.REACTION_NODE_FONT_SIZE);
						color = PathwaysFrameConstants.REACTION_NODE_DETAULT_FONT_COLOR;
						if (!foundReactionsList.contains(nodeNameList.get(j)) && LocalConfig.getInstance().isHighlightMissingReactionsSelected()) {
							color = PathwaysFrameConstants.REACTION_NOT_FOUND_FONT_COLOR;
						} else if (koReactions.contains(nodeNameList.get(j))) {
							color = PathwaysFrameConstants.REACTION_KO_FONT_COLOR;
						}
						xOffset = startX(getGraphics(), displayString(displayName), (int) (width*0.5));
						yOffset = PathwaysFrameConstants.REACTION_NODE_YPOS;
					}
				}
				svgText.setX(Double.parseDouble(nodeNamePositionMap.get(nodeNameList.get(j))[0]) + xOffset - width/2);
				svgText.setY(Double.parseDouble((nodeNamePositionMap.get(nodeNameList.get(j))[1])) + yOffset*1.1 - height/2);
				svgText.setFont(PathwaysFrameConstants.FONT_NAME);
				svgText.setFontSize(fontSize);
				svgText.setFontWeight(PathwaysFrameConstants.FONT_WEIGHT);
				svgText.setFill(color);
				if (nodeNameList.get(j).equals(compartmentLabel)) {
					svgText.setText(nodeNameList.get(j));
					// add compartment name to compartment label
					SVGText svgText2 = new SVGText();
					svgText2.setX(Double.parseDouble(nodeNamePositionMap.get(nodeNameList.get(j))[0]) + xOffset - width/2);
					svgText2.setY(Double.parseDouble((nodeNamePositionMap.get(nodeNameList.get(j))[1])) + 
							PathwaysFrameConstants.COMPARTMENT_LABEL_LINE_OFFSET + yOffset*1.1 - height/2);
					svgText2.setFont(PathwaysFrameConstants.FONT_NAME);
					svgText2.setFontSize(fontSize);
					svgText2.setFontWeight(PathwaysFrameConstants.FONT_WEIGHT);
					svgText2.setFill(color);
					svgText2.setText("Compartment: " + maybeAddCompartmentNameSuffix(LocalConfig.getInstance().getSelectedCompartmentName()));
					textList.add(svgText2);
				} else {
					svgText.setText(displayString(displayName));
				}
				if (!borderList.contains(nodeNameList.get(j))) {
					textList.add(svgText);
				}
			} else {
				//System.out.println("node not found");
			}
		}
		builder.setRects(rects);
		builder.setTextList(textList);
		SVGWriter writer = new SVGWriter();
		writer.setBuilder(builder);
		writer.saveFile();
	}
	
	private String[] endpointCorrection(String[] endpoint0, String[] endpoint1, int width, int height) {
		String[] correction = {"0", "0"};
		// cast all four values once
		double endpoint0X = Double.valueOf(endpoint0[0]);
		double endpoint0Y = Double.valueOf(endpoint0[1]);
		double endpoint1X = Double.valueOf(endpoint1[0]);
		double endpoint1Y = Double.valueOf(endpoint1[1]);
		double sine = 0;
		double cosine = 0;
		// since trig. functions are expensive, calculate corrections for horizontal and vertical
		// lines without using trig.
		// vertical
		if (endpoint0X == endpoint1X) {
			// down
			if (endpoint1Y > endpoint0Y) {
				sine = 1;
//				correction[1] = Double.toString(height/2);
			// up
			} else if (endpoint0Y > endpoint1Y) {
				sine = -1;
//				correction[1] = Double.toString(-height/2);
			}
		// horizontal
		} else if (endpoint0Y == endpoint1Y) {
			// left
			if (endpoint1X > endpoint0X) {
				cosine = 1;
//				correction[0] = Double.toString(-width/2);
			// right
			} else if (endpoint0X > endpoint1X) {
				cosine = -1;
//				correction[0] = Double.toString(width/2);
			}
		} else {
			System.out.println("p1 " + endpoint0X + " " + endpoint0Y);
			System.out.println("p2 " + endpoint1X + " " + endpoint1Y);
			double angle = getAngleOfLineBetweenTwoPoints(endpoint0X, endpoint0Y, 
		    		endpoint1X, endpoint1Y);
			System.out.println("angle " + angle);
			sine = Math.sin(angle);
			cosine = Math.cos(angle);
			System.out.println("sin " + sine);
			System.out.println("cos " + cosine);
		}
		correction[0] = Double.toString(-cosine*width/2);
		correction[1] = Double.toString(sine*width/2);
//		System.out.println(correction[0]);
//		System.out.println(correction[1]);
		
		return correction;
	}
	
	private String[] correctedEndpoint(String[] endpoint, String[] correction, String type) {
		String[] correctedEndpoint = {endpoint[0], endpoint[1]};
		// cast all four values once
		double endpointX = Double.valueOf(endpoint[0]);
		double endpointY = Double.valueOf(endpoint[1]);
		double correctionX = Double.valueOf(correction[0]);
		double correctionY = Double.valueOf(correction[1]);
//		System.out.println("e " + endpointX);
//		System.out.println("e " + endpointY);
		if (type.equals(PathwaysFrameConstants.REACTION_CORRECTION_TYPE)) {
			correctedEndpoint[0] = Double.toString(endpointX - correctionX);
			correctedEndpoint[1] = Double.toString(endpointY + correctionY);
		} else if (type.equals(PathwaysFrameConstants.METABOLITE_CORRECTION_TYPE)) {
			correctedEndpoint[0] = Double.toString(endpointX + correctionX);
			correctedEndpoint[1] = Double.toString(endpointY - correctionY);
		}
//		System.out.println("c " + correctedEndpoint[0]);
//		System.out.println("c " + correctedEndpoint[1]);
		
		return correctedEndpoint;
		
	}
	
	/**
     * Determines the angle of a straight line drawn between point one and two. The number returned, which is a double in degrees, tells us how much we have to rotate a horizontal line clockwise for it to match the line between the two points.
     * If you prefer to deal with angles using radians instead of degrees, just change the last line to: "return Math.atan2(yDiff, xDiff);"
     * based on http://wikicode.wikidot.com/get-angle-of-line-between-two-points
     */
    private double getAngleOfLineBetweenTwoPoints(double endpoint0X, double endpoint0Y, 
    		double endpoint1X, double endpoint1Y)
    {
        double xDiff = endpoint1X - endpoint0X;
        double yDiff = endpoint1Y - endpoint0Y;
//        return Math.toDegrees(Math.atan2(yDiff, xDiff));
        return Math.atan2(yDiff, xDiff);
    }
	
	public void saveAsPNG() {
		JTextArea output = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save PNG File");
		fileChooser.setFileFilter(new PNGFileFilter());
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		String lastPNG_path = GraphicalInterface.curSettings.get("LastPNGPath");
		Utilities u = new Utilities();
		// if path is null or does not exist, default used, else last path used		
		fileChooser.setCurrentDirectory(new File(u.lastPath(lastPNG_path, fileChooser)));

		boolean done = false;
		while (!done) {
			//... Open a file dialog.
			int retval = fileChooser.showSaveDialog(output);
			if (retval == JFileChooser.CANCEL_OPTION) {
				done = true;
				//exit = false;
			}
			if (retval == JFileChooser.APPROVE_OPTION) {
				//... The user selected a file, get it, use it.
				String rawPathName = fileChooser.getSelectedFile().getAbsolutePath();
				if (!rawPathName.endsWith(".png")) {
					rawPathName = rawPathName + ".png";
				}
				GraphicalInterface.curSettings.add("LastPNGPath", rawPathName);

				//checks if filename endswith .png else renames file to end with .png
				String path = fileChooser.getSelectedFile().getPath();
				if (!path.endsWith(".png")) {
					path = path + ".png";
				}

				File file = new File(path);
				if (file.exists()) {
					int confirmDialog = JOptionPane.showConfirmDialog(fileChooser, "Replace existing file?");
					if (confirmDialog == JOptionPane.YES_OPTION) {
						done = true;

						saveWindowAsPNG(path);

					} else if (confirmDialog == JOptionPane.NO_OPTION) {        		    	  
						done = false;
					} else {
						done = true;
					}       		    	  
				} else {
					done = true;

					saveWindowAsPNG(path);
				}
			}
		}
	}

	public void saveWindowAsPNG(String path) {
		// based on http://stackoverflow.com/questions/8518390/exporting-jung-graphs-to-hi-res-images-preferably-vector-based
		Dimension vsDims = getSize();

		int width = vsDims.width;
		int height = vsDims.height;
		Color bg = getBackground();

		BufferedImage im = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		Graphics2D graphics = im.createGraphics();
		graphics.setColor(bg);
		graphics.fillRect(0,0, width, height);
		paintComponents(graphics);

		// there does not seem to be any way to programmatically determine the scroll bar width
		// and height but it seems to remain constant at 17 any way the window is resized
		int scrollBarSize = 17;
		int heightCorrection = controls.getHeight() + getJMenuBar().getHeight() + scrollBarSize;
		// create a cropped image from the original image
		BufferedImage croppedImage = im.getSubimage(0, getJMenuBar().getHeight(), width - scrollBarSize, height - heightCorrection);
		//BufferedImage croppedImage = im.getSubimage(0, 23, width - 17, height - 76);

		try{
			ImageIO.write(croppedImage,"png",new File(path));
			//ImageIO.write(croppedImage,"png",new File("window.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// based on http://stackoverflow.com/questions/6686007/how-to-sort-array-of-strings-in-numerical-order
	class NumComparator implements Comparator<String> {
		public int compare(String a, String b) {
			return Float.valueOf(a.toString()).compareTo(Float.valueOf(b.toString()));
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