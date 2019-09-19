package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapAircraft", table = "isw_iap_aircraft"
	,filterincident=" iap_form_220_id in ( select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID ) ) ) "
	,filtergroup=" iap_form_220_id in ( select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID ) ) ) "
)
public class IswIapAircraft {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_AIRCRAFT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="WING_TYPE", type="STRING")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="AIRCRAFT", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="NBR_AVAILABLE", type="INTEGER")
	private Integer c;
	
	@XmlTransferField(name = "D", sqlname="TYPE", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="MAKE_MODEL", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="FAA_NBR", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="BASE", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="BASE_FAX", type="STRING")
	private String h;
	
	@XmlTransferField(name = "I", sqlname="AVAILABLE", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="START_TIME", type="STRING")
	private String j;
	
	@XmlTransferField(name = "K", sqlname="REMARKS", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="AVAILABLE_DATE", type="DATE")
	private Date l;
	
	@XmlTransferField(name = "M", sqlname="POSITION_NUM", type="INTEGER")
	private Integer m;

	@XmlTransferField(name = "N", sqlname="IS_BLANK_LINE", type="STRING")
	private String n;

	@XmlTransferField(name = "O", sqlname="IAP_FORM_220_ID", type="STRING"
		,derivedtable="isw_iap_form_220")
	private String o;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapAircraft() {
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

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
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

	public Date getL() {
		return l;
	}

	public void setL(Date l) {
		this.l = l;
	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

}
