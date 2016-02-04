<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class AdapterModel extends AbstractXMLModel
{
	private static final String ADAPTER_ID = "adapterId=";
	private static final String TITLE = "//ns2:tooladapter/ns3:title";
	private static final String INSTRUCTIONS = "//ns2:tooladapter/ns11:instructions";
	private static final String TASKS = "//ns2:tooladapter/ns11:tasks";
	private static final String HREF = "href";
	
	public AdapterModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns3", "http://purl.org/dc/elements/1.1/" );
		perfectoNamespaceContext.registerNamespace( "ns11", "http://jazz.net/xmlns/alm/qm/qmadapter/v0.1" );
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
	}
	
	public String getTitle()
	{
		return getNode( TITLE ).getTextContent();
	}
	
	public String getInstructions()
	{
		return getAttribute( INSTRUCTIONS, HREF );
	}
	
	public String getTasks()
	{
		return getAttribute( TASKS, HREF );
	}
	
	public String getId()
	{
		String taskUrl = getTasks();
		return taskUrl.substring( taskUrl.indexOf( ADAPTER_ID ) + ADAPTER_ID.length() );
	}
	
}
=======
package com.perfectomobile.integration.rqm.model;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class AdapterModel extends AbstractXMLModel
{
	private static final String ADAPTER_ID = "adapterId=";
	private static final String TITLE = "//ns2:tooladapter/ns3:title";
	private static final String INSTRUCTIONS = "//ns2:tooladapter/ns11:instructions";
	private static final String TASKS = "//ns2:tooladapter/ns11:tasks";
	private static final String HREF = "href";
	
	public AdapterModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns3", "http://purl.org/dc/elements/1.1/" );
		perfectoNamespaceContext.registerNamespace( "ns11", "http://jazz.net/xmlns/alm/qm/qmadapter/v0.1" );
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
	}
	
	public String getTitle()
	{
		return getNode( TITLE ).getTextContent();
	}
	
	public String getInstructions()
	{
		return getAttribute( INSTRUCTIONS, HREF );
	}
	
	public String getTasks()
	{
		return getAttribute( TASKS, HREF );
	}
	
	public String getId()
	{
		String taskUrl = getTasks();
		return taskUrl.substring( taskUrl.indexOf( ADAPTER_ID ) + ADAPTER_ID.length() );
	}
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
