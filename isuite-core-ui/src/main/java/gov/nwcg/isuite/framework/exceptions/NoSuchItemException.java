/**
 * 
 */
package gov.nwcg.isuite.framework.exceptions;

import java.util.Collection;

/**
 * @author doug
 *
 */
public class NoSuchItemException extends PersistenceException {

	public NoSuchItemException() {
		super();
	}

	public NoSuchItemException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchItemException(String message) {
		super(message);
	}

	public NoSuchItemException(Throwable cause) {
		super(cause);
	}
   
	/**
    * @param message
    * @param errors
    */
   public NoSuchItemException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * @param message
    * @param cause
    * @param errors
    */
   public NoSuchItemException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
   }

   private static final long serialVersionUID = 5533561961123254172L; // generated

}
