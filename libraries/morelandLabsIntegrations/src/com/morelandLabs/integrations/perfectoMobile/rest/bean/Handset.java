package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

/**
 * The Class Handset.
 */
@BeanDescriptor( beanName="handset" )
public class Handset extends AbstractBean
{
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Handset [deviceId=" + deviceId + ", manufacturer=" + manufacturer + ", model=" + model + ", description=" + description + ", firmware=" + firmware + ", wifiMacAddress=" + wifiMacAddress + ", link=" + link + ", operatorName=" + operatorName + ", operatorCountry=" + operatorCountry + ", operatorCode="
				+ operatorCode + ", phoneNumber=" + phoneNumber + ", location=" + location + ", lastCradleId=" + lastCradleId + ", language=" + language + ", status=" + status + ", mode=" + mode + ", reserved=" + reserved + ", inUse=" + inUse + ", operabilityRating=" + operabilityRating + ", cradleId=" + cradleId
				+ ", positionId=" + positionId + ", positionMethod=" + positionMethod + ", positionRotation=" + positionRotation + ", os=" + os + ", osVersion=" + osVersion + ", resolution=" + resolution + "]";
	}

	@FieldDescriptor ( fieldPath = "deviceId")
	private String deviceId;

	@FieldDescriptor ( )
	private String manufacturer;
	@FieldDescriptor ( )
	private String model;
	
	@FieldDescriptor ( )
	private String description;
	@FieldDescriptor ( )
	private String firmware;
	@FieldDescriptor ( )
	private String wifiMacAddress;

	@FieldDescriptor ( fieldPath = "link/type")
	private String link;

	@FieldDescriptor ( fieldPath = "operator/name")
	private String operatorName;

	@FieldDescriptor ( fieldPath = "operator/country")
	private String operatorCountry;

	@FieldDescriptor ( fieldPath = "operator/code")
	private String operatorCode;
	@FieldDescriptor ( )
	private String phoneNumber;
	@FieldDescriptor ( )
	private String location;
	@FieldDescriptor ( )
	private String lastCradleId;
	@FieldDescriptor ( )
	private String language;
	@FieldDescriptor ( )
	private String status;
	@FieldDescriptor ( )
	private String mode;
	@FieldDescriptor ( )
	private Boolean reserved;
	@FieldDescriptor ( )
	private Boolean inUse;

	@FieldDescriptor ( fieldPath = "operabilityRating/score")
	private Double operabilityRating;
	@FieldDescriptor ( )
	private String cradleId;

	@FieldDescriptor ( fieldPath = "position/id")
	private String positionId;

	@FieldDescriptor ( fieldPath = "position/method")
	private String positionMethod;

	@FieldDescriptor ( fieldPath = "position/rotation")
	private Double positionRotation;
	@FieldDescriptor ( )
	private String os;

	private String osVersion;

	private String resolution;
	
	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId()
	{
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId( String deviceId )
	{
		this.deviceId = deviceId;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public String getManufacturer()
	{
		return manufacturer;
	}

	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer the new manufacturer
	 */
	public void setManufacturer( String manufacturer )
	{
		this.manufacturer = manufacturer;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel()
	{
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel( String model )
	{
		this.model = model;
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
	 * Gets the firmware.
	 *
	 * @return the firmware
	 */
	public String getFirmware()
	{
		return firmware;
	}

	/**
	 * Sets the firmware.
	 *
	 * @param firmware the new firmware
	 */
	public void setFirmware( String firmware )
	{
		this.firmware = firmware;
	}

	/**
	 * Gets the wifi mac address.
	 *
	 * @return the wifi mac address
	 */
	public String getWifiMacAddress()
	{
		return wifiMacAddress;
	}

	/**
	 * Sets the wifi mac address.
	 *
	 * @param wifiMacAddress the new wifi mac address
	 */
	public void setWifiMacAddress( String wifiMacAddress )
	{
		this.wifiMacAddress = wifiMacAddress;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink()
	{
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link the new link
	 */
	public void setLink( String link )
	{
		this.link = link;
	}

	/**
	 * Gets the operator name.
	 *
	 * @return the operator name
	 */
	public String getOperatorName()
	{
		return operatorName;
	}

	/**
	 * Sets the operator name.
	 *
	 * @param operatorName the new operator name
	 */
	public void setOperatorName( String operatorName )
	{
		this.operatorName = operatorName;
	}

	/**
	 * Gets the operator country.
	 *
	 * @return the operator country
	 */
	public String getOperatorCountry()
	{
		return operatorCountry;
	}

	/**
	 * Sets the operator country.
	 *
	 * @param operatorCountry the new operator country
	 */
	public void setOperatorCountry( String operatorCountry )
	{
		this.operatorCountry = operatorCountry;
	}

	/**
	 * Gets the operator code.
	 *
	 * @return the operator code
	 */
	public String getOperatorCode()
	{
		return operatorCode;
	}

	/**
	 * Sets the operator code.
	 *
	 * @param operatorCode the new operator code
	 */
	public void setOperatorCode( String operatorCode )
	{
		this.operatorCode = operatorCode;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber( String phoneNumber )
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation( String location )
	{
		this.location = location;
	}

	/**
	 * Gets the last cradle id.
	 *
	 * @return the last cradle id
	 */
	public String getLastCradleId()
	{
		return lastCradleId;
	}

	/**
	 * Sets the last cradle id.
	 *
	 * @param lastCradleId the new last cradle id
	 */
	public void setLastCradleId( String lastCradleId )
	{
		this.lastCradleId = lastCradleId;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage( String language )
	{
		this.language = language;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus( String status )
	{
		this.status = status;
	}

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public String getMode()
	{
		return mode;
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode( String mode )
	{
		this.mode = mode;
	}

	/**
	 * Gets the reserved.
	 *
	 * @return the reserved
	 */
	public Boolean getReserved()
	{
		return reserved;
	}

	/**
	 * Sets the reserved.
	 *
	 * @param reserved the new reserved
	 */
	public void setReserved( Boolean reserved )
	{
		this.reserved = reserved;
	}

	/**
	 * Gets the in use.
	 *
	 * @return the in use
	 */
	public Boolean getInUse()
	{
		if ( inUse == null )
			return false;
		return inUse;
	}

	/**
	 * Sets the in use.
	 *
	 * @param inUse the new in use
	 */
	public void setInUse( Boolean inUse )
	{
		this.inUse = inUse;
	}

	/**
	 * Gets the operability rating.
	 *
	 * @return the operability rating
	 */
	public double getOperabilityRating()
	{
		return operabilityRating;
	}

	/**
	 * Sets the operability rating.
	 *
	 * @param operabilityRating the new operability rating
	 */
	public void setOperabilityRating( double operabilityRating )
	{
		this.operabilityRating = operabilityRating;
	}

	/**
	 * Gets the cradle id.
	 *
	 * @return the cradle id
	 */
	public String getCradleId()
	{
		return cradleId;
	}

	/**
	 * Sets the cradle id.
	 *
	 * @param cradleId the new cradle id
	 */
	public void setCradleId( String cradleId )
	{
		this.cradleId = cradleId;
	}

	/**
	 * Gets the position id.
	 *
	 * @return the position id
	 */
	public String getPositionId()
	{
		return positionId;
	}

	/**
	 * Sets the position id.
	 *
	 * @param positionId the new position id
	 */
	public void setPositionId( String positionId )
	{
		this.positionId = positionId;
	}

	/**
	 * Gets the position method.
	 *
	 * @return the position method
	 */
	public String getPositionMethod()
	{
		return positionMethod;
	}

	/**
	 * Sets the position method.
	 *
	 * @param positionMethod the new position method
	 */
	public void setPositionMethod( String positionMethod )
	{
		this.positionMethod = positionMethod;
	}

	/**
	 * Gets the position rotation.
	 *
	 * @return the position rotation
	 */
	public Double getPositionRotation()
	{
		return positionRotation;
	}

	/**
	 * Sets the position rotation.
	 *
	 * @param positionRotation the new position rotation
	 */
	public void setPositionRotation( Double positionRotation )
	{
		this.positionRotation = positionRotation;
	}

	/**
	 * Gets the os.
	 *
	 * @return the os
	 */
	public String getOs()
	{
		return os;
	}

	/**
	 * Sets the os.
	 *
	 * @param os the new os
	 */
	public void setOs( String os )
	{
		this.os = os;
	}

	/**
	 * Gets the os version.
	 *
	 * @return the os version
	 */
	public String getOsVersion()
	{
		return osVersion;
	}

	/**
	 * Sets the os version.
	 *
	 * @param osVersion the new os version
	 */
	public void setOsVersion( String osVersion )
	{
		this.osVersion = osVersion;
	}

	/**
	 * Gets the resolution.
	 *
	 * @return the resolution
	 */
	public String getResolution()
	{
		return resolution;
	}

	/**
	 * Sets the resolution.
	 *
	 * @param resolution the new resolution
	 */
	public void setResolution( String resolution )
	{
		this.resolution = resolution;
	}

	

}
