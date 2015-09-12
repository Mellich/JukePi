package com.jukepi.androidclient;

import java.util.ArrayList;

import com.jukepi.androidclient.asynctasks.DisconnectAsync;

import client.listener.DefaultNotificationListener;
import client.serverconnection.Song;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The Activity, that is the Main-Screen for the User, where almost every important Information 
 * is shown.
 * @author Haeldeus
 * @version 1.0
 */
public class MainActivity extends Activity implements DefaultNotificationListener {

	/**
	 * The Wishlist as an ArrayList of Songs.
	 * @see ArrayList
	 * @see Song
	 */
	private ArrayList<Song> list;
	
  //  private ArrayList<String> listItems=new ArrayList<String>();
    
	/**
	 * The ListView, that involves the Wishlist.
	 * @see ListView
	 */
    private ListView view;
    
    /**
     * The TextView, that contains the Name of the current Track.
     * @see TextView
     */
    private TextView currentTrack;
    
    /**
     * The Adapter for the Wishlist.
     * @see CustomList
     */
    private CustomList adapter;
    
    /**
     * A boolean value, that determines, if the BackButton was pressed.
     */
    private boolean backPressed;
    
  //  private String title;
    
  //  private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("JukePi");
		backPressed = false;
		GlobalAccess.con.addDefaultNotificationListener(this);
		list = new ArrayList<Song>();
		
		for (Song s : GlobalAccess.con.getWishList())
			list.add(s);
		
		adapter = new CustomList(MainActivity.this, list);
        view=(ListView)findViewById(android.R.id.list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(MainActivity.this, "You Clicked at 3", Toast.LENGTH_SHORT).show();
 
                    }
                });
        onNextTrackNotify(GlobalAccess.con.getCurrentTrackTitle(), "", false);
        onWishListUpdatedNotify(GlobalAccess.con.getWishList());
		
	/*	listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
		onWishListUpdatedNotify(GlobalAccess.con.getWishList());
		
		view = (ListView)findViewById(android.R.id.list);
		view.setAdapter(listAdapter);
		view.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			    Toast.makeText(getApplicationContext(),
			      "Click ListItem Number " + position, Toast.LENGTH_LONG)
			      .show();
			  }
			}); 
		*/
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
		else if (id == R.id.add_settings) {
			changeToAdd();
		}
		else if (id == R.id.removeVote_settings) {
			if (GlobalAccess.con.removeVote())
				Toast.makeText(MainActivity.this, "Removed Vote", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(MainActivity.this, "Failed to remove Vote", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		new SetWishlist(songs, adapter).execute();
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
/*		this.title = title;
		this.url = url;
		
		TextView view = (TextView)this.findViewById(R.id.playingTrack);
	//	new SetNowPlaying(view, title).execute();
		view.setText(title);
		
		TextView view2 = (TextView)this.findViewById(R.id.nextTrackName);
		String nextName;
		if (list.size() == 0)
			if (GlobalAccess.con.getGapList().length == 0)
				nextName = getString(R.string.nothing);
			else
				nextName = GlobalAccess.con.getGapList()[0].getName();
		else
			nextName = list.get(0).getName();
		
	//	new SetNextTrack(view2, nextName).execute();
		view2.setText(nextName); */
		
	//	this.title = title;
		final String lTitle = title;
	//	this.url = url;
		currentTrack = (TextView)findViewById(R.id.playingTrack);
	//	new SetNowPlaying(textView, title).execute();
		
		currentTrack.post(new Runnable() {
		    public void run() {
		        currentTrack.setText(lTitle);
		    } 
		});
	}

	@Override
	public void onDisconnect() {
		if (!backPressed)
			new DisconnectAsync(this).execute();
	}
	
	/**
	 * The AsyncTask, that will perform Changes to the Wishlist.
	 * @author Haeldeus
	 * @version 1.0
	 * @see AsyncTask
	 */
	private class SetWishlist extends AsyncTask<Void, Void, Void> {
		Song[] songs;
		CustomList adapter;
		
		public SetWishlist(Song[] songs, CustomList adapter) {
			this.songs = songs;
			this.adapter = adapter;
		}

		@Override
		protected Void doInBackground(Void... params) {
		/*	listItems.clear();
			
			String[] names = new String[songs.length];
			Integer[] votes = new Integer[songs.length];
			for (int i = 0; i < songs.length; i++) {
				listItems.add(songs[i].getName());
				names[i] = songs[i].getName();
				votes[i] = songs[i].getVotes();
			}
			*/
			
			list.clear();
			adapter.setEmpty(false);
			for (Song s : songs)
				list.add(s);
			
			if (list.size() == 0)
				adapter.setEmpty(true);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * The Method, that will change the current visible Activity to the Add-Activity.
	 * @since 1.0
	 */
	public void changeToAdd() {
		Intent intent = new Intent(this, AddActivity.class);
		this.startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		backPressed = true;
		GlobalAccess.con.close();
		Intent intent = new Intent(this, LoginActivity.class);
		this.startActivity(intent);
	}
	
/*	private class SetNowPlaying extends AsyncTask<Void, Void, Void> {

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
	
/*	private class SetNextTrack extends AsyncTask<Void, Void, Void> {
		
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
*/
}
