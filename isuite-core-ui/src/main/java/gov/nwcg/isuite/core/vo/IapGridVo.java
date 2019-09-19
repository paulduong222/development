package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapGridVo extends AbstractVo {
	private Collection<String> hierachalGroupField = new ArrayList<String>();
	private String recordType; // plan or form
	private String name; // plan name or form name
	private String formType = "";
	private String uniqueKey = "";
	private String sortValue;
	private String displayName;
	private String fromDateString;
	private String fromTimeString;
	private String toDateString;
	private String toTimeString;
	private Boolean isLocked;
	private Long parentGridVoId;
	private String operationalPeriod;
	private Date fromDate;
	private Date toDate;

	private Integer formSequence = 1;
	private Boolean hasMultiple = false;
	private Boolean printDocument=false;
	private Integer printPosition=Integer.valueOf(0);
	private Boolean isAttachment=false;
	private Long attachmentId;
	
	private Collection<IapAttachmentVo> iapAttachmentVos = new ArrayList<IapAttachmentVo>();
	private Collection<IapPlanPrintOrderVo> iapPlanPrintOrderVos = new ArrayList<IapPlanPrintOrderVo>();
	
	private Collection<IapGridVo> children = new ArrayList<IapGridVo>();

	public IapGridVo(){

	}

	public static IapGridVo getForm202Instance(IapForm202 entity) throws Exception {
		IapGridVo gridVo = new IapGridVo();
		gridVo.setId(entity.getId());

		if(LongUtility.hasValue(entity.getIapPlanId()))
			gridVo.setParentGridVoId(entity.getIapPlanId());
		else{
			if(null != entity.getIapPlan())
				gridVo.setParentGridVoId(entity.getIapPlan().getId());
		}
		gridVo.setIsLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
		gridVo.setRecordType("FORM202");
		gridVo.setFormType("202");
		gridVo.setDisplayName("ICS 202");
		gridVo.setUniqueKey("FORM202" + entity.getId());
		gridVo.setSortValue("A202"+entity.getId());
		return gridVo;
	}

	public static IapGridVo getForm203Instance(IapForm203 entity) throws Exception {
		IapGridVo gridVo = new IapGridVo();
		gridVo.setId(entity.getId());

		if(LongUtility.hasValue(entity.getIapPlanId()))
			gridVo.setParentGridVoId(entity.getIapPlanId());
		else{
			if(null != entity.getIapPlan())
				gridVo.setParentGridVoId(entity.getIapPlan().getId());
		}
		gridVo.setIsLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
		gridVo.setRecordType("FORM203");
		gridVo.setFormType("203");
		gridVo.setDisplayName("ICS 203");
		gridVo.setUniqueKey("FORM203" + entity.getId());
		gridVo.setSortValue("B203"+entity.getId());
		
		return gridVo;
	}
	
	public static IapGridVo getForm204Instance(IapBranch entity) throws Exception {
		IapGridVo gridVo = new IapGridVo();
		gridVo.setId(entity.getId());

		if(LongUtility.hasValue(entity.getIapPlanId()))
			gridVo.setParentGridVoId(entity.getIapPlanId());
		else{
			if(null != entity.getIapPlan())
				gridVo.setParentGridVoId(entity.getIapPlan().getId());
		}
		gridVo.setIsLocked(StringBooleanEnum.toBooleanValue(entity.getIsForm204Locked()));
		gridVo.setRecordType("FORM204");
		gridVo.setFormType("204");
		gridVo.setDisplayName("ICS 204" 
				+ (StringUtility.hasValue(entity.getBranchName()) ? " Branch "+entity.getBranchName() : "" ) 
				+ (StringUtility.hasValue(entity.getDivisionName()) ? " Division "+entity.getDivisionName() : "" ) 
				);
		gridVo.setUniqueKey("FORM204" + entity.getId());
		gridVo.setSortValue("C204"
					+ (StringUtility.hasValue(entity.getBranchName()) ? entity.getBranchName() : "A" ) 
					+ (StringUtility.hasValue(entity.getDivisionName()) ? entity.getDivisionName() : "" )
					+ " " + entity.getId()); 
		
		return gridVo;
	}

	public static IapGridVo getForm205Instance(IapForm205 entity) throws Exception {
		IapGridVo gridVo = new IapGridVo();
		gridVo.setId(entity.getId());

		if(LongUtility.hasValue(entity.getIapPlanId()))
			gridVo.setParentGridVoId(entity.getIapPlanId());
		else{
			if(null != entity.getIapPlan())
				gridVo.setParentGridVoId(entity.getIapPlan().getId());
		}
		gridVo.setIsLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
		gridVo.setRecordType("FORM205");
		gridVo.setFormType("205");
		gridVo.setDisplayName("ICS 205");
		gridVo.setUniqueKey("FORM205" + entity.getId());
		gridVo.setSortValue("D205"+entity.getId());
		
		return gridVo;
	}
	
	public static IapGridVo getForm206Instance(IapForm206 entity) throws Exception {
		IapGridVo gridVo = new IapGridVo();
		gridVo.setId(entity.getId());

		if(LongUtility.hasValue(entity.getIapPlanId()))
			gridVo.setParentGridVoId(entity.getIapPlanId());
		else{
			if(null != entity.getIapPlan())
				gridVo.setParentGridVoId(entity.getIapPlan().getId());
		}
		gridVo.setIsLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
		gridVo.setRecordType("FORM206");
		gridVo.setFormType("206");
		gridVo.setDisplayName("ICS 206");
		gridVo.setUniqueKey("FORM206" + entity.getId());
		gridVo.setSortValue("E206"+entity.getId());
		
		return gridVo;
	}
	
	public static IapGridVo getForm220Instance(IapForm220 entity) throws Exception {
		IapGridVo gridVo = new IapGridVo();
		gridVo.setId(entity.getId());

		if(LongUtility.hasValue(entity.getIapPlanId()))
			gridVo.setParentGridVoId(entity.getIapPlanId());
		else{
			if(null != entity.getIapPlan())
				gridVo.setParentGridVoId(entity.getIapPlan().getId());
		}
		gridVo.setIsLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
		gridVo.setRecordType("FORM220");
		gridVo.setFormType("220");
		gridVo.setDisplayName("ICS 220");
		gridVo.setUniqueKey("FORM220" + entity.getId());
		gridVo.setSortValue("F220"+entity.getId());
		
		return gridVo;
	}
	
	public static IapGridVo getInstance(IapPlan entity) throws Exception {
		IapGridVo gridVo = new IapGridVo();

		gridVo.setId(entity.getId());

		gridVo.setParentGridVoId(null);
		gridVo.setIsLocked(StringBooleanEnum.toBooleanValue(entity.getIsPlanLocked()));

		gridVo.setUniqueKey("PLAN" + entity.getId());
		gridVo.setRecordType("PLAN");
		gridVo.setName(entity.getIncidentName());
		gridVo.setFromDate(entity.getFromDate());
		gridVo.setFromDateString(DateUtil.toDateString(entity.getFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
		gridVo.setFromTimeString(DateUtil.toMilitaryTime(entity.getFromDate()));
		gridVo.setToDate(entity.getToDate());
		gridVo.setToDateString(DateUtil.toDateString(entity.getToDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
		gridVo.setToTimeString(DateUtil.toMilitaryTime(entity.getToDate()));
		
		if(gridVo.getToTimeString().equals("2359"))
			gridVo.setToTimeString("2400");
		
		// build the displayName
		String dname = gridVo.getFromDateString() + " - " + gridVo.getToDateString() + " " +
		gridVo.getFromTimeString() + " - " + gridVo.getToTimeString() +
		(StringUtility.hasValue(entity.getOperationPeriod()) ? " " + entity.getOperationPeriod() : "");
		gridVo.setDisplayName(dname);
		gridVo.setOperationalPeriod(entity.getOperationPeriod());

		if(CollectionUtility.hasValue(entity.getIapAttachments())){
			gridVo.setIapAttachmentVos(IapAttachmentVo.getInstances(entity.getIapAttachments(), true));
		}
		if(CollectionUtility.hasValue(entity.getIapPlanPrintOrder())){
			gridVo.setIapPlanPrintOrderVos(IapPlanPrintOrderVo.getInstances(entity.getIapPlanPrintOrder(), true));
		}

		/* dan 7/20/2019  need to rebuild hierarchy for ag-grid , this code
		 *  moved to iapplanservice.getgrid
		 */
		/*
		if(CollectionUtility.hasValue(entity.getIapForm202s())){
			for(IapForm202 form : entity.getIapForm202s()){
				IapGridVo childVo = getForm202Instance(form);
				gridVo.getChildren().add(childVo);
			}
		}
		if(CollectionUtility.hasValue(entity.getIapForm203s())){
			for(IapForm203 form : entity.getIapForm203s()){
				IapGridVo childVo = getForm203Instance(form);
				gridVo.getChildren().add(childVo);
			}
		}
		if(CollectionUtility.hasValue(entity.getIapBranchs())){
			for(IapBranch form : entity.getIapBranchs()){
				IapGridVo childVo = getForm204Instance(form);
				gridVo.getChildren().add(childVo);
			}
		}
		if(CollectionUtility.hasValue(entity.getIapForm205s())){
			for(IapForm205 form : entity.getIapForm205s()){
				IapGridVo childVo = getForm205Instance(form);
				gridVo.getChildren().add(childVo);
			}
		}
		if(CollectionUtility.hasValue(entity.getIapForm206s())){
			for(IapForm206 form : entity.getIapForm206s()){
				IapGridVo childVo = getForm206Instance(form);
				gridVo.getChildren().add(childVo);
			}
		}
		if(CollectionUtility.hasValue(entity.getIapForm220s())){
			for(IapForm220 form : entity.getIapForm220s()){
				IapGridVo childVo = getForm220Instance(form);
				gridVo.getChildren().add(childVo);
			}
		}
		*/
	
		return gridVo;
	}

	public static Collection<IapGridVo> getInstances(Collection<IapPlan> entities) throws Exception {
		Collection<IapGridVo> vos = new ArrayList<IapGridVo>();

		for(IapPlan entity : entities){
			vos.add(getInstance(entity));
		}

		return vos;
	}

	/**
	 * @return the recordType
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType the recordType to set
	 */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fromDateString
	 */
	public String getFromDateString() {
		return fromDateString;
	}

	/**
	 * @param fromDateString the fromDateString to set
	 */
	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	/**
	 * @return the fromTimeString
	 */
	public String getFromTimeString() {
		return fromTimeString;
	}

	/**
	 * @param fromTimeString the fromTimeString to set
	 */
	public void setFromTimeString(String fromTimeString) {
		this.fromTimeString = fromTimeString;
	}

	/**
	 * @return the toDateString
	 */
	public String getToDateString() {
		return toDateString;
	}

	/**
	 * @param toDateString the toDateString to set
	 */
	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	/**
	 * @return the toTimeString
	 */
	public String getToTimeString() {
		return toTimeString;
	}

	/**
	 * @param toTimeString the toTimeString to set
	 */
	public void setToTimeString(String toTimeString) {
		this.toTimeString = toTimeString;
	}

	/**
	 * @return the parentGridVoId
	 */
	public Long getParentGridVoId() {
		return parentGridVoId;
	}

	/**
	 * @param parentGridVoId the parentGridVoId to set
	 */
	public void setParentGridVoId(Long parentGridVoId) {
		this.parentGridVoId = parentGridVoId;
	}

	/**
	 * @return the children
	 */
	public Collection<IapGridVo> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Collection<IapGridVo> children) {
		this.children = children;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the isLocked
	 */
	public Boolean getIsLocked() {
		return isLocked;
	}
	/**
	 * @param isLocked the isLocked to set
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the formType
	 */
	public String getFormType() {
		return formType;
	}

	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}

	/**
	 * @return the sortValue
	 */
	public String getSortValue() {
		return sortValue;
	}

	/**
	 * @param sortValue the sortValue to set
	 */
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	/**
	 * @return the iapAttachmentVos
	 */
	public Collection<IapAttachmentVo> getIapAttachmentVos() {
		return iapAttachmentVos;
	}

	/**
	 * @param iapAttachmentVos the iapAttachmentVos to set
	 */
	public void setIapAttachmentVos(Collection<IapAttachmentVo> iapAttachmentVos) {
		this.iapAttachmentVos = iapAttachmentVos;
	}
	/**
	 * @return the iapPlanPrintOrderVos
	 */
	public Collection<IapPlanPrintOrderVo> getIapPlanPrintOrderVos() {
		return iapPlanPrintOrderVos;
	}

	/**
	 * @param iapPlanPrintOrderVos the iapPlanPrintOrderVos to set
	 */
	public void setIapPlanPrintOrderVos(Collection<IapPlanPrintOrderVo> iapPlanPrintOrderVos) {
		this.iapPlanPrintOrderVos = iapPlanPrintOrderVos;
	}

	/**
	 * @return the printDocument
	 */
	public Boolean getPrintDocument() {
		return printDocument;
	}

	/**
	 * @param printDocument the printDocument to set
	 */
	public void setPrintDocument(Boolean printDocument) {
		this.printDocument = printDocument;
	}

	/**
	 * @return the printPosition
	 */
	public Integer getPrintPosition() {
		return printPosition;
	}

	/**
	 * @param printPosition the printPosition to set
	 */
	public void setPrintPosition(Integer printPosition) {
		this.printPosition = printPosition;
	}

	/**
	 * @return the isAttachment
	 */
	public Boolean getIsAttachment() {
		return isAttachment;
	}

	/**
	 * @param isAttachment the isAttachment to set
	 */
	public void setIsAttachment(Boolean isAttachment) {
		this.isAttachment = isAttachment;
	}

	/**
	 * @return the attachmentId
	 */
	public Long getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId the attachmentId to set
	 */
	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getOperationalPeriod() {
		return operationalPeriod;
	}

	public void setOperationalPeriod(String operationalPeriod) {
		this.operationalPeriod = operationalPeriod;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Collection<String> getHierachalGroupField() {
		return hierachalGroupField;
	}

	public void setHierachalGroupField(Collection<String> hierachalGroupField) {
		this.hierachalGroupField = hierachalGroupField;
	}

	public Integer getFormSequence() {
		return formSequence;
	}

	public void setFormSequence(Integer formSequence) {
		this.formSequence = formSequence;
	}

	public Boolean getHasMultiple() {
		return hasMultiple;
	}

	public void setHasMultiple(Boolean hasMultiple) {
		this.hasMultiple = hasMultiple;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	
}
