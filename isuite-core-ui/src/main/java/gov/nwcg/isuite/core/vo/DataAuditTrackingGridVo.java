package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

public class DataAuditTrackingGridVo extends AbstractVo {
	private String loginName;
	private String firstName;
	private String lastName;
	private String homeUnit;
	private String primaryDispatchCenter;
	private String auditEvent;
	private Date eventDate;
	private String eventTime;
	private String modifiedBy;
	
	private String backupFilename;
	private String backupFilepath;
	private String backupType;

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setHomeUnit(String homeUnit) {
		this.homeUnit = homeUnit;
	}

	public String getHomeUnit() {
		return homeUnit;
	}

	public void setPrimaryDispatchCenter(String primaryDispatchCenter) {
		this.primaryDispatchCenter = primaryDispatchCenter;
	}

	public String getPrimaryDispatchCenter() {
		return primaryDispatchCenter;
	}

	public void setAuditEvent(String auditEvent) {
		this.auditEvent = auditEvent;
	}

	public String getAuditEvent() {
		return auditEvent;
	}

	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
		if(null != eventDate){
			String time=DateUtil.toMilitaryTimeColon(eventDate);
			this.eventTime=time;
		}
	}

	/**
	 * @return the eventTime
	 */
	public String getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the backupFilename
	 */
	public String getBackupFilename() {
		return backupFilename;
	}

	/**
	 * @param backupFilename the backupFilename to set
	 */
	public void setBackupFilename(String backupFilename) {
		this.backupFilename = backupFilename;
	}

	/**
	 * @return the backupFilepath
	 */
	public String getBackupFilepath() {
		return backupFilepath;
	}

	/**
	 * @param backupFilepath the backupFilepath to set
	 */
	public void setBackupFilepath(String backupFilepath) {
		this.backupFilepath = backupFilepath;
	}

	/**
	 * @return the backupType
	 */
	public String getBackupType() {
		return backupType;
	}

	/**
	 * @param backupType the backupType to set
	 */
	public void setBackupType(String backupType) {
		this.backupType = backupType;
	}
}
