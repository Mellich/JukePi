package util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GenericMouseListener extends MouseAdapter{

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed at Content Pane");
	}
}
