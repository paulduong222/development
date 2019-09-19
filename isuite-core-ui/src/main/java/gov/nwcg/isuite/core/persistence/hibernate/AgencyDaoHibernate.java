package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

/**
 * @author mpoll
 *
 */
public class AgencyDaoHibernate extends TransactionSupportImpl implements AgencyDao {

   private final CrudDao<Agency> crudDao;
   
   /**
    * Constructor.
    * @param crudDao can't be null
    * @param transferableDao can't be null
    */
   public AgencyDaoHibernate(final CrudDao<Agency> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(Agency persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public Agency getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, AgencyImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(Agency persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<Agency> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.organization.AgencyDao#getAgencies(gov.nwcg.isuite.domain.organization.AgencyFilter)
    */
   @SuppressWarnings("unchecked") 
   public Collection<AgencyVo> getGrid(AgencyFilter f) throws PersistenceException {
      Collection<Agency> agencies = new ArrayList<Agency>();
      if (f != null) {
         if (f.getEventTypeId() != null) {
            // figure out which agencies to include.
            Query eventTypeQuery = getHibernateSession().createQuery("select et from EventTypeImpl et where et.id = :id");
            eventTypeQuery.setLong("id", f.getEventTypeId());
            EventType et = (EventType) eventTypeQuery.uniqueResult();
            
            // call the appropriate named query. (see AgencyImpl annotations)
            boolean isWf = et.getEventTypeCode().equalsIgnoreCase("WF");
            String name = isWf 
               ? "agency.getWildlandFireAgencies"
               : "agency.getNonWildlandFireAgencies";
            
            
            Query query = getHibernateSession().getNamedQuery(name);
            query.setString("fed", "FED");
            if (isWf) {
               query.setString("federal", "FEDERAL");
            }
            query.setMaxResults(getMaxResultSize());
            //query.setResultTransformer(Transformers.aliasToBean(AgencyVo.class));
            agencies = query.list();
         } 
         else {
            Criteria crit = getHibernateSession().createCriteria(AgencyImpl.class);
//            crit.setProjection(Projections.projectionList()
//                     .add(Projections.property("id"), "id")
//                     .add(Projections.property("agencyCode"), "agencyCd")
//                     .add(Projections.property("agencyName"), "agencyNm")
//                     .add(Projections.property("agencyType"), "theAgencyType")
//                     .add(Projections.property("standard"), "standard")
//                     );
//            crit.createAlias("this.incident", "i", CriteriaSpecification.LEFT_JOIN);
            
            if (f.getAgencyCode() != null && f.getAgencyCode().length() > 0) {
               crit.add(Expression.ilike("agencyCode", f.getAgencyCode(), MatchMode.START));
            }
            if (f.getAgencyName() != null && f.getAgencyName().length() > 0) {
               crit.add(Expression.ilike("agencyName", f.getAgencyName(), MatchMode.START));
            }
            if (f.getAgencyType() != null) {
               crit.add(Expression.eq("agencyType", f.getAgencyType()));
            }
            
//            crit.add(Expression.isNull("i.incidentEndDate"));
            
            crit.setMaxResults(getMaxResultSize());
//            crit.setResultTransformer(Transformers.aliasToBean(AgencyVo.class));
            crit.addOrder(Order.asc("agencyCode"));
            agencies = crit.list();
         }
      }
      
      try {
    	  return AgencyVo.getInstances(agencies, true);
      }catch(Exception e){
    	  throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.organization.AgencyDao#getAgencies(gov.nwcg.isuite.domain.organization.AgencyFilter)
    */
   public Collection<AgencyVo> getAgencies(AgencyFilter f) throws PersistenceException {
      return getGrid(f);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.organization.AgencyDao#getByAgencyCode(java.lang.String)
    */
   public Agency getByAgencyCode(String agencyCode) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(AgencyImpl.class);
	   crit.add(Restrictions.eq("agencyCode", agencyCode));
	   crit.setMaxResults(1);
	   return (Agency)crit.uniqueResult();
   }
   
  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.persistence.AgencyDao#getDuplicateCodeCount(gov.nwcg.isuite.core.vo.AgencyVo)
   */
   public int getDuplicateCodeCount(AgencyVo vo) throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(AgencyImpl.class);
	   
	   crit.add(Expression.eq("agencyCode", vo.getAgencyCd().toUpperCase()));
	   crit.add(Expression.ne("id", vo.getId()));
	   
	   if(vo.getStandard()) {
		   crit.add(Expression.eq("standard", Boolean.TRUE));  
	   } else {
		   if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())) {
			   crit.add(Expression.disjunction()
					   .add(Expression.eq("standard", Boolean.TRUE))
					   .add(Expression.eq("incidentId", vo.getIncidentVo().getId()))
			   			);
		   }
		   
		   if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())) {
			   crit.add(Expression.disjunction()
					   .add(Expression.eq("standard", Boolean.TRUE))
					   .add(Expression.eq("incidentGroupId", vo.getIncidentGroupVo().getId()))
			   			);
		   }
	   }
	   
	   return crit.list().size();
   }

	public Collection<AgencyVo> getIncidentGroupAgencyDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException {
		Collection<AgencyVo> vos = new ArrayList<AgencyVo>();

		StringBuffer sql = new StringBuffer()
			.append("select distinct(ag1.agency_code) as agencyCd ")
			.append("      ,ag1.agency_name as agencyNm ")
			.append("      ,inc1.incident_name as tempString1 ")
			.append("from iswl_agency ag1 ")
			.append("	  ,iswl_agency ag2 ")
			.append("     ,isw_incident inc1 ")
			.append("where ag1.agency_code = ag2.agency_code ")
			.append("and ag1.incident_id is not null ")
			.append("and ag2.incident_id is not null ")
			.append("and ag1.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and ag2.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and ag1.incident_id != ag2.incident_id ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      ag1.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      ag1.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      ag2.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      ag2.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and inc1.id = ag1.incident_id ")
			.append("order by ag1.agency_code, inc1.incident_name ")
			;

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		query.setParameterList("incidentIds", incidentIds);
		
		CustomResultTransformer rt = new CustomResultTransformer(AgencyVo.class);
		
		query.setResultTransformer(rt);
		
		vos = query.list();
		
		return vos;
	}
	
	public Collection<AgencyVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException {
	      Criteria crit = getHibernateSession().createCriteria(Agency.class);
		   if(CollectionUtility.hasValue(incidentIds)) {
			   crit.add(Expression.disjunction()
					   .add(Expression.eq("standard", Boolean.TRUE))
					   .add(Expression.in("incidentId", incidentIds))
			   );
		   } else if(LongUtility.hasValue(incidentGroupId)) {
			   crit.add(Expression.disjunction()
					   .add(Expression.eq("standard", Boolean.TRUE))
					   .add(Expression.eq("incidentGroupId", incidentGroupId))
			   			);
		   }
	      		
	      crit.addOrder(Order.asc("this.agencyCode"));
	      
	      Collection<Agency> entities = crit.list();
	      Collection<AgencyVo> vos = new ArrayList<AgencyVo>();
	      
	      try{
	          vos=AgencyVo.getInstances(entities, true);
	      }catch(Exception e){
	    	  throw new PersistenceException(e);
	      }
	      
	      return vos;		   

	}

	public Collection<AgencyVo> getByIncidentId(Long incidentId) throws PersistenceException {
	      Criteria crit = getHibernateSession().createCriteria(Agency.class);
	      crit.add(Expression.eq("incidentId", incidentId));
	      		
	      crit.addOrder(Order.asc("this.agencyCode"));
	      
	      Collection<Agency> entities = crit.list();
	      Collection<AgencyVo> vos = new ArrayList<AgencyVo>();
	      
	      try{
	          vos=AgencyVo.getInstances(entities, true);
	      }catch(Exception e){
	    	  throw new PersistenceException(e);
	      }
	      
	      return vos;		   

	}
		   
	
   
}
