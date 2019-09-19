package gov.nwcg.isuite.core.domain.views;

import java.math.BigDecimal;
import java.util.Date;

public interface CRPersonTimeView {

	public Long getResourceId();

	public void setResourceId(Long resourceId);

	public String getAccrual();

	public void setAccrual(String accrual);

	public Date getActualReleaseDate();

	public void setActualReleaseDate(Date actualReleaseDate);

	public String getActualReleaseRemarks();

	public void setActualReleaseRemarks(String actualReleaseRemarks);

	public Date getActualReleaseTime();

	public void setActualReleaseTime(Date actualReleaseTime);

	public String getAdminOffice();

	public void setAdminOffice(String adminOffice);

	public String getAgency();

	public void setAgency(String agency);

	public String getAgreementNumber();

	public void setAgreementNumber(String agreementNumber);

	public String getAirTravelToDispatch();

	public void setAirTravelToDispatch(String airTravelToDispatch);

	public String getAirline();

	public void setAirline(String airline);

	public Date getAssignDate();

	public void setAssignDate(Date assignDate);

	public Date getBeginningDate();

	public void setBeginningDate(Date beginningDate);

	public Date getCheckInDate();

	public void setCheckInDate(Date checkInDate);

	public Date getCheckInTime();

	public void setCheckInTime(Date checkInTime);

	public String getCheckoutFormPrinted();

	public void setCheckoutFormPrinted(String checkoutFormPrinted);

	public String getClassName();

	public void setClassName(String className);

	public String getStrikeTeamTaskForce();

	public void setStrikeTeamTaskForce(String strikeTeamTaskForce);

	public String getContracted();

	public void setContracted(String contracted);

	public String getContractorAddress();

	public void setContractorAddress(String contractorAddress);

	public String getContractorName();

	public void setContractorName(String contractorName);

	public String getContractorTelephone();

	public void setContractorTelephone(String contractorTelephone);

	public String getCostUOM();

	public void setCostUOM(String costUOM);

	public String getDemobilizationCity();

	public void setDemobilizationCity(String demobilizationCity);

	public Date getDemobilizationDate();

	public void setDemobilizationDate(Date demobilizationDate);

	public String getDemobilizationState();

	public void setDemobilizationState(String demobilizationState);

	public String getDescription1();

	public void setDescription1(String description1);

	public String getDescription2();

	public void setDescription2(String description2);

	public String getDispatchNotifiedOfActualRelease();

	public void setDispatchNotifiedOfActualRelease(
			String dispatchNotifiedOfActualRelease);

	public String getDispatchNotifiedOfTentativeRelease();

	public void setDispatchNotifiedOfTentativeRelease(
			String dispatchNotifiedOfTentativeRelease);

	public String getDunsNumber();

	public void setDunsNumber(String dunsNumber);

	public String getEmploymentType();

	public void setEmploymentType(String employmentType);

	public Date getEstimatedDateOfArrival();

	public void setEstimatedDateOfArrival(Date estimatedDateOfArrival);

	public Date getEstimatedTimeOfArrival();

	public void setEstimatedTimeOfArrival(Date estimatedTimeOfArrival);

	public Date getExpirationDate();

	public void setExpirationDate(Date expirationDate);

	public String getFaxNumber();

	public void setFaxNumber(String faxNumber);

	public Date getFirstDayOfWork();

	public void setFirstDayOfWork(Date firstDayOfWork);

	public String getFirstName();

	public void setFirstName(String firstName);

	public Integer getFlightTime();

	public void setFlightTime(Integer flightTime);

	public BigDecimal getGuaranteeRate();

	public void setGuaranteeRate(BigDecimal guaranteeRate);

	public Date getHireDate();

	public void setHireDate(Date hireDate);

	public Date getHireTime();

	public void setHireTime(Date hireTime);

	public Long getIncidentId();

	public void setIncidentId(Long incidentId);

	public String getIncidentJetport();

	public void setIncidentJetport(String incidentJetport);

	public String getIncidentName();

	public void setIncidentName(String incidentName);

	public String getIncidentNumber();

	public void setIncidentNumber(String incidentNumber);

	public String getInvoiceRemarks();

	public void setInvoiceRemarks(String invoiceRemarks);

	public String getRostered();

	public void setRostered(String rostered);

	public String getItemCode();

	public void setItemCode(String itemCode);

	public String getItemCodeDescription();

	public void setItemCodeDescription(String itemCodeDescription);

	public String getItineraryReceived();

	public void setItineraryReceived(String itineraryReceived);

	public String getJetPort();

	public void setJetPort(String jetPort);

	public String getLastName();

	public void setLastName(String lastName);

	public String getLeader();

	public void setLeader(String leader);

	public String getLeaderName();

	public void setLeaderName(String leaderName);

	public Long getLengthOfAssignment();

	public void setLengthOfAssignment(Long lengthOfAssignment);

	public Integer getResourceLevel();

	public void setResourceLevel(Integer resourceLevel);

	public String getMethodOfTravel();

	public void setMethodOfTravel(String methodOfTravel);

	public Date getMobilizationDate();

	public void setMobilizationDate(Date mobilizationDate);

	public String getNameOnId();

	public void setNameOnId(String nameOnId);

	public Long getNumberOfPersonnel();

	public void setNumberOfPersonnel(Long numberOfPersonnel);

	public String getOther1();

	public void setOther1(String other1);

	public String getOther2();

	public void setOther2(String other2);

	public String getOther3();

	public void setOther3(String other3);

	public String getOverHead();

	public void setOverHead(String overHead);
	
	public void setLineOverHead(String lineOverHead);
	
	public String getLineOverHead();

	public Long getParentId();

	public void setParentId(Long parentId);

	public String getPaymentAgency();

	public void setPaymentAgency(String paymentAgency);

	public String getPersonAddressInfo();

	public void setPersonAddressInfo(String personAddressInfo);

	public String getPhoneNumber();

	public void setPhoneNumber(String phoneNumber);

	public String getPointOfHire();

	public void setPointOfHire(String pointOfHire);

	public BigDecimal getRate();

	public void setRate(BigDecimal rate);

	public String getRateType();

	public void setRateType(String rateType);

	public String getReassignment();

	public void setReassignment(String reassignment);

	public String getRemarksCost();

	public void setRemarksCost(String remarksCost);

	public String getRemarksPlans();

	public void setRemarksPlans(String remarksPlans);

	public String getRequestCategory();

	public void setRequestCategory(String requestCategory);

	public String getRequestNumber();

	public void setRequestNumber(String requestNumber);

	public String getResourceName();

	public void setResourceName(String resourceName);

	public String getReturnTravelMethod();

	public void setReturnTravelMethod(String returnTravelMethod);

	public String getSectionCode();

	public void setSectionCode(String sectionCode);
	
	public String getSectionDescription();
	
	public void setSectionDescription(String sectionDescription);

	public String getSpecialInstructions();

	public void setSpecialInstructions(String specialInstructions);

	public BigDecimal getSpcialRate();

	public void setSpcialRate(BigDecimal spcialRate);

	public String getStatus();

	public void setStatus(String status);

	public String getTelephone();

	public void setTelephone(String telephone);

	public Date getTentativeReleaseDate();

	public void setTentativeReleaseDate(Date tentativeReleaseDate);

	public String getTentativeReleaseRemarks();

	public void setTentativeReleaseRemarks(String tentativeReleaseRemarks);

	public Date getTentativeReleaseTime();

	public void setTentativeReleaseTime(Date tentativeReleaseTime);

	public String getTimeUOM();

	public void setTimeUOM(String timeUOM);

	public String getTrainee();

	public void setTrainee(String trainee);

	public Integer getTravelHours();

	public void setTravelHours(Integer travelHours);

	public Integer getTravelMinutes();

	public void setTravelMinutes(Integer travelMinutes);

	public String getUniqueNameVin();

	public void setUniqueNameVin(String uniqueNameVin);

	public String getUnitId();

	public void setUnitId(String unitId);

}