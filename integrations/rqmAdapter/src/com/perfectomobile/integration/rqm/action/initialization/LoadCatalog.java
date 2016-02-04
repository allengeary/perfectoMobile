<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.initialization;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.ProjectsModel;
import com.perfectomobile.integration.rqm.model.ServiceProviderCatalogModel;

public class LoadCatalog extends AbstractAdapterAction
{

	public static final String NAME = "Load QM Catalog";
	public static final String PROJECT_AREA = "Project Area";
	
	
	private URL catalogUrl;

	public LoadCatalog( String catalogUrl ) throws MalformedURLException
	{
		this.catalogUrl = new URL( catalogUrl );
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		Project projectArea = (Project) actionParameters.get( PROJECT_AREA );
		
		
		//
		// Get the list of all service provider catalogs
		//
		
		HttpRequest catalogRequest = new HttpRequest( GET, catalogUrl.getPath() );
		catalogRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( catalogUrl.toString(), catalogRequest );
		
		
		ServiceProviderCatalogModel spCatalog = new ServiceProviderCatalogModel( new String( httpResponse.getresponseBody() ) );
		
		for ( int i=1; i<spCatalog.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing Service Provider #" + i + " as [" + spCatalog.getName( i ) + "]" );
			if ( projectArea.getName().equals( spCatalog.getName( i ) ) )
			{
				return spCatalog.getServiceProvider( i );
			}
		}
		
		throw new IllegalArgumentException( "Could not locate a Service Provider Catalog for [" + projectArea.getName() + "]" );
	}

	public String getName()
	{
		return NAME;
	}

}
=======
package com.perfectomobile.integration.rqm.action.initialization;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.ProjectsModel;
import com.perfectomobile.integration.rqm.model.ServiceProviderCatalogModel;

public class LoadCatalog extends AbstractAdapterAction
{

	public static final String NAME = "Load QM Catalog";
	public static final String PROJECT_AREA = "Project Area";
	
	
	private URL catalogUrl;

	public LoadCatalog( String catalogUrl ) throws MalformedURLException
	{
		this.catalogUrl = new URL( catalogUrl );
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		Project projectArea = (Project) actionParameters.get( PROJECT_AREA );
		
		
		//
		// Get the list of all service provider catalogs
		//
		
		HttpRequest catalogRequest = new HttpRequest( GET, catalogUrl.getPath() );
		catalogRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( catalogUrl.toString(), catalogRequest );
		
		
		ServiceProviderCatalogModel spCatalog = new ServiceProviderCatalogModel( new String( httpResponse.getresponseBody() ) );
		
		for ( int i=1; i<spCatalog.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing Service Provider #" + i + " as [" + spCatalog.getName( i ) + "]" );
			if ( projectArea.getName().equals( spCatalog.getName( i ) ) )
			{
				return spCatalog.getServiceProvider( i );
			}
		}
		
		throw new IllegalArgumentException( "Could not locate a Service Provider Catalog for [" + projectArea.getName() + "]" );
	}

	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
