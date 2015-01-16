package client.visuals;


import clientwrapper.ClientWrapper;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class IdleViewer implements Visualizer {
	
	private Stage stage;
	private ClientWrapper serverConnection;
	private ImageView imgView;
	private Text ipAddress;
	private Text info;
	private Text currentGapList;
	private Group root;
	
	public IdleViewer(Stage stage, ClientWrapper serverConnection) {
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
	}
	
	private void buildIdleScreen(){
		imgView = new ImageView(new Image(this.getClass().getResourceAsStream("logo.jpg")));
		ipAddress = new Text(500,700,"Suche Server...");
		info = new Text(500,750,"");
		info.setFont(new Font(30));
		info.setFill(Color.WHITE);
		Text version = new Text(5,25,"Build version 0.7.5");
		version.setFont(new Font(20));
		version.setFill(Color.WHITE);
		currentGapList = new Text(500,800,"");
		currentGapList.setFont(new Font(30));
		currentGapList.setFill(Color.WHITE);
		ipAddress.setFont(new Font(30));
		ipAddress.setFill(Color.WHITE);
		root.getChildren().add(imgView);
		root.getChildren().add(ipAddress);
		root.getChildren().add(info);
		root.getChildren().add(currentGapList);
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
			if (maxCount == 0)
				info.setText("Gaplist ist leer!");
			else info.setText("Gaplist vollständig geladen!");
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
	}

	@Override
	public void resetView() {
		Platform.runLater(() ->{
		ipAddress.setText("Suche Server...");
		info.setText("");
		currentGapList.setText("");
		});
	}

}
