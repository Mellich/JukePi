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

public class SetGaplistTask extends SwingWorker<Song[], Song[]>{

	private Song[] gaplist;
	private Song[] newGaplist;
	private JLabel lblNoGaplist;
	private ServerConnection wrapper;
	private JFrame frame;
	private JScrollPane oldGaplistPane;
	private MainWindow mw;
	
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
	protected Song[] doInBackground() throws Exception {
		gaplist = new Song[newGaplist.length];
		
		for (int i = 0; i < newGaplist.length; i++) {
			gaplist[i] = newGaplist[i];
			publish(gaplist);
		}
		return gaplist;
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
        
  //TODO      table.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer());
        
		JScrollPane gaplistPane = new JScrollPane(table);
		
		if (notFirst) 
			gaplistPane.getViewport().setViewPosition(p);
		oldGaplistPane = gaplistPane;
		frame.getContentPane().add(oldGaplistPane, ClientLayout.GAPLIST_SCROLL);
		System.gc();
	}
	
	@Override
	protected void done() {
		mw.doneGaplistUpdate(gaplist, lblNoGaplist, frame, oldGaplistPane);
	}
}
