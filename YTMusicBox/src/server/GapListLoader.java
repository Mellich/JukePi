package server;

import java.util.LinkedList;

import utilities.IO;

public class GapListLoader extends Thread {
	
	
	private String filename;
	private LinkedList<MusicTrack> gapList;
	
	public GapListLoader(String filename, LinkedList<MusicTrack> gapList) {
		this.filename = filename;
		this.gapList = gapList;
	}
	
	@Override
	public void run() {
		super.run();
		IO.loadGapListFromFile(filename, gapList);
	}
}
