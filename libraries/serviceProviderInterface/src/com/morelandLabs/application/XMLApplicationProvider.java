package com.morelandLabs.application;

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

import org.apache.http.impl.io.SocketOutputBuffer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class XMLApplicationProvider.
 */
public class XMLApplicationProvider extends AbstractApplicationProvider
{
	private static final String NAME = "name";
	private static final String APP_PACKAGE = "appPackage";
	private static final String BUNDLE_ID = "bundleId";
	private static final String URL = "url";
	private static final String IOS_INSTALL = "iosInstall";
	private static final String ANDROID_INSTALL = "androidInstall";

	private XPathFactory xPathFactory;
	private File fileName;
	private String resourceName;
	
	/**
	 * Instantiates a new XML application provider.
	 *
	 * @param fileName the file name
	 */
	public XMLApplicationProvider( File fileName )
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
	public XMLApplicationProvider( String resourceName )
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
	
	private void readElements( InputStream inputStream )
	{
		
		try
		{
		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware( true );
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document xmlDocument = dBuilder.parse( inputStream );
		
			NodeList nodeList = getNodes( xmlDocument, "//applicationRegistry/application");
			for ( int i=0; i<nodeList.getLength(); i++ )
				parseApplication( nodeList.item( i ) );

			
		}
		catch( Exception e )
		{
			log.fatal( "Error reading CSV Element File", e );
		}
	}
	
	private void parseApplication( Node appNode )
	{
		String appName = appNode.getAttributes().getNamedItem( NAME ).getNodeValue();
		
		if ( log.isDebugEnabled() )
			log.debug( "Extracted Site [" + appName + "]" );
		
		String iosInstall = "";
		String androidInstall = "";		
		
		Node iosNode = appNode.getAttributes().getNamedItem( IOS_INSTALL );
		if ( iosNode != null )
			iosInstall = iosNode.getNodeValue();
		Node androidNode = appNode.getAttributes().getNamedItem( ANDROID_INSTALL );
		if ( androidNode != null )
			androidInstall = androidNode.getNodeValue();
		
		Map<String,String> capabilities = new HashMap<String,String>( 10 );
		
		for ( int i=0; i<appNode.getChildNodes().getLength(); i++ )
		{
			Node currentNode = appNode.getChildNodes().item( i );
			if ( currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().toLowerCase().equals( "capability" ) )
				capabilities.put( currentNode.getAttributes().getNamedItem( "name" ).getNodeValue(), currentNode.getTextContent() );
		}
		
		
		
		String description = appNode.getTextContent();
		
		ApplicationRegistry.instance().addApplicationDescriptor( new ApplicationDescriptor( appName, description, appNode.getAttributes().getNamedItem( APP_PACKAGE ).getNodeValue(), appNode.getAttributes().getNamedItem( BUNDLE_ID ).getNodeValue(), appNode.getAttributes().getNamedItem( URL ).getNodeValue(), iosInstall, androidInstall, capabilities ) );
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
