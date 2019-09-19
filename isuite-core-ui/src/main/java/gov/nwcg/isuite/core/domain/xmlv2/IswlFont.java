package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlFont", table = "iswl_font")
public class IswlFont {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_FONT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "A", sqlname = "ENABLED", type="BOOLEAN")
	private String a;

	@XmlTransferField(name = "B", sqlname = "FONT_NAME", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "FONT_SIZE", type="SHORT")
	private Short c;

	@XmlTransferField(name = "D", sqlname = "FONT_WEIGHT", type="SHORT")
	private Short d;

	@XmlTransferField(name = "E", type="SHORT")
	private Short e;

	/**
	 * Default constructor.
	 */
	public IswlFont() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Short getC() {
		return c;
	}

	public void setC(Short c) {
		this.c = c;
	}

	public Short getD() {
		return d;
	}

	public void setD(Short d) {
		this.d = d;
	}

	public Short getE() {
		return e;
	}

	public void setE(Short e) {
		this.e = e;
	}

}
