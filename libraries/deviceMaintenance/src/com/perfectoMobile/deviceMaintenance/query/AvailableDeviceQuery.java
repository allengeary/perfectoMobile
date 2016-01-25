package com.perfectoMobile.deviceMaintenance.query;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;

/**
 * The Class AvailableDeviceQuery.
 */
public class AvailableDeviceQuery extends AbstractDeviceQuery
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.query.AbstractDeviceQuery#_findDevices()
	 */
	@Override
	protected List<Handset> _findDevices()
	{
		try
		{
			return PerfectoMobile.instance().devices().getDevices( false ).getHandsetList();
		}
		catch( Exception e )
		{
			log.fatal( "Could not acquire device list", e );
			return null;
		}
	}
	
	/**
	 * Gets the value.
	 *
	 * @param handSet the hand set
	 * @param tagName the tag name
	 * @return the value
	 */
	protected String getValue( Node handSet, String tagName )
	{
		NodeList params = handSet.getChildNodes();
		
		for ( int i=0; i<params.getLength(); i++ )
		{
			if ( params.item( i ).getNodeName().toLowerCase().equals( tagName.toLowerCase() ) )
			{
				return params.item( i ).getTextContent();
			}
		}
		
		return null;
	}
}
