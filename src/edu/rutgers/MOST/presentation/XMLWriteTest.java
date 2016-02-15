package edu.rutgers.MOST.presentation;

/*
This program is a part of the companion code for Core Java 8th ed.
(http://horstmann.com/corejava)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Random;




import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.rutgers.MOST.presentation.GraphicalInterface.CSVFileFilter;

/**
* This program shows how to write an XML file. It saves a file describing a modern drawing in SVG
* format.
* @version 1.10 2004-09-04
* @author Cay Horstmann
* http://www.java2s.com/Code/Java/XML/HowtowriteanXMLfileItsavesafiledescribingamoderndrawinginSVGformat.htm
*/
public class XMLWriteTest
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				XMLWriteFrame frame = new XMLWriteFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * A frame with a component for showing a modern drawing.
 */
class XMLWriteFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 200;

	private RectangleComponent comp;
	private JFileChooser chooser;
	
	public XMLWriteFrame()
	{
		setTitle("XMLWriteTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		chooser = new JFileChooser();
		chooser.setFileFilter(new SVGFileFilter());

		// add component to frame

		comp = new RectangleComponent();
		add(comp);

		// set up menu bar

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem newItem = new JMenuItem("New");
		menu.add(newItem);
		newItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				comp.newDrawing();
			}
		});

		JMenuItem saveItem = new JMenuItem("Save with DOM/XSLT");
		menu.add(saveItem);
		saveItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					saveDocument();
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(XMLWriteFrame.this, e.toString());
				}
			}
		});

		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
	}

	/**
	 * Saves the drawing in SVG format, using DOM/XSLT
	 */
	public void saveDocument() throws TransformerException, IOException
	{
		if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
		File f = chooser.getSelectedFile();
		Document doc = comp.buildDocument();
		Transformer t = TransformerFactory.newInstance().newTransformer();
//		t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
//				"http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd");
//		t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 20000802//EN");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(f)));
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

/**
 * A component that shows a set of colored rectangles
 */
class RectangleComponent extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ArrayList<String[]>> edges;
	private ArrayList<Rectangle2D> rects;
//	private ArrayList<Color> colors;
//	private Random generator;
	private DocumentBuilder builder;
	
	public RectangleComponent()
	{
		edges = new ArrayList<ArrayList<String[]>>();
		rects = new ArrayList<Rectangle2D>();
//		colors = new ArrayList<Color>();
		//generator = new Random();

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
	 * Create a new random drawing.
	 */
	public void newDrawing()
	{
		//int n = 10 + generator.nextInt(20);
		edges.clear();
		ArrayList<String[]> endpoints = new ArrayList<String[]>();
		String[] e1 = {"1", "1"};
		String[] e2 = {"500", "500"};
		endpoints.add(e1);
		endpoints.add(e2);
		edges.add(endpoints);
		rects.clear();
//		colors.clear();
		rects.add(new Rectangle(0, 0, 100, 50));
//		for (int i = 1; i <= n; i++)
//		{
//			int x = generator.nextInt(getWidth());
//			int y = generator.nextInt(getHeight());
//			int width = generator.nextInt(getWidth() - x);
//			int height = generator.nextInt(getHeight() - y);
//			rects.add(new Rectangle(x, y, width, height));
//			int r = generator.nextInt(256);
//			int g = generator.nextInt(256);
//			int b = generator.nextInt(256);
//			colors.add(new Color(r, g, b));
//		}
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		if (rects.size() == 0) newDrawing();
		Graphics2D g2 = (Graphics2D) g;

		// draw all rectangles
		for (int i = 0; i < rects.size(); i++)
		{
			//g2.setPaint(colors.get(i));
			g2.setPaint(Color.BLACK);
			g2.fill(rects.get(i));
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
		svgElement.setAttribute("width", "" + getWidth());
		svgElement.setAttribute("height", "" + getHeight());
		svgElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		for (int e = 0; e < edges.size(); e++) {
			Element lineElement = doc.createElement("line");
			lineElement.setAttribute("x1", "" + edges.get(e).get(0)[0]);
			lineElement.setAttribute("y1", "" + edges.get(e).get(0)[1]);
			lineElement.setAttribute("x1", "" + edges.get(e).get(1)[0]);
			lineElement.setAttribute("y1", "" + edges.get(e).get(1)[1]);
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
	 * Writers an SVG document of the current drawing.
	 * @param writer the document destination
	 */
//	public void writeDocument(XMLStreamWriter writer) throws XMLStreamException
//	{
//		writer.writeStartDocument();
////		writer.writeDTD("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20000802//EN\" " 
////				+ "\"http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd\">");
//		writer.writeStartElement("svg");
//		writer.writeAttribute("width", "" + getWidth());
//		writer.writeAttribute("height", "" + getHeight());
//		for (int e = 0; e < edges.size(); e++) {
//			writer.writeEmptyElement("line");
//			writer.writeAttribute("x1", "" + edges.get(e).get(0)[0]);
//			writer.writeAttribute("y1", "" + edges.get(e).get(0)[1]);
//			writer.writeAttribute("x1", "" + edges.get(e).get(1)[0]);
//			writer.writeAttribute("y1", "" + edges.get(e).get(1)[1]);
//			writer.writeAttribute("stroke", "black");
//			writer.writeAttribute("stroke-width", "2");
//		}
//		for (int i = 0; i < rects.size(); i++)
//		{
////			Color c = colors.get(i);
//			Rectangle2D r = rects.get(i);
//			writer.writeEmptyElement("rect");
//			writer.writeAttribute("x", "" + r.getX());
//			writer.writeAttribute("y", "" + r.getY());
//			writer.writeAttribute("width", "" + r.getWidth());
//			writer.writeAttribute("height", "" + r.getHeight());
//			writer.writeAttribute("stroke", "black");
//			writer.writeAttribute("stroke-width", "2");
//			//writer.writeAttribute("fill", colorToString(c));
//			writer.writeAttribute("fill", colorToString(Color.WHITE));
//		}
//		writer.writeEndDocument(); // closes svg element
//	}  

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

