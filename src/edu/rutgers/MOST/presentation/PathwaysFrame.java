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
import java.awt.event.ActionEvent;                                                                                   
import java.awt.event.ActionListener;                                                                                
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;                                                                                
import java.awt.geom.Point2D;                                                                                        
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;                                                                                          
import java.util.Collections;
import java.util.HashMap;                                                                                            
import java.util.Map;                                                                                                
                                                                                                                     
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;                                                                                        
import javax.swing.JApplet;                                                                                          
import javax.swing.JButton;                                                                                          
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;                                                                                           
                                                                                                                     
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import org.apache.commons.collections15.Transformer;                                                                 
import org.apache.commons.collections15.functors.ChainedTransformer;                                                 
                                                                                                                     






import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.data.MetabolicPathway;
import edu.rutgers.MOST.data.PathwayMetaboliteNode;
import edu.rutgers.MOST.data.PathwayMetaboliteNodeFactory;
import edu.rutgers.MOST.data.PathwayNameNode;
import edu.rutgers.MOST.data.PathwayReactionNode;
import edu.rutgers.MOST.data.PathwayReactionNodeFactory;
import edu.rutgers.MOST.data.PathwaysCSVFileConstants;
import edu.rutgers.MOST.data.ReactionFactory;
import edu.rutgers.MOST.data.SBMLReaction;
import edu.rutgers.MOST.data.SettingsConstants;
import edu.uci.ics.jung.algorithms.layout.Layout;                                                                    
import edu.uci.ics.jung.algorithms.layout.StaticLayout;                                                              
import edu.uci.ics.jung.graph.Graph;  
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeType;                                                                         
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;                                                           
import edu.uci.ics.jung.visualization.Layer;                                                                         
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
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
    public JButton redrawButton = new JButton("Redraw");
    public JPanel controls = new JPanel();
                                                                                                                     
    /**                                                                                                              
     * the visual component and renderer for the graph                                                               
     */                                                                                                              
    VisualizationViewer<String, Number> vv;  
    
    //public final JMenuItem visualizationOptionsItem = new JMenuItem(VisualizationOptionsConstants.VISUALIZATION_OPTIONS_MENU_ITEM_NAME);
                                                                                                                     
	Map<String, String[]> metabPosMap = new HashMap<String, String[]>();                                                       
	ArrayList<String> metaboliteList = new ArrayList<String>(); 
   	
   	// key = name of rxn, value = reactant, product, reversible
   	Map<String, String[]> reactionMap = new HashMap<String, String[]>(); 
   	ArrayList<String> reactionList = new ArrayList<String>();
   	
   	// lists used to distinguish node types
   	ArrayList<String> borderList = new ArrayList<String>();   // compartment border
   	ArrayList<String> noBorderList = new ArrayList<String>();   // metabolite node border
   	ArrayList<String> pathwayNames = new ArrayList<String>();
   	ArrayList<String> metabolites = new ArrayList<String>();
   	ArrayList<String> smallMainMetabolites = new ArrayList<String>();
   	ArrayList<String> sideMetabolites = new ArrayList<String>();
   	ArrayList<String> cofactors = new ArrayList<String>();
   	ArrayList<String> reactions = new ArrayList<String>();
   	Map<String, Double> fluxMap = new HashMap<String, Double>(); 
   	Map<String, Double> colorMap = new HashMap<String, Double>();
   	ArrayList<String> koReactions = new ArrayList<String>();
   	ArrayList<String> foundMetabolitesList = new ArrayList<String>();
   	ArrayList<String> foundReactionsList = new ArrayList<String>();
   	Map<String, Icon> iconMap = new HashMap<String, Icon>(); 
   	ArrayList<Integer> plottedIds = new ArrayList<Integer>();
   	
   	PathwayReactionNodeFactory prnf = new PathwayReactionNodeFactory();
   	
   	String compartmentLabel = "Model Name: " + LocalConfig.getInstance().getModelName();
   	
//   	private double layoutScale;
//   	private double viewScale;
   	
//   	private double startX = 0;
//   	private double startY = PathwaysFrameConstants.START_Y;
   	private double startX = 2*PathwaysFrameConstants.HORIZONTAL_INCREMENT;
   	private double startY = PathwaysFrameConstants.START_Y;
   
   	private final JMenuItem saveGraphPNGItem = new JMenuItem("Save Graph As PNG");
   	private final JMenuItem saveWindowPNGItem = new JMenuItem("Save Window As PNG");
   	private final JCheckBoxMenuItem transformItem = new JCheckBoxMenuItem("Transform");
   	
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
	
	private Map<String, ArrayList<Double>> startPosMap = new HashMap<String, ArrayList<Double>>();

	/**                                                                                                              
     * create an instance of a simple graph with controls to                                                         
     * demo the zoom features.                                                                                       
     *                                                                                                               
     */                                                                                                              
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public PathwaysFrame(int component) { 
        setLayout(new BorderLayout()); 
        
        transformItem.setState(false);
        
        /**************************************************************************/
    	// create menu bar
    	/**************************************************************************/

    	JMenuBar menuBar = new JMenuBar();

    	setJMenuBar(menuBar);

    	JMenu fileMenu = new JMenu("File");
    	fileMenu.setMnemonic(KeyEvent.VK_F);
    	
    	fileMenu.add(saveGraphPNGItem);
    	saveGraphPNGItem.setMnemonic(KeyEvent.VK_G);
    	
    	saveGraphPNGItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				saveGraphAsPNG();
			}
		});
    	
    	fileMenu.add(saveWindowPNGItem);
    	saveWindowPNGItem.setMnemonic(KeyEvent.VK_W);
    	
    	saveWindowPNGItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
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
		});

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
         
    	processData(component);
    	
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
        		new Dimension(PathwaysFrameConstants.GRAPH_WINDOW_WIDTH, 
        				PathwaysFrameConstants.GRAPH_WINDOW_HEIGHT));   
        
        final ScalingControl scaler = new CrossoverScalingControl();
        
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
			        //final Point2D p = me.getPoint();
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
           
        //System.out.println("fl " + foundReactionsList);            
        
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
        		//                    g.drawImage(icon.getImage(), 0, 0,                                                               
        		//                    		icon.getIconWidth(),icon.getIconHeight(),vv);                                            
        		g2d.setTransform(oldXform);                                                                      
        	}                                                                                                    
        	public boolean useTransform() { return false; }                                                      
        });  
        
        ewcs = new EdgeWeightStrokeFunction<Number>(edge_weight);
        //arrowTransformer = new DirectionalEdgeArrowTransformer(PathwaysFrameConstants.ARROW_LENGTH, PathwaysFrameConstants.ARROW_WIDTH, PathwaysFrameConstants.ARROW_NOTCH); 
        arrowTransformer = new DirectionalEdgeArrowTransformer();
        
        // don't think this is necessary
        //vv.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.gray));
        
        // all edges same color
        //vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.black));
        // vary colors
        vv.getRenderContext().setEdgeDrawPaintTransformer(colorTransformer);
        
        // all edges same thickness
        //vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer(new BasicStroke(2.5f)));
        // vary edge thicknesses
        vv.getRenderContext().setEdgeStrokeTransformer(ewcs);
        
        // all arrows same
        vv.getRenderContext().setEdgeArrowTransformer(arrowTransformer);
        vv.getRenderContext().setArrowFillPaintTransformer(colorTransformer);
        vv.getRenderContext().setArrowDrawPaintTransformer(colorTransformer);
        
        // add listeners for ToolTips  
        vv.setVertexToolTipTransformer(new ToStringLabeller()); 
        // no tooltips on edges for now
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
//				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
            }                                                                                                        
        });                                                                                                          
        JButton minus = new JButton("-");                                                                            
        minus.addActionListener(new ActionListener() {                                                               
            public void actionPerformed(ActionEvent e) {                                                             
                scaler.scale(vv, 1/PathwaysFrameConstants.SCALING_FACTOR, vv.getCenter());
//                System.out.println("layout scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale());
//				System.out.println("view scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale());
//                layoutScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale();
//				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
            }                                                                                                        
        });                                                                                                          
                                                                                                                     
//        JButton reset = new JButton("Reset");                                                                        
//        reset.addActionListener(new ActionListener() {                                                               
//                                                                                                                     
//			public void actionPerformed(ActionEvent e) {                                                             
//				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();       
//				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setToIdentity();  
//				System.out.println("layout scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale());
//				System.out.println("view scale " + vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale());
//				layoutScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getScale();
//				viewScale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
//			}}); 
        
        //JButton redraw = new JButton("Redraw");                                                                        
//        redrawButton.addActionListener(new ActionListener() {                                                               
//
//        	public void actionPerformed(ActionEvent e) {                                                             
//
//        	}});

        //JPanel controls = new JPanel();                                                                              
        controls.add(plus);                                                                                          
        controls.add(minus); 
        controls.add(redrawButton);
        //controls.add(reset);                                                                                         
        add(controls, BorderLayout.SOUTH);  
       
    }  
    
    public void saveGraphAsPNG() {
    	double centerX = 2000;
    	double centerY = 2000;
    	double width = 6000;
    	double height = 18000;
    	vv.getGraphLayout().setSize(new Dimension((int) width, (int) height));
    	// based on code from http://stackoverflow.com/questions/10420779/jung-save-whole-graph-not-only-visible-part-as-image
    	// Create the VisualizationImageServer
    	// vv is the VisualizationViewer containing my graph
    	VisualizationImageServer<String, Number> vis =
    	    new VisualizationImageServer<String,Number>(vv.getGraphLayout(),
    	            vv.getGraphLayout().getSize());
    	// Configure the VisualizationImageServer the same way
    	// you did your VisualizationViewer.

    	vis.setBackground(Color.WHITE);
    	
    	vis.getRenderContext().setVertexShapeTransformer(vv.getRenderContext().getVertexShapeTransformer());
    	vis.getRenderContext().setVertexIconTransformer(vv.getRenderContext().getVertexIconTransformer());

    	vis.getRenderContext().setEdgeDrawPaintTransformer(vv.getRenderContext().getEdgeDrawPaintTransformer());
    	vis.getRenderContext().setEdgeShapeTransformer(vv.getRenderContext().getEdgeShapeTransformer());
    	vis.getRenderContext().setEdgeStrokeTransformer(vv.getRenderContext().getEdgeStrokeTransformer());
    	vis.getRenderContext().setEdgeArrowTransformer(vv.getRenderContext().getEdgeArrowTransformer());
    	vis.getRenderContext().setArrowFillPaintTransformer(vv.getRenderContext().getArrowFillPaintTransformer());
    	vis.getRenderContext().setArrowDrawPaintTransformer(vv.getRenderContext().getArrowDrawPaintTransformer());
    	vis.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<String, Number>());
    	
//    	double centerX = vv.getVisibleRect().getCenterX();
//    	double centerY = vv.getVisibleRect().getCenterY();
//    	double width = vv.getVisibleRect().getWidth();
//    	double height = vv.getVisibleRect().getHeight();
//    	double width = 8000;
//    	double height = 15000;
//    	System.out.println("cx " + centerX);
//    	System.out.println("cy " + centerY);
//    	System.out.println("w " + width);
//    	System.out.println("h " + height);
//    	System.out.println("cx2 " + vv.getGraphLayout().getSize().getWidth() / 2);
//    	System.out.println("cy2 " + vv.getGraphLayout().getSize().getHeight() / 2);
//    	System.out.println("s " + vv.getGraphLayout().getSize());
//    	System.out.println(vv.getVisibleRect().getSize());
//    	
//    	// Create the buffered image
    	BufferedImage image = (BufferedImage) vis.getImage(
    	    new Point2D.Double(centerX, centerY),
    	    new Dimension((int) width, (int) height));
    	
    	// Create the buffered image
//    	BufferedImage image = (BufferedImage) vis.getImage(
//    	    new Point2D.Double(vv.getGraphLayout().getSize().getWidth() / 2,
//    	    vv.getGraphLayout().getSize().getHeight() / 2),
//    	    new Dimension(vv.getGraphLayout().getSize()));
    	
    	// Write image to a png file
    	File outputfile = new File("graph.png");

    	try {
    	    ImageIO.write(image, "png", outputfile);
    	} catch (IOException e) {
    	    // Exception handling
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
    
    public Vector<SBMLReaction> compartmentReactions(ReactionFactory f, String compartment) {
    	Vector<SBMLReaction> rxns = null;
    	if (compartment != null && compartment.length() > 0) {
			rxns = f.getReactionsByCompartment(compartment);
		} else {
			rxns = f.getAllReactions();
		}
		return rxns;
    }
    
    public Map<Integer, SBMLReaction> createCompartmentIdReactionMap(ReactionFactory f, Vector<SBMLReaction> rxns) {
    	Map<Integer, SBMLReaction> idReactionMap = new HashMap<Integer, SBMLReaction>();
		for (int i = 0; i < rxns.size(); i++) {
			idReactionMap.put(rxns.get(i).getId(), rxns.get(i));
		}
    	
		return idReactionMap;
    }
    
    public void processData(int component) {
    	ReactionFactory f = new ReactionFactory("SBML");
    	Vector<SBMLReaction> rxns = compartmentReactions(f, LocalConfig.getInstance().getSelectedCompartmentName());
    	Map<Integer, SBMLReaction> idReactionMap = createCompartmentIdReactionMap(f, rxns);
//		// temporary lists to keep track of what ec numbers have been found
//		ArrayList<String> foundEcNumbers = new ArrayList<String>();
//	    ArrayList<String> notFoundEcNumbers = new ArrayList<String>(LocalConfig.getInstance().getEcNumberReactionMap().keySet());
		
	    //ArrayList<String> foundMetabolitesList = new ArrayList<String>();
	    //ArrayList<String> foundReactionsList = new ArrayList<String>();
//		ArrayList<PathwayMetaboliteNode> externalMetaboliteNodeList = new ArrayList<PathwayMetaboliteNode>();
//		ArrayList<PathwayMetaboliteNode> transportMetaboliteNodeList = new ArrayList<PathwayMetaboliteNode>();
//		ArrayList<Integer> plottedIds = new ArrayList<Integer>();
		
//		PathwayReactionNodeFactory prnf = new PathwayReactionNodeFactory();
		MetabolicPathway pathway = LocalConfig.getInstance().getMetabolicPathways().get("0");
		if (pathway.getComponent() == PathwaysFrameConstants.PATHWAYS_COMPONENT) {
			if (startPosMap.containsKey(pathway.getId())) {
				startX = startPosMap.get(pathway.getId()).get(0);
				startY = startPosMap.get(pathway.getId()).get(1);
			}
		} else if (pathway.getComponent() == PathwaysFrameConstants.PROCESSES_COMPONENT) {
			
		}
		drawMetabolites(pathway, component, LocalConfig.getInstance().getSelectedCompartmentName());
		drawReactions(pathway, component, rxns, idReactionMap);
		drawPathwayNames(component);
		
		String borderLeftX = Double.toString(PathwaysFrameConstants.HORIZONTAL_INCREMENT*PathwaysFrameConstants.BORDER_LEFT);
		String borderRightX = Double.toString(PathwaysFrameConstants.HORIZONTAL_INCREMENT*PathwaysFrameConstants.BORDER_RIGHT);
		String borderTopY = Double.toString(PathwaysFrameConstants.VERTICAL_INCREMENT*PathwaysFrameConstants.BORDER_TOP);
		String borderBottomY = Double.toString(PathwaysFrameConstants.VERTICAL_INCREMENT*PathwaysFrameConstants.BORDER_BOTTOM);
		
		// draw cell border
		drawCompartmentBorder(borderLeftX, borderRightX, borderTopY, borderBottomY, 0);
		
		String compartmentLabelXOffset = Double.toString(
				PathwaysFrameConstants.BORDER_TOP*PathwaysFrameConstants.VERTICAL_INCREMENT + 
				PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_WIDTH/2 + 
				PathwaysFrameConstants.COMPARTMENT_LABEL_TOP_PADDING);
		String compartmentLabelYOffset = Double.toString(
				PathwaysFrameConstants.BORDER_LEFT*PathwaysFrameConstants.HORIZONTAL_INCREMENT + 
				PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_HEIGHT/2 + 
				PathwaysFrameConstants.COMPARTMENT_LABEL_LEFT_PADDING);
		
		if (component == PathwaysFrameConstants.PATHWAYS_COMPONENT) {
			drawCompartmentLabel(compartmentLabel, compartmentLabelXOffset, compartmentLabelYOffset);
		}
		
		if (LocalConfig.getInstance().getMembraneName() != null && LocalConfig.getInstance().getMembraneName().length() > 0
				&& component == PathwaysFrameConstants.PATHWAYS_COMPONENT) {
//			maxY += PathwaysFrameConstants.ADDITIONAL_COMPARTMENT_OFFSET;
//			Vector<SBMLReaction> membraneRxns = compartmentReactions(f, LocalConfig.getInstance().getMembraneName());
//	    	Map<Integer, SBMLReaction> membraneIdReactionMap = createCompartmentIdReactionMap(f, membraneRxns);
//	    	drawMetabolites(pathway, component, LocalConfig.getInstance().getMembraneName());
//			drawReactions(pathway, component, membraneRxns, membraneIdReactionMap);
//			drawPathwayNames(component);
		}
//		if (LocalConfig.getInstance().getOutsideName() != null && LocalConfig.getInstance().getOutsideName().length() > 0
//				&& component == PathwaysFrameConstants.PATHWAYS_COMPONENT) {
//			if (LocalConfig.getInstance().getMembraneName() != null && LocalConfig.getInstance().getMembraneName().length() > 0) {
//
//			} else {
//
//			}
//		}
		
		Collections.sort(plottedIds);
		//System.out.println("pf plotted " + plottedIds);
		//System.out.println("pf unplotted " + LocalConfig.getInstance().getUnplottedReactionIds());
		for (int p = 0; p < plottedIds.size(); p++) {
			if (LocalConfig.getInstance().getUnplottedReactionIds().contains(plottedIds.get(p))) {
				LocalConfig.getInstance().getUnplottedReactionIds().remove(LocalConfig.getInstance().getUnplottedReactionIds().indexOf(plottedIds.get(p)));
			}
		}
		//System.out.println("pf unplotted " + LocalConfig.getInstance().getUnplottedReactionIds());
		
		//Collections.sort(foundEcNumbers);
		//System.out.println("found " + foundEcNumbers);
		//Collections.sort(notFoundEcNumbers);
		//System.out.println("not found " + notFoundEcNumbers);
                                                                                                                     
   		metaboliteList = new ArrayList<String>(metabPosMap.keySet()); 
   		Collections.sort(metaboliteList);
   		//System.out.println("m " + metaboliteList);
   		
   		reactionList = new ArrayList<String>(reactionMap.keySet()); 
   		Collections.sort(reactionList);
    }
    
    public void drawMetabolites(MetabolicPathway pathway, int component, String compartment) {
		for (int j = 0; j < pathway.getMetabolitesData().size(); j++) {
			if (pathway.getComponent() == component) {
				String metabName = pathway.getMetabolitesData().get(Integer.toString(j)).getName();
				String type = pathway.getMetabolitesData().get(Integer.toString(j)).getType();
				String keggId = pathway.getMetabolitesData().get(Integer.toString(j)).getKeggId();
				boolean drawMetabolite = true;
				if (LocalConfig.getInstance().getKeggIdMetaboliteMap().containsKey(keggId)) {
//					for (int k = 0; k < LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId).size(); k++) {
//						if (LocalConfig.getInstance().getKeggIdMetaboliteMap().get(keggId).get(k).getCompartment().
//								equals(compartment)) {
//							//foundMetabolitesList.add(metabName);
//						} 
//					}
				} else {
					if (!LocalConfig.getInstance().isGraphMissingMetabolitesSelected()) {
						drawMetabolite = false;
					}
				}
				if (drawMetabolite) {
					if (pathway.getMetabolitesData().get(Integer.toString(j)).getBorder().equals("0")) {
						noBorderList.add(pathway.getMetabolitesData().get(Integer.toString(j)).getName());
					}
					classifyMetabolite(type, metabName, keggId);
					double x = 0;
					double y = 0;
					x = startX + PathwaysFrameConstants.HORIZONTAL_INCREMENT*pathway.getMetabolitesData().get(Integer.toString(j)).getLevel();
					y = startY + PathwaysFrameConstants.VERTICAL_INCREMENT*pathway.getMetabolitesData().get(Integer.toString(j)).getLevelPosition();
					PathwayMetaboliteNodeFactory pmnf = new PathwayMetaboliteNodeFactory();
					PathwayMetaboliteNode pn = pmnf.createPathwayMetaboliteNode(pathway.getMetabolitesData().get(Integer.toString(j)).getId(), 
							x, y, type, pathway.getMetabolitesData().get(Integer.toString(j)).getAbbreviation(), 
							pathway.getMetabolitesData().get(Integer.toString(j)).getName(), keggId);
					pathway.getMetabolitesNodes().put(pn.getDataId(), pn);
					metabPosMap.put(metabName, new String[] {Double.toString(x), Double.toString(y)});
				}
			}
		}
    }
    
    public void drawReactions(MetabolicPathway pathway, int component, Vector<SBMLReaction> rxns, Map<Integer, SBMLReaction> idReactionMap) {
    	ArrayList<String> metabPosKeys = new ArrayList<String>(metabPosMap.keySet());
    	for (int k = 0; k < pathway.getReactionsData().size(); k++) {
			if (pathway.getComponent() == component) {
				PathwayReactionNode pn = prnf.createPathwayReactionNode(pathway.getReactionsData().get(Integer.toString(k)).getEcNumbers(),
						pathway.getReactionsData().get(Integer.toString(k)).getKeggReactionIds(), pathway.getReactionsData().get(Integer.toString(k)).getKeggReactantIds(),
						pathway.getReactionsData().get(Integer.toString(k)).getKeggProductIds(), LocalConfig.getInstance().getSelectedCompartmentName(), pathway.getComponent(), rxns, 
						idReactionMap);
				String displayName = prnf.createDisplayName(pathway.getReactionsData().get(Integer.toString(k)).getDisplayName(),
						pathway.getReactionsData().get(Integer.toString(k)).getName(),
						pn.getReactions(), idReactionMap);
				// update temporary lists to keep track of what ec numbers have been found
				double edgeColor = PathwaysFrameConstants.BLACK_COLOR_VALUE;
				double flux = 0;
				for (int z = 0; z < pn.getReactions().size(); z++) {
					if (pn.getReactions().get(z) != null) {
						flux += pn.getReactions().get(z).getFluxValue();
						if (pn.getReactions().get(z).getKnockout().equals(GraphicalInterfaceConstants.BOOLEAN_VALUES[1])) {
							koReactions.add(displayName);
							edgeColor = PathwaysFrameConstants.RED_COLOR_VALUE;
						}
					}
				}
				// set sum for flux
				pn.setFluxValue(flux);
				boolean drawReaction = true;
				if (pn.getReactions().size() > 0) {
					foundReactionsList.add(displayName);
					for (int r = 0; r < pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().size(); r++) {
						String metabName = pathway.getMetabolitesData().get(pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().get(r)).getName();
						if (!foundMetabolitesList.contains(metabName)) {
							//System.out.println(metabName);
							foundMetabolitesList.add(metabName);
						}
					}
					for (int p = 0; p < pathway.getReactionsData().get(Integer.toString(k)).getProductIds().size(); p++) {
						String metabName = pathway.getMetabolitesData().get(pathway.getReactionsData().get(Integer.toString(k)).getProductIds().get(p)).getName();
						if (!foundMetabolitesList.contains(metabName)) {
							//System.out.println(metabName);
							foundMetabolitesList.add(metabName);
						}
					}
					// for bookkeeping only
					for (int z = 0; z < pn.getReactions().size(); z++) {
						if (pn.getReactions().get(z) != null) {
							if (!plottedIds.contains(pn.getReactions().get(z).getId())) {
								plottedIds.add(pn.getReactions().get(z).getId());
							}
						}	
					}
				} else {
					if (!LocalConfig.getInstance().isGraphMissingReactionsSelected()) {
						drawReaction = false;
					} 
				}
				if (drawReaction) {
					reactions.add(displayName);
					double x = 0;
					double y = 0;
					x = startX + PathwaysFrameConstants.HORIZONTAL_INCREMENT*pathway.getReactionsData().get(Integer.toString(k)).getLevel();
					y = startY + PathwaysFrameConstants.VERTICAL_INCREMENT*pathway.getReactionsData().get(Integer.toString(k)).getLevelPosition();
					metabPosMap.put(displayName, new String[] {Double.toString(x), Double.toString(y)});  
					pn.setDataId(pathway.getReactionsData().get(Integer.toString(k)).getReactionId());
					pn.setxPosition(x);
					pn.setyPosition(y);
					String reversible = prnf.reversibleString(pathway.getReactionsData().get(Integer.toString(k)).getReversible());
					pn.setReversible(reversible);
					pathway.getReactionsNodes().put(pn.getDataId(), pn);
					for (int r = 0; r < pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().size(); r++) {
						if (pathway.getMetabolitesData().containsKey((pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().get(r)))) {
							String reac = pathway.getMetabolitesData().get((pathway.getReactionsData().get(Integer.toString(k)).getReactantIds().get(r))).getName();
							reactionMap.put(displayName + "reactant " + Integer.toString(r), new String[] {displayName, reac, reversible});
							fluxMap.put(displayName + "reactant " + Integer.toString(r), edgeThickness(pn.getFluxValue()));
							if (pn.getFluxValue() == 0 && !koReactions.contains(displayName)) {
								edgeColor = PathwaysFrameConstants.GRAY_COLOR_VALUE;
							} 
							if (!foundReactionsList.contains(displayName)) {
								edgeColor = PathwaysFrameConstants.BLUE_NOT_FOUND_COLOR_VALUE;
							}
							colorMap.put(displayName + "reactant " + Integer.toString(r), edgeColor);
						}
					}
					for (int p = 0; p < pathway.getReactionsData().get(Integer.toString(k)).getProductIds().size(); p++) {
						if (pathway.getMetabolitesData().containsKey((pathway.getReactionsData().get(Integer.toString(k)).getProductIds().get(p)))) {
							String prod = pathway.getMetabolitesData().get((pathway.getReactionsData().get(Integer.toString(k)).getProductIds().get(p))).getName();
							reactionMap.put(displayName + "product " + Integer.toString(p), new String[] {displayName, prod, "true"});
							fluxMap.put(displayName + "product " + Integer.toString(p), edgeThickness(pn.getFluxValue()));
							if (pn.getFluxValue() == 0 && !koReactions.contains(displayName)) {
								edgeColor = PathwaysFrameConstants.GRAY_COLOR_VALUE;
							}
							if (!foundReactionsList.contains(displayName)) {
								edgeColor = PathwaysFrameConstants.BLUE_NOT_FOUND_COLOR_VALUE;
							}
							colorMap.put(displayName + "product " + Integer.toString(p), edgeColor);
						}
					}
				}
			}
		}
    	if (!LocalConfig.getInstance().isGraphMissingMetabolitesSelected()) {
			for (int i = 0; i < metabPosKeys.size(); i++) {
				if (!foundMetabolitesList.contains(metabPosKeys.get(i))) {
					metabPosMap.remove(metabPosKeys.get(i));
				}
			}
		}
    }
    
    public void drawPathwayNames(int component) {
    	if (component == PathwaysFrameConstants.PATHWAYS_COMPONENT) {
			for(int p = 0; p < LocalConfig.getInstance().getPathwayNameMap().size(); p++) {
				String pathwayName = LocalConfig.getInstance().getPathwayNameMap().get(Integer.toString(p)).getName();
				pathwayNames.add(pathwayName);
				PathwayNameNode pnn = new PathwayNameNode();
				double x = 0;
				double y = 0;
				pnn.setDataId(LocalConfig.getInstance().getPathwayNameMap().get(Integer.toString(p)).getId());
				pnn.setName(pathwayName);
				x = startX + PathwaysFrameConstants.HORIZONTAL_INCREMENT*LocalConfig.getInstance().getPathwayNameMap().get(Integer.toString(p)).getLevel();
				y = startY + PathwaysFrameConstants.VERTICAL_INCREMENT*LocalConfig.getInstance().getPathwayNameMap().get(Integer.toString(p)).getLevelPosition();
				pnn.setxPosition(x);
				pnn.setyPosition(y);
				metabPosMap.put(pathwayName, new String[] {Double.toString(x), Double.toString(y)});
			}
		}
    }
    
    public void classifyMetabolite(String type, String metabName, String keggId) {
		if (type.equals(PathwaysCSVFileConstants.MAIN_METABOLITE_TYPE)) {
			metabolites.add(metabName);
		} else if (type.equals(PathwaysCSVFileConstants.SMALL_MAIN_METABOLITE_TYPE)) {
			smallMainMetabolites.add(metabName);
		} else if (type.equals(PathwaysCSVFileConstants.SIDE_METABOLITE_TYPE)) {
			sideMetabolites.add(metabName);
			if (PathwaysFrameConstants.cofactorList.contains(keggId)) {
				cofactors.add(metabName);
			}
		}
    }
    
    public void drawCompartmentBorder(String borderLeftX, String borderRightX, 
    		String borderTopY, String borderBottomY, int startNumber) {
		// draw cell border
    	String topLeft = Integer.toString(startNumber + 1);
    	String topRight = Integer.toString(startNumber + 2);
    	String bottomRight = Integer.toString(startNumber + 3);
    	String bottomLeft = Integer.toString(startNumber + 4);
    	
		metabPosMap.put(topLeft, new String[] {borderLeftX, borderTopY}); 
		metabPosMap.put(topRight, new String[] {borderRightX, borderTopY}); 
		metabPosMap.put(bottomRight, new String[] {borderRightX, borderBottomY});
		metabPosMap.put(bottomLeft, new String[] {borderLeftX, borderBottomY}); 

		reactionMap.put(topLeft, new String[] {topLeft, topRight, "false"});
		reactionMap.put(topRight, new String[] {topRight, bottomRight, "false"});
		reactionMap.put(bottomRight, new String[] {bottomRight, bottomLeft, "false"});
		reactionMap.put(bottomLeft, new String[] {bottomLeft, topLeft, "false"});

		fluxMap.put(topLeft, PathwaysFrameConstants.BORDER_THICKNESS);
		fluxMap.put(topRight, PathwaysFrameConstants.BORDER_THICKNESS);
		fluxMap.put(bottomRight, PathwaysFrameConstants.BORDER_THICKNESS);
		fluxMap.put(bottomLeft, PathwaysFrameConstants.BORDER_THICKNESS);

		for (int b = startNumber + 1; b < startNumber + 5; b++) {
			borderList.add(Integer.toString(b));
		}
    }
    
    public void drawCompartmentLabel(String text, String xOffset, String yOffset) {
    	metabPosMap.put(text, new String[] {xOffset, yOffset});
    }
    
    public void createGraph() {
    	
    }
                                                                                                                     
    /**                                                                                                              
     * create some vertices                                                                                          
     * @param count how many to create                                                                               
     * @return the Vertices in an array                                                                              
     */                                                                                                              
    public void createVertices() {                                                                                  
        for (String met : metabPosMap.keySet()) {
            graph.addVertex(met); 
        } 
    } 
    
    public void removeVertices() {                                                                                  
        for (String met : metabPosMap.keySet()) {
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
        for (int i = 0; i < metaboliteList.size(); i++) {                                                                                                        
        	String name = metaboliteList.get(i);
        	String abbr = LocalConfig.getInstance().getMetaboliteNameAbbrMap().get(name);
        	int width = (int) PathwaysFrameConstants.BORDER_THICKNESS;
    		int height = (int) PathwaysFrameConstants.BORDER_THICKNESS;
        	if (borderList.contains(name)) {
        		width = (int) PathwaysFrameConstants.BORDER_THICKNESS;
        		height = (int) PathwaysFrameConstants.BORDER_THICKNESS;
        	} else if (name.equals(compartmentLabel)) {
        		width = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_WIDTH;
            	height = PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_HEIGHT;
        	} else if (metabolites.contains(name)) {
        		if (!noBorderList.contains(name)) {
        			width = PathwaysFrameConstants.METABOLITE_NODE_WIDTH;
                	height = PathwaysFrameConstants.METABOLITE_NODE_HEIGHT;
        		} else {
        			width = PathwaysFrameConstants.SIDE_NODE_WIDTH;
                	height = PathwaysFrameConstants.SIDE_NODE_HEIGHT;
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
        		graphics.setColor(PathwaysFrameConstants.PATHWAY_NAME_COLOR);
//        		graphics.setFont(new Font("Arial", Font.BOLD, PathwaysFrameConstants.PATHWAY_NAME_NODE_FONT_SIZE));
//            	graphics.drawString(name, 5, 15);
        		alignCenterString(graphics, name, width, PathwaysFrameConstants.PATHWAY_NAME_NODE_XPOS, PathwaysFrameConstants.PATHWAY_NAME_NODE_YPOS, PathwaysFrameConstants.PATHWAY_NAME_NODE_FONT_SIZE);
            	drawBorder(graphics, width, height, PathwaysFrameConstants.PATHWAY_NAME_BORDER_WIDTH);
        	} else if (name.equals(compartmentLabel)) {
        		graphics.setFont(new Font("Arial", Font.TYPE1_FONT, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_FONT_SIZE));
        		graphics.drawString(compartmentLabel, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS);
        		graphics.drawString("Compartment Name: " + LocalConfig.getInstance().getSelectedCompartmentName(), PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS, 
        				PathwaysFrameConstants.COMPARTMENT_LABEL_LINE_OFFSET + PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS);
        		//alignCenterString(graphics, compartmentLabel, width, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_XPOS, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_YPOS, PathwaysFrameConstants.COMPARTMENT_LABEL_NODE_FONT_SIZE);
        		//drawBorder(graphics, width, height, PathwaysFrameConstants.PATHWAY_NAME_BORDER_WIDTH);
        	} else {
        		if (metabolites.contains(name)) {
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
        	if (metabolites.contains(name) || smallMainMetabolites.contains(name)) {
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
        protected Map<E,Number> edge_weight;
       
        public EdgeWeightStrokeFunction(Map<E,Number> edge_weight)
        {
            this.edge_weight = edge_weight;
        }
        
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
        	if (edge_weight.containsKey(context.element)) {
            	value = edge_weight.get(context.element).doubleValue();
        	}
        	double arrowSize = 0.25;
        	if (value >= 1) {
        		arrowSize = Math.sqrt(value)/2;
        	}
        	directed_arrow = ArrowFactory.getNotchedArrow((float) (arrowSize*width), 
        			(float) (arrowSize*length), (float) (arrowSize*notch_depth));
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
    
    Transformer<Number, Paint> colorTransformer = new Transformer<Number, Paint>() {
        //private final Color[] palette = {Color.BLACK, Color.GREEN, Color.BLUE, Color.RED}; 

        public Paint transform(Number i) {
        	if (edge_color.containsKey(i)) {
        		double color = edge_color.get(i).doubleValue();
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
        	}
        	return Color.BLACK;
//            return palette[i.intValue() % palette.length];
        }
    };
    
    public void createNodeInformationDialog(Object arg0) {
    	final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("images/most16.jpg").getImage()); 
		icons.add(new ImageIcon("images/most32.jpg").getImage());

		if (getNodeInformationDialog() != null) {
			getNodeInformationDialog().dispose();
		}
		NodeInformationDialog frame = new NodeInformationDialog(arg0.toString());
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
	
	class PNGFileFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
		}

		public String getDescription() {
			return ".png files";
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