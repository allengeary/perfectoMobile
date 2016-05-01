package com.perfectomobile.integration.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AbstractXMLModel implements XMLModel
{
	private Document xmlDocument;
	private XPathFactory xPathFactory;
	private TransformerFactory transformerFactory;
	protected PerfectoNamespaceContext perfectoNamespaceContext;
	protected Log log = LogFactory.getLog( XMLModel.class ); 

	protected AbstractXMLModel( URL getUrl ) throws ParserConfigurationException, IOException, SAXException
	{
		InputStream inputStream = getUrl.openStream();
		parseXml( inputStream );
		inputStream.close();
	}
	
	protected AbstractXMLModel( String xmlData ) throws ParserConfigurationException, IOException, SAXException 
	{
		parseXml( new ByteArrayInputStream( xmlData.getBytes() ) );
	}
	
	protected AbstractXMLModel( InputStream xmlData ) throws ParserConfigurationException, IOException, SAXException 
	{
		parseXml( xmlData );
	}
	
	protected AbstractXMLModel( File xmlData ) throws ParserConfigurationException, IOException, SAXException 
	{
		parseXml( new BufferedInputStream( new FileInputStream( xmlData ) ) );
	}

	private void parseXml( InputStream inputStream ) throws ParserConfigurationException, IOException, SAXException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware( true );
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		

		xmlDocument = dBuilder.parse( inputStream );
		xPathFactory = XPathFactory.newInstance();
		transformerFactory = TransformerFactory.newInstance();
	}
	
	public NodeList getNodes( String xPathExpression )
	{
		try
		{
			if ( log.isDebugEnabled() )
				log.debug( "Attempting to return Nodes for [" + xPathExpression + "]" );
			
			XPath xPath = xPathFactory.newXPath();
			if ( perfectoNamespaceContext != null )
			{
				xPath.setNamespaceContext( perfectoNamespaceContext );
			}
			return (NodeList) xPath.evaluate( xPathExpression, xmlDocument, XPathConstants.NODESET );
		}
		catch( Exception e )
		{
			log.error( "Error parsing xPath Expression [" + xPathExpression + "]" );
			return null;
		}
	}
	
	public Node getNode( String xPathExpression )
	{
		try
		{
			if ( log.isDebugEnabled() )
				log.debug( "Attempting to return Node for [" + xPathExpression + "]" );
			
			XPath xPath = xPathFactory.newXPath();
			if ( perfectoNamespaceContext != null )
				xPath.setNamespaceContext( perfectoNamespaceContext );
			Node xmlNode = (Node) xPath.evaluate( xPathExpression, xmlDocument, XPathConstants.NODE );

			if ( xmlNode == null && log.isDebugEnabled() )
				log.debug( "No node was found using [" + xPathExpression + "]" );
			
			if ( xmlNode != null && log.isDebugEnabled() )
				log.debug( "Found " + xmlNode.getNodeName() );
			
			return xmlNode;
		}
		catch( Exception e )
		{
			log.error( "Error parsing xPath Expression [" + xPathExpression + "]" );
			return null;
		}
	}
	
	public String getAttribute( String xPathExpression, String attributeName )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Attempting to Attribute of [" + attributeName + "] on [" + xPathExpression + "]" );
		
		Node xmlNode = getNode( xPathExpression );
		
		if ( xmlNode == null )
			throw new IllegalArgumentException( "[" + xPathExpression + "] could not be located" );
		
		Node attributeNode = xmlNode.getAttributes().getNamedItem( attributeName );
		if ( attributeNode != null )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Located Attribute [" + attributeNode.getNodeName() + "] with a value of [" + attributeNode.getNodeValue() + "]" );
			return attributeNode.getNodeValue();
		}
		
		return null;
	}
	
	
	public String toString()
	{
		try
		{
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
			return writer.toString();
		}
		catch( Exception e )
		{
			log.error( "Error converting document to string", e );
			return null;
		}
	}
}

