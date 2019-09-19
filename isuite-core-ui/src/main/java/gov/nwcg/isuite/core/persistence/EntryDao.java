package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.EntryInfo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * This is the DAO that deals with entries.
 * 
 * @author doug
 */
public interface EntryDao extends CrudDao<Entry> {

   /**
    * Return next entry to process.
    * <p>
    * If there is an entry of the given <code>UpdateDataTypeEnum</code> and from the specified <code>source</code>
    * with the <code>UpdateStateEnum.PROCESSING</code>, then there is not a <i>next</i> entry to process and the
    * this method returns <code>null</code>.
    * </p>
    * <p>
    * Otherwise, the next entry is the oldest entry of the given <code>UpdateDataTypeEnum</code> from the specified
    * <code>source</code> that is in the <code>UpdateStateEnum.INITIAL</code>.
    * </p>
    * 
    * @param EntryInfo
    *           describes the data to be retrieved unique identifer of that data type
    * @return next entry to process, may be null
    * @throws PersistenceException
    *            if needed
    */
   Entry getNext(EntryInfo entryInfo) throws PersistenceException;
   
   public Collection<Entry> getAll(Filter filter) throws PersistenceException;

}
