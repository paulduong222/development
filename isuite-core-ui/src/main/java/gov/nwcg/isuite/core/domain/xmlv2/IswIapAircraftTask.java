package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapAircraftTask", table = "isw_iap_aircraft_task"
	,filterincident=" iap_form_220_id in ( select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID ) ) ) "
	,filtergroup=" iap_form_220_id in ( select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID ) ) ) "
)
public class IswIapAircraftTask {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_AIRCRAFT_TASK", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="TYPE", type="STRING")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="NAME", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="START_TIME", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="FLY_FROM", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="FLY_TO", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="POSITION_NUM", type="INTEGER")
	private Integer f;

	@XmlTransferField(name = "G", sqlname="IS_BLANK_LINE", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="IAP_FORM_220_ID", type="STRING"
		,derivedtable="isw_iap_form_220")
	private String h;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapAircraftTask() {
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

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
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
