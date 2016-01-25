package com.perfectoMobile.gesture.factory.spi.perfecto;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.common.PercentagePoint;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.perfectoMobile.gesture.AbstractSwipeGesture;

/**
 * The Class SwipeGesture.
 */
public class SwipeGesture extends AbstractSwipeGesture
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
			PerfectoMobile.instance().gestures().swipe( executionId, deviceName, new PercentagePoint( getSwipeStart().getX(), getSwipeStart().getY() ), new PercentagePoint( getSwipeEnd().getX(), getSwipeEnd().getY() ) );
			return true;
		}
		else
			return false;
	}

}
