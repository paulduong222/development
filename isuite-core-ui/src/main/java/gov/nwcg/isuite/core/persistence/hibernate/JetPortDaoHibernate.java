package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.core.filter.JetPortFilter;
import gov.nwcg.isuite.core.filter.impl.JetPortFilterImpl;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

/**
 *
 */
public class JetPortDaoHibernate extends TransactionSupportImpl implements JetPortDao {

   private final CrudDao<JetPort> crudDao;
   
   /**
    * Constructor.
    * @param crudDao can't be null
    */
   public JetPortDaoHibernate(final CrudDao<JetPort> crudDao) {

      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.JetPortDao#getGrid(gov.nwcg.isuite.core.filter.JetPortFilter)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<JetPortVo> getGrid(JetPortFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(JetPortImpl.class);
      
      if (filter != null) {
         Collection<FilterCriteria> filterCriteria;
         try {
            filterCriteria = JetPortFilterImpl.getFilterCriteria(filter);
            CriteriaBuilder.addCriteria(crit, filterCriteria);
         } catch ( Exception e ) {
            throw new PersistenceException(e);
         }
      }

      Collection<JetPort> entities = crit.list();
      
      try{
    	  return JetPortVo.getInstances(entities, true);
      }catch(Exception e){
    	  throw new PersistenceException(e);
      }
      
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.JetPortDao#getPicklist()
    */
   @SuppressWarnings("unchecked")
   public Collection<JetPortVo> getPicklist() throws PersistenceException {
      
      Criteria crit = getHibernateSession().createCriteria(JetPortImpl.class);
      
//      crit.createAlias("this.incident", "i", CriteriaSpecification.LEFT_JOIN);
      
//      crit.add(Expression.isNull("i.incidentEndDate"));
      
      crit.addOrder(Order.asc("this.code"));
      
      Collection<JetPort> entities = crit.list();
      
      try{
    	  return JetPortVo.getInstances(entities, true);
      }catch(Exception e){
    	  throw new PersistenceException(e);
      }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void delete(JetPort persistable) throws PersistenceException {
	   crudDao.delete(persistable);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public JetPort getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, JetPortImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   @Override
   public void save(JetPort persistable) throws PersistenceException {
      crudDao.save(persistable);
   }   

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   @Override
   public void saveAll(Collection<JetPort> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.JetPortDao#isCodeUnique(java.lang.String)
    */
   @Override
   public Boolean isCodeUnique(String code) throws PersistenceException {
      if (code == null || code.length() < 1) {
         throw new PersistenceException("code cannot be null!");
      }
      
      Query jetPortQuery = getHibernateSession().createQuery(ReferenceDataQuery.IS_JET_PORT_CODE_UNIQUE_QUERY);
      jetPortQuery.setParameter("code", code);
      return ((Long) jetPortQuery.uniqueResult()).equals(0L);
   }   
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.JetPortDao#getDuplicateCodeCount(java.lang.String, java.lang.Long)
    */
   public int getDuplicateCodeCount(JetPortVo vo) throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(JetPortImpl.class);
	   
	   crit.add(Expression.eq("code", vo.getCode().toUpperCase()));
	   crit.add(Expression.ne("id", vo.getId()));
	   
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
   
	public Collection<JetPortVo> getIncidentGroupJetPortDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException {
		Collection<JetPortVo> vos = new ArrayList<JetPortVo>();

		StringBuffer sql = new StringBuffer()
			.append("select distinct(j1.code) as code ")
			.append("      ,j1.description as description ")
			.append("      ,inc1.incident_name as tempString1 ")
			.append("from iswl_jet_port j1 ")
			.append("	  ,iswl_jet_port j2 ")
			.append("     ,isw_incident inc1 ")
			.append("where j1.code = j2.code ")
			.append("and j1.incident_id is not null ")
			.append("and j2.incident_id is not null ")
			.append("and j1.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and j2.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and j1.incident_id != j2.incident_id ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      j1.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      j1.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      j2.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      j2.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and inc1.id = j1.incident_id ")
			.append("order by j1.code, inc1.incident_name ")
			;

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		query.setParameterList("incidentIds", incidentIds);
		
		CustomResultTransformer rt = new CustomResultTransformer(JetPortVo.class);
		
		query.setResultTransformer(rt);
		
		vos = query.list();
		
		return vos;
	}
   
	public Collection<JetPortVo> getNonStdJetports(Long incidentGroupId, Long incidentId) throws PersistenceException {
	      Criteria crit = getHibernateSession().createCriteria(JetPort.class);
	      crit.add(Expression.eq("incidentId", incidentId));
	      		
	      crit.addOrder(Order.asc("this.code"));
	      
	      Collection<JetPort> entities = crit.list();
	      Collection<JetPortVo> vos = new ArrayList<JetPortVo>();
	      
	      try{
	          vos=JetPortVo.getInstances(entities, true);
	      }catch(Exception e){
	    	  throw new PersistenceException(e);
	      }
	      
	      return vos;		   
	}

	public Collection<JetPortVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException {
	      Criteria crit = getHibernateSession().createCriteria(JetPort.class);
		   if(CollectionUtility.hasValue(incidentIds)) {
			   crit.add(Expression.disjunction()
					   .add(Expression.eq("standard", Boolean.TRUE))
					   .add(Expression.in("incidentId", incidentIds))
			   );
		   }else if(LongUtility.hasValue(incidentGroupId)) {
			   crit.add(Expression.disjunction()
					   .add(Expression.eq("standard", Boolean.TRUE))
					   .add(Expression.eq("incidentGroupId", incidentGroupId))
			   			);
		   }
	      		
	      crit.addOrder(Order.asc("this.code"));
	      
	      Collection<JetPort> entities = crit.list();
	      Collection<JetPortVo> vos = new ArrayList<JetPortVo>();
	      
	      try{
	          vos=JetPortVo.getInstances(entities, true);
	      }catch(Exception e){
	    	  throw new PersistenceException(e);
	      }
	      
	      return vos;		   

	}

}
