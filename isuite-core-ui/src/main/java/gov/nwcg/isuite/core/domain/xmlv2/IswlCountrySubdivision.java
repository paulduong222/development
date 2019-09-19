package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlCountrySubdivision", table="iswl_country_subdivision"
)
public class IswlCountrySubdivision {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "CS_NAME", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "CS_ABBREVIATION", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "CS_STANDARD", type="BOOLEAN")
	private Boolean c;
	
	@XmlTransferField(name = "D", sqlname="COUNTRY_ID", type="STRING"
		,derivedtable="iswl_country")
	private String d;


	/**
	 * Default Constructor
	 */
	public IswlCountrySubdivision() {
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


	public String getD() {
		return d;
	}


	public void setD(String d) {
		this.d = d;
	}


}
