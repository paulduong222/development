package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface AccrualCodeDao extends TransactionSupport, CrudDao<AccrualCode> {

   /**
    * @return
    * @throws PersistenceException
    */
   public Collection<AccrualCode> getAll() throws PersistenceException;

   public Collection<AccrualCodeVo> getPicklist() throws PersistenceException;
   
}
