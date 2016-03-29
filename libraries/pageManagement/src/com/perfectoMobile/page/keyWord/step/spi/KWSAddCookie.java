package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSAddCookie.
 */
public class KWSAddCookie extends AbstractKeyWordStep
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

        Object name = null;
        Object value = null;
                
        if ( getParameterList().size() == 2 )
        {
            name = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap );
            value = getParameterValue( getParameterList().get( 1 ), contextMap, dataMap );
            if ( !( name instanceof String ) )
                throw new IllegalStateException( "Cookie name must be of type String" );
            if ( !( value instanceof String ) )
                throw new IllegalStateException( "Cookie value must be of type String" );
        }
        else
        {
            throw new IllegalStateException( "Add cookie requires two string properties (name, value)" );
        }

        Cookie cookie = new Cookie( (String)name, (String)value );
        if ( webDriver instanceof RemoteWebDriver )
            ((RemoteWebDriver) webDriver).manage().addCookie( cookie );
        else if ( webDriver instanceof NativeDriverProvider && ( (NativeDriverProvider) webDriver).getNativeDriver() instanceof RemoteWebDriver )
        {
            ( (RemoteWebDriver) ( (NativeDriverProvider) webDriver).getNativeDriver() ).manage().addCookie( cookie );
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
