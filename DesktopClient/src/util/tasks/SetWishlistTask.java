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
import util.layouts.ClientLayout;
import windows.MainWindow;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * The {@link SwingWorker}, that will fill the Wishlist table.
 * @author Haeldeus
 * @version 1.0
 */
public class SetWishlistTask extends SwingWorker<Void, Song[]>{

	/**
	 * The new Wishlist to be loaded as an Array of {@link Song}s.
	 */
	private Song[] newWishlist;
	
	/**
	 * The Wishlist, that is actually displayed on the Frame.
	 */
	private Song[] wishlist;
	
	/**
	 * The {@link JLabel}, that displays the Number of Tracks in the Wishlist.
	 */
	private JLabel lblNoWishlist;
	
	/**
	 * The {@link ServerConnection}, that will send Messages to the Server.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The {@link JFrame}, that displays the {@link MainWindow}.
	 */
	private JFrame frame;
	
	/**
	 * The {@link JScrollPane}, that displays the Wishlist as a table.
	 */
	private JScrollPane oldPane;
	
	/**
	 * The {@link MainWindow}, that called this Worker.
	 */
	private MainWindow mw;

	/**
	 * The Constructor for this Worker.
	 * @param wishlist	The Wishlist, that is shown at the Frame, as an Array of {@link Song}s.
	 * @param newWishlist	The new Wishlist to be shown as an Array of {@link Song}s.
	 * @param lblNoWishlist	The {@link JLabel}, that displays the number of Tracks in the 
	 * Wishlist.
	 * @param wrapper The {@link ServerConnection}, that will send the Messages to the Server.
	 * @param frame	The {@link JFrame}, that displays the {@link MainWindow}.
	 * @param pane	The {@link JScrollPane}, that displays the Wishlist as a table.
	 * @param mw	The {@link MainWindow}, that called this Worker.
	 * @since 1.0
	 */
	public SetWishlistTask(Song[] wishlist, Song[] newWishlist, JLabel lblNoWishlist,
			ServerConnection wrapper, JFrame frame, JScrollPane pane, MainWindow mw) {
		this.newWishlist = newWishlist;
		this.wishlist = wishlist;
		this.lblNoWishlist = lblNoWishlist;
		this.wrapper = wrapper;
		this.frame = frame;
		this.oldPane = pane;
		this.mw = mw;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		util.IO.println(this, "Starting to update the WishlistPane");
		wishlist = new Song[newWishlist.length];
		
		for (int i = 0; i < newWishlist.length; i++) {
			wishlist[i] = newWishlist[i];
			publish(wishlist);
		}
		publish(wishlist);
		return null;
	}
	
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
			 * The Serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The ToolTips for the TableHeaders.
			 */
			private String [] columnToolTips = {"The Name of the Song", "The Votes for this Song"};

			/**
			 * Returns the ToolTip for the Cell at the given Position of the Cursor.
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
			 * Returns, if the Cell at the given Position is editable.
			 * @param row	The row-index of the Cell.
			 * @param column	The column-index of the Cell.
			 * @return false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return the new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * The Serial Version ID.
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * Returns the ToolTip for the column at the given Cursor's Position.
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
        
        table.addMouseListener(new TablePopClickListener(table, wishlist, wrapper, mw));
        
        table.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer(lastList));
        
        table.getColumnModel().getColumn(0).setMinWidth(210);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
		JScrollPane wishlistPane = new JScrollPane(table);
		frame.getContentPane().add(wishlistPane, ClientLayout.WISHLIST_SCROLL);
		if (notFirst)
			wishlistPane.getViewport().setViewPosition(p);
		oldPane = wishlistPane;
	}
	
	@Override
	protected void done() {
		util.IO.println(this, "Updated WishlistPane");
		mw.doneWishlistUpdate(wishlist, lblNoWishlist, frame, oldPane);
	}
}
