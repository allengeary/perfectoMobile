<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.initialization;

import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.ProjectsModel;

public class ValidateProjectArea extends AbstractAdapterAction
{

	public static final String NAME = "Validate Project Area";
	
	
	//
	// Static Request Names
	//
	public static final String INTEGRATION_SERVICE = "secure/service/com.ibm.rqm.integration.service.IIntegrationService/projects";
	
	private String baseUrl;
	private String projectArea;

	public ValidateProjectArea( String baseUrl, String projectArea )
	{
		this.baseUrl = baseUrl;
		this.projectArea = projectArea;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		
		//
		// Get the list of all active projects
		//
		HttpRequest projectRequest = new HttpRequest( GET, INTEGRATION_SERVICE );
		projectRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( baseUrl + INTEGRATION_SERVICE, projectRequest );
		
		
		ProjectsModel projectList = new ProjectsModel( new String( httpResponse.getresponseBody() ) );
		
		for ( int i=1; i<projectList.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing project #" + i + " using Alias [" + projectList.getAlias( i ) + "] and Name [" + projectList.getName( i ) + "]" );
			if ( projectArea.equals( projectList.getAlias( i ) ) || projectArea.equals( projectList.getName( i ) ) )
			{
				return new Project( projectList.getName( i ), projectList.getAlias( i ), projectList.getId( i ) );
			}
		}
		
		throw new IllegalArgumentException( "The Project [" + projectArea + "] could not be located" );
	}

	public String getName()
	{
		return NAME;
	}

}
=======
package com.perfectomobile.integration.rqm.action.initialization;

import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.ProjectsModel;

public class ValidateProjectArea extends AbstractAdapterAction
{

	public static final String NAME = "Validate Project Area";
	
	
	//
	// Static Request Names
	//
	public static final String INTEGRATION_SERVICE = "secure/service/com.ibm.rqm.integration.service.IIntegrationService/projects";
	
	private String baseUrl;
	private String projectArea;

	public ValidateProjectArea( String baseUrl, String projectArea )
	{
		this.baseUrl = baseUrl;
		this.projectArea = projectArea;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		
		//
		// Get the list of all active projects
		//
		HttpRequest projectRequest = new HttpRequest( GET, INTEGRATION_SERVICE );
		projectRequest.getRequestHeaders().addHeader( sessionId );
				
		HttpResponse httpResponse = doGet( baseUrl + INTEGRATION_SERVICE, projectRequest );
		
		
		ProjectsModel projectList = new ProjectsModel( new String( httpResponse.getresponseBody() ) );
		
		for ( int i=1; i<projectList.getEntryCount() + 1; i++ )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Analyzing project #" + i + " using Alias [" + projectList.getAlias( i ) + "] and Name [" + projectList.getName( i ) + "]" );
			if ( projectArea.equals( projectList.getAlias( i ) ) || projectArea.equals( projectList.getName( i ) ) )
			{
				return new Project( projectList.getName( i ), projectList.getAlias( i ), projectList.getId( i ) );
			}
		}
		
		throw new IllegalArgumentException( "The Project [" + projectArea + "] could not be located" );
	}

	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
