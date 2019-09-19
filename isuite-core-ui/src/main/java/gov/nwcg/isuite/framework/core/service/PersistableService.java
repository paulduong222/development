package gov.nwcg.isuite.framework.core.service;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface PersistableService<T extends Persistable, E extends PersistableVo> {

   /**
    * Delete the item.
    * <p>
    * If the id of the item is null or zero, then nothing happens because the
    * item was never perisisted in the first place.
    * </p>
    * 
    * @param persistable
    *           item to be deleted
    * @throws ServiceException
    *            if needed
    */
   public void delete(T persistable) throws ServiceException;

   /**
    * Save the item.
    * <p>
    * if the id of the item is null or zero, then a new item will be persisted,
    * if the id of the item is non-null or zero then the existing item with that
    * id will be updated to refect the state of the item passed in as the
    * arguement.
    * </p>
    * 
    * @param persistable
    *           item to be saved
    * @throws ServiceException
    *            if needed
    */
   public void save(T persistable) throws ServiceException;

}