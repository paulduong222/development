package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.TimeAdjustmentVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

public interface TimeAssignAdjustDao extends TransactionSupport, CrudDao<TimeAssignAdjust>{

	/**
	 * @param assignmentId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<TimeAssignAdjustVo> getGrid(Long assignmentId) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(TimeAssignAdjust persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(TimeAssignAdjust persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public TimeAssignAdjust getById(Long id, Class clazz) throws PersistenceException;

	/**
	 * @param ids
	 * @return
	 * @throws PersistenceException
	 */
	public int getResourcesTimeAdjustmentCount(Collection<Long> ids) throws PersistenceException;

	public Collection<TimeAssignAdjust> getByIncidentResourceId(Long id) throws PersistenceException; 

	public Long getIncidentId(Long assignmentId) throws PersistenceException ;
	
	public Long getIncidentResourceId(Long assignmentId) throws PersistenceException ;	

	public int deleteResourceAdjustments(Long incidentResourceId) throws PersistenceException ;

	public Collection<TimeAdjustmentVo> getTimeAdjustmentsByAssignmentId(Long id) throws PersistenceException;

	public Collection<Long> getAdjustIds(TimeAssignAdjust entity, Collection<Long> crewIds) throws PersistenceException;

	public void deleteAdjustments(Collection<Long> taaIds, Date dt) throws PersistenceException;

	public Collection<Long> getAssignmentIds(Collection<Long> crewIrIds) throws PersistenceException;	

	public Collection<TimeAssignAdjustVo> getLastInvoiceDateConflicts(Date activityDate,Collection<Long> crewIrIds) throws PersistenceException;

	public Collection<TimeAssignAdjustVo> getResourceInfo(Collection<Long> assignmentIds) throws PersistenceException;

	public Collection<TimeAssignAdjustVo> getNoTimePostingsList(Collection<Long> irIrIds) throws PersistenceException;

	public Collection<Long> getMatchingBatchAdjIds(TimeAssignAdjust entity, Collection<Long> crewIrIds) throws PersistenceException;	
	
	public void updateBatch(TimeAssignAdjust entity, Collection<Long> adjIds) throws PersistenceException ;	
	
	public Collection<IncidentResourceTimeAdustDataVo> getTimeAdjustData(Long incidentResourceParentId, Date postDate, Boolean subsOnly) throws PersistenceException;
	public Collection<IncidentResourceTimeAdustDataVo> getTimeAdjustData2(Long incidentResourceParentId, Date postDate, Boolean subsOnly) throws PersistenceException;
	
	public Date getIncidentStartDate(Long assignmentId) throws PersistenceException ;
	
	public Date getIncidentStartDateForCrew(Collection<Long> irIds) throws PersistenceException;	
}
