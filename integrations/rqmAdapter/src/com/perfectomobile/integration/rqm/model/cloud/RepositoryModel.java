package com.perfectomobile.integration.rqm.model.cloud;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class RepositoryModel extends AbstractXMLModel
{
	private static final String ENTRIES = "//response/items/item";
	
	public RepositoryModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
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
		return getNode( ENTRIES + "[" + (entryId + 1) + "]" ).getTextContent();
	}
	
	public String[] getEntries()
	{
		int entryCount = getEntryCount();
		
		String[] entryArray = new String[ entryCount ];
		for ( int i=0; i<entryCount; i++ )
		{
			entryArray[ i ] = getId( i );
		}
		
		return entryArray;
		
	}
	
	
}
