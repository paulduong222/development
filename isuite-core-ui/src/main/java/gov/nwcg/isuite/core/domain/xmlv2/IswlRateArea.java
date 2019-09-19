package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlRateArea", table = "iswl_rate_area")
public class IswlRateArea {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RATE_AREA", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="CODE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="DESCRIPTION", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean d;

	public IswlRateArea() {
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

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public Boolean getD() {
		return d;
	}

	public void setD(Boolean d) {
		this.d = d;
	}

}
