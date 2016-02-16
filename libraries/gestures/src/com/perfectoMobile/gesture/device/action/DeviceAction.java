package com.perfectoMobile.gesture.device.action;

import java.util.List;

import org.openqa.selenium.WebDriver;

/**
 * The Interface DeviceAction.
 */
public interface DeviceAction
{
	
	/**
	 * The Enum ActionType.
	 */
	public enum ActionType
	{
		
		/** The hide keyboard. */
		HIDE_KEYBOARD,
		
		/** The back. */
		BACK,
		
		/** The url. */
		URL,
		
		/** The install. */
		INSTALL,
		
		/** The uninstall. */
		UNINSTALL,
		
		/** The reset apps. */
		RESET_APPS,
		
		/** The open app. */
		OPEN_APP,
		
		/** The close app. */
		CLOSE_APP,
		
		/** The reboot. */
		REBOOT,
		
		/** The recover. */
		RECOVER,
		
		/** The clean. */
		CLEAN,
		
		/** switch the context */
		CONTEXT,
		
		DUMP_STATE,
		
		
		/** The location. */
		LOCATION;
	}
	
	/**
	 * Execute action.
	 *
	 * @param webDriver the web driver
	 * @return true, if successful
	 */
	public boolean executeAction( WebDriver webDriver );
	
	/**
	 * Execute action.
	 *
	 * @param webDriver the web driver
	 * @param parameterList the parameter list
	 * @return true, if successful
	 */
	public boolean executeAction( WebDriver webDriver, List<Object> parameterList );
}
