package messages;

public 	enum Permission{
	/**
	 * adds permissions for debugging purposes
	 */
	DEBUGGING, 
	
	/**
	 * standard permissions
	 */
	STANDARD, 
	
	/**
	 * adds player permissions
	 */
	PLAYER, 
	
	/**
	 * adds permissions for gap list editing (admin permissions)
	 */
	GAPLIST, 
	
	/**
	 * control the play back of the server
	 */
	PLAYBACK;
}
