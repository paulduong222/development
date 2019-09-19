package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception base class for Exceptions to be used in the application.
 * 
 * This Exception class allows for errors to be associated to it.  The errors 
 * are Enums that can be associated to error messages found in the 
 * <code>isuite.properties</code> file.  
 * 
 * The error in the properties file should be identified like the following if 
 * you added PLETHORA_OF_CHARACTERS to the Collection:
 * 
 * error.plethora_of_characters=You have provided a plethora of characters which exceeds our limit.
 * 
 * @author bsteiner
 *
 */
public class IsuiteException extends Exception {

	private static final long serialVersionUID = 130237474681930060L;

	private Map<Enum<?>, Object[]> errors;

	private Collection<ErrorObject> errorObjects=new ArrayList<ErrorObject>();

	private String propertyErrorMsg="";
	
	/**
	 * Default Constructor
	 */
	public IsuiteException() {
		super();
	}

	/**
	 * Default constructor
	 * @param message a message to be logged stack trace.
	 */
	public IsuiteException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public IsuiteException(Throwable cause) {
		super(cause);
		if(cause instanceof IsuiteException) {
			this.setErrors(((IsuiteException) cause).getErrors());
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public IsuiteException(ErrorEnum error) {
		super();
		this.errorObjects.add(new ErrorObject(error));
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public IsuiteException(String msg, ErrorEnum error) {
		super(msg);
		this.addError(error);
	}

	public IsuiteException(String msg, String propertyErrorMsg) {
		super(msg);
		this.propertyErrorMsg=propertyErrorMsg;
	}

	/**
	 * This constructor will take a Map of errors and their associated parameters.
	 * This map should only be used for error messages that have parameters, although
	 * it could also work with error messages that don't have parameters.
	 * 
	 * @param cause
	 */
	public IsuiteException(Map<Enum<?>,Object[]> errorsWithParameters) {
		super(errorsWithParameters.isEmpty()? 
				"Who added an empty Map of errors?" : errorsWithParameters.keySet().iterator().next().toString());
		this.errors = errorsWithParameters;
	}

	/**
	 * Constructor.
	 * 
	 * @param message a message to be logged stack trace
	 * @param cause
	 */
	public IsuiteException(String message, Throwable cause) {
		super(message, cause);
		if(cause instanceof IsuiteException) {
			this.setErrors(((IsuiteException) cause).getErrors());
		}
	}

	/**
	 * Constructor allowing a message for log and errors to be passed to UI.
	 * @param message a message to be logged stack trace
	 * @param errors
	 */
	public IsuiteException(String message, Collection<Enum<?>> errors) {
		super(message);
		for (Enum<?> e : errors) {
			this.addError(e);  
		}
	}

	/**
	 * This Constructor is used to be able to pass error(s) along to the UI.  
	 * 
	 * @param message a message to be logged stack trace
	 * @param cause
	 * @param errors
	 */
	public IsuiteException(String message, Throwable cause, Collection<Enum<?>> errors) {
		super(message, cause);
		for (Enum<?> e : errors) {
			this.addError(e);  
		}
	}

	/**
	 * Constructor allowing a message for log and parameterized errors to be passed to UI.
	 * @param message a message to be logged stack trace
	 * @param errors
	 */
	public IsuiteException(String message, Map<Enum<?>,Object[]> errorsWithParameters) {
		super(message);
		this.errors = errorsWithParameters;
	}

	/**
	 * This Constructor is used to be able to pass parameterized error(s) along to the UI.  
	 * 
	 * @param message a message to be logged stack trace
	 * @param cause
	 * @param errors
	 */
	public IsuiteException(String message, Throwable cause, Map<Enum<?>,Object[]> errorsWithParameters) {
		super(message, cause);
		this.errors = errorsWithParameters;
	}

	public IsuiteException(ErrorObject errorObject){
		super("Service Exception");
		this.errorObjects.add(errorObject);
	}

	public IsuiteException(Collection<ErrorObject> errorObjects){
		super("Service Exception");
		this.errorObjects=errorObjects;
	}
	
	/**
	 * @return the errors
	 */
	public Map<Enum<?>,Object[]> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(Map<Enum<?>,Object[]> errors) {
		this.errors = errors;
	}

	/**
	 * @param error enum key that corresponds to the error.properties file
	 * @return void
	 */
	public void addError(Enum<?> error) {
		if(null == errors) {
			errors = new HashMap<Enum<?>,Object[]>();
		}
		errors.put(error, null);
	}

	/**
	 * @param error enum key that corresponds to the error.properties file
	 * @return void
	 */
	public void addError(Enum<?> error, Object[] params) {
		if(null == errors) {
			errors = new HashMap<Enum<?>,Object[]>();
		}
		errors.put(error, params);
	}

	/**
	 * Returns the propertyErrorMsg.
	 *
	 * @return 
	 *		the propertyErrorMsg to return
	 */
	public String getPropertyErrorMsg() {
		return propertyErrorMsg;
	}

	/**
	 * Sets the propertyErrorMsg.
	 *
	 * @param propertyErrorMsg 
	 *			the propertyErrorMsg to set
	 */
	public void setPropertyErrorMsg(String propertyErrorMsg) {
		this.propertyErrorMsg = propertyErrorMsg;
	}

	/**
	 * Returns the errorObjects.
	 *
	 * @return 
	 *		the errorObjects to return
	 */
	public Collection<ErrorObject> getErrorObjects() {
		return errorObjects;
	}

	/**
	 * Sets the errorObjects.
	 *
	 * @param errorObjects 
	 *			the errorObjects to set
	 */
	public void setErrorObjects(Collection<ErrorObject> errorObjects) {
		this.errorObjects = errorObjects;
	}

	
}
