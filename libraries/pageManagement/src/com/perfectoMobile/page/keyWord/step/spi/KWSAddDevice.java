package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSAddDevice.
 */
public class KWSAddDevice extends AbstractKeyWordStep
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
                throw new IllegalStateException( "Device name must be of type String" );
        }
        else
        {
            throw new IllegalStateException( "add device requires one string properties (name)" );
        }

        if ( PageManager.instance().getAlternateWebDriverSource() != null )
        {
            PageManager.instance().getAlternateWebDriverSource().registerAltWebDriver( (String) name );
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
