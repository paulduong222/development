package gov.nwcg.isuite.framework.core.persistence.hibernate;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Base class for crud (create, read, update, delete) operations.
 * <p>
 * extends TransactionSupportImpl to make all operations transactional
 * </p>
 * 
 * @author doug
 */
public class CrudDaoHibernate<P extends Persistable> extends TransactionSupportImpl implements CrudDao<P> {

	private static final Logger log = Logger.getLogger(CrudDaoHibernate.class);
   
	
   /*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.persistence.CrudDao#delete(java.lang.Object)
	 */
	public void delete(P persistent) throws PersistenceException {
		if (persistent == null) {
			throw new PersistenceException("object can not be null");
		}
      if (log.isDebugEnabled()) {
         log.debug("deleting id: " + persistent.getId() + " of type: " + persistent.getClass().getName());
      }
		getHibernateTemplate().delete(persistent); // could specify lock mode
	}
   
   @SuppressWarnings("unchecked")
	protected P getUniqueByColumn(String queryName, String queryParameterName,
			Object parameter) throws PersistenceException {
		getHibernateTemplate().setMaxResults(getMaxResultSize());
		HibernateTemplate template = getHibernateTemplate();
		List<P> results = template.findByNamedQueryAndNamedParam(queryName,
				queryParameterName, parameter);
		if (results == null || results.size() == 0) {
			return null;
		}
		// since this is unique, only return the first (should only be one!)
		/*
		 * if (results.size() != 1) { throw new PersistenceException("more than
		 * one object with " + queryParameterName + ": [" + parameter + "]"); }
		 */
		return results.get(0);
	}

	@SuppressWarnings("unchecked")
	protected P getUniqueByColumns(String queryName,
			String[] queryParameterNames, Object[] parameters)
			throws PersistenceException {
		getHibernateTemplate().setMaxResults(getMaxResultSize());
		HibernateTemplate template = getHibernateTemplate();
		List<P> results = template.findByNamedQueryAndNamedParam(queryName,
				queryParameterNames, parameters);
		if (results == null || results.size() == 0) {
			return null;
		}
		// since this is unique, only return the first (should only be one!)
		/*
		 * if (results.size() != 1) { throw new PersistenceException("more than
		 * one object with " + queryParameterName + ": [" + parameter + "]"); }
		 */
		return results.get(0);
	}

	@SuppressWarnings("unchecked")
	protected Collection<P> getCollectionByColumn(String queryName,
			String queryParameterName, Object parameter)
			throws PersistenceException {
		getHibernateTemplate().setMaxResults(getMaxResultSize());
		HibernateTemplate template = getHibernateTemplate();
		List<P> results = template.findByNamedQueryAndNamedParam(queryName,
				queryParameterName, parameter);
		return results;

	}

	@SuppressWarnings("unchecked")
	protected Collection<P> getCollectionByColumns(String queryName,
			String[] queryParameterNames, Object[] parameters)
			throws PersistenceException {
		getHibernateTemplate().setMaxResults(getMaxResultSize());
		HibernateTemplate template = getHibernateTemplate();
		List<P> results = template.findByNamedQueryAndNamedParam(queryName,
				queryParameterNames, parameters);
		return results;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(int)
	 */
	@SuppressWarnings("unchecked")
	public P getById(Long id, Class<?> clazz) throws PersistenceException {
      if (id == null) {
    	  throw new PersistenceException("Unable to getById with null id");
      }
      log.debug("Retrieving id: " + id + " of type: " + clazz.getCanonicalName());
      return (P) getHibernateSession().get(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(java.lang.Object)
	 */
	public void save(P persistent) throws PersistenceException {
		if (persistent == null) {
			throw new IllegalArgumentException(
					"Persistent object can not be null");
		}
		try {
         if (log.isDebugEnabled()) {
            log.debug("saving id: " + persistent.getId() + " of type: " + persistent.getClass().getName());
         }
         
       	 setAuditableInfo(persistent);

       	 if(BooleanUtility.isFalse(skipFixCasing))
       		 super.fixCasing(persistent);
       	 
         getHibernateTemplate().saveOrUpdate(persistent);
		} catch (ConstraintViolationException cve) {
			log.error("Constraint error: " + cve.getConstraintName()
					+ cve.getMessage());
         throw new PersistenceException(cve);
		} catch (DataIntegrityViolationException dive) {
		   log.error("Data integrity violation: " + dive.getMostSpecificCause()
		            + " : " + dive.getMessage());
		   throw new PersistenceException(dive);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<P> persistables) throws PersistenceException {
		if (persistables == null) {
         throw new IllegalArgumentException("Persistables cannot be null");
      }
		try {
         for (P p : persistables) {
            save(p);
         }
      } catch (ConstraintViolationException cve) {
         log.error("Constraint error: " + cve.getConstraintName()
                  + cve.getMessage());
         throw new PersistenceException(cve);
		}
	}
	
}
