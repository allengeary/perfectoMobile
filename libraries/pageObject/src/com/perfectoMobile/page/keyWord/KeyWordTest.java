package com.perfectoMobile.page.keyWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.data.PageDataManager;
import com.perfectoMobile.page.keyWord.step.spi.KWSLoopBreak;

/**
 * The Class KeyWordTest.
 */
public class KeyWordTest
{
	
	private Log log = LogFactory.getLog( KeyWordTest.class );
	private String name;
	private boolean active;
	private String[] dataProviders;
	private String dataDriver;
	private boolean timed;
	private String linkId;
	private String os;
	
	private List<KeyWordStep> stepList = new ArrayList<KeyWordStep>( 10 );
	
	/**
	 * Instantiates a new key word test.
	 *
	 * @param name the name
	 * @param active the active
	 * @param dataProviders the data providers
	 * @param dataDriver the data driver
	 * @param timed the timed
	 * @param linkId the link id
	 */
	public KeyWordTest( String name, boolean active, String dataProviders, String dataDriver, boolean timed, String linkId, String os )
	{
		this.name = name;
		this.active = active;
		if ( dataProviders != null )
			this.dataProviders = dataProviders.split( "," );
		this.dataDriver = dataDriver;
		this.timed = timed;
		this.linkId = linkId;
		this.os = os;
	}

	/**
	 * Copy test.
	 *
	 * @param testName the test name
	 * @return the key word test
	 */
	public KeyWordTest copyTest( String testName )
	{
		KeyWordTest newTest = new KeyWordTest( testName, active, null, dataDriver, timed, linkId, os );
		newTest.dataProviders = dataProviders;
		newTest.stepList = stepList;
		return newTest;
	}
	
	/**
	 * Gets the data driver.
	 *
	 * @return the data driver
	 */
	public String getDataDriver()
	{
		return dataDriver;
	}

	/**
	 * Adds the step.
	 *
	 * @param step the step
	 */
	public void addStep( KeyWordStep step )
	{
		if ( step.isActive() )
		{
			if ( log.isInfoEnabled() )
				log.info( "Adding Step [" + step.getName() + "] to [" + name + "]"  );
			stepList.add( step );
		}
	}
	
	/**
	 * Gets the data providers.
	 *
	 * @return the data providers
	 */
	public String[] getDataProviders()
	{
		return dataProviders;
	}
	
	/**
	 * Gets the data providers.
	 *
	 * @return the data providers
	 */
	public String getDataProvidersAsString()
	{
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(" [");
		for ( String provider : dataProviders )
			sBuilder.append( provider ).append(", " );
		sBuilder.append( "]" );
		return sBuilder.toString();
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName( String name )
	{
		this.name = name;
	}
	
	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive()
	{
		return active;
	}
	
	/**
	 * Checks if is timed.
	 *
	 * @return true, if is timed
	 */
	public boolean isTimed()
	{
		return timed;
	}

	/**
	 * Execute test.
	 *
	 * @param webDriver the web driver
	 * @param contextMap the context map
	 * @param dataMap the data map
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean executeTest( WebDriver webDriver, Map<String,Object> contextMap, Map<String,PageData> dataMap ) throws Exception
	{
		if ( log.isInfoEnabled() )
			log.info( "*** Executing Test " + name + ( linkId != null ? " linked to " + linkId : "" ) );
		
		long startTime = System.currentTimeMillis();
		
		boolean stepSuccess = true;
		Exception stepException = null;
		
		for( KeyWordStep step : stepList )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Executing Step [" + step.getName() + "]" );
			
			Page page = null;
			if ( step.getPageName() != null )
			{
				if ( log.isInfoEnabled() )
					log.info( "Attempting to create page for " + step.getPageName() );
				page = PageManager.instance().createPage( KeyWordDriver.instance().getPage( step.getPageName() ), webDriver );
			}
			
			long stepStart = System.currentTimeMillis();
			
			stepSuccess = step.executeStep( page, webDriver, contextMap, dataMap );
			
			if ( !stepSuccess )
			{
				if ( log.isWarnEnabled() )
					log.warn( "***** Step [" + step.getName() + "] Failed" );
				
				if ( step.isTimed() )
					PageManager.instance().addExecutionTiming( getName() + "." + step.getPageName() + "." + step.getName() + "." + step.getClass().getSimpleName(), System.currentTimeMillis() - stepStart );
				
				if ( timed )
					PageManager.instance().addExecutionTiming( getName(), System.currentTimeMillis() - startTime );
				
				if ( PageManager.instance().getThrowable() == null )
					PageManager.instance().setThrowable( new IllegalStateException( step.toError() ) );
				
				return false;

			}	
			
			if ( step.isTimed() )
				PageManager.instance().addExecutionTiming( getName() + "." + step.getPageName() + "." + step.getName(), System.currentTimeMillis() - stepStart );
		}
		
		if ( timed )
			PageManager.instance().addExecutionTiming( getName(), System.currentTimeMillis() - startTime );
		
		return stepSuccess;
	}

	public String getOs()
	{
		return os;
	}

	public void setOs( String os )
	{
		this.os = os;
	}
	
	
}
