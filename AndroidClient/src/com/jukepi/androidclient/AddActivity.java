package com.jukepi.androidclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The Activity, where you can add a Track to the Wishlist.
 * @author Haeldeus
 * @version 1.0
 */
public class AddActivity extends Activity{
	
	/**
	 * The {@link EditText}, where you can add the Link of the Track.
	 */
	private EditText link;
//	private Button add;
//	private Button clipboardPaste;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		this.setTitle("Add a Track");
		
		link = (EditText)this.findViewById(R.id.add);
	//	add = (Button)this.findViewById(R.id.addButton);
	//	clipboardPaste = (Button)this.findViewById(R.id.clipboardPaste);
	}

	/**
	 * The Method, that is called, when the Add-Button was clicked.
	 * @param v	The {@link View}, this Method was called from.
	 * @since 1.0
	 */
	public void addClick(View v) {
		if (ServerConnectionContainer.getServerConnection().addToList(link.getText().toString(), true, true))
			Toast.makeText(this, "Added the Song to the Wishlist.", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(this, "Failed to add the Song", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * The Method, that is called, when the Back-Button was clicked.
	 * @param v	The {@link View}, this Method was called from.
	 * @since 1.0
	 */
	public void backClick(View v) {
		this.onBackPressed();
	}
}
