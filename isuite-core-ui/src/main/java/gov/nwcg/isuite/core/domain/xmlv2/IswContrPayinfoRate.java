package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswContrPayinfoRate", table="isw_contr_payinfo_rate"
	,filterincident=" CONTRACTOR_PAY_INFO_ID in ("+
		"   select id from isw_contr_payment_info where " +
		"   assignment_time_id in ("+
		"   select at3.id "+
		"   from isw_assignment_time at3 "+
		"        ,isw_assignment a3 "+
		"		 , isw_work_period_assignment wpa3 " +
		"		 , isw_work_period wp3 " +
		"        , isw_incident_resource ir3 " +
		"   where at3.assignment_id = a3.id " +
		"   and a3.id = wpa3.assignment_id " + 
		"   and wpa3.work_period_id = wp3.id " +
		"   and wp3.incident_resource_id = ir3.id " +
		"   and ir3.incident_id in (:INCIDENTID) " +
	    ") " +
	    ") "
)
public class IswContrPayinfoRate {

	@XmlTransferField(name = "A", sqlname="CONTRACTOR_PAY_INFO_ID", type="STRING"
		  ,derivedtable="isw_contr_payment_info")
	private String a;

	@XmlTransferField(name = "B", sqlname="CONTRACTOR_RATE_ID", type="STRING"
		  ,derivedtable="isw_contractor_rate")
    private String b;
	

    public IswContrPayinfoRate(){
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
