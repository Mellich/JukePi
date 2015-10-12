package com.jukepi.androidclient.asynctasks;

import com.jukepi.androidclient.MainActivity;
import com.jukepi.androidclient.ServerConnectionContainer;

import client.serverconnection.ServerConnection;
import client.serverconnection.UDPTimeoutException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * The AsyncTask, that performs the Action for the UDP-Connect.
 * @author Mellich, Haeldeus
 * @version 1.0
 * @see AsyncTask
 */
public class UDPConnectAsync extends AsyncTask<Void, Integer, Boolean> {

	/**
	 * The current Activity as a Context.
	 */
	private Context context;
	
	/**
	 * A ProgressDialog to show the User a response when the Task is working.
	 */
	private ProgressDialog progress;
	
	/**
	 * The Constructor for the Task.
	 * @param context	The current Activity as a Context.
	 * @since 1.0
	 */
	public UDPConnectAsync(Context context) {
		progress = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Searching for server in the network...");
		progress.setCancelable(false);
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			if (ServerConnectionContainer.getServerConnection().connect(ServerConnectionContainer.getServerConnection().udpScanning()))
				return true;
			return false;
		} catch (UDPTimeoutException e) {
			Toast.makeText(context, "No Servers were found.", Toast.LENGTH_LONG).show();
			return false;
		} catch (NullPointerException e){
			Toast.makeText(context, "An Error occured.", Toast.LENGTH_LONG).show();
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
			//Intent intent = new Intent(context, MainActivity.class);
			//context.startActivity(intent);
			((Activity) context).finish();
		}

	}

}
