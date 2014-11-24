package client.visuals;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class IdleViewer {
	
	private Stage stage;
	private ImageView imgView;
	private Text ipAddress;
	private Text info;
	private Text currentGapList;
	public Scene infoFullscreen;
	public Scene infoNextTrack;
	
	public IdleViewer(Stage stage) {
		this.stage = stage;
		buildInfoFullscreen();
		buildNextTrack();
		stage.centerOnScreen();
		stage.setFullScreenExitHint("");
		stage.setScene(infoFullscreen);
		stage.setFullScreen(true);
		stage.show();
	}
	
	private void buildInfoFullscreen(){
		Group root = new Group();
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		infoFullscreen = new Scene(root,screenBounds.getWidth(),screenBounds.getHeight(),Color.BLACK);
		imgView = new ImageView(new Image(this.getClass().getResourceAsStream("logo.jpg")));
		ipAddress = new Text(500,700,"Lade Server...");
		info = new Text(500,750,"Gaplist wird ausgelesen... ");
		info.setFont(new Font(30));
		info.setFill(Color.WHITE);
		Text version = new Text(5,25,"Build version 0.6.9 (Client-Server Structure added!)");
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
	
	private void buildNextTrack(){
		VBox vbox = new VBox();
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		vbox.setPadding(new Insets(10));
	    vbox.setSpacing(10);
	    Text now = new Text("Jetzt:");
	    Text next = new Text("2.");
	    Text soon = new Text("3.");
	    now.setFont(new Font(30));
	    next.setFont(new Font(30));
	    soon.setFont(new Font(30));
	    now.setFill(Color.WHITE);
	    next.setFill(Color.WHITE);
	    soon.setFill(Color.WHITE);
	    vbox.getChildren().add(now);
	    vbox.getChildren().add(next);
	    vbox.getChildren().add(soon);
	    infoNextTrack = new Scene(vbox,screenBounds.getWidth(),vbox.getHeight(),Color.BLACK);
	}
	
	public void gaplistStatus(int current,int max){
		Platform.runLater(() -> this.gaplistReadOutStatus(current, max));
	}
	
	public void currentGaplist(String name){
		Platform.runLater(() -> this.currentGapList.setText("Geöffnete Gaplist: "+name)); 
	}
	
	private void gaplistReadOutStatus(int currentCount,int maxCount){
		if (currentCount < maxCount){
			if (currentCount == 0)
				info.setText("Gaplist wird geladen: ("+currentCount+"/"+maxCount+")");
			else info.setText("Wiedergabe pausiert! Gaplist wird geladen: ("+currentCount+"/"+maxCount+")");
		}
		else{
			if (maxCount == 0)
				info.setText("Wiedergabe pausiert! Gaplist ist leer!");
			else info.setText("Wiedergabe pausiert! Gaplist vollständig geladen!");
		}
	}
	
	public void editConnectionDetails(String ip,int port){
		ipAddress.setText("Die Jukebox IP-Addresse: "+ip+" und Port: "+port);
	}
	
	public void showLogo(boolean show){
		if (show){
			stage.setOpacity(1);
			imgView.setOpacity(1);
		}else{
			stage.setOpacity(0);
			imgView.setOpacity(0);
		}
	}

}
