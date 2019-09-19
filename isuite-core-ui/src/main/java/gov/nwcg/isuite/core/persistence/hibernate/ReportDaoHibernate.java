package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.persistence.ReportDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

public class ReportDaoHibernate extends TransactionSupportImpl implements ReportDao {
	private final CrudDao<Report> crudDao;
	private static final Logger LOG = Logger.getLogger(ReportDaoHibernate.class);

	public ReportDaoHibernate(final CrudDao<Report> crudDao) {
		if ( crudDao == null ) {
			LOG.error("crudDao was null coming in to constructor.  Check the Spring config.");
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * \(non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Report getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Report persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(Report persistable) throws PersistenceException {
		if(null != persistable.getId() && persistable.getId()>0)
			   super.getHibernateSession().merge(persistable);
		   else
			   crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Report> persistables)
			throws PersistenceException {
		// TODO : Auto-generated method stub
	}
	
	public Collection<String> getObsoleteReportFilenames(String beforeDate) throws PersistenceException {
		Collection<String> names = new ArrayList<String>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("")
		   .append("select file_name ")
		   .append("from isw_report ")
		   .append("where ")
		   .append("( ")
		   .append("  id not in ( ")
		   .append("    select invoice_report_id as reportid ")
		   .append("	from isw_time_invoice ")
		   .append("	where is_draft="+(super.isOracleDialect()?1:true)+" ")
		   .append("  ) ")
		   .append("  or ")
		   .append("  id not in ( ")
		   .append("     select adjustment_report_id as reportid ")
		   .append("     from isw_time_invoice ")
		   .append("	where is_draft="+(super.isOracleDialect()?1:true)+" ")
		   .append("  ) " )
		   .append(") ")
		   .append("and to_date(to_char(date_requested,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+beforeDate+"','MM/DD/YYYY') ")
		   ;

		SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		names = (Collection<String>)q.list();
		
		return names;
	}
	
	public void deleteObsoleteReports(String beforeDate) throws PersistenceException {
		Collection<String> names = new ArrayList<String>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("")
		   .append("delete ")
		   .append("from isw_report ")
		   .append("where ")
		   .append("( ")
		   .append("  id not in ( ")
		   .append("    select invoice_report_id as reportid ")
		   .append("	from isw_time_invoice ")
		   .append("	where is_draft="+(super.isOracleDialect()?1:true)+" ")
		   .append("  ) ")
		   .append("  or ")
		   .append("  id not in ( ")
		   .append("     select adjustment_report_id as reportid ")
		   .append("     from isw_time_invoice ")
		   .append("	where is_draft="+(super.isOracleDialect()?1:true)+" ")
		   .append("  ) " )
		   .append(") ")
		   .append("and to_date(to_char(date_requested,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+beforeDate+"','MM/DD/YYYY') ")
		   ;

		SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		q.executeUpdate();
	}
	
}
