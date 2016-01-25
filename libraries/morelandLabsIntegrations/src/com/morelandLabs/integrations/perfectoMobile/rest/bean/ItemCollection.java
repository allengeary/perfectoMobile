package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import java.util.ArrayList;
import java.util.List;

import com.morelandLabs.integrations.rest.bean.AbstractBean;

/**
 * The Class ItemCollection.
 */
public class ItemCollection extends AbstractBean
{
	@FieldCollection( fieldElement=Item.class, fieldPath="response/items" )
	private List<Item> itemList = new ArrayList<Item>( 10 );

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ItemCollection [itemList=" + itemList + "]";
	}
	
	
}
