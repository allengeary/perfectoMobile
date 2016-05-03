
package com.perfectomobile.integration.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.net.http.RequestDefinition;
import com.perfectomobile.integration.rqm.RQMAdapter;
import com.perfectomobile.integration.rqm.bean.Attachment;

public abstract class AbstractAdapterAction implements AdapterAction
{
	protected static final String GET = "GET";
	protected static final String POST = "POST";
	protected static final String PUT = "PUT";
	protected static final String JSESSIONID = "JSESSIONID";
	
	protected RQMAdapter rqmAdapter;
	
	protected abstract Object _performAction( Map<String, Object> actionParameters ) throws Exception;
	protected static Log log = LogFactory.getLog( AdapterAction.class ); 
	public final Object performAction( Map<String, Object> actionParameters )
	{
		try
		{
			return _performAction( actionParameters );
		}
		catch( Exception e )
		{
			log.error( "Error execution action", e );
			throw new IllegalStateException( e );
		}
	}
	
	public void setRQMAdapter( RQMAdapter rqmAdapter )
	{
		this.rqmAdapter = rqmAdapter;
	}

	
	public static byte[] getUrl( URL currentUrl )
	{
		if ( log.isInfoEnabled() )
			log.info( "Executing " + currentUrl.toString() );
		InputStream inputStream = null;
		try
		{
			ByteArrayOutputStream resultBuilder = new ByteArrayOutputStream();
			inputStream = currentUrl.openStream();
			byte[] buffer = new byte[ 1024 ];
			int bytesRead = 0;
			
			while ( ( bytesRead = inputStream.read( buffer ) ) > 0 )
				resultBuilder.write( buffer, 0, bytesRead );
			
			return resultBuilder.toByteArray();
		}
		catch( Exception e )
		{
			log.error( "Error performing GET request", e );
			return null;
		}
		finally
		{
			try { inputStream.close(); } catch( Exception e ) {}
		}
		
	}
	
	protected HttpResponse doGet( String url ) throws MalformedURLException, IOException
	{
		return doGet( url, null );
	}
	
	protected HttpResponse doGet( String url, HttpRequest httpRequest ) throws MalformedURLException, IOException
	{
		Socket serverSocket = null;
		
		if ( log.isInfoEnabled() )
			log.info( "HTML Get to " + url );
		
		try
		{
			URL currentUrl = new URL( url );
			
			serverSocket = getSocket( currentUrl.getHost(), currentUrl.getPort(), true );
			
			if ( httpRequest == null )
			{
				httpRequest = new HttpRequest();
				httpRequest.getRequestDefinition().setUrl( currentUrl.getPath() + ( currentUrl.getQuery() == null ? "" : ("?" + currentUrl.getQuery()) ) );
			}
			else
				httpRequest.getRequestDefinition().setUrl( currentUrl.getPath() + ( currentUrl.getQuery() == null ? "" : ("?" + currentUrl.getQuery()) ) );
			
			httpRequest.getRequestHeaders().addHeader( "Host", currentUrl.getHost() + ":" + getPort( currentUrl.getPort(), true ) );
			httpRequest.getRequestHeaders().addHeader( "User-Agent", "Perfecto Mobile RQM Adapter" );

			if ( log.isDebugEnabled() )
				log.debug( "Request: " + httpRequest.toString() );
			
			OutputStream outputStream = serverSocket.getOutputStream();
			httpRequest.writeRequest( outputStream );
			
			
			InputStream inputStream = serverSocket.getInputStream();
			
			HttpResponse httpResponse = new HttpResponse();
			httpResponse.extractResponse( new BufferedInputStream( inputStream ) );
			if ( log.isDebugEnabled() )
				log.debug( "Response: " + httpResponse.toString() );
			
			httpResponse = followRedirect( httpResponse );

			return httpResponse;
		}
		finally
		{
			try { serverSocket.close(); } catch( Exception e ) {}
		}
		
	}
	
	protected HttpResponse uploadAttachment( String url, HttpRequest httpRequest, Attachment attachment, String contentType, String projectId ) throws Exception
	{
		String boundary = "--" + UUID.randomUUID().toString();
		
		httpRequest.getRequestHeaders().addHeader( "Content-Type", "multipart/form-data; boundary=" + boundary );
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();	
		
		
		outputStream.write( ( "--" + boundary + "\r\nContent-Disposition: form-data; name=\"uploadFileInput\";filename=\"" + attachment.getFileName().getName() + "\"\r\nContent-Type: " + contentType + "\r\n\r\n").getBytes() );
		outputStream.write( readFile( attachment.getFileName() ) );
		outputStream.write( "\r\n".getBytes() );
		addUploadField( "projectId", projectId, boundary, outputStream );
		addUploadField( "mimetype", "text/html", boundary, outputStream );
		addUploadField( "fileFields", "uploadFileInput", boundary, outputStream );

		outputStream.write( ("--" + boundary + "--").getBytes() );
		httpRequest.setRequestBody( outputStream.toByteArray() );
		return doPost( url, httpRequest );
	}
	
	private void addUploadField( String name, String value, String boundary, ByteArrayOutputStream outputStream ) throws Exception
	{
		outputStream.write( ( "--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + name + "\"\r\n\r\n" ).getBytes() );
		outputStream.write( value.getBytes() );
		outputStream.write( "\r\n".getBytes() );
	}
	
	protected byte[] readFile( File file ) throws Exception
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[ 1024 ];
		
		BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream( file ) );
		int bytesRead = 0;
		
		while ( ( bytesRead = inputStream.read( buffer ) ) != -1 )
			outputStream.write( buffer, 0, bytesRead );
		
		inputStream.close();
		return outputStream.toByteArray();
	}
	
	
	private HttpResponse doPostPut( String url, HttpRequest httpRequest, String requestMethod ) throws MalformedURLException, IOException
	{
		URL currentUrl = new URL( url );
		
		if ( log.isInfoEnabled() )
			log.info( "HTML " + requestMethod + " of [" + httpRequest.getRequestBody().length + " bytes] to " + url );
		
		Socket serverSocket = null;
		
		try
		{
			
			if ( httpRequest.getRequestDefinition() == null )
			{
				RequestDefinition requestDefinition = new RequestDefinition();
				requestDefinition.setMethod( requestMethod );
				httpRequest.setRequestDefinition( requestDefinition );
				httpRequest.getRequestDefinition().setUrl( currentUrl.getPath() + ( currentUrl.getQuery() == null ? "" : ("?" + currentUrl.getQuery()) ) );
			}
			else
			{
				httpRequest.getRequestDefinition().setMethod( requestMethod );
				httpRequest.getRequestDefinition().setUrl( currentUrl.getPath() + ( currentUrl.getQuery() == null ? "" : ("?" + currentUrl.getQuery()) ) );
			}
			
			httpRequest.getRequestHeaders().addHeader( "Host", currentUrl.getHost() + ":" + getPort( currentUrl.getPort(), true ) );
			httpRequest.getRequestHeaders().addHeader( "User-Agent", "Perfecto Mobile RQM Adapter" );
			
			
			serverSocket = getSocket( currentUrl.getHost(), currentUrl.getPort(), true );
			OutputStream outputStream = serverSocket.getOutputStream();
			
			if ( log.isDebugEnabled() )
				log.debug( "Request: " + httpRequest.toString() );
			httpRequest.writeRequest( outputStream );
			
			
			InputStream inputStream = serverSocket.getInputStream();
			HttpResponse httpResponse = new HttpResponse();
			httpResponse.extractResponse( new BufferedInputStream( inputStream ) );

			if ( log.isDebugEnabled() )
				log.debug( "Response: " + httpResponse.toString() );
			return httpResponse;
		}
		finally
		{
			try { serverSocket.close(); } catch( Exception e ) {}
		}
	}
	
	protected HttpResponse doPost( String url, HttpRequest httpRequest ) throws MalformedURLException, IOException
	{
		return doPostPut( url, httpRequest, "POST" );
	}
	
	protected HttpResponse doPut( String url, HttpRequest httpRequest ) throws MalformedURLException, IOException
	{
		return doPostPut( url, httpRequest, "PUT" );
	}
	
	private int getPort( int port, boolean useSsl )
	{
		if ( port <= 0 )
			return useSsl ? 443 : 80;
		else
			return port;
	}
	
	private Socket getSocket( String hostName, int port, boolean useSsl ) throws IOException
	{
		
		int usePort = getPort( port, useSsl );
		
		if ( log.isDebugEnabled() )
			log.debug( "Opening Socket to " + hostName +":" + usePort );
		
		if ( useSsl )
		{
			try
			{
				if ( log.isDebugEnabled() )
					log.debug( "Opening SSL Socket to " + hostName + ":" + usePort );
				
				TrustManager[] trustAllCerts = new TrustManager[] { 
					    new X509TrustManager() {     
					        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
					            return new X509Certificate[0];
					        } 
					        public void checkClientTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					            } 
					        public void checkServerTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					        }
					    } 
					};
				
				SSLContext sc = SSLContext.getInstance( "TLS" ); 
			    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
				SSLSocketFactory factory = sc.getSocketFactory();
				SSLSocket sslSocket = (SSLSocket) factory.createSocket(hostName, usePort);
				sslSocket.setUseClientMode( true );
				return sslSocket;
			}
			catch( Exception e )
			{
				log.error( "Could not create SSL Connection", e );
			}
			
			return null;
		}
		else
		{
			if ( log.isDebugEnabled() )
				log.debug( "Opening Socket to " + hostName + ":" + usePort );
			return new Socket( hostName, usePort );
		}
	}
	
	protected HttpResponse followRedirect( HttpResponse currentResponse ) throws IOException
	{
		if ( log.isDebugEnabled() )
			log.debug( "Analyzing Response for Redirects " + currentResponse.getresponseDefinition().getCode() );
		
		while( currentResponse.getresponseDefinition().getCode() == 302 )
		{
			if ( currentResponse.getresponseHeaders().getHeader( "Location" ) != null )
			{
				Header locationHeader = currentResponse.getresponseHeaders().getHeader( "Location" );
				
				if ( locationHeader.getValues() != null && !locationHeader.getValues().isEmpty() )
				{
					if ( log.isDebugEnabled() )
						log.debug( "Redirecting Request to " + locationHeader.getValues().get( 0 ) );
					return doGet( locationHeader.getValues().get( 0 ) );
				}
			}
		}
		
		return currentResponse;
	}
}
