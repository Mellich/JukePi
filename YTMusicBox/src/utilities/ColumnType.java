package utilities;

/**
 * The column types of the init file. 
 * To add a new column, add a new value to this class and add a constructor with a id to the new value.
 * The id represents the index of the default value in the defalutValues array. Add a default value there in the right position.
 * 
 * @author mellich
 *
 */
public enum ColumnType{
	PLAYERPW("player"),
	PLAYBACKPW("playback"), 
	DEBUGPW("debug"), 
	ADMINPW("gaplist"),
	STARTUPGAPLIST("gaplist.jb"), 
	AUTOPLAY(""+true) ,
	MAXADMINCOUNT(""+Long.MAX_VALUE),
	MAXPLAYERCOUNT("1"),
	STARTPLAYER(""+true),
	AUTOUPDATE(""+true),
	PORT("22222");
	
	private String defValue;
	
	private ColumnType(String defValue){
		this.defValue = defValue;
	}

	public String getDefaultValue() {
		return defValue;
	}
}
