package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswResourceOther", table = "isw_resource_other"
	,filterincident=" "+
					"id in ("+
					"   select resource_other_id " +
					"   from isw_incident_resource_other " +
					"   where incident_id in (:INCIDENTID) " +
					") "
	,filtergroup=" "+
				"id in ("+
				"   select resource_other_id " +
				"   from isw_incident_resource_other " +
				"   where incident_group_id in (:INCIDENTGROUPID) " +
				") "
)
public class IswResourceOther {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_OTHER", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "REQUEST_NUMBER", type = "STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "COST_DESCRIPTION", type = "STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "ACTUAL_RELEASE_DATE", type = "DATE")
	private Date c;

	@XmlTransferField(name = "D", sqlname="KIND_ID", type="STRING"
		  ,derivedtable="iswl_kind")
	private String d;

	@XmlTransferField(name = "E", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="STRING"
		  ,derivedtable="isw_incident_account_code")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="AGENCY_ID", type="STRING"
		  ,derivedtable="iswl_agency")
	private String f;
	
	public IswResourceOther() {
		super();
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

	public String getD() {
		return d;
	}

	public void setD(String d) {
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
