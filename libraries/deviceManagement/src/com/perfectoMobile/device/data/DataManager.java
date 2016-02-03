package com.perfectoMobile.device.data;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectoMobile.device.artifact.ArtifactProducer;
import com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType;

/**
 * The Class DataManager.
 */
public class DataManager
{
	private static DataManager singleton = new DataManager();

	/**
	 * Instance.
	 *
	 * @return the data manager
	 */
	public static DataManager instance()
	{
		return singleton;
	}

	private DataManager()
	{

	}
	
	private Log log = LogFactory.getLog( DataManager.class );
	private String[] testNames;
	private ArtifactProducer artifactProducer;
	private File reportFolder;
	private ArtifactType[] automaticDownloads;

	/**
	 * Sets the tests.
	 *
	 * @param testNames the new tests
	 */
	public void setTests( String[] testNames )
	{
		this.testNames = testNames;
	}
	
	/**
	 * Gets the tests.
	 *
	 * @return the tests
	 */
	public String[] getTests()
	{
		return testNames;
	}
	
	/**
	 * Read data.
	 *
	 * @param dataProvider the data provider
	 */
	public void readData( DataProvider dataProvider )
	{
		if ( log.isInfoEnabled() )
			log.info( "Reading Device Data from " + dataProvider );
		
		dataProvider.readData();
	}
	
	/**
	 * Sets the artifact producer.
	 *
	 * @param artifactProducer the new artifact producer
	 */
	public void setArtifactProducer( ArtifactProducer artifactProducer )
	{
		this.artifactProducer = artifactProducer;
	}

	/**
	 * Gets the artifact producer.
	 *
	 * @return the artifact producer
	 */
	public ArtifactProducer getArtifactProducer()
	{
		return artifactProducer;
	}

	/**
	 * Gets the report folder.
	 *
	 * @return the report folder
	 */
	public File getReportFolder()
	{
		return reportFolder;
	}

	/**
	 * Sets the report folder.
	 *
	 * @param reportFolder the new report folder
	 */
	public void setReportFolder( File reportFolder )
	{
		this.reportFolder = reportFolder;
	}

	/**
	 * Gets the automatic downloads.
	 *
	 * @return the automatic downloads
	 */
	public ArtifactType[] getAutomaticDownloads()
	{
		return automaticDownloads;
	}

	/**
	 * Sets the automatic downloads.
	 *
	 * @param automaticDownloads the new automatic downloads
	 */
	public void setAutomaticDownloads( ArtifactType[] automaticDownloads )
	{
		this.automaticDownloads = automaticDownloads;
	}
	
	

	
}
