package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswCostData", table = "isw_cost_data"
	,filterincident=" id in (select cost_data_id from isw_incident_resource where incident_id in (:INCIDENTID) ) "
)
public class IswCostData {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_DATA", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname = "ASSIGN_DATE", type = "DATE")
	private Date a;

	@XmlTransferField(name = "B", sqlname = "IS_ACCRUAL_LOCKED", type = "BOOLEAN")
	private Boolean b;

	@XmlTransferField(name = "C", sqlname = "IS_USE_ACCRUALS_ONLY", type = "BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "D", sqlname = "IS_GENERATE_COSTS", type = "BOOLEAN")
	private Boolean d;

	@XmlTransferField(name = "E", sqlname = "IS_GENERATE_COSTS_SYS", type = "STRING", defaultvalue="N")
	private String e;

	@XmlTransferField(name = "F", sqlname = "COST_REMARKS", type = "STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "COST_OTHER_1", type = "STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname = "COST_OTHER_2", type = "STRING")
	private String h;

	@XmlTransferField(name = "I", sqlname = "COST_OTHER_3", type = "STRING")
	private String i;

	@XmlTransferField(name = "J", sqlname="PAYMENT_AGENCY_ID", type="STRING"
		,derivedtable="iswl_agency")
	private String j;

	@XmlTransferField(name = "K", sqlname="ACCRUAL_CODE_ID", type="STRING"
		,derivedtable="iswl_accrual_code")
	private String k;

	@XmlTransferField(name = "L", sqlname = "DEFAULT_COST_GROUP_ID", type = "STRING"
		, derivedtable="isw_cost_group")
	private String l;

	@XmlTransferField(name = "M", sqlname = "DEFAULT_INC_SHIFT_ID", type = "STRING"
		, derivedtable="isw_incident_shift")
	private String m;
	
	public IswCostData() {
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

	public Date getA() {
		return a;
	}

	public void setA(Date a) {
		this.a = a;
	}

	public Boolean getB() {
		return b;
	}

	public void setB(Boolean b) {
		this.b = b;
	}

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
		this.c = c;
	}

	public Boolean getD() {
		return d;
	}

	public void setD(Boolean d) {
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

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
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

}
