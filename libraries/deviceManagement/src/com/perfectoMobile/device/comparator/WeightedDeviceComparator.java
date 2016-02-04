package com.perfectoMobile.device.comparator;

import java.util.Comparator;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.DeviceManager;

/**
 * The Class WeightedDeviceComparator.
 */
public class WeightedDeviceComparator implements Comparator<Device>
{

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare( Device o1, Device o2 )
	{
		int usageOne = DeviceManager.instance().getUsage( o1 );
		int usageTwo = DeviceManager.instance().getUsage( o2 );
		
		
		if ( usageOne < usageTwo )
			return -1;
		else if ( usageOne > usageTwo )
			return 1;
		else
			return 0;
	}
}
