package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * The {@link LayoutManager}, that will manage the Layout for the 
 * {@link windows#DisplayGaplistsWindow}.
 * @author Haeldeus
 * @version 1.0
 */
public class DisplayGaplistsLayout implements LayoutManager{

	/**
	 * The String, that declares the Fail_Label.
	 */
	public static final String FAIL_LABEL = "Fail_Label";
	
	/**
	 * The String, that declares the Saved_Gaplists_Label.
	 */
	public static final String SAVED_GAPLISTS_LABEL = "Saved_Gaplists_Label";
	
	/**
	 * The String, that declares the Content_Label.
	 */
	public static final String CONTENT_LABEL = "Content_Label";

	/**
	 * The String, that declares the Load_Button.
	 */
	public static final String LOAD_BUTTON = "Load_Button";
	
	/**
	 * The String, that declares the Show_Button.
	 */
	public static final String SHOW_BUTTON = "Show_Button";
	
	/**
	 * The String, that declares the Delete_Button.
	 */
	public static final String DELETE_BUTTON = "Delete_Button";
	
	/**
	 * The String, that declares the Gaplists_Pane.
	 */
	public static final String GAPLISTS_PANE = "Gaplists_Pane";
	
	/**
	 * The String, that declares the Content_Pane.
	 */
	public static final String CONTENT_PANE = "Content_Pane";
	
	/**
	 * All components, that are used by this Layout as a {@link HashMap} of {@link String} and
	 * {@link Component}.
	 */
	private HashMap<String, Component> components;
	
	/**
	 * The Constructor for the Layout. Initializes the components-HashMap.
	 * @since 1.0
	 */
	public DisplayGaplistsLayout() {
		components = new HashMap<String, Component>();
	}
	
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (checkLayoutComponent(regex))
			components.put(regex, comp);
	}

	private boolean checkLayoutComponent(String regex) {
		if (regex.equals(SAVED_GAPLISTS_LABEL) || regex.equals(CONTENT_LABEL) || 
				regex.equals(FAIL_LABEL) || regex.equals(LOAD_BUTTON) || 
				regex.equals(SHOW_BUTTON) || regex.equals(DELETE_BUTTON) || 
				regex.equals(GAPLISTS_PANE) || regex.equals(CONTENT_PANE))
			return true;
		else
			return false;
	}

	@Override
	public void layoutContainer(Container parent) {
		int height = parent.getHeight();
		int width = parent.getWidth();
		int lblHeight = 14;
		int buttonHeight = 22;
		int buttonY = height-(buttonHeight+10);
		int buttonWidth = (((width-20)/2)-20)/3;
		
		/*********************Labels*************************/
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds(10 + (width-20)/2+5, buttonY, (width-20)/2-5, lblHeight);
		
		if (components.get(SAVED_GAPLISTS_LABEL) != null)
			components.get(SAVED_GAPLISTS_LABEL).setBounds(10, 10, (width-20)/2-5, lblHeight);
		
		if (components.get(CONTENT_LABEL) != null)
			components.get(CONTENT_LABEL).setBounds(10 + (width-20)/2 + 5, 10, (width-20)/2-5, lblHeight);
		
		/********************Buttons************************/
		if (components.get(LOAD_BUTTON) != null)
			components.get(LOAD_BUTTON).setBounds(10, buttonY, buttonWidth, buttonHeight);
		
		if (components.get(SHOW_BUTTON) != null)
			components.get(SHOW_BUTTON).setBounds(10 + buttonWidth + 10, buttonY, buttonWidth, buttonHeight);
		
		if (components.get(DELETE_BUTTON) != null) 
			components.get(DELETE_BUTTON).setBounds(30 + 2*buttonWidth, buttonY, ((width-20)/2+5) - (30 + 2*buttonWidth), buttonHeight);
		
		/*****************Panes*****************************/
		if (components.get(GAPLISTS_PANE) != null)
			components.get(GAPLISTS_PANE).setBounds(10, 20+lblHeight, (width-20)/2-5, height - (10 + lblHeight + 52));
		
		if (components.get(CONTENT_PANE) != null)
			components.get(CONTENT_PANE).setBounds(10 + (width-20)/2+5, 20 + lblHeight, (width-20)/2-5, height - (10 + lblHeight + 52));
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(300,300);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(300,300);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp))
			remove(comp);
	}
	
	/**
	 * Removes the given Component from the Layout.
	 * @param comp	The Component to be removed.
	 * @since 1.0
	 */
	private void remove(Component comp) {
		if (components.get(FAIL_LABEL) != null)
			if (components.get(FAIL_LABEL).equals(comp)) {
				components.put(FAIL_LABEL, null);
				return;
			}
		
		if (components.get(SAVED_GAPLISTS_LABEL) != null)
			if (components.get(SAVED_GAPLISTS_LABEL).equals(comp)) {
				components.put(SAVED_GAPLISTS_LABEL, null);
				return;
			}
		
		if (components.get(CONTENT_LABEL) != null)
			if (components.get(CONTENT_LABEL).equals(comp)) {
				components.put(CONTENT_LABEL, null);
				return;
			}
		
		if (components.get(LOAD_BUTTON) != null)
			if (components.get(LOAD_BUTTON).equals(comp)) {
				components.put(LOAD_BUTTON, null);
				return;
			}
		
		if (components.get(SHOW_BUTTON) != null)
			if (components.get(SHOW_BUTTON).equals(comp)) {
				components.put(SHOW_BUTTON, null);
				return;
			}
		
		if (components.get(DELETE_BUTTON) != null)
			if (components.get(DELETE_BUTTON).equals(comp)) {
				components.put(DELETE_BUTTON, null);
				return;
			}
		
		if (components.get(GAPLISTS_PANE) != null)
			if (components.get(GAPLISTS_PANE).equals(comp)) {
				components.put(GAPLISTS_PANE, null);
				return;
			}
		
		if (components.get(CONTENT_PANE) != null)
			if (components.get(CONTENT_PANE).equals(comp)) {
				components.put(CONTENT_PANE, null);
				return;
			}
	}

}
