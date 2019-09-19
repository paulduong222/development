package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlRateClass", table = "iswl_rate_class"
	,filterincident=" id in (" +
		"   select rate_class_id from isw_incident where id in (:INCIDENTID) " +
		") " +
		" or " +
		" id = " +
		"  (select :PARAM_STR_TO_NUM from isw_system_parameter where upper(parameter_name)='DEFAULT_RATE_CLASS_ID' )"
)
public class IswlRateClass {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RATE_CLASS", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "RATE_CLASS_NAME", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "RATE_YEAR", type="INTEGER")
	private Integer b;

	@XmlTransferField(name = "C", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean c;

	public IswlRateClass() {
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

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
		this.c = c;
	}



}
