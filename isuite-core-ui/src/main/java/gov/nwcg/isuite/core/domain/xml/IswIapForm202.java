package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapForm202", table="isw_iap_form_202")
public class IswIapForm202 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_202", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IapPlanTransferableIdentity", alias="planti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapPlanId"
		, disjoined=true, disjoinedtable="isw_iap_plan", disjoinedfield="transferable_identity",disjoinedsource="iap_plan_id")
	private String iapPlanTransferableIdentity;

	@XmlTransferField(name = "IapPlanId", sqlname="IAP_PLAN_ID", type="LONG"
		,derived=true,derivedfield="IapPlanTransferableIdentity")
	private Long iapPlanId;

	@XmlTransferField(name = "PreparedBy", sqlname="PREPARED_BY", type="STRING")
	private String preparedBy;

	@XmlTransferField(name = "PreparedByPosition", sqlname="PREPARED_BY_POS", type="STRING")
	private String preparedByPosition;

	@XmlTransferField(name = "ApprovedBy", sqlname="APPROVED_BY", type="STRING")
	private String approvedBy;
	
	@XmlTransferField(name = "ApprovedDate", sqlname="APPROVED_DATE", type="DATE")
	private Date approvedDate;

	@XmlTransferField(name = "Objectives", sqlname="OBJECTIVES", type="STRING", ischardata=true)
	private String objectives;
	
	@XmlTransferField(name = "CommandEmphasis", sqlname="COMMAND_EMPHASIS", type="STRING", ischardata=true)
	private String commandEmphasis;

	@XmlTransferField(name = "GeneralSituationalAwareness", sqlname="GEN_SIT_AWARENESS", type="STRING", ischardata=true)
	private String generalSituationalAwareness;

	@XmlTransferField(name = "SiteSafetyPlanRqrd", sqlname="SITE_SAFETY_PLAN_RQRD", type="STRING")
	private String siteSafetyPlanRqrd;
	
	@XmlTransferField(name = "SiteSafetyPlanLoc", sqlname="SITE_SAFETY_PLAN_LOC", type="STRING")
	private String siteSafetyPlanLoc;

	@XmlTransferField(name = "IsForm202Attached", sqlname="IS_FORM_202_ATTACHED", type="STRING")
	private String isForm202Attached;
	
	@XmlTransferField(name = "IsForm203Attached", sqlname="IS_FORM_203_ATTACHED", type="STRING")
	private String isForm203Attached;
	
	@XmlTransferField(name = "IsForm204Attached", sqlname="IS_FORM_204_ATTACHED", type="STRING")
	private String isForm204Attached;
	
	@XmlTransferField(name = "IsForm205Attached", sqlname="IS_FORM_205_ATTACHED", type="STRING")
	private String isForm205Attached;
	
	@XmlTransferField(name = "IsForm205aAttached", sqlname="IS_FORM_205A_ATTACHED", type="STRING")
	private String isForm205aAttached;
	
	@XmlTransferField(name = "IsForm206Attached", sqlname="IS_FORM_206_ATTACHED", type="STRING")
	private String isForm206Attached;
	
	@XmlTransferField(name = "IsForm207Attached", sqlname="IS_FORM_207_ATTACHED", type="STRING")
	private String isForm207Attached;
	
	@XmlTransferField(name = "IsForm208Attached", sqlname="IS_FORM_208_ATTACHED", type="STRING")
	private String isForm208Attached;
	
	@XmlTransferField(name = "IsIncidentMapAttached", sqlname="IS_INCIDENT_MAP_ATTACHED", type="STRING")
	private String isIncidentMapAttached;
	
	@XmlTransferField(name = "IsWeatherForecastAttached", sqlname="IS_WEATHER_FORECAST_ATTACHED", type="STRING")
	private String isWeatherForecastAttached;

	@XmlTransferField(name = "IsOtherFormAttached1", sqlname="IS_OTHER_FORM_ATTACHED_1", type="STRING")
	private String isOtherFormAttached1;
	
	@XmlTransferField(name = "IsOtherFormAttached2", sqlname="IS_OTHER_FORM_ATTACHED_2", type="STRING")
	private String isOtherFormAttached2;

	@XmlTransferField(name = "IsOtherFormAttached3", sqlname="IS_OTHER_FORM_ATTACHED_3", type="STRING")
	private String isOtherFormAttached3;

	@XmlTransferField(name = "IsOtherFormAttached4", sqlname="IS_OTHER_FORM_ATTACHED_4", type="STRING")
	private String isOtherFormAttached4;

	@XmlTransferField(name = "OtherFormName1", sqlname="OTHER_FORM_NAME_1", type="STRING")
	private String otherFormName1;
	
	@XmlTransferField(name = "OtherFormName2", sqlname="OTHER_FORM_NAME_2", type="STRING")
	private String otherFormName2;

	@XmlTransferField(name = "OtherFormName3", sqlname="OTHER_FORM_NAME_3", type="STRING")
	private String otherFormName3;
	
	@XmlTransferField(name = "OtherFormName4", sqlname="OTHER_FORM_NAME_4", type="STRING")
	private String otherFormName4;
	
	@XmlTransferField(name = "IsFormLocked", sqlname="IS_FORM_LOCKED", type="STRING")
	private String isFormLocked;

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

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the objectives
	 */
	public String getObjectives() {
		return objectives;
	}

	/**
	 * @param objectives the objectives to set
	 */
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	/**
	 * @return the commandEmphasis
	 */
	public String getCommandEmphasis() {
		return commandEmphasis;
	}

	/**
	 * @param commandEmphasis the commandEmphasis to set
	 */
	public void setCommandEmphasis(String commandEmphasis) {
		this.commandEmphasis = commandEmphasis;
	}

	/**
	 * @return the generalSituationalAwareness
	 */
	public String getGeneralSituationalAwareness() {
		return generalSituationalAwareness;
	}

	/**
	 * @param generalSituationalAwareness the generalSituationalAwareness to set
	 */
	public void setGeneralSituationalAwareness(String generalSituationalAwareness) {
		this.generalSituationalAwareness = generalSituationalAwareness;
	}

	/**
	 * @return the siteSafetyPlanRqrd
	 */
	public String getSiteSafetyPlanRqrd() {
		return siteSafetyPlanRqrd;
	}

	/**
	 * @param siteSafetyPlanRqrd the siteSafetyPlanRqrd to set
	 */
	public void setSiteSafetyPlanRqrd(String siteSafetyPlanRqrd) {
		this.siteSafetyPlanRqrd = siteSafetyPlanRqrd;
	}

	/**
	 * @return the siteSafetyPlanLoc
	 */
	public String getSiteSafetyPlanLoc() {
		return siteSafetyPlanLoc;
	}

	/**
	 * @param siteSafetyPlanLoc the siteSafetyPlanLoc to set
	 */
	public void setSiteSafetyPlanLoc(String siteSafetyPlanLoc) {
		this.siteSafetyPlanLoc = siteSafetyPlanLoc;
	}

	/**
	 * @return the isForm202Attached
	 */
	public String getIsForm202Attached() {
		return isForm202Attached;
	}

	/**
	 * @param isForm202Attached the isForm202Attached to set
	 */
	public void setIsForm202Attached(String isForm202Attached) {
		this.isForm202Attached = isForm202Attached;
	}

	/**
	 * @return the isForm203Attached
	 */
	public String getIsForm203Attached() {
		return isForm203Attached;
	}

	/**
	 * @param isForm203Attached the isForm203Attached to set
	 */
	public void setIsForm203Attached(String isForm203Attached) {
		this.isForm203Attached = isForm203Attached;
	}

	/**
	 * @return the isForm204Attached
	 */
	public String getIsForm204Attached() {
		return isForm204Attached;
	}

	/**
	 * @param isForm204Attached the isForm204Attached to set
	 */
	public void setIsForm204Attached(String isForm204Attached) {
		this.isForm204Attached = isForm204Attached;
	}

	/**
	 * @return the isForm205Attached
	 */
	public String getIsForm205Attached() {
		return isForm205Attached;
	}

	/**
	 * @param isForm205Attached the isForm205Attached to set
	 */
	public void setIsForm205Attached(String isForm205Attached) {
		this.isForm205Attached = isForm205Attached;
	}

	/**
	 * @return the isForm205aAttached
	 */
	public String getIsForm205aAttached() {
		return isForm205aAttached;
	}

	/**
	 * @param isForm205aAttached the isForm205aAttached to set
	 */
	public void setIsForm205aAttached(String isForm205aAttached) {
		this.isForm205aAttached = isForm205aAttached;
	}

	/**
	 * @return the isForm206Attached
	 */
	public String getIsForm206Attached() {
		return isForm206Attached;
	}

	/**
	 * @param isForm206Attached the isForm206Attached to set
	 */
	public void setIsForm206Attached(String isForm206Attached) {
		this.isForm206Attached = isForm206Attached;
	}

	/**
	 * @return the isForm207Attached
	 */
	public String getIsForm207Attached() {
		return isForm207Attached;
	}

	/**
	 * @param isForm207Attached the isForm207Attached to set
	 */
	public void setIsForm207Attached(String isForm207Attached) {
		this.isForm207Attached = isForm207Attached;
	}

	/**
	 * @return the isForm208Attached
	 */
	public String getIsForm208Attached() {
		return isForm208Attached;
	}

	/**
	 * @param isForm208Attached the isForm208Attached to set
	 */
	public void setIsForm208Attached(String isForm208Attached) {
		this.isForm208Attached = isForm208Attached;
	}

	/**
	 * @return the isIncidentMapAttached
	 */
	public String getIsIncidentMapAttached() {
		return isIncidentMapAttached;
	}

	/**
	 * @param isIncidentMapAttached the isIncidentMapAttached to set
	 */
	public void setIsIncidentMapAttached(String isIncidentMapAttached) {
		this.isIncidentMapAttached = isIncidentMapAttached;
	}

	/**
	 * @return the isWeatherForecastAttached
	 */
	public String getIsWeatherForecastAttached() {
		return isWeatherForecastAttached;
	}

	/**
	 * @param isWeatherForecastAttached the isWeatherForecastAttached to set
	 */
	public void setIsWeatherForecastAttached(String isWeatherForecastAttached) {
		this.isWeatherForecastAttached = isWeatherForecastAttached;
	}

	/**
	 * @return the isOtherFormAttached1
	 */
	public String getIsOtherFormAttached1() {
		return isOtherFormAttached1;
	}

	/**
	 * @param isOtherFormAttached1 the isOtherFormAttached1 to set
	 */
	public void setIsOtherFormAttached1(String isOtherFormAttached1) {
		this.isOtherFormAttached1 = isOtherFormAttached1;
	}

	/**
	 * @return the isOtherFormAttached2
	 */
	public String getIsOtherFormAttached2() {
		return isOtherFormAttached2;
	}

	/**
	 * @param isOtherFormAttached2 the isOtherFormAttached2 to set
	 */
	public void setIsOtherFormAttached2(String isOtherFormAttached2) {
		this.isOtherFormAttached2 = isOtherFormAttached2;
	}

	/**
	 * @return the isOtherFormAttached3
	 */
	public String getIsOtherFormAttached3() {
		return isOtherFormAttached3;
	}

	/**
	 * @param isOtherFormAttached3 the isOtherFormAttached3 to set
	 */
	public void setIsOtherFormAttached3(String isOtherFormAttached3) {
		this.isOtherFormAttached3 = isOtherFormAttached3;
	}

	/**
	 * @return the isOtherFormAttached4
	 */
	public String getIsOtherFormAttached4() {
		return isOtherFormAttached4;
	}

	/**
	 * @param isOtherFormAttached4 the isOtherFormAttached4 to set
	 */
	public void setIsOtherFormAttached4(String isOtherFormAttached4) {
		this.isOtherFormAttached4 = isOtherFormAttached4;
	}

	/**
	 * @return the otherFormName1
	 */
	public String getOtherFormName1() {
		return otherFormName1;
	}

	/**
	 * @param otherFormName1 the otherFormName1 to set
	 */
	public void setOtherFormName1(String otherFormName1) {
		this.otherFormName1 = otherFormName1;
	}

	/**
	 * @return the otherFormName2
	 */
	public String getOtherFormName2() {
		return otherFormName2;
	}

	/**
	 * @param otherFormName2 the otherFormName2 to set
	 */
	public void setOtherFormName2(String otherFormName2) {
		this.otherFormName2 = otherFormName2;
	}

	/**
	 * @return the otherFormName3
	 */
	public String getOtherFormName3() {
		return otherFormName3;
	}

	/**
	 * @param otherFormName3 the otherFormName3 to set
	 */
	public void setOtherFormName3(String otherFormName3) {
		this.otherFormName3 = otherFormName3;
	}

	/**
	 * @return the otherFormName4
	 */
	public String getOtherFormName4() {
		return otherFormName4;
	}

	/**
	 * @param otherFormName4 the otherFormName4 to set
	 */
	public void setOtherFormName4(String otherFormName4) {
		this.otherFormName4 = otherFormName4;
	}

	/**
	 * @return the isFormLocked
	 */
	public String getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(String isFormLocked) {
		this.isFormLocked = isFormLocked;
	}

	/**
	 * @return the iapPlanTransferableIdentity
	 */
	public String getIapPlanTransferableIdentity() {
		return iapPlanTransferableIdentity;
	}

	/**
	 * @param iapPlanTransferableIdentity the iapPlanTransferableIdentity to set
	 */
	public void setIapPlanTransferableIdentity(String iapPlanTransferableIdentity) {
		this.iapPlanTransferableIdentity = iapPlanTransferableIdentity;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

}
