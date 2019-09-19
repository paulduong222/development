package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswRscTrainingObjective", table = "isw_rsc_training_objective"
	,filterincident=" resource_training_id in ( "+
				    "   select rt.id "+
				    "   from isw_resource_training rt "+
				    "   where rt.incident_resource_id in ("+
				    "      select id from isw_incident_resource where incident_id in (:INCIDENTID) " +
				    "   ) "+
				    ")"
)
public class IswRscTrainingObjective {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RSC_TRAINING_OBJECTIVE", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="RESOURCE_TRAINING_ID", type="STRING",	
			  			derivedtable="isw_resource_training")
	private String a;

	@XmlTransferField(name = "B", sqlname="OBJECTIVE", type="STRING")	
	private String b;

	@XmlTransferField(name = "C", sqlname="POSITION_NUM", type="INTEGER")		
	private String c;

	/**
	 * Default constructor.
	 * 
	 */
	public IswRscTrainingObjective() {
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
