package gov.nwcg.isuite.core.domain.xmlv2;

import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswResourceHomeUnitContact", table = "isw_resource_home_unit_contact"
	,filterincident=" incident_resource_id in ( "+
				    "   select id "+
				    "   from isw_incident_resource ir2"+
				    "   where ir2.incident_id in (:INCIDENTID) " +
				    ")"
)
public class IswResourceHomeUnitContact {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_HOME_UNIT_CONTACT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="INCIDENT_RESOURCE_ID", type="STRING",	
			  			derivedtable="isw_incident_resource")
	private String a;

	@XmlTransferField(name = "B", sqlname="UNIT_ID", type="STRING",	
					  derivedtable="isw_organization")
	private String b;

	@XmlTransferField(name = "C", sqlname="ADDRESS_ID", type="STRING",		
		  derivedtable="isw_address")
	private String c;

	@XmlTransferField(name = "D", sqlname="LAST_NAME", type="STRING")	
	private String d;

	@XmlTransferField(name = "E", sqlname="FIRST_NAME", type="STRING")	
	private String e;

	@XmlTransferField(name = "F", sqlname="PHONE", type="STRING")	
	private String f;

	@XmlTransferField(name = "G", sqlname="EMAIL", type="STRING")	
	private String g;

	@XmlTransferField(name = "H", sqlname="CONTACT_NAME", type="STRING")	
	private String h;

	/**
	 * Default constructor.
	 * 
	 */
	public IswResourceHomeUnitContact() {
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

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
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
