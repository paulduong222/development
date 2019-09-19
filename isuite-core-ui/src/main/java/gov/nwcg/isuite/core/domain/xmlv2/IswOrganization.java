package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswOrganization", table = "isw_organization"
	, filterincident=" is_standard = :FALSE and incident_id in (:INCIDENTID) " 
	, filtergroup=" is_standard = :FALSE and incident_group_id = :INCIDENTGROUPID "
)
public class IswOrganization  {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_ORGANIZATION", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "ORGANIZATION_TYPE", type = "STRING",nullwhenempty=true)
	private String a;

	@XmlTransferField(name = "B", sqlname = "ORGANIZATION_NAME", type = "STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "UNIT_CODE", type = "STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "COUNTRY_SUBDIVISION", type = "STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname = "IS_DISPATCH_CENTER", type="BOOLEAN")
	private Boolean e;

	@XmlTransferField(name = "F", sqlname = "IS_SUPPLY_CACHE", type="BOOLEAN")
	private Boolean F;

	@XmlTransferField(name = "G", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean g;

	@XmlTransferField(name = "H", sqlname = "IS_LOCAL", type="BOOLEAN")
	private Boolean H;

	@XmlTransferField(name = "I", sqlname = "INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String i;

	@XmlTransferField(name = "J", sqlname = "INCIDENT_GROUP_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_group")
	private String j;

	@XmlTransferField(name = "K", sqlname = "IS_ACTIVE", type = "STRING")
	private String k;

	public IswOrganization(){}

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

	public Boolean getE() {
		return e;
	}

	public void setE(Boolean e) {
		this.e = e;
	}

	public Boolean getF() {
		return F;
	}

	public void setF(Boolean f) {
		F = f;
	}

	public Boolean getG() {
		return g;
	}

	public void setG(Boolean g) {
		this.g = g;
	}

	public Boolean getH() {
		return H;
	}

	public void setH(Boolean h) {
		H = h;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
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

}
