package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.impl.IapPositionItemCodeImpl;
import gov.nwcg.isuite.core.persistence.IapPositionItemCodeDao;
import gov.nwcg.isuite.core.vo.IapPositionItemCodeVo;
import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.IapSectionEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;


import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

public class IapPositionItemCodeDaoHibernate extends TransactionSupportImpl implements IapPositionItemCodeDao {

	private final CrudDao<IapPositionItemCode> crudDao;
	
	
	/**
	 * Constructor.
	 * @param crudDao can't be null
	 */
	public IapPositionItemCodeDaoHibernate(final CrudDao<IapPositionItemCode> crudDao) {

      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapPositionItemCode persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapPositionItemCode getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapPositionItemCode persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapPositionItemCode> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);

	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapPositionItemCodeDao#getGrid(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapPositionVo> getGrid(Long incidentId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapPositionItemCodeImpl.class);
		
		crit.add(Expression.eq("incidentId", incidentId));
		
		Collection<IapPositionItemCode> entities = crit.list();
		
		try {
			return IapPositionVo.getInstances(entities);
		}catch(Exception e){
	    	 throw new PersistenceException(e);
	    }
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapPositionItemCodeDao#getByPosition(gov.nwcg.isuite.core.vo.IapPositionVo)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapPositionItemCode> getByPosition(IapPositionVo vo) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IapPositionItemCodeImpl.class);
		
		if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId()))
			crit.add(Restrictions.eq("incidentId", vo.getIncidentVo().getId()));
		else if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId()))
			crit.add(Restrictions.eq("incidentGroupId", vo.getIncidentGroupVo().getId()));
		
		crit.add(Restrictions.eq("position", vo.getPosition()));
		crit.add(Restrictions.eq("form", vo.getForm()));
		crit.add(Restrictions.eq("section", IapSectionEnum.toEnumValue(vo.getSectionVo().getCode())));
		
		return crit.list();
	}
	
	public Collection<IapPositionItemCode> getByPosition(IapPositionVo vo,String positionName) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapPositionItemCodeImpl.class);
		
		if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId()))
			crit.add(Restrictions.eq("incidentId", vo.getIncidentVo().getId()));
		else if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId()))
			crit.add(Restrictions.eq("incidentGroupId", vo.getIncidentGroupVo().getId()));
		
		crit.add(Restrictions.eq("position", positionName));
		crit.add(Restrictions.eq("form", vo.getForm()));
		crit.add(Restrictions.eq("section", IapSectionEnum.toEnumValue(vo.getSectionVo().getCode())));
		
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapPositionItemCodeDao#deleteAll(java.util.Collection)
	 */
	public void deleteAll(Collection<IapPositionItemCode> persistables) throws PersistenceException {
		for(IapPositionItemCode persistable : persistables) {
			crudDao.delete(persistable);
		}
	}
	
	public int get204PositionCount(Long incidentId, Long incidentGroupId) throws PersistenceException{
		String sql="select count(distinct(position)) from isw_iap_position_item_code "+
				   "where form = '204' ";

		if(LongUtility.hasValue(incidentId)){
			sql=sql+"and incident_id = " + incidentId+" ";
		}
		if(LongUtility.hasValue(incidentGroupId)){
			sql=sql+"and incident_group_id = " + incidentGroupId+" ";
		}
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}catch(Exception e){
				
			}
		}
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<IapPositionItemCodeVo> getAllForIncident(Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IapPositionItemCodeImpl.class);
		crit.add(Restrictions.eq("incidentId", incidentId));
		
		Collection<IapPositionItemCode> entities = crit.list();
		
		try {
			return IapPositionItemCodeVo.getInstances(entities, true);
		}catch(Exception e){
	    	 throw new PersistenceException(e);
	    }
	}
	
	@SuppressWarnings("unchecked")
	public Collection<IapPositionItemCodeVo> getAllForIncidentGroup(Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IapPositionItemCodeImpl.class);
		crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		
		Collection<IapPositionItemCode> entities = crit.list();
		
		try {
			return IapPositionItemCodeVo.getInstances(entities, true);
		}catch(Exception e){
	    	 throw new PersistenceException(e);
	    }
	}

}
