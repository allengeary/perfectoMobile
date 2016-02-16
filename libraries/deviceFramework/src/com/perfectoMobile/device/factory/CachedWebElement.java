/*
 * 
 */
package com.perfectoMobile.device.factory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Node;

// TODO: Auto-generated Javadoc
/**
 * The Class CachedWebElement.
 */
public class CachedWebElement implements WebElement
{
	
	/** The element node. */
	private Node elementNode;
	
	/** The web driver. */
	private WebDriver webDriver;
	
	/** The by. */
	private By by;
	
	/**
	 * Instantiates a new cached web element.
	 *
	 * @param webDriver the web driver
	 * @param by the by
	 * @param elementNode the element node
	 */
	public CachedWebElement( WebDriver webDriver, By by, Node elementNode )
	{
		this.elementNode = elementNode;
		this.webDriver = webDriver;
		this.by = by;
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#click()
	 */
	@Override
	public void click()
	{
		webDriver.findElement( by ).click();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	@Override
	public void submit()
	{
		webDriver.findElement( by ).submit();
		
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#sendKeys(java.lang.CharSequence[])
	 */
	@Override
	public void sendKeys( CharSequence... keysToSend )
	{
		webDriver.findElement( by ).sendKeys( keysToSend );
		
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear()
	{
		webDriver.findElement( by ).clear();
		
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	@Override
	public String getTagName()
	{
		return elementNode.getNodeName();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute( String name )
	{
		Node attributeNode = elementNode.getAttributes().getNamedItem( name );
		if ( attributeNode != null )
			return attributeNode.getNodeValue();
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected()
	{
		return webDriver.findElement( by ).isSelected();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	@Override
	public boolean isEnabled()
	{
		try
		{
			
			return Boolean.parseBoolean( getAttribute( "enabled" ) );
		}
		catch( Exception e )
		{
			return webDriver.findElement( by ).isEnabled();
		}
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText()
	{
		return elementNode.getTextContent();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements( By by )
	{
		return webDriver.findElement( this.by ).findElements( by );
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement( By by )
	{
		return webDriver.findElement( this.by ).findElement( by );
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isDisplayed()
	 */
	@Override
	public boolean isDisplayed()
	{
		return webDriver.findElement( by ).isDisplayed();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getLocation()
	 */
	@Override
	public Point getLocation()
	{
		try
		{
			String x = null, y = null;
			
			x=getAttribute( "x" );
			y=getAttribute( "y" );
			return new Point( Integer.parseInt( x ), Integer.parseInt( y ) );
		}
		catch( Exception e )
		{
			return webDriver.findElement( by ).getLocation();
		}
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getSize()
	 */
	@Override
	public Dimension getSize()
	{
		try
		{
			String height = null, width = null;
			
			height=getAttribute( "height" );
			width=getAttribute( "width" );
			return new Dimension( Integer.parseInt( width ), Integer.parseInt( height ) );
		}
		catch( Exception e )
		{
			return webDriver.findElement( by ).getSize();
		}
		
		
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue( String propertyName )
	{
		return webDriver.findElement( by ).getCssValue( propertyName );
	}

}
