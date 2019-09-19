package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswTimeAssignAdjustInvoice", table="isw_time_assign_adj_invoice",jointable=true)
public class IswTimeAssignAdjustInvoice {

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

	@XmlTransferField(name = "TimePostAdjustTransferableIdentity", alias="atpti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="TimePostAdjustId"
						, disjoined=true, disjoinedtable="isw_time_assign_adjust", disjoinedfield="transferable_identity",disjoinedsource="time_post_adjust_id")
    private String timePostAdjustTransferableIdentity;
	
	@XmlTransferField(name = "TimePostAdjustId", sqlname="TIME_POST_ADJUST_ID", type="LONG"
						,joinkeyprimary=true)
	private Long timePostAdjustId;
	

    public IswTimeAssignAdjustInvoice(){
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
	 * @return the timePostAdjustTransferableIdentity
	 */
	public String getTimePostAdjustTransferableIdentity() {
		return timePostAdjustTransferableIdentity;
	}


	/**
	 * @param timePostAdjustTransferableIdentity the timePostAdjustTransferableIdentity to set
	 */
	public void setTimePostAdjustTransferableIdentity(
			String timePostAdjustTransferableIdentity) {
		this.timePostAdjustTransferableIdentity = timePostAdjustTransferableIdentity;
	}


	/**
	 * @return the timePostAdjustId
	 */
	public Long getTimePostAdjustId() {
		return timePostAdjustId;
	}


	/**
	 * @param timePostAdjustId the timePostAdjustId to set
	 */
	public void setTimePostAdjustId(Long timePostAdjustId) {
		this.timePostAdjustId = timePostAdjustId;
	}


   
}
