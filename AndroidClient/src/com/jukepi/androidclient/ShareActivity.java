package com.jukepi.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ShareActivity extends Activity {
	
	EditText pastedURL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		String url = this.getIntent().getStringExtra(Intent.EXTRA_TEXT);
		pastedURL = (EditText) this.findViewById(R.id.efPastedURL);
		pastedURL.setText(url);
	}
	
	public void onAddButtonClicked(View v){
		if (ServerConnectionContainer.getServerConnection().isConnected()){
			if (ServerConnectionContainer.getServerConnection().addToList(pastedURL.getText().toString(), true, true))
				Toast.makeText(this, "Added the Song to the Wishlist.", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this, "Failed to add the Song", Toast.LENGTH_LONG).show();			
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}else{
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share, menu);
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
}
