package server.connectivity;

import java.io.BufferedWriter;






import messages.MessageType;
import server.YTJBServer;
import server.connectivity.commands.*;
import utilities.IO;

/**handles incomming connections
 * 
 * @author Mellich
 *
 */
public class CommandHandler extends Thread {
	
	public static final String FILESAVELOCATION = "files/";
	public static final String TEMPDIRECTORY = "/tmp/";
	
	private YTJBServer server;
	private BufferedWriter out;
	private String message;
	private Connection parent;
	
	public CommandHandler(BufferedWriter out,YTJBServer server,Connection parent,String message) {
		this.out = out;
		this.server = server;
		this.message = message;
		this.parent = parent;
	}
	
	private void handleCommand(String[] args){
		int prompt = Integer.parseInt(args[0]);
		IO.printlnDebug(this, "Parsing input...");
		switch (prompt){
		case MessageType.PAUSERESUME: new PauseResumeCommand(out,server).handle();
			break;
		case MessageType.SKIP: new SkipCommand(out,server.getScheduler()).handle();
			break;
		case MessageType.GAPYOUTUBE: new YoutubeCommand(out,server,false,false,args[1]).handle();
			break;
		case MessageType.GAPLISTSAVETOFILE: new SaveGapListCommand(out,server).handle();
			break;
		case MessageType.DELETEFROMGAPLIST: new DeleteFromListCommand(out,server,false,Integer.parseInt(args[1])).handle();
			break;
		case MessageType.GETGAPLIST: new GetListCommand(out, server,false).handle();
			break;
		case MessageType.GETWISHLIST: new GetListCommand(out, server,true).handle();
			break;
		case MessageType.YOUTUBE:  new YoutubeCommand(out,server,true,false,args[1]).handle();
			break;
		case MessageType.ISREADY: new CheckIfReadyCommand(out).handle();
			break;
		case MessageType.GETCURRENTTRACK: new GetCurrentTrackCommand(out,server.getScheduler()).handle();
			break;
		case MessageType.GETCURRENTPLAYBACKSTATUS: new GetCurrentPlaybackStatusCommand(out,server.getScheduler()).handle();
			break;
		case MessageType.BEGINNINGYOUTUBE: new YoutubeCommand(out,server,true,true,args[1]).handle();
			break;
		case MessageType.GAPBEGINNINGYOUTUBE: new YoutubeCommand(out,server,false,true,args[1]).handle();
			break;
		case MessageType.DECLAREMEASNOTIFY: server.registerNotifiable(parent);;
			break;
		case MessageType.GAPLISTTRACKUP: new GapListTrackUpCommand(out,server,Integer.parseInt(args[1])).handle();
			break;
		case MessageType.GAPLISTTRACKDOWN: new GapListTrackUpCommand(out,server,Integer.parseInt(args[1]) + 1).handle();
			break;	
		case MessageType.GETAVAILABLEGAPLISTS: new GetGapListsCommand(out,server).handle();
			break;
		case MessageType.LOADGAPLIST: new ChangeGapListCommand(out,server,args[1]).handle();
			break;
		case MessageType.GETCURRENTGAPLISTNAME: new GetCurrentGapListNameCommand(out,server).handle();
			break;
		case MessageType.GETTITLEFROMGAPLIST: new GetTitleOfGapListCommand(out,server,args[1]).handle();
			break;
		case MessageType.SETMEASPLAYER: server.registerPlayer(parent);
			break;
		case MessageType.GETNEXTVIDEOURL: new GetNextVideoURLCommand(out, server.getScheduler()).handle();
			break;
		case MessageType.PLAYERFINISHED: new PlayerFinishedCommand(out, server).handle();
			break;
		default: new UnknownCommand(out,""+prompt).handle();
		}		
	}
	
	@Override
	public void run() {
		super.run();
		handleCommand(message.split(MessageType.SEPERATOR));
	}


}
