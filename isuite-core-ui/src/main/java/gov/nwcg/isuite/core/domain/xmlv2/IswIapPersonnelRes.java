package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapPersonnelRes", table = "isw_iap_personnel_res"
	, filterincident="" +
					"( " +
					"  IAP_PERSONNEL_ID in (   " +
					"     select id " +
					"     from isw_iap_personnel "+
					"	  where iap_form_203_id in ( " +         
					"      select id from isw_iap_form_203 where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID) ) " +      
				    "     ) " +    
				    "  ) "+    
					") " +
					"or (" +    
					"  IAP_PERSONNEL_ID in (   " +
					"     select id " +
					"     from isw_iap_personnel "+
					"	  where iap_form_220_id in ( " +         
					"      select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID) ) " +      
				    "     ) " +    
				    "  ) "+    
				    ") " 
	, filtergroup="" +
					"( " +
					"  IAP_PERSONNEL_ID in (   " +
					"     select id " +
					"     from isw_iap_personnel "+
					"	  where iap_form_203_id in ( " +         
					"      select id from isw_iap_form_203 where iap_plan_id in ( select id from isw_iap_plan where incident_group_id = :INCIDENTGROUPID ) " +      
				    "     ) " +    
				    "  ) "+    
					") " +
					"or (" +    
					"  IAP_PERSONNEL_ID in (   " +
					"     select id " +
					"     from isw_iap_personnel "+
					"	  where iap_form_220_id in ( " +         
					"      select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_group_id = :INCIDENTGROUPID ) " +      
				    "     ) " +    
				    "  ) "+    
				    ") " 
	,orderby=" order by id asc "
)
public class IswIapPersonnelRes {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PERSONNEL_RES", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="NAME", type="STRING")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="POSITION_NUM", type="INTEGER")
	private Integer b;

	@XmlTransferField(name = "C", sqlname="IS_TRAINEE", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="IAP_PERSONNEL_ID", type="STRING"
		,derivedtable="isw_iap_personnel")
	private String d;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapPersonnelRes() {
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

}
