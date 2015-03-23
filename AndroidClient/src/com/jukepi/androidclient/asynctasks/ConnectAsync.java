package com.jukepi.androidclient.asynctasks;

import com.jukepi.androidclient.MainActivity;
import com.jukepi.androidclient.ServerConnectionContainer;

import client.serverconnection.ServerConnection;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class ConnectAsync extends AsyncTask<String, Integer, Boolean> {
	
	private Context context;
	private ProgressDialog progress;

	public ConnectAsync(Context context) {
		progress = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Connecting to server...");
		progress.setCancelable(false);
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... arg0) {
		ServerConnection con = ServerConnectionContainer.getServerConnection();
		if (con.connect(arg0[0], Integer.parseInt(arg0[1])))
			return true;
		return false;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progress.show();
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result){
			Intent intent = new Intent(context, MainActivity.class);
			context.startActivity(intent);
		}
	}

}
