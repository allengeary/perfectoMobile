package com.perfectoMobile.page.keyWord;

import java.util.ArrayList;
import java.util.Collection;
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
import com.perfectoMobile.page.keyWord.provider.KeyWordProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyWordDriver.
 */
public class KeyWordDriver
{
	
	/** The test list. */
	private List<String> testList = new ArrayList<String>( 20 );
	
	/** The test map. */
	private Map<String, KeyWordTest> testMap = new HashMap<String, KeyWordTest>( 10 );
	
	/** The inactive test map. */
	private Map<String, KeyWordTest> inactiveTestMap = new HashMap<String, KeyWordTest>( 10 );
	
	/** The function map. */
	private Map<String, KeyWordTest> functionMap = new HashMap<String, KeyWordTest>( 10 );
	
	/** The page map. */
	private Map<String, Class<Page>> pageMap = new HashMap<String, Class<Page>>( 10 );

	/** The context map. */
	private ThreadLocal<Map<String, Object>> contextMap = new ThreadLocal<Map<String, Object>>();
	
	private Map<String,List<KeyWordTest>> tagMap = new HashMap<String,List<KeyWordTest>>( 10 );

	/** The singleton. */
	private static KeyWordDriver singleton = new KeyWordDriver();

	/**
	 * Instance.
	 *
	 * @return the key word driver
	 */
	public static KeyWordDriver instance()
	{
		return singleton;
	}

	/**
	 * Instantiates a new key word driver.
	 */
	private KeyWordDriver()
	{

	}

	/**
	 * Load tests.
	 *
	 * @param keyWordProvider
	 *            the key word provider
	 */
	public void loadTests( KeyWordProvider keyWordProvider )
	{
		keyWordProvider.readData();
	}

	/** The log. */
	private Log log = LogFactory.getLog( KeyWordDriver.class );

	/**
	 * Adds the page.
	 *
	 * @param pageName
	 *            the page name
	 * @param pageClass
	 *            the page class
	 */
	public void addPage( String pageName, Class<Page> pageClass )
	{
		if (log.isDebugEnabled())
			log.debug( "Mapping Page [" + pageName + "] to [" + pageClass.getName() + "]" );
		pageMap.put( pageName, pageClass );
	}

	/**
	 * Adds the test.
	 *
	 * @param test
	 *            the test
	 */
	public void addTest( KeyWordTest test )
	{
		if (test.isActive())
		{
			if (log.isInfoEnabled())
				log.info( "Adding test [" + test.getName() + "]" );
			testList.add( test.getName() );
			testMap.put( test.getName(), test );
			
			//
			// Add the Tagged tests
			//
			for ( String tag : test.getTags() )
			{
			    List<KeyWordTest> tagList = tagMap.get( tag.toLowerCase() );
			    if ( tagList == null )
			    {
			        tagList = new ArrayList<KeyWordTest>( 10 );
			        tagMap.put( tag.toLowerCase(), tagList );
			    }
			    
			    tagList.add( test );
			}
		}
		else
			inactiveTestMap.put( test.getName(), test );
	}

	/**
	 * Adds the function.
	 *
	 * @param test
	 *            the test
	 */
	public void addFunction( KeyWordTest test )
	{
		if (test.isActive())
		{
			if (log.isDebugEnabled())
				log.debug( "Adding function [" + test.getName() + "]" );
			functionMap.put( test.getName(), test );
		}
	}

	/**
	 * Gets the page.
	 *
	 * @param pageName
	 *            the page name
	 * @return the page
	 */
	public Class<Page> getPage( String pageName )
	{
		return pageMap.get( pageName );
	}

	/**
	 * Gets the context map.
	 *
	 * @return the context map
	 */
	public Map<String, Object> getContextMap()
	{
		return contextMap.get();
	}

	/**
	 * Execution function.
	 *
	 * @param testName
	 *            the test name
	 * @param webDriver
	 *            the web driver
	 * @param dataMap
	 *            the data map
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean executionFunction( String testName, WebDriver webDriver, Map<String, PageData> dataMap, Map<String,Page> pageMap ) throws Exception
	{
		if (log.isDebugEnabled())
			log.debug( "Attempting to locate function/test [" + testName + "]" );

		KeyWordTest test = functionMap.get( testName );

		if (test == null)
		{
			test = testMap.get( testName );

			if (test == null)
			{
				test = inactiveTestMap.get( testName );

				if (test == null)
					throw new IllegalArgumentException( "The function [" + testName + "] does not exist" );
			}
		}
		
		if (test.getDataProviders() != null)
		{
			if (log.isInfoEnabled())
				log.info( "Data Provider set as " + test.getDataProvidersAsString() );

			for (String dataProvider : test.getDataProviders())
			{
				if ( !dataMap.containsKey( dataProvider ) )
				{
					//
					// On a function call we only add the page data if it did not already exist
					//
					PageData pageData = PageDataManager.instance().getPageData( dataProvider );
					if (log.isInfoEnabled())
						log.info( "Adding " + dataProvider + " as " + pageData );
					dataMap.put( dataProvider, pageData );
				}
			}
		}

		return test.executeTest( webDriver, contextMap.get(), dataMap, pageMap );
	}

	
	/**
	 * Gets the test.
	 *
	 * @param testName the test name
	 * @return the test
	 */
	public KeyWordTest getTest( String testName )
	{
		KeyWordTest test = testMap.get( testName );

		if (test == null)
			test = inactiveTestMap.get( testName );

		if (test == null)
			test = functionMap.get( testName );
		
		return test;
	}
	
	public Collection<KeyWordTest> getTaggedTests( String[] tagNames ) 
	{
	    Map<String,KeyWordTest> testMap = new HashMap<String,KeyWordTest>( 10 );
	    
	    for ( String tagName : tagNames )
	    {
	        if ( log.isInfoEnabled() )
	            log.info( "Adding Tests by TAG [" + tagName.toLowerCase() + "]" );
	        for ( KeyWordTest t : tagMap.get( tagName.toLowerCase() ) )
	        {
	            if ( log.isDebugEnabled() )
	                log.debug( "Adding Test [" + t.getName() + "]" );
	            testMap.put( t.getName(), t );
	        }
	    }
	    
	    return testMap.values();
	}

	/**
	 * Execute test.
	 *
	 * @param testName
	 *            the test name
	 * @param webDriver
	 *            the web driver
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean executeTest( String testName, WebDriver webDriver ) throws Exception
	{
	    PageManager.instance().getPageCache().clear();
	    
		if (log.isDebugEnabled())
			log.debug( "Attempting to locate test [" + testName + "]" );

		KeyWordTest test = testMap.get( testName );

		if (test == null)
			throw new IllegalArgumentException( "The test [" + testName + "] does not exist" );

		Map<String, PageData> dataMap = new HashMap<String, PageData>( 10 );
		Map<String, Page> pageMap = new HashMap<String, Page>( 10 );

		try
		{
			if (test.getDataProviders() != null)
			{
				if (log.isInfoEnabled())
					log.info( "Data Provider set as " + test.getDataProvidersAsString() );

				for (String dataProvider : test.getDataProviders())
				{
					PageData pageData = PageDataManager.instance().getPageData( dataProvider );
					
					if ( pageData == null )
						throw new IllegalArgumentException( "Invalid page data value specified.  Ensure that [" + dataProvider + "] exists in your page data definition");
					
					if (log.isInfoEnabled())
						log.info( "Adding " + dataProvider + " as " + pageData );
					dataMap.put( dataProvider, pageData );
				}
			}

			//
			// If there was a looped driver, then add that
			//
			if (test.getDataDriver() != null && !test.getDataDriver().isEmpty())
			{
				String[] testInfo = testName.split( "!" );
				if (testInfo.length != 2)
					throw new IllegalArgumentException( "Could not extract data record from " + testName );

				dataMap.put( test.getDataDriver(), PageDataManager.instance().getPageData( test.getDataDriver(), testInfo[1] ) );
			}

			//
			// Create a new context map and pass it along to all of the steps
			//
			contextMap.set( new HashMap<String, Object>( 10 ) );
			boolean returnValue = test.executeTest( webDriver, contextMap.get(), dataMap, pageMap );
			contextMap.set( null );

			return returnValue;

		}
		catch (Throwable e)
		{
			if ( PageManager.instance().getThrowable() == null )
				PageManager.instance().setThrowable( e );
			
			log.error( "Error executing Test " + testName, PageManager.instance().getThrowable() );
			return false;
		}
		finally
		{
			for (String key : dataMap.keySet())
			{
				PageDataManager.instance().putPageData( dataMap.get( key ) );
			}
		}
	}

	/**
	 * Gets the test names.
	 *
	 * @return the test names
	 */
	public String[] getTestNames()
	{
		return testList.toArray( new String[0] );
	}

	/**
	 * Gets the test names.
	 *
	 * @param useNames
	 *            the use names
	 * @return the test names
	 */
	public String[] getTestNames( String useNames )
	{
		if (useNames == null || useNames.isEmpty())
			return getTestNames();

		List<String> useList = new ArrayList<String>( 5 );

		String[] nameArray = useNames.split( "," );

		for (String name : nameArray)
		{
			if (testMap.containsKey( name ))
				useList.add( name );
		}

		return useList.toArray( new String[0] );
	}

}
