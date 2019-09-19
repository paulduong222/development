package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswResourceInvoice", table="isw_resource_invoice", finaltable=true)
public class IswResourceInvoice {

	@XmlTransferField(name = "ResourceTransferableIdentity", alias="resti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="ResourceId"
		, disjoined=true, disjoinedtable="isw_resource", disjoinedfield="transferable_identity",disjoinedsource="RESOURCE_ID")
	private String resourceTransferableIdentity;

	@XmlTransferField(name = "ResourceId", sqlname="RESOURCE_ID", type="LONG"
		,derived=true, derivedfield="resourceTransferableIdentity")
	private Long resourceId;
	
	@XmlTransferField(name = "TimeInvoiceTransferableIdentity", alias="tinvti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="TimeInvoiceId"
						, disjoined=true, disjoinedtable="isw_time_invoice", disjoinedfield="transferable_identity",disjoinedsource="time_invoice_id")
    private String timeInvoiceTransferableIdentity;
	
	@XmlTransferField(name = "TimeInvoiceId", sqlname="TIME_INVOICE_ID", type="LONG"
						,joinkeyprimary=true)
	private Long timeInvoiceId;
	

    public IswResourceInvoice(){
    }


	/**
	 * @return the resourceTransferableIdentity
	 */
	public String getResourceTransferableIdentity() {
		return resourceTransferableIdentity;
	}


	/**
	 * @param resourceTransferableIdentity the resourceTransferableIdentity to set
	 */
	public void setResourceTransferableIdentity(String resourceTransferableIdentity) {
		this.resourceTransferableIdentity = resourceTransferableIdentity;
	}


	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}


	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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


   
}
