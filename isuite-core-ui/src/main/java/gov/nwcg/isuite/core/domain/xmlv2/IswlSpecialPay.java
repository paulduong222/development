package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlSpecialPay", table = "iswl_special_pay")
public class IswlSpecialPay {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_SPECIAL_PAY", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="CODE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="DESCRIPTION", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "D", sqlname = "IS_AVAIL_TO_AD", type="BOOLEAN")
	private Boolean d;

	@XmlTransferField(name = "E", sqlname = "IS_AVAIL_TO_FED", type="BOOLEAN")
	private Boolean e;

	@XmlTransferField(name = "G", sqlname = "IS_AVAIL_TO_OTHER", type="BOOLEAN")
	private Boolean f;

	@XmlTransferField(name = "H", sqlname = "ORDINAL_VALUE", type="INTEGER")
	private Integer h;

	public IswlSpecialPay() {
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

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
		this.c = c;
	}

	public Boolean getD() {
		return d;
	}

	public void setD(Boolean d) {
		this.d = d;
	}

	public Boolean getE() {
		return e;
	}

	public void setE(Boolean e) {
		this.e = e;
	}

	public Boolean getF() {
		return f;
	}

	public void setF(Boolean f) {
		this.f = f;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

}
