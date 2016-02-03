package com.perfectoMobile.imaging.opencv;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import com.perfectoMobile.imaging.keyWord.step.spi.KWTemplateMatch;
import com.perfectoMobile.page.keyWord.step.KeyWordStepFactory;

public class OpenCVManager
{
	private static OpenCVManager singleton = new OpenCVManager();

	public static OpenCVManager instance()
	{
		return singleton;
	}

	private OpenCVManager()
	{

	}

	private String libraryLocation;
	private String cacheFolder;
	private int[] matchAlgorithms;

	private Log log = LogFactory.getLog( OpenCVManager.class );

	public void initializeCV( String libraryLocation, String cacheFolder, String templateMatchAlgorithms )
	{
		if (log.isInfoEnabled())
			log.info( "Loading OpenCV Library from " + libraryLocation );
		System.load( libraryLocation );

		this.libraryLocation = libraryLocation;
		this.cacheFolder = cacheFolder;
		String[] matchString = templateMatchAlgorithms.split( "," );
		this.matchAlgorithms = new int[ matchString.length ];
		for ( int i=0; i<matchString.length; i++ )
			matchAlgorithms[ i ] = Integer.parseInt( matchString[ i ].trim() );
		
		KeyWordStepFactory.instance().addKeyWord( "CV_TEMPLATE", KWTemplateMatch.class );

	}
	
	public String getLibraryLocation()
	{
		return libraryLocation;
	}

	public String getCacheFolder()
	{
		return cacheFolder;
	}

	public int[] getMatchAlgorithms()
	{
		return matchAlgorithms;
	}

	public double matchImages( InputStream originalImage, InputStream templateImage ) throws IOException
	{
		return matchImages( ImageIO.read( originalImage ), ImageIO.read(  templateImage ) );

	}

	public double matchImages( BufferedImage originalImage, BufferedImage templateImage ) throws IOException
	{
		Mat oImage = new Mat( originalImage.getWidth(), originalImage.getHeight(), CvType.CV_8UC3 );
		oImage.put( 0, 0, ( ( DataBufferByte ) originalImage.getRaster().getDataBuffer() ).getData() );

		Mat tImage = new Mat( templateImage.getWidth(), templateImage.getHeight(), CvType.CV_8UC3 );
		tImage.put( 0, 0, ( ( DataBufferByte ) templateImage.getRaster().getDataBuffer() ).getData() );

		double[] matchScores = new double[ matchAlgorithms.length ];
		double lowMatch = Double.MAX_VALUE;
		for ( int i=0; i<matchScores.length; i++ )
		{
			double currentValue = matchImages( oImage, tImage, matchAlgorithms[ i ] );
			if ( currentValue < lowMatch )
				lowMatch = currentValue;
		}
		
		
		return lowMatch;
	}
	
	public static void main( String[] args ) throws Exception
	{
		OpenCVManager.instance().initializeCV( "c:/tools/opencv_java248.dll", "", "1, 3, 5" );
		System.out.println( OpenCVManager.instance().matchImages( new FileInputStream( "c:/tools/screen.png" ), new FileInputStream( "c:/tools/logo.png" ) ) );	
	}

	private double matchImages( Mat originalImage, Mat imageTemplate, int matchMethod )
	{

		int resultCols = originalImage.cols() - imageTemplate.cols() + 1;
		int resultRows = originalImage.rows() - imageTemplate.rows() + 1;
		Mat result = new Mat( resultRows, resultCols, CvType.CV_32FC1 );

		Imgproc.matchTemplate( originalImage, imageTemplate, result, matchMethod );

		MinMaxLocResult mmr = Core.minMaxLoc( result );

		Point matchLoc;
		double matchScore;
		if (matchMethod == Imgproc.TM_SQDIFF || matchMethod == Imgproc.TM_SQDIFF_NORMED)
		{
			matchLoc = mmr.minLoc;
			matchScore = 1.0 - mmr.minVal;
		}
		else
		{
			matchLoc = mmr.maxLoc;
			matchScore = mmr.maxVal;
		}
		
		if ( log.isDebugEnabled() )
			log.debug( "Match Score: " + matchScore + " found at " + matchLoc + " using method " + matchMethod );

		return matchScore;
	}

}
