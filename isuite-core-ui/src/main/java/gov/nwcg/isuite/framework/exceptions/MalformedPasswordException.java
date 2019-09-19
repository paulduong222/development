/**
 * 
 */
package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.MessageCauseEnum;

import java.util.Collection;

/**
 * Used to indicate that the password does not meet validation criteria.
 * @author doug
 *
 */
public class MalformedPasswordException extends ServiceException {

   private static final long serialVersionUID = -8421400055643678687L;

   /**
    * Constructor.
    * 
    */
   public MalformedPasswordException() {
      super();
   }

   /**
    * Constructor.
    * @param messageCause
    * @param message
    * @param t
    */
   public MalformedPasswordException(MessageCauseEnum messageCause, String message, Throwable t) {
      super(messageCause, message, t);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param message
    */
   public MalformedPasswordException(MessageCauseEnum messageCause, String message) {
      super(messageCause, message);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param t
    */
   public MalformedPasswordException(MessageCauseEnum messageCause, Throwable t) {
      super(messageCause, t);
   }

   /**
    * Constructor.
    * @param message
    * @param t
    */
   public MalformedPasswordException(String message, Throwable t) {
      super(message, t);
   }

   /**
    * Constructor.
    * @param message
    */
   public MalformedPasswordException(String message) {
      super(message);
   }

   /**
    * Constructor.
    * @param t
    */
   public MalformedPasswordException(Throwable t) {
      super(t);
   }

   /**
    * Constructor.
    * 
    * @param message
    * @param errors
    */
   public MalformedPasswordException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * Constructor.
    * 
    * @param message
    * @param cause
    * @param errors
    */
   public MalformedPasswordException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
   }
}
