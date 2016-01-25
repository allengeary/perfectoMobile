<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;
import com.perfectomobile.integration.model.XMLModel;
import com.perfectomobile.integration.ssl.SSLUtils;

public class RootServicesModel extends AbstractXMLModel
{
	private static final String RDF_RESOURCE = "rdf:resource";
	private static final String SERVICE_ROOT = "/rdf:Description/jd:viewletServiceRoot";
	private static final String USERS = "/rdf:Description/jfs:users";
	private static final String CURRENT_USER = "/rdf:Description/jfs:currentUser";
	private static final String DISCOVERY = "/rdf:Description/jd:discovery";
	private static final String QM_CATALOG = "/rdf:Description/oslc_qm:qmServiceProviders";
	private static final String AUTO_CATALOG = "/rdf:Description/oslc_auto:autoServiceProviders";
	
	public RootServicesModel( URL rootServicesUrl ) throws SAXException, IOException, ParserConfigurationException
	{
		super( rootServicesUrl );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
		perfectoNamespaceContext.registerNamespace( "dc", "http://purl.org/dc/terms/" );
		perfectoNamespaceContext.registerNamespace( "jd", "http://jazz.net/xmlns/prod/jazz/discovery/1.0/" );
		perfectoNamespaceContext.registerNamespace( "jfs", "http://jazz.net/xmlns/prod/jazz/jfs/1.0/" );
		perfectoNamespaceContext.registerNamespace( "oslc_cm", "http://open-services.net/xmlns/cm/1.0/" );
		perfectoNamespaceContext.registerNamespace( "oslc_qm", "http://open-services.net/xmlns/qm/1.0/" );
		perfectoNamespaceContext.registerNamespace( "oslc_auto", "http://open-services.net/ns/auto#" );
		
	}
	
	public String getServiceRoot()
	{
		return getAttribute( SERVICE_ROOT, RDF_RESOURCE );
	}
	
	public String getUsers()
	{
		return getAttribute( USERS, RDF_RESOURCE );
	}
	
	public String getCurrentUser()
	{
		return getAttribute( CURRENT_USER, RDF_RESOURCE );
	}
	
	public String getDiscovery()
	{
		return getAttribute( DISCOVERY, RDF_RESOURCE );
	}
	
	public String getQMCatalog()
	{
		return getAttribute( QM_CATALOG, RDF_RESOURCE );
	}
	
	public String getAutoCatalog()
	{
		return getAttribute( AUTO_CATALOG, RDF_RESOURCE );
	}
}
=======
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;
import com.perfectomobile.integration.model.XMLModel;
import com.perfectomobile.integration.ssl.SSLUtils;

public class RootServicesModel extends AbstractXMLModel
{
	private static final String RDF_RESOURCE = "rdf:resource";
	private static final String SERVICE_ROOT = "/rdf:Description/jd:viewletServiceRoot";
	private static final String USERS = "/rdf:Description/jfs:users";
	private static final String CURRENT_USER = "/rdf:Description/jfs:currentUser";
	private static final String DISCOVERY = "/rdf:Description/jd:discovery";
	private static final String QM_CATALOG = "/rdf:Description/oslc_qm:qmServiceProviders";
	private static final String AUTO_CATALOG = "/rdf:Description/oslc_auto:autoServiceProviders";
	
	public RootServicesModel( URL rootServicesUrl ) throws SAXException, IOException, ParserConfigurationException
	{
		super( rootServicesUrl );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
		perfectoNamespaceContext.registerNamespace( "dc", "http://purl.org/dc/terms/" );
		perfectoNamespaceContext.registerNamespace( "jd", "http://jazz.net/xmlns/prod/jazz/discovery/1.0/" );
		perfectoNamespaceContext.registerNamespace( "jfs", "http://jazz.net/xmlns/prod/jazz/jfs/1.0/" );
		perfectoNamespaceContext.registerNamespace( "oslc_cm", "http://open-services.net/xmlns/cm/1.0/" );
		perfectoNamespaceContext.registerNamespace( "oslc_qm", "http://open-services.net/xmlns/qm/1.0/" );
		perfectoNamespaceContext.registerNamespace( "oslc_auto", "http://open-services.net/ns/auto#" );
		
	}
	
	public String getServiceRoot()
	{
		return getAttribute( SERVICE_ROOT, RDF_RESOURCE );
	}
	
	public String getUsers()
	{
		return getAttribute( USERS, RDF_RESOURCE );
	}
	
	public String getCurrentUser()
	{
		return getAttribute( CURRENT_USER, RDF_RESOURCE );
	}
	
	public String getDiscovery()
	{
		return getAttribute( DISCOVERY, RDF_RESOURCE );
	}
	
	public String getQMCatalog()
	{
		return getAttribute( QM_CATALOG, RDF_RESOURCE );
	}
	
	public String getAutoCatalog()
	{
		return getAttribute( AUTO_CATALOG, RDF_RESOURCE );
	}
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
