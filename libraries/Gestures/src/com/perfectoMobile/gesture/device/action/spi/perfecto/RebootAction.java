package com.perfectoMobile.gesture.device.action.spi.perfecto;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.perfectoMobile.gesture.device.action.AbstractDefaultAction;
import com.perfectoMobile.gesture.device.action.DeviceAction;

/**
 * The Class RebootAction.
 */
public class RebootAction extends AbstractDefaultAction implements DeviceAction
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.device.action.AbstractDefaultAction#_executeAction(org.openqa.selenium.WebDriver, java.util.List)
	 */
	@Override
	public boolean _executeAction( WebDriver webDriver, List<Object> parameterList )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		PerfectoMobile.instance().device().reboot( executionId, deviceName );
		return true;
	}

}
