package com.perfectoMobile.device.artifact;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.device.ConnectedDevice;

/**
 * The Interface ArtifactProducer.
 */
public interface ArtifactProducer
{
	
	/**
	 * The Enum ArtifactType.
	 */
	public enum ArtifactType
	{
		
		/** The execution report. */
		EXECUTION_REPORT ( ArtifactTime.AFTER_TEST ),
		
		/** The execution report pdf. */
		EXECUTION_REPORT_PDF ( ArtifactTime.AFTER_TEST ),
		
		/** The execution report html. */
		EXECUTION_REPORT_HTML ( ArtifactTime.AFTER_TEST ),
		
		/** The execution report csv. */
		EXECUTION_REPORT_CSV ( ArtifactTime.AFTER_TEST ),
		
		/** The execution report xml. */
		EXECUTION_REPORT_XML ( ArtifactTime.AFTER_TEST ),
		
		/** The execution video. */
		EXECUTION_VIDEO( ArtifactTime.AFTER_TEST ),
		
		/** The failure source. */
		FAILURE_SOURCE( ArtifactTime.ON_FAILURE ),
		
		/** The device log. */
		DEVICE_LOG( ArtifactTime.ON_FAILURE );
		
		private ArtifactTime time;
		
		/**
		 * Instantiates a new artifact type.
		 *
		 * @param time the time
		 */
		ArtifactType( ArtifactTime time )
		{
			this.time = time;
		}
		
		/**
		 * Gets the time.
		 *
		 * @return the time
		 */
		public ArtifactTime getTime()
		{
			return time;
		}
	}
	
	/**
	 * The Enum ArtifactTime.
	 */
	public enum ArtifactTime
	{
		
		/** The on failure. */
		ON_FAILURE,
		
		/** The after test. */
		AFTER_TEST
	}
	
	/**
	 * Gets the artifact.
	 *
	 * @param webDriver the web driver
	 * @param aType the a type
	 * @param connectedDevice the connected device
	 * @return the artifact
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice );
	
	/**
	 * Gets the artifact.
	 *
	 * @param webDriver the web driver
	 * @param aType the a type
	 * @param parameterMap the parameter map
	 * @param connectedDevice the connected device
	 * @return the artifact
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, Map<String,String> parameterMap, ConnectedDevice connectedDevice );
}
