<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class WorkItemModel extends AbstractXMLModel
{
	private static final String TITLE = "//ns2:executionworkitem/ns3:title";
	private static final String CREATOR = "//ns2:executionworkitem/ns3:creator";
	private static final String OWNER = "//ns2:executionworkitem/ns3:title";
	private static final String TEST_CASE = "//ns2:executionworkitem/ns2:testcase";
	private static final String REMOTE_SCRIPT = "//ns2:executionworkitem/ns2:remotescript";
	private static final String HREF = "href";
	private static final String RESOURCE = "ns7:resource";
	
	public WorkItemModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns3", "http://purl.org/dc/elements/1.1/" );
		perfectoNamespaceContext.registerNamespace( "ns12", "http://jazz.net/xmlns/alm/qm/v0.1/executionworkitem/v0.1" );
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns5", "http://jazz.net/xmlns/alm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns7", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
	}
	
	public String getTitle()
	{
		return getNode( TITLE ).getTextContent();
	}
	
	public String getCreator()
	{
		return getNode( CREATOR ).getTextContent();
	}
	
	public String getOwner()
	{
		return getNode( OWNER ).getTextContent();
	}
	
	public String getTestCase()
	{
		return getAttribute( TEST_CASE, HREF );
	}
	
	public String getRemoteScript()
	{
		
		Node scriptNode = getNode( REMOTE_SCRIPT );
		if ( scriptNode != null )
			return getAttribute( REMOTE_SCRIPT, HREF );
		else
			return "";
	}
	
}
=======
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class WorkItemModel extends AbstractXMLModel
{
	private static final String TITLE = "//ns2:executionworkitem/ns3:title";
	private static final String CREATOR = "//ns2:executionworkitem/ns3:creator";
	private static final String OWNER = "//ns2:executionworkitem/ns3:title";
	private static final String TEST_CASE = "//ns2:executionworkitem/ns2:testcase";
	private static final String REMOTE_SCRIPT = "//ns2:executionworkitem/ns2:remotescript";
	private static final String HREF = "href";
	private static final String RESOURCE = "ns7:resource";
	
	public WorkItemModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns3", "http://purl.org/dc/elements/1.1/" );
		perfectoNamespaceContext.registerNamespace( "ns12", "http://jazz.net/xmlns/alm/qm/v0.1/executionworkitem/v0.1" );
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns5", "http://jazz.net/xmlns/alm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns7", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
	}
	
	public String getTitle()
	{
		return getNode( TITLE ).getTextContent();
	}
	
	public String getCreator()
	{
		return getNode( CREATOR ).getTextContent();
	}
	
	public String getOwner()
	{
		return getNode( OWNER ).getTextContent();
	}
	
	public String getTestCase()
	{
		return getAttribute( TEST_CASE, HREF );
	}
	
	public String getRemoteScript()
	{
		
		Node scriptNode = getNode( REMOTE_SCRIPT );
		if ( scriptNode != null )
			return getAttribute( REMOTE_SCRIPT, HREF );
		else
			return "";
	}
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
