package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import connection.Collector;

public class OptionsWindow extends Window{

	private JFrame frame;
	
	private Collector collector;
	
	public OptionsWindow(Collector collector) {
		frame = new JFrame();
		this.collector = collector;
	}
	
	@Override
	public void showFail(String text) {
		//Nothing to do here
	}

	@Override
	public void show() {
		constructFrame();
		frame.setVisible(true);
	}

	@Override
	public void close() {
		frame.setVisible(false);
	}
	
	private void constructFrame() {
		frame.setTitle("Options");
		frame.setSize(new Dimension(500,500));
		frame.setMinimumSize(new Dimension(500,500));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(0,30));
		
		Container upLeft = new Container();
		upLeft.setLayout(new BorderLayout());
		
		JLabel lblPasswords = new JLabel("Passwords:");
		lblPasswords.setHorizontalAlignment(JLabel.CENTER);
		JLabel lblPWAdmin = new JLabel("Admin:");
		lblPWAdmin.setHorizontalAlignment(JLabel.CENTER);
		JLabel lblPWPlayer = new JLabel("Player");
		lblPWPlayer.setHorizontalAlignment(JLabel.CENTER);
		JTextField txtAdminPW = new JTextField();
		JTextField txtPlayerPW = new JTextField();
		
		Container upLeftCenter = new Container();
		upLeftCenter.setLayout(new GridLayout(2, 2, 1, 1));
		upLeftCenter.add(lblPWAdmin);
		upLeftCenter.add(txtAdminPW);
		upLeftCenter.add(lblPWPlayer);
		upLeftCenter.add(txtPlayerPW);
		
		upLeft.add(lblPasswords, BorderLayout.NORTH);
		upLeft.add(upLeftCenter, BorderLayout.CENTER);
		
		
		Container upRight = new Container();
		upRight.setLayout(new GridLayout(1,2));
		
		JLabel lblLang = new JLabel("Language");
		lblLang.setHorizontalAlignment(JLabel.CENTER);
		JComboBox<String> cbxLang = new JComboBox<String>();
		cbxLang.addItem("English");
		cbxLang.addItem("Deutsch");
		cbxLang.addItem("Klingonisch");
		((JLabel)cbxLang.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		upRight.add(lblLang);
		upRight.add(cbxLang);
		
		Container up = new Container();
		up.setLayout(new GridLayout(1,2,20,1));
		up.add(upLeft);
		up.add(upRight);
		
		content.add(up, BorderLayout.NORTH);
		
		
		
		Container center = new Container();
		center.setLayout(new GridLayout(1,2,20,0));
		
		Container centerLeft = new Container();
		centerLeft.setLayout(new BorderLayout(2,20));
		
		JLabel lblRestrictions = new JLabel("Restrictions");
		lblRestrictions.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane pane = new JScrollPane(new JTextField(""));
		JButton btnRemove = new JButton("Remove Restrictions");
		
		centerLeft.add(lblRestrictions, BorderLayout.NORTH);
		centerLeft.add(pane, BorderLayout.CENTER);
		centerLeft.add(btnRemove, BorderLayout.SOUTH);
		
		Container centerRight = new Container();
		centerRight.setLayout(new BorderLayout(20,20));
		
		JScrollPane pane2 = new JScrollPane(new JTextField(""));
		centerRight.add(pane2, BorderLayout.CENTER);
		
		center.add(centerLeft);
		center.add(centerRight);
		
		content.add(center, BorderLayout.CENTER);
		
		
		Container south = new Container();
		south.setLayout(new GridLayout(1,2,50,0));
		
		JButton btnSave = new JButton("Save");
		JButton btnCancel = new JButton("Cancel");
		
		south.add(btnSave);
		south.add(btnCancel);
		
		content.add(south, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
	}
}
