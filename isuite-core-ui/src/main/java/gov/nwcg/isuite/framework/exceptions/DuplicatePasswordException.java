/**
 * 
 */
package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.framework.types.MessageCauseEnum;

import java.util.Collection;

/**
 * Used to indicate that the password is a duplicate.
 * @author doug
 *
 */
public class DuplicatePasswordException extends ServiceException {

   private static final long serialVersionUID = 2737083533162372106L;

   /**
    * Constructor.
    */
   public DuplicatePasswordException() {
   }

   /**
    * Constructor.
    * @param message
    * @param t
    */
   public DuplicatePasswordException(String message, Throwable t) {
      super(message, t);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param message
    * @param t
    */
   public DuplicatePasswordException(MessageCauseEnum messageCause, String message, Throwable t) {
      super(messageCause, message, t);
   }

   /**
    * Constructor.
    * @param message
    */
   public DuplicatePasswordException(String message) {
      super(message);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param message
    */
   public DuplicatePasswordException(MessageCauseEnum messageCause, String message) {
      super(messageCause, message);
   }

   /**
    * Constructor.
    * @param t
    */
   public DuplicatePasswordException(Throwable t) {
      super(t);
   }

   /**
    * Constructor.
    * @param messageCause
    * @param t
    */
   public DuplicatePasswordException(MessageCauseEnum messageCause, Throwable t) {
      super(messageCause, t);
   }

   /**
    * Constructor.
    * 
    * @param message
    * @param errors
    */
   public DuplicatePasswordException(String message, Collection<Enum<?>> errors) {
      super(message, errors);
   }

   /**
    * Constructor.
    * 
    * @param message
    * @param cause
    * @param errors
    */
   public DuplicatePasswordException(String message, Throwable cause, Collection<Enum<?>> errors) {
      super(message, cause, errors);
   }

}
