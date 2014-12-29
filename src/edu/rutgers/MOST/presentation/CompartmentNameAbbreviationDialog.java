package edu.rutgers.MOST.presentation;

import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.rutgers.MOST.config.LocalConfig;

public class CompartmentNameAbbreviationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JComboBox<String> cbCytosolName = new JComboBox<String>();
	public JComboBox<String> cbPeriplasmName = new JComboBox<String>();
	public JComboBox<String> cbExtraOrganismName = new JComboBox<String>();
	public JComboBox<String> cbCytosol = new JComboBox<String>();
	public JComboBox<String> cbPeriplasm = new JComboBox<String>();
	public JComboBox<String> cbExtraOrganism = new JComboBox<String>();

	public JButton okButton = new JButton("     OK     ");
	public JButton cancelButton = new JButton("  Cancel  ");

	public CompartmentNameAbbreviationDialog() {

		getRootPane().setDefaultButton(okButton);

		setTitle(CompartmentNameAbbreviationConstants.DIALOG_TITLE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		cbCytosol.setEditable(false);	
		cbPeriplasm.setEditable(false);
		cbExtraOrganism.setEditable(false);	
		
		cbCytosolName.setEditable(false);	
		cbPeriplasmName.setEditable(false);
		cbExtraOrganismName.setEditable(false);	

		cbCytosol.setPreferredSize(new Dimension(150, 25));
		cbCytosol.setMaximumSize(new Dimension(150, 25));
		cbCytosol.setMinimumSize(new Dimension(150, 25));
		
		cbCytosolName.setPreferredSize(new Dimension(150, 25));
		cbCytosolName.setMaximumSize(new Dimension(150, 25));
		cbCytosolName.setMinimumSize(new Dimension(150, 25));

		cbPeriplasm.setPreferredSize(new Dimension(150, 25));
		cbPeriplasm.setMaximumSize(new Dimension(150, 25));
		cbPeriplasm.setMinimumSize(new Dimension(150, 25));
		
		cbPeriplasmName.setPreferredSize(new Dimension(150, 25));
		cbPeriplasmName.setMaximumSize(new Dimension(150, 25));
		cbPeriplasmName.setMinimumSize(new Dimension(150, 25));

		cbExtraOrganism.setPreferredSize(new Dimension(150, 25));
		cbExtraOrganism.setMaximumSize(new Dimension(150, 25));
		cbExtraOrganism.setMinimumSize(new Dimension(150, 25));
		
		cbExtraOrganismName.setPreferredSize(new Dimension(150, 25));
		cbExtraOrganismName.setMaximumSize(new Dimension(150, 25));
		cbExtraOrganismName.setMinimumSize(new Dimension(150, 25));

		populateComboBoxes();
		
		//box layout
		Box vb = Box.createVerticalBox();

		Box hbLabels = Box.createHorizontalBox();
		Box hbTop = Box.createHorizontalBox();	    	    
		Box hbCytosolLabel = Box.createHorizontalBox();	    
		Box hbCytosol = Box.createHorizontalBox();
		Box hbPeriplasmLabel = Box.createHorizontalBox();	    
		Box hbMetabolite = Box.createHorizontalBox();
		Box hbExtraOrganismLabel = Box.createHorizontalBox();	    
		Box hbExtraOrganism = Box.createHorizontalBox();
		
		Box vbLabels = Box.createVerticalBox();
		Box vbCombos = Box.createVerticalBox();

		Box hbLabeledCombos = Box.createHorizontalBox();
		Box hbRequiredLabel = Box.createHorizontalBox();
		Box hbButton = Box.createHorizontalBox();

		//top label
		JLabel topLabel = new JLabel();
		topLabel.setText(CompartmentNameAbbreviationConstants.TOP_LABEL);
		topLabel.setSize(new Dimension(300, 30));
		topLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		//top, left, bottom. right
		//topLabel.setBorder(BorderFactory.createEmptyBorder(20,30,20,200));
		//topLabel.setAlignmentX(LEFT_ALIGNMENT);

		hbTop.add(topLabel);	
		hbTop.setAlignmentX(LEFT_ALIGNMENT);

		hbLabels.add(hbTop);

		//cytosol Label and combo
		JLabel cytosolLabel = new JLabel();
		cytosolLabel.setText("Cytosol");
		cytosolLabel.setPreferredSize(new Dimension(150, 25));
		cytosolLabel.setMaximumSize(new Dimension(150, 25));
		cytosolLabel.setMinimumSize(new Dimension(150, 25));
		cytosolLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		cytosolLabel.setAlignmentX(LEFT_ALIGNMENT);
		//cytosolLabel.setAlignmentY(TOP_ALIGNMENT);	    	    

		JPanel panelCytosolLabel = new JPanel();
		panelCytosolLabel.setLayout(new BoxLayout(panelCytosolLabel, BoxLayout.X_AXIS));
		panelCytosolLabel.add(cytosolLabel);
		panelCytosolLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		hbCytosolLabel.add(panelCytosolLabel);
		hbCytosolLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelCytosol = new JPanel();
		panelCytosol.setLayout(new BoxLayout(panelCytosol, BoxLayout.X_AXIS));
		panelCytosol.add(cbCytosol);
		JLabel cytosolBlankLabel = new JLabel("     ");
		panelCytosol.add(cytosolBlankLabel);
		panelCytosol.add(cbCytosolName);
		panelCytosol.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
		panelCytosol.setAlignmentX(RIGHT_ALIGNMENT);

		hbCytosol.add(panelCytosol);
		hbCytosol.setAlignmentX(RIGHT_ALIGNMENT);

		vbLabels.add(hbCytosolLabel);
		JLabel blankLabel1 = new JLabel("");
		vbLabels.add(blankLabel1);
		vbCombos.add(hbCytosol);

		//metabolite Name Label and combo
		JLabel periplasmLabel = new JLabel();
		periplasmLabel.setText("Periplasm");
		periplasmLabel.setPreferredSize(new Dimension(150, 25));
		periplasmLabel.setMaximumSize(new Dimension(150, 25));
		periplasmLabel.setMinimumSize(new Dimension(150, 25));
		periplasmLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		periplasmLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelPeriplasmLabel = new JPanel();
		panelPeriplasmLabel.setLayout(new BoxLayout(panelPeriplasmLabel, BoxLayout.X_AXIS));
		panelPeriplasmLabel.add(periplasmLabel);
		panelPeriplasmLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		hbPeriplasmLabel.add(panelPeriplasmLabel);
		hbPeriplasmLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelPeriplasm = new JPanel();
		panelPeriplasm.setLayout(new BoxLayout(panelPeriplasm, BoxLayout.X_AXIS));
		panelPeriplasm.add(cbPeriplasm);
		JLabel periplasmBlankLabel = new JLabel("     ");
		panelPeriplasm.add(periplasmBlankLabel);
		panelPeriplasm.add(cbPeriplasmName);
		panelPeriplasm.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
		panelPeriplasm.setAlignmentX(RIGHT_ALIGNMENT);

		hbMetabolite.add(panelPeriplasm);
		hbMetabolite.setAlignmentX(RIGHT_ALIGNMENT);

		vbLabels.add(hbPeriplasmLabel);
		JLabel blankLabel2 = new JLabel("");
		vbLabels.add(blankLabel2);
		vbCombos.add(hbMetabolite);

		//extraOrganism label and combo
		JLabel extraOrganismLabel = new JLabel();
		extraOrganismLabel.setText("Extra Organism");
		extraOrganismLabel.setPreferredSize(new Dimension(150, 25));
		extraOrganismLabel.setMaximumSize(new Dimension(150, 25));
		extraOrganismLabel.setMinimumSize(new Dimension(150, 25));
		extraOrganismLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		extraOrganismLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelExtraOrganismLabel = new JPanel();
		panelExtraOrganismLabel.setLayout(new BoxLayout(panelExtraOrganismLabel, BoxLayout.X_AXIS));
		panelExtraOrganismLabel.add(extraOrganismLabel);
		panelExtraOrganismLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));

		hbExtraOrganismLabel.add(panelExtraOrganismLabel);
		hbExtraOrganismLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelExtraOrganism = new JPanel();
		panelExtraOrganism.setLayout(new BoxLayout(panelExtraOrganism, BoxLayout.X_AXIS));
		panelExtraOrganism.add(cbExtraOrganism);
		JLabel extraOrgBlankLabel = new JLabel("     ");
		panelExtraOrganism.add(extraOrgBlankLabel);
		panelExtraOrganism.add(cbExtraOrganismName);
		panelExtraOrganism.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
		panelExtraOrganism.setAlignmentX(RIGHT_ALIGNMENT);

		hbExtraOrganism.add(panelExtraOrganism);
		hbExtraOrganism.setAlignmentX(RIGHT_ALIGNMENT);

		vbLabels.add(hbExtraOrganismLabel);
		JLabel blankLabel3 = new JLabel("");
		vbLabels.add(blankLabel3);
		vbCombos.add(hbExtraOrganism);
		
		okButton.setMnemonic(KeyEvent.VK_O);
		JLabel blank = new JLabel("    "); 
		cancelButton.setMnemonic(KeyEvent.VK_C);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(okButton);
		buttonPanel.add(blank);
		buttonPanel.add(cancelButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

		hbButton.add(buttonPanel);

		vb.add(hbLabels);
		hbLabeledCombos.add(vbLabels);
		hbLabeledCombos.add(vbCombos);
		vb.add(hbLabeledCombos);
		vb.add(hbRequiredLabel);
		vb.add(hbButton);

		add(vb);
		
		ActionListener okButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
					
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
	
    public void populateComboBoxes() {
    	if (LocalConfig.getInstance().getCompartmentAbbreviationList().size() > 0) {
    		cbCytosol.removeAllItems();
    		cbPeriplasm.removeAllItems();
    		cbExtraOrganism.removeAllItems();
    		//add all column names to from file comboboxes
    		for (int c = 0; c < LocalConfig.getInstance().getCompartmentAbbreviationList().size(); c++) { 
    			cbCytosol.addItem(LocalConfig.getInstance().getCompartmentAbbreviationList().get(c));
    			cbPeriplasm.addItem(LocalConfig.getInstance().getCompartmentAbbreviationList().get(c));
    			cbExtraOrganism.addItem(LocalConfig.getInstance().getCompartmentAbbreviationList().get(c));
    		}
    		cbCytosol.setSelectedIndex(-1);
    		cbPeriplasm.setSelectedIndex(-1);
    		cbExtraOrganism.setSelectedIndex(-1);
    	}
    	if (LocalConfig.getInstance().getListOfCompartments().size() > 0) {
    		cbCytosolName.removeAllItems();
    		cbPeriplasmName.removeAllItems();
    		cbExtraOrganismName.removeAllItems();
    		//add all column names to from file comboboxes
    		for (int c = 0; c < LocalConfig.getInstance().getListOfCompartments().size(); c++) { 
    			cbCytosolName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c));
    			cbPeriplasmName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c));
    			cbExtraOrganismName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c));
    		}
    		cbCytosolName.setSelectedIndex(-1);
    		cbPeriplasmName.setSelectedIndex(-1);
    		cbExtraOrganismName.setSelectedIndex(-1);
    	}
    }
	
	public static void main(String[] args) throws Exception {
		//based on code from http://stackoverflow.com/questions/6403821/how-to-add-an-image-to-a-jframe-title-bar
//		final ArrayList<Image> icons = new ArrayList<Image>(); 
//		icons.add(new ImageIcon("images/most16.jpg").getImage()); 
//		icons.add(new ImageIcon("images/most32.jpg").getImage());
//
//		CompartmentNameAbbreviationDialog frame = new CompartmentNameAbbreviationDialog();
//
//		frame.setIconImages(icons);
//		//frame.setSize(550, 270);
//		frame.pack();
//		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
	}
}






