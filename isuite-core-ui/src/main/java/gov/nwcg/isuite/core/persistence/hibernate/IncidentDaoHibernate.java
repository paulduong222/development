package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IapDefaultSettingsQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentUserQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.WorkPeriodQuestValueQuery;
import gov.nwcg.isuite.core.vo.GenericVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentPicklistVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * Dao for Incidents using hibernate.
 * @author mpoll
 *
 */
@SuppressWarnings("unchecked")
public class IncidentDaoHibernate extends TransactionSupportImpl implements IncidentDao {

	private final CrudDao<Incident> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao
	 *           can't be null
	 */
	public IncidentDaoHibernate(final CrudDao<Incident> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#delete(java.lang.Long)
	 */
	public void delete(Long id) throws PersistenceException {
		Incident incident = getById(id);
		if (incident == null) {
			throw new PersistenceException("unable to locate object with id: " + id);
		}
		this.delete(incident);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#delete(gov.nwcg.isuite.domain.incident.Incident)
	 */
	public void delete(Incident incident) throws PersistenceException {
		if (incident == null) {
			throw new PersistenceException("incident can not be null");
		}
		crudDao.delete(incident);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#getById(java.lang.Long)
	 */
	public Incident getById(Long id) throws PersistenceException {
		return this.getById(id, IncidentImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Incident getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(Incident incident) throws PersistenceException {
		if (incident == null) {
			throw new PersistenceException("Incoming object cannot be null.");
		}

		crudDao.save(incident);
	}

	public void merge(Incident entity) throws PersistenceException {
		super.getHibernateTemplate().merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Incident> incident) throws PersistenceException {
		if (incident == null) {
			throw new PersistenceException("Incoming collection cannot be null.");
		}
		crudDao.saveAll(incident);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#getPicklist(gov.nwcg.isuite.domain.incident.IncidentFilter)
	 */
	public Collection<IncidentPicklistVo> getIncidentPickList(IncidentFilter f) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.createAlias("countrySubdivision", "state");
		crit.createAlias("homeUnit", "org");
		crit.createAlias("eventType", "et");
		crit.createAlias("agency", "agency");
		crit.setProjection( Projections.projectionList()
				.add( Projections.property("incidentName"), "name")
				.add( Projections.property("id"), "id")
				.add( Projections.property("eventTypeId"), "eventTypeId")
		);
		crit.setResultTransformer( Transformers.aliasToBean(IncidentPicklistVo.class));

		if (f != null) {
			if (f.getIncidentName() != null && !"".equals(f.getIncidentName()))
			{
				crit.add(Restrictions.ilike("incidentName", f.getIncidentName(), MatchMode.START));
			}
			if (f.getEventType() != null && f.getEventType().trim().length() > 0)
			{
				crit.add(Expression.ilike("et.eventType", f.getEventType(), MatchMode.START));
			}
			if (f.getCountrySubdivision() != null && f.getCountrySubdivision().trim().length() > 0)
			{
				crit.add(Expression.ilike("state.abbreviation", f.getCountrySubdivision(), MatchMode.START));
			}

			if(f.getHomeUnit() != null && f.getHomeUnit().trim().length() > 0) {
				crit.add(Expression.ilike("org.unitCode", f.getHomeUnit(), MatchMode.START));
			}

			if (f.getWorkAreaId() != null && f.getWorkAreaId() > 0) {
				crit.add(Expression.eq("org.id", f.getWorkAreaId()));
			}

			if(f.getIncidentNumber() != null && f.getIncidentNumber().trim().length() > 0) {
				crit.add(Expression.ilike("nbr", f.getIncidentNumber(), MatchMode.START));
			}

			if(f.getAgency() != null 
					&& f.getAgency().trim().length() > 0) {
				crit.add(Expression.ilike("agency.agencyCode", f.getAgency()
						, MatchMode.START));
			}

			if(f.getIncidentStartDate() != null) {
				crit.add(Expression.ge("incidentStartDate", f.getIncidentStartDate()));
			}
		}

		crit.setMaxResults(super.getMaxResultSize());
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#getAllIncidents(gov.nwcg.isuite.domain.incident.IncidentFilter)
	 */
	public Collection<IncidentGridVo> getGrid(IncidentFilter f, Boolean privilegedUser) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);

		crit.createAlias("countrySubdivision", "state", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("homeUnit", "org");
		crit.createAlias("eventType", "et");
		crit.createAlias("agency", "agency");
		crit.createAlias("restrictedIncidentUsers", "riu", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("workAreas", "wa");
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		if (f != null) {
			if(f.getId() != null){
				crit.add(Restrictions.eq("this.id", f.getId()));
			}
			if(f.getWorkAreaId() != null && f.getWorkAreaId() > 0){
				crit.add(Restrictions.eq("wa.id", f.getWorkAreaId()));
			}
			if (f.getIncidentName() != null && !"".equals(f.getIncidentName())) 
			{
				crit.add(Restrictions.ilike("this.incidentName", f.getIncidentName().toUpperCase(), MatchMode.START));
			}
			if (f.getEventType() != null && f.getEventType().trim().length() > 0)
			{
				crit.add(Expression.ilike("et.eventType", f.getEventType().toUpperCase(), MatchMode.START));
			}
			if (f.getCountrySubdivision() != null && f.getCountrySubdivision().trim().length() > 0)
			{
				crit.add(Expression.ilike("state.abbreviation", f.getCountrySubdivision().toUpperCase(), MatchMode.START));
			}

			if(f.getHomeUnit() != null && f.getHomeUnit().trim().length() > 0) {
				crit.add(Expression.ilike("org.unitCode", f.getHomeUnit().toUpperCase(), MatchMode.START));
			}

//			if(f.getIncidentNumber() != null && f.getIncidentNumber().trim().length() > 0) {
//				// the grid displays US-UT-UTUSO-000011, configure filter
//				// to try and get best matches
//				crit.add(Restrictions.disjunction()
//						//.add(Restrictions.ilike("state.abbreviation", f.getIncidentNumber().trim().toUpperCase(),MatchMode.START))
//						.add(Restrictions.ilike("org.unitCode", f.getIncidentNumber().trim().toUpperCase(),MatchMode.START))
//						.add(Restrictions.ilike("this.nbr", f.getIncidentNumber().trim().toUpperCase(),MatchMode.ANYWHERE))
//						);
//			}

			if(f.getDefaultAccountingCode() != null && f.getDefaultAccountingCode().length()>0){
				crit.createAlias("incidentAccountCodes", "iac");
				crit.createAlias("iac.accountCode", "ac");
				crit.add(Expression.eq("iac.defaultFlag", Boolean.TRUE));
				crit.add(Expression.ilike("ac.accountCode", f.getDefaultAccountingCode().toUpperCase(), MatchMode.START));
			}

			if(f.getAgency() != null 
					&& f.getAgency().trim().length() > 0) {
				crit.add(Expression.ilike("agency.agencyCode", f.getAgency().toUpperCase()
						, MatchMode.START));
			}

			if(f.getIncidentStartDate() != null) {
//				crit.add(Expression.ge("this.incidentStartDate", f.getIncidentStartDate()));
				crit.add(Expression.eq("this.incidentStartDate", f.getIncidentStartDate()));
			} else if(StringUtility.hasValue(f.getStartDateString())) {
				try {
					super.applyCrypticDateFilter(crit, f.getStartDateString(), "this_.incident_start_date");
				} catch (Exception e) {
					throw new PersistenceException(e);
				}
			}
			
			if(f.getDeletable() != null) {
			   if(f.getDeletable() == true) {
			      //TODO:  Add deletable criteria. -dbudge
			   } else {
			      //TODO:  Add non-deletable criteria. -dbudge
			      return null;
			   }
			}
			
		}

		//crit.addOrder(Order.asc("state.abbreviation"));
		crit.addOrder(Order.asc("org.unitCode"));
		crit.addOrder(Order.asc("nbr"));
		crit.setMaxResults(super.getMaxResultSize());
		
		Collection<Incident> entities = crit.list();

		Collection<IncidentGridVo> vos = null;
		try{
			vos=IncidentGridVo.getInstances(entities,f.getCurrentUserId(), privilegedUser);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		Collection<IncidentGridVo> filteredVos = IncidentFilterImpl.filterRestrStatus(vos, f);

		/*
		if((f.getIncidentRestrictedStatuses() != null) && (f.getIncidentRestrictedStatuses().size() > 0)) {
			for(IncidentGridVo incidentGridVo : vos) {
				for(IncidentRestrictedStatusVo vo : f.getIncidentRestrictedStatuses()) {
					if (vo.getName().equalsIgnoreCase(incidentGridVo.getIncidentRestrictedStatusType().toString())) {
						filteredVos.add(incidentGridVo);
					}
				}
			}
		} else {
			filteredVos = vos;
		}
		*/
		
		return filteredVos;

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#getAllByIds(java.util.Collection)
	 */
	public Collection<Incident> getAllByIds(Collection<Long> ids) throws PersistenceException {
		//TODO:  Rename this method to getIncidentsByIncidentIds or getIncidentsByIds. -dbudge
	   Collection<Incident> incidents;
		if (ids != null && ids.size() > 0) {
			Criteria crit = (Criteria) getHibernateSession().createCriteria(IncidentImpl.class);
			crit.add(Expression.in("id", ids));
			incidents = crit.list();
		}
		else {
			incidents = new ArrayList<Incident>();
		}

		return incidents;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#getByIncidentName(java.lang.String)
	 */
	public Incident getByIncidentName(String incidentName) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.add(Restrictions.eq("incidentName", incidentName));
		crit.setMaxResults(1);
		return (Incident)crit.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentDao#getByNbrAndIncidentYear(gov.nwcg.isuite.core.vo.IncidentVo, java.lang.Integer)
	 */
	public Incident getByNbrAndIncidentYear(IncidentVo vo, Integer incidentYear) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.add(Restrictions.eq("nbr", vo.getIncidentNumber().toUpperCase().trim()));
		//crit.add(Restrictions.eq("countrySubdivisionId", vo.getCountryCodeSubdivisionVo().getId()));
		//crit.add(Restrictions.eq("homeUnitId", vo.getHomeUnitVo().getId() ));
		crit.add(Restrictions.eq("incidentYear", incidentYear));
		crit.setMaxResults(1);
		return (Incident)crit.uniqueResult();
	}

	public Incident getByIncNbrAndIncidentYear(IncidentVo vo, Integer incidentYear) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.add(Restrictions.eq("homeUnitId", vo.getHomeUnitVo().getId()));
		crit.add(Restrictions.eq("nbr", vo.getIncidentNumber().toUpperCase().trim()));
		//crit.add(Restrictions.eq("countrySubdivisionId", vo.getCountryCodeSubdivisionVo().getId()));
		//crit.add(Restrictions.eq("homeUnitId", vo.getHomeUnitVo().getId() ));
		crit.add(Restrictions.eq("incidentYear", incidentYear));
		crit.setMaxResults(1);
		return (Incident)crit.uniqueResult();
	}
	
	public Collection<Incident> getByIncNbrAndIncidentYear(Long homeUnitId, String incNumber,Integer incidentYear) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.add(Restrictions.eq("homeUnitId", homeUnitId));
		crit.add(Restrictions.eq("nbr", incNumber.toUpperCase().trim()));
		//crit.add(Restrictions.eq("countrySubdivisionId", vo.getCountryCodeSubdivisionVo().getId()));
		//crit.add(Restrictions.eq("homeUnitId", vo.getHomeUnitVo().getId() ));
		crit.add(Restrictions.eq("incidentYear", incidentYear));

		Collection<Incident> incidents=crit.list();
		return incidents;
	}

	/**
	    * Returns list of permission keys that the shared user has authorizations to execute.
	    * 
	    * @param incidentId
	    * 			the incident id
	    * @param userId
	    * 			the user id
	    * @return
	    * 			collection of permission keys
	    * @throws PersistenceException
	    */
	  public Collection<String> getPermissionsForSharedUser(Long incidentId, Long userId) throws PersistenceException {
		  StringBuffer sql = new StringBuffer()
		   	.append("SELECT DISTINCT(mp.permission_key) FROM ")
	        .append("ISW_SYSTEM_MODULE_PERM MP, ")
	        .append("ISW_SYSTEM_ROLE_PERM SRP,  ")
	        .append("ISW_RESTRICTED_INCIDENT_USER RIU,  ")
	        .append("ISW_REST_INC_USER_ROLE RIUR  ")
	        .append("WHERE RIUR.RESTRICTED_INCIDENT_USER_ID = RIU.ID  ")
	        .append("AND ( ")
	        .append("SRP.ROLE_ID = RIUR.ROLE_ID  ")
	        .append(")  ")
	        .append("AND MP.ID = SRP.MODULE_PERM_ID  ")
	        .append("AND RIU.INCIDENT_ID = " + incidentId + " ")
	        .append("AND RIU.USER_ID = " + userId + " ") 
		  	.append("AND SRP.ROLE_FLAG = " + super.getBooleanComparison(true));
		  	
		  SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		  Collection<String> list = q.list();

		  return list;
	   }
	
	  public Collection<IncidentVo> getPossibleRossMatches(IncidentFilter filter) throws PersistenceException {
		  
			Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
			crit.setFetchMode("homeUnit",FetchMode.JOIN);
			crit.createAlias("homeUnit", "org");
			
			if(null != filter){
				if(LongUtility.hasValue(filter.getRossIncId())){
					crit.add(Restrictions.eq("rossIncidentId", String.valueOf(filter.getRossIncId())));
				}
				if(StringUtility.hasValue(filter.getIncidentName())){
					crit.add(Restrictions.ilike("incidentName", filter.getIncidentName().toUpperCase().trim()));
				}
				if(IntegerUtility.hasValue(filter.getIncidentYear())){
					crit.add(Restrictions.eq("incidentYear", filter.getIncidentYear()));
				}
				if(StringUtility.hasValue(filter.getIncidentTagNumber())){
					String sql = "(org1_.unit_code || '-' || this_.nbr ) = '"+ filter.getIncidentTagNumber() +"'";
					crit.add(Restrictions.sqlRestriction(sql));
				}
				
				if(StringUtility.hasValue(filter.getIncidentNumber())){
					//crit.add(Restrictions.ilike("nbr", filter.getIncidentNumber().toUpperCase().trim()));
					crit.add(Restrictions.ilike("nbr", filter.getIncidentNumber().toUpperCase().trim(),MatchMode.END));
				}
				if(StringUtility.hasValue(filter.getCountrySubdivision())){
					crit.add(Restrictions.sqlRestriction(""));
				}
				if(null != filter.getIncidentStartDate()){
					crit.add(Restrictions.sqlRestriction(""));
				}
			}
			
			crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		    
			Collection<Incident> entities = crit.list();
			
			try{
				if(null != entities)
					return IncidentVo.getInstances(entities, true);
				else
					return new ArrayList<IncidentVo>();
			}catch(Exception e){
				throw new PersistenceException(e);
			}
	  }
	  
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentDao#getByIncidentKey(java.lang.String, java.lang.String)
	 */
	public Collection<Incident> getByIncidentKey(String nbr, String name) throws PersistenceException{
		
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		
		crit.add(Restrictions.eq("nbr", nbr));
		crit.add(Restrictions.ilike("incidentName", name,MatchMode.START));
		
		return crit.list();
    }

	@Override
	public int checkForTimePostings(Long incidentId) throws PersistenceException, Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(atp.id) ") 
		.append("from isw_incident_resource ir ")
		       .append(",isw_work_period wp ")
		       .append(",isw_work_period_assignment wpa ")
		       .append(",isw_assignment a ")
		       .append(",isw_assignment_time at ")
		       .append(",isw_assign_time_post atp ")
		.append("where ir.incident_id = :incidentid ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and at.assignment_id = a.id ")
		.append("and atp.assignment_time_id = at.id ");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sb.toString());

		if(isOracleDialect())
			query.setLong("incidentid", incidentId);
		else
			query.setInteger("incidentid", TypeConverter.convertToInteger(incidentId));
			
		Object rslt = query.uniqueResult();

		int cnt = 0;
		if(null != rslt)
			cnt = TypeConverter.convertToInteger(rslt).intValue();
				
		return cnt;
	}

	public void deleteFromUserCustomView(Long incidentId) throws PersistenceException {
		
		String sql = "DELETE FROM ISW_USER_INCVIEW_EXCLUDE WHERE incident_id = " + incidentId + " ";
        SQLQuery q = getHibernateSession().createSQLQuery(sql);
        q.executeUpdate();
		
	}

	public void executeInsertIncidentUser(Long incidentId) throws PersistenceException,Exception{
		int cnt=0;
		
		String sql = IncidentUserQuery.getInsertIncidentUserCount(incidentId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt)
			cnt=TypeConverter.convertToLong(rslt).intValue();

		if(cnt>0){
			sql = IncidentUserQuery.getInsertIncidentUser(incidentId, super.isOracleDialect());
			query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
		
	}
	
	public void executeInsertIncidentNewUser(Long userId) throws PersistenceException,Exception{
		int cnt=0;
		
		String sql = IncidentUserQuery.getInsertIncidentNewUserCount(userId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt)
			cnt=TypeConverter.convertToLong(rslt).intValue();

		if(cnt>0){
			sql = IncidentUserQuery.getInsertIncidentNewUser(userId, super.isOracleDialect());
			query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
		
	}
	
	public Collection<Incident> getByRossIncidentId(Long id) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.add(Restrictions.eq("rossIncidentId", String.valueOf(id)));
		
		return crit.list();
	}
	
	public Collection<Incident> getAutoCostRunIncidents() throws PersistenceException {
		Collection<Incident> entities = new ArrayList<Incident>();
		
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		String sql = "this_.incident_end_date is null and cost_autorun = 'Y' ";
		crit.add(Expression.sqlRestriction(sql));
		
		entities = crit.list();
		
		return entities;
	}
	
	public Collection<Incident> getAllActiveIncidents() throws PersistenceException {
		Collection<Incident> entities = new ArrayList<Incident>();
		
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		String sql = "this_.incident_end_date is null ";
		crit.add(Expression.sqlRestriction(sql));
		
		entities = crit.list();
		
		return entities;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentDao#deleteCostRecords(java.lang.Long)
	 */
	public void deleteCostRecords(Long incidentId) throws PersistenceException {
		String sql = "DELETE FROM ISW_INC_RES_DAILY_COST WHERE INCIDENT_RESOURCE_ID IN (" +
					 "SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE INCIDENT_ID = " + incidentId + ") ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
		sql = "DELETE FROM ISW_INC_RES_DAILY_COST WHERE INCIDENT_RESOURCE_OTHER_ID IN (" +
		 "SELECT ID FROM ISW_INCIDENT_RESOURCE_OTHER WHERE INCIDENT_ID = " + incidentId + ") ";

		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentDao#preDeleteIncident(java.lang.Long)
	 */
	public void preDeleteIncident(Long incidentId) throws PersistenceException {
		String sql = "DELETE FROM ISW_INC_RES_DAILY_COST WHERE INCIDENT_RESOURCE_ID IN (" +
					 "SELECT ID FROM ISW_INCIDENT_RESOURCE WHERE INCIDENT_ID = " + incidentId + ") ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		sql = "DELETE FROM ISW_INC_RES_DAILY_COST WHERE INCIDENT_RESOURCE_OTHER_ID IN (" +
		 "SELECT ID FROM ISW_INCIDENT_RESOURCE_OTHER WHERE INCIDENT_ID = " + incidentId + ") ";

		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		sql = "DELETE FROM ISW_IAP_POSITION_ITEM_CODE WHERE INCIDENT_ID = " + incidentId + " " ;

		query = getHibernateSession().createSQLQuery(sql);
		//query.executeUpdate();

		sql="delete from isw_wp_overnight_stay_info " +
			"where work_period_id in ("+
			"   select id from isw_work_period "+
			"   where incident_resource_id in ("+
			"       select id from isw_incident_resource " +
			"       where incident_id = " + incidentId + " " +
			"   )" +
			")";
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();

		sql="delete from isw_resource_training " +
			"where incident_resource_id in ("+
			"   select id from isw_incident_resource "+
			"   where incident_id = " + incidentId + " " +
			")";
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		sql="delete from iswl_priority_program where incident_id = " + incidentId;
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();

		sql="delete from isw_training_set_fuel_type where training_settings_id in (select id from isw_training_settings where incident_id = " + incidentId + ")";
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		sql="delete from isw_training_settings where incident_id = " + incidentId;
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public Collection<IncidentGridVo> getIncidentsForUser(Long userId) throws PersistenceException {
		Collection<Incident> entities = new ArrayList<Incident>();

		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		String sql = "this_.id in (select incident_id from isw_restricted_incident_user where user_id = " + userId + " ) ";
		crit.addOrder(Order.asc("incidentName"));
		crit.add(Expression.sqlRestriction(sql));
		
		entities = crit.list();

		Collection<IncidentGridVo> vos = new ArrayList<IncidentGridVo>();
		
		if(CollectionUtility.hasValue(entities)){
			for(Incident entity : entities){
				IncidentGridVo vo = new IncidentGridVo();
				vo.setId(entity.getId());
				vo.setIncidentName(entity.getIncidentName());
				vo.setIncidentNumber(entity.getIncidentNumber());
				if(null != entity.getHomeUnit())
					vo.setUnitCode(entity.getHomeUnit().getUnitCode());
				if(CollectionUtility.hasValue(entity.getIncidentGroups())){
					IncidentGroup ig = (IncidentGroup)entity.getIncidentGroups().iterator().next();
					if(null != ig){
						IncidentGroupVo igVo = new IncidentGroupVo();
						igVo.setId(ig.getId());
						igVo.setGroupName(ig.getGroupName());
						
						vo.setIncidentGroupVo(igVo);
					}
				}
				
				vos.add(vo);
			}
		}
		
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentDao#createDefaultIapSettings(java.lang.Long)
	 */
	public void createDefaultIapSettings(Long incidentId) throws PersistenceException {

		String sql1 = "SELECT COUNT(*) FROM ISW_IAP_POSITION_ITEM_CODE WHERE INCIDENT_ID = " + incidentId + " ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql1);
		Object rslt=q.uniqueResult();
		int cnt=0;
		if(null != rslt){
			try{
				cnt=TypeConverter.convertToInt(rslt);
			}catch(Exception e){}
		}
		
		if(cnt==0){
			String sequence = (super.isOracleDialect()==true ? "SEQ_IAP_POSITION_ITEM_CODE.nextVal" : "nextVal('SEQ_IAP_POSITION_ITEM_CODE')");
			
			for(IapDefaultSettingsQuery._203Settings _203Enum : IapDefaultSettingsQuery._203Settings.values()){
				String sql = _203Enum.sql;
				sql=sql.replaceAll(":fieldName", "INCIDENT_ID");
				sql=sql.replaceAll(":seqId", sequence);
				sql=sql.replaceAll(":incidentId", String.valueOf(incidentId));
				
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				
				query.executeUpdate();
				
			}
			
			for(IapDefaultSettingsQuery._204Settings _204Enum : IapDefaultSettingsQuery._204Settings.values()){
				String sql = _204Enum.sql;
				sql=sql.replaceAll(":fieldName", "INCIDENT_ID");
				sql=sql.replaceAll(":seqId", sequence);
				sql=sql.replaceAll(":incidentId", String.valueOf(incidentId));
				
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				
				query.executeUpdate();
				
			}
			
		}
	}

	public Long getIncidentGroupId(Long incidentId) throws PersistenceException {

		String sql1 = "SELECT incident_group_id FROM isw_incident_group_incident where incident_id = " + incidentId+" " ;
		SQLQuery q = getHibernateSession().createSQLQuery(sql1);
		Object rslt=q.uniqueResult();
		Long groupId=0L;
		if(null != rslt){
			try{
				groupId=TypeConverter.convertToLong(rslt);
			}catch(Exception e){}
		}

		return groupId;
	}
	
	public void restoreDefault203TemplateSettings(Long incidentId, String sectionCode) throws PersistenceException {
		// Delete existing settings from ISW_IAP_POSITION_ITEM_CODE
		String sql1 = "DELETE FROM ISW_IAP_POSITION_ITEM_CODE WHERE INCIDENT_ID = " + incidentId 
			+ " AND SECTION = '" + sectionCode + "' AND FORM = '203'";
		
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
		query1.executeUpdate();
		
		// Insert default settings for this section
		String sequence = (super.isOracleDialect()==true ? "SEQ_IAP_POSITION_ITEM_CODE.nextVal" : "nextVal('SEQ_IAP_POSITION_ITEM_CODE')");
		
		for(IapDefaultSettingsQuery._203Settings _203Enum : IapDefaultSettingsQuery._203Settings.values()){
			if(_203Enum.section.equals(sectionCode)){
				String sql = _203Enum.sql;
				sql=sql.replaceAll(":fieldName", "INCIDENT_ID");
				sql=sql.replaceAll(":seqId", sequence);
				sql=sql.replaceAll(":incidentId", String.valueOf(incidentId));
				
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				query.executeUpdate();
			}
		}
	}
	
	public void restoreDefault204TemplateSettings(Long incidentId) throws PersistenceException {
		// Delete existing settings from ISW_IAP_POSITION_ITEM_CODE
		String sql1 = "DELETE FROM ISW_IAP_POSITION_ITEM_CODE WHERE INCIDENT_ID = " 
			+ incidentId 
			+ " AND FORM = '204'";
		
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
		query1.executeUpdate();
		
		// Insert default settings
		String sequence = (super.isOracleDialect()==true ? "SEQ_IAP_POSITION_ITEM_CODE.nextVal" : "nextVal('SEQ_IAP_POSITION_ITEM_CODE')");
		
		for(IapDefaultSettingsQuery._204Settings _204Enum : IapDefaultSettingsQuery._204Settings.values()){
			String sql = _204Enum.sql;
			sql=sql.replaceAll(":fieldName", "INCIDENT_ID");
			sql=sql.replaceAll(":seqId", sequence);
			sql=sql.replaceAll(":incidentId", String.valueOf(incidentId));
			
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	}
	
	public Date getEarliestIncStartDateResource(Long incidentId, Long incidentResourceId) throws PersistenceException {
		Date rtn=null;
		
		Long groupId=null;
		String sql1="select incident_group_id from isw_incident_group_incident where incident_id = " + incidentId + " ";
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
		Object rslt=query1.uniqueResult();
		
		if(null != rslt){
			try{
				groupId=TypeConverter.convertToLong(rslt);
			}catch(Exception smother){}
		}
		
		if(LongUtility.hasValue(groupId)){
			String sql2="SELECT min(i.incident_start_date) " +
			   "from isw_incident i " +
			   "where i.id in (" +
			   "  select incident_id from isw_incident_group_incident " +
			   "  where incident_group_id = " + groupId + " " +
			   ")";
			SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);
			Object rslt2=query2.uniqueResult();
			
			if(null != rslt2){
				try{
					rtn=TypeConverter.convertToDate(rslt2);
				}catch(Exception smother){}
			}
		}else{
			String sql3="select i.incident_start_date "+
			            "from isw_incident i " +
			            "where i.id = " + incidentId + " ";
			SQLQuery query3 = getHibernateSession().createSQLQuery(sql3);
			Object rslt3=query3.uniqueResult();
			
			if(null != rslt3){
				try{
					rtn=TypeConverter.convertToDate(rslt3);
				}catch(Exception smother){}
			}
		}
		
		return rtn;
	}
	
	public Date getEarliestIncidentStartDate() throws PersistenceException {
		Date rtn=null;
		
		String sql="SELECT min(incident_start_date) from isw_incident";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		
		if(null != rslt){
			try{
				rtn=TypeConverter.convertToDate(rslt);
			}catch(Exception smother){}
		}
		
		return rtn;
	}
	
	public void updateSiteManaged(Long id, Boolean siteManaged) throws PersistenceException {
		String sql = "UPDATE isw_incident set is_site_managed = '" +
						(siteManaged==true ? "Y" : "N" ) +
					 "' where id = " + id;
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public void updateSiteManaged(String incidentTi, Boolean siteManaged) throws PersistenceException {
		String sql = "UPDATE isw_incident set is_site_managed = '" +
						(siteManaged==true ? "Y" : "N" ) +
					 "' where transferable_identity = '" + incidentTi + "' ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public Long getIncidentQuestionId(String question, Long incidentId) throws PersistenceException {
		Long id=0L;
		
		String sql="select id "+
				   "from isw_incident_question "+
				   "where incident_id = " + incidentId + " " +
				   "and question_id in ("+
				   " select id from isw_question where upper(question)='"+question.toUpperCase()+"' " +
				   ") ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				id=TypeConverter.convertToLong(rslt);
			}catch(Exception ee){}
		}
		
		return id;
	}
	
	public Boolean isIncidentLocked(Long id, String idType) throws PersistenceException {
		String sql="";
		
		if(idType.equals("INCIDENT")){
			sql="select is_site_managed from isw_incident where id = " + id + " ";
		}else if(idType.equals("INCIDENTGROUP")){
			sql="select is_site_managed from isw_incident_group where id = " + id + " ";
		}else if(idType.equals("INCIDENTRESOURCE")){
			sql="select is_site_managed from isw_incident where id = (select incident_id from isw_incident_resource where id = " + id + ") ";
		}
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt = query.uniqueResult();
		
		try{
			String val = TypeConverter.convertToString(rslt);
			
			if(StringUtility.hasValue(val) && val.equalsIgnoreCase("Y"))
				return true;
		}catch(Exception e){}
		
		return false;
	}

	public Boolean isSyncedOnce(Long id) throws PersistenceException {
		String sql="";
		
		sql="select is_site_synced_once from isw_incident where id = " + id + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt = query.uniqueResult();
		
		try{
			String val = TypeConverter.convertToString(rslt);
			
			if(StringUtility.hasValue(val) && val.equalsIgnoreCase("Y"))
				return true;
		}catch(Exception e){}
		
		return false;
	}

	public void updateIsSyncedOnce(Long id) throws PersistenceException {
		String sql="update isw_incident set is_site_synced_once = 'Y' where id = " + id + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
	}

	public GenericVo getDataByIncAcctCodeId(Long iacId) throws PersistenceException {
		StringBuffer sql = new StringBuffer()
			.append("select org.unit_code as str1 ")
			.append("  , i.nbr as str2 ")
			.append("  , i.incident_name as str3 ")
			.append("from isw_incident i ")
			.append("     ,isw_organization org ")
			.append("where org.id = i.unit_id ")
			.append("and i.id = (select incident_id from isw_incident_account_code where id = "+iacId+") ");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		CustomResultTransformer crt = new CustomResultTransformer(GenericVo.class);
		crt.addProjection("str1", "str1");
		crt.addProjection("str2", "str2");
		crt.addProjection("str3", "str3");
		
		query.setResultTransformer(crt);
		
		Collection<GenericVo> vos = (Collection<GenericVo>)query.list();
		if(CollectionUtility.hasValue(vos)){
			return vos.iterator().next();
		}
		
		return null;
	}
	
	public Long getIncidentIdByTi(String ti) throws PersistenceException {
		Long incId=0L;
		
		String sql = "select id from isw_incident where transferable_identity='"+ti+"' ";
		SQLQuery query=getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				incId=TypeConverter.convertToLong(rslt);
			}catch(Exception e){}
		}
		
		return incId;
	}

	public void cleanUpDuplicateQuestionIssue(Long incidentId) throws PersistenceException {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("")
			   .append("select count(qv.id) ")
			   .append("from isw_work_period_question_value  qv ")
			   .append("where qv.work_period_id in ( ")
				.append("	select id ")
				.append("	from isw_work_period ")
				.append("	where incident_resource_id in (")
				.append("       select id ")
				.append("       from isw_incident_resource ")
				.append("       where incident_id = "+ incidentId + " ")
				.append("   ) ")
				.append(") ")
				.append("and qv.id not in (")
				.append("  select min(qv2.id) " )
				.append("  from isw_work_period_question_value qv2 ")
				.append("  where qv2.work_period_id = qv.work_period_id ")
				.append("  group by qv2.work_period_id, qv2.incident_question_id ")
				.append(") ");
			
			SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
			Object rslt = q.uniqueResult();
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt > 0){
					sql = new StringBuffer();
					sql.append("")
					   .append("delete  ")
					   .append("from isw_work_period_question_value  qv ")
					   .append("where qv.work_period_id in ( ")
						.append("	select id ")
						.append("	from isw_work_period ")
						.append("	where incident_resource_id in (")
						.append("       select id ")
						.append("       from isw_incident_resource ")
						.append("       where incident_id = " + incidentId + " ")
						.append("   ) ")
						.append(") ")
						.append("and qv.id not in (")
						.append("  select min(qv2.id) " )
						.append("  from isw_work_period_question_value qv2 ")
						.append("  where qv2.work_period_id = qv.work_period_id ")
						.append("  group by qv2.work_period_id, qv2.incident_question_id ")
						.append(") ");
					q = getHibernateSession().createSQLQuery(sql.toString());
					q.executeUpdate();
				}
			}
		}catch(Exception e){
			throw new PersistenceException("cannot cleanup incident questions");
		}
	}
	
	public void createDefaultQuestionValues(Long incidentId) throws PersistenceException {
		String sql1=WorkPeriodQuestValueQuery.getMissingDefaultCountQuery(incidentId, super.isOracleDialect());
		SQLQuery q = getHibernateSession().createSQLQuery(sql1);
		Object rslt=q.uniqueResult();
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt>0){
					String sql2=WorkPeriodQuestValueQuery.getCreateDefaultValuesQuery(incidentId,super.isOracleDialect());
					SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
					q2.executeUpdate();
				}
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}
	}
	
}