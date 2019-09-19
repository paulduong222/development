package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswAssignmentTimePostInvoice", table="isw_assign_time_post_invoice"
	,filterincident=" assign_time_post_id in (" +
					"   select id " +
					"   from isw_assign_time_post " +
					"   where assignment_time_id in ("+
					"   	select at3.id "+
					"   	from isw_assignment_time at3 "+
					"        	,isw_assignment a3 "+
					"		 	, isw_work_period_assignment wpa3 " +
					"		 	, isw_work_period wp3 " +
					"        	, isw_incident_resource ir3 " +
					"   	where at3.assignment_id = a3.id " +
					"   	and a3.id = wpa3.assignment_id " + 
					"   	and wpa3.work_period_id = wp3.id " +
					"   	and wp3.incident_resource_id = ir3.id " +
					"   	and ir3.incident_id in (:INCIDENTID) " +
					"   ) " +
					") and time_invoice_id is not null " +
					" and time_invoice_id in ("+
				    "   select id from isw_time_invoice "+
				    "   where deleted_date is null " +
				    "   and is_draft = :FALSE " +
					") " 
)
public class IswAssignmentTimePostInvoice {

	@XmlTransferField(name = "A", sqlname="ASSIGN_TIME_POST_ID", type="STRING"
		  ,derivedtable="isw_assign_time_post")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="TIME_INVOICE_ID", type="STRING"
		  ,derivedtable="isw_time_invoice")
	private String b;
	
    public IswAssignmentTimePostInvoice(){
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
