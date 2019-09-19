package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswContractorPaymentInfo", table = "isw_contr_payment_info"
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
public class IswContractorPaymentInfo {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTR_PAYMENT_INFO", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "ASSIGNMENT_TIME_ID", type = "STRING",updateable=false
			,derivedtable="isw_assignment_time")
	private String a;

	@XmlTransferField(name = "B", sqlname = "VIN_NAME", type = "STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "DESC1", type = "STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "DESC2", type = "STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname = "HIRED_DATE", type = "DATE")
	private Date e;

	@XmlTransferField(name = "Fr", sqlname = "POINT_OF_HIRE", type = "STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "IS_OPERATION", type = "BOOLEAN")
	private Boolean g;

	@XmlTransferField(name = "H", sqlname = "IS_SUPPLIES", type = "BOOLEAN")
	private Boolean h;

	@XmlTransferField(name = "I", sqlname = "IS_WITHDRAWN", type = "BOOLEAN")
	private Boolean i;

	@XmlTransferField(name = "J", sqlname = "DELETED_DATE", type = "DATE")
	private Date j;

	@XmlTransferField(name = "K", sqlname="CONTRACTOR_ID", type="STRING"
		  ,derivedtable="isw_contractor")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="CONTRACTOR_AGREEMENT_ID", type="STRING"
		  ,derivedtable="isw_contractor_agreement")
	private String l;
	
	public IswContractorPaymentInfo() {

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

	public Date getE() {
		return e;
	}

	public void setE(Date e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
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

}
