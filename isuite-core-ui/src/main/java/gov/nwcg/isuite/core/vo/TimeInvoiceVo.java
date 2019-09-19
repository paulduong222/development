package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.TimeInvoiceImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class TimeInvoiceVo extends AbstractVo {

	private String invoiceNumber;
	private ReportVo invoiceReportVo;
	private ReportVo adjustmentReportVo;
	private Date firstIncludeDate;
	private Date lastIncludeDate;
	private Date deletedDate;
	private Boolean isDraft;
	private Boolean isDuplicate;
	private Boolean isFinal;
	private Boolean isInvoiceAdjust;
	private Boolean isInvoiceOnly;
	private Boolean isAdjustOnly;
	private Collection<ResourceVo> resources;
	
	public TimeInvoiceVo() {
		super();
	}
	
	public static TimeInvoiceVo getInstance(TimeInvoice entity, boolean cascadable) throws Exception {
	
		if(null == entity)
			throw new Exception("Unable to create vo from null TimeInvoice entity");
		
		TimeInvoiceVo vo = new TimeInvoiceVo();
		
		vo.setId(entity.getId());
		
		
		if(cascadable) {

			vo.setInvoiceNumber(entity.getInvoiceNumber());
			vo.setFirstIncludeDate(entity.getFirstIncludeDate());
			vo.setLastIncludeDate(entity.getLastIncludeDate());
			vo.setDeletedDate(entity.getDeletedDate());
			vo.setIsDraft(entity.getIsDraft());
			vo.setIsDuplicate(entity.getIsDuplicate());			
			vo.setIsFinal(entity.getIsFinal());
			vo.setIsInvoiceAdjust(entity.getIsInvoiceAdjust());
			vo.setIsInvoiceOnly(entity.getIsInvoiceOnly());
			vo.setIsAdjustOnly(entity.getIsAdjustOnly());
			if(entity.getInvoiceReport() != null)
				vo.setInvoiceReportVo(ReportVo.getInstance(entity.getInvoiceReport(), true));
			if(entity.getAdjustmentReport() != null)
				vo.setAdjustmentReportVo(ReportVo.getInstance(entity.getAdjustmentReport(), true));
		}
		
		return vo;
	}
	
	public static Collection<TimeInvoiceVo> getInstances(Collection<TimeInvoice> entities, boolean cascadable) throws Exception {
		
		ArrayList<TimeInvoiceVo> vos = new ArrayList<TimeInvoiceVo>();
		
		for(TimeInvoice entity : entities) {
			
			TimeInvoiceVo vo = TimeInvoiceVo.getInstance(entity, cascadable);
			vos.add(vo);
		}
		
		return vos;
	}
	
	public static TimeInvoice toEntity(TimeInvoice entity, TimeInvoiceVo vo
			, boolean cascadable, Persistable...persistables) throws Exception {
		
		if(null == entity) entity = new TimeInvoiceImpl();
		
		entity.setId(vo.getId());
		entity.setIsExported(StringBooleanEnum.N);
		
		if(cascadable) {
			
			entity.setInvoiceNumber(vo.getInvoiceNumber());
			entity.setFirstIncludeDate(vo.getFirstIncludeDate());
			entity.setLastIncludeDate(vo.getLastIncludeDate());
			entity.setDeletedDate(vo.getDeletedDate());			
			entity.setIsDraft(vo.getIsDraft());
			entity.setIsDuplicate(vo.getIsDuplicate());
			entity.setIsFinal(vo.getIsFinal());
			entity.setIsInvoiceAdjust(vo.getIsInvoiceAdjust());
			entity.setIsInvoiceOnly(vo.getIsInvoiceOnly());
			entity.setIsAdjustOnly(vo.getIsAdjustOnly());
			if(vo.getInvoiceReportVo()!=null)
				entity.setInvoiceReport(ReportVo.toEntity(null, vo.getInvoiceReportVo(), true));
			if(vo.getAdjustmentReportVo()!=null)
				entity.setAdjustmentReport(ReportVo.toEntity(null, vo.getAdjustmentReportVo(), true));		
		}
		
		return entity;
	}
	
	public static Collection<TimeInvoice> toEntities(Collection<TimeInvoiceVo> vos
			, boolean cascadable) throws Exception {
		
		ArrayList<TimeInvoice> entities = new ArrayList<TimeInvoice>();
		
		for(TimeInvoiceVo vo : vos) {
			
			TimeInvoice entity = TimeInvoiceVo.toEntity(null, vo, cascadable);
			entities.add(entity);
		}
		
		return entities;
	}

	

	public ReportVo getInvoiceReportVo() {
		return invoiceReportVo;
	}

	public void setInvoiceReportVo(ReportVo invoiceReportVo) {
		this.invoiceReportVo = invoiceReportVo;
	}

	public ReportVo getAdjustmentReportVo() {
		return adjustmentReportVo;
	}

	public void setAdjustmentReportVo(ReportVo adjustmentReportVo) {
		this.adjustmentReportVo = adjustmentReportVo;
	}

	public Date getFirstIncludeDate() {
		return firstIncludeDate;
	}

	public void setFirstIncludeDate(Date firstIncludeDate) {
		this.firstIncludeDate = firstIncludeDate;
	}

	public Boolean getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}

	public Boolean getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(Boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the isFinal
	 */
	public Boolean getIsFinal() {
		return isFinal;
	}

	/**
	 * @param isFinal the isFinal to set
	 */
	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the lastIncludeDate
	 */
	public Date getLastIncludeDate() {
		return lastIncludeDate;
	}

	/**
	 * @param lastIncludeDate the lastIncludeDate to set
	 */
	public void setLastIncludeDate(Date lastIncludeDate) {
		this.lastIncludeDate = lastIncludeDate;
	}

	/**
	 * @return the isInvoiceAdjust
	 */
	public Boolean getIsInvoiceAdjust() {
		return isInvoiceAdjust;
	}

	/**
	 * @param isInvoiceAdjust the isInvoiceAdjust to set
	 */
	public void setIsInvoiceAdjust(Boolean isInvoiceAdjust) {
		this.isInvoiceAdjust = isInvoiceAdjust;
	}

	/**
	 * @return the isInvoiceOnly
	 */
	public Boolean getIsInvoiceOnly() {
		return isInvoiceOnly;
	}

	/**
	 * @param isInvoiceOnly the isInvoiceOnly to set
	 */
	public void setIsInvoiceOnly(Boolean isInvoiceOnly) {
		this.isInvoiceOnly = isInvoiceOnly;
	}

	/**
	 * @return the isAdjustOnly
	 */
	public Boolean getIsAdjustOnly() {
		return isAdjustOnly;
	}

	/**
	 * @param isAdjustOnly the isAdjustOnly to set
	 */
	public void setIsAdjustOnly(Boolean isAdjustOnly) {
		this.isAdjustOnly = isAdjustOnly;
	}

	
	public Collection<ResourceVo> getResources() {
		return resources;
	}

	public void setResources(Collection<ResourceVo> resources) {
		this.resources = resources;		
	}
}
