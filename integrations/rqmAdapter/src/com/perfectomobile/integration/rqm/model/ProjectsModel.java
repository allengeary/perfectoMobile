<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class ProjectsModel extends AbstractXMLModel
{
	private static final String ENTRIES = "//atom:feed/atom:entry";
	private static final String URL = "/atom:id";
	private static final String NAME = "/atom:title";
	private static final String ALIAS = "/atom:summary";
	
	public ProjectsModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "atom", "http://www.w3.org/2005/Atom" );
		
	}
	
	public int getEntryCount()
	{
		NodeList nodes = getNodes( ENTRIES );
		if ( nodes != null )
			return nodes.getLength();
		else
			return 0;
	}
	
	public String getId( int entryId )
	{
		return getNode( ENTRIES + "[" + entryId + "]" + URL ).getTextContent();
	}
	
	public String getName( int entryId )
	{
		return getNode( ENTRIES + "[" + entryId + "]" + NAME ).getTextContent();
	}
	
	public String getAlias( int entryId )
	{
		return getNode( ENTRIES + "[" + entryId + "]" + ALIAS ).getTextContent();
	}
	
}
=======
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class ProjectsModel extends AbstractXMLModel
{
	private static final String ENTRIES = "//atom:feed/atom:entry";
	private static final String URL = "/atom:id";
	private static final String NAME = "/atom:title";
	private static final String ALIAS = "/atom:summary";
	
	public ProjectsModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "atom", "http://www.w3.org/2005/Atom" );
		
	}
	
	public int getEntryCount()
	{
		NodeList nodes = getNodes( ENTRIES );
		if ( nodes != null )
			return nodes.getLength();
		else
			return 0;
	}
	
	public String getId( int entryId )
	{
		return getNode( ENTRIES + "[" + entryId + "]" + URL ).getTextContent();
	}
	
	public String getName( int entryId )
	{
		return getNode( ENTRIES + "[" + entryId + "]" + NAME ).getTextContent();
	}
	
	public String getAlias( int entryId )
	{
		return getNode( ENTRIES + "[" + entryId + "]" + ALIAS ).getTextContent();
	}
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
