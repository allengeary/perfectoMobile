<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.task;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.perfectomobile.integration.action.AdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.rqm.RQMAdapter;
import com.perfectomobile.integration.rqm.action.cloud.HandsetList;
import com.perfectomobile.integration.rqm.action.cloud.RepositoryList;
import com.perfectomobile.integration.rqm.bean.Progress;
import com.perfectomobile.integration.rqm.model.tasks.TaskModel;

public class ImportTask extends AbstractTask
{
	private static final String SCRIPT = "scripts";
	private static final String DEVICES = "devices";
	public static final String NAME = "IMPORT";
	

	private static final String LEFT_BRACKET = "{";
	private static final String RIGHT_BRACKET = "}";
	private static final String LEFT_BRACE = "[";
	private static final String RIGHT_BRACE = "]";
	private static final String COMMA = ",";
	private static final String COLON = ":";
	private static final String QUOTE = "\"";
	private static final String FILE_SEP = "\"fileSeparator\"";
	private static final String RESOURCE_LIST = "\"resourceList\"";

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		TaskModel tModel = (TaskModel) actionParameters.get( TASK_MODEL );

		URL taskUrl = new URL( tModel.getUpdateUrl() );
		
		//
		// Let RQM know that we are processing this
		//
		HttpRequest adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 0, "Acquiring Resources...", null );
		
		switch( tModel.getResourceType().toLowerCase() )
		{
			case SCRIPT:
				AdapterAction adapterAction = rqmAdapter.getAction( RepositoryList.NAME );
				String[] scriptList = (String[]) adapterAction.performAction( null );
				//
				// Let RQM know that we have data from Perfecto
				//
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				setStatus( adapterRequest, taskUrl, 25, "Script List Acquired...", null );
				
				
				//
				// Package the script list and send it off
				//
				Progress currentProgress = new Progress();
				currentProgress.setCode( -1 );
				currentProgress.setProgressLevel( 100 );
				currentProgress.setPayLoad( toJson( scriptList ) );
				
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				adapterRequest.setRequestBody( currentProgress.toString().getBytes() );
				doPut( taskUrl.toString(), adapterRequest );
				break;
				
			case DEVICES:
				AdapterAction deviceAction = rqmAdapter.getAction( HandsetList.NAME );
				String[] deviceList = (String[]) deviceAction.performAction( null );
				//
				// Let RQM know that we have data from Perfecto
				//
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				setStatus( adapterRequest, taskUrl, 25, "Script List Acquired...", null );
				
				
				//
				// Package the script list and send it off
				//
				Progress deviceProgress = new Progress();
				deviceProgress.setCode( -1 );
				deviceProgress.setProgressLevel( 100 );
				deviceProgress.setPayLoad( toJson( deviceList ) );
				
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				adapterRequest.setRequestBody( deviceProgress.toString().getBytes() );
				doPut( taskUrl.toString(), adapterRequest );
				break;
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName()
	{
		return NAME;
	}
	
	private String toJson( String[] resourceList )
	{
		try
		{
		
			StringBuilder jsonBuilder = new StringBuilder();
			
			jsonBuilder.append( LEFT_BRACKET ).append( FILE_SEP ).append( COLON ).append( QUOTE ).append( "\\" ).append( File.separator ).append( QUOTE ).append( COMMA );
			jsonBuilder.append( RESOURCE_LIST ).append( COLON ).append( LEFT_BRACE );
			
			boolean firstAdd = true;
			for ( String resource : resourceList )
			{
				if ( !firstAdd )
				{
					jsonBuilder.append( COMMA );
				}
				
				jsonBuilder.append( QUOTE ).append( URLEncoder.encode( resource, "UTF-8" ) ).append( QUOTE );
				
				firstAdd = false;
			}
			
			jsonBuilder.append( RIGHT_BRACE ).append( RIGHT_BRACKET );
			
			return jsonBuilder.toString();
		}
		catch( Exception e )
		{
			log.error( "Error creating JSON String", e );
		}
		
		return null;
	}

}
=======
package com.perfectomobile.integration.rqm.action.task;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.perfectomobile.integration.action.AdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.rqm.RQMAdapter;
import com.perfectomobile.integration.rqm.action.cloud.HandsetList;
import com.perfectomobile.integration.rqm.action.cloud.RepositoryList;
import com.perfectomobile.integration.rqm.bean.Progress;
import com.perfectomobile.integration.rqm.model.tasks.TaskModel;

public class ImportTask extends AbstractTask
{
	private static final String SCRIPT = "scripts";
	private static final String DEVICES = "devices";
	public static final String NAME = "IMPORT";
	

	private static final String LEFT_BRACKET = "{";
	private static final String RIGHT_BRACKET = "}";
	private static final String LEFT_BRACE = "[";
	private static final String RIGHT_BRACE = "]";
	private static final String COMMA = ",";
	private static final String COLON = ":";
	private static final String QUOTE = "\"";
	private static final String FILE_SEP = "\"fileSeparator\"";
	private static final String RESOURCE_LIST = "\"resourceList\"";

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		TaskModel tModel = (TaskModel) actionParameters.get( TASK_MODEL );

		URL taskUrl = new URL( tModel.getUpdateUrl() );
		
		//
		// Let RQM know that we are processing this
		//
		HttpRequest adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 0, "Acquiring Resources...", null );
		
		switch( tModel.getResourceType().toLowerCase() )
		{
			case SCRIPT:
				AdapterAction adapterAction = rqmAdapter.getAction( RepositoryList.NAME );
				String[] scriptList = (String[]) adapterAction.performAction( null );
				//
				// Let RQM know that we have data from Perfecto
				//
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				setStatus( adapterRequest, taskUrl, 25, "Script List Acquired...", null );
				
				
				//
				// Package the script list and send it off
				//
				Progress currentProgress = new Progress();
				currentProgress.setCode( -1 );
				currentProgress.setProgressLevel( 100 );
				currentProgress.setPayLoad( toJson( scriptList ) );
				
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				adapterRequest.setRequestBody( currentProgress.toString().getBytes() );
				doPut( taskUrl.toString(), adapterRequest );
				break;
				
			case DEVICES:
				AdapterAction deviceAction = rqmAdapter.getAction( HandsetList.NAME );
				String[] deviceList = (String[]) deviceAction.performAction( null );
				//
				// Let RQM know that we have data from Perfecto
				//
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				setStatus( adapterRequest, taskUrl, 25, "Script List Acquired...", null );
				
				
				//
				// Package the script list and send it off
				//
				Progress deviceProgress = new Progress();
				deviceProgress.setCode( -1 );
				deviceProgress.setProgressLevel( 100 );
				deviceProgress.setPayLoad( toJson( deviceList ) );
				
				adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				adapterRequest.setRequestBody( deviceProgress.toString().getBytes() );
				doPut( taskUrl.toString(), adapterRequest );
				break;
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName()
	{
		return NAME;
	}
	
	private String toJson( String[] resourceList )
	{
		try
		{
		
			StringBuilder jsonBuilder = new StringBuilder();
			
			jsonBuilder.append( LEFT_BRACKET ).append( FILE_SEP ).append( COLON ).append( QUOTE ).append( "\\" ).append( File.separator ).append( QUOTE ).append( COMMA );
			jsonBuilder.append( RESOURCE_LIST ).append( COLON ).append( LEFT_BRACE );
			
			boolean firstAdd = true;
			for ( String resource : resourceList )
			{
				if ( !firstAdd )
				{
					jsonBuilder.append( COMMA );
				}
				
				jsonBuilder.append( QUOTE ).append( URLEncoder.encode( resource, "UTF-8" ) ).append( QUOTE );
				
				firstAdd = false;
			}
			
			jsonBuilder.append( RIGHT_BRACE ).append( RIGHT_BRACKET );
			
			return jsonBuilder.toString();
		}
		catch( Exception e )
		{
			log.error( "Error creating JSON String", e );
		}
		
		return null;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
