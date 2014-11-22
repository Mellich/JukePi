package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import connection.Collector;
import javax.swing.JLabel;
import javax.swing.JButton;

import javax.swing.JTextField;

public class OpenButtonListener implements ActionListener{

	private JFrame frame;
	private EditTrackListener listener;
	private Collector c;
	private JTextField textField;
	
	public OpenButtonListener(JFrame frame, EditTrackListener listener, Collector c) {
		this.frame = frame;
		this.listener = listener;
		this.c = c;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//TODO Delete
	//	JFrame frame = new JFrame();
		//Till here
		frame.setSize(new Dimension(575, 450));
		frame.setTitle("Saved Gaplists");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setContentPane(fillContent());
		frame.setVisible(true);
		frame.setResizable(false);
	}

	
	private JPanel fillContent() {
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		
		DefaultListModel<String> savedGaplists = new DefaultListModel<String>();
		c.addGaplistCollectionModel(savedGaplists);
		DefaultListModel<String> contents = new DefaultListModel<String>();
		c.addContentModel(contents);
		c.fillGaplistModel();
		
		JList<String> gaplistList = new JList<String>(savedGaplists);
		JList<String> contentList = new JList<String>(contents);
		
		JScrollPane gaplistScrollPane = new JScrollPane(gaplistList);
		gaplistScrollPane.setBounds(25, 40, 250, 250);	
		JScrollPane contentScrollPane = new JScrollPane(contentList);
		contentScrollPane.setBounds(300, 40, 250, 250);
		
		contentPane.add(gaplistScrollPane);
		contentPane.add(contentScrollPane);
		
		JLabel lblGaplists = new JLabel("Saved Gaplists");
		lblGaplists.setBounds(25, 15, 250, 14);
		lblGaplists.setHorizontalAlignment(JLabel.CENTER);
		lblGaplists.setVerticalAlignment(JLabel.CENTER);
		contentPane.add(lblGaplists);
		
		JLabel lblContent = new JLabel("Content");
		lblContent.setBounds(300, 15, 250, 14);
		lblContent.setHorizontalAlignment(JLabel.CENTER);
		lblContent.setVerticalAlignment(JLabel.CENTER);
		c.addContentLabel(lblContent);
		contentPane.add(lblContent);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(25, 301, 76, 23);
		contentPane.add(btnLoad);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(461, 388, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(25, 388, 89, 23);
		contentPane.add(btnCreate);
		
		textField = new JTextField();
		textField.setBounds(124, 389, 188, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnShow = new JButton("Show");
		btnShow.setBounds(110, 301, 76, 23);
		contentPane.add(btnShow);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(195, 301, 80, 23);
		contentPane.add(btnRemove);
		
		JLabel lblFail = new JLabel("");
		lblFail.setBounds(25, 355, 525, 14);
		contentPane.add(lblFail);
		
		btnCreate.addActionListener(new CreateButtonListener(c, textField, lblFail));
		btnRemove.addActionListener(new RemoveButtonListener(c, gaplistList, lblFail));
		btnShow.addActionListener(new ShowButtonListener(c, gaplistList, lblFail));
		btnBack.addActionListener(new BackButtonListener(listener));
		btnLoad.addActionListener(new LoadButtonListener(c, gaplistList, lblFail));
		
		return contentPane;
	}
}
