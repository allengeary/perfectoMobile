package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.device.factory.DeviceWebDriver;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSDeleteCookie.
 */
public class KWSDeleteCookie extends AbstractKeyWordStep
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
                
        if ( getParameterList().size() == 1 )
        {
            name = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap );
            if ( !( name instanceof String ) )
                throw new IllegalStateException( "Cookie name must be of type String" );
        }
        else
        {
            throw new IllegalStateException( "Delete cookie requires one string properties (name)" );
        }

        if ( !( webDriver instanceof DeviceWebDriver ))
        {
            throw new IllegalStateException( "Web driver (" + webDriver.getClass().getName() + ") isn't an DeviceWebDriver" );
        }

        ((DeviceWebDriver) webDriver).deleteCookieNamed( (String) name );
        	
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
