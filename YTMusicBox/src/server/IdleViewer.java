package server;


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

public class IdleViewer {
	
	private Stage stage;
	private ImageView imgView;
	private Text ipAddress;
	private Text info;
	private Text currentGapList;
	
	public IdleViewer(Stage stage) {
		this.stage = stage;
		Group root = new Group();
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(root,screenBounds.getWidth(),screenBounds.getHeight(),Color.BLACK);
		imgView = new ImageView(new Image(this.getClass().getResourceAsStream("logo.jpg")));
		ipAddress = new Text(500,700,"Lade Server...");
		info = new Text(500,750,"Gaplist wird ausgelesen... ");
		info.setFont(new Font(30));
		info.setFill(Color.WHITE);
		currentGapList = new Text(500,800,"");
		currentGapList.setFont(new Font(30));
		currentGapList.setFill(Color.WHITE);
		ipAddress.setFont(new Font(30));
		ipAddress.setFill(Color.WHITE);
		root.getChildren().add(imgView);
		root.getChildren().add(ipAddress);
		root.getChildren().add(info);
		root.getChildren().add(currentGapList);
		stage.centerOnScreen();
		stage.setFullScreenExitHint("");
		stage.setScene(scene);
		stage.setFullScreen(true);
		//stage.setX(60);
		//stage.setY(30);
		stage.show();
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
