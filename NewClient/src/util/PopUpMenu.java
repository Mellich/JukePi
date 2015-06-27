package util;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class PopUpMenu extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4884891507561461361L;
	TextTransfer tt = new TextTransfer();
    JMenuItem copy;
    JMenuItem paste;
    JMenuItem markAll;
    public PopUpMenu(JTextField txtLink){
        copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke('c'));
        copy.addActionListener((ActionEvent ae) -> {tt.setClipboardContents(txtLink.getSelectedText());});
        add(copy);
        
        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke('v'));
        paste.addActionListener((ActionEvent ae) -> {txtLink.setText(tt.getClipboardContents());});
        add(paste);
        
        addSeparator();
        
        markAll = new JMenuItem("Mark All");
        markAll.setAccelerator(KeyStroke.getKeyStroke('m'));
        markAll.addActionListener((ActionEvent ae) -> {txtLink.setSelectionStart(0);txtLink.setSelectionEnd(txtLink.getText().length());});
        add(markAll);
    }
}