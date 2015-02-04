package edu.rutgers.MOST.presentation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReactionsSupplementalDataDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JComboBox<String> cbReactionAbbreviation = new JComboBox<String>();
	public JComboBox<String> cbECNumber = new JComboBox<String>();
	public JCheckBox replaceCheckBox = new JCheckBox("Replace Illegal Characters with Underscore");
	public JComboBox<String> cbTrimBeginFromFile = new JComboBox<String>();
	public JComboBox<String> cbTrimEndFromFile = new JComboBox<String>();
	public JComboBox<String> cbTrimBeginFromModel = new JComboBox<String>();
	public JComboBox<String> cbTrimEndFromModel = new JComboBox<String>();
	
	public JButton okButton = new JButton("     OK     ");
	public JButton cancelButton = new JButton("  Cancel  ");
	
	private File loadedFile;

	public File getLoadedFile() {
		return loadedFile;
	}

	public void setLoadedFile(File loadedFile) {
		this.loadedFile = loadedFile;
	}

	public ReactionsSupplementalDataDialog(ArrayList<String> columnNamesFromFile) {
		
		getRootPane().setDefaultButton(okButton);
		
		setTitle("Reactions Supplemental Data Dialog");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		cbReactionAbbreviation.setEditable(false);	
		cbECNumber.setEditable(false);
		cbTrimBeginFromFile.setEditable(false);
		cbTrimEndFromFile.setEditable(false);
		cbTrimBeginFromModel.setEditable(false);
		cbTrimEndFromModel.setEditable(false);

		cbReactionAbbreviation.setPreferredSize(new Dimension(250, 25));
		cbReactionAbbreviation.setMaximumSize(new Dimension(250, 25));
		cbReactionAbbreviation.setMinimumSize(new Dimension(250, 25));

		cbECNumber.setPreferredSize(new Dimension(250, 25));
		cbECNumber.setMaximumSize(new Dimension(250, 25));
		cbECNumber.setMinimumSize(new Dimension(250, 25));
		
		cbTrimBeginFromFile.setPreferredSize(new Dimension(40, 25));
		cbTrimBeginFromFile.setMaximumSize(new Dimension(40, 25));
		cbTrimBeginFromFile.setMinimumSize(new Dimension(40, 25));
		
		cbTrimEndFromFile.setPreferredSize(new Dimension(40, 25));
		cbTrimEndFromFile.setMaximumSize(new Dimension(40, 25));
		cbTrimEndFromFile.setMinimumSize(new Dimension(40, 25));
		
		cbTrimBeginFromModel.setPreferredSize(new Dimension(40, 25));
		cbTrimBeginFromModel.setMaximumSize(new Dimension(40, 25));
		cbTrimBeginFromModel.setMinimumSize(new Dimension(40, 25));
		
		cbTrimEndFromModel.setPreferredSize(new Dimension(40, 25));
		cbTrimEndFromModel.setMaximumSize(new Dimension(40, 25));
		cbTrimEndFromModel.setMinimumSize(new Dimension(40, 25));
		
		for (int i = 0; i < 5; i++) {
			cbTrimBeginFromFile.addItem(Integer.toString(i));
			cbTrimEndFromFile.addItem(Integer.toString(i));
			cbTrimBeginFromModel.addItem(Integer.toString(i));
			cbTrimEndFromModel.addItem(Integer.toString(i));
		}
		
		replaceCheckBox.setSelected(true);
		
		populateNamesFromFileBoxes(columnNamesFromFile);
		
		//box layout
		Box vb = Box.createVerticalBox();

		Box hbLabels = Box.createHorizontalBox();
		Box hbTop = Box.createHorizontalBox();	 
		Box hbReactionAbbreviationLabel = Box.createHorizontalBox();	    
		Box hbReactionAbbreviation = Box.createHorizontalBox();
		Box hbECNumberLabel = Box.createHorizontalBox();	    
		Box hbECNumber = Box.createHorizontalBox();
		
		Box vbLabels = Box.createVerticalBox();
		Box vbCombos = Box.createVerticalBox();

		Box hbLabeledCombos = Box.createHorizontalBox();
		Box hbReacAbbrParametersLabel = Box.createHorizontalBox();
		Box hbReplaceBox = Box.createHorizontalBox();
		Box hbTrimFromFileBox = Box.createHorizontalBox();
		Box hbTrimFromModelBox = Box.createHorizontalBox();
		Box hbButton = Box.createHorizontalBox();
		
		//top label
		JLabel topLabel = new JLabel();
		topLabel.setText(ColumnInterfaceConstants.METABOLITES_TOP_LABEL);
		topLabel.setSize(new Dimension(300, 30));
		//top, left, bottom. right
		topLabel.setBorder(BorderFactory.createEmptyBorder(20,30,20,200));
		topLabel.setAlignmentX(LEFT_ALIGNMENT);

		hbTop.add(topLabel);	
		hbTop.setAlignmentX(LEFT_ALIGNMENT);

		hbLabels.add(hbTop);
		
		//reaction Abbreviation Label and combo
		JLabel reactionAbbreviationLabel = new JLabel();
		reactionAbbreviationLabel.setText("Reaction Abbreviation Column");
		reactionAbbreviationLabel.setPreferredSize(new Dimension(250, 25));
		reactionAbbreviationLabel.setMaximumSize(new Dimension(250, 25));
		reactionAbbreviationLabel.setMinimumSize(new Dimension(250, 25));
		reactionAbbreviationLabel.setBorder(BorderFactory.createEmptyBorder(10,0,ColumnInterfaceConstants.LABEL_BOTTOM_BORDER_SIZE,10));
		reactionAbbreviationLabel.setAlignmentX(LEFT_ALIGNMENT);  	    

		JPanel panelReactionAbbreviationLabel = new JPanel();
		panelReactionAbbreviationLabel.setLayout(new BoxLayout(panelReactionAbbreviationLabel, BoxLayout.X_AXIS));
		panelReactionAbbreviationLabel.add(reactionAbbreviationLabel);
		panelReactionAbbreviationLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		hbReactionAbbreviationLabel.add(panelReactionAbbreviationLabel);
		hbReactionAbbreviationLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelReactionAbbreviation = new JPanel();
		panelReactionAbbreviation.setLayout(new BoxLayout(panelReactionAbbreviation, BoxLayout.X_AXIS));
		panelReactionAbbreviation.add(cbReactionAbbreviation);
		panelReactionAbbreviation.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		panelReactionAbbreviation.setAlignmentX(RIGHT_ALIGNMENT);

		hbReactionAbbreviation.add(panelReactionAbbreviation);
		hbReactionAbbreviation.setAlignmentX(RIGHT_ALIGNMENT);

		vbLabels.add(hbReactionAbbreviationLabel);
		JLabel blankLabel1 = new JLabel("");
		vbLabels.add(blankLabel1);
		vbCombos.add(hbReactionAbbreviation);

		//EC Number Label and combo Id
		JLabel ecNumberLabel = new JLabel();
		ecNumberLabel.setText("EC Number Column");
		ecNumberLabel.setPreferredSize(new Dimension(250, 25));
		ecNumberLabel.setMaximumSize(new Dimension(250, 25));
		ecNumberLabel.setMinimumSize(new Dimension(250, 25));
		ecNumberLabel.setBorder(BorderFactory.createEmptyBorder(10,0,ColumnInterfaceConstants.LABEL_BOTTOM_BORDER_SIZE,10));
		ecNumberLabel.setAlignmentX(LEFT_ALIGNMENT);    	    

		JPanel panelECNumberLabel = new JPanel();
		panelECNumberLabel.setLayout(new BoxLayout(panelECNumberLabel, BoxLayout.X_AXIS));
		panelECNumberLabel.add(ecNumberLabel);
		panelECNumberLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));

		hbECNumberLabel.add(panelECNumberLabel);
		hbECNumberLabel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel panelECNumber = new JPanel();
		panelECNumber.setLayout(new BoxLayout(panelECNumber, BoxLayout.X_AXIS));
		panelECNumber.add(cbECNumber);
		panelECNumber.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		panelECNumber.setAlignmentX(RIGHT_ALIGNMENT);

		hbECNumber.add(panelECNumber);
		hbECNumber.setAlignmentX(RIGHT_ALIGNMENT);

		vbLabels.add(hbECNumberLabel);
		JLabel blankLabel2 = new JLabel("");
		vbLabels.add(blankLabel2);
		vbCombos.add(hbECNumber);
		
		JLabel reacAbbrParameters = new JLabel("Reaction Abbreviation String Matching Parameters");
		hbReacAbbrParametersLabel.add(reacAbbrParameters);
		
		JPanel panelReplaceBox = new JPanel();
		panelReplaceBox.setLayout(new BoxLayout(panelReplaceBox, BoxLayout.X_AXIS));
		panelReplaceBox.add(replaceCheckBox);
		panelReplaceBox.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		hbReplaceBox.add(panelReplaceBox);
		
		JPanel panelTrimFromFileBox = new JPanel();
		panelTrimFromFileBox.setLayout(new BoxLayout(panelTrimFromFileBox, BoxLayout.X_AXIS));
		JLabel fileStartLabel = new JLabel("Trim ");
		panelTrimFromFileBox.add(fileStartLabel);
		panelTrimFromFileBox.add(cbTrimBeginFromFile);
		JLabel fileMiddleLabel = new JLabel(" Characters from Start and ");
		panelTrimFromFileBox.add(fileMiddleLabel);
		panelTrimFromFileBox.add(cbTrimEndFromFile);
		JLabel fileEndLabel = new JLabel(" Characters from End of Abbreviations From File.     ");
		panelTrimFromFileBox.add(fileEndLabel);
		panelTrimFromFileBox.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		hbTrimFromFileBox.add(panelTrimFromFileBox);
		
		JPanel panelTrimFromModelBox = new JPanel();
		panelTrimFromModelBox.setLayout(new BoxLayout(panelTrimFromModelBox, BoxLayout.X_AXIS));
		JLabel modelStartLabel = new JLabel("Trim ");
		panelTrimFromModelBox.add(modelStartLabel);
		panelTrimFromModelBox.add(cbTrimBeginFromModel);
		JLabel modelMiddleLabel = new JLabel(" Characters from Start and ");
		panelTrimFromModelBox.add(modelMiddleLabel);
		panelTrimFromModelBox.add(cbTrimEndFromModel);
		JLabel modelEndLabel = new JLabel(" Characters from End of Abbreviations From Model.");
		panelTrimFromModelBox.add(modelEndLabel);
		panelTrimFromModelBox.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		hbTrimFromModelBox.add(panelTrimFromModelBox);
		
		okButton.setMnemonic(KeyEvent.VK_O);
		JLabel blank = new JLabel("    "); 
		cancelButton.setMnemonic(KeyEvent.VK_C);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(okButton);
		buttonPanel.add(blank);
		buttonPanel.add(cancelButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		hbButton.add(buttonPanel);

		vb.add(hbLabels);
		hbLabeledCombos.add(vbLabels);
		hbLabeledCombos.add(vbCombos);
		vb.add(hbLabeledCombos);
		vb.add(hbReacAbbrParametersLabel);
		vb.add(hbReplaceBox);
		vb.add(hbTrimFromFileBox);
		vb.add(hbTrimFromModelBox);
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
	
	public void populateNamesFromFileBoxes(ArrayList<String> columnNamesFromFile) {

		cbReactionAbbreviation.removeAllItems();
		cbECNumber.removeAllItems();
		//add all column names to from file comboboxes
		for (int c = 0; c < columnNamesFromFile.size(); c++) { 
			cbReactionAbbreviation.addItem(columnNamesFromFile.get(c));
			cbECNumber.addItem(columnNamesFromFile.get(c));
		}
		for (int c = 0; c < columnNamesFromFile.size(); c++) { 
			//filters to match column names from file to required column names in table		
			if(columnNamesFromFile.get(c).equals(GraphicalInterfaceConstants.EC_NUMBER_COLUMN_NAMES[0]) ||
					columnNamesFromFile.get(c).equals(GraphicalInterfaceConstants.EC_NUMBER_COLUMN_NAMES[1])) {
				cbECNumber.setSelectedIndex(c);	
			} else if (columnNamesFromFile.get(c).toLowerCase().contains(GraphicalInterfaceConstants.PROTEIN_CLASS_COLUMN_FILTER[0]) &&
					columnNamesFromFile.get(c).toLowerCase().contains(GraphicalInterfaceConstants.PROTEIN_CLASS_COLUMN_FILTER[1])) {
				cbECNumber.setSelectedIndex(c);	
			}
		}
		cbReactionAbbreviation.setSelectedIndex(0);
	}
	
	public static void main(String[] args) throws Exception {
		//based on code from http:stackoverflow.com/questions/6403821/how-to-add-an-image-to-a-jframe-title-bar
		final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("etc/most16.jpg").getImage()); 
		icons.add(new ImageIcon("etc/most32.jpg").getImage());

		ArrayList<String> list = new ArrayList<String>();
		list.add("test1");
		list.add("test2");
		list.add("ProteinClassDescription");
		
		ReactionsSupplementalDataDialog frame = new ReactionsSupplementalDataDialog(list);

		frame.setIconImages(icons);
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}

