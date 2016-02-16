package com.perfectoMobile.page.keyWord.step;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectoMobile.page.keyWord.KeyWordStep;
import com.perfectoMobile.page.keyWord.KeyWordStep.StepFailure;
import com.perfectoMobile.page.keyWord.step.spi.KWSAttribute;
import com.perfectoMobile.page.keyWord.step.spi.KWSBreak;
import com.perfectoMobile.page.keyWord.step.spi.KWSCall;
import com.perfectoMobile.page.keyWord.step.spi.KWSCheckColor;
import com.perfectoMobile.page.keyWord.step.spi.KWSClick;
import com.perfectoMobile.page.keyWord.step.spi.KWSContrastRatio;
import com.perfectoMobile.page.keyWord.step.spi.KWSDevice;
import com.perfectoMobile.page.keyWord.step.spi.KWSExists;
import com.perfectoMobile.page.keyWord.step.spi.KWSFork;
import com.perfectoMobile.page.keyWord.step.spi.KWSFunction;
import com.perfectoMobile.page.keyWord.step.spi.KWSGesture;
import com.perfectoMobile.page.keyWord.step.spi.KWSLoop;
import com.perfectoMobile.page.keyWord.step.spi.KWSReturn;
import com.perfectoMobile.page.keyWord.step.spi.KWSSet;
import com.perfectoMobile.page.keyWord.step.spi.KWSValue;
import com.perfectoMobile.page.keyWord.step.spi.KWSVisible;
import com.perfectoMobile.page.keyWord.step.spi.KWSWait;
import com.perfectoMobile.page.keyWord.step.spi.KWSWaitFor;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating KeyWordStep objects.
 */
public class KeyWordStepFactory
{
	
	/** The singleton. */
	private static KeyWordStepFactory singleton = new KeyWordStepFactory();

	/**
	 * Instance.
	 *
	 * @return the key word step factory
	 */
	public static KeyWordStepFactory instance()
	{
		return singleton;
	}
	
	/** The step map. */
	private Map<String,Class>stepMap = new HashMap<String,Class>( 20 );
	
	/** The log. */
	private Log log = LogFactory.getLog( KeyWordStepFactory.class );
	
	/**
	 * Instantiates a new key word step factory.
	 */
	private KeyWordStepFactory()
	{
		initializeDefaults();
	}
	
	/**
	 * Initialize defaults.
	 */
	private void initializeDefaults()
	{
		addKeyWord( "CALL", KWSCall.class );
		addKeyWord( "CLICK", KWSClick.class );
		addKeyWord( "EXISTS", KWSExists.class );
		addKeyWord( "FUNCTION", KWSFunction.class );
		addKeyWord( "GESTURE", KWSGesture.class );
		addKeyWord( "RETURN", KWSReturn.class );
		addKeyWord( "SET", KWSSet.class );
		addKeyWord( "VALUE", KWSValue.class );
		addKeyWord( "GET", KWSValue.class );
		addKeyWord( "WAIT", KWSWait.class );
		addKeyWord( "WAIT_FOR", KWSWaitFor.class );
		addKeyWord( "ATTRIBUTE", KWSAttribute.class );
		addKeyWord( "LOOP", KWSLoop.class );
		addKeyWord( "BREAK", KWSBreak.class );
		addKeyWord( "DEVICE", KWSDevice.class );
		addKeyWord( "FORK", KWSFork.class );
		addKeyWord( "VISIBLE", KWSVisible.class );
		addKeyWord( "VERIFY_COLOR", KWSCheckColor.class );
		addKeyWord( "VERIFY_CONTRAST", KWSContrastRatio.class );
	}
	
	/**
	 * Adds the key word.
	 *
	 * @param keyWord the key word
	 * @param kwImpl the kw impl
	 */
	public void addKeyWord( String keyWord, Class kwImpl )
	{
		if ( stepMap.containsKey( keyWord ) )
			log.warn( "Overwriting Keyword [" + keyWord + "] of type [" + stepMap.get( keyWord ).getClass().getSimpleName() + "] with [" + kwImpl.getClass().getSimpleName() );
		
		stepMap.put( keyWord.toUpperCase(), kwImpl );
	}
	
	/**
	 * Creates a new KeyWordStep object.
	 *
	 * @param name the name
	 * @param pageName the page name
	 * @param active the active
	 * @param type the type
	 * @param linkId the link id
	 * @param timed the timed
	 * @param sFailure the s failure
	 * @param inverse the inverse
	 * @param os the os
	 * @param poi the poi
	 * @param threshold the threshold
	 * @param description the description
	 * @return the key word step
	 */
	public KeyWordStep createStep( String name, String pageName, boolean active, String type, String linkId, boolean timed, StepFailure sFailure, boolean inverse, String os, String poi, int threshold, String description )
	{
		
		Class kwImpl = stepMap.get( type.toUpperCase() );
		
		if ( kwImpl == null )
		{
			log.error( "Unknown KeyWord [" + type + "]" );
			System.exit( -1 );
		}
		try
		{
			KeyWordStep returnValue = (KeyWordStep) kwImpl.newInstance();
			returnValue.setActive( active );
			returnValue.setLinkId( linkId );
			returnValue.setName( name );
			returnValue.setPageName( pageName );
			returnValue.setTimed( timed );
			returnValue.setFailure( sFailure );
			returnValue.setInverse( inverse );
			returnValue.setOs( os );
			returnValue.setPoi( poi );
			returnValue.setThreshold( threshold );
			returnValue.setDescription(description);
			
			return returnValue;
		}
		catch( Exception e )
		{
			throw new IllegalArgumentException( "Unknown KeyWord [" + type + "]", e );
		}
		
		
	}
}