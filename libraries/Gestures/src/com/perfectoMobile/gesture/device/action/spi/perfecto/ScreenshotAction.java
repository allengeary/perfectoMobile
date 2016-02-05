package com.perfectoMobile.gesture.device.action.spi.perfecto;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.ImageFormat;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Screen;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Repositories.RepositoryType;
import com.perfectoMobile.gesture.device.action.AbstractDefaultAction;
import com.perfectoMobile.gesture.device.action.DeviceAction;

/**
 * The Class InstallApplicationAction.
 */
public class ScreenshotAction extends AbstractDefaultAction implements DeviceAction
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.device.action.AbstractDefaultAction#_executeAction(org.openqa.selenium.WebDriver, java.util.List)
	 */
	@Override
	public boolean _executeAction( WebDriver webDriver, List<Object> parameterList )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		
		Resolution resolution = Resolution.valueOf( ( (String) parameterList.get( 0 )  ).toLowerCase() );
		String rootFolder = System.getProperty( "__outputFolder" );
		
		if ( parameterList.size() > 1 )
			rootFolder = (String) parameterList.get( 1 ); 
		
		File outputFile = new File( new File( rootFolder, executionId ), System.currentTimeMillis() + ".png" );
		String fileKey = "PRIVATE:" + deviceName + ".png";
		
		PerfectoMobile.instance().imaging().screenShot( executionId, deviceName, fileKey, Screen.primary, ImageFormat.png, resolution );
		byte[] imageData = PerfectoMobile.instance().repositories().download( RepositoryType.MEDIA, fileKey );
		
		try
		{
			FileOutputStream outputStream = new FileOutputStream( outputFile );
			outputStream.write( imageData );
			outputStream.flush();
			outputStream.close();
			return true;
		}
		catch( Exception e )
		{
			throw new IllegalArgumentException( "Could not write to file", e );
		}
	}

}
