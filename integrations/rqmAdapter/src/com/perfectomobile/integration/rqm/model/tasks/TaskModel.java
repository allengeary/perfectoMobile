package com.perfectomobile.integration.rqm.model.tasks;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class TaskModel extends AbstractXMLModel
{
	private static final String TITLE = "//ns2:adaptertask/ns11:task";
	private static final String ADAPTER_ID = "//ns2:adaptertask/ns11:task";
	private static final String TYPE = "//ns2:adaptertask/ns10:type";
	private static final String PROGRESS = "//ns2:adaptertask/ns10:progress";
	private static final String VARIABLES = "//ns2:adaptertask/ns11:task";
	private static final String UPDATE_URL = "//ns2:adaptertask/ns10:updateURL";
	private static final String RESULT_URL = "//ns2:adaptertask/ns10:resultURL";
	private static final String RESOURCE_URL = "//ns2:adaptertask/ns2:resource";
	private static final String EXECUTION_URL = "//ns2:adaptertask/ns2:executionresult";
	private static final String SCRIPT_TYPE = "//ns2:adaptertask/ns10:scriptType";
	private static final String RESOURCE_TYPE = "//ns2:adaptertask/ns10:resourceType";
	private static final String WORK_ITEM = "//ns2:adaptertask/ns2:executionworkitem";
	
	private static final String SCRIPT = "//ns2:adaptertask/ns10:scriptKey";
	private static final String DEVICE = "//ns2:adaptertask/ns10:Device";
	
	private static final String HREF = "href";
	
	public TaskModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns3", "http://purl.org/dc/elements/1.1/" );
		
		perfectoNamespaceContext.registerNamespace( "ns5", "http://jazz.net/xmlns/alm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns10", "http://jazz.net/xmlns/alm/qm/qmadapter/task/v0.1" );
		perfectoNamespaceContext.registerNamespace( "ns11", "http://jazz.net/xmlns/alm/qm/qmadapter/v0.1" );
		
	}
	
	public String getDeviceList()
	{
		return getNode( DEVICE ).getTextContent();
	}
	
	public String getScriptList()
	{
		return getNode( SCRIPT ).getTextContent();
	}
	
	public String getTitle()
	{
		return getNode( TITLE ).getTextContent();
	}
	
	public String getAdapterId()
	{
		return getNode( ADAPTER_ID ).getTextContent();
	}
	
	public String getType()
	{
		return getNode( TYPE ).getTextContent();
	}
	
	public String getProgress()
	{
		return getNode( PROGRESS ).getTextContent();
	}
	
	public String getUpdateUrl()
	{
		return getAttribute( UPDATE_URL, HREF );
	}
	
	public String getResultUrl()
	{
		return getAttribute( RESULT_URL, HREF );
	}
	
	public String getWorkItem()
	{
		return getAttribute( WORK_ITEM, HREF );
	}
	
	public String getResourceUrl()
	{
		return getAttribute( RESOURCE_URL, HREF );
	}
	
	public String getExecutionUrl()
	{
		return getAttribute( EXECUTION_URL, HREF );
	}
	
	public String getScriptType()
	{
		return getNode( SCRIPT_TYPE ).getTextContent();
	}
	
	public String getResourceType()
	{
		return getNode( RESOURCE_TYPE ).getTextContent();
	}
	
}
