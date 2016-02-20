package util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import client.serverconnection.Song;

/**
 * <p>The Renderer for the Table Cells in the Gaplist-Pane.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #TableRenderer(Song[])}:
 * 				The Constructor for the Renderer. Will set {@link #gaplist} to the given 
 * 				{@link Song}-Array.</li>
 * 
 * 			<li>{@link #getTableCellRendererComponent(JTable, Object, boolean, boolean, int, 
 * 				int)}:
 * 				The overridden Method, that will set the Background-Color of the Table-Cells 
 * 				to red, yellow or white, depending on their Parse-Status. If it is parsed, the 
 * 				Color will be White, if the Song, that is represented in the Cell is parsed at 
 * 				the Moment, the Color will be Yellow. If there was an Error while parsing the 
 * 				Song, the Color will be red.</li>
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
 * 			<li>{@link #gaplist}:
 * 				The Gaplist, that is represented in the {@link JTable} as an 
 * 				{@link Song}-Array.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class TableRenderer extends DefaultTableCellRenderer {

    /**
	 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
	 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}</p>
	 * <p style="margin-left: 20px">The serial Version UID.</p>
	 */
	private static final long serialVersionUID = 1386922222679555490L;
	
	/**	 
	 * <p style="margin-left: 10px"><em><b>gaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] gaplist}</p>
	 * <p style="margin-left: 20px">The Gaplist as an Array of {@link Song}s.</p>
	 */
	private Song[] gaplist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>TableRenderer</b></em></p>
	 * <p style="margin-left: 20px">{@code public TableRenderer(Song[])}</p>
	 * <p style="margin-left: 20px">The Constructor for this Renderer. Will set the given 
	 * {@link Song}-Array as the {@link #gaplist}.</p>
	 * @param gaplist	The Gaplist as an Array of {@link Song}s.
	 * @since 1.0
	 */
	public TableRenderer(Song[] gaplist) {
		this.gaplist = gaplist;
	}

	/**
	 * <p style="margin-left: 10px"><em><b>getTableCellRendererComponent</b></em></p>
	 * <p style="margin-left: 20px">{@code public Component 
	 * getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)}</p>
	 * <p style="margin-left: 20px">Returns the {@link Component}, that will be used, as Table 
	 * Elements.</p>
	 * @param table	The {@link JTable}, that this Renderer is operating on.
	 * @param value	The value of the Cell.
	 * @param isSelected	The {@code boolean} value, if the Cell, this Renderer is added to, 
	 * 						is selected.
	 * @param hasFocus	The {@code boolean} value, if the Table, this Renderer is operating 
	 * 					on, has the Focus or not.
	 * @param row	The row of the Cell, this Renderer is added to.
	 * @param column	The column of the Cell, the Renderer is added to.
	 * @since 1.0
	 */
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
    		boolean isSelected, boolean hasFocus, int row, int column) {
    	final Component c = super.getTableCellRendererComponent(table, value, isSelected, 
    			hasFocus, row, column);
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