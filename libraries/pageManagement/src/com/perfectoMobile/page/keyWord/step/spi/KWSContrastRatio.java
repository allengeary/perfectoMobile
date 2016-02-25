package com.perfectoMobile.page.keyWord.step.spi;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * <b>Keyword(s):</b> <code>VERIFY_CONTRAST</code><br>
 * The verify contrast is used to locate an element on the screen and perform a Lumenosity analysis on it.  The Lumenosity calculate
 * is derived from the WCAG 2.0 standard.  The returned value will be between 0 and 21.  Please refer to the WCAG 2.0 specification for 
 * a definition of the values  Note that PageManager can be configured to dump these images to disk 
 * for analysis purposes.<br><br>

 * <b>Parameters:</b> <br>
 * <ul>
 * <li>Resolution: high, med or low.  The specifies the screenshot resolution that will be used.</li>
 * <li>Minimum Contrast:  The minimum amount of contrast that must be present</li>
 * <li>Maximum Contrast:  The maximum amount of contrast that must be present</li>
 * </ul>

 * <br><b>Example(s): </b><ul>
 * <li> This example will find the LOGO element and analyze the contrast ratio to ensure that it meets AAA standards<br>
 * {@literal <step name="LOGO" type="VERIFY_CONTRAST" page="TEST_PAGE"> }<br>
 * &nbsp;&nbsp;&nbsp;{@literal  <parameter type="static" value="high" /> }<br>
 * &nbsp;&nbsp;&nbsp;{@literal  <parameter type="static" value="7.0" /> }<br>
 * &nbsp;&nbsp;&nbsp;{@literal  <parameter type="static" value="21.1" /> }<br>
 * {@literal </step> }
 * </li>
 * </ul>
 */
public class KWSContrastRatio extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
	{
		if ( pageObject == null )
			throw new IllegalStateException( "Page Object was not defined" );

		if (getParameterList().size() < 2 )
			throw new IllegalArgumentException( "Verify Color must have 3 parameters" );
		
		long fileKey = System.currentTimeMillis();
		Resolution resolution = Resolution.valueOf( ( getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "" ).toLowerCase() );
		double minContrast = Double.parseDouble( getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "" );
		double maxContrast = Double.parseDouble( getParameterValue( getParameterList().get( 2 ), contextMap, dataMap ) + "" );
		
		
		BufferedImage elementValue = (BufferedImage)getElement( pageObject, contextMap, webDriver, dataMap ).getImage( resolution );
		if ( elementValue != null && PageManager.instance().isStoreImages() )
			PageManager.instance().writeImage( elementValue, fileKey + "-" + getName() + ".png" );

		
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
		if ( contrastRatio < minContrast || contrastRatio > maxContrast )
		{
			throw new IllegalArgumentException( "The contrast for [" + getName() + "] should be between [#" + Integer.toHexString( minColor ) + "] and [" + Integer.toHexString( maxColor ) + "] was [" + contrastRatio + "] and fell outside of the expected range" );
		}
		
		if ( getContext() != null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Setting Context Data to [" + contrastRatio + "] for [" + getContext() + "]" );
			contextMap.put( getContext(), contrastRatio + "" );
		}
		
		return true;
	}

	/**
	 * Calculate luminance.
	 *
	 * @param rgb the rgb
	 * @return the double
	 */
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
	
	/**
	 * Extract colors.
	 *
	 * @param colorData the color data
	 * @return the int[]
	 */
	public int[] extractColors( int colorData )
	{
		return new int[] { (int) ( (colorData & 0x00ff0000) >> 16 ), (int) ( (colorData & 0x0000ff00) >> 8 ), (int) ( (colorData & 0x000000ff) )} ;
	}

}
