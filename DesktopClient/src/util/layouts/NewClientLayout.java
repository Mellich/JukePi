package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * The Layout for the AdminClient.
 * @author Haeldeus
 * @version 1.0
 */
public class NewClientLayout implements LayoutManager{

	/**
	 * The String, that declares the Gaplist_Label.
	 */
	public static final String GAPLIST_LABEL = "Gaplist_Label";

	/**
	 * The String, that declares the Wishlist_Label.
	 */
	public static final String WISHLIST_LABEL = "Wishlist_Label";

	/**
	 * The String, that declares the Count_Gaplist_Label.
	 */
	public static final String COUNT_GAPLIST_LABEL = "Count_Gaplist_Label";

	/**
	 * The String, that declares the Count_Wishlist_Label.
	 */
	public static final String COUNT_WISHLIST_LABEL = "Count_Wishlist_Label";

	/**
	 * The String, that declares the Now_Playing_Label.
	 */
	public static final String NOW_PLAYING_LABEL = "Now_Playing_Label";

	/**
	 * The String, that declares the Next_Track_Label.
	 */
	public static final String NEXT_TRACK_LABEL = "Next_Track_Label";

	/**
	 * The String that declares, the Name_Now_Playing_Label.
	 */
	public static final String NAME_NOW_PLAYING_LABEL = "Name_Now_Playing_Label";

	/**
	 * The String, that declares the Name_Next_Track_Label.
	 */
	public static final String NAME_NEXT_TRACK_LABEL = "Name_Next_Track_Label";
	
	/**
	 * The String, that declares the Gaplist_Name_Label.
	 */
	public static final String GAPLIST_NAME_LABEL = "Gaplist_Name_Label";
	
	/**
	 * The String, that declares the Wishlist_Name_Label.
	 */
	public static final String WISHLIST_NAME_LABEL = "Wishlist_Name_Label";
	
	/**
	 * The String, that declares the Build_Version_Label.
	 */
	public static final String BUILD_VERSION_LABEL = "Build_Version_Label";

	/**
	 * The String, that declares the Fail_Label.
	 */
	public static final String FAIL_LABEL = "Fail_Label";

	/**
	 * The String, that declares the Add_Button.
	 */
	public static final String ADD_BUTTON = "Add_Button";

	/**
	 * The String, that declares the Seek_Back_Button.
	 */
	public static final String SEEK_BACK_BUTTON = "Seek_Back_Button";

	/**
	 * The String, that declares the Play_Pause_Button.
	 */
	public static final String PLAY_PAUSE_BUTTON = "Play_Pause_Button";

	/**
	 * The String, that declares the Seek_Forward_Button.
	 */
	public static final String SEEK_FORWARD_BUTTON = "Seek_Forward_Button";

	/**
	 * The String, that declares the Skip_Button.
	 */
	public static final String SKIP_BUTTON = "Skip_Button";
	
	/**
	 * The String, that declares the Save_Button.
	 */
	public static final String SAVE_BUTTON = "Save_Button";
	
	/**
	 * The String, that declares the Vote_Button.
	 */
	public static final String VOTE_BUTTON = "Vote_Button";
	
	/**
	 * The String, that declares the Track_Up_Button.
	 */
	public static final String TRACK_UP_BUTTON = "Track_Up_Button";
	
	/**
	 * The String, that declares the Track_Down_Button.
	 */
	public static final String TRACK_DOWN_BUTTON = "Track_Down_Button";
	
	/**
	 * The String, that declares the Gaplist_Pane.
	 */
	public static final String GAPLIST_PANE = "Gaplist_Pane";
	
	/**
	 * The String, that declares the Wishlist_Pane.
	 */
	public static final String WISHLIST_PANE = "Wishlist_Pane";
	
	/**
	 * The String, that declares the Debug_Pane.
	 */
	public static final String DEBUG_PANE = "Debug_Pane";
	
	/**
	 * The String, that declares the Link_Textfield.
	 */
	public static final String LINK_TEXTFIELD = "Link_Textfield";
	
	/**
	 * The String, that declares the Gaplist_Radiobutton.
	 */
	public static final String GAPLIST_RADIO = "Gaplist_Radio";
	
	/**
	 * The String, that declares the Wishlist_Radiobutton.
	 */
	public static final String WISHLIST_RADIO = "Wishlist_Radio";
	
	/**
	 * The String, that declares the In_Front_Checkbox.
	 */
	public static final String IN_FRONT_CHECKBOX = "In_Front_Checkbox";
	
	/**
	 * The String, that declares the Menu_Bar.
	 */
	public static final String MENU_BAR = "Menu_Bar";
	
	/**
	 * All components, that are used by this Layout as a {@link HashMap} of {@link String} and
	 * {@link Component}.
	 */
	private HashMap<String, Component> components;
	
	/**
	 * The Constructor for this Layout.
	 * @since 1.0
	 */
	public NewClientLayout() {
		components = new HashMap<String, Component>();
	}
	
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (isLayoutComponent(regex)) {
			components.put(regex, comp);
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		final int height = parent.getHeight();
		final int width = parent.getWidth();
		
		final int lblHeight = (int)(height*0.02);
		final int spacer = 10;
		final int manButtonHeight = 42;
		final int manButtonWidth = (int)(width*0.2);
		final int buttonHeight = 22;
		
		/***********************************Labels***************************************/
		if (components.get(GAPLIST_LABEL) != null)
			components.get(GAPLIST_LABEL).setBounds(10, 20 + 10, Math.min((int)(width*0.205),123), lblHeight);
		
		if (components.get(WISHLIST_LABEL) != null)
			components.get(WISHLIST_LABEL).setBounds(10, 20 + 10 + (int)(height*0.037), Math.min((int)(width*0.205), 123), lblHeight);
		
		if (components.get(COUNT_GAPLIST_LABEL) != null)
			components.get(COUNT_GAPLIST_LABEL).setBounds(10+Math.min((int)(width*0.205),123)+spacer, 20 + 10, (int)(width*0.1133), lblHeight);
		
		if (components.get(COUNT_WISHLIST_LABEL) != null)
			components.get(COUNT_WISHLIST_LABEL).setBounds(10+Math.min((int)(width*0.205),123)+spacer, 20 + 10 + (int)(height*0.037),(int)(width*0.1133),lblHeight);
		
		if (components.get(NOW_PLAYING_LABEL) != null)
			components.get(NOW_PLAYING_LABEL).setBounds(10, 20 + (int)(height*0.205), Math.min(68,(int)(width*0.1133)),lblHeight);
		
		if (components.get(NEXT_TRACK_LABEL) != null)
			components.get(NEXT_TRACK_LABEL).setBounds(10, 20 + (int)(height*0.241), Math.min(68,(int)(width*0.1133)),lblHeight);
		
		if (components.get(NAME_NOW_PLAYING_LABEL) != null)
			components.get(NAME_NOW_PLAYING_LABEL).setBounds(Math.min(68,(int)(width*0.1133)) + 10 + spacer, 20 + (int)(height*0.205), (int)(width*0.6406), lblHeight);
		
		if (components.get(NAME_NEXT_TRACK_LABEL) != null)
			components.get(NAME_NEXT_TRACK_LABEL).setBounds(Math.min(68,(int)(width*0.1133)) + 10 + spacer, 20 + (int)(height*0.241), (int)(width*0.6406), lblHeight);

		if (components.get(GAPLIST_NAME_LABEL) != null)
			components.get(GAPLIST_NAME_LABEL).setBounds(10, 20 + (int)(height*0.277) + manButtonHeight + 10 + lblHeight + 10, (width-20)/2-55, lblHeight);

		if (components.get(WISHLIST_NAME_LABEL) != null)
			components.get(WISHLIST_NAME_LABEL).setBounds(10 + ((width-20)/2) + 5, (int)(components.get(GAPLIST_NAME_LABEL).getBounds().getY()), (width-20)/2 - 5, lblHeight);
		
		if (components.get(BUILD_VERSION_LABEL) != null)
			components.get(BUILD_VERSION_LABEL).setBounds(10 + ((width-20)/2) + 5, components.get(GAPLIST_LABEL).getY(), (width-20)/2 - 5, lblHeight);
		
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds(10, 20 + (int)(height*0.277) + manButtonHeight + 10, width-20, lblHeight);
		
		/*****************Buttons********************/
		if (components.get(ADD_BUTTON) != null)
			components.get(ADD_BUTTON).setBounds(10, 20 + (int)(height*0.13), (int)(width*0.1), (int)(height*0.04));
		
		if (components.get(SEEK_BACK_BUTTON) != null)
			components.get(SEEK_BACK_BUTTON).setBounds(10, 20 + (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		if (components.get(PLAY_PAUSE_BUTTON) != null)
			components.get(PLAY_PAUSE_BUTTON).setBounds(manButtonWidth+10+spacer, 20 + (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		if (components.get(SEEK_FORWARD_BUTTON) != null)
			components.get(SEEK_FORWARD_BUTTON).setBounds((int)(width*0.533), 20 + (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		if (components.get(SKIP_BUTTON) != null)
			components.get(SKIP_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, 20 + (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		int paneY = components.get(GAPLIST_NAME_LABEL).getY() + lblHeight + spacer;
		int buttonY = paneY + (height - 10 - paneY - 22);
		
		if (components.get(SAVE_BUTTON) != null)
			components.get(SAVE_BUTTON).setBounds(10, buttonY, (width-20)/2-55, buttonHeight);
		
		if (components.get(VOTE_BUTTON) != null)
			components.get(VOTE_BUTTON).setBounds(10 + ((width-20)/2) + 5, buttonY, (width-20)/2-5, buttonHeight);
		
		if (components.get(TRACK_UP_BUTTON) != null)
			components.get(TRACK_UP_BUTTON).setBounds(10 + (width-20)/2-50, paneY + (height - 10 - paneY - 32)/2 - 30, 40, 25);
		
		if (components.get(TRACK_DOWN_BUTTON) != null)
			components.get(TRACK_DOWN_BUTTON).setBounds(10 + (width-20)/2-50, paneY + (height - 10 - paneY - 32)/2 +5, 40, 25);

		/*****************Scroll-Panes********************/
		if (components.get(GAPLIST_PANE) != null)
			components.get(GAPLIST_PANE).setBounds(10, paneY, (width-20)/2-55, height - 10 - paneY - 32);
		
		if (components.get(WISHLIST_PANE) != null)
			components.get(WISHLIST_PANE).setBounds(10 + ((width-20)/2) + 5, paneY, (width-20)/2 - 5, height - 10 - paneY - 32);
		
		if (components.get(DEBUG_PANE) != null)
			components.get(DEBUG_PANE).setBounds(10 + ((width-20)/2) + 5, components.get(WISHLIST_LABEL).getY(), (width-20)/2 - 5, components.get(NEXT_TRACK_LABEL).getY() - (40 + buttonHeight));
		
		/*******************TextFields*******************/
		if (components.get(LINK_TEXTFIELD) != null)
			components.get(LINK_TEXTFIELD).setBounds(10, components.get(WISHLIST_LABEL).getY() + lblHeight + 10, (width-20)/2-55, 20);
		
		/*******************Radio-Buttons*****************/
		int radioY = components.get(ADD_BUTTON).getY() + (components.get(ADD_BUTTON).getHeight())/4;
		if (components.get(GAPLIST_RADIO) != null) 
			components.get(GAPLIST_RADIO).setBounds(10 + (int)(width*0.1) + 10, radioY, 70, lblHeight);
		
		if (components.get(WISHLIST_RADIO) != null)
			components.get(WISHLIST_RADIO).setBounds(10 + (int)(width*0.1) + 10 + 70, radioY, 75, lblHeight);
		
		/*******************Checkboxes**********************/
		if (components.get(IN_FRONT_CHECKBOX) != null)
			components.get(IN_FRONT_CHECKBOX).setBounds(10 + (int)(width*0.1) + 10 + 70 + 75, radioY, 70, lblHeight);
		
		/*******************Menu***************************/
		if (components.get(MENU_BAR) != null)
			components.get(MENU_BAR).setBounds(0, 0, width, 20);
		
//		System.out.println("Height - Width: " + height + " " + width);
//		System.out.println("GLP: " + components.get(GAPLIST_PANE).getBounds());
//		System.out.println("TUB: " + components.get(TRACK_UP_BUTTON).getBounds());
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return new Dimension(300,300);
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		return new Dimension(600,700);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp))
			remove(comp);
	}

	/**
	 * Returns the boolean value, if the given String defines a Component.
	 * @param regex	The String, that will be checked, if it defines a Component.
	 * @return	{@code true}, if {@code regex} defines a Component, {@code false} else.
	 * @since 1.0
	 */
	private boolean isLayoutComponent(String regex) {
		if (regex.equals(GAPLIST_LABEL) || regex.equals(WISHLIST_LABEL) || 
				regex.equals(COUNT_GAPLIST_LABEL) || regex.equals(COUNT_WISHLIST_LABEL) || 
				regex.equals(NOW_PLAYING_LABEL) || regex.equals(NEXT_TRACK_LABEL) || 
				regex.equals(NAME_NOW_PLAYING_LABEL) || regex.equals(NAME_NEXT_TRACK_LABEL) || 
				regex.equals(GAPLIST_NAME_LABEL) || regex.equals(WISHLIST_NAME_LABEL) || 
				regex.equals(BUILD_VERSION_LABEL) || regex.equals(FAIL_LABEL) || 
				regex.equals(ADD_BUTTON) || regex.equals(SEEK_BACK_BUTTON) || 
				regex.equals(PLAY_PAUSE_BUTTON) || regex.equals(SEEK_FORWARD_BUTTON) || 
				regex.equals(SKIP_BUTTON) || regex.equals(SAVE_BUTTON) || 
				regex.equals(VOTE_BUTTON) || regex.equals(TRACK_UP_BUTTON) || 
				regex.equals(TRACK_DOWN_BUTTON) || regex.equals(GAPLIST_PANE) || 
				regex.equals(WISHLIST_PANE) || regex.equals(DEBUG_PANE) || 
				regex.equals(LINK_TEXTFIELD) || regex.equals(GAPLIST_RADIO) || 
				regex.equals(WISHLIST_RADIO) || regex.equals(IN_FRONT_CHECKBOX) || 
				regex.equals(MENU_BAR))
			return true;
		else
			return false;
	}
	
	/**
	 * Removes the given Component from the Layout.
	 * @param comp	The Component to be removed.
	 * @since 1.0
	 */
	private void remove(Component comp) {
		if (components.get(GAPLIST_LABEL) != null) 
			if (components.get(GAPLIST_LABEL).equals(comp)) {
				components.put(GAPLIST_LABEL, null);
				return;
			}
		
		if (components.get(WISHLIST_LABEL) != null)
			if (components.get(WISHLIST_LABEL).equals(comp)) {
				components.put(WISHLIST_LABEL, null);
				return;
			}
		
		if (components.get(COUNT_GAPLIST_LABEL) != null)
			if (components.get(COUNT_GAPLIST_LABEL).equals(comp)) {
				components.put(COUNT_GAPLIST_LABEL, null);
				return;
			}
		
		if (components.get(COUNT_WISHLIST_LABEL) != null)
			if (components.get(COUNT_WISHLIST_LABEL) != null) {
				components.put(COUNT_WISHLIST_LABEL, null);
				return;
			}
		
		if (components.get(NOW_PLAYING_LABEL) != null)
			if (components.get(NOW_PLAYING_LABEL).equals(comp)) {
				components.put(NOW_PLAYING_LABEL, null);
				return;
			}
		
		if (components.get(NEXT_TRACK_LABEL) != null)
			if (components.get(NEXT_TRACK_LABEL).equals(comp)) {
				components.put(NEXT_TRACK_LABEL, null);
				return;
			}
		
		if (components.get(NAME_NOW_PLAYING_LABEL) != null)
			if (components.get(NAME_NOW_PLAYING_LABEL).equals(comp)) {
				components.put(NAME_NOW_PLAYING_LABEL, null);
				return;
			}
		
		if (components.get(NAME_NEXT_TRACK_LABEL) != null)
			if (components.get(NAME_NEXT_TRACK_LABEL).equals(comp)) {
				components.put(NAME_NEXT_TRACK_LABEL, null);
				return;
			}
		
		if (components.get(GAPLIST_NAME_LABEL) != null)
			if (components.get(GAPLIST_NAME_LABEL).equals(comp)) {
				components.put(GAPLIST_NAME_LABEL, null);
				return;
			}
		
		if (components.get(WISHLIST_NAME_LABEL) != null)
			if (components.get(WISHLIST_NAME_LABEL).equals(comp)) {
				components.put(WISHLIST_NAME_LABEL, null);
				return;
			}
		
		if (components.get(BUILD_VERSION_LABEL) != null)
			if (components.get(BUILD_VERSION_LABEL).equals(comp)) {
				components.put(BUILD_VERSION_LABEL, null);
				return;
			}
		
		if (components.get(FAIL_LABEL) != null)
			if (components.get(FAIL_LABEL).equals(comp)) {
				components.put(FAIL_LABEL, null);
				return;
			}
		
		if (components.get(ADD_BUTTON) != null)
			if (components.get(ADD_BUTTON).equals(comp)) {
				components.put(ADD_BUTTON, null);
				return;
			}
		
		if (components.get(SEEK_BACK_BUTTON) != null)
			if (components.get(SEEK_BACK_BUTTON).equals(comp)) {
				components.put(SEEK_BACK_BUTTON, null);
				return;
			}
		
		if (components.get(PLAY_PAUSE_BUTTON) != null)
			if (components.get(PLAY_PAUSE_BUTTON).equals(comp)) {
				components.put(PLAY_PAUSE_BUTTON, null);
				return;
			}
		
		if (components.get(SEEK_FORWARD_BUTTON) != null)
			if (components.get(SEEK_FORWARD_BUTTON).equals(comp)) {
				components.put(SEEK_FORWARD_BUTTON, null);
				return;
			}
		
		if (components.get(SKIP_BUTTON) != null)
			if (components.get(SKIP_BUTTON).equals(comp)) {
				components.put(SKIP_BUTTON, null);
				return;
			}
		
		if (components.get(SAVE_BUTTON) != null)
			if (components.get(SAVE_BUTTON).equals(comp)) {
				components.put(SAVE_BUTTON, null);
				return;
			}
		
		if (components.get(VOTE_BUTTON) != null)
			if (components.get(VOTE_BUTTON).equals(comp)) {
				components.put(VOTE_BUTTON, null);
				return;
			}
		
		if (components.get(TRACK_UP_BUTTON) != null)
			if (components.get(TRACK_UP_BUTTON).equals(comp)) {
				components.put(TRACK_UP_BUTTON, null);
				return;
			}
		
		if (components.get(TRACK_DOWN_BUTTON) != null)
			if (components.get(TRACK_DOWN_BUTTON).equals(comp)) {
				components.put(TRACK_DOWN_BUTTON, null);
				return;
			}
		
		if (components.get(GAPLIST_PANE) != null)
			if (components.get(GAPLIST_PANE).equals(comp)) {
				components.put(GAPLIST_PANE, null);
				return;
			}
		
		if (components.get(WISHLIST_PANE) != null)
			if (components.get(WISHLIST_PANE).equals(comp)) {
				components.put(WISHLIST_PANE, null);
				return;
			}
		
		if (components.get(DEBUG_PANE) != null)
			if (components.get(DEBUG_PANE).equals(comp)) {
				components.put(DEBUG_PANE, null);
				return;
			}
		
		if (components.get(LINK_TEXTFIELD) != null)
			if (components.get(LINK_TEXTFIELD).equals(comp)) {
				components.put(LINK_TEXTFIELD, null);
				return;
			}
		
		if (components.get(GAPLIST_RADIO) != null)
			if (components.get(GAPLIST_RADIO).equals(comp)) {
				components.put(GAPLIST_RADIO, null);
				return;
			}
		
		if (components.get(WISHLIST_RADIO) != null)
			if (components.get(WISHLIST_RADIO).equals(comp)) {
				components.put(WISHLIST_RADIO, null);
				return;
			}
		
		if (components.get(IN_FRONT_CHECKBOX) != null)
			if (components.get(IN_FRONT_CHECKBOX).equals(comp)) {
				components.put(IN_FRONT_CHECKBOX, null);
				return;
			}
		
		if (components.get(MENU_BAR) != null)
			if (components.get(MENU_BAR).equals(comp)) {
				components.put(MENU_BAR, null);
				return;
			}
	}
}