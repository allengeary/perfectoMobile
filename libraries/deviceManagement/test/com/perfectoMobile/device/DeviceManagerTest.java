package com.perfectoMobile.device;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.listener.RunListener;

/**
 * The Class DeviceManagerTest.
 */
public class DeviceManagerTest
{

	/**
	 * Gets the device string.
	 *
	 * @return the device string
	 */
	@Test
	public void getDeviceString()
	{
		DeviceManager.instance().registerDevice( new DeviceImpl( "one", 1 ) );
		DeviceManager.instance().registerDevice( new DeviceImpl( "two", 2 ) );
		
		Assert.assertNotNull( DeviceManager.instance().getDevice( "one" ) );
		Assert.assertNotNull( DeviceManager.instance().getDevice( "two" ) );
		Assert.assertNull( DeviceManager.instance().getDevice( "three" ) );
		
	}

	/**
	 * Gets the device method.
	 *
	 * @return the device method
	 */
	@Test
	public void getDeviceMethod()
	{
		DeviceManager.instance().registerDevice( new DeviceImpl( "one", 1 ) );
		DeviceManager.instance().registerDevice( new DeviceImpl( "two", 1 ) );
		DeviceManager.instance().setRetryCount( 2 );
		
		Method currentMethod = DeviceManagerTest.class.getMethods()[0];
		
		ConnectedDevice deviceOne = DeviceManager.instance().getDevice( currentMethod, null, false );
		Assert.assertNotNull( deviceOne );
		DeviceManager.instance().addRun( deviceOne.getDevice(), currentMethod, null, false );
		
		ConnectedDevice deviceTwo = DeviceManager.instance().getDevice( currentMethod, null, false );
		Assert.assertNotNull( deviceTwo );
		DeviceManager.instance().addRun( deviceTwo.getDevice(), currentMethod, null, false );
		
		ConnectedDevice deviceThree = DeviceManager.instance().getDevice( currentMethod, null, false );
		Assert.assertNull( deviceThree );
		
		DeviceManager.instance().releaseDevice( deviceOne.getDevice() );
		deviceThree = DeviceManager.instance().getDevice( currentMethod, null, false );
		Assert.assertNull( deviceThree );
		
		currentMethod = DeviceManagerTest.class.getMethods()[1];
		deviceThree = DeviceManager.instance().getDevice( currentMethod, null, false );
		Assert.assertNotNull( deviceThree );
		
	}


	/**
	 * Instance.
	 */
	@Test
	public void instance()
	{
		Assert.assertNotNull( DeviceManager.instance() );
	}

	private static String beforeRun = null;
	private static String afterRun = null;
	
	/**
	 * Notify before after run.
	 */
	@Test
	public void notifyBeforeAfterRun()
	{
		String x;
		DeviceManager.instance().addRunListener( new RunListener()
		{
			
			public boolean beforeRun( Device currentDevice, String runKey )
			{
				beforeRun = runKey;
				return true;
			}
			
			public void afterRun( Device currentDevice, String runKey, boolean success )
			{
				afterRun = runKey;
			}
		} );
		
		DeviceManager.instance().registerDevice( new DeviceImpl( "one", 1 ) );
		DeviceManager.instance().registerDevice( new DeviceImpl( "two", 1 ) );
		
		Method currentMethod = DeviceManagerTest.class.getMethods()[0];
		
		ConnectedDevice deviceOne = DeviceManager.instance().getDevice( currentMethod, null, false );
		Assert.assertNotNull( deviceOne );
		DeviceManager.instance().addRun( deviceOne.getDevice(), currentMethod, null, false );
		
		Assert.assertNotNull(  beforeRun );
		Assert.assertEquals( beforeRun, afterRun );
	}


	
}
