package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.reports.GlidePathReportTabEnum;
import gov.nwcg.isuite.core.reports.data.GlidePathReportResourceData;
import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;

public interface GlidePathReportDao extends TransactionSupport {
	
	public void setSessionFactory(SessionFactory sf);
	
	/**
	 * Generates glide path report data based on the filter values. 
	 * @param filter
	 * @param tabsToGenerate
	 * @return list of GlidePathReportResourceData objects
	 * @throws PersistenceException
	 */
	public Collection<GlidePathReportResourceData> getGlidePathReportData(GlidePathReportFilter filter, List<GlidePathReportTabEnum> tabsToGenerate) throws PersistenceException;
}
