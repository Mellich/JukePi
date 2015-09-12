package com.jukepi.androidclient;

import com.jukepi.androidclient.asynctasks.ConnectAsync;
import com.jukepi.androidclient.asynctasks.UDPConnectAsync;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;

/**
 * The Activity, that is used to log into the Server.
 * @author Haeldeus, Mellich
 * @version 1.0
 */
public class LoginActivity extends Activity {
	
//	private Button connect;
//	private Button udpConnect;
	
	/**
	 * The EditText Field, that is used to enter the IP-Address.
	 * @see EditText
	 */
	private EditText ipAddress;
	
	/**
	 * The EditText Field, that is used to enter the Port.
	 * @see EditText
	 */
	private EditText port;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	//	connect = (Button) this.findViewById(R.id.button_connect);
	//	udpConnect = (Button) this.findViewById(R.id.button_udpconnect);
		ipAddress = (EditText) this.findViewById(R.id.edit_ip);
		port = (EditText) this.findViewById(R.id.edit_port);	
	}
	
	/**
	 * The Method, that is called, when the Connection to the given Server shall be established.
	 * @param v	The View, this Method was called from.
	 * @since 1.0
	 */
	public void Connect_Click(View v){
		String ip = ipAddress.getText().toString();
		String p = port.getText().toString();
		new ConnectAsync(this).execute(ip,p);
	}
	
	/**
	 * The Method, that is called, when the Connection to a Server in the network shall be 
	 * established.
	 * @param v	The View, this Method was called from.
	 * @since 1.0
	 */
	public void UDPConnectClick(View v){
		new UDPConnectAsync(this).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	
	@Override
	public void onBackPressed() {
		this.finish();
	}
}
