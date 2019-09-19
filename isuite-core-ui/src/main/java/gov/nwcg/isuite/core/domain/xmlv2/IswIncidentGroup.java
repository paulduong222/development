package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIncidentGroup", table="isw_incident_group"
	,filtergroup=" id = :INCIDENTGROUPID "
)
public class IswIncidentGroup {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_GROUP", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="GROUP_NAME", type="STRING", updateable=false)
	private String a;

	@XmlTransferField(name = "B", sqlname="COST_AUTORUN", type="STRING", defaultvalue="N")
	private String b = "N";

	@XmlTransferField(name = "C", sqlname="COST_DEFAULT_HOURS", type="INTEGER")
	private Integer c;

	@XmlTransferField(name = "D", sqlname="IAP_PERSON_NAME_ORDER", type="SHORT", defaultvalue="0")
	private Short d;

	@XmlTransferField(name = "E", sqlname="INCLUDE_FILLED", type="STRING", defaultvalue="N")
	private String e;

	@XmlTransferField(name = "F", sqlname="PRIMARY_INCIDENT_ID", type="STRING"
						,derivedtable="isw_incident")
	private String f;

	@XmlTransferField(name = "G", sqlname="SEQUENCE_NUMBER", type="SHORT")
	private Short g;
	
	@XmlTransferField(name = "H", sqlname="IAP_RESOURCE_TO_DISPLAY_FROM", type="SHORT", defaultvalue="0")
	private Short h;

	@XmlTransferField(name = "I", sqlname="IAP_TREEVIEW_DISPLAY", type="SHORT", defaultvalue="0")
	private Short i;

	@XmlTransferField(name = "J", sqlname="BY_DATE", type="DATE")
	private Date j;

	@XmlTransferField(name = "K", sqlname="NBR_OF_DAYS_PRIOR", type="SHORT")
	private Short k;

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

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	public Short getD() {
		return d;
	}

	public void setD(Short d) {
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

	public Short getG() {
		return g;
	}

	public void setG(Short g) {
		this.g = g;
	}

	public Short getH() {
		return h;
	}

	public void setH(Short h) {
		this.h = h;
	}

	public Short getI() {
		return i;
	}

	public void setI(Short i) {
		this.i = i;
	}

	public Date getJ() {
		return j;
	}

	public void setJ(Date j) {
		this.j = j;
	}

	public Short getK() {
		return k;
	}

	public void setK(Short k) {
		this.k = k;
	}
	

}
