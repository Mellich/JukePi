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
 
/**
 * A customized ArrayAdapter for the Wishlist.
 * @author Haeldeus
 * @version 1.0
 * @see ArrayAdapter
 */
public class CustomList extends ArrayAdapter<Song>{
 
	/**
	 * The Activity, the Adapter is working on.
	 */
	private Activity context;
	
	/**
	 * The Wishlist as an ArrayList of Songs.
	 * @see ArrayList
	 * @see Song
	 */
	private ArrayList<Song> songs;
	
	/**
	 * The boolean Value, that determines, if the Wishlist is empty.
	 */
	private boolean isEmpty;
	
	/**
	 * The Constructor for this Adapter.
	 * @param context	The Activity, this Adapter is working on.
	 * @param songs	The Wishlist as an {@link ArrayList} of {@link Song}s
	 * @since 1.0
	 */
	public CustomList(Activity context, ArrayList<Song> songs) {
		super(context, R.layout.list_single, songs);
		this.context = context;
		this.songs = songs;
		this.isEmpty = false;
	}
	
	/**
	 * Sets {@link #isEmpty} to the given boolean Value.
	 * @param isEmpty	The boolean Value, whether the Wishlist is empty or not.
	 * @since 1.0
	 */
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