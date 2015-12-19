package clientplayer.visuals;

import clientplayer.Status;

public interface Visualizer {
	public void showIdleScreen(boolean show);
	public void showTrackInfo();
	public void updateInfos(Status newStatus);
	public void resetView();
	public void showDebugInfo(String info);
}
