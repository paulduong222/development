package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswCostGroupAgencyDayShare", table = "isw_cost_group_ag_ds"
	, filterincident=" cost_group_id in (select id from isw_cost_group where incident_id in (:INCIDENTID) ) "
)
public class IswCostGroupAgencyDayShare {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_GROUP_AG_DS", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "COST_GROUP_ID", type = "STRING"
		,derivedtable="isw_cost_group")
	private String a;

	@XmlTransferField(name = "B", sqlname = "AGENCY_SHARE_DATE", type = "DATE")
	private Date b;

	@XmlTransferField(name = "C", sqlname = "DELETED_DATE", type = "DATE")
	private Date c;

	/*
	@XmlTransferField(name = "C1", target=IswCostGroupAgencyDaySharePercentage.class
			, targettable="isw_cost_group_ag_ds_pct", targetfield="COST_GROUP_DAY_SHARE_ID", sourcename="Id"
			, cascade=true)
	private Collection<IswCostGroupAgencyDaySharePercentage> c1s = new ArrayList<IswCostGroupAgencyDaySharePercentage>();
	*/
	
	public IswCostGroupAgencyDayShare() {
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

	public Date getB() {
		return b;
	}

	public void setB(Date b) {
		this.b = b;
	}

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
		this.c = c;
	}

}
