package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswProjection", table = "isw_projection"
	,filterincident=" incident_id in (:INCIDENTID) " 
	,filtergroup=" incident_group_id in (:INCIDENTGROUPID) "
)
public class IswProjection {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_PROJECTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "PROJECTION_NAME", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "START_DATE", type="DATE")
	private Date b;

	@XmlTransferField(name = "C", sqlname = "NUMBER_OF_DAYS", type="SHORT")
	private Short c;

	@XmlTransferField(name = "D", sqlname="INCIDENT_ID", type="STRING"
		,derivedtable="isw_incident")
	private String d;
	
	@XmlTransferField(name = "F", sqlname="INCIDENT_GROUP_ID", type="STRING"
		,derivedtable="isw_incident_group")
	private String f;

	/**
	 * Default constructor.
	 *
	 */
	public IswProjection() {
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

	public Date getB() {
		return b;
	}

	public void setB(Date b) {
		this.b = b;
	}

	public Short getC() {
		return c;
	}

	public void setC(Short c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

}
