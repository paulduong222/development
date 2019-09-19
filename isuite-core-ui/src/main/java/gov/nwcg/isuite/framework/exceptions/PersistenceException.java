package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.Collection;

/**
 * @author dougAnderson
 */
public class PersistenceException extends IsuiteException {

	/**
    * 
    */
   private static final long serialVersionUID = 2728766908474843758L;

   /**
    * Default Constructor.
    */

	public PersistenceException() {
		super();
	}

	/**
    * Parameterized Constructor.
    * 
    * @param message message associated with exception
    * @param cause cause of exception
    */
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
    * Parameterized Constructor.
    * 
    * @param message message associated with exception
    */
	public PersistenceException(String message) {
		super(message);
	}
   
   /**
    * Parameterized Constructor.
    * 
    * @param message message associated with exception
    */
   public PersistenceException(String message, ErrorEnum error) {
      super(message, error);
   }

	/**
    * Parameterized Constructor.
    * 
    * @param cause cause of exception
    */
	public PersistenceException(Throwable cause) {
		super(cause);
	}

   /**
    * Parameterized Constructor.
    * 
    * @param message message to be put into the stack trace for log purposes.
    * @param errors error code(s) to be provided to the UI.
    */
   public PersistenceException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * Parameterized Constructor.
    * 
    * @param message message to be put into the stack trace for log purposes.
    * @param cause cause of exception
    * @param errors error code(s) to be provided to the UI.
    */
   public PersistenceException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
   }
}
