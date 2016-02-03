<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model.cloud;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.model.PerfectoNamespaceContext;

public class ExecutionProgressModel extends AbstractXMLModel
{
	private static final String SUCCESS_END_CODE = "Success";
	private static final String STATUS = "//response/status";
	private static final String COMPLETED = "//response/completed";
	private static final String CODE = "//response/flowEndCode";
	private static final String PROGRESS = "//response/progressPercentage";
	
	public ExecutionProgressModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
	}
	
	
	public String getStatus()
	{
		return getNode( STATUS ).getTextContent();
	}
	
	public String getFlowEndCode()
	{
		return getNode( CODE ).getTextContent();
	}
	
	public boolean isSuccess()
	{
		return getFlowEndCode().equals( SUCCESS_END_CODE );
	}
	
	public boolean isCompleted()
	{
		return Boolean.parseBoolean( getNode( COMPLETED ).getTextContent() );
	}
	
	public double getProgress()
	{
		return Double.parseDouble( getNode( PROGRESS ).getTextContent() );
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

public class ExecutionProgressModel extends AbstractXMLModel
{
	private static final String SUCCESS_END_CODE = "Success";
	private static final String STATUS = "//response/status";
	private static final String COMPLETED = "//response/completed";
	private static final String CODE = "//response/flowEndCode";
	private static final String PROGRESS = "//response/progressPercentage";
	
	public ExecutionProgressModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
	}
	
	
	public String getStatus()
	{
		return getNode( STATUS ).getTextContent();
	}
	
	public String getFlowEndCode()
	{
		return getNode( CODE ).getTextContent();
	}
	
	public boolean isSuccess()
	{
		return getFlowEndCode().equals( SUCCESS_END_CODE );
	}
	
	public boolean isCompleted()
	{
		return Boolean.parseBoolean( getNode( COMPLETED ).getTextContent() );
	}
	
	public double getProgress()
	{
		return Double.parseDouble( getNode( PROGRESS ).getTextContent() );
	}
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
