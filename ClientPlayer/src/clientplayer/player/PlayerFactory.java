package clientplayer.player;

import clientplayer.PlayerStarter;

public interface PlayerFactory {
	public Player newInstance(PlayerStarter parent);
}
