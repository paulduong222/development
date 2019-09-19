package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface EventTypeFilter extends Filter {
   
   /**
    * Code of the event type.
    * @param event type code cannot be null
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
    * @param eventType event type description of event type code, cannot be null
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
