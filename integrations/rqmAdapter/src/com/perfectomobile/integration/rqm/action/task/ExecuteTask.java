<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.task;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Attachment;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.bean.Result;
import com.perfectomobile.integration.rqm.bean.Results;
import com.perfectomobile.integration.rqm.bean.StepResult;
import com.perfectomobile.integration.rqm.model.WorkItemModel;
import com.perfectomobile.integration.rqm.model.cloud.ExecutionModel;
import com.perfectomobile.integration.rqm.model.cloud.ExecutionProgressModel;
import com.perfectomobile.integration.rqm.model.cloud.HandsetModel;
import com.perfectomobile.integration.rqm.model.tasks.ResultModel;
import com.perfectomobile.integration.rqm.model.tasks.TaskModel;

public class ExecuteTask extends AbstractTask
{
	public static final String NAME = "EXECUTE";

	private static final String COMMA = ",";
	private static final String ATTACH_BEGIN = "<html><body>File Upload Response:";
	private static final String ATTACH_END = "</body></html>";

	private static final String PASSED = "PASSED";
	private static final String FAILED = "FAILED";

	private String cloudUrl;
	private String userName;
	private String password;
	private String attachmentRoot;
	private String rqmUrl;

	public ExecuteTask( String cloudUrl, String userName, String password, String attachmentRoot, String rqmUrl )
	{
		this.cloudUrl = cloudUrl;
		this.userName = userName;
		this.password = password;
		this.attachmentRoot = attachmentRoot;
		this.rqmUrl = rqmUrl;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{

		long startTime = System.currentTimeMillis();

		Header sessionId = ( Header ) actionParameters.get( Header.COOKIE );
		if (sessionId == null)
			throw new SecurityException( "Session Header is not specified" );

		TaskModel tModel = ( TaskModel ) actionParameters.get( TASK_MODEL );
		Project projectArea = ( Project ) actionParameters.get( PROJECT );
		String adapterId = ( String ) actionParameters.get( ADAPTER_ID );

		URL taskUrl = new URL( tModel.getUpdateUrl() );

		//
		// Let RQM know that we are processing this
		//
		HttpRequest adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 0, "Contacting [" + cloudUrl + "]...", null );

		//
		// Now find our execution work item
		//
		URL workItemUrl = new URL( tModel.getWorkItem() );
		adapterRequest = new HttpRequest( GET, workItemUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		HttpResponse httpResponse = doGet( workItemUrl.toString(), adapterRequest );
		WorkItemModel wModel = new WorkItemModel( new String( httpResponse.getresponseBody() ) );

		//
		// Now start the executions on all devices
		//
		String[] scriptName = tModel.getScriptList().split( COMMA );
		String[] deviceList = tModel.getDeviceList().split( COMMA );

		List<ExecutionModel> modelList = new ArrayList<ExecutionModel>( 5 );

		for (String script : scriptName)
		{
			if (!script.trim().isEmpty())
			{
				if (log.isInfoEnabled())
					log.info( "Processing Script [" + script + "]" );

				for (String deviceName : deviceList)
				{
					if (!deviceName.trim().isEmpty())
					{
						if (log.isInfoEnabled())
							log.info( "Processing Device [" + deviceName + "]" );
						String restResponse = new String( getUrl( new URL( cloudUrl + "/services/executions?operation=execute&user=" + userName + "&password=" + password + "&scriptKey=" + script.trim() + "&param.DUT=" + deviceName.trim() + "&responseFormat=xml" ) ) );

						ExecutionModel eModel = new ExecutionModel( restResponse );
						eModel.setStepResult( new StepResult( System.currentTimeMillis(), script.trim(), deviceName.trim() ) );
						modelList.add( eModel );
						
						//
						// Grab additional information on the device in use
						//
						restResponse = new String( getUrl( new URL( cloudUrl + "/services/handsets/" + deviceName.trim() + "?operation=info&user=" + userName + "&password=" + password + "&responseFormat=xml" ) ) );
						HandsetModel hModel = new HandsetModel( restResponse );
						
						eModel.getStepResult().setManufacturer( hModel.getManufacturer() );
						eModel.getStepResult().setModel( hModel.getModel() );
					}
				}
			}
		}

		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 10, "Executing [" + modelList.size() + "] tests...", "" );

		double progressDivider = 70 / modelList.size();
		boolean testRunning = true;

		boolean testPassed = true;

		//
		// Loop and wait for completion
		//
		
		while (testRunning)
		{
			double progressTotal = 0;

			testRunning = false;
			int testCount = 0;

			for (ExecutionModel model : modelList)
			{
				
					String restResponse = new String( getUrl( new URL( cloudUrl + "/services/executions/" + model.getId() + "?operation=status&user=" + userName + "&password=" + password + "&responseFormat=xml" ) ) );
					ExecutionProgressModel pModel = new ExecutionProgressModel( restResponse );
	
					//
					// If one test is still running then flag it
					//
					if (!pModel.isCompleted())
					{
						testCount++;
						testRunning = true;
					}
					else
					{
						if ( !model.getStepResult().isComplete() )
						{
							model.getStepResult().setEndTime( System.currentTimeMillis() );
							model.getStepResult().setState( pModel.isSuccess() ? PASSED : FAILED );
						}
						
						//
						// Check for a failed test and flag that
						//
						if (!pModel.isSuccess())
							testPassed = false;
					}
					

					if ( pModel.isCompleted() && pModel.getProgress() < 100 )
						progressTotal += ( progressDivider );
					else
						progressTotal += ( ( pModel.getProgress() / 100.0 ) * progressDivider );


					
				
			}

			adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
			adapterRequest.getRequestHeaders().addHeader( sessionId );
			setStatus( adapterRequest, taskUrl, 10 + ( int ) progressTotal, "There are [" + testCount + "] tests remaining...", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );
			try
			{
				Thread.sleep( 5000 );
			}
			catch (Exception e)
			{
			}

		}

		int pointsFailed = 0;
		for (ExecutionModel model : modelList)
		{
			String restResponse = new String( getUrl( new URL( cloudUrl + "/services/executions/" + model.getId() + "?operation=status&user=" + userName + "&password=" + password + "&responseFormat=xml" ) ) );
			ExecutionProgressModel pModel = new ExecutionProgressModel( restResponse );

			//
			// Check for a failed test and flag that
			//
			if (!pModel.isSuccess())
				pointsFailed++;
		}

		long endTime = System.currentTimeMillis();

		//
		// Cycle Through and download all of the reports
		//
		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 95, "Packaging Up Reports", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );

		List<Attachment> aList = new ArrayList<Attachment>( modelList.size() );

		for (ExecutionModel model : modelList)
		{
			URL downloadUrl = new URL( cloudUrl + "/services/reports/" + model.getKey() + "?operation=download&user=" + userName + "&password=" + password + "&responseFormat=xml&format=html" );
			if (log.isDebugEnabled())
				log.debug( "Downloading report from [" + downloadUrl.toString() + "]" );

			byte[] responseData = getUrl( downloadUrl );

			if (responseData != null)
			{
				File outputFile = File.createTempFile( "report", ".html", new File( attachmentRoot ) );

				if (log.isDebugEnabled())
					log.debug( "Writing report to [" + outputFile.getAbsolutePath() + "]" );

				FileOutputStream outputStream = new FileOutputStream( outputFile );
				outputStream.write( responseData, 0, responseData.length );
				outputStream.flush();
				outputStream.close();

				Attachment attach = new Attachment( "test/html", outputFile.getAbsolutePath() );
				
				model.getStepResult().setAttachment( attach );
				
				aList.add( attach );
			}
		}

		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 97, "Reports Packaged - Uploading to RQM", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );

		//
		// Cycle through all of the reports and upload them as an attachment
		//
		for (Attachment a : aList)
		{
			try
			{
				URL uploadUrl = new URL( rqmUrl + "secure/service/com.ibm.rqm.planning.service.internal.rest.IAttachmentRestService?projectId=" + projectArea.getId() );
				adapterRequest = new HttpRequest( POST, uploadUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				adapterRequest.getRequestHeaders().addHeader( "OSLC-Core-Version", "2.0" );
	
				httpResponse = uploadAttachment( uploadUrl.toString(), adapterRequest, a, a.getContentType(), projectArea.getId() );
	
				String rqmContent = new String( httpResponse.getresponseBody() );
				if (log.isDebugEnabled())
					log.debug( "Response from Attachment Upload [" + rqmContent + "]" );
	
				if (rqmContent != null && !rqmContent.isEmpty())
				{
					if (rqmContent.startsWith( ATTACH_BEGIN ))
					{
						if (log.isDebugEnabled())
							log.debug( "Response from Attachment Upload [" + rqmContent + "]" );
						rqmContent = rqmContent.substring( ATTACH_BEGIN.length(), rqmContent.indexOf( ATTACH_END ) );
						String[] attachData = rqmContent.split( COMMA );
						if (attachData.length == 3)
							a.setId( attachData[0] );
						else
							log.warn( "Attachment result parameters were in an invalid format" );
					}
					else
						log.warn( "Attachment result was in an invalid format" );
				}
				else
					log.warn( "Attachment result was in an invalid format" );
			
			}
			catch( Exception e )
			{
				log.warn( "Error uploading attachments", e );
			}
		}

		//
		// Now post our result
		//
		Results runResults = new Results();
		runResults.setStartTime( startTime );
		runResults.setEndTime( endTime );
		runResults.setStatus( testPassed ? PASSED : FAILED );
		runResults.setPointsFailed( pointsFailed );
		runResults.setPointsAttempted( modelList.size() );
		runResults.getAttachments().addAll( aList );
		runResults.setAdapterId( adapterId );
		runResults.setTitle( tModel.getTitle() );
		runResults.setTestCase( wModel.getTestCase() );
		runResults.setTestScript( wModel.getRemoteScript() );
		runResults.setExecutionWorkItem( tModel.getWorkItem() );
		runResults.setRqmUrl( rqmUrl );
		runResults.addExecutionVariable( "scriptList", tModel.getScriptList() );
		runResults.addExecutionVariable( "deviceList", tModel.getDeviceList() );
		for (ExecutionModel model : modelList)
			runResults.addStep( model.getStepResult() );
		
		
		URL resultUrl = new URL( tModel.getResultUrl() );
		adapterRequest = new HttpRequest( POST, resultUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		adapterRequest.setRequestBody( runResults.toString().getBytes() );
		httpResponse = doPut( resultUrl.toString(), adapterRequest );

		//
		// Update our task with the status
		//
		if ( httpResponse.getresponseDefinition().getCode() == 201 )
		{
			ResultModel rModel = new ResultModel( new String( httpResponse.getresponseBody() ) );
			
			Result rBean = new Result( rModel.getId(), rModel.getItemId() );
			
			URL taskUpdateUrl = new URL( tModel.getUpdateUrl() );
			adapterRequest = new HttpRequest( PUT, taskUpdateUrl.getPath() );
			adapterRequest.getRequestHeaders().addHeader( sessionId );
			adapterRequest.setRequestBody( rBean.toString().getBytes() );
			httpResponse = doPut( taskUpdateUrl.toString(), adapterRequest );
			
			if ( httpResponse.getresponseDefinition().getCode() != 200 )
				log.warn( "Could not update the result with " + httpResponse.getresponseDefinition().getCode() );
		}
		
		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 100, "Executed [" + modelList.size() + "] tests", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );
		
		return true;
	}

	@Override
	public String getName()
	{
		return NAME;
	}

}
=======
package com.perfectomobile.integration.rqm.action.task;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Attachment;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.bean.Result;
import com.perfectomobile.integration.rqm.bean.Results;
import com.perfectomobile.integration.rqm.bean.StepResult;
import com.perfectomobile.integration.rqm.model.WorkItemModel;
import com.perfectomobile.integration.rqm.model.cloud.ExecutionModel;
import com.perfectomobile.integration.rqm.model.cloud.ExecutionProgressModel;
import com.perfectomobile.integration.rqm.model.cloud.HandsetModel;
import com.perfectomobile.integration.rqm.model.tasks.ResultModel;
import com.perfectomobile.integration.rqm.model.tasks.TaskModel;

public class ExecuteTask extends AbstractTask
{
	public static final String NAME = "EXECUTE";

	private static final String COMMA = ",";
	private static final String ATTACH_BEGIN = "<html><body>File Upload Response:";
	private static final String ATTACH_END = "</body></html>";

	private static final String PASSED = "PASSED";
	private static final String FAILED = "FAILED";

	private String cloudUrl;
	private String userName;
	private String password;
	private String attachmentRoot;
	private String rqmUrl;

	public ExecuteTask( String cloudUrl, String userName, String password, String attachmentRoot, String rqmUrl )
	{
		this.cloudUrl = cloudUrl;
		this.userName = userName;
		this.password = password;
		this.attachmentRoot = attachmentRoot;
		this.rqmUrl = rqmUrl;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{

		long startTime = System.currentTimeMillis();

		Header sessionId = ( Header ) actionParameters.get( Header.COOKIE );
		if (sessionId == null)
			throw new SecurityException( "Session Header is not specified" );

		TaskModel tModel = ( TaskModel ) actionParameters.get( TASK_MODEL );
		Project projectArea = ( Project ) actionParameters.get( PROJECT );
		String adapterId = ( String ) actionParameters.get( ADAPTER_ID );

		URL taskUrl = new URL( tModel.getUpdateUrl() );

		//
		// Let RQM know that we are processing this
		//
		HttpRequest adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 0, "Contacting [" + cloudUrl + "]...", null );

		//
		// Now find our execution work item
		//
		URL workItemUrl = new URL( tModel.getWorkItem() );
		adapterRequest = new HttpRequest( GET, workItemUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		HttpResponse httpResponse = doGet( workItemUrl.toString(), adapterRequest );
		WorkItemModel wModel = new WorkItemModel( new String( httpResponse.getresponseBody() ) );

		//
		// Now start the executions on all devices
		//
		String[] scriptName = tModel.getScriptList().split( COMMA );
		String[] deviceList = tModel.getDeviceList().split( COMMA );

		List<ExecutionModel> modelList = new ArrayList<ExecutionModel>( 5 );

		for (String script : scriptName)
		{
			if (!script.trim().isEmpty())
			{
				if (log.isInfoEnabled())
					log.info( "Processing Script [" + script + "]" );

				for (String deviceName : deviceList)
				{
					if (!deviceName.trim().isEmpty())
					{
						if (log.isInfoEnabled())
							log.info( "Processing Device [" + deviceName + "]" );
						String restResponse = new String( getUrl( new URL( cloudUrl + "/services/executions?operation=execute&user=" + userName + "&password=" + password + "&scriptKey=" + script.trim() + "&param.DUT=" + deviceName.trim() + "&responseFormat=xml" ) ) );

						ExecutionModel eModel = new ExecutionModel( restResponse );
						eModel.setStepResult( new StepResult( System.currentTimeMillis(), script.trim(), deviceName.trim() ) );
						modelList.add( eModel );
						
						//
						// Grab additional information on the device in use
						//
						restResponse = new String( getUrl( new URL( cloudUrl + "/services/handsets/" + deviceName.trim() + "?operation=info&user=" + userName + "&password=" + password + "&responseFormat=xml" ) ) );
						HandsetModel hModel = new HandsetModel( restResponse );
						
						eModel.getStepResult().setManufacturer( hModel.getManufacturer() );
						eModel.getStepResult().setModel( hModel.getModel() );
					}
				}
			}
		}

		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 10, "Executing [" + modelList.size() + "] tests...", "" );

		double progressDivider = 70 / modelList.size();
		boolean testRunning = true;

		boolean testPassed = true;

		//
		// Loop and wait for completion
		//
		
		while (testRunning)
		{
			double progressTotal = 0;

			testRunning = false;
			int testCount = 0;

			for (ExecutionModel model : modelList)
			{
				
					String restResponse = new String( getUrl( new URL( cloudUrl + "/services/executions/" + model.getId() + "?operation=status&user=" + userName + "&password=" + password + "&responseFormat=xml" ) ) );
					ExecutionProgressModel pModel = new ExecutionProgressModel( restResponse );
	
					//
					// If one test is still running then flag it
					//
					if (!pModel.isCompleted())
					{
						testCount++;
						testRunning = true;
					}
					else
					{
						if ( !model.getStepResult().isComplete() )
						{
							model.getStepResult().setEndTime( System.currentTimeMillis() );
							model.getStepResult().setState( pModel.isSuccess() ? PASSED : FAILED );
						}
						
						//
						// Check for a failed test and flag that
						//
						if (!pModel.isSuccess())
							testPassed = false;
					}
					

					if ( pModel.isCompleted() && pModel.getProgress() < 100 )
						progressTotal += ( progressDivider );
					else
						progressTotal += ( ( pModel.getProgress() / 100.0 ) * progressDivider );


					
				
			}

			adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
			adapterRequest.getRequestHeaders().addHeader( sessionId );
			setStatus( adapterRequest, taskUrl, 10 + ( int ) progressTotal, "There are [" + testCount + "] tests remaining...", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );
			try
			{
				Thread.sleep( 5000 );
			}
			catch (Exception e)
			{
			}

		}

		int pointsFailed = 0;
		for (ExecutionModel model : modelList)
		{
			String restResponse = new String( getUrl( new URL( cloudUrl + "/services/executions/" + model.getId() + "?operation=status&user=" + userName + "&password=" + password + "&responseFormat=xml" ) ) );
			ExecutionProgressModel pModel = new ExecutionProgressModel( restResponse );

			//
			// Check for a failed test and flag that
			//
			if (!pModel.isSuccess())
				pointsFailed++;
		}

		long endTime = System.currentTimeMillis();

		//
		// Cycle Through and download all of the reports
		//
		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 95, "Packaging Up Reports", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );

		List<Attachment> aList = new ArrayList<Attachment>( modelList.size() );

		for (ExecutionModel model : modelList)
		{
			URL downloadUrl = new URL( cloudUrl + "/services/reports/" + model.getKey() + "?operation=download&user=" + userName + "&password=" + password + "&responseFormat=xml&format=html" );
			if (log.isDebugEnabled())
				log.debug( "Downloading report from [" + downloadUrl.toString() + "]" );

			byte[] responseData = getUrl( downloadUrl );

			if (responseData != null)
			{
				File outputFile = File.createTempFile( "report", ".html", new File( attachmentRoot ) );

				if (log.isDebugEnabled())
					log.debug( "Writing report to [" + outputFile.getAbsolutePath() + "]" );

				FileOutputStream outputStream = new FileOutputStream( outputFile );
				outputStream.write( responseData, 0, responseData.length );
				outputStream.flush();
				outputStream.close();

				Attachment attach = new Attachment( "test/html", outputFile.getAbsolutePath() );
				
				model.getStepResult().setAttachment( attach );
				
				aList.add( attach );
			}
		}

		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 97, "Reports Packaged - Uploading to RQM", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );

		//
		// Cycle through all of the reports and upload them as an attachment
		//
		for (Attachment a : aList)
		{
			try
			{
				URL uploadUrl = new URL( rqmUrl + "secure/service/com.ibm.rqm.planning.service.internal.rest.IAttachmentRestService?projectId=" + projectArea.getId() );
				adapterRequest = new HttpRequest( POST, uploadUrl.getPath() );
				adapterRequest.getRequestHeaders().addHeader( sessionId );
				adapterRequest.getRequestHeaders().addHeader( "OSLC-Core-Version", "2.0" );
	
				httpResponse = uploadAttachment( uploadUrl.toString(), adapterRequest, a, a.getContentType(), projectArea.getId() );
	
				String rqmContent = new String( httpResponse.getresponseBody() );
				if (log.isDebugEnabled())
					log.debug( "Response from Attachment Upload [" + rqmContent + "]" );
	
				if (rqmContent != null && !rqmContent.isEmpty())
				{
					if (rqmContent.startsWith( ATTACH_BEGIN ))
					{
						if (log.isDebugEnabled())
							log.debug( "Response from Attachment Upload [" + rqmContent + "]" );
						rqmContent = rqmContent.substring( ATTACH_BEGIN.length(), rqmContent.indexOf( ATTACH_END ) );
						String[] attachData = rqmContent.split( COMMA );
						if (attachData.length == 3)
							a.setId( attachData[0] );
						else
							log.warn( "Attachment result parameters were in an invalid format" );
					}
					else
						log.warn( "Attachment result was in an invalid format" );
				}
				else
					log.warn( "Attachment result was in an invalid format" );
			
			}
			catch( Exception e )
			{
				log.warn( "Error uploading attachments", e );
			}
		}

		//
		// Now post our result
		//
		Results runResults = new Results();
		runResults.setStartTime( startTime );
		runResults.setEndTime( endTime );
		runResults.setStatus( testPassed ? PASSED : FAILED );
		runResults.setPointsFailed( pointsFailed );
		runResults.setPointsAttempted( modelList.size() );
		runResults.getAttachments().addAll( aList );
		runResults.setAdapterId( adapterId );
		runResults.setTitle( tModel.getTitle() );
		runResults.setTestCase( wModel.getTestCase() );
		runResults.setTestScript( wModel.getRemoteScript() );
		runResults.setExecutionWorkItem( tModel.getWorkItem() );
		runResults.setRqmUrl( rqmUrl );
		runResults.addExecutionVariable( "scriptList", tModel.getScriptList() );
		runResults.addExecutionVariable( "deviceList", tModel.getDeviceList() );
		for (ExecutionModel model : modelList)
			runResults.addStep( model.getStepResult() );
		
		
		URL resultUrl = new URL( tModel.getResultUrl() );
		adapterRequest = new HttpRequest( POST, resultUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		adapterRequest.setRequestBody( runResults.toString().getBytes() );
		httpResponse = doPut( resultUrl.toString(), adapterRequest );

		//
		// Update our task with the status
		//
		if ( httpResponse.getresponseDefinition().getCode() == 201 )
		{
			ResultModel rModel = new ResultModel( new String( httpResponse.getresponseBody() ) );
			
			Result rBean = new Result( rModel.getId(), rModel.getItemId() );
			
			URL taskUpdateUrl = new URL( tModel.getUpdateUrl() );
			adapterRequest = new HttpRequest( PUT, taskUpdateUrl.getPath() );
			adapterRequest.getRequestHeaders().addHeader( sessionId );
			adapterRequest.setRequestBody( rBean.toString().getBytes() );
			httpResponse = doPut( taskUpdateUrl.toString(), adapterRequest );
			
			if ( httpResponse.getresponseDefinition().getCode() != 200 )
				log.warn( "Could not update the result with " + httpResponse.getresponseDefinition().getCode() );
		}
		
		adapterRequest = new HttpRequest( PUT, taskUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		setStatus( adapterRequest, taskUrl, 100, "Executed [" + modelList.size() + "] tests", cloudUrl + "/nexperience/dashboard.jsp?autowatch=true" );
		
		return true;
	}

	@Override
	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
