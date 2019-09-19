package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;


@XmlTransferTable(name = "IswResourceMobilization", table = "isw_resource_mobilization"
	,filterincident=" resource_id in ( select id from isw_resource where id in (select resource_id from isw_incident_resource where incident_id in (:INCIDENTID) ) ) "
)
public class IswResourceMobilization {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_MOBILIZATION", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="RESOURCE_ID", type="STRING", updateable=false
			,derivedtable="isw_resource")
	private String a;

	@XmlTransferField(name = "B", sqlname="START_DATE", type="DATE")
	private Date b;


	public IswResourceMobilization(){

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


}
