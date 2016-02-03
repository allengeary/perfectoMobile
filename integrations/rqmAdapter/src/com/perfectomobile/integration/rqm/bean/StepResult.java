<<<<<<< HEAD
package com.perfectomobile.integration.rqm.bean;

public class StepResult
{

	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String EQUALS = "=";
	private static final String QUOTE = "\"";
	private static final String QM_RESULT = "qmresult";
	private static final String QM_STEP = "stepResult";
	private static final String RESULT = "result";
	private static final String ACTUAL = "actualResult";
	private static final String STARTTIME = "startTime";
	private static final String ENDTIME = "endTime";
	private static final String DESC = "description";
	private static final String TYPE = "stepType";
	private static final String ATTACH = "stepAttachment";
	private static final String HREF = " href";
	private static final String PREFIX = "com.ibm.rqm.execution.common.state.";

	private long startTime;
	private long endTime = -1;
	private String state;
	private String scriptName;
	private String deviceName;
	private Attachment attachment;
	private String model;
	private String manufacturer;
	

	public String getModel()
	{
		return model;
	}



	public void setModel( String model )
	{
		this.model = model;
	}



	public String getManufacturer()
	{
		return manufacturer;
	}



	public void setManufacturer( String manufacturer )
	{
		this.manufacturer = manufacturer;
	}



	public StepResult( long startTime, String scriptName, String deviceName )
	{
		this.startTime = startTime;
		this.scriptName = scriptName;
		this.deviceName = deviceName;
	}

	
	
	public Attachment getAttachment()
	{
		return attachment;
	}



	public void setAttachment( Attachment attachment )
	{
		this.attachment = attachment;
	}



	public boolean isComplete()
	{
		return endTime > -1;
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

	public String getState()
	{
		return state;
	}

	public void setState( String state )
	{
		this.state = state;
	}

	public String getScriptName()
	{
		return scriptName;
	}

	public void setScriptName( String scriptName )
	{
		this.scriptName = scriptName;
	}

	public String getDeviceName()
	{
		return deviceName;
	}

	public void setDeviceName( String deviceName )
	{
		this.deviceName = deviceName;
	}

	public String toString()
	{
		StringBuilder stringData = new StringBuilder();

		
		addTag( stringData, QM_RESULT, QM_STEP, false );
		addTaggedValue( stringData, QM_RESULT, ACTUAL, "Actual Result" );
		addTaggedValue( stringData, QM_RESULT, ENDTIME, endTime + "" );
		addTaggedValue( stringData, QM_RESULT, STARTTIME, startTime + "" );
		addTaggedValue( stringData, QM_RESULT, RESULT, PREFIX + state.toLowerCase() );
		addTaggedValue( stringData, QM_RESULT, DESC, "Device: [" + deviceName + "] Script: [" + scriptName + "]" );
		addTaggedValue( stringData, QM_RESULT, TYPE, "Remote" );
		
		stringData.append( LEFT );
		stringData.append( QM_RESULT ).append( COLON ).append( ATTACH ).append( HREF ).append( EQUALS );
		stringData.append( QUOTE ).append( attachment.getId() ).append( QUOTE ).append( SLASH ).append( RIGHT );
		
		addTag( stringData, QM_RESULT, QM_STEP, true );
		

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

public class StepResult
{

	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String EQUALS = "=";
	private static final String QUOTE = "\"";
	private static final String QM_RESULT = "qmresult";
	private static final String QM_STEP = "stepResult";
	private static final String RESULT = "result";
	private static final String ACTUAL = "actualResult";
	private static final String STARTTIME = "startTime";
	private static final String ENDTIME = "endTime";
	private static final String DESC = "description";
	private static final String TYPE = "stepType";
	private static final String ATTACH = "stepAttachment";
	private static final String HREF = " href";
	private static final String PREFIX = "com.ibm.rqm.execution.common.state.";

	private long startTime;
	private long endTime = -1;
	private String state;
	private String scriptName;
	private String deviceName;
	private Attachment attachment;
	private String model;
	private String manufacturer;
	

	public String getModel()
	{
		return model;
	}



	public void setModel( String model )
	{
		this.model = model;
	}



	public String getManufacturer()
	{
		return manufacturer;
	}



	public void setManufacturer( String manufacturer )
	{
		this.manufacturer = manufacturer;
	}



	public StepResult( long startTime, String scriptName, String deviceName )
	{
		this.startTime = startTime;
		this.scriptName = scriptName;
		this.deviceName = deviceName;
	}

	
	
	public Attachment getAttachment()
	{
		return attachment;
	}



	public void setAttachment( Attachment attachment )
	{
		this.attachment = attachment;
	}



	public boolean isComplete()
	{
		return endTime > -1;
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

	public String getState()
	{
		return state;
	}

	public void setState( String state )
	{
		this.state = state;
	}

	public String getScriptName()
	{
		return scriptName;
	}

	public void setScriptName( String scriptName )
	{
		this.scriptName = scriptName;
	}

	public String getDeviceName()
	{
		return deviceName;
	}

	public void setDeviceName( String deviceName )
	{
		this.deviceName = deviceName;
	}

	public String toString()
	{
		StringBuilder stringData = new StringBuilder();

		
		addTag( stringData, QM_RESULT, QM_STEP, false );
		addTaggedValue( stringData, QM_RESULT, ACTUAL, "Actual Result" );
		addTaggedValue( stringData, QM_RESULT, ENDTIME, endTime + "" );
		addTaggedValue( stringData, QM_RESULT, STARTTIME, startTime + "" );
		addTaggedValue( stringData, QM_RESULT, RESULT, PREFIX + state.toLowerCase() );
		addTaggedValue( stringData, QM_RESULT, DESC, "Device: [" + deviceName + "] Script: [" + scriptName + "]" );
		addTaggedValue( stringData, QM_RESULT, TYPE, "Remote" );
		
		stringData.append( LEFT );
		stringData.append( QM_RESULT ).append( COLON ).append( ATTACH ).append( HREF ).append( EQUALS );
		stringData.append( QUOTE ).append( attachment.getId() ).append( QUOTE ).append( SLASH ).append( RIGHT );
		
		addTag( stringData, QM_RESULT, QM_STEP, true );
		

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
