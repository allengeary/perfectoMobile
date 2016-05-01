package com.perfectomobile.integration.rqm.model;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class ServiceProviderCatalogModel extends AbstractXMLModel
{
	private static final String TITLE = "//rdf:RDF/oslc:ServiceProviderCatalog/dcterms:title";
	private static final String DOMAIN = "//rdf:RDF/oslc:ServiceProviderCatalog/oslc:domain";
	
	private static final String SERVICE_PROVIDER = "//rdf:RDF/oslc:ServiceProviderCatalog/oslc:serviceProvider/oslc:ServiceProvider";
	private static final String BASE_URL = "/oslc:detail";
	private static final String NAME = "/dcterms:title";
	
	private static final String RESOURCE = "rdf:resource";
	private static final String ABOUT = "rdf:about";
	
	public ServiceProviderCatalogModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
		perfectoNamespaceContext.registerNamespace( "oslc", "http://open-services.net/ns/core#" );
		perfectoNamespaceContext.registerNamespace( "dcterms", "http://purl.org/dc/terms/" );
	}
	
	public int getEntryCount()
	{
		NodeList nodes = getNodes( SERVICE_PROVIDER );
		if ( nodes != null )
			return nodes.getLength();
		else
			return 0;
	}
	
	public String getTitle()
	{
		return getNode( TITLE ).getTextContent();
	}
	
	public String getDomain()
	{
		return getAttribute( DOMAIN, RESOURCE );
	}
	
	public String getName( int entryId )
	{
		return getNode( SERVICE_PROVIDER + "[" + entryId + "]" + NAME ).getTextContent();
	}
	
	public String getBaseURL( int entryId )
	{
		return getAttribute( SERVICE_PROVIDER + "[" + entryId + "]" + BASE_URL, RESOURCE );
	}
	
	public String getServiceProvider( int entryId )
	{
		return getAttribute( SERVICE_PROVIDER + "[" + entryId + "]", ABOUT );
	}
	
}
