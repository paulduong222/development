package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswAddress", table = "isw_address"
	,filterincident=" id in ("+
				    "   (select address_id from isw_contractor where id in ( select contractor_id from isw_incident_contractor where incident_id in (:INCIDENTID) )) "+
				    ") "+
				    " or id in (" +  
				    "  (select address_id from isw_admin_office ) " +
		    		") "+
				    "or id in (" +
				    "   (select at1.mailing_address_id " +
				    "    from isw_assignment_time at1" +
				    "         ,isw_assignment a1"+
				    "         ,isw_work_period_assignment wpa1"+
				    "         ,isw_work_period wp1" +
				    "         ,isw_incident_resource ir1" +
				    "    where ir1.incident_id in ( :INCIDENTID ) " +
				    "    and wp1.incident_resource_id = ir1.id " +
				    "    and wpa1.work_period_id = wp1.id " +
				    "    and a1.id = wpa1.assignment_id " +
				    "    and at1.assignment_id = a1.id " +
				    "   ) " +
				    ") " +
				    "or id in (" +
				    "  ( " +
				    "    select iaph1.address_id "+
				    "    from isw_iap_hospital iaph1 " +
				    "         , isw_iap_form_206 frm1 " +
				    "         , isw_iap_plan iap1 " +
				    "    where iaph1.iap_form_206_id = frm1.id " +
				    "    and frm1.iap_plan_id = iap1.id " +
				    "    and iap1.incident_id in (:INCIDENTID) " +
				    "  ) " +
				    ") "+
				    " or id in ("+
				    "  ( " +
				    "    select iapma1.address_id "+
				    "    from isw_iap_medical_aid iapma1 " +
				    "         , isw_iap_form_206 frm2 " +
				    "         , isw_iap_plan iap2 " +
				    "    where iapma1.iap_form_206_id = frm2.id " +
				    "    and frm2.iap_plan_id = iap2.id " +
				    "    and iap2.incident_id in (:INCIDENTID) " +
				    "  ) " +
				    ") " +
				    " or id in ("+
				    "  ( " +
				    "    select rtt.address_id "+
				    "    from isw_rsc_training_trainer rtt " +
				    "         , isw_resource_training rt " +
				    "         , isw_incident_resource ir3 " +
				    "    where rtt.resource_training_id = rt.id " +
				    "    and rt.incident_resource_id = ir3.id " +
				    "    and ir3.incident_id in (:INCIDENTID) " +
				    "  ) " +
				    ") " +
				    " or id in ("+
				    "  ( " +
				    "    select huc.address_id "+
				    "    from isw_resource_home_unit_contact huc " +
				    "         , isw_incident_resource ir4 " +
				    "    where huc.incident_resource_id = ir4.id " +
				    "    and ir4.incident_id in (:INCIDENTID) " +
				    "  ) " +
				    ") " +
				    " or id in ("+
				    "  ( " +
				    "    select tc.address_id "+
				    "    from isw_training_contact tc " +
				    "         , isw_incident_resource ir5 " +
				    "    where tc.incident_resource_id = ir5.id " +
				    "    and ir5.incident_id in (:INCIDENTID) " +
				    "  ) " +
				    ") "
)
public class IswAddress {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_ADDRESS", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "ADDRESS_LINE_1", type = "STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "ADDRESS_LINE_2", type = "STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "CITY", type = "STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "POSTAL_CODE", type = "STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="COUNTRY_SUBDIVISION_ID", type="STRING"		
					  ,derivedtable="iswl_country_subdivision")
	private String e;

	/**
	 * Default constructor.
	 */
	public IswAddress() {
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

}
