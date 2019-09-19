package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIapForm220", table="isw_iap_form_220")
public class IswIapForm220 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_220", type="LONG")
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

	@XmlTransferField(name = "PreparedDate", sqlname="PREPARED_DATE", type="DATE")
	private Date preparedDate;
	
	@XmlTransferField(name = "IsFormLocked", sqlname="IS_FORM_LOCKED", type="STRING")
	private String isFormLocked;

	@XmlTransferField(name = "Sunset", sqlname="SUNSET", type="STRING")
	private String sunset;

	@XmlTransferField(name = "Sunrise", sqlname="SUNRISE", type="STRING")
	private String sunrise;
	
	@XmlTransferField(name = "Medivac", sqlname="MEDIVAC", type="STRING")
	private String medivac;
	
	@XmlTransferField(name = "NewIncident", sqlname="NEW_INCIDENT", type="STRING")
	private String newIncident;

	@XmlTransferField(name = "ReadyAlertAircraft", sqlname="READY_ALERT_AIRCRAFT", type="STRING")
	private String readyAlertAircraft;

	@XmlTransferField(name = "TfrNbr", sqlname="TFR_NBR", type="STRING")
	private String tfrNbr;
	
	@XmlTransferField(name = "Altitude", sqlname="ALTITUDE", type="STRING")
	private String altitude;
	
	@XmlTransferField(name = "CentralPoint", sqlname="CENTRAL_POINT", type="STRING")
	private String centralPoint;

	@XmlTransferField(name = "Remarks", sqlname="REMARKS", type="STRING")
	private String remarks;

	@XmlTransferField(name = "IapAircraft", type="COMPLEX", target=IswIapAircraft.class
			, lookupname="IapForm220Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapAircraft> iapAircrafts = new ArrayList<IswIapAircraft>();
	
	@XmlTransferField(name = "IapAircraftFrequency", type="COMPLEX", target=IswIapAircraftFrequency.class
			, lookupname="IapForm220Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapAircraftFrequency> iapAircraftFrequencys = new ArrayList<IswIapAircraftFrequency>();

	@XmlTransferField(name = "IapAircraftTask", type="COMPLEX", target=IswIapAircraftTask.class
			, lookupname="IapForm220Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapAircraftTask> iapAircraftTasks = new ArrayList<IswIapAircraftTask>();
	
	@XmlTransferField(name = "IapPersonnel", type="COMPLEX", target=IswIapPersonnel.class
			, lookupname="IapForm220Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapPersonnel> iapPersonnels = new ArrayList<IswIapPersonnel>();

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
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
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
	 * @return the sunset
	 */
	public String getSunset() {
		return sunset;
	}

	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	/**
	 * @return the sunrise
	 */
	public String getSunrise() {
		return sunrise;
	}

	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	/**
	 * @return the medivac
	 */
	public String getMedivac() {
		return medivac;
	}

	/**
	 * @param medivac the medivac to set
	 */
	public void setMedivac(String medivac) {
		this.medivac = medivac;
	}

	/**
	 * @return the newIncident
	 */
	public String getNewIncident() {
		return newIncident;
	}

	/**
	 * @param newIncident the newIncident to set
	 */
	public void setNewIncident(String newIncident) {
		this.newIncident = newIncident;
	}

	/**
	 * @return the readyAlertAircraft
	 */
	public String getReadyAlertAircraft() {
		return readyAlertAircraft;
	}

	/**
	 * @param readyAlertAircraft the readyAlertAircraft to set
	 */
	public void setReadyAlertAircraft(String readyAlertAircraft) {
		this.readyAlertAircraft = readyAlertAircraft;
	}

	/**
	 * @return the tfrNbr
	 */
	public String getTfrNbr() {
		return tfrNbr;
	}

	/**
	 * @param tfrNbr the tfrNbr to set
	 */
	public void setTfrNbr(String tfrNbr) {
		this.tfrNbr = tfrNbr;
	}

	/**
	 * @return the altitude
	 */
	public String getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the centralPoint
	 */
	public String getCentralPoint() {
		return centralPoint;
	}

	/**
	 * @param centralPoint the centralPoint to set
	 */
	public void setCentralPoint(String centralPoint) {
		this.centralPoint = centralPoint;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the iapAircrafts
	 */
	public Collection<IswIapAircraft> getIapAircrafts() {
		return iapAircrafts;
	}

	/**
	 * @param iapAircrafts the iapAircrafts to set
	 */
	public void setIapAircrafts(Collection<IswIapAircraft> iapAircrafts) {
		this.iapAircrafts = iapAircrafts;
	}

	/**
	 * @return the iapAircraftFrequencys
	 */
	public Collection<IswIapAircraftFrequency> getIapAircraftFrequencys() {
		return iapAircraftFrequencys;
	}

	/**
	 * @param iapAircraftFrequencys the iapAircraftFrequencys to set
	 */
	public void setIapAircraftFrequencys(
			Collection<IswIapAircraftFrequency> iapAircraftFrequencys) {
		this.iapAircraftFrequencys = iapAircraftFrequencys;
	}

	/**
	 * @return the iapAircraftTasks
	 */
	public Collection<IswIapAircraftTask> getIapAircraftTasks() {
		return iapAircraftTasks;
	}

	/**
	 * @param iapAircraftTasks the iapAircraftTasks to set
	 */
	public void setIapAircraftTasks(Collection<IswIapAircraftTask> iapAircraftTasks) {
		this.iapAircraftTasks = iapAircraftTasks;
	}

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IswIapPersonnel> getIapPersonnels() {
		return iapPersonnels;
	}

	/**
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IswIapPersonnel> iapPersonnels) {
		this.iapPersonnels = iapPersonnels;
	}

	
}
