package gov.nwcg.isuite.framework.other;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.framework.types.MessageCauseEnum;
import gov.nwcg.isuite.framework.types.MessageSeverityEnum;


/**
 * Factory to create messages.
 * @author dougAnderson
 *
 */
public interface MessageFactory {
   
   /**
    * Create a message based on the cause and exception.
    * <p>The  title and details of the created exception come from the exception, the severity is MessageSeverity:Critical </p>
    * @param cause cause of the message.
    * @param exception exception on which the message is based.
    * @return new message instance
    */
   public Message createMessage(MessageCauseEnum cause, Exception exception);
   
   /**
    * Create a message. 
    * @param severity desired severity level
    * @param cause cause of message
    * @param title title of message
    * @param description description of message
    * @return new message instance
    */
   public Message createMessage(MessageSeverityEnum severity, MessageCauseEnum cause, String title, String description);

}



