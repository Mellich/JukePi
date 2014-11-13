package server;

import java.util.List;

import server.visuals.IdleViewer;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		IdleViewer viewer = new IdleViewer(primaryStage);
		List<String> args = this.getParameters().getRaw();
		YTJBServer s;
		if (args.size() == 1){
			int port = Integer.parseInt(args.get(0));
			s = new YTJBServer(port,viewer);
		} else {
			s = new YTJBServer(YTJBServer.PORT,viewer);
		}
		viewer.showLogo(true);
		s.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
