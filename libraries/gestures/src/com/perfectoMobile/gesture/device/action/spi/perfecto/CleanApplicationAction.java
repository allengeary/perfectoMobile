package com.perfectoMobile.gesture.device.action.spi.perfecto;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.morelandLabs.application.ApplicationDescriptor;
import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;
import com.morelandLabs.utility.BrowserCacheLogic;
import com.perfectoMobile.gesture.device.action.AbstractDefaultAction;
import com.perfectoMobile.gesture.device.action.DeviceAction;

/**
 * The Class CleanApplicationAction.
 */
public class CleanApplicationAction extends AbstractDefaultAction implements DeviceAction
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.device.action.AbstractDefaultAction#_executeAction(org.openqa.selenium.WebDriver, java.util.List)
	 */
	@Override
	public boolean _executeAction( WebDriver webDriver, List<Object> parameterList )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		
		String applicationName = (String) parameterList.get( 0 );

		ApplicationDescriptor appDesc = ApplicationRegistry.instance().getApplication( applicationName );
	
		Handset localDevice = PerfectoMobile.instance().devices().getDevice( deviceName );
		
		if ( appDesc.getUrl() != null && !appDesc.getUrl().isEmpty() )
		{
		    //
		    // This is a Web URL so clear all cookies
		    //
                    
		    webDriver.manage().deleteAllCookies();

                    //
                    // clear the browser cache (IOS only)
                    //

                    if (( localDevice.getOs().toLowerCase().equals( "ios" ) ) &&
                        ( webDriver instanceof RemoteWebDriver ))
                    {
                        try
                        {
                            BrowserCacheLogic.clearSafariIOSCache( (RemoteWebDriver) webDriver );
                        }
                        catch( Throwable e )
                        {
                            e.printStackTrace();
                        }
                    }
		}
		else
		{
    		if ( localDevice.getOs().toLowerCase().equals( "android" ) )
    			PerfectoMobile.instance().application().clean( executionId, deviceName, appDesc.getName(), appDesc.getAndroidIdentifier() );
    		else
    			log.warn( "Could not clean application on " + localDevice.getOs() );
		}
		return true;
	}

}
