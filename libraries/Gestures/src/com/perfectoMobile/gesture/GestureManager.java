package com.perfectoMobile.gesture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;

import com.perfectoMobile.gesture.Gesture.Direction;
import com.perfectoMobile.gesture.Gesture.GestureType;
import com.perfectoMobile.gesture.factory.GestureFactory;

/**
 * The Class GestureManager.
 */
public class GestureManager
{
	private static GestureManager singleton = new GestureManager();

	/**
	 * Instance.
	 *
	 * @return the gesture manager
	 */
	public static GestureManager instance()
	{
		return singleton;
	}

	private GestureManager()
	{

	}
	
	private GestureFactory gestureFactory;
	
	private Log log = LogFactory.getLog( GestureManager.class );
	
	/**
	 * Sets the gesture factory.
	 *
	 * @param gestureFactory the new gesture factory
	 */
	public void setGestureFactory( GestureFactory gestureFactory )
	{
		this.gestureFactory = gestureFactory;
	}
	
	/**
	 * Creates the Hide Keyboard Gesture.
	 *
	 * @return the gesture
	 */
	public Gesture createHideKeyboard()
	{
		return gestureFactory.createGesture( GestureType.HIDE_KEYBOARD, new Object[] { false } ); 
	}
	
	/**
	 * Creates the swipe.
	 *
	 * @param swipeDirection the swipe direction
	 * @return the gesture
	 */
	public Gesture createSwipe( Direction swipeDirection )
	{
		switch( swipeDirection )
		{
			case DOWN:
				return createSwipe( new Point( 50, 15 ), new Point( 50, 85 ) );
				
			case LEFT:
				return createSwipe( new Point( 55, 50 ), new Point( 85, 50 ) );
				
			case RIGHT:
				return createSwipe( new Point( 85, 50 ), new Point( 15, 50 ) );
				
			case UP:
				return createSwipe( new Point( 50, 85 ), new Point( 50, 15 ) );
				
			default:
				return null;
		}
	}
	
	/**
	 * Creates the rotate.
	 *
	 * @param sOrientation the s orientation
	 * @return the gesture
	 */
	public Gesture createRotate( ScreenOrientation sOrientation )
	{
		return gestureFactory.createGesture( GestureType.ROTATE, new Object[] { sOrientation } );
	}
	
	/**
	 * Creates the swipe.
	 *
	 * @param startPosition the start position
	 * @param endPosition the end position
	 * @return the gesture
	 */
	public Gesture createSwipe( Point startPosition, Point endPosition )
	{
		return gestureFactory.createGesture( GestureType.SWIPE, new Object[] { startPosition, endPosition } );
	}
	
	/**
	 * Creates the press.
	 *
	 * @param pressPosition the press position
	 * @return the gesture
	 */
	public Gesture createPress( Point pressPosition )
	{
		return createPress( pressPosition, 100 );
	}
	
	/**
	 * Creates the press.
	 *
	 * @param pressPosition the press position
	 * @param pressLength the press length
	 * @return the gesture
	 */
	public Gesture createPress( Point pressPosition, long pressLength )
	{
		return gestureFactory.createGesture( GestureType.PRESS, new Object[] { pressPosition, pressLength } );
	}
	
	/**
	 * Creates the key press.
	 *
	 * @param keyCode the key code
	 * @return the gesture
	 */
	public Gesture createKeyPress( int keyCode )
	{
		return gestureFactory.createGesture( GestureType.KEYPRESS, new Object[] { keyCode } );
	}
	
	/**
	 * Creates the zoom.
	 *
	 * @return the gesture
	 */
	public Gesture createZoom()
	{
		return createZoom( new Point( 45, 45 ), new Point( 55, 55 ), new Point( 15, 15 ), new Point( 85, 85 ) );
	}
	
	/**
	 * Creates the zoom.
	 *
	 * @param startOne the start one
	 * @param startTwo the start two
	 * @param endOne the end one
	 * @param endTwo the end two
	 * @return the gesture
	 */
	public Gesture createZoom( Point startOne, Point startTwo, Point endOne, Point endTwo )
	{
		return gestureFactory.createGesture( GestureType.ZOOM, new Object[] { startOne, startTwo, endOne, endTwo } );
	}
	
	/**
	 * Creates the pinch.
	 *
	 * @return the gesture
	 */
	public Gesture createPinch()
	{
		return createPinch( new Point( 15, 15 ), new Point( 85, 85 ), new Point( 45, 45 ), new Point( 55, 55 ) );
	}
	
	/**
	 * Creates the pinch.
	 *
	 * @param startOne the start one
	 * @param startTwo the start two
	 * @param endOne the end one
	 * @param endTwo the end two
	 * @return the gesture
	 */
	public Gesture createPinch( Point startOne, Point startTwo, Point endOne, Point endTwo )
	{
		return gestureFactory.createGesture( GestureType.PINCH, new Object[] { startOne, startTwo, endOne, endTwo } );
	}
	
}
