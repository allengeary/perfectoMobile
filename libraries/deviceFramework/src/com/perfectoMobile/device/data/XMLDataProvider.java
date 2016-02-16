package com.perfectoMobile.device.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.SimpleDevice;

/**
 * The Class CSVDataProvider.
 */
public class XMLDataProvider implements DataProvider
{
	private Log log = LogFactory.getLog( XMLDataProvider.class );
	private XPathFactory xPathFactory;
	private File fileName;
	private String resourceName;
	private DriverType driverType;
	private static final String NAME = "name";

	/**
	 * Instantiates a new CSV data provider.
	 *
	 * @param fileName            the file name
	 * @param driverType the driver type
	 */
	public XMLDataProvider( File fileName, DriverType driverType )
	{
		xPathFactory = XPathFactory.newInstance();
		this.fileName = fileName;
		this.driverType = driverType;
	}

	/**
	 * Instantiates a new CSV data provider.
	 *
	 * @param resourceName            the resource name
	 * @param driverType the driver type
	 */
	public XMLDataProvider( String resourceName, DriverType driverType )
	{
		xPathFactory = XPathFactory.newInstance();
		this.resourceName = resourceName;
		this.driverType = driverType;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.data.DataProvider#readData()
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
		
			NodeList nodeList = getNodes( xmlDocument, "//deviceRegistry/device");
			for ( int i=0; i<nodeList.getLength(); i++ )
				parseDevice( nodeList.item( i ) );
			
		}
		catch( Exception e )
		{
			log.fatal( "Error reading XML Element File", e );
		}
	}
	
	private void parseDevice( Node deviceNode )
	{
		String name = deviceNode.getAttributes().getNamedItem( NAME ).getNodeValue();
		
		if ( log.isDebugEnabled() )
			log.debug( "Extracted Device [" + name + "]" );
		
		String manufacturer = getValue( deviceNode, "manufacturer" );
		String model = getValue( deviceNode, "model" );
		String os = getValue( deviceNode, "os" );
		String osVersion = getValue( deviceNode, "osVersion" );
		String browserName = getValue( deviceNode, "browserName" );
		String availableDevicesString = getValue( deviceNode, "availableDevices" );
		int availableDevices = 1;
		if ( availableDevicesString != null )
			availableDevices = Integer.parseInt( availableDevicesString );
		
		String activeString = getValue( deviceNode, "active" );
		boolean active = true;
		if ( activeString != null )
			active = Boolean.parseBoolean( activeString );
		String id = getValue( deviceNode, "id" );
		
		String driverName = "";
		switch( driverType )
		{
			case APPIUM:
				if ( os.toUpperCase().equals( "IOS" ) )
					driverName = "IOS";
				else if ( os.toUpperCase().equals( "ANDROID" ) )
					driverName = "ANDROID";
				else
					log.warn( "Appium is not supported on the following OS " + os.toUpperCase() + " - this device will be ignored" );
				break;
				
			case PERFECTO:
				driverName = "PERFECTO";
				break;
				
			case WEB:
				driverName = "WEB";
				break;
		}
		
		Device currentDevice = new SimpleDevice( name, manufacturer, model, os, osVersion, browserName, null, availableDevices, driverName, active, id );
		
		for ( int i=0; i<deviceNode.getChildNodes().getLength(); i++ )
		{
			Node currentNode =deviceNode.getChildNodes().item( i );
			if ( currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().toLowerCase().equals( "capability" ) )
				currentDevice.addCapability( currentNode.getAttributes().getNamedItem( "name" ).getNodeValue(), currentNode.getTextContent() );
		}
		

		if ( currentDevice.isActive() )
		{				
			if (log.isDebugEnabled())
				log.debug( "Extracted: " + currentDevice );

			DeviceManager.instance().registerDevice( currentDevice );
		}
		
	}
	
	private String getValue( Node deviceNode, String attributeName )
	{
		Node attrNode = deviceNode.getAttributes().getNamedItem( attributeName );
		if ( attrNode != null )
			return attrNode.getNodeValue();
		else
			return null;
	}
	
	private NodeList getNodes( Document xmlDocument, String xPathExpression )
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
