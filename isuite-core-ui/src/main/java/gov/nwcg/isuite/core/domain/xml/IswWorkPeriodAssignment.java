package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswWorkPeriodAssignment", table="isw_work_period_assignment",jointable=true)
public class IswWorkPeriodAssignment {

	@XmlTransferField(name = "AssignmentTransferableIdentity", alias="ati", type="STRING"
		, lookupname="TransferableIdentity", sourcename="AssignmentId"
		, disjoined=true, disjoinedtable="isw_assignment", disjoinedfield="transferable_identity",disjoinedsource="assignment_id")
    private String assignmentTransferableIdentity;
	
	@XmlTransferField(name = "Assignment", type="COMPLEX", target=IswAssignment.class
						, lookupname="Id", sourcename="AssignmentId")
	private IswAssignment assignment;
	
	@XmlTransferField(name = "AssignmentId", sqlname="ASSIGNMENT_ID", type="LONG"
						,derived=true, derivedfield="Assignment"
						, joinkeysecondary=true)
	private Long assignmentId;

	@XmlTransferField(name = "WorkPeriodTransferableIdentity", alias="wpti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="WorkPeriodId"
						, disjoined=true, disjoinedtable="isw_work_period", disjoinedfield="transferable_identity",disjoinedsource="work_period_id")
    private String workPeriodTransferableIdentity;
	
	@XmlTransferField(name = "WorkPeriodId", sqlname="WORK_PERIOD_ID", type="LONG"
						,joinkeyprimary=true)
	private Long workPeriodId;
	

    public IswWorkPeriodAssignment(){
    }

	/**
	 * @return
	 */
	public IswAssignment getAssignment() {
		return assignment;
	}

	/**
	 * @param assignment
	 */
	public void setAssignment(IswAssignment assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	/**
	 * @return the assignmentTransferableIdentity
	 */
	public String getAssignmentTransferableIdentity() {
		return assignmentTransferableIdentity;
	}

	/**
	 * @param assignmentTransferableIdentity the assignmentTransferableIdentity to set
	 */
	public void setAssignmentTransferableIdentity(
			String assignmentTransferableIdentity) {
		this.assignmentTransferableIdentity = assignmentTransferableIdentity;
	}

	/**
	 * @return the workPeriodTransferableIdentity
	 */
	public String getWorkPeriodTransferableIdentity() {
		return workPeriodTransferableIdentity;
	}

	/**
	 * @param workPeriodTransferableIdentity the workPeriodTransferableIdentity to set
	 */
	public void setWorkPeriodTransferableIdentity(
			String workPeriodTransferableIdentity) {
		this.workPeriodTransferableIdentity = workPeriodTransferableIdentity;
	}
   
}
