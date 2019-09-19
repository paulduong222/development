/**
 * 
 */
package gov.nwcg.isuite.framework.exceptions;

import java.util.Collection;

/**
 * @author doug
 *
 */
public class DuplicateItemException extends PersistenceException {

	private static final long serialVersionUID = -4886460785805225636L; // generated

	public DuplicateItemException() {
		super();
	}

	public DuplicateItemException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateItemException(String message) {
		super(message);
	}

	public DuplicateItemException(Throwable cause) {
		super(cause);
	}

   /**
    * Constructor.
    * 
    * @param message
    * @param errors
    */
   public DuplicateItemException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * Constructor.
    * 
    * @param message
    * @param cause
    * @param errors
    */
   public DuplicateItemException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
   }

}
