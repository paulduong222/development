package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswTimeInvoice", table = "isw_time_invoice"
	,filterincident=" deleted_date is null " +
					" and is_draft = :FALSE " +
					" and id in ( " +
					"   select time_invoice_id " +
					"   from isw_resource_invoice " +
					"   where resource_id in (" +
					"     select resource_id " + 
					"     from isw_incident_resource where incident_id in (:INCIDENTID) " +
					"   ) " +
					" ) "
	,filtergroup=" deleted_date is null and is_draft = :FALSE "
)
public class IswTimeInvoice {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_TIME_INVOICE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "invoice_number", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "first_include_date", type="DATE")
	private Date b;

	@XmlTransferField(name = "C", sqlname = "last_include_date", type="DATE")
	private Date c;

	@XmlTransferField(name = "D", sqlname = "deleted_date", type="DATE")
	private Date d;

	@XmlTransferField(name = "E", sqlname = "deleted_reason", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname = "is_draft", type="BOOLEAN")
	private Boolean f;

	@XmlTransferField(name = "G", sqlname = "is_duplicate", type="BOOLEAN")
	private Boolean g;

	@XmlTransferField(name = "H", sqlname = "is_final", type="BOOLEAN")
	private Boolean h;

	@XmlTransferField(name = "I", sqlname = "is_invoice_adjust", type="BOOLEAN")
	private Boolean i;

	@XmlTransferField(name = "J", sqlname = "is_invoice_only", type="BOOLEAN")
	private Boolean j;

	@XmlTransferField(name = "K", sqlname = "is_adjust_only", type="BOOLEAN")
	private Boolean k;

	@XmlTransferField(name = "L", sqlname = "is_exported", type="STRING")
	private String l;

	@XmlTransferField(name = "M", sqlname = "export_date", type="DATE")
	private Date m;

	@XmlTransferField(name = "N", sqlname="INVOICE_REPORT_ID", type="STRING"
		  ,derivedtable="isw_report")
	private String n;
	
	@XmlTransferField(name = "O", sqlname="ADJUSTMENT_REPORT_ID", type="STRING"
		  ,derivedtable="isw_report")
	private String o;

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

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public Boolean getF() {
		return f;
	}

	public void setF(Boolean f) {
		this.f = f;
	}

	public Boolean getG() {
		return g;
	}

	public void setG(Boolean g) {
		this.g = g;
	}

	public Boolean getH() {
		return h;
	}

	public void setH(Boolean h) {
		this.h = h;
	}

	public Boolean getI() {
		return i;
	}

	public void setI(Boolean i) {
		this.i = i;
	}

	public Boolean getJ() {
		return j;
	}

	public void setJ(Boolean j) {
		this.j = j;
	}

	public Boolean getK() {
		return k;
	}

	public void setK(Boolean k) {
		this.k = k;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public Date getM() {
		return m;
	}

	public void setM(Date m) {
		this.m = m;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

}
