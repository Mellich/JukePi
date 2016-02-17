package util;

import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * <p>A Class, that creates a Multiline-Label, but without using HTML.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul> 
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #JMultilineLabel(String)}:
 * 				The Constructor for a MultilineLabel. This will create a Non-editable {@link 
 * 				JTextArea} with LineWrap enabled and the LineWraps will always be after a Word 
 * 				and won't interrupt single words. </li>
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
 * 				The VersionUID of this serialized Object.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * @author Haeldeus
 * @version 1.0
 */
public class JMultilineLabel extends JTextArea{
	
	/**
	 * <p style="margin-left: 10px"><em><b>serialVersionUID</b></em></p>
	 * <p style="margin-left: 20px">{@code private static final long serialVersionUID}</p>
	 * <p style="margin-left: 20px">The VersionUID of this serialized Object.</p>
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * <p style="margin-left: 10px"><em><b>JMultilineLabel</b></em></p>
     * <p style="margin-left: 20px">{@code public JMultilineLabe(String)}</p>
     * <p style="margin-left: 20px">Creates a MultilineLabel with the given {@code text} as 
     * Text.</p>
     * @param text	The Text, that will be displayed by the Label.
     * @since 1.0
     */
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