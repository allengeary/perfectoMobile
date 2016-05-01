package com.perfectomobile.integration.rqm.model.tasks;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class TasksModel extends AbstractXMLModel
{
	private static final String TASKS = "//ns2:adaptertask/ns11:task";
	private static final String HREF = "href";
	
	public TasksModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns11", "http://jazz.net/xmlns/alm/qm/qmadapter/v0.1" );
		
	}
	
	public int getEntryCount()
	{
		return getNodes( TASKS ).getLength();
	}
	
	public String[] getTasks()
	{
		NodeList tasks = getNodes( TASKS );
		String[] taskList = new String[ tasks.getLength() ];
		
		for ( int i=0; i<tasks.getLength(); i++ )
			taskList[ i ] = tasks.item( i ).getAttributes().getNamedItem( HREF ).getNodeValue();
		
		return taskList;
	}
	
}
