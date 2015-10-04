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
import util.layouts.ClientLayout;
import windows.MainWindow;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

public class SetWishlistTask extends SwingWorker<Void, Song[]>{

	private Song[] newWishlist;
	private Song[] wishlist;
	private JLabel lblNoWishlist;
	private ServerConnection wrapper;
	private JFrame frame;
	private JScrollPane oldPane;
	private MainWindow mw;

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
		wishlist = new Song[newWishlist.length];
		
		for (int i = 0; i < newWishlist.length; i++) {
			wishlist[i] = newWishlist[i];
			publish(wishlist);
		}
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
		mw.doneWishlistUpdate(wishlist, lblNoWishlist, frame, oldPane);
	}
}
