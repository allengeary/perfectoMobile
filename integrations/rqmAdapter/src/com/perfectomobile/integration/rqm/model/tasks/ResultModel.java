package com.perfectomobile.integration.rqm.model.tasks;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class ResultModel extends AbstractXMLModel
{
	private static final String ID = "//atom:entry/rqm:resultId";
	private static final String ITEM_ID = "//atom:entry/rqm:resultItemId";
	private static final String EXTERNAL_ID = "//atom:entry/rqm:resultExternalId";

	
	public ResultModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "rqm", "http://schema.ibm.com/rqm/2007#executionresult" );
		perfectoNamespaceContext.registerNamespace( "atom", "http://www.w3.org/2005/Atom" );
	}
	
	public String getId()
	{
		return getNode( ID ).getTextContent();
	}
	
	public String getItemId()
	{
		return getNode( ITEM_ID ).getTextContent();
	}
	
	public String getExternalId()
	{
		return getNode( EXTERNAL_ID ).getTextContent();
	}

}