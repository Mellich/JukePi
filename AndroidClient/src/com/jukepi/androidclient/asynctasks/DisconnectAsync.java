package com.jukepi.androidclient.asynctasks;

import com.jukepi.androidclient.LoginActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class DisconnectAsync extends AsyncTask<String, Integer, Boolean>{

	private Context context;
	private ProgressDialog progress;

	public DisconnectAsync(Context context) {
		progress = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Server was shut down. Going Back to Login-Screen!");
		progress.setCancelable(false);
		this.context = context;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		try {Thread.sleep(1000);} catch (Exception e) {}
		return true;
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
			Intent intent = new Intent(context, LoginActivity.class);
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
