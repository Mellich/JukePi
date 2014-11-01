package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import exampleResponses.TestClass;

public class ShowActionListener implements ActionListener{

	private TestClass tc;
	
	public ShowActionListener(TestClass tc) {
		this.tc = tc;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		JFrame jFrame = new JFrame();
		jFrame.setSize(new Dimension(300, 376));
		jFrame.setTitle("JukePi");
		jFrame.setContentPane(fillContent());
		jFrame.setVisible(true);
	}
	
	public JPanel fillContent() {
		
		JPanel jContentPane = new JPanel();
		jContentPane.setLayout(null);
		
		JTextArea tpList = new JTextArea();
		JScrollPane spScroll = new JScrollPane(tpList);
		spScroll.setBounds(15, 11, 250, 300);
		tpList.setLineWrap(false);
		
		String txt = "";
		for (String i : tc.sr.getGapList()) {
			txt = txt+i+"\n";
		}
		tpList.setText(txt);
		tpList.setEditable(false);
		
		tpList.setCaretPosition(0);
		
		jContentPane.add(spScroll);
		
		return jContentPane;
	}

}
