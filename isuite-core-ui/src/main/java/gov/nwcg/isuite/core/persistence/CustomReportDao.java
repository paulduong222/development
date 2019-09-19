package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.reports.data.CustomReportData;
import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.core.vo.CustomReportViewVo;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface CustomReportDao extends TransactionSupport, CrudDao<CustomReport> {
	
	/**
	 * Executes the sql query to generate the custom report results. These can later be 
	 * previewed or be used to generate a PDF or XLS file. 
	 * @param sql
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CustomReportData> getReportResults(String sql, Collection<CustomReportColumnVo> columnsVo) throws PersistenceException ;
	
	
	/**
	 * @param userId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CustomReportVo> getGrid(Long userId, Collection<SystemRoleVo> roles) throws PersistenceException;

	/**
	 * @param roles
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CustomReportViewVo> getCustomReportViewVos(Collection<SystemRoleVo> roles) throws PersistenceException;
	
	/**
	 * @param vo
	 * @param userId
	 * @return
	 * @throws PersistenceException
	 */
	public int getDuplicateReportTitleCount(CustomReportVo vo, Long userId) throws PersistenceException;
	
	
}
