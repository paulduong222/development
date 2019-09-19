package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswWorkPeriodOvernightStayInfo", table="isw_wp_overnight_stay_info"
	,filterincident=" work_period_id in ( select id from isw_work_period where incident_resource_id in ( select id from isw_incident_resource where incident_id in (:INCIDENTID) ) ) "
)
public class IswWorkPeriodOvernightStayInfo {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_WP_OVERNIGHT_STAY_INFO", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="WORK_PERIOD_ID", type="STRING", updateable=false
			,derivedtable="isw_work_period")
	private String a;

    @XmlTransferField(name = "B", sqlname = "CITY", type="STRING")
	private String b;
	
    @XmlTransferField(name = "C", sqlname="ESTIMATED_ARRIVAL_DATE", type="DATE")
	private Date c;
	
    @XmlTransferField(name = "D", sqlname = "LENGTH_OF_STAY", type="LONG")
	private Long d;
	
    @XmlTransferField(name = "E", sqlname = "REMARKS", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname="STATE_ID", type="STRING"
		  ,derivedtable="iswl_country_subdivision")
	private String f;
	
	public IswWorkPeriodOvernightStayInfo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTI() {
		return tI;
	}

	public void setTI(String ti) {
		tI = ti;
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

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
		this.c = c;
	}

	public Long getD() {
		return d;
	}

	public void setD(Long d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

}
