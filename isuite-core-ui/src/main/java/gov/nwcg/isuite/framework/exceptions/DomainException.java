/**
 * Exceptions from domain layer.
 */
package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.MessageCauseEnum;

import java.util.Collection;


/**
 * Exceptions from domain layer.
 * @author mpoll
 *
 */
public class DomainException extends IsuiteException {
	
	private MessageCauseEnum messageCause = MessageCauseEnum.UNSPECIFIED;

	/**
     * SerializationId
    */
   private static final long serialVersionUID = 787948572640061288L;

   /**
	 * Default Constructor which has a default {@link MessageCauseEnum} of "UNSPECIFIED"
	 * 
	 */
	public DomainException() {
		super();		
	}
	
	/**
	 * Constructor.
	 * @param message
	 * @param t
	 */
	public DomainException(String message, Throwable t) {
		super(message, t);
	}

	/**
	 * Constructor.
	 * @param messageCause
	 * @param message
	 * @param t
	 */
	public DomainException(MessageCauseEnum messageCause, String message, Throwable t) {
		super(message, t);
		this.messageCause = messageCause;
	}
	
	/**
	 * Constructor.
	 * @param message
	 */
	public DomainException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param messageCause
	 * @param message
	 */
	public DomainException(MessageCauseEnum messageCause, String message) {
		super(message);
		this.messageCause = messageCause;
	}

	/**
	 * Constructor.
	 * @param t
	 */
	public DomainException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor.
	 * @param messageCause
	 * @param t
	 */
	public DomainException(MessageCauseEnum messageCause, Throwable t) {
		super(t);
		this.messageCause= messageCause;
	}
   
	/**
    * Constructor.
    * 
    * @param message
    * @param errors
    */
   public DomainException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * @param message
    * @param cause
    * @param errors
    */
   public DomainException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
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
