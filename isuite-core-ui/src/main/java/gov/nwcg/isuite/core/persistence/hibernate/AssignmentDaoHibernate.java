package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 * @author mpoll
 *
 */
public class AssignmentDaoHibernate extends TransactionSupportImpl implements AssignmentDao {

   /**
    * 
    */
   private final CrudDao<Assignment> crudDao;
   @SuppressWarnings("unused")
   private static final Logger LOG = Logger.getLogger(AssignmentDaoHibernate.class);
   
   public AssignmentDaoHibernate(final CrudDao<Assignment> crudDao) {
      if ( crudDao == null ) {
         LOG.error("crudDao was null coming in to constructor.  Check the Spring config.");
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }
   
   public void delete(Assignment persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   public Assignment getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, AssignmentImpl.class);
   }

   public void save(Assignment persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   public void saveAll(Collection<Assignment> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   public Assignment getByUniqueIdentity(String uniqueIdentity, Class clazz) throws PersistenceException, DuplicateItemException {
      throw new UnsupportedOperationException();
   }

   public void updateStatus(Long parentResourceId, String status) throws PersistenceException {
	   String sql = "update isw_assignment set assignment_status = '" + status + "' " +
		   			" where id in ( " +
		   			" 	select assignment_id from isw_work_period_assignment where work_period_id in ( " + 
		            " 		select id from isw_work_period where incident_resource_id in ( " +
		            "          select id from isw_incident_resource where resource_id in ( " + 
		            "  				select id from isw_resource where parent_resource_id = " + parentResourceId + " " +
		            "              ) " +
		            "       ) " +
		            "   ) " +
		            ") " +
		            "and (assignment_status is null "+
		            " or assignment_status not in ('D','R') " +
		            ") " +
		            "and end_date is null "
		            ; 
	   Query q = getHibernateSession().createSQLQuery(sql);
	   q.executeUpdate();
   }
   
   public Long getIncidentResourceId(Long assignmentId) throws PersistenceException {
	   String sql="select ir.id " +
	   			  "from isw_incident_resource ir "+
	   			  "     , isw_work_period wp " +
	   			  "     , isw_work_period_assignment wpa " +
	   			  "where wpa.assignment_id = " + assignmentId + " " +
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
