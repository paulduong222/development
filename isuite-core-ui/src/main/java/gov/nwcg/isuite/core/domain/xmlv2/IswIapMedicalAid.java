package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapMedicalAid", table = "isw_iap_medical_aid"
	,filterincident=" iap_form_206_id in ( select id from isw_iap_form_206 where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID ) ) ) "
	,filtergroup=" iap_form_206_id in ( select id from isw_iap_form_206 where iap_plan_id in ( select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID ) ) ) "
)
public class IswIapMedicalAid {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_MEDICAL_AID", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_FORM_206_ID", type="STRING"
		,derivedtable="isw_iap_form_206")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="TYPE", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="NAME", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="LOCATION", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="PHONE", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="PARAMEDICS", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="SERVICE_LEVEL", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname="LIFE_SUPPORT", type="STRING")
	private String h;

	@XmlTransferField(name = "I", sqlname="EMS_FREQUENCY", type="STRING")
	private String i;

	@XmlTransferField(name = "J", sqlname="AIR_TYPE", type="STRING")
	private String j;
	
	@XmlTransferField(name = "K", sqlname="CAPABILITY", type="STRING")
	private String k;

	@XmlTransferField(name = "L", sqlname="POSITION_NUM", type="INTEGER")
	private Integer l;

	@XmlTransferField(name = "M", sqlname="IS_BLANK_LINE", type="STRING")
	private String m;

	@XmlTransferField(name = "O", sqlname="ADDRESS_ID", type="STRING"		
		  ,derivedtable="isw_address")
	private String o;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapMedicalAid() {
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

	public Integer getL() {
		return l;
	}

	public void setL(Integer l) {
		this.l = l;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

}
