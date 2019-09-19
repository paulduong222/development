package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswContractorAgreement", table = "isw_contractor_agreement"
	,filterincident=" contractor_id in (select contractor_id from isw_incident_contractor where incident_id in (:INCIDENTID) ) "
)
public class IswContractorAgreement {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTRACTOR_AGREEMENT", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "agreement_number", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "start_date", type="DATE")
	private Date b;

	@XmlTransferField(name = "C", sqlname = "end_date", type="DATE")
	private Date c;

	@XmlTransferField(name = "D", sqlname = "point_of_hire", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname = "DELETED_DATE", type="DATE")
	private Date e;

	@XmlTransferField(name = "F", sqlname = "IS_ENABLED", type="BOOLEAN")
	private Boolean f;

	@XmlTransferField(name = "G", sqlname = "ADMIN_OFFICE_ID", type = "STRING"
		  ,derivedtable="isw_admin_office")
	private String g;

	@XmlTransferField(name = "H", sqlname = "CONTRACTOR_ID", type = "STRING"
		  ,derivedtable="isw_contractor")
	private String h;
	

	public IswContractorAgreement() {

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


	public Date getB() {
		return b;
	}


	public void setB(Date b) {
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


	public Date getE() {
		return e;
	}


	public void setE(Date e) {
		this.e = e;
	}


	public Boolean getF() {
		return f;
	}


	public void setF(Boolean f) {
		this.f = f;
	}


	public String getG() {
		return g;
	}


	public void setG(String g) {
		this.g = g;
	}


	public String getH() {
		return h;
	}


	public void setH(String h) {
		this.h = h;
	}

}
