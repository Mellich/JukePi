package com.jukepi.androidclient.asynctasks;

import com.jukepi.androidclient.GlobalAccess;
import com.jukepi.androidclient.MainActivity;
import com.jukepi.androidclient.ServerConnectionContainer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * The AsyncTask, that will perform the Connect-Action.
 * @author Mellich
 * @version 1.0
 * @see AsyncTask
 */
public class ConnectAsync extends AsyncTask<String, Integer, Boolean> {
	
	/**
	 * The current Activity as a Context.
	 */
	private Context context;
	
	/**
	 * A ProgressDialog, to show a response to the User, when the Task is working.
	 */
	private ProgressDialog progress;

	/**
	 * The Constructor for the Task.
	 * @param context	The current Activity as a Context.
	 * @since 1.0
	 */
	public ConnectAsync(Context context) {
		progress = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Connecting to server...");
		progress.setCancelable(false);
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... arg0) {
		GlobalAccess.con = ServerConnectionContainer.getServerConnection();
		if (GlobalAccess.con.connect(arg0[0], Integer.parseInt(arg0[1])))
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
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		progress.dismiss();
	}

}
