package com.jukepi.androidclient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.jukepi.androidclient.asynctasks.DisconnectAsync;

import client.listener.DefaultNotificationListener;
import client.serverconnection.Song;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
    
    /**
     * The URL of the current playing Song.
     */
    private String url;
    
    private boolean firstPressedSave;
    
    @Override
    protected void onResume() {
    	super.onResume();
		list = new ArrayList<Song>();
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
		String url = this.getIntent().getStringExtra(Intent.EXTRA_TEXT);
		//TODO: hier neuen share intent einfügen
    	if(ServerConnectionContainer.getServerConnection().isConnected()){
    		refreshInput();
    	}else{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			Toast.makeText(this, "Connection lost", Toast.LENGTH_SHORT).show();
		}
    }
    
    private void refreshInput(){
		
		for (Song s : ServerConnectionContainer.getServerConnection().getWishList())
			list.add(s);
        String title;
        if (ServerConnectionContainer.getServerConnection().getCurrentSong() != null) {
        	title = ServerConnectionContainer.getServerConnection().getCurrentSong().getName();
        	url = ServerConnectionContainer.getServerConnection().getCurrentSong().getURL();
        }
        else {
        	title = this.getString(R.string.nothing);
        	url = this.getString(R.string.nothing);
        }
        
        firstPressedSave = true;
        onNextTrackNotify(title, url, false);
        onWishListUpdatedNotify(ServerConnectionContainer.getServerConnection().getWishList());
		
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		backPressed = false;
		ServerConnectionContainer.getServerConnection().addDefaultNotificationListener(this);
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
			if (ServerConnectionContainer.getServerConnection().removeVote())
				Toast.makeText(MainActivity.this, "Removed Vote", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(MainActivity.this, "Failed to remove Vote", Toast.LENGTH_SHORT).show();
		}
		else if (id == R.id.currentURL_settings) {
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText(url, url);
			clipboard.setPrimaryClip(clip);
			
			String FILENAME = "saved_songs";
			String text = "";
			if (ServerConnectionContainer.getServerConnection().getCurrentSong() != null) {
				text = ServerConnectionContainer.getServerConnection().getCurrentSong().getName();
				text = text.concat(System.getProperty("line.separator") + ServerConnectionContainer.getServerConnection().getCurrentSong().getURL());
			}

			FileOutputStream fos;
			try {
				fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write(text.getBytes());
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (id == R.id.SavedSongList_settings) {
			changeToSavedSongs();
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
		this.url = url;
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
	
	/**
	 * The Method, that will change the current visible Activity to the SavedSong-Activity.
	 * @since 1.0
	 */
	public void changeToSavedSongs() {
		Intent intent = new Intent();
		this.startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		backPressed = true;
		ServerConnectionContainer.getServerConnection().close();
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
