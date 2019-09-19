package gov.nwcg.isuite.core.domain.xml;

import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswRestrictedIncidentUser", table = "isw_restricted_incident_user")
public class IswRestrictedIncidentUser {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESTRICTED_INCIDENT_USER", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Incident", type="COMPLEX", target=IswIncident.class
				, lookupname="ID", sourcename="IncidentId")
	private IswIncident incident;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
			,derived=true, derivedfield="Incident")
	private Long incidentId;
	
	@XmlTransferField(name = "User", type="COMPLEX", target=IswUser.class
				, lookupname="ID", sourcename="UserId")
	private IswUser user;
	
	@XmlTransferField(name = "UserId", sqlname="USER_ID", type="LONG"
			,derived=true, derivedfield="User")
	private Long userId;

	@XmlTransferField(name = "UserType", sqlname = "USER_TYPE", type="STRING")
	private String userType;

	@XmlTransferField(name = "AccessEndDate", sqlname = "ACCESS_END_DATE", type="DATE")
	private Date accessEndDate;

	@XmlTransferField(name = "DefaultCheckinDate", sqlname = "DEFAULT_CHECKIN_DATE", type="DATE")
	private Date defaultCheckinDate;

	@XmlTransferField(name = "DefaultCheckinType", sqlname = "DEFAULT_CHECKIN_TYPE", type="STRING")
	private String defaultCheckinType;
	
	
	public IswRestrictedIncidentUser(){
	}

	/**
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public IswIncident getIncident() {
		return this.incident;
	}

	/**
	 * @return
	 */
	public Long getIncidentId() {
		return this.incidentId;
	}

	/**
	 * @return
	 */
	public IswUser getUser() {
		return this.user;
	}

	/**
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * @return
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * @param incident
	 */
	public void setIncident(IswIncident incident) {
		this.incident = incident;
	}

	/**
	 * @param incidentId
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @param user
	 */
	public void setUser(IswUser user) {
		this.user = user;
	}

	/**
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @param userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return
	 */
	public Date getAccessEndDate() {
		return this.accessEndDate;
	}

	/**
	 * @param endDate
	 */
	public void setAccessEndDate(Date endDate) {
		this.accessEndDate = endDate;
	}

	public Date getDefaultCheckinDate() {
		return defaultCheckinDate;
	}

	public void setDefaultCheckinDate(Date defaultCheckinDate) {
		this.defaultCheckinDate = defaultCheckinDate;
	}

	public String getDefaultCheckinType() {
		return defaultCheckinType;
	}

	public void setDefaultCheckinType(String defaultCheckinType) {
		this.defaultCheckinType = defaultCheckinType;
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
