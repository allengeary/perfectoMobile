package com.perfectomobile.integration.rqm.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Results
{
	private DateFormat dateFormat = new SimpleDateFormat( "yyyy.MM.dd G 'at' HH:mm:ss z" );
	private DateFormat smallDate = new SimpleDateFormat( "MM/dd h:mm a" );
	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String EQUALS = "=";
	private static final String QUOTE = "\"";
	private static final String QM_TASK = "qmtask";
	private static final String QM_RESULT = "qmresult";
	private static final String QM_ADAPTER = "qmadapter";
	private static final String EXEC_RESULT = "executionresult";
	private static final String QM = "qm";
	private static final String ALM = "alm";
	private static final String STATE = "state";
	private static final String ADAPTER_ID = "adapterId";
	private static final String MACHINE = "machine";
	private static final String VARIABLE = "variable";
	private static final String VARIABLES = "variables";
	private static final String PROPERTIES = "properties";
	private static final String ATTACHMENT = "attachment";

	private static final String STARTTIME = "starttime";
	private static final String ENDTIME = "endtime";
	private static final String ATTEMPTED = "pointsattempted";
	private static final String PASSED = "pointspassed";
	private static final String FAILED = "pointsfailed";
	private static final String BLOCKED = "pointsblocked";
	private static final String INCONCLUSIVE = "pointsinconclusive";
	private static final String REMOTE = "remoteexecution";
	private static final String WORK_ITEM = "executionworkitem";
	private static final String TEST_CASE = "testcase";
	private static final String TEST_SCRIPT = "testscript";
	private static final String TITLE = "title";
	private static final String DETAIL = "details";

	private static final String RESULT_ID = "resultId";
	private static final String STEPS = "stepResults";
	private static final String OWNER = "owner";

	private static final String NAME = "name";
	private static final String VALUE = "value";
	private static final String HREF = " href";
	private static final String PREFIX = "com.ibm.rqm.execution.common.state.";

	private static final String DIV = "div";
	private static final String OL = "ol";
	private static final String LI = "li";
	private static final String B = "b";
	private static final String H = "h";

	private List<Attachment> attachments = new ArrayList<Attachment>();
	private List<StepResult> steps = new ArrayList<StepResult>();
	private List<RQMProperty> propertyList;
	private String[] defectLinks;
	private long startTime;
	private long endTime;
	private String machine;
	private int pointsAttempted;
	private int pointsBlocked;
	private int pointsFailed;
	private int pointsInconclusive;
	private int pointsPassed;
	private String status;
	private Map<String, Object> executionVariables;
	private String resultId;
	private String owner;
	private String rqmUrl;
	private String adapterId;
	private String title;
	private String testCase;
	private String testScript;
	private String executionWorkItem;

	public String getExecutionWorkItem()
	{
		return executionWorkItem;
	}

	public void setExecutionWorkItem( String executionWorkItem )
	{
		this.executionWorkItem = executionWorkItem;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getTestCase()
	{
		return testCase;
	}

	public void setTestCase( String testCase )
	{
		this.testCase = testCase;
	}

	public String getTestScript()
	{
		return testScript;
	}

	public void setTestScript( String testScript )
	{
		this.testScript = testScript;
	}

	public void addStep( StepResult stepResult )
	{
		if (steps == null)
			steps = new ArrayList<StepResult>( 10 );

		steps.add( stepResult );
	}

	public void addExecutionVariable( String variableName, Object variableValue )
	{
		if (executionVariables == null)
			executionVariables = new HashMap<String, Object>( 10 );

		executionVariables.put( variableName, variableValue );
	}

	public void addProperty( String nameSpace, String nameSpaceUrl, String name, String value )
	{
		if (propertyList == null)
			propertyList = new ArrayList<RQMProperty>( 20 );

		propertyList.add( new RQMProperty( name, value, nameSpaceUrl, nameSpace ) );
	}

	public String toString()
	{
		StringBuilder stringData = new StringBuilder();

		stringData.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" );
		stringData.append(
				"<qm:executionresult xmlns:h=\"http://www.w3.org/1999/xhtml\" xmlns:alm=\"http://jazz.net/xmlns/alm/v0.1/\" xmlns:qm=\"http://jazz.net/xmlns/alm/qm/v0.1/\" xmlns:qmtask=\"http://jazz.net/xmlns/alm/qm/qmadapter/task/v0.1\" xmlns:qmresult=\"http://jazz.net/xmlns/alm/qm/v0.1/executionresult/v0.1\" xmlns:qmadapter=\"http://jazz.net/xmlns/alm/qm/qmadapter/v0.1\" >" );

		if (status != null)
			addTaggedValue( stringData, ALM, STATE, PREFIX + status.toLowerCase() );

		if (machine != null)
			addTaggedValue( stringData, ALM, MACHINE, machine );

		addTaggedValue( stringData, QM_RESULT, STARTTIME, startTime + "" );
		addTaggedValue( stringData, QM_RESULT, ENDTIME, endTime + "" );
		addTaggedValue( stringData, QM_RESULT, ATTEMPTED, pointsAttempted + "" );
		addTaggedValue( stringData, QM_RESULT, PASSED, ( pointsAttempted - pointsFailed ) + "" );
		addTaggedValue( stringData, QM_RESULT, FAILED, pointsFailed + "" );
		addTaggedValue( stringData, QM_RESULT, BLOCKED, pointsBlocked + "" );
		addTaggedValue( stringData, QM_RESULT, INCONCLUSIVE, pointsInconclusive + "" );
		addTaggedValue( stringData, QM_RESULT, REMOTE, "true" );

		if (resultId != null)
			addTaggedValue( stringData, QM_RESULT, RESULT_ID, resultId );

		if (owner != null)
			addTaggedValue( stringData, ALM, OWNER, owner );

		if (!attachments.isEmpty())
		{
			for (Attachment attachment : attachments)
			{
				stringData.append( LEFT );
				stringData.append( QM ).append( COLON ).append( ATTACHMENT ).append( HREF ).append( EQUALS );
				stringData.append( QUOTE ).append( rqmUrl ).append( "/secure/service/com.ibm.rqm.planning.service.internal.rest.IAttachmentRestService/" ).append( attachment.getId() ).append( QUOTE ).append( SLASH ).append( RIGHT );
			}
		}

		stringData.append( LEFT );
		stringData.append( QM ).append( COLON ).append( TEST_CASE ).append( HREF ).append( EQUALS );
		stringData.append( QUOTE ).append( testCase ).append( QUOTE ).append( SLASH ).append( RIGHT );

		stringData.append( LEFT );
		stringData.append( QM ).append( COLON ).append( TEST_SCRIPT ).append( HREF ).append( EQUALS );
		stringData.append( QUOTE ).append( testScript ).append( QUOTE ).append( SLASH ).append( RIGHT );

		addTaggedValue( stringData, QM, TITLE, title + "(" + smallDate.format( new Date( startTime ) ) + ")" );

		stringData.append( LEFT );
		stringData.append( QM ).append( COLON ).append( WORK_ITEM ).append( HREF ).append( EQUALS );
		stringData.append( QUOTE ).append( executionWorkItem ).append( QUOTE ).append( SLASH ).append( RIGHT );

		if (executionVariables != null && !executionVariables.isEmpty())
		{
			addTag( stringData, QM, VARIABLES, false );
			for (String keyName : executionVariables.keySet())
			{
				addTag( stringData, QM, VARIABLE, false );
				addTaggedValue( stringData, QM, NAME, keyName );
				addTaggedValue( stringData, QM, VALUE, executionVariables.get( keyName ).toString() );
				addTag( stringData, QM, VARIABLE, true );
			}
			addTag( stringData, QM, VARIABLES, true );
		}

		if (propertyList != null && !propertyList.isEmpty())
		{
			addTag( stringData, QM_TASK, PROPERTIES, false );
			for (RQMProperty keyName : propertyList)
				stringData.append( keyName.toXMLString() );
			addTag( stringData, QM_TASK, PROPERTIES, true );
		}

		if (steps != null && !steps.isEmpty())
		{
			addTag( stringData, QM_RESULT, STEPS, false );
			for (StepResult step : steps)
				stringData.append( step.toString() );
			addTag( stringData, QM_RESULT, STEPS, true );
		}

		addTaggedValue( stringData, QM_RESULT, DETAIL, toDetail() );
		addTaggedValue( stringData, QM_ADAPTER, ADAPTER_ID, adapterId );

		addTag( stringData, QM, EXEC_RESULT, true );

		return stringData.toString();

	}

	private void addTag( StringBuilder stringBuilder, String nameSpace, String tagName, boolean endTag )
	{
		stringBuilder.append( LEFT );
		if (endTag)
			stringBuilder.append( SLASH );
		stringBuilder.append( nameSpace ).append( COLON ).append( tagName ).append( RIGHT );
	}

	private void addTaggedValue( StringBuilder stringBuilder, String nameSpace, String tagName, String value )
	{
		addTag( stringBuilder, nameSpace, tagName, false );
		stringBuilder.append( value );
		addTag( stringBuilder, nameSpace, tagName, true );
	}

	public List<RQMProperty> getPropertyList()
	{
		return propertyList;
	}

	public void setPropertyList( List<RQMProperty> propertyList )
	{
		this.propertyList = propertyList;
	}

	public String[] getDefectLinks()
	{
		return defectLinks;
	}

	public void setDefectLinks( String[] defectLinks )
	{
		this.defectLinks = defectLinks;
	}

	public long getStartTime()
	{
		return startTime;
	}

	public void setStartTime( long startTime )
	{
		this.startTime = startTime;
	}

	public long getEndTime()
	{
		return endTime;
	}

	public void setEndTime( long endTime )
	{
		this.endTime = endTime;
	}

	public String getMachine()
	{
		return machine;
	}

	public void setMachine( String machine )
	{
		this.machine = machine;
	}

	public int getPointsAttempted()
	{
		return pointsAttempted;
	}

	public void setPointsAttempted( int pointsAttempted )
	{
		this.pointsAttempted = pointsAttempted;
	}

	public int getPointsBlocked()
	{
		return pointsBlocked;
	}

	public void setPointsBlocked( int pointsBlocked )
	{
		this.pointsBlocked = pointsBlocked;
	}

	public int getPointsFailed()
	{
		return pointsFailed;
	}

	public void setPointsFailed( int pointsFailed )
	{
		this.pointsFailed = pointsFailed;
	}

	public int getPointsInconclusive()
	{
		return pointsInconclusive;
	}

	public void setPointsInconclusive( int pointsInconclusive )
	{
		this.pointsInconclusive = pointsInconclusive;
	}

	public int getPointsPassed()
	{
		return pointsPassed;
	}

	public void setPointsPassed( int pointsPassed )
	{
		this.pointsPassed = pointsPassed;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	public Map<String, Object> getExecutionVariables()
	{
		return executionVariables;
	}

	public void setExecutionVariables( Map<String, Object> executionVariables )
	{
		this.executionVariables = executionVariables;
	}

	public String getResultId()
	{
		return resultId;
	}

	public void setResultId( String resultId )
	{
		this.resultId = resultId;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner( String owner )
	{
		this.owner = owner;
	}

	public String getRqmUrl()
	{
		return rqmUrl;
	}

	public void setRqmUrl( String rqmUrl )
	{
		this.rqmUrl = rqmUrl;
	}

	public String getAdapterId()
	{
		return adapterId;
	}

	public void setAdapterId( String adapterId )
	{
		this.adapterId = adapterId;
	}

	public List<Attachment> getAttachments()
	{
		return attachments;
	}

	private String toDetail()
	{
		StringBuilder builder = new StringBuilder();

		builder.append( "<div><b>Device Executions</b><br/><ol>" );
		for (StepResult step : steps)
		{
			String fontColor = step.getState().equals( "PASSED" ) ? "green" : "red";
			builder.append( "<li>" ).append( step.getScriptName() ).append( ": " );
			builder.append( "<b><a target=\"_blank\" hRef=\"" ).append( rqmUrl ).append( "/secure/service/com.ibm.rqm.planning.service.internal.rest.IAttachmentRestService/" ).append( step.getAttachment().getId() ).append( "\">" ).append( step.getManufacturer() ).append( " " ).append( step.getModel() ).append( "(" ).append( step.getDeviceName() ).append( ")" ).append( "</a></b><br/><ol>" );
			builder.append( "<li>Started: " ).append( dateFormat.format( new Date( step.getStartTime() ) ) ).append( "</li>");
			builder.append( "<li>Elapsed Time (s): " ).append( ( step.getEndTime() - step.getStartTime() ) / 1000 ).append( "</li>");
			builder.append( "<li>Status: <font color=\"" ).append( fontColor ).append( "\"><b>" ).append( step.getState() ).append( "</b></font></li></ol></li>");

			builder.append( "<br/>" );
		}
		builder.append( "</ol></div>" );
		return builder.toString();
	}

}
