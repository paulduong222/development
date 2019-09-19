package gov.nwcg.isuite.core.filter.impl;

import java.util.Date;

import gov.nwcg.isuite.core.filter.DataAuditTrackingFilter;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.util.DateUtil;

/**
 * Filter for a user.
 * @author jbrose
 */
public class DataAuditTrackingFilterImpl extends FilterImpl implements DataAuditTrackingFilter {

	private static final long serialVersionUID = 6872321116677638024L;
	
	private Date startDate;
	private Date endDate;
	private String auditEvent;
	private String homeUnit;
	private String primaryDispatchCenter;
	private String lastName;
	private String firstName;
	private String loginName;
	private Date createdDate;
    private String crypticDateFilterCode;
	private String[] auditEventType;
	private String modifiedBy;
	
	private String backupFilename;
	private String backupFilepath;
	private String backupType;

	private DateTransferVo startDateVo;
	private DateTransferVo endDateVo;
	public DataAuditTrackingFilterImpl() {
	}

	@Override
	public Date getStartDate() {
		if(DateUtil.hasValue(startDate)){
			try{
				startDate = DateUtil.addMilitaryTimeToDate(startDate, "0001");
				return startDate;
			}catch(Exception e){
			}
		}
		return null;
	}

	@Override
	public Date getEndDate() {
		if(DateUtil.hasValue(endDate)){
			try{
				endDate = DateUtil.addMilitaryTimeToDate(endDate, "2359");
				return endDate;
			}catch(Exception e){
			}
		}
		return null;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAuditEventType(String[] auditEventType) {
		this.auditEventType = auditEventType;
	}

	public String[] getAuditEventType() {
		return this.auditEventType;
	}

	public String getAuditEvent() {
		return auditEvent;
	}

	public void setAuditEvent(String auditEvent) {
		this.auditEvent = auditEvent;
	}

	public String getHomeUnit() {
		return homeUnit;
	}

	public void setHomeUnit(String homeUnit) {
		this.homeUnit = homeUnit;
	}

	public String getPrimaryDispatchCenter() {
		return primaryDispatchCenter;
	}

	public void setPrimaryDispatchCenter(String primaryDispatchCenter) {
		this.primaryDispatchCenter = primaryDispatchCenter;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCrypticDateFilterCode() {
		return crypticDateFilterCode;
	}

	public void setCrypticDateFilterCode(String crypticDateFilterCode) {
		this.crypticDateFilterCode = crypticDateFilterCode;
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

	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}

}
