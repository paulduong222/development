package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.EventTypeFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class EventTypeFilterImpl extends FilterImpl implements EventTypeFilter {

   private static final long serialVersionUID = -3788939806355561004L;
   private String eventType;
   private String eventTypeCode;
   
   /**
    * Code of the event type.
    * @param event type code cannot be null
    * @see #getEventTypeCode()
    */
   public void setEventTypeCode(String eventTypeCode) {
      this.eventTypeCode = eventTypeCode;
   }

   /**
    * Code of the event type
    * @return code of the event type, will not be null
    * @see #setEventTypeCode(String)
    */
   public String getEventTypeCode() {
      return eventTypeCode;
   }

   /**
    * Accessor for description of event type code.
    * @param eventType event type description of event type code, cannot be null
    * @see #getEventType()
    */
   public void setEventType(String eventType) {
      this.eventType = eventType;
   }

   /**
    * Accessor for description of event type code.
    * @return eventType event type code description
    * @see #setEventType(String)
    */
   public String getEventType() {
      return eventType;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#reset()
    */
   public void reset() {
      this.eventType = null;
      this.eventTypeCode = null;
   }
}
