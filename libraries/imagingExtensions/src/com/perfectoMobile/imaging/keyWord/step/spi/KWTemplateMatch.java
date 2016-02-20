package com.perfectoMobile.imaging.keyWord.step.spi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution;
import com.perfectoMobile.imaging.opencv.OpenCVManager;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWTemplateMatch.
 */
public class KWTemplateMatch extends AbstractKeyWordStep
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com
	 * .perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map,
	 * java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
	{
		if (pageObject == null || getElement( pageObject, contextMap, webDriver, dataMap ) == null)
			throw new IllegalStateException( "The Element " + getName() + " is not defined.  Ensure it exists on your Page object" );

		Resolution resolution = Resolution.valueOf( ( getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "" ).toLowerCase() );

		String templateFile = getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "";
		double minimumThreshold = Double.parseDouble( getParameterValue( getParameterList().get( 2 ), contextMap, dataMap ) + "" );
		BufferedImage templateImage = null;
		BufferedImage elementValue = ( BufferedImage ) getElement( pageObject, contextMap, webDriver, dataMap ).getImage( resolution );

		

		double matchNumber = 0;
		try
		{
			File.createTempFile( getName(), ".png", new File( OpenCVManager.instance().getCacheFolder() ) );
			if (templateFile.startsWith( "cp://" ))
			{
				templateFile = templateFile.substring( 4 );
				templateImage = ImageIO.read( getClass().getClassLoader().getSystemResourceAsStream( templateFile ) );
			}
			else
				templateImage = ImageIO.read( new File( templateFile ) );

			if (log.isInfoEnabled())
			{
				log.info( "Element Image " + elementValue );
				log.info( "Template Image " + templateImage );
			}

			matchNumber = OpenCVManager.instance().matchImages( templateImage, elementValue );
		}
		catch (IOException e)
		{
			throw new IllegalStateException( "Could not compare images", e );
		}

		if (log.isInfoEnabled())
			log.info( "Match value was " + matchNumber + " and minimim threshold is " + minimumThreshold );

		return matchNumber > minimumThreshold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
	 */
	public boolean isRecordable()
	{
		return false;
	}

}
