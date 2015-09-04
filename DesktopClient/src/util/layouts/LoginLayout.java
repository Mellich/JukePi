package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * The Layout, that will be used for the LogIn-Screen.
 * @author Haeldeus
 * @version 1.0
 */
public class LoginLayout implements LayoutManager{

	/**
	 * The String, that declares the TextField for the IP.
	 * @see windows.LogIn#txtIp
	 */
	public static final String IP_TEXT = "Ip_Text";
	
	/**
	 * The String, that declares the TextField for the Port.
	 * @see windows.LogIn#txtPort
	 */
	public static final String PORT_TEXT = "Port_Text";
	
	
	/**
	 * The String, that declares the Label, that contains the Welcome-Message.
	 */
	public static final String WELCOME_LABEL = "Welcome_Label";
	
	/**
	 * The String, that declares the Label, that contains {@code "IP:"}.
	 */
	public static final String IP_LABEL = "Ip_Label";
	
	/**
	 * The String, that declares the Label, that contains {@code "Port:"}.
	 */
	public static final String PORT_LABEL = "Port_Label";
	
	/**
	 * The String, that declares the Label, that displays responses from the Server.
	 */
	public static final String FAIL_LABEL = "Fail_Label";
	
	
	/**
	 * The String, that declares the Connect_Button.
	 */
	public static final String CONNECT_BUTTON = "Connect_Button";
	
	/**
	 * The String, that declares the UDP_Connect_Button.
	 */
	public static final String UDP_BUTTON = "Udp_Button";
	
	/**
	 * The String, that declares the Intern_Server_Button.
	 */
	public static final String INTERN_SERVER_BUTTON = "Intern_Server_Button";
	
	/**
	 * All Components, that were added to this Layout as a {@link HashMap} with String-Keys 
	 * and Component-Values.
	 */
	private HashMap<String, Component> components;
	
	/**
	 * The Constructor for this Layout.
	 * @since 1.0
	 */
	public LoginLayout() {
		components = new HashMap<String, Component>();
	}
	
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (checkLayoutComponent(regex)) {
			components.put(regex, comp);
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		final int height = parent.getHeight();
		final int width = parent.getWidth();
		
		final int textHeight = (int)(height*0.05);
		final int labelHeight = (int)(height*0.035);
		final int buttonY = (int)(height*0.7);
		final int buttonHeight = (int)(height*0.1);
		
		/******************************TextFields*****************************/
		if (components.get(IP_TEXT) != null)
			components.get(IP_TEXT).setBounds((int)(width*0.3), (int)(height*0.275), (int)(width*0.37), textHeight);
		
		if (components.get(PORT_TEXT) != null)
			components.get(PORT_TEXT).setBounds((int)(width*0.3), (int)(height*0.4625), (int)(width*0.09), textHeight);
		
		/*****************************Labels*********************************/
		if (components.get(WELCOME_LABEL) != null)
			components.get(WELCOME_LABEL).setBounds((int)(width*0.02), (int)(height*0.025), (int)(width*0.96), (int)(height*0.2));
		
		if (components.get(IP_LABEL) != null)
			components.get(IP_LABEL).setBounds((int)(width*0.06), (int)(height*0.275), (int)(width*0.152), labelHeight);
		
		if (components.get(PORT_LABEL) != null)
			components.get(PORT_LABEL).setBounds((int)(width*0.06), (int)(height*0.4625), (int)(width*0.09), labelHeight);
		
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds((int)(width*0.1), (int)(height*0.5875), (int)(width*0.75), labelHeight);
		
		/******************************Buttons******************************/
		if (components.get(CONNECT_BUTTON) != null)
			components.get(CONNECT_BUTTON).setBounds((int)(width*0.02), buttonY, (int)(width*0.26), buttonHeight);
		
		if (components.get(UDP_BUTTON) != null)
			components.get(UDP_BUTTON).setBounds((int)(width*0.32), buttonY, (int)(width*0.26), buttonHeight);
		
		if (components.get(INTERN_SERVER_BUTTON) != null)
			components.get(INTERN_SERVER_BUTTON).setBounds((int)(width*0.62), buttonY, (int)(width*0.34), buttonHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return new Dimension(500,400);
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		return new Dimension(500,400);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp)) {
			remove(comp);
		}
	}

	/**
	 * Checks, if the given {@code regex} is a String-Key for this Layout.
	 * @param regex	The String, that will be checked.
	 * @return	{@code true}, if {@code regex} is a Key for this Layout, {@code false} else.
	 * @since 1.0
	 */
	private boolean checkLayoutComponent(String regex) {
		if (regex.equals(IP_TEXT) || regex.equals(PORT_TEXT) || regex.equals(WELCOME_LABEL) ||
				regex.equals(IP_LABEL) || regex.equals(PORT_LABEL) || 
				regex.equals(FAIL_LABEL) || regex.equals(CONNECT_BUTTON) ||
				regex.equals(UDP_BUTTON) || regex.equals(INTERN_SERVER_BUTTON))
			return true;
		else
			return false;
	}
	
	/**
	 * Removes the given Component {@code comp} from the Layout.
	 * @param comp	The Component, that will be removed.
	 * @since 1.0
	 */
	private void remove(Component comp) {
		if (components.get(IP_TEXT) != null)
			if (components.get(IP_TEXT).equals(comp)) {
				components.put(IP_TEXT, null);
				return;
			}
		
		if (components.get(PORT_TEXT) != null)
			if (components.get(PORT_TEXT).equals(comp)) {
				components.put(PORT_TEXT, null);
				return;
			}
		
		if (components.get(WELCOME_LABEL) != null)
			if (components.get(WELCOME_LABEL).equals(comp)) {
				components.put(WELCOME_LABEL, null);
				return;
			}
		
		if (components.get(IP_LABEL) != null)
			if (components.get(IP_LABEL).equals(comp)) {
				components.put(IP_LABEL, null);
				return;
			}
		
		if (components.get(PORT_LABEL) != null)
			if (components.get(PORT_LABEL).equals(comp)) {
				components.put(PORT_LABEL, null);
				return;
			}
		
		if (components.get(FAIL_LABEL) != null)
			if (components.get(FAIL_LABEL).equals(comp)) {
				components.put(FAIL_LABEL, null);
				return;
			}
		
		if (components.get(CONNECT_BUTTON) != null)
			if (components.get(CONNECT_BUTTON).equals(comp)) {
				components.put(CONNECT_BUTTON, null);
				return;
			}
		
		if (components.get(UDP_BUTTON) != null)
			if (components.get(UDP_BUTTON).equals(comp)) {
				components.put(UDP_BUTTON, null);
				return;
			}
		
		if (components.get(INTERN_SERVER_BUTTON) != null)
			if (components.get(INTERN_SERVER_BUTTON).equals(comp)) {
				components.put(INTERN_SERVER_BUTTON, null);
				return;
			}
	}
}
