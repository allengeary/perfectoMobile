
package com.perfectomobile.integration.net.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpResponse
{
	private static final String CRLF = "\r\n";
	private ResponseDefinition responseDefinition = new ResponseDefinition();
	private Headers responseHeaders = new Headers();
	private byte[] responseBody;
	private Log log = LogFactory.getLog( this.getClass() );

	public HttpResponse()
	{

	}

	public boolean extractResponse( InputStream httpResponse ) throws IOException
	{
		if (log.isDebugEnabled())
			log.debug( Thread.currentThread().getName() + "-Extracting Response Information" );

		responseDefinition = new ResponseDefinition( StreamUtility.readLine( httpResponse ) );
		responseHeaders.extractHeaders( httpResponse );

		int contentLength = responseHeaders.getContentLength();

		if (log.isDebugEnabled())
			log.debug( Thread.currentThread().getName() + "-Content length extracted as " + contentLength );

		boolean chunkedTransfer = ( responseHeaders.getHeader( "Transfer-Encoding" ) != null && "chunked".equals( responseHeaders.getHeader( "Transfer-Encoding" ).getValues().get( 0 ) ) );
		
		if (contentLength > 0 || chunkedTransfer )
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] requestBuffer = new byte[4096];
			int bytesRead = 0;

			
			if ( chunkedTransfer )
			{
				if (log.isDebugEnabled())
					log.debug( Thread.currentThread().getName() + "-Decoding Chunked Transfer" );
				
				ChunkedInputStream chunkedStream = new ChunkedInputStream( httpResponse );
				
				while ( ( bytesRead = chunkedStream.read( requestBuffer ) ) > 0 )
					outputStream.write( requestBuffer, 0, bytesRead );
				
			}
			else
			{
				while (( contentLength == 0 || outputStream.size() < contentLength ) && ( bytesRead = httpResponse.read( requestBuffer ) ) > 0)
					outputStream.write( requestBuffer, 0, bytesRead );
				
				responseBody = outputStream.toByteArray();
			}

			responseBody = outputStream.toByteArray();
			
			if (log.isDebugEnabled())
				log.debug( Thread.currentThread().getName() + "-Extracted " + responseBody.length + " bytes of response data" );
		}

		return true;
	}

	public ResponseDefinition getresponseDefinition()
	{
		return responseDefinition;
	}

	public void setresponseDefinition( ResponseDefinition responseDefinition )
	{
		this.responseDefinition = responseDefinition;
	}

	public Headers getresponseHeaders()
	{
		return responseHeaders;
	}

	public void setresponseHeaders( Headers responseHeaders )
	{
		this.responseHeaders = responseHeaders;
	}

	public byte[] getresponseBody()
	{
		return responseBody;
	}

	public void setresponseBody( byte[] responseBody )
	{
		this.responseBody = responseBody;
	}

	public int writeResponse( OutputStream outputStream ) throws IOException
	{
		String headerString = toString();
		outputStream.write( headerString.getBytes() );
		if (responseBody != null)
			outputStream.write( responseBody );
		outputStream.flush();

		return headerString.length() + ( responseBody == null ? 0 : responseBody.length );
	}

	public String toString()
	{
		StringBuilder httpresponse = new StringBuilder();
		httpresponse.append( responseDefinition.toString() ).append( CRLF );
		httpresponse.append( responseHeaders.toString() ).append( CRLF ).append( CRLF );
		if ( responseBody != null )
		httpresponse.append(  new String( responseBody  ) );
		return httpresponse.toString();
	}

}