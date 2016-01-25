package com.perfectoMobile.page.keyWord;

/**
 * The Class KeyWordToken.
 */
public class KeyWordToken
{

	/**
	 * The Enum ParameterType.
	 */
	public enum TokenType
	{

		/** The context. */
		CONTEXT,
		/** The property. */
		PROPERTY,
		/** The static. */
		STATIC,
		/** The data. */
		
		CONTENT, 
		DATA;
	}

	private TokenType type;
	private String value;
	private String name;

	/**
	 * Instantiates a new key word parameter.
	 *
	 * @param type            the type
	 * @param value            the value
	 * @param name the name
	 */
	public KeyWordToken( TokenType type, String value, String name )
	{
		super();
		this.type = type;
		this.value = value;
		this.name = name;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public TokenType getType()
	{
		return type;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
}
