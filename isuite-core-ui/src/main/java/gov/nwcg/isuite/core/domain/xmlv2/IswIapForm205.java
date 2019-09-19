package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapForm205", table="isw_iap_form_205"
	,filterincident=" iap_plan_id in (select id from isw_iap_plan where incident_id in (:INCIDENTID) ) "
	,filtergroup=" iap_plan_id in (select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID) ) "
)
public class IswIapForm205 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_205", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_PLAN_ID", type="STRING"
		,derivedtable="isw_iap_plan")
	private String a;

	@XmlTransferField(name = "B", sqlname="SPECIAL_INSTRUCTION", type="STRING", ischardata=true)
	private String b;
	
	@XmlTransferField(name = "C", sqlname="PREPARED_BY", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="PREPARED_BY_POS", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="PREPARED_DATE", type="DATE")
	private Date e;

	@XmlTransferField(name = "F", sqlname="IS_FORM_LOCKED", type="STRING")
	private String f;

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

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public Date getE() {
		return e;
	}

	public void setE(Date e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}
	
}
