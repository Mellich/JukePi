package util.tasks;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.JTableHeader;

import util.layouts.DisplayGaplistsLayout;
import windows.DisplayGaplistsWindow;
import windows.MainWindow;

/**
 * The {@link SwingWorker}, that will fill the Table of Saved Gaplists.
 * @author Haeldeus
 * @version 1.0
 */
public class SetSavedGaplistsTask extends SwingWorker<Void, String[]>{

	/**
	 * The {@link JFrame}, that displays the {@link MainWindow}.
	 */
	private JFrame frame;
	
	/**
	 * The Gaplists saved on the Server as an Array of Strings.
	 */
	private String[] gaplists;
	
	/**
	 * The {@link JScrollPane}, that displays the saved Gaplists as a table.
	 */
	private JScrollPane oldSavedGaplistPane;
	
	/**
	 * The {@link DisplayGaplistsWindow}, that called this Worker.
	 */
	private DisplayGaplistsWindow dgw;
	
	/**
	 * The Constructor for this Worker.
	 * @param frame	The {@link JFrame}, that displays the {@link MainWindow}.
	 * @param gaplists	The Gaplists saved on the Server as an Array of Strings.
	 * @param gaplistsPane	The {@link JScrollPane}, that displays the saved Gaplists as a 
	 * table.
	 * @param dgw	The {@link MainWindow}, that called this Worker.
	 * @since 1.0
	 */
	public SetSavedGaplistsTask(JFrame frame, String[] gaplists, JScrollPane gaplistsPane, DisplayGaplistsWindow dgw) {
		this.frame = frame;
		this.gaplists = gaplists;
		this.oldSavedGaplistPane = gaplistsPane;
		this.dgw = dgw;
	}
	
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
			 * The serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The Tooltip of the column.
			 */
			private String [] columnToolTips = {"The Name of the Gaplist"};

			/**
			 * Returns the ToolTip for the Cell at the Position of the Cursor.
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
			 * Returns, if the Cell at the Row and column is editable.
			 * @param row	The row index of the Cell.
			 * @param column The column index of the Cell.
			 * @return	false as default value, since these Cells shouldn't be edited.
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
					 * Returns the ToolTip for the column at the Cursor's Position
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
	
	@Override
	protected void done() {
		util.IO.println(this, "Updated saved Gaplists");
		dgw.doneSavedListsUpdate(frame, oldSavedGaplistPane);
	}
}
