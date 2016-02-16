package com.perfectoMobile.imaging;

import java.util.Properties;

import com.morelandLabs.Initializable;
import com.perfectoMobile.imaging.opencv.OpenCVManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ImagingInitializer.
 */
public class ImagingInitializer implements Initializable
{
	
	/** The Constant LIBRARY. */
	private static final String LIBRARY = ".opencv.libraryLocation";
	
	/** The Constant TEMPLATE_ALGO. */
	private static final String TEMPLATE_ALGO = ".opencv.templateMatch.algorithms";
	
	/** The Constant CACHE. */
	private static final String CACHE = ".cacheLocation";

	/* (non-Javadoc)
	 * @see com.morelandLabs.Initializable#initialize(java.lang.String, java.util.Properties)
	 */
	public void initialize( String propertyPrefix, Properties propertyMap )
	{
		String libraryLocation = propertyMap.getProperty( propertyPrefix + LIBRARY );
		String templateAlgo = propertyMap.getProperty( propertyPrefix + TEMPLATE_ALGO );
		String cacheFolder = propertyMap.getProperty( propertyPrefix + CACHE );
		
		OpenCVManager.instance().initializeCV( libraryLocation, cacheFolder, templateAlgo );
	}
}
