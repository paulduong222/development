package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswContractorRate", table="isw_contractor_rate"
	,filterincident=" id in ("+
					"   select contractor_rate_id "+
					"   from isw_contr_payinfo_rate " +
					"   where contractor_pay_info_id in (" +
					"    select id from isw_contr_payment_info " +
					"   where assignment_time_id in ("+
					"   	select at3.id "+
					"   	from isw_assignment_time at3 "+
					"        	,isw_assignment a3 "+
					"		 	, isw_work_period_assignment wpa3 " +
					"		 	, isw_work_period wp3 " +
					"        	, isw_incident_resource ir3 " +
					"   	where at3.assignment_id = a3.id " +
					"   	and a3.id = wpa3.assignment_id " + 
					"   	and wpa3.work_period_id = wp3.id " +
					"   	and wp3.incident_resource_id = ir3.id " +
					"   	and ir3.incident_id in (:INCIDENTID) " +
					"   ) " +
					"  ) " +
					") "
)
public class IswContractorRate {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_CONTRACTOR_RATE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "RATE_TYPE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "UNIT_OF_MEASURE", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "RATE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal c;

	@XmlTransferField(name = "D", sqlname = "GUARANTEE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal d;

	@XmlTransferField(name = "E", sqlname = "DESCRIPTION", type="STRING")
	private String e;

	
	public IswContractorRate() {

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

}
