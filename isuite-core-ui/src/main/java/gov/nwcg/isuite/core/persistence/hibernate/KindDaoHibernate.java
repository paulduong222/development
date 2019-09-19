package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ItemCodeQuery;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 *
 */
public class KindDaoHibernate extends TransactionSupportImpl implements KindDao {
   private final CrudDao<Kind> crudDao;

   /*
    * Constructor
    */
   public KindDaoHibernate(final CrudDao<Kind> crudDao) {

      if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
      this.crudDao = crudDao;
      
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(Kind persistable) throws PersistenceException {
      crudDao.save(persistable);
   }   

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<Kind> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(Kind persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }   
   
   public Collection<Kind> getAll() throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(KindImpl.class);
	   return crit.list();
   }
   
   @SuppressWarnings("unchecked")
   public Collection<KindVo> getPicklist(KindFilter filter) throws PersistenceException {
      
      Criteria crit = getHibernateSession().createCriteria(Kind.class);
      /*
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               );
      */
//      crit.createAlias("this.incident", "i", CriteriaSpecification.LEFT_JOIN);
      
//      crit.add(Expression.isNull("i.incidentEndDate"));
      
      if(filter != null) {
         if(filter.getCode() != null && filter.getCode().trim().length() > 0) 
            crit.add(Expression.ilike("this.code", filter.getCode(), MatchMode.START));
         
         if(filter.getDescription() != null && filter.getDescription().trim().length() > 0)
            crit.add(Expression.ilike("this.description", filter.getDescription(), MatchMode.START));
      }
      //crit.setResultTransformer(Transformers.aliasToBean(KindVo.class));
      
      //crit.add(Expression.ne("this.code", "UNK")); 
      		
      crit.addOrder(Order.asc("this.code"));
      
      Collection<Kind> entities = crit.list();
      Collection<KindVo> vos = new ArrayList<KindVo>();
      
      try{
          vos=KindVo.getInstances(entities, true);
      }catch(Exception e){
    	  throw new PersistenceException(e);
      }
      
      return vos;
      //return crit.list();
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public Kind getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, KindImpl.class);
   }   
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.KindDao#getDuplicateCodeCount(gov.nwcg.isuite.core.vo.KindVo)
    */
   public int getDuplicateCodeCount(KindVo vo) throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(KindImpl.class);
	   
	   crit.add(Expression.eq("code", vo.getCode().toUpperCase()));
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

	public Collection<KindVo> getIncidentGroupKindDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException {
		Collection<KindVo> vos = new ArrayList<KindVo>();

		StringBuffer sql = new StringBuffer()
			.append("select distinct(k1.code) as code ")
			.append("      ,k1.description as description ")
			.append("      ,inc1.incident_name as tempString1 ")
			.append("from iswl_kind k1 ")
			.append("	  ,iswl_kind k2 ")
			.append("     ,isw_incident inc1 ")
			.append("where k1.code = k2.code ")
			.append("and k1.incident_id is not null ")
			.append("and k2.incident_id is not null ")
			.append("and k1.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and k2.is_standard = " + (super.isOracleDialect() ? 0 : false) + " ")
			.append("and k1.incident_id != k2.incident_id ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      k1.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      k1.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and (");
			if(LongUtility.hasValue(incidentGroupId)){
				sql.append("      k2.incident_id in ( ")
				   .append("         select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + " ")
				   .append("      ) ")
				   .append("      or " );
			}
		 sql.append("      k2.incident_id in ( " )
			.append("          :incidentIds ")
			.append("      ) ")
			.append(") ")
			.append("and inc1.id = k1.incident_id ")
			.append("order by k1.code, inc1.incident_name ")
			;

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		query.setParameterList("incidentIds", incidentIds);
		
		CustomResultTransformer rt = new CustomResultTransformer(KindVo.class);
		
		query.setResultTransformer(rt);
		
		vos = query.list();
		
		return vos;
	}
	
	public void executeInsertIncidentCostRateKind(Long kindId, String reqCatCode, Long incidentId) throws PersistenceException {
		SQLQuery query = 
			getHibernateSession().createSQLQuery(ItemCodeQuery.getInsertIncidentCostRateKind(
					super.isOracleDialect(), 
					kindId,
					reqCatCode,
					incidentId));
		
		query.executeUpdate();
	}
	
	public void executeInsertIncidentCostRateStateKind(Long kindId, String reqCatCode, Long incidentId) throws PersistenceException {
		SQLQuery query = 
			getHibernateSession().createSQLQuery(ItemCodeQuery.getInsertIncidentCostRateStateKind(
					super.isOracleDialect(), 
					kindId,
					reqCatCode,
					incidentId));
		
		query.executeUpdate();
	}

	   public Collection<KindVo> getByIncidentId(Long incidentId) throws PersistenceException {
		      Criteria crit = getHibernateSession().createCriteria(Kind.class);
		      crit.add(Expression.eq("incidentId", incidentId));
		      		
		      crit.addOrder(Order.asc("this.code"));
		      
		      Collection<Kind> entities = crit.list();
		      Collection<KindVo> vos = new ArrayList<KindVo>();
		      
		      try{
		          vos=KindVo.getInstances(entities, true);
		      }catch(Exception e){
		    	  throw new PersistenceException(e);
		      }
		      
		      return vos;		   
	   }

		public Collection<KindVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException {
		      Criteria crit = getHibernateSession().createCriteria(Kind.class);
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
		      		
		      crit.addOrder(Order.asc("this.code"));
		      
		      Collection<Kind> entities = crit.list();
		      Collection<KindVo> vos = new ArrayList<KindVo>();
		      
		      try{
		          vos=KindVo.getInstances(entities, true);
		      }catch(Exception e){
		    	  throw new PersistenceException(e);
		      }
		      
		      return vos;		   

		}
	   
}
