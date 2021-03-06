package gov.nwcg.isuite.framework.core.xmltransferv2.exporter;

import gov.nwcg.isuite.core.domain.xmlv2.*;

public enum TableExportOrderEnum {
    IswlDepartment(IswlDepartment.class,false,false)
	,IswlDepartmentSub(IswlDepartmentSub.class,false,false)
	,IswlRequestCategory(IswlRequestCategory.class,false,false)
	,IswlEventType(IswlEventType.class,false,false)
	,IswlDailyForm(IswlDailyForm.class,false,false)
	,IswlCountry(IswlCountry.class,false,false)
	//,IswlCountrySubdivision(IswlCountrySubdivision.class,false,false)
	,IswlSpecialPay(IswlSpecialPay.class,false,false)
	,IswlGroupCategory(IswlGroupCategory.class,false,false)
	,IswlSubGroupCategory(IswlSubGroupCategory.class,false,false)
	,IswlGraphGroup(IswlGraphGroup.class,false,false)
	,IswlRateGroup(IswlRateGroup.class,false,false)
	,IswlKind1(IswlKind.class,true,false)
	,IswlKind2(IswlKind.class,false,true)
	,IswlJetPort1(IswlJetPort.class,true,false)
	,IswlJetPort2(IswlJetPort.class,false,true)
	,IswlAgency1(IswlAgency.class,true,false)
	,IswlAgency2(IswlAgency.class,false,true)
	,IswOrganization1(IswOrganization.class,true,false)
	,IswOrganization2(IswOrganization.class,false,true)
	,IswlRateClass(IswlRateClass.class,true,false)
	,IswlRateClassRate(IswlRateClassRate.class,true,false)
	,IswAddress(IswAddress.class,true,false)
	,IswAdminOffice(IswAdminOffice.class,true,false)
	,IswIncident(IswIncident.class,true,false)
	,IswIncidentShift(IswIncidentShift.class,true,false)
	,IswIncidentCostDefaults(IswIncidentCostDefaults.class,true,false)
	,IswIncidentPrefsOtherFields1(IswIncidentPrefsOtherFields.class,true,false)
	,IswIncidentPrefsOtherFields2(IswIncidentPrefsOtherFields.class,false,true)
	,IswIapMasterFrequency1(IswIapMasterFrequency.class,true,false)
	,IswIapMasterFrequency2(IswIapMasterFrequency.class,false,true)
	,IswIapPositionItemCode1(IswIapPositionItemCode.class,true,false)
	,IswIapPositionItemCode2(IswIapPositionItemCode.class,false,true)
	,IswAccountCode(IswAccountCode.class,true,false)
	,IswIncidentAccountCode(IswIncidentAccountCode.class,true,false)
	,IswIncidentQSKind(IswIncidentQSKind.class,true,false)
	,IswIncidentGroupQSKind(IswIncidentGroupQSKind.class,false,true)
	,IswIncidentPrefs(IswIncidentPrefs.class,true,false)
	,IswIncidentGroupPrefs(IswIncidentGroupPrefs.class,false,true)
	,IswQuestion1(IswQuestion.class,true,false)
	,IswQuestion2(IswQuestion.class,false,true)
	,IswIncidentQuestion(IswIncidentQuestion.class,true,false)
	,IswIncidentGroupQuestion(IswIncidentGroupQuestion.class,false,true)
	,IswIncidentCostRate1(IswIncidentCostRate.class,true,false)
	,IswIncidentCostRate2(IswIncidentCostRate.class,false,true)
	,IswIncidentCostRateState1(IswIncidentCostRateState.class,true,false)
	,IswIncidentCostRateState2(IswIncidentCostRateState.class,false,true)
	,IswIncidentCostRateStateKind1(IswIncidentCostRateStateKind.class,true,false)
	,IswIncidentCostRateStateKind2(IswIncidentCostRateStateKind.class,false,true)
	,IswIncidentCostRateKind1(IswIncidentCostRateKind.class,true,false)
	,IswIncidentCostRateKind2(IswIncidentCostRateKind.class,false,true)
	,IswIncidentCostRateOvhd1(IswIncidentCostRateOvhd.class,true,false)
	,IswIncidentCostRateOvhd2(IswIncidentCostRateOvhd.class,false,true)
	,IswCostGroup(IswCostGroup.class,true,false)
	,IswCostGroupAgencyDayShare(IswCostGroupAgencyDayShare.class,true,false)
	,IswCostGroupAgencyDaySharePercentage(IswCostGroupAgencyDaySharePercentage.class,true,false)
	,IswCostGroupDefaultAgencyDaySharePercentage(IswCostGroupDefaultAgencyDaySharePercentage.class,true,false)
	,IswContractor(IswContractor.class,true,false)
	,IswIncidentContractor(IswIncidentContractor.class,true,false)
	,IswContractorAgreement(IswContractorAgreement.class,true,false)
	,IswIapPlan1(IswIapPlan.class,true,false)
	,IswIapPlan2(IswIapPlan.class,false,true)
	,IswIapAttachment1(IswIapAttachment.class,true,false)
	,IswIapAttachment2(IswIapAttachment.class,false,true)
	,IswIapForm2021(IswIapForm202.class,true,false)
	,IswIapForm2022(IswIapForm202.class,false,true)
	,IswIapForm2031(IswIapForm203.class,true,false)
	,IswIapForm2032(IswIapForm203.class,false,true)
	,IswIapBranch1(IswIapBranch.class,true,false)
	,IswIapBranch2(IswIapBranch.class,false,true)
	,IswIapBranchCommSummary1(IswIapBranchCommSummary.class,true,false)
	,IswIapBranchCommSummary2(IswIapBranchCommSummary.class,false,true)
	,IswIapBranchPersonnel1(IswIapBranchPersonnel.class,true,false)
	,IswIapBranchPersonnel2(IswIapBranchPersonnel.class,false,true)
	,IswIapBranchPersonnelRes1(IswIapBranchPersonnelRes.class,true,false)
	,IswIapBranchPersonnelRes2(IswIapBranchPersonnelRes.class,false,true)
	,IswIapBranchRscAssign1(IswIapBranchRscAssign.class,true,false)
	,IswIapBranchRscAssign2(IswIapBranchRscAssign.class,false,true)
	,IswIapForm2051(IswIapForm205.class,true,false)
	,IswIapForm2052(IswIapForm205.class,false,true)
	,IswIapFrequency1(IswIapFrequency.class,true,false)
	,IswIapFrequency2(IswIapFrequency.class,false,true)
	,IswIapForm2061(IswIapForm206.class,true,false)
	,IswIapForm2062(IswIapForm206.class,false,true)
	,IswIapHospital1(IswIapHospital.class,true,false)
	,IswIapHospital2(IswIapHospital.class,false,true)
	,IswIapMedicalAid1(IswIapMedicalAid.class,true,false)
	,IswIapMedicalAid2(IswIapMedicalAid.class,false,true)
	,IswIapAreaLocationCapability1(IswIapAreaLocationCapability.class,true,false)
	,IswIapAreaLocationCapability2(IswIapAreaLocationCapability.class,false,true)
	,IswIapRemoteCampLocation1(IswIapRemoteCampLocation.class,true,false)
	,IswIapRemoteCampLocation2(IswIapRemoteCampLocation.class,false,true)
	,IswIapForm2201(IswIapForm220.class,true,false)
	,IswIapForm2202(IswIapForm220.class,false,true)
	,IswIapAircraft1(IswIapAircraft.class,true,false)
	,IswIapAircraft2(IswIapAircraft.class,false,true)
	,IswIapAircraftFrequency1(IswIapAircraftFrequency.class,true,false)
	,IswIapAircraftFrequency2(IswIapAircraftFrequency.class,false,true)
	,IswIapAircraftTask1(IswIapAircraftTask.class,true,false)
	,IswIapAircraftTask2(IswIapAircraftTask.class,false,true)
	,IswIapPersonnel1(IswIapPersonnel.class,true,false)
	,IswIapPersonnel2(IswIapPersonnel.class,false,true)
	,IswIapPersonnelRes1(IswIapPersonnelRes.class,true,false)
	,IswIapPersonnelRes2(IswIapPersonnelRes.class,false,true)
	,IswFinancialExport1(IswFinancialExport.class,true,false)
	,IswFinancialExport2(IswFinancialExport.class,false,true)
	,IswResource(IswResource.class,true,false)
	,IswResourceKind(IswResourceKind.class,true,false)
	,IswResourceMobilization(IswResourceMobilization.class,true,false)
	,IswCostData(IswCostData.class,true,false)
	,IswIncidentResource(IswIncidentResource.class,true,false)
	,IswResourceOther(IswResourceOther.class,true,false)
	,IswIncidentResourceOther(IswIncidentResourceOther.class,true,false)
	,IswAirTravel(IswAirTravel.class,true,false)
	,IswWorkPeriod(IswWorkPeriod.class,true,false)
	,IswWorkPeriodOvernightStayInfo(IswWorkPeriodOvernightStayInfo.class,true,false)
	,IswWorkPeriodQuestionValue(IswWorkPeriodQuestionValue.class,true,false)
	,IswIncidentResourceDailyCost(IswIncidentResourceDailyCost.class,true,false)
	,IswAssignment(IswAssignment.class,true,false)
	,IswWorkPeriodAssignment(IswWorkPeriodAssignment.class,true,false)
	,IswAssignmentTime(IswAssignmentTime.class,true,false)
	,IswAdPaymentInfo(IswAdPaymentInfo.class,true,false)
	,IswContractorPaymentInfo(IswContractorPaymentInfo.class,true,false)
	,IswContractorRate(IswContractorRate.class,true,false)
	,IswContrPayinfoRate(IswContrPayinfoRate.class,true,false)
	,IswAssignmentTimePost(IswAssignmentTimePost.class,true,false)
	,IswTimeAssignAdjust(IswTimeAssignAdjust.class,true,false)
	,IswReport(IswReport.class,true,false)
	,IswTimeInvoice(IswTimeInvoice.class,true,false)
	,IswAssignmentTimePostInvoice(IswAssignmentTimePostInvoice.class,true,false)
	,IswTimeAssignAdjustInvoice(IswTimeAssignAdjustInvoice.class,true,false)
	,IswResourceInvoice(IswResourceInvoice.class,true,false)
	,IswCostAccrualExtract1(IswCostAccrualExtract.class,true,false)
	,IswCostAccrualExtract2(IswCostAccrualExtract.class,false,true)
	,IswCostAccrualExtractRsc1(IswCostAccrualExtractRsc.class,true,false)
	,IswCostAccrualExtractRsc2(IswCostAccrualExtractRsc.class,false,true)
	,IswProjection(IswProjection.class,true,false)
	,IswProjectionItem(IswProjectionItem.class,true,false)
	,IswProjectionItemWorksheet(IswProjectionItemWorksheet.class,true,false)
	,IswIncidentGroup(IswIncidentGroup.class,false,true)
	,IswIncidentGroupIncident(IswIncidentGroupIncident.class,false,true)
	,IswlComplexity(IswlComplexity.class,true,false)
	,IswlRecommendation(IswlRecommendation.class,true,false)
	,IswlFuelType(IswlFuelType.class,true,false)
	,IswlPriorityProgram(IswlPriorityProgram.class,true,false)
	,IswlPriorityProgram2(IswlPriorityProgram.class,false,true)
	,IswIncidentFuelType(IswIncidentFuelType.class,true,false)
	,IswResourceHomeUnitContact(IswResourceHomeUnitContact.class,true,false)
	,IswResourceTraining(IswResourceTraining.class,true,false)
	,IswRscTrainingFuelType(IswRscTrainingFuelType.class,true,false)
	,IswRscTrainingObjective(IswRscTrainingObjective.class,true,false)
	,IswRscTrainingTrainer(IswRscTrainingTrainer.class,true,false)
	,IswTrainingContact(IswTrainingContact.class,true,false)
	,IswTrainingSettings(IswTrainingSettings.class,true,false)
	,IswTrainingSettings2(IswTrainingSettings.class,false,true)
	,IswTrainingSetFuelType(IswTrainingSetFuelType.class,true,false)
	;
	private Class tblClass;
	private boolean filterIncident;
	private boolean filterGroup;
	
	TableExportOrderEnum(Class cls,boolean filterInc, boolean filterGrp){
		this.tblClass=cls;
		this.filterIncident=filterInc;
		this.filterGroup=filterGrp;
	}

	public Class getTblClass() {
		return tblClass;
	}

	public boolean getFilterIncident(){
		return this.filterIncident;
	}

	public boolean getFilterGroup(){
		return this.filterGroup;
	}
}
