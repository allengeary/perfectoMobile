package com.perfectoMobile.gesture.device.action.spi.perfecto;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.gesture.device.action.AbstractDefaultAction;
import com.perfectoMobile.gesture.device.action.DeviceAction;

import io.appium.java_client.AppiumDriver;

/**
 * The Class RecoverAction.
 */
public class RecoverAction extends AbstractDefaultAction implements DeviceAction
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.device.action.AbstractDefaultAction#_executeAction(org.openqa.selenium.WebDriver, java.util.List)
	 */
	@Override
	public boolean _executeAction( WebDriver webDriver, List<Object> parameterList )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		PerfectoMobile.instance().device().recover( executionId, deviceName );
		return true;
	}

}
