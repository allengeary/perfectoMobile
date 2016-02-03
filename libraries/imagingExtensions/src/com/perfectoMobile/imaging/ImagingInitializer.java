package com.perfectoMobile.imaging;

import java.util.Properties;

import com.morelandLabs.Initializable;
import com.perfectoMobile.imaging.opencv.OpenCVManager;

public class ImagingInitializer implements Initializable
{
	private static final String LIBRARY = ".opencv.libraryLocation";
	private static final String TEMPLATE_ALGO = ".opencv.templateMatch.algorithms";
	private static final String CACHE = ".cacheLocation";

	public void initialize( String propertyPrefix, Properties propertyMap )
	{
		String libraryLocation = propertyMap.getProperty( propertyPrefix + LIBRARY );
		String templateAlgo = propertyMap.getProperty( propertyPrefix + TEMPLATE_ALGO );
		String cacheFolder = propertyMap.getProperty( propertyPrefix + CACHE );
		
		OpenCVManager.instance().initializeCV( libraryLocation, cacheFolder, templateAlgo );
	}
}
