package com.jukepi.androidclient;

import client.listener.DefaultNotificationListener;
import client.serverconnection.Song;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class MainActivity extends Activity implements DefaultNotificationListener {

	private Song[] list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("JukePi");
	//	GridView wishList = (GridView) this.findViewById(R.id.wishListView);
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
		ProgressDialog progress = new ProgressDialog(this,ProgressDialog.STYLE_HORIZONTAL);
		progress.setMessage(v.toString());
		progress.setCancelable(true);
		progress.show();
	} 
	
	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}
}
