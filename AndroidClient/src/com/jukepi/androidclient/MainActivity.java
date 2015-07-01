package com.jukepi.androidclient;

import java.util.ArrayList;

import com.jukepi.androidclient.asynctasks.DisconnectAsync;

import client.listener.DefaultNotificationListener;
import client.serverconnection.Song;
import android.app.Activity;
import android.app.ProgressDialog;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("JukePi");
		GlobalAccess.con.addDefaultNotificationListener(this);
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
		
		ListView view = (ListView)findViewById(android.R.id.list);
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
		System.out.println("New Wishlist");
		list = songs;
		
		System.out.println("Size :" + list.length);
		
		for (int i = 0; i < list.length; i++) {
			listItems.add(list[i].getName());
		}
	//	adapter.notifyDataSetChanged();
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
}
