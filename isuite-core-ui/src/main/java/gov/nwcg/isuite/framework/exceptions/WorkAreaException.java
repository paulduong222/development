/**
 * Represents an execption when working with workarea
 */
package gov.nwcg.isuite.framework.exceptions;


/**
 * @author doug
 *
 */
public class WorkAreaException extends IsuiteException {

	/**
    * 
    */
   private static final long serialVersionUID = 2972642198194946710L;

   /**
	 * Constructor 
	 */
	public WorkAreaException() {
		super();
	}

	/**
	 * Message Constructor
	 * @param message
	 */
	public WorkAreaException(String message) {
		super(message);
	}

	/**
	 * Cause Constructor
	 * @param cause
	 */
	public WorkAreaException(Throwable cause) {
		super(cause);
	}

	/**
	 * Complete constructor
	 * @param message
	 * @param cause
	 */
	public WorkAreaException(String message, Throwable cause) {
		super(message, cause);
	}

}
