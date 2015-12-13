package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * The Layout for the Desktop-Client without Admin-Permissions.
 * @author Haeldeus
 * @version 1.0
 */
public class LowClientLayout implements LayoutManager{

	/**
	 * The String, that defines the Gaplist-Label.
	 */
	public final static String GAPLIST_LABEL = "Gaplist_Label";
	
	/**
	 * The String, that defines the Count-Gaplist-Label.
	 */
	public final static String COUNT_GAPLIST_LABEL = "Count_Gaplist_Label";
	
	/**
	 * The String, that defines the Wishlist-Label.
	 */
	public final static String WISHLIST_LABEL = "Wishlist_Label";
	
	/**
	 * The String, that defines the Count-Wishlist-Label.
	 */
	public final static String COUNT_WISHLIST_LABEL = "Count_Wishlist_Label";
	
	/**
	 * The String, that defines the Wishlist-Show-Label.
	 */
	public final static String WISHLIST_SHOW_LABEL = "Wishlist_Show_Label";
	
	/**
	 * The String, that defines the Fail-Label.
	 */
	public final static String FAIL_LABEL = "Fail_Label";
	
	/**
	 * The String, that defines the Current_Track_Label.
	 */
	public final static String CURRENT_TRACK_LABEL = "Current_Track_Label";
	
	/**
	 * The String, that defines the Label, that displays the Current Track.
	 */
	public final static String NAME_CURRENT_TRACK_LABEL = "Name_Current_Track_Label";
	
	
	/**
	 * The String, that defines the Link-TextField.
	 */
	public final static String LINK_TEXT = "Link_Text";
	
	
	/**
	 * The String, that defines the Add-Button.
	 */
	public final static String ADD_BUTTON = "Add_Button";
	
	/**
	 * The String, that defines the Disconnect-Button.
	 */
	public final static String DISCONNECT_BUTTON = "Disconnect_Button";
	
	/**
	 * The String, that defines the Vote-Button.
	 */
	public final static String VOTE_BUTTON = "Vote_Button";
	
	/**
	 * The String, that defines the Remove-Vote-Button.
	 */
	public final static String REMOVE_BUTTON = "Remove_Button";
	
	
	/**
	 * The String, that defines the Wishlist-Pane.
	 */
	public final static String WISHLIST_PANE = "Wishlist_Pane";
	
	
	/**
	 * The HashMap of Components, added to this Layout.
	 */
	private HashMap<String, Component> components;
	
	/**
	 * The Constructor for all Objects of this Layout.
	 * @since 1.0
	 */
	public LowClientLayout() {
		components = new HashMap<String, Component>();
	}
	
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (isLayoutComponent(regex)) {
			components.put(regex, comp);
		}
	}

	/**
	 * Checks, if the given {@code regex} is a key for this Layout.
	 * @param regex	The String to be checked.
	 * @return	{@code true}, if {@code regex} is a key, {@code false} else.
	 */
	private boolean isLayoutComponent(String regex) {
		if (regex.equals(GAPLIST_LABEL) || regex.equals(COUNT_GAPLIST_LABEL) || 
				regex.equals(WISHLIST_LABEL) || regex.equals(COUNT_WISHLIST_LABEL) || 
				regex.equals(WISHLIST_SHOW_LABEL) || regex.equals(FAIL_LABEL) || 
				regex.equals(CURRENT_TRACK_LABEL) || regex.equals(NAME_CURRENT_TRACK_LABEL) || 
				regex.equals(LINK_TEXT) || regex.equals(ADD_BUTTON) || 
				regex.equals(DISCONNECT_BUTTON) || regex.equals(VOTE_BUTTON) || 
				regex.equals(REMOVE_BUTTON) || regex.equals(WISHLIST_PANE)) {
			return true; }
		else
			return false;
	}

	@Override
	public void layoutContainer(Container parent) {
		int width = parent.getSize().width;
		int height = parent.getSize().height;
		int inset = 10;
		int hSpacer = (int) (width*0.025);
		int vSpacer = (int) (height*0.025);
		int labelHeight = (int) (height*0.0375);
		int voteButtonY = (int) ((int)(height*0.275) + 2*vSpacer + (int)(height*0.525) + labelHeight);
		
		/***********************Labels***********************************/
		if (components.get(GAPLIST_LABEL) != null)
			components.get(GAPLIST_LABEL).setBounds(inset, inset, (int)(width*0.325), labelHeight);
		
		if (components.get(COUNT_GAPLIST_LABEL) != null)
			components.get(COUNT_GAPLIST_LABEL).setBounds(inset + (int)(width*0.325)+5, inset, (int)(width*0.125), labelHeight);
		
		if (components.get(WISHLIST_LABEL) != null)
			components.get(WISHLIST_LABEL).setBounds(inset, inset + labelHeight + 5, (int)(width*0.325), labelHeight);
		
		if (components.get(COUNT_WISHLIST_LABEL)!= null)
			components.get(COUNT_WISHLIST_LABEL).setBounds(inset + (int)(width*0.325) + 5, inset + labelHeight + 5, (int)(width*0.125), labelHeight);
		
		if (components.get(WISHLIST_SHOW_LABEL) != null)
			components.get(WISHLIST_SHOW_LABEL).setBounds(inset, (int)(height*0.275)+vSpacer+labelHeight, width-2*inset, labelHeight);
		
		if (components.get(CURRENT_TRACK_LABEL) != null)
			components.get(CURRENT_TRACK_LABEL).setBounds(inset, (int)(height*0.275), (int)(width*0.175), labelHeight);
		
		if (components.get(NAME_CURRENT_TRACK_LABEL) != null)
			components.get(NAME_CURRENT_TRACK_LABEL).setBounds(inset + (int)(width*0.175)+hSpacer, (int)(height*0.275), width-2*inset-(int)(width*0.175)-hSpacer, labelHeight);
		
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds((int)(width*0.1875) + inset + hSpacer, (int)(height*0.125) + (int)(height*0.05) + 5, width- ((int)(width*0.1875) + inset + hSpacer) - inset, (int)(height*0.0625));
		
		/***********************TextFields***************************/
		if (components.get(LINK_TEXT) != null)
			components.get(LINK_TEXT).setBounds(inset, (int)(height*0.125), (int)(width*0.4875), (int)(height*0.05));
		
		/**********************Buttons********************************/
		if (components.get(ADD_BUTTON) != null)
			components.get(ADD_BUTTON).setBounds(inset, (int)(height*0.125) + (int)(height*0.05) + 5, (int)(width*0.1875), (int)(height*0.0625));
		
		if (components.get(DISCONNECT_BUTTON) != null)
			components.get(DISCONNECT_BUTTON).setBounds(inset + (int)(width*0.325) + 5 + (int)(width*0.125) + hSpacer, inset, (int)(width - (2*inset + (int)(width*0.325) + 5 + (int)(width*0.125) + hSpacer)), 2*labelHeight + 5);
		
		if (components.get(VOTE_BUTTON) != null)
			components.get(VOTE_BUTTON).setBounds(inset, voteButtonY, (int)((width-2*inset)/2 - hSpacer/2), 2*labelHeight+5);
		
		if (components.get(REMOVE_BUTTON) != null)
			components.get(REMOVE_BUTTON).setBounds(inset + (int)((width-2*inset)/2 + hSpacer/2), voteButtonY, (int)((width-2*inset)/2 - hSpacer/2), 2*labelHeight + 5);
		
		/***********************Panes*******************************/
		if (components.get(WISHLIST_PANE) != null)
			components.get(WISHLIST_PANE).setBounds(inset, (int)(height*0.275) + 2*vSpacer + 2*labelHeight, width-2*inset, (int)(height*0.525)-labelHeight-vSpacer);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(300,300);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(400,400);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp))
			remove(comp);
	}

	/**
	 * Removes the Component from the Layout.
	 * @param comp	The Component to be removed.
	 * @since 1.0
	 */
	private void remove(Component comp) {
		if (components.get(GAPLIST_LABEL) != null) 
			if (components.get(GAPLIST_LABEL).equals(comp)) {
				components.put(GAPLIST_LABEL, null);
				return;
			}
		
		if (components.get(COUNT_GAPLIST_LABEL) != null)
			if (components.get(COUNT_GAPLIST_LABEL).equals(comp)) {
				components.put(COUNT_GAPLIST_LABEL, null);
				return;
			}
		
		if (components.get(WISHLIST_LABEL) != null)
			if (components.get(WISHLIST_LABEL).equals(comp)) {
				components.put(WISHLIST_LABEL, null);
				return;
			}
		
		if (components.get(COUNT_WISHLIST_LABEL) != null)
			if (components.get(COUNT_WISHLIST_LABEL).equals(comp)) {
				components.put(COUNT_WISHLIST_LABEL, null);
				return;
			}
		
		if (components.get(WISHLIST_SHOW_LABEL) != null)
			if (components.get(WISHLIST_SHOW_LABEL).equals(comp)) {
				components.put(WISHLIST_SHOW_LABEL, null);
				return;
			}
		
		if (components.get(FAIL_LABEL) != null)
			if (components.get(FAIL_LABEL).equals(comp)) {
				components.put(FAIL_LABEL, null);
				return;
			}
		
		if (components.get(CURRENT_TRACK_LABEL) != null)
			if (components.get(CURRENT_TRACK_LABEL).equals(comp)) {
				components.put(CURRENT_TRACK_LABEL, null);
				return;
			}
		
		if (components.get(NAME_CURRENT_TRACK_LABEL) != null)
			if (components.get(NAME_CURRENT_TRACK_LABEL).equals(comp)) {
				components.put(NAME_CURRENT_TRACK_LABEL, null);
				return;
			}
		
		if (components.get(LINK_TEXT) != null)
			if (components.get(LINK_TEXT).equals(comp)) {
				components.put(LINK_TEXT, null);
				return;
			}
		
		if (components.get(ADD_BUTTON) != null)
			if (components.get(ADD_BUTTON).equals(comp)) {
				components.put(ADD_BUTTON, null);
				return;
			}
		
		if (components.get(DISCONNECT_BUTTON) != null)
			if (components.get(DISCONNECT_BUTTON).equals(comp)) {
				components.put(DISCONNECT_BUTTON, null);
				return;
			}
		
		if (components.get(VOTE_BUTTON) != null)
			if (components.get(VOTE_BUTTON).equals(comp)) {
				components.put(VOTE_BUTTON, null);
				return;
			}
		
		if (components.get(REMOVE_BUTTON) != null)
			if (components.get(REMOVE_BUTTON).equals(comp)) {
				components.put(REMOVE_BUTTON, null);
				return;
			}
		
		if (components.get(WISHLIST_PANE) != null)
			if (components.get(WISHLIST_PANE).equals(comp)) {
				components.put(WISHLIST_PANE, null);
				return;
			}
	}
}
