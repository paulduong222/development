package gov.nwcg.isuite.framework.exceptions;

import java.util.Collection;
import java.util.Map;

public class DailyCostException extends IsuiteException {
	private static final long serialVersionUID = -3L;

	/**
	 * Default constructor
	 */
	public DailyCostException(){
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param message
	 */
   public DailyCostException(String message){
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
	public DailyCostException(String message, Collection<Enum<?>> errors){
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
   public DailyCostException(String message, Map<Enum<?>, Object[]> errors){
      super(message, errors);
   }

}
