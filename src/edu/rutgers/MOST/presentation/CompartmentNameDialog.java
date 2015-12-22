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
	public JComboBox<String> cbCompartmentName = new JComboBox<String>();
//	public JComboBox<String> cbMembraneName = new JComboBox<String>();
//	public JComboBox<String> cbOutsideName = new JComboBox<String>();
	
	public JButton okButton = new JButton("     OK     ");
	public JButton cancelButton = new JButton("  Cancel  ");

	public CompartmentNameDialog() {

		getRootPane().setDefaultButton(okButton);

		setTitle(CompartmentNameAbbreviationConstants.DIALOG_TITLE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		cbCompartmentName.setEditable(false);	
//		cbMembraneName.setEditable(false);
//		cbOutsideName.setEditable(false);	

		cbCompartmentName.setPreferredSize(new Dimension(250, 25));
		cbCompartmentName.setMaximumSize(new Dimension(250, 25));
		cbCompartmentName.setMinimumSize(new Dimension(250, 25));

//		cbMembraneName.setPreferredSize(new Dimension(250, 25));
//		cbMembraneName.setMaximumSize(new Dimension(250, 25));
//		cbMembraneName.setMinimumSize(new Dimension(250, 25));
//	
//		cbOutsideName.setPreferredSize(new Dimension(250, 25));
//		cbOutsideName.setMaximumSize(new Dimension(250, 25));
//		cbOutsideName.setMinimumSize(new Dimension(250, 25));

		populateComboBoxes();
		
		//box layout
		Box vb = Box.createVerticalBox();

		Box hbLabels = Box.createHorizontalBox();
		Box hbTop = Box.createHorizontalBox();	    	    
		Box hbCompartmentLabel = Box.createHorizontalBox();	    
		Box hbCompartment = Box.createHorizontalBox();
//		Box hbMembraneLabel = Box.createHorizontalBox();	    
//		Box hbMembrane = Box.createHorizontalBox();
//		Box hbOutsideLabel = Box.createHorizontalBox();	    
//		Box hbOutside = Box.createHorizontalBox();
		
		Box vbLabels = Box.createVerticalBox();
		Box vbCombos = Box.createVerticalBox();

		Box hbLabeledCombos = Box.createHorizontalBox();
		Box hbButton = Box.createHorizontalBox();

		//top label
		JLabel topLabel = new JLabel();
		topLabel.setText(CompartmentNameAbbreviationConstants.TOP_LABEL);
		topLabel.setSize(new Dimension(300, 30));
		topLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		hbTop.add(topLabel);	
		hbTop.setAlignmentX(LEFT_ALIGNMENT);

		hbLabels.add(hbTop);

		//compartment Label and combo
		JLabel compartmentLabel = new JLabel();
		compartmentLabel.setText(CompartmentsConstants.COMPARTMENT_NAME_LABEL);
		compartmentLabel.setPreferredSize(new Dimension(150, 25));
		compartmentLabel.setMaximumSize(new Dimension(150, 25));
		compartmentLabel.setMinimumSize(new Dimension(150, 25));
		compartmentLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		compartmentLabel.setAlignmentX(LEFT_ALIGNMENT);
		//compartmentLabel.setAlignmentY(TOP_ALIGNMENT);	    	    

		JPanel panelCompartmentLabel = new JPanel();
		panelCompartmentLabel.setLayout(new BoxLayout(panelCompartmentLabel, BoxLayout.X_AXIS));
		panelCompartmentLabel.add(compartmentLabel);
		panelCompartmentLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		hbCompartmentLabel.add(panelCompartmentLabel);
		hbCompartmentLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelCytosol = new JPanel();
		panelCytosol.setLayout(new BoxLayout(panelCytosol, BoxLayout.X_AXIS));
		panelCytosol.add(cbCompartmentName);
		panelCytosol.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
		panelCytosol.setAlignmentX(RIGHT_ALIGNMENT);

		hbCompartment.add(panelCytosol);
		hbCompartment.setAlignmentX(RIGHT_ALIGNMENT);

		vbLabels.add(hbCompartmentLabel);
		JLabel blankLabel1 = new JLabel("");
		vbLabels.add(blankLabel1);
		vbCombos.add(hbCompartment);

		//membrane Name Label and combo
//		JLabel membraneLabel = new JLabel();
//		membraneLabel.setText(CompartmentsConstants.MEMBRANE_NAME_LABEL);
//		membraneLabel.setPreferredSize(new Dimension(150, 25));
//		membraneLabel.setMaximumSize(new Dimension(150, 25));
//		membraneLabel.setMinimumSize(new Dimension(150, 25));
//		membraneLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
//		membraneLabel.setAlignmentX(LEFT_ALIGNMENT);
//
//		JPanel panelMembraneLabel = new JPanel();
//		panelMembraneLabel.setLayout(new BoxLayout(panelMembraneLabel, BoxLayout.X_AXIS));
//		panelMembraneLabel.add(membraneLabel);
//		panelMembraneLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
//
//		hbMembraneLabel.add(panelMembraneLabel);
//		hbMembraneLabel.setAlignmentX(LEFT_ALIGNMENT);
//
//		JPanel panelMembrane = new JPanel();
//		panelMembrane.setLayout(new BoxLayout(panelMembrane, BoxLayout.X_AXIS));
//		panelMembrane.add(cbMembraneName);
//		panelMembrane.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
//		panelMembrane.setAlignmentX(RIGHT_ALIGNMENT);
//
//		hbMembrane.add(panelMembrane);
//		hbMembrane.setAlignmentX(RIGHT_ALIGNMENT);
//
//		vbLabels.add(hbMembraneLabel);
//		JLabel blankLabel2 = new JLabel("");
//		vbLabels.add(blankLabel2);
//		vbCombos.add(hbMembrane);

		//outside label and combo
//		JLabel outsideLabel = new JLabel();
//		outsideLabel.setText(CompartmentsConstants.OUTSIDE_NAME_LABEL);
//		outsideLabel.setPreferredSize(new Dimension(150, 25));
//		outsideLabel.setMaximumSize(new Dimension(150, 25));
//		outsideLabel.setMinimumSize(new Dimension(150, 25));
//		outsideLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
//		outsideLabel.setAlignmentX(LEFT_ALIGNMENT);
//
//		JPanel panelOutsideLabel = new JPanel();
//		panelOutsideLabel.setLayout(new BoxLayout(panelOutsideLabel, BoxLayout.X_AXIS));
//		panelOutsideLabel.add(outsideLabel);
//		panelOutsideLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
//
//		hbOutsideLabel.add(panelOutsideLabel);
//		hbOutsideLabel.setAlignmentX(LEFT_ALIGNMENT);
//
//		JPanel panelExtraOrganism = new JPanel();
//		panelExtraOrganism.setLayout(new BoxLayout(panelExtraOrganism, BoxLayout.X_AXIS));
//		panelExtraOrganism.add(cbOutsideName);
//		panelExtraOrganism.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
//		panelExtraOrganism.setAlignmentX(RIGHT_ALIGNMENT);
//
//		hbOutside.add(panelExtraOrganism);
//		hbOutside.setAlignmentX(RIGHT_ALIGNMENT);
//
//		vbLabels.add(hbOutsideLabel);
//		JLabel blankLabel3 = new JLabel("");
//		vbLabels.add(blankLabel3);
//		vbCombos.add(hbOutside);
		
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
    		cbCompartmentName.removeAllItems();
//    		cbMembraneName.removeAllItems();
//    		cbOutsideName.removeAllItems();
    		//populate combo boxes
    		for (int c = 0; c < LocalConfig.getInstance().getListOfCompartments().size(); c++) { 
    			cbCompartmentName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c).getName());
//    			cbMembraneName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c).getName());
//    			cbOutsideName.addItem(LocalConfig.getInstance().getListOfCompartments().get(c).getName());
    		}
    		// add empty item
    		cbCompartmentName.addItem("");
//			cbMembraneName.addItem("");
//			cbOutsideName.addItem("");
    		cbCompartmentName.setSelectedIndex(-1);
//    		cbMembraneName.setSelectedIndex(-1);
//    		cbOutsideName.setSelectedIndex(-1);
    		for (int c = 0; c < LocalConfig.getInstance().getListOfCompartments().size(); c++) {
    			//filters to match compartment names from list of compartments		
    			if((LocalConfig.getInstance().getListOfCompartments().get(c).getName().toLowerCase()).contains(CompartmentsConstants.CYTOSOL_FILTER[0])) {
    				cbCompartmentName.setSelectedIndex(c);
    			}
//    			} else if((LocalConfig.getInstance().getListOfCompartments().get(c).getName().toLowerCase()).contains(CompartmentsConstants.PERIPLASM_FILTER[0])) {
//    				cbMembraneName.setSelectedIndex(c);
//    			} else if((LocalConfig.getInstance().getListOfCompartments().get(c).getName().toLowerCase()).contains(CompartmentsConstants.EXTRA_ORGANISM_FILTER[0])) {
//    				cbOutsideName.setSelectedIndex(c);
//    			}  
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






