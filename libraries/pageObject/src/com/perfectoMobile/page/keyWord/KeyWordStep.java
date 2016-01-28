package com.perfectoMobile.page.keyWord;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;

/**
 * The Interface KeyWordStep.
 */
public interface KeyWordStep
{

	/** The Constant TYPE. */
	public static final String TYPE = "TYPE";

	/**
	 * The Enum StepFailure.
	 */
	public enum StepFailure
	{

		/** The error. */
		ERROR,
		/** The ignore. */
		IGNORE,
		/** The log ignore. */
		LOG_IGNORE
	}
	
	/**
	 * The Enum StepFailure.
	 */
	public enum ValidationType
	{
		REGEX,
		EMPTY,
		NOT_EMPTY;
	}

	/**
	 * Gets the link id.
	 *
	 * @return the link id
	 */
	public String getOs();

	/**
	 * Sets the link id.
	 *
	 * @param linkId
	 *            the new link id
	 */
	public void setOs( String os );
	
	/**
	 * Gets the link id.
	 *
	 * @return the link id
	 */
	public String getLinkId();

	/**
	 * Sets the link id.
	 *
	 * @param linkId
	 *            the new link id
	 */
	public void setLinkId( String linkId );

	/**
	 * Gets the failure.
	 *
	 * @return the failure
	 */
	public StepFailure getFailure();

	/**
	 * Sets the failure.
	 *
	 * @param sf
	 *            the new failure
	 */
	public void setFailure( StepFailure sf );

	/**
	 * Adds the parameter.
	 *
	 * @param param
	 *            the param
	 */
	public void addParameter( KeyWordParameter param );

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName( String name );

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive();

	/**
	 * Sets the active.
	 *
	 * @param active
	 *            the new active
	 */
	public void setActive( boolean active );

	/**
	 * Checks if is inverse.
	 *
	 * @return true, if is inverse
	 */
	public boolean isInverse();

	/**
	 * Sets the inverse.
	 *
	 * @param inverse
	 *            the new inverse
	 */
	public void setInverse( boolean inverse );

	/**
	 * Gets the page name.
	 *
	 * @return the page name
	 */
	public String getPageName();

	/**
	 * Sets the page name.
	 *
	 * @param pageName
	 *            the new page name
	 */
	public void setPageName( String pageName );

	/**
	 * Checks if is timed.
	 *
	 * @return true, if is timed
	 */
	public boolean isTimed();

	/**
	 * Sets the timed.
	 *
	 * @param timed
	 *            the new timed
	 */
	public void setTimed( boolean timed );

	/**
	 * Adds the all steps.
	 *
	 * @param step
	 *            the step
	 */
	public void addAllSteps( KeyWordStep[] step );

	/**
	 * Adds the step.
	 *
	 * @param step
	 *            the step
	 */
	public void addStep( KeyWordStep step );

	/**
	 * Gets the step list.
	 *
	 * @return the step list
	 */
	public List<KeyWordStep> getStepList();

	/**
	 * Checks if is fork.
	 *
	 * @return true, if is fork
	 */
	public boolean isFork();

	/**
	 * Sets the fork.
	 *
	 * @param fork
	 *            the new fork
	 */
	public void setFork( boolean fork );
	
	/**
	 * Adds the token.
	 *
	 * @param token
	 *            the token
	 */
	public void addToken( KeyWordToken token  );

	/**
	 * Execute step.
	 *
	 * @param pageObject            the page object
	 * @param webDriver            the web driver
	 * @param contextMap            the context map
	 * @param dataMap            the data map
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap ) throws Exception;
	
	/**
	 * To error.
	 *
	 * @return the string
	 */
	public String toError();
	
	public String getContext();
	
	public String getValidation();
	
	public void setValidation( String validation );
	
	public ValidationType getValidationType();
	
	public void setValidationType( ValidationType validationType );
	
	public void setContext( String contextName );
}
