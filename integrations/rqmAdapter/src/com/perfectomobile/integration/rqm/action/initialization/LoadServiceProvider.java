<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.initialization;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.model.AutomationCatalogModel;

public class LoadServiceProvider extends AbstractAdapterAction
{
	public static final String NAME = "Load Service Provider Catalog";
	public static final String SP_URL = "SP URL";
	private static final String AUTO_ADAPTER = "http://jazz.net/ns/auto/rqm#AutomationAdapter";

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL spUrl = new URL( (String) actionParameters.get( SP_URL ) );
		
		
		//
		// Get the list of all Creation Factories
		//
		
		HttpRequest catalogRequest = new HttpRequest( GET, spUrl.getPath() );
		catalogRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( spUrl.toString(), catalogRequest );
		
		AutomationCatalogModel automationModel = new AutomationCatalogModel( new String( httpResponse.getresponseBody() ) );
		
		if ( log.isDebugEnabled() )
			log.debug( "Located " + automationModel.getEntryCount() + " Automation Results - searching for " + AUTO_ADAPTER );
		
		for ( int i=1; i<automationModel.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing project #" + i + " using [" + automationModel.getType( i )  + "] against [" + AUTO_ADAPTER + "]" );
			if ( AUTO_ADAPTER.equals( automationModel.getType( i ) ) )
			{
				return automationModel.getUrl( i );
			}
		}
		
		throw new IllegalStateException( "The Automation Adapter [" + AUTO_ADAPTER + "] could not be located" );
	}

	public String getName()
	{
		return NAME;
	}

}
=======
package com.perfectomobile.integration.rqm.action.initialization;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.model.AutomationCatalogModel;

public class LoadServiceProvider extends AbstractAdapterAction
{
	public static final String NAME = "Load Service Provider Catalog";
	public static final String SP_URL = "SP URL";
	private static final String AUTO_ADAPTER = "http://jazz.net/ns/auto/rqm#AutomationAdapter";

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL spUrl = new URL( (String) actionParameters.get( SP_URL ) );
		
		
		//
		// Get the list of all Creation Factories
		//
		
		HttpRequest catalogRequest = new HttpRequest( GET, spUrl.getPath() );
		catalogRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( spUrl.toString(), catalogRequest );
		
		AutomationCatalogModel automationModel = new AutomationCatalogModel( new String( httpResponse.getresponseBody() ) );
		
		if ( log.isDebugEnabled() )
			log.debug( "Located " + automationModel.getEntryCount() + " Automation Results - searching for " + AUTO_ADAPTER );
		
		for ( int i=1; i<automationModel.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing project #" + i + " using [" + automationModel.getType( i )  + "] against [" + AUTO_ADAPTER + "]" );
			if ( AUTO_ADAPTER.equals( automationModel.getType( i ) ) )
			{
				return automationModel.getUrl( i );
			}
		}
		
		throw new IllegalStateException( "The Automation Adapter [" + AUTO_ADAPTER + "] could not be located" );
	}

	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
