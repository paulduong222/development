package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageCauseEnum;

import java.util.Collection;
import java.util.Map;


/**
 * Exceptions from service layer.
 * @author dougAnderson
 *
 */
public class ServiceException extends IsuiteException{
	
	private MessageCauseEnum messageCause = MessageCauseEnum.UNSPECIFIED;
	
	/**
     * SerializationId
    */
   private static final long serialVersionUID = 787948572640061288L;

   /**
	 * Default Constructor which has a default {@link MessageCauseEnum} of "UNSPECIFIED"
	 * 
	 */
	public ServiceException() {
		super();		
	}
	
   public ServiceException(String msg, String propertyErrorMsg) {
	   super(msg,propertyErrorMsg);
   }

   /**
	 * Constructor.
	 * @param message
	 * @param t
	 */
	public ServiceException(String message, Throwable t) {
		super(message, t);
	}

	/**
    * Constructor.
    * 
    * @param cause
    */
   public ServiceException(ErrorEnum error) {
	   super(error);
   }
	
	/**
	 * Constructor.
	 * @param messageCause
	 * @param message
	 * @param t
	 */
	public ServiceException(MessageCauseEnum messageCause, String message, Throwable t) {
		super(message, t);
		this.messageCause = messageCause;
	}
	
	/**
	 * Constructor.
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param messageCause
	 * @param message
	 */
	public ServiceException(MessageCauseEnum messageCause, String message) {
		super(message);
		this.messageCause = messageCause;
	}

	/**
	 * Constructor.
	 * @param t
	 */
	public ServiceException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor.
	 * @param messageCause
	 * @param t
	 */
	public ServiceException(MessageCauseEnum messageCause, Throwable t) {
		super(t);
		this.messageCause= messageCause;
	}
   
	/**
    * Constructor.
    * 
    * @param message
    * @param errors
    */
   public ServiceException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * @param message
    * @param cause
    * @param errors
    */
   public ServiceException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
   }
   
   /**
    * Constructor.
    * 
    * @param message
    * @param errors
    */
   public ServiceException(String message, Map<Enum<?>, Object[]> errors) {
      super(message, errors);
   }

   
   /**
    * @param message
    * @param cause
    * @param errors
    */
   public ServiceException(String message, Throwable cause, Map<Enum<?>, Object[]> errors) {
      super(message, cause, errors);
   }

	public ServiceException(ErrorObject errorObject){
		super(errorObject);
	}

	public ServiceException(Collection<ErrorObject> errorObjects){
		super(errorObjects);
	}
   
   /**
    * @return the cause
    */
   public MessageCauseEnum getMessageCause() {
   	return messageCause;
   }

	/**
    * @param cause the cause to set
    */
   public void setMessageCause(MessageCauseEnum messageCause) {
   	this.messageCause = messageCause;
   }


}
