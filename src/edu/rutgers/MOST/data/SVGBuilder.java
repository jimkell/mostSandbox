package edu.rutgers.MOST.data;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.rutgers.MOST.presentation.PathwaysFrameConstants;

public class SVGBuilder {

	private ArrayList<ArrayList<String[]>> edges;
	
	public ArrayList<ArrayList<String[]>> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<ArrayList<String[]>> edges) {
		this.edges = edges;
	}
	
	private ArrayList<BorderRectangle> rects;
	
	public ArrayList<BorderRectangle> getRects() {
		return rects;
	}

	public void setRects(ArrayList<BorderRectangle> rects) {
		this.rects = rects;
	}

//	private ArrayList<Rectangle2D> rects;
//	
//	public ArrayList<Rectangle2D> getRects() {
//		return rects;
//	}
//
//	public void setRects(ArrayList<Rectangle2D> rects) {
//		this.rects = rects;
//	}

	private DocumentBuilder builder;
	
	public SVGBuilder()
	{
//		edges = new ArrayList<ArrayList<String[]>>();
//		rects = new ArrayList<Rectangle2D>();
//		
//		edges.clear();
//		ArrayList<String[]> endpoints = new ArrayList<String[]>();
//		String[] e1 = {"1", "1"};
//		String[] e2 = {"500", "500"};
//		endpoints.add(e1);
//		endpoints.add(e2);
//		edges.add(endpoints);
//		rects.clear();
//		rects.add(new Rectangle(0, 0, 100, 50));

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
		svgElement.setAttribute("width", "" + PathwaysFrameConstants.GRAPH_WIDTH);
		svgElement.setAttribute("height", "" + PathwaysFrameConstants.GRAPH_HEIGHT);
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
			//Rectangle2D r = rects.get(i);
			BorderRectangle r = rects.get(i);
			Element rectElement = doc.createElement("rect");
			rectElement.setAttribute("x", "" + r.getX());
			rectElement.setAttribute("y", "" + r.getY());
			rectElement.setAttribute("width", "" + r.getWidth());
			rectElement.setAttribute("height", "" + r.getHeight());
			rectElement.setAttribute("stroke", colorToString(r.getStroke()));
			rectElement.setAttribute("stroke-width", r.getStrokeWidth());
			rectElement.setAttribute("fill", colorToString(r.getFill()));
//			rectElement.setAttribute("stroke", "black");
//			rectElement.setAttribute("stroke-width", "6");
//			rectElement.setAttribute("fill", colorToString(c));
//			rectElement.setAttribute("fill", colorToString(Color.WHITE));
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
