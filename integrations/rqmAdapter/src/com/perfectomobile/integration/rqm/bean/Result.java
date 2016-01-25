<<<<<<< HEAD
package com.perfectomobile.integration.rqm.bean;

public class Result
{

	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String QM = "qm";
	private static final String QM_TASK = "qmtask";
	private static final String QM_ADAPTER_TASK = "adaptertask";
	
	private static final String RESULT_ID = "resultId";
	private static final String RESULT_ITEM_ID = "resultItemId";

	private String resultId;
	private String resultItemId;

	

	public String toString()
	{
		StringBuilder stringData = new StringBuilder();

		stringData.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" );
		stringData.append( "<qm:adaptertask xmlns:qm=\"http://jazz.net/xmlns/alm/qm/v0.1/\" xmlns:qmtask=\"http://jazz.net/xmlns/alm/qm/qmadapter/task/v0.1\" >" );

		addTaggedValue( stringData, QM_TASK, RESULT_ID, resultId );
		addTaggedValue( stringData, QM_TASK, RESULT_ITEM_ID, resultItemId );

		addTag( stringData, QM, QM_ADAPTER_TASK, true );

		return stringData.toString();

	}

	
	
	public Result( String resultId, String resultItemId )
	{
		super();
		this.resultId = resultId;
		this.resultItemId = resultItemId;
	}



	public String getResultId()
	{
		return resultId;
	}



	public void setResultId( String resultId )
	{
		this.resultId = resultId;
	}



	public String getResultItemId()
	{
		return resultItemId;
	}



	public void setResultItemId( String resultItemId )
	{
		this.resultItemId = resultItemId;
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

public class Result
{

	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String QM = "qm";
	private static final String QM_TASK = "qmtask";
	private static final String QM_ADAPTER_TASK = "adaptertask";
	
	private static final String RESULT_ID = "resultId";
	private static final String RESULT_ITEM_ID = "resultItemId";

	private String resultId;
	private String resultItemId;

	

	public String toString()
	{
		StringBuilder stringData = new StringBuilder();

		stringData.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" );
		stringData.append( "<qm:adaptertask xmlns:qm=\"http://jazz.net/xmlns/alm/qm/v0.1/\" xmlns:qmtask=\"http://jazz.net/xmlns/alm/qm/qmadapter/task/v0.1\" >" );

		addTaggedValue( stringData, QM_TASK, RESULT_ID, resultId );
		addTaggedValue( stringData, QM_TASK, RESULT_ITEM_ID, resultItemId );

		addTag( stringData, QM, QM_ADAPTER_TASK, true );

		return stringData.toString();

	}

	
	
	public Result( String resultId, String resultItemId )
	{
		super();
		this.resultId = resultId;
		this.resultItemId = resultItemId;
	}



	public String getResultId()
	{
		return resultId;
	}



	public void setResultId( String resultId )
	{
		this.resultId = resultId;
	}



	public String getResultItemId()
	{
		return resultItemId;
	}



	public void setResultItemId( String resultItemId )
	{
		this.resultItemId = resultItemId;
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
