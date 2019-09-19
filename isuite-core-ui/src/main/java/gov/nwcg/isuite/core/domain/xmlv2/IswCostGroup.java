package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswCostGroup", table = "isw_cost_group"
	, filterincident=" incident_id in (:INCIDENTID) ")
public class IswCostGroup {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_GROUP", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String a;

	@XmlTransferField(name = "B", sqlname = "COST_GROUP_NAME", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "DESCRIPTION", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "START_DATE", type = "DATE")
	private Date d;
	
	@XmlTransferField(name = "E", sqlname = "DELETED_DATE", type = "DATE")
	private Date e;

	@XmlTransferField(name = "F", sqlname="INCIDENT_SHIFT_ID", type="STRING"		
		  ,derivedtable="isw_incident_shift")
	private String f;
	
	public IswCostGroup() {
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

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
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

}
