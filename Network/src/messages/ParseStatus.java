package messages;

/**
 * The ParseStatus for Songs. May be {@code PARSED}, {@code PARSING}, {@code NOT_PARSED} or 
 * {@code ERROR}.
 * @author Mellich
 * @version 1.0
 */
public enum ParseStatus {
	/**
	 * The Parsing for the Song with this Status is completed and without errors.
	 */
	PARSED,
	
	/**
	 * The Song with this Status is parsed at the Moment.
	 */
	PARSING,
	
	/**
	 * A Song is not (yet) parsed.
	 */
	NOT_PARSED,
	
	/**
	 * An Error occured while parsing this Song.
	 */
	ERROR
}
