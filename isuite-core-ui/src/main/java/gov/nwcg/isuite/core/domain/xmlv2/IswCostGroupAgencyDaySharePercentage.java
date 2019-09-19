package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswCostGroupAgencyDaySharePercentage", table="isw_cost_group_ag_ds_pct"
	, filterincident=" COST_GROUP_DAY_SHARE_ID in (select id from isw_cost_group_ag_ds where cost_group_id in (select id from isw_cost_group where incident_id in (:INCIDENTID) ) )"
)
public class IswCostGroupAgencyDaySharePercentage {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_COST_GROUP_AG_DS_PCT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname = "COST_GROUP_DAY_SHARE_ID", type="STRING", updateable=false
			,derivedtable="isw_cost_group_ag_ds")
	private String a;

	@XmlTransferField(name = "B", sqlname = "PERCENTAGE", type="BIGDECIMAL")
	private BigDecimal b;
	
	@XmlTransferField(name = "C", sqlname="AGENCY_ID", type="STRING"		
		  ,derivedtable="iswl_agency")
	private String c;

	public IswCostGroupAgencyDaySharePercentage() {
		super();
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

}
