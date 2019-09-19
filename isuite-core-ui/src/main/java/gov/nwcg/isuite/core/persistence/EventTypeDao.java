package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.filter.EventTypeFilter;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface EventTypeDao extends TransactionSupport, CrudDao<EventType>{
   
   /**
    * Get a list of Event Types based on the provided criteria.
    * @param filter
    * @return {@link Collection} of {@link EventTypeVo}'s
    */
   public Collection<EventTypeVo> getPicklist(EventTypeFilter filter) throws PersistenceException;
}