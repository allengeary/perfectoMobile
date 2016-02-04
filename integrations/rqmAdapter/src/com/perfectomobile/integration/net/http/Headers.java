<<<<<<< HEAD
package com.perfectomobile.integration.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Headers
{
	private static final String CRLF = "\r\n";
	private static final String COLON = ":";
	private static final String CONTENT_LENGTH = "content-length";
	private static final String HOST = "host";
	private Map<String, Header> headerMap = new LinkedHashMap<String, Header>( 10 );
	private Log log = LogFactory.getLog( this.getClass() );

	public Headers()
	{

	}

	public HttpCookie getCookie( String cookieName )
	{
		Header cookieHeader = getHeader( Header.SET_COOKIE );
		return cookieHeader.getCookie( cookieName );
	}
	
	public Header getHeader( String headerName )
	{
		return headerMap.get( headerName.toLowerCase() );
	}
	
	public void addHeader( Header header )
	{
		Header currentHeader = headerMap.get( header.getName().toLowerCase() );
		if ( currentHeader == null )
			headerMap.put( header.getName().toLowerCase(), header );
		else
		{
			for ( String value : header.getValues() )
				currentHeader.addValue( value );
		}
	}
	
	public void addHeader( String name, String value )
	{
		Header currentHeader = headerMap.get( name );
		if ( currentHeader == null )
		{
			currentHeader = new Header( name, value );
			headerMap.put( name, currentHeader );
		}
		else
			currentHeader.addValue( value );
	}
	
	public int extractHeaders( InputStream inputStream ) throws IOException
	{
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Extracting Header Information" );
		
		String currentLine = null;
		while (( currentLine = StreamUtility.readLine( inputStream ) ) != null)
		{
			if ( currentLine.trim().isEmpty() )
				break;
			
			Header currentHeader = new Header( currentLine );
			
			Header storedHeader = headerMap.get( currentHeader.getName().toLowerCase() );
			
			if ( storedHeader == null )
				headerMap.put( currentHeader.getName().toLowerCase(), currentHeader );
			else
				storedHeader.addValue( currentHeader.getValues().get( 0 ) );
		}

		return headerMap.size();
	}

	public String toString()
	{
		StringBuilder headerData = new StringBuilder();

		boolean oneAdded = false;
		for (Header currentHeader : headerMap.values())
		{
			if (oneAdded)
				headerData.append( CRLF );
			headerData.append( currentHeader.toString() );
			oneAdded = true;
		}

		return headerData.toString();
	}

	public int getContentLength()
	{
		try
		{
			return Integer.parseInt( headerMap.get( CONTENT_LENGTH ).getValues().get( 0 ) );
		} 
		catch (Exception e)
		{
			return 0;
		}
	}

	public String getHost()
	{
		try
		{
			return headerMap.get( HOST ).getValues().get( 0 );
		} catch (Exception e)
		{
			return "";
		}
	}

	public String getHostName()
	{
		return getHost().split( COLON )[0];
	}

	public int getHostPort()
	{
		String[] hostSplit = getHost().split( COLON );
		if (hostSplit.length > 1)
		{
			try
			{
				return Integer.parseInt( hostSplit[ 1 ] );
			} 
			catch (Exception e)
			{
			}
		}
		
		return 80;
	}
=======
package com.perfectomobile.integration.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Headers
{
	private static final String CRLF = "\r\n";
	private static final String COLON = ":";
	private static final String CONTENT_LENGTH = "content-length";
	private static final String HOST = "host";
	private Map<String, Header> headerMap = new LinkedHashMap<String, Header>( 10 );
	private Log log = LogFactory.getLog( this.getClass() );

	public Headers()
	{

	}

	public HttpCookie getCookie( String cookieName )
	{
		Header cookieHeader = getHeader( Header.SET_COOKIE );
		return cookieHeader.getCookie( cookieName );
	}
	
	public Header getHeader( String headerName )
	{
		return headerMap.get( headerName.toLowerCase() );
	}
	
	public void addHeader( Header header )
	{
		Header currentHeader = headerMap.get( header.getName().toLowerCase() );
		if ( currentHeader == null )
			headerMap.put( header.getName().toLowerCase(), header );
		else
		{
			for ( String value : header.getValues() )
				currentHeader.addValue( value );
		}
	}
	
	public void addHeader( String name, String value )
	{
		Header currentHeader = headerMap.get( name );
		if ( currentHeader == null )
		{
			currentHeader = new Header( name, value );
			headerMap.put( name, currentHeader );
		}
		else
			currentHeader.addValue( value );
	}
	
	public int extractHeaders( InputStream inputStream ) throws IOException
	{
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Extracting Header Information" );
		
		String currentLine = null;
		while (( currentLine = StreamUtility.readLine( inputStream ) ) != null)
		{
			if ( currentLine.trim().isEmpty() )
				break;
			
			Header currentHeader = new Header( currentLine );
			
			Header storedHeader = headerMap.get( currentHeader.getName().toLowerCase() );
			
			if ( storedHeader == null )
				headerMap.put( currentHeader.getName().toLowerCase(), currentHeader );
			else
				storedHeader.addValue( currentHeader.getValues().get( 0 ) );
		}

		return headerMap.size();
	}

	public String toString()
	{
		StringBuilder headerData = new StringBuilder();

		boolean oneAdded = false;
		for (Header currentHeader : headerMap.values())
		{
			if (oneAdded)
				headerData.append( CRLF );
			headerData.append( currentHeader.toString() );
			oneAdded = true;
		}

		return headerData.toString();
	}

	public int getContentLength()
	{
		try
		{
			return Integer.parseInt( headerMap.get( CONTENT_LENGTH ).getValues().get( 0 ) );
		} 
		catch (Exception e)
		{
			return 0;
		}
	}

	public String getHost()
	{
		try
		{
			return headerMap.get( HOST ).getValues().get( 0 );
		} catch (Exception e)
		{
			return "";
		}
	}

	public String getHostName()
	{
		return getHost().split( COLON )[0];
	}

	public int getHostPort()
	{
		String[] hostSplit = getHost().split( COLON );
		if (hostSplit.length > 1)
		{
			try
			{
				return Integer.parseInt( hostSplit[ 1 ] );
			} 
			catch (Exception e)
			{
			}
		}
		
		return 80;
	}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
}