package com.jukepi.androidclient;

import java.util.ArrayList;

import client.serverconnection.Song;
import android.R.color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * The Listener for the Items in the List.
 * @author Haeldeus
 * @version 1.0
 */
public class ClickListener implements OnClickListener{

	/**
	 * The position of the Item in the List.
	 */
	private int pos;
	
	/**
	 * The Wishlist as an ArrayList of Songs.
	 * @see ArrayList
	 */
	private ArrayList<Song> songs;
	
	/**
	 * The Constructor for this Listener.
	 * @param pos	The Position of the Item.
	 * @param songs	The Wishlist as an {@link ArrayList} of Songs.
	 * @since 1.0
	 */
	public ClickListener(int pos, ArrayList<Song> songs) {
		this.pos = pos;
		this.songs = songs;
	}

	@Override
	public void onClick(View v) {
		ServerConnectionContainer.getServerConnection().removeVote();
		ServerConnectionContainer.getServerConnection().voteSong(songs.get(pos));
		Toast.makeText(v.getContext(),
			      "Voted for Track Number: " + pos, Toast.LENGTH_LONG)
			      .show();
	}

}
