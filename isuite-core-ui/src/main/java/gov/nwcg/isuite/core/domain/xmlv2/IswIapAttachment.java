package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapAttachment", table = "isw_iap_attachment"
	,filterincident=" iap_plan_id in (select id from isw_iap_plan where incident_id in (:INCIDENTID) ) "
	,filtergroup=" iap_plan_id in (select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID) ) "
)
public class IswIapAttachment {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_ATTACHMENT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_PLAN_ID", type="STRING"
		,derivedtable="isw_iap_plan")
	private String a;

	@XmlTransferField(name = "B", sqlname="ATTACHMENT_NAME", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="FILENAME", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="ATTACHED", type="STRING")
	private String d;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapAttachment() {
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

}
