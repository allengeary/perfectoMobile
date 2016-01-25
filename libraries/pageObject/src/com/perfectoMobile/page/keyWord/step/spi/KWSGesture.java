package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;

import com.perfectoMobile.gesture.Gesture.Direction;
import com.perfectoMobile.gesture.Gesture.GestureType;
import com.perfectoMobile.gesture.GestureManager;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The Class KWSGesture.
 */
public class KWSGesture extends AbstractKeyWordStep
{

	private Point createPoint( String pointValue )
	{
		Point x = null;
		
		try
		{
			String[] coors = pointValue.split( "," );
			
			if ( coors.length == 2 )
			{
				x = new Point( Integer.parseInt( coors[ 0 ].trim() ), Integer.parseInt( coors[ 1 ].trim() ) );
				return x;
			}
		}
		catch( Exception e )
		{
			log.warn( "Could not parse coordinates " +  pointValue + " due to " + e.getMessage() );
		}
		
		return null;
		
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Executing Gesture " + getName() + " using " + getParameterList() );
		long startTime = System.currentTimeMillis();
		boolean success = false;
		try
		{
			switch( GestureType.valueOf( getName() ) )
			{
				case PINCH:
					if ( getParameterList().size() == 4 )
					{
						Point[] pPoint = new Point[ 4 ];
						pPoint[ 0 ] = createPoint( (String) getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) );
						pPoint[ 1 ] = createPoint( (String) getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) );
						pPoint[ 2 ] = createPoint( (String) getParameterValue( getParameterList().get( 2 ), contextMap, dataMap ) );
						pPoint[ 3 ] = createPoint( (String) getParameterValue( getParameterList().get( 3 ), contextMap, dataMap ) );
						GestureManager.instance().createPinch( pPoint[ 0 ], pPoint[ 1 ], pPoint[ 2 ], pPoint[ 3 ] ).executeGesture( webDriver );
					}
					else
						GestureManager.instance().createPinch().executeGesture( webDriver );
									
					break;
					
				case ZOOM:
					if ( getParameterList().size() == 4 )
					{
						Point[] pPoint = new Point[ 4 ];
						pPoint[ 0 ] = createPoint( (String) getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) );
						pPoint[ 1 ] = createPoint( (String) getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) );
						pPoint[ 2 ] = createPoint( (String) getParameterValue( getParameterList().get( 2 ), contextMap, dataMap ) );
						pPoint[ 3 ] = createPoint( (String) getParameterValue( getParameterList().get( 3 ), contextMap, dataMap ) );
						GestureManager.instance().createPinch( pPoint[ 0 ], pPoint[ 1 ], pPoint[ 2 ], pPoint[ 3 ] ).executeGesture( webDriver );
					}
					else
						GestureManager.instance().createZoom().executeGesture( webDriver );
					break;
					
				case SWIPE:
					if ( getParameterList().size() > 1 )
					{
						Point[] pPoint = new Point[ 2 ];
						if ( getParameterList().size() == 2 )
						{
							pPoint[ 0 ] = createPoint( (String) getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) );
							pPoint[ 1 ] = createPoint( (String) getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) );
						}
						else
						{
							pPoint[ 0 ] = createPoint( (String) getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) );
							pPoint[ 1 ] = createPoint( (String) getParameterValue( getParameterList().get( 2 ), contextMap, dataMap ) );
						}
						
						GestureManager.instance().createSwipe( pPoint[ 0 ], pPoint[ 1 ] ).executeGesture( webDriver );
						
					}
					else
						GestureManager.instance().createSwipe( Direction.valueOf( (String) getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) ) ).executeGesture( webDriver );
					
					break;
					
				case PRESS:
					GestureManager.instance().createPress( new Point( Integer.parseInt( (String) getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) ), Integer.parseInt( (String) getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) ) ) ).executeGesture( webDriver );
					break;
					
				case ROTATE:
					GestureManager.instance().createRotate( ScreenOrientation.valueOf( (String) getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) ) ).executeGesture( webDriver );
					break;
					
				case KEYPRESS:
					GestureManager.instance().createKeyPress( Integer.parseInt( ( (String) getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) ) ) ).executeGesture( webDriver );
					break;
					
				case HIDE_KEYBOARD:
					GestureManager.instance().createHideKeyboard().executeGesture( webDriver );
					break;
			}
			
			success = true;
		}
		finally
		{

		}
		
		return success;
	}

}
