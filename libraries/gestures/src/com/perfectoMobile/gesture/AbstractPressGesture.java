package com.perfectoMobile.gesture;

import org.openqa.selenium.Point;

/**
 * The Class AbstractPressGesture.
 */
public abstract class AbstractPressGesture extends AbstractGesture
{
	private Point pressPosition;
	private int pressLength;
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.Gesture#setParameters(java.lang.Object[])
	 */
	public void setParameters( Object[] parameterArray )
	{
		setPressPosition( (Point) parameterArray[ 0 ] );
		setPressLength( (int) parameterArray[ 1 ] );
	}
	
	/**
	 * Gets the press position.
	 *
	 * @return the press position
	 */
	public Point getPressPosition()
	{
		return pressPosition;
	}
	
	/**
	 * Sets the press position.
	 *
	 * @param pressPosition the new press position
	 */
	public void setPressPosition( Point pressPosition )
	{
		this.pressPosition = pressPosition;
	}
	
	/**
	 * Gets the press length.
	 *
	 * @return the press length
	 */
	public int getPressLength()
	{
		return pressLength;
	}
	
	/**
	 * Sets the press length.
	 *
	 * @param pressLength the new press length
	 */
	public void setPressLength( int pressLength )
	{
		this.pressLength = pressLength;
	}
	
	
}
