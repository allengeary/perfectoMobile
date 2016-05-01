package com.perfectomobile.integration.rqm.bean;

public class RQMProperty
{
	
	private static final String LEFT = "<";
	private static final String RIGHT = ">";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	private static final String EQUALS = "=";
	private static final String QUOTE = "\"";
	private static final String SPACE = " ";
	private static final String QM_TASK = "qmtask";
	private static final String PROPERTY = "property";
	private static final String PROPERTY_NAME = "propertyName";
	private static final String PROPERTY_TYPE = "propertyType";
	
	private String name;
	private String value;
	private String url;
	private String prefix;
	
	public RQMProperty( String name, String value, String url, String prefix )
	{
		super();
		this.name = name;
		this.value = value;
		this.url = url;
		this.prefix = prefix;
	}
	public String getName()
	{
		return name;
	}
	public void setName( String name )
	{
		this.name = name;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue( String value )
	{
		this.value = value;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl( String url )
	{
		this.url = url;
	}
	public String getPrefix()
	{
		return prefix;
	}
	public void setPrefix( String prefix )
	{
		this.prefix = prefix;
	}
	
	public String toXMLString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( LEFT ).append( QM_TASK ).append( COLON ).append( PROPERTY );
		stringBuilder.append( SPACE ).append( PROPERTY_NAME ).append( EQUALS ).append( QUOTE ).append( name ).append( QUOTE );
		stringBuilder.append( SPACE ).append( PROPERTY_TYPE ).append( EQUALS ).append( QUOTE ).append( prefix ).append( COLON ).append( url ).append( QUOTE ).append( RIGHT );
		stringBuilder.append( value );
		stringBuilder.append( LEFT ).append( SLASH ).append( QM_TASK ).append( COLON ).append( PROPERTY ).append( RIGHT );
		
		return stringBuilder.toString();
	}
	
	
}
