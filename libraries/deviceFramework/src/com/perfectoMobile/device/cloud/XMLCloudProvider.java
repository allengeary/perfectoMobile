/*
 * 
 */
package com.perfectoMobile.device.cloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLApplicationProvider.
 */
public class XMLCloudProvider extends AbstractCloudProvider
{
	
	/** The Constant NAME. */
	private static final String NAME = "name";
	
	/** The Constant USER_NAME. */
	private static final String USER_NAME = "userName";
	
	/** The Constant PASSWORD. */
	private static final String PASSWORD = "password";
	
	/** The Constant HOST_NAME. */
	private static final String HOST_NAME = "hostName";
	
	/** The Constant PROXY_HOST. */
	private static final String PROXY_HOST = "proxyHost";
	
	/** The Constant PROXY_PORT. */
	private static final String PROXY_PORT = "proxyPort";
	
	/** The Constant GRID. */
	private static final String GRID = "grid";

	/** The x path factory. */
	private XPathFactory xPathFactory;
	
	/** The file name. */
	private File fileName;
	
	/** The resource name. */
	private String resourceName;
	
	/**
	 * Instantiates a new XML application provider.
	 *
	 * @param fileName the file name
	 */
	public XMLCloudProvider( File fileName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.fileName = fileName;
		readData();
	}
	
	/**
	 * Instantiates a new XML application provider.
	 *
	 * @param resourceName the resource name
	 */
	public XMLCloudProvider( String resourceName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.resourceName = resourceName;
		readData();
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.application.ApplicationProvider#readData()
	 */
	public void readData()
	{
		if ( fileName == null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Reading from CLASSPATH as " + resourceName );
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
	
	/**
	 * Read elements.
	 *
	 * @param inputStream the input stream
	 */
	private void readElements( InputStream inputStream )
	{
		
		try
		{
		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware( true );
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document xmlDocument = dBuilder.parse( inputStream );
		
			NodeList nodeList = getNodes( xmlDocument, "//cloudRegistry/cloud");
			for ( int i=0; i<nodeList.getLength(); i++ )
				parseApplication( nodeList.item( i ) );
			
		}
		catch( Exception e )
		{
			log.fatal( "Error reading CSV Element File", e );
		}
	}
	
	/**
	 * Parses the application.
	 *
	 * @param appNode the app node
	 */
	private void parseApplication( Node appNode )
	{
		String cloudName = appNode.getAttributes().getNamedItem( NAME ).getNodeValue();
		String userName = appNode.getAttributes().getNamedItem( USER_NAME ).getNodeValue();
		String password = appNode.getAttributes().getNamedItem( PASSWORD ).getNodeValue();
		String hostName = appNode.getAttributes().getNamedItem( HOST_NAME ).getNodeValue();
		String proxyHost = appNode.getAttributes().getNamedItem( PROXY_HOST ).getNodeValue();
		String proxyPort = appNode.getAttributes().getNamedItem( PROXY_PORT ).getNodeValue();
		String grid = null;
		Node gridInstance = appNode.getAttributes().getNamedItem( GRID );
		if ( gridInstance != null )
			grid = gridInstance.getNodeValue();
			
		if ( log.isDebugEnabled() )
			log.debug( "Extracted Site [" + cloudName + "]" );
		
		String description = appNode.getTextContent();
		
		CloudRegistry.instance().addCloudDescriptor( new CloudDescriptor( cloudName, userName, password, hostName, proxyHost, proxyPort, description, grid ) );
	}
	
	/**
	 * Gets the nodes.
	 *
	 * @param xmlDocument the xml document
	 * @param xPathExpression the x path expression
	 * @return the nodes
	 */
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
