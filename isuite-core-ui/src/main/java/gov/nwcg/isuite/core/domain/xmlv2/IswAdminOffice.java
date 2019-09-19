package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswAdminOffice", table = "isw_admin_office"
	,filterincident=" id in ("+
			"select ca.admin_office_id " +
			"from isw_contractor_agreement ca " +
			"     , isw_contractor c " +
			"     , isw_contr_payment_info cpi " +
			"     , isw_assignment_time at " +
			"     , isw_assignment a " +
			"    , isw_work_period_assignment wpa " +
			"     , isw_work_period wp " +
			"     , isw_incident_resource ir " +
			"where ca.contractor_id = c.id " +
			"and c.id = cpi.contractor_id " +
			"and cpi.assignment_time_id = at.id " +
			"and at.assignment_id = a.id " +
			"and a.id = wpa.assignment_id " +
			"and wpa.work_period_id = wp.id " +
			"and wp.incident_resource_id = ir.id " +
			"and ir.incident_id in ( :INCIDENTID ) " +
		" ) "
)
public class IswAdminOffice {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ADMIN_OFFICE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "OFFICE_NAME", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "PHONE", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "DELETED_DATE", type = "DATE")
	private Date c;

	@XmlTransferField(name = "D", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean d;
	
	@XmlTransferField(name = "E", sqlname="ADDRESS_ID", type="STRING"		
					  ,derivedtable="isw_address")
	private String e;
	
	/**
	 * Default constructor.
	 */
	public IswAdminOffice() {
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

	public Boolean getD() {
		return d;
	}

	public void setD(Boolean d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

}

