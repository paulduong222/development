package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.persistence.AssignmentTimeDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;

@SuppressWarnings("unchecked")
public class AssignmentTimeDaoHibernate extends TransactionSupportImpl implements AssignmentTimeDao{

	private final CrudDao<AssignmentTime> crudDao;
	
	public AssignmentTimeDaoHibernate(final CrudDao<AssignmentTime> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.AssignmentTimeDao#getById(java.lang.Long, java.lang.Class)
	 */
	public AssignmentTime getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, AssignmentTimeImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.AssignmentTimeDao#save(gov.nwcg.isuite.core.domain.AssignmentTime)
	 */
	public void save(AssignmentTime persistable) throws PersistenceException {
		
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.AssignmentTimeDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<AssignmentTime> persistables) throws PersistenceException {
		
		for(AssignmentTime persistable : persistables) {
			crudDao.save(persistable);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.AssignmentTimeDao#delete(gov.nwcg.isuite.core.domain.AssignmentTime)
	 */
	public void delete(AssignmentTime persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.AssignmentTimeDao#deleteAll(java.util.Collection)
	 */
	public void deleteAll(Collection<AssignmentTime> persistables) throws PersistenceException {
		
		for(AssignmentTime persistable : persistables) {
			crudDao.delete(persistable);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.AssignmentTimeDao#getByIds(java.util.Collection)
	 */
	public Collection<AssignmentTime> getByIds(Collection<Long> ids) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(AssignmentTimeImpl.class);

		crit.add(Expression.in("id", ids));
		
		return crit.list();
		
	}

	   public Long getIncidentResourceId(Long assignmentTimeId) throws PersistenceException {
		   String sql="select ir.id " +
		   			  "from isw_incident_resource ir "+
		   			  "     , isw_work_period wp " +
		   			  "     , isw_work_period_assignment wpa " +
		   			  "     , isw_assignment_time at " +
		   			  "where at.id = " + assignmentTimeId + " " +
		   			  "and wpa.assignment_id = at.assignment_id "  +
		   			  "and wp.id = wpa.work_period_id " +
		   			  "and ir.id = wp.incident_resource_id ";
		   
		   SQLQuery query = getHibernateSession().createSQLQuery(sql);
		   Object rslt=query.uniqueResult();
		   if(null != rslt){
			   try{
				   Long id=TypeConverter.convertToLong(rslt);
				   return id;
			   }catch(Exception e){
			   }
		   }
		   
		   return 0L;
	   }

}
