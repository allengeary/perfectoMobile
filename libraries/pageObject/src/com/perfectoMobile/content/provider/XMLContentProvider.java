package com.perfectoMobile.content.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.perfectoMobile.content.ContentData;
import com.perfectoMobile.content.ContentManager;
import com.perfectoMobile.content.DefaultContentData;
import com.perfectoMobile.page.BY;
import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.element.ElementFactory;

/**
 * The Class XMLElementProvider.
 */
public class XMLContentProvider extends AbstractContentProvider
{
	private static final String NAME = "name";

	private XPathFactory xPathFactory;
	private File fileName;
	private String resourceName;
	
	/**
	 * Instantiates a new XML element provider.
	 *
	 * @param fileName the file name
	 */
	public XMLContentProvider( File fileName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.fileName = fileName;
	}
	
	/**
	 * Instantiates a new XML element provider.
	 *
	 * @param resourceName the resource name
	 */
	public XMLContentProvider( String resourceName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.resourceName = resourceName;
	}
	
	public void readContent()
	{
		if ( fileName == null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Reading from CLASSPATH as CSVElementProvider.elementFile" );
			readElements( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				if ( log.isInfoEnabled() )
					log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				readElements( new FileInputStream( fileName ) );
			}
			catch( FileNotFoundException e )
			{
				log.fatal( "Could not read from " + fileName, e );
			}
		}
	}
	
	private void readElements( InputStream inputStream )
	{
		
		try
		{
		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware( true );
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document xmlDocument = dBuilder.parse( inputStream );
		
			Node modelNode = getNode( xmlDocument, "//content/model" );
			if ( modelNode == null )
				throw new IllegalArgumentException( "A model must be specified at content/model" );
			
			String[] items = modelNode.getTextContent().split( "," );
			ContentManager.instance().setMatrixData( items );
						
			NodeList nodeList = getNodes( xmlDocument, "//content/record");
			
			for ( int i=0; i<nodeList.getLength(); i++ )
			{
				if ( nodeList.item( i ).getAttributes().getNamedItem( NAME ) == null )
					throw new IllegalArgumentException( "Name must be a specified attributes on each record" );
				
				String keyName = nodeList.item( i ).getAttributes().getNamedItem( NAME ).getNodeValue();
				
				String[] values = new String[ items.length ];
				
				for ( int x=0; x<items.length; x++ )
				{
					if ( nodeList.item( i ).getAttributes().getNamedItem( items[ x ] ) == null )
						throw new IllegalArgumentException( "The Model item [" + items[ x ] + "] was not specified" );
					
					values[ x ] = nodeList.item( i ).getAttributes().getNamedItem( items[ x ] ).getNodeValue();
				}
				
				ContentData contentData = new DefaultContentData( keyName, values );
				
				ContentManager.instance().addContentData( contentData );
			}
			
		}
		catch( Exception e )
		{
			log.fatal( "Error reading CSV Element File", e );
		}
	}
	
	
	
	private  Node getNode( Document xmlDocument, String xPathExpression )
	{
		try
		{
			if ( log.isDebugEnabled() )
				log.debug( "Attempting to return Node for [" + xPathExpression + "]" );
			
			XPath xPath = xPathFactory.newXPath();
			return (Node) xPath.evaluate( xPathExpression, xmlDocument, XPathConstants.NODE );
		}
		catch( Exception e )
		{
			log.error( "Error parsing xPath Expression [" + xPathExpression + "]" );
			return null;
		}
	}
	
	private  NodeList getNodes( Document xmlDocument, String xPathExpression )
	{
		try
		{
			if ( log.isDebugEnabled() )
				log.debug( "Attempting to return Nodes for [" + xPathExpression + "]" );
			
			XPath xPath = xPathFactory.newXPath();
			return (NodeList) xPath.evaluate( xPathExpression, xmlDocument, XPathConstants.NODESET );
		}
		catch( Exception e )
		{
			log.error( "Error parsing xPath Expression [" + xPathExpression + "]" );
			return null;
		}
	}
	
	
}
