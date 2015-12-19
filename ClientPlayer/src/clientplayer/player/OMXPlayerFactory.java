package clientplayer.player;

import clientplayer.PlayerStarter;

public class OMXPlayerFactory implements PlayerFactory {

	@Override
	public Player newInstance(PlayerStarter parent) {
		return new OMXPlayer(parent);
	}

}
