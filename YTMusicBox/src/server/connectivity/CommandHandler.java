package server.connectivity;

import java.io.BufferedWriter;

import messages.MessageType;
import server.YTJBServer;
import messages.Permission;
import server.connectivity.commands.*;

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

	private boolean gaplistCommand(int prompt, String[] args){	
		if (!parent.checkPermission(Permission.GAPLIST)){
			return false;
		}
		switch (prompt){
		case MessageType.GAPYOUTUBE: new YoutubeCommand(out,prompt,server,false,false,args[1]).handle();
			break;
		case MessageType.GAPLISTSAVETOFILE: new SaveGapListCommand(out,prompt,server).handle();
			break;
		case MessageType.DELETEFROMGAPLIST: new DeleteFromListCommand(out,prompt,server,false,Long.parseLong(args[1])).handle();
			break;
		case MessageType.BEGINNINGYOUTUBE: new YoutubeCommand(out,prompt,server,true,true,args[1]).handle();
			break;
		case MessageType.GAPBEGINNINGYOUTUBE: new YoutubeCommand(out,prompt,server,false,true,args[1]).handle();
			break;
		case MessageType.GAPLISTTRACKUP: new GapListTrackUpCommand(out,prompt,server,Integer.parseInt(args[1])).handle();
			break;
		case MessageType.GAPLISTTRACKDOWN: new GapListTrackDownCommand(out,prompt,server,Integer.parseInt(args[1])).handle();
			break;	
		case MessageType.GETAVAILABLEGAPLISTS: new GetGapListsCommand(out,prompt,server).handle();
			break;
		case MessageType.LOADGAPLIST: new ChangeGapListCommand(out,prompt,server,args[1]).handle();
			break;
		case MessageType.GETCURRENTGAPLISTNAME: new GetCurrentGapListNameCommand(out,prompt,server).handle();
			break;
		case MessageType.GETTITLEFROMGAPLIST: new GetTitleOfGapListCommand(out,prompt,server,args[1],parent.getVersion()).handle();
			break;
		case MessageType.DELETEGAPLIST: new DeleteGapListCommand(out, prompt,server, args[1]).handle();
			break;
		case MessageType.SWITCHGAPLISTNOTIFY: new SwitchNotifyStatusCommand(out,prompt,parent).handle();
		break;
		case MessageType.ADDTOOTHERLIST: new AddSongToOtherListCommand(out,prompt,server,Long.parseLong(args[1])).handle();
		break;
		default: return false;
		}	
		return true;
	}
	
	private boolean debuggingCommand(int prompt, String[] args){	
		if (!parent.checkPermission(Permission.DEBUGGING)){
			return false;
		}
		switch (prompt){
		case MessageType.SWITCHDEBUGNOTIFY: new SwitchNotifyStatusCommand(out,prompt,parent).handle();
		break;
		case MessageType.GETCURRENTCLIENTCOUNT: new GetCurrentClientCountCommand(out,prompt,server).handle();
			break;
		case MessageType.GETCURRENTPLAYERCOUNT: new GetCurrentPlayerCountCommand(out,prompt,server).handle();
			break;
		default: return false;
		}	
		return true;
	}
	
	private boolean playbackCommand(int prompt, String[] args){	
		if (!parent.checkPermission(Permission.PLAYBACK)){
			return false;
		}
		switch (prompt){
		case MessageType.PAUSERESUME: new PauseResumeCommand(out,prompt,server).handle();
			break;
		case MessageType.SKIP: new SkipCommand(out,prompt,server.getScheduler()).handle();
			break;
		case MessageType.SEEKFORWARD: new SeekForwardCommand(out,prompt,server.getScheduler()).handle();
			break;
		case MessageType.SEEKBACKWARD: new SeekBackwardCommand(out,prompt,server.getScheduler()).handle();
			break;
		default: return false;
		}	
		return true;
	}
	
	private boolean playerCommand(int prompt, String[] args){
		if (!parent.checkPermission(Permission.PLAYER)){
			return false;
		}	
		switch (prompt){
		case MessageType.SWITCHSEEKNOTIFY: new SwitchNotifyStatusCommand(out,prompt,parent).handle();
		break;
		case MessageType.GETNEXTVIDEOURL: new GetNextVideoURLCommand(out,prompt, server.getScheduler()).handle();
			break;
		case MessageType.PLAYERFINISHED: if (parent.isPlayer()) new PlayerFinishedCommand(out,prompt, server).handle();
			break;
		case MessageType.SETMEASPLAYER: new SetAsPlayerCommand(out,prompt,server,parent).handle();
		break;
		default:return false;
		}	
		return true;
	}
	
	private boolean standardCommand(int prompt, String[] args){
		if (!parent.checkPermission(Permission.STANDARD)){
			return false;
		}
		switch (prompt){
		case MessageType.SWITCHPAUSERESUMENOTIFY: new SwitchNotifyStatusCommand(out,prompt,parent).handle();
		break;
		case MessageType.SETPERMISSION: new SetPermissionCommand(out,prompt,server,args[1],args[2],this.parent).handle();
		break;
		case MessageType.GETLOADGAPLISTSTATUS: new GetLoadGapListStatusCommand(out, prompt, server).handle();
		break;
		case MessageType.GETGAPLIST: new GetListCommand(out,prompt, server,parent.getMACAddress(),false).handle();
			break;
		case MessageType.GETWISHLIST: new GetListCommand(out,prompt, server,parent.getMACAddress(),true).handle();
			break;
		case MessageType.YOUTUBE:  new YoutubeCommand(out,prompt,server,true,false,args[1]).handle();
			break;
		case MessageType.ISREADY: new CheckIfReadyCommand(out,prompt).handle();
			break;
		case MessageType.GETCURRENTTRACK: new GetCurrentTrackCommand(out,prompt,server.getScheduler(),parent).handle();
			break;
		case MessageType.GETCURRENTPLAYBACKSTATUS: new GetCurrentPlaybackStatusCommand(out,prompt,server.getScheduler()).handle();
			break;
		case MessageType.REGISTERCLIENT: new RegisterCommand(out, prompt,parent,server,args).handle();
			break;
		case MessageType.SWITCHDEFAULTNOTIFY: new SwitchNotifyStatusCommand(out,prompt,parent).handle();
		break;
		case MessageType.VOTEFORSONG: new VoteForSongCommand(out, prompt, server, parent.getMACAddress(), Long.parseLong(args[1])).handle();
			break;
		case MessageType.REMOVEVOTE: new RemoveVoteCommand(out, prompt, server, parent.getMACAddress()).handle();
		break;
		default: return false;
		}
		return true;
	}
	
	private void handleCommand(String[] args){
		int prompt = Integer.parseInt(args[0]);
		if (!standardCommand(prompt, args) &&
				!playerCommand(prompt, args) &&
				!playbackCommand(prompt, args) &&
				!debuggingCommand(prompt, args) &&
				!gaplistCommand(prompt, args)
				)
			new UnknownCommand(out,prompt,args.toString());
	}
	
	@Override
	public void run() {
		super.run();
		handleCommand(message.split(MessageType.SEPERATOR));
	}


}
