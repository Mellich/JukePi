package util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

public class ClientLayout implements LayoutManager{
	
	public static final String GAPLIST_LABEL = "Gaplist_Label";
	public static final String WISHLIST_LABEL = "Wishlist_Label";
	public static final String NOW_PLAYING_LABEL = "Now_Playing_Label";
	public static final String NEXT_TRACK_LABEL = "Next_Track_Label";
	public static final String COUNT_GAPLIST_LABEL = "Count_Gaplist_Label";
	public static final String COUNT_WISHLIST_LABEL = "Count_Wishlist_Label";
	public static final String NAME_NOW_PLAYING_LABEL = "Name_Now_Playing_Label";
	public static final String NAME_NEXT_TRACK_LABEL = "Name_Next_Track_Label";
	public static final String GAPLIST_NAME_LABEL = "Gaplist_Name_Label";
	public static final String WISHLIST_SHOW_LABEL = "Wishlist_Show_Label";
	public static final String SAVED_GAPLIST_LABEL = "Saved_Gaplist_Label";
	
	public static final String ADD_BUTTON = "Add_Button";
	public static final String DISCONNECT_BUTTON = "Disconnect_Button";
	public static final String DEBUG_BUTTON = "Debug_Button";
	public static final String SEEK_BACK_BUTTON = "Seek_Back_Button";
	public static final String PLAY_PAUSE_BUTTON = "Play_Pause_Button";
	public static final String SEEK_FORWARD_BUTTON = "Seek_Forward_Button";
	public static final String SKIP_BUTTON = "Skip_Button";
	public static final String TRACK_UP_BUTTON = "Track_Up_Button";
	public static final String TRACK_DOWN_BUTTON = "Track_Down_Button";
	public static final String DELETE_BUTTON = "Delete_Button";
	public static final String SAVE_BUTTON = "Save_Button";
	public static final String VOTE_BUTTON = "Vote_Button";
	public static final String REMOVE_VOTE_BUTTON = "Remove_Vote_Button";
	public static final String LOAD_BUTTON = "Load_Button";
	public static final String SHOW_BUTTON = "Show_Button";
	public static final String REMOVE_BUTTON = "Remove_Button";
	public static final String CREATE_BUTTON = "Create_Button";
	
	public static final String LINK_TEXT = "Link_Text";
	public static final String GAPLIST_TEXT = "Gaplist_Text";
	
	public static final String GAPLIST_RADIO = "Gaplist_Radio";
	public static final String WISHLIST_RADIO = "Wishlist_Radio";
	
	public static final String FRONT_CHECK = "Front_Check";
	
	public static final String GAPLIST_SCROLL = "Gaplist_Scroll";
	public static final String WISHLIST_SCROLL = "Wishlist_Scroll";
	public static final String SAVED_GAPLIST_SCROLL = "Saved_Gaplist_Scroll";
	public static final String CONTENT_SCROLL = "Content_Scroll";
	
	private HashMap<String, Component> components;
/*	private int vgap;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;
*/	
	public ClientLayout() {
		components = new HashMap<String, Component>();
	//	vgap = 5;
	}
	
	@Override
	public void addLayoutComponent(String componentPosition, Component Component) {
		if (checkLayoutComponent(componentPosition))
			components.put(componentPosition, Component);
	}

	@Override
	public void layoutContainer(Container parent) {
		// TODO Auto-generated method stub
		
		int height = parent.getHeight();
		int width = parent.getWidth();
		
		int lblHeight = (int)(height*0.02);
		int spacer = (int)(width*0.016);
		
		int manButtonHeight = (int)(height*0.064);
		int manButtonWidth = (int)(width*0.2);
		int buttonHeight = (int)(height*0.032);
		
		int secondButtonY = (int)(height*0.624);
		int thirdButtonY = (int)(height*0.91);
		
		/***********************************Labels***************************************/
		
		components.get(GAPLIST_LABEL).setBounds(10, 10, Math.min((int)(width*0.205),123), lblHeight);
		
		components.get(WISHLIST_LABEL).setBounds(10, 10+ (int)(height*0.037), Math.min((int)(width*0.205), 123), lblHeight);
		
		components.get(COUNT_GAPLIST_LABEL).setBounds(10+Math.min((int)(width*0.205),123)+spacer, 10, (int)(width*0.1133), lblHeight);
		
		components.get(COUNT_WISHLIST_LABEL).setBounds(10+Math.min((int)(width*0.205),123)+spacer,10+(int)(height*0.037),(int)(width*0.1133),lblHeight);
		
		components.get(NOW_PLAYING_LABEL).setBounds(10, (int)(height*0.205), Math.min(68,(int)(width*0.1133)),lblHeight);
		
		components.get(NEXT_TRACK_LABEL).setBounds(10, (int)(height*0.241), Math.min(68,(int)(width*0.1133)),lblHeight);
		
		components.get(NAME_NOW_PLAYING_LABEL).setBounds(Math.min(68,(int)(width*0.1133)) + 10 + spacer, (int)(height*0.205), (int)(width*0.6406), lblHeight);
		
		components.get(NAME_NEXT_TRACK_LABEL).setBounds(Math.min(68,(int)(width*0.1133)) + 10 + spacer, (int)(height*0.241), (int)(width*0.6406), lblHeight);
		
		components.get(GAPLIST_NAME_LABEL).setBounds(10, (int)(height*0.428), (int)(width*0.416), lblHeight);
		
		components.get(WISHLIST_SHOW_LABEL).setBounds((int)(width*0.533), (int)(height*0.428), (int)(width*0.416), lblHeight);
		
		components.get(SAVED_GAPLIST_LABEL).setBounds(10, (int)(height*0.714), (int)(width*0.416), lblHeight);

		/**************************Buttons**************************/
		
		components.get(ADD_BUTTON).setBounds(10, (int)(height*0.13), (int)(width*0.1), (int)(height*0.04));
		
		components.get(SEEK_BACK_BUTTON).setBounds(10, (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		components.get(PLAY_PAUSE_BUTTON).setBounds(manButtonWidth+10+spacer, (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		components.get(SEEK_FORWARD_BUTTON).setBounds((int)(width*0.533), (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		components.get(SKIP_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		components.get(DISCONNECT_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, 10, manButtonWidth, buttonHeight);
		
		components.get(DEBUG_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, 10+(int)(height*0.032+height*0.01), manButtonWidth, buttonHeight);

		components.get(DELETE_BUTTON).setBounds(10, secondButtonY, manButtonWidth, buttonHeight);
		
		components.get(SAVE_BUTTON).setBounds(10+manButtonWidth+spacer, secondButtonY, manButtonWidth, buttonHeight);
		
		components.get(TRACK_UP_BUTTON).setBounds(10+2*manButtonWidth+spacer+2, (int)(height*0.487), Math.min(40, (int)(width*0.067)), Math.min(25,(int)(height*0.036)));
		
		components.get(TRACK_DOWN_BUTTON).setBounds(10+2*manButtonWidth+spacer+2, (int)(height*0.56), Math.min(40, (int)(width*0.067)), Math.min(25,(int)(height*0.036)));
		
		components.get(LOAD_BUTTON).setBounds(10, thirdButtonY, (int)((2*manButtonWidth+spacer)*0.3), buttonHeight);
		
		components.get(SHOW_BUTTON).setBounds(10+(int)((2*manButtonWidth+spacer)*0.3)+spacer, thirdButtonY, (int)((2*manButtonWidth+spacer)*0.3), buttonHeight);
		
		components.get(REMOVE_BUTTON).setBounds(2*(int)((2*manButtonWidth+spacer)*0.3)+10+2*spacer, thirdButtonY, (int)((2*manButtonWidth+spacer+10)-(2*(int)((2*manButtonWidth+spacer)*0.3)+10+2*spacer)), buttonHeight);
		
		components.get(CREATE_BUTTON).setBounds((int)(width*0.533), thirdButtonY, (int)(width*2/15), buttonHeight);
		
		components.get(VOTE_BUTTON).setBounds((int)(width*0.533), secondButtonY, manButtonWidth, buttonHeight);
		
		components.get(REMOVE_VOTE_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, secondButtonY, manButtonWidth, buttonHeight);
		
		/************************************Panes**********************************/
		components.get(GAPLIST_SCROLL).setBounds(10, (int)(height*0.468), 2*manButtonWidth+spacer, height/7);
		
		components.get(WISHLIST_SCROLL).setBounds((int)(width*0.533), (int)(height*0.468), 2*manButtonWidth+spacer, height/7);
		
		components.get(SAVED_GAPLIST_SCROLL).setBounds(10, (int)(height*0.754), 2*manButtonWidth+spacer, height/7);
		
		components.get(CONTENT_SCROLL).setBounds((int)(width*0.533), (int)(height*0.753), 2*manButtonWidth+spacer, height/7);
		
		/******************************TextFields********************************/
		components.get(LINK_TEXT).setBounds(10, (int)(height*3/35), (int)(width*0.533)-10, (int)(height/35));
		
		components.get(GAPLIST_TEXT).setBounds((int)(width*0.533)+(int)(width*2/15)+spacer, thirdButtonY, (2*manButtonWidth)-((int)(width*2/15)), buttonHeight);
		
		/*****************************RadioButtons********************************/
		components.get(WISHLIST_RADIO).setBounds((int)(width*13/100), (int)(height*9/70), (int)(width*0.125), buttonHeight);
		
		components.get(GAPLIST_RADIO).setBounds((int)(width*13/100)+(int)(width*0.125)+2, (int)(height*9/70), (int)(width*0.125), buttonHeight);
		
		/***************************CheckBox***********************************/
		components.get(FRONT_CHECK).setBounds((int)(width*13/100)+(int)(width*0.25)+4, (int)(height*9/70), (int)(width*97/600), buttonHeight);
		
		System.out.println((int)(width*13/100) + " + " +  (int)(width*0.25) + " + " + 4 + " = " + ((int)(width*13/100)+(int)(width*0.25)+4));
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

	private boolean checkLayoutComponent(String regex) {
		if ( regex.equals(GAPLIST_LABEL) || regex.equals(WISHLIST_LABEL) || 
				regex.equals(NOW_PLAYING_LABEL) || regex.equals(NEXT_TRACK_LABEL) ||
				regex.equals(COUNT_GAPLIST_LABEL) || regex.equals(COUNT_WISHLIST_LABEL) ||
				regex.equals(NAME_NOW_PLAYING_LABEL) || regex.equals(NAME_NEXT_TRACK_LABEL) ||
				regex.equals(GAPLIST_NAME_LABEL) || regex.equals(WISHLIST_SHOW_LABEL) ||
				regex.equals(SAVED_GAPLIST_LABEL) || regex.equals(ADD_BUTTON) ||
				regex.equals(DISCONNECT_BUTTON) || regex.equals(DEBUG_BUTTON) ||
				regex.equals(SEEK_BACK_BUTTON) || regex.equals(PLAY_PAUSE_BUTTON) ||
				regex.equals(SEEK_FORWARD_BUTTON) || regex.equals(SKIP_BUTTON) || 
				regex.equals(TRACK_UP_BUTTON) || regex.equals(TRACK_DOWN_BUTTON) ||
				regex.equals(DELETE_BUTTON) || regex.equals(SAVE_BUTTON) ||
				regex.equals(VOTE_BUTTON) || regex.equals(REMOVE_VOTE_BUTTON) || 
				regex.equals(LOAD_BUTTON) || regex.equals(SHOW_BUTTON) ||
				regex.equals(REMOVE_BUTTON) || regex.equals(CREATE_BUTTON) || 
				regex.equals(LINK_TEXT) || regex.equals(GAPLIST_TEXT) || 
				regex.equals(GAPLIST_RADIO) || regex.equals(WISHLIST_RADIO) || 
				regex.equals(FRONT_CHECK) || regex.equals(GAPLIST_SCROLL) ||
				regex.equals(WISHLIST_SCROLL) || regex.equals(SAVED_GAPLIST_SCROLL) ||
				regex.equals(CONTENT_SCROLL))
			
			return true;
		else
			return false;
	}
	
	private void remove(Component comp) {
		if (components.get(GAPLIST_LABEL).equals(comp))
			components.put(GAPLIST_LABEL, null);

		else if (components.get(WISHLIST_LABEL).equals(comp))
			components.put(WISHLIST_LABEL, null);
		
		else if (components.get(NOW_PLAYING_LABEL).equals(comp))
			components.put(NOW_PLAYING_LABEL, null);
		
		else if (components.get(NEXT_TRACK_LABEL).equals(comp))
			components.put(NEXT_TRACK_LABEL, null);
		
		else if (components.get(COUNT_GAPLIST_LABEL).equals(comp))
			components.put(COUNT_GAPLIST_LABEL, null);
		
		else if (components.get(COUNT_WISHLIST_LABEL).equals(comp))
			components.put(COUNT_WISHLIST_LABEL, null);
		
		else if (components.get(NAME_NOW_PLAYING_LABEL).equals(comp))
			components.put(NAME_NOW_PLAYING_LABEL, null);
		
		else if (components.get(NAME_NEXT_TRACK_LABEL).equals(comp))
			components.put(WISHLIST_LABEL, null);
		
		else if (components.get(GAPLIST_NAME_LABEL).equals(comp))
			components.put(GAPLIST_NAME_LABEL, null);
		
		else if (components.get(WISHLIST_SHOW_LABEL).equals(comp))
			components.put(WISHLIST_SHOW_LABEL, null);
		
		else if (components.get(SAVED_GAPLIST_LABEL).equals(comp))
			components.put(SAVED_GAPLIST_LABEL, null);
		
		else if (components.get(ADD_BUTTON).equals(comp))
			components.put(ADD_BUTTON, null);
		
		else if (components.get(DISCONNECT_BUTTON).equals(comp))
			components.put(DISCONNECT_BUTTON, null);
		
		else if (components.get(DEBUG_BUTTON).equals(comp))
			components.put(DEBUG_BUTTON, null);
		
		else if (components.get(SEEK_BACK_BUTTON).equals(comp))
			components.put(SEEK_BACK_BUTTON, null);
		
		else if (components.get(PLAY_PAUSE_BUTTON).equals(comp))
			components.put(PLAY_PAUSE_BUTTON, null);
		
		else if (components.get(SEEK_FORWARD_BUTTON).equals(comp))
			components.put(SEEK_FORWARD_BUTTON, null);
		
		else if (components.get(SKIP_BUTTON).equals(comp))
			components.put(SKIP_BUTTON, null);
		
		else if (components.get(TRACK_UP_BUTTON).equals(comp))
			components.put(TRACK_UP_BUTTON, null);
		
		else if (components.get(TRACK_DOWN_BUTTON).equals(comp))
			components.put(TRACK_DOWN_BUTTON, null);
		
		else if (components.get(DELETE_BUTTON).equals(comp))
			components.put(DELETE_BUTTON, null);
		
		else if (components.get(SAVE_BUTTON).equals(comp))
			components.put(SAVE_BUTTON, null);
		
		else if (components.get(VOTE_BUTTON).equals(comp))
			components.put(VOTE_BUTTON, null);
		
		else if (components.get(REMOVE_VOTE_BUTTON).equals(comp))
			components.put(REMOVE_VOTE_BUTTON, null);
		
		else if (components.get(LOAD_BUTTON).equals(comp))
			components.put(LOAD_BUTTON, null);
		
		else if (components.get(SHOW_BUTTON).equals(comp))
			components.put(SHOW_BUTTON, null);
		
		else if (components.get(REMOVE_BUTTON).equals(comp))
			components.put(REMOVE_BUTTON, null);
		
		else if (components.get(CREATE_BUTTON).equals(comp))
			components.put(CREATE_BUTTON, null);
		
		else if (components.get(LINK_TEXT).equals(comp))
			components.put(LINK_TEXT, null);
		
		else if (components.get(GAPLIST_TEXT).equals(comp))
			components.put(GAPLIST_TEXT, null);
		
		else if (components.get(GAPLIST_RADIO).equals(comp))
			components.put(GAPLIST_RADIO, null);
		
		else if (components.get(WISHLIST_RADIO).equals(comp))
			components.put(WISHLIST_RADIO, null);
		
		else if (components.get(FRONT_CHECK).equals(comp))
			components.put(FRONT_CHECK, null);
		
		else if (components.get(GAPLIST_SCROLL).equals(comp))
			components.put(GAPLIST_SCROLL, null);
		
		else if (components.get(WISHLIST_SCROLL).equals(comp))
			components.put(WISHLIST_SCROLL, null);
		
		else if (components.get(SAVED_GAPLIST_SCROLL).equals(comp))
			components.put(SAVED_GAPLIST_SCROLL, null);
		
		else if (components.get(CONTENT_SCROLL).equals(comp))
			components.put(CONTENT_SCROLL, null);
	}
}
