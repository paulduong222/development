package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.impl.EventTypeImpl;
import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.core.filter.EventTypeFilter;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.filter.ObjectFilter;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author kvelasquez
 */
public class EventTypeVo extends AbstractVo implements PersistableVo {
   private String type;
   private String eventTypeCd;
   
   public EventTypeVo() {
      super();
   }
   
   public EventTypeVo(EventType et) {
      super();
      if (et != null) {
         setType(et.getEventType());
         setEventTypeCd(et.getEventTypeCode());
         setId(et.getId());
      }
   }
   
   /**
	 * Returns a EventTypeVo instance from an entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of EventTypeVo
	 * @throws Exception
	 */
	public static EventTypeVo getInstance(EventType entity,boolean cascadable) throws Exception {
		EventTypeVo vo = new EventTypeVo();

		if(null == entity)
			throw new Exception("Unable to create EventTypeVo from null entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setEventTypeCd(entity.getEventTypeCode());
			vo.setType(entity.getEventType());
			// populate the rest
		}

		return vo;
	}

	   /**
	    * Returns a EventType entity from a vo.
	    * 
	    * @param vo
	    * 			the source vo
	    * @param cascadable
	    * 			flag indicating whether the entity instance should created as a cascadable entity
	    * @return
	    * 			instance of EventType entity
	    * @throws Exception
	    */
	   public static EventType toEntity(EventTypeVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		   EventType entity=new EventTypeImpl();
			
		   entity.setId(vo.getId());
			
		   if(cascadable){
		   }
		   
		   return entity;
	   }
	
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public EventType toEntity(Persistable entity) throws Exception {
	   return populateEntity(this, ((EventType)entity));
   }
   
   /**
    * Populates and returns an entity object with the values from the vo object.
    * 
    * @param vo
    * 			the source vo object
    * @param entity
    * 			the entity to populate and return
    * @return
    * 		the entity to return
    */
   private static EventType populateEntity(EventTypeVo vo, EventType entity) {
	   if(null==entity){
		   entity = new EventTypeImpl();
	   }
	   
	   entity.setId(vo.getId());
	   
	   return entity;
   }
   
   /**
    * Code of the event type.
    * @param event type code cannot be null
    * @see #getEventTypeCd()
    */
   public void setEventTypeCd(String eventTypeCode) {
      this.eventTypeCd = eventTypeCode;
   }

   /**
    * Code of the event type
    * @return code of the event type, will not be null
    * @see #setEventTypeCd(String)
    */
   public String getEventTypeCd() {
      return eventTypeCd;
   }

   /**
    * Accessor for description of event type code.
    * @param type event type description of event type code, cannot be null
    * @see #getType()
    */
   public void setType(String eventType) {
      this.type = eventType;
   }

   /**
    * Accessor for description of event type code.
    * @return type event type code description
    * @see #setType(String)
    */
   public String getType() {
      return type;
   }

   /**
    * @param filter
    * @param sourceVos
    * @return
    * @throws Exception
    */
   public static Collection<EventTypeVo> getVosByFilter(EventTypeFilter filter, Collection<EventTypeVo> sourceVos) throws Exception {
	   Collection<EventTypeVo> vos = new ArrayList<EventTypeVo>();
	   ObjectFilter oFilter = new ObjectFilter(EventTypeVo.class);
	   
	   Collection<FilterCriteria> criteria = getVoFilterCriteria(filter);

	   if(!VoValidator.hasOneFilter(criteria)){
		   return sourceVos;
	   }
	   
	   for(EventTypeVo vo : sourceVos){
		   EventTypeVo ccVo = (EventTypeVo)oFilter.filterVo(vo, criteria);
		   
		   if(null != ccVo)
			   vos.add(ccVo);
	   }
	   
	   return vos;
   }

	public static Collection<FilterCriteria> getVoFilterCriteria(EventTypeFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		
		criteria.add( null != filter.getEventType() && !filter.getEventType().isEmpty() ? new FilterCriteria("type",filter.getEventType(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getEventTypeCode() && !filter.getEventTypeCode().isEmpty() ? new FilterCriteria("eventTypeCd",filter.getEventTypeCode(),FilterCriteria.TYPE_ILIKE) : null);

		return criteria;
	}

}
