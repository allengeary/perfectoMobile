package com.perfectoMobile.page.element.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
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

import com.perfectoMobile.page.BY;
import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.element.ElementFactory;

/**
 * The Class XMLElementProvider.
 */
public class XMLElementProvider extends AbstractElementProvider
{
	private static final String NAME = "name";
	private static final String DESCRIPTOR = "descriptor";
	private static final String VALUE = "value";
	private static final String CONTEXT_NAME = "contextName";
	private static final String FILE_NAME = "fileName";

	private Map<String,Element> elementMap = new HashMap<String,Element>(20);
	private XPathFactory xPathFactory;
	private File fileName;
	private String resourceName;
	private boolean asResource = false;
	
	/**
	 * Instantiates a new XML element provider.
	 *
	 * @param fileName the file name
	 */
	public XMLElementProvider( File fileName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.fileName = fileName;
		readElements();
	}
	
	/**
	 * Instantiates a new XML element provider.
	 *
	 * @param resourceName the resource name
	 */
	public XMLElementProvider( String resourceName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.resourceName = resourceName;
		readElements();
	}
	
	private void readElements()
	{
		if ( fileName == null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Reading from CLASSPATH as CSVElementProvider.elementFile" );
			asResource = true;
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
		
			NodeList nodeList = getNodes( xmlDocument, "//elementDefinition/site");
			for ( int i=0; i<nodeList.getLength(); i++ )
				parseSite( nodeList.item( i ) );
			
			nodeList = getNodes( xmlDocument, "//elementDefinition/import");
			for ( int i=0; i<nodeList.getLength(); i++ )
				parseImport( nodeList.item( i ) );
		}
		catch( Exception e )
		{
			log.fatal( "Error reading CSV Element File", e );
		}
	}
	
	private void parseImport( Node importNode )
	{
		Node fileName = importNode.getAttributes().getNamedItem( FILE_NAME );

		if (fileName != null)
		{
			try
			{
				
				
				if (log.isInfoEnabled())
					log.info( "Attempting to import file [" + Paths.get(".").toAbsolutePath().normalize().toString() + fileName.getNodeValue() + "]" );

				readElements( new FileInputStream( fileName.getNodeValue() ) );
			}
			catch (FileNotFoundException e)
			{
				log.fatal( "Could not read from " + fileName, e );
			}
		}
	}
	
	private void parseSite( Node siteNode )
	{
		String siteName = siteNode.getAttributes().getNamedItem( NAME ).getNodeValue();
		
		if ( log.isDebugEnabled() )
			log.debug( "Extracted Site [" + siteName + "]" );
		
		
		if ( PageManager.instance().getSiteName().equals( siteName ) )
		{
			NodeList nodeList = getNodes( siteNode.getOwnerDocument(), "//elementDefinition/site[@name='" + siteName + "']/page");
			
			for ( int i=0; i<nodeList.getLength(); i++ )
				parsePage( siteName, nodeList.item( i ) );
		}
	}
	
	private void parsePage( String siteName, Node pageNode )
	{
		String pageName = pageNode.getAttributes().getNamedItem( NAME ).getNodeValue();
		
		Node parentNode = pageNode;
		
		if ( log.isDebugEnabled() )
			log.debug( "Extracted Page [" + pageName + "]" );
		
		NodeList nodeList = getNodes( parentNode.getOwnerDocument(), "//elementDefinition/site[@name='" + siteName + "']/page[@name='" + pageName + "']/element");
		
		for ( int i=0; i<nodeList.getLength(); i++ )
			parseElement( siteName, pageName, nodeList.item( i ) );
	}
	
	private void parseElement( String siteName, String pageName, Node elementNode )
	{
		String elementName = elementNode.getAttributes().getNamedItem( NAME ).getNodeValue();
		
		if ( log.isDebugEnabled() )
			log.debug( "Extracted Element [" + elementName + "]" );
		
		Node descriptor = elementNode.getAttributes().getNamedItem( DESCRIPTOR );
		Node value = elementNode.getAttributes().getNamedItem( VALUE );
		String contextName = null;
		Node contextNode = elementNode.getAttributes().getNamedItem( CONTEXT_NAME );
		if ( contextNode != null )
			contextName = contextNode.getNodeValue();
		
		if ( descriptor != null && value != null )
		{
			ElementDescriptor elementDescriptor = new ElementDescriptor( siteName,  pageName, elementName );
			Element currentElement = ElementFactory.instance().createElement( BY.valueOf( descriptor.getNodeValue() ), value.getNodeValue(), elementName, pageName, contextName );
			
			if ( log.isInfoEnabled() )
				log.info( "Adding XML Element using [" + elementDescriptor.toString() + "] as [" + currentElement + "]" );
			elementMap.put(elementDescriptor.toString(), currentElement );
		}
		else
			throw new IllegalArgumentException( "An element must define a descriptor and a value" );
		
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
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.element.provider.AbstractElementProvider#_getElement(com.perfectoMobile.page.ElementDescriptor)
	 */
	@Override
	protected Element _getElement( ElementDescriptor elementDescriptor )
	{
		return elementMap.get(  elementDescriptor.toString() );
	}
	
	
}
