package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswlAgency", table = "iswl_agency"
	, filterincident=" is_standard = :FALSE and incident_id in (:INCIDENTID) " 
	, filtergroup=" is_standard = :FALSE and incident_group_id = :INCIDENTGROUPID "
)
public class IswlAgency {
	
	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_AGENCY", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "AGENCY_CODE", type = "STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "AGENCY_NAME", type = "STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "AGENCY_TYPE", type = "STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean d;

	@XmlTransferField(name = "E", sqlname = "IS_STATE", type="BOOLEAN")
	private Boolean e;
	
	@XmlTransferField(name = "F", sqlname = "IS_ACTIVE", type = "STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "CREATED_BY", type = "STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname = "CREATED_DATE", type = "DATE")
	private Date h;
	
	@XmlTransferField(name = "I", sqlname = "LAST_MODIFIED_BY", type = "STRING")
	private String i;

	@XmlTransferField(name = "J", sqlname = "LAST_MODIFIED_DATE", type = "DATE")
	private Date j;

	@XmlTransferField(name = "K", sqlname = "INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String k;

	@XmlTransferField(name = "L", sqlname = "INCIDENT_GROUP_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_group")
	private String l;
	
	@XmlTransferField(name = "M", sqlname = "AGENCY_GROUP_ID",  type="STRING"
						, derivedtable="iswl_agency_group")
	private String m;

	@XmlTransferField(name = "N", sqlname = "RATE_GROUP_ID", type="STRING"
						,derivedtable="iswl_rate_group")
	private String n;


	public IswlAgency() {
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


	public Boolean getD() {
		return d;
	}


	public void setD(Boolean d) {
		this.d = d;
	}


	public Boolean getE() {
		return e;
	}


	public void setE(Boolean e) {
		this.e = e;
	}


	public String getF() {
		return f;
	}


	public void setF(String f) {
		this.f = f;
	}


	public String getG() {
		return g;
	}


	public void setG(String g) {
		this.g = g;
	}


	public Date getH() {
		return h;
	}


	public void setH(Date h) {
		this.h = h;
	}


	public String getI() {
		return i;
	}


	public void setI(String i) {
		this.i = i;
	}


	public Date getJ() {
		return j;
	}


	public void setJ(Date j) {
		this.j = j;
	}


	public String getK() {
		return k;
	}


	public void setK(String k) {
		this.k = k;
	}


	public String getL() {
		return l;
	}


	public void setL(String l) {
		this.l = l;
	}


	public String getM() {
		return m;
	}


	public void setM(String m) {
		this.m = m;
	}


	public String getN() {
		return n;
	}


	public void setN(String n) {
		this.n = n;
	}



}