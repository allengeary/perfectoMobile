package com.perfectoMobile.device.artifact;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import com.perfectoMobile.device.ConnectedDevice;

/**
 * The Class AbstractArtifactProducer.
 */
public abstract class AbstractArtifactProducer implements ArtifactProducer
{
	
	/** The log. */
	protected Log log = LogFactory.getLog( ArtifactProducer.class );
	
	/**
	 * _get artifact.
	 *
	 * @param webDriver the web driver
	 * @param aType the a type
	 * @param connectedDevice the connected device
	 * @return the artifact
	 */
	protected abstract Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice );
	
	/**
	 * _get artifact.
	 *
	 * @param webDriver the web driver
	 * @param aType the a type
	 * @param parameterMap the parameter map
	 * @param connectedDevice the connected device
	 * @return the artifact
	 */
	protected abstract Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, Map<String,String> parameterMap, ConnectedDevice connectedDevice );
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.ArtifactProducer#getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, com.perfectoMobile.device.ConnectedDevice)
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Acquiring an Artifact of type " + aType );
		return _getArtifact( webDriver, aType, connectedDevice );
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.ArtifactProducer#getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, java.util.Map, com.perfectoMobile.device.ConnectedDevice)
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, Map<String, String> parameterMap, ConnectedDevice connectedDevice )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Acquiring an Artifact of type " + aType + " using " + parameterMap );
		return _getArtifact( webDriver, aType, parameterMap, connectedDevice );
	}

}
