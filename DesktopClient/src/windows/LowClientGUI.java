package windows;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import util.layouts.LowClientLayout;
import messages.ParseStatus;
import client.serverconnection.Song;

/**
 * The raw Implementation for a Desktop-Client without Admin-Permissions.
 * @author Haeldeus
 * @version 1.0
 */
public class LowClientGUI {

	/**
	 * The Frame.
	 */
	private final JFrame frame;
	
	/**
	 * The Wishlist as an Array of Songs (no real Songs, just an Instance of 
	 * {@link client.serverconnection.Song} with random Values).
	 */
	private Song[] wishlist;
	
	/**
	 * Creates the Instance of the Class and builds the Wishlist.
	 * @since 1.0
	 */
	public LowClientGUI() {
		frame = new JFrame();
		wishlist = new Song[10];
		for (int i = 0; i < 10; i++) {
			wishlist[i] = new Song((long) i, "Song " + i, 0, false, ParseStatus.PARSED, "A fake Link");
		}
	}
	
	/**
	 * Constructs the Frame and constructs it.
	 * @since 1.0
	 */
	public void start() {
		frame.setTitle("LowClientGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		constructFrame();
		frame.setSize(frame.getContentPane().getWidth()+15, frame.getContentPane().getHeight()+40);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(404,343));
	}
	
	/**
	 * Constructs the Frame.
	 * @since 1.0
	 */
	private void constructFrame() {
		Container contentPane = new Container();
		contentPane.setLayout(new LowClientLayout());
		contentPane.setSize(new Dimension(400,400));
		
		JLabel lblGaplist = new JLabel("Tracks in the Gaplist:");
		contentPane.add(lblGaplist, LowClientLayout.GAPLIST_LABEL);
		
		JLabel lblCountGaplist = new JLabel("73123");
		contentPane.add(lblCountGaplist, LowClientLayout.COUNT_GAPLIST_LABEL);
		
		JLabel lblWishlist = new JLabel("Tracks in the Wishlist:");
		contentPane.add(lblWishlist, LowClientLayout.WISHLIST_LABEL);
		
		JLabel lblCountWishlist = new JLabel("3131");
		contentPane.add(lblCountWishlist, LowClientLayout.COUNT_WISHLIST_LABEL);
		
		JLabel lblWishlistName = new JLabel("Wishlist:");
		lblWishlistName.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblWishlistName, LowClientLayout.WISHLIST_SHOW_LABEL);
		
		JTextField txtLink = new JTextField("Enter a Link to a Video here");
		contentPane.add(txtLink, LowClientLayout.LINK_TEXT);
		
		JButton btnAdd = new JButton("Add");
		contentPane.add(btnAdd, LowClientLayout.ADD_BUTTON);
		
		JButton btnDisc = new JButton("Disconnect");
		contentPane.add(btnDisc, LowClientLayout.DISCONNECT_BUTTON);
		
		JButton btnVote = new JButton("Vote");
		contentPane.add(btnVote, LowClientLayout.VOTE_BUTTON);
		
		JButton btnRemoveVote = new JButton("Remove Vote");
		contentPane.add(btnRemoveVote, LowClientLayout.REMOVE_BUTTON);
		
		JScrollPane wishlistPane = createWishlistTable();
		contentPane.add(wishlistPane, LowClientLayout.WISHLIST_PANE);
		
		frame.setContentPane(contentPane);
	}
	
	/**
	 * Creates the Table, that displays the Wishlist and the Votes for each Song in it.
	 * @since 1.0
	 */
	private synchronized JScrollPane createWishlistTable() {
		
		String[] columns = {"Song:", "Votes:"};
		
		String[][] data = new String[wishlist.length][2];
		
		for (int i = 0; i < wishlist.length; i++) {
			data[i][0] = wishlist[i].getName();
			data[i][1] = ""+wishlist[i].getVotes();
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
        
        table.getColumnModel().getColumn(0).setMinWidth(210);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
		JScrollPane wishlistPane = new JScrollPane(table);
		
		return wishlistPane;
	}
	
	/**
	 * The Main-Method to build the Frame.
	 * @param args	Just a stub.
	 * @since 1.0
	 */
	public static void main(String[] args) {
		new LowClientGUI().start();
	}
}
