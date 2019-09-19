package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

public interface IapForm220 extends Persistable {

	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan();

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan);

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId();

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId);

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy();

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy);

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition();

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition);

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate();

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate);

	/**
	 * @return the isFormLocked
	 */
	public StringBooleanEnum getIsFormLocked();

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(StringBooleanEnum isFormLocked);

	/**
	 * @return the sunset
	 */
	public String getSunset();

	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(String sunset);

	/**
	 * @return the sunrise
	 */
	public String getSunrise();

	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(String sunrise);

	/**
	 * @return the medivac
	 */
	public String getMedivac();

	/**
	 * @param medivac the medivac to set
	 */
	public void setMedivac(String medivac);

	/**
	 * @return the readyAlertAircraft
	 */
	public String getReadyAlertAircraft();

	/**
	 * @param readyAlertAircraft the readyAlertAircraft to set
	 */
	public void setReadyAlertAircraft(String readyAlertAircraft);

	/**
	 * @return the tfrNbr
	 */
	public String getTfrNbr();

	/**
	 * @param tfrNbr the tfrNbr to set
	 */
	public void setTfrNbr(String tfrNbr);

	/**
	 * @return the altitude
	 */
	public String getAltitude();

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(String altitude);

	/**
	 * @return the centralPoint
	 */
	public String getCentralPoint();

	/**
	 * @param centralPoint the centralPoint to set
	 */
	public void setCentralPoint(String centralPoint);

	/**
	 * @return the remarks
	 */
	public String getRemarks();

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks);

	/**
	 * @return the iapAircrafts
	 */
	public Collection<IapAircraft> getIapAircrafts();

	/**
	 * @param iapAircrafts the iapAircrafts to set
	 */
	public void setIapAircrafts(Collection<IapAircraft> iapAircrafts);

	/**
	 * @return the iapAircraftFrequencies
	 */
	public Collection<IapAircraftFrequency> getIapAircraftFrequencies();

	/**
	 * @param iapAircraftFrequencies the iapAircraftFrequencies to set
	 */
	public void setIapAircraftFrequencies(Collection<IapAircraftFrequency> iapAircraftFrequencies);

	/**
	 * @return the iapAircraftTasks
	 */
	public Collection<IapAircraftTask> getIapAircraftTasks();

	/**
	 * @param iapAircraftTasks the iapAircraftTasks to set
	 */
	public void setIapAircraftTasks(Collection<IapAircraftTask> iapAircraftTasks);

	/**
	 * @return the newIncident
	 */
	public String getNewIncident();

	/**
	 * @param newIncident the newIncident to set
	 */
	public void setNewIncident(String newIncident);

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IapPersonnel> getIapPersonnels();

	/**
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IapPersonnel> iapPersonnels);
	
}