package util;

import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * A Class, that symbols a Multiline-Label, but can be set to Multiline without using HTML.
 * 
 * @author Haeldeus
 * @version 1.0
 */
public class JMultilineLabel extends JTextArea{
    private static final long serialVersionUID = 1L;
    public JMultilineLabel(String text){
        super(text);
        setEditable(false);  
        setCursor(null);  
        setOpaque(false);  
        setFocusable(false);  
        setFont(UIManager.getFont("Label.font"));      
        setWrapStyleWord(true);  
        setLineWrap(true);
    }
} 