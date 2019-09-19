package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SysCostRateOvhd;
import gov.nwcg.isuite.core.domain.impl.SysCostRateOvhdImpl;
import gov.nwcg.isuite.core.persistence.SysCostRateOvhdDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.SysCostRateOvhdQuery;
import gov.nwcg.isuite.core.vo.SysCostRateOvhdVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.SQLQuery;

@SuppressWarnings("unchecked")
public class SysCostRateOvhdDaoHibernate extends TransactionSupportImpl implements SysCostRateOvhdDao{

	private final CrudDao<SysCostRateOvhd> crudDao;
	
	public SysCostRateOvhdDaoHibernate(final CrudDao<SysCostRateOvhd> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateOvhdDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SysCostRateOvhd getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SysCostRateOvhdImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateOvhdDao#save(gov.nwcg.isuite.core.domain.SysCostRateOvhd)
	 */
	public void save(SysCostRateOvhd persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateOvhdDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SysCostRateOvhd> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateOvhdDao#delete(gov.nwcg.isuite.core.domain.SysCostRateOvhd)
	 */
	public void delete(SysCostRateOvhd persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateOvhdDao#overwriteOvhdKindRates(java.lang.String, gov.nwcg.isuite.core.vo.SysCostRateOvhdVo)
	 */
	public void overwriteOvhdKindRates(String rateType,SysCostRateOvhdVo vo) throws PersistenceException {
		
		if(rateType.equals("DIRECT")){
			// update direct
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateOvhdQuery.getUpdateDirectKindRates(
							super.isOracleDialect() 
							,vo.getSysCostRateId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
		}
		
		if(rateType.equals("INDIRECT")){
			// update indirect
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateOvhdQuery.getUpdateInDirectKindRates(
							super.isOracleDialect() 
							,vo.getSysCostRateId() 
							,vo.getIndirectRate()));	
			
			query.executeUpdate();
		}

		if(rateType.equals("NONSUBORDINATE")){
			// update non subordinate or (single resource rate)
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateOvhdQuery.getUpdateNonSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getSysCostRateId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
		}
		
		if(rateType.equals("SUBORDINATE2")){
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateOvhdQuery.getUpdateSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getSysCostRateId() 
							,vo.getIndirectRate()));	
			
			query.executeUpdate();
		}
		
		if(rateType.equals("SUBORDINATE")){
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateOvhdQuery.getUpdateSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getSysCostRateId() 
							,vo.getSubordinateRate()));	
			
			query.executeUpdate();
		}
	
	}

}