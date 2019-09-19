package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswAssignmentTimePostInvoice", table="isw_assign_time_post_invoice",jointable=true)
public class IswAssignmentTimePostInvoice {

	@XmlTransferField(name = "TimeInvoiceTransferableIdentity", alias="tminvti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="TimeInvoiceId"
		, disjoined=true, disjoinedtable="isw_time_invoice", disjoinedfield="transferable_identity",disjoinedsource="time_invoice_id")
    private String timeInvoiceTransferableIdentity;
	
	@XmlTransferField(name = "TimeInvoice", type="COMPLEX", target=IswTimeInvoice.class
						, lookupname="Id", sourcename="TimeInvoiceId")
	private IswTimeInvoice timeInvoice;
	
	@XmlTransferField(name = "TimeInvoiceId", sqlname="TIME_INVOICE_ID", type="LONG"
						,derived=true, derivedfield="TimeInvoice"
						, joinkeysecondary=true)
	private Long timeInvoiceId;

	@XmlTransferField(name = "AssignTimePostTransferableIdentity", alias="atpti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AssignTimePostId"
						, disjoined=true, disjoinedtable="isw_assign_time_post", disjoinedfield="transferable_identity",disjoinedsource="assign_time_post_id")
    private String assignTimePostTransferableIdentity;
	
	@XmlTransferField(name = "AssignTimePostId", sqlname="ASSIGN_TIME_POST_ID", type="LONG"
						,joinkeyprimary=true)
	private Long assignTimePostId;
	

    public IswAssignmentTimePostInvoice(){
    }


	/**
	 * @return the timeInvoiceTransferableIdentity
	 */
	public String getTimeInvoiceTransferableIdentity() {
		return timeInvoiceTransferableIdentity;
	}


	/**
	 * @param timeInvoiceTransferableIdentity the timeInvoiceTransferableIdentity to set
	 */
	public void setTimeInvoiceTransferableIdentity(
			String timeInvoiceTransferableIdentity) {
		this.timeInvoiceTransferableIdentity = timeInvoiceTransferableIdentity;
	}


	/**
	 * @return the timeInvoice
	 */
	public IswTimeInvoice getTimeInvoice() {
		return timeInvoice;
	}


	/**
	 * @param timeInvoice the timeInvoice to set
	 */
	public void setTimeInvoice(IswTimeInvoice timeInvoice) {
		this.timeInvoice = timeInvoice;
	}


	/**
	 * @return the timeInvoiceId
	 */
	public Long getTimeInvoiceId() {
		return timeInvoiceId;
	}


	/**
	 * @param timeInvoiceId the timeInvoiceId to set
	 */
	public void setTimeInvoiceId(Long timeInvoiceId) {
		this.timeInvoiceId = timeInvoiceId;
	}


	/**
	 * @return the assignTimePostTransferableIdentity
	 */
	public String getAssignTimePostTransferableIdentity() {
		return assignTimePostTransferableIdentity;
	}


	/**
	 * @param assignTimePostTransferableIdentity the assignTimePostTransferableIdentity to set
	 */
	public void setAssignTimePostTransferableIdentity(
			String assignTimePostTransferableIdentity) {
		this.assignTimePostTransferableIdentity = assignTimePostTransferableIdentity;
	}


	/**
	 * @return the assignTimePostId
	 */
	public Long getAssignTimePostId() {
		return assignTimePostId;
	}


	/**
	 * @param assignTimePostId the assignTimePostId to set
	 */
	public void setAssignTimePostId(Long assignTimePostId) {
		this.assignTimePostId = assignTimePostId;
	}

   
}
