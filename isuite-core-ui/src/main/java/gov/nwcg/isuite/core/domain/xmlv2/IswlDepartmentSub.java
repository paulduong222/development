package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlDepartmentSub", table="iswl_department_sub")
public class IswlDepartmentSub {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_DEPARTMENT_SUB", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="CODE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="DESCRIPTION", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "TI1", sqlname="DEPARTMENT_ID", type="STRING"
		, derivedtable="iswl_department")
	private String tI1;

	public IswlDepartmentSub() {
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

	public String getTI1() {
		return tI1;
	}

	public void setTI1(String ti1) {
		tI1 = ti1;
	}

}
