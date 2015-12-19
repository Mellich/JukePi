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
 * The Class for the PopUpMenu for all Tables.
 * @author Haeldeus
 * @version 1.1
 */
public class TablePopUpMenu extends JPopupMenu{

	/**
	 * The serial Version UID.
	 */
	private static final long serialVersionUID = 5738519894533257906L;

	/**
	 * The MenuItem for the getLink-Command.
	 */
	private JMenuItem getLink;
	
	/**
	 * The MenuItem for the moveToOtherList-Command.
	 */
	private JMenuItem moveToOtherList;
	
	/**
	 * The MenuItem for the Delete-Command
	 */
	private JMenuItem delete;
	
	/**
	 * The TextTransfer, that will be used to copy the Link of the Song to the Clipboard.
	 */
	private TextTransfer tt;
	
	/**
	 * The {@link ServerConnection}, that will send the Message to move the Track to the other 
	 * List to the Server.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The Constructor for the PopUpMenu for the Gap- and Wishlist.
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
        getLink.addActionListener((ActionEvent ae) -> 	{	int row = table.getSelectedRow();
        													tt.setClipboardContents(list[row].getURL());
        													
        											});
        
        moveToOtherList = new JMenuItem("Move Song to the Other List");
        moveToOtherList.setAccelerator(KeyStroke.getKeyStroke('m'));
        moveToOtherList.addActionListener((ActionEvent ae) -> {	int row = table.getSelectedRow();
        														this.wrapper.addSongToOtherList((String[] s) -> {	if (s[0].equals("true"))
        																												mw.showFail("Added Song to other List");
        																											else
        																												mw.showFail("Coudln't add the Song to the other List");}, list[row]);
        													});
        delete = new JMenuItem("Delete Track from List");
        delete.setAccelerator(KeyStroke.getKeyStroke('d'));
        delete.addActionListener((ActionEvent ae) -> {	int row = table.getSelectedRow();
        												this.wrapper.deleteFromList((String[] s) -> {	if (s[0].equals("true"))
        																									mw.showFail("Deleted the Track");
        																								else
        																									mw.showFail("Couldn't delete the Track");}, list[row]);
        												mw.setSelectedGaplistIndex(row);
        												});
        add(getLink);
        add(moveToOtherList);
	}
	
	/**
	 * The PopUpMenu for the ContentTable
	 * @param table	The ContentTable as a JTable.
	 * @param list	The List of Songs.
	 * @since 1.0
	 */
	public TablePopUpMenu(JTable table, Song[] list) {
		tt = new TextTransfer();
		getLink = new JMenuItem("Copy Link to Clipboard");
        getLink.setAccelerator(KeyStroke.getKeyStroke('c'));
        getLink.addActionListener((ActionEvent ae) -> 	{	int row = table.getSelectedRow();
        													tt.setClipboardContents(list[row].getURL());
        													
        											});
        add(getLink);
	}
}
