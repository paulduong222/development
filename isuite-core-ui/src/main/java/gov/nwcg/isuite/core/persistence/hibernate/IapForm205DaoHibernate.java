package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.persistence.IapForm205Dao;
import gov.nwcg.isuite.core.reports.data.AircraftDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.MissingDaysOfPostingsSubReportData;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;

public class IapForm205DaoHibernate extends TransactionSupportImpl implements IapForm205Dao {
	private final CrudDao<IapForm205> crudDao;
	
	public IapForm205DaoHibernate(final CrudDao<IapForm205> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapForm205 persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapForm205 getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapForm205 persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapForm205> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> verifyFrequenciesFrom205To204(Long iapPlanId, Long iapForm205Id) throws PersistenceException {
		//get iapPlanId
		//select form.iap_plan_id as id from isw_iap_form_205 form where form.id = 1;
		List<BigInteger> planIdPostgres = new ArrayList<BigInteger>();
		List<BigDecimal> planIdOracle = new ArrayList<BigDecimal>();
		StringBuffer sqlId = new StringBuffer();
		SQLQuery queryId;
		
		sqlId.append("select form.iap_plan_id as id from isw_iap_form_205 form where form.id = " + iapForm205Id);
		queryId = getHibernateSession().createSQLQuery(sqlId.toString());
		
		if (super.isOracleDialect()) {
			planIdOracle = queryId.list();
		}
		else {
			planIdPostgres = queryId.list();
		}
		
		//Long iapPlanIdTmp = planId.get(0);
		
		List<String> freqRX = new ArrayList<String>();
		List<String> freqTX = new ArrayList<String>();
		List<String> freq = new ArrayList<String>();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		SQLQuery query1;	
		SQLQuery query2;	
		
		sql1.append("select freq.frequency_rx as frequencyRx ")
		.append(" from isw_iap_frequency freq, isw_iap_form_205 form ")
		.append(" where freq.iap_form_205_id = form.id ")
		.append(" and freq.is_blank_line != 'Y' ")
		.append(" and form.is_form_locked = 'N' ")
		.append(" and form.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and form.id = " + iapForm205Id)
		.append(" and freq.frequency_rx NOT IN ( ")
		.append(" select comm.rx ")
		.append(" from isw_iap_branch_comm_summary comm, isw_iap_branch branch ")
		.append(" where branch.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and comm.iap_branch_id = branch.id ")
		.append(" and branch.is_form_204_locked = 'N' ) ");
		
		query1 = getHibernateSession().createSQLQuery(sql1.toString());
		freqRX = query1.list();
		
		sql2.append("select freq.frequency_tx as frequencyTx ")
		.append(" from isw_iap_frequency freq, isw_iap_form_205 form ")
		.append(" where freq.iap_form_205_id = form.id ")
		.append(" and freq.is_blank_line != 'Y' ")
		.append(" and form.is_form_locked = 'N' ")
		.append(" and form.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and form.id = " + iapForm205Id)
		.append(" and freq.frequency_tx NOT IN ( ")
		.append(" select comm.tx ")
		.append(" from isw_iap_branch_comm_summary comm, isw_iap_branch branch ")
		.append(" where branch.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and comm.iap_branch_id = branch.id ")
		.append(" and branch.is_form_204_locked = 'N' ) ");
		
		query2 = getHibernateSession().createSQLQuery(sql2.toString());
		freqTX = query2.list();
		
		HashSet<String> cache = new HashSet<String>();
		
		for (String frequency : freqRX) {
			if (cache.add(frequency)) {
				freq.add(frequency);
				//System.out.println("freqRX: " + frequency);
			}
		}
		for (String frequency : freqTX) {
			if (cache.add(frequency)) {
				freq.add(frequency);
				//System.out.println("freqTX: " + frequency);
			}
		}		
						
		return freq;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> verifyFrequenciesFrom204To205(Long iapPlanId, Long iapForm205Id) throws PersistenceException {
		//get iapPlanId
		//select form.iap_plan_id as id from isw_iap_form_205 form where form.id = 1;
		//super.isOracleDialect()
		List<BigInteger> planIdPostgres = new ArrayList<BigInteger>();
		List<BigDecimal> planIdOracle = new ArrayList<BigDecimal>();
		StringBuffer sqlId = new StringBuffer();
		SQLQuery queryId;
		sqlId.append("select form.iap_plan_id as id from isw_iap_form_205 form where form.id = " + iapForm205Id);
		queryId = getHibernateSession().createSQLQuery(sqlId.toString());
		
		if (super.isOracleDialect()) {
			planIdOracle = queryId.list();
		}
		else {
			planIdPostgres = queryId.list();
		}
				
		//planId = queryId.list();
		
		//BigInteger iapPlanIdTmp = planId.get(0);
		//Long iapPlanIdTmp = planId.get(0);
		
		List<String> freqRX = new ArrayList<String>();
		List<String> freqTX = new ArrayList<String>();
		List<String> freq = new ArrayList<String>();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		SQLQuery query1;	
		SQLQuery query2;	
				
		sql1.append(" select comm.rx as frequencyRx ")
		.append(" from isw_iap_branch_comm_summary comm, isw_iap_branch branch ")
		.append(" where branch.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and comm.iap_branch_id = branch.id ")
		.append(" and comm.is_blank_line != 'Y' ")
		.append(" and branch.is_form_204_locked = 'N' ")
		.append(" and comm.rx NOT IN ( ")
		.append(" select freq.frequency_rx ")
		.append(" from isw_iap_frequency freq, isw_iap_form_205 form ")
		.append(" where freq.iap_form_205_id = form.id ")
		.append(" and form.is_form_locked = 'N' ")
		.append(" and form.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and form.id = " + iapForm205Id + " ) ");
		
		query1 = getHibernateSession().createSQLQuery(sql1.toString());
		freqRX = query1.list();
		
		sql2.append(" select comm.tx as frequencyTx ")
		.append(" from isw_iap_branch_comm_summary comm, isw_iap_branch branch ")
		.append(" where branch.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and comm.iap_branch_id = branch.id ")
		.append(" and comm.is_blank_line != 'Y' ")
		.append(" and branch.is_form_204_locked = 'N' ")
		.append(" and comm.tx NOT IN ( ")
		.append(" select freq.frequency_tx ")
		.append(" from isw_iap_frequency freq, isw_iap_form_205 form ")
		.append(" where freq.iap_form_205_id = form.id ")
		.append(" and form.is_form_locked = 'N' ")
		.append(" and form.iap_plan_id = " + (super.isOracleDialect() ? planIdOracle.get(0) : planIdPostgres.get(0)))
		.append(" and form.id = " + iapForm205Id + " ) ");
		
		query2 = getHibernateSession().createSQLQuery(sql2.toString());
		freqTX = query2.list();		
		
		HashSet<String> cache = new HashSet<String>();
		
		for (String frequency : freqRX) {
			if (cache.add(frequency)) {
				freq.add(frequency);
				//System.out.println("freqRX: " + frequency);
			}
		}
		for (String frequency : freqTX) {
			if (cache.add(frequency)) {
				freq.add(frequency);
				//System.out.println("freqTX: " + frequency);
			}
		}		
		
		return freq;
	}

}
