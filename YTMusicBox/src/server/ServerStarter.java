package server;

import java.io.IOException;
import java.util.List;

import utilities.IO;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		IdelViewer viewer = new IdelViewer(primaryStage);
		List<String> args = this.getParameters().getRaw();
		YTJBServer s;
		if (args.size() == 1){
			int port = Integer.parseInt(args.get(0));
			s = new YTJBServer(port,viewer);
		} else {
			s = new YTJBServer(YTJBServer.PORT,viewer);
		}
		s.start();
		this.clearScreen();
		viewer.showLogo(true);
	}
	
	private void clearScreen(){
		try {
			new ProcessBuilder("setterm","-cursor","off").start();
			new ProcessBuilder("clear").start();
		} catch (IOException e) {
			IO.printlnDebug(this, "Could not clear the screen!");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
