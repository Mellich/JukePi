package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import connection.Collector;

/**
 * <p>The Class for the Options-Window.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #OptionsWindow(Collector, String, String)}:
 * 				The Constructor for this Window. Will set the Fields to the given Parameter 
 * 				Values and creates a new {@link JFrame}, that will inherit this Window. Will 
 * 				call {@link #readInWebpage()} afterwards.</li>
 * 
 * 			<li>{@link #close()}:
 * 				Closes the Window by setting it disabled and invisible.</li>
 * 
 * 			<li>{@link #setActive(boolean)}:
 * 				Sets the state of the Window, depending on the given {@code boolean}, either 
 * 				enabled or disabled.</li>
 * 
 * 			<li>{@link #show()}:
 * 				Sets the Window visible and enabled.</li>
 * 
 * 			<li>{@link #showFail(String)}:
 * 				As there are no Messages to be displayed, this Method does nothing.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #constructFrame()}:
 * 				Constructs the Frame and adds the needed Components to it.</li>
 * 
 * 			<li>{@link #createTable()}:
 * 				Creates the Table, that shows the supported Sites.</li>
 * 
 * 			<li>{@link #getLanguages()}:
 * 				Grabs the Languages, that are saved as Files on the harddrive of the Client.
 * 				</li>
 * 
 * 			<li>{@link #readInWebpage()}:
 * 				Reads in the webpage, that shows all Sites, that are supported by youtube-dl 
 * 				(http://rg3.github.io/youtube-dl/supportedsites.html).</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * <h3>Fields:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #adminPW}:
 * 				The Password for connecting to the Server as an Admin as a {@code String} 
 * 				Value.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that inherits this Window.</li>
 * 
 * 			<li>{@link #pane}:
 * 				The {@link JScrollPane}, that contains the Table of supported Sites.</li>
 * 
 * 			<li>{@link #playerPW}:
 * 				The Password for connecting to the Server as a Player as a {@code String} 
 * 				Value.</li>
 * 
 * 			<li>{@link #supportedSites}:
 * 				The Sites, that are supported by youtube-dl as an {@link ArrayList} of 
 * 				{@code String} Values.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class OptionsWindow extends Window{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame, that inherits the Window.</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>collector</b></em></p>
	 * <p style="margin-left: 20px">{@code private Collector collector}</p>
	 * <p style="margin-left: 20px">The Collector, that provides Methods to communicate with 
	 * the Server.</p>
	 */
//	private Collector collector;
	
	/**
	 * <p style="margin-left: 10px"><em><b>adminPW</b></em></p>
	 * <p style="margin-left: 20px">{@code private String adminPW}</p>
	 * <p style="margin-left: 20px">The Password, that is needed to connect to the Server as 
	 * an Admin.</p>
	 */
	private String adminPW;
	
	/**
	 * <p style="margin-left: 10px"><em><b>playerPW</b></em></p>
	 * <p style="margin-left: 20px">{@code private String playerPW}</p>
	 * <p style="margin-left: 20px">The Password, that is needed to connect to the Server as a 
	 * Player.</p>
	 */
	private String playerPW;
	
	/**
	 * <p style="margin-left: 10px"><em><b>supportedSites</b></em></p>
	 * <p style="margin-left: 20px">{@code private ArrayList<String> supportedSites}</p>
	 * <p style="margin-left: 20px">An {@link ArrayList<String>}, that provides all Sites, 
	 * that are supported by the youtube-dl Application.</p>
	 * @see ArrayList
	 */
	private ArrayList<String> supportedSites;
	
	/**
	 * <p style="margin-left: 10px"><em><b>pane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane pane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that contains the Table for the 
	 * supported Sites.</p>
	 */
	private JScrollPane pane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>OptionsWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public OptionsWindow(Collector, String, String)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Window.</p>
	 * @param collector	The {@link Collector}, that provides Methods to communicate with the 
	 * 					Server.
	 * @param adminPW	The Password, that is needed to connect to 
	 * 					the Server as an Admin as a {@code String} Value.
	 * @param playerPW	The Password, that is needed to connect to the Server as a Player as a 
	 * 					{@code String} Value.
	 * @since 1.0
	 */
	public OptionsWindow(Collector collector, String adminPW, String playerPW) {
		frame = new JFrame();
	//	this.collector = collector;
		this.adminPW = adminPW;
		this.playerPW = playerPW;
		supportedSites = new ArrayList<String>();
		readInWebpage();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(String)}</p>
	 * <p style="margin-left: 20px">Would show the given {@code String} on the Screen, but as 
	 * there are no Messages to be displayed by this Window, this Method does nothing.</p>
	 * @param text	The {@code String}, that would have been displayed
	 * @since 1.0
	 */
	@Override
	public void showFail(String text) {
		//Nothing to do here
	}

	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Constructs the {@link #frame} by calling {@link 
	 * #constructFrame()} and setting it visible afterwards by calling {@link 
	 * JFrame#setVisible(boolean)}</p>
	 * @since 1.0
	 */
	@Override
	public void show() {
		constructFrame();
		frame.setVisible(true);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>close</b></em></p>
	 * <p style="margin-left: 20px">{@code public void close()}</p>
	 * <p style="margin-left: 20px">Sets {@link #frame} invisible and disabled.</p>
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 * @see javax.swing.JFrame#setVisible
	 */
	@Override
	public void close() {
		frame.setVisible(false);
		frame.setEnabled(false);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>readInWebpage</b></em></p>
	 * <p style="margin-left: 20px">{@code private void readInWebpage}</p>
	 * <p style="margin-left: 20px">Reads in the supported Sites from the Webpage, where 
	 * they are listed. If no connection can be established, this Method will set the static 
	 * {@link util.SupportedSites#supportedSites} as the supported Sites.</p>
	 * @see util.SupportedSites
	 * @see <a href="http://rg3.github.io/youtube-dl/supportedsites.html">Supported Sites</a>
	 * @since 1.0
	 */
	private void readInWebpage() {
	    URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    boolean reachable = false;

	    try {
	    	//TODO Worker Process
	        url = new URL("http://rg3.github.io/youtube-dl/supportedsites.html");
			final URLConnection conn = url.openConnection();
	        conn.connect();
	        reachable = true;
		} catch (IOException e) {
			// No Internet-Connection possible
			util.IO.println(this, "Couldn't connect to the Internet. Using static "
					+ "SupportedSites");
	        url = null;
		}
	    
	    if (reachable)
		    try {
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));
	
		        while ((line = br.readLine()) != null) {
		        	if (line.contains("<li><b>")) {
		        		int start = line.indexOf("<b>") +3;
		        		int end = line.indexOf("</b>");
		        		supportedSites.add(line.substring(start, end));
		        	}
		        }
		        util.IO.println(this, "Read in Supported Sites");
		    } catch (MalformedURLException mue) {
		         mue.printStackTrace();
		    } catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		            if (is != null) is.close();
		        } catch (IOException ioe) {
		            // nothing to see here
		        }
		    }
	    else
	    	for (String i : util.SupportedSites.supportedSites)
	    		supportedSites.add(i);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>constructFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void constructFrame()}</p>
	 * <p style="margin-left: 20px">The Method to construct the Frame.</p>
	 * @since 1.0
	 */
	private void constructFrame() {
		frame.setTitle("Options");
		frame.setSize(new Dimension(500,500));
		frame.setMinimumSize(new Dimension(500,500));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(0,30));
		
		Container upLeft = new Container();
		upLeft.setLayout(new BorderLayout());
		
		JLabel lblPasswords = new JLabel("Passwords:");
		lblPasswords.setHorizontalAlignment(JLabel.CENTER);
		JLabel lblPWAdmin = new JLabel("Admin:");
		lblPWAdmin.setHorizontalAlignment(JLabel.CENTER);
		JLabel lblPWPlayer = new JLabel("Player");
		lblPWPlayer.setHorizontalAlignment(JLabel.CENTER);
		JLabel lblAdminPW = new JLabel(adminPW);
		JLabel lblPlayerPW = new JLabel(playerPW);
		
		Container upLeftCenter = new Container();
		upLeftCenter.setLayout(new GridLayout(2, 2, 1, 1));
		upLeftCenter.add(lblPWAdmin);
		upLeftCenter.add(lblAdminPW);
		upLeftCenter.add(lblPWPlayer);
		upLeftCenter.add(lblPlayerPW);
		
		upLeft.add(lblPasswords, BorderLayout.NORTH);
		upLeft.add(upLeftCenter, BorderLayout.CENTER);
		
		
		Container upRight = new Container();
		upRight.setLayout(new GridLayout(1,2));
		
		JLabel lblLang = new JLabel("Language");
		lblLang.setHorizontalAlignment(JLabel.CENTER);
		JComboBox<String> cbxLang = new JComboBox<String>();
		cbxLang.addItem("English");
		cbxLang.addItem("Deutsch");
		cbxLang.addItem("Klingonisch");
		((JLabel)cbxLang.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		upRight.add(lblLang);
		upRight.add(cbxLang);
		
		Container up = new Container();
		up.setLayout(new GridLayout(1,2,20,1));
		up.add(upLeft);
		up.add(upRight);
		
		content.add(up, BorderLayout.NORTH);
		
		
		
		Container center = new Container();
		center.setLayout(new GridLayout(1,2,20,0));
		
		Container centerLeft = new Container();
		centerLeft.setLayout(new BorderLayout(2,20));
		
		JLabel lblRestrictions = new JLabel("Restrictions");
		lblRestrictions.setHorizontalAlignment(JLabel.CENTER);
		pane = new JScrollPane(createTable());
		JButton btnRemove = new JButton("Remove Restrictions");
		
		centerLeft.add(lblRestrictions, BorderLayout.NORTH);
		centerLeft.add(pane, BorderLayout.CENTER);
		centerLeft.add(btnRemove, BorderLayout.SOUTH);
		
		Container centerRight = new Container();
		centerRight.setLayout(new BorderLayout(20,20));
		
		JScrollPane pane2 = new JScrollPane(new JTextField(""));
		centerRight.add(pane2, BorderLayout.CENTER);
		
		center.add(centerLeft);
		center.add(centerRight);
		
		content.add(center, BorderLayout.CENTER);
		
		
		Container south = new Container();
		south.setLayout(new GridLayout(1,2,50,0));
		
		JButton btnSave = new JButton("Save");
		JButton btnCancel = new JButton("Cancel");
		
		btnSave.addActionListener((ActionEvent ae) -> {getLanguages();this.close();});
		btnCancel.addActionListener((ActionEvent ae) -> {this.close();});
		btnRemove.addActionListener((ActionEvent ae) -> {
			centerLeft.remove(pane);
			pane = new JScrollPane(createTable());
			centerLeft.add(pane, BorderLayout.CENTER);
			center.removeAll();
			center.add(centerLeft);
			center.add(centerRight);
			frame.getContentPane().add(center, BorderLayout.CENTER);
			frame.revalidate();
		});
		
		south.add(btnSave);
		south.add(btnCancel);
		
		content.add(south, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>createTable</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTable createTable()}</p>
	 * <p style="margin-left: 20px">The Method that creates the Table, that contains all 
	 * supported Homepages.</p>
	 * @return	The Table with the supported Homepages as Elements.
	 * @since 1.0
	 */
	private JTable createTable() {
		String[] columns = {"Homepage", "Restricted:"};
		
		Object[][] data = new Object[supportedSites.size()][2];
		
		for (int i = 0; i < supportedSites.size(); i++) {
			data[i][0] = supportedSites.get(i);
			data[i][1] = false;
		}
		
		JTable table = new JTable(data, columns) {
			
			/**
			 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
			 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}
			 * </p>
			 * <p style="margin-left: 20px">The Serial Version UID.</p>
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * <p style="margin-left: 10px"><em><b>columnToolTips</b></em></p>
			 * <p style="margin-left: 20px">{@code private String[] columnToolTips}</p>
			 * <p style="margin-left: 20px">The ToolTips for the TableHeaders.</p>
			 */
			private String [] columnToolTips = {"The Name of the Song", 
					"The Votes for this Song"};

			/**
			 * <p style="margin-left: 10px"><em><b>getToolTipText</b></em></p>
			 * <p style="margin-left: 20px">{@code public String getToolTipText(MouseEvent)}
			 * </p>
			 * <p style="margin-left: 20px">Returns the ToolTip for the Cell at the given 
			 * Position of the Cursor.</p>
			 * @param e	The MouseEvent.
			 * @return	The ToolTip for the Cell at the Cursor's Position.
			 */
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
        
				if (colIndex == 0)
					tip = ""+ getValueAt(rowIndex, colIndex);
				return tip;
			}
			
			/**
			 * <p style="margin-left: 10px"><em><b>isCellEditable</b></em></p>
			 * <p style="margin-left: 20px">{@code public boolean isCellEditable(int, int)}</p>
			 * <p style="margin-left: 20px">Returns, if the Cell at the given Position is 
			 * editable.</p>
			 * @param row	The row-index of the Cell.
			 * @param column	The column-index of the Cell.
			 * @return {@code false} by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){
				if (column == 0)
					return false;
				else
					return true;
			}
	
			/**
			 * <p style="margin-left: 10px"><em><b>getColumnClass</b></em></p>
			 * <p style="margin-left: 20px">{@code public Class<?> getColumnClass(int)}</p>
			 * <p style="margin-left: 20px">Returns the Class, the given Column represents.</p>
			 */
			@Override
            public Class<?> getColumnClass(int column) {
				switch (column) {
					case 0: return String.class;
					default: return Boolean.class;
				}
            }
			
			/**
			 * <p style="margin-left: 10px"><em><b>createDefaultTableHeader</b></em></p>
			 * <p style="margin-left: 20px">{@code protected JTableHeader 
			 * createDefaultTableHeader()}</p>
			 * <p style="margin-left: 20px">Creates a new TableHeader.</p>
			 * @return The new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					
					/**
					 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
					 * <p style="margin-left: 20px">{@code private static final long 
					 * serialVersionUID}</p>
					 * <p style="margin-left: 20px">The Serial Version UID.</p>
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * <p style="margin-left: 10px"><em><b>getToolTipText</b></em></p>
					 * <p style="margin-left: 20px">{@code public String 
					 * getToolTipText(MouseEvent)}</p>
					 * <p style="margin-left: 20px">Returns the ToolTip for the column at the 
					 * given Cursor's Position.</p>
					 * @param e	The MouseEvent.
					 * @return the ToolTip for the column at the Position of the Cursor.
					 */
					public String getToolTipText(MouseEvent e) {
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return columnToolTips[realIndex];
					}
				};
            }
        };
        table.getColumnModel().getColumn(1).setMaxWidth(70);
		return table;
	}

	/**
	 * <p style="margin-left: 10px"><em><b>getLanguages</b></em></p>
	 * <p style="margin-left: 20px">{@code private String[] getLanguages()}</p>
	 * <p style="margin-left: 20px">Grabs the Languages, that are saved on the harddrive of 
	 * the Client.</p>
	 * @return	All Languages, saved on the Harddrive of the Client as an Array of 
	 * {@code String}s.
	 * @since 1.0
	 */
	private String[] getLanguages() {
		String workingDir = MainWindow.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		System.out.println(workingDir);
		File file = new File(workingDir + "\\data\\lang\\");
		File[] languages = file.listFiles();
		
		String[] s = new String[10];
		
		for (int i = 0; i < languages.length; i++)
			s[i] = languages[i].getName();
		System.out.println(s.toString());
		return s;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>setActive</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setActive(boolean)}</p>
	 * <p style="margin-left: 20px">Sets the State of the Window to the given State. Active, 
	 * if {@code state} is {@code true}, inactive if it is {@code false}.</p>
	 * @param state	The new State of the Window; active, if {@code true}, inactive else.
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 */
	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}
}
