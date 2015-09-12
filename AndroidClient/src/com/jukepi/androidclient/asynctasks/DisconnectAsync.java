package com.jukepi.androidclient.asynctasks;

import com.jukepi.androidclient.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * The AsyncTask to disconnect from the Server.
 * @author Haeldeus, Mellich
 * @version 1.0
 * @see AsyncTask
 */
public class DisconnectAsync extends AsyncTask<Void, Void, Void>{

	/**
	 * The current Activity as a Context.
	 */
	private Context context;

	/**
	 * The Constructor for thid Task.
	 * @param context	The current Activity as a Context.
	 * @since 1.0
	 */
	public DisconnectAsync(Context context) {
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Void result) {
		Toast.makeText(context, "Server was shut down", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}
	
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

}
