package windows;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import util.layouts.DisplayGaplistsLayout;
import util.tasks.SetContentTask;
import util.tasks.SetSavedGaplistsTask;
import client.listener.GapListNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

public class DisplayGaplistsWindow extends Window implements GapListNotificationListener{

	private JFrame frame;
	
	private ServerConnection wrapper;
	
	private MainWindow mw;
	
	private JScrollPane gaplistsPane;
	
	private JScrollPane contentPane;
	
	private JLabel lblFail;
	
	private String[] gaplists;
	
	public DisplayGaplistsWindow(ServerConnection wrapper, MainWindow mw, String[] gaplists) {
		frame = new JFrame();
		lblFail = new JLabel();
		this.wrapper = wrapper;
		this.mw = mw;
		this.gaplists = gaplists;
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
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

	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}

	private void createFrame() {
		frame.setSize(new Dimension(550,300));
		frame.setTitle("Saved Gaplists");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().removeAll();
		
		Container content = frame.getContentPane();
		content.setLayout(new DisplayGaplistsLayout());
		
		content.add(lblFail, DisplayGaplistsLayout.FAIL_LABEL);
		
		JLabel lblSavedGaplists = new JLabel("Saved Gaplists");
		content.add(lblSavedGaplists, DisplayGaplistsLayout.SAVED_GAPLISTS_LABEL);
		
		JLabel lblContent = new JLabel("Content:");
		content.add(lblContent, DisplayGaplistsLayout.CONTENT_LABEL);
		
		JButton btnShow = new JButton("Show");
		content.add(btnShow, DisplayGaplistsLayout.SHOW_BUTTON);
		
		JButton btnLoad = new JButton("Load");
		content.add(btnLoad, DisplayGaplistsLayout.LOAD_BUTTON);
		
		JButton btnDelete = new JButton("Delete");
		content.add(btnDelete, DisplayGaplistsLayout.DELETE_BUTTON);
		
		contentPane = new JScrollPane();
		content.add(contentPane, DisplayGaplistsLayout.CONTENT_PANE);
		
		gaplistsPane = new JScrollPane();
		content.add(gaplistsPane, DisplayGaplistsLayout.GAPLISTS_PANE);
		
		btnShow.addActionListener((ActionEvent ae) -> {
			if (((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0) 
				showGaplist((String)(((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); 
			else 
				showFail("Select a Gaplist first.");
			});

		btnDelete.addActionListener((ActionEvent ae) -> {
			if (((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0) 
				removeGaplist((String)(((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); 
			else 
				showFail("Select a Gaplist first.");
			});
		
		btnLoad.addActionListener((ActionEvent ae) -> {
			if (((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0)
				loadGaplist((String)(((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); 
			else 
				showFail("Select a Gaplist first.");});

		new SetSavedGaplistsTask(frame, gaplists, gaplistsPane, this).execute();
		new SetContentTask(new Song[] {}, contentPane, frame, this).execute();
	}
	
	private void loadGaplist(String name) {
		wrapper.switchToGapList((String[] s) -> {	if (s[0].equals("true"))
														showFail("Loaded Gaplist.");
													else
														showFail("Couldn't load the Gaplist.");
												}, name);
	}
	private void removeGaplist(String name) {
		wrapper.deleteGapList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Removed the Gaplist.");
												else
													showFail("Couldn't remove the Gaplist");
											  }, name);
	}

	private void showGaplist(String name) {
		wrapper.getTitleFromGapList((String[] s) -> {	Song[] songs = new Song[s.length/2];
														for (int i = 0; i < s.length; i = i+2) {
																songs[i/2] = new Song(-1, s[i], 0, false, null, s[i+1]);
														}
														new SetContentTask(songs, contentPane, frame, this).execute();
													}, name);
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		gaplists = gapLists;
		new SetSavedGaplistsTask(frame, gapLists, gaplistsPane, this).execute();
	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		//Nothing to do here
	}

	@Override
	public void onGapListUpdatedNotify(Song[] songs) {
		//Nothing to do here
	}

	public void doneSavedListsUpdate(JFrame frame, JScrollPane gaplistsPane) {
		this.frame = frame;
		this.frame.remove(this.gaplistsPane);
		this.gaplistsPane = gaplistsPane;
		this.frame.revalidate();
	}

	public void doneContentUpdate(JFrame frame, JScrollPane contentPane) {
		this.frame = frame;
		this.frame.remove(this.contentPane);
		this.contentPane = contentPane;
		this.frame.revalidate();
	}
}
