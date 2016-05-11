package com.perfectoMobile.gesture.device.action.spi.perfecto;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.common.Location;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.perfectoMobile.gesture.device.action.AbstractDefaultAction;
import com.perfectoMobile.gesture.device.action.DeviceAction;

// TODO: Auto-generated Javadoc
/**
 * The Class LocationAction.
 */
public class LocationAction extends AbstractDefaultAction implements DeviceAction
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.device.action.AbstractDefaultAction#_executeAction(org.openqa.selenium.WebDriver, java.util.List)
	 */
	@Override
	public boolean _executeAction( WebDriver webDriver, List<Object> parameterList )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		
		String longitude = (String) parameterList.get( 0 );
		String latitude = (String) parameterList.get( 1 );
		
		PerfectoMobile.instance().device().setLocation( executionId, deviceName, new Location( longitude, latitude ) );
		return true;
	}

}