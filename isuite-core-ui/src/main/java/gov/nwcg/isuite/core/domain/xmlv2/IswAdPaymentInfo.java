package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswAdPaymentInfo", table = "isw_ad_payment_info"
	,filterincident=" assignment_time_id in ("+
					"   select at3.id "+
					"   from isw_assignment_time at3 "+
					"        ,isw_assignment a3 "+
					"		 , isw_work_period_assignment wpa3 " +
					"		 , isw_work_period wp3 " +
					"        , isw_incident_resource ir3 " +
					"   where at3.assignment_id = a3.id " +
					"   and a3.id = wpa3.assignment_id " + 
					"   and wpa3.work_period_id = wp3.id " +
					"   and wp3.incident_resource_id = ir3.id " +
					"   and ir3.incident_id in (:INCIDENTID) " +
				    ") "
)
public class IswAdPaymentInfo {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_AD_PAYMENT_INFO", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "RATE_AREA_NAME", type = "STRING",nullwhenempty=true)
	private String a;

	@XmlTransferField(name = "B", sqlname = "ASSIGNMENT_TIME_ID", type = "STRING",updateable=false
			,derivedtable="isw_assignment_time")
	private String b;

	@XmlTransferField(name = "C", sqlname = "IS_INITIAL_EMP", type="BOOLEAN")
	private Boolean c = false;

	@XmlTransferField(name = "D", sqlname = "IS_RETURN_TRAVEL", type="BOOLEAN")
	private Boolean d = false;

	@XmlTransferField(name = "E", sqlname = "POINT_OF_HIRE", type = "STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname = "SSN", type = "STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "ECI", type = "STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname = "DELETED_DATE", type = "DATE")
	private Date h;

	@XmlTransferField(name = "I", sqlname = "RATE_YEAR", type = "INTEGER")
	private Integer i;

	@XmlTransferField(name = "J", sqlname = "RATE_AREA_ID", type = "STRING"
		  ,derivedtable="iswl_rate_area")
	private String j;

	@XmlTransferField(name = "K", sqlname = "RATE_CLASS_RATE_ID", type = "STRING"
		  ,derivedtable="iswl_rate_class_rate")
	private String k;

	@XmlTransferField(name = "L", sqlname = "POINT_OF_HIRE_ID", type = "STRING"
		  ,derivedtable="isw_organization")
	private String l;

	public IswAdPaymentInfo() {
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

	public Date getH() {
		return h;
	}

	public void setH(Date h) {
		this.h = h;
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
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
}
