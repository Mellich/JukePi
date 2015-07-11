package com.jukepi.androidclient;

import java.util.ArrayList;

import client.serverconnection.Song;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ClickListener implements OnClickListener{

	private int pos;
	
	private ArrayList<Song> songs;
	
	public ClickListener(int pos, ArrayList<Song> songs) {
		this.pos = pos;
		this.songs = songs;
	}

	@Override
	public void onClick(View v) {
		GlobalAccess.con.removeVote();
		GlobalAccess.con.voteSong(songs.get(pos));
		Toast.makeText(v.getContext(),
			      "Voted for Track Number: " + pos, Toast.LENGTH_LONG)
			      .show();
	}

}
