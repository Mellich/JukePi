package com.jukepi.androidclient.asynctasks;

import com.jukepi.androidclient.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class DisconnectAsync extends AsyncTask<Void, Void, Void>{

	private Context context;

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
