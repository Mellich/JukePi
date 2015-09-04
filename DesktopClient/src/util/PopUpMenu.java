package util;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * The PopupMenu for the TextFields, so they have a Rightclick-Menu.
 * @author Haeldeus
 * @version 1.0
 */
public class PopUpMenu extends JPopupMenu {
	
	/**
	 * The serial Version UID.
	 */
	private static final long serialVersionUID = 4884891507561461361L;
	
	/**
	 * The {@link TextTransfer}, that will handle the Transfer with the Clipboard.
	 */
	private TextTransfer tt;
    
	/**
	 * The MenuItem for the Copy-Action.
	 */
	private JMenuItem copy;
    
	/**
	 * The MenuItem for the Paste-Action.
	 */
	private JMenuItem paste;
    
	/**
	 * The MenuItem for the "Mark All"-Action.
	 */
	private JMenuItem markAll;
	
	/**
	 * The Constructor for the Menu, that will initialize all MenuItems and link their 
	 * Listeners to them.
	 * @param txtLink	The TextField, this Menu will be linked to.
	 * @since 1.0
	 */
    public PopUpMenu(JTextField txtLink){
    	tt = new TextTransfer();
        copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke('c'));
        copy.addActionListener((ActionEvent ae) -> {tt.setClipboardContents(txtLink.getSelectedText());});
        add(copy);
        
        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke('v'));
        paste.addActionListener((ActionEvent ae) -> {	if (txtLink.getText().equals(""))
        													txtLink.setText(tt.getClipboardContents());
        												else {
        														String txt = txtLink.getText().substring(0,txtLink.getSelectionStart());
        														txt = txt.concat(tt.getClipboardContents());
        														txt = txt.concat(txtLink.getText().substring(txtLink.getSelectionEnd(), txtLink.getText().length()));
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