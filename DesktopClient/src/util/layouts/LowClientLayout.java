package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * <p>The Layout for the Desktop-Client without Admin-Permissions.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #LowClientLayout()}:
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
 * 				The {@code String}, that defines the Add_Button.</li>
 * 
 * 			<li>{@link #COUNT_GAPLIST_LABEL}:
 * 				The {@code String}, that defines the Count_Gaplist_Label.</li>
 * 
 * 			<li>{@link #COUNT_WISHLIST_LABEL}:
 * 				The {@code String}, that defines the Count_Wishlist_Label.</li>
 * 
 * 			<li>{@link #CURRENT_TRACK_LABEL}:
 * 				The {@code String}, that defines the Current_Track_Label.</li>
 * 
 * 			<li>{@link #CURRENT_TRACKNAME_LABEL}:
 * 				The {@code String}, that defines the Current_Trackname_Label.</li>
 * 
 * 			<li>{@link #DISCONNECT_BUTTON}:
 * 				The {@code String}, that defines the Disconnect_Button.</li>
 * 
 * 			<li>{@link #FAIL_LABEL}:
 * 				The {@code String}, that defines the Fail_Label.</li>
 * 
 * 			<li>{@link #GAPLIST_LABEL}:
 * 				The {@code String}, that defines the Gaplist_Label.</li>
 * 
 * 			<li>{@link #LINK_TEXT}:
 * 				The {@code String}, that defines the Link_TextField.</li>
 * 
 * 			<li>{@link #MENU_BAR}:
 * 				The {@code String}, that defines the Menu_Bar.</li>
 * 
 * 			<li>{@link #REMOVE_BUTTON}:
 * 				The {@code String}, that defines the Remove_Button.</li>
 * 
 * 			<li>{@link #VOTE_BUTTON}:
 * 				The {@code String}, that defines the Vote_Button.</li>
 * 
 * 			<li>{@link #WISHLIST_LABEL}:
 * 				The {@code String}, that defines the Wishlist_Label.</li>
 * 
 * 			<li>{@link #WISHLIST_PANE}:
 * 				The {@code String}, that defines the Wishlist_Pane.</li>
 * 
 * 			<li>{@link #WISHLIST_SHOW_LABEL}:
 * 				The {@code String}, that defines the Wishlist_Show_Label.</li>
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
 * 				The {@link HashMap}, that saves all {@link Component}s, this Layout uses with 
 * 				{@code String} as their Keys.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class LowClientLayout implements LayoutManager{

	/**
	 * <p style="margin-left: 10px"><em><b>GAPLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String GAPLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Gaplist-Label.</p>
	 */
	public final static String GAPLIST_LABEL = "Gaplist_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>COUNT_GAPLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String COUNT_GAPLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Count-Gaplist-Label.
	 * </p>
	 */
	public final static String COUNT_GAPLIST_LABEL = "Count_Gaplist_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>WISHLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String WISHLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Wishlist-Label.</p>
	 */
	public final static String WISHLIST_LABEL = "Wishlist_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>COUNT_WISHLIST_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String COUNT_WISHLIST_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Count-Wishlist-Label.
	 * </p>
	 */
	public final static String COUNT_WISHLIST_LABEL = "Count_Wishlist_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>WISHLIST_SHOW_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String WISHLIST_SHOW_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Wishlist-Show-Label.
	 * </p>
	 */
	public final static String WISHLIST_SHOW_LABEL = "Wishlist_Show_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>FAIL_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String FAIL_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Fail-Label.</p>
	 */
	public final static String FAIL_LABEL = "Fail_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>CURRENT_TRACK_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String CURRENT_TRACK_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Current_Track_Label.
	 * </p>
	 */
	public final static String CURRENT_TRACK_LABEL = "Current_Track_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>CURRENT_TRACKNAME_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String CURRENT_TRACKNAME_LABEL}
	 * </p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Label, that displays 
	 * the current Track's Name.</p>
	 */
	public final static String CURRENT_TRACKNAME_LABEL = "Current_Trackname_Label";
	
	
	/**
	 * <p style="margin-left: 10px"><em><b>LINK_TEXT</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String LINK_TEXT}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Link-TextField.</p>
	 */
	public final static String LINK_TEXT = "Link_Text";
	
	
	/**
	 * <p style="margin-left: 10px"><em><b>ADD_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String ADD_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Add-Button.</p>
	 */
	public final static String ADD_BUTTON = "Add_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>DISCONNECT_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String DISCONNECT_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Disconnect-Button.</p>
	 */
	public final static String DISCONNECT_BUTTON = "Disconnect_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>VOTE_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String VOTE_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Vote-Button.</p>
	 */
	public final static String VOTE_BUTTON = "Vote_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>REMOVE_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String REMOVE_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Remove-Vote-Button.
	 * </p>
	 */
	public final static String REMOVE_BUTTON = "Remove_Button";
	
	
	/**
	 * <p style="margin-left: 10px"><em><b>WISHLIST_PANE</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String WISHLIST_PANE}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Wishlist-Pane.</p>
	 */
	public final static String WISHLIST_PANE = "Wishlist_Pane";
	
	/**
	 * <p style="margin-left: 10px"><em><b>MENU_BAR</b></em></p>
	 * <p style="margin-left: 20px">{@code public final static String MENU_BAR}</p>
	 * <p style="margin-left: 20px">The {@code String}, that defines the Menu-Bar.</p>
	 */
	public final static String MENU_BAR = "Menu_Bar";
	
	
	/**
	 * <p style="margin-left: 10px"><em><b>components</b></em></p>
	 * <p style="margin-left: 20px">{@code private HashMap<String, Component> components}</p>
	 * <p style="margin-left: 20px">The {@link HashMap} of {@link Component}s, added to this 
	 * Layout, with {@code String}s as Keys.</p>
	 */
	private HashMap<String, Component> components;
	
	/**
	 * <p style="margin-left: 10px"><em><b>LowClientLayout</b></em></p>
	 * <p style="margin-left: 20px">{@code public LowClientLayout()}</p>
	 * <p style="margin-left: 20px">The Constructor for this Layout.</p>
	 * @since 1.0
	 */
	public LowClientLayout() {
		components = new HashMap<String, Component>();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>addLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code public void addLayoutComponent(String, Component)}
	 * </p>
	 * <p style="margin-left: 20px">Adds the given {@link Component} to the Layout, if the 
	 * given {@code String} is a legal Key for this Layout.</p>
	 * @param regex	The {@code String}, that will be checked, if it is a legal Key. If yes, 
	 * 				{@code comp} will be added to the Layout with this String as Key.
	 * @param comp	The {@link Component} to be added.
	 * @since 1.0
	 */
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (isLayoutComponent(regex)) {
			components.put(regex, comp);
		}
	}

	/**
	 * <p style="margin-left: 10px"><em><b>isLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean isLayoutComponent(String)}</p>
	 * <p style="margin-left: 20px">Checks, if the given {@code regex} is a key for this 
	 * Layout.</p>
	 * @param regex	The String to be checked.
	 * @return	{@code true}, if {@code regex} is a key, {@code false} else.
	 */
	private boolean isLayoutComponent(String regex) {
		if (regex.equals(GAPLIST_LABEL) || regex.equals(COUNT_GAPLIST_LABEL) || 
				regex.equals(WISHLIST_LABEL) || regex.equals(COUNT_WISHLIST_LABEL) || 
				regex.equals(WISHLIST_SHOW_LABEL) || regex.equals(FAIL_LABEL) || 
				regex.equals(CURRENT_TRACK_LABEL) || regex.equals(CURRENT_TRACKNAME_LABEL) || 
				regex.equals(LINK_TEXT) || regex.equals(ADD_BUTTON) || 
				regex.equals(DISCONNECT_BUTTON) || regex.equals(VOTE_BUTTON) || 
				regex.equals(REMOVE_BUTTON) || regex.equals(WISHLIST_PANE) ||
				regex.equals(MENU_BAR))
			return true;
		else
			return false;
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
		int width = parent.getSize().width;
		int height = parent.getSize().height;
		int inset = 10;
		int hSpacer = (int) (width*0.025);
		int vSpacer = (int) (height*0.025);
		int labelHeight = (int) (height*0.0375);
		int voteButtonY = 
				(int)((int)(height*0.275) + 2*vSpacer + (int)(height*0.525) + labelHeight);
		
		/***********************Labels***********************************/
		if (components.get(GAPLIST_LABEL) != null)
			components.get(GAPLIST_LABEL).setBounds(inset, inset+20, (int)(width*0.325), 
					labelHeight);
		
		if (components.get(COUNT_GAPLIST_LABEL) != null)
			components.get(COUNT_GAPLIST_LABEL).setBounds(inset + (int)(width*0.325)+5, 
					inset+20, (int)(width*0.125), labelHeight);
		
		if (components.get(WISHLIST_LABEL) != null)
			components.get(WISHLIST_LABEL).setBounds(inset, inset + labelHeight + 25, 
					(int)(width*0.325), labelHeight);
		
		if (components.get(COUNT_WISHLIST_LABEL)!= null)
			components.get(COUNT_WISHLIST_LABEL).setBounds(inset + (int)(width*0.325) + 5, 
					inset + labelHeight + 25, (int)(width*0.125), labelHeight);
		
		if (components.get(WISHLIST_SHOW_LABEL) != null)
			components.get(WISHLIST_SHOW_LABEL).setBounds(inset, 
					(int)(height*0.275)+vSpacer+labelHeight+20, width-2*inset, labelHeight);
		
		if (components.get(CURRENT_TRACK_LABEL) != null)
			components.get(CURRENT_TRACK_LABEL).setBounds(inset, (int)(height*0.275)+20, 
					(int)(width*0.175), labelHeight);
		
		if (components.get(CURRENT_TRACKNAME_LABEL) != null)
			components.get(CURRENT_TRACKNAME_LABEL).setBounds(
					inset + (int)(width*0.175)+hSpacer, (int)(height*0.275)+20, 
					width-2*inset-(int)(width*0.175)-hSpacer, labelHeight);
		
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds((int)(width*0.1875) + inset + hSpacer, 
					(int)(height*0.125) + (int)(height*0.05) + 25, 
					width- ((int)(width*0.1875) + inset + hSpacer) - inset, 
					(int)(height*0.0625));
		
		/***********************TextFields***************************/
		if (components.get(LINK_TEXT) != null)
			components.get(LINK_TEXT).setBounds(inset, (int)(height*0.125) +20, 
					(int)(width*0.4875), (int)(height*0.05));
		
		/**********************Buttons********************************/
		if (components.get(ADD_BUTTON) != null)
			components.get(ADD_BUTTON).setBounds(inset, 
					(int)(height*0.125) + (int)(height*0.05) + 25, (int)(width*0.1875), 
					(int)(height*0.0625));
		
		if (components.get(DISCONNECT_BUTTON) != null)
			components.get(DISCONNECT_BUTTON).setBounds(
					inset + (int)(width*0.325) + 5 + (int)(width*0.125) + hSpacer, inset + 20, 
					(int)(width - (2*inset + (int)(width*0.325) + 5 + (int)(width*0.125) 
									+ hSpacer)), 2*labelHeight + 5);
		
		if (components.get(VOTE_BUTTON) != null)
			components.get(VOTE_BUTTON).setBounds(inset, voteButtonY, 
					(int)((width-2*inset)/2 - hSpacer/2), 2*labelHeight+5);
		
		if (components.get(REMOVE_BUTTON) != null)
			components.get(REMOVE_BUTTON).setBounds(
					inset + (int)((width-2*inset)/2 + hSpacer/2), voteButtonY, 
					(int)((width-2*inset)/2 - hSpacer/2), 2*labelHeight + 5);
		
		/***********************Panes*******************************/
		if (components.get(WISHLIST_PANE) != null)
			components.get(WISHLIST_PANE).setBounds(inset, 
					(int)(height*0.275) + 2*vSpacer + 2*labelHeight + 20, width-2*inset, 
					(int)(height*0.525)-labelHeight-vSpacer-20);
		
		/*********************Menu_Bar******************************/
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
	 * @return	A new Dimension with the fixed Size 400x400.
	 * @since 1.0
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(400,400);
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
	 * <p style="margin-left: 10px"><em><b>remove</b></em></p>
	 * <p style="margin-left: 20px">{@code private void remove(Component)}</p>
	 * <p style="margin-left: 20px">Removes the given {@link Component} {@code comp} from the 
	 * Layout.</p>
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
		
		if (components.get(CURRENT_TRACKNAME_LABEL) != null)
			if (components.get(CURRENT_TRACKNAME_LABEL).equals(comp)) {
				components.put(CURRENT_TRACKNAME_LABEL, null);
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
		
		if (components.get(MENU_BAR) != null)
			if (components.get(MENU_BAR).equals(comp)) {
				components.put(MENU_BAR, null);
				return;
			}
	}
}
