package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_TIME_INVOICE", sequenceName="SEQ_TIME_INVOICE")
@Table(name = "isw_time_invoice")
public class TimeInvoiceImpl extends PersistableImpl implements TimeInvoice {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_TIME_INVOICE")
   private Long id = 0L;
	   
   @Column(name="invoice_number", nullable=false, length=60)
   private String invoiceNumber;
   
   @Column(name = "invoice_report_id", length= 19, insertable=false, updatable=false, nullable=true)
   private Long invoiceReportId;
   
   @ManyToOne(targetEntity=ReportImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "invoice_report_id", insertable = true, updatable = true, nullable = true)
   private Report invoiceReport;
   
   @Column(name = "adjustment_report_id", length= 19, insertable=false, updatable=false, nullable=true)
   private Long adjustmentReportId;
    
   @ManyToOne(targetEntity=ReportImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "adjustment_report_id", insertable = true, updatable = true, nullable = true)
   private Report adjustmentReport;
   
   @Column(name="first_include_date", nullable=false)
   private Date firstIncludeDate;
   
   @Column(name="last_include_date", nullable=false)
   private Date lastIncludeDate;
   
   @Column(name="deleted_date", nullable=true)
   private Date deletedDate;
   
   @Column(name="deleted_reason", nullable=true)
   private String deletedReason;
   
   @Column(name="is_draft", nullable=false)
   private Boolean isDraft;
   
   @Column(name="is_duplicate", nullable=false)
   private Boolean isDuplicate;

   @Column(name="is_final", nullable=false)
   private Boolean isFinal;
   
   @Column(name="is_invoice_adjust", nullable=false)
   private Boolean isInvoiceAdjust;
   
   @Column(name="is_invoice_only", nullable=false)
   private Boolean isInvoiceOnly;
   
   @Column(name="is_adjust_only", nullable=false)
   private Boolean isAdjustOnly;
   
   
   @Column(name = "is_exported",nullable=false)
   @Enumerated(EnumType.STRING)
   private StringBooleanEnum exported;

   @ManyToMany(targetEntity=TimeAssignAdjustImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
   @JoinTable(name = "ISW_TIME_ASSIGN_ADJ_INVOICE", 
				joinColumns = { 
					@JoinColumn(name = "TIME_INVOICE_ID", nullable = false, updatable = false) }, 
				inverseJoinColumns = { 
					@JoinColumn(name = "TIME_POST_ADJUST_ID", nullable = false, updatable = false) })
   @OrderBy("activityDate")
   private Collection<TimeAssignAdjust> timeAssignmentAdjusts;
	
   @ManyToMany(targetEntity=AssignmentTimePostImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
   @JoinTable(name = "ISW_ASSIGN_TIME_POST_INVOICE", 
					joinColumns = { 
						@JoinColumn(name = "TIME_INVOICE_ID", nullable = false, updatable = false) },
					inverseJoinColumns = { 
						@JoinColumn(name = "ASSIGN_TIME_POST_ID", nullable = false, updatable = false) })
   @OrderBy("postStartDate")
   public Collection<AssignmentTimePost> assignmentTimePosts;
	
   @ManyToOne(targetEntity=ContractorAgreementImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "CONTRACTOR_AGREEMENT_ID", insertable = true, updatable = true, nullable = true)
   private ContractorAgreement contractorAgreement; 
   
   @Column(name = "CONTRACTOR_AGREEMENT_ID", length= 19, insertable=false, updatable=false, nullable=true)
   private Long contractorAgreementId;
   
   @ManyToOne(targetEntity=ContractorImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "CONTRACTOR_ID", insertable = true, updatable = true, nullable = true)
   private Contractor contractor; 
   
   @Column(name = "CONTRACTOR_ID", length= 19, insertable=false, updatable=false, nullable=true)
   private Long contractorId;
   
   @ManyToMany(targetEntity=ResourceImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
   @JoinTable(name = "ISW_RESOURCE_INVOICE", 
					joinColumns = { 
						@JoinColumn(name = "TIME_INVOICE_ID", nullable = false, updatable = false) },
					inverseJoinColumns = { 
						@JoinColumn(name = "RESOURCE_ID", nullable = false, updatable = false) })
   public Collection<Resource> resources;
   
   @Column(name="export_date", nullable=true)
   private Date exportDate;
   
   
   /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceReportId() {
    return invoiceReportId;
  }

  public void setInvoiceReportId(Long invoiceReportId) {
    this.invoiceReportId = invoiceReportId;
  }

  public Report getInvoiceReport() {
    return invoiceReport;
  }

  public void setInvoiceReport(Report invoiceReport) {
    this.invoiceReport = invoiceReport;
  }

  public Long getAdjustmentReportId() {
    return adjustmentReportId;
  }

  public void setAdjustmentReportId(Long adjustmentReportId) {
    this.adjustmentReportId = adjustmentReportId;
  }

  public Report getAdjustmentReport() {
    return adjustmentReport;
  }

  public void setAdjustmentReport(Report adjustmentReport) {
    this.adjustmentReport = adjustmentReport;
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
  
  public StringBooleanEnum getIsExported() {
	  return exported;
  }
  
  public void setIsExported(StringBooleanEnum exported){
	  this.exported = exported;
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
	
	public String getDeletedReason() {
		return deletedReason;
	}


	public void setDeletedReason(String deletedReason) {
		this.deletedReason = deletedReason;
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

	/**
	 * @return the assignmentTimePosts
	 */
	public Collection<AssignmentTimePost> getAssignmentTimePosts() {
		return assignmentTimePosts;
	}

	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(
			Collection<AssignmentTimePost> assignmentTimePosts) {
		this.assignmentTimePosts = assignmentTimePosts;
	}

	/**
	 * @return the timeAssignmentAdjusts
	 */
	public Collection<TimeAssignAdjust> getTimeAssignmentAdjusts() {
		return timeAssignmentAdjusts;
	}

	/**
	 * @param timeAssignmentAdjusts the timeAssignmentAdjusts to set
	 */
	public void setTimeAssignmentAdjusts(
			Collection<TimeAssignAdjust> timeAssignmentAdjusts) {
		this.timeAssignmentAdjusts = timeAssignmentAdjusts;
	}
	
	@Override
	public Collection<Resource> getResources() {
		return resources;
	}
	
	@Override
	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		TimeInvoiceImpl o = (TimeInvoiceImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @param contractorAgreement the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement) {
		this.contractorAgreement = contractorAgreement;
	}

	/**
	 * @return the contractorAgreement
	 */
	public ContractorAgreement getContractorAgreement() {
		return contractorAgreement;
	}

	/**
	 * @param contractorAgreementId the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) {
		this.contractorAgreementId = contractorAgreementId;
	}

	/**
	 * @return the contractorAgreementId
	 */
	public Long getContractorAgreementId() {
		return contractorAgreementId;
	}

	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	/**
	 * @return the contractor
	 */
	public Contractor getContractor() {
		return contractor;
	}

	/**
	 * @param contractorId the contractorId to set
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	/**
	 * @return the contractorId
	 */
	public Long getContractorId() {
		return contractorId;
	}

	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * @return the exportDate
	 */
	public Date getExportDate() {
		return exportDate;
	}

}
