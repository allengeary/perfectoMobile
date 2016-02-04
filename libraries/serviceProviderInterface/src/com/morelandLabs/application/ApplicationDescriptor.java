package com.morelandLabs.application;

import com.morelandLabs.spi.Device;

/**
 * The Class ApplicationDescriptor.
 */
public class ApplicationDescriptor
{
	private String name;
	private String description;
	private String androidIdentifier;
	private String appleIdentifier;
	private String url;
	private String iosInstallation;
	private String androidInstallation;


	/**
	 * Gets the ios installation.
	 *
	 * @return the ios installation
	 */
	public String getIosInstallation()
	{
		return iosInstallation;
	}

	/**
	 * Sets the ios installation.
	 *
	 * @param iosInstallation the new ios installation
	 */
	public void setIosInstallation( String iosInstallation )
	{
		this.iosInstallation = iosInstallation;
	}

	/**
	 * Gets the android installation.
	 *
	 * @return the android installation
	 */
	public String getAndroidInstallation()
	{
		return androidInstallation;
	}

	/**
	 * Sets the android installation.
	 *
	 * @param androidInstallation the new android installation
	 */
	public void setAndroidInstallation( String androidInstallation )
	{
		this.androidInstallation = androidInstallation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		if ( url != null && !url.isEmpty() )
		{
			if ( (androidIdentifier != null && !androidIdentifier.isEmpty() ) || (appleIdentifier != null && !appleIdentifier.isEmpty() ) )
				return name + " (HYBRID)";
			else
				return name + " (WEB)";
		}
		else
		{
			if ( (androidIdentifier != null && !androidIdentifier.isEmpty() ) || (appleIdentifier != null && !appleIdentifier.isEmpty() ) )
				return name + " (NATIVE)";
			else
				return name + " (UNKNOWN)";
		}
	}
	
	/**
	 * Instantiates a new application descriptor.
	 *
	 * @param name the name
	 * @param description the description
	 * @param androidIdentifier the android identifier
	 * @param appleIdentifier the apple identifier
	 * @param url the url
	 * @param iosInstallation the ios installation
	 * @param androidInstallation the android installation
	 */
	public ApplicationDescriptor( String name, String description, String androidIdentifier, String appleIdentifier, String url, String iosInstallation, String androidInstallation )
	{
		super();
		this.name = name;
		this.description = description;
		this.androidIdentifier = androidIdentifier;
		this.appleIdentifier = appleIdentifier;
		this.url = url;
		this.iosInstallation = iosInstallation;
		this.androidInstallation = androidInstallation;
	}
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}
	
	

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl( String url )
	{
		this.url = url;
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
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName( String name )
	{
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}
	
	/**
	 * Gets the android identifier.
	 *
	 * @return the android identifier
	 */
	public String getAndroidIdentifier()
	{
		return androidIdentifier;
	}
	
	/**
	 * Sets the android identifier.
	 *
	 * @param androidIdentifier the new android identifier
	 */
	public void setAndroidIdentifier( String androidIdentifier )
	{
		this.androidIdentifier = androidIdentifier;
	}
	
	/**
	 * Gets the apple identifier.
	 *
	 * @return the apple identifier
	 */
	public String getAppleIdentifier()
	{
		return appleIdentifier;
	}
	
	/**
	 * Sets the apple identifier.
	 *
	 * @param appleIdentifier the new apple identifier
	 */
	public void setAppleIdentifier( String appleIdentifier )
	{
		this.appleIdentifier = appleIdentifier;
	}
	
	/**
	 * Gets the identifier.
	 *
	 * @param deviceInformation the device information
	 * @return the identifier
	 */
	public String getIdentifier( Device deviceInformation )
	{
		switch( deviceInformation.getOs().toLowerCase() )
		{
			case "ios":
				return getAppleIdentifier();
				
			case "android":
				return getAndroidIdentifier();
				
			default:
				return null;
		}
		
	}
}
