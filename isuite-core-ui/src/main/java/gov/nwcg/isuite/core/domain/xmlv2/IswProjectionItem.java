package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswProjectionItem", table = "isw_projection_item"
	,filterincident=" projection_id in (" +
					"    select id from isw_projection where incident_id in (:INCIDENTID) " +
					") "
	,filtergroup=" projection_id in (" +
				 "    select id from isw_projection where incident_group_id in (:INCIDENTGROUPID) " +
				 ") "
)
public class IswProjectionItem {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_PROJECTION_ITEM", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname = "COST_NAME", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "IS_MANUALLY_ADDED", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "IS_SUPPORT_COST", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname = "QUANTITY", type="INTEGER")
	private Integer d;

	@XmlTransferField(name = "E", sqlname = "AVERAGE_COST", type="BIGDECIMAL")
	private BigDecimal e;

	@XmlTransferField(name = "F", sqlname = "NUMBER_OF_PERSONNEL", type="INTEGER")
	private Integer f;

	@XmlTransferField(name = "G", sqlname="PROJECTION_ID", type="STRING"
		,derivedtable="isw_projection")
	private String g;

	@XmlTransferField(name = "H", sqlname = "total_cost", type="BIGDECIMAL")
	private BigDecimal h;

	@XmlTransferField(name = "I", sqlname = "ITEM_CODE_GROUP", type="STRING")
	private String i;

	@XmlTransferField(name = "J", sqlname = "IS_ITEM_CODE_ACTIVE", type="STRING")
	private String j;

	@XmlTransferField(name = "K", sqlname="ITEM_ID", type="STRING"	
					  ,derivedtable="iswl_kind")
	private String k;

	/**
	 * Default constructor.
	 * 
	 */
	public IswProjectionItem() {
		super();
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

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
		this.d = d;
	}

	public BigDecimal getE() {
		return e;
	}

	public void setE(BigDecimal e) {
		this.e = e;
	}

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public BigDecimal getH() {
		return h;
	}

	public void setH(BigDecimal h) {
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
