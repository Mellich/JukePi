package clientplayer.visuals;


//import java.sql.Timestamp;

import utilities.IO;
import client.LoadGapListStatus;
import clientplayer.Status;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class IdleViewer implements Visualizer {
	
	private Stage stage;
	private ImageView imgView;
	private ImageView playView;
	private Image pauseImage;
	private Image playImage;
	private Text ipAddress;
	private Text info;
	private Text currentGapList;
	private Text currentTrack;
	//private Text playbackStatus;
	//private TextArea debugInfo;
	private Group root;
	
	private static final String FONTFAMILY = "Tahoma";
	
	public IdleViewer(Stage stage) {
		this.stage = stage;
		root = new Group();
		//buildNextTrack();
		stage.centerOnScreen();
		stage.setFullScreenExitHint("");
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene infoScene = new Scene(root,screenBounds.getWidth(),screenBounds.getHeight(),Color.CORAL);
		buildIdleScreen();
		stage.setScene(infoScene);
		stage.setFullScreen(true);
		stage.show();
		this.showDebugInfo("Started up Player");
	}
	
	private void buildIdleScreen(){
		playImage = new Image(this.getClass().getResourceAsStream("play.png"));
		pauseImage = new Image(this.getClass().getResourceAsStream("pause.png"));
		imgView = new ImageView(new Image(this.getClass().getResourceAsStream("logo.jpg")));
		playView = new ImageView(pauseImage);
		playView.setX(500);
		playView.setY(880);
		playView.setFitHeight(50);
		playView.setFitWidth(50);
		ipAddress = new Text(500,700,"Suche Server...");
		info = new Text(500,750,"");
		info.setFont(new Font(FONTFAMILY,30));
		info.setFill(Color.WHITE);
		Text version = new Text(5,25,"Build version 0.9.2 (Without debug - untested)");
		version.setFont(new Font(FONTFAMILY,20));
		version.setFill(Color.WHITE);
		currentGapList = new Text(500,800,"");
		currentGapList.setFont(new Font(FONTFAMILY,30));
		currentGapList.setFill(Color.WHITE);
		currentTrack = new Text(500,850,"");
		currentTrack.setFont(new Font(FONTFAMILY,30));
		currentTrack.setFill(Color.WHITE);
		/*playbackStatus = new Text(500,900,"");
		playbackStatus.setFont(new Font(FONTFAMILY,30));
		playbackStatus.setFill(Color.WHITE);
		debugInfo = new TextArea();
		debugInfo.setFont(new Font(FONTFAMILY,15));
		debugInfo.setWrapText(true);
		debugInfo.setEditable(false);
		debugInfo.setLayoutX(0);
		debugInfo.setLayoutY(40);
		debugInfo.setMaxSize(1920, 200);
		debugInfo.setMinSize(1920, 200);*/
		ipAddress.setFont(new Font(FONTFAMILY,30));
		ipAddress.setFill(Color.WHITE);
		root.getChildren().add(imgView);
		root.getChildren().add(ipAddress);
		root.getChildren().add(info);
		root.getChildren().add(playView);
		//root.getChildren().add(debugInfo);
		root.getChildren().add(currentGapList);
		root.getChildren().add(currentTrack);
		//root.getChildren().add(playbackStatus);
		root.getChildren().add(version);		
	}
	
	/*private void buildTrackInfoScreen(){
	    Text now = new Text("Jetzt:");
	    Text next = new Text("2.");
	    Text soon = new Text("3.");
	    now.setFont(new Font(30));
	    next.setFont(new Font(30));
	    soon.setFont(new Font(30));
	    now.setFill(Color.WHITE);
	    next.setFill(Color.WHITE);
	    soon.setFill(Color.WHITE);
	    root.getChildren().add(now);
	    root.getChildren().add(next);
	    root.getChildren().add(soon);
	}*/
	
	private void gaplistReadOutStatus(LoadGapListStatus status){
		if (!status.isFullyLoaded()){
			info.setText("Gaplist wird geladen: ("+status.getLoadedTrackCount()+"/"+status.getMaxTrackCount()+")");
		}
		else{
			info.setText("Gaplist vollständig geladen!");
		}
	}
	
	private void editConnectionDetails(String ip,int port){
		ipAddress.setText("Die Jukebox IP-Addresse: "+ip+" und Port: "+port);
	}

	@Override
	public void showIdleScreen(boolean show) {
		Platform.runLater(() -> {
		if (show){
			stage.setOpacity(1);
			imgView.setOpacity(1);
		}else{
			stage.setOpacity(0);
			imgView.setOpacity(0);
		}
		});		
	}

	@Override
	public void showTrackInfo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInfos(Status newStatus) {
		Platform.runLater(() -> {
									this.showDebugInfo("Starting view update...");
									this.currentGapList.setText("Geöffnete Gaplist: "+newStatus.getGaplistTitle());
									gaplistReadOutStatus(newStatus.getLoadStatus());
									editConnectionDetails(newStatus.getServerIP(),newStatus.getServerPort());
									if (newStatus.isPlaying()) this.playView.setImage(playImage);
									else this.playView.setImage(pauseImage);
									String title = newStatus.getCurrentTrackTitle();
									if (title != null && title.length() > 45)
										this.currentTrack.setText("Jetzt spielt: "+title.substring(0, 42)+"...");
									else if (title != null ) this.currentTrack.setText("Jetzt spielt: "+title);
									else this.currentTrack.setText("Jetzt spielt: -");
									this.showDebugInfo("Vie update finished!");
		}						
		);
	}

	@Override
	public void resetView() {
		Platform.runLater(() ->{
		ipAddress.setText("Suche Server...");
		info.setText("");
		currentGapList.setText("");
		currentTrack.setText("");
		playView.setImage(pauseImage);
		//playbackStatus.setText("");
		});
		this.showDebugInfo("View was reseted!");
	}

	@Override
	public void showDebugInfo(String info) {
		/*Timestamp t = new Timestamp(System.currentTimeMillis());
		Platform.runLater(() -> {debugInfo.appendText(t.toString()+": "+info+"\n");
								 debugInfo.setScrollTop(Double.MAX_VALUE);
							});*/
		IO.printlnDebug(this, info);
	}

}
