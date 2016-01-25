<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model.cloud;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class HandsetModel extends AbstractXMLModel
{
	private static final String ENTRIES = "//handsets/handset";
	private static final String MANUFACTURER = "//handset/manufacturer";
	private static final String MODEL = "//handset/model";
	
	public HandsetModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
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
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/deviceId" ).getTextContent();
	}
	
	public String getManufacturer( int entryId )
	{
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/manufacturer" ).getTextContent();
	}
	
	public String getModel( int entryId )
	{
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/model" ).getTextContent();
	}
	
	public String getStatus( int entryId )
	{
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/status" ).getTextContent();
	}

	public String getManufacturer()
	{
		return getNode( MANUFACTURER ).getTextContent();
	}
	
	public String getModel()
	{
		return getNode( MODEL ).getTextContent();
	}
	
	public String[] getEntries()
	{
		int entryCount = getEntryCount();
		
		String[] entryArray = new String[ entryCount ];
		for ( int i=0; i<entryCount; i++ )
		{
			entryArray[ i ] = getValue( getManufacturer( i ) ) + " " + getValue( getModel( i ) ) + " : " + getId( i );
		}
		
		return entryArray;
		
	}
	
	private String getValue( String currentValue )
	{
		if ( currentValue == null )
			return "";
		else
			return currentValue;
	}
	
	
}
=======
package com.perfectomobile.integration.rqm.model.cloud;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class HandsetModel extends AbstractXMLModel
{
	private static final String ENTRIES = "//handsets/handset";
	private static final String MANUFACTURER = "//handset/manufacturer";
	private static final String MODEL = "//handset/model";
	
	public HandsetModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
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
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/deviceId" ).getTextContent();
	}
	
	public String getManufacturer( int entryId )
	{
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/manufacturer" ).getTextContent();
	}
	
	public String getModel( int entryId )
	{
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/model" ).getTextContent();
	}
	
	public String getStatus( int entryId )
	{
		return getNode( ENTRIES + "[" + (entryId + 1) + "]/status" ).getTextContent();
	}

	public String getManufacturer()
	{
		return getNode( MANUFACTURER ).getTextContent();
	}
	
	public String getModel()
	{
		return getNode( MODEL ).getTextContent();
	}
	
	public String[] getEntries()
	{
		int entryCount = getEntryCount();
		
		String[] entryArray = new String[ entryCount ];
		for ( int i=0; i<entryCount; i++ )
		{
			entryArray[ i ] = getValue( getManufacturer( i ) ) + " " + getValue( getModel( i ) ) + " : " + getId( i );
		}
		
		return entryArray;
		
	}
	
	private String getValue( String currentValue )
	{
		if ( currentValue == null )
			return "";
		else
			return currentValue;
	}
	
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
