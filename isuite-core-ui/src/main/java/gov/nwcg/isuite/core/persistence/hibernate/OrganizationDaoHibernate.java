package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.OrganizationPicklistVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 * Dao for organization
 * @author mpaskett
 */
public class OrganizationDaoHibernate extends TransactionSupportImpl implements OrganizationDao {
   
   private static final Logger LOG = Logger.getLogger(OrganizationDaoHibernate.class);
   private final CrudDao<Organization>         crudDao;

   /**
    * Constructor.
    * 
    * @param crudDao can't be null
    */
   public OrganizationDaoHibernate(final CrudDao<Organization> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;

   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.OrganizationDao#getAllBreadthFirst()
    */
   public Collection<Organization> getAllBreadthFirst() throws PersistenceException {
      return new ArrayList<Organization>();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.OrganizationDao#getAllDepthFirst()
    */
   public Collection<Organization> getAllDepthFirst() throws PersistenceException {
      return new ArrayList<Organization>();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(Organization persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(java.lang.Class)
    */
   public Collection<Organization> getAll(Class clazz) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.OrganizationDao#getAll(gov.nwcg.isuite.core.filter.OrganizationFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<Organization> getAll(OrganizationFilter f) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(OrganizationImpl.class);
      crit.setFetchMode("dispatchCenters",FetchMode.JOIN);
      
      crit.createAlias("this.incident", "i", CriteriaSpecification.LEFT_JOIN);
      
//      crit.add(Expression.isNull("i.incidentEndDate"));
//      crit.setResultTransformer(arg0)
      if (f != null) {
         if (f.getOrganizationType() != null) {
            crit.add(Expression.eq("organizationType", f.getOrganizationType()));
         }
         if (f.getOrganizationAbbreviation() != null && f.getOrganizationAbbreviation().length() > 0) {
            crit.add(Expression.ilike("abbreviation", f.getOrganizationAbbreviation(), MatchMode.START));
         }
         if (f.getUnitCode() != null && f.getUnitCode().length() > 0) {
            crit.add(Expression.ilike("unitCode", f.getUnitCode(), MatchMode.START));
         }
         if (f.getOrganizationName() != null && f.getOrganizationName().length() > 0) {
            crit.add(Expression.ilike("name", f.getOrganizationName(), MatchMode.START));
         }
         if (f.getCountrySubAbbreviation() != null && f.getCountrySubAbbreviation().length() > 0) {
            crit.add(Expression.eq("countrySubAbbreviation", f.getCountrySubAbbreviation()));
         }
      }
//      crit.setMaxResults(getMaxResultSize());
      crit.addOrder(Order.asc("unitCode"));
      
      crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      
      
      return crit.list();
   }
   
   
   
   @SuppressWarnings("unchecked")
   public Collection<Organization> getByWorkAreaId(Long workAreaId) throws PersistenceException {
	   if (null == workAreaId) {
		   throw new PersistenceException("workAreaId must be populated.");
	   }
	  Criteria crit = getHibernateSession().createCriteria(WorkAreaImpl.class, "wa");
	  crit.createAlias("filteredOrganizations", "orgs");
	  crit.setProjection(Projections.projectionList()
              .add( Projections.property("orgs.name"), "name")
              .add( Projections.property("orgs.unitCode"), "unitCode")
              .add( Projections.property("orgs.managingOrganization"), "managingOrganization")
              .add( Projections.property("orgs.managingOrganizationId"), "managingOrganizationId")
              .add( Projections.property("orgs.organizationType"), "organizationType")
              .add( Projections.property("orgs.agency"), "agency")
              .add( Projections.property("orgs.agencyId"), "agencyId")
              .add( Projections.property("orgs.countrySubAbbreviation"), "countrySubAbbreviation")
              .add( Projections.property("orgs.id"), "id")
              .add( Projections.property("orgs.dispatchCenter"), "dispatchCenter")
              .add( Projections.property("orgs.users"), "users")
              .add( Projections.property("orgs.workAreas"), "workAreas")
              .add( Projections.property("orgs.dispatchCenters"), "dispatchCenters")
              );	  
      if (workAreaId != null) {
            crit.add(Expression.eq("wa.id", workAreaId));
      }
      crit.addOrder(Order.asc("orgs.unitCode"));
	  crit.setResultTransformer(Transformers.aliasToBean(OrganizationImpl.class));
      return crit.list();
   }
   
   
   
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.organization.OrganizationDao#getOrganizationPickList(java.lang.String)
    */
   @SuppressWarnings("unchecked")
   public Collection<OrganizationPicklistVo> getOrganizationPickList(String unitCode, String name) throws PersistenceException {
      LOG.debug("*** Entering: OrganizationDaoHibernate.getOrganizationPickList(unitCode)");
      
      Criteria c = getHibernateSession().createCriteria(OrganizationImpl.class);
      c.setProjection(Projections.projectionList()
               .add( Projections.property("name"), "orgName")
               .add( Projections.property("unitCode"), "unitCd")
               .add( Projections.property("id"), "id")
               );
      
      c.add(Expression.ilike("unitCode", unitCode, MatchMode.START));
      c.add(Expression.ilike("name", name, MatchMode.START));
      c.setResultTransformer(Transformers.aliasToBean(OrganizationPicklistVo.class));
      c.setMaxResults(getMaxResultSize());
      return c.list();
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public Organization getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, OrganizationImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(Organization persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<Organization> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.organization.OrganizationDao#getAllByIds(java.util.Collection)
    */
   @SuppressWarnings("unchecked")
   public Collection<Organization> getAllByIds(Collection<Long> ids) throws PersistenceException {
      Collection<Organization> orgs;
      if (ids != null && ids.size() > 0) {
         Criteria crit = (Criteria) getHibernateSession().createCriteria(Organization.class);
         crit.add(Expression.in("id", ids));
         orgs = crit.list();
      }
      else {
         orgs = new ArrayList<Organization>();
      }
      return orgs;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.organization.OrganizationDao#getByOrganizationCode(java.lang.String)
    */
   public Organization getByOrganizationCode(String organizationCode) throws PersistenceException {
      //TODO:  change to named query. -dbudge
      Query query = getHibernateSession().createQuery("select o from OrganizationImpl o where o.unitCode = :unitCd");
      query.setString("unitCd", organizationCode);
      return (Organization)query.uniqueResult();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.organization.OrganizationDao#getPrimaryDispatchCenter(java.lang.Long)
    */
   public Organization getPrimaryDispatchCenter(Long organizationId) throws PersistenceException{
      Query query = getHibernateSession().createQuery("select o from OrganizationImpl o where o.id = (select i.managingOrganizationId from OrganizationImpl i where i.id=:id)");
      query.setLong("id", organizationId);
      Organization o = (Organization)query.uniqueResult();
      if (null == o) {
         return getById(organizationId, OrganizationImpl.class);
      }
      return o;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.OrganizationDao#getAllOrganizationsByUserId(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<OrganizationPicklistVo> getAllOrganizationsByUserId(Long userId) throws PersistenceException {
	   if (null == userId) {
		   throw new PersistenceException("userId must be populated.");
	   }
	   Criteria crit = getHibernateSession().createCriteria(UserImpl.class, "u");
	   crit.createAlias("organizations", "orgs");
	   crit.setProjection(Projections.projectionList()
			   .add(Projections.property("orgs.id"), "id")
			   .add(Projections.property("orgs.name"), "orgName")
			   );
	   
	   crit.add(Expression.eq("u.id", userId));
	   crit.setResultTransformer(Transformers.aliasToBean(OrganizationPicklistVo.class));
	   crit.setMaxResults(super.getMaxResultSize());
	   return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.OrganizationDao#getDispatchCenters()
    */
   @SuppressWarnings("unchecked")
   public Collection<OrganizationVo> getDispatchCenters() throws PersistenceException {
      //TODO:  Change to named query. -dbudge
      String hql = "select id as id, " +
      		"name as name, " +
      		"unitCode as unitCode " +
      		"from OrganizationImpl " +
      		"where dispatchCenter = true";//Only return orgs that are dispatch centers.
      Query dispatchCentersQuery = getHibernateSession().createQuery(hql);
      dispatchCentersQuery.setResultTransformer(Transformers.aliasToBean(OrganizationVo.class));
      return dispatchCentersQuery.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.OrganizationDao#getDuplicateCodeCount(gov.nwcg.isuite.core.vo.OrganizationVo)
    */
   public int getDuplicateCodeCount(OrganizationVo vo) throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(OrganizationImpl.class);
	   
	   crit.add(Expression.eq("unitCode", vo.getUnitCode().toUpperCase()));
	   crit.add(Expression.ne("id", vo.getId()));
	   crit.add(Expression.eq("dispatchCenter", Boolean.FALSE));
	   
	   if(vo.getStandard()) {
		   crit.add(Expression.eq("standard", Boolean.TRUE));
	   }else{
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

	public Collection<OrganizationVo> getIncidentGroupOrganizationDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException {
		Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();

		StringBuffer sql = new StringBuffer()
			.append("select distinct(o1.unit_code) as unitCode ")
			.append("      ,o1.organization_name as name ")
			.append("      ,inc1.incident_name as tempString1 ")
			.append("from isw_organization o1 ")
			.append("	  ,isw_organization o2 ")
			.append("     ,isw_incident inc1 ")
			.append("where o1.unit_code = o2.unit_code ")
			.append("and o1.incident_id is not null ")
			.append("and o2.incident_id is not null ")
			.append("and o1.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and o2.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and o1.incident_id != o2.incident_id ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      o1.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      o1.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      o2.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      o2.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and inc1.id = o1.incident_id ")
			.append("order by o1.unit_code, inc1.incident_name ")
			;

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		query.setParameterList("incidentIds", incidentIds);
		
		CustomResultTransformer rt = new CustomResultTransformer(OrganizationVo.class);
		
		query.setResultTransformer(rt);
		
		vos = query.list();
		
		return vos;
	}

	public String getUnitCodeByTi(String ti) throws PersistenceException {
		String unitCode="";
		
		String sql = "select unit_code from isw_organization where transferable_identity='"+ti+"' ";
		SQLQuery query=getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				unitCode=TypeConverter.convertToString(rslt);
			}catch(Exception e){}
		}
		
		return unitCode;
	}

	public Long getOrgIdByTi(String ti) throws PersistenceException {
		Long orgId=0L;
		
		String sql = "select id from isw_organization where transferable_identity='"+ti+"' ";
		SQLQuery query=getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				orgId=TypeConverter.convertToLong(rslt);
			}catch(Exception e){}
		}
		
		return orgId;
	}
	
	public Collection<OrganizationVo> getByIncidentId(Long incidentId) throws PersistenceException {
	      Criteria crit = getHibernateSession().createCriteria(Organization.class);
	      crit.add(Expression.eq("incidentId", incidentId));
	      		
	      crit.addOrder(Order.asc("this.unitCode"));
	      
	      Collection<Organization> entities = crit.list();
	      Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();
	      
	      try{
	          vos=OrganizationVo.getInstances(entities, true);
	      }catch(Exception e){
	    	  throw new PersistenceException(e);
	      }
	      
	      return vos;		   
		
	}

	public Collection<OrganizationVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException {
	      Criteria crit = getHibernateSession().createCriteria(Organization.class);
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
	      		
	      crit.addOrder(Order.asc("this.unitCode"));
	      
	      Collection<Organization> entities = crit.list();
	      Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();
	      
	      try{
	          vos=OrganizationVo.getInstances(entities, true);
	      }catch(Exception e){
	    	  throw new PersistenceException(e);
	      }
	      
	      return vos;		   

	}
	
}
