package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlAgencyGroup", table = "iswl_agency_group"
)
public class IswlAgencyGroup {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_AGENCY_GROUP", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "CODE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "DESCRIPTION", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "D", sqlname = "INCIDENT_ID", type="LONG",updateable=false)
	private Long d;

	@XmlTransferField(name = "E", sqlname = "INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long e;

	@XmlTransferField(name = "F", sqlname = "IS_ACTIVE", type="STRING",updateable=false)
	private String f;

	public IswlAgencyGroup() {
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

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
		this.c = c;
	}

	public Long getD() {
		return d;
	}

	public void setD(Long d) {
		this.d = d;
	}

	public Long getE() {
		return e;
	}

	public void setE(Long e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}


}
