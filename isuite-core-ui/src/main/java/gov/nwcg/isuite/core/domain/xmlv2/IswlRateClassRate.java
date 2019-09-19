package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswlRateClassRate", table="iswl_rate_class_rate"
	,filterincident=" rate_class_id in (" +
					"   select id from iswl_rate_class " +
					"   where id in (select rate_class_id from isw_incident where id in (:INCIDENTID) ) " +
					") " +
					" or " +
					" rate_class_id = " +
					"  (select :PARAM_STR_TO_NUM from isw_system_parameter where upper(parameter_name)='DEFAULT_RATE_CLASS_ID' )"
)
public class IswlRateClassRate {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RATE_CLASS_RATE", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "RATE_CLASS_ID", type="STRING"
		,derivedtable="iswl_rate_class")
	private String a;

	@XmlTransferField(name = "B", sqlname = "AREA", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "RATE", type="BIGDECIMAL")
	private BigDecimal c;

	@XmlTransferField(name = "D", sqlname = "RATE_YEAR", type="INTEGER")
	private Integer d;

	@XmlTransferField(name = "E", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean e;

	@XmlTransferField(name = "F", sqlname = "RATE_CLASSNAME", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "RATE_CLASSCODE", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname = "IS_ACTIVE", type="STRING")
    private String h;
	
	public IswlRateClassRate() {
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

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
		this.d = d;
	}

	public Boolean getE() {
		return e;
	}

	public void setE(Boolean e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

}
