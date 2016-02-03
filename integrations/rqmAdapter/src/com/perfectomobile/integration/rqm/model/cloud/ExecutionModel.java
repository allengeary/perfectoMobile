<<<<<<< HEAD
package com.perfectomobile.integration.rqm.model.cloud;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.rqm.bean.StepResult;

public class ExecutionModel extends AbstractXMLModel
{
	private static final String ID = "//response/executionId";
	private static final String KEY = "//response/reportKey";
	
	private StepResult stepResult;
	private double progress;
	
	public StepResult getStepResult()
	{
		return stepResult;
	}


	public void setStepResult( StepResult stepResult )
	{
		this.stepResult = stepResult;
	}
	
	


	public double getProgress()
	{
		return progress;
	}


	public void setProgress( double progress )
	{
		this.progress = progress;
	}


	public ExecutionModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
	}
	
	
	public String getId()
	{
		return getNode( ID ).getTextContent();
	}
	
	public String getKey()
	{
		return getNode( KEY ).getTextContent();
	}
	
	
}
=======
package com.perfectomobile.integration.rqm.model.cloud;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.perfectomobile.integration.model.AbstractXMLModel;
import com.perfectomobile.integration.rqm.bean.StepResult;

public class ExecutionModel extends AbstractXMLModel
{
	private static final String ID = "//response/executionId";
	private static final String KEY = "//response/reportKey";
	
	private StepResult stepResult;
	private double progress;
	
	public StepResult getStepResult()
	{
		return stepResult;
	}


	public void setStepResult( StepResult stepResult )
	{
		this.stepResult = stepResult;
	}
	
	


	public double getProgress()
	{
		return progress;
	}


	public void setProgress( double progress )
	{
		this.progress = progress;
	}


	public ExecutionModel( String xmlData ) throws SAXException, IOException, ParserConfigurationException
	{
		super( xmlData );
	}
	
	
	public String getId()
	{
		return getNode( ID ).getTextContent();
	}
	
	public String getKey()
	{
		return getNode( KEY ).getTextContent();
	}
	
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
