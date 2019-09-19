package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswAccountCode", table = "isw_account_code"
	, filterincident=" id in (select account_code_id from isw_incident_account_code where incident_id in (:INCIDENTID) ) ")
public class IswAccountCode {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ACCOUNT_CODE", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname = "ACCOUNT_CODE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="AGENCY_ID", type="STRING",	
					  derivedtable="iswl_agency")
	private String b;

	@XmlTransferField(name = "C", sqlname="REGION_UNIT_ID", type="STRING",		
		  derivedtable="iswl_region_code")
	private String c;

	/**
	 * Default constructor.
	 * 
	 */
	public IswAccountCode() {
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

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}


}
