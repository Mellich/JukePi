package windows;

/**
 * An abstract Class, that is the parent-Class of all Windows for the Client UI and 
 * provides basic Methods.
 * @author Haeldeus
 * @version 1.1
 */
public abstract class Window {

	/**
	 * Shows the fail-Label on the Frame.
	 * @param text	The text, that will be displayed.
	 * @since 1.0
	 */
	public abstract void showFail(String text);
	
	/**
	 * Sets the Frame visible and enabled.
	 * @since 1.0
	 */
	public abstract void show();
	
	/**
	 * Sets the Frame invisible and disabled.
	 * @since 1.0
	 */
	public abstract void close();
	
	/**
	 * Sets the State of the Window to the given State.
	 * @param state	The new State of the Window, active, if {@code true}, inactive else.
	 * @since 1.1
	 */
	public abstract void setActive(boolean state);
	
}
