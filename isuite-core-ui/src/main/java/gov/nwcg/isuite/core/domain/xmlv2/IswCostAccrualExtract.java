package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswCostAccrualExtract", table = "isw_cost_accrual_extract"
	,filterincident=" incident_id in (:INCIDENTID) and finalized='Y' "
	,filtergroup=" incident_group_id in (:INCIDENTGROUPID) and finalized='Y' "
)
public class IswCostAccrualExtract {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_ACCRUAL_EXTRACT", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "extract_date", type = "DATE")
	private Date a;

	@XmlTransferField(name = "B", sqlname = "FINALIZED", type = "STRING", defaultvalue="N")
	private String b;

	@XmlTransferField(name = "C", sqlname = "IS_FROM_SINGLE_INCIDENT", type = "STRING", defaultvalue="N")
	private String c;
	
	@XmlTransferField(name = "D", sqlname = "finalized_date", type = "DATE")
	private Date d;

	@XmlTransferField(name = "E", sqlname = "PREPARED_BY", type = "STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname = "PREPARED_PHONE", type = "STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname="INCIDENT_GROUP_ID", type="STRING"
		,derivedtable="isw_incident_group")
	private String g;

	@XmlTransferField(name = "H", sqlname="INCIDENT_ID", type="STRING"
		,derivedtable="isw_incident")
	private String h;

	@XmlTransferField(name = "I", sqlname = "SEQUENCE_NUMBER", type = "SHORT")
	private Short i;

	@XmlTransferField(name = "J", sqlname = "is_exported", type = "STRING", defaultvalue="N")
	private String j;

	/**
	 * Default constructor.
	 * 
	 */
	public IswCostAccrualExtract() {
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

	public Short getI() {
		return i;
	}

	public void setI(Short i) {
		this.i = i;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

}
