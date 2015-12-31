package clientplayer.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clientplayer.PlayerStarter;
import utilities.IO;

public class VLCPlayerFactory implements PlayerFactory {
	
	private static final String winURI = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
	private static final String linuxURI = "/usr/bin/vlc";
	private static int PORT = 8080;
	
	private List<String> usedCmd;
	
	public VLCPlayerFactory() {
		usedCmd = new ArrayList<String>();
		if (System.getProperty("os.name").equals("Linux")){
			usedCmd.add(linuxURI);
			usedCmd.add("--extraintf=\"rc\"");
			usedCmd.add("--rc-host=\"localhost:"+PORT+"\"");
		}
		else{
			usedCmd.add(winURI);
			usedCmd.add("--extraintf=\"rc\"");
			usedCmd.add("--rc-host=\"localhost:"+PORT+"\"");
		}
	}

	@Override
	public Player newInstance(PlayerStarter parent) {
		return new VLCPlayer(parent,usedCmd,PORT);
	}
	
	public static boolean checkUsability(){
		if (System.getProperty("os.name").equals("Linux")){
			Scanner out = null;
			try {
				IO.printlnDebug(null,"searching for installed VCL player on Linux...");
				Process checkInstall = new ProcessBuilder("which",linuxURI).start();
				out = new Scanner(checkInstall.getInputStream());
				if (out.hasNext()){
					IO.printlnDebug(null,out.nextLine());
					return true;
				}
				else return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}finally{
				if (out != null)
					out.close();
			}
			
		}else{
			IO.printlnDebug(null,"searching for installed VCL player on Windows...");
			File vlcBin = new File(winURI);
			if (vlcBin.exists())
				return true;
			else return false;
		}
	}

}
