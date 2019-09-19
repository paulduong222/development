package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswCostAccrualExtractRsc", table = "isw_cost_accrual_ext_rsc", alias="rsc"
	,filterincident=" "+
					"cost_accrual_extract_id in (" +
					"   select id "+
					"   from isw_cost_accrual_extract " +
					"   where finalized = 'Y' " +
					"   and incident_id in (:INCIDENTID) " +
					") "
	,filtergroup=" " +
				"cost_accrual_extract_id in (" +
				"   select id "+
				"   from isw_cost_accrual_extract " +
				"   where finalized = 'Y' " +
				"   and incident_group_id in (:INCIDENTGROUPID) " +
				") "
)
public class IswCostAccrualExtractRsc {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_ACCRUAL_EXT_RSC", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "COST_ACCRUAL_EXTRACT_ID", type = "STRING"
		,derivedtable="isw_cost_accrual_extract")
	private String a;
	
	@XmlTransferField(name = "B", sqlname = "INCIDENT_RESOURCE_ID", type = "STRING"
		,derivedtable="isw_incident_resource")
	private String b;

	@XmlTransferField(name = "C", sqlname = "TOTAL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal c;

	@XmlTransferField(name = "D", sqlname = "CHANGE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal d;

	@XmlTransferField(name = "E", sqlname = "COST_ACCRUAL_CODE", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname = "ACCOUNT_CODE", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "FISCAL_YEAR", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname = "DRAW_DOWN", type="STRING")
	private String h;

	@XmlTransferField(name = "I", sqlname="ACCOUNT_CODE_ID", type="STRING"
		,derivedtable="isw_incident_account_code")
	private String i;
	
	@XmlTransferField(name = "J", sqlname = "INCIDENT_RESOURCE_OTHER_ID", type = "STRING"
		,derivedtable="isw_incident_resource_other")
	private String j;

	/**
	 * Default constructor.
	 * 
	 */
	public IswCostAccrualExtractRsc() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getC() {
		return c;
	}

	public void setC(BigDecimal c) {
		this.c = c;
	}

	public BigDecimal getD() {
		return d;
	}

	public void setD(BigDecimal d) {
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

	public String getTI() {
		return tI;
	}

	public void setTI(String ti) {
		tI = ti;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

}
