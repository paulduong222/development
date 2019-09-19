/**
 * 
 */
package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.MessageCauseEnum;

import java.util.Collection;

/**
 * Used when the desiredPassword and confirmPassword do not match
 * @author doug
 *
 */
public class MismatchedPasswordException extends ServiceException {

   private static final long serialVersionUID = -8705324239209280530L;

   /**
    * Constructor.
    * 
    */
   public MismatchedPasswordException() {
   }

   /**
    * Constructor.
    * @param message
    * @param t
    */
   public MismatchedPasswordException(String message, Throwable t) {
      super(message, t);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param message
    * @param t
    */
   public MismatchedPasswordException(MessageCauseEnum messageCause, String message, Throwable t) {
      super(messageCause, message, t);
   }

   /**
    * Constructor.
    * @param message
    */
   public MismatchedPasswordException(String message) {
      super(message);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param message
    */
   public MismatchedPasswordException(MessageCauseEnum messageCause, String message) {
      super(messageCause, message);
   }

   /**
    * Constructor.
    * @param t
    */
   public MismatchedPasswordException(Throwable t) {
      super(t);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param t
    */
   public MismatchedPasswordException(MessageCauseEnum messageCause, Throwable t) {
      super(messageCause, t);
   }

   /**
    * Constructor.
    * 
    * @param message
    * @param errors
    */
   public MismatchedPasswordException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * Constructor.
    * 
    * @param message
    * @param cause
    * @param errors
    */
   public MismatchedPasswordException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
   }

}
