package client.player;

import client.PlayerStarter;

public class OMXPlayerFactory implements PlayerFactory {

	@Override
	public Player newInstance(PlayerStarter parent) {
		return new OMXPlayer(parent);
	}

}
