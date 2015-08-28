package com.jukepi.androidclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity{
	
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

	public void addClick(View v) {
		if (GlobalAccess.con.addToList(link.getText().toString(), true, true))
			Toast.makeText(this, "Added the Song to the Wishlist.", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(this, "Failed to add the Song", Toast.LENGTH_LONG).show();
	}
	
	public void backClick(View v) {
		this.onBackPressed();
	}
}
