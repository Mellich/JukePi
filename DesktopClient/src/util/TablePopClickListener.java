package util;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import windows.MainWindow;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * The Class for the MouseListener, that will be added to the Tables.
 * @author Haeldeus
 * @version 1.0
 */
public class TablePopClickListener extends MouseAdapter {
	
	/**
	 * The TextField, that is connected to this Adapter.
	 */
	private JTable table;
	
	/**
	 * The List of Songs in this table.
	 */
	private Song[] list;
	
	/**
	 * The {@link ServerConnection}, that will send possible messages to the Server.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The Boolean value, that determines, if a Listener is added to the ContentTable or to the 
	 * Wishlist- or GaplistTable.
	 */
	private boolean contentTable;
	
	/**
	 * The MainWindow, that will display possible Responses from the Server.
	 */
	private MainWindow mw;
	

	/**
	 * The Constructor for the Wishlist- and GaplistTable.
	 * @param table	The Table of Songs as a JTable.
	 * @param list	The List of Songs as an Array.
	 * @param wrapper	The {@link ServerConnection}, that will send possible Messages to the 
	 * 					Server.
	 * @param mw	The MainWindow, that will display possible responses from the Server.
	 * @since 1.0
	 */
	public TablePopClickListener(JTable table, Song[] list, ServerConnection wrapper, MainWindow mw) {
		this.list = list;
		this.table = table;
		this.wrapper = wrapper;
		this.contentTable = false;
		this.mw = mw;
	}
	
	/**
	 * The Constructor for the ContentTable.
	 * @param table	The Table of Songs as a JTable.
	 * @param list	The List of Songs as an Array.
	 * @since 1.0
	 */
	public TablePopClickListener(JTable table, Song[] list) {
		this.list = list;
		this.table = table;
		this.contentTable = true;
	}
	
	@Override
    public void mousePressed(MouseEvent e){
		if (SwingUtilities.isRightMouseButton(e)) {
        	Point p = e.getPoint();
        	int rowNumber = table.rowAtPoint(p);
        	ListSelectionModel model = table.getSelectionModel();
        	model.setSelectionInterval(rowNumber, rowNumber);
		}
        if (e.isPopupTrigger()) {
            doPop(e);
        }
    }

	@Override
    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }
    
    /**
     * Pops the Menu at the Position of the Mouse, if the Result of 
     * {@link MouseEvent#getComponent()} is the TextField for this Adapter.
     * @param e		The {@link MouseEvent}, that triggered this Adapter. Used to retrieve the
     * 				Component, this Event is fired on and the Position of the Mouse.
     * @since 1.0
     */
    private void doPop(MouseEvent e){
    	TablePopUpMenu menu;
    	if (contentTable)
    		menu = new TablePopUpMenu(table, list);
    	else
    		menu = new TablePopUpMenu(table, list, wrapper, mw);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}