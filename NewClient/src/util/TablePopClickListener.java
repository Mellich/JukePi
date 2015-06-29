package util;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import client.serverconnection.Song;

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
	 * The Constructor for all Adapters.
	 * @param txtLink	The TextField for this Adapter.
	 * @since 1.0
	 */
	public TablePopClickListener(JTable table, Song[] list) {
		this.list = list;
		this.table = table;
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
        TablePopUpMenu menu = new TablePopUpMenu(table, list);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}