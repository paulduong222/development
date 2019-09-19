package gov.nwcg.isuite.core.domain.xmlv2;


import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlKindAltDesc", table="iswl_kind_alt_desc")
public class IswlKindAltDesc {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_KIND_ALT_DESC", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "DESCRIPTION", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "KIND_ID", type="LONG")
	private Long b;

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

	public Long getB() {
		return b;
	}

	public void setB(Long b) {
		this.b = b;
	}
	

}
