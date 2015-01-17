package client.visuals;

public interface Visualizer {
	public void showIdleScreen(boolean show);
	public void showTrackInfo();
	public void updateInfos();
	public void resetView();
	public void showDebugInfo(String info);
}
