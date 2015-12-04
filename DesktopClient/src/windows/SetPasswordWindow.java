package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import connection.Collector;

public class SetPasswordWindow extends Window{

	private Collector collector;
	
	private JFrame frame;
	
	private JLabel lblFail;
	
	private int port;
	
	public SetPasswordWindow(Collector collector, int port) {
		frame = new JFrame();
		lblFail = new JLabel();
		this.collector = collector;
		this.port = port;
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text);
	}

	@Override
	public void show() {
		createFrame();
		frame.setVisible(true);
	}

	@Override
	public void close() {
		frame.setVisible(false);
	}

	private void createFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300,200));
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(10,10));
		
		Container north = new Container();
		north.add(lblFail);
		
		Container center = new Container();
		center.setLayout(new GridLayout(2,2,10,10));
		JLabel lblAdminPW = new JLabel("Admin Password:");
		JTextField txtAdminPassword = new JTextField();
		JLabel lblPlayerPW = new JLabel("Player Password:");
		JTextField txtPlayerPassword = new JTextField();
		center.add(lblAdminPW);
		center.add(txtAdminPassword);
		center.add(lblPlayerPW);
		center.add(txtPlayerPassword);
		
		Container south = new Container();
		JButton btnSet = new JButton("Set Passwords");
		JButton btnSkip = new JButton("Use Standard");
		south.setLayout(new GridLayout(2,1,10,15));
		south.add(btnSet);
		south.add(btnSkip);
		
		content.add(north, BorderLayout.NORTH);
		content.add(center, BorderLayout.CENTER);
		content.add(south, BorderLayout.SOUTH);
		
		btnSet.addActionListener((ActionEvent ae) -> {
			if (!txtAdminPassword.getText().equals("") && !txtPlayerPassword.getText().equals(""))
				collector.createLocalServerFinal(port, txtAdminPassword.getText(), txtPlayerPassword.getText());
			else if (txtAdminPassword.getText().equals("") && !txtPlayerPassword.getText().equals(""))
				collector.createLocalServerFinal(port, "gaplist", txtPlayerPassword.getText());
			else if (!txtAdminPassword.getText().equals("") && txtPlayerPassword.getText().equals(""))
				collector.createLocalServerFinal(port, txtAdminPassword.getText(), "player");
			else
				collector.createLocalServerFinal(port, "gaplist", "player");
													});
		btnSkip.addActionListener((ActionEvent ae) -> {collector.createLocalServerFinal(port, "gaplist", "player");});
		
		frame.setContentPane(content);
	}
}
