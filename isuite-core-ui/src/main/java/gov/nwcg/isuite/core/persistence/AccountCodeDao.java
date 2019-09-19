package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.vo.AccountCodePicklistVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.core.persistence.TransferableDao;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface AccountCodeDao extends TransactionSupport, CrudDao<AccountCode> {

   /**
    * @param accountCode The Account Code we are retrieving the AccountCode object for
    * @throws PersistenceException
    */
   public AccountCode getAccountCodeByAccountCode(String accountCode) throws PersistenceException;
   
   /**
    * 
    * @param accountCode
    * @return
    * @throws PersistenceException
    */
   public Collection<AccountCodePicklistVo> getAccountCodePicklistVosByAccountCode(String accountCode) throws PersistenceException;
   
   /**
    * @param accountCodeId The Account Code Id of the Account Code to be removed from the database.
    * @throws PersistenceException
    */
   public void delete(Long accountCodeId) throws PersistenceException;
   
   public AccountCode getByKey(String accountCode, Long agencyId) throws PersistenceException;

   public Collection<AccountCode> getAllByKey(String accountCode, Long agencyId) throws PersistenceException;

   public Collection<AccountCode> getAllByCode(String accountCode) throws PersistenceException;
   
}
