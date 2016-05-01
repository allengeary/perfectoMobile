package com.perfectomobile.integration.rqm.action;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.action.ActionRunner;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.action.task.AbstractTask;
import com.perfectomobile.integration.rqm.bean.Progress;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.tasks.TaskModel;

public class HandleTask extends AbstractAdapterAction
{
	public static final String NAME = "Handle Task";
	public static final String TASK_URL = "taskUrl";
	public static final String PROJECT = "PROJECT_AREA";
	public static final String ADAPTER_ID = "adapterId";
	public static final String RESULT_URL = "resultUrl";

	private ExecutorService threadPool = Executors.newCachedThreadPool();

	public HandleTask()
	{

	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL taskUrl = new URL( (String) actionParameters.get( TASK_URL ) );
		Project projectArea = (Project) actionParameters.get( PROJECT );

		//
		// Let RQM know that we are handling the request
		//
		HttpRequest adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		
		
		Progress progress = new Progress();
		progress.setTaken( true );
		
		adapterRequest.setRequestBody( progress.toString().getBytes() );
		HttpResponse httpResponse = doPut( taskUrl.toString(), adapterRequest );
		
		String executionId = httpResponse.getresponseHeaders().getHeader( "Content-Location" ).getValues().get( 0 );
		
		//
		// Get the task details
		//
		HttpRequest taskRequest = new HttpRequest( GET, taskUrl.getPath() );
		taskRequest.getRequestHeaders().addHeader( sessionId );
		httpResponse = doGet( taskUrl.toString(), taskRequest );
		
		TaskModel tModel = new TaskModel( new String( httpResponse.getresponseBody() ) );
		AbstractTask actionTask = (AbstractTask) rqmAdapter.getAction( tModel.getType() );

		HashMap<String,Object> sessionMap = new HashMap<String,Object>( 5 );
		sessionMap.put( Header.COOKIE, sessionId );
		sessionMap.put( AbstractTask.TASK_MODEL, tModel );
		sessionMap.put( AbstractTask.PROJECT, projectArea );
		sessionMap.put( AbstractTask.ADAPTER_ID , actionParameters.get( ADAPTER_ID ) );
		sessionMap.put( AbstractTask.RESULT_URL , actionParameters.get( RESULT_URL ) );
		sessionMap.put( AbstractTask.EXECUTION_ID , executionId );
		
		//
		// Fire off the action thread and return
		//
		
		threadPool.submit( new ActionRunner( sessionMap, actionTask ) );
		
		return true;
	}

	public String getName()
	{
		return NAME;
	}

}
