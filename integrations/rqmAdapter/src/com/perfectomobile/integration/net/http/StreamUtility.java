package com.perfectomobile.integration.net.http;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StreamUtility
{
	private static final char CR = 10;
	private static final char LF = 13;
	private static final char NULL = 0;
	
	private static Log log = LogFactory.getLog( StreamUtility.class );
	
	public static String readLine( InputStream in )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Extracting line from " + in );
		
		StringBuffer data = new StringBuffer( "" );
		int c;

		try
		{
			in.mark( 1 );
			if (in.read() == -1)
				return null;
			else
				in.reset();

			while (( c = in.read() ) >= 0)
			{
				if (( c == NULL ) || ( c == CR ) || ( c == LF ))
					break;
				else
					data.append( ( char ) c );
			}

			if (c == LF)
			{
				in.mark( 1 );
				if (in.read() != CR)
					in.reset();
			}
		} 
		catch (Exception e)
		{
			log.error( "Error reading line data", e );
		}

		
		if ( log.isDebugEnabled() )
			log.debug( "Line Extracted as " + data.toString() );
		 
		return data.toString();
	}
}
