package edu.rutgers.MOST.presentation;

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
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.data.SBMLCompartment;

public class CompartmentNameDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JComboBox<String> cbCytosolName = new JComboBox<String>();
	public JComboBox<String> cbPeriplasmName = new JComboBox<String>();
	public JComboBox<String> cbExtraOrganismName = new JComboBox<String>();
	
	public JButton okButton = new JButton("     OK     ");
	public JButton cancelButton = new JButton("  Cancel  ");

	public CompartmentNameDialog() {

		getRootPane().setDefaultButton(okButton);

		setTitle(CompartmentNameAbbreviationConstants.DIALOG_TITLE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		cbCytosolName.setEditable(false);	
		cbPeriplasmName.setEditable(false);
		cbExtraOrganismName.setEditable(false);	

		cbCytosolName.setPreferredSize(new Dimension(250, 25));
		cbCytosolName.setMaximumSize(new Dimension(250, 25));
		cbCytosolName.setMinimumSize(new Dimension(250, 25));

		cbPeriplasmName.setPreferredSize(new Dimension(250, 25));
		cbPeriplasmName.setMaximumSize(new Dimension(250, 25));
		cbPeriplasmName.setMinimumSize(new Dimension(250, 25));
	
		cbExtraOrganismName.setPreferredSize(new Dimension(250, 25));
		cbExtraOrganismName.setMaximumSize(new Dimension(250, 25));
		cbExtraOrganismName.setMinimumSize(new Dimension(250, 25));

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
//				setVisible(false);
//				dispose();
			}
		};

		cancelButton.addActionListener(cancelButtonActionListener);

	} 
	
    public void populateComboBoxes() {
    	if (LocalConfig.getInstance().getListOfCompartments() != null && 
    			LocalConfig.getInstance().getListOfCompartments().size() > 0) {
    		cbCytosolName.removeAllItems();
    		cbPeriplasmName.removeAllItems();
    		cbExtraOrganismName.removeAllItems();
    		//populate combo boxes
    		for (int c = 0; c < LocalConfig.getInstance().getListOfCompartments().size(); c++) { 
    			cbCytosolName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c).getName());
    			cbPeriplasmName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c).getName());
    			cbExtraOrganismName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c).getName());
    		}
    		cbCytosolName.setSelectedIndex(-1);
    		cbPeriplasmName.setSelectedIndex(-1);
    		cbExtraOrganismName.setSelectedIndex(-1);
    		for (int c = 0; c < LocalConfig.getInstance().getListOfCompartments().size(); c++) {
    			//filters to match compartment names from list of compartments		
    			if((LocalConfig.getInstance().getListOfCompartments().get(c).getName().toLowerCase()).contains(CompartmentsTableConstants.CYTOSOL_FILTER[0])) {
    				cbCytosolName.setSelectedIndex(c);
    			} else if((LocalConfig.getInstance().getListOfCompartments().get(c).getName().toLowerCase()).contains(CompartmentsTableConstants.PERIPLASM_FILTER[0])) {
    				cbPeriplasmName.setSelectedIndex(c);
    			} else if((LocalConfig.getInstance().getListOfCompartments().get(c).getName().toLowerCase()).contains(CompartmentsTableConstants.EXTRA_ORGANISM_FILTER[0])) {
    				cbExtraOrganismName.setSelectedIndex(c);
    			}  
    		}
    	}
    }
    
    public void setSelectedItemByFilter(JComboBox<String> cb, ArrayList<SBMLCompartment> compList, 
    		String[] filter, int index) {
    	if (compList.get(index).getName().contains(filter[0])) {
			cb.setSelectedIndex(index);
		} else {
			cb.setSelectedIndex(-1);
		}
    }
	
	public static void main(String[] args) throws Exception {
		//based on code from http://stackoverflow.com/questions/6403821/how-to-add-an-image-to-a-jframe-title-bar
		final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("images/most16.jpg").getImage()); 
		icons.add(new ImageIcon("images/most32.jpg").getImage());

		CompartmentNameDialog frame = new CompartmentNameDialog();

		frame.setIconImages(icons);
		//frame.setSize(550, 270);
		frame.pack();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}






