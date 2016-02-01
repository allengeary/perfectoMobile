package com.perfectoMobile.page.keyWord.step.spi;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The Class KWSValue.
 */
public class KWSContrastRatio extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		if ( pageObject == null )
			throw new IllegalStateException( "Page Object was not defined" );

		if (getParameterList().size() < 2 )
			throw new IllegalArgumentException( "Verify Color must have 3 parameters - Resolution, location(x, y) and color" );
		
		
		Resolution resolution = Resolution.valueOf( ( getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "" ).toLowerCase() );
		double minContrast = Double.parseDouble( getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "" );
		double maxContrast = Double.parseDouble( getParameterValue( getParameterList().get( 2 ), contextMap, dataMap ) + "" );
		
		
		BufferedImage elementValue = (BufferedImage)getElement( pageObject, contextMap, webDriver, dataMap ).getImage( resolution );
		
		//int[][] pixelArray = ImagingUtilities.extractAsArray( elementValue );
		
		int minColor = 0;
		double minLumens = 255;
		int maxColor = 0;
		double maxLumens = 0;
		double luminosity = 0;
		for ( int x=0; x<elementValue.getWidth(); x++ )
		{
			for ( int y=0; y<elementValue.getHeight(); y++ )
			{
				int[] colorData = extractColors( elementValue.getRGB( x, y ) );
				luminosity = calculateLuminance( colorData );
				if ( luminosity > maxLumens )
				{
					maxLumens = luminosity;
					maxColor = elementValue.getRGB( x, y );
				}
				else if ( luminosity < minLumens )
				{
					minLumens = luminosity;
					minColor = elementValue.getRGB( x, y );
				}
				
			}
		}
		
		double contrastRatio = (maxLumens + 0.05) / (minLumens + 0.05 );
		System.out.println( contrastRatio );
		if ( contrastRatio < minContrast || contrastRatio > maxContrast )
		{
			throw new IllegalArgumentException( "The contrast between [#" + Integer.toHexString( minColor ) + "] and [" + Integer.toHexString( maxColor ) + "] was [" + contrastRatio + "] and fell outside of the expected range" );
		}
		
		if ( getContext() != null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Setting Context Data to [" + contrastRatio + "] for [" + getContext() + "]" );
			contextMap.put( getContext(), contrastRatio + "" );
		}
		
		return true;
	}
	

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
	 */
	public boolean isRecordable()
	{
		return false;
	}
	
	private static double calculateLuminance( int[] rgb )
	{
		double[] dRGB = new double[3];
		
		for ( int i=0; i<3; i++ )
		{		
			dRGB[ i ] = rgb[ i ] / 255.0;
			
			if ( dRGB[ i ] <= 0.03928 )
				dRGB[i] = dRGB[ i ] / 12.92;
			else
				dRGB[ i ] = Math.pow( ( ( dRGB[ i ] + 0.055 ) / 1.055 ) , 2.4 );
		}
		
		return ( dRGB[ 0 ] * 0.2126 ) + (dRGB[ 1 ] * 0.7152) + ( dRGB[2] * 0.0722 );

	}
	
	public int[] extractColors( int colorData )
	{
		return new int[] { (int) ( (colorData & 0x00ff0000) >> 16 ), (int) ( (colorData & 0x0000ff00) >> 8 ), (int) ( (colorData & 0x000000ff) )} ;
	}

}
