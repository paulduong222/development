package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswIncidentCostRateStateKind", table="isw_inccost_rate_state_kind"
		,filter=" (last_modified_by is not null or last_modified_by != '' ) "
		,filterincident=" and INCCOST_RATE_STATE_ID in ( select id from isw_inccost_rate_state where inccost_rate_id in ( select id from isw_inccost_rate where incident_id in ( :INCIDENTID ) ) ) "
		,filtergroup=" and INCCOST_RATE_STATE_ID in ( select id from isw_inccost_rate_state where inccost_rate_id in ( select id from isw_inccost_rate where incident_group_id in ( :INCIDENTGROUPID ) ) ) "
)
public class IswIncidentCostRateStateKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE_STATE_KIND", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="RATE_TYPE", type="STRING")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="RATE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal b;

	@XmlTransferField(name = "C", sqlname="INCCOST_RATE_STATE_ID", type="STRING",updateable=false
			,derivedtable="isw_inccost_rate_state")
	private String c;
	
	/* Derived Tables */
	@XmlTransferField(name = "D", sqlname="KIND_ID", type="STRING"		
					  ,derivedtable="iswl_kind")
	private String d;

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

	public BigDecimal getB() {
		return b;
	}

	public void setB(BigDecimal b) {
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
