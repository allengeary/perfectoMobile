<<<<<<< HEAD
package com.perfectomobile.integration.net.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseDefinition
{
	private static final String SPACE = " ";
	private static final String SLASH = "/";
	
	private String protocol;
	private String version;
	private int code;
	private String message;
	private Log log = LogFactory.getLog( this.getClass() );
	
	public ResponseDefinition()
	{
		protocol = "HTTP";
		version = "1.1";
		code = 200;
		message = "OK";
	}
	
	public ResponseDefinition( String requestString )
	{
		
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Parsing request [" + requestString + "]" );
		
		int firstSpace = requestString.indexOf( SPACE );
		String[] versionData = requestString.substring( 0, firstSpace ).split( SLASH );
		protocol = versionData[ 0 ].trim();
		version = versionData[ 1 ].trim();
		
		int nextSpace = requestString.indexOf( SPACE, firstSpace + 1 );
		code = Integer.parseInt( requestString.substring( firstSpace + 1, nextSpace ) );
		message = requestString.substring(  nextSpace + 1 );
		
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Request reconstituted as [" + toString() + "]" );
	}

	public int getCode()
	{
		return code;
	}



	public String getMessage()
	{
		return message;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public String getVersion()
	{
		return version;
	}
	
	public String toString()
	{
		StringBuilder requestLine = new StringBuilder();
		requestLine.append( protocol ).append( SLASH ).append( version ).append( SPACE ).append( code ).append( SPACE ).append( message );
		return requestLine.toString();
	}
}
=======
package com.perfectomobile.integration.net.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseDefinition
{
	private static final String SPACE = " ";
	private static final String SLASH = "/";
	
	private String protocol;
	private String version;
	private int code;
	private String message;
	private Log log = LogFactory.getLog( this.getClass() );
	
	public ResponseDefinition()
	{
		protocol = "HTTP";
		version = "1.1";
		code = 200;
		message = "OK";
	}
	
	public ResponseDefinition( String requestString )
	{
		
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Parsing request [" + requestString + "]" );
		
		int firstSpace = requestString.indexOf( SPACE );
		String[] versionData = requestString.substring( 0, firstSpace ).split( SLASH );
		protocol = versionData[ 0 ].trim();
		version = versionData[ 1 ].trim();
		
		int nextSpace = requestString.indexOf( SPACE, firstSpace + 1 );
		code = Integer.parseInt( requestString.substring( firstSpace + 1, nextSpace ) );
		message = requestString.substring(  nextSpace + 1 );
		
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Request reconstituted as [" + toString() + "]" );
	}

	public int getCode()
	{
		return code;
	}



	public String getMessage()
	{
		return message;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public String getVersion()
	{
		return version;
	}
	
	public String toString()
	{
		StringBuilder requestLine = new StringBuilder();
		requestLine.append( protocol ).append( SLASH ).append( version ).append( SPACE ).append( code ).append( SPACE ).append( message );
		return requestLine.toString();
	}
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
