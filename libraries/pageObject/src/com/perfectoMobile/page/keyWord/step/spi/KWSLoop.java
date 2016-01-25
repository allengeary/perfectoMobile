package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.apache.xerces.dom.PSVIDOMImplementationImpl;
import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.data.PageDataManager;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.keyWord.KeyWordDriver;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The Class KWSLoop.
 */
public class KWSLoop extends AbstractKeyWordStep
{
	private static final String DATA_START = "data{";
	private static final String DATA_STOP = "}";
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap ) throws Exception
	{
		if ( pageObject == null )
			throw new IllegalStateException( "Page Object was not defined" );
		if ( getParameterList().size() < 2 )
			throw new IllegalArgumentException( "You must provide one parameter specifying either the loop count or the name of the element to execute on along with a function name to execution" );
		
		String useValue = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "";
		String functionName = getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "";
		
		boolean numericalLoop = false;
		int loopCount = -1;
		
		try
		{
			loopCount = Integer.parseInt( useValue );
			numericalLoop = true;
		}
		catch( Exception e )
		{
			
		}
		
		
		if ( numericalLoop )
		{
			if ( log.isInfoEnabled() )
				log.info( "Looping " + loopCount + " times" );
			
			for ( int i=0; i<loopCount; i++ )
			{
				if ( log.isDebugEnabled() )
					log.debug( "Execution Function " + functionName + " - Iteration " + i + " of " + loopCount );
				try
				{
				
					if ( !KeyWordDriver.instance().executionFunction( functionName, webDriver, dataMap ) )
					{
						return false;
					}
				}
				catch( KWSLoopBreak e )
				{
					return true;
				}
			}
			
			return true;
		}
		else
		{
			if ( useValue.startsWith( DATA_START ) )
			{
				//
				// We are using a data table for the loop
				//
				String tableName = useValue.substring( DATA_START.length(), useValue.length() - 1 );
				PageData[] dataTable = PageDataManager.instance().getRecords( tableName );
				
				for ( PageData pageData : dataTable )
				{
					try
					{
						dataMap.put( tableName, pageData );
						
						if ( log.isDebugEnabled() )
							log.debug( "Execution Function " + functionName + " - with data " + pageData );
						
						if ( !KeyWordDriver.instance().executionFunction( functionName, webDriver, dataMap ) )
						{
							return false;
						}
					}
					catch( KWSLoopBreak lb )
					{
						return true;
					}
				}
				
				return true;
			}
			
			if ( log.isInfoEnabled() )
				log.info( "Attempting to locate an array of elements using " + useValue );
			
			Element elementList = null;
			if ( Element.CONTEXT_ELEMENT.equals( useValue ) )
				elementList = (Element) contextMap.get( Element.CONTEXT_ELEMENT );
			else
				elementList = pageObject.getElement( getPageName(), useValue );
			
			if ( elementList != null )
			{
				Element[] elementArray = elementList.getAll();
				
				if ( log.isInfoEnabled() )
					log.info( "Looping " + elementArray.length + " times over the elements found" );
				
				for ( int i=0; i<elementArray.length; i++ )
				{
					if ( log.isDebugEnabled() )
						log.debug( "Execution Function " + functionName + " - Iteration " + i + " of " + elementArray.length );
					
					contextMap.put( Element.CONTEXT_ELEMENT, elementArray[ i ] );
					contextMap.put( Element.CONTEXT_INDEX, i );
					
					try
					{
						if ( !KeyWordDriver.instance().executionFunction( functionName, webDriver, dataMap ) )
						{
							return false;
						}
					}
					catch( KWSLoopBreak lb )
					{
						return true;
					}
				}
			}
		}
		
		return true;
	}
	
}
