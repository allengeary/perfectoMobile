package com.perfectoMobile.gesture.factory.spi.perfecto;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.common.PercentagePoint;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.perfectoMobile.gesture.AbstractTwoFingerGesture;

/**
 * The Class TwoFingerGesture.
 */
public class TwoFingerGesture extends AbstractTwoFingerGesture
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.AbstractGesture#_executeGesture(org.openqa.selenium.WebDriver)
	 */
	@Override
	protected boolean _executeGesture( WebDriver webDriver )
	{
		
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		if ( executionId != null && deviceName != null )
		{
			PerfectoMobile.instance().gestures().pinch( executionId, deviceName, new PercentagePoint( getStartOne().getX(), getStartOne().getY() ), new PercentagePoint( getEndOne().getX(), getEndOne().getY() ) );
			return true;
		}
		else
			return false;
	}

}
