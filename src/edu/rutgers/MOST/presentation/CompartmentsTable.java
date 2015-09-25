package edu.rutgers.MOST.presentation;

import java.awt.*;
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
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;
import edu.rutgers.MOST.config.LocalConfig;

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
			return false;
			
		}	
	};
	private	JPanel		bottomLeftPanel;
	private	JPanel		bottomRightPanel;
	private	JPanel		bottomPanel;
	public static JButton okButton = new JButton("  OK  ");
	public static JButton cancelButton = new JButton("Cancel");
	
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	private String fileType;
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

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
		
		//getRootPane().setDefaultButton(okButton);
		
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
		
		PopupListener popupListener = new PopupListener();
		table.addMouseListener(popupListener);
		
		ActionListener copyActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (table.getSelectedRow() > -1 && table.getSelectedColumn() > -1) {
					tableCopy();
				}			
			}
		};
		
		KeyStroke copyKey = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
		
		table.registerKeyboardAction(copyActionListener, copyKey, JComponent.WHEN_FOCUSED); 

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
		
		int r = table.getModel().getColumnCount();	
		for (int i = 0; i < r; i++) {
			//set background of id column to grey
			ModelCollectionCellRenderer renderer = new ModelCollectionCellRenderer();
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

		return contextMenu;
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

