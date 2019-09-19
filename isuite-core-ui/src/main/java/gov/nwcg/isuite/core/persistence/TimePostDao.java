package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.TimePostVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

public interface TimePostDao extends TransactionSupport, CrudDao<AssignmentTimePost>{

	public AssignmentTimePost getById(Long id, Class clazz) throws PersistenceException;
	public void save(AssignmentTimePost persistable) throws PersistenceException;
	public void saveAll(Collection<AssignmentTimePost> persistables) throws PersistenceException;
	public void delete(AssignmentTimePost persistable) throws PersistenceException;
	public void deleteAll(Collection<AssignmentTimePost> persistables) throws PersistenceException;
	public Collection<AssignmentTimePostVo> getGrid(Long assignmentTimeId) throws PersistenceException;

	public AssignmentTimePost getLatestTimePosting(Long incidentResourceId, Long atId, Boolean isPrimary) throws PersistenceException ;
	
	public Collection<AssignmentTimePostVo> getGridCrew(Collection<Long> ids) throws PersistenceException;
	
	public int getResourceTimePostCount(Long incidentResourceId) throws PersistenceException ;

	public int getResourcesTimePostCount(Collection<Long> ids) throws PersistenceException ;
	
	public int getResourceInvoicedTimePostCount(Long incidentResourceId) throws PersistenceException ;
	public int getResourceNonInvoicedTimePostCount(Long incidentResourceId) throws PersistenceException ;
	public int deleteResourceNonInvoicedTimePosts(Long incidentResourceId) throws PersistenceException;

	public int getDuplicateTimePostCount(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException;
	public int getDuplicateTimePostCountSpecial(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException;
	public Collection<Long> getDuplicateTimePostIdsSpecial(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException;

	
	public Long getIncidentResourceId(Long assignmentTimeId) throws PersistenceException;
	
	public void deleteDuplicateTimePosts(Long incidentResourceId, Date startTime, Date stopTime) throws PersistenceException;

	public Collection<String> getDuplicateDailyPosts(TimePostQueryFilterImpl filter ) throws PersistenceException ;
	public Collection<Long> getDuplicateDailyPostsIds(TimePostQueryFilterImpl filter) throws PersistenceException ;

	public Collection<String> getDuplicateContractorPosts(TimePostQueryFilterImpl filter ) throws PersistenceException ;
	public Collection<Long> getDuplicateContractorPostsIds(TimePostQueryFilterImpl filter) throws PersistenceException ;
	
	public void updateDuplicateDailyPosts(Long timePostId, Long incidentResourceId, Boolean invoiceOnly, Date startDate, Date stopDate, String postType) throws PersistenceException ;

	/**
	 * Return all entities matching the filter criteria.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<AssignmentTimePost> getByFilter(TimePostQueryFilterImpl filter) throws PersistenceException;

	public Collection<Long> getDuplicateTimePostIds(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) throws PersistenceException ;

	public Collection<Long> getPostByFilter(TimePostQueryFilterImpl filter) throws PersistenceException ;

	public void deleteInvoicedRecords(Long timePostId) throws PersistenceException ;

	public int getInvoicedCount(Long timePostId) throws PersistenceException;
	
	public Collection<AssignmentTimePost> getResourceTimePosts(Long incidentResourceId,Date lastDate) throws PersistenceException ;

	public Collection<Long> updateDuplicateHourlyPosts(TimePostQueryFilterImpl filter) throws PersistenceException ;

	public Collection<AssignmentTimePost> getByIncidentResourceId(Long id) throws PersistenceException;

	public Collection<Long> getChildUniqueAcctCodeIdsByDate(Long incResId, Long excludeAcctCodeId, Date dt) throws PersistenceException ;
	
	public Long getAssignmentTimeId(Long id) throws PersistenceException ;
	
	public void deleteById(Long id) throws PersistenceException;

	public Collection<AssignmentTimePost> getCrewTimePostings(Collection<Long> irids,TimePostQueryFilterImpl f) throws PersistenceException ;

	public Collection<AssignmentTimePost> getTimePostings(Date lastDate, Long incidentResourceId,Long incidentAccountCodeId) throws PersistenceException ;	
	
	public Date getLatestTimePostingDateByResourceId(Long resourceId) throws PersistenceException ;

	public Date getLatestTimePostingDateForParentId(Long irId) throws PersistenceException;
	
	public Collection<Date> getNonInvoicedUniqueTimePostDates(Long incidentResourceId) throws PersistenceException ;

	public void deletePersonInvoiceRecords(Long assignmentTimeId) throws PersistenceException ;

	public Date getLastInvoiceDate(Long assignmentTimeId) throws PersistenceException ;	
	
	public int getResourcesInvoicedTimePostCount(Collection<Long> ids) throws PersistenceException;	

	public Collection<TimePostVo> getTimePostingsByAssignmentTimeId(Long id) throws PersistenceException;

	public void fixStopTimes(Long incidentResourceId, Long incidentId) throws PersistenceException ;	
	
	public void fixStopTimes2(Collection<Long> assignmentTimeIds) throws PersistenceException;
	
	public Collection<IncidentResourceTimePostDataVo> getTimePostingData(Long incidentResourceParentId, Date postDate, Boolean subsOnly) throws PersistenceException;
	
	public void saveInvoicedAmount(AssignmentTimePost atp) throws PersistenceException ;
	
	public Collection<AssignmentTimePost> getByAssignmentId(Long assignmentId) throws PersistenceException;
}
