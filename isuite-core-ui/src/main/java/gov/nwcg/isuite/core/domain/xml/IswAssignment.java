package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswAssignment", table = "isw_assignment")
public class IswAssignment {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ASSIGNMENT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "KindTransferableIdentity", alias="kdti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="KindId"
						, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="KIND_ID")
	private String kindTransferableIdentity;

	@XmlTransferField(name = "KindId", sqlname = "KIND_ID", type = "LONG"
						, derived = true, derivedfield = "KindTransferableIdentity")
	private Long kindId;

	@XmlTransferField(name = "RequestNumber", sqlname = "REQUEST_NUMBER", type="STRING")
	private String requestNumber;

	@XmlTransferField(name = "StartDate", sqlname = "START_DATE", type="DATE")
	private Date startDate;

	@XmlTransferField(name = "EndDate", sqlname = "END_DATE", type="DATE")
	private Date endDate;

	@XmlTransferField(name = "Training", sqlname = "IS_TRAINING", type="BOOLEAN")
	private Boolean training;

	@XmlTransferField(name = "ReassignIncidentName", sqlname = "REASSIGN_INCIDENT_NAME", type="STRING")
	private String reassignIncidentName;

	@XmlTransferField(name = "ReassignIncidentNumber", sqlname = "REASSIGN_INCIDENT_NUMBER", type="STRING")
	private String reassignIncidentNumber;

	@XmlTransferField(name = "AssignmentStatus", sqlname = "ASSIGNMENT_STATUS", type="STRING")
	private String assignmentStatus;

	@XmlTransferField(name = "AssignmentTime", type="COMPLEX", target=IswAssignmentTime.class
						,lookupname="AssignmentId", sourcename="Id"
						, cascade=true)
	private IswAssignmentTime assignmentTime;

	@XmlTransferField(name = "TimeAssignAdjust", type="COMPLEX", target=IswTimeAssignAdjust.class
			, lookupname="AssignmentId", sourcename="Id"
				, cascade=true)
	private Collection<IswTimeAssignAdjust> timeAssignAdjusts = new ArrayList<IswTimeAssignAdjust>();

	/**
	 * @return
	 */
	public Long getKindId() {
		return kindId;
	}

	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return
	 */
	public Boolean isTraining() {
		return training;
	}

	/**
	 * @param training
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * @return
	 */
	public String getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * @param assignmentStatus
	 */
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the assignmentTime
	 */
	public IswAssignmentTime getAssignmentTime() {
		return assignmentTime;
	}

	/**
	 * @param assignmentTime
	 *            the assignmentTime to set
	 */
	public void setAssignmentTime(IswAssignmentTime assignmentTime) {
		this.assignmentTime = assignmentTime;
	}

	public String getReassignIncidentName() {
		return reassignIncidentName;
	}

	public void setReassignIncidentName(String reassignIncidentName) {
		this.reassignIncidentName = reassignIncidentName;
	}

	public String getReassignIncidentNumber() {
		return reassignIncidentNumber;
	}

	public void setReassignIncidentNumber(String reassignIncidentNumber) {
		this.reassignIncidentNumber = reassignIncidentNumber;
	}

	/**
	 * @return the training
	 */
	public Boolean getTraining() {
		return training;
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
	 * @return the kindTransferableIdentity
	 */
	public String getKindTransferableIdentity() {
		return kindTransferableIdentity;
	}

	/**
	 * @param kindTransferableIdentity the kindTransferableIdentity to set
	 */
	public void setKindTransferableIdentity(String kindTransferableIdentity) {
		this.kindTransferableIdentity = kindTransferableIdentity;
	}

	public Collection<IswTimeAssignAdjust> getTimeAssignAdjusts() {
		return timeAssignAdjusts;
	}

	public void setTimeAssignAdjusts(
			Collection<IswTimeAssignAdjust> timeAssignAdjusts) {
		this.timeAssignAdjusts = timeAssignAdjusts;
	}

}
