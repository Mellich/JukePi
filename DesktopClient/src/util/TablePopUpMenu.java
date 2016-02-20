package util;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import windows.MainWindow;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * <p>The Class for the PopUpMenu for all Tables.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #TablePopUpMenu(JTable, Song[])}:
 * 				The Constructor for Menus, that are added to the ContentTable. Creates the 
 * 				Menu with the MenuElement "Copy Link to Clipboard".</li>
 * 
 * 			<li>{@link #TablePopUpMenu(JTable, Song[], ServerConnection, MainWindow)}:
 * 				The Constructor for Menus, that are added to the Wishlist- or GaplistTable. 
 * 				Creates the Menu with the MenuElements "Copy Link to Clipboard", "Delete Track 
 * 				from the List" and "Move Song to other List".</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
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
 * 			<li>{@link #serialVersionUID}:
 * 				The UID of this serialized Object.</li>
 * 
 * 			<li>{@link #delete}:
 * 				The {@link JMenuItem}, that will delete the selected Track from the List when 
 * 				pressed</li>
 * 
 * 			<li>{@link #getLink}:
 * 				The {@link JMenuItem}, that will copy the selected Track's URL to the System's 
 * 				Clipboard when pressed.</li>
 * 
 * 			<li>{@link #moveToOtherList}:
 * 				The {@link JMenuItem}, that will add the selected Track to the other List, 
 * 				when pressed.</li>
 * 
 * 			<li>{@link #tt}:
 * 				The {@link TextTransfer}, that handles the Communication between the Client 
 * 				and the System's Clipboard.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection}, that will send and receive Messages to/from the 
 * 				Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.1
 */
public class TablePopUpMenu extends JPopupMenu{

	/**
	 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
	 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}</p>
	 * <p style="margin-left: 20px">The serial Version UID.</p>
	 */
	private static final long serialVersionUID = 5738519894533257906L;

	/** 	 
	 * <p style="margin-left: 10px"><em><b>getLink</b></em></p>
	 * <p style="margin-left: 20px">{@code private JMenuItem getLink}</p>
	 * <p style="margin-left: 20px">The {@link JMenuItem} for the getLink-Command.</p>
	 */
	private JMenuItem getLink;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>moveToOtherList</b></em></p>
	 * <p style="margin-left: 20px">{@code private JMenuItem moveToOtherList}</p>
	 * <p style="margin-left: 20px">The {@link JMenuItem} for the moveToOtherList-Command.</p>
	 */
	private JMenuItem moveToOtherList;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>delete</b></em></p>
	 * <p style="margin-left: 20px">{@code private JMenuItem delete}</p>
	 * <p style="margin-left: 20px">The {@link JMenuItem} for the Delete-Command.</p>
	 */
	private JMenuItem delete;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>tt</b></em></p>
	 * <p style="margin-left: 20px">{@code private TextTransfer tt}</p>
	 * <p style="margin-left: 20px">The {@link TextTransfer}, that will be used to copy the 
	 * Link of the Song to the Clipboard.</p>
	 */
	private TextTransfer tt;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection}, that will send the Message 
	 * to move the Track to the other List to the Server.</p>
	 */
	private ServerConnection wrapper;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>TablePopUpMenu</b></em></p>
	 * <p style="margin-left: 20px">{@code public TablePopUpMenu(JTable, Song[], 
	 * ServerConnection, MainWindow)}</p>
	 * <p style="margin-left: 20px">The Constructor for the PopUpMenu for the Gap- and 
	 * Wishlist.</p>
	 * @param table	The Table, this Menu will be added to.
	 * @param list	The List of Songs, either the Wishlist or the Gaplist.
	 * @param wrapper	The ServerConnection, that will send the Message to the Server.
	 * @param mw	The MainWindow, to show the Response from the Server.
	 * @since 1.0
	 */
	public TablePopUpMenu(JTable table, Song[] list, ServerConnection wrapper, MainWindow mw) {
		tt = new TextTransfer();
		this.wrapper = wrapper;
		getLink = new JMenuItem("Copy Link to Clipboard");
        getLink.setAccelerator(KeyStroke.getKeyStroke('c'));
        getLink.addActionListener((ActionEvent ae) -> 
        	{
        		int row = table.getSelectedRow();
        		tt.setClipboardContents(list[row].getURL());
        	});
        
        moveToOtherList = new JMenuItem("Move Song to the Other List");
        moveToOtherList.setAccelerator(KeyStroke.getKeyStroke('m'));
        moveToOtherList.addActionListener((ActionEvent ae) -> 
        	{
        		int row = table.getSelectedRow();
        		this.wrapper.addSongToOtherList((String[] s) -> 
        			{
        				if (s[0].equals("true"))
        					mw.showFail("Added Song to other List");
        				else
        					mw.showFail("Coudln't add the Song to the other List");
        			}, list[row]);
        	});
        delete = new JMenuItem("Delete Track from List");
        delete.setAccelerator(KeyStroke.getKeyStroke('d'));
        delete.addActionListener((ActionEvent ae) -> 
        	{
        		int row = table.getSelectedRow();
        		this.wrapper.deleteFromList((String[] s) -> 
        			{
        				if (s[0].equals("true"))
        					mw.showFail("Deleted the Track");
        				else
        					mw.showFail("Couldn't delete the Track");
        			}, list[row]);
        		mw.setSelectedGaplistIndex(row);
      		});
        
        add(getLink);
        add(moveToOtherList);
        add(delete);
	}
	
	/**
 	 * <p style="margin-left: 10px"><em><b>TablePopUpMenu</b></em></p>
	 * <p style="margin-left: 20px">{@code public TablePopUpMenu(JTable, Song[])}</p>
	 * <p style="margin-left: 20px">The PopUpMenu for the ContentTable</p>
	 * @param table	The ContentTable as a JTable.
	 * @param list	The List of Songs.
	 * @since 1.0
	 */
	public TablePopUpMenu(JTable table, Song[] list) {
		tt = new TextTransfer();
		getLink = new JMenuItem("Copy Link to Clipboard");
        getLink.setAccelerator(KeyStroke.getKeyStroke('c'));
        getLink.addActionListener((ActionEvent ae) -> {	
        		int row = table.getSelectedRow();
        		tt.setClipboardContents(list[row].getURL());
			});
        add(getLink);
	}
}
