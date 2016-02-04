<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.initialization;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.model.AdapterModel;

public class AdapterInformation extends AbstractAdapterAction
{
	public static final String NAME = "Get Adapter Information";
	public static final String ADAPTER_URL = "adapterUrl";

	private String adapterName;
	
	public AdapterInformation( String adapterName )
	{
		this.adapterName = adapterName;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL adapterUrl = new URL( (String) actionParameters.get( ADAPTER_URL ) );
		
		//
		// Get the list of all active projects
		//
		HttpRequest projectRequest = new HttpRequest( GET, adapterUrl.getPath() );
		projectRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( adapterUrl.toString(), projectRequest );
		
		AdapterModel adapterModel = new AdapterModel( new String( httpResponse.getresponseBody() ) );
		
		if ( !adapterName.equals( adapterModel.getTitle() ) )
			throw new IllegalStateException( "The Adapter information returned is not for the [" + adapterName + "] adapter" );
		
		return adapterModel;
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
import com.perfectomobile.integration.rqm.model.AdapterModel;

public class AdapterInformation extends AbstractAdapterAction
{
	public static final String NAME = "Get Adapter Information";
	public static final String ADAPTER_URL = "adapterUrl";

	private String adapterName;
	
	public AdapterInformation( String adapterName )
	{
		this.adapterName = adapterName;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL adapterUrl = new URL( (String) actionParameters.get( ADAPTER_URL ) );
		
		//
		// Get the list of all active projects
		//
		HttpRequest projectRequest = new HttpRequest( GET, adapterUrl.getPath() );
		projectRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( adapterUrl.toString(), projectRequest );
		
		AdapterModel adapterModel = new AdapterModel( new String( httpResponse.getresponseBody() ) );
		
		if ( !adapterName.equals( adapterModel.getTitle() ) )
			throw new IllegalStateException( "The Adapter information returned is not for the [" + adapterName + "] adapter" );
		
		return adapterModel;
	}

	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
