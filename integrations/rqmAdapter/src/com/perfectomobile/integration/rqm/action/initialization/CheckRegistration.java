<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.initialization;

import java.net.URLEncoder;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.AdaptersModel;

public class CheckRegistration extends AbstractAdapterAction
{
	public static final String NAME = "Check Registration";
	public static final String PROJECT_AREA = "Project Area";
	//
	// Static Request Names
	//
	private static final String INTEGRATION_SERVICE = "secure/service/com.ibm.rqm.integration.service.IIntegrationService/resources";
	
	private String baseUrl;
	private String adapterName;

	public CheckRegistration( String baseUrl, String adapterName )
	{
		this.baseUrl = baseUrl;
		this.adapterName = adapterName;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		Project projectArea = (Project) actionParameters.get( PROJECT_AREA );
		
		//
		// Get the list of all active projects
		//
		HttpRequest projectRequest = new HttpRequest( GET, INTEGRATION_SERVICE );
		projectRequest.getRequestHeaders().addHeader( sessionId );
		HttpResponse httpResponse = doGet( baseUrl + INTEGRATION_SERVICE + "/" + URLEncoder.encode( projectArea.getName(), "UTF-8" ) + "/adapter", projectRequest );
		
		
		AdaptersModel adapterModel = new AdaptersModel( new String( httpResponse.getresponseBody() ) );
		
		for ( int i=1; i<adapterModel.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing Adapter #" + i + " using as [" + adapterModel.getAlias( i ) + "] using Name [" + adapterModel.getName( i ) + "] against " + adapterName );
			if ( adapterName.equals( adapterModel.getName( i ) ) )
			{
				if ( log.isDebugEnabled() )
					log.debug( "Adapter URL: " + adapterModel.getId( i ) );
				return adapterModel.getId( i );
			}
		}
		
		return null;
	}

	public String getName()
	{
		return NAME;
	}

}
=======
package com.perfectomobile.integration.rqm.action.initialization;

import java.net.URLEncoder;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.AdaptersModel;

public class CheckRegistration extends AbstractAdapterAction
{
	public static final String NAME = "Check Registration";
	public static final String PROJECT_AREA = "Project Area";
	//
	// Static Request Names
	//
	private static final String INTEGRATION_SERVICE = "secure/service/com.ibm.rqm.integration.service.IIntegrationService/resources";
	
	private String baseUrl;
	private String adapterName;

	public CheckRegistration( String baseUrl, String adapterName )
	{
		this.baseUrl = baseUrl;
		this.adapterName = adapterName;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		Project projectArea = (Project) actionParameters.get( PROJECT_AREA );
		
		//
		// Get the list of all active projects
		//
		HttpRequest projectRequest = new HttpRequest( GET, INTEGRATION_SERVICE );
		projectRequest.getRequestHeaders().addHeader( sessionId );
		HttpResponse httpResponse = doGet( baseUrl + INTEGRATION_SERVICE + "/" + URLEncoder.encode( projectArea.getName(), "UTF-8" ) + "/adapter", projectRequest );
		
		
		AdaptersModel adapterModel = new AdaptersModel( new String( httpResponse.getresponseBody() ) );
		
		for ( int i=1; i<adapterModel.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing Adapter #" + i + " using as [" + adapterModel.getAlias( i ) + "] using Name [" + adapterModel.getName( i ) + "] against " + adapterName );
			if ( adapterName.equals( adapterModel.getName( i ) ) )
			{
				if ( log.isDebugEnabled() )
					log.debug( "Adapter URL: " + adapterModel.getId( i ) );
				return adapterModel.getId( i );
			}
		}
		
		return null;
	}

	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
