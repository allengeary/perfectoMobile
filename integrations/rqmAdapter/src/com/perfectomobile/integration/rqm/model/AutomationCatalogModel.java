<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class AutomationCatalogModel extends AbstractXMLModel
{
	private static final String ENTRIES = "//rdf:RDF/oslc:ServiceProvider/oslc:service/oslc:Service/oslc:creationFactory";
	private static final String TYPE = "/oslc:CreationFactory/oslc:resourceType";
	private static final String URL = "/oslc:CreationFactory/oslc:creation";
	private static final String DOMAIN = "//rdf:RDF/oslc:ServiceProvider/oslc:service/oslc:Service/oslc:domain";
	private static final String RDF_RESOURCE = "rdf:resource";
	
	public AutomationCatalogModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "oslc", "http://open-services.net/ns/core#" );
		perfectoNamespaceContext.registerNamespace( "dcterms", "http://purl.org/dc/terms/" );
		perfectoNamespaceContext.registerNamespace( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
	}
	
	public int getEntryCount()
	{
		NodeList nodes = getNodes( ENTRIES );
		if ( nodes != null )
			return nodes.getLength();
		else
			return 0;
	}
	
	public String getType( int entryId )
	{
		return getAttribute( ENTRIES + "[" + entryId + "]" + TYPE, RDF_RESOURCE );
	}
	
	public String getUrl( int entryId )
	{
		return getAttribute( ENTRIES + "[" + entryId + "]" + URL, RDF_RESOURCE );
	}
	
	public String getDomain()
	{
		return getAttribute( DOMAIN, RDF_RESOURCE );
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

public class AutomationCatalogModel extends AbstractXMLModel
{
	private static final String ENTRIES = "//rdf:RDF/oslc:ServiceProvider/oslc:service/oslc:Service/oslc:creationFactory";
	private static final String TYPE = "/oslc:CreationFactory/oslc:resourceType";
	private static final String URL = "/oslc:CreationFactory/oslc:creation";
	private static final String DOMAIN = "//rdf:RDF/oslc:ServiceProvider/oslc:service/oslc:Service/oslc:domain";
	private static final String RDF_RESOURCE = "rdf:resource";
	
	public AutomationCatalogModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "oslc", "http://open-services.net/ns/core#" );
		perfectoNamespaceContext.registerNamespace( "dcterms", "http://purl.org/dc/terms/" );
		perfectoNamespaceContext.registerNamespace( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
	}
	
	public int getEntryCount()
	{
		NodeList nodes = getNodes( ENTRIES );
		if ( nodes != null )
			return nodes.getLength();
		else
			return 0;
	}
	
	public String getType( int entryId )
	{
		return getAttribute( ENTRIES + "[" + entryId + "]" + TYPE, RDF_RESOURCE );
	}
	
	public String getUrl( int entryId )
	{
		return getAttribute( ENTRIES + "[" + entryId + "]" + URL, RDF_RESOURCE );
	}
	
	public String getDomain()
	{
		return getAttribute( DOMAIN, RDF_RESOURCE );
	}
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
