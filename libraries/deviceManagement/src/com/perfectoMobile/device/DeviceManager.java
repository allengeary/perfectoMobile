package com.perfectoMobile.device;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.comparator.WeightedDeviceComparator;
import com.perfectoMobile.device.factory.DeviceWebDriver;
import com.perfectoMobile.device.factory.DriverManager;
import com.perfectoMobile.device.listener.RunListener;

/**
 * The Class DeviceManager.
 */
public class DeviceManager
{
	private static DeviceManager singleton = new DeviceManager();
	
	private ThreadLocal<String> executionId = new ThreadLocal<String>();

	/**
	 * Instance.
	 *
	 * @return the device manager
	 */
	public static DeviceManager instance()
	{
		return singleton;
	}

	private DeviceManager()
	{

	}
	
	private Log log = LogFactory.getLog( DeviceManager.class );
	private Lock managerLock = new ReentrantLock();
	private Map<String, Device> deviceMap = new HashMap<String, Device>( 20 );
	private List<Device> deviceList = new LinkedList<Device>();
	private Comparator<Device> deviceComparator = new WeightedDeviceComparator();
	private int retryCount = 25;

	private Map<String, DeviceAnalytics> analyticsMap = new HashMap<String, DeviceAnalytics>( 20 );
	private Map<String,Boolean> activeRuns = new HashMap<String,Boolean>( 20 );

	private List<RunListener> runListeners = new ArrayList<RunListener>( 20 );
	private boolean cachingEnabled = false;
	private boolean dryRun = false;

	
	
	public boolean isDryRun()
	{
		return dryRun;
	}

	public void setDryRun( boolean dryRun )
	{
		this.dryRun = dryRun;
	}

	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId( String executionId )
	{
		this.executionId.set( executionId );
	}
	
	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId()
	{
		return executionId.get();
	}
	
	/**
	 * Adds the run listener.
	 *
	 * @param runListener the run listener
	 */
	public void addRunListener( RunListener runListener )
	{
		runListeners.add( runListener );
	}
	
	/**
	 * Removes the run listener.
	 *
	 * @param runListener the run listener
	 */
	public void removeRunListener( RunListener runListener )
	{
		runListeners.remove( runListener );
	}
	
	
	private boolean notifyBeforeRun( Device currentDevice, String runKey )
	{
		for ( RunListener runListener : runListeners )
		{
			try
			{
				if ( !runListener.beforeRun( currentDevice, runKey ) )
					return false;
			}
			catch( Exception e )
			{
				log.error( "Error executing run listener", e );
			}
		}
		
		return true;
	}
	
	private void notifyAfterRun( Device currentDevice, String runKey, boolean successful )
	{
		for ( RunListener runListener : runListeners )
		{
			try
			{
				runListener.afterRun( currentDevice, runKey, successful );
			}
			catch( Exception e )
			{
				log.error( "Error executing run listener", e );
			}
		}
	}
	
	/**
	 * Sets the retry count.
	 *
	 * @param retryCount the new retry count
	 */
	public void setRetryCount( int retryCount )
	{
		this.retryCount = retryCount;
	}
	
	/**
	 * Sets the device comparator.
	 *
	 * @param deviceComparator the new device comparator
	 */
	public void setDeviceComparator( Comparator<Device> deviceComparator )
	{
		this.deviceComparator = deviceComparator;
	}

	/**
	 * Gets the device comparator.
	 *
	 * @return the device comparator
	 */
	public Comparator<Device> getDeviceComparator()
	{
		return deviceComparator;
	}

	/**
	 * Gets the device.
	 *
	 * @param deviceKey the device key
	 * @return the device
	 */
	public Device getDevice( String deviceKey )
	{
		return deviceMap.get( deviceKey );

	}

	/**
	 * This will cycle through the available devices, by weight, to locate a
	 * device that has not run this method yet.
	 *
	 * @param currentMethod            The current test method
	 * @param testContext the test context
	 * @param attachDevice the attach device
	 * @return The next available device or null if no device are available
	 */
	public ConnectedDevice getDevice( Method currentMethod, String testContext, boolean attachDevice )
	{
		
		for ( int i=0; i<retryCount; i++ )
		{
			boolean deviceLocked = true;
			
			//
			// If the lock is a local lock then we will not increment the retry counter.  If it failed 
			//
			while( deviceLocked )
			{
				if (log.isDebugEnabled())
					log.debug( Thread.currentThread().getName() + ": Acquiring Device Manager Lock (Iteration #" + i + ")" );
				managerLock.lock();
		
				try
				{
					String runKey = currentMethod.getDeclaringClass().getSimpleName() + "." + currentMethod.getName() + ( testContext != null ? ( "." + testContext ) : "" );
					
					for (Device currentDevice : deviceList)
					{
						//
						// Attempt to acquire a lock for the device
						//
						if (log.isDebugEnabled())
							log.debug( Thread.currentThread().getName() + ": Attempting to acquire semaphore for " + currentDevice );
						if (currentDevice.getLock().tryAcquire())
						{
							//
							// Now, make sure this test has not run on this device yet and that there are no active runs against it
							//
							if (log.isDebugEnabled())
								log.debug( Thread.currentThread().getName() + ": Device Semaphore permitted for " + currentDevice );
							if (!analyticsMap.get( currentDevice.getKey() ).hasRun( runKey ) && !activeRuns.containsKey( currentDevice.getKey() + "." + runKey ) )
							{
								if (log.isDebugEnabled())
									log.debug( Thread.currentThread().getName() + ": Selected " + currentDevice );
		
								//
								// Notify any listeners about this device acquisition and allow them to cancel it
								//
								if ( !notifyBeforeRun( currentDevice, runKey ) )
								{
									if (log.isDebugEnabled())
										log.debug( Thread.currentThread().getName() + ": A registered RUN LISTENER cancelled this device request - Releasing Semaphore for " + currentDevice );
		
									currentDevice.getLock().release();
								}
								else
								{
									
									//
									// If we made it here then we are not locally locking the device
									//
									deviceLocked = false;
									
									if ( attachDevice && !dryRun )
									{

										
										//
										// Create the WebDriver here if we are attaching this device
										//
										DeviceWebDriver webDriver = null;
										try
										{
											if ( log.isDebugEnabled() )
												log.debug( "Attempting to create WebDriver instance for " + currentDevice );
											
											webDriver = DriverManager.instance().getDriverFactory( currentDevice.getDriverType() ).createDriver( currentDevice );
											
											if ( webDriver != null )
											{
												if ( log.isDebugEnabled() )
													log.debug( "WebDriver Created - Creating Connected Device for " + currentDevice );
												
												activeRuns.put( currentDevice.getKey() + "." + runKey , true );
												
												return new ConnectedDevice( webDriver, currentDevice );
											}
										}
										catch( Exception e )
										{
											log.error( "Error creating factory instance", e );
											try { webDriver.close(); } catch( Exception e2 ) {}
											try { webDriver.quit(); } catch( Exception e2 ) {}
										}
										
										//
										// If we are here, the driver failed
										//
										if (log.isDebugEnabled())
											log.debug( Thread.currentThread().getName() + ": Releasing unused Device Semaphore for " + currentDevice );
										currentDevice.getLock().release();
										
									}
									else
									{
										activeRuns.put( currentDevice.getKey() + "." + runKey , true );
										return new ConnectedDevice( null, currentDevice );
									}
								}
									
									
							}
							else
							{
								if (log.isDebugEnabled())
									log.debug( Thread.currentThread().getName() + ": Releasing unused Device Semaphore for " + currentDevice );
								currentDevice.getLock().release();
							}
						}
					}
				}
				finally
				{
					if (log.isDebugEnabled())
						log.debug( Thread.currentThread().getName() + ": Releasing Device Manager Lock" );
					try { managerLock.unlock(); } catch( Exception e ) {}
				}
				
				//
				// Pause and wait to reload
				//
				try
				{
					Thread.sleep( 2500 );
				}
				catch( Exception e )
				{}
			}
		}

		return null;

	}
	
	/**
	 * Release device.
	 *
	 * @param currentDevice the current device
	 */
	public void releaseDevice( Device currentDevice )
	{
		if (log.isDebugEnabled())
			log.debug( Thread.currentThread().getName() + ": Releasing Device Semaphore for " + currentDevice );
		currentDevice.getLock().release();
	}

	/**
	 * Gets the usage.
	 *
	 * @param currentDevice the current device
	 * @return the usage
	 */
	public int getUsage( Device currentDevice )
	{
		return analyticsMap.get( currentDevice.getKey() ).getUsage();
	}

	/**
	 * Adds the run.
	 *
	 * @param currentDevice the current device
	 * @param currentMethod the current method
	 * @param testContext the test context
	 * @param success the success
	 */
	public void addRun( Device currentDevice, Method currentMethod, String testContext, boolean success )
	{
		if (log.isDebugEnabled())
			log.debug( Thread.currentThread().getName() + ": Acquiring Device Manager Lock" );
		managerLock.lock();
		try
		{
			String runKey = currentMethod.getDeclaringClass().getSimpleName() + "." + currentMethod.getName() + ( testContext != null ? ( "." + testContext ) : "" );
			
			if (log.isInfoEnabled())
				log.info( Thread.currentThread().getName() + ": Adding run [" + runKey + "] to device " + currentDevice );
			analyticsMap.get( currentDevice.getKey() ).addRun( runKey );
			
			activeRuns.remove( currentDevice.getKey() + "." + runKey );
			
			notifyAfterRun( currentDevice, runKey, success );
			Collections.sort( deviceList, deviceComparator );
		}
		finally
		{
			if (log.isDebugEnabled())
				log.debug( Thread.currentThread().getName() + ": Releasing Device Manager Lock" );
			managerLock.unlock();
		}
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public List<Device> getDevices()
	{
		return Collections.unmodifiableList( deviceList );
	}
	
	
	
	/**
	 * Register device.
	 *
	 * @param currentDevice the current device
	 */
	public void registerDevice( Device currentDevice )
	{
		if (log.isInfoEnabled())
			log.info( "Registering Device " + currentDevice );
		deviceMap.put( currentDevice.getKey(), currentDevice );
		analyticsMap.put( currentDevice.getKey(), new DeviceAnalytics( currentDevice.getKey() ) );
		deviceList.add( currentDevice );
	}

	/**
	 * Checks if is caching enabled.
	 *
	 * @return true, if is caching enabled
	 */
	public boolean isCachingEnabled()
	{
		return cachingEnabled;
	}

	/**
	 * Sets the caching enabled.
	 *
	 * @param cachingEnabled the new caching enabled
	 */
	public void setCachingEnabled( boolean cachingEnabled )
	{
		this.cachingEnabled = cachingEnabled;
	}  
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main( String[] args ) throws Exception
	{
		HtmlCleaner x = new HtmlCleaner();
		
		TagNode f = x.clean( "c:/tools/report/device.xml" );
		
		System.out.println( f.getAllElements( true ).length );
		
		new PrettyXmlSerializer( x.getProperties() ).writeToFile( f, "c:/tools/report/deviceOut.xml" );
		
	}
	
}
