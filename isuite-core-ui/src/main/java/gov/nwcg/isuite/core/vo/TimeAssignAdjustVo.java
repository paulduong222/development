package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.TimeAssignAdjustImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class TimeAssignAdjustVo extends AbstractVo {

	private AssignmentVo assignmentVo;
	private Long assignmentId;
	private Date activityDate;
	private DateTransferVo activityDateVo = new DateTransferVo();
	private Date deletedDate;
	private AdjustmentTypeEnum adjustmentType;
	private AdjustCategoryVo adjustCategoryVo;
	private BigDecimal adjustmentAmount;
	private IncidentAccountCodeVo incidentAccountCodeVo;
	private String commodity;
	private Collection<TimeInvoiceVo> timeInvoiceVos;
	private Date lastInvoiceDate;

	// helper properties for batch postings
	private String requestNumber;
	private String lastName;
	private String firstName;
	private String lastInvoiceDateStr;
	
	public TimeAssignAdjustVo() {
		super();
	}

	public static TimeAssignAdjustVo getInstance(TimeAssignAdjust entity, boolean cascadable) throws Exception {

		TimeAssignAdjustVo vo = new TimeAssignAdjustVo();
		
		if(null != entity){
			vo.setId(entity.getId());
			
			if(cascadable) {

				if(DateUtil.hasValue(entity.getActivityDate()))
					DateTransferVo.populateDate(vo.getActivityDateVo(), entity.getActivityDate());
				
				//if(null != entity.getActivityDate()) {
				//	vo.setActivityDate(entity.getActivityDate());	
				//}
				if(null != entity.getDeletedDate()) {
					vo.setDeletedDate(entity.getDeletedDate());	
				}

				if(null != entity.getAdjustmentType()) {
					vo.setAdjustmentType(entity.getAdjustmentType());	
				}
				
				if(null != entity.getAdjustCategory()) {
					vo.setAdjustCategoryVo(AdjustCategoryVo.getInstance(entity.getAdjustCategory(), cascadable));	
				}
				
				if(null != entity.getAdjustmentAmount()) {
					vo.setAdjustmentAmount(entity.getAdjustmentAmount());	
				}
				if(null != entity.getIncidentAccountCode()) {
					vo.setIncidentAccountCodeVo(IncidentAccountCodeVo.getInstance(entity.getIncidentAccountCode(), cascadable));	
				}
				if(null != entity.getCommodity()) {
					vo.setCommodity(StringUtility.toUpper(entity.getCommodity()));	
				}
				if(null != entity.getTimeInvoices()) {
					vo.setTimeInvoiceVos(TimeInvoiceVo.getInstances(entity.getTimeInvoices(), cascadable));
					if(CollectionUtility.hasValue(vo.getTimeInvoiceVos())){
						for(TimeInvoiceVo tivo : vo.getTimeInvoiceVos()){
							if(BooleanUtility.isTrue(tivo.getIsInvoiceAdjust() )
									&& !DateUtil.hasValue(tivo.getDeletedDate())){
								vo.setLastInvoiceDate(tivo.getLastIncludeDate());
							}
						}
					}
				}
				
				vo.setAssignmentId(entity.getAssignmentId());
			}
		}
		else {
			//throw new Exception("cannot create instance from null TimeAssignAdjust");
		}

		return vo;
	}

	public static Collection<TimeAssignAdjustVo> getInstances(Collection<TimeAssignAdjust> entities
			, boolean cascadable) throws Exception {

		ArrayList<TimeAssignAdjustVo> vos = new ArrayList<TimeAssignAdjustVo>();

		for(TimeAssignAdjust entity : entities) {

			TimeAssignAdjustVo vo = TimeAssignAdjustVo.getInstance(entity, cascadable);
			vos.add(vo);
		}

		return vos;
	}

	public static TimeAssignAdjust toEntity(TimeAssignAdjust entity, TimeAssignAdjustVo vo
			, boolean cascadable, Persistable...persistables) throws Exception {

		if(null == entity) entity = new TimeAssignAdjustImpl();

		entity.setId(vo.getId());

		if(cascadable) {

			if(LongUtility.hasValue(vo.getAssignmentId())){
				Assignment assignment = new AssignmentImpl();
				assignment.setId(vo.getAssignmentId());
				entity.setAssignment(assignment);
			}else if(null != vo.getAssignmentVo()){
				entity.setAssignment(AssignmentVo.toEntity(null, vo.getAssignmentVo(), false));
				entity.setAssignmentId(entity.getAssignment().getId());
			}
			
			if(DateTransferVo.hasDateString(vo.getActivityDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getActivityDateVo());
				entity.setActivityDate(dt);
			}
			//entity.setActivityDate(vo.getActivityDate());

			if(null != vo.getDeletedDate()) {
				entity.setDeletedDate(vo.getDeletedDate());	
			}


			/*
			 * make sure adjustmentType is set to either 'ADDITION' or 'DEDUCTION'
			 */
			if(null==vo.getAdjustmentType()){
				if(AbstractVo.hasValue(vo.getAdjustCategoryVo()))
					entity.setAdjustmentType(vo.getAdjustCategoryVo().getAdjustmentType());
			}else{
				if(AbstractVo.hasValue(vo.getAdjustCategoryVo()))
					entity.setAdjustmentType(vo.getAdjustCategoryVo().getAdjustmentType());
				else
					entity.setAdjustmentType(vo.getAdjustmentType());
			}

			if(AbstractVo.hasValue(vo.getAdjustCategoryVo())){
				entity.setAdjustCategory(AdjustCategoryVo.toEntity(null, vo.getAdjustCategoryVo(), false));
			}
			
			entity.setAdjustmentAmount(vo.getAdjustmentAmount());
			
			if(null != vo.getIncidentAccountCodeVo()){
				entity.setIncidentAccountCode(IncidentAccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo(), false));
			}

			entity.setCommodity(StringUtility.toUpper(vo.getCommodity()));

			//entity.setTimeInvoices(TimeInvoiceVo.toEntities(vo.getTimeInvoiceVos(), cascadable));
		}

		return entity;
	}

	public static Collection<TimeAssignAdjust> toEntities(Collection<TimeAssignAdjustVo> vos
			, boolean cascadable, Persistable...persistables) throws Exception {

		ArrayList<TimeAssignAdjust> entities = new ArrayList<TimeAssignAdjust>();

		for(TimeAssignAdjustVo vo : vos) {

			TimeAssignAdjust entity = TimeAssignAdjustVo.toEntity(null, vo, cascadable, persistables);
			entities.add(entity);
		}

		return entities;
	}

	public AssignmentVo getAssignmentVo() {
		return assignmentVo;
	}

	public void setAssignmentVo(AssignmentVo assignmentVo) {
		this.assignmentVo = assignmentVo;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public AdjustmentTypeEnum getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(AdjustmentTypeEnum adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public AdjustCategoryVo getAdjustCategoryVo() {
		return adjustCategoryVo;
	}

	public void setAdjustCategoryVo(AdjustCategoryVo adjustCategoryVo) {
		this.adjustCategoryVo = adjustCategoryVo;
	}

	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public IncidentAccountCodeVo getIncidentAccountCodeVo() {
		return incidentAccountCodeVo;
	}

	public void setIncidentAccountCodeVo(IncidentAccountCodeVo incidentAccountCodeVo) {
		this.incidentAccountCodeVo = incidentAccountCodeVo;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public Collection<TimeInvoiceVo> getTimeInvoiceVos() {
		return timeInvoiceVos;
	}

	public void setTimeInvoiceVos(Collection<TimeInvoiceVo> timeInvoiceVos) {
		this.timeInvoiceVos = timeInvoiceVos;
	}

	public Date getLastInvoiceDate() {
		return lastInvoiceDate;
	}

	public void setLastInvoiceDate(Date lastInvoiceDate) {
		this.lastInvoiceDate = lastInvoiceDate;
	}

	public DateTransferVo getActivityDateVo() {
		return activityDateVo;
	}

	public void setActivityDateVo(DateTransferVo activityDateVo) {
		this.activityDateVo = activityDateVo;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastInvoiceDateStr() {
		return lastInvoiceDateStr;
	}

	public void setLastInvoiceDateStr(String lastInvoiceDateStr) {
		this.lastInvoiceDateStr = lastInvoiceDateStr;
	}


}
