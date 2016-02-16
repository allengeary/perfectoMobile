package com.perfectoMobile.gesture;

import org.openqa.selenium.ScreenOrientation;

/**
 * The Class AbstractRotateGesture.
 */
public abstract class AbstractRotateGesture extends AbstractGesture
{
	private ScreenOrientation sOrientation;
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.Gesture#setParameters(java.lang.Object[])
	 */
	public void setParameters( Object[] parameterArray )
	{
		setOrientation( (ScreenOrientation) parameterArray[ 0 ] );
	}

	/**
	 * Gets the orientation.
	 *
	 * @return the orientation
	 */
	public ScreenOrientation getOrientation()
	{
		return sOrientation;
	}

	/**
	 * Sets the orientation.
	 *
	 * @param sOrientation the new orientation
	 */
	public void setOrientation( ScreenOrientation sOrientation )
	{
		this.sOrientation = sOrientation;
	}

}
