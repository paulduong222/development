package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswAssignment", table = "isw_assignment"
	,filterincident=" id in ( "+
				    "   select wpa2.assignment_id "+
				    "   from isw_work_period_assignment wpa2 "+
				    "        , isw_work_period wp2 " +
				    "        , isw_incident_resource ir2 " +
				    "   where wpa2.work_period_id = wp2.id " +
				    "   and wp2.incident_resource_id = ir2.id " +
				    "   and ir2.incident_id in (:INCIDENTID) " +
				    ")"
)
public class IswAssignment {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ASSIGNMENT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname = "REQUEST_NUMBER", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "START_DATE", type="DATE")
	private Date b;

	@XmlTransferField(name = "C", sqlname = "END_DATE", type="DATE")
	private Date c;

	@XmlTransferField(name = "D", sqlname = "IS_TRAINING", type="BOOLEAN")
	private Boolean d;

	@XmlTransferField(name = "E", sqlname = "REASSIGN_INCIDENT_NAME", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname = "REASSIGN_INCIDENT_NUMBER", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "ASSIGNMENT_STATUS", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname = "KIND_ID", type = "STRING"
		  ,derivedtable="iswl_kind")
	private String h;

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

}
