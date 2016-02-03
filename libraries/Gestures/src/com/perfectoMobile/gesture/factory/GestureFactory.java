package com.perfectoMobile.gesture.factory;

import java.util.List;

import com.perfectoMobile.gesture.Gesture;
import com.perfectoMobile.gesture.Gesture.GestureType;

/**
 * A factory for creating Gesture objects.
 */
public interface GestureFactory
{
	
	/**
	 * Creates a new Gesture object.
	 *
	 * @param gestureType the gesture type
	 * @param parameterList the parameter list
	 * @return the gesture
	 */
	public Gesture createGesture( GestureType gestureType, Object[] parameterList );
}
