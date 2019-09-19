package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswContractor", table = "isw_contractor"
	,filterincident=" id in (select contractor_id from isw_incident_contractor where incident_id in ( :INCIDENTID ) ) "
)
public class IswContractor {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTRACTOR", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname = "NAME", type = "STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "TIN", type = "STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "DUNS", type = "STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "PHONE", type = "STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname = "FAX", type = "STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname = "DELETED_DATE", type = "DATE")
	private Date f;

	@XmlTransferField(name = "G", sqlname = "IS_ENABLED", type = "BOOLEAN")
	private Boolean g;
	
	@XmlTransferField(name = "H", sqlname="ADDRESS_ID", type="STRING"		
					  ,derivedtable="isw_address")
	private String h;
	
	public IswContractor() {

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

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public Date getF() {
		return f;
	}

	public void setF(Date f) {
		this.f = f;
	}

	public Boolean getG() {
		return g;
	}

	public void setG(Boolean g) {
		this.g = g;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

}
