package com.perfectoMobile.page.data.provider;

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

import com.perfectoMobile.page.BY;
import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.data.DefaultPageData;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.element.ElementFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLPageDataProvider.
 */
public class XMLPageDataProvider extends AbstractPageDataProvider
{
	
	/** The Constant NAME. */
	private static final String NAME = "name";
	
	/** The Constant ACTIVE. */
	private static final String ACTIVE = "active";
	
	/** The Constant LOCK. */
	private static final String LOCK = "lockRecords";

	/** The x path factory. */
	private XPathFactory xPathFactory;
	
	/** The file name. */
	private File fileName;
	
	/** The resource name. */
	private String resourceName;

	/**
	 * Instantiates a new XML page data provider.
	 *
	 * @param fileName the file name
	 */
	public XMLPageDataProvider( File fileName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.fileName = fileName;
	}

	/**
	 * Instantiates a new XML page data provider.
	 *
	 * @param resourceName the resource name
	 */
	public XMLPageDataProvider( String resourceName )
	{
		xPathFactory = XPathFactory.newInstance();
		this.resourceName = resourceName;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.provider.AbstractPageDataProvider#readPageData()
	 */
	@Override
	public void readPageData()
	{
		if (fileName == null)
		{
			if (log.isInfoEnabled())
				log.info( "Reading from CLASSPATH as " + resourceName );
			readElements( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				if (log.isInfoEnabled())
					log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				readElements( new FileInputStream( fileName ) );
			}
			catch (FileNotFoundException e)
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

			NodeList nodeList = getNodes( xmlDocument, "//data/recordType" );
			for (int i = 0; i < nodeList.getLength(); i++)
				parseType( nodeList.item( i ) );

		}
		catch (Exception e)
		{
			log.fatal( "Error reading CSV Element File", e );
		}
	}

	/**
	 * Parses the type.
	 *
	 * @param siteNode the site node
	 */
	private void parseType( Node siteNode )
	{
		String typeName = siteNode.getAttributes().getNamedItem( NAME ).getNodeValue();
		Node lockNode = siteNode.getAttributes().getNamedItem( LOCK );

		boolean lockRecords = false;
		if (lockNode != null)
			lockRecords = Boolean.parseBoolean( lockNode.getNodeValue() );

		if (log.isDebugEnabled())
			log.debug( "Extracted Record Type [" + typeName + "]" );

		addRecordType( typeName, lockRecords );

		NodeList nodeList = getNodes( siteNode.getOwnerDocument(), "//data/recordType[@name='" + typeName + "']/record" );

		for (int i = 0; i < nodeList.getLength(); i++)
			parseRecord( typeName, nodeList.item( i ) );
	}

	/**
	 * Parses the record.
	 *
	 * @param typeName the type name
	 * @param pageNode the page node
	 */
	private void parseRecord( String typeName, Node pageNode )
	{
		String recordName = pageNode.getAttributes().getNamedItem( NAME ).getNodeValue();

		boolean active = true;
		
		Node activeNode = pageNode.getAttributes().getNamedItem( ACTIVE );
		if ( activeNode != null )
			active = Boolean.parseBoolean( activeNode.getNodeValue() );
		
		if ( !active )
		{
			if (log.isDebugEnabled())
				log.debug( "Record [" + recordName + "] is being ignored as it is inactive" );
			return;
		}
		
		if (log.isDebugEnabled())
			log.debug( "Extracted Record [" + recordName + "]" );

		DefaultPageData currentRecord = new DefaultPageData( typeName, recordName, active );

		for (int i = 0; i < pageNode.getAttributes().getLength(); i++)
		{
			Node attributeNode = pageNode.getAttributes().item( i );
			if (!attributeNode.getNodeName().equals( NAME ))
				currentRecord.addValue( attributeNode.getNodeName(), attributeNode.getNodeValue() );
		}

		for (int i = 0; i < pageNode.getChildNodes().getLength(); i++)
		{
			Node elementNode = pageNode.getChildNodes().item( i );

			if (elementNode.getNodeType() == Node.ELEMENT_NODE)
				currentRecord.addValue( elementNode.getNodeName(), elementNode.getTextContent() );

		}

		addRecord( currentRecord );
	}

	/**
	 * Gets the nodes.
	 *
	 * @param xmlDocument the xml document
	 * @param xPathExpression the x path expression
	 * @return the nodes
	 */
	private NodeList getNodes( Document xmlDocument, String xPathExpression )
	{
		try
		{
			if (log.isDebugEnabled())
				log.debug( "Attempting to return Nodes for [" + xPathExpression + "]" );

			XPath xPath = xPathFactory.newXPath();
			return ( NodeList ) xPath.evaluate( xPathExpression, xmlDocument, XPathConstants.NODESET );
		}
		catch (Exception e)
		{
			log.error( "Error parsing xPath Expression [" + xPathExpression + "]" );
			return null;
		}
	}

}
