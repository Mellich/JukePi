package util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import client.serverconnection.Song;

/**
 * The Renderer for the Table Cells in the Gaplist-Pane.
 * @author Haeldeus
 * @version 1.0
 */
public class TableRenderer extends DefaultTableCellRenderer {

	/**
	 * The Gaplist as an Array of {@link Song}s.
	 */
	private Song[] gaplist;
	
	/**
	 * The Constructor for this Renderer.
	 * @param gaplist	The Gaplist as an Array of {@link Song}s.
	 * @since 1.0
	 */
	public TableRenderer(Song[] gaplist) {
		this.gaplist = gaplist;
	}
	
/*	public void setGaplist(Song[] gaplist) {
		this.gaplist = gaplist;
	}*/
	
    /**
	 * The serial Version UID.
	 */
	private static final long serialVersionUID = 1386922222679555490L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	switch (gaplist[row].getParseStatus()){
    		case PARSED: c.setBackground(Color.WHITE); break;
    		case PARSING: c.setBackground(Color.YELLOW);break;
    		case NOT_PARSED: c.setBackground(Color.LIGHT_GRAY); break;
    		default: c.setBackground(Color.RED); break;
    	}
    	if (isSelected && c.getBackground() != Color.RED) {
    		c.setBackground(table.getSelectionBackground());
    	}
        return c;
    }
}