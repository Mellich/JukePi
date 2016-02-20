package util.tasks;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.JTableHeader;

import util.TablePopClickListener;
import util.TableRenderer;
import util.layouts.NewClientLayout;
import windows.MainWindow;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * <p>The {@link SwingWorker}, that will fill the Wishlist table.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #SetWishlistTask(Song[], JLabel, ServerConnection, JFrame, JScrollPane, 
 * 					MainWindow)}:
 * 					The Constructor for this Worker. Will set the given Values to their 
 * 					Instance Variables.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<ul>
 * 			<li>{@link #doInBackground()}:
 * 				The Method, that is called, when executing this Worker. Will iterate through 
 * 				the {@link #wishlist}-Field and fills the {@link #oldPane} with Strings, 
 * 				representing the Songs in the Wishlist.</li>
 * 	
 * 			<li>{@link #done()}:
 * 				This Method is called, when the {@link #doInBackground()}-Method has finished. 
 * 				Will call {@link MainWindow#doneWishlistUpdate(Song[], JLabel, JFrame, 
 * 				JScrollPane)} to update the Fields for the {@link #mainWindow}.
 * 
 * 			<li>{@link #process(List)}:
 * 				This Method is called, whenever a new Song was read out by the 
 * 				{@link #doInBackground()}-Method. Will create a {@link JTable}, that displays 
 * 				all Songs, that were read out so far.</li>
 * 		</ul>
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
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that inherits the {@link #oldPane} and thus is the 
 * 				{@link #mainWindow}.</li>
 * 
 * 			<li>{@link #lblNoWishlist}:
 * 				The {@link JLabel}, that shows the amount of Tracks in the Wishlist.</li>
 * 
 * 			<li>{@link #mainWindow}:
 * 				The {@link MainWindow}, that called this Worker.</li>
 * 
 * 			<li>{@link #oldPane}:
 * 				The {@link JScrollPane}, that displays the Table with the Tracks in the 
 * 				Wishlist as Elements.</li>
 * 
 * 			<li>{@link #wishlist}:
 * 				The Wishlist of the Server, as an Array of {@link Song}s.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server, that will send and receive Message 
 * 				to/from the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class SetWishlistTask extends SwingWorker<Void, Song[]>{

	/**
	 * <p style="margin-left: 10px"><em><b>wishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] wishlist}</p>
	 * <p style="margin-left: 20px">The new Wishlist to be loaded as an Array of {@link Song}s.
	 * </p>
	 */
	private Song[] wishlist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblNoWishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblNoWishlist}</p>
	 * <p style="margin-left: 20px">The {@link JLabel}, that displays the Amount of Tracks in 
	 * the Wishlist.</p>
	 */
	private JLabel lblNoWishlist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection}, that will send Messages to 
	 * the Server.</p>
	 */
	private ServerConnection wrapper;
	
	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that displays the {@link MainWindow}.
	 * </p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>oldPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane oldPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that displays the Wishlist as a 
	 * table.</p>
	 */
	private JScrollPane oldPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>mainWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private MainWindow mainWindow}</p>
	 * <p style="margin-left: 20px">The {@link MainWindow}, that called this Worker.</p>
	 */
	private MainWindow mainWindow;

	/**
	 * <p style="margin-left: 10px"><em><b>SetWishlistTask</b></em></p>
	 * <p style="margin-left: 20px">{@code public SetWishlistTask(Song[], JLabel, 
	 * ServerConnection, JFrame, JScrollPane, MainWindow)}</p>
	 * <p style="margin-left: 20px">The Constructor for this Worker.</p>
	 * @param wishlist	The new Wishlist to be shown as an Array of {@link Song}s.
	 * @param lblNoWishlist	The {@link JLabel}, that displays the number of Tracks in the 
	 * Wishlist.
	 * @param wrapper The {@link ServerConnection}, that will send the Messages to the Server.
	 * @param frame	The {@link JFrame}, that displays the {@link MainWindow}.
	 * @param pane	The {@link JScrollPane}, that displays the Wishlist as a table.
	 * @param mw	The {@link MainWindow}, that called this Worker.
	 * @since 1.0
	 */
	public SetWishlistTask(Song[] wishlist, JLabel lblNoWishlist,
			ServerConnection wrapper, JFrame frame, JScrollPane pane, MainWindow mw) {
		this.wishlist = wishlist;
		this.lblNoWishlist = lblNoWishlist;
		this.wrapper = wrapper;
		this.frame = frame;
		this.oldPane = pane;
		this.mainWindow = mw;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>doInBackground</b></em></p>
	 * <p style="margin-left: 20px">{@code protected Void doInBackground()}</p>
	 * <p style="margin-left: 20px">The Method, that will be executed after 
	 * {@link SwingWorker#execute()} was called for this Worker. Will iterate through the 
	 * {@link #wishlist} and publishes every Song to {@link #process(List)}.</p>
	 * @return	{@code null} as default. There is nothing to be returned.
	 * @throws Exception	Will only throw an Exception if interrupted before completing it's 
	 * Task.
	 * @since 1.0
	 */
	@Override
	protected Void doInBackground() throws Exception {
		util.IO.println(this, "Starting to update the WishlistPane");
		Song[] list = new Song[wishlist.length];
		
		for (int i = 0; i < wishlist.length; i++) {
			list[i] = wishlist[i];
			publish(list);
		}
		publish(list);
		return null;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>process</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void process(List<Song[]>)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever {@link #doInBackground()} read 
	 * out a Song from the Wishlist. Creates a new Table with all Songs, that were read out 
	 * until now, as Elements and adds it to the Frame.</p>
	 * @param list	A List of all Arrays of Songs, that were read out so far.
	 * @since 1.0
	 */
	@Override
	protected void process(List<Song[]> list) {
		Song[] lastList = list.get(list.size()-1);
		lblNoWishlist.setText(""+lastList.length);
		
		Point p = new Point(-1,-1);
		boolean notFirst = false;
		if(oldPane != null) {
			notFirst = true;
			p = oldPane.getViewport().getViewPosition();
			frame.getContentPane().remove(oldPane);
		}
		
		String[] columns = {"Song:", "Votes:"};
		
		String[][] data = new String[lastList.length][2];
		
		for (int i = 0; i < lastList.length; i++) {
			data[i][0] = lastList[i].getName();
			data[i][1] = ""+lastList[i].getVotes();
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
			 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}
			 * </p>
			 * <p style="margin-left: 20px">The Serial Version ID.</p>
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * <p style="margin-left: 10px"><em><b>columnToolTips</b></em></p>
			 * <p style="margin-left: 20px">{@code private String[] columnToolTips}</p>
			 * <p style="margin-left: 20px">The ToolTips for the TableHeaders.</p>
			 */
			private String[] columnToolTips = {"The Name of the Song", 
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
			 * @return false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * <p style="margin-left: 10px"><em><b>createDefaultTableHeader</b></em></p>
			 * <p style="margin-left: 20px">{@code protected JTableHeader 
			 * createDefaultTableHeader()}</p>
			 * <p style="margin-left: 20px">Creates a new TableHeader.</p>
			 * @return the new TableHeader.
			 * @see	JTableHeader
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
					 * <p style="margin-left: 20px">{@code private static final long 
					 * serialVersion UID}</p>
					 * <p style="margin-left: 20px">The Serial Version ID.</p>
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
        
        table.addMouseListener(new TablePopClickListener(table, wishlist, wrapper, 
        		mainWindow));
        
        table.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer(lastList));
        
        table.getColumnModel().getColumn(0).setMinWidth(210);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
		JScrollPane wishlistPane = new JScrollPane(table);
		frame.getContentPane().add(wishlistPane, NewClientLayout.WISHLIST_PANE);
		frame.getContentPane().add(lblNoWishlist, NewClientLayout.COUNT_WISHLIST_LABEL);
		if (notFirst)
			wishlistPane.getViewport().setViewPosition(p);
		oldPane = wishlistPane;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>done</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void done()}</p>
	 * <p style="margin-left: 20px">Will be called, after {@link #doInBackground()} has 
	 * finished. Will call {@link mainWindow#doneWishlistUpdate(Song[], JLabel, JFrame, 
	 * JScrollPane)}.</p>
	 * @since 1.0
	 */
	@Override
	protected void done() {
		util.IO.println(this, "Updated WishlistPane");
		mainWindow.doneWishlistUpdate(wishlist, lblNoWishlist, frame, oldPane);
	}
}
