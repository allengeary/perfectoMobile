package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSDeleteCookies.
 */
public class KWSDeleteCookies extends AbstractKeyWordStep
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

        if ( webDriver instanceof RemoteWebDriver )
            ((RemoteWebDriver) webDriver).manage().deleteAllCookies();
        else if ( webDriver instanceof NativeDriverProvider && ( (NativeDriverProvider) webDriver).getNativeDriver() instanceof RemoteWebDriver )
        {
            ( (RemoteWebDriver) ( (NativeDriverProvider) webDriver).getNativeDriver() ).manage().deleteAllCookies();
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
