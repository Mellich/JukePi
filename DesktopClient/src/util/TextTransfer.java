package util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * The Class, that will handle the transfer of the Text to and from the Clipboard.
 * @author Haeldeus.
 * @version 1.0
 */
public class TextTransfer implements ClipboardOwner{

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		//Nothing to do here.
	}
	
	/**
	  * Places a String on the Clipboard, and makes this class the
	  * owner of the Clipboard's contents.
	  * @param aString	The String, that will be saved on the Clipboard.
	  */
	  public void setClipboardContents(String text){
	    StringSelection stringSelection = new StringSelection(text);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(stringSelection, this);
	  }

	  /**
	  * Returns the String residing on the clipboard.
	  *
	  * @return Any text found on the Clipboard. If none found, returns an
	  * empty String.
	  * @since 1.0
	  */
	  public String getClipboardContents() {
	    String result = "";
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    //odd: the Object param of getContents is not currently used
	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
	    if (hasTransferableText) {
	      try {
	        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
	      }
	      catch (UnsupportedFlavorException | IOException ex){
	        System.out.println(ex);
	        ex.printStackTrace();
	      }
	    }
	    return result;
	  }
}
