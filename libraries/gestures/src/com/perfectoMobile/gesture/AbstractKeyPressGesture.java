package com.perfectoMobile.gesture;

/**
 * The Class AbstractKeyPressGesture.
 */
public abstract class AbstractKeyPressGesture extends AbstractGesture
{
	private int keyCode;
	private int metaState;
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.Gesture#setParameters(java.lang.Object[])
	 */
	public void setParameters( Object[] parameterArray )
	{
		setKeyCode( (int) parameterArray[ 0 ] );
		setMetaState( (int) parameterArray[ 1 ] );
	}

	public int getMetaState() {
		return metaState;
	}

	public void setMetaState(int metaState) {
		this.metaState = metaState;
	}

	/**
	 * Gets the key code.
	 *
	 * @return the key code
	 */
	public int getKeyCode()
	{
		return keyCode;
	}

	/**
	 * Sets the key code.
	 *
	 * @param keyCode the new key code
	 */
	public void setKeyCode( int keyCode )
	{
		this.keyCode = keyCode;
	}	
	
	
	
	
}
