package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.persistence.GlidePathReportDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.GlidePathReportQuery;
import gov.nwcg.isuite.core.reports.GlidePathReportTabEnum;
import gov.nwcg.isuite.core.reports.data.GlidePathReportResourceData;
import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;

public class GlidePathReportDaoHibernate extends TransactionSupportImpl implements GlidePathReportDao {
	
	/**
	 * Generates glide path report data based on the filter values. 
	 * @param filter
	 * @param tabsToGenerate
	 * @return list of GlidePathReportResourceData objects
	 * @throws PersistenceException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<GlidePathReportResourceData> getGlidePathReportData(GlidePathReportFilter filter, List<GlidePathReportTabEnum> tabsToGenerate) throws PersistenceException {
		
		Collection<GlidePathReportResourceData> vos = new ArrayList<GlidePathReportResourceData>();
		
		// Create Query based on tabs to be generated.
		String sqlString = GlidePathReportQuery.generate(filter, tabsToGenerate, super.isOracleDialect());
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sqlString);

		CustomResultTransformer crt = new CustomResultTransformer(GlidePathReportResourceData.class);
		crt.addScalar("firstWorkingDate", Date.class.getName());
		crt.addScalar("lengthOfAssignment", Integer.class.getName());
		crt.addScalar("demobDate", Date.class.getName());
		
		sqlQuery.setResultTransformer(crt);
		
		try {
			vos = sqlQuery.list();
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}
		return vos;
	}
	
}
