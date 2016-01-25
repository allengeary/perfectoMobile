package com.perfectoMobile.gesture.factory.spi;

import com.perfectoMobile.gesture.Gesture;
import com.perfectoMobile.gesture.Gesture.GestureType;
import com.perfectoMobile.gesture.factory.AbstractGestureFactory;
import com.perfectoMobile.gesture.factory.GestureFactory;
import com.perfectoMobile.gesture.factory.spi.perfecto.KeyPressGesture;
import com.perfectoMobile.gesture.factory.spi.perfecto.PressGesture;
import com.perfectoMobile.gesture.factory.spi.perfecto.RotateGesture;
import com.perfectoMobile.gesture.factory.spi.perfecto.SwipeGesture;
import com.perfectoMobile.gesture.factory.spi.perfecto.TwoFingerGesture;

/**
 * A factory for creating SeleniumGesture objects.
 */
public class PerfectoGestureFactory extends AbstractGestureFactory implements GestureFactory
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.factory.GestureFactory#createGesture(com.perfectoMobile.gesture.Gesture.GestureType, java.lang.Object[])
	 */
	public Gesture createGesture( GestureType gestureType, Object[] parameterList )
	{
		Gesture returnGesture = null;
		switch( gestureType )
		{
			case PRESS:
				returnGesture = new PressGesture();
				returnGesture.setParameters( parameterList );
				break;
				
			case SWIPE:
				returnGesture = new SwipeGesture();
				returnGesture.setParameters( parameterList );
				break;
				
			case PINCH:
			case ZOOM:
				returnGesture = new TwoFingerGesture();
				returnGesture.setParameters( parameterList );
				break;
				
			case ROTATE:
				returnGesture = new RotateGesture();
				returnGesture.setParameters( parameterList );
				break;
				
			case KEYPRESS:
				returnGesture = new KeyPressGesture();
				returnGesture.setParameters( parameterList );
				break;
		}
		
		return returnGesture;
	}
}
