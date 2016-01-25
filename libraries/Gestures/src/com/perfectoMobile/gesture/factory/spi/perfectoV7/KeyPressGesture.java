package com.perfectoMobile.gesture.factory.spi.perfectoV7;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.gesture.AbstractKeyPressGesture;

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
		RemoteWebDriver remoteDriver = null;
		
		if ( webDriver instanceof RemoteWebDriver )
			remoteDriver = (RemoteWebDriver) webDriver;
		else if ( webDriver instanceof NativeDriverProvider )
		{
			NativeDriverProvider nativeProvider = (NativeDriverProvider) webDriver;
			if ( nativeProvider.getNativeDriver() instanceof RemoteWebDriver )
				remoteDriver = (RemoteWebDriver) nativeProvider.getNativeDriver();
			else
				throw new IllegalArgumentException( "Unsupported Driver Type " + webDriver );
		}
		
		return true;
	}

}
