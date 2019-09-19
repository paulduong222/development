package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswResourceKind", table = "isw_resource_kind"
	,filterincident=" resource_id in ( select id from isw_resource where id in (select resource_id from isw_incident_resource where incident_id in (:INCIDENTID) ) ) "
)
public class IswResourceKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_KIND", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="RESOURCE_ID", type="STRING", updateable=false
			,derivedtable="isw_resource")
	private String a;

	@XmlTransferField(name = "B", sqlname = "IS_TRAINING", type="BOOLEAN")
	private Boolean b = false;

	@XmlTransferField(name = "C", sqlname = "IS_PRIMARY", type="BOOLEAN")
	private Boolean c = false;
	
	@XmlTransferField(name = "D", sqlname="KIND_ID", type="STRING"		
		  ,derivedtable="iswl_kind")
	private String d;

	public IswResourceKind() {

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

	public Boolean getB() {
		return b;
	}

	public void setB(Boolean b) {
		this.b = b;
	}

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

}
