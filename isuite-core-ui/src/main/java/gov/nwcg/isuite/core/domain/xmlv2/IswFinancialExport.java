package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswFinancialExport", table = "isw_financial_export"
	,filterincident=" incident_id in (:INCIDENTID) "
	,filtergroup=" incident_group_id = :INCIDENTGROUPID "
)
public class IswFinancialExport {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_FINANCIAL_EXPORT", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING", updateable=false
			,derivedtable="isw_incident")
	private String a;

	@XmlTransferField(name = "B", sqlname="INCIDENT_GROUP_ID", type="STRING", updateable=false
			,derivedtable="isw_incident_group")
	private String b;

	@XmlTransferField(name = "C", sqlname = "EXPORT_DATE", type = "DATE")
	private Date c;

	@XmlTransferField(name = "D", sqlname = "FILE_NAME", type = "STRING")
	private String d;

	/**
	 * Default Constructor
	 */
	public IswFinancialExport() {
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

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

}
