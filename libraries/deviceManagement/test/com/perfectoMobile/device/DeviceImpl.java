package com.perfectoMobile.device;

import java.util.concurrent.Semaphore;

import com.morelandLabs.spi.Device;

/**
 * The Class DeviceImpl.
 */
public class DeviceImpl implements Device
{
	private String key;
	private int count;
	private Semaphore deviceLock;
	
	/**
	 * Instantiates a new device impl.
	 *
	 * @param key the key
	 * @param count the count
	 */
	public DeviceImpl( String key, int count )
	{
		this.key = key;
		this.count = count;
		deviceLock = new Semaphore( count );
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getDeviceName()
	 */
	public String getDeviceName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#toShortString()
	 */
	public String toShortString()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getKey()
	 */
	public String getKey()
	{
		return key;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getLock()
	 */
	public Semaphore getLock()
	{
		return deviceLock;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return key + ": Total(" + count + "): Available(" + deviceLock.availablePermits() + ")";		
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getManufacturer()
	 */
	public String getManufacturer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getModel()
	 */
	public String getModel()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getOs()
	 */
	public String getOs()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getOsVersion()
	 */
	public String getOsVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getBrowserName()
	 */
	public String getBrowserName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getBrowserVersion()
	 */
	public String getBrowserVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getAvailableDevices()
	 */
	public int getAvailableDevices()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getDriverType()
	 */
	public String getDriverType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.morelandLabs.spi.Device#isActive()
	 */
	@Override
	public boolean isActive()
	{
		// TODO Auto-generated method stub
		return false;
	}

	

}
