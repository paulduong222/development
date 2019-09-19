package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswRscTrainingTrainer", table = "isw_rsc_training_trainer"
	,filterincident=" resource_training_id in ( "+
				    "   select rt.id "+
				    "   from isw_resource_training rt "+
				    "   where rt.incident_resource_id in ("+
				    "      select id from isw_incident_resource where incident_id in (:INCIDENTID) " +
				    "   ) "+
				    ")"
)
public class IswRscTrainingTrainer {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_TRAINING", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="RESOURCE_TRAINING_ID", type="STRING",	
			  			derivedtable="isw_resource_training")
	private String a;

	@XmlTransferField(name = "B", sqlname="LAST_NAME", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="FIRST_NAME", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="REQEST_NUMBER", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="KIND_ID", type="STRING",		
			  derivedtable="iswl_kind")
	private String e;

	@XmlTransferField(name = "F", sqlname="ADDRESS_ID", type="STRING",		
			  derivedtable="isw_address")
	private String f;

	@XmlTransferField(name = "G", sqlname = "EMAIL", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname = "PHONE", type="STRING")
	private String h;

	@XmlTransferField(name = "I", sqlname = "RESOURCE_NAME", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="UNIT_ID", type="STRING",		
			  derivedtable="isw_organization")
	private String j;

	@XmlTransferField(name = "K", sqlname="INCIDENT_RESOURCE_ID", type="STRING",
		  derivedtable="isw_incident_resource")
	private String k;

	/**
	 * Default constructor.
	 * 
	 */
	public IswRscTrainingTrainer() {
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

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}



}
