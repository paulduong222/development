package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ExternalSystem;
import gov.nwcg.isuite.core.persistence.ExternalSystemDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.NoSuchItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.DataFlowDirectionEnum;

public class ExternalSystemDaoHibernate extends TransactionSupportImpl implements ExternalSystemDao {

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.update.enterprise.ExternalSystemDao#getDefaultISuiteSite(gov.nwcg.isuite.domain.update.enterprise.DataFlowDirectionEnum)
    */
   public ExternalSystem getDefaultISuiteSite(DataFlowDirectionEnum direction) throws PersistenceException {
      return null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.update.enterprise.ExternalSystemDao#getISuiteSite(java.lang.String, gov.nwcg.isuite.domain.update.enterprise.DataFlowDirectionEnum)
    */
   public ExternalSystem getISuiteSite(String name, DataFlowDirectionEnum direction) throws PersistenceException {
      return null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.update.enterprise.ExternalSystemDao#getOis()
    */
   public ExternalSystem getOis() throws PersistenceException, NoSuchItemException {
      return null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.update.enterprise.ExternalSystemDao#getRoss(gov.nwcg.isuite.domain.update.enterprise.DataFlowDirectionEnum)
    */
   public ExternalSystem getRoss(DataFlowDirectionEnum direction) throws PersistenceException, NoSuchItemException {
      return null;
   }

}
