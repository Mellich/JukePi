package util;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * <p>The PopupMenu for the TextFields, so they have a Context-Menu.</p>
 * 
 * <h3>Provided Methods:</h3> 
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #PopUpMenu(JTextField)}:
 * 				The Constructor for the Menu. Creates the whole Menu with it's MenuItems and 
 * 				adds the Listener to each MenuItem.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
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
 * 			<li>{@link #serialVersionUID}:
 * 				The VersionUID of this serialized Objext.</li>
 * 
 * 			<li>{@link #copy}:
 * 				The MenuItem to copy the selected Text.</li>
 * 
 * 			<li>{@link #markAll}:
 * 				The MenuItem to mark the whole Text of the TextField.</li>
 * 
 * 			<li>{@link #paste}:
 * 				The MenuItem to Paste the copied Text to the TextField.</li>
 * 
 * 			<li>{@link #textTransfer}:
 * 				The {@link TextTransfer} to handle the Communication between this Object and 
 * 				the Systems Clipboard.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class PopUpMenu extends JPopupMenu {
	
	/**
	 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
	 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}</p>
	 * <p style="margin-left: 20px">The serial Version UID.</p>
	 */
	private static final long serialVersionUID = 4884891507561461361L;
	
	/**
	 * <p style="margin-left: 10px"><em><b>textTransfer</b></em></p>
	 * <p style="margin-left: 20px">{@code private TextTransfer textTransfer}</p>
	 * <p style="margin-left: 20px">The {@link TextTransfer}, that will handle the Transfer 
	 * with the Clipboard.</p>
	 */
	private TextTransfer textTransfer;
    
	/**
	 * <p style="margin-left: 10px"><em><b>copy</b></em></p>
	 * <p style="margin-left: 20px">{@code private JMenuItem copy}</p>
	 * <p style="margin-left: 20px">The MenuItem for the Copy-Action.</p>
	 * @see JMenuItem
	 */
	private JMenuItem copy;
    
	/**
	 * <p style="margin-left: 10px"><em><b>paste</b></em></p>
	 * <p style="margin-left: 20px">{@code private JMenuItem paste}</p>
	 * <p style="margin-left: 20px">The MenuItem for the Paste-Action.</p>
	 * @see JMenuItem
	 */
	private JMenuItem paste;
    
	/**
	 * <p style="margin-left: 10px"><em><b>markAll</b></em></p>
	 * <p style="margin-left: 20px">{@code private JMenuItem markAll}</p>
	 * <p style="margin-left: 20px">The MenuItem for the "Mark All"-Action.</p>
	 * @see JMenuItem
	 */
	private JMenuItem markAll;
	
	/**
	 * <p style="margin-left: 10px"><em><b>PopUpMenu</b></em></p>
	 * <p style="margin-left: 20px">{@code public PopUpMenu(JTextField)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Menu, that will initialize all 
	 * MenuItems and link their Listeners to them.</p>
	 * @param txtLink	The {@link JTextField}, this Menu will be linked to.
	 * @since 1.0
	 */
    public PopUpMenu(JTextField txtLink){
    	textTransfer = new TextTransfer();
        copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke('c'));
        copy.addActionListener((ActionEvent ae) -> {
        		textTransfer.setClipboardContents(txtLink.getSelectedText());
        	});
        add(copy);
        
        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke('v'));
        paste.addActionListener((ActionEvent ae) -> {	
        		if (txtLink.getText().equals(""))
        			txtLink.setText(textTransfer.getClipboardContents());
        		else {
        			String txt = txtLink.getText().substring(0,txtLink.getSelectionStart());
        			txt = txt.concat(textTransfer.getClipboardContents());
        			txt = txt.concat(txtLink.getText().substring(txtLink.getSelectionEnd(), 
        					txtLink.getText().length()));
        			txtLink.setText(txt);
        		}
        	});
        add(paste);
        
        addSeparator();
        
        markAll = new JMenuItem("Mark All");
        markAll.setAccelerator(KeyStroke.getKeyStroke('m'));
        markAll.addActionListener((ActionEvent ae) -> {txtLink.setSelectionStart(0);txtLink.setSelectionEnd(txtLink.getText().length());});
        add(markAll);
    }
}