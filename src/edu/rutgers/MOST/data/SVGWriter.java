package edu.rutgers.MOST.data;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.rutgers.MOST.presentation.ResizableDialog;

/**
 * 
 * @author rutgers
 * based on http://www.java2s.com/Code/Java/XML/HowtowriteanXMLfileItsavesafiledescribingamoderndrawinginSVGformat.htm
 */
public class SVGWriter
{
	private ResizableDialog dialog = new ResizableDialog( "Error",
			"Error", "Error" );
	// test data - will be replaced by data from visualization
	private Builder builder = new Builder();
	
	/**
	 * Saves the drawing in SVG format, using DOM/XSLT
	 */
	public void saveDocument(File f) throws TransformerException, IOException
	{
		Document doc = builder.buildDocument();
		FileOutputStream fos = new FileOutputStream(f);
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		t.transform(new DOMSource(doc), new StreamResult(fos));
//		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(f)));
		fos.flush();
		fos.close();
	}
	
	public void saveFile() {
		JTextArea output = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new SVGFileFilter());
		boolean done = false;
		while (!done) {
			int retval = chooser.showSaveDialog(output);
			if (retval == JFileChooser.CANCEL_OPTION) {
				done = true;
			}
			if (retval == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				String path = file.getPath();
				if (!file.getPath().endsWith(".svg")) {
					path = path + ".svg";
					file = new File(path);
				}
				if (file.exists()) {
					int confirmDialog = JOptionPane.showConfirmDialog(chooser, "Replace existing file?");
					if (confirmDialog == JOptionPane.YES_OPTION) {
						done = true;

						try {
							saveDocument(file);
						} catch (TransformerException e) {
							processStackTrace(e);
							// TODO Auto-generated catch block
							//e.printStackTrace();
						} catch (IOException e) {
							processStackTrace(e);
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}

					} else if (confirmDialog == JOptionPane.NO_OPTION) {        		    	  
						done = false;
					} else {
						done = true;
					}       		    	  
				} else {
					done = true;
					
					try {
						saveDocument(file);
					} catch (TransformerException e) {
						processStackTrace(e);
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} catch (IOException e) {
						processStackTrace(e);
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}	
			}
		}
	}
	
	private void processStackTrace( Exception e ) {
		//e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace( new PrintWriter( errors ) );
		dialog.setErrorMessage( errors.toString() );
		// centers dialog
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible( true );
	}
	
	public static void main(String[] args)
	{
		SVGWriter writer = new SVGWriter();
		writer.saveFile();
	}
}

class SVGFileFilter extends javax.swing.filechooser.FileFilter {
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().toLowerCase().endsWith(".svg");
	}

	public String getDescription() {
		return ".svg files";
	}
}

class Builder
{
	private ArrayList<ArrayList<String[]>> edges;
	private ArrayList<Rectangle2D> rects;
	private DocumentBuilder builder;
	
	public Builder()
	{
		edges = new ArrayList<ArrayList<String[]>>();
		rects = new ArrayList<Rectangle2D>();
		
		edges.clear();
		ArrayList<String[]> endpoints = new ArrayList<String[]>();
		String[] e1 = {"1", "1"};
		String[] e2 = {"500", "500"};
		endpoints.add(e1);
		endpoints.add(e2);
		edges.add(endpoints);
		rects.clear();
		rects.add(new Rectangle(0, 0, 100, 50));

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try
		{
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creates an SVG document of the current drawing.
	 * @return the DOM tree of the SVG document
	 */
	public Document buildDocument()
	{
		Document doc = builder.newDocument();
		Element svgElement = doc.createElement("svg");
		doc.appendChild(svgElement);
		svgElement.setAttribute("version", "1.1");
		svgElement.setAttribute("width", "" + 1000);
		svgElement.setAttribute("height", "" + 1000);
		svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		for (int e = 0; e < edges.size(); e++) {
			Element lineElement = doc.createElement("line");
			lineElement.setAttribute("x1", "" + edges.get(e).get(0)[0]);
			lineElement.setAttribute("y1", "" + edges.get(e).get(0)[1]);
			lineElement.setAttribute("x2", "" + edges.get(e).get(1)[0]);
			lineElement.setAttribute("y2", "" + edges.get(e).get(1)[1]);
			lineElement.setAttribute("stroke", "black");
			lineElement.setAttribute("stroke-width", "2");
			svgElement.appendChild(lineElement);
		}
		for (int i = 0; i < rects.size(); i++)
		{
			//Color c = colors.get(i);
			Rectangle2D r = rects.get(i);
			Element rectElement = doc.createElement("rect");
			rectElement.setAttribute("x", "" + r.getX());
			rectElement.setAttribute("y", "" + r.getY());
			rectElement.setAttribute("width", "" + r.getWidth());
			rectElement.setAttribute("height", "" + r.getHeight());
			rectElement.setAttribute("stroke", "black");
			rectElement.setAttribute("stroke-width", "6");
			//rectElement.setAttribute("fill", colorToString(c));
			rectElement.setAttribute("fill", colorToString(Color.WHITE));
			svgElement.appendChild(rectElement);
		}
		return doc;
	}

	/**
	 * Converts a color to a hex value.
	 * @param c a color
	 * @return a string of the form #rrggbb
	 */
	private static String colorToString(Color c)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(Integer.toHexString(c.getRGB() & 0xFFFFFF));
		while (buffer.length() < 6)
			buffer.insert(0, '0');
		buffer.insert(0, '#');
		return buffer.toString();
	}
	
}


