package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSWaitFor.
 */
public class KWSWaitFor extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		if ( pageObject == null || getElement( pageObject, contextMap, webDriver, dataMap ) == null )
			throw new IllegalStateException( "The Element " + getName() + " is not defined.  Ensure it exists on your Page object" );
		
		int waitFor = 15;
		
		if ( getParameterList().size() > 0 )
		{
			try
			{
				waitFor = Integer.parseInt( getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "" );
			}
			catch( Exception e ) {}
		}
		boolean returnValue = false;
		try
		{
			returnValue = getElement( pageObject, contextMap, webDriver, dataMap ).waitForPresent( waitFor, TimeUnit.SECONDS );
		}
		finally
		{
			
		}
		
		return returnValue;
		
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
	 */
	public boolean isRecordable()
	{
		return false;
	}

}
