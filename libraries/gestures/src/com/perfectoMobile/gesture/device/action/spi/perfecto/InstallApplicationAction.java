package com.perfectoMobile.gesture.device.action.spi.perfecto;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.application.ApplicationDescriptor;
import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;
import com.perfectoMobile.gesture.device.action.AbstractDefaultAction;
import com.perfectoMobile.gesture.device.action.DeviceAction;

// TODO: Auto-generated Javadoc
/**
 * The Class InstallApplicationAction.
 */
public class InstallApplicationAction extends AbstractDefaultAction implements DeviceAction
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
		Boolean instrument = Boolean.parseBoolean( (String) parameterList.get( 1 ) );
		
		ApplicationDescriptor appDesc = ApplicationRegistry.instance().getApplication( applicationName );
	
		Handset localDevice = PerfectoMobile.instance().devices().getDevice( deviceName );
		
		if ( localDevice.getOs().toLowerCase().equals( "ios" ) )				
			PerfectoMobile.instance().application().install( executionId, deviceName, appDesc.getIosInstallation(), instrument ? "instrument" : "noinstrument" );
		else if ( localDevice.getOs().toLowerCase().equals( "android" ) )
			PerfectoMobile.instance().application().install( executionId, deviceName, appDesc.getAndroidInstallation(), instrument ? "instrument" : "noinstrument" );
		else
			throw new IllegalArgumentException( "Could not install application to " + localDevice.getOs() );
		return true;
	}

}
