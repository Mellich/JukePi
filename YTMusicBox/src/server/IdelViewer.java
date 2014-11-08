package server;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IdelViewer {
	
	private Stage stage;
	private ImageView imgView;
	private Text ipAddress;
	
	public IdelViewer(Stage stage) {
		this.stage = stage;
		Group root = new Group();
		Scene scene = new Scene(root,1920,1080,Color.BLACK);
		imgView = new ImageView(new Image(this.getClass().getResourceAsStream("logo.jpg")));
		ipAddress = new Text(50,50,"Lade Server...");
		ipAddress.setFont(new Font(30));
		ipAddress.setFill(Color.WHITE);
		root.getChildren().add(imgView);
		root.getChildren().add(ipAddress);
		stage.setScene(scene);
		stage.show();
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
