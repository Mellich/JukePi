package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

public class LoginLayout implements LayoutManager{

	public static final String IP_TEXT = "Ip_Text";
	public static final String PORT_TEXT = "Port_Text";
	
	public static final String WELCOME_LABEL = "Welcome_Label";
	public static final String IP_LABEL = "Ip_Label";
	public static final String PORT_LABEL = "Port_Label";
	public static final String FAIL_LABEL = "Fail_Label";
	
	public static final String CONNECT_BUTTON = "Connect_Button";
	public static final String UDP_BUTTON = "Udp_Button";
	public static final String INTERN_SERVER_BUTTON = "Intern_Server_Button";
	
	private HashMap<String, Component> components;
	
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
		// TODO Auto-generated method stub
		int height = parent.getHeight();
		int width = parent.getWidth();
		
		int textHeight = (int)(height*0.05);
		int labelHeight = (int)(height*0.035);
		int buttonY = (int)(height*0.7);
		int buttonHeight = (int)(height*0.1);
		
		/******************************TextFields*****************************/
		components.get(IP_TEXT).setBounds((int)(width*0.3), (int)(height*0.275), (int)(width*0.37), textHeight);
		
		components.get(PORT_TEXT).setBounds((int)(width*0.3), (int)(height*0.4625), (int)(width*0.09), textHeight);
		
		/*****************************Labels*********************************/
		components.get(WELCOME_LABEL).setBounds((int)(width*0.02), (int)(height*0.025), (int)(width*0.96), (int)(height*0.2));
		
		components.get(IP_LABEL).setBounds((int)(width*0.06), (int)(height*0.275), (int)(width*0.152), labelHeight);
		
		components.get(PORT_LABEL).setBounds((int)(width*0.06), (int)(height*0.4625), (int)(width*0.09), labelHeight);
		
		components.get(FAIL_LABEL).setBounds((int)(width*0.1), (int)(height*0.5875), (int)(width*0.75), labelHeight);
		
		/******************************Buttons******************************/
		components.get(CONNECT_BUTTON).setBounds((int)(width*0.02), buttonY, (int)(width*0.26), buttonHeight);
		
		components.get(UDP_BUTTON).setBounds((int)(width*0.32), buttonY, (int)(width*0.26), buttonHeight);
		
		components.get(INTERN_SERVER_BUTTON).setBounds((int)(width*0.62), buttonY, (int)(width*0.34), buttonHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp)) {
			remove(comp);
		}
	}

	private boolean checkLayoutComponent(String regex) {
		if (regex.equals(IP_TEXT) || regex.equals(PORT_TEXT) || regex.equals(WELCOME_LABEL) ||
				regex.equals(IP_LABEL) || regex.equals(PORT_LABEL) || 
				regex.equals(FAIL_LABEL) || regex.equals(CONNECT_BUTTON) ||
				regex.equals(UDP_BUTTON) || regex.equals(INTERN_SERVER_BUTTON))
			return true;
		else
			return false;
	}
	
	private void remove(Component comp) {
		if (components.get(IP_TEXT).equals(comp))
			components.put(IP_TEXT, null);
		
		if (components.get(PORT_TEXT).equals(comp))
			components.put(PORT_TEXT, null);
		
		if (components.get(WELCOME_LABEL).equals(comp))
			components.put(WELCOME_LABEL, null);
		
		if (components.get(IP_LABEL).equals(comp))
			components.put(IP_LABEL, null);
		
		if (components.get(PORT_LABEL).equals(comp))
			components.put(PORT_LABEL, null);
		
		if (components.get(FAIL_LABEL).equals(comp))
			components.put(FAIL_LABEL, null);
		
		if (components.get(CONNECT_BUTTON).equals(comp))
			components.put(CONNECT_BUTTON, null);
		
		if (components.get(UDP_BUTTON).equals(comp))
			components.put(UDP_BUTTON, null);
		
		if (components.get(INTERN_SERVER_BUTTON).equals(comp))
			components.put(INTERN_SERVER_BUTTON, null);
		
		
	}
}
