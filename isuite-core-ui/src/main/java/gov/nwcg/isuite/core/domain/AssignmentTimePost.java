package gov.nwcg.isuite.core.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

public interface AssignmentTimePost extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;


	/**
	 * @return the assignmentTime
	 */
	public AssignmentTime getAssignmentTime();

	/**
	 * @param assignmentTime the assignmentTime to set
	 */
	public void setAssignmentTime(AssignmentTime assignmentTime) ;


	/**
	 * @return the assignmentTimeId
	 */
	public Long getAssignmentTimeId() ;


	/**
	 * @param assignmentTimeId the assignmentTimeId to set
	 */
	public void setAssignmentTimeId(Long assignmentTimeId) ;


	/**
	 * @return the rateClassRate
	 */
	public RateClassRate getRateClassRate();


	/**
	 * @param rateClassRate the rateClassRate to set
	 */
	public void setRateClassRate(RateClassRate rateClassRate) ;	


	/**
	 * @return the rateClassRateId
	 */
	public Long getRateClassRateId() ;


	/**
	 * @param rateClassRateId the rateClassRateId to set
	 */
	public void setRateClassRateId(Long rateClassRateId) ;


	/**
	 * @return the incidentAccountCode
	 */
	public IncidentAccountCode getIncidentAccountCode() ;


	/**
	 * @param incidentAccountCode the incidentAccountCode to set
	 */
	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode) ;


	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() ;


	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) ;

	/**
	 * @return the kind
	 */
	public Kind getKind() ;


	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) ;



	/**
	 * @return the kindId
	 */
	public Long getKindId() ;

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) ;


	/**
	 * @return the contractorRate
	 */
	public ContractorRate getRefContractorRate() ;


	/**
	 * @param contractorRate the contractorRate to set
	 */
	public void setRefContractorRate(ContractorRate contractorRate) ;


	/**
	 * @return the contractorRateId
	 */
	public Long getRefContractorRateId();


	/**
	 * @param contractorRateId the contractorRateId to set
	 */
	public void setRefContractorRateId(Long contractorRateId) ;


	/**
	 * @return the specialPay
	 */
	public SpecialPay getSpecialPay() ;


	/**
	 * @param specialPay the specialPay to set
	 */
	public void setSpecialPay(SpecialPay specialPay) ;


	/**
	 * @return the specialPayId
	 */
	public Long getSpecialPayId() ;


	/**
	 * @param specialPayId the specialPayId to set
	 */
	public void setSpecialPayId(Long specialPayId) ;


	/**
	 * @return the postStartDate
	 */
	public Date getPostStartDate() ;


	/**
	 * @param postStartDate the postStartDate to set
	 */
	public void setPostStartDate(Date postStartDate) ;


	/**
	 * @return the postStopDate
	 */
	public Date getPostStopDate() ;


	/**
	 * @param postStopDate the postStopDate to set
	 */
	public void setPostStopDate(Date postStopDate) ;


	/**
	 * @return the otherRate
	 */
	public BigDecimal getOtherRate() ;


	/**
	 * @param otherRate the otherRate to set
	 */
	public void setOtherRate(BigDecimal otherRate) ;


	/**
	 * @return the rateType
	 */
	public String getRateType() ;


	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) ;


	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() ;


	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) ;


	/**
	 * @return the rateAmmount
	 */
	public BigDecimal getRateAmount() ;

	/**
	 * @param rateAmmount the rateAmmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount);


	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount();


	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount);

	/**
	 * @return the description
	 */
	public String getDescription();


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) ;	

	/**
	 * @return the timeInvoices
	 */
	public Collection<TimeInvoice> getTimeInvoices();


	/**
	 * @param timeInvoices the timeInvoices to set
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices);
	
	/**
	 * @return the isHalfRate
	 */
	public Boolean getIsHalfRate();

	/**
	 * @param isHalfRate the isHalfRate to set
	 */
	public void setIsHalfRate(Boolean isHalfRate);

	/**
	 * @return the hours
	 */
	public BigDecimal getQuantity();

	/**
	 * @param hours the hours to set
	 */
	public void setQuantity(BigDecimal quantity);

	/**
	 * @return the training
	 */
	public Boolean getTraining();


	/**
	 * @param training the training to set
	 */
	public void setTraining(Boolean training);

	/**
	 * @return the returnTravelStartOnly
	 */
	public Boolean getReturnTravelStartOnly();


	/**
	 * @param returnTravelStartOnly the returnTravelStartOnly to set
	 */
	public void setReturnTravelStartOnly(Boolean returnTravelStartOnly);
	
	public AssignmentTimePost getAssignmentTimePost();


	public void setAssignmentTimePost(AssignmentTimePost assignmentTimePost);


	public AssignmentTimePost getSpecialRateAssignmentTimePost();


	public void setSpecialRateAssignmentTimePost(AssignmentTimePost specialRateAssignmentTimePost);
	
	/**
	 * @return the primaryPosting
	 */
	public Boolean getPrimaryPosting();


	/**
	 * @param primaryPosting the primaryPosting to set
	 */
	public void setPrimaryPosting(Boolean primaryPosting);

	/**
	 * @return the specialPosting
	 */
	public Boolean getSpecialPosting();

	/**
	 * @param specialPosting the specialPosting to set
	 */
	public void setSpecialPosting(Boolean specialPosting) ;


	/**
	 * @return the guaranteePosting
	 */
	public Boolean getGuaranteePosting();


	/**
	 * @param guaranteePosting the guaranteePosting to set
	 */
	public void setGuaranteePosting(Boolean guaranteePosting) ;


	/**
	 * @return the invoicedAmount
	 */
	public BigDecimal getInvoicedAmount();


	/**
	 * @param invoicedAmount the invoicedAmount to set
	 */
	public void setInvoicedAmount(BigDecimal invoicedAmount);
	
  /**
   * The employment type at the time of posting.  A resource's employment type may change but 
   * the employment type at the time of posting needs to be preserved.
   * @param employmentType
   */
  public void setEmploymentType(EmploymentTypeEnum employmentType);
	
  /**
   * The employment type at time of posting
   * @return
   */
  public EmploymentTypeEnum getEmploymentType();

	public String getContractorPostType();

	public void setContractorPostType(String contractorPostType);  

	public IncidentResourceDailyCost getincidentResourceDailyCost() ;

	public void setIncidentResourceDailyCost(IncidentResourceDailyCost incidentResourceDailyCost);
}
