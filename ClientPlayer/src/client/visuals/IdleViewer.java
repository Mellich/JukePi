package client.visuals;


//import java.sql.Timestamp;

import client.serverconnection.ServerConnection;
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
	private ServerConnection serverConnection;
	private ImageView imgView;
	private Text ipAddress;
	private Text info;
	private Text currentGapList;
	private Text currentTrack;
	private Text playbackStatus;
	//private TextArea debugInfo;
	private Group root;
	
	private static final String FONTFAMILY = "Tahoma";
	
	public IdleViewer(Stage stage, ServerConnection serverConnection) {
		this.stage = stage;
		this.serverConnection = serverConnection;
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
		imgView = new ImageView(new Image(this.getClass().getResourceAsStream("logo.jpg")));
		ipAddress = new Text(500,700,"Suche Server...");
		info = new Text(500,750,"");
		info.setFont(new Font(FONTFAMILY,30));
		info.setFill(Color.WHITE);
		Text version = new Text(5,25,"Build version 0.8.3 (Without debug - untested)");
		version.setFont(new Font(FONTFAMILY,20));
		version.setFill(Color.WHITE);
		currentGapList = new Text(500,800,"");
		currentGapList.setFont(new Font(FONTFAMILY,30));
		currentGapList.setFill(Color.WHITE);
		currentTrack = new Text(500,850,"");
		currentTrack.setFont(new Font(FONTFAMILY,30));
		currentTrack.setFill(Color.WHITE);
		playbackStatus = new Text(500,900,"");
		playbackStatus.setFont(new Font(FONTFAMILY,30));
		playbackStatus.setFill(Color.WHITE);
		/*debugInfo = new TextArea();
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
		//root.getChildren().add(debugInfo);
		root.getChildren().add(currentGapList);
		root.getChildren().add(currentTrack);
		root.getChildren().add(playbackStatus);
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
	
	private void gaplistReadOutStatus(int currentCount,int maxCount){
		if (currentCount < maxCount){
			info.setText("Gaplist wird geladen: ("+currentCount+"/"+maxCount+")");
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
	public void updateInfos() {
		serverConnection.getCurrentGapListName((String[] s) -> {Platform.runLater(() -> this.currentGapList.setText("Geöffnete Gaplist: "+s[0]));});
		serverConnection.getLoadGapListStatus((String[] s) -> {Platform.runLater(() -> gaplistReadOutStatus(Integer.parseInt(s[0]),Integer.parseInt(s[1])));});
		Platform.runLater(() -> editConnectionDetails(serverConnection.getIPAddress(),serverConnection.getPort()));
		serverConnection.getCurrentPlaybackStatus((String[] s) -> {if (Boolean.parseBoolean(s[0])){ Platform.runLater(() -> this.playbackStatus.setText("Wiedergabe läuft..."));}
																	else Platform.runLater(() -> this.playbackStatus.setText("Wiedergabe angehalten/pausiert")); });
		serverConnection.getCurrentTrackTitle((String[] s) -> Platform.runLater(() -> this.currentTrack.setText(s[0])));
	}

	@Override
	public void resetView() {
		Platform.runLater(() ->{
		ipAddress.setText("Suche Server...");
		info.setText("");
		currentGapList.setText("");
		currentTrack.setText("");
		playbackStatus.setText("");
		});
		this.showDebugInfo("View was reseted!");
	}

	@Override
	public void showDebugInfo(String info) {
		/*Timestamp t = new Timestamp(System.currentTimeMillis());
		Platform.runLater(() -> {debugInfo.appendText(t.toString()+": "+info+"\n");
								 debugInfo.setScrollTop(Double.MAX_VALUE);
							});*/
	}

}
