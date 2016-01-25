<<<<<<< HEAD
package com.perfectomobile.integration.rqm.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Progress
{
	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String QM_TASK = "qmtask";
	private static final String ADAPTER_TASK = "adaptertask";
	private static final String QM = "qm";
	private static final String PROGRESS = "progress";
	private static final String TAKEN = "taken";
	private static final String ALM = "alm";
	private static final String STATE = "state";
	private static final String COMPLETE = "com.ibm.rqm.executionframework.common.requeststate.complete";
	private static final String CODE = "statusCode";
	private static final String MESSAGE = "message";
	private static final String DATA = "data";
	private static final String VARIABLE = "variable";
	private static final String VARIABLES = "variables";
	private static final String PROPERTIES = "properties";
	
	private static final String NAME = "name";
	private static final String VALUE = "value";

	private int progressLevel = 0;
	private int code = 0;
	private String message = null;
	private String payLoad = null;
	private Map<String, Object> executionVariables;
	private List<RQMProperty> propertyList;
	private Boolean taken = null;

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

	public int getProgressLevel()
	{
		return progressLevel;
	}

	public void setProgressLevel( int progressLevel )
	{
		this.progressLevel = progressLevel;
	}
	
	public void setTaken( boolean taken )
	{
		this.taken = taken;
	}
	
	public Boolean isTaken()
	{
		return taken;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode( int code )
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage( String message )
	{
		this.message = message;
	}

	public String getPayLoad()
	{
		return payLoad;
	}

	public void setPayLoad( String payLoad )
	{
		this.payLoad = payLoad;
	}

	public Map<String, Object> getExecutionVariables()
	{
		return executionVariables;
	}

	public void setExecutionVariables( Map<String, Object> executionVariables )
	{
		this.executionVariables = executionVariables;
	}

	public String toString()
	{
		StringBuilder stringData = new StringBuilder();

		stringData.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" );
		stringData.append( "<qm:adaptertask xmlns:alm=\"http://jazz.net/xmlns/alm/v0.1/\" xmlns:qm=\"http://jazz.net/xmlns/alm/qm/v0.1/\" xmlns:qmtask=\"http://jazz.net/xmlns/alm/qm/qmadapter/task/v0.1\">" );

		if (progressLevel > 0)
		{
			addTaggedValue( stringData, QM_TASK, PROGRESS, progressLevel + "" );

			if (progressLevel >= 100)
				addTaggedValue( stringData, ALM, STATE, COMPLETE );
		}

		if (code > 0)
		{
			addTaggedValue( stringData, QM_TASK, CODE, code + "" );

			if (message != null && !message.isEmpty())
				addTaggedValue( stringData, QM_TASK, MESSAGE, message );
		}

		if (payLoad != null)
			addTaggedValue( stringData, QM_TASK, DATA, payLoad );
		
		if (taken != null)
			addTaggedValue( stringData, QM_TASK, TAKEN, taken.toString() );

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
		
		addTag( stringData, QM, ADAPTER_TASK, true );
		
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
}
=======
package com.perfectomobile.integration.rqm.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Progress
{
	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String QM_TASK = "qmtask";
	private static final String ADAPTER_TASK = "adaptertask";
	private static final String QM = "qm";
	private static final String PROGRESS = "progress";
	private static final String TAKEN = "taken";
	private static final String ALM = "alm";
	private static final String STATE = "state";
	private static final String COMPLETE = "com.ibm.rqm.executionframework.common.requeststate.complete";
	private static final String CODE = "statusCode";
	private static final String MESSAGE = "message";
	private static final String DATA = "data";
	private static final String VARIABLE = "variable";
	private static final String VARIABLES = "variables";
	private static final String PROPERTIES = "properties";
	
	private static final String NAME = "name";
	private static final String VALUE = "value";

	private int progressLevel = 0;
	private int code = 0;
	private String message = null;
	private String payLoad = null;
	private Map<String, Object> executionVariables;
	private List<RQMProperty> propertyList;
	private Boolean taken = null;

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

	public int getProgressLevel()
	{
		return progressLevel;
	}

	public void setProgressLevel( int progressLevel )
	{
		this.progressLevel = progressLevel;
	}
	
	public void setTaken( boolean taken )
	{
		this.taken = taken;
	}
	
	public Boolean isTaken()
	{
		return taken;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode( int code )
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage( String message )
	{
		this.message = message;
	}

	public String getPayLoad()
	{
		return payLoad;
	}

	public void setPayLoad( String payLoad )
	{
		this.payLoad = payLoad;
	}

	public Map<String, Object> getExecutionVariables()
	{
		return executionVariables;
	}

	public void setExecutionVariables( Map<String, Object> executionVariables )
	{
		this.executionVariables = executionVariables;
	}

	public String toString()
	{
		StringBuilder stringData = new StringBuilder();

		stringData.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" );
		stringData.append( "<qm:adaptertask xmlns:alm=\"http://jazz.net/xmlns/alm/v0.1/\" xmlns:qm=\"http://jazz.net/xmlns/alm/qm/v0.1/\" xmlns:qmtask=\"http://jazz.net/xmlns/alm/qm/qmadapter/task/v0.1\">" );

		if (progressLevel > 0)
		{
			addTaggedValue( stringData, QM_TASK, PROGRESS, progressLevel + "" );

			if (progressLevel >= 100)
				addTaggedValue( stringData, ALM, STATE, COMPLETE );
		}

		if (code > 0)
		{
			addTaggedValue( stringData, QM_TASK, CODE, code + "" );

			if (message != null && !message.isEmpty())
				addTaggedValue( stringData, QM_TASK, MESSAGE, message );
		}

		if (payLoad != null)
			addTaggedValue( stringData, QM_TASK, DATA, payLoad );
		
		if (taken != null)
			addTaggedValue( stringData, QM_TASK, TAKEN, taken.toString() );

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
		
		addTag( stringData, QM, ADAPTER_TASK, true );
		
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
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
