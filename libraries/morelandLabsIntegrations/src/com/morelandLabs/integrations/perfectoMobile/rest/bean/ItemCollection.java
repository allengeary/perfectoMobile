package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import java.util.ArrayList;
import java.util.List;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

/**
 * The Class ItemCollection.
 */
@BeanDescriptor( beanName="response" )
public class ItemCollection extends AbstractBean
{
    @FieldCollection( fieldElement=Item.class, fieldPath="items" )
    private List<Item> itemList = new ArrayList<Item>( 10 );

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ItemCollection [itemList=" + itemList + "]";
    }

    public List<Item> getItemList()
    {
        return itemList;
    }
	
}
