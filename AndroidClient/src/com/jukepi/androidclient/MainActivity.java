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

public class MainActivity extends Activity implements DefaultNotificationListener {

	private Song[] list;
	
    ArrayList<String> listItems=new ArrayList<String>();
    
    ArrayAdapter<String> adapter;
    
    ListView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("JukePi");
		GlobalAccess.con.addDefaultNotificationListener(this);
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
		
		view = (ListView)findViewById(android.R.id.list);
		view.setAdapter(adapter);
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
		
	/*	System.out.println("New Wishlist");
		list = songs;
		
		System.out.println("Size :" + list.length);
		
		listItems.clear();
		
		for (int i = 0; i < list.length; i++) {
			listItems.add(list[i].getName());
		}
		adapter.notifyDataSetChanged(); */
		System.out.println("Size :" + songs.length);
		new SetWishlist(songs, listItems, adapter).execute();
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		System.out.println("Called");
		new DisconnectAsync(this).execute();
	}
	
	private class SetWishlist extends AsyncTask<Void, Void, Void>{
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
}
