package util.tasks;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.JTableHeader;

import util.TablePopClickListener;
import util.layouts.DisplayGaplistsLayout;
import windows.DisplayGaplistsWindow;
import windows.MainWindow;
import client.serverconnection.Song;

/**
 * The {@link SwingWorker}, that will fill the Content-Table.
 * @author Haeldeus
 * @version 1.0
 */
public class SetContentTask extends SwingWorker<Void, Song[]>{

	/**
	 * The Content of the Gaplist to be shown as an Array of {@link Song}s. This Array will be
	 * an Array of {@code null} in the beginning and will be filled while this Task is working.
	 */
	private Song[] content;
	
	/**
	 * The Content of the Gaplist to be shown, as it is prohibited by the Server. This is the 
	 * full Array of {@link Song}s in the Gaplist.
	 */
	private Song[] newContent;
	
	/**
	 * The {@link JScrollPane}, that will display the Content as a Table.
	 */
	private JScrollPane oldContentPane;
	
	/**
	 * The Frame, the {@link MainWindow} is shown in.
	 */
	private JFrame frame;
	
	/**
	 * The {@link MainWindow}, that called this Worker.
	 */
	private DisplayGaplistsWindow dgw;

	/**
	 * The Constructor for the Worker.
	 * @param newContent	The Content of the Gaplist as an Array of {@link Song}s.
	 * @param oldContentPane	The {@link JScrollPane}, that displays the Content as a table.
	 * @param frame 	The {@link JFrame}, that displays the {@link MainWindow}.
	 * @param mw	The {@link MainWindow}, that called this Worker.
	 * @since 1.0
	 */
	public SetContentTask(Song[] newContent, JScrollPane oldContentPane, JFrame frame, DisplayGaplistsWindow dgw) {
		this.newContent = newContent;
		this.oldContentPane = oldContentPane;
		this.frame = frame;
		this.dgw = dgw;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		util.IO.println(this, "Starting to update ContentPane");
		content = new Song[newContent.length];
		
		for (int i = 0; i < newContent.length; i++) {
			content[i] = newContent[i];
			publish(content);
		}
		publish(content);
		return null;
	}

	@Override
	protected void process(List<Song[]> songs) {
		Song[] lastList = songs.get(songs.size()-1);
		if (oldContentPane != null)
			frame.getContentPane().remove(oldContentPane);
		
		String[] columns = {"Content:"};
		String[][] data = new String[0][1];
		
		if (content != null) {
			data = new String[lastList.length][1];
		
			for (int i = 0; i < lastList.length; i++)
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
			private String [] columnToolTips = {"The Name of the Song in the selected Gaplist."};

			/**
			 * Returns the ToolTip of the Cell at the Cursor's Position.
			 * @param e	The MouseEvent.
			 * @return The ToolTip of the Cell at the Position of the Cursor.
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
			 * Returns, if the Cell at the given index is editable.
			 * @param row	The row-Index.
			 * @param column	The column-Index.
			 * @return	false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return The new TableHeader.
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
					 * @return	The ToolTip for the column at the Position of the Cursor.
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
        table.addMouseListener(new TablePopClickListener(table, lastList));
		JScrollPane contentPane = new JScrollPane(table);
		oldContentPane = contentPane;
		frame.getContentPane().add(contentPane, DisplayGaplistsLayout.CONTENT_PANE);
	}
	
	@Override
	protected void done() {
		util.IO.println(this, "Updated ContentPane.");
		dgw.doneContentUpdate(frame, oldContentPane);
	}
	
}
