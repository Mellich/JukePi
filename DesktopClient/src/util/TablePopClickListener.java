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
 * <p>The Class for the MouseListener, that will be added to the Tables.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #TablePopClickListener(JTable, Song[])}:
 * 				The Constructor for the Listener, if it was added to the ContentPane of the 
 * 				{@link windows.DisplayGaplistsWindow}. Will set {@link #contentTable} to 
 * 				{@code true} and adds the Parameters to the Fields {@link #list} and 
 * 				{@link #table}.</li>
 * 
 * 			<li>{@link #TablePopClickListener(JTable, Song[], ServerConnection, MainWindow)}:
 * 				The Constructor for the Listener, if it was added to the Gaplist- or 
 * 				WishlistPane of the {@link MainWindow}. Will set the Parameters to the Fields 
 * 				{@link #list}, {@link #table}, {@link #wrapper} and {@link #mw}.</li>
 * 
 * 			<li>{@link #mousePressed(MouseEvent)}:
 * 				The overridden Method, to handle Mouse clicks. If it was a right click, the 
 * 				Cell, where the Mouse was clicked will be marked and the 
 * 				{@link #doPop(MouseEvent)}-Method will be called.</li>
 * 		
 * 			<li>{@link #mouseReleased(MouseEvent)}:
 * 				The overridden Method, to handle Mouse releases. If it was a right click, the 
 * 				{@link #doPop(MouseEvent)}-Method will be called.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #doPop(MouseEvent)}:
 * 				This Method will open the Context Menu on the Position of the Mouse with the 
 * 				Content, depending on the Pane, this Listener was added to.</li>
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
 * 			<li>{@link #contentTable}:
 * 				Determines, if this Listener was added to the contentPane on the 
 * 				{@link windows.DisplayGaplistsWindow}.</li>
 * 
 * 			<li>{@link #list}:
 * 				The List of Songs, that the Pane, this Listener was added to, displays.</li>
 * 
 * 			<li>{@link #mw}:
 * 				The MainWindow, that contains the Gaplist- or WishlistPane, if this Listener 
 * 				was added to one of them.</li>
 * 
 * 			<li>{@link #table}:
 * 				The {@link JTable}, this Listener is added to.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection}, that will send the Messages to the Server. This 
 * 				will be used as a Parameter for the {@link TablePopUpMenu}.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class TablePopClickListener extends MouseAdapter {
	
	/**
 	 * <p style="margin-left: 10px"><em><b>table</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTable table}</p>
	 * <p style="margin-left: 20px">The TextField, that is connected to this Adapter.</p>
	 */
	private JTable table;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>list</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] list}</p>
	 * <p style="margin-left: 20px">The List of Songs in this table.</p>
	 */
	private Song[] list;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection}, that will send possible 
	 * messages to the Server.</p>
	 */
	private ServerConnection wrapper;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>contentTable</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean contentTable}</p>
	 * <p style="margin-left: 20px">The Boolean value, that determines, if a Listener is added 
	 * to the ContentTable or to the Wishlist- or GaplistTable.</p>
	 */
	private boolean contentTable;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>mw</b></em></p>
	 * <p style="margin-left: 20px">{@code private MainWindow mw}</p>
	 * <p style="margin-left: 20px">The MainWindow, that will display possible Responses from 
	 * the Server.</p>
	 */
	private MainWindow mw;
	

	/**
 	 * <p style="margin-left: 10px"><em><b>TablePopClickListener</b></em></p>
	 * <p style="margin-left: 20px">{@code public TablePopClickListener(JTable, Song[], 
	 * ServerConnection, MainWindow}</p>
	 * <p style="margin-left: 20px">The Constructor for the Wishlist- and GaplistTable.</p>
	 * @param table	The Table of Songs as a JTable.
	 * @param list	The List of Songs as an Array.
	 * @param wrapper	The {@link ServerConnection}, that will send possible Messages to the 
	 * 					Server.
	 * @param mw	The MainWindow, that will display possible responses from the Server.
	 * @since 1.0
	 */
	public TablePopClickListener(JTable table, Song[] list, ServerConnection wrapper, 
			MainWindow mw) {
		this.list = list;
		this.table = table;
		this.wrapper = wrapper;
		this.contentTable = false;
		this.mw = mw;
	}
	
	/**
 	 * <p style="margin-left: 10px"><em><b>TablePopClickListener</b></em></p>
	 * <p style="margin-left: 20px">{@code public TablePopClickListener(JTable, Song[])}</p>
	 * <p style="margin-left: 20px">The Constructor for the ContentTable.</p>
	 * @param table	The Table of Songs as a JTable.
	 * @param list	The List of Songs as an Array.
	 * @since 1.0
	 */
	public TablePopClickListener(JTable table, Song[] list) {
		this.list = list;
		this.table = table;
		this.contentTable = true;
	}
	
	/**
 	 * <p style="margin-left: 10px"><em><b>mousePressed</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mousePressed(MouseEvent)}</p>
	 * <p style="margin-left: 20px">Will be called, when the mouse was pressed on the 
	 * {@link JTable}, this Listener was added to. Will select the clicked row and, if 
	 * {@link MouseEvent#isPopupTrigger()} is {@code true}, opens the Context Menu by calling 
	 * {@link #doPop(MouseEvent)}.</p>
	 * @param e	The {@link MouseEvent}, that is used to check, if 
	 * 			{@link MouseEvent#isPopupTrigger()} is {@code true}.
	 * @since 1.0
	 */
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

	/**
 	 * <p style="margin-left: 10px"><em><b>mouseReleased</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mouseReleased(MouseEvent)}</p>
	 * <p style="margin-left: 20px">Will be called, when the mouse was released on the 
	 * {@link JTable}, this Listener was added to. If {@link MouseEvent#isPopupTrigger()} is 
	 * {@code true}, opens the Context Menu by calling {@link #doPop(MouseEvent)}.</p>
	 * @param e	The {@link MouseEvent}, that is used to check, if 
	 * 			{@link MouseEvent#isPopupTrigger()} is {@code true}.
	 * @since 1.0
	 */
	@Override
    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }
    
    /**
 	 * <p style="margin-left: 10px"><em><b>doPop</b></em></p>
	 * <p style="margin-left: 20px">{@code private void doPop(MouseEvent)}</p>
	 * <p style="margin-left: 20px">Pops the Menu at the Position of the Mouse, if the Result 
	 * of {@link MouseEvent#getComponent()} is the TextField for this Adapter.</p>
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