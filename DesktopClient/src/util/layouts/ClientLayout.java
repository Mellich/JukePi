package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * The {@link LayoutManager} for the Client.
 * @author Haeldeus
 * @version 1.0
 *
 */
public class ClientLayout implements LayoutManager{
	
	/**
	 * The String, that declares the Menu_Bar, which is the Menu for the Client.
	 */
	public static final String MENU_BAR = "Menu_Bar";
	
	/**
	 * The String, that declares the Gaplist_Label, which displays {@code "Tracks in the 
	 * Gaplist:"}.
	 */
	public static final String GAPLIST_LABEL = "Gaplist_Label";
	
	/**
	 * The String, that declares the Wishlist_Label, which displays {@code "Tracks in the 
	 * Wishlist:"}.
	 */
	public static final String WISHLIST_LABEL = "Wishlist_Label";
	
	/**
	 * The String, that declares the Now_Playing_Label, which displays {@code "Now Playing:"}.
	 */
	public static final String NOW_PLAYING_LABEL = "Now_Playing_Label";
	
	/**
	 * The String, that declares the Next_Track_Label, which displays {@code "Next Track:"}
	 */
	public static final String NEXT_TRACK_LABEL = "Next_Track_Label";
	
	/**
	 * The String, that declares the Count_Gaplist_Label, which displays the current amount of 
	 * Tracks in the Gaplist.
	 * @see windows.MainWindow#lblNoGaplist
	 */
	public static final String COUNT_GAPLIST_LABEL = "Count_Gaplist_Label";
	
	/**
	 * The String, that declares the Count_Wishlist_Label, which displays the current amount of
	 * Track in the Wishlist.
	 * @see windows.MainWindow#lblNoWishlist
	 */
	public static final String COUNT_WISHLIST_LABEL = "Count_Wishlist_Label";
	
	/**
	 * The String, that declares the Name_Now_Playing_Label, which displays the name of the 
	 * current Track.
	 * @see windows.MainWindow#lblPlayingTrack
	 */
	public static final String NAME_NOW_PLAYING_LABEL = "Name_Now_Playing_Label";
	
	/**
	 * The String, that declares the Name_Next_Track_Label, which displays the name of the 
	 * next Track.
	 * @see windows.MainWindow#lblTrackNext
	 */
	public static final String NAME_NEXT_TRACK_LABEL = "Name_Next_Track_Label";
	
	/**
	 * The String, that declares the Gaplist_Name_Label, which displays the Name of the current
	 * Gaplist.
	 * @see windows.MainWindow#lblGaplistName
	 */
	public static final String GAPLIST_NAME_LABEL = "Gaplist_Name_Label";
	
	/**
	 * The String, that declares the Wishlist_Show_Label, which is the Header of the 
	 * ScrollPane, that contains the Wishlist.
	 */
	public static final String WISHLIST_SHOW_LABEL = "Wishlist_Show_Label";
	
	/**
	 * The String, that declares the Saved_Gaplist_Label, which is the Header of the
	 * ScrollPane, that contains all saved Gaplists on the Server.
	 */
	public static final String SAVED_GAPLIST_LABEL = "Saved_Gaplist_Label";
	
	/**
	 * The String, that declares the Fail_Label, which will display responses from the Server.
	 */
	public static final String FAIL_LABEL = "Fail_Label";
	
	
	/**
	 * The String, that declares the Add_Button.
	 */
	public static final String ADD_BUTTON = "Add_Button";
	
	/**
	 * The String, that declares the Disconnect_Button.
	 */
	public static final String DISCONNECT_BUTTON = "Disconnect_Button";
	
	/**
	 * The String, that declares the Debug_Button.
	 */
	public static final String DEBUG_BUTTON = "Debug_Button";
	
	/**
	 * The String, that declares the Seek_Back_Button.
	 */
	public static final String SEEK_BACK_BUTTON = "Seek_Back_Button";
	
	/**
	 * The String, that declares the Play_Pause_Button.
	 * @see windows.MainWindow#btnPlayPause
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
	 * The String, that declares the Track_Up_Button.
	 */
	public static final String TRACK_UP_BUTTON = "Track_Up_Button";
	
	/**
	 * The String, that declares the Track_Down_Button.
	 */
	public static final String TRACK_DOWN_BUTTON = "Track_Down_Button";
	
	/**
	 * The String, that declares the Delete_Button.
	 */
	public static final String DELETE_BUTTON = "Delete_Button";
	
	/**
	 * The String, that declares the Save_Button.
	 */
	public static final String SAVE_BUTTON = "Save_Button";
	
	/**
	 * The String, that declares the Vote_Button.
	 */
	public static final String VOTE_BUTTON = "Vote_Button";
	
	/**
	 * The String, that declares the Remove_Vote_Button.
	 */
	public static final String REMOVE_VOTE_BUTTON = "Remove_Vote_Button";
	
	/**
	 * The String, that declares the Remove_All_Votes_Button.
	 */
	public static final String REMOVE_ALL_VOTES_BUTTON = "Remove_All_Votes_Button";
	
	/**
	 * The String, that declares the Load_Button.
	 */
	public static final String LOAD_BUTTON = "Load_Button";
	
	/**
	 * The String, that declares the Show_Button.
	 */
	public static final String SHOW_BUTTON = "Show_Button";
	
	/**
	 * The String, that declares the Remove_Button.
	 */
	public static final String REMOVE_BUTTON = "Remove_Button";
	
	/**
	 * The String, that declares the Create_Button.
	 */
	public static final String CREATE_BUTTON = "Create_Button";
	
	
	/**
	 * The String, that declares the Link_Textfield, which will be used to add Videos to the
	 * Jukebox.
	 * @see windows.MainWindow#txtLink
	 */
	public static final String LINK_TEXT = "Link_Text";
	
	/**
	 * The String, that declares the Gaplist_Textfield, which will be used to name new 
	 * Gaplists.
	 */
	public static final String GAPLIST_TEXT = "Gaplist_Text";
	
	
	/**
	 * The String, that declares the Gaplist_RadioButton.
	 */
	public static final String GAPLIST_RADIO = "Gaplist_Radio";
	
	/**
	 * The String, that declares the Wishlist_RadioButton.
	 */
	public static final String WISHLIST_RADIO = "Wishlist_Radio";
	
	
	/**
	 * The String, that declares the Front_CheckBox.
	 */
	public static final String FRONT_CHECK = "Front_Check";
	
	
	/**
	 * The String, that declares the ScrollPane for the current Gaplist.
	 */
	public static final String GAPLIST_SCROLL = "Gaplist_Scroll";
	
	/**
	 * The String, that declares the ScrollPane for the current Wishlist.
	 */
	public static final String WISHLIST_SCROLL = "Wishlist_Scroll";
	
	/**
	 * The String, that declares the ScrollPane for all saved Gaplists on the Server.
	 */
	public static final String SAVED_GAPLIST_SCROLL = "Saved_Gaplist_Scroll";
	
	/**
	 * The String, that declares the ScrollPane for the Content of the current shown Gaplist.
	 */
	public static final String CONTENT_SCROLL = "Content_Scroll";
	
	/**
	 * A {@link HashMap} of all Components, that were added to the Layout.
	 * @see Component
	 */
	private HashMap<String, Component> components;
	
	/**
	 * The Constructor for the Layout.
	 * @since 1.0
	 */
	public ClientLayout() {
		components = new HashMap<String, Component>();
	}
	
	@Override
	public void addLayoutComponent(String componentPosition, Component Component) {
		if (checkLayoutComponent(componentPosition))
			components.put(componentPosition, Component);
	}

	@Override
	public void layoutContainer(Container parent) {
		
		final int height = parent.getHeight();
		final int width = parent.getWidth();
		
		final int lblHeight = (int)(height*0.02);
		final int spacer = (int)(width*0.016);
		
	//	final int voteSpacer = (int)(width*1/75);
	//	final int voteButtonWidth = (int)(width*0.13);
		
		final int manButtonHeight = (int)(height*0.064);
		final int manButtonWidth = (int)(width*0.2);
		final int buttonHeight = (int)(height*0.032);
		
		final int secondButtonY = (int)(height*0.624);
		final int thirdButtonY = (int)(height*0.91);
		
		/***********************************Labels***************************************/
		if (components.get(GAPLIST_LABEL) != null)
			components.get(GAPLIST_LABEL).setBounds(10, 20 + 10, Math.min((int)(width*0.205),123), lblHeight);
		
		if(components.get(WISHLIST_LABEL) != null)
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
			components.get(GAPLIST_NAME_LABEL).setBounds(10, 20 + (int)(height*0.428), (int)(width*0.416), lblHeight);
		
		if (components.get(WISHLIST_SHOW_LABEL) != null)
			components.get(WISHLIST_SHOW_LABEL).setBounds((int)(width*0.533), 20 + (int)(height*0.428), (int)(width*0.416), lblHeight);
		
		if (components.get(SAVED_GAPLIST_LABEL) != null)
			components.get(SAVED_GAPLIST_LABEL).setBounds(10, 20 + (int)(height*0.714), (int)(width*0.416), lblHeight);
		
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds((int)(width*0.29), 20 + (int)(height*0.4), (int)(width*19.0/60.0), lblHeight);
		
		/**************************Buttons**************************/
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
		
		if (components.get(DISCONNECT_BUTTON) != null)
			components.get(DISCONNECT_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, 20 + 10, manButtonWidth, buttonHeight);
		
		if (components.get(DEBUG_BUTTON) != null)
			components.get(DEBUG_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, 20 + 10 + (int)(height*0.032+height*0.01), manButtonWidth, buttonHeight);

		if (components.get(DELETE_BUTTON) != null)
			components.get(DELETE_BUTTON).setBounds(10, 20 + secondButtonY, manButtonWidth, buttonHeight);
		
		if (components.get(SAVE_BUTTON) != null)
			components.get(SAVE_BUTTON).setBounds(10+manButtonWidth+spacer, 20 + secondButtonY, manButtonWidth, buttonHeight);
		
		if (components.get(TRACK_UP_BUTTON) != null)
			components.get(TRACK_UP_BUTTON).setBounds(10+2*manButtonWidth+spacer+2, 20 + (int)(height*0.487), Math.min(40, (int)(width*0.067)), Math.min(25,(int)(height*0.036)));
		
		if (components.get(TRACK_DOWN_BUTTON) != null)
			components.get(TRACK_DOWN_BUTTON).setBounds(10+2*manButtonWidth+spacer+2, 20 + (int)(height*0.56), Math.min(40, (int)(width*0.067)), Math.min(25,(int)(height*0.036)));
		
		if (components.get(LOAD_BUTTON) != null)
			components.get(LOAD_BUTTON).setBounds(10, 20 + thirdButtonY, (int)((2*manButtonWidth+spacer)*0.3), buttonHeight);
		
		if (components.get(SHOW_BUTTON) != null)
			components.get(SHOW_BUTTON).setBounds(10+(int)((2*manButtonWidth+spacer)*0.3)+spacer, 20 + thirdButtonY, (int)((2*manButtonWidth+spacer)*0.3), buttonHeight);
		
		if (components.get(REMOVE_BUTTON) != null)
			components.get(REMOVE_BUTTON).setBounds(2*(int)((2*manButtonWidth+spacer)*0.3)+10+2*spacer, 20 + thirdButtonY, (int)((2*manButtonWidth+spacer+10)-(2*(int)((2*manButtonWidth+spacer)*0.3)+10+2*spacer)), buttonHeight);
		
		if (components.get(CREATE_BUTTON) != null) 
			components.get(CREATE_BUTTON).setBounds((int)(width*0.533), 20 + thirdButtonY, (int)(width*2/15), buttonHeight);
		
		if (components.get(VOTE_BUTTON) != null)
			components.get(VOTE_BUTTON).setBounds((int)(width*0.533), 20 + secondButtonY, manButtonWidth, buttonHeight);
		
		if (components.get(REMOVE_VOTE_BUTTON) != null)
			components.get(REMOVE_VOTE_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, 20 + secondButtonY, manButtonWidth, buttonHeight);
		
		if (components.get(REMOVE_ALL_VOTES_BUTTON) != null)
			components.get(REMOVE_ALL_VOTES_BUTTON).setBounds((int)(width*0.533), 20 + secondButtonY+buttonHeight+spacer, 2*manButtonWidth+spacer, buttonHeight);
		
		/************************************Panes**********************************/
		if (components.get(GAPLIST_SCROLL) != null)
			components.get(GAPLIST_SCROLL).setBounds(10, 20 + (int)(height*0.468), 2*manButtonWidth+spacer, height/7);
		
		if (components.get(WISHLIST_SCROLL) != null)
			components.get(WISHLIST_SCROLL).setBounds((int)(width*0.533), 20 + (int)(height*0.468), 2*manButtonWidth+spacer, height/7);
		
		if (components.get(SAVED_GAPLIST_SCROLL) != null)
			components.get(SAVED_GAPLIST_SCROLL).setBounds(10, 20 + (int)(height*0.754), 2*manButtonWidth+spacer, height/7);
		
		if (components.get(CONTENT_SCROLL) != null)
			components.get(CONTENT_SCROLL).setBounds((int)(width*0.533), 20 + (int)(height*0.753), 2*manButtonWidth+spacer, height/7);
		
		/******************************TextFields********************************/
		if (components.get(LINK_TEXT) != null)
			components.get(LINK_TEXT).setBounds(10, 20 + (int)(height*3/35), (int)(width*0.533)-10, (int)(height/35));
		
		if (components.get(GAPLIST_TEXT) != null)
			components.get(GAPLIST_TEXT).setBounds((int)(width*0.533)+(int)(width*2/15)+spacer, 20 + thirdButtonY, (2*manButtonWidth)-((int)(width*2/15)), buttonHeight);
		
		/*****************************RadioButtons********************************/
		if (components.get(WISHLIST_RADIO) != null)
			components.get(WISHLIST_RADIO).setBounds((int)(width*13/100), 20 + (int)(height*9/70), (int)(width*0.125), buttonHeight);
		
		if (components.get(GAPLIST_RADIO) != null)
			components.get(GAPLIST_RADIO).setBounds((int)(width*13/100)+(int)(width*0.125)+2, 20 + (int)(height*9/70), (int)(width*0.125), buttonHeight);
		
		/***************************CheckBox***********************************/
		if (components.get(FRONT_CHECK) != null)
			components.get(FRONT_CHECK).setBounds((int)(width*13/100)+(int)(width*0.25)+4, 20 + (int)(height*9/70), (int)(width*97/600), buttonHeight);

		/***************************MenuBar**************************************/
		if (components.get(MENU_BAR) != null)
			components.get(MENU_BAR).setBounds(0, 0, width, 20);
		
		
		if (components.get(CONTENT_SCROLL)!= null) {
			util.IO.println(this, "Content Scroll Bounds: " + components.get(CONTENT_SCROLL).getBounds().toString());
			util.IO.println(this, "Content Scroll: " + components.get(CONTENT_SCROLL).hashCode());
		}
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
	 * Checks, if the given String is the key for a Component in the components-HashMap
	 * @param regex		The String, that will be checked, if it's a key in the HashMap.
	 * @return 	true, if the String is a key, false else.
	 * @since 1.0
	 * @see ClientLayout#components
	 */
	private boolean checkLayoutComponent(String regex) {
		if ( regex.equals(GAPLIST_LABEL) || regex.equals(WISHLIST_LABEL) || 
				regex.equals(NOW_PLAYING_LABEL) || regex.equals(NEXT_TRACK_LABEL) ||
				regex.equals(COUNT_GAPLIST_LABEL) || regex.equals(COUNT_WISHLIST_LABEL) ||
				regex.equals(NAME_NOW_PLAYING_LABEL) || regex.equals(NAME_NEXT_TRACK_LABEL) ||
				regex.equals(GAPLIST_NAME_LABEL) || regex.equals(WISHLIST_SHOW_LABEL) ||
				regex.equals(SAVED_GAPLIST_LABEL) || regex.equals(FAIL_LABEL) || 
				regex.equals(ADD_BUTTON) || regex.equals(DISCONNECT_BUTTON) || 
				regex.equals(DEBUG_BUTTON) || regex.equals(SEEK_BACK_BUTTON) || 
				regex.equals(PLAY_PAUSE_BUTTON) || regex.equals(SEEK_FORWARD_BUTTON) || 
				regex.equals(SKIP_BUTTON) || regex.equals(TRACK_UP_BUTTON) || 
				regex.equals(TRACK_DOWN_BUTTON) || regex.equals(DELETE_BUTTON) || 
				regex.equals(SAVE_BUTTON) || regex.equals(VOTE_BUTTON) || 
				regex.equals(REMOVE_VOTE_BUTTON) || regex.equals(REMOVE_ALL_VOTES_BUTTON) || 
				regex.equals(LOAD_BUTTON) || regex.equals(SHOW_BUTTON) || 
				regex.equals(REMOVE_BUTTON) || regex.equals(CREATE_BUTTON) || 
				regex.equals(LINK_TEXT) || regex.equals(GAPLIST_TEXT) || 
				regex.equals(GAPLIST_RADIO) || regex.equals(WISHLIST_RADIO) || 
				regex.equals(FRONT_CHECK) || regex.equals(GAPLIST_SCROLL) || 
				regex.equals(WISHLIST_SCROLL) || regex.equals(SAVED_GAPLIST_SCROLL) || 
				regex.equals(CONTENT_SCROLL) || regex.equals(MENU_BAR))	
			return true;
		else
			return false;
	}
	
	/**
	 * Removes a Component from the Layout and replaces it with {@code null}.
	 * @param comp	The Component to be removed.
	 * @since 1.0
	 * @see Component
	 */
	private void remove(Component comp) {
		if (components.get(MENU_BAR) != null)
			if (components.get(MENU_BAR).equals(comp)) {
				components.put(MENU_BAR, null);
				return;
			}
		
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
		
		if (components.get(COUNT_GAPLIST_LABEL) != null)
			if (components.get(COUNT_GAPLIST_LABEL).equals(comp)) {
				components.put(COUNT_GAPLIST_LABEL, null);
				return;
			}
		
		if (components.get(COUNT_WISHLIST_LABEL) != null)
			if (components.get(COUNT_WISHLIST_LABEL).equals(comp)) {
				components.put(COUNT_WISHLIST_LABEL, null);
				return;
			}
		
		if (components.get(NAME_NOW_PLAYING_LABEL) != null)
			if (components.get(NAME_NOW_PLAYING_LABEL).equals(comp)) {
				components.put(NAME_NOW_PLAYING_LABEL, null);
				return;
			}
		
		if (components.get(NAME_NEXT_TRACK_LABEL) != null)
			if (components.get(NAME_NEXT_TRACK_LABEL).equals(comp)) {
				components.put(WISHLIST_LABEL, null);
				return;
			}
		
		if (components.get(GAPLIST_NAME_LABEL) != null)
			if (components.get(GAPLIST_NAME_LABEL).equals(comp)) {
				components.put(GAPLIST_NAME_LABEL, null);
				return;
			}
		
		if (components.get(WISHLIST_SHOW_LABEL) != null)
			if (components.get(WISHLIST_SHOW_LABEL).equals(comp)) {
				components.put(WISHLIST_SHOW_LABEL, null);
				return;
			}
		
		if (components.get(SAVED_GAPLIST_LABEL) != null)
			if (components.get(SAVED_GAPLIST_LABEL).equals(comp)) {
				components.put(SAVED_GAPLIST_LABEL, null);
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
		
		if (components.get(DISCONNECT_BUTTON) != null)
			if (components.get(DISCONNECT_BUTTON).equals(comp)) {
				components.put(DISCONNECT_BUTTON, null);
				return;
			}
		
		if (components.get(DEBUG_BUTTON) != null)
			if (components.get(DEBUG_BUTTON).equals(comp)) {
				components.put(DEBUG_BUTTON, null);
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
		
		if (components.get(DELETE_BUTTON) != null)
			if (components.get(DELETE_BUTTON).equals(comp)) {
				components.put(DELETE_BUTTON, null);
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
		
		if (components.get(REMOVE_VOTE_BUTTON) != null)
			if (components.get(REMOVE_VOTE_BUTTON).equals(comp)) {
				components.put(REMOVE_VOTE_BUTTON, null);
				return;
			}
		
		if (components.get(REMOVE_ALL_VOTES_BUTTON) != null)
			if (components.get(REMOVE_ALL_VOTES_BUTTON).equals(comp)) {
				components.put(REMOVE_ALL_VOTES_BUTTON, null);
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
		
		if (components.get(REMOVE_BUTTON) != null)
			if (components.get(REMOVE_BUTTON).equals(comp)) {
				components.put(REMOVE_BUTTON, null);
				return;
			}
		
		if (components.get(CREATE_BUTTON) != null)
			if (components.get(CREATE_BUTTON).equals(comp)) {
				components.put(CREATE_BUTTON, null);
				return;
			}
		
		if (components.get(LINK_TEXT) != null)
			if (components.get(LINK_TEXT).equals(comp)) {
				components.put(LINK_TEXT, null);
				return;
			}
		
		if (components.get(GAPLIST_TEXT) != null)
			if (components.get(GAPLIST_TEXT).equals(comp)) {
				components.put(GAPLIST_TEXT, null);
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
		
		if (components.get(FRONT_CHECK) != null)
			if (components.get(FRONT_CHECK).equals(comp)) {
				components.put(FRONT_CHECK, null);
				return;
			}
		
		if (components.get(GAPLIST_SCROLL) != null)
			if (components.get(GAPLIST_SCROLL).equals(comp)) {
				components.put(GAPLIST_SCROLL, null);
				return;
			}
		
		if (components.get(WISHLIST_SCROLL) != null)
			if (components.get(WISHLIST_SCROLL).equals(comp)) {
				components.put(WISHLIST_SCROLL, null);
				return;
			}
		
		if (components.get(SAVED_GAPLIST_SCROLL) != null)
			if (components.get(SAVED_GAPLIST_SCROLL).equals(comp)) {
				components.put(SAVED_GAPLIST_SCROLL, null);
				return;
			}
		
		if (components.get(CONTENT_SCROLL) != null)
			if (components.get(CONTENT_SCROLL).equals(comp)) {
				util.IO.println(this, "Removed Content Scroll");
				components.put(CONTENT_SCROLL, null);
				return;
			}
	}
}
