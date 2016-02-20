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
 * <p>The {@link SwingWorker}, that fills the GaplistPane with the Tracks in the Gaplist and 
 * their Parser-Status.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #SetGaplistTask(Song[], JLabel, ServerConnection, JFrame, JScrollPane, 
 * 					MainWindow)}:
 * 				The Constructor for this Task, that will set the given Parameters to their 
 * 				belonging variables.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<ul>
 * 			<li>{@link #doInBackground()}:
 * 				Iterates through the {@link #gaplist}-Field and fills the {@link 
 * 				#oldGaplistPane} with Strings, representing the Songs in the Gaplist.</li>
 * 
 * 			<li>{@link #done()}:
 * 				This Method is called, after updating the Pane. It will call 
 * 				{@link MainWindow#doneGaplistUpdate(Song[], JLabel, JFrame, JScrollPane)}.
 * 		
 * 			<li>{@link #process(List)}:
 * 				Is called, whenever the {@link #doInBackground()}-Method has read out a new 
 * 				Song from the {@link #gaplist}-Field and puts it in a new List, that consists 
 * 				of all Songs that were read out until then.</li>
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
 * <li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that inherits the {@link #oldGaplistPane} and thus is the 
 * 				{@link #mw}.</li>
 * 
 * 			<li>{@link #gaplist}:
 * 				The Gaplist as an Array of {@link Song}s.</li>
 * 
 * 			<li>{@link #lblNoGaplist}:
 * 				The {@link JLabel}, that displays the amount of Songs in the Gaplist.</li>
 * 
 * 			<li>{@link #mw}:
 * 				The {@link MainWindow}, that called this Worker.</li>
 * 
 * 			<li>{@link #oldGaplistPane}:
 * 				The {@link JScrollPane}, that inherits the Table, that displays the Gaplist.
 * 				</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server, that will send and receive 
 * 				Messages to/from the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class SetGaplistTask extends SwingWorker<Void, Song[]>{
	
	/**
	 * <p style="margin-left: 10px"><em><b>gaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] gaplist}</p>
	 * <p style="margin-left: 20px">The new Gaplist as an Array of {@link Song}s.</p>
	 */
	private Song[] gaplist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblNoGaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblNoGaplist}</p>
	 * <p style="margin-left: 20px">The {@link JLabel} that displays the Number of Tracks in 
	 * the Gaplist.</p>
	 */
	private JLabel lblNoGaplist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection} to the Server. This 
	 * Connection is used to send and receive Messages from/to the Server.</p>
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
	 * <p style="margin-left: 10px"><em><b>oldGaplistPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane oldGaplistPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that contains the Gaplist Pane.
	 * </p>
	 */
	private JScrollPane oldGaplistPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>mw</b></em></p>
	 * <p style="margin-left: 20px">{@code private MainWindow mw}</p>
	 * <p style="margin-left: 20px">The {@link MainWindow} this Task is called from.</p>
	 */
	private MainWindow mw;
	
	/**
	 * <p style="margin-left: 10px"><em><b>SetGaplistTask</b></em></p>
	 * <p style="margin-left: 20px">{@code public SetGaplistTask(Song[], JLabel, 
	 * ServerConnection, JFrame, JScrollPane, MainWindow)}</p>
	 * <p style="margin-left: 20px">The Constructor for this Task.</p>
	 * @param gaplist	The new Gaplist as an Array of {@link Song}s.
	 * @param lblNoGaplist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * Gaplist.
	 * @param wrapper	The {@link ServerConnection} to the Server the Client is connected to.
	 * @param frame	The {@link JFrame}, the Task is operating on.
	 * @param pane	The {@link JScrollPane}, that contains the Gaplist as displayed Table.
	 * @param mw	The {@link MainWindow}, this Task is called from.
	 * @since 1.0
	 */
	public SetGaplistTask(Song[] gaplist, JLabel lblNoGaplist, 
			ServerConnection wrapper, JFrame frame, JScrollPane pane, MainWindow mw) {
		this.gaplist = gaplist;
		this.lblNoGaplist = lblNoGaplist;
		this.wrapper = wrapper;
		this.frame = frame;
		this.oldGaplistPane = pane;
		this.mw = mw;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>doInBackground</b></em></p>
	 * <p style="margin-left: 20px">{@code protected Void doInBackground()}</p>
	 * <p style="margin-left: 20px">The Method, that will be executed after 
	 * {@link SwingWorker#execute()} was called for this Worker. Will iterate through the 
	 * {@link #gaplist} and publishes every Song to {@link #process(List)}.</p>
	 * @return	{@code null} as default. There is nothing to be returned.
	 * @throws Exception	Will only throw an Exception if interrupted before completing it's 
	 * Task.
	 * @since 1.0
	 */
	@Override
	protected Void doInBackground() throws Exception {
		util.IO.println(this, "Starting to update GaplistPane");
		Song[] gaplist = new Song[this.gaplist.length];
		
		for (int i = 0; i < this.gaplist.length; i++) {
			gaplist[i] = this.gaplist[i];
			publish(gaplist);
		}
		publish(gaplist);
		return null;
	}

	/**
	 * <p style="margin-left: 10px"><em><b>process</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void process(List<Song[]>)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever {@link #doInBackground()} read 
	 * out a new Song from the Gaplist. Creates a new Table with all Songs, that were read 
	 * out until now, as Elements and adds it to the Frame.</p>
	 * @param list	A List of all Arrays of Songs, that were read out so far.
	 * @since 1.0
	 */
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
			 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
			 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}
			 * </p>
			 * <p style="margin-left: 20px">The Serial Version ID.</p>
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * <p style="margin-left: 10px"><em><b>columnToolTips</b></em></p>
			 * <p style="margin-left: 20px">{@code private String[] columnToolTips}</p>
			 * <p style="margin-left: 20px">The ToolTip for the column.</p>
			 */
			private String[] columnToolTips = {"The Name of the Song in the Gaplist"};

			/**
			 * <p style="margin-left: 10px"><em><b>getToolTipText</b></em></p>
			 * <p style="margin-left: 20px">{@code public String getToolTipText(MouseEvent)}
			 * </p>
			 * <p style="margin-left: 20px">Returns the ToolTip for the Cell at the Cursor's 
			 * Position.</p>
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
			 * <p style="margin-left: 10px"><em><b>isCellEditable</b></em></p>
			 * <p style="margin-left: 20px">{@code public boolean isCellEditable(int, int)}</p>
			 * <p style="margin-left: 20px">Returns, if the Cell at the given index is 
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
			 * @return	The new TableHeader.
			 * @see JTableHeader
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
					 * <p style="margin-left: 20px">{@code private static final long 
					 * serialVersionUID}</p>
					 * <p style="margin-left: 20px">The Serial Version ID.</p>
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * <p style="margin-left: 10px"><em><b>getToolTipText</b></em></p>
					 * <p style="margin-left: 20px">{@code public String 
					 * getToolTipText(MouseEvent)}</p>
					 * <p style="margin-left: 20px">Returns the ToolTip for the column at the 
					 * Cursor's Position.</p>
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
		frame.getContentPane().add(gaplistPane, NewClientLayout.GAPLIST_PANE);
		System.gc();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>done</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void done()}</p>
	 * <p style="margin-left: 20px">Will be called, after {@link #doInBackground()} has 
	 * finished. Will call {@link MainWindow#doneGaplistUpdate(Song[], JLabel, JFrame, 
	 * JScrollPane)}.</p>
	 * @since 1.0
	 */
	@Override
	protected void done() {
		util.IO.println(this, "Updated GaplistPane");
		mw.doneGaplistUpdate(gaplist, lblNoGaplist, frame, oldGaplistPane);
	}
}
