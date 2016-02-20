package util.tasks;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.JTableHeader;

import client.serverconnection.Song;
import util.layouts.DisplayGaplistsLayout;
import windows.DisplayGaplistsWindow;
import windows.MainWindow;

/**
 * <p>The {@link SwingWorker}, that will fill the Table of Saved Gaplists.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #SetSavedGaplistsTask(JFrame, String[], JScrollPane, 
 * 				DisplayGaplistsWindow)}:
 * 				The Constructor for this Task. Will set the given Parameters to their 
 * 				belonging Instance variables.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<ul>
 * 			<li>{@link #doInBackground()}:
 * 				Iterates through the {@link #gaplists}-Field and fills the 
 * 				{@link #oldSavedGaplistPane} with Strings, representing the Gaplists.</li>
 * 
 * 			<li>{@link #done()}:
 * 				This Method is called, after updating the Pane. It will call 
 * 				{@link DisplayGaplistsWindow#doneSavedListsUpdate(JFrame, JScrollPane)}.</li>
 * 
 * 			<li>{@link #process(List)}:
 * 				Is called, whenever the {@link #doInBackground()}-Method has read out a new 
 * 				Gaplist from the {@link #gaplists}-Field and puts it in a new List, that 
 * 				consists of all Gaplists, that were read out until then.</li>
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
 * 				The {@link JFrame}, that inherits the {@link #oldSavedGaplistPane} and thus is 
 * 				the {@link #displayGaplistsWindow}.</li>
 * 
 * 			<li>{@link #gaplists}:
 * 				All Gaplists, saved on the Server as an Array of {@code String}s. Each 
 * 				{@code String} is the Title of one Gaplist.</li>
 * 
 * 			<li>{@link #oldSavedGaplistPane}:
 * 				The {@link JScrollPane}, that displays the Table, where the Gaplists are shown 
 * 				in.</li>	
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class SetSavedGaplistsTask extends SwingWorker<Void, String[]>{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that displays the {@link MainWindow}.
	 * </p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>gaplists</b></em></p>
	 * <p style="margin-left: 20px">{@code private String[] gaplists}</p>
	 * <p style="margin-left: 20px">The Gaplists saved on the Server as an Array of Strings.
	 * </p>
	 */
	private String[] gaplists;
	
	/**
	 * <p style="margin-left: 10px"><em><b>oldSavedGaplistPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane oldSavedGaplistPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that displays the saved Gaplists 
	 * as a table.</p>
	 */
	private JScrollPane oldSavedGaplistPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>displayGaplistsWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private DisplayGaplistsWindow displayGaplistsWindow}
	 * </p>
	 * <p style="margin-left: 20px">The {@link DisplayGaplistsWindow}, that called this Worker.
	 * </p>
	 */
	private DisplayGaplistsWindow displayGaplistsWindow;
	
	/**
	 * <p style="margin-left: 10px"><em><b>SetSavedGaplistsTask</b></em></p>
	 * <p style="margin-left: 20px">{@code public SetSavedGaplistsTask(JFrame, String[], 
	 * JScrollPane, DisplayGaplistsWindow)}</p>
	 * <p style="margin-left: 20px">The Constructor for this Worker.</p>
	 * @param frame	The {@link JFrame}, that displays the {@link MainWindow}.
	 * @param gaplists	The Gaplists saved on the Server as an Array of Strings.
	 * @param gaplistsPane	The {@link JScrollPane}, that displays the saved Gaplists as a 
	 * table.
	 * @param dgw	The {@link MainWindow}, that called this Worker.
	 * @since 1.0
	 */
	public SetSavedGaplistsTask(JFrame frame, String[] gaplists, JScrollPane gaplistsPane, 
			DisplayGaplistsWindow dgw) {
		this.frame = frame;
		this.gaplists = gaplists;
		this.oldSavedGaplistPane = gaplistsPane;
		this.displayGaplistsWindow = dgw;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>doInBackground</b></em></p>
	 * <p style="margin-left: 20px">{@code protected Void doInBackground()}</p>
	 * <p style="margin-left: 20px">The Method, that will be executed after 
	 * {@link SwingWorker#execute()} was called for this Worker. Will iterate through the 
	 * {@link #gaplists} and publishes every Song to {@link #process(List)}.</p>
	 * @return	{@code null} as default. There is nothing to be returned.
	 * @throws Exception	Will only throw an Exception if interrupted before completing it's 
	 * Task.
	 * @since 1.0
	 */
	@Override
	protected Void doInBackground() throws Exception {
		util.IO.println(this, "Starting to update saved Gaplists");
		String[] savedLists = new String[gaplists.length];
		for (int i = 0; i < gaplists.length; i++) {
			savedLists[i] = gaplists[i];
			publish(savedLists);
		}
		publish(savedLists);
		return null;
	}

	/**
	 * <p style="margin-left: 10px"><em><b>process</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void process(List<Song[]>)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever {@link #doInBackground()} read 
	 * out a Gaplist. Creates a new Table with all Gaplists, that were read out until now, as 
	 * Elements and adds it to the Frame.</p>
	 * @param list	A List of all Arrays of Songs, that were read out so far.
	 * @since 1.0
	 */
	@Override
	protected void process(List<String[]> gaplists) {
		String[] lastList = gaplists.get(gaplists.size()-1);
		
		if (oldSavedGaplistPane != null)
			frame.getContentPane().remove(oldSavedGaplistPane);
		
		String[] columns = {"Gaplists:"};
		
		String[][] data = new String[lastList.length][1];
		
		for (int i = 0; i < lastList.length; i++)
			data[i][0] = lastList[i];
		
		JTable table = new JTable(data, columns) {
			/**
			 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
			 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}
			 * </p>
			 * <p style="margin-left: 20px">The serial Version ID.</p>
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * <p style="margin-left: 10px"><em><b>columnToolTips</b></em></p>
			 * <p style="margin-left: 20px">{@code private String[] columnToolTips}</p>
			 * <p style="margin-left: 20px">The Tooltip of the column.</p>
			 */
			private String [] columnToolTips = {"The Name of the Gaplist"};

			/**
			 * <p style="margin-left: 10px"><em><b>getToolTipText</b></em></p>
			 * <p style="margin-left: 20px">{@code public String getToolTipText(MouseEvent)}
			 * </p>
			 * <p style="margin-left: 20px">Returns the ToolTip for the Cell at the Position 
			 * of the Cursor.</p>
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
			 * <p style="margin-left: 20px">Returns, if the Cell at the Row and column is 
			 * editable.</p>
			 * @param row	The row index of the Cell.
			 * @param column The column index of the Cell.
			 * @return	false as default value, since these Cells shouldn't be edited.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * <p style="margin-left: 10px"><em><b></b></em></p>
			 * <p style="margin-left: 20px">{@code protected JTableHeader 
			 * createDefaultTableHeader()}</p>
			 * <p style="margin-left: 20px">Creates a new TableHeader.</p>
			 * @return The new TableHeader.
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
					 * @return The ToolTip for the column at the given Position of the Cursor.
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
		JScrollPane gaplistsPane = new JScrollPane(table);
		frame.getContentPane().add(gaplistsPane, DisplayGaplistsLayout.GAPLISTS_PANE);
		oldSavedGaplistPane = gaplistsPane;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>done</b></em></p>
	 * <p style="margin-left: 20px">{@code protected void done()}</p>
	 * <p style="margin-left: 20px">Will be called, after {@link #doInBackground()} has 
	 * finished. Will call {@link DisplayGaplistsWindow#doneSavedListsUpdate(Song[], JLabel, 
	 * JFrame, JScrollPane)}.</p>
	 * @since 1.0
	 */
	@Override
	protected void done() {
		util.IO.println(this, "Updated saved Gaplists");
		displayGaplistsWindow.doneSavedListsUpdate(frame, oldSavedGaplistPane);
	}
}
