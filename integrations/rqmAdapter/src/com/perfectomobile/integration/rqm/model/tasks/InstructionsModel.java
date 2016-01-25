<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model.tasks;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class InstructionsModel extends AbstractXMLModel
{
	private static final String INSTRUCTIONS = "//ns2:instructions/ns11:workavailable";
	
	public InstructionsModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns11", "http://jazz.net/xmlns/alm/qm/qmadapter/v0.1" );
		
	}
	
	public boolean hasInstructions()
	{
		return Boolean.parseBoolean( getNode( INSTRUCTIONS ).getTextContent() );
	}
	
}
=======
package com.perfectomobile.integration.rqm.model.tasks;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class InstructionsModel extends AbstractXMLModel
{
	private static final String INSTRUCTIONS = "//ns2:instructions/ns11:workavailable";
	
	public InstructionsModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
		perfectoNamespaceContext = new PerfectoNamespaceContext();
		perfectoNamespaceContext.registerNamespace( "ns2", "http://jazz.net/xmlns/alm/qm/v0.1/" );
		perfectoNamespaceContext.registerNamespace( "ns11", "http://jazz.net/xmlns/alm/qm/qmadapter/v0.1" );
		
	}
	
	public boolean hasInstructions()
	{
		return Boolean.parseBoolean( getNode( INSTRUCTIONS ).getTextContent() );
	}
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
