package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWGetCookies.
 */
public class KWSGetCookies extends AbstractKeyWordStep
{

    /* (non-Javadoc)
     * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
     */
    @Override
    public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
    {
        if ( pageObject == null )
        {
            throw new IllegalStateException( "Page Object was not defined" );
        }

        Set<Cookie> cookieSet = null;
        if ( webDriver instanceof RemoteWebDriver )
            cookieSet = ((RemoteWebDriver) webDriver).manage().getCookies();
        else if ( webDriver instanceof NativeDriverProvider && ( (NativeDriverProvider) webDriver).getNativeDriver() instanceof RemoteWebDriver )
        {
            cookieSet = ( (RemoteWebDriver) ( (NativeDriverProvider) webDriver).getNativeDriver() ).manage().getCookies();
        }

        StringBuilder buffer = new StringBuilder();
        Iterator<Cookie> cookies = cookieSet.iterator();

        while( cookies.hasNext() )
        {
            Cookie cookie = cookies.next();

            buffer.append( cookie.getName() + ":" + cookie.getValue() );
            if ( cookies.hasNext() )
            {
                buffer.append( ";" );
            }
        }

        Object result = buffer.toString();

        if ( log.isDebugEnabled() )
                log.debug( "Cookie List [" + result + "]" );
		
        if (( result instanceof String ) &&
            ( !validateData( result + "" )) )
        {
            throw new IllegalStateException( "Get cookies Expected a format of [" + getValidationType() + "(" + getValidation() + ") for [" + result + "]" );
        }
		
        if ( getContext() != null )
        {
            if ( log.isDebugEnabled() )
                log.debug( "Setting Context Data to [" + result + "] for [" + getContext() + "]" );
            contextMap.put( getContext(), result );
        }
		
        return true;
    }
	
    /* (non-Javadoc)
     * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
     */
    public boolean isRecordable()
    {
        return false;
    }

}
