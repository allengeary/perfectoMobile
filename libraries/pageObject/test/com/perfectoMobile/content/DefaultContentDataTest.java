package com.perfectoMobile.content;

import org.apache.xpath.patterns.ContextMatchStepPattern;
import org.testng.Assert;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultContentDataTest.
 */
public class DefaultContentDataTest
{

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Test
	public void getName()
	{
		ContentData cd = new DefaultContentData( "test", new String[] { "one", "two", "three" } );
		
		Assert.assertEquals( cd.getName(), "test" );
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	@Test
	public void getValue()
	{
		ContentData cd = new DefaultContentData( "test", new String[] { "one", "two", "three" } );
		
		Assert.assertEquals( cd.getValue(), "one" );
		
		cd = new DefaultContentData( "test", new String[ 0 ] );
		Assert.assertNull( cd.getValue() );
		cd = new DefaultContentData( "test", null );
		Assert.assertNull( cd.getValue() );
	}

	/**
	 * Gets the value string.
	 *
	 * @return the value string
	 */
	@Test
	public void getValueString()
	{
		ContentManager.instance().setMatrixData( new String[] { "1", "2", "3" } );
		
		ContentData cd = new DefaultContentData( "test", new String[] { "one", "two", "three" } );
		
		Assert.assertEquals( cd.getValue(), "one" );
		Assert.assertEquals( cd.getValue( "1" ), "one" );
		Assert.assertEquals( cd.getValue( "2" ), "two" );
		Assert.assertEquals( cd.getValue( "3" ), "three" );
		Assert.assertNull( cd.getValue( "4" ) );
	}

	/**
	 * Gets the valueint.
	 *
	 * @return the valueint
	 */
	@Test
	public void getValueint()
	{
		ContentData cd = new DefaultContentData( "test", new String[] { "one", "two", "three" } );
		
		Assert.assertEquals( cd.getValue(), "one" );
		Assert.assertEquals( cd.getValue( 0 ), "one" );
		Assert.assertEquals( cd.getValue( 1 ), "two" );
		Assert.assertEquals( cd.getValue( 2 ), "three" );
		Assert.assertNull( cd.getValue( 3 ) );
		
		cd = new DefaultContentData( "test", null );
		
		Assert.assertNull( cd.getValue() );
		Assert.assertNull( cd.getValue( 0 ) );
		Assert.assertNull( cd.getValue( 1 ) );
		Assert.assertNull( cd.getValue( 2 ) );
		Assert.assertNull( cd.getValue( 3 ) );
	}
}
