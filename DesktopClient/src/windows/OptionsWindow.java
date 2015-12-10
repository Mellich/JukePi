package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
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
 * The Class for the Options-Window.
 * @author Haeldeus
 * @version 1.0
 */
public class OptionsWindow extends Window{

	/**
	 * The Frame, that inherits the Window.
	 */
	private JFrame frame;
	
	/**
	 * The Collector, that provides Methods to communicate with the Server.
	 */
//	private Collector collector;
	
	/**
	 * The Password, that is needed to connect to the Server as an Admin.
	 */
	private String adminPW;
	
	/**
	 * The Password, that is needed to connect to the Server as a Player.
	 */
	private String playerPW;
	
	/**
	 * An {@link ArrayList<String>}, that provides all Sites, that are supported by the 
	 * youtube-dl Application.
	 */
	private ArrayList<String> supportedSites;
	
	/**
	 * The Constructor for the Window.
	 * @param collector	The Collector, that provides Methods to communicate with the Server.
	 * @param adminPW	The Password, that is needed to connect to the Server as an Admin.
	 * @param playerPW	The Password, that is needed to connect to the Server as a Player.
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
	
	@Override
	public void showFail(String text) {
		//Nothing to do here
	}

	@Override
	public void show() {
		constructFrame();
		frame.setVisible(true);
	}

	@Override
	public void close() {
		frame.setVisible(false);
	}
	
	/**
	 * Reads in the supported Sites from the Webpage, where they are listed. If no connection 
	 * can be established, this Method will set the static 
	 * {@link util.SupportedSites#supportedSites} as the supported Sites.
	 * @see util.SupportedSites
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
			util.IO.println(this, "Couldn't connect to the Internet. Using static SupportedSites");
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
	 * The Method to construct the Frame.
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
		JScrollPane pane = new JScrollPane(createTable());
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
		
		btnSave.addActionListener((ActionEvent ae) -> {this.close();});
		btnCancel.addActionListener((ActionEvent ae) -> {this.close();});
		
		south.add(btnSave);
		south.add(btnCancel);
		
		content.add(south, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
	}

	/**
	 * The Method that creates the Table, that contains all supported Homepages.
	 * @return	The Table with the supported Homepages as Elements.
	 * @since 1.0
	 */
	private JTable createTable() {
		String[] columns = {"Homepage", "Restricted:"};
		
		String[][] data = new String[supportedSites.size()][2];
		
		for (int i = 0; i < supportedSites.size(); i++) {
			data[i][0] = supportedSites.get(i);
			data[i][1] = "false";
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * The Serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The ToolTips for the TableHeaders.
			 */
			private String [] columnToolTips = {"The Name of the Song", "The Votes for this Song"};

			/**
			 * Returns the ToolTip for the Cell at the given Position of the Cursor.
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
			 * Returns, if the Cell at the given Position is editable.
			 * @param row	The row-index of the Cell.
			 * @param column	The column-index of the Cell.
			 * @return false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return the new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * The Serial Version ID.
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * Returns the ToolTip for the column at the given Cursor's Position.
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
        
        //TODO Add a listener to be able to change the Entry of "Restricted:"
        
        
        table.getColumnModel().getColumn(1).setMaxWidth(70);
		return table;
	}
}
