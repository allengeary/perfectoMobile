package com.perfectoMobile.page.data;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The default page data instance.
 */
public class DefaultPageData implements PageData
{
	
	/** The record map. */
	public Map<String, String> recordMap = new HashMap<String, String>( 10 );

	/** The type name. */
	private String typeName;
	
	/** The record name. */
	private String recordName;
	
	/** The active. */
	private boolean active;

	/**
	 * Instantiates a new default page data.
	 *
	 * @param typeName the type name
	 * @param recordName the record name
	 * @param active the active
	 */
	public DefaultPageData( String typeName, String recordName, boolean active )
	{
		this.typeName = typeName;
		this.recordName = recordName;
		this.active = active;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.PageData#getName()
	 */
	public String getName()
	{
		return recordName;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.PageData#getData(java.lang.String)
	 */
	@Override
	public String getData( String fieldName )
	{
		return recordMap.get( fieldName );
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.PageData#getType()
	 */
	@Override
	public String getType()
	{
		return typeName;
	}

	/**
	 * Adds the value.
	 *
	 * @param fieldName the field name
	 * @param value the value
	 */
	public void addValue( String fieldName, String value )
	{
		recordMap.put( fieldName, value );
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.PageData#isActive()
	 */
	@Override
	public boolean isActive()
	{
		return active;
	}

}
