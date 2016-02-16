/*
 * 
 */
package com.perfectoMobile.device.ng;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.artifact.Artifact;
import com.perfectoMobile.device.artifact.ArtifactProducer;
import com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactTime;
import com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType;
import com.perfectoMobile.device.data.DataManager;
import com.perfectoMobile.device.factory.DeviceWebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSeleniumTest.
 */
public abstract class AbstractSeleniumTest
{
	
	/** The log. */
	protected Log log = LogFactory.getLog( AbstractSeleniumTest.class );

	/** The thread device. */
	private ThreadLocal<ConnectedDevice> threadDevice = new ThreadLocal<ConnectedDevice>();

	/**
	 * The Class TestName.
	 */
	protected class TestName
	{
		
		/** The test name. */
		private String testName;
		
		/** The full name. */
		private String fullName;
		
		/** The persona name. */
		private String personaName;

		/**
		 * Gets the full name.
		 *
		 * @return the full name
		 */
		public String getFullName()
		{
			return fullName;
		}

		/**
		 * Sets the full name.
		 *
		 * @param fullName the new full name
		 */
		public void setFullName( String fullName )
		{
			this.fullName = fullName;
		}
		
		

		/**
		 * Gets the persona name.
		 *
		 * @return the persona name
		 */
		public String getPersonaName() 
		{
			return personaName;
		}

		/**
		 * Sets the persona name.
		 *
		 * @param personaName the new persona name
		 */
		public void setPersonaName(String personaName) 
		{
			this.personaName = personaName;
		}

		/**
		 * Instantiates a new test name.
		 */
		public TestName()
		{
		}

		/**
		 * Instantiates a new test name.
		 *
		 * @param testName the test name
		 */
		public TestName( String testName )
		{
			this.testName = testName;

		}

		/**
		 * Gets the test name.
		 *
		 * @return the test name
		 */
		public String getTestName()
		{
			return testName;
		}

		/**
		 * Gets the device.
		 *
		 * @return the device
		 */
		public ConnectedDevice getDevice()
		{
			return threadDevice.get();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			if ( fullName != null )
				return fullName;
			
			if (threadDevice.get() != null)
			{
				if (testName != null)
					return testName + "-->" + threadDevice.get().toString();
				else
					return threadDevice.get().toString();

			}
			if (testName != null)
				return testName;
			else
				return "Unknown Test";
		}
	}

	/**
	 * Gets the device data.
	 *
	 * @return the device data
	 */
	@DataProvider ( name = "deviceManager", parallel = true)
	public Object[][] getDeviceData()
	{
		List<Device> deviceList = DeviceManager.instance().getDevices();

		int testCount = 0;
		boolean hasPersonas = false;

		if (DataManager.instance().getTests() != null && DataManager.instance().getTests().length > 0)
			testCount = deviceList.size() * DataManager.instance().getTests().length;
		else
			testCount = deviceList.size();

		if ( DataManager.instance().getPersonas() != null && DataManager.instance().getPersonas().length > 0 )
		{
			hasPersonas = true;
			testCount = testCount * DataManager.instance().getPersonas().length;
		}
		
		Object[][] newArray = new Object[testCount][1];

		int currentPosition = 0;

		while (currentPosition < testCount)
		{
			if (DataManager.instance().getTests() != null && DataManager.instance().getTests().length > 0)
			{
				for (int i = 0; i < DataManager.instance().getTests().length; i++)
				{
					if ( hasPersonas )
					{
						for ( int j=0; j<DataManager.instance().getPersonas().length; j++ )
						{
							TestName testName = new TestName( DataManager.instance().getTests()[i] );
							testName.setPersonaName( DataManager.instance().getPersonas()[ j ] );
							newArray[currentPosition++][0] = testName;
						}
					}
					else
						newArray[currentPosition++][0] = new TestName( DataManager.instance().getTests()[i] );
				}
			}
			else
			{
				if ( hasPersonas )
				{
					for ( int j=0; j<DataManager.instance().getPersonas().length; j++ )
					{
						TestName testName = new TestName();
						testName.setPersonaName( DataManager.instance().getPersonas()[ j ] );
						newArray[currentPosition++][0] = testName;
					}
				}
				else
					newArray[currentPosition++][0] = new TestName();
			}
		}

		return newArray;
	}

	/**
	 * Adds the capabilities.
	 *
	 * @param dc the dc
	 */
	public void addCapabilities( DesiredCapabilities dc )
	{

	}

	/**
	 * Gets the web driver.
	 *
	 * @return the web driver
	 */
	protected WebDriver getWebDriver()
	{
		if (threadDevice.get() != null)
			return threadDevice.get().getWebDriver();
		else
			return null;
	}

	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	protected Device getDevice()
	{
		if (threadDevice.get() != null)
			return threadDevice.get().getDevice();
		else
			return null;
	}

	/**
	 * Before method.
	 *
	 * @param currentMethod the current method
	 * @param testArgs the test args
	 */
	@BeforeMethod
	public void beforeMethod( Method currentMethod, Object[] testArgs )
	{
		if (log.isInfoEnabled())
			log.info( "Attempting to acquire device for " + currentMethod.getName() );

		ConnectedDevice connectedDevice = DeviceManager.instance().getDevice( currentMethod, ( ( TestName ) testArgs[0] ).getTestName(), true, ( ( TestName ) testArgs[0] ).getPersonaName() );

		if (connectedDevice != null)
		{
			threadDevice.set( connectedDevice );
			( ( TestName ) testArgs[0] ).setFullName( testArgs[0].toString() );
		}
	}

	/**
	 * After method.
	 *
	 * @param currentMethod the current method
	 * @param testArgs the test args
	 * @param testResult the test result
	 */
	@AfterMethod
	public void afterMethod( Method currentMethod, Object[] testArgs, ITestResult testResult )
	{
		WebDriver webDriver = getWebDriver();
		if (webDriver != null)
		{

			//
			// If this test failed, run through the automatic downloads for a
			// failed test
			//
			if (!testResult.isSuccess())
			{
				if ( webDriver instanceof TakesScreenshot )
				{
					OutputStream os = null;
					String deviceName = ( (DeviceWebDriver) getWebDriver() ).getDeviceName();
					String executionId = ( (DeviceWebDriver) getWebDriver() ).getExecutionId();

					try
					{
						byte[] screenShot = ( ( TakesScreenshot ) webDriver ).getScreenshotAs( OutputType.BYTES );
						
						File outputFile = new File( DataManager.instance().getReportFolder(), executionId + "-" + deviceName + "-screenshot.png" );
						os = new BufferedOutputStream( new FileOutputStream( outputFile ) );
						os.write( screenShot );
						os.flush();
						os.close();
					}
					catch( Exception e )
					{
						log.error( "Error taking screenshot", e );
						try { os.close(); } catch( Exception e2 ) {}
					}
				}

				if (DataManager.instance().getAutomaticDownloads() != null)
				{
					if (webDriver instanceof ArtifactProducer)
					{
						if (DataManager.instance().getReportFolder() == null)
							DataManager.instance().setReportFolder( new File( "." ) );

						for (ArtifactType aType : DataManager.instance().getAutomaticDownloads())
						{
							if (aType.getTime() == ArtifactTime.ON_FAILURE)
							{
								try
								{
									Artifact currentArtifact = ( ( ArtifactProducer ) webDriver ).getArtifact( webDriver, aType, threadDevice.get() );
									if ( currentArtifact != null )
										currentArtifact.writeToDisk( DataManager.instance().getReportFolder() );
								}
								catch (Exception e)
								{
									log.error( "Error acquiring Artifacts", e );
								}
							}
						}
					}
				}
			}

			try
			{
				webDriver.close();
			}
			catch (Exception e)
			{
			}

			if (DataManager.instance().getAutomaticDownloads() != null)
			{
				if (webDriver instanceof ArtifactProducer)
				{
					if (DataManager.instance().getReportFolder() == null)
						DataManager.instance().setReportFolder( new File( "." ) );

					for (ArtifactType aType : DataManager.instance().getAutomaticDownloads())
					{
						if (aType.getTime() == ArtifactTime.AFTER_TEST)
						{
							try
							{
								Artifact currentArtifact = ( ( ArtifactProducer ) webDriver ).getArtifact( webDriver, aType, threadDevice.get() );
								if ( currentArtifact != null )
									currentArtifact.writeToDisk( DataManager.instance().getReportFolder() );
							}
							catch (Exception e)
							{
								log.error( "Error acquiring Artifacts - " + e.getMessage() );
							}
						}
					}
				}
			}

			try
			{
				webDriver.quit();
			}
			catch (Exception e)
			{
			}
		}

		Device currentDevice = getDevice();
		if (currentDevice != null)
		{
			DeviceManager.instance().addRun( currentDevice, currentMethod, ( ( TestName ) testArgs[0] ).getTestName(), testResult.isSuccess(), threadDevice.get().getPersona() );
			DeviceManager.instance().releaseDevice( currentDevice );
		}
	}
}
