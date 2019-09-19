package gov.nwcg.isuite.core.domain.xmlv2;

import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswResourceTraining", table = "isw_resource_training"
	,filterincident=" incident_resource_id in ( "+
				    "   select id "+
				    "   from isw_incident_resource ir2"+
				    "   where ir2.incident_id in (:INCIDENTID) " +
				    ")"
)
public class IswResourceTraining {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_TRAINING", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="INCIDENT_RESOURCE_ID", type="STRING",	
			  			derivedtable="isw_incident_resource")
	private String a;

	@XmlTransferField(name = "B", sqlname="COMPLEXITY_ID", type="STRING",	
					  derivedtable="iswl_complexity")
	private String b;

	@XmlTransferField(name = "C", sqlname="KIND_ID", type="STRING",		
		  derivedtable="iswl_kind")
	private String c;

	@XmlTransferField(name = "D", sqlname="PRIORITY_PROGRAM_ID", type="STRING",		
			  derivedtable="iswl_priority_program")
	private String d;

	@XmlTransferField(name = "E", sqlname="RECOMMENDATION_ID", type="STRING",		
			  derivedtable="iswl_recommendation")
	private String e;

	@XmlTransferField(name = "F", sqlname="IS_INITIAL_ASSIGNMENT", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "START_DATE", type="DATE")
	private Date g;

	@XmlTransferField(name = "H", sqlname = "END_DATE", type="DATE")
	private Date h;

	@XmlTransferField(name = "I", sqlname="IS_FS_PRIORITY_TRAINEE", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="IS_VALID_RED_CARD", type="STRING")
	private String j;

	@XmlTransferField(name = "K", sqlname="OBJECTIVE_ISSUER", type="STRING")
	private String k;

	@XmlTransferField(name = "L", sqlname="INCIDENT_TASK_BOOK", type="STRING")
	private String l;

	@XmlTransferField(name = "M", sqlname="PTB_PERCENTAGE", type="INTEGER")
	private Integer m;

	@XmlTransferField(name = "N", sqlname="NUMBER_OF_ACRES", type="LONG")
	private Integer n;
	
	@XmlTransferField(name = "O", sqlname="TNSP_COMMENTS", type="STRING")
	private String o;

	/**
	 * Default constructor.
	 * 
	 */
	public IswResourceTraining() {
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

	public Date getG() {
		return g;
	}

	public void setG(Date g) {
		this.g = g;
	}

	public Date getH() {
		return h;
	}

	public void setH(Date h) {
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

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}


}
