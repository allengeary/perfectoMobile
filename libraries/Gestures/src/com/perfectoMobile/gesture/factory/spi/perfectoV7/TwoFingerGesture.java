package com.perfectoMobile.gesture.factory.spi.perfectoV7;

import java.net.URL;
import java.net.URLEncoder;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.common.PercentagePoint;
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
			try
			{
				StringBuilder urlBuilder = new StringBuilder();
				urlBuilder.append( System.getProperty( "__cloudUrl") ).append(  "/services/executions/" ).append( executionId ).append ("?operation=command&user=" ).append( System.getProperty( "__userName" ) );
				urlBuilder.append( "&password=" ).append( System.getProperty( "__password" ) );
				urlBuilder.append( "&command=touch&subcommand=swipe" );
				urlBuilder.append( "&param.handsetId=" ).append( deviceName );
				urlBuilder.append( "&param.start=" ).append( URLEncoder.encode( new PercentagePoint( getStartOne().getX(), getStartOne().getY() ).toString(), "UTF-8" ) );
				urlBuilder.append( "&param.end=" ).append( URLEncoder.encode( new PercentagePoint( getEndOne().getX(), getEndOne().getY() ).toString(), "UTF-8" ) );
			
				getUrl( new URL( urlBuilder.toString() ) );
			}
			catch( Exception e ) 
			{
				log.error( "Error executing call", e );
				return false;
			}
			return true;
		}
		else
			return false;
	}

}
