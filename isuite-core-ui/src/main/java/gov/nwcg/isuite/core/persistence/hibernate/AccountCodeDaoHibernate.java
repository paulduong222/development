package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.impl.AccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.core.persistence.AccountCodeDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.AccountCodeQuery;
import gov.nwcg.isuite.core.vo.AccountCodePicklistVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransferableDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author mpoll
 *
 */
public class AccountCodeDaoHibernate extends TransactionSupportImpl implements AccountCodeDao {

   private final CrudDao<AccountCode> crudDao;
//   private static final Logger LOG = Logger.getLogger(AccountCodeDaoHibernate.class);
   
   /**
    * Constructor.
    * @param crudDao can't be null
    * @param transferableDao can't be null
    */
   public AccountCodeDaoHibernate(final CrudDao<AccountCode> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao cannot be null");
      }
      this.crudDao = crudDao;
      
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.finance.AccountCodeDao#delete(java.lang.Long)
    */
   public void delete(Long accountCodeId) throws PersistenceException {
      if (accountCodeId.compareTo(0L) == 0) {
         throw new PersistenceException("accountCodeId cannot be 0 or null.");
      }
      AccountCode ac = this.getById(accountCodeId, AccountCode.class);
      this.delete(ac);
   }

   public AccountCode getByKey(String accountCode, Long agencyId) throws PersistenceException{
	   if ( accountCode == null || accountCode.isEmpty() || agencyId == null || agencyId == 0 ) {
		   throw new PersistenceException("accountCode and agencyId are required!");
	   }

		Criteria crit = getHibernateSession().createCriteria(AccountCodeImpl.class);
		
		crit.add(Expression.eq("this.accountCode", accountCode.toUpperCase().trim()));
		crit.add(Expression.eq("this.agencyId", agencyId));

		AccountCode entity = (AccountCode)crit.uniqueResult();
		
		return entity;
   }

   public Collection<AccountCode> getAllByKey(String accountCode, Long agencyId) throws PersistenceException {
	   if ( accountCode == null || accountCode.isEmpty() || agencyId == null || agencyId == 0 ) {
		   throw new PersistenceException("accountCode and agencyId are required!");
	   }

		Criteria crit = getHibernateSession().createCriteria(AccountCodeImpl.class);
		
		crit.add(Expression.eq("this.accountCode", accountCode.toUpperCase().trim()));
		crit.add(Expression.eq("this.agencyId", agencyId));

		return crit.list();
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(AccountCode persistable) throws PersistenceException {
      if (persistable == null) {
         throw new PersistenceException("accountCode cannot be null");
      }
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public AccountCode getById(Long id, Class clazz) throws PersistenceException {
	   return crudDao.getById(id, AccountCodeImpl.class);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(AccountCode persistable) throws PersistenceException {
      if (persistable == null) {
         throw new PersistenceException("Incoming object cannot be null.");
      }
      try {
         crudDao.save(persistable);
      } catch (DataIntegrityViolationException dive) {
         throw new PersistenceException(dive);
      }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<AccountCode> persistables) throws PersistenceException {
      if (persistables == null) {
         throw new PersistenceException("Incoming collection cannot be null.");
      }
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.finance.AccountCodeDao#getAccountCodePicklistVosByAccountCode(java.lang.String)
    */
   @SuppressWarnings("unchecked")
   public Collection<AccountCodePicklistVo> getAccountCodePicklistVosByAccountCode(String accountCode) throws PersistenceException {
      
      Criteria criteria = getHibernateSession().createCriteria(AccountCodeImpl.class);
      criteria.createAlias("agency", "a");
      criteria.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("a.agencyCode"), "agencyCd")
               .add(Projections.property("accountCode"), "accountCd"));
      
      if (accountCode != null && accountCode.trim().length() > 0) {
         criteria.add(Restrictions.ilike("accountCode", accountCode.trim(), MatchMode.START));
      }
      
      criteria.setResultTransformer(Transformers.aliasToBean(AccountCodePicklistVo.class));
      criteria.setMaxResults(getMaxResultSize());      
      return criteria.list();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.finance.AccountCodeDao#getAccountCodeByAccountCode(java.lang.String)
    */
   public AccountCode getAccountCodeByAccountCode(String accountCode) throws PersistenceException {
      Query query = getHibernateSession().createQuery("from AccountCodeImpl where accountCode = :accountCode");
      query.setString("accountCode", accountCode);
      return (AccountCode)query.uniqueResult();
   }

   public Collection<AccountCode> getAllByCode(String accountCode) throws PersistenceException {
	   if ( accountCode == null || accountCode.isEmpty()  ) {
		   throw new PersistenceException("accountCode are required!");
	   }

		Criteria crit = getHibernateSession().createCriteria(AccountCodeImpl.class);
		
		crit.add(Expression.eq("this.accountCode", accountCode.toUpperCase().trim()));

		return crit.list();
   }

   
}
