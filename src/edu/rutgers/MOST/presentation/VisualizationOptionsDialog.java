package edu.rutgers.MOST.presentation;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.rutgers.MOST.config.LocalConfig;

public class VisualizationOptionsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JCheckBox graphMissingMetabolitesCheckBox = new JCheckBox("Graph Missing Metabolites");
	// only enabled if graphMissingReactions is checked
	private JCheckBox highlightMissingMetabolitesCheckBox = new JCheckBox("Highlight Missing Metabolites");
	
	private JCheckBox graphMissingReactionsCheckBox = new JCheckBox("Graph Missing Reactions");
	// only enabled if graphMissingReactions is checked
	private JCheckBox highlightMissingReactionsCheckBox = new JCheckBox("Highlight Missing Reactions");
	
	private JCheckBox scaleEdgeThicknessCheckBox = new JCheckBox("Scale Edge Thickness Using Flux Values");
	
	// Proton cannot be ignored unless reactions and metabolites positions databases are updated
	// to include protons. Protons currently omitted in most reactions.
//	private JCheckBox ignoreProtonCheckBox = new JCheckBox("Ignore Proton for Reaction Matching");
	private JCheckBox ignoreWaterCheckBox = new JCheckBox("Ignore Water for Reaction Matching");
	
	public JButton okButton = new JButton("    OK    ");
	public JButton cancelButton = new JButton("  Cancel  ");

	public VisualizationOptionsDialog() {
		
		getRootPane().setDefaultButton(okButton);
		
		setTitle(VisualizationOptionsConstants.VISUALIZATION_OPTIONS_DIALOG_TITLE);
		
		// set check box defaults
		graphMissingMetabolitesCheckBox.setSelected(LocalConfig.getInstance().isGraphMissingMetabolitesSelected());
		highlightMissingMetabolitesCheckBox.setSelected(LocalConfig.getInstance().isHighlightMissingMetabolitesSelected());
		graphMissingReactionsCheckBox.setSelected(LocalConfig.getInstance().isGraphMissingReactionsSelected());
		highlightMissingReactionsCheckBox.setSelected(LocalConfig.getInstance().isHighlightMissingReactionsSelected());
		scaleEdgeThicknessCheckBox.setSelected(LocalConfig.getInstance().isScaleEdgeThicknessSelected());
//		ignoreProtonCheckBox.setSelected(LocalConfig.getInstance().isIgnoreProtonSelected());
		ignoreWaterCheckBox.setSelected(LocalConfig.getInstance().isIgnoreWaterSelected());
		
		graphMissingMetabolitesCheckBox.setMnemonic(KeyEvent.VK_G);
		highlightMissingMetabolitesCheckBox.setMnemonic(KeyEvent.VK_M);
		graphMissingReactionsCheckBox.setMnemonic(KeyEvent.VK_R);
		highlightMissingReactionsCheckBox.setMnemonic(KeyEvent.VK_H);
		scaleEdgeThicknessCheckBox.setMnemonic(KeyEvent.VK_S);
//		ignoreProtonCheckBox.setMnemonic(KeyEvent.VK_I);
		ignoreWaterCheckBox.setMnemonic(KeyEvent.VK_W);
		
		//box layout
		Box vb = Box.createVerticalBox();

		Box hbGraphMissingMetabolites = Box.createHorizontalBox();
		Box hbHighlightMissingMetabolites = Box.createHorizontalBox();
		Box hbGraphMissingReactions = Box.createHorizontalBox();
		Box hbHighlightMissingReactions = Box.createHorizontalBox();
		Box hbScaleEdgeThickness = Box.createHorizontalBox();
//		Box hbIgnoreProton = Box.createHorizontalBox();
		Box hbIgnoreWater = Box.createHorizontalBox();
		Box hbButton = Box.createHorizontalBox();
		
		JPanel hbGraphMissingMetabolitesPanel = new JPanel();
		hbGraphMissingMetabolitesPanel.setLayout(new BoxLayout(hbGraphMissingMetabolitesPanel, BoxLayout.X_AXIS));
		hbGraphMissingMetabolitesPanel.add(graphMissingMetabolitesCheckBox);
		hbGraphMissingMetabolitesPanel.setBorder(BorderFactory.createEmptyBorder(
				VisualizationOptionsConstants.TOP_BORDER, 
				VisualizationOptionsConstants.LEFT_BORDER, 
				VisualizationOptionsConstants.SUB_TOPIC_GAP, 
				VisualizationOptionsConstants.RIGHT_BORDER));

		hbGraphMissingMetabolites.add(leftJustify(hbGraphMissingMetabolitesPanel));
		
		JPanel hbHighlightMissingMetabolitesPanel = new JPanel();
		hbHighlightMissingMetabolitesPanel.setLayout(new BoxLayout(hbHighlightMissingMetabolitesPanel, BoxLayout.X_AXIS));
		hbHighlightMissingMetabolitesPanel.add(highlightMissingMetabolitesCheckBox);
		hbHighlightMissingMetabolitesPanel.setBorder(BorderFactory.createEmptyBorder(0, 
				VisualizationOptionsConstants.LEFT_BORDER + VisualizationOptionsConstants.LEFT_INDENT, 
				VisualizationOptionsConstants.MAIN_TOPIC_GAP, 
				VisualizationOptionsConstants.RIGHT_BORDER));

		hbHighlightMissingMetabolites.add(leftJustify(hbHighlightMissingMetabolitesPanel));
		
		JPanel hbGraphMissingReactionsPanel = new JPanel();
		hbGraphMissingReactionsPanel.setLayout(new BoxLayout(hbGraphMissingReactionsPanel, BoxLayout.X_AXIS));
		hbGraphMissingReactionsPanel.add(graphMissingReactionsCheckBox);
		hbGraphMissingReactionsPanel.setBorder(BorderFactory.createEmptyBorder(
				VisualizationOptionsConstants.TOP_BORDER, 
				VisualizationOptionsConstants.LEFT_BORDER, 
				VisualizationOptionsConstants.SUB_TOPIC_GAP, 
				VisualizationOptionsConstants.RIGHT_BORDER));

		hbGraphMissingReactions.add(leftJustify(hbGraphMissingReactionsPanel));
		
		JPanel hbHighlightMissingReactionsPanel = new JPanel();
		hbHighlightMissingReactionsPanel.setLayout(new BoxLayout(hbHighlightMissingReactionsPanel, BoxLayout.X_AXIS));
		hbHighlightMissingReactionsPanel.add(highlightMissingReactionsCheckBox);
		hbHighlightMissingReactionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 
				VisualizationOptionsConstants.LEFT_BORDER + VisualizationOptionsConstants.LEFT_INDENT, 
				VisualizationOptionsConstants.SUB_TOPIC_GAP, 
				VisualizationOptionsConstants.RIGHT_BORDER));

		hbHighlightMissingReactions.add(leftJustify(hbHighlightMissingReactionsPanel));
		
		JPanel hbScaleEdgeThicknessPanel = new JPanel();
		hbScaleEdgeThicknessPanel.setLayout(new BoxLayout(hbScaleEdgeThicknessPanel, BoxLayout.X_AXIS));
		hbScaleEdgeThicknessPanel.add(scaleEdgeThicknessCheckBox);
		hbScaleEdgeThicknessPanel.setBorder(BorderFactory.createEmptyBorder(0, 
				VisualizationOptionsConstants.LEFT_BORDER, 
				VisualizationOptionsConstants.MAIN_TOPIC_GAP, 
				VisualizationOptionsConstants.RIGHT_BORDER));

		hbScaleEdgeThickness.add(leftJustify(hbScaleEdgeThicknessPanel));
		
//		JPanel hbIgnoreProtonPanel = new JPanel();
//		hbIgnoreProtonPanel.setLayout(new BoxLayout(hbIgnoreProtonPanel, BoxLayout.X_AXIS));
//		hbIgnoreProtonPanel.add(ignoreProtonCheckBox);
//		hbIgnoreProtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 
//				VisualizationOptionsConstants.LEFT_BORDER, 
//				VisualizationOptionsConstants.MAIN_TOPIC_GAP, 
//				VisualizationOptionsConstants.RIGHT_BORDER));
//
//		hbIgnoreProton.add(leftJustify(hbIgnoreProtonPanel));
		
		JPanel hbIgnoreWaterPanel = new JPanel();
		hbIgnoreWaterPanel.setLayout(new BoxLayout(hbIgnoreWaterPanel, BoxLayout.X_AXIS));
		hbIgnoreWaterPanel.add(ignoreWaterCheckBox);
		hbIgnoreWaterPanel.setBorder(BorderFactory.createEmptyBorder(0, 
				VisualizationOptionsConstants.LEFT_BORDER, 
				VisualizationOptionsConstants.MAIN_TOPIC_GAP, 
				VisualizationOptionsConstants.RIGHT_BORDER));

		hbIgnoreWater.add(leftJustify(hbIgnoreWaterPanel));
		
		okButton.setMnemonic(KeyEvent.VK_O);
		JLabel blank = new JLabel("    "); 
		cancelButton.setMnemonic(KeyEvent.VK_C);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(okButton);
		buttonPanel.add(blank);
		buttonPanel.add(cancelButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,20,VisualizationOptionsConstants.BOTTOM_BORDER,20));

		hbButton.add(buttonPanel);

		vb.add(hbGraphMissingMetabolites);
		vb.add(hbHighlightMissingMetabolites);
		vb.add(hbGraphMissingReactions);
		vb.add(hbHighlightMissingReactions);
		vb.add(hbScaleEdgeThickness);
//		vb.add(hbIgnoreProton);
		vb.add(hbIgnoreWater);
		vb.add(hbButton);
		add(vb);
    	
    	ActionListener graphMissingMetabolitesActionListener = new ActionListener() {
    		public void actionPerformed(ActionEvent actionEvent) {
    			setGraphMissingMetabolitesDefaults();							
    		}
    	};
    	
    	graphMissingMetabolitesCheckBox.addActionListener(graphMissingMetabolitesActionListener);
    	
    	ActionListener graphMissingReactionsActionListener = new ActionListener() {
    		public void actionPerformed(ActionEvent actionEvent) {
    			setGraphMissingReactionsDefaults();							
    		}
    	};
    	
    	graphMissingReactionsCheckBox.addActionListener(graphMissingReactionsActionListener);
    	
    	ActionListener okButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				LocalConfig.getInstance().setGraphMissingReactionsSelected(graphMissingReactionsCheckBox.isSelected());
				LocalConfig.getInstance().setHighlightMissingReactionsSelected(highlightMissingReactionsCheckBox.isSelected());
				LocalConfig.getInstance().setGraphMissingMetabolitesSelected(graphMissingMetabolitesCheckBox.isSelected());
				LocalConfig.getInstance().setHighlightMissingMetabolitesSelected(highlightMissingMetabolitesCheckBox.isSelected());
				LocalConfig.getInstance().setScaleEdgeThicknessSelected(scaleEdgeThicknessCheckBox.isSelected());
//				LocalConfig.getInstance().setIgnoreProtonSelected(ignoreProtonCheckBox.isSelected());
				LocalConfig.getInstance().setIgnoreWaterSelected(ignoreWaterCheckBox.isSelected());
				setVisible(false);
				dispose();
//				System.out.println(LocalConfig.getInstance().isGraphMissingReactionsSelected());
//				System.out.println(LocalConfig.getInstance().isHighlightMissingReactionsSelected());
//				System.out.println(LocalConfig.getInstance().isScaleEdgeThicknessSelected());
//				System.out.println(LocalConfig.getInstance().isIgnoreWaterSelected());
			}
		}; 
		
		okButton.addActionListener(okButtonActionListener);
    	
    	ActionListener cancelButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				setVisible(false);
				dispose();
			}
		}; 
		
		cancelButton.addActionListener(cancelButtonActionListener);
		
	}
	
	/**
	 * Set sub topic check boxes to correct values based on main topic 
	 */
	public void setGraphMissingReactionsDefaults() {
		if (graphMissingReactionsCheckBox.isSelected()) {
			highlightMissingReactionsCheckBox.setSelected(VisualizationOptionsConstants.HIGHLIGHT_MISSING_REACTIONS_DEFAULT);
			highlightMissingReactionsCheckBox.setEnabled(true);
		} else {
			highlightMissingReactionsCheckBox.setSelected(VisualizationOptionsConstants.HIGHLIGHT_MISSING_REACTIONS_GRAYED_DEFAULT);
			highlightMissingReactionsCheckBox.setEnabled(false);
		}
	}
	
	public void setGraphMissingMetabolitesDefaults() {
		if (graphMissingMetabolitesCheckBox.isSelected()) {
			highlightMissingMetabolitesCheckBox.setSelected(VisualizationOptionsConstants.HIGHLIGHT_MISSING_METABOLITES_DEFAULT);
			highlightMissingMetabolitesCheckBox.setEnabled(true);
			graphMissingReactionsCheckBox.setEnabled(true);
		} else {
			highlightMissingMetabolitesCheckBox.setSelected(VisualizationOptionsConstants.HIGHLIGHT_MISSING_METABOLITES_GRAYED_DEFAULT);
			highlightMissingMetabolitesCheckBox.setEnabled(false);
			// only possible to graph missing reactions if graph missing metabolites
			// selected. otherwise metabolite node that connects to reaction may be null
			graphMissingReactionsCheckBox.setEnabled(false);
			graphMissingReactionsCheckBox.setSelected(false);
			setGraphMissingReactionsDefaults();
		}
	}
	
	/**
	 * Left Justifies component in a panel
	 * @param panel
	 * @return
	 */
	// from http://stackoverflow.com/questions/9212155/java-boxlayout-panels-alignment
	private Component leftJustify(JPanel panel)  {
	    Box  b = Box.createHorizontalBox();
	    b.add( panel );
	    b.add( Box.createHorizontalGlue() );
	    // (Note that you could throw a lot more components
	    // and struts and glue in here.)
	    return b;
	}
	
	public static void main(String[] args) throws Exception {
		//based on code from http:stackoverflow.com/questions/6403821/how-to-add-an-image-to-a-jframe-title-bar
		final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("etc/most16.jpg").getImage()); 
		icons.add(new ImageIcon("etc/most32.jpg").getImage());
		
		VisualizationOptionsDialog d = new VisualizationOptionsDialog();
		
		d.setIconImages(icons);
    	d.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	//d.setSize(400, 300);
    	d.pack();
    	d.setLocationRelativeTo(null);
    	d.setVisible(true);

	}
	
}








