package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

/**
 * The Class Item.
 */
@BeanDescriptor( beanName="item" )
public class Item extends AbstractBean
{
    @FieldDescriptor ( textContent=true )
    private String textContent;

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Item [textContent=" + textContent + "]";
    }

    public String getTextContext()
    {
        return textContent;
    }
    
    public void setTextContext( String val )
    {
        this.textContent = val;
    }

}
