package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.morelandLabs.spi.driver.CachingDriver;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;



// TODO: Auto-generated Javadoc
/**
 * The Class KWSCache.
 */
public class KWSCache extends AbstractKeyWordStep
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com
	 * .perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map,
	 * java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Executing Device Action " + getName() + " using " + getParameterList() );
		
		if ( webDriver instanceof CachingDriver )
		{
		    if ( log.isInfoEnabled() )
		    {
    		    if ( ( (CachingDriver) webDriver ).isCachingEnabled() )
        		    log.info( Thread.currentThread().getName() + ": Disabling Caching" );
    		    else
    		        log.info( Thread.currentThread().getName() + ": Enabling Caching" );
		    }
		    
		    ((CachingDriver) webDriver).setCachingEnabled( !( (CachingDriver) webDriver ).isCachingEnabled() );
		    
		}
		
		return true;
	}

}
