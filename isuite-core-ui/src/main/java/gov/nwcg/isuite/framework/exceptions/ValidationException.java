package gov.nwcg.isuite.framework.exceptions;

import java.util.Collection;
import java.util.Map;
/**
 * @author mpaskett
 *
 */

public class ValidationException extends IsuiteException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -317160144799096350L;

	/**
	 * Default constructor
	 */
	public ValidationException(){
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param message
	 */
   public ValidationException(String message){
      super(message);
   }
	
	/**
	 * Constructor
	 *  
	 * @param message
	 * 		message describing the exception
	 * @param errors
	 * 		Collection of enums used as keys in a properties file.  Used by 
	 * the UI to make meaningful messages 
	 */
	public ValidationException(String message, Collection<Enum<?>> errors){
		super(message, errors);
	}
	
	/**
    * Constructor
    *  
    * @param message
    *       message describing the exception
    * @param errors
    *       Map of enums used as keys in a properties file. The value is the paramaters that
    *       will be passed to the message.  Used by the UI to make meaningful messages 
    */
   public ValidationException(String message, Map<Enum<?>, Object[]> errors){
      super(message, errors);
   }

	public ValidationException(ErrorObject errorObject){
		super(errorObject);
	}

	public ValidationException(Collection<ErrorObject> errorObjects){
		super(errorObjects);
	}

}
