package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**<p>The {@link LayoutManager}, that will manage the Layout for the 
 * {@link windows.DisplayGaplistsWindow}.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #DisplayGaplistsLayout()}:
 * 				Creates a new Instance of this Layout.</li>
 * 
 * 			<li>{@link #addLayoutComponent(String, Component)}:
 * 				Adds the given {@link Component} to the {@link #components} with the given 
 * 				{@code String} as the Key.</li>
 * 
 * 			<li>{@link #layoutContainer(Container)}:
 * 				This Method is called, when the Layout shall be resized. It will place the 
 * 				{@link Component}s on their belonging spot and optimizes their size.</li>
 * 
 * 			<li>{@link #minimumLayoutSize(Container)}:
 * 				This Method returns the minimum Size, this Layout occupies.</li>
 * 
 * 			<li>{@link #preferredLayoutSize(Container)}:
 * 				This Method returns the Layout's preferred Size.</li>
 * 
 * 			<li>{@link #removeLayoutComponent(Component)}:
 * 				Removes the given {@link Component} from the Layout.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #checkLayoutComponent(String)}:
 * 				Checks, if the given {@code String} describes a LayoutComponent, that is saved 
 * 				for this Layout.</li>
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
 * 			<li>{@link #CONTENT_LABEL}:
 *				The {@code String}, that defines the Content_Label on the Window.</li>
 * 
 * 			<li>{@link #CONTENT_PANE}:
 * 				The {@code String}, that defines the Content_Pane on the Window.</li>
 * 
 * 			<li>{@link #CREATE_NEW_BUTTON}:
 * 				The {@code String}, that defines the Create_New_Button on the Window.</li>
 * 
 * 			<li>{@link #DELETE_BUTTON}:
 * 				The {@code String}, that defines the Delete_Button on the Window.</li>
 * 
 * 			<li>{@link #FAIL_LABEL}:
 * 				The {@code String}, that defines the Fail_Label on the Window.</li>
 * 
 * 			<li>{@link #GAPLISTS_PANE}:
 * 				The {@code String}, that defines the Gaplists_Pane on the Window.</li>
 * 
 * 			<li>{@link #LOAD_BUTTON}
 * 				The {@code String}, that defines the Load_Button on the Window.</li>
 * 
 * 			<li>{@link #SAVED_GAPLISTS_LABEL}:
 * 				The {@code String}, that defines the Saved_Gaplists_Label on the Window.</li>
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
 * 				The {@link HashMap}, that saves the {@link Component}s used by this Layout 
 * 				with their belonging {@code String}s as Keys.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class DisplayGaplistsLayout implements LayoutManager{

	/**
	 * <p style="margin-left: 10px"><em><b>FAIL_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String FAIL_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Fail_Label.</p>
	 * 
	 */
	public static final String FAIL_LABEL = "Fail_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>SAVED_GAPLISTS_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String SAVED_GAPLISTS_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the 
	 * Saved_Gaplists_Label.</p>
	 */
	public static final String SAVED_GAPLISTS_LABEL = "Saved_Gaplists_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>CONTENT_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String CONTENT_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Content_Label.</p>
	 */
	public static final String CONTENT_LABEL = "Content_Label";

	/**
	 * <p style="margin-left: 10px"><em><b>LOAD_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String LOAD_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Load_Button.</p>
	 */
	public static final String LOAD_BUTTON = "Load_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>DELETE_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String DELETE_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Delete_Button.</p>
	 */
	public static final String DELETE_BUTTON = "Delete_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>CREATE_NEW_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String CREATE_NEW_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the 
	 * Create_New_Button.</p>
	 */
	public static final String CREATE_NEW_BUTTON = "Create_New_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>GAPLISTS_PANE</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String GAPLISTS_PANE}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Gaplists_Pane.</p>
	 */
	public static final String GAPLISTS_PANE = "Gaplists_Pane";
	
	/**
	 * <p style="margin-left: 10px"><em><b>CONTENT_PANE</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String CONTENT_PANE}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Content_Pane.</p>
	 */
	public static final String CONTENT_PANE = "Content_Pane";
	
	/**
	 * <p style="margin-left: 10px"><em><b>components</b></em></p>
	 * <p style="margin-left: 20px">{@code private HashMap<String, Component> components}</p>
	 * <p style="margin-left: 20px">All components, that are used by this Layout as a 
	 * {@link HashMap} of {@code String} and {@link Component}.</p>
	 */
	private HashMap<String, Component> components;
	
	/**
	 * <p style="margin-left: 10px"><em><b>DisplayGaplistsLayout</b></em></p>
	 * <p style="margin-left: 20px">{@code public DisplayGaplistsLayout()}</p>
	 * <p style="margin-left: 20px">The Constructor for the Layout. Initializes the 
	 * components-HashMap.</p>
	 * @since 1.0
	 */
	public DisplayGaplistsLayout() {
		components = new HashMap<String, Component>();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>addLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code public void addLayoutComponent(String, Component)}
	 * </p>
	 * <p style="margin-left: 20px">Adds the given {@link Component} to {@link #components} 
	 * with the given {@code String} as Key, if that {@code String} is a legal Key for this 
	 * Layout.</p>
	 * @param regex	The {@code String}, that will be the Key for the {@link Component}.
	 * @param comp	The {@link Component} to be added.
	 * @since 1.0
	 */
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (checkLayoutComponent(regex))
			components.put(regex, comp);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>checkLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean checkLayoutComponent(String)}</p>
	 * <p style="margin-left: 20px">Checks, if the given {@code String} defines a 
	 * LayoutComponent.</p>
	 * @param regex	The {@code String}, that may define a LayoutComponent.
	 * @return	{@code true}, if the {@code String} defines a LayoutComponent, {@code false}, 
	 * if not.
	 * @since 1.0
	 */
	private boolean checkLayoutComponent(String regex) {
		if (regex.equals(SAVED_GAPLISTS_LABEL) || regex.equals(CONTENT_LABEL) || 
				regex.equals(FAIL_LABEL) || regex.equals(LOAD_BUTTON) || 
				regex.equals(CREATE_NEW_BUTTON) || regex.equals(DELETE_BUTTON) || 
				regex.equals(GAPLISTS_PANE) || regex.equals(CONTENT_PANE))
			return true;
		else
			return false;
	}

	
	/**
	 * <p style="margin-left: 10px"><em><b>layoutContainer</b></em></p>
	 * <p style="margin-left: 20px">{@code public void layoutContainer(Container)}</p>
	 * <p style="margin-left: 20px">This Method is called, when the Layout shall be resized. 
	 * It will place the {@link Component}s on their belonging spot and optimizes their size.
	 * </p>
	 * @param parent	The Container, that inherits the Layout (e.g. a 
	 * 					{@link javax.swing.JFrame}).
	 * @since 1.0
	 */
	@Override
	public void layoutContainer(Container parent) {
		int height = parent.getHeight();
		int width = parent.getWidth();
		int lblHeight = 14;
		int buttonHeight = 22;
		int buttonY = height-(buttonHeight+10);
		int buttonWidth = ((width-20)/2-5)/2-5;
		
		/*********************Labels*************************/
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds(10 + (width-20)/2+5, buttonY, (width-20)/2-5, 
					lblHeight);
		
		if (components.get(SAVED_GAPLISTS_LABEL) != null)
			components.get(SAVED_GAPLISTS_LABEL).setBounds(10, 10, (width-20)/2-5, lblHeight);
		
		if (components.get(CONTENT_LABEL) != null)
			components.get(CONTENT_LABEL).setBounds(10 + (width-20)/2 + 5, 10, (width-20)/2-5, 
					lblHeight);
		
		/********************Buttons************************/
		if (components.get(CREATE_NEW_BUTTON) != null)
			components.get(CREATE_NEW_BUTTON).setBounds(10, buttonY, buttonWidth, 
					buttonHeight);
		
		if (components.get(DELETE_BUTTON) != null) 
			components.get(DELETE_BUTTON).setBounds(20 + buttonWidth, buttonY, 
					(10+(width-20)/2-5)-(20 + buttonWidth), buttonHeight);
		
		if (components.get(LOAD_BUTTON) != null)
			components.get(LOAD_BUTTON).setBounds(10 + (width-20)/2+5, buttonY, 
					(width-20)/2-5, buttonHeight);
		
		/*****************Panes*****************************/
		if (components.get(GAPLISTS_PANE) != null)
			components.get(GAPLISTS_PANE).setBounds(10, 20+lblHeight, (width-20)/2-5, 
					height - (10 + lblHeight + 52));
		
		if (components.get(CONTENT_PANE) != null)
			components.get(CONTENT_PANE).setBounds(10 + (width-20)/2+5, 20 + lblHeight, 
					(width-20)/2-5, height - (10 + lblHeight + 52));
	}

	/**
	 * <p style="margin-left: 10px"><em><b>minimumLayoutSize</b></em></p>
	 * <p style="margin-left: 20px">{@code public Dimension minimumLayoutSize(Container)}</p>
	 * <p style="margin-left: 20px">Returns the minimum size for the Layout for the specific 
	 * Container.</p>
	 * @param parent	The Component to be laid out.
	 * @return	A new Dimension, which size is always 300x300.
	 * @since 1.0
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(300,300);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>preferredLayoutSize</b></em></p>
	 * <p style="margin-left: 20px">{@code public Dimension preferredLayoutSize(Container)}</p>
	 * <p style="margin-left: 20px">Returns the preferred size for the Layout for the specific 
	 * Container.</p>
	 * @param parent	The Component to be laid out.
	 * @return	The preferred Size, which is always a new Dimension with the Size 300x300.
	 * @since 1.0
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(300,300);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>removeLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code public void removeLayoutComponent(Component)}</p>
	 * <p style="margin-left: 20px">Removes the given {@link Component} {@code comp} from the 
	 * Layout, if it was added to the {@link #components}.</p>
	 * @param	comp	The Component to be removed.
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
		
		if (components.get(DELETE_BUTTON) != null)
			if (components.get(DELETE_BUTTON).equals(comp)) {
				components.put(DELETE_BUTTON, null);
				return;
			}
		
		if (components.get(CREATE_NEW_BUTTON) != null)
			if (components.get(CREATE_NEW_BUTTON).equals(comp)) {
				components.put(CREATE_NEW_BUTTON, null);
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
