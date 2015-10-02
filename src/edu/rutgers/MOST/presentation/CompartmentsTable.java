package edu.rutgers.MOST.presentation;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXTable;

import edu.rutgers.MOST.config.LocalConfig;
import edu.rutgers.MOST.data.MetaboliteUndoItem;
import edu.rutgers.MOST.data.SBMLCompartment;
import edu.rutgers.MOST.data.UndoConstants;

// loosely based on http://www.cs.cf.ac.uk/Dave/HCI/HCI_Handout_CALLER/node167.html
// based on http://www.coderanch.com/t/345311/GUI/java/Adding-rows-Jtable
class CompartmentsTable
		extends 	JFrame
 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Instance attributes used in this example
	private	JPanel		topPanel;
	private	JScrollPane scrollPane;
	private JXTable table = new JXTable(){  
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column){	    	   
			if (column == CompartmentsTableConstants.ABBREVIATION_COLUMN) {
				return false;
			}
			return true;  
		}

		public Component prepareEditor(
				TableCellEditor editor, int row, int column)
		{
			Component c = super.prepareEditor(editor, row, column);

			if (c instanceof JTextComponent)
			{
				((JTextField)c).selectAll();
			}

			return c;
		}		
	};
	private	JPanel		bottomLeftPanel;
	private	JPanel		bottomRightPanel;
	private	JPanel		bottomPanel;
	public static JButton okButton = new JButton("  OK  ");
	public static JButton cancelButton = new JButton("Cancel");

	private Vector<String> columnNames;

	public Vector<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Vector<String> columnNames) {
		this.columnNames = columnNames;
	}
	
	// Constructor of main frame
	public CompartmentsTable()
	{
		// Set the frame characteristics
		setTitle(CompartmentsTableConstants.TITLE);
		//setSize( 700, 500 );
		setSize(CompartmentsTableConstants.WIDTH, CompartmentsTableConstants.HEIGHT);
		
		setBackground( Color.gray );
		
		okButton.setMnemonic(KeyEvent.VK_O);
		okButton.setEnabled(false);
		cancelButton.setMnemonic(KeyEvent.VK_C);
		
		getRootPane().setDefaultButton(okButton);
		
		table.setRowHeight(20);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true); 
		table.getSelectionModel().addListSelectionListener(new RowListener());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		// The code below allows multiple cells to be selectable, but also allows for
		// multiple row selection. Since the primary purpose of this table is to load files,
		// only 1 row can be selectable at a time. Providing copy is only a convenience
		// and should not take precedence over functionality. As a result of this,
		// only 1 cell can be copied at a time
		//table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		Action compTableAction = new AbstractAction()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{   	  
				TableCellListener tcl = (TableCellListener)e.getSource();
			
				if (tcl.getOldValue() != tcl.getNewValue()) {
					updateTableByRow(tcl.getRow());
//					for (int i = 0; i < LocalConfig.getInstance().getListOfCompartments().size(); i++) {
//						if (LocalConfig.getInstance().getListOfCompartments().get(i).getId().equals(table.getModel().getValueAt(tcl.getRow(), CompartmentsTableConstants.ABBREVIATION_COLUMN))) {
////							LocalConfig.getInstance().getListOfCompartments().get(i).setName((String) table.getModel().getValueAt(tcl.getRow(), CompartmentsTableConstants.NAME_COLUMN));
////							LocalConfig.getInstance().getListOfCompartments().get(i).setOutside((String) table.getModel().getValueAt(tcl.getRow(), CompartmentsTableConstants.OUTSIDE_COLUMN));
//							updateCompartment(LocalConfig.getInstance().getListOfCompartments().get(i), tcl.getRow());
//							System.out.println(LocalConfig.getInstance().getListOfCompartments().get(i));
//						}
//					}
				}			
			}
		};
		
		new TableCellListener(table, compTableAction);
		
		PopupListener popupListener = new PopupListener();
		table.addMouseListener(popupListener);
		
		ActionListener copyActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (table.getSelectedRow() > -1 && table.getSelectedColumn() > -1) {
					tableCopy();
				}			
			}
		};
		
		ActionListener pasteActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (table.getSelectedRow() > -1 && table.getSelectedColumn() > -1 && 
						table.getSelectedColumn() != CompartmentsTableConstants.ABBREVIATION_COLUMN) {
					tablePaste();
				}			
			}
		};
		
		KeyStroke copyKey = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
		KeyStroke pasteKey = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
		
		table.registerKeyboardAction(copyActionListener, copyKey, JComponent.WHEN_FOCUSED);
		table.registerKeyboardAction(pasteActionListener, pasteKey, JComponent.WHEN_FOCUSED);

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		topPanel.add( scrollPane, BorderLayout.CENTER );
		bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout( new BorderLayout() );
		bottomRightPanel = new JPanel();
		bottomPanel = new JPanel();
		bottomRightPanel.setLayout( new BorderLayout() );
		bottomLeftPanel.add( okButton, BorderLayout.WEST );
		bottomRightPanel.add( cancelButton, BorderLayout.EAST );
		bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);	
		bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
		topPanel.add( bottomPanel, BorderLayout.SOUTH );
		
		//from http://www.java2s.com/Tutorial/Java/0240__Swing/thelastcolumnismovedtothefirstposition.htm
		// columns cannot be rearranged by dragging
		table.getTableHeader().setReorderingAllowed(false); 
		
		createColumns();
		setUpTable();	
		
		ActionListener okButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				setVisible(false);
				//dispose();				
			}
		};

		okButton.addActionListener(okButtonActionListener);
		
		ActionListener cancelButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				setVisible(false);
				//dispose();				
			}
		};

		cancelButton.addActionListener(cancelButtonActionListener);
		
	}
	
	public void createColumns() {
		DefaultTableModel model = new DefaultTableModel();
		Vector<String> columnNames = new Vector<String>();
		
		for (int i = 0; i < CompartmentsTableConstants.visibleColumnsList.size(); i++) {						
				columnNames.add(CompartmentsTableConstants.visibleColumnsList.get(i));
		}
		for (int j = 0; j < columnNames.size(); j++) {
			model.addColumn(columnNames.get(j));
		}		
		setColumnNames(columnNames);
			
		table.setModel(model);
	}
	
	public void loadTable() {
		createColumns();
		setUpTable();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int k = 0; k < LocalConfig.getInstance().getListOfCompartments().size(); k++) {
			Vector <String> row = new Vector<String>();
			row.add(LocalConfig.getInstance().getListOfCompartments().get(k).getId());
			row.add(LocalConfig.getInstance().getListOfCompartments().get(k).getName());
			row.add(LocalConfig.getInstance().getListOfCompartments().get(k).getOutside());
			model.addRow(row);
		}
		table.setModel(model);
	}
	
	public void setUpTable() {
		int r = table.getModel().getColumnCount();	
		for (int i = 0; i < r; i++) {
			//set background of id column to grey
			CompartmentsTableCellRenderer renderer = new CompartmentsTableCellRenderer();
			TableColumn column = table.getColumnModel().getColumn(i);  
			column.setCellRenderer(renderer);
            // Column widths can be changed here           
            if (i == CompartmentsTableConstants.ABBREVIATION_COLUMN) {
            	column.setPreferredWidth(CompartmentsTableConstants.ABBREVIATION_WIDTH);
            }
            if (i == CompartmentsTableConstants.NAME_COLUMN) {
            	column.setPreferredWidth(CompartmentsTableConstants.NAME_WIDTH);
            }
            if (i == CompartmentsTableConstants.OUTSIDE_COLUMN) {
            	column.setPreferredWidth(CompartmentsTableConstants.OUTSIDE_WIDTH);
            }
		}	
	}
	
	private class RowListener implements ListSelectionListener {
    	public void valueChanged(ListSelectionEvent event) {
    		if (table.getSelectedRow() > -1) {
				String abbreviation = (table.getModel().getValueAt(table.getSelectedRow(), CompartmentsTableConstants.ABBREVIATION_COLUMN)).toString();
    			LocalConfig.getInstance().setSelectedCompartmentName(abbreviation);
				okButton.setEnabled(true);
			}
    	}
    }
	
	// from http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
	public class PopupListener extends MouseAdapter {

		public void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger() && table.isEnabled()) {
				Point p = new Point(e.getX(), e.getY());
				int col = table.columnAtPoint(p);
				int row = table.rowAtPoint(p);
				// translate table index to model index
				//int mcol = reactionsTable.getColumn(reactionsTable.getColumnName(col)).getModelIndex();

				if (row >= 0 && row < table.getRowCount()) {
					cancelCellEditing();            
					JPopupMenu contextMenu = createContextMenu(row, col);
					if (contextMenu != null
							&& contextMenu.getComponentCount() > 0) {
						contextMenu.show(table, p.x, p.y);
					}
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}
	}

	private void cancelCellEditing() {
		CellEditor ce = table.getCellEditor();
		if (ce != null) {
			ce.cancelCellEditing();
		}
	}
	
	private JPopupMenu createContextMenu(final int rowIndex,
			final int columnIndex) {
		JPopupMenu contextMenu = new JPopupMenu();

		JMenuItem copyMenu = new JMenuItem("Copy");
		copyMenu.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableCopy();
			}
		});
		contextMenu.add(copyMenu);
		JMenuItem pasteMenu = new JMenuItem("Paste");
		if (columnIndex == CompartmentsTableConstants.ABBREVIATION_COLUMN) {
			pasteMenu.setEnabled(false);
		}
		pasteMenu.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		pasteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablePaste();
			}
		});
		contextMenu.add(pasteMenu);

		return contextMenu;
	}
	
	public void updateTableByRow(int row) {
		for (int i = 0; i < LocalConfig.getInstance().getListOfCompartments().size(); i++) {
			if (LocalConfig.getInstance().getListOfCompartments().get(i).getId().equals(table.getModel().getValueAt(row, CompartmentsTableConstants.ABBREVIATION_COLUMN))) {
//				LocalConfig.getInstance().getListOfCompartments().get(i).setName((String) table.getModel().getValueAt(tcl.getRow(), CompartmentsTableConstants.NAME_COLUMN));
//				LocalConfig.getInstance().getListOfCompartments().get(i).setOutside((String) table.getModel().getValueAt(tcl.getRow(), CompartmentsTableConstants.OUTSIDE_COLUMN));
				updateCompartment(LocalConfig.getInstance().getListOfCompartments().get(i), row);
				System.out.println(LocalConfig.getInstance().getListOfCompartments().get(i));
			}
		}
	}
	
	public void updateCompartment(SBMLCompartment comp, int row) {
		comp.setName((String) table.getModel().getValueAt(row, CompartmentsTableConstants.NAME_COLUMN));
		comp.setOutside((String) table.getModel().getValueAt(row, CompartmentsTableConstants.OUTSIDE_COLUMN));
	}

	public void tableCopy() {
		int numCols=table.getSelectedColumnCount(); 
		int numRows=table.getSelectedRowCount(); 
		int[] rowsSelected=table.getSelectedRows(); 
		int[] colsSelected=table.getSelectedColumns(); 
		try {
			if (numRows!=rowsSelected[rowsSelected.length-1]-rowsSelected[0]+1 || numRows!=rowsSelected.length || 
					numCols!=colsSelected[colsSelected.length-1]-colsSelected[0]+1 || numCols!=colsSelected.length) {

				JOptionPane.showMessageDialog(null, "Invalid Copy Selection", "Invalid Copy Selection", JOptionPane.ERROR_MESSAGE);
				return; 
			} 
		} catch (Throwable t) {

		}		
		StringBuffer excelStr=new StringBuffer(); 
		for (int i=0; i<numRows; i++) { 
			for (int j=0; j<numCols; j++) { 
				try {
					excelStr.append(escape(table.getValueAt(rowsSelected[i], colsSelected[j]))); 
				} catch (Throwable t) {

				}						
				if (j<numCols-1) {
					//System.out.println("t");
					excelStr.append("\t"); 
				} 
			} 
			//System.out.println("n");
			excelStr.append("\n"); 
		} 

		StringSelection sel  = new StringSelection(excelStr.toString()); 
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, sel);
	}
	
	public void tablePaste() {
		String pasteString = ""; 
		try { 
			pasteString = (String)(Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this).getTransferData(DataFlavor.stringFlavor));
			if (table.getSelectedRow() > -1 && table.getSelectedColumn() > -1 &&
					table.getSelectedColumn() != CompartmentsTableConstants.ABBREVIATION_COLUMN) {
				table.setValueAt(pasteString, table.getSelectedRow(), table.getSelectedColumn());
				updateTableByRow(table.getSelectedRow());
			}
		} catch (Exception e1) { 
			JOptionPane.showMessageDialog(null, "Invalid Paste Type", "Invalid Paste Type", JOptionPane.ERROR_MESSAGE);
			return; 
		} 
	}
	
	private String escape(Object cell) { 
		return cell.toString().replace("\n", " ").replace("\t", " "); 
	} 
	
	/***********************************************************************************/
	//end clipboard
	/***********************************************************************************/
	
	public static void main( String args[] )
	{
		CompartmentsTable mainFrame	= new CompartmentsTable();
		mainFrame.setVisible( true );
	}
}

