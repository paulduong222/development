package gov.nwcg.isuite.core.domain.xmlv2;

import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswReport", table = "isw_report"
	,filterincident=" " +
					"id in ("+
					"   select invoice_report_id " +
					"   from isw_time_invoice " +
					"   where deleted_date is null " +
					"   and is_draft = :FALSE " +
					"   and id in ("+
					"   	select time_invoice_id " +
					"   	from isw_resource_invoice " +
					"   	where resource_id in (" +
					"     		select resource_id " + 
					"     		from isw_incident_resource where incident_id in (:INCIDENTID) " +
					"   	) " +
					"   )" +
					") " +
					" or " +
					"id in ("+
					"   select adjustment_report_id " +
					"   from isw_time_invoice " +
					"   where deleted_date is null " +
					"   and is_draft = :FALSE " +
					"   and id in ("+
					"   	select time_invoice_id " +
					"   	from isw_resource_invoice " +
					"   	where resource_id in (" +
					"     		select resource_id " + 
					"     		from isw_incident_resource where incident_id in (:INCIDENTID) " +
					"   	) " +
					"   )" +
					") "
)
public class IswReport {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_REPORT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="DATE_GENERATED", type="DATE")
	private Date a;
	
	@XmlTransferField(name = "B", sqlname="DATE_REQUESTED", type="DATE")
	private Date b;
	
	@XmlTransferField(name = "C", sqlname="ERROR_DESC", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="FILE_NAME", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="REPORT_NAME", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="RESULT_CODE", type="STRING")
	private String f;
	
	public IswReport() {	
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

	public Date getA() {
		return a;
	}

	public void setA(Date a) {
		this.a = a;
	}

	public Date getB() {
		return b;
	}

	public void setB(Date b) {
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

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

}
