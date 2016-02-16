package com.perfectoMobile.gesture.factory.spi.perfecto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.gesture.AbstractKeyPressGesture;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyPressGesture.
 */
public class KeyPressGesture extends AbstractKeyPressGesture
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.AbstractGesture#_executeGesture(org.openqa.selenium.WebDriver)
	 */
	@Override
	protected boolean _executeGesture( WebDriver webDriver )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		PerfectoMobile.instance().gestures().sendKey(executionId, deviceName, getKeyCode(), getMetaState() );
		return true;
	}

}
