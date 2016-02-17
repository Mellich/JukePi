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
 * <p>The Class, that will handle the transfer of the Text to and from the Clipboard.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #getClipboardContents()}:
 * 				Returns the String, that is saved on the System's Clipboard. If no String is 
 * 				saved on the Clipboard, an Error Message will be printed and nothing is 
 * 				returned.</li>
 * 
 * 			<li>{@link #lostOwnership(Clipboard, Transferable)}:
 * 				The overriden Method, that is called, when to Ownership was lost. Nothing will 
 * 				be done here, since it's not needed.</li>
 * 
 * 			<li>{@link #setClipboardContents(String)}:
 * 				Sets the given String as the Content of the System's Clipboard.</li>
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
 * @author Haeldeus.
 * @version 1.0
 */
public class TextTransfer implements ClipboardOwner{
	
	/**
	 * <p style="margin-left: 10px"><em><b>lostOwnership</b></em>
	 * <p style="margin-left: 20px">{@code public void lostOwnership(Clipboard, Transferable)}
	 * </p>
	 * <p style="margin-left: 20px">Will be called, when the Clipboard lost it's owner. Won't 
	 * do anything, since there is nothing to do in that case.</p>
	 * @param arg0	The Clipboard, this Owner is added to. Unused.
	 * @param arg1	The Transferable, that saves the Content of the Clipboard. Unused.
	 * @since 1.0
	 */
	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		//Nothing to do here.
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>setClipboardContents</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setClipboardContents(String)}</p>
	 * <p style="margin-left: 20px">Places a String on the Clipboard, and makes this class the
	 * owner of the Clipboard's contents.</p>
	 * @param text	The String, that will be saved on the Clipboard.
	 * @since 1.0
	 */
	public void setClipboardContents(String text) {
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>getClipboardContents</b></em></p>
	 * <p style="margin-left: 20px">{@code public String getClipboardContents()}</p>
	 * <p style="margin-left: 20px">Returns the String residing on the System's Clipboard.</p>
	 * @return	The String, that is saved on the System's Clipboard. If no String is saved, 
	 * 			nothing will be returned.
	 * @since 1.0
	 */
	public String getClipboardContents() {
		String result = "";
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    //odd: the Object param of getContents is not currently used
	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText = (contents != null) && 
	    		contents.isDataFlavorSupported(DataFlavor.stringFlavor);
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
