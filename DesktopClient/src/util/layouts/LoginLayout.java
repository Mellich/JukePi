package util.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

/**
 * <p>The Layout, that will be used for the LogIn-Screen.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #LoginLayout()}:
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
 * 			<li>{@link #checkLayoutComponent(String)}:
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
 * 			<li>{@link #CONNECT_BUTTON}:
 * 				The {@code String}, that defines the Connect_Button.</li>
 * 
 * 			<li>{@link #FAIL_LABEL}:
 * 				The {@code String}, that defines the Fail_Label.</li>
 * 
 * 			<li>{@link #INTERN_SERVER_BUTTON}:
 * 				The {@code String}, that defines the Intern_Server_Button.</li>
 * 
 * 			<li>{@link #IP_LABEL}:
 * 				The {@code String}, that defines the IP_Label.</li>
 * 
 * 			<li>{@link #IP_TEXT}:
 * 				The {@code String}, that defines the IP_Textfield.</li>
 * 
 * 			<li>{@link #PORT_LABEL}:
 * 				The {@code String}, that defines the Port_Label.</li>
 * 
 * 			<li>{@link #PORT_TEXT}:
 * 				The {@code String}, that defines the Port_Textfield.</li>
 * 
 * 			<li>{@link #UDP_BUTTON}:
 * 				The {@code String}, that defines the UDP_Button.</li>
 * 
 * 			<li>{@link #WELCOME_LABEL}:
 * 				The {@code String}, that defines the Welcome_Label.</li>
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
public class LoginLayout implements LayoutManager{

	/**
	 * <p style="margin-left: 10px"><em><b>IP_TEXT</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String IP_TEXT}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the TextField for the 
	 * IP.</p>
	 * @see windows.LoginWindow#txtIp
	 */
	public static final String IP_TEXT = "Ip_Text";
	
	/**
	 * <p style="margin-left: 10px"><em><b>PORT_TEXT</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String PORT_TEXT}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the TextField for the 
	 * Port.</p>
	 * @see windows.LoginWindow#txtPort
	 */
	public static final String PORT_TEXT = "Port_Text";
	
	
	/**
	 * <p style="margin-left: 10px"><em><b>WELCOME_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String WELCOME_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Label, that contains 
	 * the Welcome-Message.</p>
	 */
	public static final String WELCOME_LABEL = "Welcome_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>IP_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String IP_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Label, that contains 
	 * {@code "IP:"}.</p>
	 */
	public static final String IP_LABEL = "Ip_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>PORT_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String PORT_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Label, that contains 
	 * {@code "Port:"}.</p>
	 */
	public static final String PORT_LABEL = "Port_Label";
	
	/**
	 * <p style="margin-left: 10px"><em><b>FAIL_LABEL</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String FAIL_LABEL}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Label, that displays 
	 * responses from the Server.</p>
	 */
	public static final String FAIL_LABEL = "Fail_Label";
	
	
	/**
	 * <p style="margin-left: 10px"><em><b>CONNECT_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String CONNECT_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the Connect_Button.</p>
	 */
	public static final String CONNECT_BUTTON = "Connect_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>UDP_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String UDP_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the 
	 * UDP_Connect_Button.</p>
	 */
	public static final String UDP_BUTTON = "Udp_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>INTERN_SERVER_BUTTON</b></em></p>
	 * <p style="margin-left: 20px">{@code public static final String INTERN_SERVER_BUTTON}</p>
	 * <p style="margin-left: 20px">The {@code String}, that declares the 
	 * Intern_Server_Button.</p>
	 */
	public static final String INTERN_SERVER_BUTTON = "Intern_Server_Button";
	
	/**
	 * <p style="margin-left: 10px"><em><b>components</b></em></p>
	 * <p style="margin-left: 20px">{@code private HashMap<String, Component> components}</p>
	 * <p style="margin-left: 20px">All {@link Component}s, that were added to this Layout as 
	 * a {@link HashMap} with {@code String}-Keys and {@link Component}-Values.</p>
	 */
	private HashMap<String, Component> components;
	
	/**
	 * <p style="margin-left: 10px"><em><b>LoginLayout</b></em></p>
	 * <p style="margin-left: 20px">{@code public LoginLayout()}</p>
	 * <p style="margin-left: 20px">The Constructor for this Layout.</p>
	 * @since 1.0
	 */
	public LoginLayout() {
		components = new HashMap<String, Component>();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>addLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code public void addLayoutComponent(String, Component)}
	 * </p>
	 * <p style="margin-left: 20px">Adds the given {@link Component} to Layout, if the given 
	 * {@code String} is a legal Key for this Layout.</p>
	 * @param regex	The Key as a {@code String}-Value.
	 * @param comp	The {@link Component} to be added.
	 * @since 1.0
	 */
	@Override
	public void addLayoutComponent(String regex, Component comp) {
		if (checkLayoutComponent(regex))
			components.put(regex, comp);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>layoutContainer</b></em></p>
	 * <p style="margin-left: 20px">{@code public void layoutContainer(Container)}</p>
	 * <p style="margin-left: 20px">Lays out the {@link Component}s in the given Container.</p>
	 * @param parent	The Component to be laid out.
	 * @since 1.0
	 */
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
			components.get(IP_TEXT).setBounds((int)(width*0.3), (int)(height*0.275), 
					(int)(width*0.37), textHeight);
		
		if (components.get(PORT_TEXT) != null)
			components.get(PORT_TEXT).setBounds((int)(width*0.3), (int)(height*0.4625), 
					(int)(width*0.09), textHeight);
		
		/*****************************Labels*********************************/
		if (components.get(WELCOME_LABEL) != null)
			components.get(WELCOME_LABEL).setBounds((int)(width*0.02), (int)(height*0.025), 
					(int)(width*0.96), (int)(height*0.2));
		
		if (components.get(IP_LABEL) != null)
			components.get(IP_LABEL).setBounds((int)(width*0.06), (int)(height*0.275), 
					(int)(width*0.152), labelHeight);
		
		if (components.get(PORT_LABEL) != null)
			components.get(PORT_LABEL).setBounds((int)(width*0.06), (int)(height*0.4625), 
					(int)(width*0.09), labelHeight);
		
		if (components.get(FAIL_LABEL) != null)
			components.get(FAIL_LABEL).setBounds((int)(width*0.1), (int)(height*0.5875), 
					(int)(width*0.75), labelHeight);
		
		/******************************Buttons******************************/
		if (components.get(CONNECT_BUTTON) != null)
			components.get(CONNECT_BUTTON).setBounds((int)(width*0.02), buttonY, 
					(int)(width*0.26), buttonHeight);
		
		if (components.get(UDP_BUTTON) != null)
			components.get(UDP_BUTTON).setBounds((int)(width*0.32), buttonY, 
					(int)(width*0.26), buttonHeight);
		
		if (components.get(INTERN_SERVER_BUTTON) != null)
			components.get(INTERN_SERVER_BUTTON).setBounds((int)(width*0.62), buttonY, 
					(int)(width*0.34), buttonHeight);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>minimumLayoutSize</b></em></p>
	 * <p style="margin-left: 20px">{@code public Dimension minimumLayoutSize(Container)}</p>
	 * <p style="margin-left: 20px">Returns the minimum Size for this Layout, that is needed 
	 * in the given Container.</p>
	 * @param parent	The Container, in which the Layout shall be laid out.
	 * @return A new Dimension, with the fixed Size 500x400.
	 * @since 1.0
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(500,400);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>preferredLayoutSize</b></em></p>
	 * <p style="margin-left: 20px">{@code public Dimension preferredLayoutSize(Container)}</p>
	 * <p style="margin-left: 20px">Returns the preferred Size for this Layout, that is needed 
	 * in the given Container.</p>
	 * @param parent	The Container, in which the Layout shall be laid out.
	 * @return	A new Dimension, with the fixed Size 500x400.
	 * @since 1.0
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(500,400);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>removeLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code private void removeLayoutComponent(Component)}</p>
	 * <p style="margin-left: 20px">Removes the given {@link Component} {@code comp} from the 
	 * Layout, if it was added before.</p>
	 * @param comp	The {@link Component} to be removed.
	 * @since 1.0
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp))
			remove(comp);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>checkLayoutComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean checkLayoutComponent(String)}</p>
	 * <p style="margin-left: 20px">Checks, if the given String {@code regex} is a Key for 
	 * this Layout.</p>
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
	 * <p style="margin-left: 10px"><em><b>remove</b></em></p>
	 * <p style="margin-left: 20px">{@code private void remove(Component)}</p>
	 * <p style="margin-left: 20px">Removes the given {@link Component} {@code comp} from the 
	 * Layout.</p>
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
