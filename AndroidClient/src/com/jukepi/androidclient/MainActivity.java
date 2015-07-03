package com.jukepi.androidclient;

import java.util.ArrayList;

import com.jukepi.androidclient.asynctasks.DisconnectAsync;

import client.listener.DefaultNotificationListener;
import client.serverconnection.Song;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements DefaultNotificationListener {

	private Song[] list;
	
    private ArrayList<String> listItems=new ArrayList<String>();
    
    private ArrayAdapter<String> listAdapter;
    
    private ListView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("JukePi");
		GlobalAccess.con.addDefaultNotificationListener(this);
		
		listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
		onWishListUpdatedNotify(GlobalAccess.con.getWishList());
		
		view = (ListView)findViewById(android.R.id.list);
		view.setAdapter(listAdapter);
		
		String actual = GlobalAccess.con.getCurrentTrackTitle();
		this.onNextTrackNotify(actual, "", false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showFullName(View v) {
		System.out.println("Called");
		ProgressDialog progress = new ProgressDialog(this,ProgressDialog.STYLE_HORIZONTAL);
		progress.setMessage(v.toString());
		progress.setCancelable(true);
		progress.show();
	} 
	
	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		this.list = songs;
		new SetWishlist(songs, listItems, listAdapter).execute();
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		TextView view = (TextView)this.findViewById(R.id.playingTrack);
	//	new SetNowPlaying(view, title).execute();
		view.setText(title);
		
		TextView view2 = (TextView)this.findViewById(R.id.nextTrackName);
		String nextName;
		if (list.length == 0)
			if (GlobalAccess.con.getGapList().length == 0)
				nextName = getString(R.string.nothing);
			else
				nextName = GlobalAccess.con.getGapList()[0].getName();
		else
			nextName = list[0].getName();
		
	//	new SetNextTrack(view2, nextName).execute();
		view2.setText(nextName);
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		System.out.println("Called");
		new DisconnectAsync(this).execute();
	}
	
	private class SetWishlist extends AsyncTask<Void, Void, Void> {
		Song[] songs;
		ArrayList<String> listItems;
		ArrayAdapter<String> adapter;
		
		public SetWishlist(Song[] songs, ArrayList<String> listItems, ArrayAdapter<String> adapter) {
			this.songs = songs;
			this.listItems = listItems;
			this.adapter = adapter;
		}

		@Override
		protected Void doInBackground(Void... params) {
			listItems.clear();
			for (Song i : songs) {
				listItems.add(i.getName());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();
		}
	}
	
	private class SetNowPlaying extends AsyncTask<Void, Void, Void> {

		private TextView nowPlaying;
		private String name;
		
		public SetNowPlaying(TextView nowPlaying, String name) {
			this.name = name;
			this.nowPlaying = nowPlaying;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			nowPlaying.setText(name);
			return null;
		}
		
	}
	
	private class SetNextTrack extends AsyncTask<Void, Void, Void> {
		
		private TextView nextTrack;
		private String name;
		
		public SetNextTrack(TextView nextTrack, String name) {
			this.name = name;
			this.nextTrack = nextTrack;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			nextTrack.setText(name);
			return null;
		}
		
	}
}
