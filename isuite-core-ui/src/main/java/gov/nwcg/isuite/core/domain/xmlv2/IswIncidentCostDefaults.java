package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswIncidentCostDefaults", table="isw_incident_cost_defaults"
	, filterincident=" incident_id in (:INCIDENTID) ")
public class IswIncidentCostDefaults {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_COST_DEFAULTS", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING", updateable=false
			,derivedtable="isw_incident")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="AIR_TRAVEL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal b;
	
	@XmlTransferField(name = "C", sqlname="RENTAL_VEHICLE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal c;
	
	@XmlTransferField(name = "D", sqlname="OTHER_AMOUNT", type="BIGDECIMAL")
	private BigDecimal d;

	@XmlTransferField(name = "E", sqlname="OTHER_DESCRIPTION", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="DEFAULT_HOURS", type="INTEGER")
	private Integer f;
	
	public IswIncidentCostDefaults(){
		
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

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
		this.f = f;
	}

}
