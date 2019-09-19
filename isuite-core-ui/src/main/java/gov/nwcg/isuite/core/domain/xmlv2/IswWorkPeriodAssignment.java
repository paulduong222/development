package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswWorkPeriodAssignment", table="isw_work_period_assignment"
	,filterincident=" work_period_id in ( "+
				    "   select wp1.id " +
				    "   from isw_work_period wp1 " +
				    "        , isw_incident_resource ir1 " +
				    "   where wp1.incident_resource_id = ir1.id " +
				    "   and ir1.incident_id in (:INCIDENTID) " +
					") "
)
public class IswWorkPeriodAssignment {

	@XmlTransferField(name = "A", sqlname="WORK_PERIOD_ID", type="STRING"
		,derivedtable="isw_work_period")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="ASSIGNMENT_ID", type="STRING"
		  ,derivedtable="isw_assignment")
	private String b;

	
    public IswWorkPeriodAssignment(){
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
