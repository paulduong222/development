package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswIncidentCostRateState", table="isw_inccost_rate_state"
		,filterincident=" id in ( " +
						"    select inccost_rate_state_id from isw_inccost_rate_state_kind where " +
						"    last_modified_by is not null or last_modified_by != '' " +
				        ") and inccost_rate_id in ( select id from isw_inccost_rate where incident_id in ( :INCIDENTID ) )  "
		,filtergroup=" id in ( " +
						"    select inccost_rate_state_id from isw_inccost_rate_state_kind where " +
						"    last_modified_by is not null or last_modified_by != '' " +
				        ") and  inccost_rate_id in ( select id from isw_inccost_rate where incident_group_id in ( :INCIDENTGROUPID ) )  "
)
public class IswIncidentCostRateState {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE_STATE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="DIRECT_RATE", type="BIGDECIMAL")
	private BigDecimal a;

	@XmlTransferField(name = "B", sqlname="INDIRECT_RATE", type="BIGDECIMAL")
	private BigDecimal b;
	
	@XmlTransferField(name = "C", sqlname="SINGLE_RATE", type="BIGDECIMAL")
	private BigDecimal c;
	
	@XmlTransferField(name = "D", sqlname="SUBORDINATE_RATE", type="BIGDECIMAL")
	private BigDecimal d;
	
	@XmlTransferField(name = "E", sqlname="INCCOST_RATE_ID", type="STRING",updateable=false
			,derivedtable="isw_inccost_rate")
	private String e;

	@XmlTransferField(name = "F", sqlname="AGENCY_ID", type="STRING"		
		  ,derivedtable="iswl_agency")
	private String f;

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

	public BigDecimal getA() {
		return a;
	}

	public void setA(BigDecimal a) {
		this.a = a;
	}

	public BigDecimal getB() {
		return b;
	}

	public void setB(BigDecimal b) {
		this.b = b;
	}

	public BigDecimal getC() {
		return c;
	}

	public void setC(BigDecimal c) {
		this.c = c;
	}

	public BigDecimal getD() {
		return d;
	}

	public void setD(BigDecimal d) {
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
	
}
