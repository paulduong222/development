/**
 * Represents problems with DataStreams
 */
package gov.nwcg.isuite.framework.exceptions;


/**
 * @author dougAnderson
 *
 */
public class DataStreamException extends IsuiteException {

   /**
    * 
    */
   private static final long serialVersionUID = -3284981799179995737L;

   /**
    * Constructor.
    */
   public DataStreamException() {
      super();
   }

   /**
    * Constructor.
    * @param message
    */
   public DataStreamException(String message) {
      super(message);
     
   }

   /**
    * Constructor.
    * @param cause
    */
   public DataStreamException(Throwable cause) {
      super(cause);
   }

   /**
    * Constructor.
    * @param message
    * @param cause
    */
   public DataStreamException(String message, Throwable cause) {
      super(message, cause);
   }

}
