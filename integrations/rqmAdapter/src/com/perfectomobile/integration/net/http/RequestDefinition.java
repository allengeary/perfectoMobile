package com.perfectomobile.integration.net.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestDefinition
{
	private static final String SPACE = " ";
	private static final String SLASH = "/";
	
	private String method;
	private String url;
	private String protocol;
	private String version;
	private Log log = LogFactory.getLog( this.getClass() );
	
	public RequestDefinition()
	{
		method = "GET";
		url = "/";
		protocol = "HTTP";
		version = "1.1";
	}
	
	public RequestDefinition( String requestString )
	{
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Parsing request [" + requestString + "]" );
		
		String[] requestArray = requestString.split( SPACE );
		method = requestArray[ 0 ].trim();
		url = requestArray[ 1 ].trim();
		requestArray = requestArray[ 2 ].split( SLASH );
		protocol = requestArray[ 0 ].trim();
		version = requestArray[ 1 ].trim();
		
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Request reconstituted as [" + toString() + "]" );
		
	}

	public String getMethod()
	{
		return method;
	}

	public String getUrl()
	{
		return url;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public String getVersion()
	{
		return version;
	}
	
	
	
	public void setMethod( String method )
	{
		this.method = method;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

	public void setProtocol( String protocol )
	{
		this.protocol = protocol;
	}

	public void setVersion( String version )
	{
		this.version = version;
	}

	public String toString()
	{
		StringBuilder requestLine = new StringBuilder();
		requestLine.append( method ).append( SPACE ).append( url).append( SPACE ).append( protocol ).append( SLASH ).append( version );
		return requestLine.toString();
	}
}
