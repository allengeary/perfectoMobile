<<<<<<< HEAD
package com.perfectomobile.integration.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpRequest
{
	private static final String CRLF = "\r\n";
	private RequestDefinition requestDefinition = new RequestDefinition();
	private Headers requestHeaders = new Headers();
	private byte[] requestBody;
	private Log log = LogFactory.getLog( this.getClass() );
	
	public HttpRequest()
	{
		
	}
	
	public HttpRequest( String requestType, String url )
	{
		requestDefinition.setMethod( requestType );
		requestDefinition.setUrl( url );
	}
	
	public boolean extractRequest( InputStream httpRequest ) throws IOException
	{
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Extracting Request Information" );
		requestDefinition = new RequestDefinition( StreamUtility.readLine( httpRequest ) );
		requestHeaders.extractHeaders( httpRequest );
		int contentLength = requestHeaders.getContentLength();
		
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Content length extracted as " + contentLength );
		
		if ( contentLength > 0 )
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] requestBuffer = new byte[ 4096 ];
			int bytesRead = 0;
			
			while ( ( contentLength == 0 || outputStream.size() < contentLength )  && ( bytesRead = httpRequest.read( requestBuffer ) ) > 0  )
				outputStream.write( requestBuffer, 0, bytesRead );
			
			requestBody = outputStream.toByteArray();
			
			if ( log.isDebugEnabled() )
				log.debug( Thread.currentThread().getName() + "-Extracted " + requestBody.length + " bytes of request data" );
		}
		
		return true;
	}

	public RequestDefinition getRequestDefinition()
	{
		return requestDefinition;
	}

	public void setRequestDefinition( RequestDefinition requestDefinition )
	{
		this.requestDefinition = requestDefinition;
	}

	public Headers getRequestHeaders()
	{
		return requestHeaders;
	}

	public void setRequestHeaders( Headers requestHeaders )
	{
		this.requestHeaders = requestHeaders;
	}

	public byte[] getRequestBody()
	{
		return requestBody;
	}

	public void setRequestBody( byte[] requestBody )
	{
		this.requestBody = requestBody;
		if ( requestBody != null && requestBody.length > 0 )
			requestHeaders.addHeader( "Content-Length", requestBody.length + "" );
			
	}
	
	public int getContentLength()
	{
		return requestHeaders.getContentLength();
	}
	
	public String getHost()
	{
		return requestHeaders.getHost();
	}
	
	public String getHostName()
	{
		return requestHeaders.getHostName();
	}
	
	public int getHostPort()
	{
		return requestHeaders.getHostPort();
	}
	
	public int writeRequest( OutputStream outputStream ) throws IOException
	{
		String headerString = toString();
		outputStream.write( headerString.getBytes() );
		if ( requestBody != null )
			outputStream.write( requestBody );
		outputStream.flush();
		
		return headerString.length() + (requestBody == null ? 0 : requestBody.length );
	}
	
	public String toString()
	{
		StringBuilder httpRequest = new StringBuilder();
		httpRequest.append( requestDefinition.toString() ).append( CRLF );
		httpRequest.append( requestHeaders.toString() ).append( CRLF ).append( CRLF );
		
		if ( requestBody != null )
			httpRequest.append(new String( requestBody ) );
		return httpRequest.toString();
	}
	
}
=======
package com.perfectomobile.integration.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpRequest
{
	private static final String CRLF = "\r\n";
	private RequestDefinition requestDefinition = new RequestDefinition();
	private Headers requestHeaders = new Headers();
	private byte[] requestBody;
	private Log log = LogFactory.getLog( this.getClass() );
	
	public HttpRequest()
	{
		
	}
	
	public HttpRequest( String requestType, String url )
	{
		requestDefinition.setMethod( requestType );
		requestDefinition.setUrl( url );
	}
	
	public boolean extractRequest( InputStream httpRequest ) throws IOException
	{
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Extracting Request Information" );
		requestDefinition = new RequestDefinition( StreamUtility.readLine( httpRequest ) );
		requestHeaders.extractHeaders( httpRequest );
		int contentLength = requestHeaders.getContentLength();
		
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Content length extracted as " + contentLength );
		
		if ( contentLength > 0 )
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] requestBuffer = new byte[ 4096 ];
			int bytesRead = 0;
			
			while ( ( contentLength == 0 || outputStream.size() < contentLength )  && ( bytesRead = httpRequest.read( requestBuffer ) ) > 0  )
				outputStream.write( requestBuffer, 0, bytesRead );
			
			requestBody = outputStream.toByteArray();
			
			if ( log.isDebugEnabled() )
				log.debug( Thread.currentThread().getName() + "-Extracted " + requestBody.length + " bytes of request data" );
		}
		
		return true;
	}

	public RequestDefinition getRequestDefinition()
	{
		return requestDefinition;
	}

	public void setRequestDefinition( RequestDefinition requestDefinition )
	{
		this.requestDefinition = requestDefinition;
	}

	public Headers getRequestHeaders()
	{
		return requestHeaders;
	}

	public void setRequestHeaders( Headers requestHeaders )
	{
		this.requestHeaders = requestHeaders;
	}

	public byte[] getRequestBody()
	{
		return requestBody;
	}

	public void setRequestBody( byte[] requestBody )
	{
		this.requestBody = requestBody;
		if ( requestBody != null && requestBody.length > 0 )
			requestHeaders.addHeader( "Content-Length", requestBody.length + "" );
			
	}
	
	public int getContentLength()
	{
		return requestHeaders.getContentLength();
	}
	
	public String getHost()
	{
		return requestHeaders.getHost();
	}
	
	public String getHostName()
	{
		return requestHeaders.getHostName();
	}
	
	public int getHostPort()
	{
		return requestHeaders.getHostPort();
	}
	
	public int writeRequest( OutputStream outputStream ) throws IOException
	{
		String headerString = toString();
		outputStream.write( headerString.getBytes() );
		if ( requestBody != null )
			outputStream.write( requestBody );
		outputStream.flush();
		
		return headerString.length() + (requestBody == null ? 0 : requestBody.length );
	}
	
	public String toString()
	{
		StringBuilder httpRequest = new StringBuilder();
		httpRequest.append( requestDefinition.toString() ).append( CRLF );
		httpRequest.append( requestHeaders.toString() ).append( CRLF ).append( CRLF );
		
		if ( requestBody != null )
			httpRequest.append(new String( requestBody ) );
		return httpRequest.toString();
	}
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
