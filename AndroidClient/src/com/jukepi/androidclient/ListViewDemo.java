package com.jukepi.androidclient;

import java.util.ArrayList;

import client.serverconnection.Song;
import android.app.ListActivity;
import android.widget.ArrayAdapter;

public class ListViewDemo extends ListActivity{

    ArrayList<String> listItems=new ArrayList<String>();
    
    ArrayAdapter<String> adapter;
    
    public ListViewDemo() {
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
		
		setListAdapter(adapter);
    }
    
    public void changeList(Song[] list) {
    	listItems.clear();
    	for (int i = 0; i < list.length; i++) {
    		listItems.add(list[i].getName());
    	}
    	adapter.notifyDataSetChanged();
    }
}
