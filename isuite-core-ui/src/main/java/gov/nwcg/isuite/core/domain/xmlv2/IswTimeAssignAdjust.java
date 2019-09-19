package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;
import java.util.Date;


@XmlTransferTable(name = "IswTimeAssignAdjust", table="isw_time_assign_adjust"
	,filterincident=" assignment_id in ("+
					"   select a1.id " +
					"   from isw_assignment a1, isw_work_period_assignment wpa1, isw_work_period wp1, isw_incident_resource ir1 "+
					"   where a1.id = wpa1.assignment_id " +
					"   and wpa1.work_period_id = wp1.id " +
					"   and wp1.incident_resource_id = ir1.id " +
					"   and ir1.incident_id in (:INCIDENTID) " +
					") "
)
public class IswTimeAssignAdjust {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_TIME_ASSIGN_ADJUST", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "B", sqlname="activity_date", type="DATE")
	private Date b;
	
	@XmlTransferField(name = "C", sqlname="ADJUSTMENT_TYPE", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "adjustment_amount", type="BIGDECIMAL")
	private BigDecimal d;
	
	@XmlTransferField(name = "E", sqlname = "COMMODITY", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="deleted_date", type="DATE")
	private Date f;

	@XmlTransferField(name = "G", sqlname="ASSIGNMENT_ID", type="STRING"
		  ,derivedtable="isw_assignment")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="ADJUST_CATEGORY_ID", type="STRING"
		  ,derivedtable="iswl_adjust_category")
	private String h;
	
	@XmlTransferField(name = "I", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="STRING"
		  ,derivedtable="isw_incident_account_code")
	private String i;

	public IswTimeAssignAdjust() {
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

	public Date getB() {
		return b;
	}

	public void setB(Date b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
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

	public Date getF() {
		return f;
	}

	public void setF(Date f) {
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

}
