package com.jukepi.androidclient.asynctasks;

import com.jukepi.androidclient.MainActivity;
import com.jukepi.androidclient.ServerConnectionContainer;

import client.serverconnection.ServerConnection;
import client.serverconnection.UDPTimeoutException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class UDPConnectAsync extends AsyncTask<Void, Integer, Boolean> {

	private Context context;
	private ProgressDialog progress;
	
	public UDPConnectAsync(Context context) {
		progress = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Searching for server in the network...");
		progress.setCancelable(false);
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ServerConnection con = ServerConnectionContainer.getServerConnection();
		try {
			if (con.connect(con.udpScanning()))
				return true;
			return false;
		} catch (UDPTimeoutException e) {
			return false;
		}
		
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
