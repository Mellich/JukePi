package com.jukepi.androidclient;

import java.util.ArrayList;
import client.serverconnection.Song;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
 
public class CustomList extends ArrayAdapter<Song>{
 
	private Activity context;
	private ArrayList<Song> songs;
	private boolean isEmpty;
	
	public CustomList(Activity context, ArrayList<Song> songs) {
		super(context, R.layout.list_single, songs);
		this.context = context;
		this.songs = songs;
		this.isEmpty = false;
	}
	
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" }) //Works this way, dunno what to change.
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		TextView txtName = (TextView) rowView.findViewById(R.id.cell_name);
 
		TextView txtVotes = (TextView) rowView.findViewById(R.id.cell_votes);
		
		Button button = (Button) rowView.findViewById(R.id.cell_button);
		button.setOnClickListener(new ClickListener(position, songs));
	//	button.setVisibility(android.view.View.INVISIBLE);
		
		txtName.setText(songs.get(position).getName());
		txtVotes.setText("" + songs.get(position).getVotes());
		
		if (isEmpty) {
			txtName.setText("NO TRACKS YET");
		}
		
		return rowView;
	}
}