package server;


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

public class IdelViewer {
	
	private Stage stage;
	private ImageView imgView;
	private Text ipAddress;
	private Text info;
	
	public IdelViewer(Stage stage) {
		this.stage = stage;
		Group root = new Group();
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(root,screenBounds.getWidth(),screenBounds.getHeight(),Color.BLACK);
		imgView = new ImageView(new Image(this.getClass().getResourceAsStream("logo.png")));
		ipAddress = new Text(500,700,"Lade Server...");
		info = new Text(500,750,"Gaplist wird ausgelesen... ");
		info.setFont(new Font(30));
		info.setFill(Color.WHITE);
		ipAddress.setFont(new Font(30));
		ipAddress.setFill(Color.WHITE);
		root.getChildren().add(imgView);
		root.getChildren().add(ipAddress);
		root.getChildren().add(info);
		stage.centerOnScreen();
		stage.setFullScreenExitHint("");
		stage.setScene(scene);
		stage.setFullScreen(true);
		//stage.setX(60);
		//stage.setY(30);
		stage.show();
	}
	
	public void gaplistReadOut(boolean isProgressing){
		if (isProgressing){
			info.setText("Wünsch dir was! Gaplist wird eingelesen...");
		}
		else{
			info.setText("Wünsch dir was! Die Gaplist ist leider leer!");
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
