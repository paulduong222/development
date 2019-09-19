package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.RateAreaEnum;

import java.util.Date;

public interface AdPaymentInfo extends Persistable {

	/**
	 * @return the rateArea
	 */
	public RateArea getRateArea();

	/**
	 * @param rateArea the rateArea to set
	 */
	public void setRateArea(RateArea rateArea);

	/**
	 * @return the rateAreaId
	 */
	public Long getRateAreaId() ;

	/**
	 * @param rateAreaId the rateAreaId to set
	 */
	public void setRateAreaId(Long rateAreaId) ;

	/**
	 * @return the rateClassRate
	 */
	public RateClassRate getRateClassRate() ;

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
	 * @return the assignmentTime
	 */
	public AssignmentTime getAssignmentTime() ;

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
	 * @return the initialEmp
	 */
	public Boolean getInitialEmp() ;

	/**
	 * @param initialEmp the initialEmp to set
	 */
	public void setInitialEmp(Boolean initialEmp) ;

	/**
	 * @return the returnTravel
	 */
	public Boolean getReturnTravel() ;

	/**
	 * @param returnTravel the returnTravel to set
	 */
	public void setReturnTravel(Boolean returnTravel) ;

	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() ;

	/**
	 * @param pointOfHire the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) ;

	/**
	 * @return the ssn
	 */
	public String getSsn();

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn);	
	/**
	 * @return the eci
	 */
	public String getEci();

	/**
	 * @param ssn the eci to set
	 */
	public void setEci(String eci);	
	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() ;

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);

	public Organization getPointOfHireOrg() ;
	
	public void setPointOfHireOrg(Organization pointOfHireOrg) ;

	public Long getPointOfHireId();

	public void setPointOfHireId(Long pointOfHireId);

	public RateAreaEnum getRateAreaName();

	public void setRateAreaName(RateAreaEnum rateAreaName);

	public Integer getRateYear();

	public void setRateYear(Integer rateYear);
	
}
