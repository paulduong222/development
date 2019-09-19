package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;
import java.util.Date;

@XmlTransferTable(name = "IswAssignmentTime", table = "isw_assignment_time"
	,filterincident=" assignment_id in ("+
					"   select a2.id "+
					"   from isw_assignment a2 "+
					"        , isw_work_period_assignment wpa2 " +
					"        , isw_work_period wp2 " +
					"        , isw_incident_resource ir2 " +
					"   where a2.id = wpa2.assignment_id " +
					"   and wpa2.work_period_id = wp2.id " +
					"   and wp2.incident_resource_id = ir2.id " +
					"   and ir2.incident_id in (:INCIDENTID ) " +
					") "
)
public class IswAssignmentTime {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ASSIGNMENT_TIME", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "ASSIGNMENT_ID", type="STRING",updateable=false
			,derivedtable="isw_assignment")
	private String a;

	@XmlTransferField(name = "B", sqlname = "EMPLOYMENT_TYPE", type="STRING",nullwhenempty=true)
	private String b;

	@XmlTransferField(name = "C", sqlname = "PHONE", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "FAX", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname = "OF_REMARKS", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname = "OTHER_DEFAULT_RATE", type = "BIGDECIMAL")
	private BigDecimal F;

	@XmlTransferField(name = "G", sqlname = "DELETED_DATE", type = "DATE")
	private Date g;

	@XmlTransferField(name = "H", sqlname = "MAILING_ADDRESS_ID", type="STRING"
		  ,derivedtable="isw_address")
	private String h;

	
	public IswAssignmentTime() {

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


	public BigDecimal getF() {
		return F;
	}


	public void setF(BigDecimal f) {
		F = f;
	}


	public Date getG() {
		return g;
	}


	public void setG(Date g) {
		this.g = g;
	}


	public String getH() {
		return h;
	}


	public void setH(String h) {
		this.h = h;
	}

}
