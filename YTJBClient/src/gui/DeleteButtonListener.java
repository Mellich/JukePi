package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import connection.Collector;

public class DeleteButtonListener implements ActionListener{

	Collector c;
	JList<String> list;
	int buttonNo;
	DefaultListModel<String> gaplistmodel;
	DefaultListModel<String> wishlistmodel;
	DefaultListModel<String> tracklistmodel;
	
	public DeleteButtonListener(JList<String> list, int buttonNo, Collector c, DefaultListModel<String> gaplist, DefaultListModel<String> wishlist, DefaultListModel<String> tracklist) {
		this.list = list;
		this.buttonNo = buttonNo;
		this.c = c;
		this.gaplistmodel = gaplist;
		this.wishlistmodel = wishlist;
		this.tracklistmodel = tracklist;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("...");
		if (buttonNo == 1) {
			if (list.getSelectedIndex() >= 0) {
				c.deleteFromGapList(list.getSelectedIndex(), gaplistmodel, tracklistmodel);
			}
		}
		else if (buttonNo == 2) {
			//DELETEFROMWISHLIST needed
			if (list.getSelectedIndex() >= 0) {
				c.deleteFromWishList(list.getSelectedIndex(), wishlistmodel, tracklistmodel);
			}
		}
		else {
			//have to talk about what happens, if you press delete on the tracklist column
			return;
		}
	}

}
