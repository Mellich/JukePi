package util;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import client.serverconnection.Song;

public class TablePopUpMenu extends JPopupMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738519894533257906L;

	private JMenuItem getLink;
	
	private TextTransfer tt;
	
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
