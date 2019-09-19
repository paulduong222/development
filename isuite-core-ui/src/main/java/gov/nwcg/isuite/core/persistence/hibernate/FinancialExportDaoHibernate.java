package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.domain.impl.FinancialExportImpl;
import gov.nwcg.isuite.core.persistence.FinancialExportDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.FinancialExportQuery;
import gov.nwcg.isuite.core.vo.FinancialExportResourceDataVo;
import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class FinancialExportDaoHibernate extends TransactionSupportImpl implements FinancialExportDao {

	private final CrudDao<FinancialExport> crudDao;

	public FinancialExportDaoHibernate(final CrudDao<FinancialExport> crudDao) {
		if (crudDao == null) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	@SuppressWarnings("unchecked")
	public Collection<FinancialExportVo> getGrid(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(FinancialExportImpl.class);

		if (LongUtility.hasValue(incidentGroupId))
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));

		else if (LongUtility.hasValue(incidentId))
			crit.add(Restrictions.eq("incidentId", incidentId));

		crit.addOrder(Order.desc("exportDate"));

		Collection<FinancialExport> entities = crit.list();

		try {
			return FinancialExportVo.getInstances(entities, true);

		} catch (Exception e) {
			throw new PersistenceException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite
	 * .framework.core.domain.Persistable)
	 */
	public void delete(FinancialExport persistable) throws PersistenceException {
		crudDao.delete(persistable);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.
	 * Long, java.lang.Class)
	 */
	public FinancialExport getById(Long id, Class<?> clazz)
			throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite
	 * .framework.core.domain.Persistable)
	 */
	public void save(FinancialExport persistable) throws PersistenceException {
		crudDao.save(persistable);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.
	 * Collection)
	 */
	public void saveAll(Collection<FinancialExport> persistables)
			throws PersistenceException {
		crudDao.saveAll(persistables);

	}
	
	public FinancialExportResourceDataVo getFinancialExportResourceData(Long timeInvoiceId) throws PersistenceException {
		String sql = FinancialExportQuery.getFinancialExportResourceData(timeInvoiceId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(FinancialExportResourceDataVo.class);
		
		crt.addProjection("requestNumber", "requestNumber");
		crt.addProjection("resourceName", "resourceName");
		crt.addProjection("employmentType", "employmentType");
		
		query.setResultTransformer(crt); 
		
		return (FinancialExportResourceDataVo)query.uniqueResult();
	}

}
