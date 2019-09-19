/**
 * 
 */
package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

/**
 * @author mpoll
 *
 */
public interface EventType extends Persistable{
   /**
    * Code of the event type.
    * @param event type code can not be null
    * @see #getEventTypeCode()
    */
   public void setEventTypeCode(String eventTypeCode);

   /**
    * Code of the event type
    * @return code of the event type, will not be null
    * @see #setEventTypeCode(String)
    */
   public String getEventTypeCode();

   /**
    * Accessor for description of event type code.
    * @param eventType event type description of event type code, can not be null
    * @see #getEventType()
    */
   public void setEventType(String eventType);

   /**
    * Accessor for description of event type code.
    * @return eventType event type code description
    * @see #setEventType(String)
    */
   public String getEventType();
}
