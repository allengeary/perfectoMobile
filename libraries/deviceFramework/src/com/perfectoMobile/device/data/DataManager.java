/*
 * 
 */
package com.perfectoMobile.device.data;

import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.morelandLabs.artifact.ArtifactType;
import com.perfectoMobile.device.artifact.ArtifactProducer;

// TODO: Auto-generated Javadoc
/**
 * The Class DataManager.
 */
public class DataManager
{
	
	/** The singleton. */
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

	/**
	 * Instantiates a new data manager.
	 */
	private DataManager()
	{

	}
	
	/** The log. */
	private Log log = LogFactory.getLog( DataManager.class );
	
	/** The test names. */
	private String[] testNames;
	
	/** The persona names. */
	private String[] personaNames;
	
	/** The artifact producer. */
	private ArtifactProducer artifactProducer;
	
	/** The report folder. */
	private File reportFolder;
	
	/** The automatic downloads. */
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
	 * Gets the personas.
	 *
	 * @return the personas
	 */
	public String[] getPersonas() 
	{
		return personaNames;
	}

	/**
	 * Sets the personas.
	 *
	 * @param personaNames the new personas
	 */
	public void setPersonas(String[] personaNames) 
	{
		this.personaNames = personaNames;
	}
	
	/**
	 * Sets the personas.
	 *
	 * @param personaNames the new personas
	 */
	public void setPersonas(String personaNames) 
	{
		this.personaNames = personaNames.split( "," );
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
		System.setProperty( "__outputFolder", reportFolder.getAbsolutePath() );
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
