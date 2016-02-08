package com.perfectoMobile.page.keyWord.step.spi;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The Class KWSValue.
 */
public class KWSCheckColor extends AbstractKeyWordStep
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
		
		long fileKey = System.currentTimeMillis();
		Resolution resolution = Resolution.valueOf( ( getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "" ).toLowerCase() );
		Point location = createPoint( getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "" );
		int percentDeviation = 0;
		String colorCode = null;
		
		if ( getParameterList().size() >= 3 )
		{
			if ( log.isInfoEnabled() )
				log.info( "Extracted information for color comparison" );
			colorCode = getParameterValue( getParameterList().get( 2 ), contextMap, dataMap ) + "";
			percentDeviation = Integer.parseInt( getParameterValue( getParameterList().get( 3 ), contextMap, dataMap ) + "" );
		}
		
		BufferedImage elementValue = (BufferedImage)getElement( pageObject, contextMap, webDriver, dataMap ).getImage( resolution );
		if ( elementValue != null && PageManager.instance().isStoreImages() )
			PageManager.instance().writeImage( elementValue, fileKey + "-" + getName() + ".png" );
		
		
		int elementColor = elementValue.getRGB( location.getX(), location.getY() );
		
		if ( getContext() != null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Setting Context Data to [" + elementColor + "] for [" + getContext() + "]" );
			contextMap.put( getContext(), elementColor + "" );
		}
		
		if ( colorCode != null )
		{
			int[] expectedColors = extractColors( colorCode );
			int[] extractColors = new int[] { ( (elementColor & 0x00ff0000) >> 16 ), ( (elementColor & 0x0000ff00) >> 8 ), ( (elementColor & 0x000000ff) )} ;
			
			int redChange = compareColor( expectedColors[0], extractColors[0] );
			int greenChange = compareColor( expectedColors[1], extractColors[1] );
			int blueChange = compareColor( expectedColors[2], extractColors[2] );
			
			if ( ( ( redChange + greenChange + blueChange ) / 3 ) > percentDeviation )
			{
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append( "The COLOR value for [" + getName() + "] was off by " + ( ( redChange + greenChange + blueChange ) / 3 ) + "% - Here is the break down - " );
				if ( redChange > 0 )
					stringBuilder.append( "The RED channel was off by " + redChange + "% " );
				if ( greenChange > 0 )
					stringBuilder.append( "The GREEN channel was off by " + greenChange + "% " );
				if ( blueChange > 0 )
					stringBuilder.append( "The BLUE channel was off by " + blueChange + "% " );
				
				throw new IllegalArgumentException( stringBuilder.toString() );
			}
		}
		
		return true;
	}
	
	private static int compareColor( int colorOne, int colorTwo )
	{
		double difference = 0;
		
		colorOne++;
		colorTwo++;
		
		if ( colorOne > colorTwo )
			difference = (double) ((double)colorTwo / (double)colorOne);
		else
			difference = (double) ((double)colorOne / (double)colorTwo);
		
		if ( difference == 0 )
			return 0;
		
		difference = 1 - difference;
		
		return (int) (100*difference);
		
	}

	
	public int[] extractColors( String colorValue )
	{
		int colorData = 0;
		
		if ( colorValue.startsWith( "#" ) )
			colorData = Integer.parseInt( colorValue.substring( 1 ).trim(), 16 );
		else if ( colorValue.indexOf( "," ) > 0 )
		{
			String[] colors = colorValue.split( "," );
			if ( colors.length == 3 )
				return new int[] { Integer.parseInt( colors[ 0 ] ), Integer.parseInt( colors[ 1 ] ), Integer.parseInt( colors[ 2 ] ) };
		}
		else
			colorData = Integer.parseInt( colorValue );
		
		return new int[] { (int) ( (colorData & 0x00ff0000) >> 16 ), (int) ( (colorData & 0x0000ff00) >> 8 ), (int) ( (colorData & 0x000000ff) )} ;

	}

}
