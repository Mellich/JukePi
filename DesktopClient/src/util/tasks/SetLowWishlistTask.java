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
import util.layouts.LowClientLayout;
import windows.LowClientWindow;
import client.serverconnection.Song;

/**
* <p>The {@link SwingWorker}, that will fill the Wishlist table at the LowClient.</p>
* 
* <h3>Provided Methods:</h3>
* <ul>
* 	<li><b>Public:</b>
* 		<ul>
* 			<li>{@link #SetLowWishlistTask(Song[], JLabel, JFrame, JScrollPane, 
* 				LowClientWindow)}:
* 				The Constructor for this Task.</li>
* 		</ul>
* 	</li>
* 	<li><b>Protected:</b>
* 		<ul>
* 			<li>{@link #doInBackground()}:
* 				The Method, that will be executed after {@link SwingWorker#execute()} was 
* 				called for this Worker. Will iterate through the {@link #wishlist} and 
* 				publishes every Song to {@link #process(List)}.</li>
* 
* 			<li>{@link #done()}:
* 				Will be called, after {@link #doInBackground()} has finished. Will call 
* 				{@link LowClientWindow#doneWishlistUpdate(Song[], JLabel, JFrame, 
* 				JScrollPane)}.<li>
* 
* 			<li>{@link #process(List)}:
* 				Will be called, whenever {@link #doInBackground()} read out a new Song from 
* 				the Wishlist. Creates a new Table with all Songs, that were read out until 
* 				now, as Elements and adds it to the Frame.</li>
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
* 				The {@link JFrame}, that displays the {@link #lowClientWindow}.</li>
* 
* 			<li>{@link #lblNoWishlist}:
* 				The {@link JLabel}, that displays the amount of Tracks in the Wishlist.</li>
* 
* 			<li>{@link #lowClientWindow}:
* 				The {@link LowClientWindow}, that called this Worker.</li>
* 
* 			<li>{@link #oldPane}:
* 				The {@link JScrollPane}, that displays the Wishlist as a Table.</li>
* 
* 			<li>{@link #wishlist}:
* 				The Wishlist as an Array of {@link Song}s.</li>
* 		</ul>
* 	</li>
* </ul>
* @author Haeldeus
* @version 1.0
*/
public class SetLowWishlistTask extends SwingWorker<Void, Song[]>{

	/**
	 * <p style="margin-left: 10px"><em><b>wishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] wishlist}</p>
	 * <p style="margin-left: 20px">The Wishlist as an Array of {@link Song}s.</p>
	 */
	private Song[] wishlist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblNoWishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblNoWishlist}</p>
	 * <p style="margin-left: 20px">The {@link JLabe}, that displays the amount of Tracks in 
	 * the Wishlist.</p>
	 */
	private JLabel lblNoWishlist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that displays {@link #lowClientWindow}.
	 * </p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>oldPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane oldPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that displays the Wishlist as a 
	 * Table.</p>
	 */
	private JScrollPane oldPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lowClientWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private LowClientWindow lowClientWindow}</p>
	 * <p style="margin-left: 20px">The {@link LowClientWindow}, that called this Worker.</p>
	 */
	private LowClientWindow lowClientWindow;

	/**
	 * <p style="margin-left: 10px"><em><b>SetLowWishlistTask</b></em></p>
	 * <p style="margin-left: 20px">{@code public SetLowWishlistTask(Song[], JLabel, JFrame, 
	 * JScrollPane, LowClientWindow)}</p>
	 * <p style="margin-left: 20px">The Constructor for this Task.</p>
	 * @param wishlist	The Wishlist as an Array of {@link Song}s.
	 * @param lblNoWishlist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * 						Wishlist.
	 * @param frame	The {@link JFrame}, that displays the LowClientWindow, that called this 
	 * 				Worker.
	 * @param pane	The {@link JScrollPane}, that displays the Wishlist as a Table.
	 * @param lowClientWindow	The {@link LowClientWindow}, that called this Worker.
	 * @since 1.0
	 */
	public SetLowWishlistTask(Song[] wishlist, JLabel lblNoWishlist, JFrame frame, 
			JScrollPane pane, LowClientWindow lowClientWindow) {
		this.wishlist = wishlist;
		this.lblNoWishlist = lblNoWishlist;
		this.frame = frame;
		this.oldPane = pane;
		this.lowClientWindow = lowClientWindow;
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
	 * out a new Song from the Wishlist. Creates a new Table with all Songs, that were read 
	 * out until now, as Elements and adds it to the Frame.</p>
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
        
        table.addMouseListener(new TablePopClickListener(table, wishlist));
        
        table.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer(lastList));
        
        table.getColumnModel().getColumn(0).setMinWidth(210);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
		JScrollPane wishlistPane = new JScrollPane(table);
		frame.getContentPane().add(wishlistPane, LowClientLayout.WISHLIST_PANE);
		frame.getContentPane().add(lblNoWishlist, LowClientLayout.COUNT_WISHLIST_LABEL);
		if (notFirst)
			wishlistPane.getViewport().setViewPosition(p);
		oldPane = wishlistPane;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>done</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void done()}</p>
	 * <p style="margin-left: 20px">Will be called, after {@link #doInBackground()} has 
	 * finished. Will call {@link LowClientWindow#doneWishlistUpdate(Song[], JLabel, JFrame, 
	 * JScrollPane)}.</p>
	 * @since 1.0
	 */
	@Override
	protected void done() {
		util.IO.println(this, "Updated WishlistPane");
		lowClientWindow.doneWishlistUpdate(wishlist, lblNoWishlist, frame, oldPane);
	}
}
