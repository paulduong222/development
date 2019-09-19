package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentQSKindDao extends TransactionSupport, CrudDao<IncidentQSKind> {

   /**
    * Retrieve available item codes.
    * 
    * @param incidentOrGroupId
    * @param isGroup
    * @param itemFilter
    * @return {@link Collection} of {@link KindVo} objects.
    * @throws PersistenceException
    */
   public Collection<KindVo> getAvailablePrefKindCodes(Long incidentOrGroupId, Boolean isGroup, KindFilter itemFilter) throws PersistenceException;
   
   /**
    * Retrieves the selected item codes ({@link KindImpl} objects).
    * 
    * @param incidentOrGroupId
    * @param isGroup
    * @return {@link Collection} of {@link KindVo} objects.
    * @throws PersistenceException
    */
   public Collection<KindVo> getSelectedPrefKindCodes(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;
   
   /**
    * Removes All Kind Codes from the IncidentQSKind table with a specific incidentOrGrouopId
    * @param incidentOrGroupId id of record(s) to remove from the table
    * @param isGroup Are these records associated with a group?
    * @throws PersistenceException
    */
   public void removeAllQSKindsWithId(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException;

}
