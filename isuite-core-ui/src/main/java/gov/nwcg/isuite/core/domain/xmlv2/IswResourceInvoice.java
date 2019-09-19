package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswResourceInvoice", table="isw_resource_invoice"
	,filterincident=" resource_id in (" +
					"   select resource_id " +
					"   from isw_incident_resource " +
					"   where incident_id in (:INCIDENTID) " +
					") and time_invoice_id in ("+
					"   select id from isw_time_invoice where deleted_date is null "+
					"   and is_draft = :FALSE " +
					" ) "
    ,filtergroup = " time_invoice_id in (select id from isw_time_invoice where deleted_date is null and " +
    				" is_draft = :FALSE ) "
)
public class IswResourceInvoice {

	@XmlTransferField(name = "A", sqlname="RESOURCE_ID", type="STRING"
		  ,derivedtable="isw_resource")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="TIME_INVOICE_ID", type="STRING"
		  ,derivedtable="isw_time_invoice")
	private String b;

    public IswResourceInvoice(){
    }

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

}
