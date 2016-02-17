package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * <p>The Layout for the AdminClient.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #NewClientLayout()}:
 * 				Creates a new Instance of this Layout.</li>
 * 		
 * 			<li>{@link #addLayoutComponent(String, Component)}:
 * 				Adds the given {@link Component} to {@link #components} with the given 
 * 				{@code String} as it's Key.</li>
 * 
 * 			<li>{@link #layoutContainer(Container)}:
 * 				This Method is called whenever the Layout has to be resized. It will place the 
 * 				{@link Component}s in {@link #components} on their belonging spot on the Frame 
 * 				and optimizes their size.</li>
 * 
 * 			<li>{@link #minimumLayoutSize(Container)}:
 * 				This Method returns the minimum Size, this Layout needs.</li>
 * 
 * 			<li>{@link #preferredLayoutSize(Container)}:
 * 				This Method returns the Size, this Layouts prefers to occupy.</li>
 * 
 * 			<li>{@link #removeLayoutComponent(Component)}:
 * 				The given {@link Component} will be removed from the Layout by this Method.
 * 				</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #isLayoutComponent(String)}:
 * 				Checks, if the given {@code String} is a Key-Word for a {@link Component} of 
 * 				this Layout.</li>
 * 
 * 			<li>{@link #remove(Component)}:
 * 				Removes the given {@link Component} from the Layout.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * <h3>Fields:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #ADD_BUTTON}:
 * 				The {@code String}, that defines the Add_Button.
 * 
 * 			<li>{@link #COUNT_GAPLIST_LABEL}:
 * 				The {@code String}, that defines the Count_Gaplist_Label.
 * 
 * 			<li>{@link #COUNT_WISHLIST_LABEL}:
 * 				The {@code String}, that defines the Count_Wishlist_Label.
 * 
 * 			<li>{@link #DEBUG_PANE}:
 * 				The {@code String}, that defines the Debug_Pane.
 * 
 * 			<li>{@link #FAIL_LABEL}:
 * 				The {@code String}, that defines the Fail_Label.
 * 
 * 			<li>{@link #GAPLIST_LABEL}:
 * 				The {@code String}, that defines the Gaplist_Label.
 * 
 * 			<li>{@link #GAPLIST_NAME_LABEL}:
 * 				The {@code String}, that defines the Gaplist_Name_Label.
 * 
 * 			<li>{@link #GAPLIST_PANE}:
 * 				The {@code String}, that defines the Gaplist_Pane.
 * 
 * 			<li>{@link #GAPLIST_RADIO}:
 * 				The {@code String}, that defines the Gaplist_Radiobutton.
 * 
 * 			<li>{@link #IN_FRONT_CHECKBOX}:
 * 				The {@code String}, that defines the In_Front_Checkbox.
 * 
 * 			<li>{@link #LINK_TEXTFIELD}:
 * 				The {@code String}, that defines the Link_TextField.
 * 
 * 			<li>{@link #MENU_BAR}:
 * 				The {@code String}, that defines the Menu_Bar.
 * 
 * 			<li>{@link #NAME_NEXT_TRACK_LABEL}:
 * 				The {@code String}, that defines the Name_Next_Track_Label.
 * 
 * 			<li>{@link #NAME_NOW_PLAYING_LABEL}:
 * 				The {@code String}, that defines the Name_Now_Playing_Label.
 * 
 * 			<li>{@link #NEXT_TRACK_LABEL}:
 * 				The {@code String}, that defines the Next_Track_Label.
 * 
 * 			<li>{@link #NOW_PLAYING_LABEL}:
 * 				The {@code String}, that defines the Now_Playing_Label.
 * 
 * 			<li>{@link #PLAY_PAUSE_BUTTON}:
 * 				The {@code String}, that defines the Play_Pause_Button.
 * 
 * 			<li>{@link #SAVE_BUTTON}:
 * 				The {@code String}, that defines the Save_Button.
 * 		
 * 			<li>{@link #SEEK_BACK_BUTTON}:
 * 				The {@code String}, that defines the Seek_Back_Button.
 * 
 * 			<li>{@link #SEEK_FORWARD_BUTTON}:
 * 				The {@code String}, that defines the Seek_Forward_Button.
 * 
 * 			<li>{@link #SKIP_BUTTON}:
 * 				The {@code String}, that defines the Skip_Button.
 * 
 * 			<li>{@link #TRACK_DOWN_BUTTON}:
 * 				The {@code String}, that defines the Track_Down_Button.
 * 
 * 			<li>{@link #TRACK_UP_BUTTON}:
 * 				The {@code String}, that defines the Track_Up_Button.
 * 
 * 			<li>{@link #VOTE_BUTTON}:
 * 				The {@code String}, that defines the Vote_Button.
 * 
 * 			<li>{@link #WISHLIST_LABEL}:
 * 				The {@code String}, that defines the Wishlist_Label.
 * 
 * 			<li>{@link #WISHLIST_NAME_LABEL}:
 * 				The {@code String}, that defines the Wishlist_Name_Label.
 * 
 * 			<li>{@link #WISHLIST_PANE}:
 * 				The {@code String}, that defines the Wishlist_Pane.
 * 
 * 			<li>{@link #WISHLIST_RADIO}:
 * 				The {@code String}, that defines the Wishlist_Radiobutton.
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #components}:
 * 				All {@link Component}s, that were added to this Layout as a {@link HashMap} of 
 * 				{@code String}s and given {@link Component}s.
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class NewClientLayout implements LayoutManager{

	/**
	 * <p style="margin-left: 10px"><em><b>GAPLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String GAPLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Gaplist_Label.</p>
	 */
	public static final String GAPLIST_LABEL = "Gaplist_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>WISHLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String WISHLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Wishlist_Label.</p>
	 */
	public static final String WISHLIST_LABEL = "Wishlist_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>COUNT_GAPLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String COUNT_GAPLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Count_Gaplist_Label.
	 * </p>
	 */
	public static final String COUNT_GAPLIST_LABEL = "Count_Gaplist_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>COUNT_WISHLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String COUNT_WISHLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Count_Wishlist_Label.
	 * </p>
	 */
	public static final String COUNT_WISHLIST_LABEL = "Count_Wishlist_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>NOW_PLAYING_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String NOW_PLAYING_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Now_Playing_Label.
	 * </p>
	 */
	public static final String NOW_PLAYING_LABEL = "Now_Playing_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>NEXT_TRACK_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String NEXT_TRACK_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Next_Track_Label.</p>
	 */
	public static final String NEXT_TRACK_LABEL = "Next_Track_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>NAME_NOW_PLAYING_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String NAME_NOW_PLAYING_LABEL}
	 * </p>
	 * <p style="margin-left: 20px">The {@code String} that declares the 
	 * Name_Now_Playing_Label.</p>
	 */
	public static final String NAME_NOW_PLAYING_LABEL = "Name_Now_Playing_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>NAME_NEXT_TRACK_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String NAME_NEXT_TRACK_LABEL}
	 * </p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the 
	 * Name_Next_Track_Label.</p>
	 */
	public static final String NAME_NEXT_TRACK_LABEL = "Name_Next_Track_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>GAPLIST_NAME_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String GAPLIST_NAME_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Gaplist_Name_Label.
	 * </p>
	 */
	public static final String GAPLIST_NAME_LABEL = "Gaplist_Name_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>WISHLIST_NAME_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String WISHLIST_NAME_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Wishlist_Name_Label.
	 * </p>
	 */
	public static final String WISHLIST_NAME_LABEL = "Wishlist_Name_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>FAIL_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String FAIL_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Fail_Label.</p>
	 */
	public static final String FAIL_LABEL = "Fail_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>ADD_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String ADD_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Add_Button.</p>
	 */
	public static final String ADD_BUTTON = "Add_Button";

	/**
	 * <p style="margin-left: 10px"><em><b>SEEK_BACK_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String SEEK_BACK_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Seek_Back_Button.</p>
	 */
	public static final String SEEK_BACK_BUTTON = "Seek_Back_Button";

	/**
	 * <p style="margin-left: 10px"><em><b>PLAY_PAUSE_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String PLAY_PAUSE_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Play_Pause_Button.
	 * </p>
	 */
	public static final String PLAY_PAUSE_BUTTON = "Play_Pause_Button";

	/**
	 * <p style="margin-left: 10px"><em><b>SEEK_FORWARD_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String SEEK_FORWARD_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Seek_Forward_Button.
	 * </p>
	 */
	public static final String SEEK_FORWARD_BUTTON = "Seek_Forward_Button";

	/**
	 * <p style="margin-left: 10px"><em><b>SKIP_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String SKIP_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Skip_Button.</p>
	 */
	public static final String SKIP_BUTTON = "Skip_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>SAVE_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String SAVE_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Save_Button.</p>
	 */
	public static final String SAVE_BUTTON = "Save_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>VOTE_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String VOTE_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Vote_Button.</p>
	 */
	public static final String VOTE_BUTTON = "Vote_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>TRACK_UP_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String TRACK_UP_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Track_Up_Button.</p>
	 */
	public static final String TRACK_UP_BUTTON = "Track_Up_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>TRACK_DOWN_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String TRACK_DOWN_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Track_Down_Button.
	 * </p>
	 */
	public static final String TRACK_DOWN_BUTTON = "Track_Down_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>GAPLIST_PANE</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String GAPLIST_PANE}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Gaplist_Pane.</p>
	 */
	public static final String GAPLIST_PANE = "Gaplist_Pane";
	
	/**
	 * <p style="margin-left: 10px"><em><b>WISHLIST_PANE</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String WISHLIST_PANE}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Wishlist_Pane.</p>
	 */
	public static final String WISHLIST_PANE = "Wishlist_Pane";
	
	/**
	 * <p style="margin-left: 10px"><em><b>DEBUG_PANE</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String DEBUG_PANE}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Debug_Pane.</p>
	 */
	public static final String DEBUG_PANE = "Debug_Pane";
	
	/**
	 * <p style="margin-left: 10px"><em><b>LINK_TEXTFIELD</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String LINK_TEXTFIELD}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Link_Textfield.</p>
	 */
	public static final String LINK_TEXTFIELD = "Link_Textfield";
	
	/**
	 * <p style="margin-left: 10px"><em><b>GAPLIST_RADIO</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String GAPLIST_RADIO}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Gaplist_Radiobutton.
	 * </p>
	 */
	public static final String GAPLIST_RADIO = "Gaplist_Radio";
	
	/**
	 * <p style="margin-left: 10px"><em><b>WISHLIST_RADIO</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String WISHLIST_RADIO}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Wishlist_Radiobutton.
	 * </p>
	 */
	public static final String WISHLIST_RADIO = "Wishlist_Radio";
	
	/**
	 * <p style="margin-left: 10px"><em><b>IN_FRONT_CHECKBOX</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String IN_FRONT_CHECKBOX}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the In_Front_Checkbox.
	 * </p>
	 */
	public static final String IN_FRONT_CHECKBOX = "In_Front_Checkbox";
	
	/**
	 * <p style="margin-left: 10px"><em><b>MENU_BAR</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String MENU_BAR}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Menu_Bar.</p>
	 */
	public static final String MENU_BAR = "Menu_Bar";
	
	/**
	 * <p style="margin-left: 10px"><em><b>components</b></em></p>
	 * <p style="margin-left: 20px">{@code private HashMap<String, Component> components}</p>
	 * <p style="margin-left: 20px">All components, that are used by this Layout as a 
	 * {@link HashMap} of {@link String}s and {@link Component}s.</p>
	 */
	private HashMap<String, Component> components;
	
	/**
	 * <p style="margin-left: 10px"><em><b>NewClientLayout</b></em></p>
	 * <p style="margin-left: 20px">{@code public NewClientLayout()}</p>
	 * <p style="margin-left: 20px">The Constructor for this Layout.</p>
	 * @since 1.0
	 */
	public NewClientLayout() {
		components = new HashMap<String, Component>();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>addLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code public void addLayoutComponent()}</p>
	 * <p style="margin-left: 20px">Adds the given {@link Component} {@code comp} to the 
	 * Layout, if {@code regex} is a legal {@code String} Key for this Layout.</p>
	 * @param regex	The {@code String}, that will be checked, if it is a legal Key for this 
	 * 				Layout. If yes, it will be the Key for {@code comp}.
	 * @param comp	The {@link Component} to be added.
	 * @since 1.0
	 */
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (isLayoutComponent(regex))
			components.put(regex, comp);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>layoutContainer</b></em></p>
	 * <p style="margin-left: 20px">{@code public void layoutContainer(Container)}</p>
	 * <p style="margin-left: 20px">Lays out the Components in the given {@link Container}.</p>
	 * @param parent	The {@link Container}, this Layout will be laid out in.
	 * @since 1.0
	 */
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
			components.get(GAPLIST_LABEL).setBounds(10, 20 + 10, 123, lblHeight);
		
		if (components.get(WISHLIST_LABEL) != null)
			components.get(WISHLIST_LABEL).setBounds(10, 20+10+10+lblHeight, 123, lblHeight);
		
		if (components.get(COUNT_GAPLIST_LABEL) != null)
			components.get(COUNT_GAPLIST_LABEL).setBounds(
					components.get(GAPLIST_LABEL).getX() 
					+ components.get(GAPLIST_LABEL).getWidth() + spacer, 20 + 10, 
					(int)(width*0.1133), lblHeight);
		
		if (components.get(COUNT_WISHLIST_LABEL) != null)
			components.get(COUNT_WISHLIST_LABEL).setBounds(
					components.get(WISHLIST_LABEL).getX() 
					+ components.get(WISHLIST_LABEL).getWidth() + spacer, 
					components.get(WISHLIST_LABEL).getY(),(int)(width*0.1133),lblHeight);
		
		if (components.get(NOW_PLAYING_LABEL) != null)
			components.get(NOW_PLAYING_LABEL).setBounds(10, 20 + (int)(height*0.205), 
					Math.min(68,(int)(width*0.1133)),lblHeight);
		
		if (components.get(NEXT_TRACK_LABEL) != null)
			components.get(NEXT_TRACK_LABEL).setBounds(10, 20 + (int)(height*0.241), 
					Math.min(68,(int)(width*0.1133)),lblHeight);
		
		if (components.get(NAME_NOW_PLAYING_LABEL) != null)
			components.get(NAME_NOW_PLAYING_LABEL).setBounds(
					Math.min(68,(int)(width*0.1133)) + 10 + spacer, 20 + (int)(height*0.205), 
					(int)(width*0.6406), lblHeight);
		
		if (components.get(NAME_NEXT_TRACK_LABEL) != null)
			components.get(NAME_NEXT_TRACK_LABEL).setBounds(
					Math.min(68,(int)(width*0.1133)) + 10 + spacer, 20 + (int)(height*0.241), 
					(int)(width*0.6406), lblHeight);

		if (components.get(GAPLIST_NAME_LABEL) != null)
			components.get(GAPLIST_NAME_LABEL).setBounds(10, 
					20 + (int)(height*0.277) + manButtonHeight + 10 + lblHeight + 10, 
					(width-20)/2-55, lblHeight);

		if (components.get(WISHLIST_NAME_LABEL) != null)
			components.get(WISHLIST_NAME_LABEL).setBounds(10 + ((width-20)/2) + 5, 
					(int)(components.get(GAPLIST_NAME_LABEL).getBounds().getY()), 
					(width-20)/2 - 5, lblHeight);
		
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds(10, 
					20 + (int)(height*0.277) + manButtonHeight + 10, width-20, lblHeight);
		
		/*****************Buttons********************/
		if (components.get(ADD_BUTTON) != null)
			components.get(ADD_BUTTON).setBounds(10, 20 + (int)(height*0.13), 
					(int)(width*0.1), (int)(height*0.04));
		
		if (components.get(SEEK_BACK_BUTTON) != null)
			components.get(SEEK_BACK_BUTTON).setBounds(10, 20 + (int)(height*0.277), 
					manButtonWidth, manButtonHeight);
		
		if (components.get(PLAY_PAUSE_BUTTON) != null)
			components.get(PLAY_PAUSE_BUTTON).setBounds(manButtonWidth+10+spacer, 
					20 + (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		if (components.get(SEEK_FORWARD_BUTTON) != null)
			components.get(SEEK_FORWARD_BUTTON).setBounds((int)(width*0.533), 
					20 + (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		if (components.get(SKIP_BUTTON) != null)
			components.get(SKIP_BUTTON).setBounds((int)(width*0.533)+manButtonWidth+spacer, 
					20 + (int)(height*0.277), manButtonWidth, manButtonHeight);
		
		int paneY = components.get(GAPLIST_NAME_LABEL).getY() + lblHeight + spacer;
		int buttonY = paneY + (height - 10 - paneY - 22);
		
		if (components.get(SAVE_BUTTON) != null)
			components.get(SAVE_BUTTON).setBounds(10, buttonY, (width-20)/2-55, buttonHeight);
		
		if (components.get(VOTE_BUTTON) != null)
			components.get(VOTE_BUTTON).setBounds(10 + ((width-20)/2) + 5, buttonY, 
					(width-20)/2-5, buttonHeight);
		
		if (components.get(TRACK_UP_BUTTON) != null)
			components.get(TRACK_UP_BUTTON).setBounds(10 + (width-20)/2-50, 
					paneY + (height - 10 - paneY - 32)/2 - 30, 40, 25);
		
		if (components.get(TRACK_DOWN_BUTTON) != null)
			components.get(TRACK_DOWN_BUTTON).setBounds(10 + (width-20)/2-50, 
					paneY + (height - 10 - paneY - 32)/2 +5, 40, 25);

		/*****************Scroll-Panes********************/
		if (components.get(GAPLIST_PANE) != null)
			components.get(GAPLIST_PANE).setBounds(10, paneY, (width-20)/2-55, 
					height - 10 - paneY - 32);
		
		if (components.get(WISHLIST_PANE) != null)
			components.get(WISHLIST_PANE).setBounds(10 + ((width-20)/2) + 5, paneY, 
					(width-20)/2 - 5, height - 10 - paneY - 32);
		
		if (components.get(DEBUG_PANE) != null)
			components.get(DEBUG_PANE).setBounds(10 + ((width-20)/2) + 5, 
					components.get(GAPLIST_LABEL).getY(), (width-20)/2 - 5, 
					components.get(NOW_PLAYING_LABEL).getY() - (40 + buttonHeight));
		
		/*******************TextFields*******************/
		if (components.get(LINK_TEXTFIELD) != null)
			components.get(LINK_TEXTFIELD).setBounds(10, 
					components.get(WISHLIST_LABEL).getY() + lblHeight + 10, 
					(width-20)/2-55, 20);
		
		/*******************Radio-Buttons*****************/
		int radioY = components.get(ADD_BUTTON).getY() + 
				(components.get(ADD_BUTTON).getHeight())/4;
		
		if (components.get(GAPLIST_RADIO) != null) 
			components.get(GAPLIST_RADIO).setBounds(10 + (int)(width*0.1) + 10, radioY, 70, 
					lblHeight);
		
		if (components.get(WISHLIST_RADIO) != null)
			components.get(WISHLIST_RADIO).setBounds(10 + (int)(width*0.1) + 10 + 70, radioY, 
					75, lblHeight);
		
		/*******************Checkboxes**********************/
		if (components.get(IN_FRONT_CHECKBOX) != null)
			components.get(IN_FRONT_CHECKBOX).setBounds(10 + (int)(width*0.1) + 10 + 70 + 75, 
					radioY, 70, lblHeight);
		
		/*******************Menu***************************/
		if (components.get(MENU_BAR) != null)
			components.get(MENU_BAR).setBounds(0, 0, width, 20);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>minimumLayoutSize</b></em></p>
	 * <p style="margin-left: 20px">{@code public Dimension minimumLayoutSize(Container)}</p>
	 * <p style="margin-left: 20px">Returns the minimum Size, this Layout occupies in the 
	 * given {@link Container}.</p>
	 * @param parent	The Container, in which the Layout shall be laid out.
	 * @return	A new Dimension with the fixed Size 300x300.
	 * @since 1.0
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(300,300);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>preferredLayoutSize</b></em></p>
	 * <p style="margin-left: 20px">{@code public Dimension preferredLayoutSize(Container)}</p>
	 * <p style="margin-left: 20px">Returns the preferred Size, this Layout occupies in the 
	 * given {@link Container}.</p>
	 * @param parent	The Container, in which the Layout shall be laid out.
	 * @return	A new Dimension with the fixed Size 600x700.
	 * @since 1.0
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(600,700);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>removeLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code public void removeLayoutComponent(Component)}</p>
	 * <p style="margin-left: 20px">Removes the given {@link Component} from the Layout, if it 
	 * was added before.</p>
	 * @param comp	The {@link Component} to be removed.
	 * @since 1.0
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp))
			remove(comp);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>isLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean isLayoutComponent(String)}</p>
	 * <p style="margin-left: 20px">Returns the boolean value, if the given {@code String} 
	 * defines a Component.</p>
	 * @param regex	The {@code String}, that will be checked, if it defines a Component.
	 * @return	{@code true}, if {@code regex} defines a Component, {@code false} else.
	 * @since 1.0
	 */
	private boolean isLayoutComponent(String regex) {
		if (regex.equals(GAPLIST_LABEL) || regex.equals(WISHLIST_LABEL) || 
				regex.equals(COUNT_GAPLIST_LABEL) || regex.equals(COUNT_WISHLIST_LABEL) || 
				regex.equals(NOW_PLAYING_LABEL) || regex.equals(NEXT_TRACK_LABEL) || 
				regex.equals(NAME_NOW_PLAYING_LABEL) || regex.equals(NAME_NEXT_TRACK_LABEL) || 
				regex.equals(GAPLIST_NAME_LABEL) || regex.equals(WISHLIST_NAME_LABEL) || 
				regex.equals(FAIL_LABEL) || regex.equals(ADD_BUTTON) || 
				regex.equals(SEEK_BACK_BUTTON) || regex.equals(PLAY_PAUSE_BUTTON) || 
				regex.equals(SEEK_FORWARD_BUTTON) || regex.equals(SKIP_BUTTON) || 
				regex.equals(SAVE_BUTTON) || regex.equals(VOTE_BUTTON) || 
				regex.equals(TRACK_UP_BUTTON) || regex.equals(TRACK_DOWN_BUTTON) || 
				regex.equals(GAPLIST_PANE) || regex.equals(WISHLIST_PANE) || 
				regex.equals(DEBUG_PANE) || regex.equals(LINK_TEXTFIELD) || 
				regex.equals(GAPLIST_RADIO) || regex.equals(WISHLIST_RADIO) || 
				regex.equals(IN_FRONT_CHECKBOX) || regex.equals(MENU_BAR))
			return true;
		else
			return false;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>remove</b></em></p>
	 * <p style="margin-left: 20px">{@code private void remove(Component)}</p>
	 * <p style="margin-left: 20px">Removes the given Component from the Layout.</p>
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