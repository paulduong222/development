package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.persistence.IncidentSelectorDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentSelectorQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.ResourceQuery;
import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.core.vo.WorkAreaGridVo;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Calendar;
import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

public class IncidentSelectorDaoHibernate extends TransactionSupportImpl implements IncidentSelectorDao {

	public IncidentSelectorDaoHibernate() {
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentSelectorDao#getIncidents(java.lang.Long,java.lang.Long)
	 */
	public Collection<IncidentSelector2Vo> getIncidents(Long userId, Long incidentId, Boolean filterExcluded) throws PersistenceException {
		String sql = IncidentSelectorQuery.getUserIncidentQuery(userId,incidentId,filterExcluded,super.isOracleDialect());
		
        SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		   
	    CustomResultTransformer crt = new CustomResultTransformer(IncidentSelector2Vo.class);
		crt.addProjection("id", "id");
		crt.addProjection("incidentId", "incidentId");
	    crt.addProjection("incidentName", "name");
	    crt.addProjection("type", "type");
	    crt.addProjection("eventTypeName", "eventType");
		crt.addProjection("incidentNumber", "incidentNumber");
		crt.addProjection("agencyCode", "agency");
		crt.addProjection("incidentStartDate", "startDate");
		crt.addProjection("accountCode", "defaultAccountingCode");
		crt.addProjection("accountCodeAgency", "defaultAccountingCodeAgency");
		
	    crt.addScalar("id", Long.class.getName());
	    crt.addScalar("incidentId", Long.class.getName());
	    crt.addScalar("incidentStartDate", String.class.getName());
	    crt.addScalar("type", String.class.getName());
	    
	    q.setResultTransformer(crt); 
	       
        return q.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentSelectorDao#getIncidentGroups(java.lang.Long,java.lang.Long)
	 */
	public Collection<IncidentSelector2Vo> getIncidentGroups(Long userId,Long incidentGroupId,Boolean filterExcluded) throws PersistenceException {
		String sql = IncidentSelectorQuery.getUserIncidentGroupQuery(userId,incidentGroupId,filterExcluded);
		
        SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		   
	    CustomResultTransformer crt = new CustomResultTransformer(IncidentSelector2Vo.class);
		crt.addProjection("id", "id");
		crt.addProjection("incidentGroupId", "incidentGroupId");
	    crt.addProjection("groupName", "name");
	    crt.addProjection("type", "type");
		
	    crt.addScalar("id", Long.class.getName());
	    crt.addScalar("incidentGroupId", Long.class.getName());
	    crt.addScalar("type", String.class.getName());
	    
	    q.setResultTransformer(crt); 
	       
        return q.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentSelectorDao#getIncidentGroupIncidents(java.lang.Long)
	 */
	public Collection<IncidentSelector2Vo> getIncidentGroupIncidents(Long groupId) throws PersistenceException {
		String sql = IncidentSelectorQuery.getIncidentGroupIncidentsQuery(groupId, super.isOracleDialect());

		
		return null;
	}

	public Collection<IncidentSelector2Vo> getExcludedIncidents(Long userId,Long incidentId) throws PersistenceException {
		String sql = IncidentSelectorQuery.getUserIncidentExcludedQuery(userId,super.isOracleDialect());
		
        SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		   
	    CustomResultTransformer crt = new CustomResultTransformer(IncidentSelector2Vo.class);
		crt.addProjection("id", "id");
		crt.addProjection("incidentId", "incidentId");
	    crt.addProjection("incidentName", "name");
	    crt.addProjection("type", "type");
	    crt.addProjection("eventTypeName", "eventType");
		crt.addProjection("incidentNumber", "incidentNumber");
		crt.addProjection("agencyCode", "agency");
		crt.addProjection("incidentStartDate", "startDate");
		crt.addProjection("accountCode", "defaultAccountingCode");
		crt.addProjection("accountCodeAgency", "defaultAccountingCodeAgency");
		
	    crt.addScalar("id", Long.class.getName());
	    crt.addScalar("incidentId", Long.class.getName());
	    crt.addScalar("incidentStartDate", String.class.getName());
	    crt.addScalar("type", String.class.getName());
	    
	    q.setResultTransformer(crt); 
	       
        return q.list();
	}
	
	public Collection<IncidentSelector2Vo> getExcludedIncidentGroups(Long userId,Long incidentGroupId) throws PersistenceException {
		String sql = IncidentSelectorQuery.getUserIncidentGroupExcludedQuery(userId);
		
        SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		   
	    CustomResultTransformer crt = new CustomResultTransformer(IncidentSelector2Vo.class);
		crt.addProjection("id", "id");
		crt.addProjection("incidentGroupId", "incidentGroupId");
	    crt.addProjection("groupName", "name");
	    crt.addProjection("type", "type");
		
	    crt.addScalar("id", Long.class.getName());
	    crt.addScalar("incidentGroupId", Long.class.getName());
	    crt.addScalar("type", String.class.getName());
	    
	    q.setResultTransformer(crt); 
	       
        return q.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentSelectorDao#removeExcludedItems(java.util.Collection, java.util.Collection)
	 */
	public int removeExcludedItems(Long userId,Collection<Long> incidentIds, Collection<Long> incidentGroupids) throws PersistenceException {
		int rslt=0;
		int rslt2=0;
		
		String sql = "";
		SQLQuery q = null;	   
		if(CollectionUtility.hasValue(incidentIds)){
		   sql = IncidentSelectorQuery.REMOVE_EXCLUDED_INCIDENTS_QUERY;
		   q = getHibernateSession().createSQLQuery(sql);
		   q.setParameter("userid", userId);
		   q.setParameterList("incidentids", incidentIds);
		   rslt = q.executeUpdate();
		}else{
			// make sure to remove all excluded incidents for user
		   sql = IncidentSelectorQuery.REMOVE_ALL_EXCLUDED_INCIDENTS_QUERY;
		   q = getHibernateSession().createSQLQuery(sql);
		   q.setParameter("userid", userId);
		   rslt = q.executeUpdate();
		}

	   
		if(CollectionUtility.hasValue(incidentGroupids)){
		   sql = IncidentSelectorQuery.REMOVE_EXCLUDED_INCIDENT_GROUPS_QUERY ;
		   q = getHibernateSession().createSQLQuery(sql);
		   q.setParameter("userid", userId);
		   q.setParameterList("incidentgroupids", incidentGroupids);
		   rslt2 = q.executeUpdate();
		}else{
		   sql = IncidentSelectorQuery.REMOVE_ALL_EXCLUDED_INCIDENT_GROUPS_QUERY;
		   q = getHibernateSession().createSQLQuery(sql);
		   q.setParameter("userid", userId);
		   rslt2 = q.executeUpdate();
		}
	   
	   return (rslt + rslt2);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentSelectorDao#createExcludedItems(java.util.Collection, java.util.Collection)
	 */
	public int createExcludedItems(Long userId, Collection<Long> incidentIds, Collection<Long> incidentGroupids) throws PersistenceException {
		
		int rslt=0;
		int rslt2=0;
		
		String sql = null;	   
		for(Long id : incidentIds){
		   sql = IncidentSelectorQuery.createExcludedIncidentQuery(userId, id, super.isOracleDialect());
           SQLQuery q = getHibernateSession().createSQLQuery(sql);
           rslt=q.executeUpdate();
		}

		for(Long id : incidentGroupids){
		   sql = IncidentSelectorQuery.createExcludedIncidentGroupQuery(userId, id, super.isOracleDialect());
           SQLQuery q = getHibernateSession().createSQLQuery(sql);
           rslt=q.executeUpdate();
		}
		
		return (rslt+rslt2);
	}
}
