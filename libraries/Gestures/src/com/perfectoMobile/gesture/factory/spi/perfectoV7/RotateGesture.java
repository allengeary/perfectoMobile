package com.perfectoMobile.gesture.factory.spi.perfectoV7;

import java.net.URL;

import org.openqa.selenium.WebDriver;

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
			try
			{
				StringBuilder urlBuilder = new StringBuilder();
				urlBuilder.append( System.getProperty( "__cloudUrl") ).append(  "/services/executions/" ).append( executionId ).append ("?operation=command&user=" ).append( System.getProperty( "__userName" ) );
				urlBuilder.append( "&password=" ).append( System.getProperty( "__password" ) );
				urlBuilder.append( "&command=device&subcommand=rotate" );
				urlBuilder.append( "&param.handsetId=" ).append( deviceName );
				urlBuilder.append( "&param.state=" ).append( getOrientation().name().toLowerCase() );
			
				getUrl( new URL( urlBuilder.toString() ) );
				return true;
			}
			catch( Exception e )
			{
				log.error( "Error execution rotate", e );
				return false;
			}
		}
		else
			return false;
	}

}
