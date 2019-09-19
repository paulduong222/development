package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Date;

public interface CostData extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the accrualCode
	 */
	public AccrualCode getAccrualCode() ;

	/**
	 * @param accrualCode the accrualCode to set
	 */
	public void setAccrualCode(AccrualCode accrualCode) ;

	/**
	 * @return the paymentAgency
	 */
	public Agency getPaymentAgency() ;

	/**
	 * @param paymentAgency the paymentAgency to set
	 */
	public void setPaymentAgency(Agency paymentAgency) ;

	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() ;

	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) ;

	/**
	 * @return the accrualLocked
	 */
	public Boolean getAccrualLocked() ;

	/**
	 * @param accrualLocked the accrualLocked to set
	 */
	public void setAccrualLocked(Boolean accrualLocked) ;

	/**
	 * @return the useAccrualsOnly
	 */
	public Boolean getUseAccrualsOnly() ;

	/**
	 * @param useAccrualsOnly the useAccrualsOnly to set
	 */
	public void setUseAccrualsOnly(Boolean useAccrualsOnly) ;

	/**
	 * @return the generateCosts
	 */
	public Boolean getGenerateCosts() ;

	/**
	 * @param generateCosts the generateCosts to set
	 */
	public void setGenerateCosts(Boolean generateCosts) ;

	/**
	 * @return the costRemarks
	 */
	public String getCostRemarks() ;

	/**
	 * @param costRemarks the costRemarks to set
	 */
	public void setCostRemarks(String costRemarks) ;

	/**
	 * @return the costOther1
	 */
	public String getCostOther1() ;

	/**
	 * @param costOther1 the costOther1 to set
	 */
	public void setCostOther1(String costOther1) ;

	/**
	 * @return the costOther2
	 */
	public String getCostOther2() ;

	/**
	 * @param costOther2 the costOther2 to set
	 */
	public void setCostOther2(String costOther2) ;

	/**
	 * @return the costOther3
	 */
	public String getCostOther3() ;

	/**
	 * @param costOther3 the costOther3 to set
	 */
	public void setCostOther3(String costOther3) ;

	/**
	 * @return the incidentResource
	 */
	public IncidentResource getIncidentResource() ;

	/**
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResource(IncidentResource incidentResource) ;

//	/**
//	 * @return the incidentResourceOthers
//	 */
//	public Collection<IncidentResourceOther> getIncidentResourceOthers() ;
//
//	/**
//	 * @param incidentResourceOthers the incidentResourceOthers to set
//	 */
//	public void setIncidentResourceOthers(
//			Collection<IncidentResourceOther> incidentResourceOthers) ;
	
	/**
	 * @return the incidentResourceOther
	 */
	public IncidentResourceOther getIncidentResourceOther();
	
	/**
	 * @param incidentResourceOther the incidentResourceOther to set
	 */
	public void setIncidentResourceOther(IncidentResourceOther incidentResourceOther);

	/**
	 * @return the costGroup
	 */
	public CostGroup getDefaultCostGroup() ;


	/**
	 * @param costGroup the costGroup to set
	 */
	public void setDefaultCostGroup(CostGroup costGroup) ;


	/**
	 * @return the costGroupId
	 */
	public Long getDefaultCostGroupId();


	/**
	 * @param costGroupId the costGroupId to set
	 */
	public void setDefaultCostGroupId(Long costGroupId) ;

	/**
	 * @return the defaultIncidentShift
	 */
	public IncidentShift getDefaultIncidentShift() ;


	/**
	 * @param defaultIncidentShift the defaultIncidentShift to set
	 */
	public void setDefaultIncidentShift(IncidentShift defaultIncidentShift) ;


	/**
	 * @return the defaultIncidentShiftId
	 */
	public Long getDefaultIncidentShiftId();


	/**
	 * @param defaultIncidentShiftId the defaultIncidentShiftId to set
	 */
	public void setDefaultIncidentShiftId(Long defaultIncidentShiftId);

	/**
	 * @return the generateCostsSys
	 */
	public StringBooleanEnum getGenerateCostsSys();


	/**
	 * @param generateCostsSys the generateCostsSys to set
	 */
	public void setGenerateCostsSys(StringBooleanEnum generateCostsSys);
	
}
