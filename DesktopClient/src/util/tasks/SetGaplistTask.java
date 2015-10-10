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
 * The {@link SwingWorker}, that fills the GaplistPane with the Tracks in the Gaplist and their
 * Parser-Status.
 * @author Haeldeus
 * @version 1.0
 */
public class SetGaplistTask extends SwingWorker<Void, Song[]>{

	/**
	 * The old Gaplist as an Array of {@link Song}s.
	 */
	private Song[] gaplist;
	
	/**
	 * The new Gaplist as an Array of {@link Song}s.
	 */
	private Song[] newGaplist;
	
	/**
	 * The {@link JLabel} that displays the Number of Tracks in the Gaplist.
	 */
	private JLabel lblNoGaplist;
	
	/**
	 * The {@link ServerConnection} to the Server. This Connection is used to send and receive 
	 * Messages from/to the Server.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The {@link JFrame}, that displays the {@link MainWindow}.
	 */
	private JFrame frame;
	
	/**
	 * The {@link JScrollPane}, that contains the Gaplist Pane
	 */
	private JScrollPane oldGaplistPane;
	
	/**
	 * The {@link MainWindow} this Task is called from.
	 */
	private MainWindow mw;
	
	/**
	 * The Constructor for this Task.
	 * @param gaplist	The old Gaplist as an Array of {@link Song}s.
	 * @param newGaplist	The new Gaplist as an Array of {@link Song}s.
	 * @param lblNoGaplist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * Gaplist.
	 * @param wrapper	The {@link ServerConnection} to the Server the Client is connected to.
	 * @param frame	The {@link JFrame}, the Task is operating on.
	 * @param pane	The {@link JScrollPane}, that contains the Gaplist as displayed Table.
	 * @param mw	The {@link MainWindow}, this Task is called from.
	 * @since 1.0
	 */
	public SetGaplistTask(Song[] gaplist, Song[] newGaplist, JLabel lblNoGaplist, 
			ServerConnection wrapper, JFrame frame, JScrollPane pane, MainWindow mw) {
		this.newGaplist = newGaplist;
		this.gaplist = gaplist;
		this.lblNoGaplist = lblNoGaplist;
		this.wrapper = wrapper;
		this.frame = frame;
		this.oldGaplistPane = pane;
		this.mw = mw;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		gaplist = new Song[newGaplist.length];
		
		for (int i = 0; i < newGaplist.length; i++) {
			gaplist[i] = newGaplist[i];
			publish(gaplist);
		}
		return null;
	}

	@Override
	protected void process(List<Song[]> songs) {
		Song[] lastList = songs.get(songs.size()-1);
		lblNoGaplist.setText(""+lastList.length);
		
		Point p = new Point(-1,-1);
		boolean notFirst = false;
		
		if (oldGaplistPane != null) {
			notFirst = true;
			p = oldGaplistPane.getViewport().getViewPosition();
			frame.getContentPane().remove(oldGaplistPane);
		}
		
		String[] columns = {"Gaplist:"};
		
		String[][] data = new String[lastList.length][1];
		
		for (int i = 0; i < lastList.length; i++) {
			data[i][0] = lastList[i].getName();
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * The Serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The ToolTip for the column.
			 */
			private String [] columnToolTips = {"The Name of the Song in the Gaplist"};

			/**
			 * Returns the ToolTip for the Cell at the Cursor's Position.
			 * @param e	The MouseEvent.
			 * @return The ToolTip for the Cell at the Position of the Cursor.
			 */
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				
				if (colIndex == 0) {
					switch (gaplist[rowIndex].getParseStatus()){
						case PARSED: tip = "Parsed - "; break;
						case NOT_PARSED: tip = "Not Parsed - "; break;
						case PARSING: tip = "Currently Parsing - "; break;
						default: tip = "Error while parsing: Check the URL! - "; break;
					}
					tip = tip.concat(""+ getValueAt(rowIndex, colIndex));
				}
				return tip;
			}
			
			/**
			 * Returns, if the Cell at the given index is editable.
			 * @param row	The row-index of the Cell.
			 * @param column	The column-index of the Cell.
			 * @return false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return	The new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * The Serial Version ID.
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * Returns the ToolTip for the column at the Cursor's Position.
					 * @param e	The MouseEvent.
					 * @return	The ToolTip for the given column.
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
        
        table.addMouseListener(new TablePopClickListener(table, gaplist, wrapper, mw));
        
        table.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer(lastList));
        
		JScrollPane gaplistPane = new JScrollPane(table);
		
		if (notFirst) 
			gaplistPane.getViewport().setViewPosition(p);
		oldGaplistPane = gaplistPane;
		frame.getContentPane().add(gaplistPane, ClientLayout.GAPLIST_SCROLL);
		System.gc();
	}
	
	@Override
	protected void done() {
		mw.doneGaplistUpdate(gaplist, lblNoGaplist, frame, oldGaplistPane);
	}
}
