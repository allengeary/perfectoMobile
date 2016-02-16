package com.perfectoMobile.page.element.provider;

import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.element.Element;

// TODO: Auto-generated Javadoc
/**
 * The Interface ElementDataSource.
 */
public interface ElementDataSource
{
	
	/**
	 * Gets the element.
	 *
	 * @param elementDescriptor the element descriptor
	 * @return the element
	 */
	Element getElement( ElementDescriptor elementDescriptor );
}
