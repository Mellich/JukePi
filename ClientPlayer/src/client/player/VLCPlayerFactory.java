package client.player;

import client.PlayerStarter;

public class VLCPlayerFactory implements PlayerFactory {

	@Override
	public Player newInstance(PlayerStarter parent) {
		return new VLCPlayer(parent);
	}

}
