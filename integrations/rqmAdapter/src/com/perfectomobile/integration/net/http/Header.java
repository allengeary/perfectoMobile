<<<<<<< HEAD
package com.perfectomobile.integration.net.http;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Header
{
	public static final String SET_COOKIE = "Set-Cookie";
	public static final String COOKIE = "Cookie";
	private static final String COLON = ":";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private Log log = LogFactory.getLog( this.getClass() );
	
	private String name;
	private boolean cookieFlag;
	private List<String> valueList = new ArrayList<String>( 5 );
	
	private Map<String,HttpCookie> cookieMap = new HashMap<String,HttpCookie>( 10 );
	
	public Header( String name, String value )
	{
		this.name = name;
		cookieFlag = SET_COOKIE.equals( name );
			
		addValue( value );
	}
	
	public Header( String headerDefinition )
	{
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Parsing header value [" + headerDefinition + "]" );
		
		int namePosition = headerDefinition.indexOf( COLON );
		

		name = headerDefinition.substring( 0, namePosition );
		cookieFlag = SET_COOKIE.equals( name );
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Extracted header name as [" + name + "]" );
		
		String[] headerArray = headerDefinition.substring( namePosition + 1 ).split(  "," );
		
		
		
		for ( String headerValue : headerArray )
		{
			if ( log.isDebugEnabled() )
				log.debug( Thread.currentThread().getName() + "-Extracted header value as [" + headerValue + "]" );
			
			addValue( headerValue.trim() );
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public HttpCookie getCookie( String cookieName )
	{
		return cookieMap.get( cookieName.toLowerCase() );
	}
	
	public void addValue( String value )
	{
		if ( value != null )
		{
			if ( cookieFlag )
			{
				String[] cookieValue = value.split( "=" );
				HttpCookie cookie = null;
				if ( cookieValue.length == 1 )
					cookie = new HttpCookie( cookieValue[0], "" );
				else
					cookie = new HttpCookie( cookieValue[0], cookieValue[ 1 ]);
				
				cookie.setVersion( 0 );
				cookieMap.put( cookieValue[0].toLowerCase(), cookie );
				
			}
			
			valueList.add( value );
		}
	}
	
	public List<String> getValues()
	{
		return Collections.unmodifiableList( valueList );
	}
	
	public String toString()
	{
		StringBuilder headerLine = new StringBuilder();
		headerLine.append( name ).append( COLON ).append( SPACE );
		boolean oneAdded = false;
		for ( String headerValue : valueList )
		{
			if ( oneAdded )
				headerLine.append( COMMA ).append( SPACE );
			headerLine.append( headerValue );
			oneAdded = true;
		}
		
		return headerLine.toString();
	}
	
}
=======
package com.perfectomobile.integration.net.http;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Header
{
	public static final String SET_COOKIE = "Set-Cookie";
	public static final String COOKIE = "Cookie";
	private static final String COLON = ":";
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private Log log = LogFactory.getLog( this.getClass() );
	
	private String name;
	private boolean cookieFlag;
	private List<String> valueList = new ArrayList<String>( 5 );
	
	private Map<String,HttpCookie> cookieMap = new HashMap<String,HttpCookie>( 10 );
	
	public Header( String name, String value )
	{
		this.name = name;
		cookieFlag = SET_COOKIE.equals( name );
			
		addValue( value );
	}
	
	public Header( String headerDefinition )
	{
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Parsing header value [" + headerDefinition + "]" );
		
		int namePosition = headerDefinition.indexOf( COLON );
		

		name = headerDefinition.substring( 0, namePosition );
		cookieFlag = SET_COOKIE.equals( name );
		if ( log.isDebugEnabled() )
			log.debug( Thread.currentThread().getName() + "-Extracted header name as [" + name + "]" );
		
		String[] headerArray = headerDefinition.substring( namePosition + 1 ).split(  "," );
		
		
		
		for ( String headerValue : headerArray )
		{
			if ( log.isDebugEnabled() )
				log.debug( Thread.currentThread().getName() + "-Extracted header value as [" + headerValue + "]" );
			
			addValue( headerValue.trim() );
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public HttpCookie getCookie( String cookieName )
	{
		return cookieMap.get( cookieName.toLowerCase() );
	}
	
	public void addValue( String value )
	{
		if ( value != null )
		{
			if ( cookieFlag )
			{
				String[] cookieValue = value.split( "=" );
				HttpCookie cookie = null;
				if ( cookieValue.length == 1 )
					cookie = new HttpCookie( cookieValue[0], "" );
				else
					cookie = new HttpCookie( cookieValue[0], cookieValue[ 1 ]);
				
				cookie.setVersion( 0 );
				cookieMap.put( cookieValue[0].toLowerCase(), cookie );
				
			}
			
			valueList.add( value );
		}
	}
	
	public List<String> getValues()
	{
		return Collections.unmodifiableList( valueList );
	}
	
	public String toString()
	{
		StringBuilder headerLine = new StringBuilder();
		headerLine.append( name ).append( COLON ).append( SPACE );
		boolean oneAdded = false;
		for ( String headerValue : valueList )
		{
			if ( oneAdded )
				headerLine.append( COMMA ).append( SPACE );
			headerLine.append( headerValue );
			oneAdded = true;
		}
		
		return headerLine.toString();
	}
	
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
