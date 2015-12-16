package util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class OptionsTableClickListener extends MouseAdapter{

	private JTable table;
	
	public OptionsTableClickListener(JTable table) {
		this.table = table;
	}
	
	@Override
    public void mousePressed(MouseEvent e){
        table.getSelectedRow();
    }

	@Override
    public void mouseReleased(MouseEvent e){
        
    }
}
