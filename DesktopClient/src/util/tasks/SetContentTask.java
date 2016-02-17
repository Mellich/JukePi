package util.tasks;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
 * <p>The {@link SwingWorker}, that will fill the Content-Table.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #SetContentTask(Song[], JScrollPane, JFrame, DisplayGaplistsWindow)}:
 * 				The Constructor for this Task. Will set all Fields to their given Parameter 
 * 				Values.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<ul>
 * 			<li>{@link #doInBackground()}:
 * 				Iterates through the {@link #content}-Field and fills the {@link 
 * 				#oldContentPane} with List-Elements, that represent these Songs.</li>
 * 		
 * 			<li>{@link #done()}:
 * 				Is called, when the Task is finished. This will call 
 * 				{@link DisplayGaplistsWindow#doneContentUpdate(JFrame, JScrollPane)} to update 
 * 				the Window, that the Task was finished.</li>
 * 		
 * 			<li>{@link #process(List)}:
 * 				Is called, whenever the {@link #doInBackground()}-Method has read out a new 
 * 				Song from the {@link #content}-Field and puts it in a new List, that consists 
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
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #displayGaplistsWindow}:
 * 				The {@link DisplayGaplistsWindow}, that called this Worker.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The Frame, that inherits the {@link #oldContentPane} and thus is the 
 * 				{@link DisplayGaplistsWindow}, that this Worker is operating on.</li>
 * 		
 * 			<li>{@link #content}:
 * 				The content of the Gaplist, that will be put as a List into the 
 * 				{@link #oldContentPane} as an Array of {@link Song}s.</li>
 * 
 * 			<li>{@link #oldContentPane}:
 * 				The {@link JScrollPane}, that contains the Table, which will be filled by this 
 * 				Task.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class SetContentTask extends SwingWorker<Void, Song[]>{
	
	/**	 
	 * <p style="margin-left: 10px"><em><b>content</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] content}</p>
	 * <p style="margin-left: 20px">The Content of the Gaplist to be shown, as it is 
	 * prohibited by the Server. This is the full Array of {@link Song}s in the Gaplist.</p>
	 */
	private Song[] content;
	
	/**
	 * <p style="margin-left: 10px"><em><b>oldContentPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane oldContentPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that will display the Content as 
	 * a Table.</p>
	 */
	private JScrollPane oldContentPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame, the {@link MainWindow} is shown in.</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>displayGaplistsWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private DisplayGaplistsWindow 
	 * displayGaplistsWindow}</p>
	 * <p style="margin-left: 20px">The {@link DisplayGaplistsWindow}, that called this Worker.
	 * </p>
	 */
	private DisplayGaplistsWindow displayGaplistsWindow;

	/**
	 * <p style="margin-left: 10px"><em><b>SetContentTask</b></em></p>
	 * <p style="margin-left: 20px">{@code public SetContentTask(Song[], JScrollPane, 
	 * JFrame, DisplayGaplistsWindow)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Worker.</p>
	 * @param content	The Content of the Gaplist as an Array of {@link Song}s.
	 * @param oldContentPane	The {@link JScrollPane}, that displays the Content as a table.
	 * @param frame 	The {@link JFrame}, that displays the {@link DisplayGaplistsWindow}.
	 * @param displayGaplistsWindow	The {@link DisplayGaplistsWindow}, that called this Worker.
	 * @since 1.0
	 */
	public SetContentTask(Song[] content, JScrollPane oldContentPane, JFrame frame, 
			DisplayGaplistsWindow displayGaplistsWindow) {
		this.content = content;
		this.oldContentPane = oldContentPane;
		this.frame = frame;
		this.displayGaplistsWindow = displayGaplistsWindow;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>doInBackground</b></em></p>
	 * <p style="margin-left: 20px">{@code protected Void doInBackground()}</p>
	 * <p style="margin-left: 20px">The Method, that will be executed after 
	 * {@link SwingWorker#execute()} was called for this Worker. Will iterate through the 
	 * {@link #content} and publishes every Song to {@link #process(List)}.</p>
	 * @return	{@code null} as default. There is nothing to be returned.
	 * @throws Exception	Will only throw an Exception if interrupted before completing it's 
	 * Task.
	 * @since 1.0
	 */
	@Override
	protected Void doInBackground() throws Exception {
		util.IO.println(this, "Starting to update ContentPane");
		Song[] content = new Song[this.content.length];
		
		for (int i = 0; i < this.content.length; i++) {
			content[i] = this.content[i];
			publish(content);
		}
		publish(content);
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
		if (oldContentPane != null)
			frame.getContentPane().remove(oldContentPane);
		
		String[] columns = {"Content:"};
		String[][] data = new String[0][1];
		
		if (lastList != null) {
			data = new String[lastList.length][1];
		
			for (int i = 0; i < lastList.length; i++)
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
			private String[] columnToolTips = {"The Name of the Song in the selected "
					+ "Gaplist."};

			/**
			 * <p style="margin-left: 10px"><em><b>getToolTipText</b></em></p>
			 * <p style="margin-left: 20px">{@code public String getToolTipText(MouseEvent)}
			 * </p>
			 * <p style="margin-left: 20px">Returns the ToolTip of the Cell at the Cursor's 
			 * Position.</p>
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
			 * <p style="margin-left: 10px"><em><b>isCellEditable</b></em></p>
			 * <p style="margin-left: 20px">{@code public boolean isCellEditable(int, int)}</p>
			 * <p style="margin-left: 20px">Returns, if the Cell at the given index is 
			 * editable.</p>
			 * @param row	The row-Index.
			 * @param column	The column-Index.
			 * @return	false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * <p style="margin-left: 10px"><em><b>createDefaultTableHeader</b></em></p>
			 * <p style="margin-left: 20px">{@code protected JTableHeader 
			 * createDefaultTableHeader()}</p>
			 * <p style="margin-left: 20px">Creates a new TableHeader.</p>
			 * @return The new TableHeader.
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
	
	/**
	 * <p style="margin-left: 10px"><em><b>done</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void done()}</p>
	 * <p style="margin-left: 20px">Will be called, after {@link #doInBackground()} has 
	 * finished. Will call {@link DisplayGaplistsWindow#doneContentUpdate(Song[], JLabel, 
	 * JFrame, JScrollPane)}.</p>
	 * @since 1.0
	 */
	@Override
	protected void done() {
		util.IO.println(this, "Updated ContentPane.");
		displayGaplistsWindow.doneContentUpdate(frame, oldContentPane);
	}
	
}
