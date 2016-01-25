package com.perfectoMobile.gesture.factory.spi.perfecto;

import java.io.InputStream;
import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.common.PercentagePoint;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Device.ScreenOrientation;
import com.perfectoMobile.gesture.AbstractRotateGesture;

/**
 * The Class RotateGesture.
 */
public class RotateGesture extends AbstractRotateGesture
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.AbstractGesture#_executeGesture(org.openqa.selenium.WebDriver)
	 */
	@Override
	protected boolean _executeGesture( WebDriver webDriver )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		if ( executionId != null )
		{
			PerfectoMobile.instance().device().rotate( executionId, deviceName, ScreenOrientation.valueOf( getOrientation().name().toLowerCase() ) );
			return true;
		}
		else
			return false;
	}

}
